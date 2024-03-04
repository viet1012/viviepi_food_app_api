package com.food_app_api.Viviepi.dto;

import com.food_app_api.Viviepi.entities.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserSignUpDTO {
    private String fullName;
    private String email;
    private String password;
    private String roleName;
    private boolean isActive;


    @Override
    public String toString() {
        return "User{" +
                "displayName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", role=" + roleName  +
                ", password=" + password +
                ", active=" + isActive +
                '}';
    }
}
