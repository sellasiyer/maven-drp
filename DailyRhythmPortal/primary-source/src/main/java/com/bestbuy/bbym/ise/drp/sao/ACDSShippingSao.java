package com.bestbuy.bbym.ise.drp.sao;

import java.math.BigInteger;

import com.bestbuy.bbym.ise.drp.domain.DrpUser;
import com.bestbuy.bbym.ise.drp.domain.Manifest;
import com.bestbuy.bbym.ise.exception.IseBusinessException;
import com.bestbuy.bbym.ise.exception.ServiceException;

/**
 * Interface for ACDS Shipping SAO.
 * 
 * @author a909237
 */
public interface ACDSShippingSao {

    public Manifest getShipManifestInfo(BigInteger manifestID, DrpUser drpUser) throws ServiceException,
	    IseBusinessException;

    public byte[] getShippingImage(BigInteger manifestID) throws ServiceException, IseBusinessException;

}
