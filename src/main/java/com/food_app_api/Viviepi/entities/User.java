package com.food_app_api.Viviepi.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "[user]")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "address")
    private String address;

    @Column(name = "new_password")
    private String newPassword;

    @Column(name = "gender")
    private int gender;

    @Column(name = "phone")
    private String phoneNumber;

    @Column(name = "full_name")
    private String fullname;

    @Column(name = "avt_url")
    private String avtUrl;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_dt")
    private LocalDateTime createdDate;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_dt")
    private LocalDateTime updatedDate;

    @Column(name = "active")
    private Boolean isActive;

    @Column(name = "otp")
    private String otp;

    @Column(name = "create_date_otp")
    private LocalDateTime createDateOtp;

    @Column(name = "birthday", nullable = true)
    private LocalDateTime birthday;

    @Column(name = "verify_code", nullable = true)
    private String verifyCode;

    @Column(name = "verify_code_expired", nullable = true)
    private String verifyCodeExpired;

    @Column(name = "token", nullable = true)
    private String token;

    @OneToMany(mappedBy = "idUser")
    private Set<RolesUsers> rolesUsersEntities;
    @Override
    public String toString() {
        return "User{" +
                "fullname='" + fullname + '\'' +
                ", email='" + email + '\'' +
                ", password=" + password +

                '}';
    }
}

