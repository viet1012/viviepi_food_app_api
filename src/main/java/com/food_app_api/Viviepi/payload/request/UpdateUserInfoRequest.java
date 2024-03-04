package com.food_app_api.Viviepi.payload.request;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UpdateUserInfoRequest {
    @NotBlank(message = "Username must not be blank")
    private String fullName;
    @NotBlank(message = "Phonenumber must not be blank")
    private String phoneNumber;
}
