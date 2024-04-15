package com.food_app_api.Viviepi.entities;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "ward")
    private String ward;
    @Column(name = "district")

    private String district;
    @Column(name = "house_number")

    private String houseNumber;
    @Column(name = "note")

    private String note;
    @Column(name = "active")

    private boolean active;

}

