package com.bestbuy.bbym.ise.drp.sao;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.ws.soap.SOAPFaultException;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.bestbuy.bbym.ise.domain.Customer;
import com.bestbuy.bbym.ise.domain.Product;
import com.bestbuy.bbym.ise.drp.domain.DrpUser;
import com.bestbuy.bbym.ise.drp.utils.DrpConstants;
import com.bestbuy.bbym.ise.exception.IseBusinessException;
import com.bestbuy.bbym.ise.exception.IseExceptionCodeEnum;
import com.bestbuy.bbym.ise.exception.ServiceException;
import com.bestbuy.bbym.ise.util.Util;
import com.bestbuy.tsh.common.datatype.v1.IdentifierType;
import com.bestbuy.tsh.common.datatype.v1.TextType;
import com.bestbuy.tsh.common.metadata.components.v1.InternationalBusinessHierarchyType;
import com.bestbuy.tsh.sales.common.components.v1.CustomerType;
import com.bestbuy.tsh.sales.common.components.v1.ItemType;
import com.bestbuy.tsh.sales.common.components.v1.RequestMetaDataExtensionType;
import com.bestbuy.tsh.sales.common.components.v1.SearchConfigType;
import com.bestbuy.tsh.sales.common.components.v1.SearchParameterType;
import com.bestbuy.tsh.sales.common.components.v1.SearchTransactionsRequestType;
import com.bestbuy.tsh.sales.common.components.v1.SearchTransactionsResponseType;
import com.bestbuy.tsh.sales.common.components.v1.TransactionHeaderType;
import com.bestbuy.tsh.sales.common.components.v1.TransactionKeyType;
import com.bestbuy.tsh.sales.common.components.v1.TransactionLineItemType;
import com.bestbuy.tsh.sales.common.components.v1.TransactionType;
import com.bestbuy.tsh.tsh.tshfault.FaultType;
import com.example.xmlns._1299800422009.SearchTransactionFault;

/**
 * @author a218635
 * 
 */
@Service("ecTransactionLookupSao")
public class EcTransactionLookupSaoImpl extends AbstractSao implements EcTransactionLookupSao {

    private static Logger logger = LoggerFactory.getLogger(EcTransactionLookupSaoImpl.class);
    private Map<String, String> transactionTypeMap = new HashMap<String, String>();
    private static final String SERVICE = "EC ";

    public EcTransactionLookupSaoImpl() {

	transactionTypeMap.put("01", "Sale");
	transactionTypeMap.put("02", "Return");
	transactionTypeMap.put("03", "Exchange");
	transactionTypeMap.put("04", "Price Match");
	transactionTypeMap.put("05", "Employee Sale");
	transactionTypeMap.put("06", "Employee Return");
	transactionTypeMap.put("70", "Dot Com Order");
	transactionTypeMap.put("80", "Retail Rebate");
	transactionTypeMap.put("81", "Dot Com Rebate");
	transactionTypeMap.put("90", "Dot Com Allowances");
	transactionTypeMap.put("UK", "UK");
    }

    /**
     * Gets All the online and POS Transaction data from the EC system.
     */
    public List<Product> getAllTransactions(Customer bbyCustomer, DrpUser drpUser, Date startDate, Date endDate,
	    boolean mobileOnly) throws ServiceException, IseBusinessException {

	logger.info("In the getAllTransactions method");
	List<Product> transactionList = new ArrayList<Product>();
	SearchTransactionsRequestType searchTransactionRequest = prepareSearchTransactionRequest(bbyCustomer, drpUser,
		startDate, endDate);
	SearchTransactionsResponseType searchTransactionResponse = null;

	try{
	    logger.info("Invoking the searchTransaction Service with customer Id : " + bbyCustomer.getBbyCustomerId());
	    searchTransactionResponse = getTshEcService().searchTransaction(searchTransactionRequest);

	    if (searchTransactionResponse != null && searchTransactionResponse.getStatus() != null){

		String StatusCode = searchTransactionResponse.getStatus().getCode().getValue();
		String statusMessage = searchTransactionResponse.getStatus().getDescription();
		logger.info("search Transaction Response : Status Code : " + StatusCode + " and Status Description : "
			+ statusMessage);

		if (StatusCode.equalsIgnoreCase("TSH-PXY-000000")){
		    List<TransactionType> ecTransactionList = new ArrayList<TransactionType>();
		    ecTransactionList = searchTransactionResponse.getTransaction();
		    logger.info("Number of transactions List Retrieved : " + ecTransactionList.size());
		    transactionList = intrepretResponseTransactionList(ecTransactionList, mobileOnly);
		}else{
		    throw new IseBusinessException(statusMessage);
		}
	    }

	}catch(SearchTransactionFault e){
	    logger.error(DrpConstants.EXTERNAL_SERVICE_CALL_ERROR + SERVICE
		    + "SearchTransactionFault Exception - getAllTransactions : " + e.getMessage());
	    String errorDetails = "";
	    for(FaultType ft: e.getFaultInfo().getFault()){
		errorDetails = ft.getCode().getValue() + " : " + ft.getMessage().getValue();
		logger.error(errorDetails);
		break;
	    }
	    throw new IseBusinessException(errorDetails);
	}catch(SOAPFaultException sfe){
	    String errorMessage = sfe.getFault().getFaultCode() + " : " + sfe.getFault().getFaultString();
	    logger.error(DrpConstants.EXTERNAL_SERVICE_CALL_ERROR + SERVICE + "In the EC SOAPFaultException : "
		    + errorMessage);
	    throw new ServiceException(IseExceptionCodeEnum.ExternalServiceException, errorMessage);
	}catch(ServiceException se){
	    logger.error(DrpConstants.EXTERNAL_SERVICE_CALL_ERROR + SERVICE
		    + "Service Exception - getAllTransactions(): " + se.getMessage());
	    throw new ServiceException(IseExceptionCodeEnum.ExternalServiceException, se.getMessage());
	}catch(Exception e){
	    logger.error(DrpConstants.EXTERNAL_SERVICE_CALL_ERROR + SERVICE + " Exception - getAllTransactions : "
		    + e.getMessage());
	    if (searchTransactionResponse != null && searchTransactionResponse.getStatus() != null){
		String StatusCode = searchTransactionResponse.getStatus().getCode().getValue();
		if (!StatusCode.equalsIgnoreCase("TSH-PXY-000000")){
		    throw new IseBusinessException(searchTransactionResponse.getStatus().getDescription());
		}
	    }

	    throw new ServiceException(IseExceptionCodeEnum.ExternalServiceException, e.getMessage());
	}

	return transactionList;
    }

    /**
     * Gets the retail Price of the device by fourpartKey and SKU from the EC
     * system for the buy back computation.
     */
    public BigDecimal getRetailPriceByFourPartKey(String fourPartKey, String sku, DrpUser drpUser)
	    throws ServiceException {

	logger.info("In the getRetailPriceByFourPartKey method");
	BigDecimal retailPrice = null;
	SearchTransactionsRequestType searchTransactionRequest = prepareSearchByTransIdRequest(fourPartKey, sku,
		drpUser);
	SearchTransactionsResponseType searchTransactionResponse = null;

	try{
	    logger.info("Invoking the searchTransaction Service with TransactionKey : " + fourPartKey);
	    searchTransactionResponse = getTshEcService().searchTransaction(searchTransactionRequest);

	    if (searchTransactionResponse.getStatus() != null){

		String StatusCode = searchTransactionResponse.getStatus().getCode().getValue();
		String statusMessage = searchTransactionResponse.getStatus().getDescription();
		logger.info("search Transaction Response  : Status Code : " + StatusCode + " and Status Description : "
			+ statusMessage);

		if (StatusCode.equalsIgnoreCase("TSH-PXY-000000")){
		    List<TransactionType> ecTransactionList = new ArrayList<TransactionType>();
		    ecTransactionList = searchTransactionResponse.getTransaction();
		    logger.info("Number of transactions List Retrieved : " + ecTransactionList.size());
		    retailPrice = getRetailPriceFromTransactionList(ecTransactionList, sku);
		}else{
		    logger.error(statusMessage);
		}
	    }

	}catch(SearchTransactionFault e){
	    logger.error(DrpConstants.EXTERNAL_SERVICE_CALL_ERROR + SERVICE
		    + "Service Exception - getRetailPriceByFourPartKey : " + e.getMessage());
	    String errorDetails = "";
	    for(FaultType ft: e.getFaultInfo().getFault()){
		errorDetails = "ErrorMessage : " + ft.getCode().getValue() + " : " + ft.getMessage().getValue();
		logger.error(errorDetails);
		break;
	    }
	}catch(SOAPFaultException sfe){
	    String errorMessage = sfe.getFault().getFaultCode() + " : " + sfe.getFault().getFaultString();
	    logger.error(DrpConstants.EXTERNAL_SERVICE_CALL_ERROR + SERVICE + "In the EC SOAPFaultException : "
		    + errorMessage);
	    throw new ServiceException(IseExceptionCodeEnum.ExternalServiceException, errorMessage);
	}catch(ServiceException se){
	    logger.error(DrpConstants.EXTERNAL_SERVICE_CALL_ERROR + SERVICE
		    + "Service Exception - getRetailPriceByFourPartKey : " + se.getMessage());
	    throw new ServiceException(IseExceptionCodeEnum.ExternalServiceException, se.getMessage());
	}catch(Exception e){
	    logger.error(DrpConstants.EXTERNAL_SERVICE_CALL_ERROR + SERVICE
		    + " Exception - getRetailPriceByFourPartKey : " + e.getMessage());
	    throw new ServiceException(IseExceptionCodeEnum.ExternalServiceException, e.getMessage());
	}

	return retailPrice;
    }

    /**
     * Sets all the request data needed to perform the search Transaction with
     * EC.
     */
    private SearchTransactionsRequestType prepareSearchTransactionRequest(Customer bbyCustomer, DrpUser drpUser,
	    Date startDate, Date endDate) throws ServiceException {

	logger.info("In the prepareSearchTransactionRequest");
	SearchTransactionsRequestType searchTransactionRequest = new SearchTransactionsRequestType();
	searchTransactionRequest
		.setInternationBusinessHierarcy(getPopulatedInternationalBusinessHierarchy(new InternationalBusinessHierarchyType()));

	RequestMetaDataExtensionType ecMetaData = getRequestMetaDataExtensionType(drpUser);
	searchTransactionRequest.setMetaData(ecMetaData);
	logger.info("In the prepareSearchTransactionRequest : set the MetaData");

	SearchConfigType ecSearchConfig = new SearchConfigType();
	ecSearchConfig.setMaxRows(new BigInteger("1000"));
	ecSearchConfig.setSearchTimeLimitMS(new BigInteger("20000"));
	ecSearchConfig.setSearchTypeToPerform(getTextValue("ecByCustomer"));
	ecSearchConfig.setPerformDrillDown(false);
	ecSearchConfig.setSearchOnlyAuditedtransaction(false);
	ecSearchConfig.setRetrieveCustomerInfo(false);
	ecSearchConfig.setRetrieveAllTransactionKeys(true);
	ecSearchConfig.setRetrieveMasterItemDetails(false);
	ecSearchConfig.setInquiryLevel(getTextValue(""));
	searchTransactionRequest.setSearchConfig(ecSearchConfig);
	logger.info("In the prepareSearchTransactionRequest : set the Search Config Data");

	SearchParameterType ecSearchParameter = new SearchParameterType();
	CustomerType customerType = new CustomerType();
	customerType.setApplicationID(getTextValue("EC"));
	customerType.setCustomerID(getTextValue(bbyCustomer.getBbyCustomerId()));
	ecSearchParameter.setCustomer(customerType);
	ecSearchParameter.setStartDate(Util.toXmlGregorianCalendarNoTimePart(startDate));
	ecSearchParameter.setEndDate(Util.toXmlGregorianCalendarNoTimePart(endDate));

	searchTransactionRequest.setSearchParameter(ecSearchParameter);
	logger.info("In the prepareSearchTransactionRequest : set the Search Parameter Data");

	return searchTransactionRequest;
    }

    /**
     * Sets all the request data needed to perform the search Transaction with
     * EC by the fourPartKey.
     */
    private SearchTransactionsRequestType prepareSearchByTransIdRequest(String fourPartKey, String sku, DrpUser drpUser)
	    throws ServiceException {

	logger.info("In the prepareSearchByTransIdRequest");
	SearchTransactionsRequestType searchTransactionRequest = new SearchTransactionsRequestType();
	searchTransactionRequest
		.setInternationBusinessHierarcy(getPopulatedInternationalBusinessHierarchy(new InternationalBusinessHierarchyType()));

	RequestMetaDataExtensionType ecMetaData = getRequestMetaDataExtensionType(drpUser);
	searchTransactionRequest.setMetaData(ecMetaData);
	logger.info("In the prepareSearchByTransIdRequest : set the MetaData");

	SearchConfigType ecSearchConfig = new SearchConfigType();
	ecSearchConfig.setMaxRows(new BigInteger("100"));
	ecSearchConfig.setSearchTimeLimitMS(new BigInteger("15000"));
	ecSearchConfig.setSearchTypeToPerform(getTextValue("ecByTransaction"));
	ecSearchConfig.setPerformDrillDown(false);
	ecSearchConfig.setSearchOnlyAuditedtransaction(false);
	ecSearchConfig.setRetrieveCustomerInfo(false);
	ecSearchConfig.setRetrieveAllTransactionKeys(true);
	ecSearchConfig.setRetrieveMasterItemDetails(false);
	ecSearchConfig.setInquiryLevel(getTextValue(""));
	searchTransactionRequest.setSearchConfig(ecSearchConfig);
	logger.info("In the prepareSearchByTransIdRequest : set the Search Config Data");

	SearchParameterType ecSearchParameter = new SearchParameterType();
	ItemType item = new ItemType();
	item.setSKU(getIdentifierValue(sku));
	ecSearchParameter.setItem(item);
	ecSearchParameter.setTransactionKey(getTextValue(fourPartKey));
	ecSearchParameter.setTransactionKeyType(getTextValue("FourPartKey"));

	searchTransactionRequest.setSearchParameter(ecSearchParameter);
	logger.info("In the prepareSearchByTransIdRequest : set the Search Parameter Data");

	return searchTransactionRequest;
    }

    /**
     * Interprets the Transaction Response data from the EC and map it to the
     * list of products.
     */
    private List<Product> intrepretResponseTransactionList(List<TransactionType> ecTransactionList, boolean mobileOnly) {

	logger
		.info("In the intrepretResponseTransactionList : About to map the Transaction response to List of Product");

	List<Product> transactionList = new ArrayList<Product>();

	for(TransactionType respTransaction: ecTransactionList){

	    if (respTransaction != null && respTransaction.getTransactionLineItem() != null
		    && !respTransaction.getTransactionLineItem().isEmpty()){
		String transactionTypeVal = "";
		String fourPartKey = "";
		String transactionSourceSystem = "";
		String transactionKeyType = "";
		String sourceTransactionKey = "";

		TransactionHeaderType transHeader = respTransaction.getTransactionHeader();
		String transType = transHeader.getTransactionType().getValue();
		if (StringUtils.isNotBlank(transType) && transactionTypeMap.containsKey(transType)){
		    transactionTypeVal = transactionTypeMap.get(transType);
		}
		Date transactionDate = Util.toUtilDate(transHeader.getTransactionDate());

		List<TransactionKeyType> transKeyList = transHeader.getTransactionKey();
		// displaying Order Number for online and fourPartKey for POS
		if (transKeyList != null && !transKeyList.isEmpty()){
		    for(TransactionKeyType transkey: transKeyList){
			if (transkey.getTransactionKeyType() != null){
			    if (transkey.getTransactionKeyType().getValue().equalsIgnoreCase("Order")){
				fourPartKey = transkey.getSourceTransactionKey().getValue();
				transactionSourceSystem = transkey.getTransactionSourceSystem().getValue();
				transactionKeyType = transkey.getTransactionKeyType().getValue();
				sourceTransactionKey = transkey.getSourceTransactionKey().getValue();
				break;
			    }else if (transkey.getTransactionKeyType().getValue().equalsIgnoreCase("FourPartKey")){
				transactionSourceSystem = transkey.getTransactionSourceSystem().getValue();
				transactionKeyType = transkey.getTransactionKeyType().getValue();
				sourceTransactionKey = transkey.getSourceTransactionKey().getValue();
				fourPartKey = transkey.getSourceTransactionKey().getValue();
				break;
			    }
			}
		    }
		}

		List<TransactionLineItemType> lineItemList = respTransaction.getTransactionLineItem();
		for(TransactionLineItemType lineItem: lineItemList){
		    // Filtering by Sale(SL) and Return(SR)
		    if (lineItem.getTransactionLineType() != null
			    && StringUtils.isNotBlank(lineItem.getTransactionLineType().getValue())
			    && (lineItem.getTransactionLineType().getValue().equalsIgnoreCase("SL") || lineItem
				    .getTransactionLineType().getValue().equalsIgnoreCase("SR"))){
			Product product = new Product();

			product.setTransactionId(fourPartKey);
			product.setPurchaseDate(transactionDate);
			product.setTransactionType(transactionTypeVal);
			product.setTransactionSourceSystem(transactionSourceSystem);
			product.setTransactionKeyType(transactionKeyType);
			product.setSourceTransactionKey(sourceTransactionKey);
			if (lineItem.getItem() != null && lineItem.getItem().getSKU() != null)
			    product.setSku(lineItem.getItem().getSKU().getValue());
			if (lineItem.getItem() != null && lineItem.getItem().getSkuDescription() != null)
			    product.setDescription(lineItem.getItem().getSkuDescription().getValue());
			if (lineItem.getSerialNumber() != null)
			    product.setSerialNumber(lineItem.getSerialNumber().getValue());
			if (lineItem.getRetailPrice() != null)
			    product.setRetailPrice(BigDecimal.valueOf(lineItem.getRetailPrice()));
			if (lineItem.getSalePrice() != null)
			    product.setPurchasePrice(BigDecimal.valueOf(lineItem.getSalePrice()));
			if (lineItem.getProductCategory() != null && lineItem.getProductCategory().getDeptID() != null)
			    product.setDepartmentNum(lineItem.getProductCategory().getDeptID().intValue());
			if (mobileOnly){
			    if (product.getDepartmentNum() != null
				    && (product.getDepartmentNum() == 3 || product.getDepartmentNum() == 6 || product
					    .getDepartmentNum() == 7)){
				transactionList.add(product);
			    }
			}else{
			    transactionList.add(product);
			}
		    }
		}
	    }
	}

	return transactionList;
    }

    /**
     * Interprets the Transaction Response data from the EC and retrieves the
     * retail price corresponding to the product SKU provided.
     */
    private BigDecimal getRetailPriceFromTransactionList(List<TransactionType> ecTransactionList, String sku) {

	logger
		.info("In the getRetailPriceFromTransactionList : About to retreive the retail Price for the Product sku : "
			+ sku);
	BigDecimal productRetailPrice = null;
	for(TransactionType respTransaction: ecTransactionList){

	    if (respTransaction != null && respTransaction.getTransactionLineItem() != null
		    && !respTransaction.getTransactionLineItem().isEmpty()){

		List<TransactionLineItemType> lineItemList = respTransaction.getTransactionLineItem();
		for(TransactionLineItemType lineItem: lineItemList){
		    // Filtering by Sale(SL) and Return(SR)
		    if (lineItem.getTransactionLineType() != null
			    && StringUtils.isNotBlank(lineItem.getTransactionLineType().getValue())
			    && (lineItem.getTransactionLineType().getValue().equalsIgnoreCase("SL"))){
			if (lineItem.getItem() != null && lineItem.getItem().getSKU() != null
				&& lineItem.getItem().getSKU().getValue().equalsIgnoreCase(sku)
				&& lineItem.getRetailPrice() != null){
			    productRetailPrice = BigDecimal.valueOf(lineItem.getRetailPrice());
			    break;
			}
		    }
		}
	    }
	}

	return productRetailPrice;
    }

    private TextType getTextValue(String str) {
	TextType text = new TextType();
	text.setValue(str);
	return text;
    }

    private IdentifierType getIdentifierValue(String str) {
	IdentifierType text = new IdentifierType();
	text.setValue(str);
	return text;
    }

    private RequestMetaDataExtensionType getRequestMetaDataExtensionType(DrpUser drpUser) {
	RequestMetaDataExtensionType ecMetaData = new RequestMetaDataExtensionType();
	try{
	    ecMetaData.setSourceID(getDrpPropertiesService().getProperty("APPLICATION_NAME"));
	    ecMetaData.setUserID(drpUser.getUserId());
	    ecMetaData.setProgramID(getDrpPropertiesService().getProperty("SOURCE_SYSTEM"));
	}catch(ServiceException e){
	    logger.error("Error setting metadata values", e);
	}
	ecMetaData.setRequestTimeStamp(Util.toXmlGregorianCalendar(new Date()));
	ecMetaData.setMaximumResponseCount(new BigInteger("20"));
	return ecMetaData;
    }

    @Override
    public List<Product> getAllTransactionsByFourPartKey(String transactionKey, DrpUser drpUser, boolean mobileOnly)
	    throws ServiceException {

	List<Product> transactionList = new ArrayList<Product>();
	logger.info("In the getAllTransactionsByFourPartKey method");
	SearchTransactionsRequestType searchTransactionRequest = prepareSearchByTransIdRequest(transactionKey, null,
		drpUser);
	SearchTransactionsResponseType searchTransactionResponse = null;
	try{
	    logger
		    .info("getAllTransactionsByFourPartKey() : Invoking the searchTransaction Service with TransactionKey : "
			    + transactionKey);
	    searchTransactionResponse = getTshEcService().searchTransaction(searchTransactionRequest);
	    if (searchTransactionResponse.getStatus() != null){
		String StatusCode = searchTransactionResponse.getStatus().getCode().getValue();
		String statusMessage = searchTransactionResponse.getStatus().getDescription();
		logger.info("search Transaction Response  : Status Code : " + StatusCode + " and Status Description : "
			+ statusMessage);

		if (StatusCode.equalsIgnoreCase("TSH-PXY-000000")){
		    List<TransactionType> ecTransactionList = new ArrayList<TransactionType>();
		    ecTransactionList = searchTransactionResponse.getTransaction();
		    logger.info("Number of transactions List Retrieved : " + ecTransactionList.size());
		    transactionList = intrepretResponseTransactionList(ecTransactionList, mobileOnly);

		}else{
		    logger.error(statusMessage);
		}
	    }

	}catch(SearchTransactionFault e){
	    logger.error(DrpConstants.EXTERNAL_SERVICE_CALL_ERROR + SERVICE
		    + "Service Exception - getAllTransactionsByFourPartKey : " + e.getMessage());
	    String errorDetails = "";
	    for(FaultType ft: e.getFaultInfo().getFault()){
		errorDetails = "ErrorMessage : " + ft.getCode().getValue() + " : " + ft.getMessage().getValue();
		logger.error(errorDetails);
		break;
	    }
	}catch(SOAPFaultException sfe){
	    String errorMessage = sfe.getFault().getFaultCode() + " : " + sfe.getFault().getFaultString();
	    logger.error(DrpConstants.EXTERNAL_SERVICE_CALL_ERROR + SERVICE + "In the EC SOAPFaultException : "
		    + errorMessage);
	    throw new ServiceException(IseExceptionCodeEnum.ExternalServiceException, errorMessage);
	}catch(ServiceException se){
	    logger.error(DrpConstants.EXTERNAL_SERVICE_CALL_ERROR + SERVICE
		    + "Service Exception - getAllTransactionsByFourPartKey : " + se.getMessage());
	    throw new ServiceException(IseExceptionCodeEnum.ExternalServiceException, se.getMessage());
	}catch(Exception e){
	    logger.error(DrpConstants.EXTERNAL_SERVICE_CALL_ERROR + SERVICE
		    + " Exception - getAllTransactionsByFourPartKey : " + e.getMessage());
	    throw new ServiceException(IseExceptionCodeEnum.ExternalServiceException, e.getMessage());
	}

	return transactionList;
    }

    @Override
    public boolean validateFourPartKeySkuCombo(String transactionKey, DrpUser drpUser, boolean mobileOnly, String sku)
	    throws ServiceException {
	boolean isFourPartKeySkuValid = false;
	try{
	    List<Product> productList = getAllTransactionsByFourPartKey(transactionKey, drpUser, mobileOnly);
	    if (productList != null && !productList.isEmpty())
		for(Product product: productList){
		    if (product != null){
			if (sku.equalsIgnoreCase(product.getSku())){
			    isFourPartKeySkuValid = true;
			    break;
			}
		    }
		}
	}catch(SOAPFaultException sfe){
	    String errorMessage = sfe.getFault().getFaultCode() + " : " + sfe.getFault().getFaultString();
	    logger.error(DrpConstants.EXTERNAL_SERVICE_CALL_ERROR + SERVICE + "In the EC SOAPFaultException : "
		    + errorMessage);
	    throw new ServiceException(IseExceptionCodeEnum.ExternalServiceException, errorMessage);
	}catch(ServiceException se){
	    logger.error(DrpConstants.EXTERNAL_SERVICE_CALL_ERROR + SERVICE
		    + "Service Exception - validateFourPartKeySkuCombo : " + se.getMessage());
	    throw new ServiceException(IseExceptionCodeEnum.ExternalServiceException, se.getMessage());
	}catch(Exception e){
	    logger.error(DrpConstants.EXTERNAL_SERVICE_CALL_ERROR + SERVICE
		    + " Exception - validateFourPartKeySkuCombo : " + e.getMessage());
	    throw new ServiceException(IseExceptionCodeEnum.ExternalServiceException, e.getMessage());
	}

	return isFourPartKeySkuValid;
    }

}
