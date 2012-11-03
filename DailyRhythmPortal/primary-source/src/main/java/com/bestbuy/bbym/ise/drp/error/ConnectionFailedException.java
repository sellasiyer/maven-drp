package com.bestbuy.bbym.ise.drp.error;

/**
 * Use to display all kind of connection exceptions: LADP, JNDI, JDBC, etc.
 * 
 * @author a915722
 *
 */
public class ConnectionFailedException extends IseRuntimeException {

    /**
     * Constructs a new instance.
     * 
     * @param arg0 exception message key.
     * @param arg1 Exception.
     */
    public ConnectionFailedException(String arg0, Throwable arg1) {
	super(arg0, arg1);
    }

    /**
     * Constructs a new instance.
     * 
     * @param errorMessageEnum error message.
     * @param arg1 exception
     */
    public ConnectionFailedException(ErrorMessageEnum errorMessageEnum, Throwable arg1) {
	super(errorMessageEnum.getKey(), arg1);
    }

    /**
     * Constructs a new instance.
     * 
     * @param arg0 exception message key.
     */
    public ConnectionFailedException(String arg0) {
	super(arg0);
    }

}
