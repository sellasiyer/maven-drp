/**
 *
 */
package com.bestbuy.bbym.ise.exception;

/**
 * @author a186288
 * 
 */
public class ServiceException extends IseSystemException {

    private static final long serialVersionUID = 1L;

    public ServiceException(IseExceptionCodeEnum errorCode, String message, Throwable cause) {
	super(IseExceptionCategoryEnum.Service, errorCode, message, cause);
    }

    public ServiceException(IseExceptionCodeEnum errorCode, String message) {
	super(IseExceptionCategoryEnum.Service, errorCode, message);
    }

    public ServiceException(IseExceptionCodeEnum errorCode) {
	super(IseExceptionCategoryEnum.Service, errorCode);
    }

}
