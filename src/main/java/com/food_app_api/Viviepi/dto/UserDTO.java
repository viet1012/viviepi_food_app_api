package com.food_app_api.Viviepi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserDTO {
    private long id;
    private String email;
    private String password;
    private String avtUrl;
    private String fullName;
    private String phone;
    private String verifyCode;
    private String verifyCodeExpired;
    boolean isActive;


}
