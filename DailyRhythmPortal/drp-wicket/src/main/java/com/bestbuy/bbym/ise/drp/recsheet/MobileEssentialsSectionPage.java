package com.bestbuy.bbym.ise.drp.recsheet;

import org.apache.commons.lang.StringUtils;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class MobileEssentialsSectionPage extends AbstractRecSheetPage {

    private String handsFree;
    private String memory;
    private String caseOrShield;
    private String appcessories;
    private String charger;
    private String gsProtection;
    private String tradeInPlus;
    private String financing;

    //Character counters noted by CC at the end of the variable name
    private Integer handsFreeCC = 50;
    private Integer memoryCC = 50;
    private Integer caseOrShieldCC = 50;
    private Integer appcessoriesCC = 50;
    private Integer chargerCC = 50;
    private Integer gsProtectionCC = 50;
    private Integer tradeInPlusCC = 50;
    private Integer financingCC = 50;

    public String getHandsFree() {
	return handsFree;
    }

    public void setHandsFree(String handsFree) {
	this.handsFree = handsFree;
    }

    public void setMemory(String memory) {
	this.memory = memory;
    }

    public String getMemory() {
	return memory;
    }

    public void setCaseOrShield(String caseOrShield) {
	this.caseOrShield = caseOrShield;
    }

    public String getCaseOrShield() {
	return caseOrShield;
    }

    public void setAppcessories(String appcessories) {
	this.appcessories = appcessories;
    }

    public String getAppcessories() {
	return appcessories;
    }

    public void setCharger(String charger) {
	this.charger = charger;
    }

    public String getCharger() {
	return charger;
    }

    public void setGSProtection(String gsProtection) {
	this.gsProtection = gsProtection;
    }

    public String getGSProtection() {
	return gsProtection;
    }

    public void setTradeInPlus(String tradeInPlus) {
	this.tradeInPlus = tradeInPlus;
    }

    public String getTradeInPlus() {
	return tradeInPlus;
    }

    public void setFinancing(String financing) {
	this.financing = financing;
    }

    public String getFinancing() {
	return financing;
    }

    public MobileEssentialsSectionPage(PageParameters parameters) {
	super(parameters);

	if (recommendation != null
		&& StringUtils.isNotBlank(recommendation.getPreference(EssentialTypeEnum.HANDSFREE.getId()))){
	    setHandsFree(recommendation.getPreference(EssentialTypeEnum.HANDSFREE.getId()));
	    handsFreeCC = handsFreeCC - handsFree.length();
	}
	if (recommendation != null
		&& StringUtils.isNotBlank(recommendation.getPreference(EssentialTypeEnum.MEMORY.getId()))){
	    setMemory(recommendation.getPreference(EssentialTypeEnum.MEMORY.getId()));
	    memoryCC = memoryCC - memory.length();
	}
	if (recommendation != null
		&& StringUtils.isNotBlank(recommendation.getPreference(EssentialTypeEnum.CASE_SHIELD.getId()))){
	    setCaseOrShield(recommendation.getPreference(EssentialTypeEnum.CASE_SHIELD.getId()));
	    caseOrShieldCC = caseOrShieldCC - caseOrShield.length();
	}
	if (recommendation != null
		&& StringUtils.isNotBlank(recommendation.getPreference(EssentialTypeEnum.APPCESSORIES.getId()))){
	    setAppcessories(recommendation.getPreference(EssentialTypeEnum.APPCESSORIES.getId()));
	    appcessoriesCC = appcessoriesCC - appcessories.length();
	}
	if (recommendation != null
		&& StringUtils.isNotBlank(recommendation.getPreference(EssentialTypeEnum.CHARGER.getId()))){
	    setCharger(recommendation.getPreference(EssentialTypeEnum.CHARGER.getId()));
	    chargerCC = chargerCC - charger.length();
	}
	if (recommendation != null
		&& StringUtils
			.isNotBlank(recommendation.getPreference(EssentialTypeEnum.GEEK_SQUAD_PROTECTION.getId()))){
	    setGSProtection(recommendation.getPreference(EssentialTypeEnum.GEEK_SQUAD_PROTECTION.getId()));
	    gsProtectionCC = gsProtectionCC - gsProtection.length();
	}
	if (recommendation != null
		&& StringUtils.isNotBlank(recommendation.getPreference(EssentialTypeEnum.TRADE_IN_PLUS.getId()))){
	    setTradeInPlus(recommendation.getPreference(EssentialTypeEnum.TRADE_IN_PLUS.getId()));
	    tradeInPlusCC = tradeInPlusCC - tradeInPlus.length();
	}
	if (recommendation != null
		&& StringUtils.isNotBlank(recommendation.getPreference(EssentialTypeEnum.FINANCING.getId()))){
	    setFinancing(recommendation.getPreference(EssentialTypeEnum.FINANCING.getId()));
	    financingCC = financingCC - financing.length();
	}

	// Adding section title
	Label sectionTitle = new Label("sectionTitle", getString("moblieEssentialsSectionPage.title.label"));
	sectionTitle.setOutputMarkupPlaceholderTag(true);
	form.add(sectionTitle);

	TextField<String> handsFreeTextField = new TextField<String>("handsFreeTextField", new PropertyModel<String>(
		this, "handsFree"));
	handsFreeTextField.setMarkupId("handsFreeTextField");
	handsFreeTextField.setOutputMarkupPlaceholderTag(true);
	form.add(handsFreeTextField);

	final Label handsFreeCCLabel = new Label("handsFreeCCLabel", new PropertyModel<Integer>(this, "handsFreeCC"));
	handsFreeCCLabel.setMarkupId("handsFreeCCLabel");
	handsFreeCCLabel.setOutputMarkupId(true);
	form.add(handsFreeCCLabel);

	handsFreeTextField.add(new AjaxFormComponentUpdatingBehavior("onkeyup") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void onUpdate(AjaxRequestTarget target) {
		//logger.debug("subscription info..." + recommendation.getSubscriptionInfo());
		if (recommendation != null && handsFree != null){
		    handsFreeCC = 50 - handsFree.length();
		    target.add(handsFreeCCLabel);
		}else{
		    handsFreeCC = 50;
		    target.add(handsFreeCCLabel);
		}

	    }
	});

	TextField<String> memoryTextField = new TextField<String>("memoryTextField", new PropertyModel<String>(this,
		"memory"));
	memoryTextField.setMarkupId("memoryTextField");
	memoryTextField.setOutputMarkupPlaceholderTag(true);
	form.add(memoryTextField);

	final Label memoryCCLabel = new Label("memoryCCLabel", new PropertyModel<Integer>(this, "memoryCC"));
	memoryCCLabel.setMarkupId("memoryCCLabel");
	memoryCCLabel.setOutputMarkupId(true);
	form.add(memoryCCLabel);

	memoryTextField.add(new AjaxFormComponentUpdatingBehavior("onkeyup") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void onUpdate(AjaxRequestTarget target) {
		//logger.debug("subscription info..." + recommendation.getSubscriptionInfo());
		if (recommendation != null && memory != null){
		    memoryCC = 50 - memory.length();
		    target.add(memoryCCLabel);
		}else{
		    memoryCC = 50;
		    target.add(memoryCCLabel);
		}

	    }
	});

	TextField<String> caseOrShieldTextField = new TextField<String>("caseOrShieldTextField",
		new PropertyModel<String>(this, "caseOrShield"));
	caseOrShieldTextField.setMarkupId("caseOrShieldTextField");
	caseOrShieldTextField.setOutputMarkupPlaceholderTag(true);
	form.add(caseOrShieldTextField);

	final Label caseOrShieldCCLabel = new Label("caseOrShieldCCLabel", new PropertyModel<Integer>(this,
		"caseOrShieldCC"));
	caseOrShieldCCLabel.setMarkupId("caseOrShieldCCLabel");
	caseOrShieldCCLabel.setOutputMarkupId(true);
	form.add(caseOrShieldCCLabel);

	caseOrShieldTextField.add(new AjaxFormComponentUpdatingBehavior("onkeyup") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void onUpdate(AjaxRequestTarget target) {
		//logger.debug("subscription info..." + recommendation.getSubscriptionInfo());
		if (recommendation != null && caseOrShield != null){
		    caseOrShieldCC = 50 - caseOrShield.length();
		    target.add(caseOrShieldCCLabel);
		}else{
		    caseOrShieldCC = 50;
		    target.add(caseOrShieldCCLabel);
		}

	    }
	});

	TextField<String> appcessoriesTextField = new TextField<String>("appcessoriesTextField",
		new PropertyModel<String>(this, "appcessories"));
	appcessoriesTextField.setMarkupId("appcessoriesTextField");
	appcessoriesTextField.setOutputMarkupPlaceholderTag(true);
	form.add(appcessoriesTextField);

	final Label appcessoriesCCLabel = new Label("appcessoriesCCLabel", new PropertyModel<Integer>(this,
		"appcessoriesCC"));
	appcessoriesCCLabel.setMarkupId("appcessoriesCCLabel");
	appcessoriesCCLabel.setOutputMarkupId(true);
	form.add(appcessoriesCCLabel);

	appcessoriesTextField.add(new AjaxFormComponentUpdatingBehavior("onkeyup") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void onUpdate(AjaxRequestTarget target) {
		//logger.debug("subscription info..." + recommendation.getSubscriptionInfo());
		if (recommendation != null && appcessories != null){
		    appcessoriesCC = 50 - appcessories.length();
		    target.add(appcessoriesCCLabel);
		}else{
		    appcessoriesCC = 50;
		    target.add(appcessoriesCCLabel);
		}

	    }
	});

	TextField<String> chargerTextField = new TextField<String>("chargerTextField", new PropertyModel<String>(this,
		"charger"));
	chargerTextField.setMarkupId("chargerTextField");
	chargerTextField.setOutputMarkupPlaceholderTag(true);
	addChild(chargerTextField);

	final Label chargerCCLabel = new Label("chargerCCLabel", new PropertyModel<Integer>(this, "chargerCC"));
	chargerCCLabel.setMarkupId("chargerCCLabel");
	chargerCCLabel.setOutputMarkupId(true);
	form.add(chargerCCLabel);

	chargerTextField.add(new AjaxFormComponentUpdatingBehavior("onkeyup") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void onUpdate(AjaxRequestTarget target) {
		//logger.debug("subscription info..." + recommendation.getSubscriptionInfo());
		if (recommendation != null && charger != null){
		    chargerCC = 50 - charger.length();
		    target.add(chargerCCLabel);
		}else{
		    chargerCC = 50;
		    target.add(chargerCCLabel);
		}

	    }
	});

	TextField<String> gsProtectionTextField = new TextField<String>("gsProtectionTextField",
		new PropertyModel<String>(this, "gsProtection"));
	gsProtectionTextField.setMarkupId("gsProtectionTextField");
	gsProtectionTextField.setOutputMarkupPlaceholderTag(true);
	form.add(gsProtectionTextField);

	final Label gsProtectionCCLabel = new Label("gsProtectionCCLabel", new PropertyModel<Integer>(this,
		"gsProtectionCC"));
	gsProtectionCCLabel.setMarkupId("gsProtectionCCLabel");
	gsProtectionCCLabel.setOutputMarkupId(true);
	form.add(gsProtectionCCLabel);

	gsProtectionTextField.add(new AjaxFormComponentUpdatingBehavior("onkeyup") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void onUpdate(AjaxRequestTarget target) {
		//logger.debug("subscription info..." + recommendation.getSubscriptionInfo());
		if (recommendation != null && gsProtection != null){
		    gsProtectionCC = 50 - gsProtection.length();
		    target.add(gsProtectionCCLabel);
		}else{
		    gsProtectionCC = 50;
		    target.add(gsProtectionCCLabel);
		}

	    }
	});

	TextField<String> tradeInPlusTextField = new TextField<String>("tradeInPlusTextField",
		new PropertyModel<String>(this, "tradeInPlus"));
	tradeInPlusTextField.setMarkupId("tradeInPlusTextField");
	tradeInPlusTextField.setOutputMarkupPlaceholderTag(true);
	form.add(tradeInPlusTextField);

	final Label tradeInPlusCCLabel = new Label("tradeInPlusCCLabel", new PropertyModel<Integer>(this,
		"tradeInPlusCC"));
	tradeInPlusCCLabel.setMarkupId("tradeInPlusCCLabel");
	tradeInPlusCCLabel.setOutputMarkupId(true);
	form.add(tradeInPlusCCLabel);

	tradeInPlusTextField.add(new AjaxFormComponentUpdatingBehavior("onkeyup") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void onUpdate(AjaxRequestTarget target) {
		//logger.debug("subscription info..." + recommendation.getSubscriptionInfo());
		if (recommendation != null && tradeInPlus != null){
		    tradeInPlusCC = 50 - tradeInPlus.length();
		    target.add(tradeInPlusCCLabel);
		}else{
		    tradeInPlusCC = 50;
		    target.add(tradeInPlusCCLabel);
		}

	    }
	});

	TextField<String> financingTextField = new TextField<String>("financingTextField", new PropertyModel<String>(
		this, "financing"));
	financingTextField.setMarkupId("financingTextField");
	financingTextField.setOutputMarkupPlaceholderTag(true);
	form.add(financingTextField);

	final Label financingCCLabel = new Label("financingCCLabel", new PropertyModel<Integer>(this, "financingCC"));
	financingCCLabel.setMarkupId("financingCCLabel");
	financingCCLabel.setOutputMarkupId(true);
	form.add(financingCCLabel);

	financingTextField.add(new AjaxFormComponentUpdatingBehavior("onkeyup") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void onUpdate(AjaxRequestTarget target) {
		//logger.debug("subscription info..." + recommendation.getSubscriptionInfo());
		if (recommendation != null && financing != null){
		    financingCC = 50 - financing.length();
		    target.add(financingCCLabel);
		}else{
		    financingCC = 50;
		    target.add(financingCCLabel);
		}

	    }
	});

	setLinkVisible(this.clearSectionButton, RecSheetsSections.ESSENTIALS_MOBILE.dataWasEntered(recommendation));

    }

    @Override
    void clearSection(AjaxRequestTarget target) {
	recommendation.setPreference(EssentialTypeEnum.HANDSFREE.getId(), null);
	recommendation.setPreference(EssentialTypeEnum.MEMORY.getId(), null);
	recommendation.setPreference(EssentialTypeEnum.CASE_SHIELD.getId(), null);
	recommendation.setPreference(EssentialTypeEnum.APPCESSORIES.getId(), null);
	recommendation.setPreference(EssentialTypeEnum.CHARGER.getId(), null);
	recommendation.setPreference(EssentialTypeEnum.GEEK_SQUAD_PROTECTION.getId(), null);
	recommendation.setPreference(EssentialTypeEnum.TRADE_IN_PLUS.getId(), null);
	recommendation.setPreference(EssentialTypeEnum.FINANCING.getId(), null);
	setHandsFree(null);
	setMemory(null);
	setCaseOrShield(null);
	setAppcessories(null);
	setCharger(null);
	setGSProtection(null);
	setTradeInPlus(null);
	setFinancing(null);
	handsFreeCC = 50;
	memoryCC = 50;
	caseOrShieldCC = 50;
	appcessoriesCC = 50;
	chargerCC = 50;
	gsProtectionCC = 50;
	tradeInPlusCC = 50;
	financingCC = 50;
	form.clearInput();
	target.add(form);

	markSections();

	setLinkVisible(this.clearSectionButton, RecSheetsSections.ESSENTIALS_MOBILE.dataWasEntered(recommendation));
    }

    @Override
    void saveScreenValues(final AjaxRequestTarget target) {
	recommendation.setPreference(EssentialTypeEnum.HANDSFREE.getId(), handsFree);
	recommendation.setPreference(EssentialTypeEnum.MEMORY.getId(), memory);
	recommendation.setPreference(EssentialTypeEnum.CASE_SHIELD.getId(), caseOrShield);
	recommendation.setPreference(EssentialTypeEnum.APPCESSORIES.getId(), appcessories);
	recommendation.setPreference(EssentialTypeEnum.CHARGER.getId(), charger);
	recommendation.setPreference(EssentialTypeEnum.GEEK_SQUAD_PROTECTION.getId(), gsProtection);
	recommendation.setPreference(EssentialTypeEnum.TRADE_IN_PLUS.getId(), tradeInPlus);
	recommendation.setPreference(EssentialTypeEnum.FINANCING.getId(), financing);

	setLinkVisible(this.clearSectionButton, RecSheetsSections.ESSENTIALS_MOBILE.dataWasEntered(recommendation));
    }

}
