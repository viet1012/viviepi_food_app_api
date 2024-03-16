package com.food_app_api.Viviepi.mapper;

import com.food_app_api.Viviepi.dto.BillDetailDTO;
import com.food_app_api.Viviepi.entities.BillDetail;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BillDetailMapper {
    public BillDetailDTO toDTO(BillDetail billDetail) {
        return BillDetailDTO.builder()
                .id(billDetail.getId())
                .billId(billDetail.getBill().getId())
                .foodId(billDetail.getFood() != null ? billDetail.getFood().getId() : null)
                .quantity(billDetail.getQuantity())
                .build();
    }
    public BillDetail toBill(BillDetailDTO billDetailDTO) {
        BillDetail billDetail = new BillDetail();
        billDetail.setId(billDetailDTO.getId());
        billDetail.setBill(billDetailDTO.getBill());
        billDetail.setFood(billDetailDTO.getFood());
        billDetail.setQuantity(billDetailDTO.getQuantity());
        billDetail.setPrice(billDetailDTO.getPrice());
        return billDetail;
    }

    public List<BillDetailDTO> toBillDetailDTOList(List<BillDetail> billDetailList) {
        return billDetailList.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<BillDetail> toBillDetailList(List<BillDetailDTO> billDetailDTOList) {
        return billDetailDTOList.stream()
                .map(this::toBill)
                .collect(Collectors.toList());
    }
}
