package com.food_app_api.Viviepi.repositories;

import com.food_app_api.Viviepi.entities.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IFoodRepository extends JpaRepository<Food, Long> {
    List<Food> findByCategoryId(Long categoryId);

    List<Food> findFoodByName(String name);
    @Query("SELECT p FROM food p ORDER BY  p.price ASC ")
    List<Food> findAllFoodByPriceAsc();
    @Query("SELECT p from food p ORDER BY p.price DESC ")
    List<Food> findAllFoodByPriceDesc();
    @Query("select p from food p where  p.category.id = :categoryId")
    List<Food> findFoodsByCategoryId(@Param("categoryId") long categoryId);
}
