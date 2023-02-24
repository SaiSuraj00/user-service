package com.userservice.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.userservice.dto.UserDetailsDto;
import com.userservice.entity.User;
import com.userservice.exceptions.PricipalNotFoundException;
import com.userservice.repository.UserRepository;
import com.userservice.util.UserUtils;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ImageService imageService;
	
	/*
	 * 	Retrieves Principal from current Security Context if exists
	 *  Responds with all the user details UserDetailsDto
	 *  and all the image links associated with current account 
	 */
	
	public UserDetailsDto getUserProfile() throws PricipalNotFoundException {
		String username = UserUtils.getCurrentPrincipal().getUsername();
		Optional<User> user = userRepository.findByUsername(username);
		return UserUtils.packUserDetails(user.get(), imageService.getAllImages());
	}
	
	
}
