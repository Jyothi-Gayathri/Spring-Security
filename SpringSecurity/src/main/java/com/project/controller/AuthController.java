package com.project.controller;

import com.project.pojo.OutputResponse;
import com.project.security.JwtUtil;
import com.project.service.AuthService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Controller for handling authentication (Login API).
 */
@RestController
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
	private AuthService authService;


    /**
     * Login endpoint that authenticates the user and generates a JWT token.
     * @param request A map containing "username" and "password".
     * @return JWT token if authentication is successful.
     */
    @PostMapping("/login")
    public ResponseEntity<OutputResponse> login(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        String password = request.get("password");

        // Call AuthService for login
        OutputResponse response = authService.login(username, password);

        return ResponseEntity.ok(response);
    }
    
    
    @PostMapping("/refresh")
    public ResponseEntity<OutputResponse> refreshAccessToken(@RequestBody Map<String, String> request) {
        String refreshToken = request.get("refreshToken");

        OutputResponse outputResponse = authService.refreshAccessToken(refreshToken);
        
        return ResponseEntity.ok(outputResponse);
    }
    
    
    

}