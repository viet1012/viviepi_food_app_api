package com.food_app_api.Viviepi.dto.token;

import jakarta.validation.constraints.NotBlank;
import lombok.*;



@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RefreshTokenDTO {
    @NotBlank
    private String accessToken;
}
