package com.project.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.project.entity.Muser;

@Repository
public interface MUserRepo extends JpaRepository<Muser, Long> {

	
	 Optional<Muser> findByUsername(String username);
	    Optional<Muser> findByEmail(String email);
		Optional<Muser> findByUsernameAndEmail(String username, String email);
}