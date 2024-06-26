package com.portalevent.infrastructure.security;

import com.portalevent.infrastructure.apiconstant.ActorConstants;
import com.portalevent.infrastructure.constant.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author thangncph26123
 */

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfiguration {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        JwtTokenFilter jwtTokenFilter = new JwtTokenFilter(jwtTokenProvider);
        http
                .cors()
                .and()
                .csrf()
                .disable()
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .requestMatchers("/roles").permitAll()
                .requestMatchers(Constants.UrlPath.URL_API_HISTORY + "/**").permitAll()
                .requestMatchers(Constants.UrlPath.URL_API_PARTICIPANT + "/**").permitAll()
                .requestMatchers(Constants.UrlPath.URL_API_APPROVER_MANAGEMENT + "/**").hasAnyAuthority(ActorConstants.ACTOR_CNBM, ActorConstants.ACTOR_TM)
                .requestMatchers(Constants.UrlPath.URL_API_ORGANIZER_MANAGEMENT + "/**").hasAnyAuthority(ActorConstants.ACTOR_GV, ActorConstants.ACTOR_TM, ActorConstants.ACTOR_CNBM)
                .requestMatchers(Constants.UrlPath.URL_API_ADMIN_MANAGEMENT + "/**").hasAnyAuthority(ActorConstants.ACTOR_BDT_CS)
                .requestMatchers(Constants.UrlPath.URL_API_ADMIN_H_MANAGEMENT + "/**").hasAuthority(ActorConstants.ACTOR_TBDT_CS)
                .requestMatchers(Constants.UrlPath.URL_API_ADMIN_HO_MANAGEMENT + "/**").hasAuthority(ActorConstants.ACTOR_BDT_HO);
        // .requestMatchers(Constants.UrlPath.URL_API_ADMINISTRATIVE + "/**").hasAuthority(ActorConstants.ACTOR_ADMINISTRATIVE)
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}