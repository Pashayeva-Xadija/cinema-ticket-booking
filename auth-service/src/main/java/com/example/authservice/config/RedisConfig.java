package com.example.authservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

@Configuration
public class RedisConfig {
    @Bean
    LettuceConnectionFactory redisConnectionFactory(@Value("${spring.data.redis.url}") String url) {
        return new LettuceConnectionFactory();
    }
}
