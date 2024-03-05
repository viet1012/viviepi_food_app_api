package com.food_app_api.Viviepi.util;

import org.springframework.stereotype.Component;

@Component
public class StringsUtil {
    public String getUserNameFormDomain(String domain){
        if (domain.contains("@")){
            int index = domain.indexOf("@");
            return domain.substring(0, index);
        }
        return domain;
    }
}
