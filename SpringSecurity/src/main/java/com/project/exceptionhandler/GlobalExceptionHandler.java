package com.project.exceptionhandler;

import java.util.HashMap;
import java.util.Map;

import javax.naming.AuthenticationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;
import com.project.pojo.OutputResponse;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	
	
	 // Handle validation errors (400 Bad Request)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	
	 public ResponseEntity<OutputResponse> handleValidationErrors(MethodArgumentNotValidException ex) {
		OutputResponse outputResponse = new OutputResponse();

		 Map<String, String> errors = new HashMap<>();
        // Extract field errors and put them into a map
        ex.getBindingResult().getFieldErrors().forEach(error ->
            errors.put(error.getField(), error.getDefaultMessage())
        );
        outputResponse.setStatus("fail");
        outputResponse.setMessage(errors);

        // Return structured response
        return ResponseEntity.badRequest().body(outputResponse);
    }
	
	  @ExceptionHandler(InvalidRefreshTokenException.class)
	    public ResponseEntity<OutputResponse> handleInvalidTokenException(InvalidRefreshTokenException ex) {
		  OutputResponse outputResponse = new OutputResponse();
	        outputResponse.setStatus("fail");
	        outputResponse.setMessage(ex.getMessage());
		  
		  return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(outputResponse);
	    }
	
	
	
	
	// Handle unsupported HTTP methods (405 Method Not Allowed)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<OutputResponse> handleMethodNotSupported(HttpRequestMethodNotSupportedException ex) {
        OutputResponse outputResponse = new OutputResponse();
        outputResponse.setStatus("fail");
        outputResponse.setMessage("Request method '" + ex.getMethod() + "' is not supported. Please check the API documentation.");

        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(outputResponse);
    }
	
    
    
 // Handle generic exceptions (500 Internal Server Error)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<OutputResponse> handleGeneralException(Exception ex) {
        OutputResponse outputResponse = new OutputResponse();
        outputResponse.setStatus("error");
        outputResponse.setMessage("An unexpected error occurred. Please try again later.");
        
        // Log the exception for debugging
        ex.printStackTrace(); 

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(outputResponse);
    }
    
    
 // Handle specific business logic exceptions
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<OutputResponse> handleResponseStatusException(ResponseStatusException ex) {
        OutputResponse outputResponse = new OutputResponse();
        outputResponse.setStatus("fail");
        outputResponse.setMessage(ex.getReason());
        return ResponseEntity.status(ex.getStatusCode()).body(outputResponse);
    }
    
    
    // Handle authentication failures (401 Unauthorized)
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<OutputResponse> handleAuthenticationException(AuthenticationException ex) {
        OutputResponse outputResponse = new OutputResponse();
        outputResponse.setStatus("fail");
        outputResponse.setMessage("Authentication failed: " + ex.getMessage());


        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(outputResponse);
    }

    // Handle incorrect credentials (401 Unauthorized)
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<OutputResponse> handleBadCredentialsException(BadCredentialsException ex) {
        OutputResponse outputResponse = new OutputResponse();
        outputResponse.setStatus("fail");
        outputResponse.setMessage("Invalid username or password.");


        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(outputResponse);
    }

    // Handle unauthorized access (403 Forbidden)
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<OutputResponse> handleAccessDeniedException(AccessDeniedException ex) {
        OutputResponse outputResponse = new OutputResponse();
        outputResponse.setStatus("fail");
        outputResponse.setMessage("You do not have permission to access this resource.");


        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(outputResponse);
    }

    // Handle user not found (404 Not Found)
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<OutputResponse> handleUserNotFoundException(UsernameNotFoundException ex) {
        OutputResponse outputResponse = new OutputResponse();
        outputResponse.setStatus("fail");
        outputResponse.setMessage("User not found: " + ex.getMessage());


        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(outputResponse);
    }

    // Handle JWT Authentication errors (401 Unauthorized)
    @ExceptionHandler(JwtException.class)
    public ResponseEntity<OutputResponse> handleJwtException(JwtException ex) {
        OutputResponse outputResponse = new OutputResponse();
        outputResponse.setStatus("fail");
        outputResponse.setMessage("Invalid or expired JWT token. Please log in again.");


        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(outputResponse);
    }


 // Handle Expired JWT Token Exception (No duplicate)
    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<OutputResponse> handleExpiredJwtException(ExpiredJwtException ex) {
        OutputResponse outputResponse = new OutputResponse();
        outputResponse.setStatus("fail");
        outputResponse.setMessage("Token has expired. Please log in again.");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(outputResponse);
    }

}