package com.userservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.userservice.dto.Root;
import com.userservice.dto.UserDetailsDto;
import com.userservice.exceptions.ApiResponse;
import com.userservice.exceptions.ImageNotFoundException;
import com.userservice.exceptions.PricipalNotFoundException;
import com.userservice.service.ImageService;
import com.userservice.service.UserService;
import com.userservice.util.UserUtils;	

@RestController
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ImageService imageService;
	

	@GetMapping("/user")
	public ResponseEntity<UserDetailsDto> getUserProfile() throws PricipalNotFoundException {
		return new ResponseEntity<>(userService.getUserProfile(), HttpStatus.OK);
	}
	
	@GetMapping("/user/images")
	public ResponseEntity<List<String>> getAllImages() throws PricipalNotFoundException {
		return new ResponseEntity<List<String>>(imageService.getAllImages(), HttpStatus.OK);
		
	}

	@GetMapping("/user/images/{imageId}")
	public ResponseEntity<Root> getImage(@PathVariable("imageId") String imageId) throws ImageNotFoundException, PricipalNotFoundException {
		return new ResponseEntity<Root>(imageService.getImage(imageId), HttpStatus.OK);
	}

	@DeleteMapping("/user/images/{imageId}")
	public ResponseEntity<ApiResponse> deleteImage(@PathVariable("imageId") String imageId) {
		try {
			imageService.deleteImage(imageId);
			return new ResponseEntity<>(UserUtils.getProperApiResponse(HttpStatus.OK.value(), UserUtils.DELETION_SUCCESSFULL), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(UserUtils.getProperApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), UserUtils.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

}
