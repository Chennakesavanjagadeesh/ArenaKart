package com.example.arenakart.Controllers;

import com.example.arenakart.DTO.*;
import com.example.arenakart.Services.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    

    public AuthController(AuthService authService) {
		super();
		this.authService = authService;
	}

	@PostMapping("/register")
    public ResponseEntity<ApiResponse<JwtAuthResponse>> register(
        @Valid @RequestBody UserRegistrationDto registrationDto
    ) {
        JwtAuthResponse response = authService.register(registrationDto);
        return ResponseEntity.ok(ApiResponse.success("User registered successfully", response));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<JwtAuthResponse>> login(@Valid @RequestBody LoginDto loginDto) {
        JwtAuthResponse response = authService.login(loginDto);
        return ResponseEntity.ok(ApiResponse.success("Login successful", response));
    }
}
