package com.ekfc.onlinestore.Services;

import java.util.Date;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.ekfc.onlinestore.Models.users.users;
import com.ekfc.onlinestore.Models.users.usersDto;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
    private final String SECRE_KEY = "19e1f3ec575aa2afb3264035e72059c91eb3006743effd10fe295d4786805b21";

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public boolean isValid(String token, UserDetails user) {
        String username = extractUsername(token);
        return (username.equals(user.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> resolver) {
        Claims claims = extractAllClaims(token);
        return resolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().verifyWith(getSignInKey()).build().parseSignedClaims(token).getPayload();
    }

    public String generateToke(users user) {
        usersDto userDetails = new usersDto();
        userDetails.setUsername(user.getUsername());
        userDetails.setRole(user.getRole());
        String token = Jwts.builder().subject(user.getUsername()).claim("username", userDetails.getUsername())
                .claim("role", userDetails.getRole())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 3600000)) // 1 Hour
                .signWith(getSignInKey()).compact();
        return token;
    }

    private SecretKey getSignInKey() {
        byte[] keyByte = Decoders.BASE64URL.decode(SECRE_KEY);
        return Keys.hmacShaKeyFor(keyByte);
    }
}
