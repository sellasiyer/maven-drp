package com.bestbuy.bbym.ise.drp.recsheet;

import java.util.Map;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MobileWOWSectionPage extends AbstractRecSheetPage {

    private static Logger logger = LoggerFactory.getLogger(MobileWOWSectionPage.class);

    private static final long serialVersionUID = 1L;

    private String CBX_TRUE = "true";

    enum EssentialTypeId {
	DATATRANSFER(20), SOCIAL(21), EMAIL(22), POWER(23), BLUETOOTH(24), VOICEMAIL(25), APPLICATIONS(26), OTHER(27),
	SOFTWARE(28);

	private int id;

	private EssentialTypeId(int id) {
	    this.id = id;
	}

	public Long getId() {
	    return new Long(id);
	}

    }

    private Boolean dataTransferModel, socialModel, emailModel, powerModel, bluetoothModel, voicemailModel,
	    applicationsModel, otherModel, softwareModel;

    private CheckBox datatransferCbx, socialCbx, emailCbx, powerCbx, bluetoothCbx, voicemailCbx, applicationsCbx,
	    otherCbx, softwareCbx;

    public MobileWOWSectionPage(PageParameters parameters) {
	super(parameters);

	Label title = new Label("sectionTitle", getString("mobileWOWSectionForm.title.label"));
	title.setOutputMarkupPlaceholderTag(true);
	form.add(title);

	loadSavedPreferences();

	datatransferCbx = new CheckBox("datatransfer", new PropertyModel<Boolean>(this, "dataTransferModel"));
	datatransferCbx.setMarkupId("datatransferCbx");
	datatransferCbx.setOutputMarkupPlaceholderTag(true);
	form.add(datatransferCbx);

	socialCbx = new CheckBox("social", new PropertyModel<Boolean>(this, "socialModel"));
	socialCbx.setMarkupId("socialCbx");
	socialCbx.setOutputMarkupPlaceholderTag(true);
	form.add(socialCbx);

	emailCbx = new CheckBox("email", new PropertyModel<Boolean>(this, "emailModel"));
	emailCbx.setMarkupId("emailCbx");
	emailCbx.setOutputMarkupPlaceholderTag(true);
	form.add(emailCbx);

	powerCbx = new CheckBox("power", new PropertyModel<Boolean>(this, "powerModel"));
	powerCbx.setMarkupId("powerCbx");
	powerCbx.setOutputMarkupPlaceholderTag(true);
	form.add(powerCbx);

	bluetoothCbx = new CheckBox("bluetooth", new PropertyModel<Boolean>(this, "bluetoothModel"));
	bluetoothCbx.setMarkupId("bluetoothCbx");
	bluetoothCbx.setOutputMarkupPlaceholderTag(true);
	form.add(bluetoothCbx);

	voicemailCbx = new CheckBox("voicemail", new PropertyModel<Boolean>(this, "voicemailModel"));
	voicemailCbx.setMarkupId("voicemailCbx");
	voicemailCbx.setOutputMarkupPlaceholderTag(true);
	form.add(voicemailCbx);

	applicationsCbx = new CheckBox("applications", new PropertyModel<Boolean>(this, "applicationsModel"));
	applicationsCbx.setMarkupId("applicationsCbx");
	applicationsCbx.setOutputMarkupPlaceholderTag(true);
	form.add(applicationsCbx);

	otherCbx = new CheckBox("other", new PropertyModel<Boolean>(this, "otherModel"));
	otherCbx.setMarkupId("otherCbx");
	otherCbx.setOutputMarkupPlaceholderTag(true);
	form.add(otherCbx);

	softwareCbx = new CheckBox("software", new PropertyModel<Boolean>(this, "softwareModel"));
	softwareCbx.setMarkupId("softwareCbx");
	softwareCbx.setOutputMarkupPlaceholderTag(true);
	form.add(softwareCbx);

	setLinkVisible(this.clearSectionButton, RecSheetsSections.WALK_OUT_WORKING.dataWasEntered(recommendation));
    }

    private void loadSavedPreferences() {
	logger.debug("recommendation.getPreferences() size at loading beginning:"
		+ recommendation.getPreferences().size());
	logger.debug("in loading screen values for MOB WOW Page.." + recommendation);

	Map<Long, String> preferences = recommendation.getPreferences();
	if (preferences.get(EssentialTypeId.DATATRANSFER.getId()) != null){
	    dataTransferModel = Boolean.TRUE;
	}
	if (preferences.get(EssentialTypeId.SOCIAL.getId()) != null){
	    socialModel = Boolean.TRUE;
	}
	if (preferences.get(EssentialTypeId.EMAIL.getId()) != null){
	    emailModel = Boolean.TRUE;
	}
	if (preferences.get(EssentialTypeId.POWER.getId()) != null){
	    powerModel = Boolean.TRUE;
	}
	if (preferences.get(EssentialTypeId.BLUETOOTH.getId()) != null){
	    bluetoothModel = Boolean.TRUE;
	}
	if (preferences.get(EssentialTypeId.VOICEMAIL.getId()) != null){
	    voicemailModel = Boolean.TRUE;
	}
	if (preferences.get(EssentialTypeId.APPLICATIONS.getId()) != null){
	    applicationsModel = Boolean.TRUE;
	}
	if (preferences.get(EssentialTypeId.OTHER.getId()) != null){
	    otherModel = Boolean.TRUE;
	}
	if (preferences.get(EssentialTypeId.SOFTWARE.getId()) != null){
	    softwareModel = Boolean.TRUE;
	}
    }

    @Override
    void saveScreenValues(final AjaxRequestTarget target) {
	logger
		.debug("recommendation.getPreferences() size at save beginning:"
			+ recommendation.getPreferences().size());
	if (dataTransferModel != null && dataTransferModel.booleanValue()){
	    recommendation.getPreferences().put(EssentialTypeId.DATATRANSFER.getId(), CBX_TRUE);
	    recommendation.setWowRequirements("DATATRANSFER", Boolean.TRUE);
	}else{
	    recommendation.getPreferences().remove(EssentialTypeId.DATATRANSFER.getId());
	    recommendation.setWowRequirements("DATATRANSFER", Boolean.FALSE);
	}
	if (socialModel != null && socialModel.booleanValue()){
	    recommendation.getPreferences().put(EssentialTypeId.SOCIAL.getId(), CBX_TRUE);
	    recommendation.setWowRequirements("SOCIALNETWORKING", Boolean.TRUE);
	}else{
	    recommendation.getPreferences().remove(EssentialTypeId.SOCIAL.getId());
	    recommendation.setWowRequirements("SOCIALNETWORKING", Boolean.FALSE);
	}
	if (emailModel != null && emailModel.booleanValue()){
	    recommendation.getPreferences().put(EssentialTypeId.EMAIL.getId(), CBX_TRUE);
	    recommendation.setWowRequirements("PERSONALEMAIL", Boolean.TRUE);
	}else{
	    recommendation.getPreferences().remove(EssentialTypeId.EMAIL.getId());
	    recommendation.setWowRequirements("PERSONALEMAIL", Boolean.FALSE);
	}
	if (powerModel != null && powerModel.booleanValue()){
	    recommendation.getPreferences().put(EssentialTypeId.POWER.getId(), CBX_TRUE);
	    recommendation.setWowRequirements("POWERMANAGEMENT", Boolean.TRUE);
	}else{
	    recommendation.getPreferences().remove(EssentialTypeId.POWER.getId());
	    recommendation.setWowRequirements("POWERMANAGEMENT", Boolean.FALSE);
	}
	if (bluetoothModel != null && bluetoothModel.booleanValue()){
	    recommendation.getPreferences().put(EssentialTypeId.BLUETOOTH.getId(), CBX_TRUE);
	    recommendation.setWowRequirements("BLUETOOTHPAIRING", Boolean.TRUE);
	}else{
	    recommendation.getPreferences().remove(EssentialTypeId.BLUETOOTH.getId());
	    recommendation.setWowRequirements("BLUETOOTHPAIRING", Boolean.FALSE);
	}
	if (voicemailModel != null && voicemailModel.booleanValue()){
	    recommendation.getPreferences().put(EssentialTypeId.VOICEMAIL.getId(), CBX_TRUE);
	    recommendation.setWowRequirements("VOICEMAIL", Boolean.TRUE);
	}else{
	    recommendation.getPreferences().remove(EssentialTypeId.VOICEMAIL.getId());
	    recommendation.setWowRequirements("VOICEMAIL", Boolean.FALSE);
	}
	if (applicationsModel != null && applicationsModel.booleanValue()){
	    recommendation.getPreferences().put(EssentialTypeId.APPLICATIONS.getId(), CBX_TRUE);
	    recommendation.setWowRequirements("APPLICATIONS", Boolean.TRUE);
	}else{
	    recommendation.getPreferences().remove(EssentialTypeId.APPLICATIONS.getId());
	    recommendation.setWowRequirements("APPLICATIONS", Boolean.FALSE);
	}
	if (otherModel != null && otherModel.booleanValue()){
	    recommendation.getPreferences().put(EssentialTypeId.OTHER.getId(), CBX_TRUE);
	    recommendation.setWowRequirements("OTHER", Boolean.TRUE);
	}else{
	    recommendation.getPreferences().remove(EssentialTypeId.OTHER.getId());
	    recommendation.setWowRequirements("OTHER", Boolean.FALSE);
	}
	if (softwareModel != null && softwareModel.booleanValue()){
	    recommendation.getPreferences().put(EssentialTypeId.SOFTWARE.getId(), CBX_TRUE);
	    recommendation.setWowRequirements("SOFTWARE", Boolean.TRUE);
	}else{
	    recommendation.getPreferences().remove(EssentialTypeId.SOFTWARE.getId());
	    recommendation.setWowRequirements("SOFTWARE", Boolean.FALSE);
	}
	setLinkVisible(this.clearSectionButton, RecSheetsSections.WALK_OUT_WORKING.dataWasEntered(recommendation));
	logger.debug("in saved screen values for MOB WOW Page.." + recommendation);
    }

    @Override
    void clearSection(AjaxRequestTarget target) {
	dataTransferModel = Boolean.FALSE;
	socialModel = Boolean.FALSE;
	emailModel = Boolean.FALSE;
	powerModel = Boolean.FALSE;
	bluetoothModel = Boolean.FALSE;
	voicemailModel = Boolean.FALSE;
	applicationsModel = Boolean.FALSE;
	otherModel = Boolean.FALSE;
	softwareModel = Boolean.FALSE;
	recommendation
		.removePreferences(new Long[] {EssentialTypeId.DATATRANSFER.getId(), EssentialTypeId.SOCIAL.getId(),
			EssentialTypeId.EMAIL.getId(), EssentialTypeId.POWER.getId(),
			EssentialTypeId.BLUETOOTH.getId(), EssentialTypeId.VOICEMAIL.getId(),
			EssentialTypeId.APPLICATIONS.getId(), EssentialTypeId.OTHER.getId(),
			EssentialTypeId.SOFTWARE.getId() });

	target.add(form);

	markSections();

	setLinkVisible(this.clearSectionButton, RecSheetsSections.WALK_OUT_WORKING.dataWasEntered(recommendation));
    }

}
