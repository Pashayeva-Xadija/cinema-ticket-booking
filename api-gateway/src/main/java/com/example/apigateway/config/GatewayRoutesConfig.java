package com.example.apigateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class GatewayRoutesConfig {

    @Bean
    public RouteLocator routes(RouteLocatorBuilder b) {
        String AUTH = "https://SENIN-AUTH-DOMENIN.up.railway.app";

        return b.routes()

                .route("auth-docs", r -> r.path(
                                "/api/auth/v3/api-docs",
                                "/api/auth/swagger-ui/**",
                                "/api/auth/swagger-ui.html")
                        .filters(f -> f.stripPrefix(2)) 
                        .uri(AUTH))


                .route("auth-api", r -> r.path("/api/auth/**")
                        .filters(f -> f.stripPrefix(2).prefixPath("/auth"))
                        .uri(AUTH))

                .build();
    }
}
