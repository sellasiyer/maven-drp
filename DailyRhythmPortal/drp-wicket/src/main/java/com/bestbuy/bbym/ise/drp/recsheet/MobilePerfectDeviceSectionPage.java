package com.bestbuy.bbym.ise.drp.recsheet;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.validation.validator.StringValidator.MaximumLengthValidator;

import com.bestbuy.bbym.ise.drp.recsheet.MobileWOWSectionPage.EssentialTypeId;

public class MobilePerfectDeviceSectionPage extends AbstractRecSheetPage {

    private boolean internetSelected;
    private boolean musicSelected;
    private boolean emailSelected;
    private boolean videoSelected;
    private boolean photosSelected;
    private boolean televisionSelected;
    private boolean navigationSelected;
    private boolean gamingSelected;
    private boolean textingSelected;
    private boolean unlockedSelected;
    private String other;
    private Integer otherCC = 105; //CC is for character counter

    public MobilePerfectDeviceSectionPage(PageParameters parameters) {
	super(parameters);

	// checking for existing data
	if (recommendation != null){
	    if (recommendation.getPreference(EssentialTypeEnum.INTERNET.getId()) != null
		    && recommendation.getPreference(EssentialTypeEnum.INTERNET.getId()).equals("true")){
		setInternetSelected(true);
	    }
	    if (recommendation.getPreference(EssentialTypeEnum.MUSIC.getId()) != null
		    && recommendation.getPreference(EssentialTypeEnum.MUSIC.getId()).equals("true")){
		setMusicSelected(true);
	    }
	    if (recommendation.getPreference(EssentialTypeEnum.EMAIL.getId()) != null
		    && recommendation.getPreference(EssentialTypeEnum.EMAIL.getId()).equals("true")){
		setEmailSelected(true);
	    }
	    if (recommendation.getPreference(EssentialTypeEnum.VIDEO.getId()) != null
		    && recommendation.getPreference(EssentialTypeEnum.VIDEO.getId()).equals("true")){
		setVideoSelected(true);
	    }
	    if (recommendation.getPreference(EssentialTypeEnum.PHOTOS.getId()) != null
		    && recommendation.getPreference(EssentialTypeEnum.PHOTOS.getId()).equals("true")){
		setPhotosSelected(true);
	    }
	    if (recommendation.getPreference(EssentialTypeEnum.TV.getId()) != null
		    && recommendation.getPreference(EssentialTypeEnum.TV.getId()).equals("true")){
		setTelevisionSelected(true);
	    }
	    if (recommendation.getPreference(EssentialTypeEnum.NAVIGATION.getId()) != null
		    && recommendation.getPreference(EssentialTypeEnum.NAVIGATION.getId()).equals("true")){
		setNavigationSelected(true);
	    }
	    if (recommendation.getPreference(EssentialTypeEnum.GAMING.getId()) != null
		    && recommendation.getPreference(EssentialTypeEnum.GAMING.getId()).equals("true")){
		setGamingSelected(true);
	    }
	    if (recommendation.getPreference(EssentialTypeEnum.TEXTING.getId()) != null
		    && recommendation.getPreference(EssentialTypeEnum.TEXTING.getId()).equals("true")){
		setTextingSelected(true);
	    }
	    if (recommendation.getPreference(EssentialTypeEnum.UNLOCKED.getId()) != null
		    && recommendation.getPreference(EssentialTypeEnum.UNLOCKED.getId()).equals("true")){
		setUnlockedSelected(true);
	    }
	    if (recommendation.getPreference(EssentialTypeEnum.OTHER.getId()) != null){
		setOther(recommendation.getPreference(EssentialTypeEnum.OTHER.getId()));
	    }
	}

	// Adding section title
	Label sectionTitle = new Label("sectionTitle", getString("mobilePerfectDeviceSectionPage.title.label"));
	sectionTitle.setOutputMarkupPlaceholderTag(true);
	form.add(sectionTitle);

	CheckBox internetCbx = new CheckBox("internet", new PropertyModel<Boolean>(this, "internetSelected"));
	internetCbx.setMarkupId("internetCbx");
	internetCbx.setOutputMarkupId(true);
	form.add(internetCbx);

	Label internetLabel = new Label("internetLabel",
		getString("mobilePerfectDeviceSectionPage.internetLabel.label"));
	internetLabel.setOutputMarkupPlaceholderTag(true);
	form.add(internetLabel);

	CheckBox musicCbx = new CheckBox("music", new PropertyModel<Boolean>(this, "musicSelected"));
	musicCbx.setMarkupId("musicCbx");
	musicCbx.setOutputMarkupId(true);
	form.add(musicCbx);

	Label musicLabel = new Label("musicLabel", getString("mobilePerfectDeviceSectionPage.musicLabel.label"));
	musicLabel.setOutputMarkupPlaceholderTag(true);
	form.add(musicLabel);

	CheckBox emailCbx = new CheckBox("email", new PropertyModel<Boolean>(this, "emailSelected"));
	emailCbx.setMarkupId("emailCbx");
	emailCbx.setOutputMarkupId(true);
	form.add(emailCbx);

	Label emailLabel = new Label("emailLabel", getString("mobilePerfectDeviceSectionPage.emailLabel.label"));
	emailLabel.setOutputMarkupPlaceholderTag(true);
	form.add(emailLabel);

	CheckBox videoCbx = new CheckBox("video", new PropertyModel<Boolean>(this, "videoSelected"));
	videoCbx.setMarkupId("videoCbx");
	videoCbx.setOutputMarkupId(true);
	form.add(videoCbx);

	Label videoLabel = new Label("videoLabel", getString("mobilePerfectDeviceSectionPage.videoLabel.label"));
	videoLabel.setOutputMarkupPlaceholderTag(true);
	form.add(videoLabel);

	CheckBox photosCbx = new CheckBox("photos", new PropertyModel<Boolean>(this, "photosSelected"));
	photosCbx.setMarkupId("photosCbx");
	photosCbx.setOutputMarkupId(true);
	form.add(photosCbx);

	Label photosLabel = new Label("photosLabel", getString("mobilePerfectDeviceSectionPage.photosLabel.label"));
	photosLabel.setOutputMarkupPlaceholderTag(true);
	form.add(photosLabel);

	CheckBox televisionCbx = new CheckBox("television", new PropertyModel<Boolean>(this, "televisionSelected"));
	televisionCbx.setMarkupId("televisionCbx");
	televisionCbx.setOutputMarkupId(true);
	form.add(televisionCbx);

	Label televisionLabel = new Label("televisionLabel",
		getString("mobilePerfectDeviceSectionPage.televisionLabel.label"));
	televisionLabel.setOutputMarkupPlaceholderTag(true);
	form.add(televisionLabel);

	CheckBox navigationCbx = new CheckBox("navigation", new PropertyModel<Boolean>(this, "navigationSelected"));
	navigationCbx.setMarkupId("navigationCbx");
	navigationCbx.setOutputMarkupId(true);
	form.add(navigationCbx);

	Label navigationLabel = new Label("navigationLabel",
		getString("mobilePerfectDeviceSectionPage.navigationLabel.label"));
	navigationLabel.setOutputMarkupPlaceholderTag(true);
	form.add(navigationLabel);

	CheckBox gamingCbx = new CheckBox("gaming", new PropertyModel<Boolean>(this, "gamingSelected"));
	gamingCbx.setMarkupId("gamingCbx");
	gamingCbx.setOutputMarkupId(true);
	form.add(gamingCbx);

	Label gamingLabel = new Label("gamingLabel", getString("mobilePerfectDeviceSectionPage.gamingLabel.label"));
	gamingLabel.setOutputMarkupPlaceholderTag(true);
	form.add(gamingLabel);

	CheckBox textingCbx = new CheckBox("texting", new PropertyModel<Boolean>(this, "textingSelected"));
	textingCbx.setMarkupId("textingCbx");
	textingCbx.setOutputMarkupId(true);
	form.add(textingCbx);

	Label textingLabel = new Label("textingLabel", getString("mobilePerfectDeviceSectionPage.textingLabel.label"));
	textingLabel.setOutputMarkupPlaceholderTag(true);
	form.add(textingLabel);

	CheckBox unlockedCbx = new CheckBox("unlocked", new PropertyModel<Boolean>(this, "unlockedSelected"));
	unlockedCbx.setMarkupId("unlockedCbx");
	unlockedCbx.setOutputMarkupId(true);
	form.add(unlockedCbx);

	Label unlockedLabel = new Label("unlockedLabel",
		getString("mobilePerfectDeviceSectionPage.unlockedLabel.label"));
	unlockedLabel.setOutputMarkupPlaceholderTag(true);
	form.add(unlockedLabel);

	Label otherLabel = new Label("otherLabel", getString("mobilePerfectDeviceSectionPage.otherLabel.label"));
	otherLabel.setOutputMarkupId(true);
	form.add(otherLabel);

	TextArea<String> otherTextArea = new TextArea<String>("otherTextBox", new PropertyModel<String>(this, "other"));
	otherTextArea.setMarkupId("otherTextBox");
	otherTextArea.setOutputMarkupPlaceholderTag(true);
	otherTextArea.add(new MaximumLengthValidator(105));
	form.add(otherTextArea);

	final Label otherCCLabel = new Label("otherCCLabel", new PropertyModel<Integer>(this, "otherCC"));
	otherCCLabel.setMarkupId("otherCCLabel");
	otherCCLabel.setOutputMarkupId(true);
	form.add(otherCCLabel);

	otherTextArea.add(new AjaxFormComponentUpdatingBehavior("onkeyup") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void onUpdate(AjaxRequestTarget target) {

		if (recommendation != null && other != null){
		    otherCC = 105 - other.length();
		    target.add(otherCCLabel);
		}else{
		    otherCC = 105;
		    target.add(otherCCLabel);
		}

	    }
	});

	setLinkVisible(this.clearSectionButton, RecSheetsSections.PERFECT_MOBILE_DEVICE.dataWasEntered(recommendation));

    }

    @Override
    void clearSection(AjaxRequestTarget target) {	
	recommendation
	.removePreferences(new Long[] {EssentialTypeEnum.INTERNET.getId(), EssentialTypeEnum.MUSIC.getId(),
		EssentialTypeEnum.EMAIL.getId(), EssentialTypeEnum.VIDEO.getId(),
		EssentialTypeEnum.PHOTOS.getId(), EssentialTypeEnum.TV.getId(),
		EssentialTypeEnum.NAVIGATION.getId(), EssentialTypeEnum.GAMING.getId(),
		EssentialTypeEnum.TEXTING.getId(),EssentialTypeEnum.UNLOCKED.getId(),EssentialTypeEnum.OTHER.getId() });
	
	internetSelected = false;
	    musicSelected = false;
	    emailSelected = false;
	    videoSelected = false;
	    photosSelected = false;
	    televisionSelected = false;
	    navigationSelected = false;
	    gamingSelected = false;
	    textingSelected = false;
	    unlockedSelected = false;
	    other = "";
	
	setOther(null);
	setOtherCC(105);
	form.clearInput();
	target.add(form);

	markSections();

	setLinkVisible(this.clearSectionButton, RecSheetsSections.PERFECT_MOBILE_DEVICE.dataWasEntered(recommendation));
    }

    @Override
    void saveScreenValues(final AjaxRequestTarget target) {
	if (internetSelected){
	    recommendation.setPreference(EssentialTypeEnum.INTERNET.getId(), "true");
	}
	if (musicSelected){
	    recommendation.setPreference(EssentialTypeEnum.MUSIC.getId(), "true");
	}
	if (emailSelected){
	    recommendation.setPreference(EssentialTypeEnum.EMAIL.getId(), "true");
	}
	if (videoSelected){
	    recommendation.setPreference(EssentialTypeEnum.VIDEO.getId(), "true");
	}
	if (photosSelected){
	    recommendation.setPreference(EssentialTypeEnum.PHOTOS.getId(), "true");
	}
	if (televisionSelected){
	    recommendation.setPreference(EssentialTypeEnum.TV.getId(), "true");
	}
	if (navigationSelected){
	    recommendation.setPreference(EssentialTypeEnum.NAVIGATION.getId(), "true");
	}
	if (gamingSelected){
	    recommendation.setPreference(EssentialTypeEnum.GAMING.getId(), "true");
	}
	if (textingSelected){
	    recommendation.setPreference(EssentialTypeEnum.TEXTING.getId(), "true");
	}
	if (unlockedSelected){
	    recommendation.setPreference(EssentialTypeEnum.UNLOCKED.getId(), "true");
	}
	if (other != null){
	    recommendation.setPreference(EssentialTypeEnum.OTHER.getId(), other);
	}
	setLinkVisible(this.clearSectionButton, RecSheetsSections.PERFECT_MOBILE_DEVICE.dataWasEntered(recommendation));
    }

    public void setInternetSelected(boolean internetSelected) {
	this.internetSelected = internetSelected;
    }

    public boolean isInternetSelected() {
	return internetSelected;
    }

    public void setMusicSelected(boolean musicSelected) {
	this.musicSelected = musicSelected;
    }

    public boolean isMusicSelected() {
	return musicSelected;
    }

    public void setEmailSelected(boolean emailSelected) {
	this.emailSelected = emailSelected;
    }

    public boolean isEmailSelected() {
	return emailSelected;
    }

    public void setVideoSelected(boolean videoSelected) {
	this.videoSelected = videoSelected;
    }

    public boolean isVideoSelected() {
	return videoSelected;
    }

    public void setPhotosSelected(boolean photosSelected) {
	this.photosSelected = photosSelected;
    }

    public boolean isPhotosSelected() {
	return photosSelected;
    }

    public void setTelevisionSelected(boolean televisionSelected) {
	this.televisionSelected = televisionSelected;
    }

    public boolean isTelevisionSelected() {
	return televisionSelected;
    }

    public void setNavigationSelected(boolean navigationSelected) {
	this.navigationSelected = navigationSelected;
    }

    public boolean isNavigationSelected() {
	return navigationSelected;
    }

    public void setGamingSelected(boolean gamingSelected) {
	this.gamingSelected = gamingSelected;
    }

    public boolean isGamingSelected() {
	return gamingSelected;
    }

    public void setTextingSelected(boolean textingSelected) {
	this.textingSelected = textingSelected;
    }

    public boolean isTextingSelected() {
	return textingSelected;
    }

    public void setUnlockedSelected(boolean unlockedSelected) {
	this.unlockedSelected = unlockedSelected;
    }

    public boolean isUnlockedSelected() {
	return unlockedSelected;
    }

    public void setOther(String other) {
	this.other = other;
    }

    public String getOther() {
	return other;
    }

    public void setOtherCC(Integer otherCC) {
	this.otherCC = otherCC;
    }

    public Integer getOtherCC() {
	return otherCC;
    }
}
