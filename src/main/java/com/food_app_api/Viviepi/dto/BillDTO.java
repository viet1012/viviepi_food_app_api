package com.food_app_api.Viviepi.dto;


import com.food_app_api.Viviepi.entities.User;
import com.food_app_api.Viviepi.entities.Voucher;
import jakarta.validation.constraints.Min;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BillDTO {
    private long id;
    private User user;
    private long userId;
    private String username;
    private LocalDateTime date;
    private String name;
    private long phone;
    private String deliveryAddress;
    private String description;
    private int status;
    private Voucher voucher;
    private String nameVoucher;
    @Min(value = 10000, message = "Total price must be greater than 10000")
    private double totalPrice;
    private String paymentMethod;


}
