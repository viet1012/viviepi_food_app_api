package com.food_app_api.Viviepi.dto;

import com.food_app_api.Viviepi.entities.Role;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserSignUpDTO {
    private String userId;
    private String fullName;
    @Pattern(regexp = "^[a-zA-Z0-9.!#$%&’*+/=?^_`{|}~-]+@[a-zA-Z0-9.-]+$", message = "Please enter the correct email format!" )
    @NotNull(message = "Please fill all information!")
    private String email;
    @NotNull(message = "Please fill all information!")
    private String password;
    private String roleName;
    private boolean isActive;
    private String token;

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
