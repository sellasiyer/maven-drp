package com.bestbuy.bbym.ise.drp.sao;

import java.math.BigInteger;
import java.util.List;

import com.bestbuy.bbym.ise.drp.domain.DrpUser;
import com.bestbuy.bbym.ise.drp.domain.Manifest;
import com.bestbuy.bbym.ise.drp.domain.ManifestLine;
import com.bestbuy.bbym.ise.drp.domain.ManifestSearchCriteria;
import com.bestbuy.bbym.ise.exception.IseBusinessException;
import com.bestbuy.bbym.ise.exception.ServiceException;

/**
 * Interface for ManageManifest Service SAO for Shipping Portal of ACDS.
 * 
 * @author a909237
 */
public interface ManageManifestSao {

    public ManifestLine addManifestLine(ManifestLine manifestLine, DrpUser User) throws IseBusinessException,
	    ServiceException;

    public boolean removeManifestLine(List<ManifestLine> manifestlines, DrpUser drpUser) throws ServiceException,
	    IseBusinessException;

    public List<Manifest> searchManifest(ManifestSearchCriteria request, DrpUser drpUser) throws ServiceException,
	    IseBusinessException;

    public byte[] generateManifestDoc(BigInteger manifestID, DrpUser User) throws ServiceException,
	    IseBusinessException;
}
