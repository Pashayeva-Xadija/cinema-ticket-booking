package com.example.authservice.controller;

import com.example.authservice.dto.*;
import com.example.authservice.model.User;
import com.example.authservice.repository.UserRepository;
import com.example.authservice.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

    @RestController
    @RequestMapping("/auth")

    public class AuthController {
        private final AuthService authService;
        private final UserRepository users;
        public AuthController(AuthService authService, UserRepository users) {
            this.authService = authService;
            this.users = users;
        }
        @PostMapping("/register")
        public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest req) {
            return ResponseEntity.ok(authService.register(req));
        }

        @PostMapping("/login")
        public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest req) {
            return ResponseEntity.ok(authService.login(req));
        }

        @PostMapping("/refresh")
        public ResponseEntity<TokenResponse> refresh(@RequestParam String token) {
            return ResponseEntity.ok(authService.refresh(token));
        }

        @GetMapping("/me")
        public ResponseEntity<UserResponse> me(@RequestParam String username) {
            return ResponseEntity.ok(authService.me(username));
        }

        @GetMapping("/users/{id}/email")
        public ResponseEntity<UserEmailResponse> userEmail(@PathVariable Long id) {
            User u = users.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found"));
            var resp = new UserEmailResponse();
            resp.setId(u.getId());
            resp.setEmail(u.getEmail());
            return ResponseEntity.ok(resp);
        }
    }
