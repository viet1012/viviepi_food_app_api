package com.food_app_api.Viviepi.services;

import com.food_app_api.Viviepi.dto.FoodDTO;
import com.food_app_api.Viviepi.entities.Category;
import com.food_app_api.Viviepi.entities.Food;
import com.food_app_api.Viviepi.mapper.FoodMapper;
import com.food_app_api.Viviepi.repositories.IFoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FoodService implements IFoodService {

    @Autowired
    private IFoodRepository foodRepository;

    @Autowired
    private FoodMapper foodMapper;

    @Override
    public List<FoodDTO> getAllFood() {
        List<Food> foods = foodRepository.findAll();
        return foodMapper.toFoodDTOList(foods);
    }

    @Override
    public FoodDTO getFoodById(Long id) {
        Food food = foodRepository.findById(id).orElse(null);
        return (food != null) ? foodMapper.toFoodDTO(food) : null;
    }

    @Override
    public List<FoodDTO> getFoodByName(String name) {
        List<Food> foods = foodRepository.findFoodByName(name);
        return foodMapper.toFoodDTOList(foods);
    }

    @Override
    public void saveFood(FoodDTO foodDTO) {
        Category category = getCategoryById(foodDTO.getCategory()); // You need to implement this method to get the Category entity
        Food food = foodMapper.toFood(foodDTO, category);
        foodRepository.save(food);
    }

    @Override
    public void updateFood(Long id, FoodDTO foodDTO) {
        Category category = getCategoryById(foodDTO.getCategory()); // You need to implement this method to get the Category entity
        Food existingFood = foodRepository.findById(id).orElse(null);

        if (existingFood != null) {
            foodMapper.toFood(foodDTO, existingFood, category);
            foodRepository.save(existingFood);
        }
    }

    @Override
    public void deleteFood(Long id) {
        foodRepository.deleteById(id);
    }

    // Implement this method to get Category entity by ID
    private Category getCategoryById(Long categoryId) {

        return null;
    }
}
