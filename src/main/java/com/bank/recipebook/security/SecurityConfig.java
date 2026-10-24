package com.bank.recipebook.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http
    ) throws Exception {

        http
                .csrf(csrf -> csrf
                        .csrfTokenRequestHandler(
                                new CsrfTokenRequestAttributeHandler()
                        )
                        .ignoringRequestMatchers("/api/users")
                )

                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.IF_REQUIRED
                        )
                )

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/users"
                        ).permitAll()
                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/csrf"
                        ).permitAll()
                        .requestMatchers(
                                HttpMethod.GET, "/api/**"
                        ).hasAnyRole("ADMIN", "USER")
                        .requestMatchers(
                                HttpMethod.POST, "/api/**"
                        ).hasRole("ADMIN")
                        .requestMatchers(
                                HttpMethod.PUT, "/api/**"
                        ).hasRole("ADMIN")
                        .requestMatchers(
                                HttpMethod.PATCH, "/api/**"
                        ).hasRole("ADMIN")
                        .requestMatchers(
                                HttpMethod.DELETE, "/api/**"
                        ).hasRole("ADMIN")
                        .anyRequest().denyAll()
                )

                .httpBasic(httpBasic -> {
                });

        return http.build();
    }
}