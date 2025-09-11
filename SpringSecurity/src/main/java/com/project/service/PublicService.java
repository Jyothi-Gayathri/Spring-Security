package com.project.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.common.CommonAutoWired;
import com.project.common.FinalConstants;
import com.project.entity.Muser;
import com.project.entity.TUserProfile;
import com.project.entity.TUserRole;
import com.project.pojo.ForgetPasswordPojo;
import com.project.pojo.OutputResponse;
import com.project.pojo.RegisterUserDetails;

import jakarta.validation.Valid;

@Service 
public class PublicService {
	@Autowired
	private FinalConstants finalConstants;

	@Autowired
	private CommonAutoWired commonAutoWired;

	public OutputResponse registerUser(RegisterUserDetails entity) {
		OutputResponse outputResponse = new OutputResponse();

		if(!entity.isPasswordMatching()) {
			outputResponse.setStatus(finalConstants.FAIL);
			outputResponse.setMessage("Password and Confirm Password not matched");	
			return outputResponse;
		}


		Optional<Muser>mUserCheck = commonAutoWired.mUserRepo.findByUsernameAndEmail(entity.getUserName().trim(), entity.getEmail().trim());
		if(mUserCheck.isPresent()) {
			outputResponse.setStatus(finalConstants.FAIL);
			outputResponse.setMessage("Username and Email are already present. Please log in with your Username.");
		}else {
			Muser muser = new Muser();
			muser.setUsername(entity.getUserName());
			muser.setEmail(entity.getEmail());
			muser.setFirstName(entity.getFirstName());
			muser.setLastName(entity.getLastName());
			muser.setFullName(entity.getFirstName()+entity.getLastName());
			commonAutoWired.mUserRepo.save(muser);

			TUserProfile tUserProfile = new TUserProfile();
			tUserProfile.setPasswordHash(entity.getConfirmPassword());
			tUserProfile.setUser(muser);

			commonAutoWired.tUserProfileRepo.save(tUserProfile);

			TUserRole tUserRole = new TUserRole();
			tUserRole.setUser(muser);
			tUserRole.setRole("User");
			commonAutoWired.tUserRoleRepo.save(tUserRole);

			outputResponse.setStatus(finalConstants.SUCCESS);
			outputResponse.setMessage("User registered successfully.");
		}

		return outputResponse;		
	}


	public OutputResponse forgetPassword(@Valid ForgetPasswordPojo entity) {
		OutputResponse outputResponse = new OutputResponse();
		if(!entity.isPasswordMatching()) {
			outputResponse.setStatus(finalConstants.FAIL);
			outputResponse.setMessage("Password and Confirm Password not matched");	
			return outputResponse;
		}
		
		Optional<Muser>mUserCheck = commonAutoWired.mUserRepo.findByUsername(entity.getUserName().trim());

		if(mUserCheck.isPresent()) {
			System.out.println("in muser");
			Optional<TUserProfile> tUserProfile = commonAutoWired.tUserProfileRepo.findByUserId(mUserCheck.get().getId());
			if(tUserProfile.isPresent()) {

				System.out.println("in tUserProfile");
				TUserProfile tup = tUserProfile.get();
				if(tup.getPasswordHash().equals(entity.getConfirmPassword())) {
					outputResponse.setStatus(finalConstants.FAIL);
					outputResponse.setMessage("The new password matches the old password. Please try a different password.");
				}else {
					tup.setPasswordHash(entity.getConfirmPassword());
					commonAutoWired.tUserProfileRepo.save(tup);
					outputResponse.setStatus(finalConstants.SUCCESS);
					outputResponse.setMessage("Password changed successfully.");
				}
				return outputResponse;
			}
		}else {
			outputResponse.setStatus(finalConstants.FAIL);
			outputResponse.setMessage("UserName not found, Please give the valid username.");
			return outputResponse;
		}
		
		return outputResponse;
	}

}