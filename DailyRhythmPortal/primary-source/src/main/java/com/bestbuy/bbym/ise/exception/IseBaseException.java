package com.bestbuy.bbym.ise.exception;

/**
 * 
 * Base exception for system wide exception. Contains the information about
 * error code(from IseExceptionCodeEnum), exception type(from
 * IseBaseExceptionTypeEnum) and exception message(custom).
 * 
 * @author a904002
 */
public class IseBaseException extends Exception {

    private static final long serialVersionUID = 1L;

    private IseExceptionCodeEnum errorCode;
    private IseBaseExceptionTypeEnum exceptionType;
    private IseExceptionCategoryEnum category;

    public IseBaseException(IseBaseExceptionTypeEnum exceptionType, IseExceptionCategoryEnum category,
	    IseExceptionCodeEnum errorCode, String exceptionMessage, Throwable cause) {
	super(exceptionMessage, cause);
	setErrorCode(errorCode);
	setCategory(category);
	setExceptionType(exceptionType);
    }

    public IseBaseException(IseExceptionCategoryEnum category, IseExceptionCodeEnum errorCode, String exceptionMessage,
	    Throwable cause) {
	super(exceptionMessage, cause);
	setErrorCode(errorCode);
	setCategory(category);
    }

    public IseBaseException(IseExceptionCategoryEnum category, IseExceptionCodeEnum errorCode, String exceptionMessage) {
	super(exceptionMessage);
	setErrorCode(errorCode);
	setCategory(category);
    }

    public IseBaseException(IseExceptionCategoryEnum category, IseExceptionCodeEnum errorCode) {
	super();
	setErrorCode(errorCode);
	setCategory(category);
    }

    public IseBaseException(IseExceptionCodeEnum errorCode, String exceptionMessage, Throwable cause) {
	super(exceptionMessage, cause);
	this.errorCode = errorCode;
    }

    public IseBaseException(IseExceptionCodeEnum errorCode, String exceptionMessage) {
	super(exceptionMessage);
	this.errorCode = errorCode;
    }

    public IseBaseException(IseExceptionCodeEnum errorCode) {
	super();
	this.errorCode = errorCode;
    }

    public IseExceptionCodeEnum getErrorCode() {
	return errorCode;
    }

    public void setErrorCode(IseExceptionCodeEnum errorCode) {
	this.errorCode = errorCode;
    }

    public IseBaseExceptionTypeEnum getExceptionType() {
	return exceptionType;
    }

    public void setExceptionType(IseBaseExceptionTypeEnum exceptionType) {
	this.exceptionType = exceptionType;
    }

    public IseExceptionCategoryEnum getCategory() {
	return category;
    }

    public void setCategory(IseExceptionCategoryEnum category) {
	this.category = category;
    }

    public String getFullMessage() {
	StringBuilder sb = new StringBuilder();
	if (getErrorCode() != null && getErrorCode().getDescription() != null){
	    sb.append(getErrorCode().getDescription());
	}
	if (getMessage() != null){
	    if (sb.length() > 0){
		sb.append(": ");
	    }
	    sb.append(getMessage());
	}
	if (sb.length() == 0){
	    sb.append("Unknown");
	}
	return sb.toString();
    }

}
