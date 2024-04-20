package com.food_app_api.Viviepi.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AddressDTO {
    private Long id;
    private String fullname;
    private Long phoneNumber;
    private Long userId;
    private String ward;
    private String district;
    private String houseNumber;
    private String note;
    private boolean active;
}
