package com.food_app_api.Viviepi.exceptions;

public class CategoryNotExistException extends Exception {
    public CategoryNotExistException() {
        super("Not exist category");
    }
}
