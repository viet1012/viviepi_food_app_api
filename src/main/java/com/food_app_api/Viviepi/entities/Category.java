package com.food_app_api.Viviepi.entities;

import com.google.api.client.util.DateTime;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import java.sql.Timestamp;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "category")
public class Category{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @CreatedBy
    @Column(name = "created_by")
    private String createdBy;
    @CreatedDate
    @Column(name = "created_at")
    private DateTime createdAt;
    @LastModifiedBy
    @Column(name = "updated_by")
    private String updateBy;
    @LastModifiedDate
    @Column(name = "updated_at")
    private DateTime updateAt;
    @Column(name = "category_code", unique = true)
    private String categoryCode;
    @Column(name = "name")
    private String name;
    @Column(name = "status", nullable = false)
    private int status;
    @Column(name = "banner_url")
    private String bannerUrl;


}
