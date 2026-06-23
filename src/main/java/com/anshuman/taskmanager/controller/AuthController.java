package com.anshuman.taskmanager.controller;

import com.anshuman.taskmanager.dto.LoginRequest;
import com.anshuman.taskmanager.dto.RegisterRequest;
import com.anshuman.taskmanager.dto.UserResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.anshuman.taskmanager.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    public AuthController(AuthService authService) {
        this.authService = authService;
    }
    @PostMapping("/register")
    public UserResponse register(@RequestBody RegisterRequest request){

        return authService.register(request);
    }
    @PostMapping("/login")
    public UserResponse login(@RequestBody LoginRequest request){
        return authService.login(request);
    }
}
