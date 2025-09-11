package com.project.service;


import com.project.entity.Muser;
import com.project.entity.RefreshToken;
import com.project.repository.RefreshTokenRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional 
public class RefreshTokenService {

	private final RefreshTokenRepository refreshTokenRepository;



	public RefreshTokenService(RefreshTokenRepository refreshTokenRepository) {
		this.refreshTokenRepository = refreshTokenRepository;
	}




	/**
	 * Generates a new refresh token for a user.
	 */
	public RefreshToken createRefreshToken(Muser user) {


		// Get the latest active refresh token and revoke it
		refreshTokenRepository.findFirstByUserAndRevokedFalseOrderByExpiryDateDesc(user)
		.ifPresent(token -> {
			token.setRevoked(true);
			refreshTokenRepository.save(token);
		});




		// Generate a unique token and set expiration time
		RefreshToken refreshToken = new RefreshToken();
		refreshToken.setUser(user);
		refreshToken.setToken(UUID.randomUUID().toString());
		refreshToken.setRevoked(false); // Ensure it's active

		return refreshTokenRepository.save(refreshToken);
	}

	/**
	 * Finds a refresh token by token value.
	 */
	public Optional<RefreshToken> getRefreshToken(String token) {
		return refreshTokenRepository.findByToken(token);
	}

	/**
	 * Validates the refresh token (checks expiration and revocation).
	 */
	public RefreshToken getValidRefreshToken(String token) {
		return refreshTokenRepository.findByToken(token)
				.filter(this::validateRefreshToken) // Ensure it's not expired/revoked
				.orElseThrow(() -> new RuntimeException("Invalid or expired refresh token"));
	}

	public boolean validateRefreshToken(RefreshToken refreshToken) {
		return refreshToken.getExpiryDate().isAfter(Instant.now()) && !refreshToken.isRevoked();
	}

	/**
	 * Revokes an old refresh token (e.g., when generating a new access token).
	 */
	@Transactional
	public void revokeRefreshToken(RefreshToken refreshToken) {
		refreshToken.setRevoked(true);
		refreshTokenRepository.save(refreshToken);
	}

	/**
	 * Deletes all refresh tokens for a user (useful for logout).
	 */
	@Transactional
	public void deleteRefreshTokensByUser(Muser user) {
		refreshTokenRepository.deleteByUser(user);
	}




	public Optional<RefreshToken> getLatestActiveToken(Muser user) {
		return refreshTokenRepository.findFirstByUserAndRevokedFalseOrderByExpiryDateDesc(user);
	}


}