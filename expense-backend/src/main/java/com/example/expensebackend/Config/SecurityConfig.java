package com.example.expensebackend.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration // Bao cho Spring biet class nay chua cac bean cau hinh.
public class SecurityConfig {

    @Bean // Tao bean PasswordEncoder de inject vao UserService.
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // BCrypt dung de ma hoa password an toan hon plain text.
    }

    @Bean // Tao bean SecurityFilterChain de cau hinh bao mat HTTP.
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable()) // Tat CSRF de API REST/Postman de test hon.
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // Bat CORS theo config ben duoi.
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll()); // Tam thoi cho phep moi request.
        return http.build(); // Build va tra ve security filter chain.
    }

    @Bean // Tao bean cau hinh CORS cho frontend goi API.
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration(); // Object chua cau hinh CORS.
        config.setAllowedOrigins(List.of("*")); // Cho phep moi domain goi API.
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE")); // Cho phep cac method REST can dung.
        config.setAllowedHeaders(List.of("*")); // Cho phep moi header tu client.
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource(); // Gan config theo URL pattern.
        source.registerCorsConfiguration("/**", config); // Ap dung CORS cho tat ca endpoint.
        return source; // Tra source de SecurityFilterChain su dung.
    }
}
