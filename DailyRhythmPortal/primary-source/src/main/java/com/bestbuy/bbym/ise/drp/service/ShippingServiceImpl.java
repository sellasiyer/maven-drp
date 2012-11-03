package com.bestbuy.bbym.ise.drp.service;

import java.math.BigInteger;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bestbuy.bbym.ise.drp.domain.ACDSParameters;
import com.bestbuy.bbym.ise.drp.domain.DrpUser;
import com.bestbuy.bbym.ise.drp.domain.Manifest;
import com.bestbuy.bbym.ise.drp.domain.ManifestLine;
import com.bestbuy.bbym.ise.drp.domain.ManifestSearchCriteria;
import com.bestbuy.bbym.ise.drp.sao.ACDSParamSao;
import com.bestbuy.bbym.ise.drp.sao.ACDSShippingSao;
import com.bestbuy.bbym.ise.drp.sao.ManageManifestSao;
import com.bestbuy.bbym.ise.drp.sao.SearchForDevicesSao;
import com.bestbuy.bbym.ise.drp.sao.UpdateACDSDeviceStatusSaoImpl;
import com.bestbuy.bbym.ise.exception.IseBusinessException;
import com.bestbuy.bbym.ise.exception.ServiceException;

@Service("shippingService")
public class ShippingServiceImpl implements ShippingService {

    @Autowired
    private ACDSParamSao acdsParamSao;
    @Autowired
    private ACDSShippingSao acdsShippingSao;
    @Autowired
    private ManageManifestSao manageManifestSao;
    @Autowired
    private SearchForDevicesSao searchForDevicesSao;
    @Autowired
    private UpdateACDSDeviceStatusSaoImpl updateDeviceStatusSao;

    @Override
    public List<ACDSParameters> getACDSParameters(String groupName) throws ServiceException, IseBusinessException {
	return acdsParamSao.getACDSParameters(groupName);
    }

    @Override
    public Manifest getShipManifestInfo(BigInteger manifestID, DrpUser drpUser) throws ServiceException,
	    IseBusinessException {
	return acdsShippingSao.getShipManifestInfo(manifestID, drpUser);
    }

    @Override
    public byte[] getShippingImage(BigInteger manifestID) throws ServiceException, IseBusinessException {
	return acdsShippingSao.getShippingImage(manifestID);
    }

    @Override
    public ManifestLine addManifestLine(ManifestLine manifestLine, DrpUser user) throws ServiceException,
	    IseBusinessException {
	return manageManifestSao.addManifestLine(manifestLine, user);
    }

    @Override
    public boolean removeManifestLine(List<ManifestLine> manifestlines, DrpUser drpUser) throws ServiceException,
	    IseBusinessException {
	return manageManifestSao.removeManifestLine(manifestlines, drpUser);
    }

    @Override
    public List<Manifest> searchManifests(ManifestSearchCriteria manifestSearchCriteria, DrpUser drpUser)
	    throws ServiceException, IseBusinessException {
	return manageManifestSao.searchManifest(manifestSearchCriteria, drpUser);
    }

    @Override
    public byte[] generateManifestDoc(BigInteger manifestID, DrpUser user) throws ServiceException,
	    IseBusinessException {
	return manageManifestSao.generateManifestDoc(manifestID, user);
    }

    @Override
    public List<ManifestLine> searchManifest(ManifestSearchCriteria request, DrpUser drpUser) throws ServiceException,
	    IseBusinessException {
	return searchForDevicesSao.searchManifest(request, drpUser);
    }

    public void setACDSParamSao(ACDSParamSao acdsParamSao) {
	this.acdsParamSao = acdsParamSao;
    }

    public void setACDSShippingSao(ACDSShippingSao acdsShippingSao) {
	this.acdsShippingSao = acdsShippingSao;
    }

    public void setSearchForDevicesSao(SearchForDevicesSao searchForDevicesSao) {
	this.searchForDevicesSao = searchForDevicesSao;
    }

    public void setManageManifestSao(ManageManifestSao mockSao) {
	this.manageManifestSao = mockSao;
    }

    @Override
    public void shrinkDevice(String itemTag, String returnStatus, String disposition) throws ServiceException,
	    IseBusinessException {
	updateDeviceStatusSao.updateDeviceStatus(itemTag, returnStatus, disposition);
    }
}
