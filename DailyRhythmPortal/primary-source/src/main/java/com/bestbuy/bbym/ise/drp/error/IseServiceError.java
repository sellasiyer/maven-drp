package com.bestbuy.bbym.ise.drp.error;

/**
 * Must be parent class for all service checked exceptions. The message key
 * should be provided during instance initiation and the key should be mapped to
 * the front-end property file.
 * 
 * It should not be instantiated directly as there should no be the situation
 * that unknown exception is checked exception.
 * 
 * @author a915722
 */
public abstract class IseServiceError extends Exception {

    /**
     * Constructs a new instance.
     * 
     * @param messageKey
     *            exception message key
     * @param cause
     *            the cause
     */
    public IseServiceError(String messageKey, Throwable cause) {
	super(messageKey, cause);
    }

    /**
     * Constructs a new instance.
     * 
     * @param errorMessageEnum
     *            error message
     * @param cause
     *            the cause
     */
    public IseServiceError(ErrorMessageEnum errorMessageEnum, Throwable cause) {
	super(errorMessageEnum.getKey(), cause);
    }

    /**
     * Constructs a new instance.
     * 
     * @param messageKey
     *            exception message key
     */
    public IseServiceError(String messageKey) {
	super(messageKey);
    }

}
