package com.food_app_api.Viviepi.dto;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResetPasswordDTO {
    private String token;
    private String email;
    private String newPassword;
}
