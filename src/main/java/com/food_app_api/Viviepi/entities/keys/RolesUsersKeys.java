package com.food_app_api.Viviepi.entities.keys;

import jakarta.persistence.*;
import lombok.*;


import java.io.Serializable;
import java.util.Objects;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class RolesUsersKeys implements Serializable {

    @Column(name = "role_id")
    private long idRole;

    @Column(name = "user_id")
    private long idUser;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RolesUsersKeys)) return false;
        RolesUsersKeys that = (RolesUsersKeys) o;
        return idRole == that.idRole && idUser == that.idUser;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idRole, idUser);
    }
}
