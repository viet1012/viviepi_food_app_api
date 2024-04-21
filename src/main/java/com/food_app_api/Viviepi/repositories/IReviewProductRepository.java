package com.food_app_api.Viviepi.repositories;

import com.food_app_api.Viviepi.entities.ReviewProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IReviewProductRepository extends JpaRepository<ReviewProduct, Long> {
}
