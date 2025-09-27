package com.queue.service;

import com.queue.dto.AuthRequest;
import com.queue.dto.AuthResponse;
import com.queue.dto.RegisterRequest;

public interface AuthService {
    AuthResponse login(AuthRequest req);
    AuthResponse register(RegisterRequest req);
}
