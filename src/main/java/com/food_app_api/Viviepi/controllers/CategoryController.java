package com.food_app_api.Viviepi.controllers;

import com.food_app_api.Viviepi.dto.CategoryDTO;
import com.food_app_api.Viviepi.dto.FoodDTO;
import com.food_app_api.Viviepi.payload.response.ResponseObject;
import com.food_app_api.Viviepi.payload.response.ResponseOutput;
import com.food_app_api.Viviepi.services.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/categories/api")
public class CategoryController {

    private final ICategoryService categoryService;

    @Autowired
    public CategoryController(ICategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/get/all")
    public ResponseEntity<ResponseObject> getAllCategories() {
        List<CategoryDTO> categories = categoryService.getAllCategory();
        ResponseObject responseObject = new ResponseObject(HttpStatus.OK.value(), "Successfully retrieved all categories", categories);
        return new ResponseEntity<>(responseObject, HttpStatus.OK);
    }

    @GetMapping("/paged")
    public ResponseEntity<ResponseOutput> getAllCategoriesPaged(
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "limit", defaultValue = "10") int limit,
            @RequestParam(name = "sortBy", defaultValue = "ASC") String sortBy,
            @RequestParam(name = "sortField", defaultValue = "categoryName") String sortField) {
        ResponseOutput responseOutput = categoryService.getAllCategory(page, limit, sortBy, sortField);
        return ResponseEntity.ok(responseOutput);
    }

    @GetMapping("/search")
    public ResponseEntity<List<CategoryDTO>> searchCategoriesByName(
            @RequestParam("name") String name) {
        List<CategoryDTO> categories = categoryService.searchAllByName(name);
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/names")
    public ResponseEntity<List<String>> getAllCategoryNames() {
        List<String> categoryNames = categoryService.getAllNameByCategory();
        return ResponseEntity.ok(categoryNames);
    }

    @PostMapping("/insert")
    public ResponseEntity<ResponseObject> savedCategory(@RequestParam(value = "file",required = false) MultipartFile file  , @ModelAttribute  CategoryDTO categoryDTO) {
        categoryService.insert(categoryDTO,file);
        ResponseObject responseObject = new ResponseObject(HttpStatus.CREATED.value(), "Category saved successfully", null);
        return new ResponseEntity<>(responseObject, HttpStatus.CREATED);

    }
    @PutMapping("/update")
    public ResponseEntity<ResponseObject> savedCategory(@RequestBody CategoryDTO categoryDTO, @PathVariable long id) {
        categoryService.update(categoryDTO,id);
        ResponseObject responseObject = new ResponseObject(HttpStatus.CREATED.value(), "Category saved successfully", null);
        return new ResponseEntity<>(responseObject, HttpStatus.CREATED);

    }
    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteCategory(@RequestParam("id") Long categoryId) {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(categoryId);
        categoryService.deleteCategory(categoryDTO);
        return ResponseEntity.noContent().build();
    }



    @PostMapping("/upload-local")
    public ResponseEntity<String> uploadLocalCategoryImages(@RequestParam("file") MultipartFile file) {
        String fileName = categoryService.uploadLocalCategoryImages(file);
        return ResponseEntity.ok(fileName);
    }

    @GetMapping("/image/{fileName}")
    public ResponseEntity<byte[]> readImageUrl(@PathVariable String fileName) {
        byte[] imageContent = categoryService.readImageUrl(fileName);
        return ResponseEntity.ok(imageContent);
    }
}
