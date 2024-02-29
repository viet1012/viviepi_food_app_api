package com.food_app_api.Viviepi.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity(name = "role_models")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String roleTitle;

    private String description;

    private String created_by;

    private LocalDateTime created_dt;

    private String updated_by;

    private LocalDateTime updated_dt;

    private Boolean active;


    // getters and setters
}
