package com.bestbuy.bbym.ise.drp.dashboard;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class PlanInformationAndFeaturesPrintSummaryPage extends WebPage {

    private static final long serialVersionUID = 1L;

    public PlanInformationAndFeaturesPrintSummaryPage(PageParameters parameters) {
	super(parameters);
	PlanInformationAndFeaturesPanel panel = new PlanInformationAndFeaturesPanel("panel");
	add(panel);
    }
}
