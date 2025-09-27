package com.example.testt.controller;

import com.example.testt.dto.RegisterRequest;
import com.example.testt.model.User;
import com.example.testt.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {
    private final UserService userService;
@PostMapping("/register")
    public ResponseEntity<User> resgister(@RequestBody RegisterRequest re){
return  ResponseEntity.ok(userService.signup(re));
    }
}
