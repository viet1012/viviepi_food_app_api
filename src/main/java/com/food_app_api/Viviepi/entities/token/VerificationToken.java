package com.food_app_api.Viviepi.entities.token;

import com.food_app_api.Viviepi.entities.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.util.Calendar;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "verification_token")
public class VerificationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "otp")
    private String otp;
    @Column(name = "expired_time")
    private Date expirationTime;
    private static final int EXPIRATION_TIME = 15;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    public VerificationToken(String otp, User user) {
        this.otp = otp;
        this.user = user;
        this.expirationTime = getTokenExpirationTime();
    }

    public VerificationToken(String otp) {
        this.otp = otp;
        this.expirationTime = getTokenExpirationTime();
    }

    public Date getTokenExpirationTime(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(new Date().getTime());
        calendar.add(Calendar.MINUTE, EXPIRATION_TIME);
        return new Date(calendar.getTime().getTime());
    }
}
