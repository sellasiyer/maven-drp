package com.bestbuy.bbym.ise.drp.service;

import java.math.BigInteger;
import java.util.List;

import com.bestbuy.bbym.ise.drp.domain.ACDSParameters;
import com.bestbuy.bbym.ise.drp.domain.DrpUser;
import com.bestbuy.bbym.ise.drp.domain.Manifest;
import com.bestbuy.bbym.ise.drp.domain.ManifestLine;
import com.bestbuy.bbym.ise.drp.domain.ManifestSearchCriteria;
import com.bestbuy.bbym.ise.exception.IseBusinessException;
import com.bestbuy.bbym.ise.exception.ServiceException;

/**
 * Shipping service.
 */
public interface ShippingService {

    public List<ACDSParameters> getACDSParameters(String groupName) throws ServiceException, IseBusinessException;

    public Manifest getShipManifestInfo(BigInteger manifestID, DrpUser drpUser) throws ServiceException,
	    IseBusinessException;

    public byte[] getShippingImage(BigInteger manifestID) throws ServiceException, IseBusinessException;

    public ManifestLine addManifestLine(ManifestLine manifestLine, DrpUser user) throws ServiceException,
	    IseBusinessException;

    public boolean removeManifestLine(List<ManifestLine> manifestlines, DrpUser drpUser) throws ServiceException,
	    IseBusinessException;

    public List<Manifest> searchManifests(ManifestSearchCriteria manifestSearchCriteria, DrpUser drpUser)
	    throws ServiceException, IseBusinessException;

    public byte[] generateManifestDoc(BigInteger manifestID, DrpUser user) throws ServiceException,
	    IseBusinessException;

    public List<ManifestLine> searchManifest(ManifestSearchCriteria request, DrpUser drpUser) throws ServiceException,
	    IseBusinessException;

    public void shrinkDevice(String itemTag, String returnStatus, String disposition) throws ServiceException,
	    IseBusinessException;
}
