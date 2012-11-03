package com.bestbuy.bbym.ise.drp.common;

import org.apache.wicket.Component;
import org.apache.wicket.Page;
import org.apache.wicket.authorization.IAuthorizationStrategy;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.devutils.debugbar.DebugBar;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.TransparentWebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.EmptyPanel;
import org.apache.wicket.model.ResourceModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bestbuy.bbym.ise.domain.Carrier;
import com.bestbuy.bbym.ise.domain.Customer;
import com.bestbuy.bbym.ise.drp.DailyRhythmPortalApplication;
import com.bestbuy.bbym.ise.drp.DailyRhythmPortalSession;
import com.bestbuy.bbym.ise.drp.dashboard.CustomerDashboardSearchPage;
import com.bestbuy.bbym.ise.drp.domain.DrpUser;
import com.bestbuy.bbym.ise.drp.util.PhoneFormatter;

/**
 * @author a186288
 */
public class HeaderPanel extends BasePanel {

    private static final long serialVersionUID = -5476572565311651330L;
    private static Logger logger = LoggerFactory.getLogger(HeaderPanel.class);

    public HeaderPanel(String id) {
	super(id);

	if (getApplication().getDebugSettings().isDevelopmentUtilitiesEnabled()){
	    add(new DebugBar("debug"));
	}else{
	    add(new EmptyPanel("debug").setVisible(false));
	}

	add(new BookmarkablePageLink<Page>("homeLink", HomePage.class));

	ExternalLink feedbackLink = new ExternalLink("feedbackLink", new ResourceModel("header.feedbackLink"));
	add(feedbackLink);

	Link<Object> clearCustomerLink = new Link<Object>("clearCustomerLink") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onClick() {
		getDailyRhythmPortalSession().clearCarrierCustomer();
		getDailyRhythmPortalSession().clearBestBuyCustomer();
		getDailyRhythmPortalSession().clearSearchCustomer();
		getDailyRhythmPortalSession().setSessionPropertyBoolean("needCustomer", true);
		getDailyRhythmPortalSession().setPageError(null);
		setResponsePage(CustomerDashboardSearchPage.class);
	    }

	    @Override
	    public boolean isVisible() {
		IAuthorizationStrategy authorizationStrategy = DailyRhythmPortalApplication.get().getSecuritySettings()
			.getAuthorizationStrategy();
		if (!authorizationStrategy.isInstantiationAuthorized(CustomerDashboardSearchPage.class)){
		    logger.debug("Hiding clear customer button, user is not allowed to view dashboard search page");
		    return false;
		}

		DailyRhythmPortalSession session = getDailyRhythmPortalSession();
		return !(session.getSessionPropertyBoolean("needCustomer", false) && (session.getCustomer() == null
			|| session.getCustomer().getSubscription() == null || session.getCustomer().getSubscription()
			.getLines() == null));
	    }
	};
	add(clearCustomerLink);

	Label menuContent = new Label("menuContent", getDailyRhythmPortalSession().getMenuBarHtml());
	menuContent.setEscapeModelStrings(false);
	add(menuContent);
    }

    public void refresh() {
	DailyRhythmPortalSession session = getDailyRhythmPortalSession();
	DrpUser user = session.getDrpUser();

	String userName = "unknown";
	if (user != null && user.getFirstName() != null && !user.getFirstName().equals("")
		&& user.getLastName() != null && !user.getLastName().equals("")){
	    userName = user.getFirstName() + " " + user.getLastName();
	}
	addOrReplace(new Label("userName", userName));

	Customer customer = session.getCustomer();
	String customerName = "unknown";
	if (customer != null && customer.getNameString() != null){
	    customerName = customer.getNameString();
	}
	addOrReplace(new Label("customerName", customerName));

	StringBuilder carrierStyleClass = new StringBuilder();
	carrierStyleClass.append("carrier-logo");
	if (customer.getSubscription() != null && customer.getSubscription().getCarrier() != null){
	    Carrier carrier = customer.getSubscription().getCarrier();
	    carrierStyleClass.append(" ");
	    carrierStyleClass.append(carrier.getStyleClass());
	}
	final String carrierCssClass = carrierStyleClass.toString();
	TransparentWebMarkupContainer carrierLogo = new TransparentWebMarkupContainer("carrierLogo");
	carrierLogo.add(new Behavior() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onComponentTag(Component component, ComponentTag tag) {
		tag.put("class", carrierCssClass);
	    }
	});
	addOrReplace(carrierLogo);

	String acctNumber = "unknown";
	if (customer.getAcctNumber() != null){
	    acctNumber = customer.getAcctNumber();
	}
	addOrReplace(new Label("acctNumber", acctNumber));

	String phoneNumber = "unknown";
	if (customer.getContactPhone() != null){
	    PhoneFormatter<String> formatter = new PhoneFormatter<String>();
	    phoneNumber = formatter.format(customer.getContactPhone());
	}
	addOrReplace(new Label("phoneNumber", phoneNumber));
    }

}
