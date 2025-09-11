package com.project.entity;

import java.time.Instant;
import java.util.Set;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "m_users") // Maps this entity to the "m_users" table in the database
public class Muser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increment ID (BIGSERIAL in DB)
    @Column(name = "id", nullable = false, updatable = false) // Ensures ID is never null or changed
    private Long id;

    @Column(nullable = false, unique = true, length = 50) // Ensures unique, non-null usernames (max length 50)
    private String username;

    
    @Column(nullable = false, length = 50)
    private String firstName; // First Name

    @Column(nullable = false, length = 50)
    private String lastName; // Last Name

    @Column(nullable = false, length = 101, insertable = false, updatable = false)
    private String fullName; // Computed Column (Read-Only)
    
    
    @Column(nullable = false, unique = true, length = 255) // Ensures unique, non-null emails (max length 255)
    private String email;

    @CreationTimestamp // Auto-sets when the entity is created
    @Column(name = "created_at", updatable = false) // Cannot be updated after creation
    private Instant createdAt;

    @UpdateTimestamp // Auto-updates whenever the entity is modified
    @Column(name = "updated_at")
    private Instant updatedAt;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true) 
    // Defines a one-to-one relationship with TUserProfile; deletes profile if user is deleted
    private TUserProfile tUserProfile;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    // Defines a one-to-many relationship with TUserRole; deletes roles if user is deleted
    private Set<TUserRole> roles;

    
    
    
    
    
    // Getter and Setter for ID
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // Getter and Setter for Username
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    
    
    
    public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	// Getter and Setter for Email
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Getter and Setter for CreatedAt
    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    // Getter and Setter for UpdatedAt
    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    // Getter and Setter for UserProfile
    public TUserProfile getTUserProfile() {
        return tUserProfile;
    }

    public void setTUserProfile(TUserProfile tUserProfile) {
        this.tUserProfile = tUserProfile;
    }

    // Getter and Setter for Roles
    public Set<TUserRole> getRoles() {
        return roles;
    }

    public void setRoles(Set<TUserRole> roles) {
        this.roles = roles;
    }
    
    

    public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public TUserProfile gettUserProfile() {
		return tUserProfile;
	}

	public void settUserProfile(TUserProfile tUserProfile) {
		this.tUserProfile = tUserProfile;
	}

	// Override toString() for debugging and logging
    @Override
    public String toString() {
        return "Muser [id=" + id + ", username=" + username + ", email=" + email + 
               ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + "]";
    }
    
    
}