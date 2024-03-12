package com.food_app_api.Viviepi.repositories;

import com.food_app_api.Viviepi.entities.Voucher;
import com.google.api.client.util.DateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
@Repository
public interface IVoucherRepository extends JpaRepository<Voucher, Long> {

    Optional<Voucher> findByCode(String code);

    @Query("SELECT vc.value FROM Voucher vc WHERE vc.code = :code")
    Float getValueByCode(@Param("code") String code);

    @Query("SELECT vc.code FROM Voucher vc")
    List<String> getAllCode();

    Boolean existsByCode(String code);

    @Query("SELECT v.end_date FROM Voucher v WHERE v.code = :code")
    Optional<LocalDateTime> findEndDateByCode(@Param("code") String code);

    void deleteByCode(String code);
}

