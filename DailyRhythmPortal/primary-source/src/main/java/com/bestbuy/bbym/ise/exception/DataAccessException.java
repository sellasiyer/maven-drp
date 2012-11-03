/**
 *
 */
package com.bestbuy.bbym.ise.exception;

/**
 * @author a186288
 * 
 */
public class DataAccessException extends IseSystemException {

    private static final long serialVersionUID = 1L;

    public DataAccessException(String message, Throwable cause) {
	super(IseExceptionCategoryEnum.Dao, IseExceptionCodeEnum.DataAccess, message, cause);
    }

    public DataAccessException(Throwable cause) {
	super(IseExceptionCategoryEnum.Dao, IseExceptionCodeEnum.DataAccess, null, cause);
    }

    public DataAccessException(String message) {
	super(IseExceptionCategoryEnum.Dao, IseExceptionCodeEnum.DataAccess, message);
    }

    public DataAccessException() {
	super(IseExceptionCategoryEnum.Dao, IseExceptionCodeEnum.DataAccess);
    }

    public DataAccessException(IseExceptionCodeEnum errorCode, String message, Throwable cause) {
	super(IseExceptionCategoryEnum.Dao, errorCode, message, cause);
    }

    public DataAccessException(IseExceptionCodeEnum errorCode, String message) {
	super(IseExceptionCategoryEnum.Dao, errorCode, message);
    }

    public DataAccessException(IseExceptionCodeEnum errorCode) {
	super(IseExceptionCategoryEnum.Dao, errorCode);
    }
}
