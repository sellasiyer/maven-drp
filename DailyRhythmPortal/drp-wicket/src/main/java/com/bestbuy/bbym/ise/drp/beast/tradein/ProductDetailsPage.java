package com.bestbuy.bbym.ise.drp.beast.tradein;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.image.NonCachingImage;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.ContextRelativeResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bestbuy.bbym.ise.drp.beast.common.OkCancelModalPanel;
import com.bestbuy.bbym.ise.drp.beast.common.StatusPage;
import com.bestbuy.bbym.ise.drp.common.PageParameterKeys;
import com.bestbuy.bbym.ise.drp.domain.TitanDevice;
import com.bestbuy.bbym.ise.drp.domain.ui.UIData;
import com.bestbuy.bbym.ise.drp.domain.ui.UIDataItem;
import com.bestbuy.bbym.ise.drp.domain.ui.UIDataList;
import com.bestbuy.bbym.ise.drp.domain.ui.UIElement;
import com.bestbuy.bbym.ise.drp.domain.ui.UIReply;

public class ProductDetailsPage extends BeastPage {

    private static Logger logger = LoggerFactory.getLogger(ProductDetailsPage.class);
    private static final long serialVersionUID = 1L;

    private UIReply uiReply;

    String inputPrice;
    private final OkCancelModalPanel zeroValueCancel;

    public ProductDetailsPage(final PageParameters parameters, UIReply uiReplyArg) {
	super(parameters);

	this.getDailyRhythmPortalSession().saveUIReply("ProductDetailsPage", uiReplyArg);

	getBorder().setPageTitle(new String(""));

	uiReply = uiReplyArg;

	UIDataItem dataItem = getValueFromDataItem("productName");
	logger.debug("Product Name : " + dataItem.getStringProp("Value", "UnKnown"));
	add(new Label("productName", new Model<String>(new String(dataItem.getStringProp("Value", "Unknown")))));

	TitanDevice titanDevice = getDailyRhythmPortalSession().getTitanDevice();
	titanDevice.setDeviceName(dataItem.getStringProp("Value", "Unknown"));

	dataItem = null;
	UIElement elem = uiReply.get("largeImage");
	if (elem != null && elem.isData()){
	    UIData data = (UIData) elem;
	    if (data.getType() == UIData.Type.ITEM){
		dataItem = (UIDataItem) data;
	    }
	}
	if (dataItem != null){
	    Image img = new NonCachingImage("productImage");
	    img.add(AttributeModifier.replace("src", dataItem.getStringProp("href", null)));
	    add(img);
	}else{
	    Image img = new NonCachingImage("productImage", new ContextRelativeResource(
		    "/images/titan_generic-product.png"));
	    img.add(AttributeModifier.replace("width", new String("170")));
	    add(img);
	}

	dataItem = getValueFromDataItem("modelNumber");
	add(new Label("modelNumber", dataItem.getStringProp("Value", "Unknown")));
	titanDevice.setModelNumber(dataItem.getStringProp("Value", "Unknown"));
	//bestPriceEstimate

	dataItem = getValueFromDataItem("estimatedBuyPrice");
	inputPrice = dataItem.getStringProp("buyPrice", "Unknown");
	int lastIndex = inputPrice.length();
	int indexOfPeriod = -1;
	char ch;
	for(int x = 0; x < lastIndex; x++){
	    ch = inputPrice.charAt(x);
	    if (ch == '.'){
		indexOfPeriod = x;
	    }
	}
	// check to see where the decimal point was found if -1 no decimal point found
	if (indexOfPeriod == -1){
	    inputPrice = inputPrice + ".00";
	}else if (indexOfPeriod == lastIndex - 2){
	    inputPrice = inputPrice + "0";
	}
	add(new Label("bestPriceEstimate", inputPrice));

	dataItem = getValueFromDataItem("productSku");
	add(new Label("sku", dataItem.getStringProp("Value", "Unknown")));

	titanDevice.setSku(dataItem.getStringProp("Value", "Unknown"));

	dataItem = getValueFromDataItem("productDescription");
	add(new Label("description", dataItem.getStringProp("Value", "Unknown")));

	titanDevice.setDescription(dataItem.getStringProp("Value", "Unknown"));
	UIDataList includedItemsList = getUIDataList("includedItems");

	List<String> includedItems = new ArrayList<String>();
	for(UIData item: includedItemsList){

	    includedItems.add(((UIDataItem) item).getStringProp("includedItem", "Unknown"));
	}
	add(new ListView<String>("options", includedItems) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void populateItem(ListItem<String> item) {
		String option = item.getModelObject();
		item.add(new Label("option", option));
	    }
	});

	zeroValueCancel = new OkCancelModalPanel("zeroValueCancel", "Cancel Trade-In", "Back") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onClose(AjaxRequestTarget target) {
		if (zeroValueCancel.isOk()){
		    getDailyRhythmPortalSession().setBestBuyCustomer(null);
		    getDailyRhythmPortalSession().setProtectionPlanList(null);
		    getDailyRhythmPortalSession().setProtectionPlanDetails(null);
		    PageParameters pp = new PageParameters();
		    pp.add(PageParameterKeys.EXIT_STATUS.getUrlParameterKey(), "cancel");
		    setResponsePage(new StatusPage(pp));
		}
	    }
	};
	String zeroValueCancelMessage = "You cannot Trade In a device that has a $0.00 value." + "\n\n"
		+ "Press Cancel Trade-In to return to the activation";
	zeroValueCancel.setMultiLineQuestion(zeroValueCancelMessage);
	zeroValueCancel.setOutputMarkupPlaceholderTag(true);
	add(zeroValueCancel);
    }

    private UIDataItem getValueFromDataItem(String itemName) {
	UIDataItem dataItem = null;
	UIElement elem = uiReply.get(itemName);
	if (elem != null && elem.isData()){
	    UIData data = (UIData) elem;
	    if (data.getType() == UIData.Type.ITEM){
		dataItem = (UIDataItem) data;
	    }
	}
	if (dataItem == null){
	    dataItem = new UIDataItem();
	}
	return dataItem;
    }

    private UIDataList getUIDataList(String itemName) {
	UIDataList dataList = null;
	UIElement elem = uiReply.get(itemName);
	if (elem != null && elem.isData()){
	    UIData data = (UIData) elem;
	    if (data.getType() == UIData.Type.LIST){
		dataList = (UIDataList) data;
	    }
	}
	if (dataList == null){
	    dataList = new UIDataList();
	}
	return dataList;
    }

    /**
     * @see com.bestbuy.bbym.ise.drp.beast.tradein.BeastPage#clearForm(org.apache.wicket.ajax.AjaxRequestTarget)
     */
    @Override
    void clearForm(AjaxRequestTarget target) {
    }

    /**
     * @see com.bestbuy.bbym.ise.drp.beast.tradein.BeastPage#goBack(org.apache.wicket.ajax.AjaxRequestTarget)
     */
    @Override
    void goBack(AjaxRequestTarget target) {
	try{
	    this.setResponsePage(new CatalogSearchPage(this.getPageParameters()));
	}catch(Exception exc){
	    logger.error(exc.getMessage(), exc);
	    displayError(getString("ProductDetailsPage.service.down.message"), target);
	    return;
	}
	return;
    }

    /**
     * @see com.bestbuy.bbym.ise.drp.beast.tradein.BeastPage#nextPage(org.apache.wicket.ajax.AjaxRequestTarget,
     *      org.apache.wicket.markup.html.form.Form)
     */
    @Override
    void nextPage(AjaxRequestTarget target, Form<?> form) {
	if (inputPrice.equals("0.00")){
	    zeroValueCancel.open(target);
	}else{
	    try{
		logger.info("continueButton onSubmit");
		logger.info("TitanDataItem : >>> " + getDailyRhythmPortalSession().getTitanDataItem().toString());

		this.getDailyRhythmPortalSession().setPriceQAQuestions(null);
		setResponsePage(new TradeInConditionPage(this.getPageParameters(), uiReply));
	    }catch(Exception exc){
		logger.error(exc.getMessage(), exc);
		displayError(getString("ProductDetailsPage.service.down.message"), target);
		return;
	    }
	}
    }
}
