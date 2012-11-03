package com.bestbuy.bbym.ise.drp.dashboard;

import org.apache.wicket.markup.html.basic.Label;

import com.bestbuy.bbym.ise.drp.common.BasePanel;

public class PlanInformationAndFeaturesPrimaryLineHeaderPanel extends BasePanel {

    private static final long serialVersionUID = 1L;

    public PlanInformationAndFeaturesPrimaryLineHeaderPanel(String id, String phoneNumber) {
	super(id);
	Label phoneNumberLabel = new Label("phoneNumberLabel", phoneNumber);
	add(phoneNumberLabel);
    }
}
