package com.food_app_api.Viviepi.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewItemDTO {
    private Long foodId;
    private float rating;
    private String comment;

}
