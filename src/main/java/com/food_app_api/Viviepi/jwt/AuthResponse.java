package com.food_app_api.Viviepi.jwt;

import com.food_app_api.Viviepi.entities.User;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AuthResponse {
    private User user;
    private String accessToken;
    private String token;
    public AuthResponse(){

    }


}
