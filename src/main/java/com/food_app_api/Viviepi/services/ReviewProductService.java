package com.food_app_api.Viviepi.services;

import com.food_app_api.Viviepi.dto.ReviewProductDTO;
import com.food_app_api.Viviepi.entities.ReviewProduct;
import com.food_app_api.Viviepi.mapper.ReviewProductMapper;
import com.food_app_api.Viviepi.repositories.IReviewProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ReviewProductService implements IReviewProductService {
    @Autowired
    private  IReviewProductRepository reviewProductRepository;
    @Autowired
    private  ReviewProductMapper reviewProductMapper;


    @Override
    public ReviewProductDTO createReviewProduct(ReviewProductDTO reviewProductDTO) {
        ReviewProduct reviewProduct = reviewProductMapper.toEntity(reviewProductDTO);
        reviewProduct = reviewProductRepository.save(reviewProduct);
        return reviewProductMapper.toDTO(reviewProduct);
    }

    @Override
    public ReviewProductDTO getReviewProductById(Long id) {
        ReviewProduct reviewProduct = reviewProductRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ReviewProduct not found with id: " + id));
        return reviewProductMapper.toDTO(reviewProduct);
    }

    @Override
    public List<ReviewProductDTO> getAllReviewProducts() {
        List<ReviewProduct> reviewProducts = reviewProductRepository.findAll();
        return reviewProducts.stream()
                .map(reviewProductMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ReviewProductDTO updateReviewProduct(Long id, ReviewProductDTO reviewProductDTO) {
        ReviewProduct existingReviewProduct = reviewProductRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ReviewProduct not found with id: " + id));

        // Update existingReviewProduct entity with data from reviewProductDTO
        // For brevity, I'm assuming your mapper also has a method to update existing entity

        existingReviewProduct = reviewProductRepository.save(existingReviewProduct);
        return reviewProductMapper.toDTO(existingReviewProduct);
    }

    @Override
    public void deleteReviewProduct(Long id) {
        reviewProductRepository.deleteById(id);
    }
}
