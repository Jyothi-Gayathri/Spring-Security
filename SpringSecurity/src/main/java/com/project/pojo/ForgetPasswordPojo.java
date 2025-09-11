package com.project.pojo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class ForgetPasswordPojo {
	
	  // Username cannot be blank, must be between 3-50 characters, and must not contain only spaces
    @NotBlank(message = "Username is required and cannot be empty or just spaces")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    @Pattern(regexp = "^[^\\s]+$", message = "Username cannot contain spaces")
	private String userName;
    
    // Password validation: must follow security rules, at least 6 characters, and no spaces
    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    @Pattern(
        regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!])[A-Za-z\\d@#$%^&+=!]{6,}$",
        message = "Password must contain at least 1 uppercase letter, 1 lowercase letter, 1 number, and 1 special character (@#$%^&+=!)"
    )
	private String newPassword;
    
    // Confirm Password validation: must not be blank and must match password format
    @NotBlank(message = "Confirm Password is required")
    @Pattern(
        regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!])[A-Za-z\\d@#$%^&+=!]{6,}$",
        message = "Confirm Password must match password rules"
    )
	private String confirmPassword;
	
	
	
	
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	public String getConfirmPassword() {
		return confirmPassword;
	}
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	// Check if passwords match
    public boolean isPasswordMatching() {
        return newPassword != null && newPassword.equals(confirmPassword);
    }
	
	@Override
	public String toString() {
		return "ForgetPasswordPojo [userName=" + userName + ", newPassword=" + newPassword + ", confirmPassword="
				+ confirmPassword + "]";
	}
	
	

}