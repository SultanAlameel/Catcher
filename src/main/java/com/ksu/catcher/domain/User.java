package com.ksu.catcher.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;


@Entity
@Table(name="TBL_USER")
public class User {
	
@Id
@GeneratedValue(strategy=GenerationType.AUTO)	
private long id;
private String firstName;
private String lastName;
@Email
private String email;
private String password;
//@OneToMany(mappedBy = "user",fetch = FetchType.LAZY )
//private List<Domain> domains ;



public long getId() {
	return id;
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
