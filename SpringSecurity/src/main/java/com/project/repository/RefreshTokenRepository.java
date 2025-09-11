package com.project.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.entity.Muser;
import com.project.entity.RefreshToken;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    // Find refresh token by token value
    Optional<RefreshToken> findByToken(String token);

    // Find active refresh token for a user
    Optional<RefreshToken> findFirstByUserAndRevokedFalseOrderByExpiryDateDesc(Muser user);

    // Delete all refresh tokens for a user (optional, for logout scenarios)
    void deleteByUser(Muser user);

	Optional<RefreshToken> findTopByUserAndRevokedFalseOrderByExpiryDateDesc(Muser user);
}