package com.food_app_api.Viviepi.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserDTO {
    private long id;
    private String email;
    private String password;
    private String avtUrl;
    private String fullName;
    private String phoneNumber;
    private String verifyCode;
    private String verifyCodeExpired;
    boolean isActive;


}
