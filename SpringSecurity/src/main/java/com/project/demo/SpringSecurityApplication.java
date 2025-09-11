package com.project.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
@ComponentScan(basePackages = "com.project") // Add this line
@EnableJpaRepositories(basePackages = "com.project.repository") 
@EntityScan(basePackages = "com.project.entity")
public class SpringSecurityApplication extends SpringBootServletInitializer{

	public static void main(String[] args)  {
		SpringApplication.run(SpringSecurityApplication.class, args);
	}
	
	

	 @Override
	    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) { // ✅ Override correctly
	        return builder.sources(SpringSecurityApplication.class);
	    }


}