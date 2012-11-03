package com.bestbuy.bbym.ise.drp.sao;

import java.math.BigInteger;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.bestbuy.bbym.ise.drp.domain.DrpUser;
import com.bestbuy.bbym.ise.drp.domain.Manifest;
import com.bestbuy.bbym.ise.drp.utils.DrpConstants;
import com.bestbuy.bbym.ise.exception.IseBusinessException;
import com.bestbuy.bbym.ise.exception.IseExceptionCodeEnum;
import com.bestbuy.bbym.ise.exception.ServiceException;
import com.bestbuy.bbym.ise.iseclientacdsshipping.DimensionsType;
import com.bestbuy.bbym.ise.iseclientacdsshipping.GetLabelRequestType;
import com.bestbuy.bbym.ise.iseclientacdsshipping.GetLabelResponseType;
import com.bestbuy.bbym.ise.iseclientacdsshipping.InternationalBusinessHierarchyType;
import com.bestbuy.bbym.ise.iseclientacdsshipping.LabelImageFormatType;
import com.bestbuy.bbym.ise.iseclientacdsshipping.LabelRequestType;
import com.bestbuy.bbym.ise.iseclientacdsshipping.LabelSpecificationType;
import com.bestbuy.bbym.ise.iseclientacdsshipping.LabelStockSizeType;
import com.bestbuy.bbym.ise.iseclientacdsshipping.PackageType;
import com.bestbuy.bbym.ise.iseclientacdsshipping.ShipLocationType;
import com.bestbuy.bbym.ise.iseclientacdsshipping.ShipManifestType;
import com.bestbuy.bbym.ise.iseclientacdsshipping.ShipRequestType;
import com.bestbuy.bbym.ise.iseclientacdsshipping.ShipResponseType;
import com.bestbuy.bbym.ise.iseclientacdsshipping.ShipUnitOfMeasurementType;
import com.bestbuy.bbym.ise.iseclientacdsshipping.ShippingFault;
import com.bestbuy.bbym.ise.iseclientacdsshipping.ShippingRequestType;
import com.bestbuy.bbym.ise.iseclientacdsshipping.ShippingResponseType;
import com.bestbuy.bbym.ise.util.Util;

/**
 * SAO Implementation for the TSH Shipping Service.
 * 
 * @author a909237
 */
@Service("acdsShippingSao")
public class ACDSShippingSaoImpl extends AbstractSao implements ACDSShippingSao {

    private static Logger logger = LoggerFactory.getLogger(ACDSShippingSaoImpl.class);
    private static final String SERVICE = "ACDS SHIPPING ";

    @Override
    public Manifest getShipManifestInfo(BigInteger manifestID, DrpUser drpUser) throws ServiceException,
	    IseBusinessException {

	logger.info("Creating the RequestShipping for ACDS Shipping Service" + manifestID);

	ShippingRequestType shippingRequest = prepareShippingRequest(manifestID, drpUser);
	logger.info("Store info to close the manifest: " + drpUser.getStoreId());
	logger.info("Manifest ID to close: " + shippingRequest.getShipRequest().getShipManifest().getManifestID());
	String trackingID = "";
	byte[] labelImage = null;
	Manifest shipManifest = new Manifest();
	try{
	    logger.info("Invoking the ACDS Shipping Proxy Service");
	    ShippingResponseType shippingResponse = getACDSShippingService().shipping(shippingRequest);
	    if (shippingResponse != null){
		trackingID = getTracID(shippingResponse);
		labelImage = getLabelImage(shippingResponse);
		if (StringUtils.isNotBlank(shippingResponse.getShipInfo().getManifestStatus()))
		    shipManifest.setStatus(shippingResponse.getShipInfo().getManifestStatus());
		shipManifest.setDateCompleted(Util
			.toUtilDate(shippingResponse.getShipInfo().getShipScheduledDateTime()));
	    }
	}catch(ShippingFault e){
	    String faultMessage = e.getFaultInfo().getFault().get(0).getMessage().getValue();
	    logger.error(DrpConstants.EXTERNAL_SERVICE_CALL_ERROR + SERVICE + "Failed to get Response from service: "
		    + faultMessage);
	    throw new ServiceException(IseExceptionCodeEnum.ExternalServiceException, faultMessage);
	}catch(Throwable t){
	    handleException(SERVICE + " - get ship manifest info", t);
	}
	if (StringUtils.isNotBlank(trackingID))
	    shipManifest.setTrackingIdentifier(trackingID);
	if (labelImage != null)
	    shipManifest.setLabelImage(labelImage);
	logger.debug("ShipManifestInfo:" + shipManifest);
	return shipManifest;
    }

    private byte[] getLabelImage(ShippingResponseType shippingResponse) {
	byte[] labelImage = null;
	ShipResponseType shipResponse = new ShipResponseType();
	if (shippingResponse != null){
	    shipResponse = shippingResponse.getShipInfo();
	    labelImage = shipResponse.getLabelImage();
	}
	return labelImage;
    }

    private String getTracID(ShippingResponseType shippingResponse) {
	String trackingID = new String();
	if (shippingResponse != null){
	    ShipResponseType shipResponse = shippingResponse.getShipInfo();
	    if (StringUtils.isNotBlank(shipResponse.getTrackingID()))
		trackingID = shipResponse.getTrackingID();
	}
	return trackingID;
    }

    private ShippingRequestType prepareShippingRequest(BigInteger manifestID, DrpUser drpUser) throws ServiceException {
	ShippingRequestType shippingRequest = new ShippingRequestType();
	ShipRequestType requestType = new ShipRequestType();
	ShipManifestType manifestType = new ShipManifestType();
	ShipLocationType shipLocation = new ShipLocationType();
	if (manifestID != null)
	    manifestType.setManifestID(manifestID);
	if (manifestType != null)
	    requestType.setShipManifest(manifestType);
	if (drpUser != null){
	    shipLocation.setStoreID(new BigInteger(drpUser.getStoreId()));
	}
	if (shipLocation != null)
	    requestType.setShipFromLocation(shipLocation);

	// Hard coding the Package Details
	PackageType shipPackage = new PackageType();
	DimensionsType dimensions = new DimensionsType();
	dimensions.setHeight("16");
	dimensions.setLength("12");
	dimensions.setWidth("18");
	ShipUnitOfMeasurementType shipUnit = new ShipUnitOfMeasurementType();
	shipUnit.setCode("IN");
	dimensions.setUnitOfMeasurement(shipUnit);
	shipPackage.setDimensions(dimensions);

	shipUnit = new ShipUnitOfMeasurementType();

	LabelSpecificationType labelSpec = new LabelSpecificationType();
	LabelImageFormatType labelFormat = new LabelImageFormatType();
	labelFormat.setCode("GIF");
	labelSpec.setLabelImageFormat(labelFormat);
	LabelStockSizeType labelStock = new LabelStockSizeType();
	labelStock.setHeight("2");
	labelStock.setWidth("3");
	labelSpec.setLabelStockSize(labelStock);
	shipPackage.setLabelSpecification(labelSpec);

	requestType.setPackage(shipPackage);
	shippingRequest.setShipRequest(requestType);
	shippingRequest
		.setInternationBusinessHierarchy(getPopulatedInternationalBusinessHierarchy(new InternationalBusinessHierarchyType()));

	return shippingRequest;
    }

    @Override
    public byte[] getShippingImage(BigInteger manifestID) throws ServiceException, IseBusinessException {
	GetLabelResponseType labelResponse = null;
	logger.info("Creating the LabelRequest for ACDS Shipping Service" + manifestID);
	GetLabelRequestType labelRequest = prepareLabelRequest(manifestID);
	if (labelRequest != null){
	    try{
		labelResponse = getACDSShippingService().getShippingLabel(labelRequest);
	    }catch(ShippingFault e){
		String faultMessage = e.getFaultInfo().getFault().get(0).getMessage().getValue();
		logger.error(DrpConstants.EXTERNAL_SERVICE_CALL_ERROR + SERVICE
			+ "Failed to get Response from service: " + faultMessage);
		throw new ServiceException(IseExceptionCodeEnum.ExternalServiceException, faultMessage);
	    }catch(Throwable t){
		handleException(SERVICE + " - get shipping image", t);
	    }

	}
	byte[] labelImage = null;
	if (labelResponse != null){
	    labelImage = labelResponse.getLabel().getLabelImage();
	}
	return labelImage;
    }

    private GetLabelRequestType prepareLabelRequest(BigInteger manifestId) throws ServiceException {
	GetLabelRequestType labelRequest = new GetLabelRequestType();
	labelRequest
		.setInternationBusinessHierarchy(getPopulatedInternationalBusinessHierarchy(new InternationalBusinessHierarchyType()));
	LabelRequestType labelType = new LabelRequestType();
	if (manifestId != null){
	    ShipManifestType manifestType = new ShipManifestType();
	    manifestType.setManifestID(manifestId);
	    labelType.setShipManifest(manifestType);
	    labelRequest.setLabelRequest(labelType);
	}
	return labelRequest;
    }

}
