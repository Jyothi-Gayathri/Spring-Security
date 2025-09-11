package com.project.common;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;

import com.project.repository.MUserRepo;
import com.project.repository.TUserProfileRepo;
import com.project.repository.TUserRoleRepo;
import com.project.service.RefreshTokenService;

@Component
public class CommonAutoWired {
	
	@Autowired
	public MUserRepo mUserRepo;
	@Autowired
	public TUserProfileRepo tUserProfileRepo;
	@Autowired
	public TUserRoleRepo tUserRoleRepo;
	
	@Autowired
	public RefreshTokenService refreshTokenService;

}