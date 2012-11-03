package com.bestbuy.bbym.ise.drp.sao;

import com.bestbuy.bbym.ise.drp.domain.DrpUser;
import com.bestbuy.bbym.ise.exception.IseBusinessException;
import com.bestbuy.bbym.ise.exception.ServiceException;

/**
 * Service access object responsible for authentification and authorization.
 */
public interface OpenSSOSao {

    public static final String COOKIE_NAME_OBLIX = "ObSSOCookie";
    public static final String COOKIE_NAME_OPENSSO = "iPlanetDirectoryPro";

    /**
     * Authenticates a user.
     * 
     * @param userName
     *            the user name
     * @param password
     *            the user's password
     * @return the user
     * @throws ServiceException
     *             thrown if the user could not be authenticated due to a system
     *             issue
     * @throws IseBusinessException
     *             thrown if the user could not be authenticated due to invalid
     *             user name and/or password
     */
    public DrpUser authenticate(String userName, String password) throws ServiceException, IseBusinessException;

    /**
     * Gets the user's information using either the Oblix token or the the
     * OpenSSO token.
     * 
     * @throws ServiceException
     *             thrown if the user information could not be retrieved due to
     *             a system issue
     * @throws IseBusinessException
     *             thrown if the user information could not be retrieved due to
     *             an expired token
     */
    public DrpUser attributes(String oblixToken, String openSsoToken) throws ServiceException, IseBusinessException;

}
