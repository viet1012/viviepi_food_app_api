package com.food_app_api.Viviepi.repositories;

import com.food_app_api.Viviepi.entities.Voucher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IVoucherRepository extends JpaRepository<Voucher,Long> {
}
