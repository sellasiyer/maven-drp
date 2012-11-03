package com.bestbuy.bbym.ise.drp.sao;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.bestbuy.bbym.ise.drp.utils.DrpConstants;
import com.bestbuy.bbym.ise.exception.IseBusinessException;
import com.bestbuy.bbym.ise.exception.IseExceptionCodeEnum;
import com.bestbuy.bbym.ise.exception.ServiceException;
import com.bestbuy.bbym.ise.util.Util;
import com.bestbuy.bbym.logistics.device.status.component.v1.ReturnStatusUpdateMessageType;
import com.bestbuy.bbym.logistics.device.status.component.v1.ReturnStatusUpdateType;
import com.bestbuy.bbym.logistics.device.status.component.v1.StatusType;
import com.bestbuy.bbym.logistics.device.status.component.v1.StatusUpdateType;
import com.bestbuy.bbym.logistics.device.status.service.v1.UpdateDeviceStatusRequestType;
import com.bestbuy.bbym.logistics.device.status.service.v1.UpdateDeviceStatusResponseType;
import com.bestbuy.tsh.common.datatype.v1.IdentifierType;
import com.bestbuy.tsh.common.metadata.components.v2.InternationalBusinessHierarchyType;
import com.bestbuy.tsh.common.metadata.components.v2.RequestMetaDataType;

/**
 * Implementation for update device Service which shrinks device
 * 
 * @author a194869
 */
@Service("updateDeviceStatusSao")
public class UpdateACDSDeviceStatusSaoImpl extends AbstractSao implements UpdateACDSDeviceStatusSao {

    private static Logger logger = LoggerFactory.getLogger(UpdateACDSDeviceStatusSaoImpl.class);
    private static final String SERVICE = "ACDS UPDATE DEVICE STATUS";
    private static final String SUCCESS_CODE = "000000";

    @Override
    public void updateDeviceStatus(String itemTag, String returnStatus, String disposition) throws ServiceException,
	    IseBusinessException {
	if (itemTag == null || returnStatus == null || disposition == null){
	    throw new IseBusinessException(IseExceptionCodeEnum.BADREQUEST,
		    "Missing mandatory fields in ACDS update device status service request.");
	}
	UpdateDeviceStatusRequestType updateDeviceRequest = new UpdateDeviceStatusRequestType();
	RequestMetaDataType requestMetaData = new RequestMetaDataType();
	requestMetaData.setSourceID(DrpConstants.APPLICATION_ID);
	updateDeviceRequest.setRequestMetaData(requestMetaData);
	updateDeviceRequest
		.setInternationalBusinessHierarchy(getPopulatedInternationalBusinessHierarchy(new InternationalBusinessHierarchyType()));
	ReturnStatusUpdateMessageType returnStatusUpdateMessage = new ReturnStatusUpdateMessageType();
	ReturnStatusUpdateType returnStatusUpdate = new ReturnStatusUpdateType();
	IdentifierType identifier = new IdentifierType();
	identifier.setValue(itemTag);
	returnStatusUpdate.setReturnID(identifier);
	StatusUpdateType statusUpdate = new StatusUpdateType();
	statusUpdate.setUpdateTimeStamp(Util.toXmlGregorianCalendar(new Date()));
	StatusType status = new StatusType();
	status.setDisposition(disposition);
	status.setReturnStatus(returnStatus);
	statusUpdate.setStatus(status);
	returnStatusUpdate.getStatusUpdate().add(statusUpdate);
	returnStatusUpdateMessage.getReturnStatusUpdate().add(returnStatusUpdate);
	updateDeviceRequest.setReturnStatusUpdateMessage(returnStatusUpdateMessage);

	try{
	    UpdateDeviceStatusResponseType response = getACDSUpdateDeviceService().updateDeviceStatus(
		    updateDeviceRequest);
	    if (response == null){
		throw new ServiceException(IseExceptionCodeEnum.ExternalServiceException,
			"Response not Received from Shipping service for update device status");
	    }else{
		if (response.getStatus() != null && response.getStatus().getReasonCode() != null
			&& StringUtils.isNotBlank(response.getStatus().getReasonCode().getValue())){
		    if (!response.getStatus().getReasonCode().getValue().endsWith(SUCCESS_CODE)){
			throw new ServiceException(IseExceptionCodeEnum.ExternalServiceException, response.getStatus()
				.getDescription());
		    }else{
			throw new ServiceException(IseExceptionCodeEnum.ExternalServiceException,
				"Invalid response received from Shipping service for update device status");
		    }
		}
	    }
	}catch(ServiceException se){
	    logger.error(DrpConstants.EXTERNAL_SERVICE_CALL_ERROR + SERVICE
		    + " ServiceException - updateDeviceStatus : " + se.getMessage());
	    throw new ServiceException(IseExceptionCodeEnum.ExternalServiceException, se.getMessage());
	}catch(Exception se){
	    logger.error(DrpConstants.EXTERNAL_SERVICE_CALL_ERROR + SERVICE + " Exception - updateDeviceStatus : "
		    + se.getMessage());
	    throw new ServiceException(IseExceptionCodeEnum.ExternalServiceException, se.getMessage());
	}catch(Throwable t){
	    handleException(SERVICE + " - search manifest", t);
	}

    }
}
