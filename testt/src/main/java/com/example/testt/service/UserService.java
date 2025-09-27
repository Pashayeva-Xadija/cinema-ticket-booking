package com.example.testt.service;

import com.example.testt.dto.LoginRequest;
import com.example.testt.dto.RegisterRequest;
import com.example.testt.model.User;

public interface UserService {
User signup(RegisterRequest r);
User login(LoginRequest l);
}
