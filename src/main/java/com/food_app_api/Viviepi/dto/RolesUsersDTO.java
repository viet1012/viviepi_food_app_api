package com.food_app_api.Viviepi.dto;

import com.food_app_api.Viviepi.entities.keys.RolesUsersKeys;
import lombok.*;

import java.sql.Timestamp;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RolesUsersDTO {
    private RolesUsersKeys keys;
    private Long idUser;
    private Long idRole;
    private String memberCode;
    private String roleName;
    private String createdBy;
    private String updatedBy;
    private Timestamp createdAt;
    private Timestamp updatedAt;

}
