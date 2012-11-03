package com.bestbuy.bbym.ise.drp.rest.util;

import com.bestbuy.bbym.ise.exception.IseExceptionCodeEnum;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.springframework.stereotype.Service;

/**
 *
 * @author a904002
 */
@Service("exceptionHelper")
public class ExceptionHelper {

    public WebApplicationException createException(String errorMsg, Response.Status status)
	    throws WebApplicationException {
	return new WebApplicationException(Response.status(status).entity(errorMsg).type(MediaType.TEXT_PLAIN).build());
    }

    public WebApplicationException createException(String errorMsg, IseExceptionCodeEnum errorCode) {
	return new WebApplicationException(Response.status(errorCode.getCode()).entity(errorMsg).type(
		MediaType.TEXT_PLAIN).build());
    }
}
