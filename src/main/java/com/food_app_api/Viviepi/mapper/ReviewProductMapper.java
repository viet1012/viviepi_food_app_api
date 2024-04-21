package com.food_app_api.Viviepi.mapper;

import com.food_app_api.Viviepi.dto.ReviewProductDTO;
import com.food_app_api.Viviepi.dto.ReviewItemDTO;
import com.food_app_api.Viviepi.entities.ReviewProduct;
import com.food_app_api.Viviepi.entities.User;
import com.food_app_api.Viviepi.entities.Food;
import com.food_app_api.Viviepi.repositories.IFoodRepository;
import com.food_app_api.Viviepi.repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ReviewProductMapper {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IFoodRepository foodRepository;

    public ReviewProductDTO toDTO(ReviewProduct reviewProduct) {
        ReviewProductDTO dto = new ReviewProductDTO();
        dto.setId(reviewProduct.getId());
        dto.setUserId(reviewProduct.getUser().getId());
        dto.setCreatedDt(reviewProduct.getCreatedDt());
        dto.setFoodId(reviewProduct.getFood().getId());
        dto.setComment(reviewProduct.getComment());
        dto.setRating(reviewProduct.getRating());
        return dto;
    }

    public ReviewProduct toEntity(ReviewProductDTO dto) {
        ReviewProduct reviewProduct = new ReviewProduct();

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + dto.getUserId()));
        reviewProduct.setUser(user);

        reviewProduct.setCreatedDt(dto.getCreatedDt());

        // Map review items
        List<ReviewItemDTO> reviewItemDTOs = dto.getReviewItems();
        for (ReviewItemDTO reviewItemDTO : reviewItemDTOs) {
            Optional<Food> food = foodRepository.findById(reviewItemDTO.getFoodId());
            if(food.isPresent())
            {
                reviewProduct.setFood(food.get());
                reviewProduct.setRating(reviewItemDTO.getRating());
                reviewProduct.setComment(reviewItemDTO.getComment());
            } else {
                throw new RuntimeException("Food not found with id: " + reviewItemDTO.getFoodId());
            }

        }

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
