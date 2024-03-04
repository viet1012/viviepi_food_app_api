package com.food_app_api.Viviepi.payload.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseError {
    private int status;
    private boolean success = false;
    private Object message;
}
