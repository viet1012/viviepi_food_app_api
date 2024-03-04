package com.food_app_api.Viviepi.jwt;

import com.google.gson.Gson;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;
import java.util.function.Function;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class JwtUtil {
    private static final int TOKEN_EXPIRE_MILLIS = 8 * 60 * 60 * 1000;
    private static final int REFRESH_TOKEN_EXPIRE_MILLIS = TOKEN_EXPIRE_MILLIS;
    private final static Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);



    public String generateToken(String data){
        return Jwts.builder()
                .setSubject(data)
                .setIssuer("FDA")
                .signWith(key)
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_EXPIRE_MILLIS))
                .compact();
    }

    public String generateRefreshToken(String data) {
        long refreshTokenExpirationTime = System.currentTimeMillis() + REFRESH_TOKEN_EXPIRE_MILLIS;
        Date refreshTokenExpirationDate = new Date(refreshTokenExpirationTime);

        return Jwts.builder()
                .setSubject(data)
                .signWith(key)
                .setExpiration(refreshTokenExpirationDate)
                .compact();
    }

    public String parserToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key).build()
                .parseClaimsJws(token).getBody()
                .getSubject();
    }

    public boolean validate(String token){
        try{
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        }catch (MalformedJwtException ex){
            System.out.println("Invalid JWT token");
        }catch (ExpiredJwtException ex)
        {
            System.out.println("Expired JWT token");
        }catch (UnsupportedJwtException ex)
        {
            System.out.println("Unsupported JWT token");
        }catch (IllegalArgumentException ex)
        {
            System.out.println("JWT claims string is empty");
        }
        return false;
    }


    private Claims getAllClaimsFromToken(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token).getBody();
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public boolean validationToke(String token) {
        return !isTokenExpired((token));
    }
}
