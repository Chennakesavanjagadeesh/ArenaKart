package com.example.arenakart.Services;

import com.example.arenakart.Entities.User;
import com.example.arenakart.DTO.JwtAuthResponse;
import com.example.arenakart.DTO.LoginDto;
import com.example.arenakart.DTO.UserRegistrationDto;
import com.example.arenakart.Security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
@Service
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtTokenProvider tokenProvider;
    

    public AuthService(AuthenticationManager authenticationManager, UserService userService,
			JwtTokenProvider tokenProvider) {
		super();
		this.authenticationManager = authenticationManager;
		this.userService = userService;
		this.tokenProvider = tokenProvider;
	}

	public JwtAuthResponse login(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginDto.getEmail(),
                loginDto.getPassword()
            )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = tokenProvider.generateToken(authentication);

        User user = userService.getUserByEmail(loginDto.getEmail());

        return new JwtAuthResponse(
            token,
            user.getId(),
            user.getEmail(),
            user.getRole().name()
        );
    }

    public JwtAuthResponse register(UserRegistrationDto registrationDto) {
        User user = userService.registerUser(registrationDto);

        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                registrationDto.getEmail(),
                registrationDto.getPassword()
            )
        );

        String token = tokenProvider.generateToken(authentication);

        return new JwtAuthResponse(
            token,
            user.getId(),
            user.getEmail(),
            user.getRole().name()
        );
    }
}