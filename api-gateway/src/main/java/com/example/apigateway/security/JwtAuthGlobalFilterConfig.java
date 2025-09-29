package com.example.apigateway.security;

import io.jsonwebtoken.Jwts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

@Configuration
public class JwtAuthGlobalFilterConfig {

    private static final Logger log = LoggerFactory.getLogger(JwtAuthGlobalFilterConfig.class);

    @Bean
    public GlobalFilter jwtAuthGlobalFilter(@Value("${jwt.secret}") String secret) {
        final byte[] key = secret.getBytes(StandardCharsets.UTF_8);

        return (exchange, chain) -> {
            HttpMethod method = exchange.getRequest().getMethod();


            if (HttpMethod.OPTIONS.equals(method)) {
                return chain.filter(exchange);
            }

            String path = exchange.getRequest().getPath().value();
            if (isPublicPath(path)) {
                return chain.filter(exchange);
            }

            String auth = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
            if (auth == null || !auth.startsWith("Bearer ")) {
                return unauthorized(exchange);
            }

            try {
                String token = auth.substring(7);
                Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
                return chain.filter(exchange);
            } catch (Exception e) {
                log.warn("JWT validation failed: {}", e.getMessage());
                return unauthorized(exchange);
            }
        };
    }

    private boolean isPublicPath(String path) {
        return path.startsWith("/actuator")
                || path.contains("/swagger-ui")
                || path.contains("/v3/api-docs")
                || path.startsWith("/api/auth/")
                || path.equals("/")
                || path.equals("/index.html")
                || path.startsWith("/app.js")
                || path.startsWith("/styles.css")
                || path.equals("/favicon.ico");
    }

    private Mono<Void> unauthorized(ServerWebExchange ex) {
        ex.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        ex.getResponse().getHeaders().add(HttpHeaders.WWW_AUTHENTICATE, "Bearer realm=\"api\"");
        return ex.getResponse().setComplete();
    }
}
