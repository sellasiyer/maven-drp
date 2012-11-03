package com.bestbuy.bbym.ise.drp.beast.tradein;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.ajax.form.OnChangeAjaxBehavior;
import org.apache.wicket.datetime.markup.html.form.DateTextField;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.HiddenField;
import org.apache.wicket.markup.html.form.Radio;
import org.apache.wicket.markup.html.form.RadioGroup;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.validation.validator.StringValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bestbuy.bbym.ise.drp.beast.tradein.validator.LengthValidator;
import com.bestbuy.bbym.ise.drp.common.PageParameterKeys;
import com.bestbuy.bbym.ise.drp.domain.FourPartKey;
import com.bestbuy.bbym.ise.drp.domain.TitanDevice;
import com.bestbuy.bbym.ise.drp.domain.ui.UIData;
import com.bestbuy.bbym.ise.drp.domain.ui.UIDataItem;
import com.bestbuy.bbym.ise.drp.domain.ui.UIReply;
import com.bestbuy.bbym.ise.drp.domain.ui.UIRequest;
import com.bestbuy.bbym.ise.drp.service.UIService;
import com.bestbuy.bbym.ise.drp.util.SelectItem;

/**
 * Implements 1.2. (22B) Product Information
 * 
 * @author a915722
 * @see <a href="https://spr.bestbuy.com/ISE/Shared%20Documents/Requirements%20(User%20Stories)/Release%203.0/Trade-In%20Stories/User%20Story_B-09025%20Product%20and%20Customer%20QA.docx">User Story_B-09025 Product and Customer QA</a>
 */
public class EditProductAnswersPage extends BeastPage {

    private String dataSharingKey;
    private static Logger LOGGER = LoggerFactory.getLogger(EditProductAnswersPage.class);
    private static final long serialVersionUID = 1L;
    private TitanDevice titanDevice;

    private final DateTextField date;

    @SpringBean(name = "uiService")
    private UIService uiService;

    // private UIReply uiReply;
    private UIRequest uiRequest;

    private String selected;
    private String hiddenReceiptRequired;
    private UIReply uiReply;

    public String getHiddenReceiptRequired() {
	return hiddenReceiptRequired;
    }

    public void setHiddenReceiptRequired(String hiddenReceiptRequired) {
	this.hiddenReceiptRequired = hiddenReceiptRequired;
    }

    private final TextField<String> store;
    private TextField<String> register;
    private TextField<String> tranId;
    private final RadioGroup<String> group;

    public String getSelected() {
	return selected;
    }

    public void setSelected(String selected) {
	this.selected = selected;
    }

    public EditProductAnswersPage(final PageParameters parameters, UIReply uiReply) {
	super(parameters);

	this.uiReply = uiReply;

	this.backButton.setVisible(false);

	dataSharingKey = parameters.get(PageParameterKeys.DATA_SHARING_KEY.getUrlParameterKey()).toString();

	selected = "Yes";

	logger.debug("passed in DSK=" + dataSharingKey);

	UIDataItem titanDataItem = new UIDataItem();
	titanDataItem.setStringProp("dataSharingKey", dataSharingKey);
	getDailyRhythmPortalSession().setTitanDataItem(titanDataItem);

	titanDevice = this.getDailyRhythmPortalSession().getTitanDevice();
	if (titanDevice.getFourPartKey() == null){
	    titanDevice.setFourPartKey(new FourPartKey());
	    selected = titanDevice.isReceiptRequired()?"Yes":"No";
	}

	this.populatePageTitle(uiReply, "EditCustomerInfo", "EditProductAnswers");

	getBorder().setButtonEnabled(nextButton, true);
	UIDataItem di = ((UIDataItem) ((UIRequest) uiReply.get("EditCustomerInfo")).get("EditProductAnswers"));
	Label productName = new Label("productName", new Model<String>(di.getStringProp("name", "Device Description")));
	add(productName);

	this.readQuestions(uiReply);

	for(int i = questions.size() - 1; i >= 0; i--){
	    ProductQuestion productQuestion = questions.get(i);
	    if ("Receipt Number".equalsIgnoreCase(productQuestion.getName())){
		questions.remove(productQuestion);
	    }else if ("IMEI/MEID/Serial Number".equalsIgnoreCase(productQuestion.getName())){
		questions.remove(productQuestion);
	    }

	    if ("Manufacturer".equalsIgnoreCase(productQuestion.getqName())){
		productQuestion.addValidator(new LengthValidator(1, 50, "Manufacturer"));
	    }

	    if ("ModelNumber".equalsIgnoreCase(productQuestion.getqName())){
		productQuestion.addValidator(new LengthValidator(1, 50, "Model Number"));
	    }
	}

	ListView<ProductQuestion> listview = new ListView<ProductQuestion>("listview", questions) {

	    /**
	     * 
	     */
	    private static final long serialVersionUID = 1L;

	    protected void populateItem(ListItem<ProductQuestion> item) {
		ProductQuestion current = item.getModelObject();
		boolean focus = false;
		if (item.getIndex() == 0){
		    focus = true;
		}

		if (current.getType() == QuestionType.TEXT){
		    DynamicTextField dynamicTextField = new DynamicTextField("panel", current, focus,
			    EditProductAnswersPage.this.getBorder().getNotificationPanel());
		    item.add(dynamicTextField);

		}else{
		    item.add(new DynamicSelectField("panel", current, focus, EditProductAnswersPage.this.getBorder()
			    .getNotificationPanel()));
		}
	    }
	};
	add(listview);

	group = new RadioGroup<String>("group", new PropertyModel<String>(this, "selected"));
	group.setOutputMarkupId(true);
	group.setMarkupId("group");
	add(group);

	IModel<String> myes = new Model<String>("Yes");
	IModel<String> mno = new Model<String>("No");

	Radio<String> yes = new Radio<String>("yes", myes);
	yes.setMarkupId("yes");
	group.add(yes);

	Radio<String> no = new Radio<String>("no", mno);
	no.setMarkupId("no");
	group.add(no);
	//Set the yes or no value from titandevice;

	if (titanDevice.isReceiptRequired()){
	    group.setModel(myes);
	    titanDevice.setReceiptRequired(true);

	    group.setEnabled(false);
	    String cssClasses = String.valueOf(group.getMarkupAttributes().get("class"));
	    cssClasses.replace("disabled", "");

	    cssClasses += " disabled";
	    group.add(AttributeModifier.replace("class", cssClasses));
	}else{
	    group.setModel(mno);
	    titanDevice.setReceiptRequired(false);
	}
	//group.setModelObject(Boolean.toString(titanDevice.isReceiptRequired()));

	//titanDevice.setReceiptNumber("111111111111111");

	store = new TextField<String>("store", new PropertyModel<String>(titanDevice.getFourPartKey(), "storeId"));
	store.setMarkupId("store");
	store.setOutputMarkupId(true);

	store.add(new OnChangeAjaxBehavior() {

	    private static final long serialVersionUID = 2462233190993745889L;

	    @Override
	    protected void onUpdate(final AjaxRequestTarget target) {

		// Maybe you want to update some components here?

		// Access the updated model object:
		//final Object value = getComponent().getDefaultModelObject();
		// or:
		//final String valueAsString = ((TextField<String>) getComponent()).getModelObject();
		//titanDevice.setReceiptNumber(valueAsString);
	    }
	});

	store.add(StringValidator.lengthBetween(1, 4));
	//store.add(new PatternValidator("[0-9]+"));
	add(store);

	register = new TextField<String>("register", new PropertyModel<String>(titanDevice.getFourPartKey(),
		"workStationId"));
	register.setMarkupId("register");
	register.setOutputMarkupId(true);
	//register.add(new PatternValidator("[0-9]+"));
	add(register);

	tranId = new TextField<String>("tranId", new PropertyModel<String>(titanDevice.getFourPartKey(),
		"registerTransactionNum"));
	tranId.setMarkupId("tranId");
	tranId.setOutputMarkupId(true);
	//tranId.add(new PatternValidator("[0-9]+"));
	add(tranId);

	date = DateTextField.forDatePattern("businessDate", new PropertyModel<Date>(titanDevice.getFourPartKey(),
		"businessDate"), "MM/dd/yy");
	date.setMarkupId("businessDate");
	date.setOutputMarkupId(true);
	date.setOutputMarkupPlaceholderTag(true);
	add(date);

	date.add(new AjaxFormComponentUpdatingBehavior("onBlur") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void onUpdate(AjaxRequestTarget target) {
		target.add(date);
	    }

	});

	hiddenReceiptRequired = new String(selected);

	HiddenField<String> hiddenField = new HiddenField<String>("hiddenReceiptRequired", new PropertyModel<String>(
		this, "hiddenReceiptRequired"));
	hiddenField.setMarkupId("hiddenReceiptRequired");
	hiddenField.setOutputMarkupId(true);
	add(hiddenField);

	validateQuestions();
    }

    /**
     * @see com.bestbuy.bbym.ise.drp.beast.tradein.BeastPage#clearForm(org.apache.wicket.ajax.AjaxRequestTarget)
     */
    @Override
    void clearForm(AjaxRequestTarget target) {
	LOGGER.info("clear form page pressed");

	for(ProductQuestion current: this.questions){
	    if (current.getType() == QuestionType.DATE){
		current.setDate(new Date());
	    }else if (current.getType() == QuestionType.SELECT){
		current.setSelectOption(new SelectItem<String>("", ""));
	    }else if (current.getType() == QuestionType.TEXT){
		current.setValue("");
	    }
	}

	store.setDefaultModel(new Model(""));
	tranId.setDefaultModel(new Model(""));
	register.setDefaultModel(new Model(""));
	date.setDefaultModel(new Model(""));

	getBorder().setButtonEnabled(nextButton, false, target);

	target.add(this.getBorder().getForm());

	//target.appendJavaScript("initEditProductAnswersPage();");
	target.appendJavaScript("bindHotKeys();");
    }

    /**
     * @see com.bestbuy.bbym.ise.drp.beast.tradein.BeastPage#goBack(org.apache.wicket.ajax.AjaxRequestTarget)
     */
    @Override
    void goBack(AjaxRequestTarget target) {
	//Remove the unnecessary page
	//	this.getPageParameters().add("backbutton", "backbutton");
	//	this.setResponsePage(new EditProductInfoPage(this.getPageParameters()));

    }

    private int fieldValidator(String label, String value) {
	if (StringUtils.isEmpty(value)){
	    this.error(label + " is required.");
	    return 1;
	}

	if (NumberUtils.isDigits(value) == false){
	    this.error(label + " is invalid.");
	    return 1;
	}
	return 0;
    }

    /**
     * @see com.bestbuy.bbym.ise.drp.beast.tradein.BeastPage#nextPage(org.apache.wicket.ajax.AjaxRequestTarget,
     *      org.apache.wicket.markup.html.form.Form)
     */
    @Override
    void nextPage(AjaxRequestTarget target, Form<?> form) {
	if ("yes".equalsIgnoreCase(hiddenReceiptRequired)){
	    titanDevice.setReceiptRequired(true);
	    int cnt = 0;
	    cnt = cnt + fieldValidator("Store", titanDevice.getFourPartKey().getStoreId());
	    cnt = cnt + fieldValidator("Register", titanDevice.getFourPartKey().getWorkStationId());
	    cnt = cnt + fieldValidator("Tran ID", titanDevice.getFourPartKey().getRegisterTransactionNum());

	    if (titanDevice.getFourPartKey().getBusinessDate() == null){
		this.error("Date is required.");
		cnt++;
	    }

	    if (cnt != 0){

		this.getBorder().getNotificationPanel().refresh(target);
		return;
	    }

	}else{
	    titanDevice.setReceiptRequired(false);
	}

	try{
	    Iterator<UIData> iterator = list.iterator();
	    while(iterator.hasNext()){
		UIData current = iterator.next();
		if (current instanceof UIDataItem){
		    UIDataItem currentUIDataItem = (UIDataItem) current;

		    String qName = currentUIDataItem.getStringProp("Name", null);

		    if (titanDevice.isReceiptRequired() & "ReceiptNumber".equalsIgnoreCase(qName)){
			logger.debug("getting UIDataItem qName : " + qName);
			DateFormat formatter = new SimpleDateFormat("MMddyy");

			String dtString = formatter.format(titanDevice.getFourPartKey().getBusinessDate());

			String receiptNumber = titanDevice.getFourPartKey().getStoreId()
				+ titanDevice.getFourPartKey().getRegisterTransactionNum()
				+ titanDevice.getFourPartKey().getWorkStationId() + dtString;
			logger.debug("Converted ReceiptNumber : " + receiptNumber);
			receiptNumber = StringUtils.remove(receiptNumber, "null");
			logger.debug("Converted without null values ReceiptNumber : " + receiptNumber);

			currentUIDataItem.setStringProp("Value", receiptNumber);

			//currentUIDataItem.setStringProp("userId", getDailyRhythmPortalSession().getDrpUser().getUserId());
		    }else if ("IMEI".equalsIgnoreCase(current.getName())){
			logger.debug("getting UIDataItem qName : " + qName);
			currentUIDataItem.setStringProp("Value", titanDevice.getSerialNumber());
			//currentUIDataItem.setStringProp("userId", getDailyRhythmPortalSession().getDrpUser().getUserId());
		    }
		}
	    }

	    for(ProductQuestion current: questions){
		if (current.getType() == QuestionType.TEXT){
		    current.getUiDataItem().setStringProp("Value", current.getValue());
		    current.getUiDataItem().setStringProp("userId",
			    getDailyRhythmPortalSession().getDrpUser().getUserId());
		}else if (current.getType() == QuestionType.SELECT){
		    current.getUiDataItem().setStringProp("Value", current.getSelectOption().getKey());
		    current.getUiDataItem().setStringProp("userId",
			    getDailyRhythmPortalSession().getDrpUser().getUserId());

		}
	    }

	    this.getDailyRhythmPortalSession().saveUIReply(this.getClass().getName(), uiReply);

	    uiRequest = (UIRequest) uiReply.get("EditCustomerInfo");
	    UIDataItem di = (UIDataItem) uiRequest.get("EditProductAnswers");
	    di.setStringProp("userId", getDailyRhythmPortalSession().getDrpUser().getUserId());

	    uiReply = uiService.processUIRequest(uiRequest);

	    this.setResponsePage(new EditCustomerBasicInfoPage(this.getPageParameters(), uiReply));
	}catch(Exception exc){
	    LOGGER.error(exc.getMessage());
	    this.displayError(this.getString("EditProductAnswersPage.service.down.message"), target);
	    return;
	}
    }
}
