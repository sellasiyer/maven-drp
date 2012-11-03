package com.bestbuy.bbym.ise.drp.beast.scheduler;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.ajax.markup.html.repeater.data.table.AjaxFallbackDefaultDataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bestbuy.bbym.ise.drp.common.BasePanel;
import com.bestbuy.bbym.ise.drp.domain.Appointment;
import com.bestbuy.bbym.ise.drp.domain.AppointmentCloseStatus;
import com.bestbuy.bbym.ise.drp.util.DateFormatter;
import com.bestbuy.bbym.ise.drp.util.FormatModel;
import com.bestbuy.bbym.ise.drp.util.panel.SpanPanel;

public class ViewManageSelectAppointmentPanel extends BasePanel {

    private static final long serialVersionUID = 1L;

    private static Logger logger = LoggerFactory.getLogger(ViewManageSelectAppointmentPanel.class);

    private List<Appointment> selectedList;

    private AjaxFallbackDefaultDataTable<Appointment> schedulerResultDataTable;

    final DateFormatter<Date> dateFmt = new DateFormatter<Date>("MM/dd/yy");

    final DateFormatter<Date> timeFmt = new DateFormatter<Date>("hh:mm a");

    private HashMap<AppointmentCloseStatus, List<Appointment>> appointmentSelectionMap;

    final String na = getString("notApplicable.label");

    private ManageAppointmentsDataProvider manageAppointmentsDataProvider = new ManageAppointmentsDataProvider();

    public ViewManageSelectAppointmentPanel(String id,
	    LinkedHashMap<AppointmentCloseStatus, List<Appointment>> appointmentSelectionMap) {
	super(id);
	this.appointmentSelectionMap = appointmentSelectionMap;
    }

    public void setSelectedList(List<Appointment> selectedList) {
	this.selectedList = selectedList;
    }

    public void reloadPage() {

	manageAppointmentsDataProvider.setSchedulerDataList(selectedList);

	final List<IColumn<Appointment>> columns = new ArrayList<IColumn<Appointment>>();

	columns.add(new PropertyColumn<Appointment>(new ResourceModel("schedulerViewManagePage.appointmnetId.column"),
		"appointmentId"));

	columns.add(new PropertyColumn<Appointment>(new ResourceModel("schedulerViewManagePage.order.column"),
		"orderNo"));

	columns.add(new PropertyColumn<Appointment>(new ResourceModel("schedulerViewManagePage.name.column"), "name") {

	    private static final long serialVersionUID = 1L;

	    @SuppressWarnings("rawtypes")
	    @Override
	    public void populateItem(Item cellItem, String componentId, IModel rowModel) {
		Appointment row = (Appointment) rowModel.getObject();
		cellItem.add(new ViewManageAppointmentPage().new CustomerNameDisplayPanel(componentId, rowModel, row));
	    }
	});

	columns.add(new PropertyColumn<Appointment>(new ResourceModel("schedulerViewManagePage.data.column"), "date") {

	    private static final long serialVersionUID = 1L;

	    @SuppressWarnings("rawtypes")
	    @Override
	    public void populateItem(Item cellItem, String componentId, IModel rowModel) {
		Appointment row = (Appointment) rowModel.getObject();
		cellItem.add(new ViewManageAppointmentPage().new DateTimeDisplayPanel(componentId, rowModel, row,
			"date"));
	    }
	});

	columns.add(new PropertyColumn<Appointment>(new ResourceModel("schedulerViewManagePage.time.column"), "time") {

	    private static final long serialVersionUID = 1L;

	    @SuppressWarnings("rawtypes")
	    @Override
	    public void populateItem(Item cellItem, String componentId, IModel rowModel) {
		Appointment row = (Appointment) rowModel.getObject();
		cellItem.add(new ViewManageAppointmentPage().new DateTimeDisplayPanel(componentId, rowModel, row,
			"time"));
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
		"status") {

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
			    status = getString("schedulerViewManagePage.pending.column");
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

	columns.add(new AbstractColumn<Appointment>(new ResourceModel("schedulerViewManagePage.cancel.column"),
		"cancel") {

	    private static final long serialVersionUID = 1L;

	    @SuppressWarnings("rawtypes")
	    @Override
	    public void populateItem(Item cellItem, String componentId, IModel rowModel) {
		Appointment row = (Appointment) rowModel.getObject();
		cellItem
			.add(new ViewManageSelectAppointmentPanel.CancelCheckBoxDisplayPanel(componentId, rowModel, row));
	    }

	    @Override
	    public boolean isSortable() {
		return false;
	    }

	});

	columns.add(new AbstractColumn<Appointment>(new ResourceModel("schedulerViewManagePage.complete.column"),
		"complete") {

	    private static final long serialVersionUID = 1L;

	    @SuppressWarnings("rawtypes")
	    @Override
	    public void populateItem(Item cellItem, String componentId, IModel rowModel) {
		Appointment row = (Appointment) rowModel.getObject();
		cellItem.add(new ViewManageSelectAppointmentPanel.CompleteCheckBoxDisplayPanel(componentId, rowModel,
			row));
	    }

	    @Override
	    public boolean isSortable() {
		return false;
	    }

	});

	columns.add(new AbstractColumn<Appointment>(new ResourceModel("schedulerViewManagePage.noshow.column"),
		"noshow") {

	    private static final long serialVersionUID = 1L;

	    @SuppressWarnings("rawtypes")
	    @Override
	    public void populateItem(Item cellItem, String componentId, IModel rowModel) {
		Appointment row = (Appointment) rowModel.getObject();
		cellItem
			.add(new ViewManageSelectAppointmentPanel.NoShowCheckBoxDisplayPanel(componentId, rowModel, row));
	    }

	    @Override
	    public boolean isSortable() {
		return false;
	    }

	});

	WebMarkupContainer schedularSelectedDisplayContainer = new WebMarkupContainer(
		"schedularSelectedDisplayContainer");
	schedulerResultDataTable = new AjaxFallbackDefaultDataTable<Appointment>("selectedSchedulerTable", columns,
		manageAppointmentsDataProvider, 200);
	schedulerResultDataTable.setOutputMarkupPlaceholderTag(true);
	schedularSelectedDisplayContainer.add(schedulerResultDataTable);
	addOrReplace(schedularSelectedDisplayContainer);

	WebMarkupContainer schedularSelectedTextContainer = new WebMarkupContainer("schedularSelectedTextContainer");

	Appointment appointment = (Appointment) selectedList.get(0);

	schedularSelectedTextContainer.add(new Label("appointmentId", new PropertyModel<String>(appointment,
		"appointmentId")));

	String fullNameVal = na;

	if (appointment.getFirstName() != null && appointment.getLastName() != null){
	    fullNameVal = appointment.getFirstName() + " " + appointment.getLastName();
	}

	schedularSelectedTextContainer.add(new Label("fullName", new Model<String>(fullNameVal)));
	schedularSelectedTextContainer.add(new Label("phoneNo", new PropertyModel<String>(appointment, "phoneNo")));
	schedularSelectedTextContainer.add(new Label("email", new PropertyModel<String>(appointment, "email")));
	schedularSelectedTextContainer.add(new Label("orderNo", new PropertyModel<String>(appointment, "orderNo")));
	schedularSelectedTextContainer.add(new Label("apptDate", new FormatModel<Date>(appointment
		.getAppointmentDateTime(), dateFmt, na)));
	schedularSelectedTextContainer.add(new Label("timeLabel", new FormatModel<Date>(appointment
		.getAppointmentDateTime(), timeFmt, na)));
	schedularSelectedTextContainer.add(new Label("reason", new PropertyModel<String>(appointment, "reason")));

	String reasonType = na;
	StringBuffer sb = new StringBuffer();
	if (appointment.getReasonType() != null && appointment.getReasonType().entrySet() != null){
	    final Iterator<Map.Entry<String, String>> mapIterator = appointment.getReasonType().entrySet().iterator();
	    while(mapIterator.hasNext()){
		Map.Entry<String, String> statusType = (Map.Entry<String, String>) mapIterator.next();
		sb.append(statusType.getValue());
	    }
	}else{
	    sb.append(reasonType);
	}
	schedularSelectedTextContainer.add(new Label("reasonTypeLabel", new Model<String>(sb.toString())));
	addOrReplace(schedularSelectedTextContainer);

    }

    class CancelCheckBoxDisplayPanel extends Panel {

	private static final long serialVersionUID = 1L;
	private boolean cancelChecked;
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

	    final CustomAjaxCheckBox cancelCheck = new CustomAjaxCheckBox("cancelCheckboxSubType",
		    new PropertyModel<Boolean>(this, "cancelChecked")) {

		private static final long serialVersionUID = 1L;

		@Override
		public boolean isVisible() {
		    return existingStatus == 0;
		}

		@Override
		protected void onUpdate(AjaxRequestTarget arg0) {
		    if (isChecked()){
			List<Appointment> datList = new ArrayList<Appointment>();
			datList.add(row);
			appointmentSelectionMap.put(AppointmentCloseStatus.CANCEL, datList);
			appointmentSelectionMap.put(AppointmentCloseStatus.COMPLETE, new ArrayList<Appointment>());
			appointmentSelectionMap.put(AppointmentCloseStatus.NO_SHOW, new ArrayList<Appointment>());
		    }else{
			if (appointmentSelectionMap.containsKey(AppointmentCloseStatus.CANCEL))
			    appointmentSelectionMap.remove(AppointmentCloseStatus.CANCEL);
		    }
		}
	    };

	    cancelCheck.setMarkupId("cancelCheckboxSubType");
	    cancelCheck.setOutputMarkupId(true);
	    cancelCheck.setOutputMarkupPlaceholderTag(true);

	    add(cancelCheck);

	    add(new Label("naLabel", new Model<String>(na)) {

		private static final long serialVersionUID = 1L;

		@Override
		public boolean isVisible() {
		    return appointmentCloseStatus == AppointmentCloseStatus.CANCEL
			    || appointmentCloseStatus == AppointmentCloseStatus.COMPLETE;
		}
	    });

	}

	public boolean isCancelChecked() {
	    return cancelChecked;
	}

	public void setCancelChecked(boolean cancelChecked) {
	    this.cancelChecked = cancelChecked;
	}

    }

    class CompleteCheckBoxDisplayPanel extends Panel {

	private static final long serialVersionUID = 1L;
	private boolean completeChecked;
	AppointmentCloseStatus appointmentCloseStatus;

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

	    final CustomAjaxCheckBox cancelCheck = new CustomAjaxCheckBox("completeCheckboxSubType",
		    new PropertyModel<Boolean>(this, "completeChecked")) {

		private static final long serialVersionUID = 1L;

		@Override
		public boolean isVisible() {
		    return existingStatus == 0;
		}

		@Override
		protected void onUpdate(AjaxRequestTarget arg0) {
		    if (isChecked()){
			List<Appointment> datList = new ArrayList<Appointment>();
			datList.add(row);
			appointmentSelectionMap.put(AppointmentCloseStatus.CANCEL, new ArrayList<Appointment>());
			appointmentSelectionMap.put(AppointmentCloseStatus.COMPLETE, datList);
			appointmentSelectionMap.put(AppointmentCloseStatus.NO_SHOW, new ArrayList<Appointment>());
		    }else{
			if (appointmentSelectionMap.containsKey(AppointmentCloseStatus.COMPLETE))
			    appointmentSelectionMap.remove(AppointmentCloseStatus.COMPLETE);
		    }
		}
	    };

	    cancelCheck.setMarkupId("completeCheckboxSubType");
	    cancelCheck.setOutputMarkupId(true);
	    cancelCheck.setOutputMarkupPlaceholderTag(true);

	    add(cancelCheck);

	    add(new Label("naLabel", new Model<String>(na)) {

		private static final long serialVersionUID = 1L;

		@Override
		public boolean isVisible() {
		    return appointmentCloseStatus == AppointmentCloseStatus.CANCEL;
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

	public boolean isCompleteChecked() {
	    return completeChecked;
	}

	public void setCompleteChecked(boolean completeChecked) {
	    this.completeChecked = completeChecked;
	}

    }

    class NoShowCheckBoxDisplayPanel extends Panel {

	private static final long serialVersionUID = 1L;
	private boolean noShowChecked;
	AppointmentCloseStatus appointmentCloseStatus;

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

	    final CustomAjaxCheckBox cancelCheck = new CustomAjaxCheckBox("noShowCheckboxSubType",
		    new PropertyModel<Boolean>(this, "noShowChecked")) {

		private static final long serialVersionUID = 1L;

		@Override
		public boolean isVisible() {
		    return existingStatus == 0;
		}

		@Override
		protected void onUpdate(AjaxRequestTarget arg0) {
		    if (isChecked()){
			List<Appointment> datList = new ArrayList<Appointment>();
			datList.add(row);
			appointmentSelectionMap.put(AppointmentCloseStatus.CANCEL, new ArrayList<Appointment>());
			appointmentSelectionMap.put(AppointmentCloseStatus.COMPLETE, new ArrayList<Appointment>());
			appointmentSelectionMap.put(AppointmentCloseStatus.NO_SHOW, datList);
		    }else{
			if (appointmentSelectionMap.containsKey(AppointmentCloseStatus.NO_SHOW))
			    appointmentSelectionMap.remove(AppointmentCloseStatus.NO_SHOW);
		    }
		}
	    };

	    cancelCheck.setMarkupId("noShowCheckboxSubType");
	    cancelCheck.setOutputMarkupId(true);
	    cancelCheck.setOutputMarkupPlaceholderTag(true);

	    add(cancelCheck);

	    add(new Label("naLabel", new Model<String>(na)) {

		private static final long serialVersionUID = 1L;

		@Override
		public boolean isVisible() {
		    return appointmentCloseStatus == AppointmentCloseStatus.CANCEL
			    || appointmentCloseStatus == AppointmentCloseStatus.COMPLETE;
		}
	    });

	}

	public boolean isNoShowChecked() {
	    return noShowChecked;
	}

	public void setNoShowChecked(boolean noShowChecked) {
	    this.noShowChecked = noShowChecked;
	}

    }
}
