package com.bestbuy.bbym.ise.drp.loanerphone;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;

import com.bestbuy.bbym.ise.drp.common.BasePanel;
import com.bestbuy.bbym.ise.drp.domain.Phone;

public class ViewPanel extends BasePanel {

    private static final long serialVersionUID = 1L;

    private Phone phone;

    public ViewPanel(String id, Phone viewPhone) {
	super(id);
	phone = viewPhone;

	FeedbackPanel feedbackPanel = new FeedbackPanel("feedback");
	feedbackPanel.setOutputMarkupPlaceholderTag(true);
	add(feedbackPanel);

	final Label carrierLoanerSkuField = new Label("carrierLoanerSkuField", new Model<String>() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public String getObject() {
		if (phone.getCarrier() == null){
		    return null;
		}
		return phone.getCarrier().getCarrierLoanerSku();
	    }
	});
	add(carrierLoanerSkuField);

	final Label carrierField = new Label("carrierField", new Model<String>() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public String getObject() {
		if (phone.getCarrier() == null){
		    return null;
		}
		return phone.getCarrier().getCarrier();
	    }
	});
	add(carrierField);

	final Label operatingSystemField = new Label("operatingSystemField", new Model<String>() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public String getObject() {
		if (phone.getOperatingSystem() == null){
		    return null;
		}
		return phone.getOperatingSystem().getOs();
	    }
	});
	add(operatingSystemField);

	final Label makeField = new Label("makeField", new Model<String>() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public String getObject() {
		return phone.getMake();
	    }
	});
	add(makeField);

	final Label modelNumberField = new Label("modelNumberField", new Model<String>() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public String getObject() {
		return phone.getModelNumber();
	    }
	});
	add(modelNumberField);

	final Label serialNumberField = new Label("serialNumberField", new Model<String>() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public String getObject() {
		return phone.getSerialNumber();
	    }
	});
	add(serialNumberField);

	final Label conditionField = new Label("conditionField", new Model<String>() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public String getObject() {
		switch (phone.getCondition()) {
		    case GOOD:
			return getString("loanerPhone.condition.good");
		    case FAIR:
			return getString("loanerPhone.condition.fair");
		    case POOR:
			return getString("loanerPhone.condition.poor");
		    case DAMAGED:
			return getString("loanerPhone.condition.damaged");
		    case LOST:
			return getString("loanerPhone.condition.lost");
		    default:
			throw new RuntimeException("Unexpected phone condition: " + phone.getCondition());
		}
	    }
	});
	conditionField.setEscapeModelStrings(false);
	add(conditionField);

	final Label conditionCommentField = new Label("conditionCommentField", new Model<String>() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public String getObject() {
		return phone.getConditionComment();
	    }
	});
	add(conditionCommentField);
    }

    public void setPhone(Phone phone) {
	this.phone = phone;
    }
}
