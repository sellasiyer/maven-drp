/**
 * Best Buy Inc (c) 2012
 */
package com.bestbuy.bbym.ise.drp.scoreboard;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.datetime.markup.html.basic.DateLabel;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.list.PropertyListView;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.IResource;
import org.apache.wicket.request.resource.ResourceReference;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.convert.IConverter;
import org.apache.wicket.util.string.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bestbuy.bbym.ise.drp.DailyRhythmPortalSession;
import com.bestbuy.bbym.ise.drp.common.NewBaseNavPage;
import com.bestbuy.bbym.ise.drp.common.OkCancelDialogPanel;
import com.bestbuy.bbym.ise.drp.common.PageParameterKeys;
import com.bestbuy.bbym.ise.drp.common.PdfByteArrayResource;
import com.bestbuy.bbym.ise.drp.domain.Config;
import com.bestbuy.bbym.ise.drp.domain.scoreboard.ScoreboardDailyGoal;
import com.bestbuy.bbym.ise.drp.domain.scoreboard.ScoreboardEmployeeShift;
import com.bestbuy.bbym.ise.drp.domain.scoreboard.ScoreboardOperationType;
import com.bestbuy.bbym.ise.drp.domain.scoreboard.ScoreboardStoreType;
import com.bestbuy.bbym.ise.drp.domain.scoreboard.ScoreboardWidget.Type;
import com.bestbuy.bbym.ise.drp.service.ScoreboardService;
import com.bestbuy.bbym.ise.drp.util.WicketUtils;
import com.bestbuy.bbym.ise.exception.IseExceptionCodeEnum;
import com.bestbuy.bbym.ise.exception.ServiceException;

/**
 * @author Yernar Khuangan (a1003810)
 * @version 1.0
 * @since iteration 26
 */
public class SettingsPage extends NewBaseNavPage {

    private static final long serialVersionUID = 6661086149272068977L;
    private static Logger logger = LoggerFactory.getLogger(SettingsPage.class);

    private static final int GOAL_DISPLAY_COLUMNS = 2;
    private static final String DATE_PATTERN = "EEEE, MMMM dd, yyyy";

    @SpringBean(name = "scoreboardService")
    private ScoreboardService scoreboardService;

    // State Data
    private List<ScoreboardEmployeeShift> empList = new ArrayList<ScoreboardEmployeeShift>();
    private List<ScoreboardEmployeeShift> selectedEmpList = new ArrayList<ScoreboardEmployeeShift>();
    private ScoreboardOperationType operationType = ScoreboardOperationType.MOBILE;
    private ScoreboardStoreType storeType = ScoreboardStoreType.SWAS;

    // Wicket Elements
    private SimpleDateFormat sdf = new SimpleDateFormat("h:mm a");
    private OkCancelDialogPanel confirmDialog;
    private String addEmpBbyId = "";
    private List<Config> zoneSelectionList = null;
    private int removeEmployeeIdx = -1;
    private List<List<ScoreboardDailyGoal>> goals = new ArrayList<List<ScoreboardDailyGoal>>();
    private ListView<ScoreboardEmployeeShift> listView;
    private WebMarkupContainer listContainer, goalContainer;
    private TextField<String> addEmpText;

    public SettingsPage(final PageParameters parameters) {
	super(parameters);

	String paramValue = parameters.get(PageParameterKeys.SCOREBOARD_OPERATION.getUrlParameterKey()).toString();
	if (paramValue == null){
	    logger.warn("Parameter " + PageParameterKeys.SCOREBOARD_OPERATION.name()
		    + " not passed in, using mobile by default");
	    operationType = ScoreboardOperationType.MOBILE;
	}else{
	    try{
		operationType = ScoreboardOperationType.valueOf(paramValue);
	    }catch(IllegalArgumentException iae){
		logger.warn("Parameter " + PageParameterKeys.SCOREBOARD_OPERATION.name() + " passed in but value "
			+ paramValue + " is invalid, using mobile by default");
		operationType = ScoreboardOperationType.MOBILE;
	    }
	}
	logger.debug("operationType=" + operationType.name());

	paramValue = parameters.get(PageParameterKeys.STORE_TYPE.getUrlParameterKey()).toString();
	if (paramValue == null){
	    logger.warn("Parameter " + PageParameterKeys.STORE_TYPE.name() + " not passed in, using SWAS by default");
	    storeType = ScoreboardStoreType.SWAS;
	}else{
	    try{
		storeType = ScoreboardStoreType.valueOf(paramValue);
	    }catch(IllegalArgumentException iae){
		logger.warn("Parameter " + PageParameterKeys.STORE_TYPE.name() + " passed in but value " + paramValue
			+ " is invalid, using SWAS by default");
		storeType = ScoreboardStoreType.SWAS;
	    }
	}
	logger.debug("storeType=" + storeType.name());

	final FeedbackPanel feedbackPanel = new FeedbackPanel("feedback");
	feedbackPanel.setOutputMarkupPlaceholderTag(true);
	add(feedbackPanel);

	Label pageHeader = new Label("pageHeader");
	if (operationType == ScoreboardOperationType.MOBILE){
	    pageHeader.setDefaultModel(Model.of(getString("scoreboardPage.mobile.label")));
	}else{
	    pageHeader.setDefaultModel(Model.of(getString("scoreboardPage.computing.label")));
	}
	add(pageHeader);

	add(DateLabel.forDatePattern("currentDate", Model.of(new Date()), DATE_PATTERN));

	Form<Void> settingsForm = new Form<Void>("settingsForm");
	add(settingsForm);

	AjaxLink<Void> cancelLink = new AjaxLink<Void>("cancelLink") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onClick(AjaxRequestTarget target) {

		setResponsePage(ScoreboardPage.class);
	    }

	};
	settingsForm.add(cancelLink);

	AjaxSubmitLink saveLink = new AjaxSubmitLink("saveLink", settingsForm) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
		openLoadingModal("Saving settings", target);
		launchAction("saveEmployeeList", target);
		return;
	    }

	};
	settingsForm.add(saveLink);

	// add goals
	goalContainer = new WebMarkupContainer("goalContainer");
	goalContainer.setOutputMarkupId(true);
	settingsForm.add(goalContainer);

	goals.add(new ArrayList<ScoreboardDailyGoal>());
	goals.add(new ArrayList<ScoreboardDailyGoal>());

	for(int i = 0; i < GOAL_DISPLAY_COLUMNS; i++){
	    PropertyListView<ScoreboardDailyGoal> goalColumn = new PropertyListView<ScoreboardDailyGoal>("goalColumn"
		    + i, goals.get(i)) {

		private static final long serialVersionUID = 1L;

		@Override
		protected void populateItem(ListItem<ScoreboardDailyGoal> item) {
		    Label label = new Label("name", getString("scoreboardPage.goal."
			    + item.getModelObject().getName().toLowerCase() + ".label"));
		    item.add(label);
		    TextField<Void> textField;
		    if (item.getModelObject().getGoalType() == Type.DOLLARVALUE){
			textField = new TextField<Void>("goalValue") {

			    @Override
			    public <C> IConverter<C> getConverter(Class<C> type) {
				return new IConverter() {

				    private static final long serialVersionUID = 1L;

				    @Override
				    public Object convertToObject(String arg0, Locale arg1) {
					if (arg0 == null){
					    return null;
					}
					try{
					    String value = arg0;
					    if (arg0.startsWith("$")){
						value = arg0.substring(1);
					    }
					    return Long.valueOf(value);
					}catch(Exception e){
					    logger.debug("Conversation error", e);
					    return null;
					}
				    }

				    @Override
				    public String convertToString(Object arg0, Locale arg1) {
					if (arg0 == null){
					    return "";
					}
					return "$" + arg0.toString();

				    }

				};
			    }
			};
		    }else if (item.getModelObject().getGoalType() == Type.PERCENTAGE){
			textField = new TextField<Void>("goalValue") {

			    @Override
			    public <C> IConverter<C> getConverter(Class<C> type) {
				return new IConverter() {

				    private static final long serialVersionUID = 3331088828646153075L;

				    @Override
				    public Object convertToObject(String arg0, Locale arg1) {
					if (arg0 == null){
					    return null;
					}
					try{
					    String value = arg0;
					    if (arg0.endsWith("%")){
						value = arg0.substring(0, arg0.length() - 1);
					    }
					    return Long.valueOf(value);
					}catch(Exception e){
					    logger.debug("Conversation error", e);
					    return null;
					}
				    }

				    @Override
				    public String convertToString(Object arg0, Locale arg1) {
					if (arg0 == null){
					    return "";
					}
					return arg0.toString() + "%";

				    }

				};
			    }
			};
		    }else{
			textField = new TextField<Void>("goalValue") {

			    @Override
			    public <C> IConverter<C> getConverter(Class<C> type) {
				return new IConverter() {

				    private static final long serialVersionUID = 5212611747690904834L;

				    @Override
				    public Object convertToObject(String arg0, Locale arg1) {
					if (arg0 == null){
					    return null;
					}
					try{
					    String value = arg0;
					    return Long.valueOf(value);
					}catch(Exception e){
					    logger.debug("Conversation error", e);
					    return null;
					}
				    }

				    @Override
				    public String convertToString(Object arg0, Locale arg1) {
					if (arg0 == null){
					    return "";
					}
					return arg0.toString();

				    }

				};
			    }
			};
		    }
		    item.add(textField);
		}
	    };
	    goalColumn.setRenderBodyOnly(true);
	    goalContainer.add(goalColumn);
	}

	Form<Void> addEmpForm = new Form<Void>("addEmpForm");
	settingsForm.add(addEmpForm);

	listContainer = new WebMarkupContainer("listContainer");
	listContainer.setOutputMarkupId(true);
	addEmpForm.add(listContainer);

	listView = new ListView<ScoreboardEmployeeShift>("listView", empList) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void populateItem(ListItem<ScoreboardEmployeeShift> item) {

		ScoreboardEmployeeShift shift = item.getModelObject();

		CheckBox checkBox = new CheckBox("selected", new PropertyModel<Boolean>(shift, "selected"));
		AttributeAppender valMod = new AttributeAppender("currentValue", shift.getSelected().toString());
		checkBox.add(valMod);
		AttributeModifier idMod = new AttributeModifier("id", shift.getEmpDailyNtlMrgnId());
		checkBox.add(idMod);
		item.add(checkBox);

		Label label = new Label("empFullName", shift.getEmpFullName());
		AttributeModifier forMod = new AttributeModifier("for", shift.getEmpDailyNtlMrgnId());
		label.add(forMod);
		item.add(label);

		Label empId = new Label("empId", shift.getEmpBBYId());
		empId.setDefaultModel(Model.of(shift.getEmpBBYId().toUpperCase()));
		item.add(empId);

		DropDownChoice<Date> startDate = new DropDownChoice<Date>("shiftStartTime", new PropertyModel<Date>(
			shift, "shiftStartTime"), buildTimeChoiceItems(new Date()), new DateChoiceRenderer());
		item.add(startDate);

		DropDownChoice<Date> endDate = new DropDownChoice<Date>("shiftEndTime", new PropertyModel<Date>(shift,
			"shiftEndTime"), buildTimeChoiceItems(new Date()), new DateChoiceRenderer());
		item.add(endDate);

		DropDownChoice<Config> primaryZone = new DropDownChoice<Config>("primaryZone",
			new PropertyModel<Config>(shift, "primaryZone"), zoneSelectionList, new ZoneChoiceRenderer());
		item.add(primaryZone);

		DropDownChoice<Config> secondaryZone = new DropDownChoice<Config>("secondaryZone",
			new PropertyModel<Config>(shift, "secondaryZone"), zoneSelectionList, new ZoneChoiceRenderer());
		item.add(secondaryZone);

		if (!item.getModelObject().getSelected()){
		    AttributeModifier am = new AttributeModifier("disabled", "disabled");
		    AttributeModifier amLabel = new AttributeModifier("class", "disabled");
		    label.add(amLabel);
		    startDate.add(am);
		    endDate.add(am);
		    primaryZone.add(am);
		    secondaryZone.add(am);
		}

		item.add(new AjaxLink<Integer>("removeLink", Model.of(item.getIndex())) {

		    private static final long serialVersionUID = 1L;

		    @Override
		    public void onClick(AjaxRequestTarget target) {
			logger.debug("removeLink: " + this.getModelObject());
			removeEmployeeIdx = this.getModelObject();
			confirmDialog.open(target);
		    }

		});
	    }

	};
	listView.setReuseItems(true);
	listView.setOutputMarkupPlaceholderTag(true);
	listContainer.add(listView);

	addEmpText = new TextField<String>("addEmpText", new PropertyModel<String>(this, "addEmpBbyId"));
	addEmpText.setOutputMarkupPlaceholderTag(true);
	addEmpForm.add(addEmpText);
	AjaxButton ajaxButton = new AjaxButton("submitAddEmpLink", addEmpForm) {

	    private static final long serialVersionUID = -595864769049767514L;

	    @Override
	    protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
		logger.debug("Add employee: " + addEmpBbyId);
		if (!Strings.isEmpty(addEmpBbyId)){
		    launchAction("addEmployee", target);
		    return;
		}else{
		    error(getString("scoreboardPage.noEmployeeId.message"));
		    target.add(feedbackPanel);
		}
	    }
	};
	addEmpForm.add(ajaxButton);
	addEmpForm.setDefaultButton(ajaxButton);

	confirmDialog = new OkCancelDialogPanel("confirmDialog", getString("deleteLabel"), getString("cancelLabel")) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onClose(AjaxRequestTarget target) {
		if (isOk()){
		    launchAction("removeEmployee", target);
		    return;
		}
		removeEmployeeIdx = -1;
	    }

	};
	confirmDialog.setMessage(getString("scoreboardPage.confirmRemoveEmployee.title"));
	confirmDialog.setQuestion(getString("scoreboardPage.confirmRemoveEmployee.message"));
	confirmDialog.setOutputMarkupPlaceholderTag(true);
	add(confirmDialog);

	determineClientTime();
	launchAction("load");
    }

    private static List<Date> buildTimeChoiceItems(Date localDate) {
	List<Date> list = new ArrayList<Date>();
	Calendar cal = Calendar.getInstance();
	cal.setTime(localDate);
	cal.set(Calendar.HOUR_OF_DAY, 9);
	cal.set(Calendar.MINUTE, 0);
	cal.set(Calendar.SECOND, 0);
	cal.set(Calendar.MILLISECOND, 0);

	for(int i = 0; i <= 52; i++){
	    list.add(cal.getTime());
	    cal.add(Calendar.MINUTE, 15);
	}

	return list;
    }

    private final class DateChoiceRenderer implements IChoiceRenderer<Date> {

	private static final long serialVersionUID = 1L;

	@Override
	public Object getDisplayValue(Date arg0) {
	    return sdf.format(arg0);
	}

	@Override
	public String getIdValue(Date arg0, int arg1) {
	    return sdf.format(arg0);
	}
    }

    private final class ZoneChoiceRenderer implements IChoiceRenderer<Config> {

	private static final long serialVersionUID = 1L;

	@Override
	public Object getDisplayValue(Config arg0) {
	    if (arg0 == null){
		return null;
	    }
	    return arg0.getParamName();
	}

	@Override
	public String getIdValue(Config arg0, int arg1) {
	    if (arg0.getConfigId() == null || arg0.getConfigId() == 0){
		return null;
	    }
	    return arg0.getConfigId().toString();
	}
    }

    @Override
    protected String getOnDomReadyJS() {
	return "";
    }

    @Override
    protected void doAction(String action, AjaxRequestTarget target) {
	DailyRhythmPortalSession session = getDailyRhythmPortalSession();
	String storeId = session.getDrpUser().getStore().getId();

	if ("load".equals(action)){
	    if (!isLoadingModalOpen()){
		openLoadingModal(getString("scoreboardPage.loading.label"), target);
		launchAction("load", target);
		return;
	    }
	    logger.debug("Loading employee list for store id:" + storeId + " " + session.getDrpUser().getStoreId());
	    try{
		List<ScoreboardDailyGoal> goalList = scoreboardService.getStoreDailyGoals(storeId, storeType,
			operationType);

		Map<String, ScoreboardDailyGoal> map = new HashMap<String, ScoreboardDailyGoal>();
		for(ScoreboardDailyGoal goal: goalList){
		    map.put(goal.getName(), goal);
		}
		String[][] goalOrder = null;
		if (storeType == ScoreboardStoreType.SWAS){
		    if (operationType == ScoreboardOperationType.MOBILE){
			String[][] SWAS_MOBILE_COLUMN_GOALS = {
				{"UPGRADE_CHECKS", "BBYM_ACCESSORIES", "MP3_ACCESSORIES", "NO_CONTRACT_ACTIVATIONS" },
				{"NOTIONAL_MARGIN_GOAL", "BBYM_GSP", "MP3_GSP" } };
			goalOrder = SWAS_MOBILE_COLUMN_GOALS;
		    }else{
			String[][] SWAS_COMPUTING_COLUMN_GOALS = { {"NOTIONAL_MARGIN_GOAL" }, {"GRAB_AND_GO_PERCENT" } };
			goalOrder = SWAS_COMPUTING_COLUMN_GOALS;
		    }
		}else{
		    String[][] SAS_MOBILE_COLUMN_GOALS = {
			    {"UPGRADE_CHECKS", "BBYM_ACCESSORIES", "NO_CONTRACT_ACTIVATIONS" },
			    {"NOTIONAL_MARGIN_GOAL", "BBYM_GSP" } };
		    goalOrder = SAS_MOBILE_COLUMN_GOALS;
		}
		for(int i = 0; i < GOAL_DISPLAY_COLUMNS; i++){
		    List<ScoreboardDailyGoal> listSdg = goals.get(i);
		    listSdg.clear();
		    for(int j = 0; j < goalOrder[i].length; j++){
			if (map.get(goalOrder[i][j]) != null){
			    listSdg.add(map.get(goalOrder[i][j]));
			}
		    }
		}
		zoneSelectionList = scoreboardService.getZoneList(storeType, operationType);

		empList = scoreboardService.getStoreDailyEmployeeShift(storeId, storeType, operationType);
		listView.setList(empList);
		target.add(listContainer);
		target.add(goalContainer);
		target.appendJavaScript("initSettingsPage()");
	    }catch(ServiceException e){
		logger.error("ServiceException is " + e.getFullMessage());
		error(getString("scoreboardPage.noScoreboardDataReturned.message.label") + " " + e.getMessage());
		target.appendJavaScript("scrollToTop();");
		return;
	    }finally{
		closeLoadingModal(target);
	    }

	}else if ("addEmployee".equals(action)){
	    try{
		
		if(!Pattern.matches("[0-9a-zA-Z'.'\\w-]{1,20}", addEmpBbyId)){
		    throw new ServiceException(IseExceptionCodeEnum.NOTFOUND);
		}
		
		ScoreboardEmployeeShift empShift = scoreboardService.addEmployee(addEmpBbyId, storeId, storeType,
			operationType, session.getDrpUser());
		empList.add(empShift);

		addEmpBbyId = "";
		//listView.detach();
		listView.setList(empList);
		target.add(listContainer);
		target.add(addEmpText);
		target.appendJavaScript("initSettingsPage()");
	    }catch(ServiceException e){
		logger.error("ServiceException is " + e.getFullMessage());
		if (e.getErrorCode() == IseExceptionCodeEnum.DataItemAlreadyExists){
		    error(getString("scoreboardPage.employeeAlreadyExist.message"));
		}else if (e.getErrorCode() == IseExceptionCodeEnum.INTERNALSERVERERROR){
		    error(getString("scoreboardPage.serviceNotAvailable.message"));
		}else if (e.getErrorCode() == IseExceptionCodeEnum.NOTFOUND){
		    error(getString("scoreboardPage.networkIdNotValid.message"));
		}else{
		    error(getString("scoreboardPage.noScoreboardDataReturned.message.label") + " " + e.getMessage());
		}
		target.appendJavaScript("scrollToTop();");
		return;
	    }

	}else if ("removeEmployee".equals(action)){
	    try{

		int checkedEmpCount = 0;
		for(ScoreboardEmployeeShift emp: empList){
		    if (emp.getSelected()){
			checkedEmpCount++;
			break;
		    }
		}
		if (checkedEmpCount == 0){
		    throw new ServiceException(IseExceptionCodeEnum.DataAccess,
			    getString("scoreboardPage.employeeMustBeChecked.message"));
		}

		ScoreboardEmployeeShift empShift = empList.get(removeEmployeeIdx);
		scoreboardService.removeEmployeeShift(empShift);

		empList.remove(removeEmployeeIdx);
		listView.detach();
		listView.setList(empList);
		target.add(listContainer);
		removeEmployeeIdx = -1;
		target.appendJavaScript("initSettingsPage()");
	    }catch(ServiceException e){
		logger.error("ServiceException is " + e.getFullMessage());
		if (e.getErrorCode() == IseExceptionCodeEnum.DataItemAlreadyExists){
		    error(getString("scoreboardPage.employeeHasTransactions.message"));
		}else{
		    error(getString("scoreboardPage.noScoreboardDataReturned.message.label") + " " + e.getMessage());
		}
		target.appendJavaScript("scrollToTop();");
		return;
	    }

	}else if ("saveEmployeeList".equals(action)){
	    try{
		for(List<ScoreboardDailyGoal> goalList: goals){
		    for(ScoreboardDailyGoal sdg: goalList){
			boolean isValid = true;
			if (sdg.getGoalValue() == null){
			    isValid = false;
			}else{
			    if (sdg.getMinValue() != null){
				if (sdg.getGoalValue().intValue() < sdg.getMinValue().intValue()){
				    isValid = false;
				}
			    }
			    if (sdg.getMaxValue() != null){
				if (sdg.getGoalValue().intValue() > sdg.getMaxValue().intValue()){
				    isValid = false;
				}
			    }
			}
			if (!isValid){
			    String msg = getString("scoreboardPage.goal.validation.number.message");
			    String type = "";
			    if (sdg.getGoalType() == Type.PERCENTAGE){
				msg = getString("scoreboardPage.goal.validation.percentage.message");
				type = "%";
			    }else if (sdg.getGoalType() == Type.DOLLARVALUE){
				msg = getString("scoreboardPage.goal.validation.dollarvalue.message");
				type = "$";
			    }
			    String errorMsg = String.format(msg, getString("scoreboardPage.goal."
				    + sdg.getName().toLowerCase() + ".label"), sdg.getMinValue() + type, sdg
				    .getMaxValue()
				    + type);
			    throw new ServiceException(IseExceptionCodeEnum.DataAccess, errorMsg);
			}
			sdg.setModifiedBy(session.getDrpUser().getUserId());
			sdg.setCreatedBy(session.getDrpUser().getUserId());
		    }
		}

		List<ScoreboardDailyGoal> dailyGoalsList = new ArrayList<ScoreboardDailyGoal>();
		for(List<ScoreboardDailyGoal> goalList: goals){
		    dailyGoalsList.addAll(goalList);
		}
		scoreboardService.saveStoreDailyGoals(storeId, storeType, operationType, dailyGoalsList);

		int checkedEmpCount = 0;
		for(ScoreboardEmployeeShift emp: empList){
		    if (emp.getSelected()){
			checkedEmpCount++;
			break;
		    }
		}
		if (checkedEmpCount == 0){
		    throw new ServiceException(IseExceptionCodeEnum.DataAccess,
			    getString("scoreboardPage.employeeMustBeChecked.message"));
		}
		for(ScoreboardEmployeeShift emp: empList){
		    if (emp.getSelected()){
			checkedEmpCount++;
			if (emp.getShiftStartTime() == null || emp.getShiftEndTime() == null
				|| emp.getPrimaryZone() == null || emp.getShiftStartTime().after(emp.getShiftEndTime())){
			    throw new ServiceException(IseExceptionCodeEnum.DataAccess,
				    getString("scoreboardPage.employeeListShiftNotValid.message"));
			}
		    }else{
			emp.setShiftStartTime(null);
			emp.setShiftEndTime(null);
			emp.setPrimaryZone(null);
			emp.setSecondaryZone(null);
			emp.setModifiedBy(session.getDrpUser().getUserId());
		    }
		}

		scoreboardService.saveStoreDailyEmployeeShift(storeId, storeType, operationType, empList);

	    }catch(ServiceException e){
		logger.error("ServiceException is " + e.getFullMessage());
		closeLoadingModal(target);
		error(e.getMessage());
		target.appendJavaScript("scrollToTop();");
		return;
	    }

	    // call distribution page

	    // uncomment below lines if you want to generate html
	    /*
	     * ScoreboardEmpSchedulePage tmp = new ScoreboardEmpSchedulePage(new
	     * Date()); CharSequence url = RequestCycle.get().urlFor(new
	     * RenderPageRequestHandler(new PageProvider(tmp)));
	     * target.appendJavaScript("window.open('" + url.toString() +
	     * "');");
	     */
	    String html = WicketUtils.renderHTMLFromPage(new ScoreboardEmpSchedulePage(getClientTime(), operationType));
	    final PdfByteArrayResource pdfResource = WicketUtils.renderPDFFromHtml(html);
	    pdfResource.fetchData();

	    String uidString = UUID.randomUUID().toString().replaceAll("-", "");
	    ResourceReference rr = new ResourceReference(uidString) {

		private static final long serialVersionUID = 1L;

		@Override
		public IResource getResource() {
		    return pdfResource;

		}
	    };

	    if (rr.canBeRegistered()){
		getApplication().getResourceReferenceRegistry().registerResourceReference(rr);
		target.appendJavaScript("window.open('" + urlFor(rr, null) + "')");
	    }

	    closeLoadingModal(target);
	    launchAction("gotoScoreboardPage", target);

	}else if ("gotoScoreboardPage".equals(action)){
	    setResponsePage(ScoreboardPage.class);
	}
    }
}
