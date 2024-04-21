package com.food_app_api.Viviepi.services;

import com.food_app_api.Viviepi.dto.ReviewProductDTO;

import java.util.List;


import java.util.List;

public interface IReviewProductService {
    ReviewProductDTO createReviewProduct(ReviewProductDTO reviewProductDTO);
    ReviewProductDTO getReviewProductById(Long id);
    List<ReviewProductDTO> getAllReviewProducts();
    ReviewProductDTO updateReviewProduct(Long id, ReviewProductDTO reviewProductDTO);
    void deleteReviewProduct(Long id);
}

