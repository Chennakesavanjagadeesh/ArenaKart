package com.example.arenakart.Controllers;

import com.example.arenakart.Entities.User;
import com.example.arenakart.Services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CurrentUser {
    private final UserService userService;
    

    public CurrentUser(UserService userService) {
		super();
		this.userService = userService;
	}

	public Long getUserId(UserDetails userDetails) {
        User user = userService.getUserByEmail(userDetails.getUsername());
        return user.getId();
    }

    public User getUser(UserDetails userDetails) {
        return userService.getUserByEmail(userDetails.getUsername());
    }
}