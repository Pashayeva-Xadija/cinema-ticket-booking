package com.example.authservice.serviceImpl;

import com.example.authservice.service.TokenCacheService;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import java.util.concurrent.TimeUnit;

@Service
public class TokenCacheServiceImpl implements TokenCacheService {

    private final StringRedisTemplate redisTemplate;

    public TokenCacheServiceImpl(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void saveRefreshToken(String userId, String token) {
        redisTemplate.opsForValue().set("refresh:" + userId, token, 1, TimeUnit.DAYS);
    }

    @Override
    public String getRefreshToken(String userId) {
        return redisTemplate.opsForValue().get("refresh:" + userId);
    }

    @Override
    public void deleteRefreshToken(String userId) {
        redisTemplate.delete("refresh:" + userId);
    }
}

