package com.project.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Value;

@Entity
@Table(name = "refresh_tokens")
public class RefreshToken {
	@Value("${jwt.refresh-expiration-time}") // Read expiration time from properties
	private long refreshExpirationTime;


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)  // Auto-incrementing ID
	private Long id;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)  // Foreign key reference to User entity
	private Muser user;

	@Column(nullable = false, unique = true, length = 500)  // Store refresh token
	private String token;


	@NotNull
	@Column(nullable = false)
	private Instant expiryDate;  // Expiration timestamp

	@Column(nullable = false)
	private boolean revoked = false;  // Whether the token is revoked

	@Column(name = "created_at", updatable = false, nullable = false)
	private Instant createdAt = Instant.now();  // Timestamp when created


	@PrePersist
	public void prePersist() {
		this.createdAt = Instant.now();  // Set creation time
		this.expiryDate = createdAt.plusSeconds(20 * 60);  // Set expiry time (20 minutes later)
	}



	// Getters and Setters

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Muser getUser() {
		return user;
	}

	public void setUser(Muser user) {
		this.user = user;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}



	public Instant getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Instant expiryDate) {
		this.expiryDate = expiryDate;
	}

	public boolean isRevoked() {
		return revoked;
	}

	public void setRevoked(boolean revoked) {
		this.revoked = revoked;
	}

	public Instant getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Instant createdAt) {
		this.createdAt = createdAt;
	}
}