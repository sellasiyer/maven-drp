package com.bestbuy.bbym.ise.drp.scoreboard;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.extensions.ajax.markup.html.repeater.data.table.AjaxFallbackDefaultDataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bestbuy.bbym.ise.drp.DailyRhythmPortalSession;
import com.bestbuy.bbym.ise.drp.domain.scoreboard.ScoreboardCategory;
import com.bestbuy.bbym.ise.drp.domain.scoreboard.ScoreboardDataItem;
import com.bestbuy.bbym.ise.drp.domain.scoreboard.ScoreboardEmployeeNotionalMargin;
import com.bestbuy.bbym.ise.drp.domain.scoreboard.ScoreboardTransactionType;
import com.bestbuy.bbym.ise.drp.service.ScoreboardService;
import com.bestbuy.bbym.ise.drp.util.DateFormatter;
import com.bestbuy.bbym.ise.drp.util.FormatPropertyColumn;
import com.bestbuy.bbym.ise.drp.util.MoneyFormatter;
import com.bestbuy.bbym.ise.exception.ServiceException;
import com.bestbuy.bbym.ise.util.Util;

public abstract class EmployeeDetailsModalPanel extends ScoreboardBaseModal {

    private static final long serialVersionUID = 1L;
    private static Logger logger = LoggerFactory.getLogger(EmployeeDetailsModalPanel.class);

    @SpringBean(name = "scoreboardService")
    private ScoreboardService scoreboardService;

    // State Data
    private ScoreboardEmployeeNotionalMargin employeeNotionalMargin;
    private List<ScoreboardDataItem> employeeSalesList;
    private boolean showWaiting = false;
    private String showWaitingMessage;
    private ScoreboardDataItem selectedSalesItem;
    private String serviceError;
    private int numItemsDeleted;

    // Model Data
    private String na = getString("notApplicable.label");
    private DateFormatter<Date> dateFmt = new DateFormatter<Date>("HH:mm a");
    private MoneyFormatter<BigDecimal> moneyFmt = new MoneyFormatter<BigDecimal>(true, false);

    // Wicket Elements
    private FeedbackPanel modalFeedback;
    private ScoreboardItemDataProvider itemDataProvider = new ScoreboardItemDataProvider();
    private WebMarkupContainer employeeSales, modalWaiting;
    private AjaxFallbackDefaultDataTable<ScoreboardDataItem> employeeSalesTable;
    private AjaxLink<Void> editLink;

    public EmployeeDetailsModalPanel(String id, List<ScoreboardCategory> categoryList) {
	super(id, categoryList);

	showFooter = false;

	modalFeedback = new FeedbackPanel("modalFeedback");
	modalFeedback.setOutputMarkupPlaceholderTag(true);
	mainform.add(modalFeedback);

	final Label headerLabel = new Label("headerLabel", new Model<String>() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public String getObject() {
		if (showColumns){
		    return getString("scoreboardModal.employeeDetails.label");
		}
		return getString("scoreboardModal.employeeSales.label");
	    }
	});
	mainform.add(headerLabel);

	final Label employeeNameLabel = new Label("employeeName", new Model<String>() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public String getObject() {
		if (employeeNotionalMargin == null || employeeNotionalMargin.getEmployeeShift() == null
			|| employeeNotionalMargin.getEmployeeShift().getEmpFullName() == null){
		    return na;
		}
		return employeeNotionalMargin.getEmployeeShift().getEmpFullName();
	    }
	});
	mainform.add(employeeNameLabel);

	editLink = new AjaxLink<Void>("editLink") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onClick(AjaxRequestTarget target) {
		logger.debug("editLink onClick");
		showColumns = !showColumns;
		if (showColumns){
		    launchAction("showEmplDetailsView", target);
		}else{
		    launchAction("showEmplSalesList", target);
		}
	    }
	};
	editLink.setOutputMarkupPlaceholderTag(true);
	mainform.add(editLink);

	final Label editLinkLabel = new Label("editLinkLabel", new Model<String>() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public String getObject() {
		if (showColumns){
		    return getString("scoreboardModal.editSales.button");
		}
		return getString("scoreboardModal.viewEmployeeDetails.button");
	    }
	});
	editLink.add(editLinkLabel);

	final List<IColumn<ScoreboardDataItem>> columns = new ArrayList<IColumn<ScoreboardDataItem>>();
	columns.add(new FormatPropertyColumn<ScoreboardDataItem, Date>(new ResourceModel(
		"scoreboardModal.itemEnteredTime.column"), "transactionDate", "transactionDate", dateFmt, na));
	columns.add(new PropertyColumn<ScoreboardDataItem>(new ResourceModel("scoreboardModal.itemGroup.column"),
		"groupName", "groupName"));
	columns.add(new PropertyColumn<ScoreboardDataItem>(new ResourceModel("scoreboardModal.itemName.column"),
		"name", "name"));
	columns.add(new PropertyColumn<ScoreboardDataItem>(new ResourceModel("scoreboardModal.itemQuantity.column"),
		"editQuantity", "editQuantity"));
	columns.add(new FormatPropertyColumn<ScoreboardDataItem, BigDecimal>(new ResourceModel(
		"scoreboardModal.itemUnitPrice.column"), "editUnitPrice", "editUnitPrice", moneyFmt, ""));
	columns.add(new AbstractColumn<ScoreboardDataItem>(null, "name") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void populateItem(Item<ICellPopulator<ScoreboardDataItem>> cellItem, String componentId,
		    IModel<ScoreboardDataItem> rowModel) {
		ScoreboardDataItem row = (ScoreboardDataItem) rowModel.getObject();
		cellItem.add(new EmployeeDetailsModalPanel.DeleteLinkPanel(componentId, rowModel, row));
	    }
	});

	employeeSales = new WebMarkupContainer("employeeSales") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public boolean isVisible() {
		if (showWaiting){
		    return false;
		}
		return !showColumns;
	    }
	};
	employeeSales.setMarkupId("employeeSales");
	employeeSales.setOutputMarkupId(true);
	employeeSales.setOutputMarkupPlaceholderTag(true);
	mainform.add(employeeSales);

	employeeSalesTable = new AjaxFallbackDefaultDataTable<ScoreboardDataItem>("employeeSalesTable", columns,
		itemDataProvider, 200) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected Item<ScoreboardDataItem> newRowItem(String id, int index, final IModel<ScoreboardDataItem> model) {
		final Item<ScoreboardDataItem> item = super.newRowItem(id, index, model);
		ScoreboardDataItem row = (ScoreboardDataItem) model.getObject();
		if (row.isDeleted()){
		    item.add(AttributeModifier.append("class", new Model<String>("deleted")));
		}
		return item;
	    }
	};
	employeeSalesTable.setMarkupId("employeeSalesTable");
	employeeSalesTable.setOutputMarkupId(true);
	employeeSalesTable.setOutputMarkupPlaceholderTag(true);
	employeeSales.add(employeeSalesTable);

	modalWaiting = new WebMarkupContainer("modalWaiting") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public boolean isVisible() {
		return showWaiting;
	    }
	};
	modalWaiting.setOutputMarkupPlaceholderTag(true);
	mainform.add(modalWaiting);

	final Label modalWaitingLabel = new Label("modalWaitingLabel", new Model<String>() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public String getObject() {
		return showWaitingMessage;
	    }
	});
	modalWaiting.add(modalWaitingLabel);
    }

    @Override
    protected void submitHandler(AjaxRequestTarget target, Form<?> form) {
    }

    @Override
    public ScoreboardModalColumnPanel getLeftPanel(String id, List<ScoreboardCategory> categoryList) {
	List<ScoreboardCategory> myList = new ArrayList<ScoreboardCategory>();
	myList.addAll(categoryList.subList(0, getNumberOfCategoriesOnLeftside()));
	ScoreboardModalColumnPanel panel = new EmployeeDetailsColumnPanel(id, myList);
	return panel;
    }

    @Override
    public ScoreboardModalColumnPanel getRightPanel(String id, List<ScoreboardCategory> categoryList) {
	List<ScoreboardCategory> myList = new ArrayList<ScoreboardCategory>();
	myList.addAll(categoryList.subList(getNumberOfCategoriesOnLeftside(), categoryList.size()));
	ScoreboardModalColumnPanel panel = new EmployeeDetailsColumnPanel(id, myList);
	return panel;
    }

    @Override
    public void update(AjaxRequestTarget target) {
	if (!showColumns){
	    itemDataProvider.setScoreboardDataItemList(employeeSalesList);
	    employeeSalesTable.modelChanged();
	}
	target.add(tableListingMarkupContainer);
	target.add(editLink);
	target.add(employeeSales);
	target.add(mainform);
	target.add(modalWaiting);
	target.add(modalFeedback);
    }

    public void setEmployeeSales(List<ScoreboardDataItem> employeeSalesList) {
	this.employeeSalesList = employeeSalesList;
    }

    public ScoreboardEmployeeNotionalMargin getEmployeeNotionalMargin() {
	return employeeNotionalMargin;
    }

    public void setEmployeeNotionalMargin(ScoreboardEmployeeNotionalMargin employeeNotionalMargin) {
	this.employeeNotionalMargin = employeeNotionalMargin;
    }

    class DeleteLinkPanel extends Panel {

	private static final long serialVersionUID = 1L;

	public DeleteLinkPanel(String id, final IModel<?> model, final ScoreboardDataItem row) {
	    super(id, model);
	    final AjaxLink<Object> link = new AjaxLink<Object>("deleteLink") {

		private static final long serialVersionUID = 1L;

		@Override
		public void onClick(AjaxRequestTarget target) {
		    logger.debug("deleteLinkPanel onClick");
		    selectedSalesItem = (ScoreboardDataItem) Util.clone(row);
		    launchAction("deleteEmplSalesItem", target);
		}
	    };
	    if (row.isDeleted()){
		link.setVisibilityAllowed(false);
	    }
	    add(link);
	}
    }

    @Override
    public void resetAfterClose() {
	showColumns = true;
	showWaiting = false;
    }

    @Override
    public String getRefreshModalJS() {
	StringBuilder refreshModalJS = new StringBuilder();
	if (!showColumns){
	    refreshModalJS.append("tablePrep(employeeSalesTable);");
	}
	return refreshModalJS.toString();
    }

    @Override
    public boolean canHandleAction(String action) {
	if ("showEmplDetailsView".equals(action)){
	    return true;
	}else if ("showEmplSalesList".equals(action)){
	    return true;
	}else if ("deleteEmplSalesItem".equals(action)){
	    return true;
	}else if ("handleEmplDetailsError".equals(action)){
	    return true;
	}
	return false;
    }

    @Override
    public void doAction(String action, AjaxRequestTarget target) {
	final DailyRhythmPortalSession session = getDailyRhythmPortalSession();
	if ("showEmplDetailsView".equals(action)){
	    refresh(target);

	}else if ("showEmplSalesList".equals(action)){
	    refresh(target);

	}else if ("handleEmplDetailsError".equals(action)){
	    modalFeedback.error(serviceError);
	    showWaiting = false;
	    refresh(target);
	    serviceError = null;

	}else if ("deleteEmplSalesItem".equals(action)){
	    if (!showWaiting){
		showWaitingMessage = "Deleting sales item";
		showWaiting = true;
		refresh(target);
		launchAction("deleteEmplSalesItem", target);
		return;
	    }

	    try{
		logger.debug("Before call to delete sales item");
		scoreboardService.makeTransactionStatusDeleted(selectedSalesItem, session.getDrpUser());
		logger.debug("After call to delete sales item");
	    }catch(ServiceException se){
		logger.error("Failed to delete scoreboard sales item: ServiceException is " + se.getFullMessage());
		serviceError = getString("scoreboardPage.deleteSalesItemFailed.message.label") + " " + se.getMessage();
		launchAction("handleEmplDetailsError", target);
		return;
	    }
	    numItemsDeleted++;

	    List<ScoreboardCategory> catList = null;
	    List<ScoreboardDataItem> salesList = null;
	    try{
		logger.debug("Before call to get employee details data");
		catList = scoreboardService.getScoreboardItems(session.getDrpUser().getStoreId(),
			employeeNotionalMargin.getEmployeeShift().getEmpDailyNtlMrgnId(), session.getDrpUser()
				.getStore().getStoreType(), operationType, ScoreboardTransactionType.EMP_SALES_DETAILS);
		ScoreboardPage.addNotionalMarginToCategoryList(employeeNotionalMargin, catList, this);

		salesList = scoreboardService.getEmployeeSalesTransactions(session.getDrpUser().getStoreId(), session
			.getDrpUser().getStore().getStoreType(), operationType, employeeNotionalMargin
			.getEmployeeShift());
		logger.debug("After call to get employee details data");
	    }catch(ServiceException se){
		logger.error("Failed to get employee details scoreboard data: ServiceException is "
			+ se.getFullMessage());
		serviceError = getString("scoreboardPage.getScoreboardDataFailed.message.label") + " "
			+ se.getMessage();
		launchAction("handleEmplDetailsError", target);
		return;
	    }
	    showWaiting = false;
	    employeeSalesList = salesList;
	    refreshCategoryData(catList);
	    refresh(target);
	}
    }

    public int getNumItemsDeleted() {
	return numItemsDeleted;
    }

    public void reset() {
	numItemsDeleted = 0;
    }
}
