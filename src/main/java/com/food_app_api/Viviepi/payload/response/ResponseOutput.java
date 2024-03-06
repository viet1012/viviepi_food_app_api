package com.food_app_api.Viviepi.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseOutput {
    private int page;
    private int totalItem;
    private int totalPage;
    private Object data;
}
