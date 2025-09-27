package com.example.authservice.service;

public interface TokenCacheService {
    void saveRefreshToken(String userId, String token);
    String getRefreshToken(String userId);
    void deleteRefreshToken(String userId);
}
