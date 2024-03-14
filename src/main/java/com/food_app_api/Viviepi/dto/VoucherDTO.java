package com.food_app_api.Viviepi.dto;

import com.google.api.client.util.DateTime;
import jakarta.validation.constraints.Min;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VoucherDTO {
    private long id;
    private String code;
    @Min(value = 10000, message = "Value must be greater than 10000")
    private long value;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private int status;

}
