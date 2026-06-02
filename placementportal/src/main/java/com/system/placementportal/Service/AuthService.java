package com.system.placementportal.Service;

import com.system.placementportal.Dto.LoginRequestDto;
import com.system.placementportal.Dto.RegisterRequestDto;
import com.system.placementportal.Entity.*;
import com.system.placementportal.Exception.DuplicateResourceException;
import com.system.placementportal.Exception.ResourceNotFoundException;
import com.system.placementportal.Repository.StudentRepository;
import com.system.placementportal.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    // 🔹 REGISTER
    public String register(RegisterRequestDto request) {
        log.info("Registering new user with email: {}", request.getEmail());

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            log.warn("Registration failed: Email {} already exists", request.getEmail());
            throw new DuplicateResourceException("Email already exists");
        }

        // 🔹 Create User (default STUDENT)
        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.STUDENT)
                .build();

        userRepository.save(user);

        // 🔹 Create Student Profile
        Student student = Student.builder()
                .name(request.getName())
                .phone(request.getPhone())
                .user(user)
                .build();

        studentRepository.save(student);
        log.info("User registered successfully: {}", request.getEmail());

        return "User registered successfully";
    }

    // 🔹 LOGIN
    public Map<String, String> login(LoginRequestDto request) {
        log.info("Login attempt for email: {}", request.getEmail());

        // Authenticate using AuthenticationManager
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        // Generate JWT
        String token = jwtService.generateToken(user.getEmail());
        log.info("Login successful for email: {}", request.getEmail());

        return Map.of("token", token);
    }
}