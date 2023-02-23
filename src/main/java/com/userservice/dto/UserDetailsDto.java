package com.userservice.dto;

import java.util.List;

public class UserDetailsDto {
	
	private String firstname;
	private String lastname;
	private List<String> links;
	public UserDetailsDto(String firstname, String lastname, List<String> links) {
		super();
		this.firstname = firstname;
		this.lastname = lastname;
		this.links = links;
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
	public List<String> getLinks() {
		return links;
	}
	public void setLinks(List<String> links) {
		this.links = links;
	}
	

}
