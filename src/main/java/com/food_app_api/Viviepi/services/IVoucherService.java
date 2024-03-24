package com.food_app_api.Viviepi.services;

import com.food_app_api.Viviepi.dto.VoucherDTO;
import com.food_app_api.Viviepi.entities.Voucher;
import com.food_app_api.Viviepi.payload.response.ResponseObject;
import com.food_app_api.Viviepi.payload.response.ResponseOutput;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface IVoucherService {
    ResponseObject getAll();
    List<String> getAllCode();
    int getValueByCode(String code);
    VoucherDTO insert(VoucherDTO voucherDTO, MultipartFile file) throws IOException;
    void deleteVoucher();
    void deleteVoucherByCode(String code);
    Boolean existDiscount(VoucherDTO discountTypeDTO);
    Boolean isExpire(String code);
    Voucher findByCode(String code);

}
