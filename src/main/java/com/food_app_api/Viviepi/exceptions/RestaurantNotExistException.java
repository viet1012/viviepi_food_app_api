package com.food_app_api.Viviepi.exceptions;

public class RestaurantNotExistException extends Exception {
    public RestaurantNotExistException() {
        super("Not exist restaurants");
    }
}
