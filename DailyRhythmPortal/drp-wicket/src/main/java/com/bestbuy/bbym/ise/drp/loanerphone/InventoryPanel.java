package com.bestbuy.bbym.ise.drp.loanerphone;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.extensions.ajax.markup.html.repeater.data.table.AjaxFallbackDefaultDataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
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
import com.bestbuy.bbym.ise.drp.domain.LoanerPhoneCondition;
import com.bestbuy.bbym.ise.drp.domain.OperatingSystem;
import com.bestbuy.bbym.ise.drp.domain.Phone;
import com.bestbuy.bbym.ise.drp.helpers.PhoneSearchCriteria;
import com.bestbuy.bbym.ise.drp.service.LoanerPhoneService;
import com.bestbuy.bbym.ise.drp.util.SelectItem;
import com.bestbuy.bbym.ise.drp.utils.DrpConstants;
import com.bestbuy.bbym.ise.exception.ServiceException;

@AuthorizeInstantiation( {DrpConstants.DRP_ADMIN, DrpConstants.DRP_MANAGER, DrpConstants.DRP_LEAD })
public class InventoryPanel extends BasePanel implements Loadable {

    private static final long serialVersionUID = 1L;
    private static Logger logger = LoggerFactory.getLogger(InventoryPanel.class);

    @SpringBean(name = "loanerPhoneService")
    private LoanerPhoneService loanerPhoneService;

    private Carrier selectedCarrier;
    private OperatingSystem selectedOS;
    private String selectedImeiEsn;
    private SelectItem<String> availabilitySelected;
    private List<OperatingSystem> osDDList = new ArrayList<OperatingSystem>();
    private List<Carrier> carrierDDList = new ArrayList<Carrier>();

    private PhoneDataProvider phoneDataProvider = new PhoneDataProvider(null);
    private AjaxFallbackDefaultDataTable<Phone> inventoryTable;
    private InventoryPanel thisPanel;

    private static final String ALL_CARRIERS = "All Carriers";
    private static final String ALL_OPERATING_SYSTEMS = "All OS";
    private static final String ALL_AVAILABILITIES = "All Availabilities";
    private static final String LAST_4IMEI_ESN = "Last 4 of IMEI/ESN";
    private static final String CHECKED_OUT = "Checked Out";

    public InventoryPanel(String id, final FeedbackPanel feedbackPanel) {
	super(id);
	thisPanel = this;

	Form<Object> form = new Form<Object>("inventoryForm");
	add(form);

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

	List<SelectItem<String>> availablitySelections = new ArrayList<SelectItem<String>>();
	// TODO Pull labels from properties
	availablitySelections.add(new SelectItem<String>("All", "All Availabilities"));
	availablitySelections.add(new SelectItem<String>("Available", "Available"));
	availablitySelections.add(new SelectItem<String>("Checked Out", "Checked Out"));

	for(SelectItem<String> selected: availablitySelections){
	    if ("All".equals(selected.getKey())){
		availabilitySelected = selected;
		break;
	    }
	}

	ChoiceRenderer<Carrier> carrierRenderer = new ChoiceRenderer<Carrier>("carrier", "carrier");

	final DropDownChoice<Carrier> carrierDropDown = new DropDownChoice<Carrier>("carrierFilter",
		new PropertyModel<Carrier>(this, "selectedCarrier"), carrierDDList, carrierRenderer);
	form.add(carrierDropDown);

	ChoiceRenderer<OperatingSystem> osRenderer = new ChoiceRenderer<OperatingSystem>("os", "os");

	final DropDownChoice<OperatingSystem> osDropDown = new DropDownChoice<OperatingSystem>("osFilter",
		new PropertyModel<OperatingSystem>(this, "selectedOS"), osDDList, osRenderer);
	form.add(osDropDown);

	ChoiceRenderer<SelectItem<String>> availabilityRenderer = new ChoiceRenderer<SelectItem<String>>("value", "key");
	DropDownChoice<SelectItem<String>> availabilityDropDown = new DropDownChoice<SelectItem<String>>(
		"availabilityFilter", new PropertyModel<SelectItem<String>>(this, "availabilitySelected"),
		availablitySelections, availabilityRenderer);
	form.add(availabilityDropDown);

	final TextField<String> imeiFilter = new TextField<String>("imeiFilter", new PropertyModel<String>(this,
		"selectedImeiEsn"));
	imeiFilter.setOutputMarkupPlaceholderTag(true);

	imeiFilter.add(new Behavior() {

	    private static final long serialVersionUID = 1L;

	    public void onComponentTag(Component component, ComponentTag tag) {
		tag.put("data-prefill", LAST_4IMEI_ESN);
	    }
	});

	form.add(imeiFilter);

	final List<IColumn<Phone>> columns = new ArrayList<IColumn<Phone>>();
	columns.add(new AbstractColumn<Phone>(new ResourceModel("lptable.inventory.carrier.column"), "carrier") {

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
	columns.add(new PropertyColumn<Phone>(new ResourceModel("lptable.inventory.os.column"), "operatingSystem.os",
		"operatingSystem.os") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public String getCssClass() {
		return "operatingSystem_col";
	    }
	});
	columns.add(new PropertyColumn<Phone>(new ResourceModel("lptable.inventory.make.column"), "make", "make") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public String getCssClass() {
		return "make_col";
	    }
	});
	columns.add(new AbstractColumn<Phone>(new ResourceModel("lptable.inventory.model.column"), "modelNumber") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void populateItem(Item<ICellPopulator<Phone>> cellItem, String componentId, IModel<Phone> rowModel) {
		cellItem.add(new ModelCellPanel(componentId, rowModel));
	    }

	    @Override
	    public String getCssClass() {
		return "model_col";
	    }
	});
	columns.add(new PropertyColumn<Phone>(new ResourceModel("lptable.inventory.imei.column"), "serialNumber",
		"serialNumber"));
	columns.add(new PropertyColumn<Phone>(new ResourceModel("lptable.inventory.customer.column"),
		"latestCheckOutCheckInHistory.lastName", "latestCheckOutCheckInHistory.lastName") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected IModel<?> createLabelModel(IModel<Phone> rowModel) {

		Phone phn = (Phone) rowModel.getObject();
		IModel<?> m = new PropertyModel<String>(phn, this.getPropertyExpression());
		CheckOutCheckInHistory chist = phn.getLatestCheckOutCheckInHistory();
		if (chist == null || (chist != null && !chist.isCheckedOut())){
		    // do not show customer name unless phone is checked out.
		    m = new Model<String>(new String(""));
		}
		return m;
	    }

	    @Override
	    public String getCssClass() {
		return "customer_col";
	    }
	});
	columns.add(new PropertyColumn<Phone>(new ResourceModel("lptable.inventory.daysout.column"),
		"latestCheckOutCheckInHistory.noOfDaysOut", "latestCheckOutCheckInHistory.noOfDaysOut") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected IModel<?> createLabelModel(IModel<Phone> rowModel) {

		Phone phn = (Phone) rowModel.getObject();
		IModel<?> m = new PropertyModel<Phone>(phn, this.getPropertyExpression());
		if (phn.getLatestCheckOutCheckInHistory() != null
			&& phn.getLatestCheckOutCheckInHistory().getNoOfDaysOut() <= 0){
		    // FIXME: something weird happening here from service call.
		    // should fix disease not symptoms.
		    m = new Model<String>(new String(""));
		}
		return m;
	    }

	    @Override
	    public String getCssClass() {
		return "numDaysOut_col";
	    }
	});
	columns.add(new PropertyColumn<Phone>(new ResourceModel("lptable.inventory.lastupdatedby.column"), "lastName",
		"lastName") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public String getCssClass() {
		return "lastUpdatedBy_col";
	    }
	});
	if (getDailyRhythmPortalSession().isAuthorizedToInstantiate(EditPage.class)){
	    columns.add(new AbstractColumn<Phone>(null, "editLinkColumn") {

		private static final long serialVersionUID = 1L;

		@Override
		public void populateItem(Item<ICellPopulator<Phone>> cellItem, String componentId,
			IModel<Phone> rowModel) {
		    Phone row = (Phone) rowModel.getObject();
		    cellItem.add(new EditLinkPanel(componentId, rowModel, row, feedbackPanel));
		}

		@Override
		public String getCssClass() {
		    return "edit_col";
		}
	    });
	}
	columns.add(new AbstractColumn<Phone>(null, "viewLinkColumn") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void populateItem(Item<ICellPopulator<Phone>> cellItem, String componentId, IModel<Phone> rowModel) {
		Phone row = (Phone) rowModel.getObject();
		cellItem.add(new ViewLinkPanel(componentId, rowModel, row));
		cellItem.add(new AttributeAppender("class", new Model<String>("last"), " "));
	    }

	    @Override
	    public String getCssClass() {
		return "view_col";
	    }
	});

	inventoryTable = new AjaxFallbackDefaultDataTable<Phone>("inventoryTable", columns, phoneDataProvider, 200);
	inventoryTable.setMarkupId("inventoryTable");
	inventoryTable.setOutputMarkupId(true);
	inventoryTable.setOutputMarkupPlaceholderTag(true);
	add(inventoryTable);

	AjaxButton filterButton = new AjaxButton("filterSubmit", new ResourceModel("availableForm.filter.button")) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
		logger.debug("filterSubmit onSubmit");
		if (selectedImeiEsn != null && !LAST_4IMEI_ESN.equalsIgnoreCase(selectedImeiEsn)){
		    // check if is all digits
		    String NUM_REGEX = "[0-9A-Za-z]+";
		    if (!selectedImeiEsn.matches(NUM_REGEX)){
			error(getString("lpTabs.panel.inventoryForm.imeiFilter.Validator"));
			return;
		    }
		}
		loadData();
		if (selectedImeiEsn == null){
		    selectedImeiEsn = LAST_4IMEI_ESN;
		}
		target.add(inventoryTable);
		//target.add(imeiFilter);
		target.appendJavaScript("setupInventoryTable();");
		target.appendJavaScript("setupTableSorting('#inventoryTable', 'setupInventoryTable();');");
	    }

	    @Override
	    protected void onError(AjaxRequestTarget target, Form<?> form) {
		logger.debug("filterSubmit onError");
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
		onDomReadyJS.append("setupInventoryTable();");
		onDomReadyJS.append("setupTableSorting('#inventoryTable', 'setupInventoryTable();');");
		logger.debug("onDomReadyJS=" + onDomReadyJS);
		response.renderOnDomReadyJavaScript(onDomReadyJS.toString());
	    }
	});
    }

    @Override
    public void loadData() {
	logger.debug("loadData");
	final DrpUser user = getDailyRhythmPortalSession().getDrpUser();
	List<Phone> phoneList = new ArrayList<Phone>();
	try{
	    PhoneSearchCriteria criteria = buildCriteria();
	    if (isDummyStoreEnabled()){
		phoneList = loanerPhoneService.getPhones(getDummyStoreNum(), criteria);
		// For CAP, QA environment only is configured
	    }else{
		phoneList = loanerPhoneService.getPhones(user.getStoreId(), criteria);
	    }
	}catch(ServiceException e){
	    String message = "An unexpected exception occured while attempting to get the inventory phones with the filter criteria";
	    logger.error(message, e);
	    processException(message, e, getPage().getPageParameters());
	    return;
	}
	phoneDataProvider.setPhoneList(phoneList);
	inventoryTable.modelChanged();
    }

    private PhoneSearchCriteria buildCriteria() {
	PhoneSearchCriteria criteria = new PhoneSearchCriteria();
	if (selectedCarrier != null && selectedCarrier.getId() != null){
	    logger.debug("selectedCarrier=" + selectedCarrier.getCarrier());
	    criteria.setCarrier(selectedCarrier);
	}
	if (selectedOS != null && selectedOS.getId() != null){
	    logger.debug("selectedOS=" + selectedOS.getOs());
	    criteria.setOperatingSystem(selectedOS);
	}

	if (selectedImeiEsn != null && !LAST_4IMEI_ESN.equalsIgnoreCase(selectedImeiEsn)){
	    logger.debug("selectedImeiEsn=" + selectedImeiEsn);
	    criteria.setLast4SerialNo(selectedImeiEsn);
	}

	logger.debug("availabilitySelected=" + availabilitySelected.getValue());

	if (availabilitySelected != null && !ALL_AVAILABILITIES.equals(availabilitySelected.getValue())){
	    criteria.setCheckedOut(CHECKED_OUT.equalsIgnoreCase(availabilitySelected.getValue()));
	}
	return criteria;
    }

    class EditLinkPanel extends Panel {

	private static final long serialVersionUID = 1L;

	public EditLinkPanel(String id, IModel<Phone> model, final Phone row, final FeedbackPanel feedbackPanel) {
	    super(id, model);

	    add(new AjaxLink<Phone>("editLink") {

		private static final long serialVersionUID = 1L;

		@Override
		public void onClick(AjaxRequestTarget target) {
		    if (row != null && row.getLatestCheckOutCheckInHistory() != null
			    && row.getLatestCheckOutCheckInHistory().isCheckedOut()){
			error(getString("inventoryForm.editLink.checkedOutPhone.error.label"));
			target.add(feedbackPanel);
			target.appendJavaScript("scrollToTop();");
		    }else{
			Phone phone = new Phone();
			BeanUtils.copyProperties(row, phone);
			setResponsePage(new EditPage(phone, getPage(), thisPanel, null, null));
		    }
		}
	    });
	}

    }

    class ViewLinkPanel extends Panel {

	private static final long serialVersionUID = 1L;

	public ViewLinkPanel(String id, IModel<Phone> model, final Phone row) {
	    super(id, model);

	    add(new AjaxLink<Phone>("viewLink") {

		private static final long serialVersionUID = 1L;

		@Override
		public void onClick(AjaxRequestTarget target) {
		    Phone phone = new Phone();
		    BeanUtils.copyProperties(row, phone);
		    setResponsePage(new ViewPage(phone, getPage(), thisPanel));
		}
	    });
	}
    }

    class ModelCellPanel extends Panel {

	private static final long serialVersionUID = 1L;

	public ModelCellPanel(String id, IModel<Phone> model) {
	    super(id, model);
	    Phone phn = model.getObject();
	    Label phoneLabel = new Label("damagedPhoneLabel", new String(""));

	    if (phn.getCondition() != null){ // if condition is damaged or lost,
		// display sad face.
		if (phn.getCondition() == LoanerPhoneCondition.DAMAGED
			|| phn.getCondition() == LoanerPhoneCondition.LOST){
		    phoneLabel.add(AttributeModifier.replace("class", "damaged"));
		}
	    }
	    add(phoneLabel);
	    add(new Label("modelLabel", phn.getModelNumber()));
	}
    }

}
