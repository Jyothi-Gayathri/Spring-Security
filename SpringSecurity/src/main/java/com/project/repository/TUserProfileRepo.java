package com.project.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.project.entity.TUserProfile;


@Repository
public interface TUserProfileRepo extends JpaRepository<TUserProfile, Long>{

	Optional<TUserProfile> findByUserId(Long long1);

}