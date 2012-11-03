package com.bestbuy.bbym.ise.drp.scoreboard;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.form.validation.AbstractFormValidator;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.validation.validator.MinimumValidator;

import com.bestbuy.bbym.ise.drp.domain.scoreboard.ScoreboardCategory;
import com.bestbuy.bbym.ise.drp.domain.scoreboard.ScoreboardDataItem;

public class PostComputingColumnPanel extends ScoreboardModalColumnPanel {

    private static final long serialVersionUID = 1L;

    private static String[] disabledAmountCategories = {"laptops", "desktops & all-in-ones", "tablets", "ereaders",
	    "connections" };
    private List<String> disabledAmountCategoriesList = Arrays.asList(disabledAmountCategories);

    private String quantityPropertyName = "";
    private String amountPropertyName = "";

    public PostComputingColumnPanel(String id, List<ScoreboardCategory> categoryList) {
	super(id, categoryList);

	ListView<ScoreboardCategory> categoryListView = new ListView<ScoreboardCategory>("categoryListView",
		categoryList) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void populateItem(ListItem<ScoreboardCategory> categoryListItem) {
		ScoreboardCategory category = categoryListItem.getModelObject();
		categoryListItem.add(new Label("listHeaderLabel", Model.of(category.getName())));
		ListView<ScoreboardDataItem> dataItemList = makeDataItemList(category);
		dataItemList.setReuseItems(true);
		categoryListItem.add(dataItemList);
	    }
	};
	add(categoryListView);
    }

    private ListView<ScoreboardDataItem> makeDataItemList(final ScoreboardCategory category) {
	ListView<ScoreboardDataItem> dataItemList = new ListView<ScoreboardDataItem>("dataItemListview", category
		.getDataItems()) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void populateItem(ListItem<ScoreboardDataItem> listviewItem) {
		ScoreboardDataItem dataItem = listviewItem.getModelObject();

		listviewItem.add(new Label("itemNameLabel", PropertyModel.of(dataItem, "name")));

		TextField<Integer> itemQtyField = new TextField<Integer>("itemQtyField", new PropertyModel<Integer>(
			dataItem, quantityPropertyName));
		itemQtyField.setOutputMarkupPlaceholderTag(true);
		//	itemQtyField.add(new MinimumValidator<Integer>(0));
		listviewItem.add(itemQtyField);

		TextField<BigDecimal> itemAmtField = new TextField<BigDecimal>("itemAmtField",
			new PropertyModel<BigDecimal>(dataItem, amountPropertyName));
		itemAmtField.setOutputMarkupPlaceholderTag(true);
		//	itemAmtField.add(new MinimumValidator<BigDecimal>(new BigDecimal(0)));
		listviewItem.add(itemAmtField);
		listviewItem.add(new AmountRequiredValidator(itemQtyField, itemAmtField, dataItem.getName()));

		if (disabledAmountCategoriesList.contains(category.getName().toLowerCase())){
		    itemAmtField.setEnabled(false);
		}
	    }

	};

	return dataItemList;
    }

    @Override
    public void setPostMode(PostMode postMode) {
	super.setPostMode(postMode);
	quantityPropertyName = postMode.toString();

	switch (postMode) {
	    case RETURN:
		this.amountPropertyName = "editUnitPrice";
		break;
	    case SALE:
		this.amountPropertyName = "editUnitPrice";
		break;
	}

    }

    @SuppressWarnings("rawtypes")
    class AmountRequiredValidator extends AbstractFormValidator {

	private static final long serialVersionUID = 1L;

	private TextField quantity;
	private TextField amount;
	private String fieldname;

	public AmountRequiredValidator(TextField quantity, TextField amount, String fieldName) {
	    this.quantity = quantity;
	    this.amount = amount;
	    this.fieldname = fieldName;
	}

	@Override
	public FormComponent<?>[] getDependentFormComponents() {
	    return new FormComponent[] {quantity, amount };
	}

	@Override
	public void validate(Form<?> form) {
	    Integer quantityEntered = (Integer) quantity.getConvertedInput();
	    BigDecimal amountEntered = (BigDecimal) amount.getConvertedInput();

	    //check if quantity is NOT blank.
	    if (quantityEntered != null && !quantityEntered.equals(0)){

		if (amountEntered == null){
		    String errormsg = fieldname + ": " + " Unit Price is required";
		    HashMap<String, Object> map = new HashMap<String, Object>();
		    map.put("fieldname", fieldname);
		    error(amount, "scoreboardModal.amountRequired.validator.errormsg", map);
		}

	    }

	}

    }
}
