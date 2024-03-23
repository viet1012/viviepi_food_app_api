package com.food_app_api.Viviepi.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;

import java.util.HashMap;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class CloudinaryConfig {

    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dpxakd3vt",
                "api_key", "493935384733852",
                "api_secret", "lNUwPLXz9vZrVQHuqFCBqzbYglY"));
    }
}
