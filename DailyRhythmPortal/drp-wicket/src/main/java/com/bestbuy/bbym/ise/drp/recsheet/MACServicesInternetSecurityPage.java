package com.bestbuy.bbym.ise.drp.recsheet;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.wicket.IClusterable;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.CheckBoxMultipleChoice;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bestbuy.bbym.ise.drp.util.SelectItem;

public class MACServicesInternetSecurityPage extends AbstractRecSheetPage {

    private static Logger logger = LoggerFactory.getLogger(MACServicesInternetSecurityPage.class);
    private static final long serialVersionUID = 1L;

    CheckBoxMultipleChoice<SelectItem<String>> listChoice, listChoice2;

    long[] serviceLengthChkBoxIdArray = new long[] {95, 96, 97, 98 };
    long[] softPackageChkBoxIdArray = new long[] {99, 100, 101 };

    PageInputModel selectedInput;

    public MACServicesInternetSecurityPage(PageParameters parameters) {
	super(parameters);

	selectedInput = new PageInputModel();
	setDefaultModel(new CompoundPropertyModel<PageInputModel>(selectedInput));

	ChoiceRenderer<SelectItem<String>> checkRenderer = new ChoiceRenderer<SelectItem<String>>("value", "key");

	listChoice = new CheckBoxMultipleChoice<SelectItem<String>>("macInternetSecurityOptions",
		retrieveServiceLengthCheckBoxPropertiesList(), checkRenderer);
	listChoice.setMarkupId("macInternetSecurityOptions");
	listChoice.setOutputMarkupId(true);
	listChoice.setOutputMarkupPlaceholderTag(true);
	addChild(listChoice);

	listChoice2 = new CheckBoxMultipleChoice<SelectItem<String>>("macSoftwarePackageOptions",
		retrieveSoftpackageCheckBoxPropertiesList(), checkRenderer);
	listChoice2.setMarkupId("macInternetSecurityOptions");
	listChoice2.setOutputMarkupId(true);
	listChoice2.setOutputMarkupPlaceholderTag(true);
	addChild(listChoice2);

    }

    private List<SelectItem<String>> retrieveServiceLengthCheckBoxPropertiesList() {
	List<SelectItem<String>> propertiesList = new ArrayList<SelectItem<String>>();

	String[] propArray = populateServiceLengthPageProeprties();
	for(int j = 0; j < propArray.length; j++){
	    propertiesList.add(new SelectItem<String>(String.valueOf(serviceLengthChkBoxIdArray[j]), propArray[j]));
	}
	return propertiesList;
    }

    private List<SelectItem<String>> retrieveSoftpackageCheckBoxPropertiesList() {
	List<SelectItem<String>> propertiesList = new ArrayList<SelectItem<String>>();

	String[] propArray = populateSoftPackagePageProeprties();
	for(int j = 0; j < propArray.length; j++){
	    propertiesList.add(new SelectItem<String>(String.valueOf(softPackageChkBoxIdArray[j]), propArray[j]));
	}
	return propertiesList;
    }

    private class PageInputModel implements IClusterable {

	private static final long serialVersionUID = 1L;
	List<SelectItem<String>> pageSelectedOptions = new ArrayList<SelectItem<String>>();
	List<SelectItem<String>> macInternetSecurityOptions = new ArrayList<SelectItem<String>>();
	List<SelectItem<String>> macSoftwarePackageOptions = new ArrayList<SelectItem<String>>();

	public PageInputModel() {
	    for(int i = 0; i < serviceLengthChkBoxIdArray.length; i++){
		if (recommendation != null){
		    String selVal = recommendation.getPreference(serviceLengthChkBoxIdArray[i]);
		    if (selVal != null && selVal.equalsIgnoreCase("true")){
			macInternetSecurityOptions.add(new SelectItem<String>(String
				.valueOf(serviceLengthChkBoxIdArray[i]), selVal));
		    }
		}
	    }

	    for(int i = 0; i < softPackageChkBoxIdArray.length; i++){
		if (recommendation != null){
		    String selVal = recommendation.getPreference((softPackageChkBoxIdArray[i]));
		    if (selVal != null && selVal.equalsIgnoreCase("true")){
			macSoftwarePackageOptions.add(new SelectItem<String>(String
				.valueOf(softPackageChkBoxIdArray[i]), selVal));
		    }
		}
	    }

	}

	public List<SelectItem<String>> getSelectedItem() {
	    pageSelectedOptions.addAll(macInternetSecurityOptions);
	    pageSelectedOptions.addAll(macSoftwarePackageOptions);
	    return pageSelectedOptions;
	}

	public void clearSelectedItems() {
	    macInternetSecurityOptions.clear();
	    macSoftwarePackageOptions.clear();
	    pageSelectedOptions.clear();
	}

    }

    @Override
    void clearSection(AjaxRequestTarget target) {

	clearPreferences();
	selectedInput.clearSelectedItems();
	target.add(listChoice);
	target.add(listChoice2);
	target.appendJavaScript("installCheckBoxHandlers();");
    }

    @Override
    void saveScreenValues(final AjaxRequestTarget target) {
	clearPreferences();
	List<SelectItem<String>> selectedList = selectedInput.getSelectedItem();
	for(SelectItem<String> selectItem: selectedList){
	    logger.debug("selectItem.getKey()............." + selectItem.getKey());
	    if (selectItem.getKey() != null)
		recommendation.setPreference(Long.valueOf((selectItem.getKey())), "true");
	}
    }

    private void clearPreferences() {
	long[] collArray = (long[]) ArrayUtils.addAll(serviceLengthChkBoxIdArray, softPackageChkBoxIdArray);
	Long[] longArr = new Long[collArray.length];
	int i = 0;
	for(long temp: collArray){
	    longArr[i++] = temp;
	}
	recommendation.removePreferences(longArr);
    }

    public String[] populateServiceLengthPageProeprties() {

	String[] props = new String[] {getString("recommendationSheet.macInternetsecurityform.6months.serviceLength"),
		getString("recommendationSheet.macInternetsecurityform.1year.serviceLength"),
		getString("recommendationSheet.macInternetsecurityform.2year.serviceLength"),
		getString("recommendationSheet.macInternetsecurityform.3year.serviceLength") };
	return props;
    }

    public String[] populateSoftPackagePageProeprties() {

	String[] props = new String[] {
		getString("recommendationSheet.macInternetsecurityform.webroot.softwarepackage"),
		getString("recommendationSheet.macInternetsecurityform.kaspersky.softwarepackage"),
		getString("recommendationSheet.macInternetsecurityform.titanium.softwarepackage"), };
	return props;
    }

}
