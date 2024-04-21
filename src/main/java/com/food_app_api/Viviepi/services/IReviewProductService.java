package com.food_app_api.Viviepi.services;

import com.food_app_api.Viviepi.dto.ReviewProductDTO;
import com.food_app_api.Viviepi.entities.ReviewProduct;

import java.util.List;


import java.util.List;

public interface IReviewProductService {
    ReviewProduct createReviewProducts(ReviewProductDTO reviewProductDTOs);
    ReviewProductDTO getReviewProductById(Long id);
    List<ReviewProductDTO> getAllReviewProducts();
    ReviewProductDTO updateReviewProduct(Long id, ReviewProductDTO reviewProductDTO);
    void deleteReviewProduct(Long id);
}

