package com.bestbuy.bbym.ise.drp.sao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.bestbuy.bbym.ise.drp.domain.DrpUser;
import com.bestbuy.bbym.ise.drp.domain.ManifestLine;
import com.bestbuy.bbym.ise.drp.domain.ManifestSearchCriteria;
import com.bestbuy.bbym.ise.drp.utils.DrpConstants;
import com.bestbuy.bbym.ise.exception.IseBusinessException;
import com.bestbuy.bbym.ise.exception.IseExceptionCodeEnum;
import com.bestbuy.bbym.ise.exception.ServiceException;
import com.bestbuy.bbym.ise.iseclientacdsdevice.DeviceListType;
import com.bestbuy.bbym.ise.iseclientacdsdevice.DeviceSearchType;
import com.bestbuy.bbym.ise.iseclientacdsdevice.DeviceType;
import com.bestbuy.bbym.ise.iseclientacdsdevice.InternationalBusinessHierarchyType;
import com.bestbuy.bbym.ise.iseclientacdsdevice.ManageDeviceFault;
import com.bestbuy.bbym.ise.iseclientacdsdevice.RequestMetaDataType;
import com.bestbuy.bbym.ise.iseclientacdsdevice.SearchForDevicesRequestType;
import com.bestbuy.bbym.ise.iseclientacdsdevice.SearchForDevicesResponseType;

/**
 * Implementation for Devices Search Service which returns list of devices for a
 * search criteria
 * 
 * @author a909237
 */
@Service("searchForDeviceSao")
public class SearchForDevicesSaoImpl extends AbstractSao implements SearchForDevicesSao {

    private static Logger logger = LoggerFactory.getLogger(SearchForDevicesSaoImpl.class);
    private static final String SERVICE = "ACDS DEVICE ";

    @Override
    public List<ManifestLine> searchManifest(ManifestSearchCriteria request, DrpUser drpUser) throws ServiceException,
	    IseBusinessException {
	List<ManifestLine> itemList = null;
	SearchForDevicesResponseType deviceSearchResponse = null;
	SearchForDevicesRequestType deviceSearchRequest = prepareDeviceSearchRequest(request, drpUser);
	try{
	    deviceSearchResponse = getACDSDeviceSearchService().searchDevices(deviceSearchRequest);
	}catch(ServiceException se){
	    logger.error(DrpConstants.EXTERNAL_SERVICE_CALL_ERROR + SERVICE + " ServiceException - searchManifest : "
		    + se.getMessage());
	    throw new ServiceException(IseExceptionCodeEnum.ExternalServiceException, se.getMessage());
	}catch(ManageDeviceFault e){
	    String faultMessage = e.getFaultInfo().getFault().get(0).getMessage().getValue();
	    logger.error(DrpConstants.EXTERNAL_SERVICE_CALL_ERROR + SERVICE + "Failed to get Response from service: "
		    + faultMessage);
	    throw new IseBusinessException(IseExceptionCodeEnum.ExternalServiceException, faultMessage);
	}catch(Throwable t){
	    handleException(SERVICE + " - search manifest", t);
	}

	if (deviceSearchResponse != null)
	    itemList = getItemList(deviceSearchResponse);
	return itemList;
    }

    private List<ManifestLine> getItemList(SearchForDevicesResponseType deviceSearchResponse) {
	logger.info("Parsing the Item List from the response");
	List<ManifestLine> itemList = new ArrayList<ManifestLine>();
	DeviceListType deviceListType = new DeviceListType();
	if (deviceSearchResponse != null)
	    deviceListType = deviceSearchResponse.getDevices();
	List<DeviceType> deviceList = new ArrayList<DeviceType>();
	if (deviceListType != null)
	    deviceList = deviceListType.getDevice();
	if (!deviceList.isEmpty()){
	    for(DeviceType device: deviceList){
		ManifestLine manifestLine = new ManifestLine();
		if (StringUtils.isNotBlank(device.getIMEIESN()))
		    manifestLine.setImeiesn(device.getIMEIESN());
		if (StringUtils.isNotBlank(device.getDeviceStatus()))
		    manifestLine.setDeviceStatus(device.getDeviceStatus());
		if (StringUtils.isNotBlank(device.getItemTag()))
		    manifestLine.setItemTag(device.getItemTag());
		if (StringUtils.isNotBlank(device.getMake()))
		    manifestLine.setMake(device.getMake());
		if (StringUtils.isNotBlank(device.getModel()))
		    manifestLine.setModel(device.getModel());
		if (StringUtils.isNotBlank(device.getProductDescription()))
		    manifestLine.setProductDescription(device.getProductDescription());
		if (StringUtils.isNotBlank(device.getReturnType()))
		    manifestLine.setReturnType(device.getReturnType());
		if (device.isShortIndicator() != null)
		    manifestLine.setShort(device.isShortIndicator());
		if (StringUtils.isNotBlank(device.getSKU()))
		    manifestLine.setSku(device.getSKU());
		if (StringUtils.isNotBlank(device.getTransferNumber()))
		    manifestLine.setTransferNumber(device.getTransferNumber());
		if (device.isShrinkable() != null)
		    manifestLine.setShrinkable(device.isShrinkable());
		itemList.add(manifestLine);
	    }
	}

	if (itemList != null){
	    getShortItemsOnTop(itemList);
	}
	return itemList;
    }

    private void getShortItemsOnTop(List<ManifestLine> itemList) {
	List<ManifestLine> shortList = new ArrayList<ManifestLine>();
	List<ManifestLine> nonShortList = new ArrayList<ManifestLine>();
	for(ManifestLine line: itemList){
	    if (line.isShort()){
		shortList.add(line);
	    }else{
		nonShortList.add(line);
	    }
	}
	itemList.clear();
	itemList.addAll(shortList);
	itemList.addAll(nonShortList);
    }

    private SearchForDevicesRequestType prepareDeviceSearchRequest(ManifestSearchCriteria request, DrpUser drpUser)
	    throws ServiceException {
	SearchForDevicesRequestType deviceSearchRequest = new SearchForDevicesRequestType();
	RequestMetaDataType requestMetaData = new RequestMetaDataType();
	requestMetaData.setSourceID(DrpConstants.APPLICATION_ID);
	deviceSearchRequest.setRequestMetaData(requestMetaData);
	deviceSearchRequest
		.setInternationBusinessHierarchy(getPopulatedInternationalBusinessHierarchy(new InternationalBusinessHierarchyType()));
	DeviceSearchType deviceSearchType = new DeviceSearchType();
	logger.info("Assigning Search For Device Request: ");
	if (drpUser != null){
	    if (StringUtils.isNotBlank(drpUser.getStoreId()))
		deviceSearchType.setStoreID(new BigInteger(drpUser.getStoreId()));
	    if (StringUtils.isNotBlank(request.getManifestStatus()))
		deviceSearchType.setDeviceStatus(request.getManifestStatus());
	    else if (StringUtils.isNotBlank(request.getImeiesn()))
		deviceSearchType.setIMEIESN(request.getImeiesn());
	    else if (StringUtils.isNotBlank(request.getItemTag()))
		deviceSearchType.setItemTag(request.getItemTag());
	}
	if (deviceSearchType != null)
	    deviceSearchRequest.setSearchCriteria(deviceSearchType);
	return deviceSearchRequest;
    }

}
