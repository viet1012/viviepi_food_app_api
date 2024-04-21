package com.food_app_api.Viviepi.controllers;

import com.food_app_api.Viviepi.dto.ReviewProductDTO;
import com.food_app_api.Viviepi.entities.ReviewProduct;
import com.food_app_api.Viviepi.services.IReviewProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/review/api")
public class ReviewProductController {

    @Autowired
    private  IReviewProductService reviewProductService;

    @PostMapping("/create")
    public ResponseEntity<ReviewProduct> createReviewProduct(@RequestBody ReviewProductDTO reviewProductDTO) {
        ReviewProduct createdReviewProduct = reviewProductService.createReviewProducts(reviewProductDTO);
        return new ResponseEntity<>(createdReviewProduct, HttpStatus.CREATED);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ReviewProductDTO> getReviewProductById(@PathVariable Long id) {
        ReviewProductDTO reviewProductDTO = reviewProductService.getReviewProductById(id);
        return new ResponseEntity<>(reviewProductDTO, HttpStatus.OK);
    }

    @GetMapping("/get/all")
    public ResponseEntity<List<ReviewProductDTO>> getAllReviewProducts() {
        List<ReviewProductDTO> reviewProductDTOs = reviewProductService.getAllReviewProducts();
        return new ResponseEntity<>(reviewProductDTOs, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ReviewProductDTO> updateReviewProduct(@PathVariable Long id, @RequestBody ReviewProductDTO reviewProductDTO) {
        ReviewProductDTO updatedReviewProduct = reviewProductService.updateReviewProduct(id, reviewProductDTO);
        return new ResponseEntity<>(updatedReviewProduct, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteReviewProduct(@PathVariable Long id) {
        reviewProductService.deleteReviewProduct(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
