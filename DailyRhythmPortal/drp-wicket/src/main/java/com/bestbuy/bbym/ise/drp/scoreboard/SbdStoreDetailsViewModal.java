package com.bestbuy.bbym.ise.drp.scoreboard;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.behavior.SimpleAttributeModifier;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.PropertyModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bestbuy.bbym.ise.drp.DailyRhythmPortalSession;
import com.bestbuy.bbym.ise.drp.domain.DrpUser;
import com.bestbuy.bbym.ise.drp.domain.scoreboard.ScoreboardCategory;
import com.bestbuy.bbym.ise.drp.domain.scoreboard.ScoreboardDataItem;
import com.bestbuy.bbym.ise.drp.domain.scoreboard.ScoreboardNotionalMargin;
import com.bestbuy.bbym.ise.drp.util.DateFormatter;
import com.bestbuy.bbym.ise.drp.util.FormatPropertyModel;
import com.bestbuy.bbym.ise.exception.ServiceException;

public class SbdStoreDetailsViewModal extends ScoreboardBaseModal {

    private static final long serialVersionUID = 1L;

    // State Data
    private ScoreboardNotionalMargin notionalMargin;
    private Logger logger = LoggerFactory.getLogger(SbdStoreDetailsViewModal.class);

    private String serviceError;

    WebMarkupContainer returnsContainer;
    AjaxLink<Void> editButton;

    private List<ScoreboardDataItem> salesList = new ArrayList<ScoreboardDataItem>();
    ListView<ScoreboardDataItem> tableListView;

    public SbdStoreDetailsViewModal(String id, List<ScoreboardCategory> categoryList) {
	super(id, categoryList);

	submitButton.setVisible(false);
	submitButton.setEnabled(false);

	editButton = new AjaxLink<Void>("editButton") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onClick(AjaxRequestTarget target) {
		// target.appendJavaScript("alert('button clicked');");
		showColumns = false;
		refreshViewMode(target);
	    }

	};
	editButton.setOutputMarkupId(true);
	editButton.setMarkupId("edit"); // toms markup has this, not sure if
	// important.)
	mainform.add(editButton);

	returnsContainer = new WebMarkupContainer("returnsContainer");
	returnsContainer.setVisible(false); // hidden at first.
	mainform.add(returnsContainer);

	tableListView = new ListView<ScoreboardDataItem>("tableListView", salesList) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void populateItem(final ListItem<ScoreboardDataItem> item) {
		final ScoreboardDataItem dataItem = item.getModelObject();

		String na = getString("notApplicable.label");
		DateFormatter<Date> dateFmt = new DateFormatter<Date>("HH:mm a");
		FormatPropertyModel<Date, Date> transDateModel = new FormatPropertyModel<Date, Date>(
			new PropertyModel<Date>(dataItem, "transactionDate"), dateFmt, na);
		Label postedTimeLabel = new Label("postedTimeLabel", transDateModel);
		item.add(postedTimeLabel);

		Label groupLabel = new Label("groupLabel", new PropertyModel<String>(dataItem, "groupName"));
		item.add(groupLabel);

		Label itemNameLabel = new Label("itemNameLabel", new PropertyModel<String>(dataItem, "name"));
		item.add(itemNameLabel);

		Label qtyLabel = new Label("qtyLabel", new PropertyModel<Integer>(dataItem, "returnQuantity"));
		item.add(qtyLabel);

		Label unitPriceLabel = new Label("unitPriceLabel", new PropertyModel<BigDecimal>(dataItem,
			"editUnitPrice"));
		item.add(unitPriceLabel);

		AjaxLink<Void> deleteRowButton = new AjaxLink<Void>("deleteRowButton") {

		    private static final long serialVersionUID = 1L;

		    @Override
		    public void onClick(AjaxRequestTarget target) {

			target.add(item);
			logger.debug("deleteLink onClick");
			this.setVisible(false);
			// launchAction("deleteReturnsItem", target);

			try{
			    scoreboardService.makeTransactionStatusDeleted(dataItem, getDailyRhythmPortalSession()
				    .getDrpUser());
			    item.add(new SimpleAttributeModifier("class", "deleted"));
			}catch(ServiceException se){
			    logger.error("Failed to delete scoreboard returns item: ServiceException is "
				    + se.getFullMessage());
			    serviceError = getString("scoreboardPage.deleteSalesItemFailed.message.label") + " "
				    + se.getMessage();
			}

		    }

		};
		deleteRowButton.setVisibilityAllowed(false);
		item.add(deleteRowButton);

		if (dataItem.isDeleted()){
		    item.add(new SimpleAttributeModifier("class", "deleted"));
		}

	    }

	};
	returnsContainer.add(tableListView);

    }

    @Override
    protected void submitHandler(AjaxRequestTarget target, Form<?> form) {
	// TODO Auto-generated method stub

    }

    @Override
    public ScoreboardModalColumnPanel getLeftPanel(String id, List<ScoreboardCategory> categoryList) {
	List<ScoreboardCategory> myList = new ArrayList<ScoreboardCategory>();
	if (categoryList.size() > 0)
	    myList.addAll(categoryList.subList(0, getNumberOfCategoriesOnLeftside()));
	ScoreboardModalColumnPanel panel = new SbdStoreDetailsViewColumnPanel(id, myList);
	return panel;

    }

    @Override
    public ScoreboardModalColumnPanel getRightPanel(String id, List<ScoreboardCategory> categoryList) {
	List<ScoreboardCategory> myList = new ArrayList<ScoreboardCategory>();
	if (categoryList.size() > 0)
	    myList.addAll(categoryList.subList(getNumberOfCategoriesOnLeftside(), categoryList.size()));
	ScoreboardModalColumnPanel panel = new SbdStoreDetailsViewColumnPanel(id, myList);
	return panel;
    }

    @Override
    public void update(AjaxRequestTarget target) {
	target.add(mainform);

    }

    @Override
    public void onClose(AjaxRequestTarget target) {
	showColumns = true;
	refreshViewMode(target);
    }

    public ScoreboardNotionalMargin getNotionalMargin() {
	return notionalMargin;
    }

    public void setNotionalMargin(ScoreboardNotionalMargin notionalMargin) {
	this.notionalMargin = notionalMargin;
    }

    private void refreshViewMode(AjaxRequestTarget target) {
	target.add(mainform);
	if (showColumns){ // READMODE
	    editButton.setEnabled(true);
	    editButton.setVisible(true);
	    returnsContainer.setVisible(false);
	}else{ // WRITE
	    editButton.setEnabled(false);
	    editButton.setVisible(false);
	    returnsContainer.setVisible(true);
	    retrieveSalesList(target);
	}
    }

    private void retrieveSalesList(AjaxRequestTarget target) {
	DrpUser user = getDailyRhythmPortalSession().getDrpUser();

	try{
	    salesList = scoreboardService.getStoreReturnsTransactions(user.getStoreId(),
		    user.getStore().getStoreType(), operationType);
	}catch(ServiceException se){
	    logger.error("Failed to get Store Details Returns data: ServiceException is " + se.getFullMessage());
	    target.appendJavaScript("alert('There was an error while retrieving returns data');");

	}

	tableListView.setList(salesList);
	tableListView.modelChanged();
    }

}
