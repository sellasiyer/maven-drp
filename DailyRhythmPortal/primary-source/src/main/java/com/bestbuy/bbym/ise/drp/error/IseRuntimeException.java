package com.bestbuy.bbym.ise.drp.error;

/**
 * Must be parent class for all service runtime exceptions. The message key
 * should be provided and the key should be mapped to the front-end property
 * file.
 * 
 * @author a915722
 */
public class IseRuntimeException extends RuntimeException {

    /**
     * Constructs a new instance.
     * 
     * @param messageKey
     *            exception message key
     * @param cause
     *            the cause
     */
    public IseRuntimeException(String messageKey, Throwable cause) {
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
    public IseRuntimeException(ErrorMessageEnum errorMessageEnum, Throwable cause) {
	super(errorMessageEnum.getKey(), cause);
    }

    /**
     * Constructs a new instance.
     * 
     * @param arg0
     *            exception message key
     */
    public IseRuntimeException(String messageKey) {
	super(messageKey);
    }

}
