package com.food_app_api.Viviepi.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "review_product_models")
public class ReviewProduct{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "food_id", referencedColumnName = "id")
    private Food food;

    @Column(name = "rating")
    private int rating;

    @Column(name = "comment")
    private String comment;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_dt")
    private LocalDateTime createdDt;

}
