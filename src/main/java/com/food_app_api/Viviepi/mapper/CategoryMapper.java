package com.food_app_api.Viviepi.mapper;


import com.food_app_api.Viviepi.dto.CategoryDTO;
import com.food_app_api.Viviepi.entities.Category;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CategoryMapper {
    public CategoryDTO toCategoryDTO(Category category){
        return CategoryDTO.builder()
                .id(category.getId())
                .createdBy(category.getCreatedBy() != null ? category.getCreatedBy() : "DefaultCreatedBy")
                .createdAt(category.getCreatedAt())
                .updatedBy(category.getUpdateBy() != null ? category.getUpdateBy() : "DefaultUpdateBy")
                .updatedAt(category.getUpdateAt())
                .categoryCode(category.getCategoryCode() != null ? category.getCategoryCode() : "DefaultCategoryCode")
                .name(category.getName() != null ? category.getName() : "DefaultName")
                .bannerUrl(category.getBannerUrl() != null ? category.getBannerUrl() : "DefaultBannerUrl")
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
                .build();
    }

    public Category toCategory(CategoryDTO categoryDTO, Category category){
        category.setName(categoryDTO.getName() != null ? categoryDTO.getName() :  category.getName());
        category.setCategoryCode(categoryDTO.getCategoryCode() != null? categoryDTO.getCategoryCode() : category.getCategoryCode() );
        category.setBannerUrl(categoryDTO.getBannerUrl() != null ? categoryDTO.getBannerUrl() : category.getBannerUrl());
        return category;
    }

    public List<Category> toCategoryList(List<CategoryDTO> categoryDTOList){
        return categoryDTOList.stream().map(this::toCategory).collect(Collectors.toList());
    }
}
