package com.example.webapp.components;

import com.example.webapp.models.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.InvalidParameterException;
import java.security.Key;
import java.security.SecureRandom;
import java.util.*;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class JwtTokenUtil {
    @Value("${jwt.expiration}")
    private Long EXPIRATION_TIME;
    @Value("${jwt.secretKey}")
    private String SECRET_KEY;

    public String generateToken(User user) throws Exception{
        Map<String, Object> claims = new HashMap<>();
//        this.generateSecretKey();
        claims.put("phoneNumber", user.getPhoneNumber());
        try {
            String token = Jwts.builder()
                    .setClaims(claims)
                    .setSubject(user.getPhoneNumber())
                    .setIssuedAt(new Date(System.currentTimeMillis()))
                    .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME * 1000L))
                    .signWith(SignatureAlgorithm.HS256, getSingKey())
                    .compact();
            return token;
        } catch (Exception e) {
           throw new InvalidParameterException("Cannot create jwt token, error: " + e.getMessage());
        }
    }
    private Key getSingKey() {
        byte[] bytes = Decoders.BASE64.decode(SECRET_KEY); // 3Pgwya2DSlH7pVoBvFf3qIdBG9Vv9qF1Iy6cOh5uZz8=
        return Keys.hmacShaKeyFor(bytes);
    }

    private String generateSecretKey() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] bytes = new byte[32];
        secureRandom.nextBytes(bytes);
        String secretKey = Base64.getEncoder().encodeToString(bytes);
        return secretKey;
    }

    private Claims extractAllClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getSingKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            System.out.println("Cannot extractAllClaims: " + e.getMessage());
            return null;
        }
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        try {
            final Claims claims = extractAllClaims(token);
            return claimResolver.apply(claims);
        } catch (Exception e) {
            System.out.println("Cannot extractClaim: " + e.getMessage());
            return null;
        }
    }

    private boolean isTokenExpired(String token) {
        try {
            Date expiration = extractClaim(token, Claims::getExpiration);
            return expiration.before(new Date());
        } catch (Exception e) {
            System.out.println("Cannot check tokenExpired: " + e.getMessage());
            return true;
        }
    }
}