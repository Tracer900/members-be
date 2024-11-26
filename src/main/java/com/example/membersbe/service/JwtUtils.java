package com.example.membersbe.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;

@Component
public class JwtUtils {
    private final SecretKey nyckel;
    private static final Long UTGANGS_TID = 7200000L; //2 hours

    public JwtUtils() {
        String nyckel_secret = "05045e1b-30da-460c-ac97-16ec88921580-fc42a36f-9136-4655-abf2-cf8591cad79e";
        byte[] nyckelBytes = Base64.getDecoder().decode(nyckel_secret.getBytes(StandardCharsets.UTF_8));
        this.nyckel = new SecretKeySpec(nyckelBytes, "HmacSHA256");
    }

    public String skapaJwt(UserDetails userDetails) {
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + UTGANGS_TID))
                .signWith(nyckel)
                .compact();
    }

    public String uppdateraJwt(HashMap<String, Object> claims, UserDetails userDetails) {
        return Jwts.builder()
                .claims(claims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + UTGANGS_TID))
                .signWith(nyckel)
                .compact();
    }

    public String extractUsername(String token) {
        return extractClaims(token, Claims::getSubject);
    }

    private <T> T extractClaims(String token, Function<Claims, T> claimsResolver) {
        return claimsResolver.apply(Jwts.parser().verifyWith(nyckel).build().parseSignedClaims(token).getPayload());
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String namn = extractUsername(token);
        return (namn.equals(userDetails.getUsername()) && !arTokenUtgangen(token));
    }

    public boolean arTokenUtgangen(String token) {
        return extractClaims(token, Claims::getExpiration).before(new Date());
    }
}
