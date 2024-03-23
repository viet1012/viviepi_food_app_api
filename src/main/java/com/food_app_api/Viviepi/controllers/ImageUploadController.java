package com.food_app_api.Viviepi.controllers;

import com.cloudinary.Cloudinary;
import com.food_app_api.Viviepi.util.CloudinaryUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/test/cloud")
public class ImageUploadController {

    @Autowired
    private CloudinaryUtil cloudinaryUtil;



    @PostMapping("/upload")
    public ResponseEntity<String> uploadImageToFolder(@RequestParam("file") MultipartFile file) {
        try {
            String imageUrl = cloudinaryUtil.uploadImageToFolder(file, "folderName");
            return ResponseEntity.ok().body(imageUrl);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload image: " + e.getMessage());
        }
    }
}