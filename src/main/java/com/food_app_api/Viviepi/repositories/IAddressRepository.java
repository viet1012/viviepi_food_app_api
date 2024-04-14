package com.food_app_api.Viviepi.repositories;

import com.food_app_api.Viviepi.entities.Address;
import com.food_app_api.Viviepi.entities.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IAddressRepository extends JpaRepository<Address, Long> {
    List<Address> findByUserId(Long userId);
}
