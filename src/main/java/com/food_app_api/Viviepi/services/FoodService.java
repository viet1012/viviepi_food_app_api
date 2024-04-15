package com.food_app_api.Viviepi.services;

import com.food_app_api.Viviepi.dto.FoodDTO;
import com.food_app_api.Viviepi.entities.Category;
import com.food_app_api.Viviepi.entities.Food;
import com.food_app_api.Viviepi.mapper.FoodMapper;
import com.food_app_api.Viviepi.repositories.ICategoryRepository;
import com.food_app_api.Viviepi.repositories.IFoodRepository;
import com.food_app_api.Viviepi.util.CloudinaryUtil;
import com.food_app_api.Viviepi.util.UploadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class FoodService implements IFoodService {

    @Autowired
    private IFoodRepository foodRepository;

    @Autowired
    private ICategoryRepository categoryRepository;

    @Autowired
    private FoodMapper foodMapper;

    @Autowired
    private UploadLocalUtil uploadLocalUtil;

    @Autowired
    private CloudinaryUtil cloudinaryUtil;

    @Override
    public List<FoodDTO> getFoodsByCategoryId(Long categoryId) {
        List<Food> foods = foodRepository.findByCategoryId(categoryId);
        return foodMapper.toFoodDTOList(foods);
    }


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
    public void saveFood(FoodDTO foodDTO, MultipartFile file) throws IOException {
        String fileName = cloudinaryUtil.uploadImageToFolder(file, "food");

        Category category = getCategoryById(foodDTO.getCategoryId());
        System.out.println("Category: " + category.getName());
        Food food = foodMapper.toFood(foodDTO, category);
        food.setImgUrl(fileName);
        foodRepository.save(food);
    }

    @Override
    public void updateFood(Long id, FoodDTO foodDTO) {
        Category category = getCategoryById(foodDTO.getCategoryId()); // You need to implement this method to get the Category entity
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

    @Override
    public byte[] readImageUrl(String fileName) {
        String folderName = "food";
        return uploadLocalUtil.readFileContent(fileName, folderName);
    }

    private Category getCategoryById(Long categoryId) {
        return categoryRepository.findOneById(categoryId);
    }
}
