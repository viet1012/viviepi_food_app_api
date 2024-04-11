package com.food_app_api.Viviepi.redis;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserSession implements Serializable {
    private String userId;
    private String token;
    private String email;

}
