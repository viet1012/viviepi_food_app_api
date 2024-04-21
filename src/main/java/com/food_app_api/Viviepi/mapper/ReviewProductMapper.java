package com.food_app_api.Viviepi.mapper;

import com.food_app_api.Viviepi.dto.ReviewProductDTO;
import com.food_app_api.Viviepi.entities.ReviewProduct;
import com.food_app_api.Viviepi.entities.User;
import com.food_app_api.Viviepi.entities.Food;
import com.food_app_api.Viviepi.repositories.IFoodRepository;
import com.food_app_api.Viviepi.repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ReviewProductMapper {

    @Autowired
    private  IUserRepository userRepository;
    @Autowired
    private  IFoodRepository foodRepository;

    public ReviewProductDTO toDTO(ReviewProduct reviewProduct) {
        ReviewProductDTO dto = new ReviewProductDTO();
        dto.setId(reviewProduct.getId());
        dto.setUserId(reviewProduct.getUser().getId());
        dto.setFoodId(reviewProduct.getFood().getId());
        dto.setRating(reviewProduct.getRating());
        dto.setComment(reviewProduct.getComment());
        dto.setCreatedDt(reviewProduct.getCreatedDt());
        return dto;
    }


    public ReviewProduct toEntity(ReviewProductDTO dto) {
        ReviewProduct reviewProduct = new ReviewProduct();

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + dto.getUserId()));
        reviewProduct.setUser(user);

        Food food = foodRepository.findById(dto.getFoodId())
                .orElseThrow(() -> new RuntimeException("Food not found with id: " + dto.getFoodId()));
        reviewProduct.setFood(food);

        reviewProduct.setRating(dto.getRating());
        reviewProduct.setComment(dto.getComment());

        return reviewProduct;
    }

    public List<ReviewProductDTO> toListDTO(List<ReviewProduct> reviewProducts) {
        return reviewProducts.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<ReviewProduct> toListEntity(List<ReviewProductDTO> dtos) {
        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
}
