package com.userservice.util;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.userservice.dto.Root;
import com.userservice.dto.UserDetailsDto;
import com.userservice.entity.User;
import com.userservice.exceptions.ApiResponse;

public interface UserUtils {
	
	public final static String INTERNAL_SERVER_ERROR = "Your request cannot be processed at this time. Please try again later";
	public final static String CREATED = "Your registration is successfully completed";
	public final static String NOT_FOUND = "User details are not found";
	
	public static ApiResponse getProperApiResponse(int code, String message) {
		return new ApiResponse(code, message);
	}

	public static UserDetailsDto packUserDetails(User user, List<String> list) {
		return new UserDetailsDto(user.getFirstname(), user.getLastname(), list);
	}
	
	public static List<String> getImageLInks(List<Root> list) {
		return list.stream().map(root -> root.getData().getLink()).collect(Collectors.toList());
	}
	
	public static UserDetails getCurrentPrincipal() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		return userDetails;
		
	}
}