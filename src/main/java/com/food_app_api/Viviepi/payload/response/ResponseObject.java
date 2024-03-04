package com.food_app_api.Viviepi.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseObject {
    private int statusCode;
    private String message;
    private Object data;
}
