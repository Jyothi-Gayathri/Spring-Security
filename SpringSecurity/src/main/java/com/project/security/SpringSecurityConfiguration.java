package com.project.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.project.exceptionhandler.CustomAuthenticationEntryPoint;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfiguration {

	  private final CustomAuthenticationEntryPoint authenticationEntryPoint;
	 private final JwtAuthenticationFilter jwtAuthenticationFilter;

	 public SpringSecurityConfiguration(CustomAuthenticationEntryPoint authenticationEntryPoint, JwtAuthenticationFilter jwtAuthenticationFilter) {
	        this.authenticationEntryPoint = authenticationEntryPoint;
	        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
	    }
	
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // Disable CSRF for stateless authentication
            .csrf(csrf -> csrf.disable())
            
            // Enable CORS configuration
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            
            // Configure session management (Max 2 active sessions per user, timeout: 10 minutes)
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))

            // Define authorization rules
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/public/**", "/auth/**").permitAll() // Public endpoints accessible without authentication
//                .requestMatchers("/private/**").authenticated() // Secure all other endpoints
                .anyRequest().authenticated()
            ) .exceptionHandling(exception -> exception
                    .authenticationEntryPoint(authenticationEntryPoint) // Use custom entry point
                    )
            
            // Add JWT authentication filter before UsernamePasswordAuthenticationFilter
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        
        
        return http.build();
    }

    /**
     * Password encoder bean using BCrypt hashing algorithm.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new CustomPasswordEncoder(); //Use AES-based password encryption
    }
    /**
     * Authentication provider bean that uses UserDetailsService and PasswordEncoder.
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider(UserDetailsService userDetailsService) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

   
    /**
     * Provide AuthenticationManager as a bean for authentication handling.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    /**
     * CORS configuration bean to allow cross-origin requests.
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("*")); // Allow all origins (modify for security)
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
    
    
 
    
}