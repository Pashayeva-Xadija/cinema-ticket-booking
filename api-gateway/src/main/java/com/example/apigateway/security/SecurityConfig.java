package com.example.apigateway.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .cors(cors -> {})
                .authorizeExchange(ex -> ex
                        .pathMatchers("/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**").permitAll()
                        .pathMatchers("/api/*/swagger-ui.html", "/api/*/swagger-ui/**", "/api/*/v3/api-docs/**").permitAll()
                        .pathMatchers("/api/auth/**").permitAll()
                        .pathMatchers("/actuator/**").permitAll()
                        // --- Demo üçün public GET-lər:
                        .pathMatchers(org.springframework.http.HttpMethod.GET,
                                "/api/showtime/**",
                                "/api/inventory/**",
                                "/api/booking/**",
                                "/api/payment/**",
                                "/api/ticket/**",
                                "/api/notification/**"
                        ).permitAll()
                        .anyExchange().authenticated()
                )
                .build();
    }
}
