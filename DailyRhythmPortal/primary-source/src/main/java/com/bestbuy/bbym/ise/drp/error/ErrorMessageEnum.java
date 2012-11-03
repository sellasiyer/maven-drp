package com.bestbuy.bbym.ise.drp.error;

/**
 * Contains list of keys that represents error messages
 * and must be mapped to the properties on front-end side. 
 * 
 * @author a915722
 *
 */
public enum ErrorMessageEnum {
    /**
     * Has not been assigned any application roles.
     */
    HAS_NOT_BEEN_ASSIGNED_APP_ROLES("has.not.been.assigned.any.application.roles"),
    /**
     * Service connection failed.
     */
    SERVICE_NO_AVAILABLE("service.not.available"),
    /**
     * username and password exist, but user doesn't have access
     * to resources.
     */
    ACCESS_DENIED("access.denied"),
    /**
     * Fail to recognize username and password.
     */
    BAD_USER_CREDENTIALS("bad.user.credentials"),

    /**
     * Token Expired
     */
    TOKEN_EXPIRED("token.expired"),
    /**
     * Zero records return.
     */
    NO_DATA_FOUND("no.data.found"),
    /**
     * Unknown exception.
     */
    UNKNOWN_ERROR("unknown.error");

    private final String key;

    /**
     * Returns message key.
     * 
     * @return message key.
     */
    public String getKey() {
	return key;
    }

    /**
     * Constructs a new instance.
     * 
     * @param key
     */
    private ErrorMessageEnum(final String key) {
	this.key = key;
    }
}
