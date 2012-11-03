package com.bestbuy.bbym.ise.drp.sao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.bestbuy.bbym.ise.drp.domain.DrpUser;
import com.bestbuy.bbym.ise.drp.domain.Manifest;
import com.bestbuy.bbym.ise.drp.domain.ManifestLine;
import com.bestbuy.bbym.ise.drp.domain.ManifestSearchCriteria;
import com.bestbuy.bbym.ise.drp.utils.DrpConstants;
import com.bestbuy.bbym.ise.exception.IseBusinessException;
import com.bestbuy.bbym.ise.exception.IseExceptionCodeEnum;
import com.bestbuy.bbym.ise.exception.ServiceException;
import com.bestbuy.bbym.ise.iseclientacds.AddToManifestRequestType;
import com.bestbuy.bbym.ise.iseclientacds.AddToManifestResponseType;
import com.bestbuy.bbym.ise.iseclientacds.DateRange;
import com.bestbuy.bbym.ise.iseclientacds.GenerateManifestDocRequestType;
import com.bestbuy.bbym.ise.iseclientacds.GenerateManifestDocumentRequestType;
import com.bestbuy.bbym.ise.iseclientacds.GenerateManifestDocumentResponseType;
import com.bestbuy.bbym.ise.iseclientacds.InternationalBusinessHierarchyType;
import com.bestbuy.bbym.ise.iseclientacds.ManageManifestFault;
import com.bestbuy.bbym.ise.iseclientacds.ManifestAddRequestType;
import com.bestbuy.bbym.ise.iseclientacds.ManifestHeaderType;
import com.bestbuy.bbym.ise.iseclientacds.ManifestLineItemType;
import com.bestbuy.bbym.ise.iseclientacds.ManifestRemoveLineType;
import com.bestbuy.bbym.ise.iseclientacds.ManifestRemoveType;
import com.bestbuy.bbym.ise.iseclientacds.ManifestSearchType;
import com.bestbuy.bbym.ise.iseclientacds.RemoveFromManifestRequestType;
import com.bestbuy.bbym.ise.iseclientacds.RemoveFromManifestResponseType;
import com.bestbuy.bbym.ise.iseclientacds.SearchForManifestsRequestType;
import com.bestbuy.bbym.ise.iseclientacds.SearchForManifestsResponseType;
import com.bestbuy.bbym.ise.util.Util;

/**
 * SAO Implementation for ManageManifest Service Business
 * 
 * @author a909237
 */
@Service("manageManifestSao")
public class ManageManifestSaoImpl extends AbstractSao implements ManageManifestSao {

    private static Logger logger = LoggerFactory.getLogger(ManageManifestSaoImpl.class);
    private static final String SERVICE = "ACDS MANIFEST ";

    @Override
    /**
     * Generates the Manifest Document for the shipped Manifest
     * Input: ManifestID
     */
    public byte[] generateManifestDoc(BigInteger manifestID, DrpUser User) throws ServiceException,
	    IseBusinessException {
	GenerateManifestDocumentResponseType manifestDocResponse = null;
	GenerateManifestDocumentRequestType manifestDocRequest = new GenerateManifestDocumentRequestType();
	manifestDocRequest = prepareManifestDoc(manifestID, User);
	try{
	    logger.info("Calling the Manage Manifest Service for Manifest Doc");
	    manifestDocResponse = getManageManifestService().generateManifestDoc(manifestDocRequest);
	}catch(ManageManifestFault e){
	    String faultMessage = e.getFaultInfo().getFault().get(0).getMessage().getValue();
	    logger.error(DrpConstants.EXTERNAL_SERVICE_CALL_ERROR + SERVICE + "Failed to get Response from service: "
		    + faultMessage);
	    throw new IseBusinessException(IseExceptionCodeEnum.ExternalServiceException, faultMessage);
	}catch(ServiceException se){
	    logger.error(DrpConstants.EXTERNAL_SERVICE_CALL_ERROR + SERVICE + "Error in Manifest Service call:", se);
	    throw new ServiceException(IseExceptionCodeEnum.ExternalServiceException, se.getMessage());
	}catch(Throwable t){
	    handleException(SERVICE + " - generate manifest", t);
	}
	byte[] manDoc = null;
	if (manifestDocResponse.getGenerateManifestDocResponse().getManifestDoc() != null){
	    manDoc = manifestDocResponse.getGenerateManifestDocResponse().getManifestDoc();
	}
	return manDoc;
    }

    private GenerateManifestDocumentRequestType prepareManifestDoc(BigInteger manifestID, DrpUser User)
	    throws ServiceException {
	GenerateManifestDocumentRequestType manifestDocRequest = new GenerateManifestDocumentRequestType();
	manifestDocRequest
		.setInternationBusinessHierarchy(getPopulatedInternationalBusinessHierarchy(new InternationalBusinessHierarchyType()));
	GenerateManifestDocRequestType docRequest = new GenerateManifestDocRequestType();
	logger.info("Assigning the Manifest Document Request: ");
	if (manifestID != null)
	    docRequest.setManifestID(manifestID + "");
	if (User != null){
	    docRequest.setStoreID(User.getStoreId());
	}
	if (docRequest != null)
	    manifestDocRequest.setGenerateManifestDocRequest(docRequest);

	return manifestDocRequest;
    }

    @Override
    /**
     * Invokes the service port to perform AddManifestLine operation and returns the ManifestLine
     */
    public ManifestLine addManifestLine(ManifestLine manifestLine, DrpUser drpUser) throws IseBusinessException,
	    ServiceException {

	logger.info("Assigning AddToManifestRequestType: ");
	AddToManifestRequestType addRequest = new AddToManifestRequestType();
	try{
	    logger.info("Invoking the ManageManifest Service of ACDS");
	    // Separate method for getting response and checking the codes
	    // of the response
	    addRequest = prepareManifestData(manifestLine, drpUser);
	    AddToManifestResponseType addResponse = getManageManifestService().addManifestLine(addRequest);
	    manifestLine = getManifestLine(addResponse);
	    logger.info(manifestLine.getItemTag());
	}catch(ManageManifestFault e){
	    String faultMessage = e.getFaultInfo().getFault().get(0).getMessage().getValue();
	    logger.error(DrpConstants.EXTERNAL_SERVICE_CALL_ERROR + SERVICE + "Failed to get Response from service: "
		    + faultMessage);
	    throw new IseBusinessException(IseExceptionCodeEnum.ExternalServiceException, faultMessage);
	}catch(Throwable t){
	    handleException(SERVICE + " - add manifest line", t);
	}

	return manifestLine;
    }

    /**
     * Takes the AddManifestResponse, checks each field returned from service
     * and populates the ManifestLine
     */
    private ManifestLine getManifestLine(AddToManifestResponseType addResponse) {
	ManifestLine manifestLine = new ManifestLine();
	if (addResponse != null){
	    if (StringUtils.isNotBlank(addResponse.getManifestLine().getIMEIESN()))
		manifestLine.setImeiesn(addResponse.getManifestLine().getIMEIESN());
	    if (StringUtils.isNotBlank(addResponse.getManifestLine().getItemTag()))
		manifestLine.setItemTag(addResponse.getManifestLine().getItemTag());
	    if (StringUtils.isNotBlank(addResponse.getManifestLine().getMake()))
		manifestLine.setMake(addResponse.getManifestLine().getMake());
	    if (addResponse.getManifestLine().getManifestID() != null)
		manifestLine.setManifestID(addResponse.getManifestLine().getManifestID());
	    if (addResponse.getManifestLine().getManifestLineID() != null)
		manifestLine.setManifestLineID(addResponse.getManifestLine().getManifestLineID());
	    if (StringUtils.isNotBlank(addResponse.getManifestLine().getModel()))
		manifestLine.setModel(addResponse.getManifestLine().getModel());
	    if (StringUtils.isNotBlank(addResponse.getManifestLine().getReturnType()))
		manifestLine.setReturnType(addResponse.getManifestLine().getReturnType());
	    if (StringUtils.isNotBlank(addResponse.getManifestLine().getDeviceStatus()))
		manifestLine.setDeviceStatus(addResponse.getManifestLine().getDeviceStatus());
	    if (StringUtils.isNotBlank(addResponse.getManifestLine().getProductDescription()))
		manifestLine.setProductDescription(addResponse.getManifestLine().getProductDescription());
	    if (StringUtils.isNotBlank(addResponse.getManifestLine().getSKU()))
		manifestLine.setSku((addResponse.getManifestLine().getSKU()));
	    if (StringUtils.isNotBlank(addResponse.getManifestLine().getTransferNumber()))
		manifestLine.setTransferNumber((addResponse.getManifestLine().getTransferNumber()));
	}
	return manifestLine;
    }

    /**
     * Preparing the AddManifestRequest with required fields- IBH,
     * ManifestRequest
     */
    private AddToManifestRequestType prepareManifestData(ManifestLine manifestLine, DrpUser drpUser)
	    throws ServiceException {
	AddToManifestRequestType addRequest = new AddToManifestRequestType();

	addRequest
		.setInternationBusinessHierarchy(getPopulatedInternationalBusinessHierarchy(new InternationalBusinessHierarchyType()));
	logger.info("Assigning the manifest request to the AddToManifestRequestType: ");
	ManifestAddRequestType request = new ManifestAddRequestType();
	request.setItemTag(manifestLine.getItemTag());
	if (manifestLine.getManifestID() != null)
	    request.setManifestID(manifestLine.getManifestID());
	request.setStoreID(new BigInteger(drpUser.getStoreId()));
	request.setUserID(drpUser.getUserId());
	GregorianCalendar gc = new GregorianCalendar();
	gc.setTimeInMillis(new Date().getTime());
	DatatypeFactory dtf = null;
	try{
	    dtf = DatatypeFactory.newInstance();
	}catch(DatatypeConfigurationException e){
	    logger.error("Unable to load the DatatypeFactory" + e.getMessage());
	}
	XMLGregorianCalendar xgc = dtf.newXMLGregorianCalendar(gc);
	request.setTransactionDateTime(xgc);
	addRequest.setManifestRequest(request);
	return addRequest;
    }

    /**
     * Returns true if the manifest(s) deleted successfully; Otherwise throws
     * corresponding Business Exception
     */
    @Override
    public boolean removeManifestLine(List<ManifestLine> manifestlines, DrpUser drpUser) throws ServiceException,
	    IseBusinessException {
	// follows the flow of the RemoveRequest and response !
	boolean flag = false;
	RemoveFromManifestRequestType request = prepareRemoveManifestRequest(manifestlines, drpUser);

	try{
	    logger.info("Invoking the ManageManifest Service of ACDS");
	    RemoveFromManifestResponseType response = getManageManifestService().removeManifestLine(request);
	    flag = removeManifestLineFlag(response);
	}catch(ManageManifestFault e){
	    String faultMessage = e.getFaultInfo().getFault().get(0).getMessage().getValue();
	    logger.error(DrpConstants.EXTERNAL_SERVICE_CALL_ERROR + SERVICE + "Failed to invoke the Remove Operation: "
		    + faultMessage);
	    throw new IseBusinessException(IseExceptionCodeEnum.ExternalServiceException, faultMessage);
	}catch(Throwable t){
	    handleException(SERVICE + " - remove manifest line", t);
	}

	return flag;
    }

    // Method to get the Service Response of RemoveManifestLine
    private boolean removeManifestLineFlag(RemoveFromManifestResponseType response) {
	boolean removeFlag = false;
	if (response != null){
	    if (response.isManifestDeleted())
		removeFlag = true;
	    else
		try{
		    throw new Exception("Failed to remove ManifestLine due to: "
			    + response.getStatus().getDescription());
		}catch(Exception e){
		    // TODO This is weird!
		    logger.error("Failed to remove manifest line", e);
		}
	}
	return removeFlag;
    }

    // Preparing the ManifestRequest with ManifestLines and StoreId
    private RemoveFromManifestRequestType prepareRemoveManifestRequest(List<ManifestLine> manifestlines, DrpUser drpUser)
	    throws ServiceException {
	logger.info("Assigning the manifest request to the RemoveFromManifestRequestType: ");
	RemoveFromManifestRequestType removeRequest = new RemoveFromManifestRequestType();
	removeRequest
		.setInternationBusinessHierarchy(getPopulatedInternationalBusinessHierarchy(new InternationalBusinessHierarchyType()));
	ManifestRemoveType rmLines = new ManifestRemoveType();

	ManifestRemoveLineType lineType = new ManifestRemoveLineType();
	for(ManifestLine manLine: manifestlines){
	    lineType.setManifestID(manLine.getManifestID());
	    lineType.setManifestLineID(manLine.getManifestLineID());
	    lineType.setStoreID(new BigInteger(drpUser.getStoreId()));
	    logger.info(lineType.getManifestID() + " " + lineType.getManifestLineID());
	    rmLines.getManifestRequest().add(lineType);

	}
	removeRequest.setRemoveLines(rmLines);
	return removeRequest;
    }

    /**
     * Takes the Search Criteria and returns the List of manifest lines which
     * correspond to that search
     */
    @Override
    public List<Manifest> searchManifest(ManifestSearchCriteria criteria, DrpUser drpUser) throws ServiceException,
	    IseBusinessException {
	List<Manifest> manifests = new ArrayList<Manifest>();
	SearchForManifestsRequestType request = prepareSearchRequest(criteria, drpUser);
	logger.info("Invoking the ManageManifest Service of ACDS");
	try{
	    SearchForManifestsResponseType response = getManageManifestService().searchManifests(request);
	    manifests = getManifestList(response);
	}catch(ManageManifestFault e){
	    String faultMessage = e.getFaultInfo().getFault().get(0).getMessage().getValue();
	    logger.error(DrpConstants.EXTERNAL_SERVICE_CALL_ERROR + SERVICE + "Failed to invoke the Search Operation: "
		    + faultMessage);
	    throw new IseBusinessException(IseExceptionCodeEnum.ExternalServiceException, faultMessage);
	}catch(ServiceException se){
	    logger.error(DrpConstants.EXTERNAL_SERVICE_CALL_ERROR + SERVICE + "Failed to invoke the service: ", se);
	    throw new ServiceException(IseExceptionCodeEnum.ExternalServiceException, se.getMessage());
	}catch(Throwable t){
	    handleException(SERVICE + " - search manifest", t);
	}
	return manifests;
    }

    /**
     * Returns the Manifest List from the Service Response
     */
    private List<Manifest> getManifestList(SearchForManifestsResponseType response) {
	List<Manifest> manifests = new ArrayList<Manifest>();
	if (response != null)
	    if (response.getManifests() != null)
		for(ManifestHeaderType manifestHeader: response.getManifests().getManifest()){
		    Manifest singlemanifest = new Manifest();
		    List<ManifestLine> manifestlines = new ArrayList<ManifestLine>();
		    if (StringUtils.isNotBlank(manifestHeader.getCreatedByUser()))
			singlemanifest.setCreatedByUser(manifestHeader.getCreatedByUser());
		    if (StringUtils.isNotBlank(manifestHeader.getStatus())){
			singlemanifest.setStatus(manifestHeader.getStatus());
			if (manifestHeader.getStatus().equalsIgnoreCase("OPEN"))
			    singlemanifest.setDetails("Resume");
			else
			    singlemanifest.setDetails("Details");
		    }
		    singlemanifest.setDateTimeCreated(Util.toUtilDate(manifestHeader.getDateTimeCreated()));
		    singlemanifest.setDateCompleted(Util.toUtilDate(manifestHeader.getShipScheduledDateTime()));
		    if (StringUtils.isNotBlank(manifestHeader.getDayCreated()))
			singlemanifest.setDayCreated(manifestHeader.getDayCreated());
		    if (manifestHeader.getDeviceCount() != null)
			singlemanifest.setDeviceCount(manifestHeader.getDeviceCount());
		    if (manifestHeader.getManifestID() != null)
			singlemanifest.setManifestID(manifestHeader.getManifestID());
		    if (StringUtils.isNotBlank(manifestHeader.getTrackingIdentifier()))
			singlemanifest.setTrackingIdentifier(manifestHeader.getTrackingIdentifier());
		    if (manifestHeader.getManifestLine() != null)
			for(ManifestLineItemType lineItem: manifestHeader.getManifestLine()){
			    ManifestLine manifestline = new ManifestLine();
			    if (StringUtils.isNotBlank(lineItem.getIMEIESN()))
				manifestline.setImeiesn(lineItem.getIMEIESN());
			    if (StringUtils.isNotBlank(lineItem.getItemTag()))
				manifestline.setItemTag(lineItem.getItemTag());
			    if (StringUtils.isNotBlank(lineItem.getMake()))
				manifestline.setMake(lineItem.getMake());
			    if (lineItem.getManifestID() != null)
				manifestline.setManifestID(lineItem.getManifestID());
			    if (lineItem.getManifestLineID() != null)
				manifestline.setManifestLineID(lineItem.getManifestLineID());
			    if (StringUtils.isNotBlank(lineItem.getModel()))
				manifestline.setModel(lineItem.getModel());
			    if (StringUtils.isNotBlank(lineItem.getReturnType()))
				manifestline.setReturnType(lineItem.getReturnType());
			    if (StringUtils.isNotBlank(lineItem.getProductDescription()))
				manifestline.setProductDescription(lineItem.getProductDescription());
			    if (StringUtils.isNotBlank(lineItem.getDeviceStatus()))
				manifestline.setDeviceStatus(lineItem.getDeviceStatus());
			    if (StringUtils.isNotBlank(lineItem.getSKU()))
				manifestline.setSku(lineItem.getSKU());
			    if (StringUtils.isNotBlank(lineItem.getTransferNumber()))
				manifestline.setTransferNumber(lineItem.getTransferNumber());
			    if (manifestline != null){
				manifestlines.add(manifestline);
			    }
			}
		    singlemanifest.setManifestLine(manifestlines);
		    if (singlemanifest != null)
			manifests.add(singlemanifest);
		}
	return manifests;
    }

    /**
     * Prepares the ManifestSearchRequest based on selected criteria
     * @throws ServiceException 
     */
    private SearchForManifestsRequestType prepareSearchRequest(ManifestSearchCriteria criteria, DrpUser drpUser)
	    throws ServiceException {
	logger.info("Assigning the manifest request to the SearchManifestRequestType: ");
	SearchForManifestsRequestType searchRequest = new SearchForManifestsRequestType();
	searchRequest
		.setInternationBusinessHierarchy(getPopulatedInternationalBusinessHierarchy(new InternationalBusinessHierarchyType()));
	ManifestSearchType searchType = new ManifestSearchType();
	searchType.setStoreID(new BigInteger(drpUser.getStoreId()));

	// Checking which search Criteria is selected
	if (criteria.getMostRecentNumber() != null){
	    searchType.setMostRecentNumber(criteria.getMostRecentNumber());
	    searchRequest.setSearchCriteria(searchType);
	}else if (StringUtils.isNotBlank(criteria.getImeiesn())){
	    searchType.setIMEIESN(criteria.getImeiesn());
	    searchRequest.setSearchCriteria(searchType);
	}else if (criteria.getManifestID() != null){
	    searchType.setManifestID(criteria.getManifestID());
	    searchRequest.setSearchCriteria(searchType);
	}else if (StringUtils.isNotBlank(criteria.getItemTag())){
	    searchType.setItemTag(criteria.getItemTag());
	    searchRequest.setSearchCriteria(searchType);
	}else if (StringUtils.isNotBlank(criteria.getManifestStatus())){
	    searchType.setManifestStatus(criteria.getManifestStatus());
	    searchRequest.setSearchCriteria(searchType);
	}else if (StringUtils.isNotBlank(criteria.getTrackingIdentifier())){
	    searchType.setTrackingIdentifier(criteria.getTrackingIdentifier());
	    searchRequest.setSearchCriteria(searchType);
	}else if (criteria.getStartDate() != null && criteria.getEndDate() != null){
	    DateRange dateRange = new DateRange();
	    GregorianCalendar gc = new GregorianCalendar();
	    gc.setTimeInMillis(criteria.getStartDate().getTime());
	    DatatypeFactory dtf = null;
	    try{
		dtf = DatatypeFactory.newInstance();
	    }catch(DatatypeConfigurationException e){
		logger.error("Unable to load the DatatypeFactory" + e.getMessage());
	    }
	    XMLGregorianCalendar xgc = dtf.newXMLGregorianCalendar(gc);
	    dateRange.setStartDate(xgc);
	    gc.setTimeInMillis(criteria.getEndDate().getTime());
	    xgc = dtf.newXMLGregorianCalendar(gc);
	    dateRange.setEndDate(xgc);
	    searchType.setTransactionDateRange(dateRange);
	    searchRequest.setSearchCriteria(searchType);
	}else if (criteria.getStoreID() != null){
	    searchType.setStoreID(criteria.getStoreID());
	    searchRequest.setSearchCriteria(searchType);
	}

	return searchRequest;
    }

}
