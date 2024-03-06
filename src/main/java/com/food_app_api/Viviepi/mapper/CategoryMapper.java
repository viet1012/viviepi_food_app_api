package com.food_app_api.Viviepi.mapper;


import com.food_app_api.Viviepi.dto.CategoryDTO;
import com.food_app_api.Viviepi.entities.Category;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CategoryMapper {
    public CategoryDTO toCategoryDTO(Category Category){
        return CategoryDTO.builder()
                .id(Category.getId())
                .createdBy(Category.getCreatedBy())
                .createdAt(Category.getCreatedAt())
                .updatedBy(Category.getUpdateBy())
                .updatedAt(Category.getUpdateAt())
                .categoryCode(Category.getCategoryCode())
                .name(Category.getName())
                .bannerUrl(Category.getBannerUrl())
                .status(Category.getStatus())
                .build();
    }

    public List<CategoryDTO> toCategoryDTOList(List<Category> CategoryList){
        return CategoryList.stream().map(this::toCategoryDTO).collect(Collectors.toList());
    }

    public Category toCategory(CategoryDTO categoryDTO){
        return Category.builder()
                .id(categoryDTO.getId())
                .categoryCode(categoryDTO.getCategoryCode())
                .name(categoryDTO.getName())
                .bannerUrl(categoryDTO.getBannerUrl())
                .status(categoryDTO.getStatus())
                .build();
    }

    public Category toCategory(CategoryDTO categoryDTO, Category Category){
        Category.setId(categoryDTO.getId());
        Category.setName(categoryDTO.getName());
        Category.setStatus(categoryDTO.getStatus());
        Category.setBannerUrl(categoryDTO.getBannerUrl());
        return Category;
    }

    public List<Category> toCategoryList(List<CategoryDTO> categoryDTOList){
        return categoryDTOList.stream().map(this::toCategory).collect(Collectors.toList());
    }
}
