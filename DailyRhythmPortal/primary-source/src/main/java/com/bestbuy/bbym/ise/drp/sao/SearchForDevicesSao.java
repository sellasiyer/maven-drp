package com.bestbuy.bbym.ise.drp.sao;

import java.util.List;

import com.bestbuy.bbym.ise.drp.domain.DrpUser;
import com.bestbuy.bbym.ise.drp.domain.ManifestLine;
import com.bestbuy.bbym.ise.drp.domain.ManifestSearchCriteria;
import com.bestbuy.bbym.ise.exception.IseBusinessException;
import com.bestbuy.bbym.ise.exception.ServiceException;

/**
 * SAO Interface for Search For Devices (Inventory Management) Service
 * 
 * @author a909237
 */
public interface SearchForDevicesSao {

    public List<ManifestLine> searchManifest(ManifestSearchCriteria request, DrpUser drpUser) throws ServiceException,
	    IseBusinessException;
}
