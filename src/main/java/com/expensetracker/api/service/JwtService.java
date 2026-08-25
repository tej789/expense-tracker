package com.expensetracker.api.service;

import com.expensetracker.api.model.Role;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;


@Service
public class JwtService {

 @Value("${jwt.secret}")
private String Secret;

public String generateToken(String username , Role role){

    SecretKey key = Keys.hmacShaKeyFor(Secret.getBytes());
    return Jwts.builder()
            .subject(username)
            .claim("role",role)    // for role based access
            .issuedAt(new java.util.Date())
            .expiration(new java.util.Date(System.currentTimeMillis() + 1000 * 60 * 120))
            .signWith(key)
            .compact();
}


public String extractUsername(String token){
    return Jwts.parser()
            .verifyWith(Keys.hmacShaKeyFor(Secret.getBytes()))
            .build()
            .parseSignedClaims(token)
            .getPayload()
            .getSubject()   ;
}


    public String extractRole(String token) {

        return Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(Secret.getBytes()))
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("role", String.class);
    }
}
