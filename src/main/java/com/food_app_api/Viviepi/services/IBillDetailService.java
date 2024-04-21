package com.food_app_api.Viviepi.services;

import com.food_app_api.Viviepi.dto.BillDetailDTO;
import com.food_app_api.Viviepi.entities.BillDetail;

import java.util.List;

public interface IBillDetailService {
    List<BillDetailDTO> getBillDetailsByBillId(Long billId);
     int countTotalBillDetailsByBillId(Long billId);
    List<BillDetailDTO> getAllBillDetail();
    List<BillDetailDTO> createBillDetail(BillDetailDTO newBillDetail, Long billId);
    BillDetail updateBillDetail(Long billDetailId, BillDetail updatedBillDetail);
    void deleteAll();

}
