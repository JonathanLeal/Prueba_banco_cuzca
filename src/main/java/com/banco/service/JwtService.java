package com.banco.service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.banco.config.JwtProperties;
import com.banco.entity.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
	
	private final JwtProperties jwtProperties;
	
	public JwtService(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

	private Key getSigningKey() {
        return Keys.hmacShaKeyFor(
                jwtProperties.getSecret().getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Genera un JWT para el usuario autenticado
     */
    public String generateToken(User user) {

        Date now = new Date();

        Date expiration = new Date(
                now.getTime() + jwtProperties.getExpiration());

        return Jwts.builder()
                .subject(user.getEmail())
                .claim("role", user.getRole().name())
                .issuedAt(now)
                .expiration(expiration)
                .signWith(getSigningKey())
                .compact();
    }

    /**
     * Obtiene el email almacenado en el token
     */
    public String extractUsername(String token) {

        return extractClaims(token).getSubject();
    }

    /**
     * Verifica si el token pertenece al usuario
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {

        String username = extractUsername(token);

        return username.equals(userDetails.getUsername())
                && !isTokenExpired(token);
    }

    /**
     * Verifica si el token expiró
     */
    private boolean isTokenExpired(String token) {

        return extractClaims(token)
                .getExpiration()
                .before(new Date());
    }

    /**
     * Obtiene todos los claims del JWT
     */
    private Claims extractClaims(String token) {

        return Jwts.parser()
                .verifyWith((javax.crypto.SecretKey) getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

}