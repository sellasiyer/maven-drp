package com.bestbuy.bbym.ise.drp.recsheet;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.IClusterable;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.CheckBoxMultipleChoice;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.image.NonCachingImage;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.DynamicImageResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bestbuy.bbym.ise.drp.util.SelectItem;
import com.bestbuy.bbym.ise.drp.util.WicketUtils;

public class PCServicesWowPage extends AbstractRecSheetPage {

    private static Logger logger = LoggerFactory.getLogger(PCServicesWowPage.class);
    private static final long serialVersionUID = 1L;

    CheckBoxMultipleChoice<SelectItem<String>> listChoice;

    long[] checkBoxIdArray = new long[] {61, 62, 63, 64 };

    PageInputModel selectedInput;

    public PCServicesWowPage(PageParameters parameters) {
	super(parameters);

	selectedInput = new PageInputModel();
	setDefaultModel(new CompoundPropertyModel<PageInputModel>(selectedInput));

	List<SelectItem<String>> workOutwokringCheckOptionsList = retrieveCheckBoxPropertiesList();

	ChoiceRenderer<SelectItem<String>> checkRenderer = new ChoiceRenderer<SelectItem<String>>("value", "key");

	listChoice = new CheckBoxMultipleChoice<SelectItem<String>>("pcWalkOutwokringCheckOptions",
		workOutwokringCheckOptionsList, checkRenderer);
	listChoice.setMarkupId("pcWalkOutwokringCheckOptions");
	listChoice.setOutputMarkupId(true);
	listChoice.setOutputMarkupPlaceholderTag(true);
	addChild(listChoice);

	// Code to dynamically generate and add barcode for the sku.
	addChild(new NonCachingImage("generatedBarcodeSku", new AbstractReadOnlyModel<DynamicImageResource>() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public DynamicImageResource getObject() {
		DynamicImageResource dir = new DynamicImageResource() {

		    private static final long serialVersionUID = 1L;

		    @Override
		    protected byte[] getImageData(Attributes attributes) {
			return WicketUtils.generateBarCodeBytes(getString("recommendationSheet.pcServices.sku"));
		    }
		};
		dir.setFormat("image/png");
		return dir;
	    }
	}));
    }

    private List<SelectItem<String>> retrieveCheckBoxPropertiesList() {
	List<SelectItem<String>> propertiesList = new ArrayList<SelectItem<String>>();
	String[] propsArr = populatePcWowPageProeprties();
	for(int j = 0; j < propsArr.length; j++){
	    propertiesList.add(new SelectItem<String>(String.valueOf(checkBoxIdArray[j]), propsArr[j]));
	}
	return propertiesList;
    }

    private class PageInputModel implements IClusterable {

	private static final long serialVersionUID = 1L;
	List<SelectItem<String>> pcWalkOutwokringCheckOptions = new ArrayList<SelectItem<String>>();

	public PageInputModel() {

	    for(int i = 0; i < checkBoxIdArray.length; i++){
		if (recommendation != null){
		    String selVal = recommendation.getPreference(checkBoxIdArray[i]);
		    if (selVal != null && selVal.equalsIgnoreCase("true")){
			pcWalkOutwokringCheckOptions.add(new SelectItem<String>(String.valueOf(checkBoxIdArray[i]),
				selVal));
		    }
		}
	    }
	}

	public List<SelectItem<String>> getSelectedItem() {
	    return pcWalkOutwokringCheckOptions;
	}

    }

    @Override
    void clearSection(AjaxRequestTarget target) {
	clearPreferences();
	//Clear all the selected items..
	selectedInput.getSelectedItem().clear();
	target.add(listChoice);
	target.appendJavaScript("installCheckboxHanlders();");

    }

    @Override
    void saveScreenValues(final AjaxRequestTarget target) {
	clearPreferences();
	List<SelectItem<String>> selectedList = selectedInput.getSelectedItem();
	logger.debug("selectedList......**......" + selectedList);
	for(SelectItem<String> selectItem: selectedList){
	    logger.debug("selectItem.getKey()............." + selectItem.getKey());
	    if (selectItem.getKey() != null)
		recommendation.setPreference(Long.valueOf((selectItem.getKey())), "true");
	}
    }

    private void clearPreferences() {
	Long[] arr = new Long[checkBoxIdArray.length];
	int i = 0;
	for(long temp: checkBoxIdArray){
	    arr[i++] = temp;
	}
	logger.debug("arr....**...." + arr);
	recommendation.removePreferences(arr);
    }

    public String[] populatePcWowPageProeprties() {

	String[] props = new String[] {getString("recommendationSheet.pcServies.wow.text1"),
		getString("recommendationSheet.pcServies.wow.text2"),
		getString("recommendationSheet.pcServies.wow.text3"),
		getString("recommendationSheet.pcServies.wow.text4") };
	return props;
    }

}
