package com.food_app_api.Viviepi.repositories;

import com.food_app_api.Viviepi.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByCategoryCode(String categoryCode);
    List<Category> searchAllByNameContainingIgnoreCase(String name);
    @Query("SELECT c.name FROM category c")
    List<String> getAllByCategoryName();
    Category findOneByCategoryCode(String categoryCode);
    Category findOneByName(String name);
    Category findOneById(Long id);
    Boolean existsByCategoryCode(String categoryCode);
    Boolean existsByName(String name);
}
