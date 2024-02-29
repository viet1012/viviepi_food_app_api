package com.food_app_api.Viviepi.services;

import com.food_app_api.Viviepi.dtos.UserDTO;
import com.food_app_api.Viviepi.entities.User;
import com.food_app_api.Viviepi.jwt.JwtTokenProvider;
import com.food_app_api.Viviepi.repositories.RoleRepository;
import com.food_app_api.Viviepi.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;


    public User getUserFromEmail(String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if(optionalUser.isPresent())
        {
            return optionalUser.get();
        }
        else {
            return null;
        }
    }


}
