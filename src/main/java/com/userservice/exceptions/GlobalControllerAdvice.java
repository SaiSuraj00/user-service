package com.userservice.exceptions;

import java.nio.file.attribute.UserPrincipalNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.userservice.util.UserUtils;

@ControllerAdvice
public class GlobalControllerAdvice {

	@ExceptionHandler(value = ImageNotFoundException.class)
	public ResponseEntity<ApiResponse> handleImageException(ImageNotFoundException exception) {
		return UserUtils.packExceptionDetails(exception, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<ApiResponse> handleDefaultException(Exception exception) {
		return UserUtils.packExceptionDetails(exception, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(value = UserNotFoundException.class)
	public ResponseEntity<ApiResponse> handleUserException(UserNotFoundException exception) {
		return UserUtils.packExceptionDetails(exception, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(value = UserPrincipalNotFoundException.class)
	public ResponseEntity<ApiResponse> handlePrincipalException(UserPrincipalNotFoundException exception) {
		return UserUtils.packExceptionDetails(exception, HttpStatus.NOT_FOUND);
	}
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiResponse> handleValidationExceptions(
	  MethodArgumentNotValidException exception) {
		return UserUtils.packExceptionDetails(exception, HttpStatus.BAD_REQUEST);
	
	}
}
