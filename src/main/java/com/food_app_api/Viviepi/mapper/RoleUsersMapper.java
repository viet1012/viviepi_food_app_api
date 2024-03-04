package com.food_app_api.Viviepi.mapper;

import com.food_app_api.Viviepi.dto.RolesUsersDTO;
import com.food_app_api.Viviepi.entities.Role;
import com.food_app_api.Viviepi.entities.RolesUsers;
import com.food_app_api.Viviepi.entities.User;
import com.food_app_api.Viviepi.entities.keys.RolesUsersKeys;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RoleUsersMapper {
    public RolesUsersDTO toRoleUserDTO(User user, Role role){
        return RolesUsersDTO.builder()
                .idRole(role.getId())
                .idUser(user.getId())
                .roleName(role.getName())
                .build();
    }

    public RolesUsersDTO toRoleUserDTO(RolesUsers rolesUsersEntity){
        return RolesUsersDTO.builder()
                .idUser(rolesUsersEntity.getRoleUserKeys().getIdUser())
                .idRole(rolesUsersEntity.getRoleUserKeys().getIdRole())
                .roleName(rolesUsersEntity.getIdRole().getName())
                .build();
    }

    public RolesUsersKeys toRoleUserKeys(RolesUsersDTO rolesUsersDTO){
        return RolesUsersKeys.builder()
                .idRole(rolesUsersDTO.getIdRole())
                .idUser(rolesUsersDTO.getIdUser())
                .build();
    }

    public RolesUsers toRoleUserEntity(RolesUsersDTO rolesUsersDTO){
        RolesUsers rolesUsersEntity = new RolesUsers();
        rolesUsersEntity.setRoleUserKeys(toRoleUserKeys(rolesUsersDTO));
        return rolesUsersEntity;
    }

    public List<RolesUsersDTO> toRoleUserDTOList(List<RolesUsers> rolesUsersEntityList){
        return rolesUsersEntityList.stream().map(this::toRoleUserDTO).collect(Collectors.toList());
    }
}
