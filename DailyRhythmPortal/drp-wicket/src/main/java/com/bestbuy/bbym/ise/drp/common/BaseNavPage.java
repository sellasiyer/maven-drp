package com.bestbuy.bbym.ise.drp.common;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bestbuy.bbym.ise.drp.DailyRhythmPortalApplication;
import com.bestbuy.bbym.ise.drp.DailyRhythmPortalSession;
import com.bestbuy.bbym.ise.drp.DrpMenuTool;
import com.bestbuy.bbym.ise.drp.service.DrpPropertyService;
import com.bestbuy.bbym.ise.drp.service.HotlinkService;
import com.bestbuy.bbym.ise.exception.ServiceException;

/**
 * @deprecated replaced by {@link NewBaseNavPage}.
 */
@Deprecated
public abstract class BaseNavPage extends BaseWebPage {

    private static Logger logger = LoggerFactory.getLogger(BaseNavPage.class);

    @SpringBean(name = "hotlinkService")
    private HotlinkService hotLinkService;

    @SpringBean(name = "drpPropertyService")
    private DrpPropertyService drpPropertyService;

    public BaseNavPage(PageParameters parameters) {
	super(parameters);

	DailyRhythmPortalSession session = getDailyRhythmPortalSession();

	// Generate menu bar & menu HTML
	if (session.getMenuBar() == null || session.getMenuBarHtml() == null){
	    DrpMenuTool drpMenuTool = new DrpMenuTool();
	    drpMenuTool.setHotlinkService(hotLinkService);
	    drpMenuTool.setUser(session.getDrpUser());
	    session.setMenuBar(drpMenuTool.generateMenuBar(this));
	    session.setMenuBarHtml(drpMenuTool.generateMenuBarHtml(session.getMenuBar()));
	    logger.trace("MENUBAR=" + session.getMenuBarHtml());
	}
	StringBuilder sb = new StringBuilder();
	sb.append(getString("footer.release.label"));
	sb.append(" ");
	sb.append(getString("footer.releaseValue.label"));
	sb.append(" &nbsp; ");
	sb.append(getString("footer.version.label"));
	sb.append(" ");
	sb.append(((DailyRhythmPortalApplication) getApplication()).getBuildVersion());
	Label footer = new Label("footer.version.label", new Model<String>(sb.toString()));
	footer.setEscapeModelStrings(false);
	add(footer);

	final HeaderPanel header = new HeaderPanel("headerPanel");
	header.setOutputMarkupPlaceholderTag(true);
	header.refresh();
	add(header);
    }

    public boolean isDummyStoreEnabled() throws ServiceException {
	return BasePanel.accessDummyStoreEnabled(drpPropertyService);
    }

    public String getDummyStoreNum() throws ServiceException {
	return BasePanel.accessDummyStoreNum(drpPropertyService);
    }

    public boolean getBooleanProperty(String name, boolean defaultValue) {
	return BasePanel.accessBooleanProperty(drpPropertyService, name, defaultValue);
    }
}
