package com.food_app_api.Viviepi.repositories;

import com.food_app_api.Viviepi.entities.Role;
import com.food_app_api.Viviepi.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    User findByDisplayName(String displayName);
    @Query("SELECT u.role FROM User u WHERE u.role.id = :roleId")
    Role findRoleByRoleId(@Param("roleId") Long roleId);
}

