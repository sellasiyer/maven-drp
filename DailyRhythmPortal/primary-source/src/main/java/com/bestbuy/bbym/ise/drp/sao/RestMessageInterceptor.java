package com.bestbuy.bbym.ise.drp.sao;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

public class RestMessageInterceptor implements ClientHttpRequestInterceptor {

    @Autowired
    private RestErrorHandler errorHandler;

    private static Logger logger = LoggerFactory.getLogger(RestMessageInterceptor.class);

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
	    throws IOException {

	String outbound = new String(body);
	if (StringUtils.isNotBlank(outbound))
	    logger.debug("Outbound Rest Message : " + outbound);
	logger.debug("Outbound Rest Message Headers : " + request.getHeaders().toString());
	RestClientHttpResponse interPretResponse = null;
	ClientHttpResponse response = execution.execute(request, body);
	if (errorHandler.hasError(response)){
	    errorHandler.handleError(response);
	    return response;
	}else{
	    interPretResponse = new RestClientHttpResponse(response.getBody(), response.getStatusCode(), response
		    .getStatusText(), response.getHeaders());

	    logger.debug("Inbound Rest Message Status Code : " + interPretResponse.getStatusCode());
	    logger.debug("Inbound Rest Message Status Message : " + interPretResponse.getStatusText());
	    logger.debug("Inbound Rest Message Headers >> ");
	    for(String str: interPretResponse.getHeaders().keySet())
		logger.debug("Header Key : " + str + ", Value : " + interPretResponse.getHeaders().get(str));
	    logger.debug("Inbound Rest Message : " + interPretResponse.getResponseMsg());
	}
	return interPretResponse;

    }

    class RestClientHttpResponse implements ClientHttpResponse {

	private String responseString;
	private String statusMsg;
	private HttpStatus httpStatus;
	private HttpHeaders httpHead;

	public RestClientHttpResponse(InputStream is, HttpStatus sc, String st, HttpHeaders head) throws IOException {

	    responseString = IOUtils.toString(is);
	    httpStatus = sc;
	    httpHead = head;
	    statusMsg = st;
	}

	@Override
	public InputStream getBody() throws IOException {
	    // TODO Auto-generated method stub
	    return IOUtils.toInputStream(responseString);
	}

	@Override
	public HttpHeaders getHeaders() {
	    // TODO Auto-generated method stub
	    return httpHead;
	}

	@Override
	public void close() {
	    // TODO Auto-generated method stub

	}

	@Override
	public HttpStatus getStatusCode() throws IOException {
	    // TODO Auto-generated method stub
	    return httpStatus;
	}

	@Override
	public String getStatusText() throws IOException {
	    // TODO Auto-generated method stub
	    return statusMsg;
	}

	public String getResponseMsg() {
	    return responseString;
	}
    }

}
