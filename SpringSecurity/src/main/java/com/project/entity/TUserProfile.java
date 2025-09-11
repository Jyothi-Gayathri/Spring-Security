package com.project.entity;

import com.project.security.AESUtil;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "t_user_profile") // Maps this entity to the "t_user_profile" table in the database
public class TUserProfile {

    @Id
    private Long userId; // Primary key, should match "m_users.id" type (Long)

    @OneToOne
    @MapsId // Ensures this entity shares the same ID as the associated Muser entity
    @JoinColumn(name = "user_id", referencedColumnName = "id") // Foreign key linking to "m_users(id)"
    private Muser user;

    @Column(nullable = false) // Ensures password hash cannot be null
    private String passwordHash;

    // Getter and Setter for userId
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    // Getter and Setter for User
    public Muser getUser() {
        return user;
    }

    public void setUser(Muser user) {
        this.user = user;
    }

    // Getter and Setter for PasswordHash
    public String getPasswordHash() {
        return passwordHash;
    }

//    public void setPasswordHash(String passwordHash) {
//        this.passwordHash = passwordHash;
//    }

    
    
    // Override toString() for debugging and logging
    @Override
    public String toString() {
        return "TUserProfile [userId=" + userId + ", user=" + user + ", passwordHash=" + passwordHash + "]";
    }
    
    
    
    public void setPasswordHash(String rawPassword) {
        try {
            this.passwordHash = AESUtil.encrypt(rawPassword); // Encrypt password automatically
        } catch (Exception e) {
            throw new RuntimeException("Error encrypting password", e);
        }
    }
}