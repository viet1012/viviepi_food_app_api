package com.food_app_api.Viviepi.mapper;

import com.food_app_api.Viviepi.dto.CategoryDTO;
import com.food_app_api.Viviepi.dto.VoucherDTO;
import com.food_app_api.Viviepi.entities.Category;
import com.food_app_api.Viviepi.entities.Voucher;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class VoucherMapper {

    public Voucher updateVoucher(int statusTicket, Voucher oldTicket) {
        if(!Objects.equals(statusTicket, oldTicket.getStatus())) {
            oldTicket.setStatus(statusTicket);
        }
        return oldTicket;
    }



    public Voucher toVoucher(VoucherDTO voucherDTO){
        return Voucher.builder()
                .id(voucherDTO.getId())
                .code(voucherDTO.getCode())
                .value(voucherDTO.getValue())
                .start_date(voucherDTO.getStartDate())
                .end_date(voucherDTO.getEndDate())
                .status(voucherDTO.getStatus())
                .bannerUrl(voucherDTO.getBannerUrl())
                .build();
    }

    public List<VoucherDTO> toVoucherDTOList(List<Voucher> voucherList){
        return voucherList.stream().map(this::toVoucherDTO).collect(Collectors.toList());
    }
    public VoucherDTO toVoucherDTO(Voucher voucher){
        VoucherDTO voucherDTO = new VoucherDTO();
        voucherDTO.setId(voucher.getId());
        voucherDTO.setCode(voucher.getCode());
        voucherDTO.setValue(voucher.getValue());
        voucherDTO.setStartDate(voucher.getStart_date());
        voucherDTO.setEndDate(voucher.getEnd_date());
        voucherDTO.setStatus(voucher.getStatus() == 1 ?  voucher.getStatus() : 0);
        voucherDTO.setBannerUrl(voucher.getBannerUrl() != null ? voucherDTO.getBannerUrl() : null);
        return voucherDTO;
    }
}
