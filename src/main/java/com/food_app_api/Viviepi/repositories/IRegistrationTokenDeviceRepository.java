package com.food_app_api.Viviepi.repositories;
import com.food_app_api.Viviepi.entities.RegistrationTokenDevice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRegistrationTokenDeviceRepository extends JpaRepository<RegistrationTokenDevice, Long> {
}

