package com.food_app_api.Viviepi.notify;

import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
public class NotificationMessage {
    private List<String> registrationTokens;
    private String title;
    private String body;
    private String image;
    private Map<String,String> data;
    private Date sendTime;
}
