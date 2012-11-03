package com.bestbuy.bbym.ise.drp.recsheet;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.validation.validator.StringValidator.MaximumLengthValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MobileOurRecommendationsSectionPage extends AbstractRecSheetPage {

    private static final long serialVersionUID = 1L;

    private static Logger logger = LoggerFactory.getLogger(MobileOurRecommendationsSectionPage.class);
    private Integer connectivityCount = 200;
    private Integer phoneDeviceCount = 100;

    TextArea<String> connectivityTextArea;
    TextArea<String> phoneDeviceTextArea;

    public MobileOurRecommendationsSectionPage(PageParameters parameters) {
	super(parameters);

	recommendation.setRecShtTyp(1);

	Label title = new Label("sectionTitle", getString("mobileOurRecommendationsSectionForm.title.label"));
	title.setOutputMarkupPlaceholderTag(true);
	form.add(title);

	final Label connectivityCountLabel = new Label("connectivityCountLabel", new PropertyModel<Integer>(this,
		"connectivityCount"));
	connectivityCountLabel.setMarkupId("connectivityCountLabel");
	connectivityCountLabel.setOutputMarkupPlaceholderTag(true);
	form.add(connectivityCountLabel);

	connectivityTextArea = new TextArea<String>("connectivityPlanFeaturesUsage", new PropertyModel<String>(
		recommendation, "recommendedSubscription"));
	connectivityTextArea.add(new AjaxFormComponentUpdatingBehavior("onkeyup") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void onUpdate(AjaxRequestTarget target) {
		logger.debug("subscription info..." + recommendation.getSubscriptionInfo());
		if (recommendation != null && recommendation.getRecommendedSubscription() != null){
		    connectivityCount = 200 - recommendation.getRecommendedSubscription().length();
		    target.add(connectivityCountLabel);
		}else{
		    connectivityCount = 200;
		    target.add(connectivityCountLabel);
		}

	    }
	});
	connectivityTextArea.add(new MaximumLengthValidator(200));
	form.add(connectivityTextArea);

	final Label phoneDeviceCountLabel = new Label("phoneDeviceCountLabel", new PropertyModel<Integer>(this,
		"phoneDeviceCount"));
	phoneDeviceCountLabel.setMarkupId("phoneDeviceCountLabel");
	phoneDeviceCountLabel.setOutputMarkupPlaceholderTag(true);
	form.add(phoneDeviceCountLabel);

	phoneDeviceTextArea = new TextArea<String>("phoneDevice", new PropertyModel<String>(recommendation,
		"recommendedDevice"));
	phoneDeviceTextArea.add(new AjaxFormComponentUpdatingBehavior("onkeyup") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void onUpdate(AjaxRequestTarget target) {
		logger.debug("subscription info..." + recommendation.getSubscriptionInfo());
		if (recommendation != null && recommendation.getRecommendedDevice() != null){
		    phoneDeviceCount = 100 - recommendation.getRecommendedDevice().length();
		    target.add(phoneDeviceCountLabel);
		}else{
		    phoneDeviceCount = 100;
		    target.add(phoneDeviceCountLabel);
		}

	    }
	});
	phoneDeviceTextArea.add(new MaximumLengthValidator(100));
	form.add(phoneDeviceTextArea);

	setLinkVisible(this.clearSectionButton, RecSheetsSections.OUR_RECOMMENDATIONS.dataWasEntered(recommendation));
    }

    @Override
    void clearSection(AjaxRequestTarget target) {

	recommendation.setRecommendedSubscription(null);
	recommendation.setRecommendedDevice(null);
	connectivityCount = 200;
	phoneDeviceCount = 100;
	form.clearInput();

	target.add(form);
	target.add(connectivityTextArea);
	target.add(phoneDeviceTextArea);

	markSections();

	setLinkVisible(this.clearSectionButton, RecSheetsSections.OUR_RECOMMENDATIONS.dataWasEntered(recommendation));
    }

    @Override
    void saveScreenValues(AjaxRequestTarget target) {
	setLinkVisible(this.clearSectionButton, RecSheetsSections.OUR_RECOMMENDATIONS.dataWasEntered(recommendation));
    }

}
