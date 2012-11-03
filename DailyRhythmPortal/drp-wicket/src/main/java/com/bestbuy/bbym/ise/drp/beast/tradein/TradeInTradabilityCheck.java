package com.bestbuy.bbym.ise.drp.beast.tradein;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bestbuy.bbym.ise.drp.common.PageParameterKeys;
import com.bestbuy.bbym.ise.drp.domain.BeastDevice;
import com.bestbuy.bbym.ise.drp.domain.TitanDevice;
import com.bestbuy.bbym.ise.drp.domain.ui.UIDataItem;
import com.bestbuy.bbym.ise.drp.service.TradeInService;
import com.bestbuy.bbym.ise.drp.util.behavior.DefaultFocusBehavior;
import com.bestbuy.bbym.ise.drp.validator.AlphaNumericValidator;
import com.bestbuy.bbym.ise.exception.IseBusinessException;
import com.bestbuy.bbym.ise.exception.ServiceException;

/**
 * Entry point of the TRade-In with activation flow.
 *
 * @see <a href="https://spr.bestbuy.com/ISE/Shared%20Documents/Requirements%20(User%20Stories)/Release%203.0/Trade-In%20Stories/User%20Story_B-09022%20Tradability%20Check.docx">User Story_B-09022 Tradability Check</a>
 */
public class TradeInTradabilityCheck extends BeastPage {

    private static final long serialVersionUID = 1L;
    private static Logger LOGGER = LoggerFactory.getLogger(TradeInTradabilityCheck.class);

    @SpringBean(name = "tradeInService")
    private TradeInService tradeInService;
    private String selLastFour = "";
    private String selFullSerial = "";
    private final WebMarkupContainer tradabilityCheckId;
    private final WebMarkupContainer selLastFourWebMarkupContainer;
    private final WebMarkupContainer selFullSerialWebMarkupContainer;
    private final WebMarkupContainer nonTradableWebMarkupContainer;
    private String dataSharingKey;

    private List<BeastDevice> beastDeviceList = null;

    public TradeInTradabilityCheck(final PageParameters parameters) {
	super(parameters);

	// some basic UI Init.
	getBorder().setButtonEnabled(nextButton, false);
	getBorder().setPageTitle(getString("tradabilityCheck.title.label"));
	getBorder().setButtonEnabled(backButton, false);
	backButton.setVisible(false); // backbutton not on this page

	getBorder().setProcessingMessage(getString("tradabilityCheckPage.serviceLoading.label"));
	getBorder().getLoadingModalPanel().setMessage("Checking Number");

	dataSharingKey = parameters.get(PageParameterKeys.DATA_SHARING_KEY.getUrlParameterKey()).toString();
	LOGGER.debug("passed in DSK=" + dataSharingKey);

	// gather data
	UIDataItem titanDataItem = new UIDataItem();
	titanDataItem.setStringProp("dataSharingKey", dataSharingKey);
	getDailyRhythmPortalSession().setTitanDataItem(titanDataItem);
	boolean newActivationFlag = true;

	try{
	    beastDeviceList = tradeInService.getDeviceDetails(dataSharingKey);
	    logger.debug(Integer.toString(beastDeviceList.size()));
	    if (beastDeviceList != null && beastDeviceList.size() > 0)
		newActivationFlag = false;
	}catch(ServiceException exc){
	    LOGGER.error(exc.getMessage(), exc);
	    // TODO Auto-generated catch block
	    // doesn't require to catch the exception.
	}

	tradabilityCheckId = new WebMarkupContainer("tradabilityCheckId");
	tradabilityCheckId.setOutputMarkupId(true);
	tradabilityCheckId.setOutputMarkupPlaceholderTag(true);
	add(tradabilityCheckId);
	//getBorder().getForm().add(getBorder().getBodyContainer());

	//last four of serial number.
	final TextField<String> selLastFourInput = new TextField<String>("selLastFour", new PropertyModel<String>(this,
		"selLastFour"));
	selLastFourInput.setOutputMarkupId(true);
	selLastFourInput.setMarkupId("selLastFour");
	selLastFourInput.add(AlphaNumericValidator.INSTANCE);

	selLastFourInput.add(new DefaultFocusBehavior());

	selLastFourInput.add(new AjaxFormComponentUpdatingBehavior("onBlur") {

	    @Override
	    protected void onError(AjaxRequestTarget target, RuntimeException e) {
		TradeInTradabilityCheck.this.getBorder().getNotificationPanel().refresh(target);
	    }

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void onUpdate(AjaxRequestTarget target) {
	    }
	});

	selLastFourWebMarkupContainer = new WebMarkupContainer("selLastFourContainer");
	selLastFourWebMarkupContainer.setMarkupId("selLastFourContainer");
	selLastFourWebMarkupContainer.setOutputMarkupId(true);
	selLastFourWebMarkupContainer.setVisible(!newActivationFlag);
	if (selLastFourWebMarkupContainer.isVisible())
	    selLastFourWebMarkupContainer.add(new DefaultFocusBehavior());
	selLastFourWebMarkupContainer.add(selLastFourInput);
	tradabilityCheckId.add(selLastFourWebMarkupContainer);

	//full serial number.
	final TextField<String> selFullSerial = new TextField<String>("selFullSerial", new PropertyModel<String>(this,
		"selFullSerial"));

	selFullSerial.setOutputMarkupId(true);
	selFullSerial.setMarkupId("selFullSerial");
	selFullSerial.setOutputMarkupPlaceholderTag(true);
	selFullSerial.add(AlphaNumericValidator.INSTANCE);

	selFullSerial.add(new DefaultFocusBehavior());

	selFullSerial.add(new AjaxFormComponentUpdatingBehavior("onBlur") {

	    @Override
	    protected void onError(AjaxRequestTarget target, RuntimeException e) {
		TradeInTradabilityCheck.this.getBorder().getNotificationPanel().refresh(target);
	    }

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void onUpdate(AjaxRequestTarget target) {
	    }
	});

	selFullSerialWebMarkupContainer = new WebMarkupContainer("selFullSerialContainer");
	selFullSerialWebMarkupContainer.setVisible(newActivationFlag);
	selFullSerialWebMarkupContainer.setOutputMarkupId(true);
	selFullSerialWebMarkupContainer.setMarkupId("selFullSerialContainer");
	if (selFullSerialWebMarkupContainer.isVisible())
	    selFullSerialWebMarkupContainer.add(new DefaultFocusBehavior());
	selFullSerialWebMarkupContainer.add(selFullSerial);
	tradabilityCheckId.add(selFullSerialWebMarkupContainer);

	// ====  ====  ====  ====

	nonTradableWebMarkupContainer = new WebMarkupContainer("non-tradable");
	nonTradableWebMarkupContainer.setVisible(false);
	nonTradableWebMarkupContainer.setOutputMarkupId(true);

	tradabilityCheckId.add(nonTradableWebMarkupContainer);

    }

    @Override
    void clearForm(final AjaxRequestTarget target) {
	try{
	    LOGGER.info("clear form page pressed");
	    if (!selFullSerialWebMarkupContainer.isVisible()){
		target.appendJavaScript(" setVisible('selFullSerialContainer', false);");
		target.appendJavaScript(" setVisible('selLastFourContainer', true);");
		target.appendJavaScript("$('#selLastFourContainer').focus();");
	    }else{
		target.appendJavaScript(" setVisible('selFullSerialContainer', true);");
		target.appendJavaScript(" setVisible('selLastFourContainer', false);");
		target.appendJavaScript("$('#selFullSerialContainer').focus();");
	    }

	    target.appendJavaScript("initTradeInTradabilityCheck();");

	    nonTradableWebMarkupContainer.setVisible(false);

	    selLastFour = "";
	    selFullSerial = "";

	    getBorder().setButtonEnabled(nextButton, false, target);

	    if (target != null){
		target.add(tradabilityCheckId);
	    }

	    target.appendJavaScript("initTradeInTradabilityCheck();");
	    target.appendJavaScript("bindHotKeys();");
	}catch(Exception exc){
	    LOGGER.error(ExceptionUtils.getStackTrace(exc));
	    displayError(getString("TradeInTradabilityCheck.service.down.message"), target);
	    return;
	}
    }

    @Override
    void goBack(final AjaxRequestTarget target) {
	// this is disabled, and should never be run.
	LOGGER.error("goBack was triggered, and should not have been as there is no 'back' functionality.");
    }

    @Override
    void nextPage(final AjaxRequestTarget target, Form<?> form) {
	boolean isTradable = false;
	BeastDevice beastDevice = null;
	if (StringUtils.isNotEmpty(selFullSerial)){

	    BeastDevice tradableDevice = null;
	    try{
		tradableDevice = tradeInService.getTradeableDevice(dataSharingKey, selFullSerial,
			getDailyRhythmPortalSession().getDrpUser());
	    }catch(ServiceException e){
		LOGGER.error(ExceptionUtils.getStackTrace(e));
		displayError(getString("TradeInTradabilityCheck.service.down.message"), target);
		return;
	    }
	    if (tradableDevice == null){
		// need to write
		// TitanDevice titanDevice = new TitanDevice();
		// titanDevice.set
		// tradeInService.writeTradeInRecord(titanDevice)
	    }
	    beastDevice = tradableDevice;
	}else if (StringUtils.isNotEmpty(selLastFour)){

	    BeastDevice tradableDevice = null;
	    try{
		tradableDevice = tradeInService.getTradeableDevice(dataSharingKey, selLastFour,
			getDailyRhythmPortalSession().getDrpUser());

	    }catch(ServiceException exc){
		LOGGER.error(ExceptionUtils.getStackTrace(exc));
		displayError(getString("TradeInTradabilityCheck.service.down.message"), target);
		return;
	    }

	    if (tradableDevice == null){
		target.appendJavaScript(" setVisible('selFullSerialContainer', true);");
		target.appendJavaScript(" setVisible('selLastFourContainer', false);");
		target.appendJavaScript("$('#selFullSerialContainer').focus();");
		target.appendJavaScript("initTradeInTradabilityCheck();");
		target.appendJavaScript("bindHotKeys();");

		nonTradableWebMarkupContainer.setVisible(false);

		displayError(getString("no.last.4.digits.found"), target);

		selLastFour = null;

		if (target != null){
		    target.add(tradabilityCheckId);
		    target.add(nextButton);
		    selFullSerialWebMarkupContainer.setVisible(true);
		    target.appendJavaScript("$('#selFullSerialContainer').focus();");
		    target.add(selFullSerialWebMarkupContainer);

		    getBorder().setButtonEnabled(nextButton, false);
		}
		return;
	    }

	    beastDevice = tradableDevice;
	}else{
	    LOGGER.error("selLastFour=" + selLastFour + ", selFullSerial=" + selFullSerial);
	    displayError("Unknown values have been entered.", target);
	    return;

	}
	LOGGER.info("BEAST DEVICE for Tradability Check: " + beastDevice);
	try{
	    isTradable = tradeInService.isTradable(beastDevice, getDailyRhythmPortalSession().getDrpUser());
	}catch(ServiceException e){
	    LOGGER.error(ExceptionUtils.getStackTrace(e));
	    displayError(getString("TradeInTradabilityCheck.service.down.message"), target);
	    return;
	}catch(IseBusinessException e){
	    LOGGER.error(ExceptionUtils.getStackTrace(e));
	    displayError(getString("TradeInTradabilityCheck.service.down.message"), target);
	    return;
	}

	try{
	    if (isTradable){
		TitanDevice titanDevice = new TitanDevice();
		titanDevice.setDataSharingKey(beastDevice.getDataSharingKey());
		titanDevice.setSerialNumber(beastDevice.getHandsetIdentifier());
		titanDevice.setDeviceId(beastDevice.getId());
		titanDevice.setDescription(beastDevice.getDeviceDesc());
		if (getDailyRhythmPortalSession().getDrpUser() != null){
		    titanDevice.setCreatedBy(getDailyRhythmPortalSession().getDrpUser().getUserId());
		}
		titanDevice.setTransactionId(beastDevice.getBeastTransactionId());
		titanDevice.setTechnologyType(beastDevice.getHandsetIdentifierType());
		getDailyRhythmPortalSession().setTitanDevice(titanDevice);
		getDailyRhythmPortalSession().getTitanDevice().setDataSharingKey(dataSharingKey);

		this.getPageParameters().add(PageParameterKeys.DATA_SHARING_KEY.getUrlParameterKey(), dataSharingKey);
		this.getPageParameters().add(PageParameterKeys.STORE_ID.getUrlParameterKey(),
			getDailyRhythmPortalSession().getDrpUser().getStoreId());
		if (beastDevice.getDeviceDesc() != null){
		    this.getPageParameters().add(PageParameterKeys.SEARCH_DESCRIPTION.name(),
			    beastDevice.getDeviceDesc());
		}else{
		    this.getPageParameters().add(PageParameterKeys.SEARCH_DESCRIPTION.name(), new String(""));
		}
		this.setResponsePage(new CatalogSearchPage(this.getPageParameters()));
	    }else{
		target.appendJavaScript(" setVisible('selFullSerialContainer', false);");
		target.appendJavaScript(" setVisible('selLastFourContainer', false);");
		target.appendJavaScript("initTradeInTradabilityCheck();");
		nonTradableWebMarkupContainer.setVisible(true);

		if (target != null){
		    target.add(tradabilityCheckId);
		}
	    }
	}catch(Exception exc){
	    LOGGER.error(ExceptionUtils.getStackTrace(exc));
	    displayError(getString("TradeInTradabilityCheck.service.down.message"), target);
	    return;

	}

    }
}
