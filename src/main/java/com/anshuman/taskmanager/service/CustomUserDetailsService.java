package com.anshuman.taskmanager.service;

import com.anshuman.taskmanager.repository.UserRepository;
import com.anshuman.taskmanager.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username){
    Optional<User> existingUser  = userRepository.findByEmail(username);
    if (!existingUser.isPresent()) {
        throw new UsernameNotFoundException("User not found");
    }
    User user = existingUser.get();

    return org.springframework.security.core.userdetails.User.withUsername(user.getEmail()).password(user.getPassword()).authorities(user.getRole()).build();
    }
}
