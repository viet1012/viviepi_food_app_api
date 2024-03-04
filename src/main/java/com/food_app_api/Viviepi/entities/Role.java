package com.food_app_api.Viviepi.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity(name = "role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false,unique = true)
    private String name;

    @OneToMany(mappedBy = "idRole")
    private Set<RolesUsers> rolesUsersEntities;

}
