package com.bestbuy.bbym.ise.drp.msw;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.wicket.Page;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow.WindowClosedCallback;
import org.apache.wicket.extensions.markup.html.form.DateTextField;
import org.apache.wicket.extensions.yui.calendar.DatePicker;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.HiddenField;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.ContextRelativeResource;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.convert.IConverter;
import org.apache.wicket.validation.validator.MinimumValidator;
import org.apache.wicket.validation.validator.StringValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bestbuy.bbym.ise.domain.Customer;
import com.bestbuy.bbym.ise.domain.User;
import com.bestbuy.bbym.ise.drp.DailyRhythmPortalSession;
import com.bestbuy.bbym.ise.drp.common.BaseWebPage;
import com.bestbuy.bbym.ise.drp.common.PhoneNumber;
import com.bestbuy.bbym.ise.drp.common.SessionTimeoutPanel;
import com.bestbuy.bbym.ise.drp.common.YesNoPanel;
import com.bestbuy.bbym.ise.drp.dashboard.CustomerDashboardPage;
import com.bestbuy.bbym.ise.drp.dashboard.CustomerDashboardSearchPage;
import com.bestbuy.bbym.ise.drp.domain.DrpUser;
import com.bestbuy.bbym.ise.drp.domain.Essentials;
import com.bestbuy.bbym.ise.drp.domain.Recommendation;
import com.bestbuy.bbym.ise.drp.service.CustomerService;
import com.bestbuy.bbym.ise.drp.service.MobileSalesWorkspaceService;
import com.bestbuy.bbym.ise.drp.util.MoneyConverter;
import com.bestbuy.bbym.ise.drp.util.PhoneNumberConverter;
import com.bestbuy.bbym.ise.exception.ServiceException;
import com.bestbuy.bbym.ise.util.Util;

public class CTDigitalRecSheetPage extends BaseWebPage {

    private static Logger logger = LoggerFactory.getLogger(CTDigitalRecSheetPage.class);
    private static final long serialVersionUID = 1L;

    @SpringBean(name = "mobileSalesWorkspaceService")
    private MobileSalesWorkspaceService mswService;
    @SpringBean(name = "customerService")
    private CustomerService customerService;
    private DigitalRecSheetModel model;
    public Boolean formChanged = false;

    private Form<CTDigitalRecSheetPage> form = new Form<CTDigitalRecSheetPage>("digitalRecSheetForm");
    private Form<CTDigitalRecSheetPage> closeForm = new Form<CTDigitalRecSheetPage>("closeForm");

    public PhoneNumber getPhoneNumber() {
	return new PhoneNumber(model.getRecommendation().getMobileNo());
    }

    public void setPhoneNumber(PhoneNumber phoneNumber) {
	if (model != null){
	    model.getRecommendation().setMobileNo(phoneNumber.toString());
	}
    }

    @SuppressWarnings( {"rawtypes", "unchecked" })
    public CTDigitalRecSheetPage(Recommendation recommendation, final CTRecSheetPage mswPage) {
	final DailyRhythmPortalSession drpSession = getDailyRhythmPortalSession();

	if (recommendation == null){
	    recommendation = new Recommendation(); // check for null!
	}
	model = new DigitalRecSheetModel(recommendation);

	final boolean transferToBeast = recommendation.isTransferFlag();

	form.add(new Image("transfer-to-beast", new ContextRelativeResource("/css/img/transfer-to-beast.png")) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public boolean isVisible() {
		return transferToBeast;
	    }
	});

	formChanged = false;

	FeedbackPanel feedbackPanel = new FeedbackPanel("feedback");
	feedbackPanel.setOutputMarkupPlaceholderTag(true);
	add(feedbackPanel);

	DrpUser user = drpSession.getDrpUser();

	model.setUser(user);

	// Tools Menu components:
	final ModalWindow coverageCheckModal = new ModalWindow("coverageCheckModal");

	// tools menu links:
	form.add(new BookmarkablePageLink("toolsLink1", CustomerDashboardPage.class));

	AjaxLink alink = new AjaxLink("toolsLink2") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onClick(AjaxRequestTarget target) {

		coverageCheckModal.setContent(new CoverageCheckPanel(coverageCheckModal.getContentId(),
			coverageCheckModal));
		coverageCheckModal.show(target);

	    }
	};
	add(coverageCheckModal);
	form.add(alink);

	// no-contract coverage maps link.
	AjaxLink ppcmLink = new AjaxLink("toolsLink3") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onClick(AjaxRequestTarget target) {
		coverageCheckModal.setContent(new NoContractCoveragePanel(coverageCheckModal.getContentId(),
			coverageCheckModal));
		coverageCheckModal.show(target);
	    }
	};
	form.add(ppcmLink);

	Map<LengthProperty, Integer> fieldLengths = this.getLengthProperties();

	final ModalWindow printOrEmailModal = new ModalWindow("printOrEmailModal");
	printOrEmailModal.setHeightUnit("px");
	printOrEmailModal.setInitialHeight(Util.getInt(getString("printOrEmailPopup.height"), 220));
	printOrEmailModal.setWidthUnit("px");
	printOrEmailModal.setInitialWidth(Util.getInt(getString("printOrEmailPopup.width"), 440));
	printOrEmailModal.setResizable(false);
	printOrEmailModal.setWindowClosedCallback(new WindowClosedCallback() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onClose(AjaxRequestTarget target) {
		target.add(form);
	    }
	});

	/**
	 * Launches dialog to perform either a print or email of the current
	 * recommendation worksheet
	 */
	final AjaxButton printOrEmail = new AjaxButton("printOrEmailBtn") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onSubmit(AjaxRequestTarget target, Form<?> form) {
		logger.debug("in print or email handler");
		saveRecSheet();
		printOrEmailModal.show(target);
	    }

	    @Override
	    protected void onError(AjaxRequestTarget target, Form<?> form) {
		// TODO Implement me!
	    }
	};
	form.add(printOrEmail);
	printOrEmail.setOutputMarkupPlaceholderTag(true);

	// print or email modal dialog
	add(printOrEmailModal);
	printOrEmailModal.setPageCreator(new ModalWindow.PageCreator() {

	    private static final long serialVersionUID = 1L;

	    public Page createPage() {
		return new PrintOrEmailDialog(model.getObject());
	    }
	});

	printOrEmailModal.setCloseButtonCallback(new ModalWindow.CloseButtonCallback() {

	    public boolean onCloseButtonClicked(AjaxRequestTarget target) {
		target.prependJavaScript("doLoad();");
		return true;
	    }
	});

	/**
	 * Saves the rec sheet
	 */
	final Button saveButton = new Button("saveBtn") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onSubmit() {
		logger.debug("saving recsheet by button request");
		saveRecSheet();

	    }
	};

	// save rec sheet button
	form.add(saveButton);
	saveButton.setOutputMarkupPlaceholderTag(true);

	// === Clear screen prompt and components ===
	final ModalWindow clearModal = new ModalWindow("clearModal");
	final AjaxButton clearButton = new AjaxButton("clearButton") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onSubmit(AjaxRequestTarget target, Form<?> form) {

		String message = "Clear customer data?\n\nThis will apply to both the Dashboard and the Recommendation Sheet.";

		final YesNoPanel clearPanel = new YesNoPanel(clearModal.getContentId(), message, clearModal);
		// changing button text.
		clearPanel.setYesButtonText("Clear");
		clearPanel.setNoButtonText("Back");

		clearModal.setContent(clearPanel);

		clearModal.setWindowClosedCallback(new ModalWindow.WindowClosedCallback() {

		    private static final long serialVersionUID = 1L;

		    @Override
		    public void onClose(AjaxRequestTarget target) {
			if (clearPanel.getDialogResult()){
			    logger.debug("clearing customer from recsheet.");
			    DailyRhythmPortalSession session = getDailyRhythmPortalSession();
			    session.clearBestBuyCustomer();
			    session.clearSearchCustomer();
			    session.clearCarrierCustomer();
			    Recommendation rec = new Recommendation();
			    rec.setCreatedOn(new Date());
			    rec.setCreatedBy(session.getDrpUser().getUserId());
			    rec.setTransferFlag(false);
			    CTDigitalRecSheetPage drsPage = new CTDigitalRecSheetPage(rec, mswPage);
			    setResponsePage(drsPage);
			}
		    }
		});

		clearModal.show(target);

	    }

	    @Override
	    protected void onError(AjaxRequestTarget target, Form<?> form) {
		// TODO Implement me!
	    }
	};

	clearButton.setDefaultFormProcessing(false);
	form.add(clearButton);
	add(clearModal);
	// === END of Clear Components ===

	/*
	 * Search Rec Sheet button Navigates back to rec sheet search page
	 */

	// close button exit point with popup.
	final ModalWindow exitWithoutSavingModal = new ModalWindow("exitWithoutSavingModal");
	String message = "Are you sure you want to exit without saving your changes?";

	final YesNoPanel exitPanel = new YesNoPanel(exitWithoutSavingModal.getContentId(), message,
		exitWithoutSavingModal);
	exitWithoutSavingModal.setContent(exitPanel);

	exitWithoutSavingModal.setWindowClosedCallback(new ModalWindow.WindowClosedCallback() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onClose(AjaxRequestTarget target) {
		if (exitPanel.getDialogResult()){
		    setResponsePage(mswPage);
		}
	    }
	});
	add(exitWithoutSavingModal);

	/*
	 * ===== Search Rec Sheet Button ===== Search Rec Sheet button Navigates
	 * back to rec sheet search page
	 */
	final AjaxButton searchButton = new AjaxButton("searchBtn") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onSubmit(AjaxRequestTarget target, Form<?> form) {

		logger.debug("closing recSheet by return to search button request.");
		PageParameters parameters = mswPage.getPageParameters();
		setResponsePage(new MobileSalesWorkspacePage(parameters));

	    }

	    @Override
	    protected void onError(AjaxRequestTarget target, Form<?> form) {
		// TODO Implement me!
	    }
	};

	// this is a pure navigation button so we do not want form validation to
	// occur
	searchButton.setDefaultFormProcessing(false);
	closeForm.add(searchButton);

	/*
	 * ===== Close Rec Sheet Button ===== Close Rec Sheet button Navigates
	 * back to rec sheet search page
	 */
	final AjaxButton closeButton = new AjaxButton("closeBtn") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onSubmit(AjaxRequestTarget target, Form<?> form) {
		logger.debug("closing recSheet by close button request.");
		if (drpSession.getCustomer() == null){
		    setResponsePage(CustomerDashboardSearchPage.class);
		}else{
		    setResponsePage(CustomerDashboardPage.class);
		}
	    }

	    @Override
	    protected void onError(AjaxRequestTarget target, Form<?> form) {
		// TODO Implement me!
	    }
	};

	closeButton.setDefaultFormProcessing(false);
	closeForm.add(closeButton);
	form.add(closeForm);

	closeButton.setOutputMarkupPlaceholderTag(true);

	// if recommendation comes in blank, the model creates a new one...
	// Therefore we shouldn't mess with it until we retrieve it from the
	// model.
	Recommendation r = model.getRecommendation();
	String phoneNum = user.getLocationPhoneNum() == null?"":user.getLocationPhoneNum();
	if (r.getId() == 0){ // new rec sheet
	    r.setSpecialistContactInfo(user.getFirstName() + " " + phoneNum);
	    r.setEmpCrtFrstNm(user.getFirstName());
	    r.setEmpCrtLastNm(user.getLastName());
	}else{
	    r.setEmpAltFrstNm(user.getFirstName());
	    r.setEmpAltLastNm(user.getLastName());
	}
	Essentials e = r.getEssentials();

	// first name
	TextField fldFirstname = new RequiredTextField("firstName", new PropertyModel<String>(r, "firstName"));
	fldFirstname.add(new StringValidator.LengthBetweenValidator(fieldLengths
		.get(LengthProperty.FLD_FIRSTNAME_MINLENGTH), fieldLengths.get(LengthProperty.FLD_FIRSTNAME_LENGTH)));
	form.add(fldFirstname);

	// last name
	RequiredTextField fldLastname = new RequiredTextField("lastName", new PropertyModel<String>(r, "lastName"));
	fldLastname.add(new StringValidator.LengthBetweenValidator(fieldLengths
		.get(LengthProperty.FLD_LASTNAME_MINLENGTH), fieldLengths.get(LengthProperty.FLD_LASTNAME_LENGTH)));
	form.add(fldLastname);

	// mobile number
	final TextField mobile = new RequiredTextField("mobileNum", new PropertyModel<PhoneNumber>(this, "phoneNumber")) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public <C> IConverter<C> getConverter(Class<C> type) {
		return new PhoneNumberConverter();
	    }
	};
	mobile.add(new AjaxFormComponentUpdatingBehavior("onBlur") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void onUpdate(AjaxRequestTarget target) {
		target.add(mobile);
	    }
	});
	mobile.setMarkupId("mobileNum");
	form.add(mobile);

	form
		.add(new TextField("contacttime", new PropertyModel<String>(r, "bestTimeToContact"))
			.add(new StringValidator.MaximumLengthValidator(fieldLengths
				.get(LengthProperty.FLD_CONTACTTIME_LENGTH))));

	TextField tradeInValue = new TextField("tradeInValue", new PropertyModel<String>(r, "tradeInValue")) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public <C> IConverter<C> getConverter(Class<C> type) {
		return new MoneyConverter();
	    }
	};

	tradeInValue.add(new MinimumValidator<BigDecimal>(new BigDecimal(0)));
	form.add(tradeInValue);

	form.add(new CheckBox("upgradetext", new PropertyModel<Boolean>(r, "upgradeReminderText")));
	form.add(new CheckBox("upgradecall", new PropertyModel<Boolean>(r, "upgradeReminderCall")));

	// upgrade date
	DateTextField udate = new DateTextField("upgradedate", new PropertyModel<Date>(r, "upgradeEligibilityDate"),
		"EEEE MM/dd/yy");
	DatePicker dp = new DatePicker();
	udate.add(dp);
	form.add(udate);

	// customer - plan features device. On left side of form, not to be
	// confused with recommended plan/features/device.
	form.add(new TextArea("planFeaturesDevice", new PropertyModel<String>(r, "subscriptionInfo"))
		.add(new StringValidator.MaximumLengthValidator(fieldLengths
			.get(LengthProperty.FLD_PLANFEATURESDEVICE_LENGTH))));
	form
		.add(new TextArea("internetuse", new PropertyModel<String>(r, "netUseInfo"))
			.add(new StringValidator.MaximumLengthValidator(fieldLengths
				.get(LengthProperty.FLD_INTERNETUSE_LENGTH))));

	form.add(new TextArea("rec_connectivity", new PropertyModel<String>(r, "recommendedSubscription"))
		.add(new StringValidator.MaximumLengthValidator(fieldLengths
			.get(LengthProperty.FLD_REC_CONNECTIVITY_LENGTH))));
	form.add(new TextArea("rec_phonedevice", new PropertyModel<String>(r, "recommendedDevice"))
		.add(new StringValidator.MaximumLengthValidator(fieldLengths
			.get(LengthProperty.FLD_REC_PHONEDEVICE_LENGTH))));
	form
		.add(new TextField("e_bluetooth", new PropertyModel<String>(e, "handsfree"))
			.add(new StringValidator.MaximumLengthValidator(fieldLengths
				.get(LengthProperty.FLD_ESSENTIAL_LENGTH))));
	form
		.add(new TextField("e_memory", new PropertyModel<String>(e, "memory"))
			.add(new StringValidator.MaximumLengthValidator(fieldLengths
				.get(LengthProperty.FLD_ESSENTIAL_LENGTH))));
	form
		.add(new TextField("e_accessories", new PropertyModel<String>(e, "accessories"))
			.add(new StringValidator.MaximumLengthValidator(fieldLengths
				.get(LengthProperty.FLD_ESSENTIAL_LENGTH))));
	form
		.add(new TextField("e_shields", new PropertyModel<String>(e, "shields"))
			.add(new StringValidator.MaximumLengthValidator(fieldLengths
				.get(LengthProperty.FLD_ESSENTIAL_LENGTH))));
	form
		.add(new TextField("e_chargers", new PropertyModel<String>(e, "chargers"))
			.add(new StringValidator.MaximumLengthValidator(fieldLengths
				.get(LengthProperty.FLD_ESSENTIAL_LENGTH))));
	form
		.add(new TextField("e_gsbtp", new PropertyModel<String>(e, "gsbtp"))
			.add(new StringValidator.MaximumLengthValidator(fieldLengths
				.get(LengthProperty.FLD_ESSENTIAL_LENGTH))));
	form
		.add(new TextField("e_buyback", new PropertyModel<String>(e, "buyback"))
			.add(new StringValidator.MaximumLengthValidator(fieldLengths
				.get(LengthProperty.FLD_ESSENTIAL_LENGTH))));
	form
		.add(new TextField("e_financing", new PropertyModel<String>(e, "financing"))
			.add(new StringValidator.MaximumLengthValidator(fieldLengths
				.get(LengthProperty.FLD_ESSENTIAL_LENGTH))));

	form.add(new TextArea("notes", new PropertyModel<String>(r, "notes"))
		.add(new StringValidator.MaximumLengthValidator(fieldLengths.get(LengthProperty.FLD_NOTES_LENGTH))));
	form
		.add(new TextArea("contactinfo", new PropertyModel<String>(r, "specialistContactInfo"))
			.add(new StringValidator.MaximumLengthValidator(fieldLengths
				.get(LengthProperty.FLD_CONTACTINFO_LENGTH))));

	// Mobile Device
	form.add(new CheckBox("md_internet", new CheckboxModel(r, "deviceCapabilities",
		Recommendation.DeviceCapabilities.INTERNET.name())));
	form.add(new CheckBox("md_email", new CheckboxModel(r, "deviceCapabilities",
		Recommendation.DeviceCapabilities.EMAIL.name())));
	form.add(new CheckBox("md_music", new CheckboxModel(r, "deviceCapabilities",
		Recommendation.DeviceCapabilities.MUSIC.name())));
	form.add(new CheckBox("md_video", new CheckboxModel(r, "deviceCapabilities",
		Recommendation.DeviceCapabilities.VIDEO.name())));
	form.add(new CheckBox("md_photo", new CheckboxModel(r, "deviceCapabilities",
		Recommendation.DeviceCapabilities.PHOTO.name())));
	form.add(new CheckBox("md_tv", new CheckboxModel(r, "deviceCapabilities", Recommendation.DeviceCapabilities.TV
		.name())));
	form.add(new CheckBox("md_games", new CheckboxModel(r, "deviceCapabilities",
		Recommendation.DeviceCapabilities.GAMING.name())));
	form.add(new CheckBox("md_texting", new CheckboxModel(r, "deviceCapabilities",
		Recommendation.DeviceCapabilities.TEXTING.name())));
	form.add(new CheckBox("md_unlocked", new CheckboxModel(r, "deviceCapabilities",
		Recommendation.DeviceCapabilities.UNLOCKED.name())));
	form.add(new CheckBox("md_nav", new CheckboxModel(r, "deviceCapabilities",
		Recommendation.DeviceCapabilities.NAVIGATION.name())));
	// wow 
	form.add(new CheckBox("wow_data", new CheckboxModel(r, "wowRequirements",
		Recommendation.WowRequirements.DATATRANSFER.name())));
	form.add(new CheckBox("wow_email", new CheckboxModel(r, "wowRequirements",
		Recommendation.WowRequirements.PERSONALEMAIL.name())));
	form.add(new CheckBox("wow_bluetooth", new CheckboxModel(r, "wowRequirements",
		Recommendation.WowRequirements.BLUETOOTHPAIRING.name())));
	form.add(new CheckBox("wow_apps", new CheckboxModel(r, "wowRequirements",
		Recommendation.WowRequirements.APPLICATIONS.name())));
	form.add(new CheckBox("wow_sw", new CheckboxModel(r, "wowRequirements", Recommendation.WowRequirements.SOFTWARE
		.name())));
	form.add(new CheckBox("wow_social", new CheckboxModel(r, "wowRequirements",
		Recommendation.WowRequirements.SOCIALNETWORKING.name())));
	form.add(new CheckBox("wow_power", new CheckboxModel(r, "wowRequirements",
		Recommendation.WowRequirements.POWERMANAGEMENT.name())));
	form.add(new CheckBox("wow_voicemail", new CheckboxModel(r, "wowRequirements",
		Recommendation.WowRequirements.VOICEMAIL.name())));
	form.add(new CheckBox("wow_other", new CheckboxModel(r, "wowRequirements", Recommendation.WowRequirements.OTHER
		.name())));

	HiddenField formChanged = new HiddenField<Boolean>("formChanged", new PropertyModel<Boolean>(this,
		"formChanged"));
	closeForm.add(formChanged);

	add(form);
	form.add(closeForm);

	SessionTimeoutPanel sessionTimeoutPanel = new SessionTimeoutPanel("sessionTimeoutPanel");
	add(sessionTimeoutPanel);
	sessionTimeoutPanel.setOutputMarkupPlaceholderTag(true);
    }

    public enum LengthProperty {

	FLD_FIRSTNAME_LENGTH, FLD_FIRSTNAME_MINLENGTH, FLD_LASTNAME_LENGTH, FLD_LASTNAME_MINLENGTH,
	FLD_CONTACTTIME_LENGTH, FLD_UPGRADEDATE_LENGTH, FLD_PLANFEATURESDEVICE_LENGTH, FLD_INTERNETUSE_LENGTH,
	FLD_REC_CONNECTIVITY_LENGTH, FLD_REC_PHONEDEVICE_LENGTH, FLD_ESSENTIAL_LENGTH, FLD_NOTES_LENGTH,
	FLD_CONTACTINFO_LENGTH
    }

    /**
     * For each value of the lengthProperty enum, snags the corresponding value
     * from the properties file. And dumps them into a map with the enum as key.
     * Reduces some code in other areas. Problem is mainly strings need to be
     * grabbed from resource & parsed into ints.
     * 
     * @return A hashmap with LengthProperty => Corresponding property value.
     */
    private Map<LengthProperty, Integer> getLengthProperties() {
	LengthProperty[] props = LengthProperty.values();
	Map<LengthProperty, Integer> map = new HashMap<LengthProperty, Integer>();

	for(LengthProperty lp: props){
	    String name = lp.name();
	    String propertyValue = getString(name); // getLocalizer().getString(name,
	    // this);
	    Integer propVal = Integer.valueOf(propertyValue);
	    map.put(lp, propVal);
	}

	return map;
    }

    private void saveRecSheet() {
	formChanged = false;
	Recommendation rec = model.getRecommendation();
	User user = model.getUser();
	try{
	    mswService.saveRecommendation(rec, user);

	    Customer customer = getDailyRhythmPortalSession().getSearchCustomer();

	    customerService.addRecSheetSummary(customer, rec, getDailyRhythmPortalSession().getDrpUser());

	    getDailyRhythmPortalSession().getCustomer().setFirstName(
		    getDailyRhythmPortalSession().getSearchCustomer().getFirstName());
	    getDailyRhythmPortalSession().getCustomer().setLastName(
		    getDailyRhythmPortalSession().getSearchCustomer().getLastName());

	    final boolean transferToBeast = rec.isTransferFlag();
	    form.addOrReplace(new Image("transfer-to-beast", new ContextRelativeResource(
		    "/css/img/transfer-to-beast.png")) {

		private static final long serialVersionUID = 1L;

		@Override
		public boolean isVisible() {
		    return transferToBeast;
		}
	    });

	}catch(ServiceException e){
	    error(getLocalizer().getString("recsheetSaveFail", this, "Failed to save Digital RecSheet"));
	}
    }
}
