package com.userservice.service;

import java.util.Base64;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.userservice.dto.Root;
import com.userservice.exceptions.ImageNotFoundException;
import com.userservice.util.UserUtils;

@Service
public class ImageService {

	@Value("${imgur.token}")
	private String token;
	
	@Value("${imgur.baseurl}")
	private String baseUrl;
	
	@Autowired
	private RestTemplate restTemplate;
	
	private static final Logger logger = LoggerFactory.getLogger(ImageService.class);
    
    public Root uploadImage(MultipartFile file) throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);
		headers.add("Authorization", "Client-ID " + token);
		MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
		Base64.Encoder encoder = Base64.getEncoder();
		body.add("image", encoder.encode(file.getBytes()));
		HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
		try {
			return restTemplate.postForObject(baseUrl + "upload", requestEntity, Root.class);
		}
		catch (Exception e) {
			throw new Exception(e.getMessage());
		}

	}
    
	/*
	 * 	Retrieves Principal from current Security Context if exists
	 *  Responds with a single image entity associated with
	 *  a username & image id from imgur external API 
	 *  @throws ImageNotFoundException if image doesn't exist
	 *  @throws PricipalNotFoundException if Principal doesn't exist
	 */

	
	public Root getImage(String imageId) throws Exception {
		String username = UserUtils.getCurrentPrincipal().getUsername();
		ResponseEntity<Root> response = null;
	    HttpHeaders headers = new HttpHeaders();
	    headers.add("Authorization", "Client-ID " + token);
	    HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
	    try {
	    	response = restTemplate.exchange(baseUrl + "account/" +username + "/image/" + imageId, HttpMethod.GET, entity, Root.class);
	    }
	    catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	    if(response.hasBody()) {
	    	if(response.getBody().getData() != null) {
	    		logger.info("Successully retrieved the image with ID : "+imageId);
			    return response.getBody();
		    }
	    	else {
	    		logger.info("Image not found with ID : "+imageId);
		    	throw new ImageNotFoundException(UserUtils.IMAGE_NOT_FOUND);
	    	}
	    }
	    else {
	    	throw new Exception(UserUtils.INTERNAL_SERVER_ERROR);
	    }
	}
	
	/*
	 * 	Retrieves Principal from current Security Context if exists
	 *  Deletes an image entity associated with
	 *  a username & image id from imgur external API 
	 *  @throws PricipalNotFoundException if Principal doesn't exist
	 */
	public void deleteImage(String imageId) throws Exception {
		String username = UserUtils.getCurrentPrincipal().getUsername();
	    HttpHeaders headers = new HttpHeaders();
	    headers.add("Authorization", "Client-ID " + token);
	    HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
	    try {
	    	restTemplate.delete(baseUrl+ "account/" + username + "/image/" +imageId, entity);
	    }
	    catch (Exception e) {
			throw new Exception(UserUtils.INTERNAL_SERVER_ERROR);
		}
	}
	
	/*
	 * 	Retrieves Principal from current Security Context if exists
	 *  Responds with all the images associated with
	 *  a username & image id from imgur external API 
	 *  @throws PricipalNotFoundException if Principal doesn't exist
	 */
	
	public List<String> getAllImages() throws Exception {
		String username = UserUtils.getCurrentPrincipal().getUsername();
		ResponseEntity<List<Root>> response = null;
	    HttpHeaders headers = new HttpHeaders();
	    headers.add("Authorization", "Client-ID " + token);
	    HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
	    try {
	    	response = restTemplate.exchange(baseUrl+username+"/images/", HttpMethod.GET, entity, new ParameterizedTypeReference<List<Root>>() {});
	    }
	    catch (Exception e) {
			new Exception(e.getMessage());
		}
	    if(response.hasBody()) {
	    	if(response.getBody().size() > 0) {
	    		logger.info("Successully retrieved the images for username : "+username);
			    return UserUtils.getImageLInks(response.getBody());
		    }
	    	else {
	    		logger.info("Returning empty list when there is no images available associated with username : "+username);
		    	return Collections.emptyList();
	    	}
	    }
	    throw new Exception(UserUtils.INTERNAL_SERVER_ERROR);
	}
}
