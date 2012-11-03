package com.bestbuy.bbym.ise.drp.customer;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bestbuy.bbym.ise.domain.Customer;
import com.bestbuy.bbym.ise.domain.Product;
import com.bestbuy.bbym.ise.domain.ProtectionPlan;
import com.bestbuy.bbym.ise.drp.DailyRhythmPortalSession;
import com.bestbuy.bbym.ise.drp.common.ModalPanel;
import com.bestbuy.bbym.ise.drp.domain.RewardZone;
import com.bestbuy.bbym.ise.util.Util;

public abstract class CustInfoModalPanel extends ModalPanel {

    private static final long serialVersionUID = 1L;
    private static Logger logger = LoggerFactory.getLogger(CustInfoModalPanel.class);

    public enum ModalState {
	SELECT_GSP, SELECT_ANY_PURCHASE, SELECT_MOBILE_PURCHASE, SELECT_REWARD_ZONE
    }

    private ModalState modalState = ModalState.SELECT_GSP;
    private Label modalTitle;
    private SearchCustPanel searchCustPanel;
    private SearchRewardZonePanel searchRewardZonePanel;
    private CustDetailsPanel custDetailsPanel;
    private GspSelectPanel gspSelectPanel;
    private PurchaseSelectPanel purchaseSelectPanel;
    private FeedbackPanel feedbackPanel;

    private Customer selectedCustomer = new Customer();
    private ProtectionPlan selectedProtectionPlan;
    private Product selectedProduct;
    private RewardZone selectedRewardZone;

    public CustInfoModalPanel(final String id, final ModalState modalState) {
	super(id);
	if (modalState != null){
	    this.modalState = modalState;
	}

	DailyRhythmPortalSession session = getDailyRhythmPortalSession();

	if (session.getBestBuyCustomer() != null){
	    selectedCustomer = (Customer) Util.clone(session.getBestBuyCustomer());
	    selectedCustomer.setId(new Long(0L));
	}

	feedbackPanel = new FeedbackPanel("modalFeedback");
	feedbackPanel.setOutputMarkupPlaceholderTag(true);
	add(feedbackPanel);

	modalTitle = new Label("modalTitle", new Model<String>() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public String getObject() {
		if (getModalState() == ModalState.SELECT_REWARD_ZONE){
		    return getString("custInfo.searchRewardZone.title.label");
		}else if (selectedCustomer.getId() == null){
		    return getString("custInfo.searchCustomer.title.label");
		}else if (getModalState() == ModalState.SELECT_GSP){
		    return getString("custInfo.selectGspPlan.title.label");
		}else if (getModalState() == ModalState.SELECT_ANY_PURCHASE){
		    return getString("custInfo.selectPurchase.title.label");
		}else if (getModalState() == ModalState.SELECT_MOBILE_PURCHASE){
		    return getString("custInfo.selectMobilePurchase.title.label");
		}
		return "Unknown Title";
	    }
	});
	modalTitle.setOutputMarkupPlaceholderTag(true);
	add(modalTitle);

	searchCustPanel = new SearchCustPanel("searchCustPanel", this);
	searchCustPanel.setOutputMarkupPlaceholderTag(true);
	add(searchCustPanel);

	searchRewardZonePanel = new SearchRewardZonePanel("searchRewardZonePanel", this);
	searchRewardZonePanel.setOutputMarkupPlaceholderTag(true);
	add(searchRewardZonePanel);

	custDetailsPanel = new CustDetailsPanel("custDetailsPanel", this);
	custDetailsPanel.setOutputMarkupPlaceholderTag(true);
	add(custDetailsPanel);

	gspSelectPanel = new GspSelectPanel("gspSelectPanel", this);
	gspSelectPanel.setOutputMarkupPlaceholderTag(true);
	add(gspSelectPanel);

	purchaseSelectPanel = new PurchaseSelectPanel("purchaseSelectPanel", this);
	purchaseSelectPanel.setOutputMarkupPlaceholderTag(true);
	add(purchaseSelectPanel);

	final AjaxLink<Object> closeLink = new AjaxLink<Object>("closeLink") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onClick(AjaxRequestTarget target) {
		close(target);
	    }
	};
	closeLink.setMarkupId("closeLink");
	closeLink.setOutputMarkupId(true);
	closeLink.setOutputMarkupPlaceholderTag(true);
	add(closeLink);
    }

    @Override
    public String getOpenModalJS() {
	return searchCustPanel.getOpenPanelJS() + searchRewardZonePanel.getOpenPanelJS()
		+ gspSelectPanel.getOpenPanelJS() + purchaseSelectPanel.getOpenPanelJS()
		+ "setModalPanelFocus('#closeLink');";
    }

    @Override
    public String getRefreshModalJS() {
	return searchCustPanel.getRefreshPanelJS() + searchRewardZonePanel.getRefreshPanelJS()
		+ gspSelectPanel.getRefreshPanelJS() + purchaseSelectPanel.getRefreshPanelJS()
		+ "setModalPanelFocus('#closeLink');";
    }

    @Override
    public void update(AjaxRequestTarget target) {
	logger.debug("modalState=" + modalState);
	logger.debug("selectedCustomer=" + selectedCustomer);
	target.add(modalTitle);
	target.add(searchCustPanel);
	target.add(searchRewardZonePanel);
	custDetailsPanel.refresh();
	target.add(custDetailsPanel);
	target.add(gspSelectPanel);
	target.add(purchaseSelectPanel);
    }

    public Customer getSelectedCustomer() {
	return selectedCustomer;
    }

    public void setSelectedCustomer(Customer selectedCustomer) {
	this.selectedCustomer = selectedCustomer;
    }

    public ModalState getModalState() {
	return modalState;
    }

    public void setModalState(ModalState modalState) {
	if (modalState != null){
	    this.modalState = modalState;
	    purchaseSelectPanel.changeModalState(modalState);
	}
    }

    public FeedbackPanel getFeedbackPanel() {
	return feedbackPanel;
    }

    public ProtectionPlan getSelectedProtectionPlan() {
	return selectedProtectionPlan;
    }

    public void setSelectedProtectionPlan(ProtectionPlan selectedProtectionPlan) {
	this.selectedProtectionPlan = selectedProtectionPlan;
    }

    public Product getSelectedProduct() {
	return selectedProduct;
    }

    public void setSelectedProduct(Product selectedProduct) {
	this.selectedProduct = selectedProduct;
    }

    public RewardZone getSelectedRewardZone() {
	return selectedRewardZone;
    }

    public void setSelectedRewardZone(RewardZone selectedRewardZone) {
	this.selectedRewardZone = selectedRewardZone;
    }

    public void onNewCustomer() {
	selectedCustomer.setId(null);
	gspSelectPanel.reloadOnRefresh();
	purchaseSelectPanel.reloadOnRefresh();
    }

    @Override
    public void onOpen(AjaxRequestTarget target) {
	setSelectedProtectionPlan(null);
	setSelectedProduct(null);
	setSelectedRewardZone(null);
    }

    // return error if do not want GSP plan to be selected
    public String checkSelectedProtectionPlan(ProtectionPlan gspPlan) {
	return null;
    }

    // return error if do not want reward zone to be selected
    public String checkRewardZone(RewardZone rewardZone) {
	return null;
    }

    @Override
    public String getModalSelector() {
	return "#" + getId() + " .new-modal";
    }
}
