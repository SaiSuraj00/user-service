package com.userservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.userservice.dto.Root;
import com.userservice.exceptions.ImageNotFoundException;
import com.userservice.util.UserUtils;

@Service
public class ImageService {

	@Value("${imgur.clientid}")
	private String IMGUR_CLIENT_ID;
	
	@Value("${imgur.baseurl}")
	private String BASE_URL;
	
	public Root getImage(String imageId) throws ImageNotFoundException {
		String username = UserUtils.getCurrentPrincipal().getUsername();
		RestTemplate restTemplate = new RestTemplate();
	    HttpHeaders headers = new HttpHeaders();
	    headers.add("Authorization", "Client-ID " + IMGUR_CLIENT_ID);
	    HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
	    ResponseEntity<Root> response = restTemplate.exchange(BASE_URL+username+"/image/"+imageId, HttpMethod.GET, entity, Root.class);
	    if(!response.hasBody()) {
	    	throw new ImageNotFoundException("There is no image associated with the provided hash value : "+imageId);
	    }
	    return response.getBody();
	}
	
	public ResponseEntity<Object> deleteImage(String imageId) {
		String username = UserUtils.getCurrentPrincipal().getUsername();
		RestTemplate restTemplate = new RestTemplate();
	    HttpHeaders headers = new HttpHeaders();
	    headers.add("Authorization", "Client-ID " + IMGUR_CLIENT_ID);
	    HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
	    restTemplate.delete(BASE_URL+username+"/image/"+imageId, entity);
	    return new ResponseEntity<>(UserUtils.getProperApiResponse(HttpStatus.OK.value(), "Image deleted Successfully"), HttpStatus.OK);
	}
	
	public List<String> getAllImages() {
		String username = UserUtils.getCurrentPrincipal().getUsername();
		RestTemplate restTemplate = new RestTemplate();
	    HttpHeaders headers = new HttpHeaders();
	    headers.add("Authorization", "Client-ID " + IMGUR_CLIENT_ID);
	    HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
	    ResponseEntity<List<Root>> response = restTemplate.exchange(BASE_URL+username+"/images/", HttpMethod.GET, entity, new ParameterizedTypeReference<List<Root>>() {
		});
	    return UserUtils.getImageLInks(response.getBody());
	}
}
