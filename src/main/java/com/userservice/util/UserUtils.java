package com.userservice.util;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import com.userservice.dto.Root;
import com.userservice.dto.UserDetailsDto;
import com.userservice.entity.User;
import com.userservice.exceptions.ApiResponse;
import com.userservice.exceptions.PricipalNotFoundException;

public interface UserUtils {
	
	public static final String INTERNAL_SERVER_ERROR = "Your request cannot be processed at this time. Please try again later";
	public static final String CREATED = "Your registration is successfully completed";
	public static final String NOT_FOUND = "User details are not found";
	public static final String DELETION_SUCCESSFULL = "Image deleted Successfully";
	public static final String SESSION_TIMED_OUT = "Session timed out. Please try logging in again";
	public static final String IMAGE_NOT_FOUND = "There is no image associated with the provided hash value";
	public static final String CONFLICT = "Username is already taken. Please use a different one";
	
	public static ApiResponse getProperApiResponse(int code, String message) {
		return new ApiResponse(code, message);
	}

	/*
	 * Packs basic information and images associated with the
	 * current user and returns UserDetailsDto with all the details
	 */
	public static UserDetailsDto packUserDetails(User user, List<String> list) {
		return new UserDetailsDto(user.getFirstname(), user.getLastname(), list);
	}
	
	/*
	 * Returns list of image links from Root(Image) list with the help
	 * of stream api
	 */
	public static List<String> getImageLInks(List<Root> list) {
		return list.stream().map(root -> root.getData().getLink()).collect(Collectors.toList());
	}
	
	/*
	 * Returns the currrent user principal from the security context
	 * @throws PricipalNotFoundException if principal doesn't exist
	 */
	
	public static UserDetails getCurrentPrincipal() throws PricipalNotFoundException {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		if(userDetails == null) {
			throw new PricipalNotFoundException(SESSION_TIMED_OUT);
		}
		return userDetails;
		
	}

	/*
	 * Packs NOT_FOUND exceptions and returns the ApiResponse
	 */
	public static ResponseEntity<ApiResponse> packExceptionDetails(Exception exception) {
		ApiResponse error = new ApiResponse(HttpStatus.NOT_FOUND.value(), exception.getMessage());
		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
		
	}
}
