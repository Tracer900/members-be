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
        String nyckel_secret = "412EC77DE11A48E7B15FB94906C430050920E59BC39F408F8AB0A3E60D1420991D7D4E58BE7B4406B2F30C82657BA136D740ED074C164D6A9211051424035D5F";
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

    private <T> T extractClaims(String token, Function<Claims, T> claimsTFunction){
        return claimsTFunction.apply(Jwts.parser().verifyWith(nyckel).build().parseSignedClaims(token).getPayload());
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String namn = extractUsername(token);
        return (namn.equals(userDetails.getUsername()) && !arTokenUtgangen(token));
    }

    public boolean arTokenUtgangen(String token) {
        return extractClaims(token, Claims::getExpiration).before(new Date());
    }
}
