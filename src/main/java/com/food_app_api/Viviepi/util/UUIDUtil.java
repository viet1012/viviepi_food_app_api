package com.food_app_api.Viviepi.util;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UUIDUtil {
    public String generateUUID(){
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }
}
