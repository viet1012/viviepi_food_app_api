package com.food_app_api.Viviepi.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseSuccess {
    private int status;
    private boolean success = true;
    private Object data;
}
