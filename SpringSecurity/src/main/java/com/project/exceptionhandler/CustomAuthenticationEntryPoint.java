package com.project.exceptionhandler;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.project.pojo.OutputResponse;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;


@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        
        // Prepare JSON response
        OutputResponse outputResponse = new OutputResponse();
        outputResponse.setStatus("fail");
        outputResponse.setMessage("Unauthorized access: Please provide a valid token.");
        
        response.getWriter().write(new com.fasterxml.jackson.databind.ObjectMapper().writeValueAsString(outputResponse));
    }
}