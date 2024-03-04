package com.food_app_api.Viviepi.services;


import com.food_app_api.Viviepi.dto.UserDTO;
import com.food_app_api.Viviepi.entities.User;
import com.food_app_api.Viviepi.jwt.JwtUtil;
import com.food_app_api.Viviepi.mapper.RoleUsersMapper;
import com.food_app_api.Viviepi.mapper.UserMapper;
import com.food_app_api.Viviepi.repositories.IRoleRepository;
import com.food_app_api.Viviepi.repositories.IRoleUserRepository;
import com.food_app_api.Viviepi.repositories.IUserRepository;
import com.food_app_api.Viviepi.repositories.ITokenRepository;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
@AllArgsConstructor
public class UserServiceImp implements UserService {

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    ITokenRepository tokenRepository;

    @Autowired
    IUserRepository userRepository;

    @Autowired
    IRoleRepository roleRepository;

    @Autowired
    AuthenticationManager authenticationManager;
    
    @Autowired
    IRoleUserRepository roleUserRepository;

    @Autowired
    UserMapper userMapper;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    RoleUsersMapper roleUsersMapper;

    @Autowired
    Gson gson = new Gson();

    @Override
    public boolean checkEmailExists(String email) {
        System.out.println("Email: " + email);
        Optional<User> user = userRepository.findByEmail(email);
        System.out.println("Email Exists " + user.isPresent());

        return user.isPresent();
    }


    @Override
    public UserDTO findUserByEmail(String email){
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            UserDTO userDTO = new UserDTO();
            userDTO = userMapper.userToUserDTO(user.get());
            return userDTO;
        }
        else return null;
    }

    @Override
    public UserDTO addNewUser(long id) {
        return null;
    }


    @Override
    public UserDTO showUserInfo(String email){
        Optional<User>  optionalUser = userRepository.findByEmail(email);
        UserDTO userDTO = userMapper.userToUserDTO(optionalUser.get());
        return userDTO;
    }

    @Override
    public long getIdByEmail(String emai) {
        return 0;
    }


}
