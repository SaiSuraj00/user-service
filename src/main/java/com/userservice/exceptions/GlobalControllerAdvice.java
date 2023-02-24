package com.userservice.exceptions;

import java.nio.file.attribute.UserPrincipalNotFoundException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.userservice.util.UserUtils;

@ControllerAdvice
public class GlobalControllerAdvice {

	@ExceptionHandler(value = ImageNotFoundException.class)
	public ResponseEntity<ApiResponse> exception(ImageNotFoundException exception) {
		return UserUtils.packExceptionDetails(exception);
	}
	
	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<ApiResponse> exception(Exception exception) {
		return UserUtils.packExceptionDetails(exception);
	}
	
	@ExceptionHandler(value = UserNotFoundException.class)
	public ResponseEntity<ApiResponse> exception(UserNotFoundException exception) {
		return UserUtils.packExceptionDetails(exception);
	}
	
	@ExceptionHandler(value = UserPrincipalNotFoundException.class)
	public ResponseEntity<ApiResponse> exception(UserPrincipalNotFoundException exception) {
		return UserUtils.packExceptionDetails(exception);
	}
}
