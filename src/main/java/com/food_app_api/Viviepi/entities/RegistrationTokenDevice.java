package com.food_app_api.Viviepi.entities;

import lombok.*;


import jakarta.persistence.Column;
import jakarta.persistence.*;
import jakarta.persistence.GenerationType;
import lombok.*;
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "registration_tokens_device")
public class RegistrationTokenDevice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "token_device", nullable = false, length = 255)
    private String tokenDevice;

}
