package com.food_app_api.Viviepi.repositories;

import com.food_app_api.Viviepi.entities.Bill;
import com.food_app_api.Viviepi.entities.BillDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IBillDetailRepository  extends JpaRepository<BillDetail,Long> {
    List<BillDetail> findByBill(Bill bill);
}
