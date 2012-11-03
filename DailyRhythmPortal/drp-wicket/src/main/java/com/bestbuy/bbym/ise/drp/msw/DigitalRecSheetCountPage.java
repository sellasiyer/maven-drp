package com.bestbuy.bbym.ise.drp.msw;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.bestbuy.bbym.ise.drp.common.NewBaseNavPage;
import com.bestbuy.bbym.ise.drp.utils.DrpConstants;

@AuthorizeInstantiation( {DrpConstants.DRP_ADMIN, DrpConstants.DRP_MANAGER })
public class DigitalRecSheetCountPage extends NewBaseNavPage {

    private static final long serialVersionUID = 1L;

    public DigitalRecSheetCountPage(PageParameters parameters) {
	super(parameters);

	final DigitalRecSheetCountPanel digitalRecSheetCountPanel = new DigitalRecSheetCountPanel(
		"digitalRecSheetCountPanel");
	digitalRecSheetCountPanel.setOutputMarkupPlaceholderTag(true);
	add(digitalRecSheetCountPanel);
    }

}
