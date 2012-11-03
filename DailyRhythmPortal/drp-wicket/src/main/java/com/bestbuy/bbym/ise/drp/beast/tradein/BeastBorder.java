package com.bestbuy.bbym.ise.drp.beast.tradein;

import org.apache.commons.lang.StringUtils;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.border.Border;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.bestbuy.bbym.ise.drp.beast.common.LoadingModalPanel;
import com.bestbuy.bbym.ise.drp.beast.common.MessageModalPanel;
import com.bestbuy.bbym.ise.drp.beast.common.OkCancelModalPanel;
import com.bestbuy.bbym.ise.drp.beast.common.StatusPage;
import com.bestbuy.bbym.ise.drp.common.PageParameterKeys;

/**
 * Parent class for tradeIn pages.
 * 
 * @author a915722
 */
public class BeastBorder extends Border {

    private AbstractDefaultAjaxBehavior wicketBehavior;

    private static final long serialVersionUID = 1L;

    private final OkCancelModalPanel quitModalPanel;
    private final MessageModalPanel messageModalPanel;
    private final LoadingModalPanel loadingModalPanel;

    private final AjaxSubmitLink closeButton;
    private final AjaxSubmitLink skipTradeInButton;

    public AjaxSubmitLink getCloseButton() {
	return closeButton;
    }

    public AjaxSubmitLink getSkipTradeInButton() {
	return skipTradeInButton;
    }

    private String pageTitle = "[missing title]";
    private String processingMessage = "Retrieving Results";

    private final Form form;
    private final NotificationPanel notificationPanel;

    public NotificationPanel getNotificationPanel() {
	return notificationPanel;
    }

    /**
     * Returns reference to quit modal window.
     * 
     * @return reference to quit modal window.
     */
    public OkCancelModalPanel getQuitModalPanel() {
	return quitModalPanel;
    }

    /**
     * Returns reference to spinner modal window.
     * 
     * @return reference to spinner modal window.
     */
    public LoadingModalPanel getLoadingModalPanel() {
	return loadingModalPanel;
    }

    /**
     * Returns reference to page form.
     * 
     * @return reference to page form.
     */
    public Form getForm() {
	return form;
    }

    /**
     * Constructs a new instance.
     * 
     * @param id
     *            element uinique id.
     */
    public BeastBorder(final String id) {
	super(id);

	messageModalPanel = new MessageModalPanel("messageModalPanel", "OK") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onClose(AjaxRequestTarget target) {
		if (StringUtils.isNotEmpty(this.getReturnFocusId())){
		    target.appendJavaScript("returnControlFocus('" + this.getReturnFocusId() + "')");
		}

		if (this.getSendRedirectToPage() != null){
		    BeastBorder.this.setResponsePage(this.getSendRedirectToPage());
		}
	    }
	};
	messageModalPanel.setOutputMarkupPlaceholderTag(true);
	messageModalPanel.setEscapeModelStrings(false);
	addToBorder(messageModalPanel);

	loadingModalPanel = new LoadingModalPanel("loadingModalPanel") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onClose(AjaxRequestTarget target) {
	    }
	};
	loadingModalPanel.setOutputMarkupPlaceholderTag(true);
	addToBorder(loadingModalPanel);

	quitModalPanel = new OkCancelModalPanel("quitModalPanel", "Yes", "No") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onClose(AjaxRequestTarget target) {
		if (quitModalPanel.isOk()){
		    getDailyRhythmPortalSession().setBestBuyCustomer(null);
		    getDailyRhythmPortalSession().setProtectionPlanList(null);
		    getDailyRhythmPortalSession().setProtectionPlanDetails(null);
		    PageParameters pp = new PageParameters();
		    pp.add(PageParameterKeys.EXIT_STATUS.getUrlParameterKey(), "cancel");
		    setResponsePage(new StatusPage(pp));
		}
	    }
	};
	quitModalPanel.setOutputMarkupPlaceholderTag(true);
	addToBorder(quitModalPanel);

	form = new Form("tradeInHotKeysForm");
	form.setMarkupId("tradeInHotKeysForm");
	form.setOutputMarkupId(true);

	addToBorder(form);

	notificationPanel = new NotificationPanel("feedback", this);
	notificationPanel.setOutputMarkupId(true);
	notificationPanel.setMarkupId("feedback");
	form.add(notificationPanel);

	form.add(getBodyContainer());

	Label pageTitleLabel = new Label("pageTitleLabel", new PropertyModel<String>(this, "pageTitle"));
	form.add(pageTitleLabel);

	// close button
	closeButton = new AjaxSubmitLink("closeButton", form) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void onSubmit(AjaxRequestTarget target, Form<?> form) {

		if (!quitModalPanel.isOpen()){
		    String question = getString("close.cancel.trade.in.question1.label") + "\n \n"
			    + getString("close.cancel.trade.in.question2.label");
		    quitModalPanel.setMultiLineQuestion(question);
		    if (target != null){
			quitModalPanel.open(target);
		    }
		}
	    }

	    @Override
	    protected void onError(AjaxRequestTarget target, Form<?> form) {
		onSubmit(target, form);
	    }

	};
	closeButton.setMarkupId("closeButton");
	closeButton.setOutputMarkupPlaceholderTag(true);
	form.add(closeButton);

	// skip trade in button
	skipTradeInButton = new AjaxSubmitLink("skipTradeInButton", form) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void onSubmit(AjaxRequestTarget target, Form<?> form) {

		if (!quitModalPanel.isOpen()){
		    String question = getString("close.cancel.trade.in.question1.label") + "\n \n"
			    + getString("close.cancel.trade.in.question2.label");
		    quitModalPanel.setMultiLineQuestion(question);
		    if (target != null){
			quitModalPanel.open(target);
		    }
		}
	    }

	    @Override
	    protected void onError(AjaxRequestTarget target, Form<?> form) {
		onSubmit(target, form);
	    }

	};
	skipTradeInButton.setMarkupId("skipTradeInButton");
	skipTradeInButton.setOutputMarkupPlaceholderTag(true);
	form.add(skipTradeInButton);

	wicketBehavior = new AbstractDefaultAjaxBehavior() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void respond(AjaxRequestTarget target) {
		String id = RequestCycle.get().getRequest().getRequestParameters().getParameterValue("id").toString();
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
		onDomReadyJS.append("wicketBehavior = function(id) { ");
		onDomReadyJS.append("wicketAjaxGet('");
		onDomReadyJS.append(wicketBehavior.getCallbackUrl());
		onDomReadyJS.append("&id='+id); };");
		onDomReadyJS.append("bindHotKeys();");

		response.renderOnDomReadyJavaScript(onDomReadyJS.toString());
	    }
	});

    }

    /**
     * Returns reference to message modal window.
     * 
     * @return reference to message modal window.
     */
    public MessageModalPanel getMessageModalPanel() {
	return messageModalPanel;
    }

    /**
     * Disables a button, and adds the appropriate CSS class to indicate button
     * is disabled. If using Ajax, will still need to add the button to the
     * target for refreshing.
     * 
     * @param button
     *            - The button to set status for.
     * @param enabled
     *            - True for enabled, false if not.
     */
    public void setButtonEnabled(AjaxSubmitLink button, boolean enabled) {
	button.setEnabled(enabled);
	String cssClasses = String.valueOf(button.getMarkupAttributes().get("class"));
	cssClasses.replace("disabled", "");
	if (!enabled)
	    cssClasses += " disabled";
	button.add(AttributeModifier.replace("class", cssClasses));
    }

    /**
     * Disables a button, and adds the appropriate CSS class to indicate button
     * is disabled. Also adds the button to the request Target for updating.
     * 
     * @param button
     *            the button to set status for.
     * @param enabled
     *            {@code rue} for enabled, {@code false} if not.
     * @param target
     *            the target to use for refreshing component.
     */
    public void setButtonEnabled(AjaxSubmitLink button, boolean enabled, AjaxRequestTarget target) {
	this.setButtonEnabled(button, enabled);
	target.add(button);
    }

    /**
     * Sets page title, suppose to be populated from Titan service.
     * 
     * @param title
     *            page title.
     */
    public void setPageTitle(String title) {
	this.pageTitle = title;
    }

    /**
     * Returns page title.
     * 
     * @return page title.
     */
    public String getPageTitle() {
	return this.pageTitle;
    }

    /**
     * Returns spinner message.
     * 
     * @return spinner message.
     */
    public String getProcessingMessage() {
	return processingMessage;
    }

    /**
     * Sets spinner message.
     * 
     * @param processingMessage
     *            spinner message.
     */
    public void setProcessingMessage(String processingMessage) {
	this.processingMessage = processingMessage;
    }

    public void displayError(String message, final AjaxRequestTarget target) {
	getMessageModalPanel().setMessage(message);

	if (!getMessageModalPanel().isOpen()){
	    if (target != null){
		getMessageModalPanel().open(target);
		return;
	    }
	}
    }

}
