package com.food_app_api.Viviepi.dto;

import com.google.api.client.util.DateTime;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.sql.Timestamp;


@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO{
    private Long id;
    private String createdBy;
    private DateTime createdAt;
    private String updatedBy;
    private DateTime updatedAt;
    private String categoryCode;
    private String name;
    private int status;
    private String bannerUrl;
}
