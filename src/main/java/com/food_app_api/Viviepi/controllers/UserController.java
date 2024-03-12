package com.food_app_api.Viviepi.controllers;

import com.food_app_api.Viviepi.payload.response.ResponseObject;
import com.food_app_api.Viviepi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user/api")
public class UserController {
    @Autowired
    UserService userService;
    @GetMapping("/get/all")
    @Transactional(readOnly = true)
    public ResponseEntity<ResponseObject>getAllUser(){
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(200,
                        "get all user completed !",
                        userService.getAll()
                )
        );
    }
}
