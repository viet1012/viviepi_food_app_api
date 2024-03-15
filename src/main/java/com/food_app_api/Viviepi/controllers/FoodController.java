package com.food_app_api.Viviepi.controllers;

import com.food_app_api.Viviepi.dto.FoodDTO;
import com.food_app_api.Viviepi.payload.response.ResponseObject;
import com.food_app_api.Viviepi.services.IFoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/food/api")
public class FoodController {

    @Autowired
    private IFoodService foodService;
    @GetMapping("/get/category/{categoryId}")
    public ResponseEntity<ResponseObject> getFoodsByCategoryId(@PathVariable Long categoryId) {
        List<FoodDTO> foods = foodService.getFoodsByCategoryId(categoryId);
        ResponseObject responseObject = new ResponseObject(HttpStatus.OK.value(), "All foods by category-id: "+ categoryId, foods);

        return new ResponseEntity<>(responseObject, HttpStatus.OK);
    }

    @GetMapping("/get/all")
    public ResponseEntity<ResponseObject> getAllFood() {
        List<FoodDTO> foods = foodService.getAllFood();
        ResponseObject responseObject = new ResponseObject(HttpStatus.OK.value(), "Successfully retrieved all foods", foods);
        return new ResponseEntity<>(responseObject, HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ResponseObject> getFoodById(@PathVariable Long id) {
        FoodDTO food = foodService.getFoodById(id);
        if (food != null) {
            ResponseObject responseObject = new ResponseObject(HttpStatus.OK.value(), "Successfully retrieved food by ID", food);
            return new ResponseEntity<>(responseObject, HttpStatus.OK);
        } else {
            ResponseObject responseObject = new ResponseObject(HttpStatus.NOT_FOUND.value(), "Food not found", null);
            return new ResponseEntity<>(responseObject, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<ResponseObject> getFoodByName(@RequestParam String name) {
        List<FoodDTO> foods = foodService.getFoodByName(name);
        ResponseObject responseObject = new ResponseObject(HttpStatus.OK.value(), "Successfully retrieved foods by name", foods);
        return new ResponseEntity<>(responseObject, HttpStatus.OK);
    }

    @PostMapping("/insert")
    public ResponseEntity<ResponseObject> saveFood(@RequestParam("file") MultipartFile file , @ModelAttribute FoodDTO foodDTO) {
        foodService.saveFood(foodDTO,file);
        ResponseObject responseObject = new ResponseObject(HttpStatus.CREATED.value(), "Food saved successfully", null);
        return new ResponseEntity<>(responseObject, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseObject> updateFood(@PathVariable Long id, @RequestBody FoodDTO foodDTO) {
        foodService.updateFood(id, foodDTO);
        ResponseObject responseObject = new ResponseObject(HttpStatus.OK.value(), "Food updated successfully", null);
        return new ResponseEntity<>(responseObject, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseObject> deleteFood(@PathVariable Long id) {
        foodService.deleteFood(id);
        ResponseObject responseObject = new ResponseObject(HttpStatus.OK.value(), "Food deleted successfully", null);
        return new ResponseEntity<>(responseObject, HttpStatus.OK);
    }
}
