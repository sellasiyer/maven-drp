package com.bestbuy.bbym.ise.drp.entitlement;

import java.math.BigInteger;
import java.util.Date;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.IAjaxCallDecorator;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.extensions.markup.html.form.DateTextField;
import org.apache.wicket.extensions.yui.calendar.DatePicker;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.Radio;
import org.apache.wicket.markup.html.form.RadioGroup;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.validation.IErrorMessageSource;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidationError;
import org.apache.wicket.validation.IValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bestbuy.bbym.ise.domain.Customer;
import com.bestbuy.bbym.ise.domain.Device;
import com.bestbuy.bbym.ise.domain.Item;
import com.bestbuy.bbym.ise.domain.Product;
import com.bestbuy.bbym.ise.drp.DailyRhythmPortalSession;
import com.bestbuy.bbym.ise.drp.common.NewBaseNavPage;
import com.bestbuy.bbym.ise.drp.common.Validation;
import com.bestbuy.bbym.ise.drp.customer.CustInfoModalPanel;
import com.bestbuy.bbym.ise.drp.dashboard.CustomerDashboardHeaderPanel;
import com.bestbuy.bbym.ise.drp.dashboard.CustomerDashboardPage;
import com.bestbuy.bbym.ise.drp.domain.EntitlementLookup;
import com.bestbuy.bbym.ise.drp.domain.EntitlementPurchaseType;
import com.bestbuy.bbym.ise.drp.domain.FourPartKey;
import com.bestbuy.bbym.ise.drp.service.DrpPropertyService;
import com.bestbuy.bbym.ise.drp.service.ReturnExchangeService;
import com.bestbuy.bbym.ise.drp.service.SKUService;
import com.bestbuy.bbym.ise.drp.triage2.DashboardPage;
import com.bestbuy.bbym.ise.exception.IseBusinessException;
import com.bestbuy.bbym.ise.exception.ServiceException;
import com.bestbuy.bbym.ise.util.Util;

public class EntitlementSearchPage extends NewBaseNavPage {

    private static final long serialVersionUID = 1L;

    private static Logger logger = LoggerFactory.getLogger(EntitlementSearchPage.class);

    @SpringBean(name = "returnExchangeService")
    private ReturnExchangeService returnExchangeService;

    @SpringBean(name = "drpPropertyService")
    private DrpPropertyService drpPropertyService;

    @SpringBean(name = "skuService")
    private SKUService skuService;

    private EntitlementLookup entitlement = new EntitlementLookup();

    private CustInfoModalPanel custInfoModalPanel;

    private Customer selectedCustomer = null;

    private boolean bbyCustomerInfo = false;

    private String skuSevFlag;

    private AbstractDefaultAjaxBehavior wicketBehavior;

    public EntitlementSearchPage(final PageParameters parameters) {
	super(parameters);

	entitlement.setLine(getDailyRhythmPortalSession().getSelectedLine());
	if (getDailyRhythmPortalSession().getBestBuyCustomer() != null){
	    bbyCustomerInfo = true;
	}

	final StringBuilder storeValidationJS = new StringBuilder();
	final StringBuilder onlineValidationJS = new StringBuilder();

	// phone settings
	storeValidationJS.append("entitleStoreSearchValidation.store.minLength=");
	storeValidationJS.append(Validation.STORE_NUM_MIN);
	storeValidationJS.append(";");

	storeValidationJS.append("entitleStoreSearchValidation.store.maxLength=");
	storeValidationJS.append(Validation.STORE_NUM_MAX);
	storeValidationJS.append(";");

	storeValidationJS.append("entitleStoreSearchValidation.register.minLength=");
	storeValidationJS.append(Validation.REGISTER_MIN);
	storeValidationJS.append(";");

	storeValidationJS.append("entitleStoreSearchValidation.register.maxLength=");
	storeValidationJS.append(Validation.REGISTER_MAX);
	storeValidationJS.append(";");

	storeValidationJS.append("entitleStoreSearchValidation.transaction.minLength=");
	storeValidationJS.append(Validation.TRANSACTION_NUM_MIN);
	storeValidationJS.append(";");

	storeValidationJS.append("entitleStoreSearchValidation.transaction.maxLength=");
	storeValidationJS.append(Validation.TRANSACTION_NUM_MAX);
	storeValidationJS.append(";");

	storeValidationJS.append("entitleStoreSearchValidation.sku.minLength=");
	storeValidationJS.append(Validation.PROD_SKU_MIN);
	storeValidationJS.append(";");

	storeValidationJS.append("entitleStoreSearchValidation.sku.maxLength=");
	storeValidationJS.append(Validation.PROD_SKU_MAX);
	storeValidationJS.append(";");

	onlineValidationJS.append("entitleOnlineSearchValidation.sku.minLength=");
	onlineValidationJS.append(Validation.PROD_SKU_MIN);
	onlineValidationJS.append(";");

	onlineValidationJS.append("entitleOnlineSearchValidation.sku.maxLength=");
	onlineValidationJS.append(Validation.PROD_SKU_MAX);
	onlineValidationJS.append(";");

	final FeedbackPanel feedbackPanel = new FeedbackPanel("feedback");
	feedbackPanel.setOutputMarkupPlaceholderTag(true);
	add(feedbackPanel);

	/** Customer Dashboard Header Panel to display the Header on the Page */
	CustomerDashboardHeaderPanel customerDashboardHeaderPanel = new CustomerDashboardHeaderPanel(
		"customerDashboardHeaderPanel", null);
	customerDashboardHeaderPanel.setOutputMarkupPlaceholderTag(true);
	add(customerDashboardHeaderPanel);

	/** toggling between Carrier and Customer Header Panels **/
	if (bbyCustomerInfo){
	    customerDashboardHeaderPanel.setVisible(true);
	}else{
	    customerDashboardHeaderPanel.setVisible(false);
	}

	/** Flag check to make SKU/UPC Service call.. **/
	try{
	    skuSevFlag = drpPropertyService.getProperty("SKU_SERVICE_FLAG");
	}catch(Exception e){
	    logger.error("Error in retrieving property", e);
	}

	final Form<Object> entitlementForm = new Form<Object>("entitlementPurchaseTypeForm");
	entitlementForm.setOutputMarkupPlaceholderTag(true);

	/** Begin Page Markup Containers **/
	final WebMarkupContainer purchaseTransactionSearchContainer = new WebMarkupContainer(
		"purchaseTransactionSearchContainer");
	purchaseTransactionSearchContainer.setOutputMarkupPlaceholderTag(true);

	/******* Purchase Transaction Search Form Page **************************/

	final RadioGroup<EntitlementPurchaseType> purchaseTypeRadioGroup = new RadioGroup<EntitlementPurchaseType>(
		"purchaseTypeRadioGroup", new PropertyModel<EntitlementPurchaseType>(entitlement,
			"entitlementPurchaseType"));

	Radio<EntitlementPurchaseType> storeOptionRadio = new Radio<EntitlementPurchaseType>("storeOptionRadio",
		new Model<EntitlementPurchaseType>(EntitlementPurchaseType.STORE));
	storeOptionRadio.setMarkupId("storeOptionRadio");
	storeOptionRadio.setOutputMarkupId(true);

	storeOptionRadio.add(new AjaxEventBehavior("onclick") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void onEvent(AjaxRequestTarget target) {

		StringBuilder onDomReadyJS = new StringBuilder();
		onDomReadyJS.append(storeValidationJS.toString());
		onDomReadyJS.append("initRadioButtons();");
		onDomReadyJS.append("doFormFieldValidation(entitleStoreSearchValidation);");
		onDomReadyJS.append("handlePageContinueButtonState();");
		logger.debug("onDomReadyJS=" + onDomReadyJS);
		target.appendJavaScript(onDomReadyJS.toString());
	    }

	});

	Radio<EntitlementPurchaseType> dotComOptionRadio = new Radio<EntitlementPurchaseType>("onlineOptionRadio",
		new Model<EntitlementPurchaseType>(EntitlementPurchaseType.ONLINE));
	dotComOptionRadio.setMarkupId("onlineOptionRadio");
	dotComOptionRadio.setOutputMarkupId(true);

	dotComOptionRadio.add(new AjaxEventBehavior("onclick") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void onEvent(AjaxRequestTarget target) {

		StringBuilder onDomReadyJS = new StringBuilder();
		onDomReadyJS.append(onlineValidationJS.toString());
		onDomReadyJS.append("initRadioButtons();");
		onDomReadyJS.append("doFormFieldValidation(entitleOnlineSearchValidation);");
		onDomReadyJS.append("handlePageContinueButtonState();");
		logger.debug("onDomReadyJS=" + onDomReadyJS);
		target.appendJavaScript(onDomReadyJS.toString());

	    }
	});

	purchaseTypeRadioGroup.add(storeOptionRadio);
	purchaseTypeRadioGroup.add(dotComOptionRadio);
	purchaseTransactionSearchContainer.add(purchaseTypeRadioGroup);

	/** store group form fields **/
	final TextField<String> storeNumTxt = new TextField<String>("storeNumTxt", new PropertyModel<String>(
		entitlement, "fourpartkey.storeId"));
	storeNumTxt.setMarkupId("storeNumTxt");
	storeNumTxt.setOutputMarkupId(true);
	storeNumTxt.setOutputMarkupPlaceholderTag(true);

	final TextField<String> registerNumTxt = new TextField<String>("registerNumTxt", new PropertyModel<String>(
		entitlement, "fourpartkey.workStationId"));
	registerNumTxt.setMarkupId("registerNumTxt");
	registerNumTxt.setOutputMarkupId(true);
	registerNumTxt.setOutputMarkupPlaceholderTag(true);

	final TextField<String> transNumTxt = new TextField<String>("transNumTxt", new PropertyModel<String>(
		entitlement, "fourpartkey.registerTransactionNum"));
	transNumTxt.setMarkupId("transNumTxt");
	transNumTxt.setOutputMarkupId(true);
	transNumTxt.setOutputMarkupPlaceholderTag(true);

	final DateTextField transDateTxt = new DateTextField("transDateTxt", new PropertyModel<Date>(entitlement,
		"fourpartkey.businessDate"), "MM/dd/yy");
	transDateTxt.setMarkupId("transDateTxt");
	transDateTxt.add(new DatePicker());
	// transDateTxt.setRequired(true);
	transDateTxt.setOutputMarkupId(true);
	transDateTxt.setOutputMarkupPlaceholderTag(true);

	final TextField<String> storeSkuTxt = new TextField<String>("storeSkuTxt", new PropertyModel<String>(
		entitlement, "storeSku"));
	storeSkuTxt.setMarkupId("storeSkuTxt");
	storeSkuTxt.setOutputMarkupId(true);
	storeSkuTxt.setOutputMarkupPlaceholderTag(true);

	purchaseTransactionSearchContainer.add(storeNumTxt);
	purchaseTransactionSearchContainer.add(registerNumTxt);
	purchaseTransactionSearchContainer.add(transNumTxt);
	purchaseTransactionSearchContainer.add(transDateTxt);
	purchaseTransactionSearchContainer.add(storeSkuTxt);

	/** dotcom order group form fields **/
	final TextField<String> dotComOrderNumTxt = new TextField<String>("dotComOrderNumTxt",
		new PropertyModel<String>(entitlement, "onlineOrderNumber"));
	dotComOrderNumTxt.setMarkupId("dotComOrderNumTxt");
	dotComOrderNumTxt.setOutputMarkupId(true);
	dotComOrderNumTxt.setOutputMarkupPlaceholderTag(true);

	final TextField<String> dotComSkuTxt = new TextField<String>("dotComSkuTxt", new PropertyModel<String>(
		entitlement, "onlineSku"));
	dotComSkuTxt.setMarkupId("dotComSkuTxt");
	dotComSkuTxt.setOutputMarkupId(true);
	dotComSkuTxt.setOutputMarkupPlaceholderTag(true);
	purchaseTransactionSearchContainer.add(dotComOrderNumTxt);
	purchaseTransactionSearchContainer.add(dotComSkuTxt);

	final AjaxButton continueButton = new AjaxButton("purchaseSearchContinueButton") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void onSubmit(AjaxRequestTarget target, Form<?> form) {

		logger.debug("entitlement.getEntitlementPurchaseType()..........."
			+ entitlement.getEntitlementPurchaseType());

		StringBuffer transactionId = new StringBuffer();

		if (entitlement.getEntitlementPurchaseType() == EntitlementPurchaseType.STORE){

		    entitlement.setStoreSku(storeSkuTxt.getDefaultModelObjectAsString());
		    entitlement.setProductSku(storeSkuTxt.getDefaultModelObjectAsString());

		    transactionId.append(Integer.parseInt(storeNumTxt.getDefaultModelObjectAsString()));
		    transactionId.append("^");
		    transactionId.append(Util.toString(transDateTxt.getModelObject(), "yyyyMMdd"));
		    transactionId.append("^");
		    transactionId.append(Integer.parseInt(registerNumTxt.getDefaultModelObjectAsString()));
		    transactionId.append("^");
		    transactionId.append(Integer.parseInt(transNumTxt.getDefaultModelObjectAsString()));
		    logger.debug("transactionId.............." + transactionId);

		}else if (entitlement.getEntitlementPurchaseType() == EntitlementPurchaseType.ONLINE){

		    entitlement.setOnlineSku(dotComSkuTxt.getDefaultModelObjectAsString());
		    entitlement.setOnlineOrderNumber(dotComOrderNumTxt.getDefaultModelObjectAsString());
		    entitlement.setProductSku(dotComSkuTxt.getDefaultModelObjectAsString());

		    transactionId.append(dotComOrderNumTxt.getDefaultModelObjectAsString());
		}

		entitlement.setTransactionId(transactionId.toString());

		try{
		    logger.debug("invoking makeSkuUpcServiceCall method........." + transactionId);
		    // Make SKU Service call and set the device details to
		    // entitlement..
		    Item item = new Item();
		    entitlement.setItem(item);

		    if (skuSevFlag != null && Boolean.parseBoolean(skuSevFlag)){
			makeSkuUpcServiceCall(entitlement, entitlement.getProductSku());
		    }

		    // EC service call to Validate the transaction Id and SKU
		    // combination.
		    Product product = returnExchangeService.validateTransactionKeySkuCombo(transactionId.toString(),
			    getDailyRhythmPortalSession().getDrpUser(), entitlement.getProductSku());
		    logger.debug("product.........." + product);
		    if (product != null){
			// populate Item with Data from service...
			populateItem(transactionId.toString(), item, product);
			// entitlement.setItem(item);

			// Forward to Entitlement Lookup Page if the service
			// call valdiation is success..
			setResponsePage(new EntitlementLookupPage(entitlement, selectedCustomer, getPage()));

		    }else{
			if (entitlement.getEntitlementPurchaseType() == EntitlementPurchaseType.ONLINE){
			    error(getString("entitlement.purchase.invalidOrderSku"));
			}else if (entitlement.getEntitlementPurchaseType() == EntitlementPurchaseType.STORE){
			    error(getString("entitlement.purchase.invalidStoresku"));
			}else{
			    error(getString("entitlement.purchase.invalidTransOrderSku"));
			}
		    }

		}catch(ServiceException e){
		    error(e.getFullMessage());
		    target.add(feedbackPanel);
		}catch(IseBusinessException e){
		    error(e.getFullMessage());
		    target.add(feedbackPanel);
		}catch(Exception e){
		    error("Exception :: " + e.getMessage());
		    target.add(feedbackPanel);
		}

		target.add(entitlementForm);
		target.appendJavaScript("installSearchHandlers();initRadioButtons();");

	    }

	    @Override
	    protected IAjaxCallDecorator getAjaxCallDecorator() {
		return new IAjaxCallDecorator() {

		    private static final long serialVersionUID = 1L;

		    public CharSequence decorateScript(Component comp, CharSequence script) {
			return "showButtonLoading(true, '#purchaseSearchContinueButton');" + script;
		    }

		    public CharSequence decorateOnFailureScript(Component comp, CharSequence script) {
			return "showButtonLoading(false, '#purchaseSearchContinueButton');" + script;
		    }

		    public CharSequence decorateOnSuccessScript(Component comp, CharSequence script) {
			return "showButtonLoading(false, '#purchaseSearchContinueButton');" + script;
		    }
		};
	    }

	    @Override
	    protected void onError(AjaxRequestTarget target, Form<?> form) {
		logger.error("ContinueButton onError");
		target.add(feedbackPanel);
		target.appendJavaScript("scrollToTop();");
		target.appendJavaScript("initRadioButtons();");
	    }

	};
	continueButton.setMarkupId("purchaseSearchContinueButton");
	continueButton.setOutputMarkupId(true);
	continueButton.setOutputMarkupPlaceholderTag(true);

	final AjaxButton cancelButton = new AjaxButton("cancelButton", new ResourceModel(
		"entitlement.purchaseview.cancelButton")) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
		logger.debug("cancelButton onSubmit");
		setResponsePage(CustomerDashboardPage.class);
	    }

	    @Override
	    protected void onError(AjaxRequestTarget target, Form<?> form) {
		// TODO Implement me!
	    }
	};
	cancelButton.setOutputMarkupPlaceholderTag(true);
	cancelButton.setDefaultFormProcessing(false);

	//If flow is started with triage then updating triage history customer benefits.
	final AjaxButton triageBackButton = new AjaxButton("triageBackButton", new ResourceModel(
		"triageIssues.backButton.label")) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
		logger.debug("triageBackButton onSubmit");
		setResponsePage(DashboardPage.class);
	    }

	    @Override
	    protected void onError(AjaxRequestTarget target, Form<?> form) {
		// TODO Implement me!
	    }

	    @Override
	    public boolean isVisible() {
		return getDailyRhythmPortalSession().getWorkflowAction() == DailyRhythmPortalSession.WorkflowAction.Triage;
	    }
	};
	triageBackButton.setOutputMarkupPlaceholderTag(true);
	triageBackButton.setDefaultFormProcessing(false);
	entitlementForm.add(triageBackButton);

	entitlementForm.add(continueButton);
	entitlementForm.add(cancelButton);
	/******* Purchase Transaction Search Form Page **************************/

	entitlementForm.add(purchaseTransactionSearchContainer);
	// Adding Form to page...
	add(entitlementForm);

	/** Purchase Transaction Modal Window Panel........... **/

	custInfoModalPanel = new CustInfoModalPanel("custInfoModalPanel",
		CustInfoModalPanel.ModalState.SELECT_MOBILE_PURCHASE) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onClose(AjaxRequestTarget target) {
		logger.debug("custInfoModalPanel onClose");
		feedbackPanel.setVisible(true);
		if (getSelectedCustomer() != null){
		    logger.debug("selectedProtectionPlan=" + getSelectedCustomer());
		    Product product = getSelectedProduct();
		    Item item = new Item();

		    if (product != null){

			selectedCustomer = getSelectedCustomer();

			String transType = product.getTransactionKeyType();
			String transId = product.getTransactionId();
			String prodSku = product.getSku();

			try{
			    entitlement.setItem(item);
			    // Make the SKU/UPC service call to determine the
			    // Manufacturer serial Number....
			    if (skuSevFlag != null && Boolean.parseBoolean(skuSevFlag)){
				makeSkuUpcServiceCall(entitlement, prodSku);
			    }

			    logger.debug("selected transId.........." + transId + "..........." + transType);

			    if (StringUtils.isNotEmpty(transId) && StringUtils.isNotEmpty(transType)){
				if (Product.TRANSACTION_KEY_TYPE_4_PART.equalsIgnoreCase(transType)){
				    String[] transArr = transId.split("\\^");
				    FourPartKey fourPartKey = new FourPartKey();
				    fourPartKey.setStoreId(transArr[0]);
				    fourPartKey.setWorkStationId(transArr[2]);
				    fourPartKey.setRegisterTransactionNum(transArr[3]);
				    fourPartKey.setBusinessDate(Util.toDate(formatToTransDate(transArr[1]), "MM/dd/yy",
					    null));
				    item.setDescription(product.getDescription());
				    populateItem(transId, item, product);
				    entitlement.setEntitlementPurchaseType(EntitlementPurchaseType.STORE);
				    entitlement.setFourpartkey(fourPartKey);
				    entitlement.setItem(item);
				    entitlement.setTransactionId(transId);
				    entitlement.setStoreSku(prodSku);
				    entitlement.setProductSku(prodSku);
				}else if (Product.TRANSACTION_KEY_TYPE_ORDER.equalsIgnoreCase(transType)
					|| Product.TRANSACTION_KEY_TYPE_FULFILLMENT.equalsIgnoreCase(transType)){
				    entitlement.setEntitlementPurchaseType(EntitlementPurchaseType.ONLINE);
				    entitlement.setOnlineOrderNumber(transId);
				    entitlement.setTransactionId(transId);
				    populateItem(transId, item, product);
				    entitlement.setItem(item);
				    entitlement.setOnlineSku(prodSku);
				    entitlement.setProductSku(prodSku);

				}

				target.add(purchaseTransactionSearchContainer);
				target.add(entitlementForm);

				setResponsePage(new EntitlementLookupPage(entitlement, selectedCustomer, getPage()));

			    }else{
				error("Please select a valid transaction.");
				target.add(feedbackPanel);
			    }

			}catch(Exception e){
			    /** Filling the text fields in case of exception **/
			    if (StringUtils.isNotEmpty(transId) && StringUtils.isNotEmpty(transType)){
				if (Product.TRANSACTION_KEY_TYPE_4_PART.equalsIgnoreCase(transType)){
				    String[] transArr = transId.split("\\^");
				    storeNumTxt.setDefaultModelObject(transArr[0]);
				    registerNumTxt.setDefaultModelObject(transArr[2]);
				    transNumTxt.setDefaultModelObject(transArr[3]);
				    transDateTxt.setDefaultModelObject(Util.toDate(formatToTransDate(transArr[1]),
					    "MM/dd/yy", null));
				    storeSkuTxt.setDefaultModelObject(prodSku);
				}
			    }else if (Product.TRANSACTION_KEY_TYPE_ORDER.equalsIgnoreCase(transType)
				    || Product.TRANSACTION_KEY_TYPE_FULFILLMENT.equalsIgnoreCase(transType)){
				dotComOrderNumTxt.setDefaultModelObject(transId);
				dotComSkuTxt.setDefaultModelObject(prodSku);
			    }
			    if (e.getMessage() != null){
				error(e.getMessage());
			    }
			    target.add(feedbackPanel);
			    target.appendJavaScript("initRadioButtons()");
			}

		    }
		}

	    }
	};
	add(custInfoModalPanel);

	final AjaxLink<Object> purchaseTrasactionLink = new AjaxLink<Object>("purchaseTrasactionLink") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onClick(AjaxRequestTarget target) {
		logger.debug("purchaseTrasactionLink onClick");
		feedbackPanel.setVisible(false);
		custInfoModalPanel.open(target);

	    }
	};
	purchaseTrasactionLink.setMarkupId("purchaseTrasactionLink");
	purchaseTrasactionLink.setOutputMarkupId(true);
	purchaseTrasactionLink.setOutputMarkupPlaceholderTag(true);
	purchaseTransactionSearchContainer.add(purchaseTrasactionLink);

	wicketBehavior = new AbstractDefaultAjaxBehavior() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void respond(AjaxRequestTarget target) {
		String id = RequestCycle.get().getRequest().getRequestParameters().getParameterValue("id").toString();
		logger.info("wicketBehavior id=" + id);
		if (id != null && id.startsWith("gun")){
		    id = StringUtils.removeStart(id, "gun");
		    if (!NumberUtils.isDigits(id) || !(id.length() == 22 || id.length() == 34)){
			logger.info("Invalid Barcode:" + id.length());
			error("We could not determine the 4-part key from this bar code. Please key in the 4-part key data.");

			storeNumTxt.clearInput();
			registerNumTxt.clearInput();
			transNumTxt.clearInput();
			transDateTxt.clearInput();

			target.add(feedbackPanel);
			// FIXME - Error message is not staying for long time
			// and reset all fields to blank
			return;
		    }
		    String store = "";
		    String register = "";
		    String transaction = "";
		    String formatteddate = "";
		    if (id.length() == 22){
			store = id.substring(2, 6);
			register = id.substring(6, 9);
			transaction = id.substring(9, 13);
			String date = id.substring(13, 21);
			formatteddate = date.substring(0, 2) + "/" + date.substring(2, 4) + "/" + date.substring(6, 8);
			logger.debug("formatteddate=" + formatteddate);
		    }else if (id.length() == 34){
			/**
			 * Decryption logic 1) Remove right most check digit
			 * from 33 digit validation number to yield a 32 digit
			 * number 2) Remove next 3 right most digits from result
			 * of step one to produce a 29 digit number - save both
			 * numbers 3) Multiply the 29 digit number result from
			 * step 2) by 751, yielding a whole number product 4)
			 * Add (not append) the cut 3 digit number from step 2)
			 * to the whole number result from step 3) 5) Left pad
			 * number with 0s to produce a 29 digit number.
			 */
			String temp1 = id.substring(1, 33);
			logger.trace("SCAN - Step1: " + temp1);
			String temp2 = temp1.substring(29, 32);
			temp1 = temp1.substring(0, 29);
			logger.trace("SCAN - Step2: " + temp1);
			logger.trace("SCAN - Step2: " + temp2);
			BigInteger temp3 = (new BigInteger(temp1)).multiply(new BigInteger("751"));
			logger.trace("SCAN - Step3: " + temp3);
			temp3 = temp3.add(new BigInteger(temp2));
			logger.trace("SCAN - Step4: " + temp3);
			String temp4 = StringUtils.leftPad(String.valueOf(temp3), 29, '0');
			logger.trace("SCAN - Step5: " + temp4);

			store = temp4.substring(17, 21);
			register = '0' + temp4.substring(13, 15);
			transaction = temp4.substring(21, 25);
			formatteddate = temp4.substring(0, 2) + "/" + temp4.substring(2, 4) + "/"
				+ temp4.substring(27, 29);
		    }
		    logger.debug("Store#:" + store);
		    logger.debug("Register#:" + register);
		    logger.debug("Transaction#:" + transaction);
		    logger.debug("TransactionDate:" + formatteddate);
		    // FIXME - Values are not set to corresponding fields

		    FourPartKey fourPartKey = new FourPartKey();
		    fourPartKey.setStoreId(store);
		    fourPartKey.setWorkStationId(register);
		    fourPartKey.setRegisterTransactionNum(transaction);
		    fourPartKey.setBusinessDate(Util.toDate(formatteddate, "MM/dd/yy", null));
		    entitlement.setFourpartkey(fourPartKey);

		    target.add(storeNumTxt);
		    target.add(registerNumTxt);
		    target.add(transNumTxt);
		    target.add(transDateTxt);
		    entitlement.setProductSku("");
		    target.add(entitlementForm);

		    StringBuilder js = new StringBuilder();
		    js
			    .append("installSearchHandlers();initRadioButtons();doFormFieldValidation(entitleStoreSearchValidation);");
		    js.append(getGunEntryJavaScript());
		    target.appendJavaScript(js.toString());
		}
	    }
	};
	add(wicketBehavior);

	add(new AbstractDefaultAjaxBehavior() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void respond(AjaxRequestTarget target) {
	    }

	    @Override
	    public void renderHead(Component component, IHeaderResponse response) {
		super.renderHead(component, response);
		StringBuilder onDomReadyJS = new StringBuilder();
		onDomReadyJS.append(storeValidationJS.toString());
		onDomReadyJS.append(onlineValidationJS.toString());
		onDomReadyJS.append("doFormFieldValidation(entitleStoreSearchValidation);");
		onDomReadyJS.append("doFormFieldValidation(entitleOnlineSearchValidation);");
		onDomReadyJS.append("wicketBehavior = function(id) { ");
		onDomReadyJS.append("wicketAjaxGet('");
		onDomReadyJS.append(wicketBehavior.getCallbackUrl());
		onDomReadyJS.append("&id='+id); };");
		onDomReadyJS.append("initRadioButtons();");
		onDomReadyJS.append(getGunEntryJavaScript());
		onDomReadyJS.append("focusStoreSku();");
		logger.debug("onDomReadyJS=" + onDomReadyJS);
		response.renderOnDomReadyJavaScript(onDomReadyJS.toString());
	    }
	});

    }

    private String getGunEntryJavaScript() {
	StringBuilder js = new StringBuilder();
	js.append("installSearchHandlers();initRadioButtons();setFocusOnStoreSKU();");
	js.append("handleGunEntry('#storeNumTxt', 'gun', wicketBehavior);");
	js.append("handleGunEntry('#registerNumTxt', 'gun', wicketBehavior);");
	js.append("handleGunEntry('#transNumTxt', 'gun', wicketBehavior);");
	js.append("handleGunEntry('#transDateTxt', 'gun', wicketBehavior);");
	return js.toString();
    }

    private void makeSkuUpcServiceCall(EntitlementLookup entilement, String prodSku) throws Exception {
	Device device = null;
	try{
	    device = skuService.getSkuDetails(prodSku);
	    if (device != null && entilement.getItem() != null){
		entilement.getItem().setDescription(device.getDescription());
		entilement.setManufacturer(device.getManufacturer());
		entilement.setProductSku(device.getSku());
	    }
	    if (device == null)
		throw new IseBusinessException("SKU/UPC not found.  Please enter a valid Best Buy SKU or UPC.");

	}catch(Exception se){
	    throw new IseBusinessException(
		    "We cannot verify the SKU/UPC at this time. We are not able to recommend Customer Service Options at this time.");
	}
    }

    private String formatToTransDate(String dateStr) {
	StringBuffer convertedDate = new StringBuffer();
	if (dateStr != null && dateStr.length() >= 8){
	    convertedDate.append(dateStr.substring(4, 6));
	    convertedDate.append("/");
	    convertedDate.append(dateStr.substring(6, 8));
	    convertedDate.append("/");
	    convertedDate.append(dateStr.substring(0, 4));
	}
	return convertedDate.toString();
    }

    private void populateItem(String transactionId, Item item, Product product) {
	if (Product.TRANSACTION_KEY_TYPE_ORDER.equalsIgnoreCase(product.getTransactionKeyType())
		|| Product.TRANSACTION_KEY_TYPE_FULFILLMENT.equalsIgnoreCase(product.getTransactionKeyType())){
	    item.setTransactionId(transactionId);
	}

	item.setSourceTransactionKey(product.getSourceTransactionKey());
	item.setTransactionKeyType(product.getTransactionKeyType());
	item.setTransactionSourceSystem(product.getTransactionSourceSystem());
	item.setPurchaseDate(product.getPurchaseDate());

    }

    class CustomPatternValidator implements IValidator<String> {

	private static final long serialVersionUID = 1L;
	private final Pattern pattern;
	String errorMessage;

	CustomPatternValidator(String patternRegEx, String errorMessage) {
	    pattern = Pattern.compile(patternRegEx);
	    this.errorMessage = errorMessage;
	}

	@Override
	public void validate(IValidatable<String> validatable) {
	    final String value = validatable.getValue();
	    // validate password
	    if (pattern.matcher(value).matches() == false){
		validatable.error(new IValidationError() {

		    private static final long serialVersionUID = 1L;

		    @Override
		    public String getErrorMessage(IErrorMessageSource msgSrc) {
			return errorMessage;
		    }
		});
	    }
	}
    }

}
