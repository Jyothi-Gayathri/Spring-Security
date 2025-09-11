package com.project.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.pojo.ForgetPasswordPojo;
import com.project.pojo.OutputResponse;
import com.project.pojo.RegisterUserDetails;
import com.project.service.PublicService;

import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@Validated
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private PublicService publicService;
    
    private static final Logger logger = LoggerFactory.getLogger(PublicController.class);

    @PostConstruct
    public void init() {
        logger.info("🚀 PublicController Loaded Successfully!");
    }

    @PostMapping("/registerUser")
    public ResponseEntity<OutputResponse> registerUser(@Valid @RequestBody RegisterUserDetails entity) {
        OutputResponse response = publicService.registerUser(entity);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/test")
    public ResponseEntity<OutputResponse> registerUser() {
        OutputResponse response = new OutputResponse();
        response.setStatus("Success");
        response.setOutput("Have to register user.");
        return ResponseEntity.ok(response);
    }
    
    
    @PostMapping("/forgetPassword")
    public ResponseEntity<OutputResponse> forgetPassword(@Valid @RequestBody ForgetPasswordPojo entity) {
        OutputResponse response = publicService.forgetPassword(entity);
        return ResponseEntity.ok(response);
    }

    
    
}