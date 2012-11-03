package com.bestbuy.bbym.ise.drp.sao;

import java.util.Iterator;
import java.util.List;

import org.oasis_open.docs.wss._2004._01.oasis_200401_wss_wssecurity_secext_1_0.SecurityType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.bestbuy.bbym.ise.domain.Store;
import com.bestbuy.bbym.ise.drp.domain.scoreboard.ScoreboardStoreType;
import com.bestbuy.bbym.ise.exception.IseBusinessException;
import com.bestbuy.bbym.ise.exception.IseExceptionCodeEnum;
import com.bestbuy.bbym.ise.exception.ServiceException;
import com.bestbuy.tsh.common.communication.v1.PhoneBaseType;
import com.bestbuy.tsh.common.datatype.v1.TextType;
import com.bestbuy.tsh.common.metadata.components.v2.RequestMetaDataType;
import com.bestbuy.tsh.facilities.location.retrievelocationsservice.v2.RetrieveLocationsFault;
import com.bestbuy.tsh.facilities.locations.fields.v2.LocationHierarchyType;
import com.bestbuy.tsh.facilities.locations.fields.v2.LocationIDListType;
import com.bestbuy.tsh.facilities.locations.fields.v2.LocationListType;
import com.bestbuy.tsh.facilities.locations.fields.v2.LocationType;
import com.bestbuy.tsh.facilities.locations.fields.v2.RetrieveLocationsRequestType;
import com.bestbuy.tsh.facilities.locations.fields.v2.RetrieveLocationsResponseType;
import com.bestbuy.tsh.tsh.tshfault.FaultType;

/**
 * StoreInformationSaoImpl
 * 
 * @author a194869
 */
@Service("storeInformationSao")
public class StoreInformationSaoImpl extends AbstractSao implements StoreInformationSao {

    private static final String SERVICE = "TSH_LOCATION";
    private static final String VERSION = "TSH_LOCATION_WS_VERSION";
    private static final String SAS_INDICATOR = "Y";
    private static final String SUCCESS_CODE = "PXY-TSH-000000";
    private static final String DISTRICT = "District";
    private static final String REGION = "Region";
    private static Logger logger = LoggerFactory.getLogger(StoreInformationSaoImpl.class);

    public Store getStoreDetails(String storeId) throws ServiceException, IseBusinessException {
	logger.info("getStoreDetails method - Begin");
	try{
	    RetrieveLocationsRequestType retreiveLocationsRequest = new RetrieveLocationsRequestType();
	    LocationIDListType storeIdList = new LocationIDListType();
	    storeIdList.getLocationID().add(Long.valueOf(storeId));
	    retreiveLocationsRequest.setLocationIDList(storeIdList);
	    RequestMetaDataType metaData = new RequestMetaDataType();
	    metaData.setSourceID(getDrpPropertiesService().getProperty("APPLICATION_NAME"));
	    metaData.setVersion(Integer.valueOf(drpPropertiesService.getProperty(VERSION, "2")));
	    retreiveLocationsRequest.setMetaData(metaData);
	    SecurityType security = new SecurityType();
	    TextType text = new TextType();
	    text.setValue("");
	    security.setBase64Assertion(text);
	    RetrieveLocationsResponseType locationResponse = getRetrieveLocationService().retrieveLocations(security,
		    retreiveLocationsRequest);
	    if (locationResponse != null && locationResponse.getStatus() != null){
		String statusCode = locationResponse.getStatus().getCode().getValue();
		String statusMessage = locationResponse.getStatus().getDescription();
		logger.info("Location Service Response  : Status Code : " + statusCode + " and Status Description : "
			+ statusMessage);

		if (SUCCESS_CODE.equalsIgnoreCase(statusCode)){
		    LocationListType response = locationResponse.getLocations();
		    if (response != null){
			Store store = new Store();
			store.setId(storeId);
			List<LocationType> locationList = response.getLocation();
			for(LocationType location: locationList){
			    if (location.getHeader() != null){
				logger.info(" Location Name = " + location.getHeader().getLocationName()
					+ " Location Id = " + location.getHeader().getLocationID()
					+ " Mobile SAS Indicator = " + location.getHeader().getMobileSASIndicator());
				store.setStoreName(location.getHeader().getLocationName());
				store
					.setStoreType((SAS_INDICATOR.equals(location.getHeader()
						.getMobileSASIndicator()))?ScoreboardStoreType.SAS
						:ScoreboardStoreType.SWAS);
			    }
			    if (location.getCommunication() != null && location.getCommunication().getPhone() != null){
				for(PhoneBaseType phone: location.getCommunication().getPhone()){
				    store.setStorePhoneNumber(phone.getAreaDialing().getValue()
					    + phone.getDialNumber().getValue());
				    break;
				}
			    }
			    for(LocationHierarchyType hierarchy: location.getHeader().getLocationHierarchy()){
				if (hierarchy != null && hierarchy.getType() != null){
				    if (DISTRICT.equals(hierarchy.getType())){
					store
						.setDistrict(hierarchy.getName() != null?hierarchy.getName().getValue()
							:"");
				    }else if (REGION.equals(hierarchy.getType())){
					store.setRegion(hierarchy.getName() != null?hierarchy.getName().getValue():"");
				    }
				}
			    }
			}
			return store;
		    }
		}else{
		    logger.info("TSH_LOCATION : No results returned because " + statusMessage);
		    throw new ServiceException(IseExceptionCodeEnum.ExternalServiceException, statusMessage);
		}
	    }

	}catch(RetrieveLocationsFault e){
	    StringBuffer sb = new StringBuffer();
	    Iterator<FaultType> faultTypeIter = e.getFaultInfo().getFault().iterator();
	    while(faultTypeIter.hasNext()){
		FaultType faultType = faultTypeIter.next();
		sb.append(faultType.getCode().getValue()).append(' ');
		sb.append(faultType.getMessage().getValue()).append(' ');
		sb.append(faultType.getDetail().getValue());
		if (faultTypeIter.hasNext()){
		    sb.append('\n');
		}
	    }
	    logger.error("Retrieve location service exception - getStoreDetails: " + sb);
	    throw new ServiceException(IseExceptionCodeEnum.ExternalServiceException, sb.toString());
	}catch(Throwable t){
	    handleException(SERVICE + " - getStoreDetails ", t);
	}finally{
	    logger.info("getStoreDetails method - End");
	}

	return null;
    }
}
