package com.food_app_api.Viviepi.services;

import com.food_app_api.Viviepi.dto.BillDetailDTO;
import com.food_app_api.Viviepi.entities.BillDetail;

import java.util.List;

public interface IBillDetailService {
    List<BillDetailDTO> getBillDetailsByBillId(Long billId);
    List<BillDetailDTO> getAllBillDetail();
    BillDetailDTO createBillDetail(BillDetailDTO newBillDetail, Long billId, Long productId);
    BillDetail updateBillDetail(Long billDetailId, BillDetail updatedBillDetail);
}
