package com.bestbuy.bbym.ise.drp.dashboard;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.ajax.markup.html.tabs.AjaxTabbedPanel;
import org.apache.wicket.extensions.markup.html.tabs.AbstractTab;
import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.resource.ContextRelativeResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bestbuy.bbym.ise.drp.DailyRhythmPortalSession;
import com.bestbuy.bbym.ise.drp.common.BasePanel;
import com.bestbuy.bbym.ise.drp.rewardzone.RewardZoneTabPanel;
import com.bestbuy.bbym.ise.drp.util.FeedbackMessageFilter;

public class PurchaseHistoryPanel extends BasePanel {

    public enum TabId {
	GSP, ALL, MOBILE
    }

    private static final long serialVersionUID = 1L;
    private static Logger logger = LoggerFactory.getLogger(PurchaseHistoryPanel.class);

    private String memberNumber = null;
    private FeedbackPanel purchaseHistoryFeedback;

    public PurchaseHistoryPanel(String id) {
	super(id);

	DailyRhythmPortalSession session = getDailyRhythmPortalSession();

	if (session.getBestBuyCustomer() != null){
	    memberNumber = session.getBestBuyCustomer().getRewardZoneId();
	}

	final Form<Object> purchaseHistoryForm = new Form<Object>("purchaseHistoryForm");
	purchaseHistoryForm.setOutputMarkupPlaceholderTag(true);
	add(purchaseHistoryForm);

	final FeedbackMessageFilter filter = new FeedbackMessageFilter("PurchaseHistory");
	filter.setAcceptAllUnspecifiedComponents(false);
	filter.addFilterInComponent(purchaseHistoryForm);

	purchaseHistoryFeedback = new FeedbackPanel("purchaseHistoryFeedback", filter);
	purchaseHistoryFeedback.setOutputMarkupPlaceholderTag(true);
	add(purchaseHistoryFeedback);

	// Add feedback panel to filter so can write error to panel directly
	filter.addFilterInComponent(purchaseHistoryFeedback);

	final List<ITab> tabs = new ArrayList<ITab>();
	tabs.add(new AbstractTab(new ResourceModel("gspPurchaseHistoryTabPanel.gsp.label")) {

	    private static final long serialVersionUID = 1L;

	    public Panel getPanel(String panelId) {
		return new GspTabPanel(panelId, purchaseHistoryFeedback, purchaseHistoryForm, filter);
	    }
	});
	tabs.add(new AbstractTab(new ResourceModel("gspPurchaseHistoryTabPanel.allPurchases.label")) {

	    private static final long serialVersionUID = 1L;

	    public Panel getPanel(String panelId) {
		return new PurchaseHistoryTabPanel(panelId, purchaseHistoryFeedback, purchaseHistoryForm, filter,
			TabId.ALL);
	    }
	});
	tabs.add(new AbstractTab(new ResourceModel("gspPurchaseHistoryTabPanel.mobilePurchases.label")) {

	    private static final long serialVersionUID = 1L;

	    public Panel getPanel(String panelId) {
		return new PurchaseHistoryTabPanel(panelId, purchaseHistoryFeedback, purchaseHistoryForm, filter,
			TabId.MOBILE);
	    }
	});

	tabs.add(new AbstractTab(new ResourceModel("gspPurchaseHistoryTabPanel.rewardZone.label")) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public Panel getPanel(String panelId) {
		if (StringUtils.isNotBlank(memberNumber)){
		    return new RewardZoneTabPanel(panelId, memberNumber, purchaseHistoryFeedback, purchaseHistoryForm,
			    filter);
		}else{
		    return new RewardZoneTabPanel(panelId).new RewardZoneNoDataTabPanel(panelId);
		}
	    }
	});

	final AjaxTabbedPanel<ITab> gspPurchaseHistoryTabPanel = new AjaxTabbedPanel<ITab>(
		"gspPurchaseHistoryTabPanel", tabs) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void onAjaxUpdate(AjaxRequestTarget target) {
		super.onAjaxUpdate(target);
		StringBuilder onAjaxUpdateTabsJS = new StringBuilder();
		onAjaxUpdateTabsJS.append("applyTabStyles('#gspPurchaseHistoryTabPanel');");
		logger.debug("onAjaxUpdateTabsJS=" + onAjaxUpdateTabsJS);
		target.appendJavaScript(onAjaxUpdateTabsJS.toString());
	    }
	};
	gspPurchaseHistoryTabPanel.setMarkupId("gspPurchaseHistoryTabPanel");
	gspPurchaseHistoryTabPanel.setOutputMarkupId(true);
	purchaseHistoryForm.add(gspPurchaseHistoryTabPanel);

	// search again button
	Button searchAgainButton = new Button("searchAgainButton", new ResourceModel(
		"purchaseHistoryForm.searchAgain.button")) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onSubmit() {
		getDailyRhythmPortalSession().clearBestBuyCustomer();
		setResponsePage(CustomerDashboardPage.class);
	    }
	};
	searchAgainButton.setDefaultFormProcessing(false);
	purchaseHistoryForm.add(searchAgainButton);

	/** Customer Dashboard Header Panel to display the Header on the Page */
	CustomerDashboardHeaderPanel customerDashboardHeaderPanel = new CustomerDashboardHeaderPanel(
		"customerDashboardHeaderPanel", null);
	customerDashboardHeaderPanel.setOutputMarkupPlaceholderTag(true);
	add(customerDashboardHeaderPanel);

	add(new Image("transfer-to-beast", new ContextRelativeResource("/css/img/transfer-to-beast.png")) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public boolean isVisible() {
		return getDailyRhythmPortalSession().getBestBuyCustomer().isTransferFlag();
	    }
	});

	add(new AbstractDefaultAjaxBehavior() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void respond(AjaxRequestTarget target) {
	    }

	    @Override
	    public void renderHead(Component component, IHeaderResponse response) {
		super.renderHead(component, response);
		StringBuilder onDomReadyJS = new StringBuilder();
		onDomReadyJS.append("applyTabStyles('#gspPurchaseHistoryTabPanel');");
		logger.debug("onDomReadyJS=" + onDomReadyJS);
		response.renderOnDomReadyJavaScript(onDomReadyJS.toString());
	    }
	});
    }

    /**
     * Shows the panel if we have BestBuy Customer details.
     */
    @Override
    public boolean isVisible() {
	return getDailyRhythmPortalSession().getBestBuyCustomer() != null;
    }

    public FeedbackPanel getFeedbackPanel() {
	return purchaseHistoryFeedback;
    }
}
