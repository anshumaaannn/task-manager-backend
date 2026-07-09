package com.anshuman.taskmanager.service;

import com.anshuman.taskmanager.Util.JwtUtil;
import com.anshuman.taskmanager.dto.AuthResponse;
import com.anshuman.taskmanager.dto.LoginRequest;
import com.anshuman.taskmanager.dto.RegisterRequest;
import com.anshuman.taskmanager.dto.UserResponse;
import com.anshuman.taskmanager.entity.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.anshuman.taskmanager.repository.UserRepository;

import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder , JwtUtil jwtUtil) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public UserResponse register(RegisterRequest request) {
        Optional<User> existingUser = userRepository.findByEmail(request.getEmail());

        if (existingUser.isPresent()) {
            throw new RuntimeException("Email already in use");
        }


        String hashedPassword = passwordEncoder.encode(request.getPassword());
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(hashedPassword);
        user.setRole("USER");
        User savedUser = userRepository.save(user);

        UserResponse response = new UserResponse();
        response.setId(savedUser.getId());
        response.setEmail(savedUser.getEmail());
        response.setRole(savedUser.getRole());
        response.setName(savedUser.getName());

        return response;
    }
    public AuthResponse login(LoginRequest request) {
        Optional<User> existingUser = userRepository.findByEmail(request.getEmail());
        if (!existingUser.isPresent()) {
            throw new UsernameNotFoundException("User  not found");
        }
        boolean matches = passwordEncoder.matches(request.getPassword(), existingUser.get().getPassword());
        if(matches == false) {
            throw new RuntimeException("Wrong email or password");
        }
        String token = jwtUtil.generateToken(request.getEmail());

        UserResponse response = new UserResponse();
        response.setId(existingUser.get().getId());
        response.setEmail(existingUser.get().getEmail());
        response.setRole(existingUser.get().getRole());
        response.setName(existingUser.get().getName());

        return new AuthResponse(token);
    }


}

