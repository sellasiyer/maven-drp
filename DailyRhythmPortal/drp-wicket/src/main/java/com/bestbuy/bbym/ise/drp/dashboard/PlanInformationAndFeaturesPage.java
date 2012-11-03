package com.bestbuy.bbym.ise.drp.dashboard;

import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.bestbuy.bbym.ise.drp.common.NewBaseNavPage;

public class PlanInformationAndFeaturesPage extends NewBaseNavPage {

    private static final long serialVersionUID = 1L;

    public PlanInformationAndFeaturesPage(final PageParameters parameters) {
	super(parameters);
	PlanInformationAndFeaturesPanel panel = new PlanInformationAndFeaturesPanel("panel");
	add(panel);

	BookmarkablePageLink<String> printButton = new BookmarkablePageLink<String>("printButton",
		PlanInformationAndFeaturesPrintSummaryPage.class);

	add(printButton);
    }
}
