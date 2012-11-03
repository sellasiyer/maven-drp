package com.bestbuy.bbym.ise.drp.admin;

import java.util.Date;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.IAjaxCallDecorator;
import org.apache.wicket.ajax.calldecorator.AjaxCallDecorator;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bestbuy.bbym.ise.drp.DailyRhythmPortalSession;
import com.bestbuy.bbym.ise.drp.common.BaseNavPage;
import com.bestbuy.bbym.ise.drp.utils.DrpConstants;

/**
 * Admin utility page use by Admin/Manager to change the value of system
 * environment properties.
 * <p>
 * Part of UserStory_B-06555 Admin Utility Development.
 * </p>
 */
@AuthorizeInstantiation( {DrpConstants.DRP_ADMIN })
public class ManagePropertiesPage extends BaseNavPage {

    private static Logger logger = LoggerFactory.getLogger(ManagePropertiesPage.class);
    private static final long serialVersionUID = 1L;

    public ManagePropertiesPage(final PageParameters parameters) {
	super(parameters);

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
		    onDomReadyJS.append("handleAddPropertyButtonEnabling(false);");
		}else{
		    onDomReadyJS.append("handleAddPropertyButtonEnabling(true);");
		}
		logger.info("onDomReadyJS=" + onDomReadyJS);
		response.renderOnDomReadyJavaScript(onDomReadyJS.toString());
	    }
	});

	final FeedbackPanel feedbackPanel = new FeedbackPanel("feedback");
	feedbackPanel.setOutputMarkupPlaceholderTag(true);
	feedbackPanel.setOutputMarkupId(true);
	add(feedbackPanel);

	final AddEditPropertyPanel addEditPropertyPanel = new AddEditPropertyPanel("addEditPropertyPanel",
		feedbackPanel);
	addEditPropertyPanel.setOutputMarkupPlaceholderTag(true);
	add(addEditPropertyPanel);

	final ChangeStoreIdPanel changeStoreIdPanel = new ChangeStoreIdPanel("changeStoreIdPanel", feedbackPanel);
	changeStoreIdPanel.setOutputMarkupPlaceholderTag(true);
	add(changeStoreIdPanel);

	final PropertyTablePanel propertyTablePanel = new PropertyTablePanel("propertyTablePanel");
	propertyTablePanel.setOutputMarkupPlaceholderTag(true);
	add(propertyTablePanel);

	addEditPropertyPanel.setPropertyTablePanel(propertyTablePanel);
	propertyTablePanel.setAddEditPropertyPanel(addEditPropertyPanel);

	AjaxLink<Object> addPropertyLink = new AjaxLink<Object>("addPropertyLink") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onClick(AjaxRequestTarget target) {
		logger.info("in add onclick...");
		DailyRhythmPortalSession session = getDailyRhythmPortalSession();
		session.getAddEditDrpProperty().clear();
		session.getAddEditDrpProperty().setId(-1L);
		session.getAddEditDrpProperty().setCreatedBy(session.getDrpUser().getUserId());
		session.getAddEditDrpProperty().setCreatedDate(new Date());
		session.getAddEditDrpProperty().setModifiedDate(new Date());
		session.getAddEditDrpProperty().setModifiedBy(session.getDrpUser().getUserId());
		target.add(propertyTablePanel);
		target.add(addEditPropertyPanel);
		addEditPropertyPanel.targetComponents(target);
	    }

	    @Override
	    protected IAjaxCallDecorator getAjaxCallDecorator() {
		return new AjaxCallDecorator() {

		    private static final long serialVersionUID = 1L;

		    @Override
		    public CharSequence decorateScript(Component component, CharSequence script) {
			return "handleAddPropertyButtonEnabling(false);" + script;
		    }
		};
	    }
	};
	addPropertyLink.setOutputMarkupPlaceholderTag(true);
	add(addPropertyLink);
    }

    static boolean isInAddMode(DailyRhythmPortalSession session) {
	return isInAddEditMode(session) && session.getAddEditDrpProperty().getId().longValue() < 0L;
    }

    static boolean isInEditMode(DailyRhythmPortalSession session) {
	return isInAddEditMode(session) && session.getAddEditDrpProperty().getId().longValue() >= 0L;
    }

    static boolean isInAddEditMode(DailyRhythmPortalSession session) {
	return session != null && session.getAddEditDrpProperty() != null
		&& session.getAddEditDrpProperty().getId() != null;
    }

}
