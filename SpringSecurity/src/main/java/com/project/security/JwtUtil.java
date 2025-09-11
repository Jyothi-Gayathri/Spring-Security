package com.project.security;

import java.util.Base64;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;

/**
 * Utility class for handling JWT (JSON Web Token) generation and validation.
 */
@Component
public class JwtUtil {
	private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);
    private final SecretKey key;

    // Read properties from application.properties
    @Value("${jwt.expiration-time}")
    private long expirationTime;

//    @Value("${jwt.secret}")
//    private String jwtSecret;
    
    public JwtUtil(@Value("${jwt.secret}") String secret) {
        if (secret == null || secret.isEmpty()) {
            throw new IllegalArgumentException("JWT secret key cannot be null or empty");
        }

        // Decode Base64 secret and generate key
        byte[] decodedKey = Base64.getDecoder().decode(secret);
        this.key = Keys.hmacShaKeyFor(decodedKey);
    }

    /**
     * Generates a JWT token for a given username.
     * @param username The username for which the token is generated.
     * @return The generated JWT token.
     */
    public String generateToken(String username) {
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(key, Jwts.SIG.HS256)  //  Corrected Signing Algorithm
                .compact();
    }

    /**
     * Validates the JWT token and checks if it belongs to the given user.
     * @param token The JWT token.
     * @param userDetails The user details to validate against.
     * @return True if the token is valid and belongs to the user, otherwise false.
     */
    public boolean validateToken(String token, UserDetails userDetails) {
    	
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            
            String username = claims.getSubject();
            boolean isValid = username.equals(userDetails.getUsername()) && !isTokenExpired(token);
            
            
            logger.info("Validating Token: {}");
            logger.info("Extracted Username: {}");
            logger.info("Token Valid: {}", isValid);

            return isValid;
        } catch (JwtException | IllegalArgumentException e) {
            logger.error("Invalid Token: {}");
            return false;
        }
    }


    /**
     *  Checks if the token has expired.
     * @param token The JWT token.
     * @return True if expired, otherwise false.
     */
    public boolean isTokenExpired(String token) {
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(key)  // Fix for new JJWT 0.12.6 version
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            return claims.getExpiration().before(new Date());
        } catch (ExpiredJwtException e) {
            return true;
        }
    }
    
    
    /**
     * Extracts the username from the given JWT token.
     * @param token The JWT token.
     * @return The extracted username.
     */
    public String extractUsername(String token) {
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(key) // Use verifyWith() in the latest JJWT version
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            return claims.getSubject();
        } catch (ExpiredJwtException e) {
            return null; // Handle expired tokens appropriately
        } catch (JwtException e) {
            return null; // Handle invalid tokens
        }
    }
    
//    public Claims extractClaims(String token) {
//        try {
//            return Jwts.parser()
//                    .verifyWith(key) // Use verifyWith() in the latest JJWT version
//                    .build()
//                    .parseSignedClaims(token)
//                    .getPayload();
//        } catch (ExpiredJwtException e) {
//            throw new RuntimeException("JWT Token has expired.");
//        } catch (JwtException e) {
//            throw new RuntimeException("Invalid JWT Token.");
//        }
//    }

}