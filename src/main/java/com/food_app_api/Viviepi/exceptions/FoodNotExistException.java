package com.food_app_api.Viviepi.exceptions;

public class FoodNotExistException extends Exception {
    public FoodNotExistException(){
        super("Not exist Food!");
    }
}
