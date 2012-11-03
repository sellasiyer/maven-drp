package com.bestbuy.bbym.ise.exception;

/**
 * Business exception
 * 
 * @author a904002
 */
public class IseBusinessException extends IseBaseException {

    private static final long serialVersionUID = 1L;

    public IseBusinessException(IseBaseExceptionTypeEnum exceptionType, IseExceptionCategoryEnum category,
	    IseExceptionCodeEnum errorCode, String msg, Throwable cause) {
	super(exceptionType, category, errorCode, msg, cause);
    }

    public IseBusinessException(IseExceptionCategoryEnum category, IseExceptionCodeEnum errorCode, String msg,
	    Throwable cause) {
	super(category, errorCode, msg, cause);
    }

    public IseBusinessException(IseExceptionCodeEnum errorCode, String msg, Throwable cause) {
	super(errorCode, msg, cause);
    }

    public IseBusinessException(IseExceptionCodeEnum errorCode, String msg) {
	super(errorCode, msg);
    }

    public IseBusinessException(String msg, Throwable cause) {
	super(IseExceptionCodeEnum.BusinessException, msg, cause);
    }

    public IseBusinessException(String msg) {
	super(IseExceptionCodeEnum.BusinessException, msg);
    }

    public IseBusinessException() {
	super(IseExceptionCodeEnum.BusinessException);
    }

}
