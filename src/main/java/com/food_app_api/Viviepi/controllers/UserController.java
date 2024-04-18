package com.food_app_api.Viviepi.controllers;

import com.food_app_api.Viviepi.dto.UserDTO;
import com.food_app_api.Viviepi.payload.response.ResponseObject;
import com.food_app_api.Viviepi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

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
    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseObject> updateUserByUserId(@RequestBody UserDTO userDTO, @PathVariable Long id)
    {
        userService.updateUser(id,userDTO);
        ResponseObject responseObject = new ResponseObject(HttpStatus.OK.value(), "User updated successfully", null);
        return new ResponseEntity<>(responseObject, HttpStatus.OK);
    }
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseObject> deleteUserByUserId(@RequestBody UserDTO userDTO)
    {
        userService.deleteUserByUserId(userDTO.getUserID());
        ResponseObject responseObject = new ResponseObject(HttpStatus.OK.value(), "User deleted successfully", null);
        return new ResponseEntity<>(responseObject, HttpStatus.OK);
    }
}
