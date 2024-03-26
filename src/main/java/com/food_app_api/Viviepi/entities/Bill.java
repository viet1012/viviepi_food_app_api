package com.food_app_api.Viviepi.entities;

import jakarta.persistence.Column;
import jakarta.persistence.*;
import jakarta.persistence.GenerationType;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "bill")
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @NonNull
    private User user;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)

    @Column(name = "date")
    private LocalDateTime date;

    @Column(name = "name")
    private String name;

    @Column(name = "phone")
    private long phone;

    @Column(name = "delivery_address")
    private String deliveryAddress;

    @Column(name = "description")
    private String description;

    @Column(name = "status")
    private int status;
    @ManyToOne
    @JoinColumn(name = "voucher_id")
    private Voucher voucher;

    @Column(name = "total_price")
    private double totalPrice;
}
