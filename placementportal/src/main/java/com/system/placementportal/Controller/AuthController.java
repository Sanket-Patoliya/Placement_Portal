package com.system.placementportal.Controller;

import com.system.placementportal.Dto.LoginRequestDto;
import com.system.placementportal.Dto.RegisterRequestDto;
import com.system.placementportal.Service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    // 🔹 REGISTER
    @PostMapping("/register")
    public String register(@RequestBody @Valid RegisterRequestDto request) {
        return authService.register(request);
    }

    // 🔹 LOGIN
    @PostMapping("/login")
    public Map<String, String> login(@RequestBody @Valid LoginRequestDto request) {
        return authService.login(request);
    }
}