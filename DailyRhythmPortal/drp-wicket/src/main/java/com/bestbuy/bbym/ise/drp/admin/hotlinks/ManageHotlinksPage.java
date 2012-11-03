package com.bestbuy.bbym.ise.drp.admin.hotlinks;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.IAjaxCallDecorator;
import org.apache.wicket.ajax.calldecorator.AjaxCallDecorator;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bestbuy.bbym.ise.drp.DailyRhythmPortalSession;
import com.bestbuy.bbym.ise.drp.DrpMenuTool;
import com.bestbuy.bbym.ise.drp.common.BaseNavPage;
import com.bestbuy.bbym.ise.drp.service.HotlinkService;

/**
 * Page used to view and edit a user's list of URL favorites.
 * <p>
 * Part of UserStory_B-04953 View and Edit a list of Hotlinks in the Beast
 * Portal.
 * </p>
 */
public class ManageHotlinksPage extends BaseNavPage {

    private static Logger logger = LoggerFactory.getLogger(ManageHotlinksPage.class);
    private static final long serialVersionUID = 1L;

    public ManageHotlinksPage(final PageParameters parameters) {
	super(parameters);

	final FeedbackPanel feedbackPanel = new FeedbackPanel("feedback");
	feedbackPanel.setOutputMarkupPlaceholderTag(true);
	add(feedbackPanel);

	final HotlinkTablePanel hotlinkTablePanel = new HotlinkTablePanel(feedbackPanel, "hotlinkTablePanel");

	add(new AbstractDefaultAjaxBehavior() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void respond(AjaxRequestTarget target) {
	    }

	    @Override
	    public void renderHead(Component component, IHeaderResponse response) {
		super.renderHead(component, response);
		StringBuilder onDomReadyJS = new StringBuilder();
		if (isInAddEditMode(getDailyRhythmPortalSession())){
		    onDomReadyJS.append("handleAddHotlinkButtonEnabling(false);");
		}else{
		    onDomReadyJS.append("handleAddHotlinkButtonEnabling(true);");
		}
		logger.info("onDomReadyJS=" + onDomReadyJS);
		response.renderOnDomReadyJavaScript(onDomReadyJS.toString());
	    }
	});

	feedbackPanel.setOutputMarkupPlaceholderTag(true);
	add(feedbackPanel);

	final AddEditHotlinkPanel addEditHotlinkPanel = new AddEditHotlinkPanel("addEditHotlinkPanel", feedbackPanel);
	addEditHotlinkPanel.setOutputMarkupPlaceholderTag(true);
	add(addEditHotlinkPanel);

	hotlinkTablePanel.setOutputMarkupPlaceholderTag(true);
	add(hotlinkTablePanel);

	addEditHotlinkPanel.setHotlinkTablePanel(hotlinkTablePanel);
	hotlinkTablePanel.setAddEditHotlinkPanel(addEditHotlinkPanel);

	AjaxLink<Object> addHotlink = new AjaxLink<Object>("addHotlinkLink") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onClick(AjaxRequestTarget target) {
		logger.info("in add onclick...");
		DailyRhythmPortalSession session = getDailyRhythmPortalSession();
		session.getAddEditHotlink().clear();
		session.getAddEditHotlink().setId(-1L);

		target.add(hotlinkTablePanel);
		target.add(addEditHotlinkPanel);
		addEditHotlinkPanel.targetComponents(target);
	    }

	    @Override
	    protected IAjaxCallDecorator getAjaxCallDecorator() {
		return new AjaxCallDecorator() {

		    private static final long serialVersionUID = 1L;

		    @Override
		    public CharSequence decorateScript(Component component, CharSequence script) {
			return "handleAddHotlinkButtonEnabling(false);" + script;
		    }
		};
	    }
	};
	addHotlink.setOutputMarkupPlaceholderTag(true);
	add(addHotlink);
    }

    static boolean isInAddMode(DailyRhythmPortalSession session) {
	return isInAddEditMode(session) && session.getAddEditHotlink().getId().longValue() < 0L;
    }

    static boolean isInEditMode(DailyRhythmPortalSession session) {
	return isInAddEditMode(session) && session.getAddEditHotlink().getId().longValue() >= 0L;
    }

    static boolean isInAddEditMode(DailyRhythmPortalSession session) {
	return session != null && session.getAddEditHotlink() != null && session.getAddEditHotlink().getId() != null;
    }

    static void updateSessionMenuBarHtml(DailyRhythmPortalSession session, HotlinkService hotLinkService) {
	// Generate menu HTML
	if (session.getMenuBar() != null){
	    DrpMenuTool drpMenuTool = new DrpMenuTool();
	    drpMenuTool.setHotlinkService(hotLinkService);
	    drpMenuTool.setUser(session.getDrpUser());
	    session.setMenuBarHtml(drpMenuTool.generateMenuBarHtml(session.getMenuBar()));
	}
    }
}
