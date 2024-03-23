package com.food_app_api.Viviepi.services;

import com.food_app_api.Viviepi.dto.FoodDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IFoodService {
    List<FoodDTO> getFoodsByCategoryId(Long categoryId);

    List<FoodDTO> getAllFood();

    FoodDTO getFoodById(Long id);

    List<FoodDTO> getFoodByName(String name);

    void saveFood(FoodDTO foodDTO, MultipartFile file) throws IOException;

    void updateFood(Long id, FoodDTO foodDTO);

    void deleteFood(Long id);
    byte[] readImageUrl(String fileName);

}
