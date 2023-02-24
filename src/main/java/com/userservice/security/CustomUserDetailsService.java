package com.userservice.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.userservice.entity.User;
import com.userservice.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	/*
	 * Authentication provider which is DaoAuthenticationProvider in this app
	 * triggers the method and passes the username retrieved from
	 * UsernamePasswordAuthenticationToken and Authentication manager sets
	 * the security context after validating the user password
	 */
	@Override
	public UserDetails loadUserByUsername(String username) {
	   Optional<User> user= userRepository.findByUsername(username);
	   if(user.isEmpty()) {
		   throw new UsernameNotFoundException("User not found with username: "+username);
	   }
	   return new CustomUserDetails(user.get());
	}

}
