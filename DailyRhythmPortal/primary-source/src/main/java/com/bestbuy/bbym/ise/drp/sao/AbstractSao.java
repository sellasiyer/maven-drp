package com.bestbuy.bbym.ise.drp.sao;

import java.io.StringReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.Handler;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ClassPathResource;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

import sun.misc.BASE64Decoder;

import com.bestbuy.bbym.ise.drp.service.DrpPropertyService;
import com.bestbuy.bbym.ise.drp.utils.DrpConstants;
import com.bestbuy.bbym.ise.drp.utils.SoapMessageHandler;
import com.bestbuy.bbym.ise.exception.IseBusinessException;
import com.bestbuy.bbym.ise.exception.IseExceptionCodeEnum;
import com.bestbuy.bbym.ise.exception.ServiceException;
import com.bestbuy.bbym.ise.iseclientacds.ManageManifestPortType;
import com.bestbuy.bbym.ise.iseclientacds.ManageManifestV01Serviceagent;
import com.bestbuy.bbym.ise.iseclientacdsdevice.ManageDevicePortType;
import com.bestbuy.bbym.ise.iseclientacdsdevice.ManageDeviceServiceagent;
import com.bestbuy.bbym.ise.iseclientacdsparam.ACDSParamPortType;
import com.bestbuy.bbym.ise.iseclientacdsparam.ACDSParamServiceagent;
import com.bestbuy.bbym.ise.iseclientacdsshipping.ShippingPortType;
import com.bestbuy.bbym.ise.iseclientacdsshipping.ShippingV01Serviceagent;
import com.bestbuy.bbym.ise.iseclientca.CAServicesSOAPV2;
import com.bestbuy.bbym.ise.iseclientca.CustomerAccountServicesPortType;
import com.bestbuy.bbym.ise.iseclientcap.CAPWsController;
import com.bestbuy.bbym.ise.iseclientcap.CAPWsControllerService;
import com.bestbuy.bbym.ise.iseclientej.EJournal;
import com.bestbuy.bbym.ise.iseclientej.ElectronicJournalServices;
import com.bestbuy.bbym.ise.iseclientforcapcommon.IntlBusinessHierarchyType;
import com.bestbuy.bbym.ise.iseclienthub.ProtectionPlanSOAP;
import com.bestbuy.bbym.ise.iseclienthub.ProtectionPlanSOAP_Service;
import com.bestbuy.bbym.ise.iseclientmec.EntitlementCheckPortType;
import com.bestbuy.bbym.ise.iseclientmec.EntitlementCheckServiceServiceagent;
import com.bestbuy.bbym.ise.iseclientopensso.IdentityServicesImpl;
import com.bestbuy.bbym.ise.iseclientopensso.IdentityServicesImplService;
import com.bestbuy.bbym.ise.iseclientpromo.PromotionService;
import com.bestbuy.bbym.ise.iseclientpromo.PromotionServiceService;
import com.bestbuy.bbym.ise.iseclientrwzcerts.RZCertService;
import com.bestbuy.bbym.ise.iseclientrwzcerts.RZCertServiceSOAP;
import com.bestbuy.bbym.ise.iseclientrwzmember.InquireRZMemberV1;
import com.bestbuy.bbym.ise.iseclientrwzmember.InquireRZMemberV1SOAP;
import com.bestbuy.bbym.ise.iseclientrwzpoints.ValidateRZMember;
import com.bestbuy.bbym.ise.iseclientrwzpoints.ValidateRZMemberSOAP;
import com.bestbuy.bbym.ise.iseclientucs.UpgradeChecker;
import com.bestbuy.bbym.ise.iseclientucs.UpgradeCheckerService;
import com.bestbuy.bbym.ise.util.Util;
import com.bestbuy.tsh.bbym.logistics.updatedevicestatus.v1.UpdateDeviceStatusPortType;
import com.bestbuy.tsh.bbym.logistics.updatedevicestatus.v1.UpdateDeviceStatusServiceagent;
import com.bestbuy.tsh.businessservices.proxy.tradeincheck.TradeInCheckServiceServiceagent;
import com.bestbuy.tsh.businessservices.proxy.tradeincheck.TransactionCheckServicePortType;
import com.bestbuy.tsh.facilities.location.retrievelocationsservice.v2.RetrieveLocationsPortType;
import com.bestbuy.tsh.facilities.location.retrievelocationsservice.v2.RetrieveLocationsServiceServiceagent;
import com.example.xmlns._1299800422009.PortType;
import com.example.xmlns._1299800422009.SearchTransactionService;
import com.sun.xml.ws.developer.JAXWSProperties;

/**
 * Abstract base class for all SAOs.
 * 
 * @author a904002
 */
public abstract class AbstractSao {

    private static Logger logger = LoggerFactory.getLogger(AbstractSao.class);

    @Autowired
    @Qualifier("drpPropertyService")
    protected DrpPropertyService drpPropertiesService;
    @Autowired
    private SoapMessageHandler msgHandler;

    public DrpPropertyService getDrpPropertiesService() {
	return drpPropertiesService;
    }

    public void setDrpPropertiesService(DrpPropertyService drpPropertyService) {
	this.drpPropertiesService = drpPropertyService;
    }

    /**
     * Populates the given international business hierarchy object.
     */
    protected <T> T getPopulatedInternationalBusinessHierarchy(T emptyIbh) throws ServiceException {
	logger.info("Populating international business hierarchy values");
	try{
	    BeanUtils.copyProperties(getIntlBusHierarchyDataType(), emptyIbh, (String[]) null);
	}catch(BeansException e){
	    logger.error("Failed to populate IBH", e);
	}
	return emptyIbh;
    }

    /**
     * Gets a populated CAP common international business hierarchy.
     */
    private IntlBusinessHierarchyType getIntlBusHierarchyDataType() throws ServiceException {

	IntlBusinessHierarchyType ibhType = new IntlBusinessHierarchyType();
	ibhType.setEnterprise(drpPropertiesService.getProperty("IBH_ENTERPRISE"));
	ibhType.setTradingArea(drpPropertiesService.getProperty("IBH_TRADING_AREA"));
	ibhType.setCompany(drpPropertiesService.getProperty("IBH_COMPANY"));
	ibhType.setBrand(drpPropertiesService.getProperty("IBH_BRAND"));
	ibhType.setBusinessUnit(drpPropertiesService.getProperty("IBH_BUSINESS_UNIT"));
	ibhType.setChannel(drpPropertiesService.getProperty("IBH_CHANNEL"));

	return ibhType;
    }

    public boolean isDummyStoreEnabled() throws ServiceException {
	String dummyStoreEnabled = drpPropertiesService.getProperty("DUMMY_STORE_ENABLED");
	if (StringUtils.isNotBlank(dummyStoreEnabled)){
	    return Boolean.parseBoolean(dummyStoreEnabled);
	}else{
	    return false;
	}
    }

    public String getDummyStoreNum() throws ServiceException {
	String dummyStoreNum = drpPropertiesService.getProperty("DUMMY_STORE_NUM");
	if (StringUtils.isNotBlank(dummyStoreNum)){
	    return dummyStoreNum;
	}else{
	    return "0699"; // This value is configured in both CAP and UCS.
	}
    }

    private void insertMsgHandler(BindingProvider bp) {
	List<Handler> handlerChain = new ArrayList<Handler>();
	handlerChain.add(msgHandler);
	bp.getBinding().setHandlerChain(handlerChain);
    }

    private URL getURL(String endPointRef) throws ServiceException {
	try{
	    return new URL(endPointRef);
	}catch(Exception e){
	    logger.error("Failed to create URL for the wsdl Location: " + endPointRef + " , retrying as a local file",
		    e);
	    throw new ServiceException(IseExceptionCodeEnum.ExternalServiceException,
		    "Could not reach service definition");
	}
    }

    /**
     * Gets a URL referencing the WSDL file that was packaged in the war.
     */
    private URL getConfiguredUrl(String endPointRef) throws ServiceException {
	try{
	    return new ClassPathResource(endPointRef).getURL();
	}catch(Exception e){
	    logger.error("Failed to create URL for the wsdl Location: " + endPointRef, e);
	    throw new ServiceException(IseExceptionCodeEnum.ExternalServiceException,
		    "Could find service definition file");
	}
    }

    protected UpgradeChecker getUpgradeCheckerService() throws ServiceException {
	logger.info("Creating the UCS Proxy Service");
	URL url = null;
	String endPointRef = drpPropertiesService.getProperty("UCS_WS_URL");
	int timeout = getTimeout("UCS_WS_TIMEOUT");
	if ("PROD".equalsIgnoreCase(endPointRef))
	    url = getConfiguredUrl("/WSDL/PROD/UCS/UpgradeChecker.wsdl");
	else if ("QA".equalsIgnoreCase(endPointRef))
	    url = getConfiguredUrl("/WSDL/QA/UCS/UpgradeChecker.wsdl");
	else
	    url = getURL(endPointRef);

	logger.info("UCS URL : " + url);
	UpgradeCheckerService ucs = new UpgradeCheckerService(url, new QName("http://bestbuy.com/bbym/ucs",
		"UpgradeCheckerService"));
	UpgradeChecker ucsPort = ucs.getUpgradeCheckerPort();
	insertMsgHandler((BindingProvider) ucsPort);
	setTimeouts((BindingProvider) ucsPort, timeout);
	logger.info("Created the UCS Proxy Service");
	return ucsPort;
    }

    protected PromotionService getPromotionService() throws ServiceException {
	logger.info("Creating the Promotion Proxy Service");
	URL url = null;
	String endPointRef = drpPropertiesService.getProperty("PROMO_WS_URL");
	int timeout = getTimeout("PROMO_WS_TIMEOUT");
	if ("PROD".equalsIgnoreCase(endPointRef))
	    url = getConfiguredUrl("/WSDL/PROD/PROMO/PromotionService.wsdl");
	else if ("QA".equalsIgnoreCase(endPointRef))
	    url = getConfiguredUrl("/WSDL/QA/PROMO/PromotionService.wsdl");
	else
	    url = getURL(endPointRef);

	logger.info("PROMOTION SERVICE URL : " + url);
	PromotionServiceService promo = new PromotionServiceService(url, new QName("http://bestbuy.com/bbym/promo",
		"PromotionServiceService"));
	PromotionService promServicePort = promo.getPromotionServicePort();
	insertMsgHandler((BindingProvider) promServicePort);
	setTimeouts((BindingProvider) promServicePort, timeout);
	logger.info("Created the Promotion Proxy Service");
	return promServicePort;
    }

    protected InquireRZMemberV1 getRewardZoneMemberLookupService() throws ServiceException {
	logger.info("Creating the RewardZone Member Lookup Service");
	URL url = null;
	String endPointRef = drpPropertiesService.getProperty("RWZ_MEM_WS_URL");
	int timeout = getTimeout("RWZ_MEM_WS_TIMEOUT");
	if ("PROD".equalsIgnoreCase(endPointRef))
	    url = getConfiguredUrl("/WSDL/PROD/RewardZone/RWZ_Member/InquireMemberV1Soap.wsdl");
	else if ("QA".equalsIgnoreCase(endPointRef))
	    url = getConfiguredUrl("/WSDL/QA/RewardZone/RWZ_Member/InquireMemberV1Soap.wsdl");
	else
	    url = getURL(endPointRef);

	logger.info("REWARDZONE MEMBER SERVICE URL : " + url);
	InquireRZMemberV1SOAP rzmember = new InquireRZMemberV1SOAP(url, new QName(
		"http://webservices.bestbuy.com/rzServices/service", "InquireRZMemberV1SOAP"));
	InquireRZMemberV1 inquireRZMember = rzmember.getInquireRZMemberV1();
	insertMsgHandler((BindingProvider) inquireRZMember);
	setTimeouts((BindingProvider) inquireRZMember, timeout);
	logger.info("Created the RewardZone Member Lookup Service");
	return inquireRZMember;
    }

    protected ValidateRZMember getRewardZonePointsLookupService() throws ServiceException {
	logger.info("Creating the RewardZone Points Lookup Service");
	URL url = null;
	String endPointRef = drpPropertiesService.getProperty("RWZ_PTS_WS_URL");
	int timeout = getTimeout("RWZ_PTS_WS_TIMEOUT");
	if ("PROD".equalsIgnoreCase(endPointRef))
	    url = getConfiguredUrl("/WSDL/PROD/RewardZone/RWZ_Points/ValidateRZMemberServiceSoap.wsdl");
	else if ("QA".equalsIgnoreCase(endPointRef))
	    url = getConfiguredUrl("/WSDL/QA/RewardZone/RWZ_Points/ValidateRZMemberServiceSoap.wsdl");
	else
	    url = getURL(endPointRef);

	logger.info("RewardZone Points Lookup SERVICE URL : " + url);
	ValidateRZMemberSOAP validateRZMemberSOAP = new ValidateRZMemberSOAP(url, new QName(
		"http://webservices.bestbuy.com/rzServices/service", "ValidateRZMemberSOAP"));
	ValidateRZMember validateRZMember = validateRZMemberSOAP.getValidateRZMember();
	insertMsgHandler((BindingProvider) validateRZMember);
	setTimeouts((BindingProvider) validateRZMember, timeout);
	logger.info("Creating the RewardZone Points Lookup Service");
	return validateRZMember;
    }

    protected RZCertService getRewardZoneCertsLookupService() throws ServiceException {
	logger.info("Creating the RewardZone CertsLookup Proxy Service");
	URL url = null;
	String endPointRef = drpPropertiesService.getProperty("RWZ_CERT_WS_URL");
	int timeout = getTimeout("RWZ_CERT_WS_TIMEOUT");
	if ("PROD".equalsIgnoreCase(endPointRef))
	    url = getConfiguredUrl("/WSDL/PROD/RewardZone/RWZ_Certs/RZCertificates.wsdl");
	else if ("QA".equalsIgnoreCase(endPointRef))
	    url = getConfiguredUrl("/WSDL/QA/RewardZone/RWZ_Certs/RZCertificates.wsdl");
	else
	    url = getURL(endPointRef);

	logger.info("RewardZone CertsLookup SERVICE URL : " + url);
	RZCertServiceSOAP rzCertServiceSOAP = new RZCertServiceSOAP(url, new QName(
		"http://webservices.bestbuy.com/rewardzone/service", "RZCertServiceSOAP"));
	RZCertService rzCertService = rzCertServiceSOAP.getRZCertService();
	insertMsgHandler((BindingProvider) rzCertService);
	setTimeouts((BindingProvider) rzCertService, timeout);
	logger.info("Created the RewardZone CertsLookup Proxy Service");
	return rzCertService;
    }

    protected ManageManifestPortType getManageManifestService() throws ServiceException {
	logger.info("Creating the Manage Manifest Proxy Service");
	URL url = null;
	String endPointRef = drpPropertiesService.getProperty("ACDS_WS_URL");
	int timeout = getTimeout("ACDS_WS_TIMEOUT");
	if ("PROD".equalsIgnoreCase(endPointRef))
	    url = getConfiguredUrl("/WSDL/PROD/ACDS/ManageManifest_v01.serviceagent.wsdl");
	else if ("QA".equalsIgnoreCase(endPointRef))
	    url = getConfiguredUrl("/WSDL/QA/ACDS/ManageManifest_v01.serviceagent.wsdl");
	else
	    url = getURL(endPointRef);

	logger.info("ACDS URL : " + url);
	ManageManifestV01Serviceagent acds = new ManageManifestV01Serviceagent(url, new QName(
		"http://www.tsh.bestbuy.com/BusinessServices/Proxy/ManageManifest", "ManageManifest_v01.serviceagent"));
	ManageManifestPortType acdsPort = acds.getManageManifestPortType();
	insertMsgHandler((BindingProvider) acdsPort);
	setTimeouts((BindingProvider) acdsPort, timeout);
	logger.info("Created the Manage Manifest Proxy Service");
	return acdsPort;
    }

    protected ACDSParamPortType getACDSParamService() throws ServiceException {
	logger.info("Creating the ACDS Param Proxy Service");
	URL url = null;
	String endPointRef = drpPropertiesService.getProperty("ACDS_Param_WS_URL");
	int timeout = getTimeout("ACDS_Param_WS_TIMEOUT");
	if ("PROD".equalsIgnoreCase(endPointRef))
	    url = getConfiguredUrl("/WSDL/PROD/ACDSParam/ACDS_Param_v01.serviceagent.wsdl");
	else if ("QA".equalsIgnoreCase(endPointRef))
	    url = getConfiguredUrl("/WSDL/QA/ACDSParam/ACDS_Param_v01.serviceagent.wsdl");
	else
	    url = getURL(endPointRef);

	logger.info("ACDS Param URL : " + url);
	ACDSParamServiceagent acdsParam = new ACDSParamServiceagent(url, new QName(
		"http://www.tsh.bestbuy.com/bbym/logistics/shipping/acds_param/v1", "ACDS_Param.serviceagent"));
	ACDSParamPortType acdsParamPort = acdsParam.getACDSParamPortType();
	insertMsgHandler((BindingProvider) acdsParamPort);
	setTimeouts((BindingProvider) acdsParamPort, timeout);
	logger.info("Created the ACDS Param Proxy Service");
	return acdsParamPort;
    }

    protected ShippingPortType getACDSShippingService() throws ServiceException {
	logger.info("Creating ACDS Shipping Service to close manifest and generating UPS label");
	URL url = null;
	String endPointRef = drpPropertiesService.getProperty("ACDS_Shipping_WS_URL");
	int timeout = getTimeout("ACDS_Shipping_WS_TIMEOUT");
	if ("PROD".equalsIgnoreCase(endPointRef))
	    url = getConfiguredUrl("/WSDL/PROD/ACDSShipping/Shipping_v01.serviceagent.wsdl");
	else if ("QA".equalsIgnoreCase(endPointRef))
	    url = getConfiguredUrl("/WSDL/QA/ACDSShipping/Shipping_v01.serviceagent.wsdl");
	else
	    url = getURL(endPointRef);
	logger.info("ACDS Shipping Service URL : " + url);
	ShippingV01Serviceagent acdsShip = new ShippingV01Serviceagent(url, new QName(
		"http://www.tsh.bestbuy.com/BusinessServices/Proxy/Shipping", "Shipping_v01.serviceagent"));
	ShippingPortType acdsShipPort = acdsShip.getShippingPortType();
	insertMsgHandler((BindingProvider) acdsShipPort);
	setTimeouts((BindingProvider) acdsShipPort, timeout);
	logger.info("Created the ACDS Shipping Proxy Service");
	return acdsShipPort;
    }

    protected ManageDevicePortType getACDSDeviceSearchService() throws ServiceException {
	logger.info("Creating ACDS Device Search Service call");
	URL url = null;
	String endPointRef = drpPropertiesService.getProperty("ACDS_Device_WS_URL");
	int timeout = getTimeout("ACDS_Device_WS_TIMEOUT");
	if ("PROD".equalsIgnoreCase(endPointRef))
	    url = getConfiguredUrl("/WSDL/PROD/ACDSDeviceSearch/ManageDevice.serviceagent.wsdl");
	else if ("QA".equalsIgnoreCase(endPointRef))
	    url = getConfiguredUrl("/WSDL/QA/ACDSDeviceSearch/ManageDevice.serviceagent.wsdl");
	else
	    url = getURL(endPointRef);
	logger.info("ACDS Device Search Service URL : " + url);
	ManageDeviceServiceagent acdsDeviceSearch = new ManageDeviceServiceagent(url, new QName(
		"http://www.tsh.bestbuy.com/BusinessServices/Proxy/ManageDevice/v1", "ManageDevice.serviceagent"));
	ManageDevicePortType acdsDeviceSearchPort = acdsDeviceSearch.getManageDevicePortType();
	insertMsgHandler((BindingProvider) acdsDeviceSearchPort);
	setTimeouts((BindingProvider) acdsDeviceSearchPort, timeout);
	logger.info("Created the ACDS Device Search Proxy Service");
	return acdsDeviceSearchPort;
    }

    protected UpdateDeviceStatusPortType getACDSUpdateDeviceService() throws ServiceException {
	logger.info("Creating ACDS Update Device Service call");
	URL url = null;
	String endPointRef = drpPropertiesService.getProperty("ACDS_UPDATE_DEVICE_WS_URL");
	int timeout = getTimeout("ACDS_UPDATE_DEVICE_WS_TIMEOUT");
	if ("PROD".equalsIgnoreCase(endPointRef))
	    url = getConfiguredUrl("/WSDL/PROD/ACDS_UpdateDevice/UpdateDeviceStatus.serviceagent.wsdl");
	else if ("QA".equalsIgnoreCase(endPointRef))
	    url = getConfiguredUrl("/WSDL/QA/ACDS_UpdateDevice/UpdateDeviceStatus.serviceagent.wsdl");
	else
	    url = getURL(endPointRef);
	logger.info("ACDS Device Update Service URL : " + url);
	UpdateDeviceStatusServiceagent acdsDeviceSearch = new UpdateDeviceStatusServiceagent(url, new QName(
		"http://www.tsh.bestbuy.com/bbym/logistics/UpdateDeviceStatus/v1", "UpdateDeviceStatus.serviceagent"));
	UpdateDeviceStatusPortType acdsUpdateDevicePort = acdsDeviceSearch.getUpdateDeviceStatus();
	insertMsgHandler((BindingProvider) acdsUpdateDevicePort);
	setTimeouts((BindingProvider) acdsUpdateDevicePort, timeout);
	logger.info("Created the ACDS Device Search Proxy Service");
	return acdsUpdateDevicePort;
    }

    protected ProtectionPlanSOAP getHubService() throws ServiceException {
	logger.info("Creating the Hub Proxy Service");
	URL url = null;
	String endPointRef = drpPropertiesService.getProperty("HUB_WS_URL");
	int timeout = getTimeout("HUB_WS_TIMEOUT");
	if ("PROD".equalsIgnoreCase(endPointRef))
	    url = getConfiguredUrl("/WSDL/PROD/HUB/ProtectionPlanServiceV4Soap.wsdl");
	else if ("QA".equalsIgnoreCase(endPointRef))
	    url = getConfiguredUrl("/WSDL/QA/HUB/ProtectionPlanServiceV4Soap.wsdl");
	else
	    url = getURL(endPointRef);

	logger.info("HUB URL : " + url);
	ProtectionPlanSOAP_Service service = new ProtectionPlanSOAP_Service(url, new QName(
		"http://webservices.bestbuy.com/ProtectionHub/ProtectionPlan/Service/V4", "ProtectionPlanSOAP"));
	ProtectionPlanSOAP hubService = service.getProtectionPlanSOAP();
	insertMsgHandler((BindingProvider) hubService);
	setTimeouts((BindingProvider) hubService, timeout);
	logger.info("Created the Hub Proxy and sending the HUB request");
	return hubService;
    }

    protected CAPWsController getCapWsController() throws ServiceException {
	logger.info("Creating the CAP Proxy Service");
	URL url = null;
	String endPointRef = drpPropertiesService.getProperty("CAP_WS_URL");
	int timeout = getTimeout("CAP_WS_TIMEOUT");
	if ("PROD".equalsIgnoreCase(endPointRef))
	    url = getConfiguredUrl("/WSDL/PROD/CAP/CAPWsController.wsdl");
	else if ("QA".equalsIgnoreCase(endPointRef))
	    url = getConfiguredUrl("/WSDL/QA/CAP/CAPWsController.wsdl");
	else
	    url = getURL(endPointRef);

	logger.info("CAP URL : " + url);
	CAPWsControllerService capControllerService = new CAPWsControllerService(url, new QName(
		"http://bestbuy.com/bbym/beast/cap", "CAPWsControllerService"));
	CAPWsController capController = capControllerService.getCAPWsController();
	insertMsgHandler((BindingProvider) capController);
	setTimeouts((BindingProvider) capController, timeout);
	logger.info("Created the CAP Proxy and sending the CAP request");
	return capController;
    }

    protected CustomerAccountServicesPortType getCustomerAccountServices() throws ServiceException {
	logger.info("Creating the TSH CA proxy Service");
	URL url = null;
	String endPointRef = drpPropertiesService.getProperty("CA_WS_URL");
	int timeout = getTimeout("CA_WS_TIMEOUT");
	if ("PROD".equalsIgnoreCase(endPointRef))
	    url = getConfiguredUrl("/WSDL/PROD/CA/CustomerAccountServicesV2.wsdl");
	else if ("QA".equalsIgnoreCase(endPointRef))
	    url = getConfiguredUrl("/WSDL/QA/CA/CustomerAccountServicesV2.wsdl");
	else
	    url = getURL(endPointRef);

	logger.info("CA URL : " + url);
	CAServicesSOAPV2 caServicesSOAPV2 = new CAServicesSOAPV2(url, new QName(
		"http://webservices.bestbuy.com/ca/services/v2", "CAServicesSOAPV2"));
	CustomerAccountServicesPortType customerAccountServicesPort = caServicesSOAPV2.getCustomerAccountServices();
	insertMsgHandler((BindingProvider) customerAccountServicesPort);
	setTimeouts((BindingProvider) customerAccountServicesPort, timeout);
	logger.info("Creating the TSH CA proxy Service");
	return customerAccountServicesPort;
    }

    protected ElectronicJournalServices getEJServices() throws ServiceException {
	logger.info("");
	URL url = null;
	String endPointRef = drpPropertiesService.getProperty("EJ_WS_URL");
	int timeout = getTimeout("EJ_WS_TIMEOUT");
	if ("PROD".equalsIgnoreCase(endPointRef))
	    url = getConfiguredUrl("/WSDL/PROD/EJ/EJ.wsdl");
	else if ("QA".equalsIgnoreCase(endPointRef))
	    url = getConfiguredUrl("/WSDL/QA/EJ/EJ.wsdl");
	else
	    url = getURL(endPointRef);

	logger.info("EJ URL : " + url);
	EJournal eJournal = new EJournal(url, new QName("http://bestbuy.com/EJournal", "EJournal"));
	ElectronicJournalServices electronicJournalServices = eJournal.getElectronicJournalServicesSoap();
	insertMsgHandler((BindingProvider) electronicJournalServices);
	setTimeouts((BindingProvider) electronicJournalServices, timeout);
	return electronicJournalServices;
    }

    protected PortType getTshEcService() throws ServiceException {
	logger.info("Creating the TSH EC Proxy Service");
	URL url = null;
	String endPointRef = drpPropertiesService.getProperty("EC_WS_URL");
	int timeout = getTimeout("EC_WS_TIMEOUT");
	if ("PROD".equalsIgnoreCase(endPointRef))
	    url = getConfiguredUrl("/WSDL/PROD/EC/SearchTransactionServiceSoap.wsdl");
	else if ("QA".equalsIgnoreCase(endPointRef))
	    url = getConfiguredUrl("/WSDL/QA/EC/SearchTransactionServiceSoap.wsdl");
	else
	    url = getURL(endPointRef);

	logger.info("TSH EC URL : " + url);
	SearchTransactionService service = new SearchTransactionService(url, new QName(
		"http://xmlns.example.com/1299800422009", "SearchTransactionService"));
	PortType ecService = service.getSearchTransactionSOAPHTTP();
	insertMsgHandler((BindingProvider) ecService);
	setTimeouts((BindingProvider) ecService, timeout);
	logger.info("Created the TSH EC proxy service");
	return ecService;
    }

    /*
     * TSH - Mobile Entitlements, Carrier Return Rules and Tradeablility Check. 
     */
    protected EntitlementCheckPortType getMECService() throws ServiceException {
	logger.info("Creating the TSH MEC Proxy Service");
	URL url = null;
	String endPointRef = drpPropertiesService.getProperty("MEC_WS_URL");
	int timeout = getTimeout("MEC_WS_TIMEOUT");
	if ("PROD".equalsIgnoreCase(endPointRef))
	    url = getConfiguredUrl("/WSDL/PROD/MEC/Mobile_Entitlement_CPC.wsdl");
	else if ("QA".equalsIgnoreCase(endPointRef))
	    url = getConfiguredUrl("/WSDL/QA/MEC/Mobile_Entitlement_CPC.wsdl");
	else
	    url = getURL(endPointRef);

	logger.info("TSH MEC URL : " + url);
	EntitlementCheckServiceServiceagent service = new EntitlementCheckServiceServiceagent(url, new QName(
		"http://www.bestbuy.com/bbym/orders/EntitlementCheckService", "EntitlementCheckService.serviceagent"));
	EntitlementCheckPortType mecService = service.getEntitlementCheckPortTypeEndpoint();
	insertMsgHandler((BindingProvider) mecService);
	setTimeouts((BindingProvider) mecService, timeout);
	logger.info("Created the TSH MEC proxy service");
	return mecService;
    }

    protected TransactionCheckServicePortType getTradabilityCheckService() throws ServiceException {
	logger.info("Creating the TSH Tradability Check Proxy Service");
	URL url = null;
	String endPointRef = drpPropertiesService.getProperty("TRADABILITY_WS_URL");
	int timeout = getTimeout("TRADABILITY_WS_TIMEOUT");
	if ("PROD".equalsIgnoreCase(endPointRef))
	    url = getConfiguredUrl("/WSDL/PROD/TradabilityCheck/TradeInCheckService.wsdl");
	else if ("QA".equalsIgnoreCase(endPointRef))
	    url = getConfiguredUrl("/WSDL/QA/TradabilityCheck/TradeInCheckService.wsdl");
	else
	    url = getURL(endPointRef);

	logger.info("TSH Tradability Check URL : " + url);
	TradeInCheckServiceServiceagent service = new TradeInCheckServiceServiceagent(url, new QName(
		"http://www.tsh.bestbuy.com/BusinessServices/Proxy/TradeInCheck", "TradeInCheckService.serviceagent"));
	TransactionCheckServicePortType tradabilityCheckService = service.getTransactionCheckServicePortTypeEndpoint1();
	insertMsgHandler((BindingProvider) tradabilityCheckService);
	setTimeouts((BindingProvider) tradabilityCheckService, timeout);
	logger.info("Created the TradabilityCheck proxy service");
	return tradabilityCheckService;
    }

    protected RetrieveLocationsPortType getRetrieveLocationService() throws ServiceException {
	logger.info("Creating the TSH Retrieve Location Service");
	URL url = null;
	String endPointRef = drpPropertiesService.getProperty("TSH_LOCATION_WS_URL");
	int timeout = getTimeout("TSH_LOCATION_WS_TIMEOUT");
	if ("PROD".equalsIgnoreCase(endPointRef))
	    url = getConfiguredUrl("/WSDL/PROD/TSH_Location/RetrieveLocationServiceSoap.wsdl");
	else if ("QA".equalsIgnoreCase(endPointRef))
	    url = getConfiguredUrl("/WSDL/QA/TSH_Location/RetrieveLocationServiceSoap.wsdl");
	else
	    url = getURL(endPointRef);

	logger.info("TSH Location Check URL : " + url);
	RetrieveLocationsServiceServiceagent service = new RetrieveLocationsServiceServiceagent(url, new QName(
		"http://tsh.bestbuy.com/facilities/location/retrievelocationsservice/v2",
		"RetrieveLocationsService.serviceagent"));
	RetrieveLocationsPortType retrieveLocationService = service.getRetrieveLocationsPortType();
	insertMsgHandler((BindingProvider) retrieveLocationService);
	setTimeouts((BindingProvider) retrieveLocationService, timeout);
	logger.info("Created the Retrieve Location proxy service");
	return retrieveLocationService;
    }

    protected IdentityServicesImpl getIdentityService() throws ServiceException {
	logger.info("Creating the OpenSSO Proxy Service");
	URL url = null;
	String endPointRef = drpPropertiesService.getProperty("OPENSSO_WS_URL");
	int timeout = getTimeout("OPENSSO_WS_TIMEOUT");
	if ("PROD".equalsIgnoreCase(endPointRef))
	    url = getConfiguredUrl("/WSDL/PROD/OpenSSO/identityservices.wsdl");
	else if ("QA".equalsIgnoreCase(endPointRef))
	    url = getConfiguredUrl("/WSDL/QA/OpenSSO/identityservices.wsdl");
	else
	    url = getURL(endPointRef);

	logger.info("OpenSSO URL : " + url);
	IdentityServicesImplService service = new IdentityServicesImplService(url, new QName(
		"http://opensso.idsvcs.identity.sun.com/", "IdentityServicesImplService"));
	IdentityServicesImpl identityService = service.getIdentityServicesImplPort();
	insertMsgHandler((BindingProvider) identityService);
	setTimeouts((BindingProvider) identityService, timeout);
	logger.info("Created the OpenSSO Proxy service");
	return identityService;
    }

    /**
     * Gets the timeout setting for the given property.
     * <p>
     * If the property has not been defined in the database it will try to use
     * the <i>DEFAULT_WS_TIMEOUT</i> property value. If that doesn't exist it
     * will use 60 seconds.
     * </p>
     * 
     * @return the timeout (in seconds)
     */
    protected int getTimeout(String propertyName) {
	try{
	    String interval = drpPropertiesService.getProperty(propertyName);
	    int timeoutSeconds = Util.getInt(interval, -1);
	    if (timeoutSeconds != -1){
		return timeoutSeconds;
	    }
	}catch(ServiceException se){
	    // Ignore. We'll use the default if the property has not been
	    // defined in the database or it could not be converted to an int
	}
	logger.warn("Using default timeout value for property: " + propertyName);
	final int DEFAULT_WS_TIMEOUT = 60;
	try{
	    String interval = drpPropertiesService.getProperty("DEFAULT_WS_TIMEOUT");
	    return Util.getInt(interval, DEFAULT_WS_TIMEOUT);
	}catch(ServiceException se){
	    return DEFAULT_WS_TIMEOUT;
	}
    }

    /**
     * Sets the web service client timeouts.
     */
    private void setTimeouts(BindingProvider bindingProvider, int timeoutSeconds) {
	Map<String, Object> requestContext = bindingProvider.getRequestContext();
	int timeoutMilliseconds = timeoutSeconds * 1000;
	requestContext.put(JAXWSProperties.CONNECT_TIMEOUT, timeoutMilliseconds);
	requestContext.put(JAXWSProperties.REQUEST_TIMEOUT, timeoutMilliseconds);
    }

    /**
     * Gets a HTTP client.
     */
    protected final HttpClient getHttpClient(int timeoutSeconds) {
	HttpClient httpClient = new HttpClient();
	int timeoutMilliseconds = timeoutSeconds * 1000;
	httpClient.setConnectionTimeout(timeoutMilliseconds);
	return httpClient;
    }

    /**
     * Responsible for enforcing consistent handling of exceptions returned when
     * calling external systems
     */
    protected final void handleException(String callerInfo, Throwable t) throws ServiceException, IseBusinessException {
	if (t instanceof ServiceException){
	    throw (ServiceException) t;
	}
	if (t instanceof IseBusinessException){
	    throw (IseBusinessException) t;
	}
	logger.error(DrpConstants.EXTERNAL_SERVICE_CALL_ERROR + ' ' + callerInfo, t);
	throw new ServiceException(IseExceptionCodeEnum.ExternalServiceException, t.getMessage());
    }

    protected final Element getSamlSecurityHeader() {

	String samlToken = "PHNhbWw6QXNzZXJ0aW9uIEFzc2VydGlvbklEPSJ5Um1nM3hNWmJEeFUxaHhXaE1VM2pPQ2prOFciIElzc3Vlcj0iQmVzdEJ1eVByb2QiIElzc3VlSW5zdGFudD0iMjAxMi0wOC0yN1QxNTo1Mzo0Ni42OTVaIiBNaW5vclZlcnNpb249IjEiIE1ham9yVmVyc2lvbj0iMSIgeG1sbnM6c2FtbD0idXJuOm9hc2lzOm5hbWVzOnRjOlNBTUw6MS4wOmFzc2VydGlvbiI+PHNhbWw6Q29uZGl0aW9ucyBOb3RPbk9yQWZ0ZXI9IjIwMTMtMDgtMjdUMTU6NTM6NDYuNjk1WiIgTm90QmVmb3JlPSIyMDEyLTA4LTI3VDE1OjQ4OjQ2LjY5NVoiPjxzYW1sOkF1ZGllbmNlUmVzdHJpY3Rpb25Db25kaXRpb24+PHNhbWw6QXVkaWVuY2U+UEFSVE5FUlRPS0VOPC9zYW1sOkF1ZGllbmNlPjwvc2FtbDpBdWRpZW5jZVJlc3RyaWN0aW9uQ29uZGl0aW9uPjwvc2FtbDpDb25kaXRpb25zPjxzYW1sOkF1dGhlbnRpY2F0aW9uU3RhdGVtZW50IEF1dGhlbnRpY2F0aW9uTWV0aG9kPSJ1cm46b2FzaXM6bmFtZXM6dGM6U0FNTDoxLjA6YW06dW5zcGVjaWZpZWQiIEF1dGhlbnRpY2F0aW9uSW5zdGFudD0iMjAxMi0wOC0yN1QxNTo1Mzo0Ni42OTVaIj48c2FtbDpTdWJqZWN0PjxzYW1sOk5hbWVJZGVudGlmaWVyPklTRXx8MjAxMzA4MjcxNTUzNDc8L3NhbWw6TmFtZUlkZW50aWZpZXI+PHNhbWw6U3ViamVjdENvbmZpcm1hdGlvbj48c2FtbDpDb25maXJtYXRpb25NZXRob2Q+dXJuOm9hc2lzOm5hbWVzOnRjOlNBTUw6MS4wOmNtOnNlbmRlci12b3VjaGVzPC9zYW1sOkNvbmZpcm1hdGlvbk1ldGhvZD48L3NhbWw6U3ViamVjdENvbmZpcm1hdGlvbj48L3NhbWw6U3ViamVjdD48L3NhbWw6QXV0aGVudGljYXRpb25TdGF0ZW1lbnQ+PHNhbWw6QXR0cmlidXRlU3RhdGVtZW50PjxzYW1sOlN1YmplY3Q+PHNhbWw6TmFtZUlkZW50aWZpZXI+SVNFfHwyMDEzMDgyNzE1NTM0Nzwvc2FtbDpOYW1lSWRlbnRpZmllcj48c2FtbDpTdWJqZWN0Q29uZmlybWF0aW9uPjxzYW1sOkNvbmZpcm1hdGlvbk1ldGhvZD51cm46b2FzaXM6bmFtZXM6dGM6U0FNTDoxLjA6Y206c2VuZGVyLXZvdWNoZXM8L3NhbWw6Q29uZmlybWF0aW9uTWV0aG9kPjwvc2FtbDpTdWJqZWN0Q29uZmlybWF0aW9uPjwvc2FtbDpTdWJqZWN0PjxzYW1sOkF0dHJpYnV0ZSBBdHRyaWJ1dGVOYW1lc3BhY2U9Im5zOnVuc3BlY2lmaWVkIiBBdHRyaWJ1dGVOYW1lPSJQYXJ0bmVyQXBwSUQiPjxzYW1sOkF0dHJpYnV0ZVZhbHVlPklTRTwvc2FtbDpBdHRyaWJ1dGVWYWx1ZT48L3NhbWw6QXR0cmlidXRlPjxzYW1sOkF0dHJpYnV0ZSBBdHRyaWJ1dGVOYW1lc3BhY2U9Im5zOnVuc3BlY2lmaWVkIiBBdHRyaWJ1dGVOYW1lPSJQYXJ0bmVyQXBwUm9sZSI+PHNhbWw6QXR0cmlidXRlVmFsdWU+QXBpQWdlbnQ8L3NhbWw6QXR0cmlidXRlVmFsdWU+PC9zYW1sOkF0dHJpYnV0ZT48c2FtbDpBdHRyaWJ1dGUgQXR0cmlidXRlTmFtZXNwYWNlPSJuczp1bnNwZWNpZmllZCIgQXR0cmlidXRlTmFtZT0iVXNlclJvbGVWYWx1ZSI+PHNhbWw6QXR0cmlidXRlVmFsdWU+U2VydmljZUFjY291bnQ8L3NhbWw6QXR0cmlidXRlVmFsdWU+PC9zYW1sOkF0dHJpYnV0ZT48L3NhbWw6QXR0cmlidXRlU3RhdGVtZW50PjxkczpTaWduYXR1cmUgeG1sbnM6ZHM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvMDkveG1sZHNpZyMiPgo8ZHM6U2lnbmVkSW5mbz4KPGRzOkNhbm9uaWNhbGl6YXRpb25NZXRob2QgQWxnb3JpdGhtPSJodHRwOi8vd3d3LnczLm9yZy8yMDAxLzEwL3htbC1leGMtYzE0biMiIC8+CjxkczpTaWduYXR1cmVNZXRob2QgQWxnb3JpdGhtPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwLzA5L3htbGRzaWcjcnNhLXNoYTEiIC8+CjxkczpSZWZlcmVuY2UgVVJJPSIjeVJtZzN4TVpiRHhVMWh4V2hNVTNqT0NqazhXIj4KPGRzOlRyYW5zZm9ybXM+CjxkczpUcmFuc2Zvcm0gQWxnb3JpdGhtPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwLzA5L3htbGRzaWcjZW52ZWxvcGVkLXNpZ25hdHVyZSIgLz4KPGRzOlRyYW5zZm9ybSBBbGdvcml0aG09Imh0dHA6Ly93d3cudzMub3JnLzIwMDEvMTAveG1sLWV4Yy1jMTRuIyIgLz4KPC9kczpUcmFuc2Zvcm1zPgo8ZHM6RGlnZXN0TWV0aG9kIEFsZ29yaXRobT0iaHR0cDovL3d3dy53My5vcmcvMjAwMC8wOS94bWxkc2lnI3NoYTEiIC8+CjxkczpEaWdlc3RWYWx1ZT5uQVp0Rzg1czA5aUhHaVkvNm1NZm9PWXBOekU9PC9kczpEaWdlc3RWYWx1ZT4KPC9kczpSZWZlcmVuY2U+CjwvZHM6U2lnbmVkSW5mbz4KPGRzOlNpZ25hdHVyZVZhbHVlPgpZeGNpNStINHVncVg4NU0xN2gvdmVhSUZDUmxRc1pGZ0hOb1BwckZvZnM0YSs4ZG5oK0xEb0Q3QzNWVDg5T3JoQXczY0w1ZEFseHptClZQZHRwU2xMeWowc3hPeHRmSmppMk1pdG42UVVFSk82aEdPclhxdWVobGRYZ3llUXZaeUo0bld4bnMwTVoxb0crcFBuVjZMUkRxNmgKaHBIcnhZaERuYXlmeDRGUmZlVUk0YldrV3ZEamlzRURxNEpmOUR1Mkhob0JiUUUvK1dJL2xINlkyeHBuZ0d6bGdYbWdSQ3lwcGQ1cgo5WUhXeFhwdTNlenJXS09sZWtGZm5CUGlHOGhGTlpYOVNVMnFzK3ExdnBTL3ZMSFF3NTQ0aXh2YUJ3R3RwRHduSXVYaDBaVUlwUHI4Ck1NMzBORGdQZnppZ21VN3B3NFN2R3hxc05iODV5azBMYU15czZBPT0KPC9kczpTaWduYXR1cmVWYWx1ZT4KPGRzOktleUluZm8+CjxkczpYNTA5RGF0YT4KPGRzOlg1MDlDZXJ0aWZpY2F0ZT4KTUlJSFVUQ0NCam1nQXdJQkFnSUtWTjhiS1FBQ0FBQUNVVEFOQmdrcWhraUc5dzBCQVFVRkFEQmJNUXN3Q1FZRFZRUUdFd0pWVXpFVQpNQklHQTFVRUNoTUxRbVZ6ZENCQ2RYa2dRMjh4Q3pBSkJnTlZCQXNUQWtsVE1Ta3dKd1lEVlFRREV5QkNaWE4wSUVKMWVTQkZiblJsCmNuQnlhWE5sSUVsemMzVnBibWNnUkVVd05qQWVGdzB4TWpBNE1EZ3hOelEyTkRaYUZ3MHhOREE0TURneE56UTJORFphTUlHTk1Rc3cKQ1FZRFZRUUdFd0pWVXpFTE1Ba0dBMVVFQ0JNQ1RVNHhGREFTQmdOVkJBY1RDMEpzYjI5dGFXNW5kRzl1TVJZd0ZBWURWUVFLRXcxUQphVzVuSUVabFpHVnlZWFJsTVNBd0hnWURWUVFMREJkc1pHRndjM1Z3Y0c5eWRFQmlaWE4wWW5WNUxtTnZiVEVoTUI4R0ExVUVBeE1ZCmRHOXJaVzV6WlhKMmFXTmxMbUpsYzNSaWRYa3VZMjl0TUlJQklqQU5CZ2txaGtpRzl3MEJBUUVGQUFPQ0FROEFNSUlCQ2dLQ0FRRUEKaFNJejVNcGxlb3pGYWxtbllZdmVpVFMwSXhFWHV1UTJxNC9yTERWeE94eGJvTTI1dDJjSGh0WkxYdkpwL1QyeWRveWRMMExWT0o2YQpKWUljY3g4YmMyUHRqUW9pTHN6WEN5Q1FjUE5aUWNUZFpLVXJXVUI0R28zcms1TDNFcVNmZnNseFhwcVk1dHBOcmJKNmloR0VYTzNrCnZtRUtCY3ZEVW5kY2NOWXlXN2ZKb0paQm41RkJyMWMxK04wcEo5ejlvNFErUWlqdFpCcXczVjVpQnI2VGg5MU9BRTVrUEcrcTI2YSsKRW5PcXY5MGZvZXZ5cW1vL1J4UXlYaFhzU2JHNjVCQ1NVdlkzc2kxcWRsVCtWREZXZVFwNkFCZUwyOTRJV2U4MGptbGNPQ3dkeW1CbQpBRDNzSnBSem02Q1hWTmVWWkxNMDNmMmUzMTZTamdTdFdkUFZyd0lEQVFBQm80SUQ0akNDQTk0d0hRWURWUjBPQkJZRUZKank3WmV2CnYwMmRZTTk1alUzaWhuQkZHTXVQTUI4R0ExVWRJd1FZTUJhQUZEc3I1Y0V6YnI1cHlMQlVxZlloS2k4REd0d1ZNSUlCUndZRFZSMGYKQklJQlBqQ0NBVG93Z2dFMm9JSUJNcUNDQVM2R2dkQnNaR0Z3T2k4dkwwTk9QVUpsYzNRbE1qQkNkWGtsTWpCRmJuUmxjbkJ5YVhObApKVEl3U1hOemRXbHVaeVV5TUVSRk1EWXNRMDQ5WkhOd01EWmpaWEowTEVOT1BVTkVVQ3hEVGoxUWRXSnNhV01sTWpCTFpYa2xNakJUClpYSjJhV05sY3l4RFRqMVRaWEoyYVdObGN5eERUajFEYjI1bWFXZDFjbUYwYVc5dUxFUkRQV0ppZVdSdGVpeEVRejFqYjIwL1kyVnkKZEdsbWFXTmhkR1ZTWlhadlkyRjBhVzl1VEdsemREOWlZWE5sUDI5aWFtVmpkRU5zWVhOelBXTlNURVJwYzNSeWFXSjFkR2x2YmxCdgphVzUwaGxsb2RIUndPaTh2WW1KNVkyVnlkR0YxZEdodmNtbDBlUzVpWlhOMFluVjVMbU52YlM5RFpYSjBSR0YwWVM5Q1pYTjBKVEl3ClFuVjVKVEl3Ulc1MFpYSndjbWx6WlNVeU1FbHpjM1ZwYm1jbE1qQkVSVEEyTG1OeWJEQ0NBVm9HQ0NzR0FRVUZCd0VCQklJQlREQ0MKQVVnd2djWUdDQ3NHQVFVRkJ6QUNob0c1YkdSaGNEb3ZMeTlEVGoxQ1pYTjBKVEl3UW5WNUpUSXdSVzUwWlhKd2NtbHpaU1V5TUVsegpjM1ZwYm1jbE1qQkVSVEEyTEVOT1BVRkpRU3hEVGoxUWRXSnNhV01sTWpCTFpYa2xNakJUWlhKMmFXTmxjeXhEVGoxVFpYSjJhV05sCmN5eERUajFEYjI1bWFXZDFjbUYwYVc5dUxFUkRQV0ppZVdSdGVpeEVRejFqYjIwL1kwRkRaWEowYVdacFkyRjBaVDlpWVhObFAyOWkKYW1WamRFTnNZWE56UFdObGNuUnBabWxqWVhScGIyNUJkWFJvYjNKcGRIa3dmUVlJS3dZQkJRVUhNQUtHY1doMGRIQTZMeTlpWW5sagpaWEowWVhWMGFHOXlhWFI1TG1KbGMzUmlkWGt1WTI5dEwwTmxjblJFWVhSaEwyUnpjREEyWTJWeWRDNWlZbmxrYlhvdVkyOXRYMEpsCmMzUWxNakJDZFhrbE1qQkZiblJsY25CeWFYTmxKVEl3U1hOemRXbHVaeVV5TUVSRk1EWW9NaWt1WTNKME1Bd0dBMVVkRXdFQi93UUMKTUFBd0N3WURWUjBQQkFRREFnV2dNRDRHQ1NzR0FRUUJnamNWQndReE1DOEdKeXNHQVFRQmdqY1ZDSU9ibGcrQjhwOUFoTDJiTm9YSQptSDJCcE5SdmdSdUY0Ymt0ZzVIZ0d3SUJaQUlCQmpBVEJnTlZIU1VFRERBS0JnZ3JCZ0VGQlFjREFUQm1CZ05WSFNBRVh6QmRNRnNHCkNXQ0dTQUdHL2c0QkF6Qk9NRXdHQ0NzR0FRVUZCd0lCRmtCb2RIUndPaTh2UWtKWlEyVnlkRUYxZEdodmNtbDBlUzVDUWxsRVRWb3UKWTI5dEwwSmxjM1FsTWpBbFFuVjVKVEl3SlZCTFNTVXlNQ1ZEVUZNdWFIUnRNQnNHQ1NzR0FRUUJnamNWQ2dRT01Bd3dDZ1lJS3dZQgpCUVVIQXdFd0RRWUpLb1pJaHZjTkFRRUZCUUFEZ2dFQkFHQVY1aGE0MmVYUUd2L0RyNWx0QjRHS25lNk82TC9TUEhQNGZkejF4OU5QCno0MmxlNXBqM0RFTUtEamJKb29nQzNUczNiZTVoWnJyM2NoUGRwenJuV3VLcEd1Q29ZQU5vNVgvRDFzM2k5Z0tEeHFYdWp6UmpLaVkKbUdrQ29hRnd5b003VEFJWVE2N3YwY0Yxd2IvL1E1WlgvUmNzRVJhVS9iTXVMR1IzR05MZUZiUFZ0STZLRWNDa3IybzVaUGxHemxZSwpWMzBFN01jcEh6K0xqVXVBY0VTSENYdk9LbGFTaXdqUFFIc21UUUo0U2dWWGI3V1JVelM0VllCN2R6UU5mMzBNVzB0NGswODhwZFp0CjFJU0xacE9ENk5ZbjJKK1ZLQXFrbzVJelBycGJrOVNyY04wVXEyU2VkSE11OGVGUlF5aVJueUVtTlFiOG4xMDk1ZkxBdVlZPQo8L2RzOlg1MDlDZXJ0aWZpY2F0ZT4KPC9kczpYNTA5RGF0YT4KPGRzOktleVZhbHVlPgo8ZHM6UlNBS2V5VmFsdWU+CjxkczpNb2R1bHVzPgpoU0l6NU1wbGVvekZhbG1uWVl2ZWlUUzBJeEVYdXVRMnE0L3JMRFZ4T3h4Ym9NMjV0MmNIaHRaTFh2SnAvVDJ5ZG95ZEwwTFZPSjZhCkpZSWNjeDhiYzJQdGpRb2lMc3pYQ3lDUWNQTlpRY1RkWktVcldVQjRHbzNyazVMM0VxU2Zmc2x4WHBxWTV0cE5yYko2aWhHRVhPM2sKdm1FS0JjdkRVbmRjY05ZeVc3ZkpvSlpCbjVGQnIxYzErTjBwSjl6OW80UStRaWp0WkJxdzNWNWlCcjZUaDkxT0FFNWtQRytxMjZhKwpFbk9xdjkwZm9ldnlxbW8vUnhReVhoWHNTYkc2NUJDU1V2WTNzaTFxZGxUK1ZERldlUXA2QUJlTDI5NElXZTgwam1sY09Dd2R5bUJtCkFEM3NKcFJ6bTZDWFZOZVZaTE0wM2YyZTMxNlNqZ1N0V2RQVnJ3PT0KPC9kczpNb2R1bHVzPgo8ZHM6RXhwb25lbnQ+QVFBQjwvZHM6RXhwb25lbnQ+CjwvZHM6UlNBS2V5VmFsdWU+CjwvZHM6S2V5VmFsdWU+CjwvZHM6S2V5SW5mbz4KPC9kczpTaWduYXR1cmU+PC9zYW1sOkFzc2VydGlvbj4=";

	Element element = null;
	try{
	    BASE64Decoder decoder = new BASE64Decoder();
	    byte[] decodedBytes = decoder.decodeBuffer(samlToken);
	    String decodedString = new String(decodedBytes);

	    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	    factory.setNamespaceAware(true);
	    // Get the DocumentBuilder
	    DocumentBuilder parser = factory.newDocumentBuilder();
	    Document XMLDoc = parser.parse(new InputSource(new StringReader(decodedString)));
	    element = XMLDoc.getDocumentElement();
	}catch(ParserConfigurationException e){
	    logger.error("Unable to parse the xml", e);
	}catch(Exception e){
	    logger.error("Error while forming security header", e);
	}
	return element;
    }
}
