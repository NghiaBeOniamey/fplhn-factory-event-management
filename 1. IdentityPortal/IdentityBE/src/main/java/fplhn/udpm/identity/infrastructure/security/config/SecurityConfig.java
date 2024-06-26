package fplhn.udpm.identity.infrastructure.security.config;


import fplhn.udpm.identity.infrastructure.config.listener.ModuleInsertedEvent;
import fplhn.udpm.identity.infrastructure.constant.ApiConstant;
import fplhn.udpm.identity.infrastructure.constant.RoleConstants;
import fplhn.udpm.identity.infrastructure.security.exception.RestAuthenticationEntryPoint;
import fplhn.udpm.identity.infrastructure.security.filter.TokenAuthenticationFilter;
import fplhn.udpm.identity.infrastructure.security.oauth2.CustomOAuth2UserService;
import fplhn.udpm.identity.infrastructure.security.oauth2.HttpCookieOAuth2AuthorizationRequestRepository;
import fplhn.udpm.identity.infrastructure.security.oauth2.OAuth2AuthenticationFailureHandler;
import fplhn.udpm.identity.infrastructure.security.oauth2.OAuth2AuthenticationSuccessHandler;
import fplhn.udpm.identity.infrastructure.security.repository.ModuleAuthRepository;
import fplhn.udpm.identity.util.Helper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true,
        prePostEnabled = true
)
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;

    private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;

    private final OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;

    private final ModuleAuthRepository moduleAuthRepository;

    private List<String> allowedOrigins;

    @PostConstruct
    public void init() {
        updateAllowedOrigins();
    }

    public void updateAllowedOrigins() {
        allowedOrigins = getUpdatedOrigins();
    }

    @EventListener
    public void onModuleInsertedEvent(ModuleInsertedEvent event) {
        System.out.println("ModuleInsertedEvent triggered!");
        updateAllowedOrigins();
    }

    @Bean
    public TokenAuthenticationFilter tokenAuthenticationFilter() {
        return new TokenAuthenticationFilter();
    }

    @Bean
    public HttpCookieOAuth2AuthorizationRequestRepository cookieAuthorizationRequestRepository() {
        return new HttpCookieOAuth2AuthorizationRequestRepository();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    public AuthenticationManager authenticationManager(PasswordEncoder passwordEncoder, UserDetailsService userDetailsService) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(userDetailsService);
        return new ProviderManager(provider);
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        source.registerCorsConfiguration("/**", config.applyPermitDefaultValues());
        config.setAllowedHeaders(List.of("Authorization", "Cache-Control", "Content-Type", "*"));
        config.setAllowedOrigins(allowedOrigins);
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PUT", "OPTIONS", "PATCH", "DELETE"));
        config.setAllowCredentials(true);
        config.setExposedHeaders(List.of("Authorization"));
        return source;
    }

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        http.cors(c -> c.configurationSource(corsConfigurationSource()));
        http.sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.formLogin(AbstractHttpConfigurer::disable);
        http.httpBasic(AbstractHttpConfigurer::disable);
        http.exceptionHandling(e -> e.authenticationEntryPoint(new RestAuthenticationEntryPoint()));
        http.authorizeHttpRequests(auth -> auth.requestMatchers(
                        "/",
                        "/error",
                        "/favicon.ico",
                        "/*/*.png",
                        "/*/*.gif",
                        "/*/*.svg",
                        "/*/*.jpg",
                        "/*/*.html",
                        "/*/*.css",
                        "/*/*.js"
                )
                .permitAll());
        http.authorizeHttpRequests(
                auth -> auth.requestMatchers(
                                "/auth/**",
                                Helper.appendWildcard(ApiConstant.API_AUTH_PREFIX),
                                Helper.appendWildcard(ApiConstant.API_CONNECTOR_PREFIX),
                                "/oauth2/**",
                                "/api/entry-module/**",
                                "/api/support/mail/**",
                                "/api/module-switch/**"
                        )
                        .permitAll()
        );
        http.authorizeHttpRequests(
                auth -> auth.requestMatchers(
                                Helper.appendWildcard(ApiConstant.API_SWAGGER_API_PREFIX),
                                Helper.appendWildcard(ApiConstant.API_SWAGGER_UI_PREFIX)
                        )
                        .permitAll()
        );
        http.authorizeHttpRequests(
                auth -> auth.requestMatchers(
                                Helper.appendWildcard(ApiConstant.API_DEPARTMENT_PREFIX),
                                Helper.appendWildcard(ApiConstant.API_DEPARTMENT_CAMPUS_PREFIX),
                                Helper.appendWildcard(ApiConstant.API_MAJOR_PREFIX),
                                Helper.appendWildcard(ApiConstant.API_MAJOR_CAMPUS_PREFIX)
                        )
                        .hasAnyAuthority(RoleConstants.ACTOR_BDT_HO, RoleConstants.ACTOR_TBDT_CS));
        http.authorizeHttpRequests(
                auth -> auth.requestMatchers(
                                Helper.appendWildcard(ApiConstant.API_CAMPUS_PREFIX),
                                Helper.appendWildcard(ApiConstant.API_SEMESTER_PREFIX)
                        )
                        .hasAnyAuthority(RoleConstants.ACTOR_BDT_HO)
        );
        http.authorizeHttpRequests(
                auth -> auth.requestMatchers(
                                Helper.appendWildcard(ApiConstant.API_STAFF_PREFIX),
                                Helper.appendWildcard(ApiConstant.API_DOWNLOAD_FILE_STAFF),
                                Helper.appendWildcard(ApiConstant.API_UPLOAD_FILE_STAFF),
                                Helper.appendWildcard(ApiConstant.API_DOWNLOAD_FILE_STAFF_EXPORT)
                        )
                        .hasAnyAuthority(RoleConstants.ACTOR_TBDT_CS, RoleConstants.ACTOR_QLDT, RoleConstants.ACTOR_CNBM)
        );
        http.authorizeHttpRequests(
                auth -> auth.requestMatchers(
                                Helper.appendWildcard(ApiConstant.API_STUDENT_PREFIX)
                        )
                        .hasAnyAuthority(RoleConstants.ACTOR_TBDT_CS, RoleConstants.ACTOR_QLDT, RoleConstants.ACTOR_SV)
        );
        http.authorizeHttpRequests(
                auth -> auth.requestMatchers(
                                Helper.appendWildcard(ApiConstant.API_MODULE_PREFIX),
                                Helper.appendWildcard(ApiConstant.API_DECENTRALIZATION_MODULE_PREFIX),
                                Helper.appendWildcard(ApiConstant.API_MODULE_ROLE_STAFF_PREFIX),
                                Helper.appendWildcard(ApiConstant.API_DOWNLOAD_FILE_ROLE),
                                Helper.appendWildcard(ApiConstant.API_UPLOAD_FILE_ROLE),
                                Helper.appendWildcard(ApiConstant.API_ROLE_PREFIX)
                        )
                        .hasAnyAuthority(RoleConstants.ACTOR_ADMIN, RoleConstants.ACTOR_TBDT_CS)
        );

        http.authorizeHttpRequests(
                auth -> auth.requestMatchers(
                                ApiConstant.API_STAFF_PREFIX + "/update-staff/{id}",
                                ApiConstant.API_STAFF_PREFIX + "/update-status-staff/{id}",
                                ApiConstant.API_STAFF_PREFIX + "/detail-staff/{id}",
                                ApiConstant.API_STAFF_PREFIX + "/list-department",
                                ApiConstant.API_STAFF_PREFIX + "/list-campus"
                        )
                        .hasAnyAuthority(String.valueOf(RoleConstants.defaultRolesForStaff))
        );

        //disable security for websocket
        http.authorizeHttpRequests(
                auth -> auth.requestMatchers(
                                "/ws-message/**",
                                "/send/**",
                                "/sendMessage/**",
                                "/topic/message/**"
                        )
                        .permitAll()
        );
        http.oauth2Login(oauth2 -> oauth2.authorizationEndpoint(a -> a.baseUri("/oauth2/authorize"))
                .redirectionEndpoint(r -> r.baseUri("/oauth2/callback/**"))
                .userInfoEndpoint(u -> u.userService(customOAuth2UserService))
                .authorizationEndpoint(a -> a.authorizationRequestRepository(cookieAuthorizationRequestRepository()))
                .successHandler(oAuth2AuthenticationSuccessHandler)
                .failureHandler(oAuth2AuthenticationFailureHandler));
        http.addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    private List<String> getUpdatedOrigins() {
        return moduleAuthRepository.getAllUrl().stream()
                .map(this::createUrl)
                .distinct()
                .toList();
    }

    private String createUrl(String module) {
        try {
            URL url = new URL(module);
            return url.getProtocol() + "://" + url.getHost() + (url.getPort() != -1 ? ":" + url.getPort() : "");
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

}
