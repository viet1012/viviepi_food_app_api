package com.food_app_api.Viviepi.services;

import com.food_app_api.Viviepi.dto.BillDTO;
import com.food_app_api.Viviepi.entities.Bill;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IBillService {
    BillDTO createBill(BillDTO billDTO, String code, String token);

    List<BillDTO> getAllBills();
    Page<BillDTO> getAllBills(Pageable pageable);

    BillDTO getBillDTOById(long id);
    Bill getBillById(long id);
    List<BillDTO> getBillByUserId(long userID);
    BillDTO updateBill(long id, BillDTO billDTO);

    void deleteBill(long id);
}
