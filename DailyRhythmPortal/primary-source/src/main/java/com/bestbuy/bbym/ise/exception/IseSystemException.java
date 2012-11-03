package com.bestbuy.bbym.ise.exception;

/**
 * 
 * @author a904002
 */
public class IseSystemException extends IseBaseException {

    private static final long serialVersionUID = 1L;

    public IseSystemException(IseBaseExceptionTypeEnum exceptionType, IseExceptionCategoryEnum category,
	    IseExceptionCodeEnum errorCode, String msg, Throwable cause) {
	super(exceptionType, category, errorCode, msg, cause);
    }

    public IseSystemException(IseExceptionCategoryEnum category, IseExceptionCodeEnum errorCode, String msg,
	    Throwable cause) {
	super(category, errorCode, msg, cause);
    }

    public IseSystemException(IseExceptionCategoryEnum category, IseExceptionCodeEnum errorCode, String msg) {
	super(category, errorCode, msg);
    }

    public IseSystemException(IseExceptionCategoryEnum category, IseExceptionCodeEnum errorCode) {
	super(category, errorCode);
    }

    public IseSystemException(IseExceptionCodeEnum errorCode, String msg, Throwable cause) {
	super(errorCode, msg, cause);
    }

    public IseSystemException(IseExceptionCodeEnum errorCode, String msg) {
	super(errorCode, msg);
    }

}
