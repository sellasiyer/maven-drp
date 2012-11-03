package com.bestbuy.bbym.ise.drp.sao;

import java.util.Map;

import org.w3c.dom.Document;

import com.bestbuy.bbym.ise.exception.IseBusinessException;
import com.bestbuy.bbym.ise.exception.ServiceException;

/**
 * Responsible for interaction with Titan.
 * 
 * @author a904002
 */
public interface TitanSao {

    public Document getDomDocument(String storeId, String searchDescription, String userId) throws ServiceException,
	    IseBusinessException;

    public Document getDomDocument(String httpRef, String userId) throws ServiceException, IseBusinessException;

    public Document getDomDocument(String httpRef, Document doc, String userId) throws ServiceException,
	    IseBusinessException;

    public Document getDomDocument(String httpRef, Document doc, String httpMethod, String userId,
	    Map<String, String> urlVariables) throws ServiceException, IseBusinessException;

}
