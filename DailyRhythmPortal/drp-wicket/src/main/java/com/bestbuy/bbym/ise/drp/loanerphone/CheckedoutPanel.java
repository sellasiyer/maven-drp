package com.bestbuy.bbym.ise.drp.loanerphone;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.OnChangeAjaxBehavior;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.extensions.ajax.markup.html.repeater.data.table.AjaxFallbackDefaultDataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import com.bestbuy.bbym.ise.drp.common.BasePanel;
import com.bestbuy.bbym.ise.drp.domain.Carrier;
import com.bestbuy.bbym.ise.drp.domain.CheckOutCheckInHistory;
import com.bestbuy.bbym.ise.drp.domain.DrpUser;
import com.bestbuy.bbym.ise.drp.domain.OperatingSystem;
import com.bestbuy.bbym.ise.drp.domain.Phone;
import com.bestbuy.bbym.ise.drp.helpers.PhoneSearchCriteria;
import com.bestbuy.bbym.ise.drp.service.LoanerPhoneService;
import com.bestbuy.bbym.ise.exception.ServiceException;

public class CheckedoutPanel extends BasePanel implements Loadable {

    private static final long serialVersionUID = 1L;
    private static Logger logger = LoggerFactory.getLogger(CheckedoutPanel.class);

    @SpringBean(name = "loanerPhoneService")
    private LoanerPhoneService loanerPhoneService;

    private PhoneDataProvider phoneDataProvider = new PhoneDataProvider(null);
    private AjaxFallbackDefaultDataTable<Phone> checkedOutTable;

    private Carrier selectedCarrier;
    private OperatingSystem selectedOS;
    private String selectedServiceOrderNo;

    private List<OperatingSystem> osDDList = new ArrayList<OperatingSystem>();
    private List<Carrier> carrierDDList = new ArrayList<Carrier>();

    private static final String ALL_CARRIERS = "All Carriers";
    private static final String ALL_OPERATING_SYSTEMS = "All OS";
    private static final String LAST_4SERVICE_ORDER_NO = "Last 4 of Service Order";

    private NotesDialogPanel notesModal;
    private POS4PartKeyDialogPanel posKeyModal;

    public CheckedoutPanel(String id, final FeedbackPanel feedbackPanel) {
	super(id);

	Form<Object> form = new Form<Object>("checkedoutForm");
	add(form);

	add(new POS4PartKeyDialogPanel("pos4partkeymodal", new CheckOutCheckInHistory()));
	add(new NotesDialogPanel("checkedOutNotesModal", new CheckOutCheckInHistory()));

	try{
	    selectedCarrier = new Carrier();
	    selectedCarrier.setCarrier(ALL_CARRIERS);
	    carrierDDList = loanerPhoneService.getCarriers();
	    carrierDDList.add(0, selectedCarrier);
	    selectedOS = new OperatingSystem();
	    selectedOS.setOs(ALL_OPERATING_SYSTEMS);
	    osDDList = loanerPhoneService.getOperatingSystems();
	    osDDList.add(0, selectedOS);
	}catch(ServiceException se){
	    String message = "An unexpected exception occured while attempting to get the carriers and operating systems";
	    logger.error(message, se);
	    processException(message, se, getPage().getPageParameters());
	    return;
	}

	ChoiceRenderer<Carrier> carrierRenderer = new ChoiceRenderer<Carrier>("carrier", "carrier");

	final DropDownChoice<Carrier> carrierDropDown = new DropDownChoice<Carrier>("carrierFilter",
		new PropertyModel<Carrier>(this, "selectedCarrier"), carrierDDList, carrierRenderer);
	form.add(carrierDropDown);

	ChoiceRenderer<OperatingSystem> osRenderer = new ChoiceRenderer<OperatingSystem>("os", "os");

	final DropDownChoice<OperatingSystem> osDropDown = new DropDownChoice<OperatingSystem>("osFilter",
		new PropertyModel<OperatingSystem>(this, "selectedOS"), osDDList, osRenderer);
	form.add(osDropDown);

	final TextField<String> soNumber = new TextField<String>("soNumber", new PropertyModel<String>(this,
		"selectedServiceOrderNo"));
	soNumber.setOutputMarkupPlaceholderTag(true);

	soNumber.add(new Behavior() {

	    private static final long serialVersionUID = 1L;

	    public void onComponentTag(Component component, ComponentTag tag) {
		tag.put("data-prefill", LAST_4SERVICE_ORDER_NO);
	    }
	});

	form.add(soNumber);

	final List<IColumn<Phone>> columns = new ArrayList<IColumn<Phone>>();
	columns.add(new AbstractColumn<Phone>(new ResourceModel("lptable.checkedout.carrier.column"), "carrier") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void populateItem(Item<ICellPopulator<Phone>> cellItem, String componentId, IModel<Phone> rowModel) {
		cellItem.add(new IconPanel<Phone>(componentId, rowModel));
	    }

	    @Override
	    public String getCssClass() {
		return "carrier_col";
	    }
	});
	columns.add(new PropertyColumn<Phone>(new ResourceModel("lptable.checkedout.os.column"), "operatingSystem.os",
		"operatingSystem.os") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public String getCssClass() {
		return "operatingSystem_col";
	    }
	});
	columns.add(new PropertyColumn<Phone>(new ResourceModel("lptable.checkedout.make.column"), "make", "make") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public String getCssClass() {
		return "make_col";
	    }
	});
	columns.add(new PropertyColumn<Phone>(new ResourceModel("lptable.checkedout.model.column"), "modelNumber",
		"modelNumber") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public String getCssClass() {
		return "model_col";
	    }
	});
	columns.add(new PropertyColumn<Phone>(new ResourceModel("lptable.checkedout.serviceorder.column"),
		"latestCheckOutCheckInHistory.ServiceOrderNo", "latestCheckOutCheckInHistory.ServiceOrderNo"));
	columns.add(new PropertyColumn<Phone>(new ResourceModel("lptable.checkedout.customer.column"),
		"latestCheckOutCheckInHistory.lastName", "latestCheckOutCheckInHistory.lastName") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public String getCssClass() {
		return "customer_col";
	    }
	});
	columns.add(new PropertyColumn<Phone>(new ResourceModel("lptable.checkedout.daysout.column"),
		"latestCheckOutCheckInHistory.noOfDaysOut", "latestCheckOutCheckInHistory.noOfDaysOut") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public String getCssClass() {
		return "numDaysOut_col";
	    }
	});
	columns.add(new PropertyColumn<Phone>(new ResourceModel("lptable.checkedout.lastupdatedby.column"), "lastName",
		"lastName") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public String getCssClass() {
		return "lastUpdatedBy_col";
	    }
	});
	columns.add(new AbstractColumn<Phone>(null, "checkinColumn") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void populateItem(Item<ICellPopulator<Phone>> cellItem, String componentId, IModel<Phone> rowModel) {
		Phone row = (Phone) rowModel.getObject();
		cellItem.add(new ActionsLinkPanel(componentId, rowModel, row));
	    }

	    @Override
	    public String getCssClass() {
		return "checkin_col";
	    }
	});

	checkedOutTable = new AjaxFallbackDefaultDataTable<Phone>("checkedOutTable", columns, phoneDataProvider, 200);
	checkedOutTable.setMarkupId("checkedOutTable");
	checkedOutTable.setOutputMarkupId(true);
	checkedOutTable.setOutputMarkupPlaceholderTag(true);
	add(checkedOutTable);

	// submit button
	AjaxButton filterButton = new AjaxButton("filterSubmit", new ResourceModel("availableForm.filter.button")) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
		logger.info("On clicking  filter button...");

		if (selectedServiceOrderNo != null && !LAST_4SERVICE_ORDER_NO.equalsIgnoreCase(selectedServiceOrderNo)){
		    // check if is all digits
		    String NUM_REGEX = "[0-9]+";

		    if (!selectedServiceOrderNo.matches(NUM_REGEX)){
			error(getString("lpTabs.panel.checkedoutForm.soNumber.Validator"));
			return;
		    }
		}
		loadData();
		target.add(checkedOutTable);
		if (selectedServiceOrderNo == null){
		    selectedServiceOrderNo = LAST_4SERVICE_ORDER_NO;
		}
		//target.add(soNumber);
	    }

	    @Override
	    protected void onError(AjaxRequestTarget target, Form<?> form) {
		logger.error("in submit of the filter onError...");
		target.add(feedbackPanel);
	    }

	};
	filterButton.setOutputMarkupPlaceholderTag(true);
	form.add(filterButton);

	loadData();

	add(new AbstractDefaultAjaxBehavior() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void respond(AjaxRequestTarget target) {
	    }

	    @Override
	    public void renderHead(Component component, IHeaderResponse response) {
		super.renderHead(component, response);
		StringBuilder onDomReadyJS = new StringBuilder();
		onDomReadyJS.append("setupCheckedOutTable();");
		onDomReadyJS.append("setupTableSorting('#checkedOutTable', 'setupCheckedOutTable();');");
		logger.debug("onDomReadyJS=" + onDomReadyJS);
		response.renderOnDomReadyJavaScript(onDomReadyJS.toString());
	    }
	});
    } // end of constructor

    @Override
    public void loadData() {
	logger.debug("in loadData");
	try{
	    final DrpUser user = getDailyRhythmPortalSession().getDrpUser();

	    PhoneSearchCriteria criteria = new PhoneSearchCriteria();
	    criteria.setCheckedOut(true);
	    if (selectedCarrier != null && selectedCarrier.getId() != null){
		criteria.setCarrier(selectedCarrier);
	    }
	    if (selectedOS != null && selectedOS.getId() != null){
		criteria.setOperatingSystem(selectedOS);
	    }

	    if (selectedServiceOrderNo != null && !LAST_4SERVICE_ORDER_NO.equalsIgnoreCase(selectedServiceOrderNo)){
		criteria.setLast4ServiceOrderNo(selectedServiceOrderNo);
	    }
	    if (isDummyStoreEnabled()){
		phoneDataProvider.setPhoneList(loanerPhoneService.getPhones(getDummyStoreNum(), criteria));
	    }else{
		phoneDataProvider.setPhoneList(loanerPhoneService.getPhones(user.getStoreId(), criteria));
	    }

	}catch(ServiceException se){
	    String message = "An unexpected exception occured while attempting to get the phone Models with the filter criteria";
	    logger.error(message, se);
	    processException(message, se, getPage().getPageParameters());
	    return;
	}
	checkedOutTable.modelChanged();
    }

    class ActionsLinkPanel extends Panel {

	private static final long serialVersionUID = 1L;

	private ActionChoice selectedChoice;

	public ActionsLinkPanel(String id, IModel<Phone> model, final Phone phoneRow) {
	    super(id, model);

	    List<ActionChoice> choiceList = Arrays.asList(ActionChoice.values());
	    phoneRow.getLatestCheckOutCheckInHistory();

	    final DropDownChoice<ActionChoice> actionsChoice = new DropDownChoice<ActionChoice>("actionsChoice",
		    new PropertyModel<ActionChoice>(this, "selectedChoice"), choiceList) {

		private static final long serialVersionUID = 1L;

		@Override
		protected CharSequence getDefaultChoice(String selectedValue) {
		    return ActionChoice.ACTIONS.toString();
		}
	    };
	    add(actionsChoice);
	    actionsChoice.add(new OnChangeAjaxBehavior() {

		private static final long serialVersionUID = 1L;

		@Override
		protected void onUpdate(AjaxRequestTarget target) {
		    logger.info("onChange response.");

		    switch (selectedChoice) {
			case CHECKIN: {

			    Phone phone = new Phone();
			    BeanUtils.copyProperties(phoneRow, phone);
			    setResponsePage(new LoanerPhoneCheckInPage(phone, getPage(), CheckedoutPanel.this));
			}
			    break;
			case NOTES:
			    notesModal = new NotesDialogPanel("checkedOutNotesModal", phoneRow
				    .getLatestCheckOutCheckInHistory()) {

				private static final long serialVersionUID = 1L;

				@Override
				public void onClose(AjaxRequestTarget target) {
				    if (isOk()){
					try{
					    loanerPhoneService
						    .updateHistory(phoneRow.getLatestCheckOutCheckInHistory());
					}catch(ServiceException e){
					    logger.error("Error updating checked out history", e);
					}
				    }
				}

			    };
			    notesModal.setOutputMarkupPlaceholderTag(true);
			    CheckedoutPanel.this.addOrReplace(notesModal);
			    notesModal.open(target);
			    break;
			case POS4PARTKEY:
			    posKeyModal = new POS4PartKeyDialogPanel("pos4partkeymodal", phoneRow
				    .getLatestCheckOutCheckInHistory()) {

				private static final long serialVersionUID = 1L;

				@Override
				public void onClose(AjaxRequestTarget target) {
				    if (isOk()){
					try{
					    loanerPhoneService
						    .updateHistory(phoneRow.getLatestCheckOutCheckInHistory());
					}catch(ServiceException e){
					    logger.error("Error updating checked out history", e);
					}
				    }

				}
			    };
			    posKeyModal.setOutputMarkupPlaceholderTag(true);
			    CheckedoutPanel.this.addOrReplace(posKeyModal);
			    posKeyModal.open(target);

			    break;
			case VIEWDETAILS: {
			    Phone phone = new Phone();
			    BeanUtils.copyProperties(phoneRow, phone);
			    setResponsePage(new ViewCheckedOutPage(phone, getPage()));
			    break;
			}
			default:
		    }
		}

	    });
	}

	public ActionChoice getSelectedChoice() {
	    return this.selectedChoice;
	}

	public void setSelectedChoice(ActionChoice chosen) {
	    this.selectedChoice = chosen;
	}

    }

    enum ActionChoice {
	ACTIONS("Actions"), CHECKIN("Check In"), POS4PARTKEY("POS 4-Part Key"), NOTES("Notes"), VIEWDETAILS(
		"View Details");

	private String label;

	private ActionChoice(String label) {
	    this.label = label;
	}

	@Override
	public String toString() {
	    return this.label;
	}
    }

}
