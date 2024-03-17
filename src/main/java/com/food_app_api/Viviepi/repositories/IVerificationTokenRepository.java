package com.food_app_api.Viviepi.repositories;

import com.food_app_api.Viviepi.entities.token.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IVerificationTokenRepository extends JpaRepository<VerificationToken, Long> {
    VerificationToken findByOtp(String token);
}
