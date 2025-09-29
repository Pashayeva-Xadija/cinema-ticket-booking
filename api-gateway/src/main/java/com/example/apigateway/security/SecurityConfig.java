package com.example.apigateway.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .cors(c -> {})
                .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
                .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
                .authorizeExchange(ex -> ex

                        .pathMatchers("/", "/index.html", "/app.js", "/styles.css", "/favicon.ico").permitAll()
                        .pathMatchers("/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**").permitAll()
                        .pathMatchers(
                                "/api/auth/swagger-ui/**", "/api/auth/v3/api-docs/**",
                                "/api/booking/swagger-ui/**", "/api/booking/v3/api-docs/**",
                                "/api/inventory/swagger-ui/**", "/api/inventory/v3/api-docs/**",
                                "/api/ticket/swagger-ui/**", "/api/ticket/v3/api-docs/**",
                                "/api/payment/swagger-ui/**", "/api/payment/v3/api-docs/**",
                                "/api/showtime/swagger-ui/**", "/api/showtime/v3/api-docs/**",
                                "/api/notification/swagger-ui/**", "/api/notification/v3/api-docs/**"
                        ).permitAll()
                        .pathMatchers("/api/auth/**").permitAll()
                        .pathMatchers("/actuator/**").permitAll()
                        .anyExchange().authenticated()
                )
                .build();
    }
}
