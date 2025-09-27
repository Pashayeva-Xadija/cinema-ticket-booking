package com.example.authservice.service;

import com.example.authservice.dto.*;

public interface AuthService {
    AuthResponse register(RegisterRequest req);
    TokenResponse login(LoginRequest req);
    TokenResponse refresh(String refreshToken);
    UserResponse me(String username);
}
