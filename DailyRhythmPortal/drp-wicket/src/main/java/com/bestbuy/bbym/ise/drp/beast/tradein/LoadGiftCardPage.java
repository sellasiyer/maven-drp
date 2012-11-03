package com.bestbuy.bbym.ise.drp.beast.tradein;

import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.Date;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.OnChangeAjaxBehavior;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.bestbuy.bbym.ise.drp.beast.common.MessageModalPanel;
import com.bestbuy.bbym.ise.drp.beast.common.StatusPage;
import com.bestbuy.bbym.ise.drp.common.PageParameterKeys;
import com.bestbuy.bbym.ise.drp.domain.TitanDevice;
import com.bestbuy.bbym.ise.drp.domain.ui.UIDataItem;
import com.bestbuy.bbym.ise.drp.domain.ui.UIDataList;
import com.bestbuy.bbym.ise.drp.domain.ui.UIReply;
import com.bestbuy.bbym.ise.drp.domain.ui.UIRequest;
import com.bestbuy.bbym.ise.drp.service.TradeInService;
import com.bestbuy.bbym.ise.drp.service.UIService;
import com.bestbuy.bbym.ise.drp.util.behavior.DefaultFocusBehavior;
import com.bestbuy.bbym.ise.drp.util.behavior.DisableEnterKeyBehavior;
import com.bestbuy.bbym.ise.exception.IseBusinessException;
import com.bestbuy.bbym.ise.exception.ServiceException;

public class LoadGiftCardPage extends BeastPage {

    private static final long serialVersionUID = 1L;

    private String barcode;
    private WebMarkupContainer loadGiftCardId;
    private MessageModalPanel failedTradeIn;
    @SpringBean(name = "tradeInService")
    private TradeInService tradeInService;
    private TitanDevice titanDevice;

    @SpringBean(name = "uiService")
    private UIService uiService;

    private UIRequest uiRequest;

    public void setBarcode(String barcode) {
	this.barcode = barcode;
    }

    public String getBarcode() {
	return barcode;
    }

    public LoadGiftCardPage(final PageParameters parameters, final UIReply uiReply) {
	super(parameters);
	this.backButton.setVisible(false);
	super.getBorder().getNotificationPanel().setEnabled(false);
	super.getBorder().getNotificationPanel().setVisible(false);

	logger.info(">>> uireply :  " + uiReply.toString());

	this.populatePageTitle(uiReply, "SubmitGiftCard", "GiftCardInfo");

	this.uiRequest = (UIRequest) uiReply.get("SubmitGiftCard");

	getBorder().setButtonEnabled(nextButton, false);

	loadGiftCardId = new WebMarkupContainer("loadGiftCardId");
	loadGiftCardId.setOutputMarkupId(true);
	add(loadGiftCardId);

	final FeedbackPanel feedbackPanel = new FeedbackPanel("loadGiftCardFeedback");
	feedbackPanel.setOutputMarkupPlaceholderTag(true);

	loadGiftCardId.add(new DefaultFocusBehavior());
	loadGiftCardId.add(feedbackPanel);

	final TextField<String> barcode = new TextField<String>("barcode", new PropertyModel<String>(this, "barcode"));
	// barcode.add(new LengthBetweenValidator(15, 16));
	// barcode.add(new PatternValidator("[0-9]+"));
	barcode.add(new DefaultFocusBehavior());
	barcode.setOutputMarkupId(true);
	barcode.setMarkupId("barcode");
	barcode.setRequired(true);
	barcode.add(new DisableEnterKeyBehavior());

	barcode.add(new OnChangeAjaxBehavior() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void onUpdate(AjaxRequestTarget target) {
		String input = barcode.getModelObject();
		logger.debug(">> Updating GiftCard # : " + input);
		if (input != null){
		    getBorder().setButtonEnabled(nextButton, false);
		    if (!(input.length() > 14 || input.length() < 17)){
			displayError("Barcode must be of length 15 to 16.", target);
			target.appendJavaScript("$(window).focus();");
			target.appendJavaScript("initGiftCardScreen();");
			target.appendJavaScript("bindHotKeys();");
			target.appendJavaScript(" $('#barcode').val(\"\");");

			target.appendJavaScript("$('#barcode').focus();");
			target.appendJavaScript("$('#barcode').removeAttr(\"disabled\");");
			target.appendJavaScript("nextButtonEnableBehavior(false);");
		    }else if (!isNumeric(input)){
			displayError("Barcode is invalid. Only numbers allowed.", target);
			target.appendJavaScript("$(window).focus();");
			target.appendJavaScript("initGiftCardScreen();");
			target.appendJavaScript("bindHotKeys();");
			target.appendJavaScript(" $('#barcode').val(\"\");");

			target.appendJavaScript("$('#barcode').focus();");
			target.appendJavaScript("$('#barcode').removeAttr(\"disabled\");");
			target.appendJavaScript("nextButtonEnableBehavior(false);");

		    }else{

			getBorder().setButtonEnabled(nextButton, true);
			UIDataItem di = (UIDataItem) uiRequest.get("GiftCardInfo");

			di.setStringProp("giftCardNumber", input);
		    }

		}else{
		    getBorder().setButtonEnabled(nextButton, false);

		}
		target.focusComponent(barcode);
		target.add(nextButton);
	    }

	});

	loadGiftCardId.add(barcode);

	failedTradeIn = new MessageModalPanel("failedTradeIn", "Continue Activation") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onClose(AjaxRequestTarget target) {
		// TODO - Relinquish control to BEAST

	    }
	};
	String failedMessage = "The Trade-In and gift card transaction failed.\n" + "\n"
		+ "Please go to the stand alone Trade-In flow, search existing carts, and complet the transaction.";
	failedTradeIn.setMulitLineMessage(failedMessage);
	failedTradeIn.setOutputMarkupPlaceholderTag(true);
	loadGiftCardId.add(failedTradeIn);

    }

    /**
     * @see com.bestbuy.bbym.ise.drp.beast.tradein.BeastPage#clearForm(org.apache.wicket.ajax.AjaxRequestTarget)
     */
    @Override
    void clearForm(AjaxRequestTarget target) {
	try{
	    this.setBarcode("");

	    if (target != null){
		target.add(loadGiftCardId);

	    }
	    target.appendJavaScript("initGiftCardScreen();");
	    target.appendJavaScript("bindHotKeys();");
	    target.appendJavaScript("nextButtonEnableBehavior(false);");

	}catch(Exception exc){
	    logger.error(exc.getMessage(), exc);

	    this.displayError(this.getString("LoadGiftCardPage.service.down.message"), target);
	    return;
	}
    }

    @Override
    void goBack(AjaxRequestTarget target) {

    }

    @Override
    void nextPage(AjaxRequestTarget target, Form<?> form) {
	removeBackButtonParameter();
	boolean tradeInResponse = false;
	try{

	    UIDataItem requestDataItem = (UIDataItem) this.uiRequest.get("GiftCardInfo");
	    requestDataItem.setStringProp("userId", getDailyRhythmPortalSession().getDrpUser().getUserId());

	    UIReply uiReply = uiService.processUIRequest(this.uiRequest);

	    if (uiReply.getName().equals("TitanError")){
		UIDataList dl = (UIDataList) uiReply.get("ErrorList");
		UIDataItem di = (UIDataItem) dl.get(0);
		displayError(di.getStringProp("Error", this.getString("LoadGiftCardPage.service.down.message")), target);
		target.add(loadGiftCardId);
		target.appendJavaScript("initGiftCardScreen();");
		target.appendJavaScript("bindHotKeys();");

	    }else{

		UIDataItem di = (UIDataItem) uiReply.get("TradeInInfo");

		titanDevice = getDailyRhythmPortalSession().getTitanDevice();

		titanDevice.setDocURL(di.getStringProp("tradeInDocUrl", null));
		titanDevice.setGiftCardNo(new Long(di.getStringProp("giftCardNumber", null)));
		titanDevice.setItemTag(di.getStringProp("itemTag", null));
		titanDevice.setModifiedBy(getDailyRhythmPortalSession().getDrpUser().getUserId());
		titanDevice.setModifiedOn(new Date());

		tradeInService.updateTradeInDocs(titanDevice);

		PageParameters pp = new PageParameters();
		pp.add(PageParameterKeys.EXIT_STATUS.getUrlParameterKey(), "success");
		this.setResponsePage(new StatusPage(pp));
	    }

	}catch(ServiceException e){
	    logger.error(e.getMessage(), e);
	    this.setBarcode("");
	    this.displayError(this.getString("LoadGiftCardPage.service.down.message"), target);
	    target.add(loadGiftCardId);
	    target.focusComponent(loadGiftCardId);
	    target.appendJavaScript("$(window).focus();");
	    target.appendJavaScript("initGiftCardScreen();");
	    target.appendJavaScript("bindHotKeys();");
	    target.appendJavaScript(" $('#barcode').val(\"\");");

	    target.appendJavaScript("$('#barcode').focus();");
	    target.appendJavaScript("$('#barcode').removeAttr(\"disabled\");");
	    target.appendJavaScript("nextButtonEnableBehavior(false);");
	    return;
	}catch(IseBusinessException e){
	    logger.error(e.getMessage(), e);
	    this.setBarcode("");

	    this.displayError(this.getString("LoadGiftCardPage.service.down.message"), target);
	    target.add(loadGiftCardId);
	    target.focusComponent(loadGiftCardId);
	    target.appendJavaScript("$(window).focus();");
	    target.appendJavaScript("initGiftCardScreen();");
	    target.appendJavaScript("bindHotKeys();");
	    target.appendJavaScript(" $('#barcode').val(\"\");");
	    target.appendJavaScript("$('#barcode').removeAttr(\"disabled\");");
	    target.appendJavaScript("$('#barcode').focus();");
	    target.appendJavaScript("nextButtonEnableBehavior(false);");

	    return;
	}catch(Exception exc){
	    logger.error(exc.getMessage(), exc);
	    this.setBarcode("");
	    this.displayError(this.getString("LoadGiftCardPage.service.down.message"), target);
	    target.add(loadGiftCardId);
	    target.focusComponent(loadGiftCardId);
	    target.appendJavaScript("$(window).focus();");
	    target.appendJavaScript("initGiftCardScreen();");
	    target.appendJavaScript("bindHotKeys();");
	    target.appendJavaScript(" $('#barcode').val(\"\");");
	    target.appendJavaScript("$('#barcode').removeAttr(\"disabled\");");
	    target.appendJavaScript("$('#barcode').focus();");
	    target.appendJavaScript("nextButtonEnableBehavior(false);");
	    return;
	}

	// TODO Check for Trade-In success

    }

    private boolean isNumeric(String barcodeNumber) {
	NumberFormat formatter = NumberFormat.getInstance();
	ParsePosition pos = new ParsePosition(0);
	formatter.parse(barcodeNumber, pos);
	return barcodeNumber.length() == pos.getIndex();

    }

}
