package com.anshuman.taskmanager.config;

import org.springframework.http.HttpMethod;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class SecurityConfig {

        @Bean
        public BCryptPasswordEncoder passwordEncoder(){

            return new BCryptPasswordEncoder();
        }
        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
            http.csrf(csrf -> csrf.disable());

            http.authorizeHttpRequests(auth -> auth.requestMatchers(HttpMethod.POST,"/auth/register","/auth/login").permitAll());

            return http.build();
        }

}
