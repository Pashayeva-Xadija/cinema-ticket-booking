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


                .route("auth-swagger", r -> r.path("/api/auth/swagger-ui/**","/api/auth/v3/api-docs/**")
                        .filters(f -> f.stripPrefix(2).addRequestHeader("X-Forwarded-Prefix","/api/auth"))
                        .uri("http://auth-service:8085"))
                .route("auth", r -> r.path("/api/auth/**")
                        .filters(f -> f.stripPrefix(2).prefixPath("/auth"))
                        .uri("http://auth-service:8085"))


                .route("booking-swagger", r -> r.path("/api/booking/swagger-ui/**","/api/booking/v3/api-docs/**")
                        .filters(f -> f.stripPrefix(2).addRequestHeader("X-Forwarded-Prefix","/api/booking"))
                        .uri("http://booking-service:8087"))
                .route("booking", r -> r.path("/api/booking/**")
                        .filters(f -> f.stripPrefix(2).prefixPath("/api/bookings"))
                        .uri("http://booking-service:8087"))


                .route("ticket-swagger", r -> r.path("/api/ticket/swagger-ui/**","/api/ticket/v3/api-docs/**")
                        .filters(f -> f.stripPrefix(2).addRequestHeader("X-Forwarded-Prefix","/api/ticket"))
                        .uri("http://ticket-service:8088"))
                .route("ticket", r -> r.path("/api/ticket/**")
                        .filters(f -> f.stripPrefix(2).prefixPath("/api/tickets"))
                        .uri("http://ticket-service:8088"))


                .route("showtime-swagger", r -> r.path("/api/showtime/swagger-ui/**","/api/showtime/v3/api-docs/**")
                        .filters(f -> f.stripPrefix(2).addRequestHeader("X-Forwarded-Prefix","/api/showtime"))
                        .uri("http://showtime-service:8086"))
                .route("showtime", r -> r.path("/api/showtime/**")
                        .filters(f -> f.stripPrefix(2).prefixPath("/api/showtimes"))
                        .uri("http://showtime-service:8086"))


                .route("payment-swagger", r -> r.path("/api/payment/swagger-ui/**","/api/payment/v3/api-docs/**")
                        .filters(f -> f.stripPrefix(2).addRequestHeader("X-Forwarded-Prefix","/api/payment"))
                        .uri("http://payment-service:8082"))
                .route("payment", r -> r.path("/api/payment/**")
                        .filters(f -> f.stripPrefix(2).prefixPath("/api/payments"))
                        .uri("http://payment-service:8082"))


                .route("inventory-swagger", r -> r.path("/api/inventory/swagger-ui/**","/api/inventory/v3/api-docs/**")
                        .filters(f -> f.stripPrefix(2).addRequestHeader("X-Forwarded-Prefix","/api/inventory"))
                        .uri("http://inventory-service:8081"))
                .route("inventory", r -> r.path("/api/inventory/**")
                        .uri("http://inventory-service:8081"))


                .route("notification-swagger", r -> r.path("/api/notification/swagger-ui/**","/api/notification/v3/api-docs/**")
                        .filters(f -> f.stripPrefix(2).addRequestHeader("X-Forwarded-Prefix","/api/notification"))
                        .uri("http://notification-service:8083"))
                .route("notification", r -> r.path("/api/notification/**")
                        .filters(f -> f.stripPrefix(2).prefixPath("/api/notifications"))
                        .uri("http://notification-service:8083"))

                .build();
    }
}
