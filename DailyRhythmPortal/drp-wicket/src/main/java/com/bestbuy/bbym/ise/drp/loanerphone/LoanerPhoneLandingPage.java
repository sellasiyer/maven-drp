package com.bestbuy.bbym.ise.drp.loanerphone;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.extensions.ajax.markup.html.tabs.AjaxTabbedPanel;
import org.apache.wicket.extensions.markup.html.tabs.AbstractTab;
import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bestbuy.bbym.ise.drp.common.NewBaseNavPage;

public class LoanerPhoneLandingPage extends NewBaseNavPage {

    private static Logger logger = LoggerFactory.getLogger(LoanerPhoneLandingPage.class);
    private static final long serialVersionUID = 1L;

    private Panel selectedPanel = null;

    public Panel getSelectedPanel() {
	return selectedPanel;
    }

    public LoanerPhoneLandingPage(final PageParameters parameters) {
	super(parameters);

	final FeedbackPanel feedbackPanel = new FeedbackPanel("feedback");
	feedbackPanel.setOutputMarkupPlaceholderTag(true);
	add(feedbackPanel);

	AjaxLink<String> addPhoneButton = new AjaxLink<String>("addPhoneButton") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onClick(AjaxRequestTarget target) {
		setResponsePage(new AddPage(getPage(), (Loadable) selectedPanel));
	    }

	};
	add(addPhoneButton);
	if (getDailyRhythmPortalSession().isAuthorizedToInstantiate(AddPage.class)){
	    addPhoneButton.setVisible(true);
	}else{
	    addPhoneButton.setVisible(false);
	}

	BookmarkablePageLink<String> printInventoryButton = new BookmarkablePageLink<String>("printInventoryButton",
		InventoryPrintPage.class);

	add(printInventoryButton);

	final List<ITab> tabs = new ArrayList<ITab>();
	tabs.add(new AbstractTab(new Model<String>("Available")) {

	    private static final long serialVersionUID = 1L;

	    public Panel getPanel(String panelId) {
		selectedPanel = new AvailablePanel(panelId, feedbackPanel);
		return selectedPanel;
	    }

	});

	tabs.add(new AbstractTab(new Model<String>("Checked Out")) {

	    private static final long serialVersionUID = 1L;

	    public Panel getPanel(String panelId) {
		selectedPanel = new CheckedoutPanel(panelId, feedbackPanel);
		return selectedPanel;
	    }
	});

	if (getDailyRhythmPortalSession().isAuthorizedToInstantiate(InventoryPanel.class)){
	    tabs.add(new AbstractTab(new Model<String>("Inventory")) {

		private static final long serialVersionUID = 1L;

		public Panel getPanel(String panelId) {
		    selectedPanel = new InventoryPanel(panelId, feedbackPanel);
		    return selectedPanel;
		}
	    });
	}

	final AjaxTabbedPanel<ITab> tabPanel = new AjaxTabbedPanel<ITab>("lpTabs", tabs) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void onAjaxUpdate(AjaxRequestTarget target) {
		super.onAjaxUpdate(target);
		StringBuilder onAjaxUpdateTabsJS = new StringBuilder();
		onAjaxUpdateTabsJS.append("applyTabStyles('#lpTabs');preFillPage();");
		logger.debug("onAjaxUpdateTabsJS=" + onAjaxUpdateTabsJS);
		target.appendJavaScript(onAjaxUpdateTabsJS.toString());

	    }
	};
	tabPanel.setMarkupId("lpTabs");
	tabPanel.setOutputMarkupId(true);
	add(tabPanel);

	add(new AbstractDefaultAjaxBehavior() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void respond(AjaxRequestTarget target) {
	    }

	    @Override
	    public void renderHead(Component component, IHeaderResponse response) {
		super.renderHead(component, response);
		StringBuilder onDomReadyJS = new StringBuilder();
		onDomReadyJS.append("applyTabStyles('#lpTabs');");
		logger.debug("onDomReadyJS=" + onDomReadyJS);
		response.renderOnDomReadyJavaScript(onDomReadyJS.toString());
	    }
	});
    }
}
