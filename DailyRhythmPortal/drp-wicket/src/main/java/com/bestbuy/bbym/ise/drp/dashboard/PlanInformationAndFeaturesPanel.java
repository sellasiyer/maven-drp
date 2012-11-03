package com.bestbuy.bbym.ise.drp.dashboard;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.bestbuy.bbym.ise.domain.CarrierPlan;
import com.bestbuy.bbym.ise.domain.Customer;
import com.bestbuy.bbym.ise.domain.Line;
import com.bestbuy.bbym.ise.drp.common.BasePanel;
import com.bestbuy.bbym.ise.drp.service.DashboardDataService;
import com.bestbuy.bbym.ise.drp.util.PhoneFormatter;

public class PlanInformationAndFeaturesPanel extends BasePanel {

    private static final long serialVersionUID = 1L;

    private static final String BLANK_VALUE = "----";
    @SpringBean(name = "dashboardDataService")
    private DashboardDataService dashboardDataService;

    public PlanInformationAndFeaturesPanel(String id) {
	super(id);

	Customer customer = getDailyRhythmPortalSession().getCustomer();
	if (customer == null || customer.getSubscription() == null || customer.getSubscription().getLines() == null){
	    setResponsePage(CustomerDashboardPage.class);
	    return;
	}

	Label customerNameLabel = new Label("customerNameLabel", new PropertyModel<String>(customer, "nameString"));
	add(customerNameLabel);

	Label customerContactPhoneLabel = new Label("customerContactPhoneLabel", new PropertyModel<String>(customer,
		"contactPhone"));
	add(customerContactPhoneLabel);

	Label customerAcctNumberLabel = new Label("customerAcctNumberLabel", new PropertyModel<String>(customer,
		"acctNumber"));
	add(customerAcctNumberLabel);

	WebMarkupContainer carrierLogo = new WebMarkupContainer("carrierLogo");
	String clazz = null;
	switch (customer.getSubscription().getCarrier()) {
	    case ATT:
		clazz = "att";
		break;
	    case SPRINT:
		clazz = "sprint";
		break;
	    case TMOBILE:
		clazz = "tmobile";
		break;
	    case VERIZON:
		clazz = "verizon";
		break;
	}
	carrierLogo.add(new AttributeAppender("class", new Model<String>(clazz), " "));

	Label carrierHiddenLogo = new Label("carrierHiddenLogo", customer.getSubscription().getCarrier().toString()
		.toUpperCase());
	carrierLogo.add(carrierHiddenLogo);
	add(carrierLogo);

	List<List<Object>> data = dashboardDataService.getPlansAndOptionsTable(customer);

	RepeatingView featureRowRepeater = new RepeatingView("featureRowRepeater");
	for(int i = 1; i < data.size() - 1; ++i){
	    String name = data.get(i).get(0).toString();
	    ArrayList<Object> list = new ArrayList<Object>();
	    list.add(name);
	    TableColumnRepeater row = new TableColumnRepeater(featureRowRepeater.newChildId(),
		    new MyColumnFactory(list));
	    applyClass(row, i - 1, data.size() - 2);
	    featureRowRepeater.add(row);
	}
	add(featureRowRepeater);

	RepeatingView headerRepeater = new RepeatingView("headerRepeater");
	List<Object> headerRow = data.get(0);
	for(int i = 1; i < headerRow.size() - 1; ++i){
	    Component component = null;
	    if (Line.class.isAssignableFrom(headerRow.get(i).getClass())){
		Line line = (Line) headerRow.get(i);
		PhoneFormatter<Object> formatter = new PhoneFormatter<Object>();
		String formattedPhoneNumber = formatter.format(line != null?line.getMobileNumber():null);
		if (lineIsPrimary(line)){
		    component = new PlanInformationAndFeaturesPrimaryLineHeaderPanel(headerRepeater.newChildId(),
			    formattedPhoneNumber);
		}else{
		    component = new Label(headerRepeater.newChildId(), formattedPhoneNumber);
		}
	    }else if (CarrierPlan.class.isAssignableFrom(headerRow.get(i).getClass())){
		component = new Label(headerRepeater.newChildId(), "Shared Plan");
	    }
	    headerRepeater.add(component);
	    if (i == headerRow.size() - 2){
		component.add(new AttributeAppender("class", new Model<String>("last"), " "));
	    }
	}
	add(headerRepeater);

	RepeatingView rowRepeater = new RepeatingView("rowRepeater");
	for(int i = 1; i < data.size() - 1; ++i){
	    TableColumnRepeater row = new TableColumnRepeater(rowRepeater.newChildId(), new MyColumnFactory(
		    data.get(i), 1, data.get(i).size() - 2));
	    applyClass(row, i - 1, data.size() - 2);
	    rowRepeater.add(row);
	}
	add(rowRepeater);

	RepeatingView footerRepeater = new RepeatingView("footerRepeater");
	List<Object> footerRow = data.get(data.size() - 1);
	for(int i = 1; i < footerRow.size() - 1; ++i){
	    Label label = new Label(headerRepeater.newChildId(), formatValue((Number) footerRow.get(i)));
	    if (i == headerRow.size() - 2){
		label.add(new AttributeAppender("class", new Model<String>("last"), " "));
	    }
	    footerRepeater.add(label);
	}
	add(footerRepeater);

	RepeatingView totalsRepeater = new RepeatingView("totalsRepeater");
	for(int i = 1; i < data.size() - 1; ++i){
	    TableColumnRepeater row = new TableColumnRepeater(rowRepeater.newChildId(), new MyColumnFactory(
		    data.get(i), data.get(i).size() - 1, 1));
	    applyClass(row, i - 1, data.size() - 2);
	    totalsRepeater.add(row);
	}
	add(totalsRepeater);

	List<Object> totalRow = data.get(data.size() - 1);
	Label totalMrcLabel = new Label("totalMrcLabel", formatValue((Number) totalRow.get(totalRow.size() - 1)));
	add(totalMrcLabel);
    }

    private void applyClass(Component component, int index, int length) {
	if (index == length - 1){
	    component.add(new AttributeAppender("class", new Model<String>("last"), " "));
	}
	if (index % 2 == 1){
	    component.add(new AttributeAppender("class", new Model<String>("odd"), " "));
	}
    }

    class MyColumnFactory implements TableColumnRepeater.ColumnFactory {

	List<Object> data;
	int offset;
	int length;

	public MyColumnFactory(List<Object> data) {
	    this.data = data;
	    this.offset = 0;
	    this.length = data.size();
	}

	public MyColumnFactory(List<Object> data, int offset, int length) {
	    this.data = data;
	    this.offset = offset;
	    this.length = length;
	}

	@Override
	public int size() {
	    return length;
	}

	@Override
	public Component newColumn(int index, String id) {
	    Object value = data.get(offset + index);
	    Label label = new Label(id, formatValue(value));
	    if (index == length - 1){
		label.add(new AttributeAppender("class", new Model<String>("last"), " "));
	    }
	    return label;
	}
    }

    private String formatValue(Object value) {
	return value != null?(Number.class.isAssignableFrom(value.getClass())?toCurrencyString((Number) value):value
		.toString()):BLANK_VALUE;
    }

    private static String toCurrencyString(Number value) {
	try{
	    java.text.NumberFormat format = java.text.NumberFormat.getCurrencyInstance();
	    return format.format(value);
	}catch(Exception ex){
	    throw new RuntimeException("Cannot format " + value + ": " + ex.getMessage(), ex);
	}
    }

    private static boolean lineIsPrimary(Line line) {
	if (line == null || line.getCarrierPlans() == null){
	    return false;
	}
	for(CarrierPlan plan: line.getCarrierPlans()){
	    if (plan != null && plan.getPlanType() != null && plan.getPlanType().equals("Fam Pri.")){
		return true;
	    }
	}
	return false;
    }
}
