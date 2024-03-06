package com.food_app_api.Viviepi.services;


import com.food_app_api.Viviepi.dto.CategoryDTO;
import com.food_app_api.Viviepi.payload.response.ResponseOutput;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ICategoryService {
    List<CategoryDTO> getAllCategory();
    ResponseOutput getAllCategory(int page, int limit, String sortBy, String sortField);
    List<CategoryDTO> getAllCategoryCode(CategoryDTO categoryDTO);
    List<CategoryDTO> searchAllByName(String name);
    List<String> getAllNameByCategory();
    CategoryDTO upsert(CategoryDTO categoryDTO);
    void deleteCategory(CategoryDTO categoryDTO);
    Boolean exitsCategory(CategoryDTO categoryDTO);
    String uploadCategoryImages(MultipartFile fileName);
    String uploadLocalCategoryImages(MultipartFile fileName);
    byte[] readImageUrl(String fileName);
}
