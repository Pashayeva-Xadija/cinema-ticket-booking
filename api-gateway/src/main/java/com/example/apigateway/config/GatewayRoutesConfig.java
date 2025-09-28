package com.example.apigateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayRoutesConfig {

    @Bean
    public RouteLocator routes(RouteLocatorBuilder b) {
        return b.routes()


                .route("auth-docs", r -> r
                        .order(-1)
                        .path(
                                "/api/auth/swagger-ui/**",
                                "/api/auth/swagger-ui.html",
                                "/api/auth/v3/api-docs",
                                "/api/auth/v3/api-docs/**"
                        )
                        .filters(f -> f.stripPrefix(2))   
                        .uri("https://energetic-celebration-production.up.railway.app")
                )


                .route("auth-api", r -> r
                        .path("/api/auth/**")
                        .filters(f -> f.stripPrefix(2).prefixPath("/auth"))
                        .uri("https://energetic-celebration-production.up.railway.app")
                )

                .build();
    }
}
