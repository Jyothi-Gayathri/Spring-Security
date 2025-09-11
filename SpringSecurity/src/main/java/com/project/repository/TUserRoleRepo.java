package com.project.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.entity.Muser;
import com.project.entity.TUserRole;

@Repository
public interface TUserRoleRepo extends JpaRepository<TUserRole, Long> {
	

}