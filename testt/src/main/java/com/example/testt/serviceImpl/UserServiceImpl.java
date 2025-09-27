package com.example.testt.serviceImpl;

import com.example.testt.dto.LoginRequest;
import com.example.testt.dto.RegisterRequest;
import com.example.testt.model.User;
import com.example.testt.repo.UserRepository;
import com.example.testt.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public User signup(RegisterRequest r) {
        if (userRepository.existsByEmail(r.getEmail())) {
            throw new RuntimeException("email artiq movcuddur");
        }
        User u = new User();
        u.setName(r.getName());
        u.setEmail(r.getEmail());
        u.setPassword(r.getPassword());

        return userRepository.save(u);
    }

    @Override
    public User login(LoginRequest l) {
        return null;
    }
}
