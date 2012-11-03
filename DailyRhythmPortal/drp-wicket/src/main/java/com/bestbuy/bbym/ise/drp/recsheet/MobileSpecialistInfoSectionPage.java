package com.bestbuy.bbym.ise.drp.recsheet;

import org.apache.commons.lang.StringUtils;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.convert.IConverter;
import org.apache.wicket.validation.validator.PatternValidator;
import org.apache.wicket.validation.validator.StringValidator.MaximumLengthValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bestbuy.bbym.ise.drp.domain.DrpUser;
import com.bestbuy.bbym.ise.drp.util.PhoneNumberConverter;

public class MobileSpecialistInfoSectionPage extends AbstractRecSheetPage {

    private static final long serialVersionUID = 1L;

    private static Logger logger = LoggerFactory.getLogger(MobileSpecialistInfoSectionPage.class);

    TextField<String> firstNameTextField;
    TextField<String> lastNameTextField;
    TextField<String> storePhoneNumberTextField;
    TextField<String> extensionTextField;

    public MobileSpecialistInfoSectionPage(PageParameters parameters) {
	super(parameters);

	DrpUser drpuser = getDailyRhythmPortalSession().getDrpUser();

	recommendation.setRecShtTyp(1);

	Label title = new Label("sectionTitle", getString("mobileSpecialistInfoSectionForm.title.label"));
	title.setOutputMarkupPlaceholderTag(true);
	form.add(title);

	if (recommendation.getId() == 0){
	    recommendation.setBbyCnsFrstNm(drpuser.getFirstName());
	    recommendation.setBbyCnsLastNm(drpuser.getLastName());
	    recommendation.setBbyCnsPhNbr(drpuser.getLocationPhoneNum());
	}else{
	    if (StringUtils.isEmpty(recommendation.getBbyCnsFrstNm())){
		recommendation.setBbyCnsFrstNm(drpuser.getFirstName());
	    }
	    if (StringUtils.isEmpty(recommendation.getBbyCnsLastNm())){
		recommendation.setBbyCnsLastNm(drpuser.getLastName());
	    }
	    if (StringUtils.isEmpty(recommendation.getBbyCnsPhNbr())){
		recommendation.setBbyCnsPhNbr(drpuser.getLocationPhoneNum());
	    }

	}

	firstNameTextField = new TextField<String>("firstName", new PropertyModel<String>(recommendation,
		"bbyCnsFrstNm"));
	firstNameTextField.setMarkupId("bbyCnsFrstNm");
	firstNameTextField.add(new PatternValidator("[A-Za-z -]{0,15}"));
	firstNameTextField.add(new MaximumLengthValidator(15));
	firstNameTextField.setOutputMarkupPlaceholderTag(true);
	form.add(firstNameTextField);

	lastNameTextField = new TextField<String>("lastName", new PropertyModel<String>(recommendation, "bbyCnsLastNm"));
	lastNameTextField.setMarkupId("bbyCnsLastNm");
	lastNameTextField.add(new PatternValidator("[A-Za-z -]{0,25}"));
	lastNameTextField.add(new MaximumLengthValidator(25));
	lastNameTextField.setOutputMarkupPlaceholderTag(true);
	form.add(lastNameTextField);

	storePhoneNumberTextField = new TextField<String>("storePhoneNumber", new PropertyModel<String>(recommendation,
		"bbyCnsPhNbr")) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public <C> IConverter<C> getConverter(Class<C> type) {
		return new PhoneNumberConverter();
	    }
	};
	storePhoneNumberTextField.add(new AjaxFormComponentUpdatingBehavior("onBlur") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void onUpdate(AjaxRequestTarget target) {
		target.add(storePhoneNumberTextField);
	    }
	});
	storePhoneNumberTextField.setMarkupId("bbyCnsPhNbr");
	storePhoneNumberTextField.setOutputMarkupPlaceholderTag(true);
	form.add(storePhoneNumberTextField);

	extensionTextField = new TextField<String>("extension",
		new PropertyModel<String>(recommendation, "bbyCnsPhExt"));
	extensionTextField.setMarkupId("bbyCnsPhExt");
	extensionTextField.setOutputMarkupPlaceholderTag(true);
	form.add(extensionTextField);

	setLinkVisible(this.clearSectionButton, RecSheetsSections.SPECIALIST_INFO.dataWasEntered(recommendation));
    }

    @Override
    void clearSection(AjaxRequestTarget target) {
	recommendation.setBbyCnsFrstNm(null);
	recommendation.setBbyCnsLastNm(null);
	recommendation.setBbyCnsPhNbr(null);
	recommendation.setBbyCnsPhExt(null);
	form.clearInput();
	target.add(form);
	target.add(firstNameTextField);
	target.add(lastNameTextField);
	target.add(storePhoneNumberTextField);
	target.add(extensionTextField);

	markSections();

	setLinkVisible(this.clearSectionButton, RecSheetsSections.SPECIALIST_INFO.dataWasEntered(recommendation));
    }

    @Override
    void saveScreenValues(AjaxRequestTarget target) {
	setLinkVisible(this.clearSectionButton, RecSheetsSections.SPECIALIST_INFO.dataWasEntered(recommendation));
    }

}
