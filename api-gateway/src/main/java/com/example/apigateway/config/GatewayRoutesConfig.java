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


                .route("auth", r -> r.path("/api/auth/**")
                        .filters(f -> f.stripPrefix(2).prefixPath("/auth"))
                        .uri("https://AUTH-APP-DOMENIN.railway.app"))

                .build();
    }
}
