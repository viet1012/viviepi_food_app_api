package com.food_app_api.Viviepi.dto;

import com.food_app_api.Viviepi.entities.Bill;
import com.food_app_api.Viviepi.entities.BillDetail;
import com.food_app_api.Viviepi.entities.Food;
import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BillDetailDTO {
    private Long id;
    private Long billId;
    private Long foodId;
    private Integer quantity;
    private Double total_price;
    private Bill bill;
    private Food food;
    List<Long> foodIds;
    List<FoodDTO> foodDTOS;
}
