package com.userservice.exceptions;

public class UserNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;
	
	private String message;

	public UserNotFoundException(String message) {
		super(message);
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
