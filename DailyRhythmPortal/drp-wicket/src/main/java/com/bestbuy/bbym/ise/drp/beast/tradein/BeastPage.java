package com.bestbuy.bbym.ise.drp.beast.tradein;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bestbuy.bbym.ise.drp.beast.common.BaseBeastPage;
import com.bestbuy.bbym.ise.drp.beast.common.OkCancelModalPanel;
import com.bestbuy.bbym.ise.drp.domain.ui.UIData;
import com.bestbuy.bbym.ise.drp.domain.ui.UIDataItem;
import com.bestbuy.bbym.ise.drp.domain.ui.UIDataList;
import com.bestbuy.bbym.ise.drp.domain.ui.UIReply;
import com.bestbuy.bbym.ise.drp.domain.ui.UIRequest;
import com.bestbuy.bbym.ise.drp.util.SelectItem;

public abstract class BeastPage extends BaseBeastPage {

    private BeastBorder border;
    private final OkCancelModalPanel clearForm;
    protected final AjaxSubmitLink nextButton;
    protected final AjaxSubmitLink backButton;
    protected final AjaxSubmitLink clearButton;

    protected UIDataList list = null;
    protected List<ProductQuestion> questions = new ArrayList<ProductQuestion>();

    protected static Logger logger = LoggerFactory.getLogger(BeastPage.class);

    public BeastBorder getBorder() {
	return border;
    }

    /**
     * Checks questions values and if any question answer is empty disable F2 button.
     */
    // TODO Refactor this, it's not needed for every BEAST page!
    protected void validateQuestions() {
	for(ProductQuestion current: this.questions){
	    if (current.getType() == QuestionType.DATE){
		if (current.getDate() == null){
		    getBorder().setButtonEnabled(nextButton, false);
		    break;
		}
	    }else if (current.getType() == QuestionType.SELECT){
		if (current.getSelectOption() == null){
		    getBorder().setButtonEnabled(nextButton, false);
		    break;
		}

	    }else if (current.getType() == QuestionType.TEXT){
		getBorder().setButtonEnabled(nextButton, false);
		break;
	    }

	}

    }

    // TODO Refactor this, it's not needed for every BEAST page!
    protected ProductQuestion getProductQuestionByQname(String qname) {
	if (StringUtils.isEmpty(qname)){
	    return null;
	}

	for(ProductQuestion current: questions){
	    if (StringUtils.isEmpty(current.getqName())){
		continue;
	    }
	    if (qname.equalsIgnoreCase(current.getqName())){
		return current;
	    }
	}
	return null;
    }

    public void displayError(String message, final AjaxRequestTarget target) {
	getBorder().displayError(message, target);
    }

    public BeastPage(final PageParameters parameters) {
	super(parameters);

	if (border == null){

	    // Create border and add it to the page
	    border = new BeastBorder("border");
	    super.add(border);
	}

	clearForm = new OkCancelModalPanel("clearForm", "Yes", "No") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onClose(AjaxRequestTarget target) {
		if (clearForm.isOk()){
		    clearForm(target);
		}
	    }
	};
	clearForm.setOutputMarkupPlaceholderTag(true);
	border.addToBorder(clearForm);

	getBorder().getLoadingModalPanel().setMessage(this.getBorder().getProcessingMessage());

	nextButton = new AjaxSubmitLink("nextButton", border.getForm()) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
		if (!getBorder().getLoadingModalPanel().isOpen()){

		    getBorder().getLoadingModalPanel().open(target);
		    target.appendJavaScript("handleButtonState(false, '#nextButton');clickButton('#nextButton');");
		    return;
		}

		nextPage(target, form);

		getBorder().getLoadingModalPanel().close(target);
		target.appendJavaScript("handleButtonState(true, '#nextButton');wicketBehavior('message');");

	    }

	    @Override
	    protected void onError(AjaxRequestTarget target, Form<?> form) {

		if (BeastPage.this.getBorder().getNotificationPanel().refresh(target) == false){
		    displayError(this.getString("continue.form.error"), target);
		}
	    }
	};
	nextButton.setMarkupId("nextButton");
	nextButton.setOutputMarkupPlaceholderTag(true);
	border.getForm().add(nextButton);

	clearButton = new AjaxSubmitLink("clearButton", border.getForm()) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
		if (!clearForm.isOpen()){
		    clearForm.setQuestion("Are you sure you want to clear the form ?");
		    clearForm.open(target);
		}
	    }

	    @Override
	    protected void onError(AjaxRequestTarget target, Form<?> form) {
		onSubmit(target, form);
	    }
	};
	clearButton.setMarkupId("clearButton");
	clearButton.setOutputMarkupPlaceholderTag(true);
	border.getForm().add(clearButton);

	backButton = new AjaxSubmitLink("backButton", border.getForm()) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void onSubmit(final AjaxRequestTarget target, Form<?> form) {
		goBack(target);
	    }

	    @Override
	    protected void onError(AjaxRequestTarget target, Form<?> form) {
		//displayError(this.getString("back.form.error"), target);
		onSubmit(target, form);
	    }
	};
	backButton.setMarkupId("backButton");
	backButton.setOutputMarkupPlaceholderTag(true);
	border.getForm().add(backButton);

	//hook into behavior to enable/disable nextButton from javascript
	final AbstractDefaultAjaxBehavior nextButtonEnableBehavior = new AbstractDefaultAjaxBehavior() {

	    private static final long serialVersionUID = 1L;

	    /**
	     * @see org.apache.wicket.ajax.AbstractDefaultAjaxBehavior#respond(org.apache.wicket.ajax.AjaxRequestTarget)
	     */
	    @Override
	    protected void respond(AjaxRequestTarget target) {

		if (RequestCycle.get().getRequest().getRequestParameters().getParameterValue("msg") != null){
		    String msg = RequestCycle.get().getRequest().getRequestParameters().getParameterValue("msg")
			    .toString();
		    String returnfocusid = RequestCycle.get().getRequest().getRequestParameters().getParameterValue(
			    "returnfocusid").toString();

		    logger.debug("in displayPopupErrorMessage status = " + msg);

		    if (StringUtils.isNotEmpty(msg)){

			displayError(msg, target);
			if (StringUtils.isNotEmpty(returnfocusid)){
			    BeastPage.this.getBorder().getMessageModalPanel().setReturnFocusId(returnfocusid);
			}

			return;
		    }

		}

		// is true if string equals "true"
		if (RequestCycle.get().getRequest().getRequestParameters().getParameterValue("enabled") != null){
		    boolean enabled = RequestCycle.get().getRequest().getRequestParameters().getParameterValue(
			    "enabled").toBoolean();
		    logger.debug("in nextButtonEnableWicketBehavior status = " + enabled);

		    getBorder().setButtonEnabled(nextButton, enabled, target);
		    return;
		}

	    }

	    /**
	     * @see org.apache.wicket.ajax.AbstractDefaultAjaxBehavior#renderHead(org.apache.wicket.Component, org.apache.wicket.markup.html.IHeaderResponse)
	     */
	    @Override
	    public void renderHead(Component component, IHeaderResponse response) {
		super.renderHead(component, response);
		StringBuilder onDomReadyJS = new StringBuilder();
		// enable/disable button
		onDomReadyJS.append("nextButtonEnableBehavior = function(enabled) { ");
		onDomReadyJS.append("wicketAjaxGet('");
		onDomReadyJS.append(this.getCallbackUrl());
		onDomReadyJS.append("&enabled='+enabled); };");
		// display popup error message
		onDomReadyJS.append("displayPopupErrorMessage = function(msg) { ");
		onDomReadyJS.append("wicketAjaxGet('");
		onDomReadyJS.append(this.getCallbackUrl());
		onDomReadyJS.append("&msg='+msg); };");

		onDomReadyJS.append("displayPopupErrorMessage = function(msg, returnfocusid) { ");
		onDomReadyJS.append("wicketAjaxGet('");
		onDomReadyJS.append(this.getCallbackUrl());
		onDomReadyJS.append("&msg='+msg+'&returnfocusid='+returnfocusid); };");

		response.renderOnDomReadyJavaScript(onDomReadyJS.toString());

		logger.debug("onDomReadyJS=" + onDomReadyJS);
	    }
	};
	add(nextButtonEnableBehavior);

    }

    public MarkupContainer add(final Component child) {
	border.add(child);
	return this;
    }

    /**
     * Adds back button to indicate that back button was pressed.
     */
    protected void addBackButtonParameter() {
	this.getPageParameters().add("backbutton", "backbutton");
    }

    /**
     * Removes back button parameter.
     */
    protected void removeBackButtonParameter() {
	this.getPageParameters().remove("backbutton");
    }

    /**
     * Extracts values from provided bean and tries to populate dynamic fields if 
     * qname matches field name.
     * 
     * @param clazz data bean
     */
    // TODO Refactor this, it's not needed for every BEAST page!
    protected <T> void populateQuestionValues(T clazz) throws IllegalArgumentException, IllegalAccessException {
	SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yy");
	Map<String, String> fieldsTable = new HashMap<String, String>();
	Field[] fields = clazz.getClass().getDeclaredFields();
	for(int i = 0; i < fields.length; i++){
	    fields[i].setAccessible(true);
	    if (fields[i].getType().getName().equals("java.lang.String")){
		if (fields[i].get(clazz) != null){
		    fieldsTable.put(fields[i].getName(), (String) fields[i].get(clazz));
		}
	    }else if (fields[i].getType().getName().equals("java.util.Date")){

		if (fields[i].get(clazz) != null){
		    fieldsTable.put(fields[i].getName(), formatter.format((java.util.Date) fields[i].get(clazz)));
		}
	    }
	}

	for(ProductQuestion current: questions){
	    if (StringUtils.isEmpty(current.getValue())){
		if (fieldsTable.containsKey(current.getqName())){
		    if (current.getType() == QuestionType.TEXT){
			current.setValue(current.getValue());
		    }else if (current.getType() == QuestionType.SELECT){
			current.setSelectOption(new SelectItem<String>(current.getValue(), current.getValue()));
		    }else if (current.getType() == QuestionType.DATE){
			try{
			    current.setDate(formatter.parse(current.getValue()));
			}catch(ParseException e){
			    logger.error(e.getMessage());
			}
		    }
		}
	    }
	}
    }

    /**
     * Populates page title from titan services.
     */
    protected void populatePageTitle(UIReply uiReply, String uiRequestname, String nodeName) {
	Object obj = uiReply.get(uiRequestname);
	if (obj == null){
	    logger.error("getTitle(): uiReply.get(" + nodeName + ") == null");
	    return;
	}

	UIRequest uiRequest = (UIRequest) obj;

	obj = uiRequest.get(nodeName);
	if (obj == null){
	    logger.error("getTitle(): uiReply.get(" + nodeName + ") == null");
	    return;
	}

	String title = ((UIDataItem) obj).getStringProp("title", null);

	if (StringUtils.isNotEmpty(title)){
	    this.getBorder().setPageTitle(title);
	}
    }

    /**
     * Reads dynamic questions from UIReplay and populates 
     * List<ProductQuestion>.
     */
    // TODO Refactor this, it's not needed for every BEAST page!
    protected void readQuestions(UIReply uiReply) {

	if (uiReply == null || uiReply.get("Questions") == null){
	    return;
	}

	list = (UIDataList) uiReply.get("Questions");

	Iterator<UIData> iterator = list.iterator();
	while(iterator.hasNext()){
	    UIData current = iterator.next();
	    if (current instanceof UIDataItem){
		UIDataItem currentUIDataItem = (UIDataItem) current;

		UIDataList options = (UIDataList) currentUIDataItem.getObjProp("options", null);
		ProductQuestion productQuestion = new ProductQuestion();
		productQuestion.setUiDataItem(currentUIDataItem);

		productQuestion.setName(currentUIDataItem.getStringProp("Text", null));
		productQuestion.setValue(currentUIDataItem.getStringProp("Value", null));
		productQuestion.setqName(currentUIDataItem.getStringProp("Name", null));

		if (options != null){
		    productQuestion.setType(QuestionType.SELECT);
		    productQuestion.populateOptions(options);

		    productQuestion.markOptionsAsSelected(productQuestion.getValue());
		}else if (StringUtils.isNotEmpty(productQuestion.getName())
			&& productQuestion.getName().toUpperCase().indexOf(QuestionType.DATE.toString()) != -1){
		    productQuestion.setType(QuestionType.DATE);
		    if (StringUtils.isNotEmpty(productQuestion.getValue())){
			SimpleDateFormat format = new SimpleDateFormat("MMddyy");

			try{
			    productQuestion.setDate(format.parse(productQuestion.getValue()));
			}catch(ParseException e){
			    logger.error(e.getMessage());
			}
		    }
		}else{
		    productQuestion.setType(QuestionType.TEXT);
		}

		questions.add(productQuestion);
	    }
	}
    }

    /**
     * Creates list of dynamic controls base on List<ProductQuestion>.
     * 
     * @return list of dynamic controls base on List<ProductQuestion>.
     */
    protected ListView<ProductQuestion> createListView() {
	ListView<ProductQuestion> listview = new ListView<ProductQuestion>("listview", questions) {

	    private static final long serialVersionUID = 1L;

	    protected void populateItem(ListItem<ProductQuestion> item) {
		ProductQuestion current = item.getModelObject();
		boolean focus = false;
		if (item.getIndex() == 0){
		    focus = true;
		}

		if (current.getType() == QuestionType.TEXT){
		    DynamicTextField dynamicTextField = new DynamicTextField("panel", current, focus, getBorder()
			    .getNotificationPanel());
		    item.add(dynamicTextField);
		}else if (current.getType() == QuestionType.DATE){
		    DynamicDateTextField dynamicTextField = new DynamicDateTextField("panel", current, focus,
			    getBorder().getNotificationPanel());
		    item.add(dynamicTextField);
		}else{
		    item.add(new DynamicSelectField("panel", current, focus, getBorder().getNotificationPanel()));
		}
	    }
	};
	return listview;
    }

    /**
     * Clears Form.
     */
    abstract void clearForm(final AjaxRequestTarget target);

    /**
     * Implements go back functionality.
     */
    abstract void goBack(final AjaxRequestTarget target);

    /**
     * Implements "Continue/F2" button functionality.
     */
    abstract void nextPage(final AjaxRequestTarget target, Form<?> form);
}
