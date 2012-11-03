package com.bestbuy.bbym.ise.drp.tools;

import org.apache.wicket.markup.html.panel.FeedbackPanel;

import com.bestbuy.bbym.ise.drp.common.BasePanel;

public class BuybaculatorPanel extends BasePanel {

    private String buyBackInput;

    private static final long serialVersionUID = 1L;

    public BuybaculatorPanel(String id) {
	super(id);

	final FeedbackPanel feedbackPanel = new FeedbackPanel("feedback");
	feedbackPanel.setOutputMarkupPlaceholderTag(true);
	add(feedbackPanel);

    }

    public void setBuyBackInput(String buyBackInput) {
	this.buyBackInput = buyBackInput;
    }

    public String getBuyBackInput() {
	return buyBackInput;
    }
}
