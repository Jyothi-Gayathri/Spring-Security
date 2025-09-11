package com.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import com.project.common.CommonAutoWired;
import com.project.common.FinalConstants;
import com.project.entity.Muser;
import com.project.entity.RefreshToken;
import com.project.exceptionhandler.InvalidRefreshTokenException;
import com.project.pojo.OutputResponse;
import com.project.repository.MUserRepo;
import com.project.security.JwtUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class AuthService {

	

	
	@Autowired
	private CommonAutoWired commonAutoWired;
	
	@Autowired
	private FinalConstants finalConstants;


	private final AuthenticationManager authenticationManager;
	private final JwtUtil jwtUtil;



	public AuthService(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
		this.authenticationManager = authenticationManager;
		this.jwtUtil = jwtUtil;
	}

	public OutputResponse login(String username, String password) {
		// Authenticate user
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(username, password)
				);

		// Get authenticated user details
		User user = (User) authentication.getPrincipal();

		// Generate JWT token
		String accessToken  = jwtUtil.generateToken(user.getUsername());

		Muser muser = commonAutoWired.mUserRepo.findByUsername(user.getUsername())
				.orElseThrow(() -> new RuntimeException("User not found"));

		// Generate refresh token
		RefreshToken refreshToken = commonAutoWired.refreshTokenService.createRefreshToken(muser);


		Map<String, String> tokens = new HashMap<>();
		tokens.put("accessToken", accessToken);
		tokens.put("refreshToken", refreshToken.getToken());
		OutputResponse outputResponse = new OutputResponse();

		outputResponse.setStatus(finalConstants.SUCCESS);
		outputResponse.setOutput(tokens);
		outputResponse.setMessage("User loggedin Successfully.");

		// Return response with status and token
		return  outputResponse;
	}
	
	
	
	public OutputResponse refreshAccessToken(String refreshToken) {
	    Map<String, String> response = new HashMap<>();

	    // Retrieve the user associated with the provided refresh token
	    Optional<RefreshToken> storedToken = commonAutoWired.refreshTokenService.getRefreshToken(refreshToken);

	    // Validate the refresh token
	    if (storedToken.isEmpty() || !commonAutoWired.refreshTokenService.validateRefreshToken(storedToken.get())) {
	        throw new InvalidRefreshTokenException("Invalid or expired refresh token");
	    }

	    Muser user = storedToken.get().getUser();

	    // Fetch the latest refresh token for the user
	    Optional<RefreshToken> latestToken = commonAutoWired.refreshTokenService.getLatestActiveToken(user);

	    // Ensure that the provided refresh token matches the latest one
	    if (latestToken.isEmpty() || !latestToken.get().getToken().equals(refreshToken)) {
	        throw new InvalidRefreshTokenException("Token mismatch: Only the latest refresh token is valid.");
	    }

	    // Generate new access token
	    String newAccessToken = jwtUtil.generateToken(user.getUsername());

	    // Revoke the current refresh token and issue a new one
	    commonAutoWired.refreshTokenService.revokeRefreshToken(storedToken.get());
	    RefreshToken newRefreshToken = commonAutoWired.refreshTokenService.createRefreshToken(user);

	    OutputResponse outputResponse = new OutputResponse();
	    response.put("accessToken", newAccessToken);
	    response.put("refreshToken", newRefreshToken.getToken());

	    outputResponse.setStatus(finalConstants.SUCCESS);
	    outputResponse.setOutput(response);
	    outputResponse.setMessage("New token generated successfully.");

	    return outputResponse;
	}


	// logout Srrvice
	public ResponseEntity<OutputResponse> logOut() {
		
		 OutputResponse outputResponse = new OutputResponse();
		  // Get authenticated user from SecurityContext
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            outputResponse.setStatus(finalConstants.FAIL);
            outputResponse.setMessage("User is not authenticated.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(outputResponse);
        }

    
        // Extract username from the authenticated user
        String username = authentication.getName();
        
        System.out.println(username);

        // Fetch user by username
        Muser user = commonAutoWired.mUserRepo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Delete all refresh tokens for the user
        commonAutoWired.refreshTokenService.deleteRefreshTokensByUser(user);
        outputResponse.setStatus(finalConstants.SUCCESS);
        outputResponse.setMessage("User logged out successfully.");
        return ResponseEntity.ok(outputResponse);
	}

}