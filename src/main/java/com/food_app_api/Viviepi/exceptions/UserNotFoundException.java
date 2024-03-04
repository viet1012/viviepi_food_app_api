package com.food_app_api.Viviepi.exceptions;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserNotFoundException extends RuntimeException{
    private int statusCode;
    private String message;
}
