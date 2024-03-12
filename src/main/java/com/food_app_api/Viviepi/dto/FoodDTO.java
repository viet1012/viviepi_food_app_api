package com.food_app_api.Viviepi.dto;

import com.google.api.client.util.DateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Builder
@Getter
@Setter
public class FoodDTO {

    private Long id;
    private String name;
    private Long category;
    private String categoryName; // Thêm trường này để chứa tên của danh mục
    private String imgUrl;
    private String description;
    private int quantity;
    private float price;
    private String createdBy;
    private DateTime createdAt;
    private String updatedBy;
    private DateTime updatedAt;

}
