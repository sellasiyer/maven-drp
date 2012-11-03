package com.bestbuy.bbym.ise.drp.dashboard;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.ajax.markup.html.form.AjaxCheckBox;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.Radio;
import org.apache.wicket.markup.html.form.RadioGroup;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bestbuy.bbym.ise.domain.Customer;
import com.bestbuy.bbym.ise.domain.Line;
import com.bestbuy.bbym.ise.domain.Store;
import com.bestbuy.bbym.ise.drp.DailyRhythmPortalSession;
import com.bestbuy.bbym.ise.drp.common.NewBaseNavPage;
import com.bestbuy.bbym.ise.drp.domain.OptInResponse;
import com.bestbuy.bbym.ise.drp.service.CarrierDataService;
import com.bestbuy.bbym.ise.drp.util.FormatPropertyModel;
import com.bestbuy.bbym.ise.drp.util.PhoneFormatter;
import com.bestbuy.bbym.ise.drp.utils.DrpConstants;
import com.bestbuy.bbym.ise.exception.ServiceException;

public class OptInSelectionPage extends NewBaseNavPage {

    private static Logger logger = LoggerFactory.getLogger(OptInSelectionPage.class);
    private static final long serialVersionUID = 1L;

    @SpringBean(name = "carrierDataService")
    private CarrierDataService carrierDataService;

    private AbstractDefaultAjaxBehavior wicketBehavior;

    public OptInSelectionPage(final PageParameters parameters) {
	super(parameters);

	final DailyRhythmPortalSession session = getDailyRhythmPortalSession();
	final Line line = session.getSelectedLine();

	final FeedbackPanel optInSelectionFeedbackPanel = new FeedbackPanel("optInSelectionFeedback");
	optInSelectionFeedbackPanel.setOutputMarkupPlaceholderTag(true);
	add(optInSelectionFeedbackPanel);

	final Form<Object> optInSelectionForm = new Form<Object>("optInSelectionForm");
	optInSelectionForm.setOutputMarkupPlaceholderTag(true);
	add(optInSelectionForm);

	final PhoneFormatter<String> phoneFormatter = new PhoneFormatter<String>();

	Label lineNumber = new Label("lineNumber", new ResourceModel("optInSelectionForm.line.label"));
	optInSelectionForm.add(lineNumber);

	Label phoneNumber = new Label("phoneNumber", new FormatPropertyModel<Line, String>(new PropertyModel<Line>(
		line, "mobileNumber"), phoneFormatter, "N/A"));
	optInSelectionForm.add(phoneNumber);

	final RadioGroup<String> optInRadioGroup = new RadioGroup<String>("optInRadioGroup", new PropertyModel<String>(
		line, "optin"));
	optInRadioGroup.setOutputMarkupPlaceholderTag(true);
	final Radio<String> optInVoiceRadio = new Radio<String>("optInVoiceRadio", new Model<String>(Line.OPT_IN_VOICE));
	optInVoiceRadio.setEnabled(false);
	optInVoiceRadio.setOutputMarkupPlaceholderTag(true);
	final Radio<String> optInTextRadio = new Radio<String>("optInTextRadio", new Model<String>(Line.OPT_IN_TEXT));
	optInTextRadio.setEnabled(false);
	optInTextRadio.setOutputMarkupPlaceholderTag(true);

	optInRadioGroup.add(optInVoiceRadio);
	optInRadioGroup.add(optInTextRadio);

	optInSelectionForm.add(optInRadioGroup);

	final AjaxButton submitButton = new AjaxButton("submitButton", new ResourceModel(
		"optInSelectionForm.submit.button"), optInSelectionForm) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
		logger.debug("submitButton onSubmit");
		target
			.appendJavaScript("showButtonLoading(true, '#submitButton');doWicketBehavior('wicketBehavior(\"optIn\")');");
	    }

	    @Override
	    protected void onError(AjaxRequestTarget target, Form<?> form) {
		// TODO Implement me!
	    }
	};
	submitButton.setMarkupId("submitButton");
	submitButton.setOutputMarkupId(true);
	submitButton.setOutputMarkupPlaceholderTag(true);
	submitButton.setEnabled(false);
	optInSelectionForm.add(submitButton);

	AjaxCheckBox esignCbx = new AjaxCheckBox("esignCbx", new PropertyModel<Boolean>(line, "esignVerified")) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void onUpdate(AjaxRequestTarget target) {
		logger.debug("esignCbx onUpdate");
		if (this.getModelObject()){
		    optInVoiceRadio.setEnabled(true);
		    optInTextRadio.setEnabled(true);
		    if (line != null){
			logger.debug("opt-in=" + line.getOptin());
		    }

		    if (line != null && line.getOptin() != null && !Line.OPT_IN_NONE.equalsIgnoreCase(line.getOptin())){
			if (!restrictSubmitButton()){
			    submitButton.setEnabled(true);
			}else{
			    submitButton.setEnabled(false);
			}
		    }
		}else{
		    optInVoiceRadio.setEnabled(false);
		    optInTextRadio.setEnabled(false);
		    submitButton.setEnabled(false);
		}
		target.add(optInVoiceRadio);
		target.add(optInTextRadio);
		target.add(submitButton);

	    }
	};
	optInSelectionForm.add(esignCbx);

	optInVoiceRadio.add(new AjaxEventBehavior("onclick") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void onEvent(AjaxRequestTarget target) {
		logger.debug("optInVoiceRadio onEvent");
		if (optInVoiceRadio.getModelObject() != null
			&& Line.OPT_IN_VOICE.equalsIgnoreCase(optInVoiceRadio.getModelObject())){
		    if (!restrictSubmitButton()){
			submitButton.setEnabled(true);
		    }else{
			submitButton.setEnabled(false);
		    }
		}
		target.add(submitButton);
	    }
	});

	optInTextRadio.add(new AjaxEventBehavior("onclick") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void onEvent(AjaxRequestTarget target) {
		logger.debug("optInTextRadio onEvent");
		if (optInTextRadio.getModelObject() != null
			&& Line.OPT_IN_TEXT.equalsIgnoreCase(optInTextRadio.getModelObject())){
		    if (!restrictSubmitButton()){
			submitButton.setEnabled(true);
		    }else{
			submitButton.setEnabled(false);
		    }
		}
		target.add(submitButton);
	    }
	});

	ExternalLink esignTermsLink = new ExternalLink("esignTermsLink", new ResourceModel("esignTermsLink.url"),
		new ResourceModel("esignTermsLink.label"));
	optInSelectionForm.add(esignTermsLink);

	ExternalLink textMeTermsLink = new ExternalLink("textMeTermsLink", new ResourceModel("textMeTermsLink.url"),
		new ResourceModel("textMeTermsLink.label"));
	optInSelectionForm.add(textMeTermsLink);

	final AjaxButton backButton = new AjaxButton("backButton", new ResourceModel("optInSelectionForm.back.button"),
		optInSelectionForm) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
		setResponsePage(CustomerDashboardPage.class);
	    }

	    @Override
	    protected void onError(AjaxRequestTarget target, Form<?> form) {
		// TODO Implement me!
	    }
	};
	optInSelectionForm.add(backButton);

	// means by which JS can callback into Java
	wicketBehavior = new AbstractDefaultAjaxBehavior() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void respond(AjaxRequestTarget target) {
		StringBuilder respondJS = new StringBuilder();
		String id = RequestCycle.get().getRequest().getRequestParameters().getParameterValue("id").toString();
		logger.debug("wicketBehavior id=" + id);
		if ("optIn".equals(id)){
		    respondJS.append("showButtonLoading(false, '#submitButton');");
		    // invoke service
		    Line optinLine = null;
		    logger.trace("phone:" + line.getMobileNumber());
		    logger.trace("optin:" + line.getOptin());
		    logger.trace("esignVerified:" + line.isEsignVerified());
		    if (line.isEsignVerified()){
			optinLine = new Line();
			optinLine.setMobileNumber(line.getMobileNumber());
			optinLine.setOptin(line.getOptin());
			logger.debug("text optin for " + line.getMobileNumber());
		    }

		    OptInResponse optInResponse = null;
		    if (optinLine != null){
			Customer customer = session.getCustomer();
			List<Line> optinLines = new ArrayList<Line>();
			optinLines.add(optinLine);
			try{
			    optInResponse = carrierDataService.setSubscribersOptIn(optinLines, customer, session
				    .getDrpUser());
			}catch(ServiceException se){
			    logger.error("opt-in failed for customer " + customer.getLastName(), se);
			    optInSelectionForm.error(se.getFullMessage());
			    target.add(optInSelectionForm);
			    target.add(optInSelectionFeedbackPanel);
			    target.add(submitButton);
			    logger.debug("respondJS=" + respondJS.toString());
			    target.appendJavaScript(respondJS.toString());
			    return;
			}
		    }

		    if (optInResponse != null){
			// set line in session object to TEXT/VOICE opt-in
			if (session.getCustomer() != null && session.getCustomer().getSubscription() != null
				&& session.getCustomer().getSubscription().getLines() != null && line.getId() != null){
			    for(Line l: session.getCustomer().getSubscription().getLines()){
				if (l.getId() != null && l.getId().longValue() == line.getId().longValue()){
				    l.setOptin(line.getOptin());
				    break;
				}
			    }
			}
			if (session.getCarrierStore() == null){
			    session.setCarrierStore(new Store());
			}
			session.getCarrierStore().setOptInCount(optInResponse.getOptInCount());
		    }
		    target.add(optInSelectionForm);
		    setResponsePage(CustomerDashboardPage.class);
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
		onDomReadyJS.append("wicketBehavior = function(id) { ");
		onDomReadyJS.append("wicketAjaxGet('");
		onDomReadyJS.append(wicketBehavior.getCallbackUrl());
		onDomReadyJS.append("&id='+id); };");
		logger.debug("onDomReadyJS=" + onDomReadyJS);
		response.renderOnDomReadyJavaScript(onDomReadyJS.toString());
	    }
	});
    }

    public boolean restrictSubmitButton() {
	return getDailyRhythmPortalSession().getDrpUser().hasRole(DrpConstants.DRP_TEAM);
    }

}
