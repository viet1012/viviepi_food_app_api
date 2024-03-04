package com.food_app_api.Viviepi.jwt;

import lombok.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseAuthentication {
    private String accessToken;
    private String refreshToken;
}