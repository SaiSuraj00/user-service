package com.userservice.exceptions;

public class ImageNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;
	
	private String message;

	public ImageNotFoundException(String message) {
		super(message);
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
