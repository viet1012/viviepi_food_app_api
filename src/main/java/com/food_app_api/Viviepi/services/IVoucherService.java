package com.food_app_api.Viviepi.services;

import com.food_app_api.Viviepi.dto.VoucherDTO;
import com.food_app_api.Viviepi.entities.Voucher;
import com.food_app_api.Viviepi.payload.response.ResponseObject;
import com.food_app_api.Viviepi.payload.response.ResponseOutput;

import java.util.List;
import java.util.Optional;

public interface IVoucherService {
    ResponseObject getAll();
    List<String> getAllCode();
    int getValueByCode(String code);
    VoucherDTO insert(VoucherDTO voucherDTO);
    void deleteVoucher();
    void deleteVoucherByCode(String code);
    Boolean existDiscount(VoucherDTO discountTypeDTO);
    Boolean isExpire(String code);
    Voucher findByCode(String code);

}
