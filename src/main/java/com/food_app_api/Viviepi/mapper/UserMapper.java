package com.food_app_api.Viviepi.mapper;


import com.food_app_api.Viviepi.dto.UserDTO;
import com.food_app_api.Viviepi.dto.UserSignUpDTO;
import com.food_app_api.Viviepi.entities.Role;
import com.food_app_api.Viviepi.entities.User;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {
    public UserDTO userToUserDTO(User user){
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail(user.getEmail());
        userDTO.setUserID(user.getUserId());
        userDTO.setPassword(user.getPassword());
        userDTO.setFullName(user.getFullname());
        userDTO.setPhone(user.getPhoneNumber());
        userDTO.setVerifyCode(user.getVerifyCode());
        userDTO.setVerifyCodeExpired(user.getVerifyCodeExpired());
        userDTO.setActive(user.getIsActive());
        return userDTO;
    }

    public UserSignUpDTO userSignUpToUserSignUpDTO(User user, Role role){
        UserSignUpDTO userSignUpDTO = new UserSignUpDTO();
        userSignUpDTO.setUserId(user.getUserId());
        userSignUpDTO.setFullName(user.getFullname());
        userSignUpDTO.setEmail(user.getEmail());
        userSignUpDTO.setPassword(user.getPassword());
        userSignUpDTO.setActive(true);
       // role default : "USER"
        userSignUpDTO.setRoleName(role.getName());
        return userSignUpDTO;
    }


    public List<UserDTO> toUserDTOList(List<User> userEntityList){
        return userEntityList.stream().map(this::toUserDTO).collect(Collectors.toList());
    }

    public UserDTO toUserDTO(User userEntity) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(userEntity.getId());
        userDTO.setAvtUrl(userEntity.getAvtUrl());
        userDTO.setFullName(userEntity.getFullname());
        userDTO.setEmail(userEntity.getEmail());
        userDTO.setUserID(userEntity.getUserId());
        return userDTO;
    }
}
