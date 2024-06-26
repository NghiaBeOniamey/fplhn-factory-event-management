package fplhn.udpm.identity.infrastructure.security.oauth2;


import com.fasterxml.jackson.core.JsonProcessingException;
import fplhn.udpm.identity.entity.Client;
import fplhn.udpm.identity.entity.Module;
import fplhn.udpm.identity.infrastructure.constant.CommonStatus;
import fplhn.udpm.identity.infrastructure.constant.EntityStatus;
import fplhn.udpm.identity.infrastructure.constant.UserType;
import fplhn.udpm.identity.infrastructure.security.exception.RedirectException;
import fplhn.udpm.identity.infrastructure.security.repository.ClientAuthRepository;
import fplhn.udpm.identity.infrastructure.security.repository.ModuleAuthRepository;
import fplhn.udpm.identity.infrastructure.security.repository.StaffModuleRoleAuthRepository;
import fplhn.udpm.identity.infrastructure.security.response.ModuleAvailableResponse;
import fplhn.udpm.identity.infrastructure.security.response.TokenUriResponse;
import fplhn.udpm.identity.infrastructure.security.service.RefreshTokenService;
import fplhn.udpm.identity.infrastructure.security.service.TokenProvider;
import fplhn.udpm.identity.infrastructure.security.user.UserPrincipal;
import fplhn.udpm.identity.util.CookieUtils;
import fplhn.udpm.identity.util.GenerateClientUtils;
import fplhn.udpm.identity.util.URLBuilder;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import static fplhn.udpm.identity.infrastructure.security.oauth2.HttpCookieOAuth2AuthorizationRequestRepository.REDIRECT_URI_PARAM_COOKIE_NAME;

@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final TokenProvider tokenProvider;

    private final RefreshTokenService refreshTokenService;

    private final HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;

    private final ModuleAuthRepository moduleAuthRepository;

    private final ClientAuthRepository clientAuthRepository;

    private final StaffModuleRoleAuthRepository staffModuleRoleAuthRepository;

    @Value("${app.default-target-url-identity}")
    private String DEFAULT_TARGET_URL_IDENTITY;

    @Value("${app.default-target-url-identity-staff}")
    private String DEFAULT_TARGET_URL_IDENTITY_STAFF;

    @Value("${app.default-target-url-identity-student}")
    private String DEFAULT_TARGET_URL_IDENTITY_STUDENT;

    @Value("${app.default-identity-name}")
    private String DEFAULT_IDENTITY_NAME;

    @Value("${app.default-identity-code}")
    private String DEFAULT_IDENTITY_CODE;

    @Autowired
    public OAuth2AuthenticationSuccessHandler(
            TokenProvider tokenProvider,
            HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository,
            ModuleAuthRepository moduleAuthRepository,
            RefreshTokenService refreshTokenService,
            ClientAuthRepository clientAuthRepository,
            StaffModuleRoleAuthRepository staffModuleRoleAuthRepository
    ) {
        this.tokenProvider = tokenProvider;
        this.httpCookieOAuth2AuthorizationRequestRepository = httpCookieOAuth2AuthorizationRequestRepository;
        this.moduleAuthRepository = moduleAuthRepository;
        this.refreshTokenService = refreshTokenService;
        this.clientAuthRepository = clientAuthRepository;
        this.staffModuleRoleAuthRepository = staffModuleRoleAuthRepository;
    }

    //SET DEFAULT SECRET KEY IS MODULE IDENTITY
    @PostConstruct
    public void init() {
        tokenProvider.setTokenSecret(getSecretKeyModuleIdentity());
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String targetUrl = determineTargetUrl(request, response, authentication);
        if (response.isCommitted()) {
            logger.debug("Response has already been committed. Unable to redirect to " + targetUrl);
            return;
        }
        clearAuthenticationAttributes(request, response);
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        try {
            Optional<String> redirectUri = CookieUtils.getCookie(request, REDIRECT_URI_PARAM_COOKIE_NAME)
                    .map(Cookie::getValue);
            if (redirectUri.isEmpty()) throw new RedirectException("Redirect uri not found! Please try again later!");

            Optional<Module> module = moduleAuthRepository.findByUrl(redirectUri.get());
            if (module.isEmpty()) throw new RedirectException("Module not found! Please try again later!");

            Optional<Client> client = processClient(module, DEFAULT_TARGET_URL_IDENTITY);
            if (client.isEmpty()) throw new RedirectException("Server error! Please try again later!");

            List<ModuleAvailableResponse> moduleAvailableResponses = staffModuleRoleAuthRepository
                    .findModuleAvailableByUserId(((UserPrincipal) authentication.getPrincipal())
                            .getId());
            tokenProvider.setTokenSecret(client.get().getSecretKey());
            String targetUrl = redirectUri.orElse(getDefaultTargetUrl());
            String[] token = tokenProvider.createToken(authentication, module.get().getUrl(), moduleAvailableResponses);
            String tokenAuthorization = token[0];
            String userType = token[1];
            String status = token[2];
            String campusStatus = token[3];

            if (campusStatus.equals(CommonStatus.INACTIVE.name()))
                throw new RedirectException("Campus has been deleted or inactive! Please try again later!");

            if (status.equals(EntityStatus.DELETED.name()))
                throw new RedirectException("User has been deleted or inactive! Please try again later!");

            String refreshToken = refreshTokenService.createRefreshToken(authentication).getRefreshToken();
            if (tokenAuthorization.isEmpty() || refreshToken.isEmpty()) {
                throw new RedirectException("Server error! Please try again later!");
            }

            if (!module.get().getUrl().startsWith(DEFAULT_TARGET_URL_IDENTITY))
                tokenProvider.setTokenSecret(getSecretKeyModuleIdentity());

            if (userType.equals(UserType.SINH_VIEN.name()))
                return buildSuccessUrl(
                        DEFAULT_TARGET_URL_IDENTITY_STUDENT,
                        new TokenUriResponse(tokenAuthorization, refreshToken).getTokenAuthorizationSimple()
                );
            else
                return buildSuccessUrl(
                        targetUrl,
                        new TokenUriResponse(tokenAuthorization, refreshToken).getTokenAuthorizationSimple()
                );
        } catch (
                BadRequestException |
                JsonProcessingException |
                MalformedURLException |
                URISyntaxException |
                RedirectException e
        ) {
            e.printStackTrace(System.out);
            return buildErrorUri(e.getMessage());
        }
    }

    protected void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
        super.clearAuthenticationAttributes(request);
        httpCookieOAuth2AuthorizationRequestRepository.removeAuthorizationRequestCookies(request, response);
    }

    private Optional<Client> processClient(Optional<Module> module, String defaultTargetUrlIdentity) {
        if (module.isEmpty()) return Optional.empty();
        Optional<Client> client = Optional.ofNullable(module.get().getClient());
        if (module.get().getUrl().startsWith(defaultTargetUrlIdentity) && client.isEmpty()) {
            String clientSecret = GenerateClientUtils.generateRandomClientSecret(32);
            String clientId = GenerateClientUtils.generateRandomClientID();
            Client newClient = new Client();
            newClient.setClientId(clientId);
            newClient.setClientSecret(clientSecret);
            newClient.setModule(module.get());
            newClient.setEntityStatus(EntityStatus.NOT_DELETED);
            Client clientIdentity = clientAuthRepository.save(newClient);
            client = Optional.of(clientIdentity);
        }
        if (client.isEmpty()) return Optional.empty();
        if (client.get().getSecretKey() == null) {
            SecretKey tokenSecret = GenerateClientUtils.generateJwtSecretKey(client.get().getClientId(), client.get().getClientSecret());
            client.get().setSecretKey(tokenSecret);
            clientAuthRepository.save(client.get());
        }
        return client;
    }

    private SecretKey getSecretKeyModuleIdentity() {
        Optional<Module> module = moduleAuthRepository.findByIdentityStartWithUrl(DEFAULT_TARGET_URL_IDENTITY);
        if (module.isEmpty()) {
            Module newModule = new Module();
            newModule.setUrl(DEFAULT_TARGET_URL_IDENTITY_STAFF);
            newModule.setMa(DEFAULT_IDENTITY_CODE);
            newModule.setTen(DEFAULT_IDENTITY_NAME);
            Module moduleIdentity = moduleAuthRepository.save(newModule);
            Client newClient = new Client();
            String clientId = GenerateClientUtils.generateRandomClientID();
            String clientSecret = GenerateClientUtils.generateRandomClientSecret(32);
            SecretKey secretKey = GenerateClientUtils.generateJwtSecretKey(clientId, clientSecret);
            newClient.setClientId(clientId);
            newClient.setClientSecret(clientSecret);
            newClient.setSecretKey(secretKey);
            newClient.setModule(moduleIdentity);
            Client clientIdentity = clientAuthRepository.save(newClient);
            return clientIdentity.getSecretKey();
        } else {
            Optional<Client> client = Optional.ofNullable(module.get().getClient());
            if (client.isEmpty()) {
                String clientId = GenerateClientUtils.generateRandomClientID();
                String clientSecret = GenerateClientUtils.generateRandomClientSecret(32);
                SecretKey secretKey = GenerateClientUtils.generateJwtSecretKey(clientId, clientSecret);
                Client newClient = new Client();
                newClient.setClientId(clientId);
                newClient.setClientSecret(clientSecret);
                newClient.setSecretKey(secretKey);
                newClient.setModule(module.get());
                Client clientIdentity = clientAuthRepository.save(newClient);
                return clientIdentity.getSecretKey();
            }
            if (client.get().getSecretKey() == null && client.get().getClientId() == null && client.get().getClientSecret() == null) {
                String clientId = GenerateClientUtils.generateRandomClientID();
                String clientSecret = GenerateClientUtils.generateRandomClientSecret(32);
                SecretKey secretKey = GenerateClientUtils.generateJwtSecretKey(clientId, clientSecret);
                client.get().setClientId(clientId);
                client.get().setClientSecret(clientSecret);
                client.get().setSecretKey(secretKey);
                clientAuthRepository.save(client.get());
                return client.get().getSecretKey();
            } else if (client.get().getSecretKey() == null) {
                SecretKey secretKey = GenerateClientUtils.generateJwtSecretKey(client.get().getClientId(), client.get().getClientSecret());
                client.get().setSecretKey(secretKey);
                clientAuthRepository.save(client.get());
            }
            return client.get().getSecretKey();
        }

    }

    private String buildErrorUri(String errorMessage) {
        return DEFAULT_TARGET_URL_IDENTITY + "/error?error=" + errorMessage;
        //URLBuilder urlBuilder = new URLBuilder(DEFAULT_TARGET_URL_IDENTITY + "/error");
        //urlBuilder.addParameter("error", errorMessage);
        //try {
        //return urlBuilder.getURL();
        //} catch (Exception e) {
        //     e.printStackTrace(System.out);
        //      return DEFAULT_TARGET_URL_IDENTITY + "/error?error=Server error! Please try again later!";
        //    }
    }

    private String buildSuccessUrl(String targetUrl, String token) throws MalformedURLException, URISyntaxException {
        URLBuilder urlBuilder = new URLBuilder(targetUrl);
        urlBuilder.addParameter("state", token);
        try {
            String relativeURL = urlBuilder.getRelativeURL();
            return targetUrl + relativeURL;
        } catch (Exception e) {
            e.printStackTrace(System.out);
            return DEFAULT_TARGET_URL_IDENTITY + "/error?error=Server error! Please try again later!";
        }
    }

}
