package com.food_app_api.Viviepi.payload.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class SignUpRequest {
    @NotBlank(message = "Full name is required")
    private String fullName;

    @NotBlank(message = "Email is required")
    @Email(message = "Email is not valid", regexp = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$")
    private String email;

    @NotBlank(message = "Password is required")
//    @Pattern(message = "Password is not valid",regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{12,}$")
    private String password;

    private String roleName;

    private String tokenDevice;
}
