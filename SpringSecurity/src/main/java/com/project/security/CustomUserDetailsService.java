package com.project.security;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.project.entity.Muser;
import com.project.entity.TUserProfile;
import com.project.repository.MUserRepo;
import com.project.repository.TUserProfileRepo;

@Service  // Marks this class as a Spring Bean
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private MUserRepo userRepository; // Inject MUserRepo

    @Autowired
    private TUserProfileRepo tUserProfileRepo; // Inject TUserProfileRepo
    
  

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Fetch user from Muser table
        Muser user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        // Fetch password from TUserProfile table
        TUserProfile userProfile = tUserProfileRepo.findByUserId(user.getId())
                .orElseThrow(() -> new UsernameNotFoundException("User profile not found for: " + username));

        // Convert to UserDetails
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                userProfile.getPasswordHash(), // Get password from TUserProfile
                Collections.singletonList(new SimpleGrantedAuthority("User")) // Fixed role issue
        );
    }
}