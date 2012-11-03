package com.bestbuy.bbym.ise.drp.loanerphone;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.bestbuy.bbym.ise.drp.common.BaseWebPage;
import com.bestbuy.bbym.ise.drp.domain.Phone;
import com.bestbuy.bbym.ise.drp.helpers.PhoneSearchCriteria;
import com.bestbuy.bbym.ise.drp.service.LoanerPhoneService;
import com.bestbuy.bbym.ise.exception.ServiceException;

public class InventoryPrintPage extends BaseWebPage {

    private static final long serialVersionUID = 1L;

    @SpringBean(name = "loanerPhoneService")
    private LoanerPhoneService loanerPhoneService;

    public InventoryPrintPage(PageParameters parameters) {

	String storeId = getDailyRhythmPortalSession().getDrpUser().getStoreId();
	List<Phone> phoneList = null;
	try{
	    phoneList = loanerPhoneService.getPhones(storeId, new PhoneSearchCriteria());
	}catch(ServiceException se){
	    String message = "An unexpected exception occured while attempting to get the list of phones";
	    processException(message, se.getErrorCode());
	    return;
	}
	Label storeLabel = new Label("storeLabel", "Store " + storeId);
	add(storeLabel);

	String datestr = new SimpleDateFormat("'Printed:' MM/dd/yyyy").format(new Date());
	Label titleLabel = new Label("titleLabel", "Loaner Phone Inventory Report - Store: " + storeId + " " + datestr);
	add(titleLabel);

	final List<IColumn<Phone>> columns = new ArrayList<IColumn<Phone>>();
	columns
		.add(new PropertyColumn<Phone>(new ResourceModel("lptable.inventory.carrier.column"), "carrier.carrier"));

	columns.add(new PropertyColumn<Phone>(new ResourceModel("lptable.inventory.os.column"), "operatingSystem.os"));

	columns.add(new PropertyColumn<Phone>(new ResourceModel("lptable.inventory.make.column"), "make"));

	columns.add(new PropertyColumn<Phone>(new ResourceModel("lptable.inventory.model.column"), "modelNumber"));

	columns.add(new PropertyColumn<Phone>(new ResourceModel("lptable.inventory.condition.column"),
		"condition.label"));

	columns.add(new PropertyColumn<Phone>(new ResourceModel("lptable.inventory.imei.column"), "serialNumber"));

	// copying the 'columns' list as the checked-out table has an additional
	// column.
	final List<IColumn<Phone>> coColumns = new ArrayList<IColumn<Phone>>(columns);
	coColumns.add(new PropertyColumn<Phone>(new ResourceModel("lptable.inventory.print.dayscheckedout.column"),
		"latestCheckOutCheckInHistory.noOfDaysOut"));

	List<List<Phone>> temp = splitLists(phoneList);
	List<Phone> availableList = temp.get(0);
	List<Phone> checkedoutList = temp.get(1);

	PhoneDataProvider availablePhoneDataProvider = new PhoneDataProvider(availableList);
	PhoneDataProvider checkedoutPhoneDataProvider = new PhoneDataProvider(checkedoutList);
	DefaultDataTable<Phone> availableTable = new DefaultDataTable<Phone>("availableTable", columns,
		availablePhoneDataProvider, Integer.MAX_VALUE);
	add(availableTable);

	DefaultDataTable<Phone> checkedoutTable = new DefaultDataTable<Phone>("checkedoutTable", coColumns,
		checkedoutPhoneDataProvider, Integer.MAX_VALUE);
	checkedoutTable.setMarkupId("checkedoutTable");
	checkedoutTable.setOutputMarkupId(true);
	add(checkedoutTable);

    }

    /**
     * Routes items from phoneList into separate lists. available phones (not
     * checked out) will be in element 0. checked out phones will be in element
     * 1.
     * 
     * @param phoneList
     *            the list to separate into available/checked out lists.
     * @return A list of two elements containing available-0. checkedout-1.
     */
    private List<List<Phone>> splitLists(List<Phone> phoneList) {

	List<Phone> availableList = new ArrayList<Phone>();
	List<Phone> checkedoutList = new ArrayList<Phone>();
	List<List<Phone>> lists = new ArrayList<List<Phone>>(2);
	lists.add(0, availableList);
	lists.add(1, checkedoutList);

	for(Phone p: phoneList){
	    if (p.getLatestCheckOutCheckInHistory() != null
		    && p.getLatestCheckOutCheckInHistory().getNoOfDaysOut() <= 0){
		availableList.add(p);
	    }else{ // not checked out
		checkedoutList.add(p);
	    }
	}

	return lists;
    }

}
