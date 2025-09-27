package com.example.authservice.serviceImpl;

import com.example.authservice.dto.*;
import com.example.authservice.enumtype.Role;
import com.example.authservice.exception.ConflictException;
import com.example.authservice.exception.NotFoundException;
import com.example.authservice.exception.UnauthorizedException;
import com.example.authservice.exception.ValidationException;
import com.example.authservice.mapper.UserMapper;
import com.example.authservice.model.RefreshToken;
import com.example.authservice.model.User;
import com.example.authservice.repository.RefreshTokenRepository;
import com.example.authservice.repository.UserRepository;
import com.example.authservice.security.JwtService;
import com.example.authservice.service.AuthService;
import com.example.authservice.service.TokenCacheService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository users;
    private final RefreshTokenRepository refreshTokens;
    private final PasswordEncoder encoder;
    private final JwtService jwt;
    private final UserMapper userMapper;

    private final TokenCacheService tokenCacheService;

    @Override
    @Transactional
    public AuthResponse register(RegisterRequest req) {
        if (users.existsByUsername(req.getUsername())) {
            throw new ConflictException("Username already taken");
        }

        User u = users.save(
                User.builder()
                        .username(req.getUsername())
                        .password(encoder.encode(req.getPassword()))
                        .role(Role.USER)
                        .enabled(true)
                        .email(req.getEmail())
                        .build()
        );

        String access  = jwt.create(u.getUsername(), u.getRole().name());
        String refresh = issueRefresh(u.getUsername());

        AuthResponse resp = new AuthResponse();
        resp.setAccessToken(access);
        resp.setRefreshToken(refresh);
        resp.setMessage("User registered successfully");
        return resp;
    }

    @Override
    @Transactional
    public TokenResponse login(LoginRequest req) {
        User u = users.findByUsername(req.getUsername())
                .filter(x -> encoder.matches(req.getPassword(), x.getPassword()))
                .orElseThrow(() -> new UnauthorizedException("Bad credentials"));

        String access  = jwt.create(u.getUsername(), u.getRole().name());
        String refresh = issueRefresh(u.getUsername());

        TokenResponse resp = new TokenResponse();
        resp.setAccessToken(access);
        resp.setRefreshToken(refresh);
        return resp;
    }

    @Override
    @Transactional
    public TokenResponse refresh(String token) {
        RefreshToken rt = refreshTokens.findByToken(token)
                .filter(t -> t.getExpiresAt().isAfter(Instant.now()))
                .orElseThrow(() -> new ValidationException("Refresh token invalid/expired"));

        String cached = tokenCacheService.getRefreshToken(rt.getUsername());
        if (cached == null || !cached.equals(token)) {
            throw new ValidationException("Refresh token mismatch (Redis)");
        }

        String access     = jwt.create(rt.getUsername(), "USER");
        String newRefresh = issueRefresh(rt.getUsername());

        TokenResponse resp = new TokenResponse();
        resp.setAccessToken(access);
        resp.setRefreshToken(newRefresh);
        return resp;
    }

    @Override
    public UserResponse me(String username) {
        User u = users.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("User not found"));
        return userMapper.toResponse(u);
    }

    private String issueRefresh(String username) {
        refreshTokens.deleteByUsername(username);
        String token = UUID.randomUUID().toString();


        refreshTokens.save(RefreshToken.builder()
                .token(token)
                .username(username)
                .expiresAt(Instant.now().plusSeconds(7 * 24 * 3600))
                .build());


        tokenCacheService.saveRefreshToken(username, token);

        return token;
    }
}
