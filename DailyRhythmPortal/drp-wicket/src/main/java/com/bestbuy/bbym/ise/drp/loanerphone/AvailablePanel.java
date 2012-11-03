package com.bestbuy.bbym.ise.drp.loanerphone;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.extensions.ajax.markup.html.repeater.data.table.AjaxFallbackDefaultDataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
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
import com.bestbuy.bbym.ise.drp.domain.DrpUser;
import com.bestbuy.bbym.ise.drp.domain.LoanerPhoneCondition;
import com.bestbuy.bbym.ise.drp.domain.OperatingSystem;
import com.bestbuy.bbym.ise.drp.domain.PhoneModel;
import com.bestbuy.bbym.ise.drp.helpers.PhoneModelSearchCriteria;
import com.bestbuy.bbym.ise.drp.service.LoanerPhoneService;
import com.bestbuy.bbym.ise.exception.ServiceException;

public class AvailablePanel extends BasePanel implements Loadable {

    private static final long serialVersionUID = 1L;
    private static Logger logger = LoggerFactory.getLogger(AvailablePanel.class);

    @SpringBean(name = "loanerPhoneService")
    private LoanerPhoneService loanerPhoneService;

    private AvailablePanel thisPanel;
    private Carrier selectedCarrier;
    private OperatingSystem selectedOS;
    private List<OperatingSystem> osDDList = new ArrayList<OperatingSystem>();
    private List<Carrier> carrierDDList = new ArrayList<Carrier>();

    private PhoneModelDataProvider dataProvider = new PhoneModelDataProvider(null);
    private AjaxFallbackDefaultDataTable<PhoneModel> availableTable;

    private static final String ALL_CARRIERS = "All Carriers";
    private static final String ALL_OPERATING_SYSTEMS = "All OS";

    public AvailablePanel(String id, final FeedbackPanel feedbackPanel) {
	super(id);
	thisPanel = this;

	Form<Object> form = new Form<Object>("availableForm");
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
	    String message = "An unexpected exception occured while attempting to get the carrier , os filter";
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

	final List<IColumn<PhoneModel>> columns = new ArrayList<IColumn<PhoneModel>>();
	columns.add(new AbstractColumn<PhoneModel>(new ResourceModel("lptable.available.carrier.column"), "carrier") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void populateItem(Item<ICellPopulator<PhoneModel>> cellItem, String componentId,
		    IModel<PhoneModel> rowModel) {
		cellItem.add(new IconPanel<PhoneModel>(componentId, rowModel));
	    }

	    @Override
	    public String getCssClass() {
		return "carrier_col";
	    }
	});
	columns.add(new PropertyColumn<PhoneModel>(new ResourceModel("lptable.available.os.column"), "os.os", "os.os") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public String getCssClass() {
		return "operatingSystem_col";
	    }
	});
	columns.add(new PropertyColumn<PhoneModel>(new ResourceModel("lptable.available.make.column"), "make", "make") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public String getCssClass() {
		return "make_col";
	    }
	});
	columns.add(new AbstractColumn<PhoneModel>(new ResourceModel("lptable.available.model.column"), "model") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void populateItem(Item<ICellPopulator<PhoneModel>> cellItem, String componentId,
		    IModel<PhoneModel> rowModel) {
		cellItem.add(new ModelCellPanel(componentId, rowModel));
	    }
	});
	columns.add(new PropertyColumn<PhoneModel>(new ResourceModel("lptable.available.quantity.column"),
		"noOfPhones", "noOfPhones") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public String getCssClass() {
		return "numAvailable_col";
	    }
	});
	columns.add(new PropertyColumn<PhoneModel>(new ResourceModel("lptable.available.damagedqty.column"),
		"noOfDamaged", "noOfDamaged") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected IModel<?> createLabelModel(IModel<PhoneModel> rowModel) {
		PhoneModel pm = (PhoneModel) rowModel.getObject();
		IModel<?> m = new PropertyModel<PhoneModel>(pm, this.getPropertyExpression());
		Integer num = (Integer) m.getObject();
		if (num.intValue() <= 0){
		    m = new Model<String>(new String(""));
		}
		return m;
	    }

	    @Override
	    public String getCssClass() {
		return "numDamaged_col";
	    }
	});
	columns.add(new AbstractColumn<PhoneModel>(null, "checkedout") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void populateItem(Item<ICellPopulator<PhoneModel>> cellItem, String componentId,
		    IModel<PhoneModel> rowModel) {
		PhoneModel row = (PhoneModel) rowModel.getObject();
		cellItem.add(new CheckedOutLinkPanel(componentId, rowModel, row, feedbackPanel));
	    }

	    @Override
	    public String getCssClass() {
		return "checkout_col";
	    }
	});

	availableTable = new AjaxFallbackDefaultDataTable<PhoneModel>("availableTable", columns, dataProvider, 200);
	availableTable.setMarkupId("availableTable");
	availableTable.setOutputMarkupId(true);
	availableTable.setOutputMarkupPlaceholderTag(true);
	add(availableTable);

	AjaxButton filterButton = new AjaxButton("filterSubmit", new ResourceModel("availableForm.filter.button")) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
		loadData();
		target.add(availableTable);
		target.appendJavaScript("setupAvailableTable();");
		target.appendJavaScript("setupTableSorting('#availableTable', 'setupAvailableTable();');");
	    }

	    @Override
	    protected void onError(AjaxRequestTarget target, Form<?> form) {
		// TODO Implement me!
	    }
	};
	filterButton.setOutputMarkupPlaceholderTag(true);
	form.add(filterButton);

	loadData();
    }

    @Override
    public void renderHead(IHeaderResponse response) {
	super.renderHead(response);
	StringBuilder onDomReadyJS = new StringBuilder();
	onDomReadyJS.append("setupAvailableTable();");
	onDomReadyJS.append("setupTableSorting('#availableTable', 'setupAvailableTable();');");
	logger.debug("onDomReadyJS=" + onDomReadyJS);
	response.renderOnDomReadyJavaScript(onDomReadyJS.toString());
    }

    @Override
    public void loadData() {
	final DrpUser user = getDailyRhythmPortalSession().getDrpUser();

	PhoneModelSearchCriteria criteria = new PhoneModelSearchCriteria();
	if (selectedCarrier != null && selectedCarrier.getId() != null){
	    logger.debug("selectedCarrier=" + selectedCarrier.getCarrier());
	    criteria.setCarrier(selectedCarrier);
	}
	if (selectedOS != null && selectedOS.getId() != null){
	    logger.debug("selectedOS=" + selectedOS.getOs());
	    criteria.setOperatingSystem(selectedOS);
	}

	List<PhoneModel> phoneModelList = new ArrayList<PhoneModel>();
	try{
	    if (isDummyStoreEnabled()){
		// In CAP QA env, only is configured
		phoneModelList = loanerPhoneService.getPhoneModels(getDummyStoreNum(), criteria);
	    }else{
		phoneModelList = loanerPhoneService.getPhoneModels(user.getStoreId(), criteria);
	    }
	}catch(ServiceException e){
	    String message = "An unexpected exception occured while attempting to get the phone Models with the filter criteria";
	    logger.error(message, e);
	    processException(message, e, getPage().getPageParameters());
	    return;
	}

	dataProvider.setPhoneList(phoneModelList);
	availableTable.modelChanged();
    }

    class CheckedOutLinkPanel extends Panel {

	private static final long serialVersionUID = 1L;

	public CheckedOutLinkPanel(String id, final IModel<PhoneModel> model, final PhoneModel row,
		final FeedbackPanel feedbackPanel) {
	    super(id, model);

	    AjaxLink<PhoneModel> checkOutLink = new AjaxLink<PhoneModel>("checkOutLink") {

		private static final long serialVersionUID = 1L;

		@Override
		public void onClick(AjaxRequestTarget target) {
		    if (row != null && row.getNoOfPhones() == 0){
			error(getString("availableForm.checkOutLink.damaged.error.label"));
			target.add(feedbackPanel);
			target.appendJavaScript("scrollToTop();");
		    }else{
			PhoneModel phoneModel = new PhoneModel();
			BeanUtils.copyProperties(row, phoneModel);
			setResponsePage(new LoanerPhoneCheckedOutPage(null, phoneModel, getPage(), thisPanel));
		    }
		}
	    };
	    add(checkOutLink);
	}
    }

    class ModelCellPanel extends Panel {

	private static final long serialVersionUID = 1L;

	public ModelCellPanel(String id, IModel<PhoneModel> model) {
	    super(id, model);
	    PhoneModel pm = model.getObject();
	    Label phoneLabel = new Label("damagedPhoneLabel", new String(""));
	    if (pm.getCondition() != null){
		// if condition is damaged or lost, display sad face.
		if (pm.getCondition() == LoanerPhoneCondition.DAMAGED || pm.getCondition() == LoanerPhoneCondition.LOST){
		    phoneLabel.add(AttributeModifier.replace("class", "damaged"));
		}
	    }
	    add(phoneLabel);
	    add(new Label("modelLabel", pm.getModel()));
	}
    }
}
