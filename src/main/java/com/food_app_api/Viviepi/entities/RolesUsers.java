package com.food_app_api.Viviepi.entities;

import com.food_app_api.Viviepi.entities.keys.RolesUsersKeys;
import jakarta.persistence.*;
import lombok.*;


@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "roles_users")
public class RolesUsers {

    // Đặt PRIMARY KEY cho id_role và id_user
    @EmbeddedId
    private RolesUsersKeys roleUserKeys;
    // ----

    @ManyToOne
    @JoinColumn(name = "role_id",insertable = false, updatable = false)
    @NonNull                     // tránh update value tới những table được references tới
    private Role idRole;

    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    @NonNull
    private User idUser;

}
