package fplhn.udpm.identity.core.authentication.service.impl;

import fplhn.udpm.identity.core.authentication.model.request.SwitchModuleRequest;
import fplhn.udpm.identity.core.authentication.repository.ModuleAuthEntryRepository;
import fplhn.udpm.identity.core.authentication.service.ModuleSwitchService;
import fplhn.udpm.identity.entity.Client;
import fplhn.udpm.identity.entity.Module;
import fplhn.udpm.identity.infrastructure.constant.EntityStatus;
import fplhn.udpm.identity.infrastructure.constant.UserType;
import fplhn.udpm.identity.infrastructure.security.repository.ClientAuthRepository;
import fplhn.udpm.identity.infrastructure.security.response.TokenUriResponse;
import fplhn.udpm.identity.infrastructure.security.service.RefreshTokenService;
import fplhn.udpm.identity.infrastructure.security.service.TokenProvider;
import fplhn.udpm.identity.util.GenerateClientUtils;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import javax.crypto.SecretKey;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ModuleSwitchServiceImpl implements ModuleSwitchService {

    private final ModuleAuthEntryRepository moduleAuthEntryRepository;

    private final TokenProvider tokenProvider;

    private final RefreshTokenService refreshTokenService;

    @Value("${app.default-target-url-identity}")
    private String DEFAULT_TARGET_URL_IDENTITY;

    private final ClientAuthRepository clientAuthRepository;

    @Value("${app.default-target-url-identity-error}")
    private String DEFAULT_TARGET_URL_IDENTITY_ERROR;

    @Value("${app.default-target-url-identity-staff}")
    private String DEFAULT_TARGET_URL_IDENTITY_STAFF;

    @Value("${app.default-target-url-identity-student}")
    private String DEFAULT_TARGET_URL_IDENTITY_STUDENT;

    @Override
    public String switchModule(SwitchModuleRequest request) throws BadRequestException {
        String moduleCode = request.getModuleCode();
        Long userId = request.getUserId();
        String userType = request.getUserType();
        String identityToken = request.getIdentityToken();

        if (moduleCode == null || userId == null || userType == null || identityToken == null) {
            return buildErrorUrl("Invalid request! Please try again later!");
        }

        boolean isValidToken = tokenProvider.validateToken(identityToken);
        if (!isValidToken) return buildErrorUrl("Token has been expired! Please Login again!");

        Optional<Module> module = moduleAuthEntryRepository.findByMa(moduleCode);
        if (module.isEmpty()) return buildErrorUrl("Module has not registered to system! Please try again later!");

        if (userType.equals("CAN_BO")) {
            Optional<Client> client = processClient(module, DEFAULT_TARGET_URL_IDENTITY);
            if (client.isEmpty()) {
                return buildErrorUrl("Module has not registered to system! Please try again later!");
            }
            tokenProvider.setTokenSecret(client.get().getSecretKey());
            String targetUrl = module.get().getRedirectRoute() == null ? module.get().getUrl() : module.get().getUrl() + "/" + module.get().getRedirectRoute();
            String token = tokenProvider.createToken(userId, module.get().getUrl(), "CAN_BO");
            String refreshToken = refreshTokenService.createRefreshToken(userId, UserType.CAN_BO).getRefreshToken();
            if (token.isEmpty() || refreshToken.isEmpty()) {
                return buildErrorUrl("Server error! Please try again later!");
            }
            if (!module.get().getUrl().startsWith(DEFAULT_TARGET_URL_IDENTITY)) {
                tokenProvider.setTokenSecret(getSecretKeyModuleIdentity());
            }
            return buildSuccessUrl(targetUrl, new TokenUriResponse(token, refreshToken).getTokenAuthorizationSimple());
        } else {
            Optional<Client> client = processClient(module, DEFAULT_TARGET_URL_IDENTITY);
            if (client.isEmpty()) {
                return buildErrorUrl("Module has not registered to system! Please try again later!");
            }
            tokenProvider.setTokenSecret(client.get().getSecretKey());
            String targetUrl = module.get().getUrl();
            String token = tokenProvider.createToken(userId, module.get().getUrl(), "SINH_VIEN");
            String refreshToken = refreshTokenService.createRefreshToken(userId, UserType.SINH_VIEN).getRefreshToken();
            if (token.isEmpty() || refreshToken.isEmpty()) {
                return buildErrorUrl("Server error! Please try again later!");
            }
            if (!module.get().getUrl().startsWith(DEFAULT_TARGET_URL_IDENTITY)) {
                tokenProvider.setTokenSecret(getSecretKeyModuleIdentity());
            }
            return buildSuccessUrl(
                    module.get().getUrl() + "/permission-event",
                    new TokenUriResponse(token, refreshToken).getTokenAuthorizationSimple()
            );
        }
    }

    private Optional<Client> processClient(Optional<Module> module, String defaultTargetUrlIdentity) {
        if (module.isEmpty()) {
            return Optional.empty();
        }

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

        if (client.isEmpty()) {
            return Optional.empty();
        }

        if (client.get().getSecretKey() == null) {
            SecretKey tokenSecret = GenerateClientUtils.generateJwtSecretKey(client.get().getClientId(), client.get().getClientSecret());
            client.get().setSecretKey(tokenSecret);
            clientAuthRepository.save(client.get());
        }

        return client;
    }

    private SecretKey getSecretKeyModuleIdentity() {
        Optional<Module> module = moduleAuthEntryRepository.findByIdentityStartWithUrl(DEFAULT_TARGET_URL_IDENTITY);
        if (module.isEmpty()) {
            Module newModule = new Module();
            newModule.setUrl(DEFAULT_TARGET_URL_IDENTITY_STAFF);
            newModule.setMa("QLPQBMUDPM");
            newModule.setTen("Quản Lý Phân Quyền Bộ Môn Ứng Dụng Phần Mềm");
            Module moduleIdentity = moduleAuthEntryRepository.save(newModule);
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

    private String buildErrorUrl(String errorMessage) {
        return UriComponentsBuilder
                .fromUriString(DEFAULT_TARGET_URL_IDENTITY_ERROR)
                .queryParam("error", errorMessage)
                .build().toUriString();
    }

    private String buildSuccessUrl(String targetUrl, String token) {
        return UriComponentsBuilder.fromUriString(targetUrl)
                .queryParam("state", token)
                .build().toUriString();
    }

}
