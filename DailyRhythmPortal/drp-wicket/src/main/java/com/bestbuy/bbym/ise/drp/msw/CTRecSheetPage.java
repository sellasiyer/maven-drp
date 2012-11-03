package com.bestbuy.bbym.ise.drp.msw;

import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.bestbuy.bbym.ise.drp.common.NewBaseNavPage;

public class CTRecSheetPage extends NewBaseNavPage {

    private static final long serialVersionUID = 1L;

    private CTRecSheetSearchPanel searchPanel;

    public CTRecSheetPage(final PageParameters parameters) {

	super(parameters);

	searchPanel = new CTRecSheetSearchPanel("ctRecSheetSearchPanel", this);
	searchPanel.setOutputMarkupPlaceholderTag(true);
	add(searchPanel);
    }

}
