package com.bestbuy.bbym.ise.drp.sao;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

public class RestErrorHandler implements ResponseErrorHandler {

    private static Logger logger = LoggerFactory.getLogger(RestErrorHandler.class);

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
	logger.error("======Rest Client Error Report ========");
	logger.error("Status Message : " + response.getStatusText());
	logger.error("Status Code : " + response.getStatusCode().toString());
	logger.error("Status Code Reason: " + response.getStatusCode().getReasonPhrase());
	//Once consumed it will not be available.
	//DO NOT CONSUME the error response body here.

	for(String str: response.getHeaders().keySet())
	    logger.debug("Header Key : " + str + ", Value : " + response.getHeaders().get(str));
	//logger.error("Error Response Headers : " + response.getHeaders().toString());
	logger.error("======Rest Client Error Report ========");

    }

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {

	if ((response.getStatusCode().series() == HttpStatus.Series.CLIENT_ERROR)
		|| (response.getStatusCode().series() == HttpStatus.Series.SERVER_ERROR)){
	    return true;
	}

	return false;
    }

}
