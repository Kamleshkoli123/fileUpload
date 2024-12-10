package com.DocMate.service;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
public class JwtService {

    private static final Logger logger = LoggerFactory.getLogger(JwtService.class);

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration:2592000000}") // Default 30 days in milliseconds
    private long jwtExpiration;

    // Validate JWT token
    public boolean validateToken(String token) {
        try {
            logger.info("passkey: {}", secretKey);
            
            // Ensure only the token part is extracted
            String jwt = token.split(" ")[1];
            
            Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes()))
                .build()
                .parseClaimsJws(jwt);
            
            logger.info("JWT token validated successfully.");
            return true;
        } catch (ExpiredJwtException e) {
            logger.error("JWT token expired: {}", token);
        } catch (JwtException e) {
            logger.error("Invalid JWT token: {}", token);
        }
        return false;
    }
    
    public String extractContactFromJwt(String token) {
        try {
        	
        	String jwt = token.split(" ")[1];
        	
            Claims claims = Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes()))
                .build()
                .parseClaimsJws(jwt)
                .getBody();
            String contact = claims.getSubject();  // Assuming contact is stored in 'sub'
            logger.info("Extracted contact from JWT: {}", contact);
            return contact;
        } catch (JwtException e) {
            logger.error("Error extracting contact from JWT: {}", token, e);
            throw new RuntimeException("Invalid JWT token.");
        }
    }

}
