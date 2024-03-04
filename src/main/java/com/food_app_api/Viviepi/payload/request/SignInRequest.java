package com.food_app_api.Viviepi.payload.request;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignInRequest {
    @NotBlank(message = "Email is required")
    @Email(message = "Email is not valid", regexp = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$")
    private String email;

    @NotBlank(message = "Password is required")
    private String password;

}
