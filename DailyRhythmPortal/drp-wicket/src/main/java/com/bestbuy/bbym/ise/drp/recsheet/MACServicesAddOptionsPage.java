package com.bestbuy.bbym.ise.drp.recsheet;

import java.util.Map;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MACServicesAddOptionsPage extends AbstractRecSheetPage {

    private static Logger logger = LoggerFactory.getLogger(MACServicesAddOptionsPage.class);

    private static final long serialVersionUID = 1L;

    private static String CBX_TRUE = "true";

    private Boolean dataBackupModel, dataBackup500GBModel, msOfficeModel, appleTvModel, parallelDesktopModel;

    enum EssentialTypeId {
	MACDATABACKUP(116), MACDATABACKUP500GB(117), MACMSOFFICE(118), MACAPPLETV(119), MACPARALLELDESKTOP(120);

	private int id;

	private EssentialTypeId(int id) {
	    this.id = id;
	}

	public Long getId() {
	    return new Long(id);
	}

    }

    public MACServicesAddOptionsPage(PageParameters parameters) {
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

	CheckBox appleTvCbx = new CheckBox("appleTvCbx", new PropertyModel<Boolean>(this, "appleTvModel"));
	appleTvCbx.setOutputMarkupPlaceholderTag(true);
	appleTvCbx.setMarkupId("appleTvCbx");
	form.add(appleTvCbx);

	CheckBox parallelDesktopCbx = new CheckBox("parallelDesktopCbx", new PropertyModel<Boolean>(this,
		"parallelDesktopModel"));
	parallelDesktopCbx.setOutputMarkupPlaceholderTag(true);
	parallelDesktopCbx.setMarkupId("parallelDesktopCbx");
	form.add(parallelDesktopCbx);

    }

    private void loadSavedPreferences() {
	Map<Long, String> preferences = recommendation.getPreferences();
	if (preferences.get(EssentialTypeId.MACDATABACKUP.getId()) != null){
	    dataBackupModel = Boolean.TRUE;
	}
	if (preferences.get(EssentialTypeId.MACDATABACKUP500GB.getId()) != null){
	    dataBackup500GBModel = Boolean.TRUE;
	}
	if (preferences.get(EssentialTypeId.MACMSOFFICE.getId()) != null){
	    msOfficeModel = Boolean.TRUE;
	}

	if (preferences.get(EssentialTypeId.MACAPPLETV.getId()) != null){
	    appleTvModel = Boolean.TRUE;
	}
	if (preferences.get(EssentialTypeId.MACPARALLELDESKTOP.getId()) != null){
	    parallelDesktopModel = Boolean.TRUE;
	}
    }

    @Override
    void saveScreenValues(AjaxRequestTarget target) {
	recommendation.removePreferences(new Long[] {EssentialTypeId.MACDATABACKUP.getId(),
		EssentialTypeId.MACDATABACKUP500GB.getId(), EssentialTypeId.MACMSOFFICE.getId(),
		EssentialTypeId.MACAPPLETV.getId(), EssentialTypeId.MACPARALLELDESKTOP.getId() });
	if (dataBackupModel != null && dataBackupModel.booleanValue()){
	    recommendation.getPreferences().put(EssentialTypeId.MACDATABACKUP.getId(), CBX_TRUE);
	}
	if (dataBackup500GBModel != null && dataBackup500GBModel.booleanValue()){
	    recommendation.getPreferences().put(EssentialTypeId.MACDATABACKUP500GB.getId(), CBX_TRUE);
	}
	if (msOfficeModel != null && msOfficeModel.booleanValue()){
	    recommendation.getPreferences().put(EssentialTypeId.MACMSOFFICE.getId(), CBX_TRUE);
	}
	if (appleTvModel != null && appleTvModel.booleanValue()){
	    recommendation.getPreferences().put(EssentialTypeId.MACAPPLETV.getId(), CBX_TRUE);
	}
	if (parallelDesktopModel != null && parallelDesktopModel.booleanValue()){
	    recommendation.getPreferences().put(EssentialTypeId.MACPARALLELDESKTOP.getId(), CBX_TRUE);
	}

	logger.debug("in the save screen values for PC ADD OPTIONS..." + recommendation);
	super.saveScreenValues(target);
    }

    @Override
    void clearSection(AjaxRequestTarget target) {
	dataBackupModel = Boolean.FALSE;
	dataBackup500GBModel = Boolean.FALSE;
	msOfficeModel = Boolean.FALSE;
	appleTvModel = Boolean.FALSE;
	parallelDesktopModel = Boolean.FALSE;
	recommendation.removePreferences(new Long[] {EssentialTypeId.MACDATABACKUP.getId(),
		EssentialTypeId.MACDATABACKUP500GB.getId(), EssentialTypeId.MACMSOFFICE.getId(),
		EssentialTypeId.MACAPPLETV.getId(), EssentialTypeId.MACPARALLELDESKTOP.getId() });
	target.add(form);

    }

}
