package com.food_app_api.Viviepi.mapper;

import com.food_app_api.Viviepi.dto.FoodDTO;
import com.food_app_api.Viviepi.entities.Category;
import com.food_app_api.Viviepi.entities.Food;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class FoodMapper {

    public FoodDTO toFoodDTO(Food food) {
        return FoodDTO.builder()
                .id(food.getId())
                .name(food.getName())
                .category(food.getCategory().getId()) // Assuming you want to include only the category ID in DTO
                .imgUrl(food.getImgUrl())
                .description(food.getDescription())
                .quantity(food.getQuantity())
                .price(food.getPrice())
                .createdBy(food.getCreatedBy())
                .createdAt(food.getCreatedAt())
                .updatedBy(food.getUpdateBy())
                .updatedAt(food.getUpdateAt())
                .build();
    }

    public List<FoodDTO> toFoodDTOList(List<Food> foodList) {
        return foodList.stream().map(this::toFoodDTO).collect(Collectors.toList());
    }

    public Food toFood(FoodDTO foodDTO, Category category) {
        return Food.builder()
                .id(foodDTO.getId())
                .name(foodDTO.getName())
                .category(category)
                .imgUrl(foodDTO.getImgUrl())
                .description(foodDTO.getDescription())
                .quantity(foodDTO.getQuantity())
                .price(foodDTO.getPrice())
                .build();
    }

    public Food toFood(FoodDTO foodDTO, Food food, Category category) {
        food.setId(foodDTO.getId());
        food.setName(foodDTO.getName());
        food.setCategory(category);
        food.setImgUrl(foodDTO.getImgUrl());
        food.setDescription(foodDTO.getDescription());
        food.setQuantity(foodDTO.getQuantity());
        food.setPrice(foodDTO.getPrice());
        return food;
    }

    public List<Food> toFoodList(List<FoodDTO> foodDTOList, Category category) {
        return foodDTOList.stream().map(foodDTO -> toFood(foodDTO, category)).collect(Collectors.toList());
    }
}
