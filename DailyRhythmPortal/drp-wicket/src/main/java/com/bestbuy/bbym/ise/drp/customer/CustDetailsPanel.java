package com.bestbuy.bbym.ise.drp.customer;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.basic.Label;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bestbuy.bbym.ise.drp.common.BasePanel;
import com.bestbuy.bbym.ise.drp.util.FormatModel;

public class CustDetailsPanel extends BasePanel {

    private static final long serialVersionUID = 1L;
    private static Logger logger = LoggerFactory.getLogger(CustDetailsPanel.class);

    private final String na = getString("notApplicable.label");

    private CustInfoModalPanel parentModalPanel;

    public CustDetailsPanel(String id, final CustInfoModalPanel parentModalPanel) {
	super(id);
	this.parentModalPanel = parentModalPanel;

	refresh();

	final AjaxLink<Object> newCustomerLink = new AjaxLink<Object>("newCustomerLink") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onClick(AjaxRequestTarget target) {
		logger.debug("newCustomerLink onClick");
		parentModalPanel.onNewCustomer();
		parentModalPanel.refresh(target);
	    }
	};
	newCustomerLink.setMarkupId("newCustomerLink");
	newCustomerLink.setOutputMarkupId(true);
	newCustomerLink.setOutputMarkupPlaceholderTag(true);
	add(newCustomerLink);
    }

    @Override
    public boolean isVisible() {
	return parentModalPanel.getSelectedCustomer().getId() != null;
    }

    public void refresh() {
	if (isVisible()){
	    addOrReplace(new Label("customerNameLabel", new FormatModel<String>(parentModalPanel.getSelectedCustomer()
		    .getNameString(), na)));
	    addOrReplace(new Label("customerAddressLabel", new FormatModel<String>(parentModalPanel
		    .getSelectedCustomer().getAddressString(), na)));
	    addOrReplace(new Label("customerRewardZoneLabel", new FormatModel<String>(parentModalPanel
		    .getSelectedCustomer().getRewardZoneId(), na)));
	}
    }
}
