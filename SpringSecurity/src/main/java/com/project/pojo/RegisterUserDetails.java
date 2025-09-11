package com.project.pojo;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;



public class RegisterUserDetails {

    // Username cannot be blank, must be between 3-50 characters, and must not contain only spaces
    @NotBlank(message = "Username is required and cannot be empty or just spaces")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    @Pattern(regexp = "^[^\\s]+$", message = "Username cannot contain spaces")
    private String userName;
    
    // Username cannot be blank, must be between 3-50 characters, and must not contain only spaces
    @NotBlank(message = "FirstName is required and cannot be empty or just spaces")
    @Size(min = 3, max = 50, message = "FirstName must be between 3 and 50 characters")
    @Pattern(regexp = "^[^\\s]+$", message = "FirstName cannot contain spaces")
    private String firstName;
    
    // Username cannot be blank, must be between 3-50 characters, and must not contain only spaces
    @NotBlank(message = "LastName is required and cannot be empty or just spaces")
    @Size(min = 1, max = 50, message = "LastName must be between 3 and 50 characters")
    @Pattern(regexp = "^[^\\s]+$", message = "LastName cannot contain spaces")
    private String lastName;

    // Email validation: must be a valid email and end with .com
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.com$", message = "Email must be a valid format and end with .com")
    private String email;

    // Password validation: must follow security rules, at least 6 characters, and no spaces
    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    @Pattern(
        regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!])[A-Za-z\\d@#$%^&+=!]{6,}$",
        message = "Password must contain at least 1 uppercase letter, 1 lowercase letter, 1 number, and 1 special character (@#$%^&+=!)"
    )
    private String password;

    // Confirm Password validation: must not be blank and must match password format
    @NotBlank(message = "Confirm Password is required")
    @Pattern(
        regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!])[A-Za-z\\d@#$%^&+=!]{6,}$",
        message = "Confirm Password must match password rules"
    )
    private String confirmPassword;
    private String fullName;
    
    
    public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getConfirmPassword() {
		return confirmPassword;
	}
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	// Check if passwords match
    public boolean isPasswordMatching() {
        return password != null && password.equals(confirmPassword);
    }
	@Override
	public String toString() {
		return "RegisterUserDetails [userName=" + userName + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", email=" + email + ", password=" + password + ", confirmPassword=" + confirmPassword + "]";
	}
    
    
    

   



	
}