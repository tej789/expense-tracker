package com.expensetracker.api.service.impl;

import com.expensetracker.api.model.Role;
import com.expensetracker.api.service.JwtService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;

@Service
public class JwtServiceImpl implements JwtService {

    @Value("${jwt.secret}")
    private String Secret;

    @Override
    public String generateToken(String username , Role role){

        SecretKey key = Keys.hmacShaKeyFor(Secret.getBytes());
        return Jwts.builder()
                .subject(username)
                .claim("role",role)
                .issuedAt(new java.util.Date())
                .expiration(new java.util.Date(System.currentTimeMillis() + 1000 * 60 * 120))
                .signWith(key)
                .compact();
    }

    @Override
    public String extractUsername(String token){
        return Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(Secret.getBytes()))
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }


    @Override
    public String extractRole(String token) {

        return Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(Secret.getBytes()))
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("role", String.class);
    }
}
