
package com.project.security;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * JWT Authentication Filter that intercepts requests to validate JWT tokens.
 * Ensures authentication is processed before reaching the controller.
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil; // Utility class for handling JWT operations

    @Autowired
    private UserDetailsService userDetailsService; // Service to load user details from database

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    /**
     * Intercepts HTTP requests to extract and validate JWT tokens.
     * If a valid token is found, it sets the authentication in SecurityContext.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        // Extract the "Authorization" header from the request
        String authorizationHeader = request.getHeader("Authorization");

        // Check if the Authorization header is present and starts with "Bearer "
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            logger.warn("No Authorization header or incorrect format");
            chain.doFilter(request, response); // Continue the filter chain
            return;
        }

        // Extract the JWT token (after "Bearer ")
        String token = authorizationHeader.substring(7);

        // Extract the username from the token
        String username = jwtUtil.extractUsername(token);
        logger.info("Extracted Username from Token: {}", username);

        // Check if username is not null and the user is not already authenticated
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            
            // Load user details from the database
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            // Validate the token against user details
            if (jwtUtil.validateToken(token, userDetails)) {
                // Create an authentication token for the user
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                
                // Set request details to authentication object
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                
                // Set the authentication in SecurityContext
                SecurityContextHolder.getContext().setAuthentication(authentication);
                logger.info("User {} authenticated, SecurityContext updated");
            } else {
                logger.warn("Token validation failed for user: {}");
            }
        } else {
            logger.warn("Token is invalid or user is already authenticated");
        }

        // Continue the filter chain
        chain.doFilter(request, response);
    }
}
