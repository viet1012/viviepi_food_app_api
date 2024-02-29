package com.food_app_api.Viviepi.controllers;

import com.food_app_api.Viviepi.dtos.CustomerDTO;
import com.food_app_api.Viviepi.entities.User;
import com.food_app_api.Viviepi.jwt.AuthResponse;
import com.food_app_api.Viviepi.jwt.JwtTokenProvider;
import com.food_app_api.Viviepi.services.AuthService;
import com.food_app_api.Viviepi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth/")
public class AuthController {
    @Autowired
    private AuthenticationProvider authenticationProvider;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUserWithRole( @RequestParam(name = "role_id", defaultValue = "2") Long roleId , @RequestBody CustomerDTO request) {

        // Tạo một người dùng mới
        User registeredUser = authService.registerUser( roleId ,request);

        UserDetails userDetails = userDetailsService.loadUserByUsername(registeredUser.getEmail());
        String token = jwtTokenProvider.generateToken(userDetails);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setAccessToken(token);
        authResponse.setUser(registeredUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(authResponse);

    }


}
