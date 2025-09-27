package com.example.authservice.dto;

import com.example.authservice.enumtype.Role;
import lombok.Data;

@Data
public class RegisterRequest{
private String username;
    private String email;
   private String password;

}
