package com.userservice.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.userservice.dto.UserDetailsDto;
import com.userservice.entity.User;
import com.userservice.repository.UserRepository;
import com.userservice.util.UserUtils;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ImageService imageService;
	
	private static final Logger logger = LoggerFactory.getLogger(UserService.class);
	
	/*
	 * 	Returns Principal from current Security Context if exists
	 *  Responds with all the user details UserDetailsDto
	 *  and all the image links associated with current account 
	 */
	
	public UserDetailsDto getUserProfile() throws Exception {
		String username = UserUtils.getCurrentPrincipal().getUsername();
		Optional<User> user = userRepository.findByUsername(username);
		logger.info("Retrieved user : "+user.get().getFirstname());
		return UserUtils.packUserDetails(user.get(), imageService.getAllImages());
	}
	
	
}
