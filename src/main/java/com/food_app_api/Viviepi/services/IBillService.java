package com.food_app_api.Viviepi.services;

import com.food_app_api.Viviepi.dto.BillDTO;

import java.util.List;

public interface IBillService {
    BillDTO createBill(BillDTO billDTO, String code, String token);

    List<BillDTO> getAllBills();

    BillDTO getBillById(long id);

    BillDTO updateBill(long id, BillDTO billDTO);

    void deleteBill(long id);
}
