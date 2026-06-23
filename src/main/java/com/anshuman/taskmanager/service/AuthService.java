package com.anshuman.taskmanager.service;

import com.anshuman.taskmanager.dto.LoginRequest;
import com.anshuman.taskmanager.dto.RegisterRequest;
import com.anshuman.taskmanager.dto.UserResponse;
import com.anshuman.taskmanager.entity.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.anshuman.taskmanager.repository.UserRepository;

import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
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
    public UserResponse login(LoginRequest request) {
        Optional<User> existingUser = userRepository.findByEmail(request.getEmail());
        if (!existingUser.isPresent()) {
            throw new RuntimeException("Wrong email or password");
        }
        boolean matches = passwordEncoder.matches(request.getPassword(), existingUser.get().getPassword());
        System.out.println("Email entered: " + request.getEmail());

        System.out.println("Password entered: " + request.getPassword());

        System.out.println("Stored hash: " + existingUser.get().getPassword());

        System.out.println("Matches: " + matches);
        if(matches == false) {
            throw new RuntimeException("Wrong email or password");
        }

        UserResponse response = new UserResponse();
        response.setId(existingUser.get().getId());
        response.setEmail(existingUser.get().getEmail());
        response.setRole(existingUser.get().getRole());
        response.setName(existingUser.get().getName());

        return response;
    }
}
