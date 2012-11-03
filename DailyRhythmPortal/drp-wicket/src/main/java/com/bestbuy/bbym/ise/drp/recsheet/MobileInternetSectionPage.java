package com.bestbuy.bbym.ise.drp.recsheet;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.validation.validator.StringValidator.MaximumLengthValidator;

public class MobileInternetSectionPage extends AbstractRecSheetPage {

    private static final long serialVersionUID = 1L;

    private Integer internetCount = 150;

    TextArea<String> mobileInternetTextArea;

    public MobileInternetSectionPage(PageParameters parameters) {
	super(parameters);

	recommendation.setRecShtTyp(1);

	final Label internetCountLabel = new Label("internetCountLabel", new PropertyModel<Integer>(this,
		"internetCount"));
	internetCountLabel.setMarkupId("internetCountLabel");
	internetCountLabel.setOutputMarkupPlaceholderTag(true);
	form.add(internetCountLabel);

	mobileInternetTextArea = new TextArea<String>("mobileInternet", new PropertyModel<String>(recommendation,
		"netUseInfo"));
	mobileInternetTextArea.add(new AjaxFormComponentUpdatingBehavior("onkeyup") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void onUpdate(AjaxRequestTarget target) {

		if (recommendation != null && recommendation.getNetUseInfo() != null){
		    internetCount = 150 - recommendation.getNetUseInfo().length();
		    target.add(internetCountLabel);
		}else{
		    internetCount = 150;
		    target.add(internetCountLabel);
		}

	    }
	});
	mobileInternetTextArea.setMarkupId("netUseInfo");
	mobileInternetTextArea.setOutputMarkupPlaceholderTag(true);
	mobileInternetTextArea.add(new MaximumLengthValidator(150));
	form.add(mobileInternetTextArea);

	setLinkVisible(this.clearSectionButton, RecSheetsSections.INTERNET_USAGE.dataWasEntered(recommendation));
    }

    @Override
    void clearSection(AjaxRequestTarget target) {
	recommendation.setNetUseInfo(null);
	internetCount = 150;
	form.clearInput();

	target.add(form);
	target.add(mobileInternetTextArea);

	markSections();

	setLinkVisible(this.clearSectionButton, RecSheetsSections.INTERNET_USAGE.dataWasEntered(recommendation));

    }

    @Override
    void saveScreenValues(AjaxRequestTarget target) {
	setLinkVisible(this.clearSectionButton, RecSheetsSections.INTERNET_USAGE.dataWasEntered(recommendation));
    }

}
