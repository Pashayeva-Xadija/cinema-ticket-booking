package com.queue.serviceImpl;

import com.queue.dto.AuthRequest;
import com.queue.dto.AuthResponse;
import com.queue.dto.RegisterRequest;
import com.queue.enums.Role;
import com.queue.exception.ConflictException;
import com.queue.model.User;
import com.queue.repository.UserRepository;
import com.queue.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements com.queue.service.AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthResponse login(AuthRequest req) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword()));
        var token = jwtService.generateToken(req.getUsername(), Map.of());
        return new AuthResponse(token);
    }

    @Override
    public AuthResponse register(RegisterRequest req) {
        if (userRepository.existsByUsername(req.getUsername())) {
            throw new ConflictException("Username already exists");
        }
        User u = User.builder()
                .username(req.getUsername())
                .password(passwordEncoder.encode(req.getPassword()))
                .role(req.getRole() == null ? Role.OPERATOR : req.getRole())
                .build();
        userRepository.save(u);
        var token = jwtService.generateToken(u.getUsername(), Map.of());
        return new AuthResponse(token);
    }
}
