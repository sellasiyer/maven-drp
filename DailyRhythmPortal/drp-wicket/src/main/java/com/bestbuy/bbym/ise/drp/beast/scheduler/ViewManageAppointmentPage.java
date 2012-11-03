package com.bestbuy.bbym.ise.drp.beast.scheduler;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.extensions.ajax.markup.html.repeater.data.table.AjaxFallbackDefaultDataTable;
import org.apache.wicket.extensions.markup.html.form.DateTextField;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Check;
import org.apache.wicket.markup.html.form.CheckGroup;
import org.apache.wicket.markup.html.form.CheckGroupSelector;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Radio;
import org.apache.wicket.markup.html.form.RadioGroup;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bestbuy.bbym.ise.domain.Customer;
import com.bestbuy.bbym.ise.drp.beast.common.BaseBeastPage2;
import com.bestbuy.bbym.ise.drp.beast.common.StatusPage;
import com.bestbuy.bbym.ise.drp.common.PageParameterKeys;
import com.bestbuy.bbym.ise.drp.common.Validation;
import com.bestbuy.bbym.ise.drp.domain.Appointment;
import com.bestbuy.bbym.ise.drp.domain.AppointmentCloseStatus;
import com.bestbuy.bbym.ise.drp.domain.DrpUser;
import com.bestbuy.bbym.ise.drp.domain.SchedulerRequest;
import com.bestbuy.bbym.ise.drp.service.SchedulerService;
import com.bestbuy.bbym.ise.drp.util.DateFormatter;
import com.bestbuy.bbym.ise.drp.util.SelectItem;
import com.bestbuy.bbym.ise.drp.util.panel.SpanPanel;
import com.bestbuy.bbym.ise.exception.IseBusinessException;
import com.bestbuy.bbym.ise.exception.ServiceException;
import com.bestbuy.bbym.ise.util.Util;

public class ViewManageAppointmentPage extends BaseBeastPage2 {

    private static final long serialVersionUID = 1L;

    private static Logger logger = LoggerFactory.getLogger(ViewManageAppointmentPage.class);

    private Date fromDate, toDate;
    private String firstName, lastName, selectedAppointmentId;
    private SelectItem<String> selectedAppointmentStatus, selectedDepartment, selectedAppointmentType;
    private SelectedSearchType selectedSearchType;
    //settign some default value to avoid first time form submit valdiaiton
    private String appointmentId = "default";

    @SpringBean(name = "schedulerService")
    private SchedulerService schedulerService;

    private SchedulerRequest schedulerReq;
    private DrpUser user;

    private WebMarkupContainer backLinkContainer, appointmentSearchContainer, schedularDisplayContainer,
	    detailSearchTypeGroupContainer;
    private CheckGroup<Appointment> cancelCheckGroup, completeCheckGroup, noShowCheckGroup;

    private Map<String, String> statusMap = new HashMap<String, String>();
    private Map<String, String> statusDepartmentMap = new HashMap<String, String>();
    private Map<String, String> statusAppTypeMap = new HashMap<String, String>();
    private List<SelectItem<String>> statusList = new ArrayList<SelectItem<String>>();
    private List<SelectItem<String>> statusDepartmentList = new ArrayList<SelectItem<String>>();
    private List<SelectItem<String>> statusAppTypeList = new ArrayList<SelectItem<String>>();

    private FeedbackPanel feedbackPanel;
    final DateFormatter<Date> dateFmt = new DateFormatter<Date>("MM/dd/yy");
    final DateFormatter<Date> timeFmt = new DateFormatter<Date>("hh:mm a");

    final String na = getString("notApplicable.label");

    private TextField<String> firstNameTxt, lastNameTxt, appointmentIdTxt;
    private DropDownChoice<SelectItem<String>> statusDropDown, appTypeDropDown, departmentDropDown;

    boolean cancelGroupAllSelected;
    private AjaxFallbackDefaultDataTable<Appointment> schedulerResultDataTable;

    private RadioGroup<SelectedSearchType> selectedSearchTypeGroup;

    private LinkedHashMap<AppointmentCloseStatus, List<Appointment>> appointmentIdSelectionMap = new LinkedHashMap<AppointmentCloseStatus, List<Appointment>>();

    private ManageAppointmentsDataProvider manageAppointmentsDataProvider = new ManageAppointmentsDataProvider();

    private ViewManageSelectAppointmentPanel viewManageSelectPanel;

    private boolean defaultPageLoadCompleted, noDateRangeSelectedOnPage;

    private DateTextField fromDateField, toDateField;

    Radio<SelectedSearchType> detailsSearchRadio, aptIdSerarchRadio;

    WebMarkupContainer dataDisplayContainer;

    private enum PageDisplayMode {
	SEARCH_MODE, VIEW_MODE
    };

    private enum PageSearchSelectionMode {
	ID_SEARCH, DETAIL_SEARCH
    };

    private PageDisplayMode pageDisplayMode = PageDisplayMode.SEARCH_MODE;

    private PageSearchSelectionMode pageSearchSelectionMode = PageSearchSelectionMode.DETAIL_SEARCH;

    public ViewManageAppointmentPage() {
	super(null);
    }

    public ViewManageAppointmentPage(final PageParameters parameters) {
	super(parameters);

	noDateRangeSelectedOnPage = false;

	user = getDailyRhythmPortalSession().getDrpUser();

	feedbackPanel = new FeedbackPanel("feedback");
	feedbackPanel.setOutputMarkupPlaceholderTag(true);
	form.add(feedbackPanel);

	dataDisplayContainer = new WebMarkupContainer("dataDisplayContainer");
	dataDisplayContainer.setMarkupId("dataDisplayContainer");
	dataDisplayContainer.setOutputMarkupId(true);
	dataDisplayContainer.setOutputMarkupPlaceholderTag(true);
	form.add(dataDisplayContainer);

	cancelCheckGroup = new CheckGroup<Appointment>("cancelCheckGroup", new ArrayList<Appointment>());
	completeCheckGroup = new CheckGroup<Appointment>("completeCheckGroup", new ArrayList<Appointment>());
	noShowCheckGroup = new CheckGroup<Appointment>("noShowCheckGroup", new ArrayList<Appointment>());

	cancelCheckGroup.setMarkupId("cancelCheckGroup");
	cancelCheckGroup.setOutputMarkupId(true);
	cancelCheckGroup.setOutputMarkupPlaceholderTag(true);

	completeCheckGroup.setMarkupId("completeCheckGroup");
	completeCheckGroup.setOutputMarkupId(true);
	completeCheckGroup.setOutputMarkupPlaceholderTag(true);

	noShowCheckGroup.setMarkupId("noShowCheckGroup");
	noShowCheckGroup.setOutputMarkupId(true);
	noShowCheckGroup.setOutputMarkupPlaceholderTag(true);

	dataDisplayContainer.add(cancelCheckGroup);
	cancelCheckGroup.add(completeCheckGroup);
	completeCheckGroup.add(noShowCheckGroup);

	selectedSearchTypeGroup = new RadioGroup<SelectedSearchType>("selectedSearchTypeGroup",
		new PropertyModel<SelectedSearchType>(this, "selectedSearchType"));
	selectedSearchTypeGroup.setOutputMarkupId(true);
	selectedSearchTypeGroup.setOutputMarkupPlaceholderTag(true);

	detailSearchTypeGroupContainer = new WebMarkupContainer("detailSearchTypeGroupContainer") {

	    private static final long serialVersionUID = 1L;

	    public boolean isVisible() {
		return pageSearchSelectionMode == PageSearchSelectionMode.DETAIL_SEARCH;

	    }

	};
	detailSearchTypeGroupContainer.setMarkupId("detailSearchTypeGroupContainer");
	detailSearchTypeGroupContainer.setOutputMarkupId(true);
	detailSearchTypeGroupContainer.setOutputMarkupPlaceholderTag(true);

	appointmentSearchContainer = new WebMarkupContainer("appointmentSearchContainer") {

	    private static final long serialVersionUID = 1L;

	    public boolean isVisible() {
		return pageSearchSelectionMode == PageSearchSelectionMode.ID_SEARCH;

	    }

	};
	appointmentSearchContainer.setMarkupId("appointmentSearchContainer");
	appointmentSearchContainer.setOutputMarkupId(true);
	appointmentSearchContainer.setOutputMarkupPlaceholderTag(true);

	appointmentIdTxt = new TextField<String>("appointmentIdTxt", new PropertyModel<String>(this, "appointmentId"));
	appointmentIdTxt.setMarkupId("appointmentIdTxt");
	appointmentIdTxt.setOutputMarkupId(true);
	appointmentIdTxt.setOutputMarkupPlaceholderTag(true);
	appointmentSearchContainer.add(appointmentIdTxt);

	appointmentIdTxt.setRequired(true);

	detailsSearchRadio = new Radio<SelectedSearchType>("detailsSerarchRadio", new Model<SelectedSearchType>(
		SelectedSearchType.DETAIL_SEARCH));
	detailsSearchRadio.setMarkupId("detailsSerarchRadio");
	detailsSearchRadio.setOutputMarkupId(true);
	detailsSearchRadio.setOutputMarkupPlaceholderTag(true);

	detailsSearchRadio.add(new AjaxEventBehavior("onclick") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void onEvent(AjaxRequestTarget target) {
		logger.debug("aptIdSerarchRadio onEvent");

		pageSearchSelectionMode = PageSearchSelectionMode.DETAIL_SEARCH;

		appointmentId = null;
		noDateRangeSelectedOnPage = true;

		getDailyRhythmPortalSession().getSchedulerSearchCriteria().setAppointmentId(null);

		target.add(detailSearchTypeGroupContainer);
		target.add(appointmentSearchContainer);

	    }
	});

	fromDateField = new DateTextField("fromDate", new PropertyModel<Date>(this, "fromDate"), "MM/dd/yy");
	fromDateField.setOutputMarkupPlaceholderTag(true);

	fromDateField.add(new AjaxFormComponentUpdatingBehavior("onchange") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void onUpdate(AjaxRequestTarget target) {

		if (StringUtils.isEmpty(fromDateField.getDefaultModelObjectAsString())){
		    noDateRangeSelectedOnPage = true;
		}else{
		    noDateRangeSelectedOnPage = false;
		}
	    }

	    @Override
	    protected void onError(AjaxRequestTarget target, RuntimeException e) {
		feedbackPanel.setVisible(false);
		onUpdate(target);
	    }

	});

	toDateField = new DateTextField("toDate", new PropertyModel<Date>(this, "toDate"), "MM/dd/yy");
	toDateField.setOutputMarkupPlaceholderTag(true);

	toDateField.add(new AjaxFormComponentUpdatingBehavior("onchange") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void onUpdate(AjaxRequestTarget target) {
		if (StringUtils.isEmpty(toDateField.getDefaultModelObjectAsString())){
		    noDateRangeSelectedOnPage = true;

		}else{
		    noDateRangeSelectedOnPage = false;
		}
	    }

	    @Override
	    protected void onError(AjaxRequestTarget target, RuntimeException e) {
		feedbackPanel.setVisible(false);
		onUpdate(target);
	    }

	});

	firstNameTxt = new TextField<String>("firstNameTxt", new PropertyModel<String>(this, "firstName"));
	firstNameTxt.setMarkupId("firstNameTxt");
	firstNameTxt.setOutputMarkupId(true);
	firstNameTxt.setOutputMarkupPlaceholderTag(true);

	firstNameTxt.add(new AjaxFormComponentUpdatingBehavior("onchange") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void onUpdate(AjaxRequestTarget target) {
		if (StringUtils.isEmpty(firstNameTxt.getDefaultModelObjectAsString())){
		    noDateRangeSelectedOnPage = true;

		}else{
		    noDateRangeSelectedOnPage = false;
		}
	    }
	});

	lastNameTxt = new TextField<String>("lastNameTxt", new PropertyModel<String>(this, "lastName"));
	lastNameTxt.setMarkupId("lastNameTxt");
	lastNameTxt.setOutputMarkupId(true);
	lastNameTxt.setOutputMarkupPlaceholderTag(true);

	lastNameTxt.add(new AjaxFormComponentUpdatingBehavior("onchange") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void onUpdate(AjaxRequestTarget target) {
		if (StringUtils.isEmpty(lastNameTxt.getDefaultModelObjectAsString())){
		    noDateRangeSelectedOnPage = true;

		}else{
		    noDateRangeSelectedOnPage = false;
		}
	    }
	});

	try{
	    statusMap = schedulerService.getStatuses(user.getStoreId());
	    statusDepartmentMap = schedulerService.getDepartments(user.getStoreId());

	}catch(ServiceException se){
	    String message = "An unexpected exception occured while attempting to get status";
	    logger.error(message, se);
	}catch(IseBusinessException be){
	    String message = "An unexpected exception occured while attempting to get status";
	    logger.error(message, be);
	}

	final Iterator<Map.Entry<String, String>> mapIterator = statusMap.entrySet().iterator();
	while(mapIterator.hasNext()){
	    Map.Entry<String, String> statusType = (Map.Entry<String, String>) mapIterator.next();
	    statusList.add(new SelectItem<String>(statusType.getKey(), statusType.getValue()));
	}

	final Iterator<Map.Entry<String, String>> mapDepIterator = statusDepartmentMap.entrySet().iterator();
	while(mapDepIterator.hasNext()){
	    Map.Entry<String, String> statusType = (Map.Entry<String, String>) mapDepIterator.next();
	    statusDepartmentList.add(new SelectItem<String>(statusType.getKey(), statusType.getValue()));
	}

	ChoiceRenderer<SelectItem<String>> dropDownRenderer = new ChoiceRenderer<SelectItem<String>>("value", "key");
	statusDropDown = new DropDownChoice<SelectItem<String>>("statusSelect", new PropertyModel<SelectItem<String>>(
		ViewManageAppointmentPage.this, "selectedAppointmentStatus"), statusList, dropDownRenderer);
	statusDropDown.setMarkupId("statusSelect");
	statusDropDown.setOutputMarkupPlaceholderTag(true);

	statusDropDown.add(new AjaxFormComponentUpdatingBehavior("onchange") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void onUpdate(AjaxRequestTarget target) {
		SelectItem<String> selected = departmentDropDown.getModelObject();
		if (selected == null){
		    noDateRangeSelectedOnPage = true;
		}else{
		    noDateRangeSelectedOnPage = false;
		}
	    }
	});

	departmentDropDown = new DropDownChoice<SelectItem<String>>("departmentSelect",
		new PropertyModel<SelectItem<String>>(ViewManageAppointmentPage.this, "selectedDepartment"),
		statusDepartmentList, dropDownRenderer);
	departmentDropDown.setMarkupId("departmentSelect");
	departmentDropDown.setOutputMarkupPlaceholderTag(true);

	appTypeDropDown = new DropDownChoice<SelectItem<String>>("appointmenTypeSelect",
		new PropertyModel<SelectItem<String>>(ViewManageAppointmentPage.this, "selectedAppointmentType"),
		statusAppTypeList, dropDownRenderer);
	appTypeDropDown.setMarkupId("appointmenTypeSelect");
	appTypeDropDown.setOutputMarkupPlaceholderTag(true);

	appTypeDropDown.add(new AjaxFormComponentUpdatingBehavior("onchange") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void onUpdate(AjaxRequestTarget target) {
		SelectItem<String> selected = appTypeDropDown.getModelObject();
		if (selected == null){
		    noDateRangeSelectedOnPage = true;
		}else{
		    noDateRangeSelectedOnPage = false;
		}
	    }
	});

	departmentDropDown.add(new AjaxFormComponentUpdatingBehavior("onchange") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void onUpdate(AjaxRequestTarget target) {
		SelectItem<String> selected = departmentDropDown.getModelObject();
		if (selected != null){
		    logger.debug("selected appType key----" + selected.getKey());
		    getAppointmentTypes(selected.getKey(), target);
		    target.add(statusDropDown);
		    target.add(appTypeDropDown);
		    noDateRangeSelectedOnPage = false;
		}else{
		    noDateRangeSelectedOnPage = true;
		}
	    }
	});

	aptIdSerarchRadio = new Radio<SelectedSearchType>("aptIdSerarchRadio", new Model<SelectedSearchType>(
		SelectedSearchType.ID_SEARCH));
	aptIdSerarchRadio.setMarkupId("aptIdSerarchRadio");
	aptIdSerarchRadio.setOutputMarkupId(true);

	aptIdSerarchRadio.add(new AjaxEventBehavior("onclick") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void onEvent(AjaxRequestTarget target) {
		logger.debug("aptIdSerarchRadio onEvent");
		pageSearchSelectionMode = PageSearchSelectionMode.ID_SEARCH;

		appointmentId = null;
		fromDate = null;
		toDate = null;
		firstName = null;
		lastName = null;
		selectedAppointmentStatus = null;
		selectedDepartment = null;
		selectedAppointmentType = null;
		noDateRangeSelectedOnPage = true;

		target.add(detailSearchTypeGroupContainer);
		target.add(appointmentSearchContainer);

	    }
	});

	detailSearchTypeGroupContainer.add(fromDateField);
	detailSearchTypeGroupContainer.add(toDateField);
	detailSearchTypeGroupContainer.add(firstNameTxt);
	detailSearchTypeGroupContainer.add(lastNameTxt);
	detailSearchTypeGroupContainer.add(statusDropDown);
	detailSearchTypeGroupContainer.add(departmentDropDown);
	detailSearchTypeGroupContainer.add(appTypeDropDown);

	selectedSearchTypeGroup.add(detailsSearchRadio);
	selectedSearchTypeGroup.add(aptIdSerarchRadio);
	selectedSearchTypeGroup.add(detailSearchTypeGroupContainer);
	selectedSearchTypeGroup.add(appointmentSearchContainer);

	form.add(selectedSearchTypeGroup);

	schedularDisplayContainer = new WebMarkupContainer("schedularDisplayContainer") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public boolean isVisible() {
		return pageDisplayMode == PageDisplayMode.SEARCH_MODE;
	    };
	};
	schedularDisplayContainer.setOutputMarkupPlaceholderTag(true);
	noShowCheckGroup.add(schedularDisplayContainer);

	final List<IColumn<Appointment>> columns = new ArrayList<IColumn<Appointment>>();

	columns.add(new PropertyColumn<Appointment>(new ResourceModel("schedulerViewManagePage.appointmnetId.column"),
		"appointmentId") {

	    private static final long serialVersionUID = 1L;

	    @SuppressWarnings("rawtypes")
	    @Override
	    public void populateItem(Item cellItem, String componentId, IModel rowModel) {
		Appointment row = (Appointment) rowModel.getObject();
		cellItem.add(new ViewManageAppointmentPage.AppointmentDetailDisplayPanel(componentId, rowModel, row));
	    }

	});

	columns.add(new PropertyColumn<Appointment>(new ResourceModel("schedulerViewManagePage.order.column"),
		"orderNo"));

	columns.add(new PropertyColumn<Appointment>(new ResourceModel("schedulerViewManagePage.name.column"),
		"firstName") {

	    private static final long serialVersionUID = 1L;

	    @SuppressWarnings("rawtypes")
	    @Override
	    public void populateItem(Item cellItem, String componentId, IModel rowModel) {
		Appointment row = (Appointment) rowModel.getObject();
		cellItem.add(new ViewManageAppointmentPage.CustomerNameDisplayPanel(componentId, rowModel, row));
	    }
	});

	columns.add(new PropertyColumn<Appointment>(new ResourceModel("schedulerViewManagePage.data.column"),
		"appointmentDateTime") {

	    private static final long serialVersionUID = 1L;

	    @SuppressWarnings("rawtypes")
	    @Override
	    public void populateItem(Item cellItem, String componentId, IModel rowModel) {
		Appointment row = (Appointment) rowModel.getObject();
		cellItem.add(new ViewManageAppointmentPage.DateTimeDisplayPanel(componentId, rowModel, row, "date"));
	    }
	});

	columns.add(new PropertyColumn<Appointment>(new ResourceModel("schedulerViewManagePage.time.column"),
		"appointmentDateTime") {

	    private static final long serialVersionUID = 1L;

	    @SuppressWarnings("rawtypes")
	    @Override
	    public void populateItem(Item cellItem, String componentId, IModel rowModel) {
		Appointment row = (Appointment) rowModel.getObject();
		cellItem.add(new ViewManageAppointmentPage.DateTimeDisplayPanel(componentId, rowModel, row, "time"));
	    }
	});

	columns.add(new PropertyColumn<Appointment>(new ResourceModel("schedulerViewManagePage.reason.column"),
		"reason") {

	    private static final long serialVersionUID = 1L;

	    @SuppressWarnings("rawtypes")
	    @Override
	    public void populateItem(Item cellItem, String componentId, IModel rowModel) {
		final Appointment row = (Appointment) rowModel.getObject();
		cellItem.add(new SpanPanel<Appointment>(componentId, rowModel) {

		    private static final long serialVersionUID = 1L;

		    @Override
		    public String generateLabelString() {
			return row.getReason();
		    }
		});
	    }
	});

	columns.add(new PropertyColumn<Appointment>(new ResourceModel("schedulerViewManagePage.status.column"),
		"currentStatus") {

	    private static final long serialVersionUID = 1L;

	    @SuppressWarnings("rawtypes")
	    @Override
	    public void populateItem(Item cellItem, String componentId, IModel rowModel) {
		final Appointment row = (Appointment) rowModel.getObject();
		cellItem.add(new SpanPanel<Appointment>(componentId, rowModel) {

		    private static final long serialVersionUID = 1L;

		    @Override
		    public String generateLabelString() {
			String status = na;
			if (row.getCurrentStatus() == 0){
			    return getString("schedulerViewManagePage.pending.column");
			}
			if (row.getAllowedStatus() != null && row.getAllowedStatus().entrySet() != null
				&& row.getCurrentStatus() != null){
			    Iterator<Map.Entry<AppointmentCloseStatus, List<String>>> iter = row.getAllowedStatus()
				    .entrySet().iterator();
			    while(iter.hasNext()){
				Map.Entry<AppointmentCloseStatus, List<String>> statusType = iter.next();
				if (statusType.getValue() != null
					&& statusType.getValue().contains(row.getCurrentStatus().toString())){
				    status = statusType.getKey() != null?statusType.getKey().getServiceValue():na;
				    break;
				}
			    }
			}
			return status;
		    }
		});
	    }
	});

	columns.add(new AbstractColumn<Appointment>(null) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public Component getHeader(String componentId) {
		return new ViewManageAppointmentPage.CancelCheckGroupSelector(componentId);
	    }

	    @SuppressWarnings("rawtypes")
	    @Override
	    public void populateItem(Item cellItem, String componentId, IModel rowModel) {
		Appointment row = (Appointment) rowModel.getObject();
		cellItem.add(new ViewManageAppointmentPage.CancelCheckBoxDisplayPanel(componentId, rowModel, row));
	    }

	    @Override
	    public boolean isSortable() {
		return false;
	    }

	});

	columns.add(new AbstractColumn<Appointment>(null) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public Component getHeader(String componentId) {
		return new ViewManageAppointmentPage.CompleteCheckGroupSelector(componentId);
	    }

	    @SuppressWarnings("rawtypes")
	    @Override
	    public void populateItem(Item cellItem, String componentId, IModel rowModel) {
		Appointment row = (Appointment) rowModel.getObject();
		cellItem.add(new ViewManageAppointmentPage.CompleteCheckBoxDisplayPanel(componentId, rowModel, row));
	    }

	    @Override
	    public boolean isSortable() {
		return false;
	    }

	});

	columns.add(new AbstractColumn<Appointment>(null) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public Component getHeader(String componentId) {
		return new ViewManageAppointmentPage.NoShowCheckGroupSelector(componentId);
	    }

	    @SuppressWarnings("rawtypes")
	    @Override
	    public void populateItem(Item cellItem, String componentId, IModel rowModel) {
		Appointment row = (Appointment) rowModel.getObject();
		cellItem.add(new ViewManageAppointmentPage.NoShowCheckBoxDisplayPanel(componentId, rowModel, row));
	    }

	    @Override
	    public boolean isSortable() {
		return false;
	    }

	});

	schedulerResultDataTable = new AjaxFallbackDefaultDataTable<Appointment>("schedulerResultDataTable", columns,
		manageAppointmentsDataProvider, 5000);
	schedulerResultDataTable.setMarkupId("schedulerResultDataTable");
	schedulerResultDataTable.setOutputMarkupId(true);
	schedulerResultDataTable.setOutputMarkupPlaceholderTag(true);

	schedularDisplayContainer.add(schedulerResultDataTable);

	backLinkContainer = new WebMarkupContainer("backLinkContainer") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public boolean isVisible() {
		return pageDisplayMode == PageDisplayMode.VIEW_MODE;
	    }

	};
	backLinkContainer.setOutputMarkupId(true);
	backLinkContainer.setOutputMarkupPlaceholderTag(true);

	final AjaxLink<Object> backToPreviousLink = new AjaxLink<Object>("backToPreviousLink") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onClick(AjaxRequestTarget target) {
		pageDisplayMode = PageDisplayMode.SEARCH_MODE;
		selectedAppointmentId = null;

		target.add(detailSearchTypeGroupContainer);
		target.add(appointmentSearchContainer);
		target.add(dataDisplayContainer);
		target
			.appendJavaScript("fixedHeaderTables();toggleResheduleButton(false);toggleSubmitButton(false);installClickHandlers();setupApptTable();installSortableClickHandlers();");

	    }
	};
	backLinkContainer.add(backToPreviousLink);
	dataDisplayContainer.add(backLinkContainer);

	viewManageSelectPanel = new ViewManageSelectAppointmentPanel("viewManageSelectAppointmentPanel",
		appointmentIdSelectionMap) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public boolean isVisible() {
		return pageDisplayMode == PageDisplayMode.VIEW_MODE;
	    }
	};
	viewManageSelectPanel.setOutputMarkupId(true);
	viewManageSelectPanel.setOutputMarkupPlaceholderTag(true);
	dataDisplayContainer.add(viewManageSelectPanel);

	add(new AbstractDefaultAjaxBehavior() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void respond(AjaxRequestTarget target) {
	    }

	    @Override
	    public void renderHead(Component component, IHeaderResponse response) {
		super.renderHead(component, response);
		StringBuilder onDomReadyJS = new StringBuilder();
		onDomReadyJS.append(buildStaticValidationJS());
		onDomReadyJS.append("doFormFieldValidation(custInfoValidation);");
		onDomReadyJS.append("bindHotKeys();");
		onDomReadyJS.append("doSearchClick();fixedHeaderTables();");
		logger.debug("onDomReadyJS=" + onDomReadyJS);
		response.renderOnDomReadyJavaScript(onDomReadyJS.toString());
	    }
	});

	if (getDailyRhythmPortalSession().getSchedulerSearchCriteria() != null){
	    defaultPageLoadCompleted = true;
	}

    }

    private String buildStaticValidationJS() {
	final StringBuilder validationJS = new StringBuilder();
	// first name settings
	validationJS.append("custInfoValidation.firstName.maxLength=");
	validationJS.append(Validation.FIRST_NAME_MAX_SIZE);
	validationJS.append(";");
	validationJS.append("custInfoValidation.firstName.minLength=");
	validationJS.append(Validation.FIRST_NAME_MIN_SIZE);
	validationJS.append(";");
	//
	// last name settings
	validationJS.append("custInfoValidation.lastName.maxLength=");
	validationJS.append(Validation.LAST_NAME_MAX_SIZE);
	validationJS.append(";");
	validationJS.append("custInfoValidation.lastName.minLength=");
	validationJS.append(Validation.LAST_NAME_MIN_SIZE);
	validationJS.append(";");
	//
	return validationJS.toString();
    }

    public void getAppointmentTypes(String key, AjaxRequestTarget target) {

	try{
	    statusAppTypeMap = schedulerService.getAppointmentTypes(user.getStoreId(), key);

	}catch(ServiceException se){
	    String message = "An unexpected exception occured while attempting to get app status";
	    logger.error(message, se);
	    messageModalPanel.setMessage(message);
	    target.appendJavaScript("wicketBehavior('message');");
	}catch(IseBusinessException be){
	    String message = "An unexpected exception occured while attempting to get app status";
	    logger.error(message, be);
	    messageModalPanel.setMessage(message);
	    target.appendJavaScript("wicketBehavior('message');");
	}

	statusAppTypeList.clear();
	final Iterator<Map.Entry<String, String>> mapIterator = statusAppTypeMap.entrySet().iterator();
	while(mapIterator.hasNext()){
	    Map.Entry<String, String> department = (Map.Entry<String, String>) mapIterator.next();
	    statusAppTypeList.add(new SelectItem<String>(department.getKey(), department.getValue()));
	}
    }

    @Override
    protected String getButtonName(int buttonId) {
	switch (buttonId) {
	    case 1:
		return "closeButton";
	    case 2:
		return "clearButton";
	    case 3:
		return "searchButton";
	    case 4:
		return "submitButton";
	    case 5:
		return "rescheduleButton";
	    case 6:
		return "newAppointmentButton";

	}
	return null;
    }

    @Override
    protected String getButtonFunctionKey(int buttonId) {
	switch (buttonId) {
	    case 1:
		return "F3";
	    case 2:
		return "F4";
	    case 3:
		return "F5";
	    case 4:
		return "F2";
	    case 5:
		return "F6";
	    case 6:
		return "F7";

	}
	return null;
    }

    @Override
    protected String getButtonStyleClasses(int buttonId) {
	switch (buttonId) {
	    case 1:
		return "button left";
	    case 2:
		return "button left";
	    case 3:
		return "button right";
	    case 4:
		return "button right";
	    case 5:
		return "button right";
	    case 6:
		return "button right";
	}
	return null;
    }

    @Override
    protected void onButtonSubmit(int buttonId, AjaxRequestTarget target) {
	switch (buttonId) {
	    case 1:
		logger.debug("closeButton click");
		if (!quitModalPanel.isOpen()){
		    logger.debug("bfr open close pop");
		    quitModalPanel.setQuestion(getString("schedulerCustomerInfo.quitModal.question.label"));
		    quitModalPanel.open(target);
		}
		break;
	    case 2:
		logger.debug("clearButton onSubmit");
		if (!clearModalPanel.isOpen()){
		    clearModalPanel.setQuestion(getString("schedulerCustomerInfo.clearModal.question.label"));
		    clearModalPanel.open(target);
		}
		break;
	    case 3:
		logger.debug("searchButton onSubmit");
		doSearch(target);
		break;
	    case 4:
		logger.debug("submit button onSubmit");
		doPageSubmit(target);
		break;
	    case 5:
		doReschedule(target);
		break;
	    case 6:
		doNewAppointment(target);
		break;

	}

    }

    @Override
    protected IModel<String> getButtonModel(int buttonId) {
	switch (buttonId) {
	    case 1:
		return new ResourceModel("schedulerCustomerInfoForm.close.button");
	    case 2:
		return new ResourceModel("schedulerCustomerInfoForm.clear.button");
	    case 3:
		return new ResourceModel("schedulerCustomerInfoForm.search.button");
	    case 4:
		return new ResourceModel("schedulerCustomerInfoForm.submit.button");
	    case 5:
		return new ResourceModel("schedulerCustomerInfoForm.reschedule.button");
	    case 6:
		return new ResourceModel("schedulerCustomerInfoForm.newapt.button");
	}
	return null;
    }

    @Override
    protected boolean getDefaultFormProcessingProperty(int buttonId) {
	switch (buttonId) {
	    case 1:
		return false;
	    case 2:
		return true;
	    case 3:
		return true;
	    case 4:
		return true;
	    case 5:
		return false;
	    case 6:
		return false;
	}
	return true;
    }

    @Override
    protected void quitModalPanelOnClose(AjaxRequestTarget target) {
	if (quitModalPanel.isOk()){
	    getDailyRhythmPortalSession().setSchedulerReq(null);
	    getDailyRhythmPortalSession().setSchedulerSearchCriteria(null);
	    PageParameters pp = new PageParameters();
	    pp.add(PageParameterKeys.EXIT_STATUS.getUrlParameterKey(), "cancel");
	    setResponsePage(new StatusPage(pp));
	}
    }

    @Override
    protected void clearModalPanelOnClose(AjaxRequestTarget target) {
	logger.debug("clear onSubmit");
	if (clearModalPanel.isOk()){

	    fromDate = null;
	    toDate = null;
	    firstName = null;
	    lastName = null;
	    appointmentId = "";
	    selectedAppointmentStatus = null;
	    selectedDepartment = null;
	    selectedAppointmentType = null;

	    defaultPageLoadCompleted = true;
	    noDateRangeSelectedOnPage = true;

	    pageDisplayMode = PageDisplayMode.SEARCH_MODE;
	    manageAppointmentsDataProvider.setSchedulerDataList(null);
	    schedulerResultDataTable.modelChanged();

	    target.add(dataDisplayContainer);
	    target.add(schedularDisplayContainer);
	    target.add(detailSearchTypeGroupContainer);
	    target.add(appointmentSearchContainer);
	    target.appendJavaScript("fixedHeaderTables();");

	}
    }

    @Override
    protected void wicketBehaviorRespond(AjaxRequestTarget target) {
	String id = RequestCycle.get().getRequest().getRequestParameters().getParameterValue("id").toString();
	logger.debug("in wicketBehavior id=" + id);
	if ("pageSearch".equals(id)){
	    doSearch(target);
	}else if ("getAppointmentSlots".equals(id)){
	    // getAppointmentSlots(target);

	}else if ("newAppointment".equals(id)){
	    doNewAppointment(target);

	}else if ("rescheduleAppointment".equals(id)){
	    doReschedule(target);
	}else if ("pageSubmit".equals(id)){
	    doPageSubmit(target);
	}else if ("message".equals(id)){
	    doMessage(target);
	}
    }

    private boolean isSearchCriteriaNull() {
	logger.debug(fromDate + "::" + toDate + "::" + firstName + "::" + lastName + "::" + selectedAppointmentStatus
		+ "::" + appointmentId);

	if (fromDate == null && toDate == null && firstName == null && lastName == null
		&& selectedAppointmentStatus == null
		&& (appointmentId == null || appointmentId.equals("default") || appointmentId.equals(""))){
	    return true;
	}
	return false;
    }

    private void populateFieldsWithSearchCriteria(SchedulerRequest request) {

	fromDate = request.getStartDate();
	toDate = request.getEndDate();
	firstName = (request.getCustomer() != null?request.getCustomer().getFirstName():"");
	lastName = (request.getCustomer() != null?request.getCustomer().getLastName():"");
	appointmentId = request.getAppointmentId();
	if (request.getAppointmentStatusId() != null){
	    selectedAppointmentStatus = new SelectItem<String>(request.getAppointmentStatusId(), statusMap.get(request
		    .getAppointmentStatusId()));
	}
    }

    private void doSearch(AjaxRequestTarget target) {

	feedbackPanel.setVisible(true);
	// Perform DateValidations and return
	if (fromDate != null && toDate != null){
	    String errorMessage = validateDateInBusinessRange(fromDate, toDate);
	    if (errorMessage != null){
		logger.debug("Invalid date Search ::::");
		error(errorMessage);
		target.appendJavaScript("$(\"#fromDate\").focus();");

		target.add(schedularDisplayContainer);
		manageAppointmentsDataProvider.setSchedulerDataList(null);
		schedulerResultDataTable.modelChanged();
		target.appendJavaScript("fixedHeaderTables();installClickHandlers();");
		return;
	    }
	}

	if (!loadingModalPanel.isOpen()){
	    loadingModalPanel.setMessage(getString("schedulerViewManagePage.loading.message"));
	    loadingModalPanel.open(target);
	    target.appendJavaScript("fixedHeaderTables();");
	    target.appendJavaScript("clickButton('#searchButton');");
	    return;
	}
	logger.debug("doing Appointment search ::  " + selectedSearchType.name());

	schedulerReq = populateSchedulerRequest();

	if (schedulerReq != null){
	    try{

		manageAppointmentsDataProvider.setSchedulerDataList(schedulerService
			.searchAppointmentByCriteria(schedulerReq));

	    }catch(ServiceException e){
		loadingModalPanel.close(target);
		manageAppointmentsDataProvider.setSchedulerDataList(null);
		schedulerResultDataTable.modelChanged();
		target.add(schedularDisplayContainer);
		messageModalPanel.setMessage(getString("scheduler.generic.error"));
		target.appendJavaScript("fixedHeaderTables();");
		target.appendJavaScript("wicketBehavior('message');");
		return;
	    }catch(IseBusinessException e){
		loadingModalPanel.close(target);
		manageAppointmentsDataProvider.setSchedulerDataList(null);
		schedulerResultDataTable.modelChanged();
		target.add(schedularDisplayContainer);
		messageModalPanel.setMessage(getString("scheduler.generic.error"));
		target.appendJavaScript("wicketBehavior('message');");
		target.appendJavaScript("fixedHeaderTables();");
		return;
	    }

	    loadingModalPanel.close(target);
	    schedulerResultDataTable.modelChanged();
	    // populate the text fields with the search criteria requested...
	    populateFieldsWithSearchCriteria(schedulerReq);

	    target.add(schedularDisplayContainer);

	    if (StringUtils.isNotEmpty(schedulerReq.getAppointmentId())){
		pageSearchSelectionMode = PageSearchSelectionMode.ID_SEARCH;
		target.add(detailSearchTypeGroupContainer);
		target.add(appointmentSearchContainer);
		target.appendJavaScript("toggleSearchSelectionRadio(\"#aptIdSerarchRadio\");");

	    }else{
		pageSearchSelectionMode = PageSearchSelectionMode.DETAIL_SEARCH;
		target.add(detailSearchTypeGroupContainer);
		target.add(appointmentSearchContainer);
		target.appendJavaScript("toggleSearchSelectionRadio(\"#detailsSearchRadio\");");

	    }

	}else{
	    loadingModalPanel.close(target);
	    error("Please enter the required details to search");
	    target.appendJavaScript("fixedHeaderTables();installClickHandlers();");
	    return;
	}

	target
		.appendJavaScript("fixedHeaderTables();installClickHandlers();setupApptTable();installSortableClickHandlers();");

    }

    private SchedulerRequest populateSchedulerRequest() {

	schedulerReq = new SchedulerRequest();

	logger.debug("defaultPageLoadCompleted.........**..." + defaultPageLoadCompleted + ".........."
		+ noDateRangeSelectedOnPage);

	if (!defaultPageLoadCompleted){
	    schedulerReq.setUser(user);
	    schedulerReq.setStartDate(new Date());
	    schedulerReq.setEndDate(new Date());
	    defaultPageLoadCompleted = true;
	}else if (isSearchCriteriaNull() && noDateRangeSelectedOnPage){
	    Calendar fromCalendar = Calendar.getInstance();
	    fromCalendar.add(Calendar.MONTH, -1);
	    Calendar toCalendar = Calendar.getInstance();
	    toCalendar.add(Calendar.MONTH, 1);
	    schedulerReq.setUser(user);
	    schedulerReq.setStartDate(fromCalendar.getTime());
	    schedulerReq.setEndDate(toCalendar.getTime());

	}else if (!isSearchCriteriaNull()){

	    schedulerReq.setUser(user);
	    if (selectedSearchType == SelectedSearchType.DETAIL_SEARCH){
		logger.debug("DETAIL_SEARCH Criteria ::" + fromDate + ":" + toDate + ":" + firstName + ":" + lastName
			+ ": " + selectedAppointmentStatus);
		Customer customer = new Customer();
		schedulerReq.setStartDate(fromDate);
		schedulerReq.setEndDate(toDate);
		customer.setFirstName(firstName);
		customer.setLastName(lastName);
		schedulerReq.setCustomer(customer);
		if (selectedAppointmentStatus != null){
		    schedulerReq.setAppointmentStatusId(selectedAppointmentStatus.getKey());
		}
		if (selectedAppointmentType != null){
		    HashMap<String, String> appTypeMap = new HashMap<String, String>();
		    appTypeMap.put(selectedAppointmentType.getKey(), selectedAppointmentType.getValue());
		    schedulerReq.setAppointmentType(appTypeMap);
		}
		if (selectedDepartment != null){
		    HashMap<String, String> departmentMap = new HashMap<String, String>();
		    departmentMap.put(selectedDepartment.getKey(), selectedDepartment.getValue());
		    schedulerReq.setDepartment(departmentMap);
		}

	    }else if (selectedSearchType == SelectedSearchType.ID_SEARCH){
		if (StringUtils.isNotEmpty((appointmentId))){
		    schedulerReq.setAppointmentId(appointmentId);
		    logger.debug("ID_SEARCH Criteria :: " + appointmentId);
		}else{
		    return null;
		}
	    }
	}else if (getDailyRhythmPortalSession().getSchedulerSearchCriteria() != null){
	    schedulerReq = getDailyRhythmPortalSession().getSchedulerSearchCriteria();
	}

	getDailyRhythmPortalSession().setSchedulerSearchCriteria(schedulerReq);;
	return schedulerReq;
    }

    private void doMessage(AjaxRequestTarget target) {
	if (!messageModalPanel.isOpen()){
	    messageModalPanel.open(target);
	}
    }

    private void doPageSubmit(AjaxRequestTarget target) {

	if (!loadingModalPanel.isOpen()){
	    loadingModalPanel.setMessage(getString("schedulerViewManagePage.updatingStatus.message"));
	    loadingModalPanel.open(target);
	    target.appendJavaScript("clickButton('#submitButton');");
	    return;
	}

	LinkedHashMap<AppointmentCloseStatus, List<Appointment>> appointmentSelectionMap;

	logger.debug("pageDisplayMode................" + pageDisplayMode);

	if (pageDisplayMode == PageDisplayMode.SEARCH_MODE){

	    appointmentSelectionMap = new LinkedHashMap<AppointmentCloseStatus, List<Appointment>>();

	    appointmentSelectionMap.put(AppointmentCloseStatus.CANCEL, (ArrayList<Appointment>) cancelCheckGroup
		    .getDefaultModelObject());
	    appointmentSelectionMap.put(AppointmentCloseStatus.COMPLETE, (ArrayList<Appointment>) completeCheckGroup
		    .getDefaultModelObject());
	    appointmentSelectionMap.put(AppointmentCloseStatus.NO_SHOW, (ArrayList<Appointment>) noShowCheckGroup
		    .getDefaultModelObject());

	}else{
	    appointmentSelectionMap = appointmentIdSelectionMap;

	}

	loadingModalPanel.close(target);

	setResponsePage(new ViewAppointmentConfirmationPage(appointmentSelectionMap, statusMap));

    }

    private void doNewAppointment(AjaxRequestTarget target) {
	setResponsePage(CustomerInfoPage.class);

    }

    private void doReschedule(AjaxRequestTarget target) {
	SchedulerRequest schedulerReq = new SchedulerRequest();
	schedulerReq.setAppointmentId(selectedAppointmentId);
	getDailyRhythmPortalSession().setSchedulerReq(schedulerReq);
	setResponsePage(ChooseAppointmentPage.class);
    }

    @Override
    protected String getOnDomReadyJS() {
	// TODO Auto-generated method stub
	return null;
    }

    protected class AppointmentDetailDisplayPanel extends Panel {

	private static final long serialVersionUID = 1L;

	public AppointmentDetailDisplayPanel(String id, final IModel<?> model, final Appointment row) {
	    super(id, model);

	    Label label = new Label("appointmentId", new Model<String>(row.getAppointmentId()));

	    final AjaxLink<Object> appointmentLink = new AjaxLink<Object>("appointmentLink") {

		private static final long serialVersionUID = 1L;

		@Override
		public void onClick(AjaxRequestTarget target) {

		    pageDisplayMode = PageDisplayMode.VIEW_MODE;

		    selectedAppointmentId = row.getAppointmentId();

		    // Set to session for Reschedule option......

		    List<Appointment> selectedList = new ArrayList<Appointment>();
		    selectedList.add(row);
		    viewManageSelectPanel.setSelectedList(selectedList);
		    viewManageSelectPanel.reloadPage();
		    target.add(viewManageSelectPanel);
		    target.add(detailSearchTypeGroupContainer);
		    target.add(appointmentSearchContainer);
		    target.add(dataDisplayContainer);
		    target
			    .appendJavaScript("fixedHeaderTables();installClickHandlers();toggleResheduleButton(true);installSelectPanelClickHandlers();");

		}
	    };
	    appointmentLink.add(label);
	    this.add(appointmentLink);

	}
    }

    class CustomerNameDisplayPanel extends Panel {

	private static final long serialVersionUID = 1L;
	String name = na;

	public CustomerNameDisplayPanel(String id, final IModel<?> model, final Appointment row) {
	    super(id, model);
	    if (row != null){
		name = row.getLastName() + ", " + row.getFirstName();
		add(new Label("fullName", new Model<String>(name)));
	    }
	}
    }

    class DateTimeDisplayPanel extends Panel {

	private static final long serialVersionUID = 1L;

	public DateTimeDisplayPanel(String id, final IModel<?> model, final Appointment row, String type) {
	    super(id, model);

	    String dateTime = na;

	    Date date = row.getAppointmentDateTime();
	    if (type.equals("date")){
		dateTime = dateFmt.format(date);

	    }else if (type.equals("time")){
		dateTime = timeFmt.format(date);
	    }

	    add(new Label("aptDateTime", new Model<String>(dateTime)));
	}
    }

    class StatusDisplayPanel extends Panel {

	private static final long serialVersionUID = 1L;

	public StatusDisplayPanel(String id, final IModel<?> model, final Appointment row) {
	    super(id, model);

	    HashMap<AppointmentCloseStatus, List<String>> statusMap = (HashMap<AppointmentCloseStatus, List<String>>) row
		    .getAllowedStatus();
	    Iterator<Map.Entry<AppointmentCloseStatus, List<String>>> iter = statusMap.entrySet().iterator();
	    Map.Entry<AppointmentCloseStatus, List<String>> statusType = iter.next();
	    add(new Label("statusLabel", new Model<String>(statusType.getKey().getServiceValue())));

	}
    }

    class CancelCheckBoxDisplayPanel extends Panel {

	private static final long serialVersionUID = 1L;

	AppointmentCloseStatus appointmentCloseStatus;

	public CancelCheckBoxDisplayPanel(String id, final IModel<Appointment> model, final Appointment row) {
	    super(id, model);

	    final int existingStatus = row.getCurrentStatus();
	    if (existingStatus != 0){
		HashMap<AppointmentCloseStatus, List<String>> statusMap = (HashMap<AppointmentCloseStatus, List<String>>) row
			.getAllowedStatus();
		Iterator<Map.Entry<AppointmentCloseStatus, List<String>>> iter = statusMap.entrySet().iterator();
		while(iter.hasNext()){
		    Map.Entry<AppointmentCloseStatus, List<String>> statusType = iter.next();
		    if (statusType.getValue() != null && statusType.getValue().contains(String.valueOf(existingStatus))){
			appointmentCloseStatus = statusType.getKey();
			break;
		    }
		}
	    }
	    Check<Appointment> cancelCheck = new Check<Appointment>("cancelCheckboxType", model, cancelCheckGroup) {

		private static final long serialVersionUID = 1L;

		@Override
		public boolean isVisible() {
		    return existingStatus == 0;
		}
	    };

	    cancelCheck.setMarkupId("cancelCheckboxType");
	    cancelCheck.setOutputMarkupId(true);
	    cancelCheck.setOutputMarkupPlaceholderTag(true);

	    add(cancelCheck);

	    add(new Label("naLabel", new Model<String>(na)) {

		private static final long serialVersionUID = 1L;

		@Override
		public boolean isVisible() {
		    if (existingStatus == 0){
			return false;
		    }else{
			return appointmentCloseStatus == null
				|| appointmentCloseStatus == AppointmentCloseStatus.CANCEL
				|| appointmentCloseStatus == AppointmentCloseStatus.COMPLETE
				|| appointmentCloseStatus == AppointmentCloseStatus.NO_SHOW;
		    }
		}
	    });
	}
    }

    class CompleteCheckBoxDisplayPanel extends Panel {

	AppointmentCloseStatus appointmentCloseStatus;

	private static final long serialVersionUID = 1L;

	public CompleteCheckBoxDisplayPanel(String id, final IModel<Appointment> model, final Appointment row) {
	    super(id, model);

	    final int existingStatus = row.getCurrentStatus();
	    if (existingStatus != 0){
		HashMap<AppointmentCloseStatus, List<String>> statusMap = (HashMap<AppointmentCloseStatus, List<String>>) row
			.getAllowedStatus();
		Iterator<Map.Entry<AppointmentCloseStatus, List<String>>> iter = statusMap.entrySet().iterator();
		while(iter.hasNext()){
		    Map.Entry<AppointmentCloseStatus, List<String>> statusType = iter.next();
		    if (statusType.getValue() != null && statusType.getValue().contains(String.valueOf(existingStatus))){
			appointmentCloseStatus = statusType.getKey();
			break;
		    }
		}
	    }

	    Check<Appointment> completeCheck = new Check<Appointment>("completeCheckboxType", model, completeCheckGroup) {

		private static final long serialVersionUID = 1L;

		@Override
		public boolean isVisible() {
		    return existingStatus == 0;
		}
	    };

	    completeCheck.setMarkupId("completeCheckboxType");
	    completeCheck.setOutputMarkupId(true);
	    completeCheck.setOutputMarkupPlaceholderTag(true);
	    add(completeCheck);

	    add(new Label("naLabel", new Model<String>(na)) {

		private static final long serialVersionUID = 1L;

		@Override
		public boolean isVisible() {
		    if (existingStatus == 0){
			return false;
		    }else{
			return appointmentCloseStatus == null
				|| appointmentCloseStatus == AppointmentCloseStatus.CANCEL
				|| appointmentCloseStatus == AppointmentCloseStatus.NO_SHOW;
		    }
		}
	    });

	    add(new WebMarkupContainer("completedImg") {

		private static final long serialVersionUID = 1L;

		@Override
		public boolean isVisible() {
		    return appointmentCloseStatus == AppointmentCloseStatus.COMPLETE;
		}
	    });

	}
    }

    class NoShowCheckBoxDisplayPanel extends Panel {

	AppointmentCloseStatus appointmentCloseStatus;

	private static final long serialVersionUID = 1L;

	public NoShowCheckBoxDisplayPanel(String id, final IModel<Appointment> model, final Appointment row) {
	    super(id, model);

	    final int existingStatus = row.getCurrentStatus();

	    if (existingStatus != 0){
		HashMap<AppointmentCloseStatus, List<String>> statusMap = (HashMap<AppointmentCloseStatus, List<String>>) row
			.getAllowedStatus();
		Iterator<Map.Entry<AppointmentCloseStatus, List<String>>> iter = statusMap.entrySet().iterator();
		while(iter.hasNext()){
		    Map.Entry<AppointmentCloseStatus, List<String>> statusType = iter.next();
		    if (statusType.getValue() != null && statusType.getValue().contains(String.valueOf(existingStatus))){
			appointmentCloseStatus = statusType.getKey();
			break;
		    }
		}
	    }
	    Check<Appointment> noShowCheck = new Check<Appointment>("noShowCheckboxType", model, noShowCheckGroup) {

		private static final long serialVersionUID = 1L;

		@Override
		public boolean isVisible() {
		    return existingStatus == 0;
		}
	    };

	    noShowCheck.setMarkupId("noShowCheckboxType");
	    noShowCheck.setOutputMarkupId(true);
	    noShowCheck.setOutputMarkupPlaceholderTag(true);
	    add(noShowCheck);

	    add(new Label("naLabel", new Model<String>(na)) {

		private static final long serialVersionUID = 1L;

		@Override
		public boolean isVisible() {
		    if (existingStatus == 0){
			return false;
		    }else{
			return appointmentCloseStatus == null
				|| appointmentCloseStatus == AppointmentCloseStatus.CANCEL
				|| appointmentCloseStatus == AppointmentCloseStatus.COMPLETE
				|| appointmentCloseStatus == AppointmentCloseStatus.NO_SHOW;
		    }
		}
	    });
	}
    }

    class CancelCheckGroupSelector extends Panel {

	private static final long serialVersionUID = 1L;

	private boolean allCancelGroupChecked;

	public CancelCheckGroupSelector(String id) {
	    super(id);

	    CheckGroupSelector cancelGroupSelector = new CheckGroupSelector("cancelGroupSelector", cancelCheckGroup);
	    cancelGroupSelector.setMarkupId("cancelGroupSelector");
	    cancelGroupSelector.setOutputMarkupId(true);
	    cancelGroupSelector.setOutputMarkupPlaceholderTag(true);

	    add(cancelGroupSelector);
	    add(new Label("cancelLabel", new ResourceModel("schedulerViewManagePage.cancel.column")));

	}

	public boolean isAllCancelGroupChecked() {
	    return allCancelGroupChecked;
	}

	public void setAllCancelGroupChecked(boolean allCancelGroupChecked) {
	    this.allCancelGroupChecked = allCancelGroupChecked;
	}

    }

    class CompleteCheckGroupSelector extends Panel {

	private static final long serialVersionUID = 1L;

	public CompleteCheckGroupSelector(String id) {
	    super(id);

	    CheckGroupSelector completeGroupSelector = new CheckGroupSelector("completeGroupSelector",
		    completeCheckGroup);
	    completeGroupSelector.setMarkupId("completeGroupSelector");
	    completeGroupSelector.setOutputMarkupId(true);
	    completeGroupSelector.setOutputMarkupPlaceholderTag(true);

	    add(completeGroupSelector);
	    add(new Label("completeLabel", new ResourceModel("schedulerViewManagePage.complete.column")));

	}
    }

    class NoShowCheckGroupSelector extends Panel {

	private static final long serialVersionUID = 1L;

	public NoShowCheckGroupSelector(String id) {
	    super(id);

	    CheckGroupSelector noShowCheckboxSelector = new CheckGroupSelector("noShowCheckboxSelector",
		    noShowCheckGroup);

	    noShowCheckboxSelector.setMarkupId("noShowCheckboxSelector");
	    noShowCheckboxSelector.setOutputMarkupId(true);
	    noShowCheckboxSelector.setOutputMarkupPlaceholderTag(true);

	    add(noShowCheckboxSelector);
	    add(new Label("noShowLabel", new ResourceModel("schedulerViewManagePage.noshow.column")));

	}
    }

    private enum SelectedSearchType {

	DETAIL_SEARCH("Detail Search"), ID_SEARCH("Id Search");

	private final String label;

	private SelectedSearchType(String label) {
	    this.label = label;
	}

	@Override
	public String toString() {
	    return label;
	}
    }

    public String validateDateInBusinessRange(Date fromDate, Date toDate) {
	if (fromDate != null && toDate != null && fromDate.after(toDate)){
	    return getString("schedulerViewManagePage.invalidDateRange.message");
	}
	if (fromDate != null && Util.daysBetween(fromDate, new Date()) > 30){
	    return getString("schedulerViewManagePage.dateValidation.message");
	}
	return null;
    }

}
