package com.food_app_api.Viviepi.repositories;

import com.food_app_api.Viviepi.entities.RolesUsers;
import com.food_app_api.Viviepi.entities.keys.RolesUsersKeys;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IRoleUserRepository extends JpaRepository<RolesUsers, RolesUsersKeys> {
    @Query("SELECT ru FROM roles_users ru" +
            " JOIN User u ON ru.roleUserKeys.idUser = u.id" +
            " JOIN role r ON  ru.roleUserKeys.idRole = r.id" +
            " WHERE u.email = :email")
    RolesUsers getEmail(String email);

    @Query("SELECT ru FROM roles_users ru" +
            " JOIN User u ON ru.roleUserKeys.idUser = u.id" +
            " join role r ON ru.roleUserKeys.idRole = r.id" +
            " WHERE r.id = :idRole AND u.id = :idUser")
    RolesUsers findByIdRoleAndIdUser(long idRole, long idUser);

    @Query("SELECT ru FROM roles_users ru " +
            "JOIN User u ON ru.roleUserKeys.idUser = u.id " +
            "WHERE u.id = :idUser")
    RolesUsers findByIdUser (long idUser);

    void deleteByRoleUserKeys(RolesUsersKeys keys);

    @Query("SELECT ru FROM roles_users ru" +
            " JOIN User u ON ru.roleUserKeys.idUser = u.id" +
            " join role r ON ru.roleUserKeys.idRole = r.id" +
            " WHERE u.email=:email")
    RolesUsers findOneByEmail(String email);
}
