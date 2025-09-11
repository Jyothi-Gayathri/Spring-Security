package com.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.pojo.OutputResponse;
import com.project.service.AuthService;


@RestController
@RequestMapping("/private")
public class PrivateController {
	
	@Autowired
	private AuthService authService;
	
	  @GetMapping("/test")
	    public ResponseEntity<OutputResponse> registerUser() {
	        OutputResponse response = new OutputResponse();
	        response.setStatus("Success");
	        response.setOutput("Tested using authenticated");
	        return ResponseEntity.ok(response);
	    }
	  
	  
	  @PostMapping("/logout")
	    public  ResponseEntity<OutputResponse>  logout() {
	        return authService.logOut();
	    }

}