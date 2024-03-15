package com.food_app_api.Viviepi.services;

import com.food_app_api.Viviepi.dto.BillDTO;
import com.food_app_api.Viviepi.entities.Bill;

import java.util.List;

public interface IBillService {
    BillDTO createBill(BillDTO billDTO, String code, String token);

    List<BillDTO> getAllBills();

    BillDTO getBillDTOById(long id);
    Bill getBillById(long id);

    BillDTO updateBill(long id, BillDTO billDTO);

    void deleteBill(long id);
}
