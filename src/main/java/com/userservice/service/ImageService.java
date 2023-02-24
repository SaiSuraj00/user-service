package com.userservice.service;

import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.userservice.dto.Root;
import com.userservice.exceptions.ImageNotFoundException;
import com.userservice.exceptions.RestTemplateResponseErrorHandler;
import com.userservice.util.UserUtils;

@Service
public class ImageService {

	@Value("${imgur.clientid}")
	private String IMGUR_CLIENT_ID;
	
	@Value("${imgur.baseurl}")
	private String BASE_URL;
	
	private RestTemplate restTemplate;
	
	private static final Logger logger = LoggerFactory.getLogger(ImageService.class);

    @Autowired
    public ImageService(RestTemplateBuilder restTemplateBuilder) {
        restTemplate = restTemplateBuilder
          .errorHandler(new RestTemplateResponseErrorHandler())
          .build();
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
	    HttpHeaders headers = new HttpHeaders();
	    headers.add("Authorization", "Client-ID " + IMGUR_CLIENT_ID);
	    HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
	    ResponseEntity<Root> response = restTemplate.exchange(BASE_URL+username+"/image/"+imageId, HttpMethod.GET, entity, Root.class);
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
	    headers.add("Authorization", "Client-ID " + IMGUR_CLIENT_ID);
	    HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
	    try {
	    	restTemplate.delete(BASE_URL+username+"/image/"+imageId, entity);
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
	    HttpHeaders headers = new HttpHeaders();
	    headers.add("Authorization", "Client-ID " + IMGUR_CLIENT_ID);
	    HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
	    ResponseEntity<List<Root>> response = restTemplate.exchange(BASE_URL+username+"/images/", HttpMethod.GET, entity, new ParameterizedTypeReference<List<Root>>() {
		});
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
