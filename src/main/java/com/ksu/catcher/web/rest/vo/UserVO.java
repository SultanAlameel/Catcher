package com.ksu.catcher.web.rest.vo;

import javax.validation.constraints.Email;

public class UserVO {
	
	
	private String firstName;
	private String lastName;
	@Email
	private String email;
	private String password;
	
	
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lasName) {
		this.lastName = lasName;
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



}


