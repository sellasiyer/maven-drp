package com.bestbuy.bbym.ise.drp.security;

import org.apache.wicket.Page;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bestbuy.bbym.ise.drp.DailyRhythmPortalApplication;
import com.bestbuy.bbym.ise.drp.DailyRhythmPortalSession;

public class SessionTimeoutPage extends WebPage {

    private static final long serialVersionUID = 1L;
    private static Logger logger = LoggerFactory.getLogger(SessionTimeoutPage.class);

    public SessionTimeoutPage() {
	logger.info("Displaying SessionTimeoutPage");
	Class<? extends Page> landingPageClass = ((DailyRhythmPortalApplication) getApplication()).getHomePage();
	DailyRhythmPortalSession session = (DailyRhythmPortalSession) getSession();
	session.clearCarrierCustomer();
	session.clearBestBuyCustomer();
	session.clearSearchCustomer();
	BookmarkablePageLink<WebPage> returnToLoginLink = new BookmarkablePageLink<WebPage>("returnToLoginLink",
		landingPageClass);
	add(returnToLoginLink);
    }

}
