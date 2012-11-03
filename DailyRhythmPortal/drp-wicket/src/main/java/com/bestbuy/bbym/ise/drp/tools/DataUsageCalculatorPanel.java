package com.bestbuy.bbym.ise.drp.tools;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.PropertyModel;

import com.bestbuy.bbym.ise.drp.common.BasePanel;

/**
 * @author JAM0314
 */
public class DataUsageCalculatorPanel extends BasePanel {

    private static final long serialVersionUID = -5317069689950333496L;

    private String disclaimerText = "";

    public DataUsageCalculatorPanel(String id, boolean BroadbandCard) {
	this(id);
    }

    public DataUsageCalculatorPanel(String id) {
	super(id);

	final FeedbackPanel feedbackPanel = new FeedbackPanel("feedback");
	feedbackPanel.setOutputMarkupPlaceholderTag(true);
	add(feedbackPanel);

	Label disclaimerLabel = new Label("disclaimerLabel", new PropertyModel<String>(this, "disclaimerText"));
	add(disclaimerLabel);

    }

    public String getDisclaimerText() {
	return this.disclaimerText;
    }

    public void setDisclaimerText(String disclaimerText) {
	this.disclaimerText = disclaimerText;
    }

}
