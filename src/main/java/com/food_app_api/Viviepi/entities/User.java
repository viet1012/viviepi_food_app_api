package com.food_app_api.Viviepi.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "user_models")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "email")
    private String email;


    @Column(name = "password")
    private String password;

    @Column(name = "new_password")
    private String newPassword;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "display_name")
    private String displayName;

    @Column(name = "last_online")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime lastOnline;

    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    private Role role;

    @Column(name = "created_by")
    private String createdBy;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_dt")
    private LocalDateTime createdDate;

    @Column(name = "updated_by")
    private String updatedBy;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_dt")
    private LocalDateTime updatedDate;

    @Column(name = "active")
    private Boolean active;

    @Column(name = "otp")
    private String otp;

    @Column(name = "create_date_otp")
    private LocalDateTime createDateOtp;

}

