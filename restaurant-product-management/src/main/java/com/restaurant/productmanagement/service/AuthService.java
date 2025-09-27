package com.restaurant.productmanagement.service;


import com.restaurant.productmanagement.dto.AuthResponseDto;
import com.restaurant.productmanagement.dto.LoginDto;
import com.restaurant.productmanagement.dto.RegisterDto;

public interface AuthService {
    AuthResponseDto signup(RegisterDto dto);

    AuthResponseDto login(LoginDto dto);
}