package com.userservice.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UserRegistrationDto implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@NotEmpty
	@NotNull
	@Size( min = 3 , max = 20)
	private String firstname;
	@NotEmpty
	@NotNull
	@Size( min = 3 , max = 20)
	private String lastname;
	@NotEmpty
	@NotNull
	@Size( min = 3 , max = 20)
	@Pattern(regexp = "^[A-Za-z0-9]*$")
	private String username;
	@NotEmpty
	@NotNull
	private String password;
	public UserRegistrationDto() {
		super();
	}
	public UserRegistrationDto(String firstname, String lastname, String username, String password) {
		super();
		this.firstname = firstname;
		this.lastname = lastname;
		this.username = username;
		this.password = password;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	
	
}
