package com.food_app_api.Viviepi.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/food/api")
public class FoodController {
    @GetMapping("/test")
    public ResponseEntity<?> test(){
        System.out.println("Viet viet viet");
        return ResponseEntity.ok("ADMIN ADMIN ");
    }
}
