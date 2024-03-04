package com.food_app_api.Viviepi.exceptions;

public class NoCategoryFoundException extends Exception{
    public NoCategoryFoundException(int id) {
        super("No category found with id " + id);
    }
}
