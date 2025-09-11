package com.project.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "t_user_roles") // Maps this entity to the "t_user_roles" table in the database
public class TUserRole {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-incremented primary key (BIGSERIAL in DB)
    @Column(name = "id", nullable = false, updatable = false) // Ensures ID is not null and cannot be modified manually
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false) // Foreign key referencing "m_users.id"
    private Muser user;

    @Column(nullable = false, length = 50) // Ensures role is non-null and max length is 50 characters
    private String role;

    // Getter and Setter for ID
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // Getter and Setter for User
    public Muser getUser() {
        return user;
    }

    public void setUser(Muser user) {
        this.user = user;
    }

    // Getter and Setter for Role
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    // Override toString() for debugging and logging
    @Override
    public String toString() {
        return "TUserRole [id=" + id + ", user=" + user + ", role=" + role + "]";
    }
}