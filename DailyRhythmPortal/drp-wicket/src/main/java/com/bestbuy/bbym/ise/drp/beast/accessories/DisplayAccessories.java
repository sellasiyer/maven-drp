package com.bestbuy.bbym.ise.drp.beast.accessories;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.image.NonCachingImage;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.ContextRelativeResource;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bestbuy.bbym.ise.domain.Sku;
import com.bestbuy.bbym.ise.drp.beast.common.BaseBeastPage;
import com.bestbuy.bbym.ise.drp.common.PageParameterKeys;
import com.bestbuy.bbym.ise.drp.domain.ui.UIImage;
import com.bestbuy.bbym.ise.drp.service.AccessoryService;
import com.bestbuy.bbym.ise.drp.util.PhoneFormatter;
import com.bestbuy.bbym.ise.exception.IseBusinessException;
import com.bestbuy.bbym.ise.exception.ServiceException;

/**
 * Displays accessories related to a provided SKU.
 * 
 * @see <a href="https://spr.bestbuy.com/ISE/Shared%20Documents/Requirements%20(User%20Stories)/Release%203.0/User%20Story_B-11349%20Display%20Accessory%20window%20in%20Beast.docx">UserS Story_B-11349 Display Accessory window in Beast</a>
 */
public class DisplayAccessories extends BaseBeastPage {

    private static final long serialVersionUID = 1L;
    private static Logger logger = LoggerFactory.getLogger(DisplayAccessories.class);

    @SpringBean(name = "accessoryService")
    private AccessoryService accessoryService;

    private WebMarkupContainer accessoryOneListContainer, accessoryTwoListContainer, accessoryThreeListContainer,
	    accessoryFourListContainer, accessoryFiveListContainer;
    private WebMarkupContainer noAccessoryListContainer, noDeviceContainer;
    private String noResultsMessage = "No accessories could be found for this device";
    private List<Sku> accessoryOneRowList, accessoryTwoRowList, accessoryThreeRowList, accessoryFourRowList,
	    accessoryFiveRowList;
    private List<Sku> deviceSkuList;
    private Image deviceOneImage;
    private Label deviceOneLabel, deviceOneButtonLabel, deviceTwoButtonLabel, deviceThreeButtonLabel,
	    deviceFourButtonLabel, deviceFiveButtonLabel;
    private UIImage uiOneImage;
    private AjaxLink<Object> deviceOneButton, deviceTwoButton, deviceThreeButton, deviceFourButton, deviceFiveButton;
    private Sku deviceOneTitle;
    private Map<String, Sku> serviceLineSkuDataMap;
    private String NO_DEVICE_IMAGE = "No Image Available";
    private Map<String, String> skuLineMap;

    public enum LineClickedState {
	LINE_ONE, LINE_TWO, LINE_THREE, LINE_FOUR, LINE_FIVE
    }

    LineClickedState lineClickedState = LineClickedState.LINE_ONE;

    private final WebMarkupContainer deviceOneSelection, deviceTwoSelection, deviceThreeSelection, deviceFourSelection,
	    deviceFiveSelection;

    public DisplayAccessories(final PageParameters parameters) {
	super(parameters);

	PhoneFormatter<String> formatter = new PhoneFormatter<String>();
	String devicePhoneNumberModel = "setup";
	String beastRequest = parameters.get(PageParameterKeys.DATA.getUrlParameterKey()).toString();
	serviceLineSkuDataMap = new HashMap<String, Sku>();

	String storeId = parameters.get(PageParameterKeys.STORE_ID.getUrlParameterKey()).toString();

	skuLineMap = new HashMap<String, String>();
	String[] skuAndLine = beastRequest.split(",");
	for(String str: skuAndLine){
	    String skuText = str.substring(1, (str.length() - 1)).split("[|]")[0];
	    String phoneNumberText = str.substring(0, (str.length() - 1)).split("[|]")[1];
	    skuLineMap.put(skuText, phoneNumberText);
	    Sku sku = new Sku();
	    sku.setSku(skuText);
	    serviceLineSkuDataMap.put(phoneNumberText, sku);
	}

	List<String> skuList = new ArrayList<String>(skuLineMap.keySet());
	deviceSkuList = new ArrayList<Sku>();
	for(String sku: skuList){
	    Sku accessorySku = new Sku();
	    accessorySku.setSku(sku);
	    deviceSkuList.add(accessorySku);
	}

	boolean skuServiceResult = true;
	try{
	    serviceLineSkuDataMap = accessoryService.getAccessories(storeId, serviceLineSkuDataMap);
	}catch(ServiceException se){
	    String message = "An unexpected exception occured while attempting to get accessory list";
	    logger.error(message, se);
	    noResultsMessage = se.getMessage();
	    skuServiceResult = false;
	}catch(IseBusinessException be){
	    String message = "An unexpected exception occured while attempting to get accessory list";
	    // TODO Shouldn't we display something to the user here?
	    logger.error(message);
	}

	deviceOneTitle = new Sku();
	uiOneImage = new UIImage();
	accessoryOneRowList = new ArrayList<Sku>();
	accessoryTwoRowList = new ArrayList<Sku>();
	accessoryThreeRowList = new ArrayList<Sku>();
	accessoryFourRowList = new ArrayList<Sku>();
	accessoryFiveRowList = new ArrayList<Sku>();

	if (deviceSkuList == null || deviceSkuList.isEmpty()){
	    String message = "DisplayAccessories : deviceSkuList object returned from SKUService is null ";
	    logger.error(message);
	}

	// display first device details & accessory list in model
	if (deviceSkuList != null && !deviceSkuList.isEmpty() && !serviceLineSkuDataMap.isEmpty()){
	    accessoryOneRowList = serviceLineSkuDataMap.get(skuLineMap.get(deviceSkuList.get(0).getSku())).getSkuList();
	    if (deviceSkuList.size() > 1)
		accessoryTwoRowList = serviceLineSkuDataMap.get(skuLineMap.get(deviceSkuList.get(1).getSku()))
			.getSkuList();
	    if (deviceSkuList.size() > 2)
		accessoryThreeRowList = serviceLineSkuDataMap.get(skuLineMap.get(deviceSkuList.get(2).getSku()))
			.getSkuList();
	    if (deviceSkuList.size() > 3)
		accessoryFourRowList = serviceLineSkuDataMap.get(skuLineMap.get(deviceSkuList.get(3).getSku()))
			.getSkuList();
	    if (deviceSkuList.size() > 4)
		accessoryFiveRowList = serviceLineSkuDataMap.get(skuLineMap.get(deviceSkuList.get(4).getSku()))
			.getSkuList();
	}

	accessoryOneListContainer = new WebMarkupContainer("accessoryOneListContainer") {

	    private static final long serialVersionUID = 1L;
	};
	accessoryOneListContainer.setMarkupId("accessoryOneListContainer");

	accessoryOneListContainer.setOutputMarkupPlaceholderTag(true);
	accessoryOneListContainer.setVisible(true);
	add(accessoryOneListContainer);

	final ListView<Sku> deviceOneAccessoryListView = new AccessoryListView("deviceOneAccessoryListView",
		new PropertyModel<List<Sku>>(this, "accessoryOneRowList"), "accessoryOneName",
		"accessoryOneDescription", "accOneImage", "accessoryOnePrice");
	accessoryOneListContainer.add(deviceOneAccessoryListView);

	// TWO
	accessoryTwoListContainer = new WebMarkupContainer("accessoryTwoListContainer") {

	    private static final long serialVersionUID = 1L;
	};
	accessoryTwoListContainer.setMarkupId("accessoryTwoListContainer");
	accessoryTwoListContainer.setOutputMarkupPlaceholderTag(true);
	accessoryTwoListContainer.setVisible(false);
	add(accessoryTwoListContainer);

	final ListView<Sku> deviceTwoAccessoryListView = new AccessoryListView("deviceTwoAccessoryListView",
		new PropertyModel<List<Sku>>(this, "accessoryTwoRowList"), "accessoryTwoName",
		"accessoryTwoDescription", "accTwoImage", "accessoryTwoPrice");

	accessoryTwoListContainer.add(deviceTwoAccessoryListView);

	accessoryThreeListContainer = new WebMarkupContainer("accessoryThreeListContainer") {

	    private static final long serialVersionUID = 1L;
	};
	accessoryThreeListContainer.setMarkupId("accessoryThreeListContainer");
	accessoryThreeListContainer.setOutputMarkupPlaceholderTag(true);
	accessoryThreeListContainer.setVisible(false);
	add(accessoryThreeListContainer);

	final ListView<Sku> deviceThreeAccessoryListView = new AccessoryListView("deviceThreeAccessoryListView",
		new PropertyModel<List<Sku>>(this, "accessoryThreeRowList"), "accessoryThreeName",
		"accessoryThreeDescription", "accThreeImage", "accessoryThreePrice");

	accessoryThreeListContainer.add(deviceThreeAccessoryListView);
	accessoryFourListContainer = new WebMarkupContainer("accessoryFourListContainer") {

	    private static final long serialVersionUID = 1L;
	};
	accessoryFourListContainer.setMarkupId("accessoryFourListContainer");
	accessoryFourListContainer.setOutputMarkupPlaceholderTag(true);
	accessoryFourListContainer.setVisible(false);
	add(accessoryFourListContainer);

	final ListView<Sku> deviceFourAccessoryListView = new AccessoryListView("deviceFourAccessoryListView",
		new PropertyModel<List<Sku>>(this, "accessoryFourRowList"), "accessoryFourName",
		"accessoryFourDescription", "accFourImage", "accessoryFourPrice");

	accessoryFourListContainer.add(deviceFourAccessoryListView);

	accessoryFiveListContainer = new WebMarkupContainer("accessoryFiveListContainer") {

	    private static final long serialVersionUID = 1L;
	};
	accessoryFiveListContainer.setMarkupId("accessoryFiveListContainer");
	accessoryFiveListContainer.setOutputMarkupPlaceholderTag(true);
	accessoryFiveListContainer.setVisible(false);
	add(accessoryFiveListContainer);

	final ListView<Sku> deviceFiveAccessoryListView = new AccessoryListView("deviceFiveAccessoryListView",
		new PropertyModel<List<Sku>>(this, "accessoryFiveRowList"), "accessoryFiveName",
		"accessoryFiveDescription", "accFiveImage", "accessoryFivePrice");

	accessoryFiveListContainer.add(deviceFiveAccessoryListView);

	noAccessoryListContainer = new WebMarkupContainer("noAccessoryListContainer") {

	    private static final long serialVersionUID = 1L;
	};
	noAccessoryListContainer.setOutputMarkupPlaceholderTag(true);
	noAccessoryListContainer.setOutputMarkupId(true);

	add(noAccessoryListContainer);

	final Label noAccessoryListLabel = new Label("noAccessoryListLabel", new Model<String>() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public String getObject() {
		return noResultsMessage;
	    }
	});
	noAccessoryListLabel.setOutputMarkupPlaceholderTag(true);
	noAccessoryListLabel.setOutputMarkupId(true);
	noAccessoryListLabel.setVisible(true);
	noAccessoryListContainer.add(noAccessoryListLabel);

	noDeviceContainer = new WebMarkupContainer("noDeviceContainer") {

	    private static final long serialVersionUID = 1L;

	};
	noDeviceContainer.setOutputMarkupPlaceholderTag(true);
	noDeviceContainer.setVisible(false);
	add(noDeviceContainer);

	if (skuServiceResult && !serviceLineSkuDataMap.isEmpty() && accessoryOneRowList != null){

	    noAccessoryListContainer.setVisible(false);
	    accessoryOneListContainer.setVisible(true);

	}else{
	    noAccessoryListContainer.setVisible(true);
	    accessoryOneListContainer.setVisible(false);
	}

	deviceOneButton = new AjaxLink<Object>("deviceOneButton") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onClick(AjaxRequestTarget target) {
		if (target != null){
		    logger.info("Display Accessories : Device One clicked .....");
		    lineClickedState = LineClickedState.LINE_ONE;
		    if (accessoryOneRowList == null){
			target.add(noAccessoryListContainer);
			noAccessoryListContainer.setVisible(true);
			setupAccessoryDisplay(target, -1);
		    }else
			setupAccessoryDisplay(target, 0);
		}
	    }
	};
	deviceOneButton.setOutputMarkupPlaceholderTag(true);

	deviceOneSelection = new WebMarkupContainer("deviceOneSelection") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public boolean isVisible() {
		//do nothing
		return true;
	    }
	};
	deviceOneSelection.setOutputMarkupPlaceholderTag(true);
	deviceOneSelection.add(deviceOneButton);
	deviceOneSelection.add(AttributeModifier.append("class", new Model<String>() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public String getObject() {
		if (lineClickedState == LineClickedState.LINE_ONE){
		    return "selected";
		}
		return "";
	    }
	}));
	add(deviceOneSelection);

	if (deviceSkuList.size() > 0){
	    devicePhoneNumberModel = formatter.format(skuLineMap.get(deviceSkuList.get(0).getSku()));
	    deviceOneButtonLabel = new Label("deviceOneButtonLabel", devicePhoneNumberModel);
	    deviceOneButton.add(deviceOneButtonLabel);
	}

	deviceTwoButton = new AjaxLink<Object>("deviceTwoButton") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onClick(AjaxRequestTarget target) {
		if (target != null){
		    logger.info("Display Accessories : Device two clicked .....");
		    lineClickedState = LineClickedState.LINE_TWO;
		    if (accessoryTwoRowList == null){
			target.add(noAccessoryListContainer);
			noAccessoryListContainer.setVisible(true);
			setupAccessoryDisplay(target, -1);
		    }else
			setupAccessoryDisplay(target, 1);
		}
	    }
	};
	deviceTwoButton.setOutputMarkupPlaceholderTag(true);

	deviceTwoSelection = new WebMarkupContainer("deviceTwoSelection") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public boolean isVisible() {
		//do nothing
		return true;
	    }
	};
	deviceTwoSelection.setOutputMarkupPlaceholderTag(true);
	deviceTwoSelection.add(deviceTwoButton);
	deviceTwoSelection.add(AttributeModifier.append("class", new Model<String>() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public String getObject() {
		if (lineClickedState == LineClickedState.LINE_TWO){
		    return "selected";
		}
		return "";
	    }
	}));
	add(deviceTwoSelection);

	if (deviceSkuList.size() > 1){
	    devicePhoneNumberModel = "";
	    devicePhoneNumberModel = formatter.format(skuLineMap.get(deviceSkuList.get(1).getSku()));
	    deviceTwoButtonLabel = new Label("deviceTwoButtonLabel", devicePhoneNumberModel);
	    deviceTwoButton.add(deviceTwoButtonLabel);
	}

	deviceThreeButton = new AjaxLink<Object>("deviceThreeButton") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onClick(AjaxRequestTarget target) {
		if (target != null){
		    logger.info("Display Accessories : Device Three clicked .....");
		    lineClickedState = LineClickedState.LINE_THREE;
		    if (accessoryThreeRowList == null){
			target.add(noAccessoryListContainer);
			noAccessoryListContainer.setVisible(true);
			setupAccessoryDisplay(target, -1);
		    }else
			setupAccessoryDisplay(target, 2);
		}
	    }
	};
	deviceThreeButton.setOutputMarkupPlaceholderTag(true);

	deviceThreeSelection = new WebMarkupContainer("deviceThreeSelection") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public boolean isVisible() {
		//do nothing
		return true;
	    }
	};
	deviceThreeSelection.setOutputMarkupPlaceholderTag(true);
	deviceThreeSelection.add(deviceThreeButton);
	deviceThreeSelection.add(AttributeModifier.append("class", new Model<String>() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public String getObject() {
		if (lineClickedState == LineClickedState.LINE_THREE){
		    return "selected";
		}
		return "";
	    }
	}));

	deviceThreeSelection.add(deviceThreeButton);
	add(deviceThreeSelection);

	if (deviceSkuList.size() > 2){
	    devicePhoneNumberModel = "";
	    devicePhoneNumberModel = formatter.format(skuLineMap.get(deviceSkuList.get(2).getSku()));
	    deviceThreeButtonLabel = new Label("deviceThreeButtonLabel", devicePhoneNumberModel);
	    deviceThreeButton.add(deviceThreeButtonLabel);
	}

	deviceFourButton = new AjaxLink<Object>("deviceFourButton") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onClick(AjaxRequestTarget target) {
		if (target != null){
		    logger.info("Display Accessories : Device Four clicked .....");
		    lineClickedState = LineClickedState.LINE_FOUR;
		    if (accessoryFourRowList == null){
			target.add(noAccessoryListContainer);
			noAccessoryListContainer.setVisible(true);
			setupAccessoryDisplay(target, -1);
		    }else
			setupAccessoryDisplay(target, 3);
		}
	    }
	};
	deviceFourButton.setOutputMarkupPlaceholderTag(true);

	deviceFourSelection = new WebMarkupContainer("deviceFourSelection") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public boolean isVisible() {
		//do nothing
		return true;
	    }
	};
	deviceFourSelection.setOutputMarkupPlaceholderTag(true);
	deviceFourSelection.add(deviceFourButton);
	deviceFourSelection.add(AttributeModifier.append("class", new Model<String>() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public String getObject() {
		if (lineClickedState == LineClickedState.LINE_FOUR){
		    return "selected";
		}
		return "";
	    }
	}));
	add(deviceFourSelection);

	if (deviceSkuList.size() > 3){
	    devicePhoneNumberModel = "";
	    devicePhoneNumberModel = formatter.format(skuLineMap.get(deviceSkuList.get(3).getSku()));
	    deviceFourButtonLabel = new Label("deviceFourButtonLabel", devicePhoneNumberModel);
	    deviceFourButton.add(deviceFourButtonLabel);
	}

	deviceFiveButton = new AjaxLink<Object>("deviceFiveButton") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onClick(AjaxRequestTarget target) {
		if (target != null){
		    logger.info("Display Accessories : Device Five clicked .....");
		    lineClickedState = LineClickedState.LINE_FIVE;
		    if (accessoryFiveRowList == null){
			target.add(noAccessoryListContainer);
			noAccessoryListContainer.setVisible(true);
			setupAccessoryDisplay(target, -1);
		    }else
			setupAccessoryDisplay(target, 4);
		}
	    }
	};
	deviceFiveButton.setOutputMarkupPlaceholderTag(true);

	deviceFiveSelection = new WebMarkupContainer("deviceFiveSelection") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public boolean isVisible() {
		//do nothing
		return true;
	    }
	};
	deviceFiveSelection.setOutputMarkupPlaceholderTag(true);
	deviceFiveSelection.add(deviceFiveButton);
	deviceFiveSelection.add(AttributeModifier.append("class", new Model<String>() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public String getObject() {
		if (lineClickedState == LineClickedState.LINE_FIVE){
		    return "selected";
		}
		return "";
	    }
	}));
	add(deviceFiveSelection);

	if (deviceSkuList.size() > 4){
	    devicePhoneNumberModel = "";
	    devicePhoneNumberModel = formatter.format(skuLineMap.get(deviceSkuList.get(4).getSku()));
	    deviceFiveButtonLabel = new Label("deviceFiveButtonLabel", devicePhoneNumberModel);
	    deviceFiveButton.add(deviceFiveButtonLabel);
	    setupDeviceContainers(deviceSkuList.size());
	}

	setupDeviceContainers(deviceSkuList.size());

	deviceOneImage = new NonCachingImage("deviceOneImage");
	CompoundPropertyModel<UIImage> imageModel = new CompoundPropertyModel<UIImage>(uiOneImage);
	deviceOneImage.add(new AttributeModifier("src", true, imageModel.bind("url")));
	deviceOneImage.setOutputMarkupId(true);
	deviceOneImage.setVisible(true);
	add(deviceOneImage);

	if (!serviceLineSkuDataMap.isEmpty()){
	    if (serviceLineSkuDataMap.get(skuLineMap.get(deviceSkuList.get(0).getSku())).getName() != null)
		deviceOneTitle.setName((new String(serviceLineSkuDataMap.get(
			skuLineMap.get(deviceSkuList.get(0).getSku())).getName())));
	    else
		deviceOneTitle.setName(new String(NO_DEVICE_IMAGE));
	}else
	    deviceOneTitle.setName(new String(NO_DEVICE_IMAGE));

	CompoundPropertyModel<Sku> deviceNameModel = new CompoundPropertyModel<Sku>(deviceOneTitle);
	deviceOneLabel = new Label("deviceOneName", deviceNameModel.bind("name"));
	deviceOneLabel.setOutputMarkupId(true);
	add(deviceOneLabel);

	if (!serviceLineSkuDataMap.isEmpty()){
	    if (serviceLineSkuDataMap.get(skuLineMap.get(deviceSkuList.get(0).getSku())).getImage() != null)
		uiOneImage.setUrl(new String(serviceLineSkuDataMap.get(skuLineMap.get(deviceSkuList.get(0).getSku()))
			.getImage()));
	    else{
		noDeviceContainer.setVisible(true);
		deviceOneImage.setVisible(false);
		deviceOneLabel.setVisible(false);
	    }
	}else{
	    noDeviceContainer.setVisible(true);
	    deviceOneImage.setVisible(false);
	    deviceOneLabel.setVisible(false);
	}

    }

    private void setupDeviceContainers(int size) {
	deviceOneButton.setVisible(size >= 1);
	deviceTwoButton.setVisible(size >= 2);
	deviceThreeButton.setVisible(size >= 3);
	deviceFourButton.setVisible(size >= 4);
	deviceFiveButton.setVisible(size >= 5);
    }

    private void setupAccessoryDisplay(AjaxRequestTarget target, int lineNumber) {
	accessoryOneListContainer.setVisible(false);
	accessoryTwoListContainer.setVisible(false);
	accessoryThreeListContainer.setVisible(false);
	accessoryFourListContainer.setVisible(false);
	accessoryFiveListContainer.setVisible(false);
	noAccessoryListContainer.setVisible(false);
	target.add(deviceOneSelection);
	target.add(deviceTwoSelection);
	target.add(deviceThreeSelection);
	target.add(deviceFourSelection);
	target.add(deviceFiveSelection);
	target.add(accessoryOneListContainer);
	target.add(accessoryTwoListContainer);
	target.add(accessoryThreeListContainer);
	target.add(accessoryFourListContainer);
	target.add(accessoryFiveListContainer);
	target.add(noAccessoryListContainer);
	target.add(deviceOneLabel);
	target.add(deviceOneImage);
	target.appendJavaScript("setupAccessoryList();");

	switch (lineNumber) {
	    case 0:
		accessoryOneListContainer.setVisible(true);
		break;
	    case 1:
		accessoryTwoListContainer.setVisible(true);
		break;
	    case 2:
		accessoryThreeListContainer.setVisible(true);
		break;
	    case 3:
		accessoryFourListContainer.setVisible(true);
		break;
	    case 4:
		accessoryFiveListContainer.setVisible(true);
		break;
	    default:
		noAccessoryListContainer.setVisible(true);
	}
	if (lineNumber < 5 && lineNumber >= 0){

	    if (!serviceLineSkuDataMap.isEmpty()
		    && serviceLineSkuDataMap.get(skuLineMap.get(deviceSkuList.get(lineNumber).getSku())).getImage() != null)
		uiOneImage.setUrl(new String(serviceLineSkuDataMap.get(
			skuLineMap.get(deviceSkuList.get(lineNumber).getSku())).getImage()));
	    else{
		target.add(noDeviceContainer);
		noDeviceContainer.setVisible(true);
		deviceOneImage.setVisible(false);
		deviceOneLabel.setVisible(false);
	    }
	    if (!serviceLineSkuDataMap.isEmpty()
		    && serviceLineSkuDataMap.get(skuLineMap.get(deviceSkuList.get(lineNumber).getSku())).getName() != null){
		deviceOneTitle.setName((new String(serviceLineSkuDataMap.get(
			skuLineMap.get(deviceSkuList.get(lineNumber).getSku())).getName())));
	    }else
		deviceOneTitle.setLongDescription(NO_DEVICE_IMAGE);
	}
    }

    class AccessoryListView extends ListView<Sku> {

	private static final long serialVersionUID = 1L;

	private final String nameLabelId;
	private final String descriptionLabelId;
	private final String imageLabelId;
	private final String priceLabelId;

	public AccessoryListView(String id, PropertyModel<List<Sku>> model, String nameLabelId,
		String descriptionLabelId, String imageLabelId, String priceLabelId) {
	    super(id, model);
	    setOutputMarkupPlaceholderTag(true);
	    this.nameLabelId = nameLabelId;
	    this.descriptionLabelId = descriptionLabelId;
	    this.imageLabelId = imageLabelId;
	    this.priceLabelId = priceLabelId;
	}

	@Override
	protected void populateItem(ListItem<Sku> listItem) {
	    final Sku rowSku = listItem.getModelObject();
	    listItem.add(new Label(nameLabelId, rowSku.getName()));
	    listItem.add(new Label(descriptionLabelId, rowSku.getLongDescription()));
	    String uiImage = rowSku.getImage();
	    Image img;
	    if (uiImage == null){
		img = new Image(imageLabelId, new ContextRelativeResource("/images/picture_empty.png"));
	    }else{
		img = new NonCachingImage(imageLabelId);
		img.add(new AttributeModifier("src", true, new Model(uiImage)));
		img.add(new AttributeModifier("width", new Model("190")));
	    }
	    listItem.add(img);
	    BigDecimal price = (BigDecimal) rowSku.getRegularPrice();
	    listItem.add(new Label(priceLabelId, "$" + price.toString()));
	}

    }

}
