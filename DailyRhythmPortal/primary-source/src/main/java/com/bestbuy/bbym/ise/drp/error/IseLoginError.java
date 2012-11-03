package com.bestbuy.bbym.ise.drp.error;

/**
 * User authorization error or user doesn't have access to resources.
 * 
 * @author a915722
 *
 */
public class IseLoginError extends IseServiceError {

    /**
     * Constructs a new instance.
     * 
     * @param arg0 exception message key.
     */
    public IseLoginError(String arg0) {
	super(arg0);
    }

    /**
     * Constructs a new instance.
     * 
     * @param arg0 error message.
     */
    public IseLoginError(ErrorMessageEnum error) {
	super(error.getKey());
    }

}
