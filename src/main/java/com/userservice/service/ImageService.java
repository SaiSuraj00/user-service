package com.userservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.userservice.dto.Root;
import com.userservice.exceptions.ImageNotFoundException;
import com.userservice.exceptions.PricipalNotFoundException;
import com.userservice.util.UserUtils;

@Service
public class ImageService {

	@Value("${imgur.clientid}")
	private String IMGUR_CLIENT_ID;
	
	@Value("${imgur.baseurl}")
	private String BASE_URL;
	
	@Autowired
	private RestTemplate restTemplate;
	
	/*
	 * 	Retrieves Principal from current Security Context if exists
	 *  Responds with a single image entity associated with
	 *  a username & image id from imgur external API 
	 *  @throws ImageNotFoundException if image doesn't exist
	 *  @throws PricipalNotFoundException if Principal doesn't exist
	 */
	
	public Root getImage(String imageId) throws ImageNotFoundException, PricipalNotFoundException {
		String username = UserUtils.getCurrentPrincipal().getUsername();
	    HttpHeaders headers = new HttpHeaders();
	    headers.add("Authorization", "Client-ID " + IMGUR_CLIENT_ID);
	    HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
	    ResponseEntity<Root> response = restTemplate.exchange(BASE_URL+username+"/image/"+imageId, HttpMethod.GET, entity, Root.class);
	    if(!response.hasBody()) {
	    	throw new ImageNotFoundException(UserUtils.IMAGE_NOT_FOUND);
	    }
	    return response.getBody();
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
	
	public List<String> getAllImages() throws PricipalNotFoundException {
		String username = UserUtils.getCurrentPrincipal().getUsername();
	    HttpHeaders headers = new HttpHeaders();
	    headers.add("Authorization", "Client-ID " + IMGUR_CLIENT_ID);
	    HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
	    ResponseEntity<List<Root>> response = restTemplate.exchange(BASE_URL+username+"/images/", HttpMethod.GET, entity, new ParameterizedTypeReference<List<Root>>() {
		});
	    return UserUtils.getImageLInks(response.getBody());
	}
}
