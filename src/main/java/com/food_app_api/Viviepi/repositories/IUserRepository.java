package com.food_app_api.Viviepi.repositories;

import com.food_app_api.Viviepi.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserId(String userID);
    Optional<User> findByEmail(String email);
    Optional<User> findByFullname(String username);

}

