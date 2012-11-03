package com.bestbuy.bbym.ise.drp.admin.hotlinks;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.IAjaxCallDecorator;
import org.apache.wicket.ajax.calldecorator.AjaxCallDecorator;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.validation.validator.PatternValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bestbuy.bbym.ise.drp.DailyRhythmPortalSession;
import com.bestbuy.bbym.ise.drp.common.BasePanel;
import com.bestbuy.bbym.ise.drp.domain.Hotlink;
import com.bestbuy.bbym.ise.drp.service.HotlinkService;
import com.bestbuy.bbym.ise.drp.util.HttpUrlValidator;
import com.bestbuy.bbym.ise.exception.IseBusinessException;
import com.bestbuy.bbym.ise.exception.ServiceException;

public class AddEditHotlinkPanel extends BasePanel {

    private static final long serialVersionUID = 1L;
    private static Logger logger = LoggerFactory.getLogger(AddEditHotlinkPanel.class);

    private HotlinkTablePanel hotlinkTablePanel;
    private AddEditHotlinkPanel addEditHotlinkPanel;
    private TextField<String> websiteName;
    private TextField<String> websiteUrl;

    @SpringBean(name = "hotlinkService")
    private HotlinkService hotlinkService;

    public AddEditHotlinkPanel(String id, final FeedbackPanel feedbackPanel) {
	super(id);
	addEditHotlinkPanel = this;

	Model<Hotlink> addEditHotlinkModel = new Model<Hotlink>(getDailyRhythmPortalSession().getAddEditHotlink());

	// form
	final Form<Hotlink> form = new Form<Hotlink>("addEditHotlinkForm", addEditHotlinkModel);
	form.setOutputMarkupPlaceholderTag(true);
	add(form);

	// section title
	form.add(new Label("sectionTitleLabel", new Model<String>() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public String getObject() {
		if (ManageHotlinksPage.isInAddMode(getDailyRhythmPortalSession())){
		    return getString("addHotlinkForm.label");
		}
		return getString("editHotlinkForm.label");
	    }
	}));

	// name field
	final PropertyModel<String> websiteNameModel = new PropertyModel<String>(getDailyRhythmPortalSession()
		.getAddEditHotlink(), "urlAlias");
	form.add(new Label("websiteNameLabel", new ResourceModel("addEditHotlinkForm.websiteName")));
	websiteName = new TextField<String>("websiteName", websiteNameModel);
	websiteName.setOutputMarkupPlaceholderTag(true);
	websiteName.setRequired(true);
	websiteName.add(new PatternValidator("^[A-Za-z0-9._ -]+$"));
	form.add(websiteName);

	// value field
	final PropertyModel<String> websiteUrlModel = new PropertyModel<String>(getDailyRhythmPortalSession()
		.getAddEditHotlink(), "url");
	form.add(new Label("websiteUrlLabel", new ResourceModel("addEditHotlinkForm.websiteUrl")));
	websiteUrl = new TextField<String>("websiteUrl", websiteUrlModel);
	websiteUrl.setOutputMarkupPlaceholderTag(true);
	websiteUrl.setRequired(true);
	websiteUrl.add(new HttpUrlValidator());
	websiteUrl.add(new PatternValidator("^[A-Za-z0-9._ /@#%:-]+$"));
	form.add(websiteUrl);

	// submit button
	AjaxButton submitButton = new AjaxButton("submitButton", new ResourceModel("addEditHotlinkForm.submit.button")) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
		logger.info("in submit onSubmit...");
		DailyRhythmPortalSession session = getDailyRhythmPortalSession();
		try{
		    session.getAddEditHotlink().setUserId(session.getDrpUser().getUserId());
		    hotlinkService.createUpdateLink(session.getAddEditHotlink(), session.getDrpUser());
		    if (session.getAddEditHotlink().getId() != null
			    && session.getAddEditHotlink().getId().longValue() >= 0L){
			session.info("Hotlink named \"" + session.getAddEditHotlink().getUrlAlias() + "\" changed");
		    }else{
			session.info("Hotlink named \"" + session.getAddEditHotlink().getUrlAlias() + "\" added");
		    }
		    ManageHotlinksPage.updateSessionMenuBarHtml(session, hotlinkService);
		}catch(IseBusinessException be){
		    error(be.getFullMessage());
		    target.add(feedbackPanel);
		    return;
		}catch(ServiceException se){
		    String message = "An unexpected exception occured while attempting to create/update a hotlink";
		    logger.error(message, se);
		    processException(message, se, getPage().getPageParameters());
		}
		session.getAddEditHotlink().clear();
		setResponsePage(ManageHotlinksPage.class);
	    }

	    @Override
	    protected void onError(AjaxRequestTarget target, Form<?> form) {
		logger.info("in submit onError...");
		target.add(feedbackPanel);
		targetComponents(target);
	    }
	};
	submitButton.setOutputMarkupPlaceholderTag(true);
	form.add(submitButton);

	// cancel button
	AjaxButton cancelButton = new AjaxButton("cancelButton", new ResourceModel("addEditHotlinkForm.cancel.button")) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
		logger.info("in cancel onSubmit...");
		DailyRhythmPortalSession session = getDailyRhythmPortalSession();
		session.getAddEditHotlink().setId(null);
		// Must re-attach models
		websiteName.setModel(websiteNameModel);
		websiteUrl.setModel(websiteUrlModel);
		target.add(addEditHotlinkPanel);
		target.add(hotlinkTablePanel);
		targetComponents(target);
	    }

	    @Override
	    protected IAjaxCallDecorator getAjaxCallDecorator() {
		return new AjaxCallDecorator() {

		    private static final long serialVersionUID = 1L;

		    @Override
		    public CharSequence decorateScript(Component component, CharSequence script) {
			return "handleAddHotlinkButtonEnabling(true);" + script;
		    }
		};
	    }

	    @Override
	    protected void onError(AjaxRequestTarget target, Form<?> form) {
		// TODO Implement me!
	    }

	};
	cancelButton.setOutputMarkupPlaceholderTag(true);
	cancelButton.setDefaultFormProcessing(false);
	form.add(cancelButton);
    }

    @Override
    public boolean isVisible() {
	return ManageHotlinksPage.isInAddEditMode(getDailyRhythmPortalSession());
    }

    public void setHotlinkTablePanel(final HotlinkTablePanel hotlinkTablePanel) {
	this.hotlinkTablePanel = hotlinkTablePanel;
    }

    public void targetComponents(AjaxRequestTarget target) {
	logger.info("targetComponents");
	target.add(websiteName);
	target.add(websiteUrl);
    }
}
