package com.food_app_api.Viviepi.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class ReviewProductDTO {
  //  private String token;
    private Long id;
    private Long userId;
    private List<ReviewItemDTO> reviewItems;
    private Long foodId;
    private float rating;
    private String comment;
    private LocalDateTime createdDt;


}
