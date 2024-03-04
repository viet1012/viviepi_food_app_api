package com.food_app_api.Viviepi.entities.token;


import com.food_app_api.Viviepi.entities.User;
import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "token")
public class Tokens {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "token")
    private String token;
    @Column(name = "token_type")
    private String tokenType;
    @Column(name = "expired")
    private boolean expired;
    @Column(name = "revokes")
    private boolean revoke;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
