package com.bestbuy.bbym.ise.drp.beast.tradein;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bestbuy.bbym.ise.drp.beast.common.OkCancelCenterModalPanelTallerButtons;
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
 * Implements 1.2. (22) Product Information
 *
 * @author a915722
 */
public class EditProductInfoPage extends BeastPage {

    private static final String PROMO_CODE_INCLUDE = "promocodeskip";

    private static Logger logger = LoggerFactory.getLogger(EditProductInfoPage.class);
    private static final long serialVersionUID = 1L;

    private String dataSharingKey;
    private TitanDevice titanDevice;

    @SpringBean(name = "tradeInService")
    private TradeInService tradeInService;

    @SpringBean(name = "uiService")
    private UIService uiService;

    private String promoCodeSkip;

    private final OkCancelCenterModalPanelTallerButtons messageModalPanelRetry;

    public void displayRetry(String message, final AjaxRequestTarget target) {
	messageModalPanelRetry.setQuestion(message);

	if (!messageModalPanelRetry.isOpen()){
	    if (target != null){
		messageModalPanelRetry.open(target);
		return;
	    }
	}
    }

    /**
     * Constructs a new new instance.
     *
     * @param parameters PageParameters
     */
    public EditProductInfoPage(final PageParameters parameters) {
	super(parameters);

	this.getBorder().setPageTitle("");

	dataSharingKey = parameters.get(PageParameterKeys.DATA_SHARING_KEY.getUrlParameterKey()).toString();

	if (parameters.get(PROMO_CODE_INCLUDE) != null){
	    promoCodeSkip = parameters.get(PROMO_CODE_INCLUDE).toString();
	}else{
	    promoCodeSkip = null;
	}

	nextButton.setVisible(false);
	backButton.setVisible(false);
	clearButton.setVisible(false);

	this.getBorder().getCloseButton().setVisible(false);
	this.getBorder().getSkipTradeInButton().setVisible(false);

	messageModalPanelRetry = new OkCancelCenterModalPanelTallerButtons("messageModalPanelRetry", "Retry",
		"Discard Promo and Continue") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onClose(AjaxRequestTarget target) {
		if (this.isOk()){
		    EditProductInfoPage.this.getPageParameters().remove(PROMO_CODE_INCLUDE);
		}else{
		    EditProductInfoPage.this.getPageParameters().add(PROMO_CODE_INCLUDE, "true");
		}

		EditProductInfoPage.this.setResponsePage(new EditProductInfoPage(EditProductInfoPage.this
			.getPageParameters()));
	    }
	};
	messageModalPanelRetry.setOutputMarkupPlaceholderTag(true);
	messageModalPanelRetry.setEscapeModelStrings(false);

	add(messageModalPanelRetry);

	//messageModalPanelRetry.setSendRedirectToPage(new EditProductInfoPage(this.getPageParameters()));

	final AbstractDefaultAjaxBehavior sendRedirect = new AbstractDefaultAjaxBehavior() {

	    private static final long serialVersionUID = 1L;

	    /**
	     * @see org.apache.wicket.ajax.AbstractDefaultAjaxBehavior#respond(org.apache.wicket.ajax.AjaxRequestTarget)
	     */
	    @Override
	    protected void respond(AjaxRequestTarget target) {

		if (RequestCycle.get().getRequest().getRequestParameters().getParameterValue("pp") != null){
		    String pp = RequestCycle.get().getRequest().getRequestParameters().getParameterValue("pp")
			    .toString();

		    UIReply uiReply = null;
		    try{
			titanDevice = tradeInService.getTradeInCarrierRecord(dataSharingKey);
			EditProductInfoPage.this.getDailyRhythmPortalSession().setTitanDevice(titanDevice);

			UIDataItem di = new UIDataItem();
			String url = titanDevice.getNextUrl();
			di.setStringProp("href", url);
			if (EditProductInfoPage.this.promoCodeSkip == null
				|| "true".equalsIgnoreCase(EditProductInfoPage.this.promoCodeSkip) == false){

			    di.setStringProp("promoCode", titanDevice.getPromoCode());
			}
			di.setStringProp("userId", getDailyRhythmPortalSession().getDrpUser().getUserId());
			UIRequest req = new UIRequest();
			req.setName("EditProductAnswers");
			req.put("nextUrl", di);
			uiReply = uiService.processUIRequest(req);

			UIDataItem textData = (UIDataItem) uiReply.get("promoError");
			if (textData != null){
			    String message = EditProductInfoPage.this
				    .getString("EditProductInfoPage.promo.code.not.found");

			    if (titanDevice.getPromoCode() == null){
				message = message.replace("<code123>", "UNKNOWN");
			    }else{
				message = message.replace("<code123>", titanDevice.getPromoCode());
			    }
			    EditProductInfoPage.this.getBorder().getMessageModalPanel().setSendRedirectToPage(
				    new EditProductAnswersPage(EditProductInfoPage.this.getPageParameters(), uiReply));

			    displayError(message, target);
			    return;
			}

			EditProductInfoPage.this.setResponsePage(new EditProductAnswersPage(EditProductInfoPage.this
				.getPageParameters(), uiReply));
		    }catch(IseBusinessException ex){
			String message = EditProductInfoPage.this.getString("EditProductInfoPage.titan.call.failed");
			if (titanDevice.getPromoCode() == null){
			    message = message.replace("<code123>", "UNKNOWN");
			}else{
			    message = message.replace("<code123>", titanDevice.getPromoCode());
			}

			displayRetry(message, target);
			return;
		    }catch(ServiceException ex){
			String message = EditProductInfoPage.this.getString("EditProductInfoPage.titan.call.failed");
			if (titanDevice.getPromoCode() == null){
			    message = message.replace("<code123>", "UNKNOWN");
			}else{
			    message = message.replace("<code123>", titanDevice.getPromoCode());
			}

			displayRetry(message, target);
			return;
		    }catch(Exception exc){
			String message = EditProductInfoPage.this.getString("EditProductInfoPage.titan.call.failed");
			if (titanDevice.getPromoCode() == null){
			    message = message.replace("<code123>", "UNKNOWN");
			}else{
			    message = message.replace("<code123>", titanDevice.getPromoCode());
			}
			displayRetry(message, target);
			return;
		    }

		    return;
		}

	    }

	    /**
	     * @see org.apache.wicket.ajax.AbstractDefaultAjaxBehavior#renderHead(org.apache.wicket.Component, org.apache.wicket.markup.html.IHeaderResponse)
	     */
	    @Override
	    public void renderHead(Component component, IHeaderResponse response) {
		super.renderHead(component, response);
		StringBuilder onDomReadyJS = new StringBuilder();

		onDomReadyJS.append("redirectProxyPage = function(pp) { ");
		onDomReadyJS.append("wicketAjaxGet('");
		onDomReadyJS.append(this.getCallbackUrl());
		onDomReadyJS.append("&pp='+pp); };redirectProxyPage('test');");

		response.renderOnDomReadyJavaScript(onDomReadyJS.toString());

		logger.debug("onDomReadyJS=" + onDomReadyJS);
	    }
	};
	add(sendRedirect);

    }

    /**
     * @see
     * com.bestbuy.bbym.ise.drp.beast.tradein.BeastPage#clearForm(org.apache.wicket.ajax.AjaxRequestTarget)
     */
    @Override
    void clearForm(AjaxRequestTarget target) {

    }

    /**
     * @see
     * com.bestbuy.bbym.ise.drp.beast.tradein.BeastPage#goBack(org.apache.wicket.ajax.AjaxRequestTarget)
     */
    @Override
    void goBack(AjaxRequestTarget target) {
    }

    /**
     * @see
     * com.bestbuy.bbym.ise.drp.beast.tradein.BeastPage#nextPage(org.apache.wicket.ajax.AjaxRequestTarget,
     * org.apache.wicket.markup.html.form.Form)
     */
    @Override
    void nextPage(AjaxRequestTarget target, Form<?> form) {

    }
}
