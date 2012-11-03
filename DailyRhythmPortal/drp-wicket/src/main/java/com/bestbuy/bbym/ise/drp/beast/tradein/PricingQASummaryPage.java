package com.bestbuy.bbym.ise.drp.beast.tradein;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bestbuy.bbym.ise.drp.beast.common.OkCancelModalPanel;
import com.bestbuy.bbym.ise.drp.beast.common.StatusPage;
import com.bestbuy.bbym.ise.drp.beast.tradein.mapper.BuyPriceQuestion;
import com.bestbuy.bbym.ise.drp.beast.tradein.mapper.BuyPriceQuestionOption;
import com.bestbuy.bbym.ise.drp.common.PageParameterKeys;
import com.bestbuy.bbym.ise.drp.domain.TitanDevice;
import com.bestbuy.bbym.ise.drp.domain.ui.UIDataItem;
import com.bestbuy.bbym.ise.drp.domain.ui.UIReply;
import com.bestbuy.bbym.ise.drp.domain.ui.UIRequest;
import com.bestbuy.bbym.ise.drp.service.TradeInService;
import com.bestbuy.bbym.ise.drp.service.UIService;
import com.bestbuy.bbym.ise.exception.IseBusinessException;
import com.bestbuy.bbym.ise.exception.ServiceException;

/**
 * PricingQASummaryPage
 * 
 * @author a915722
 */
public class PricingQASummaryPage extends BeastPage {

    private static Logger LOGGER = LoggerFactory.getLogger(PricingQASummaryPage.class);
    private static final long serialVersionUID = 1L;

    final List<BuyPriceQuestionOption> options = new ArrayList<BuyPriceQuestionOption>();
    @SpringBean(name = "uiService")
    private UIService uiService;
    @SpringBean(name = "tradeInService")
    private TradeInService tradeInService;

    String chkFinalAdjustedPrice;

    final CheckBox customerAgreeChkbox;
    private OkCancelModalPanel zeroValueCancel;

    /**
     * Constructs a new instance.
     * 
     * @param parameters PageParameters
     */
    public PricingQASummaryPage(final PageParameters parameters) {
	super(parameters);

	getBorder().setPageTitle(getString("pricingSummaryQAPage.valueEstimate.label"));

	final WebMarkupContainer webMarkupContainer = new WebMarkupContainer("product-details");

	add(webMarkupContainer);

	if (getDailyRhythmPortalSession() == null){
	    throw new RuntimeException("Session doesn't exist. getDailyRhythmPortalSession() == null");
	}

	if (getDailyRhythmPortalSession().getPriceQAQuestions() == null){
	    throw new RuntimeException("No data exist. getDailyRhythmPortalSession().getPriceQAQuestions() == null");
	}

	// Get questions from session variable
	webMarkupContainer.add(new ListView<BuyPriceQuestion>("questionList", getDailyRhythmPortalSession()
		.getPriceQAQuestions().getSummaryQuestions()) {

	    private static final long serialVersionUID = 1L;

	    // Populate questions and answers
	    @Override
	    protected void populateItem(ListItem<BuyPriceQuestion> item) {
		BuyPriceQuestion question = item.getModelObject();
		if (question.getSelected() != null){
		    item.add(new Label("question", question.getQuestionText()));
		    item.add(new Label("answer", question.getSelected().getValue()));
		}
	    }
	});

	// Get final adjusted price from session variable
	chkFinalAdjustedPrice = getDailyRhythmPortalSession().getPriceQAQuestions().getFinalAdjustedPrice();
	int lastIndex = chkFinalAdjustedPrice.length();
	int indexOfPeriod = -1;
	char ch;
	for(int x = 0; x < lastIndex; x++){
	    ch = chkFinalAdjustedPrice.charAt(x);
	    if (ch == '.'){
		indexOfPeriod = x;
	    }
	}
	// check to see where the decimal point was found if -1 no decimal point found
	if (indexOfPeriod == -1){
	    chkFinalAdjustedPrice = chkFinalAdjustedPrice + ".00";
	}else if (indexOfPeriod == lastIndex - 2){
	    chkFinalAdjustedPrice = chkFinalAdjustedPrice + "0";
	}

	Label finalAdjustedPrice = new Label("finalAdjustedPrice", chkFinalAdjustedPrice);
	getDailyRhythmPortalSession().getTitanDataItem().setStringProp("finalAdjustedPrice", chkFinalAdjustedPrice);
	getDailyRhythmPortalSession().getTitanDevice().setTradeInValue(
		new BigDecimal(getDailyRhythmPortalSession().getPriceQAQuestions().getFinalAdjustedPrice()));
	webMarkupContainer.add(finalAdjustedPrice);

	getBorder().setButtonEnabled(nextButton, false);

	// Create CheckBox for customer agreement
	customerAgreeChkbox = new CheckBox("customerAgreeChkbox", Model.of(Boolean.FALSE));

	customerAgreeChkbox.add(new AjaxFormComponentUpdatingBehavior("onclick") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void onUpdate(AjaxRequestTarget target) {
		logger.debug("onUpdate -- nextButton.isEnabled():" + nextButton.isEnabled());
		if (nextButton.isEnabled()){

		    getBorder().setButtonEnabled(nextButton, false);
		}else{
		    getBorder().setButtonEnabled(nextButton, true);
		}
		target.addComponent(nextButton);

	    }
	});
	customerAgreeChkbox.setMarkupId("customerAgreeChkbox"); //this is important.
	customerAgreeChkbox.setOutputMarkupId(true);
	webMarkupContainer.setOutputMarkupPlaceholderTag(true);
	webMarkupContainer.add(customerAgreeChkbox);

	Label note = new Label("note", getString("pricingSummaryQAPage.note.label"));
	webMarkupContainer.add(note);

	zeroValueCancel = new OkCancelModalPanel("zeroValueCancel", "Cancel Trade-In", "Back") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onClose(AjaxRequestTarget target) {
		if (zeroValueCancel.isOk()){
		    getDailyRhythmPortalSession().setBestBuyCustomer(null);
		    getDailyRhythmPortalSession().setProtectionPlanList(null);
		    getDailyRhythmPortalSession().setProtectionPlanDetails(null);
		    PageParameters pp = new PageParameters();
		    pp.add(PageParameterKeys.EXIT_STATUS.getUrlParameterKey(), "cancel");
		    setResponsePage(new StatusPage(pp));
		}
	    }
	};
	String zeroValueCancelMessage = "You cannot Trade In a device that has a $0.00 value." + "\n\n"
		+ "Press Cancel Trade-In to return to the activation";
	zeroValueCancel.setMultiLineQuestion(zeroValueCancelMessage);
	zeroValueCancel.setOutputMarkupPlaceholderTag(true);
	add(zeroValueCancel);

    }

    /**
     * @see com.bestbuy.bbym.ise.drp.beast.tradein.BeastPage#clearForm(org.apache.wicket.ajax.AjaxRequestTarget)
     */
    @Override
    void clearForm(AjaxRequestTarget target) {
	try{
	    customerAgreeChkbox.setDefaultModel(Model.of(Boolean.FALSE));
	    getBorder().setButtonEnabled(nextButton, false);
	    if (target != null){
		target.add(nextButton);
		target.add(customerAgreeChkbox);

	    }
	}catch(Exception exc){
	    LOGGER.error(exc.getMessage(), exc);
	    displayError(getString("PricingQASummaryPage.service.down.message"), target);
	    return;
	}

    }

    /**
     * @see com.bestbuy.bbym.ise.drp.beast.tradein.BeastPage#goBack(org.apache.wicket.ajax.AjaxRequestTarget)
     */
    @Override
    void goBack(AjaxRequestTarget target) {
	try{
	    this.setResponsePage(new TradeInConditionPage(this.getPageParameters(), null));
	}catch(Exception exc){
	    LOGGER.error(exc.getMessage(), exc);
	    displayError(getString("PricingQASummaryPage.service.down.message"), target);
	    return;
	}
    }

    @Override
    void nextPage(AjaxRequestTarget target, Form<?> form) {
	if (chkFinalAdjustedPrice.equals("0.00")){
	    zeroValueCancel.open(target);
	}else{
	    try{
		UIRequest uiRequest = getDailyRhythmPortalSession().getPriceQAQuestions().getUiRequest();
		uiRequest.setName("CreateCart");

		uiRequest.put("domDocument", getDailyRhythmPortalSession().getPriceQAQuestions().getDomDataItem());
		UIDataItem addToCart = (UIDataItem) uiRequest.get("addToCart");
		addToCart.setStringProp("userId", getDailyRhythmPortalSession().getDrpUser().getUserId());
		UIReply uiReply = uiService.processUIRequest(uiRequest);
		UIDataItem uiDataItem = (UIDataItem) uiReply.get("nextDataItem");

		TitanDevice titanDevice = getDailyRhythmPortalSession().getTitanDevice();
		titanDevice.setNextUrl(uiDataItem.getStringProp("NextUrl", null));
		titanDevice.setCartId(Long.parseLong(uiDataItem.getStringProp("CartId", null)));
		tradeInService.writeTradeInRecord(titanDevice);

		this.getPageParameters().add(PageParameterKeys.EXIT_STATUS.getUrlParameterKey(), "success");
		this.setResponsePage(new StatusPage(this.getPageParameters()));
	    }catch(IseBusinessException ex){
		LOGGER.error(ex.getMessage(), ex);
		displayError(getString("PricingQASummaryPage.service.down.message"), target);
		return;
	    }catch(ServiceException e){
		LOGGER.error(e.getMessage(), e);
		displayError(getString("PricingQASummaryPage.service.down.message"), target);
		return;
	    }catch(Exception exc){
		LOGGER.error(exc.getMessage(), exc);
		displayError(getString("PricingQASummaryPage.service.down.message"), target);
		return;
	    }

	}
    }
}
