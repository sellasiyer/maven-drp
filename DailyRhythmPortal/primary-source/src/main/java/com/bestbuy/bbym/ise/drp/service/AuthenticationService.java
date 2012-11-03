package com.bestbuy.bbym.ise.drp.service;

import java.util.Map;

import com.bestbuy.bbym.ise.drp.domain.DrpUser;
import com.bestbuy.bbym.ise.exception.IseBusinessException;
import com.bestbuy.bbym.ise.exception.ServiceException;

public interface AuthenticationService {

    /**
     * Gets the details of a user that is already signed in using single signon.
     * 
     * @param cookieNameValueMap
     *            a {@code Map} containing the cookie names and values obtained
     *            from the request
     * @return the user; {@code null} if the user has not already been
     *         authenticated using single signon
     * @throws ServiceException
     *             thrown if the user information could not be retrieved due to
     *             a system issue
     * @throws IseBusinessException
     *             thrown if the user information could not be retrieved due to
     *             an expired token
     */
    public DrpUser getAuthenticatedUser(Map<String, String> cookieNameValueMap) throws ServiceException,
	    IseBusinessException;

    /**
     * Authenticates the user using Open SSO.
     * 
     * @param userName
     *            the user name
     * @param password
     *            the user's password
     * @return the authorized user including the application roles which the
     *         user possesses
     * @throws ServiceException
     *             thrown if the user could not be authenticated due to a system
     *             issue
     * @throws IseBusinessException
     *             thrown if the user could not be authenticated due to invalid
     *             user name and/or password, or if the user has not been
     *             assigned any ISE roles
     */
    public DrpUser authenticateUser(String userName, String password) throws ServiceException, IseBusinessException;

    /**
     * Authenticate that data sharing key is valid, less than 1 hour old and
     * associated with the given storeID and userId.
     */
    public boolean authenticateSharingKey(String dataSharingKey, String userId, String storeId, boolean isGspCancel);

}
