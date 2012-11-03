package com.bestbuy.bbym.ise.drp.msw;

import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.bestbuy.bbym.ise.drp.common.NewBaseNavPage;

public class RecSheetByEmployeePage extends NewBaseNavPage {

    private static final long serialVersionUID = 1L;

    public RecSheetByEmployeePage(PageParameters parameters) {
	super(parameters);

	final RecSheetByEmployeeSearchPanel searchByEmployeePanel = new RecSheetByEmployeeSearchPanel(
		"recSheetByEmployeeSearchPanel", this);
	searchByEmployeePanel.setOutputMarkupPlaceholderTag(true);
	add(searchByEmployeePanel);
    }

}
