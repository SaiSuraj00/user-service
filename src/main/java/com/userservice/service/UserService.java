package com.userservice.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.userservice.dto.UserDetailsDto;
import com.userservice.entity.User;
import com.userservice.exceptions.UserNotFoundException;
import com.userservice.repository.UserRepository;
import com.userservice.util.UserUtils;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ImageService imageService;
	
	public UserDetailsDto getUserProfile() throws UserNotFoundException {
		String username = UserUtils.getCurrentPrincipal().getUsername();
		Optional<User> user = userRepository.findByUsername(username);
		if(user.isEmpty()) {
			throw new UserNotFoundException("There is no user currently logged in");
		}
		return UserUtils.packUserDetails(user.get(), imageService.getAllImages());
	}
	
	
}
