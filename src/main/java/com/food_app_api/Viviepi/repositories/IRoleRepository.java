package com.food_app_api.Viviepi.repositories;

import com.food_app_api.Viviepi.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRoleRepository extends JpaRepository<Role, Long> {
    Role findOneByName(String roleName);
}

