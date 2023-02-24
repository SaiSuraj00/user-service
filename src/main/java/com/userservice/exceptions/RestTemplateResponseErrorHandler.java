package com.userservice.exceptions;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;

import com.userservice.controller.AuthController;

@Component
public class RestTemplateResponseErrorHandler 
  implements ResponseErrorHandler {
	
	private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
	
    @Override
    public boolean hasError(ClientHttpResponse httpResponse) 
      throws IOException {

        return (
          httpResponse.getStatusCode().series() == HttpStatus.Series.CLIENT_ERROR 
          || httpResponse.getStatusCode().series() == HttpStatus.Series.SERVER_ERROR);
    }

    @Override
    public void handleError(ClientHttpResponse httpResponse) 
      throws IOException {

        if (httpResponse.getStatusCode()
          .series() == HttpStatus.Series.SERVER_ERROR) {
        } else if (httpResponse.getStatusCode()
          .series() == HttpStatus.Series.CLIENT_ERROR) {
        	
            if (httpResponse.getStatusCode() == HttpStatus.NOT_FOUND) {
                
            }
        }
    }
}
