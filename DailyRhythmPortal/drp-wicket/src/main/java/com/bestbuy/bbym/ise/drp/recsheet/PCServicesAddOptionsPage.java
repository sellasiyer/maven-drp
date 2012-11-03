package com.bestbuy.bbym.ise.drp.recsheet;

import java.util.Map;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PCServicesAddOptionsPage extends AbstractRecSheetPage {

    private static Logger logger = LoggerFactory.getLogger(PCServicesAddOptionsPage.class);

    private static final long serialVersionUID = 1L;

    private static String CBX_TRUE = "true";

    private Boolean dataBackupModel, dataBackup500GBModel, msOfficeModel, restoreMediaModel, personalizedSetupModel;

    enum EssentialTypeId {
	DATABACKUP(86), DATABACKUP500GB(87), MSOFFICE(88), RESTOREMEDIA(89), PERSONALIZEDSETUP(90);

	private int id;

	private EssentialTypeId(int id) {
	    this.id = id;
	}

	public Long getId() {
	    return new Long(id);
	}

    }

    public PCServicesAddOptionsPage(PageParameters parameters) {
	super(parameters);

	loadSavedPreferences();

	CheckBox dataBackupCbx = new CheckBox("dataBackupCbx", new PropertyModel<Boolean>(this, "dataBackupModel"));
	dataBackupCbx.setOutputMarkupPlaceholderTag(true);
	dataBackupCbx.setMarkupId("dataBackupCbx");
	form.add(dataBackupCbx);

	CheckBox dataBackup500GBCbx = new CheckBox("dataBackup500GBCbx", new PropertyModel<Boolean>(this,
		"dataBackup500GBModel"));
	dataBackup500GBCbx.setOutputMarkupPlaceholderTag(true);
	dataBackup500GBCbx.setMarkupId("dataBackup500GBCbx");
	form.add(dataBackup500GBCbx);

	CheckBox msOfficeCbx = new CheckBox("msOfficeCbx", new PropertyModel<Boolean>(this, "msOfficeModel"));
	msOfficeCbx.setOutputMarkupPlaceholderTag(true);
	msOfficeCbx.setMarkupId("msOfficeCbx");
	form.add(msOfficeCbx);

	CheckBox restoreMediaCbx = new CheckBox("restoreMediaCbx",
		new PropertyModel<Boolean>(this, "restoreMediaModel"));
	restoreMediaCbx.setOutputMarkupPlaceholderTag(true);
	restoreMediaCbx.setMarkupId("restoreMediaCbx");
	form.add(restoreMediaCbx);

	CheckBox personalizedSetupCbx = new CheckBox("personalizedSetupCbx", new PropertyModel<Boolean>(this,
		"personalizedSetupModel"));
	personalizedSetupCbx.setOutputMarkupPlaceholderTag(true);
	personalizedSetupCbx.setMarkupId("personalizedSetupCbx");
	form.add(personalizedSetupCbx);

    }

    private void loadSavedPreferences() {
	Map<Long, String> preferences = recommendation.getPreferences();
	if (preferences.get(EssentialTypeId.DATABACKUP.getId()) != null){
	    dataBackupModel = Boolean.TRUE;
	}
	if (preferences.get(EssentialTypeId.DATABACKUP500GB.getId()) != null){
	    dataBackup500GBModel = Boolean.TRUE;
	}
	if (preferences.get(EssentialTypeId.MSOFFICE.getId()) != null){
	    msOfficeModel = Boolean.TRUE;
	}

	if (preferences.get(EssentialTypeId.RESTOREMEDIA.getId()) != null){
	    restoreMediaModel = Boolean.TRUE;
	}
	if (preferences.get(EssentialTypeId.PERSONALIZEDSETUP.getId()) != null){
	    personalizedSetupModel = Boolean.TRUE;
	}
    }

    @Override
    void saveScreenValues(AjaxRequestTarget target) {
	recommendation.removePreferences(new Long[] {EssentialTypeId.DATABACKUP.getId(),
		EssentialTypeId.DATABACKUP500GB.getId(), EssentialTypeId.MSOFFICE.getId(),
		EssentialTypeId.RESTOREMEDIA.getId(), EssentialTypeId.PERSONALIZEDSETUP.getId() });
	if (dataBackupModel != null && dataBackupModel.booleanValue()){
	    recommendation.getPreferences().put(EssentialTypeId.DATABACKUP.getId(), CBX_TRUE);
	}
	if (dataBackup500GBModel != null && dataBackup500GBModel.booleanValue()){
	    recommendation.getPreferences().put(EssentialTypeId.DATABACKUP500GB.getId(), CBX_TRUE);
	}
	if (msOfficeModel != null && msOfficeModel.booleanValue()){
	    recommendation.getPreferences().put(EssentialTypeId.MSOFFICE.getId(), CBX_TRUE);
	}
	if (restoreMediaModel != null && restoreMediaModel.booleanValue()){
	    recommendation.getPreferences().put(EssentialTypeId.RESTOREMEDIA.getId(), CBX_TRUE);
	}
	if (personalizedSetupModel != null && personalizedSetupModel.booleanValue()){
	    recommendation.getPreferences().put(EssentialTypeId.PERSONALIZEDSETUP.getId(), CBX_TRUE);
	}
	logger.debug("in the save screen values for PC ADD OPTIONS..." + recommendation);
	super.saveScreenValues(target);
    }

    @Override
    void clearSection(AjaxRequestTarget target) {
	dataBackupModel = Boolean.FALSE;
	dataBackup500GBModel = Boolean.FALSE;
	msOfficeModel = Boolean.FALSE;
	restoreMediaModel = Boolean.FALSE;
	personalizedSetupModel = Boolean.FALSE;
	recommendation.removePreferences(new Long[] {EssentialTypeId.DATABACKUP.getId(),
		EssentialTypeId.DATABACKUP500GB.getId(), EssentialTypeId.MSOFFICE.getId(),
		EssentialTypeId.RESTOREMEDIA.getId(), EssentialTypeId.PERSONALIZEDSETUP.getId() });
	target.add(form);

    }

}
