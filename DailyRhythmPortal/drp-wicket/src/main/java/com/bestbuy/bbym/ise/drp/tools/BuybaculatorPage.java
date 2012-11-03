package com.bestbuy.bbym.ise.drp.tools;

import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.bestbuy.bbym.ise.drp.common.NewBaseNavPage;

public class BuybaculatorPage extends NewBaseNavPage {

    private static final long serialVersionUID = 1L;

    public BuybaculatorPage(final PageParameters parameters) {
	super(parameters);

	BuybaculatorPanel buybaculatorPanel = new BuybaculatorPanel("buybaculatorPanel");

	buybaculatorPanel.setOutputMarkupPlaceholderTag(true);
	add(buybaculatorPanel);

    }

}
