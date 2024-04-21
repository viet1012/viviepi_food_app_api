package com.food_app_api.Viviepi.repositories;

import com.food_app_api.Viviepi.entities.ReviewProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IReviewProductRepository extends JpaRepository<ReviewProduct, Long> {
   List<ReviewProduct> findByUserId(Long userId);
}
