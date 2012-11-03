package com.bestbuy.bbym.ise.drp.recsheet;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.validation.validator.PatternValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bestbuy.bbym.ise.util.Util;

/**
 * 
 * @author a909782
 * 
 */

public class PCServicesSupportProtectionPage extends AbstractRecSheetPage {

    private static final long serialVersionUID = 1L;

    private static Logger logger = LoggerFactory.getLogger(PCServicesSupportProtectionPage.class);

    private static String CBX_TRUE = "true";

    // GS tech
    private Boolean gsTechSupport1YearModel, gsTechSupport2YearModel, gsTechSupport3YearModel;

    // GS Protection
    private Boolean gspLaptop1YearModel, gspLaptop2YearModel, gspLaptop3YearModel, gspDesktop1YearModel,
	    gspDesktop2YearModel, gspDesktop3YearModel;

    private Label gspLaptop1YearPriceLabel, gspLaptop2YearPriceLabel, gspLaptop3YearPriceLabel,
	    gspDesktop1YearPriceLabel, gspDesktop2YearPriceLabel, gspDesktop3YearPriceLabel;

    private String gspLaptop1YearPrice, gspLaptop2YearPrice, gspLaptop3YearPrice, gspDesktop1YearPrice,
	    gspDesktop2YearPrice, gspDesktop3YearPrice;

    private String gspPriceLabel;

    private float gspPrice;

    private TextField<String> gspPriceTextField;
    // Double Coverage
    private Boolean doubleCoverageLaptop1YearModel, doubleCoverageLaptop2YearModel, doubleCoverageDesktop1YearModel,
	    doubleCoverageDesktop2YearModel;

    enum EssentialTypeId {
	GSTS1YEAR(72), GSTS2YEAR(73), GSTS3YEAR(74), GSPLAPTOP1YEAR(76), GSPLAPTOP2YEAR(77), GSPLAPTOP3YEAR(78),
	GSPDESKTOP1YEAR(79), GSPDESKTOP2YEAR(80), GSPDESKTOP3YEAR(81), DDLAPTOP1YEAR(82), DDLAPTOP2YEAR(83),
	DDDESKTOP1YEAR(84), DDDESKTOP2YEAR(85);

	private int id;

	private EssentialTypeId(int id) {
	    this.id = id;
	}

	public Long getId() {
	    return new Long(id);
	}

    }

    public PCServicesSupportProtectionPage(PageParameters parameters) {
	super(parameters);

	setDefaultGSPPriceLabels();

	loadSavedPreferences();

	// Geek Squad Tech Support
	CheckBox gsTechSupport1YearCbx = new CheckBox("gsTechSupport1YearCbx", new PropertyModel<Boolean>(this,
		"gsTechSupport1YearModel"));
	gsTechSupport1YearCbx.setOutputMarkupPlaceholderTag(true);
	gsTechSupport1YearCbx.setMarkupId("gsTechSupport1YearCbx");
	form.add(gsTechSupport1YearCbx);

	CheckBox gsTechSupport2YearCbx = new CheckBox("gsTechSupport2YearCbx", new PropertyModel<Boolean>(this,
		"gsTechSupport2YearModel"));
	gsTechSupport2YearCbx.setOutputMarkupPlaceholderTag(true);
	gsTechSupport2YearCbx.setMarkupId("gsTechSupport2YearCbx");
	form.add(gsTechSupport2YearCbx);

	CheckBox gsTechSupport3YearCbx = new CheckBox("gsTechSupport3YearCbx", new PropertyModel<Boolean>(this,
		"gsTechSupport3YearModel"));
	gsTechSupport3YearCbx.setOutputMarkupPlaceholderTag(true);
	gsTechSupport3YearCbx.setMarkupId("gsTechSupport3YearCbx");
	form.add(gsTechSupport3YearCbx);

	gspPriceTextField = new TextField<String>("gspPrice", new PropertyModel<String>(this, "gspPriceLabel"));
	gspPriceTextField.setOutputMarkupPlaceholderTag(true);
	gspPriceTextField.setMarkupId("gspPrice");
	form.add(gspPriceTextField);

	gspPriceTextField.add(new PatternValidator("^[0-9.$]+$"));

	gspPriceTextField.add(new AjaxFormComponentUpdatingBehavior("onblur") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void onUpdate(AjaxRequestTarget target) {
		formatGspPrice();
		target.add(gspPriceTextField);
	    }

	});

	AjaxButton gspPricingSubmitButton = new AjaxButton("gspPricingSubmitButton", new ResourceModel(
		"pcSupportProtectionServicesPageForm.gsProtection.submitButton.label")) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
	    }
	};
	gspPricingSubmitButton.setOutputMarkupPlaceholderTag(true);
	gspPricingSubmitButton.setMarkupId("gspPricingSubmitButton");
	form.add(gspPricingSubmitButton);

	gspLaptop1YearPriceLabel = new Label("gspLaptop1YearPriceLabel", new PropertyModel<String>(this,
		"gspLaptop1YearPrice"));
	gspLaptop1YearPriceLabel.setOutputMarkupPlaceholderTag(true);
	gspLaptop1YearPriceLabel.setMarkupId("gspLaptop1YearPriceLabel");
	form.add(gspLaptop1YearPriceLabel);

	gspLaptop2YearPriceLabel = new Label("gspLaptop2YearPriceLabel", new PropertyModel<String>(this,
		"gspLaptop2YearPrice"));
	gspLaptop2YearPriceLabel.setOutputMarkupPlaceholderTag(true);
	gspLaptop2YearPriceLabel.setMarkupId("gspLaptop2YearPriceLabel");
	form.add(gspLaptop2YearPriceLabel);

	gspLaptop3YearPriceLabel = new Label("gspLaptop3YearPriceLabel", new PropertyModel<String>(this,
		"gspLaptop3YearPrice"));
	gspLaptop3YearPriceLabel.setOutputMarkupPlaceholderTag(true);
	gspLaptop3YearPriceLabel.setMarkupId("gspLaptop3YearPriceLabel");
	form.add(gspLaptop3YearPriceLabel);

	gspDesktop1YearPriceLabel = new Label("gspDesktop1YearPriceLabel", new PropertyModel<String>(this,
		"gspDesktop1YearPrice"));
	gspDesktop1YearPriceLabel.setOutputMarkupPlaceholderTag(true);
	gspDesktop1YearPriceLabel.setMarkupId("gspDesktop1YearPriceLabel");
	form.add(gspDesktop1YearPriceLabel);

	gspDesktop2YearPriceLabel = new Label("gspDesktop2YearPriceLabel", new PropertyModel<String>(this,
		"gspDesktop2YearPrice"));
	gspDesktop2YearPriceLabel.setOutputMarkupPlaceholderTag(true);
	gspDesktop2YearPriceLabel.setMarkupId("gspDesktop2YearPriceLabel");
	form.add(gspDesktop2YearPriceLabel);

	gspDesktop3YearPriceLabel = new Label("gspDesktop3YearPriceLabel", new PropertyModel<String>(this,
		"gspDesktop3YearPrice"));
	gspDesktop3YearPriceLabel.setOutputMarkupPlaceholderTag(true);
	gspDesktop3YearPriceLabel.setMarkupId("gspDesktop3YearPriceLabel");
	form.add(gspDesktop3YearPriceLabel);

	CheckBox gspLaptop1YearCbx = new CheckBox("gspLaptop1YearCbx", new PropertyModel<Boolean>(this,
		"gspLaptop1YearModel"));
	gspLaptop1YearCbx.setOutputMarkupPlaceholderTag(true);
	gspLaptop1YearCbx.setMarkupId("gspLaptop1YearCbx");
	form.add(gspLaptop1YearCbx);

	CheckBox gspLaptop2YearCbx = new CheckBox("gspLaptop2YearCbx", new PropertyModel<Boolean>(this,
		"gspLaptop2YearModel"));
	gspLaptop2YearCbx.setOutputMarkupPlaceholderTag(true);
	gspLaptop2YearCbx.setMarkupId("gspLaptop2YearCbx");
	form.add(gspLaptop2YearCbx);

	CheckBox gspLaptop3YearCbx = new CheckBox("gspLaptop3YearCbx", new PropertyModel<Boolean>(this,
		"gspLaptop3YearModel"));
	gspLaptop3YearCbx.setOutputMarkupPlaceholderTag(true);
	gspLaptop3YearCbx.setMarkupId("gspLaptop3YearCbx");
	form.add(gspLaptop3YearCbx);

	CheckBox gspDesktop1YearCbx = new CheckBox("gspDesktop1YearCbx", new PropertyModel<Boolean>(this,
		"gspDesktop1YearModel"));
	gspDesktop1YearCbx.setOutputMarkupPlaceholderTag(true);
	gspDesktop1YearCbx.setMarkupId("gspDesktop1YearCbx");
	form.add(gspDesktop1YearCbx);

	CheckBox gspDesktop2YearCbx = new CheckBox("gspDesktop2YearCbx", new PropertyModel<Boolean>(this,
		"gspDesktop2YearModel"));
	gspDesktop2YearCbx.setOutputMarkupPlaceholderTag(true);
	gspDesktop2YearCbx.setMarkupId("gspDesktop2YearCbx");
	form.add(gspDesktop2YearCbx);

	CheckBox gspDesktop3YearCbx = new CheckBox("gspDesktop3YearCbx", new PropertyModel<Boolean>(this,
		"gspDesktop3YearModel"));
	gspDesktop3YearCbx.setOutputMarkupPlaceholderTag(true);
	gspDesktop3YearCbx.setMarkupId("gspDesktop3YearCbx");
	form.add(gspDesktop3YearCbx);

	CheckBox doubleCoverageLaptop1YearCbx = new CheckBox("doubleCoverageLaptop1YearCbx",
		new PropertyModel<Boolean>(this, "doubleCoverageLaptop1YearModel"));
	doubleCoverageLaptop1YearCbx.setOutputMarkupPlaceholderTag(true);
	doubleCoverageLaptop1YearCbx.setMarkupId("doubleCoverageLaptop1YearCbx");
	form.add(doubleCoverageLaptop1YearCbx);

	CheckBox doubleCoverageLaptop2YearCbx = new CheckBox("doubleCoverageLaptop2YearCbx",
		new PropertyModel<Boolean>(this, "doubleCoverageLaptop2YearModel"));
	doubleCoverageLaptop2YearCbx.setOutputMarkupPlaceholderTag(true);
	doubleCoverageLaptop2YearCbx.setMarkupId("doubleCoverageLaptop2YearCbx");
	form.add(doubleCoverageLaptop2YearCbx);

	CheckBox doubleCoverageDesktop1YearCbx = new CheckBox("doubleCoverageDesktop1YearCbx",
		new PropertyModel<Boolean>(this, "doubleCoverageDesktop1YearModel"));
	doubleCoverageDesktop1YearCbx.setOutputMarkupPlaceholderTag(true);
	doubleCoverageDesktop1YearCbx.setMarkupId("doubleCoverageDesktop1YearCbx");
	form.add(doubleCoverageDesktop1YearCbx);

	CheckBox doubleCoverageDesktop2YearCbx = new CheckBox("doubleCoverageDesktop2YearCbx",
		new PropertyModel<Boolean>(this, "doubleCoverageDesktop2YearModel"));
	doubleCoverageDesktop2YearCbx.setOutputMarkupPlaceholderTag(true);
	doubleCoverageDesktop2YearCbx.setMarkupId("doubleCoverageDesktop2YearCbx");
	form.add(doubleCoverageDesktop2YearCbx);

    }

    private void setDefaultGSPPriceLabels() {
	gspLaptop1YearPrice = getString("pcSupportProtectionServicesPageForm.gsProtection.laptopHandling.1YearPrice.label");
	gspLaptop2YearPrice = getString("pcSupportProtectionServicesPageForm.gsProtection.laptopHandling.2YearPrice.label");
	gspLaptop3YearPrice = getString("pcSupportProtectionServicesPageForm.gsProtection.laptopHandling.3YearPrice.label");
	gspDesktop1YearPrice = getString("pcSupportProtectionServicesPageForm.gsProtection.desktopHandling.1YearPrice.label");
	gspDesktop2YearPrice = getString("pcSupportProtectionServicesPageForm.gsProtection.desktopHandling.2YearPrice.label");
	gspDesktop3YearPrice = getString("pcSupportProtectionServicesPageForm.gsProtection.desktopHandling.3YearPrice.label");
    }

    private void loadSavedPreferences() {
	Map<Long, String> preferences = recommendation.getPreferences();
	if (preferences.get(EssentialTypeId.GSTS1YEAR.getId()) != null){
	    gsTechSupport1YearModel = Boolean.TRUE;
	}
	if (preferences.get(EssentialTypeId.GSTS2YEAR.getId()) != null){
	    gsTechSupport2YearModel = Boolean.TRUE;
	}
	if (preferences.get(EssentialTypeId.GSTS3YEAR.getId()) != null){
	    gsTechSupport3YearModel = Boolean.TRUE;
	}

	if (preferences.get(EssentialTypeId.GSPLAPTOP1YEAR.getId()) != null){
	    gspLaptop1YearModel = Boolean.TRUE;
	}
	if (preferences.get(EssentialTypeId.GSPLAPTOP2YEAR.getId()) != null){
	    gspLaptop2YearModel = Boolean.TRUE;
	}
	if (preferences.get(EssentialTypeId.GSPLAPTOP3YEAR.getId()) != null){
	    gspLaptop3YearModel = Boolean.TRUE;
	}

	if (preferences.get(EssentialTypeId.GSPDESKTOP1YEAR.getId()) != null){
	    gspDesktop1YearModel = Boolean.TRUE;
	}
	if (preferences.get(EssentialTypeId.GSPDESKTOP2YEAR.getId()) != null){
	    gspDesktop2YearModel = Boolean.TRUE;
	}
	if (preferences.get(EssentialTypeId.GSPDESKTOP3YEAR.getId()) != null){
	    gspDesktop3YearModel = Boolean.TRUE;
	}
	if (preferences.get(EssentialTypeId.DDDESKTOP1YEAR.getId()) != null){
	    doubleCoverageDesktop1YearModel = Boolean.TRUE;
	}
	if (preferences.get(EssentialTypeId.DDDESKTOP2YEAR.getId()) != null){
	    doubleCoverageDesktop2YearModel = Boolean.TRUE;
	}
	if (preferences.get(EssentialTypeId.DDLAPTOP1YEAR.getId()) != null){
	    doubleCoverageLaptop1YearModel = Boolean.TRUE;
	}
	if (preferences.get(EssentialTypeId.DDLAPTOP2YEAR.getId()) != null){
	    doubleCoverageLaptop2YearModel = Boolean.TRUE;
	}
    }

    private void formatGspPrice() {
	if (!StringUtils.isEmpty(gspPriceLabel)){
	    gspPriceLabel = gspPriceLabel.replace("$", "");
	    gspPrice = Util.getFloat(gspPriceLabel, 0) / 100;
	    gspPriceLabel = "$" + gspPrice;
	}

    }

    private void removeGSPPreferences() {
	recommendation.removePreferences(new Long[] {EssentialTypeId.GSPLAPTOP1YEAR.getId(),
		EssentialTypeId.GSPLAPTOP2YEAR.getId(), EssentialTypeId.GSPLAPTOP3YEAR.getId(),
		EssentialTypeId.GSPDESKTOP1YEAR.getId(), EssentialTypeId.GSPDESKTOP2YEAR.getId(),
		EssentialTypeId.GSPDESKTOP3YEAR.getId() });
    }

    private void removeGSTechSupportPreferences() {
	recommendation.removePreferences(new Long[] {EssentialTypeId.GSTS1YEAR.getId(),
		EssentialTypeId.GSTS2YEAR.getId(), EssentialTypeId.GSTS3YEAR.getId() });
    }

    private void removeDDPreferences() {
	recommendation.removePreferences(new Long[] {EssentialTypeId.DDLAPTOP1YEAR.getId(),
		EssentialTypeId.DDLAPTOP2YEAR.getId(), EssentialTypeId.DDDESKTOP1YEAR.getId(),
		EssentialTypeId.DDDESKTOP2YEAR.getId() });
    }

    @Override
    void saveScreenValues(AjaxRequestTarget target) {
	removeDDPreferences();
	removeGSPPreferences();
	removeGSTechSupportPreferences();
	if (gsTechSupport1YearModel != null && gsTechSupport1YearModel.booleanValue()){
	    recommendation.getPreferences().put(EssentialTypeId.GSTS1YEAR.getId(), CBX_TRUE);
	}
	if (gsTechSupport2YearModel != null && gsTechSupport2YearModel.booleanValue()){
	    recommendation.getPreferences().put(EssentialTypeId.GSTS2YEAR.getId(), CBX_TRUE);
	}
	if (gsTechSupport3YearModel != null && gsTechSupport3YearModel.booleanValue()){
	    recommendation.getPreferences().put(EssentialTypeId.GSTS3YEAR.getId(), CBX_TRUE);
	}
	if (gspDesktop1YearModel != null && gspDesktop1YearModel.booleanValue()){
	    recommendation.getPreferences().put(EssentialTypeId.GSPDESKTOP1YEAR.getId(), CBX_TRUE);
	}
	if (gspDesktop2YearModel != null && gspDesktop2YearModel.booleanValue()){
	    recommendation.getPreferences().put(EssentialTypeId.GSPDESKTOP2YEAR.getId(), CBX_TRUE);
	}
	if (gspDesktop3YearModel != null && gspDesktop3YearModel.booleanValue()){
	    recommendation.getPreferences().put(EssentialTypeId.GSPDESKTOP3YEAR.getId(), CBX_TRUE);
	}
	if (gspLaptop1YearModel != null && gspLaptop1YearModel.booleanValue()){
	    recommendation.getPreferences().put(EssentialTypeId.GSPLAPTOP1YEAR.getId(), CBX_TRUE);
	}
	if (gspLaptop2YearModel != null && gspLaptop2YearModel.booleanValue()){
	    recommendation.getPreferences().put(EssentialTypeId.GSPLAPTOP2YEAR.getId(), CBX_TRUE);
	}
	if (gspLaptop3YearModel != null && gspLaptop3YearModel.booleanValue()){
	    recommendation.getPreferences().put(EssentialTypeId.GSPLAPTOP3YEAR.getId(), CBX_TRUE);
	}
	if (doubleCoverageDesktop1YearModel != null && doubleCoverageDesktop1YearModel.booleanValue()){
	    recommendation.getPreferences().put(EssentialTypeId.DDDESKTOP1YEAR.getId(), CBX_TRUE);
	}
	if (doubleCoverageDesktop2YearModel != null && doubleCoverageDesktop2YearModel.booleanValue()){
	    recommendation.getPreferences().put(EssentialTypeId.DDDESKTOP2YEAR.getId(), CBX_TRUE);
	}
	if (doubleCoverageLaptop1YearModel != null && doubleCoverageLaptop1YearModel.booleanValue()){
	    recommendation.getPreferences().put(EssentialTypeId.DDLAPTOP1YEAR.getId(), CBX_TRUE);
	}
	if (doubleCoverageLaptop2YearModel != null && doubleCoverageLaptop2YearModel.booleanValue()){
	    recommendation.getPreferences().put(EssentialTypeId.DDLAPTOP2YEAR.getId(), CBX_TRUE);
	}
	logger.debug("in the save screen values for PC SUPPORT PROTECT SERVICES..." + recommendation);
	super.saveScreenValues(target);
    }

    @Override
    void clearSection(AjaxRequestTarget target) {
	removeDDPreferences();
	removeGSPPreferences();
	removeGSTechSupportPreferences();
	gspPriceLabel = new String("");
	gsTechSupport1YearModel = Boolean.FALSE;
	gsTechSupport2YearModel = Boolean.FALSE;
	gsTechSupport3YearModel = Boolean.FALSE;

	gspLaptop1YearModel = Boolean.FALSE;
	gspLaptop2YearModel = Boolean.FALSE;
	gspLaptop3YearModel = Boolean.FALSE;
	gspDesktop1YearModel = Boolean.FALSE;
	gspDesktop2YearModel = Boolean.FALSE;
	gspDesktop3YearModel = Boolean.FALSE;

	doubleCoverageDesktop1YearModel = Boolean.FALSE;
	doubleCoverageDesktop2YearModel = Boolean.FALSE;
	doubleCoverageLaptop1YearModel = Boolean.FALSE;
	doubleCoverageLaptop2YearModel = Boolean.FALSE;

	target.add(form);

    }

}
