package com.userservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.userservice.dto.UserRegistrationDto;
import com.userservice.entity.User;
import com.userservice.exceptions.ApiResponse;
import com.userservice.repository.UserRepository;
import com.userservice.util.UserUtils;

@RestController
public class AuthController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder encoder;
	
	/*
	 * Registers a user in the app 
	 * Takes {@link UserRegistrationDto} with set of basic user details
	 * along with username & password
	 * 
	 */

	@PostMapping("/register")
	public ResponseEntity<ApiResponse> registerUser(@RequestBody UserRegistrationDto dto) {
		try {
			User user = userRepository.save(new User(dto.getFirstname(), dto.getLastname(),
					dto.getUsername(), encoder.encode(dto.getPassword())));
			if (user != null) {
				return new ResponseEntity<>(UserUtils.getProperApiResponse(HttpStatus.CREATED.value(), UserUtils.CREATED), HttpStatus.CREATED);
			}
			return new ResponseEntity<>(UserUtils.getProperApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), UserUtils.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			return new ResponseEntity<>(UserUtils.getProperApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), UserUtils.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
