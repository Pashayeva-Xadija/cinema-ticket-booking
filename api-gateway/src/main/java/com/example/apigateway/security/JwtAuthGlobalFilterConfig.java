package com.example.apigateway.security;

import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

@Configuration
public class JwtAuthGlobalFilterConfig {

    @Bean
    public GlobalFilter jwtAuthGlobalFilter(@Value("${jwt.secret}") String secret) {
        final byte[] key = secret.getBytes(StandardCharsets.UTF_8);

        return (exchange, chain) -> {
            String path = exchange.getRequest().getPath().value();

            // 1) Swagger UI və v3 api-docs üçün JWT tələb etmə
            if (isSwagger(path)) {
                return chain.filter(exchange);
            }

            // 2) Auth servisin bütün endpointləri üçün JWT tələb etmə
            if (path.startsWith("/api/auth/")) {
                return chain.filter(exchange);
            }

            // 3) Qalan bütün çağırışlar üçün JWT yoxlaması
            String auth = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
            if (auth == null || !auth.startsWith("Bearer ")) {
                return unauthorized(exchange);
            }

            try {
                String token = auth.substring(7);
                Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
                return chain.filter(exchange);
            } catch (Exception e) {
                org.slf4j.LoggerFactory.getLogger(getClass())
                        .warn("JWT validation failed: {}", e.getMessage());
                return unauthorized(exchange);
            }
        };
    }

    // Swagger üçün prefiksə baxmadan burax (gateway arxasında da işləyir)
    private boolean isSwagger(String path) {
        return path.contains("/swagger-ui") || path.contains("/v3/api-docs");
    }

    private Mono<Void> unauthorized(ServerWebExchange ex) {
        ex.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        return ex.getResponse().setComplete();
    }
}
