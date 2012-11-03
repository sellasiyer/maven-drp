package com.bestbuy.bbym.ise.drp.msw;

import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.bestbuy.bbym.ise.drp.common.NewBaseNavPage;

public class MobileSalesWorkspacePage extends NewBaseNavPage {

    private static final long serialVersionUID = 1L;

    private DigitalRecSheetSearchPanel searchPanel;

    public MobileSalesWorkspacePage(final PageParameters parameters) {

	super(parameters);

	searchPanel = new DigitalRecSheetSearchPanel("digitalRecSheetSearchPanel", this);
	searchPanel.setOutputMarkupPlaceholderTag(true);
	add(searchPanel);
    }

}
