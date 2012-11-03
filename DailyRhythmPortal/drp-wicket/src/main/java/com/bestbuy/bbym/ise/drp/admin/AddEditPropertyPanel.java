package com.bestbuy.bbym.ise.drp.admin;

import java.util.MissingResourceException;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.IAjaxCallDecorator;
import org.apache.wicket.ajax.calldecorator.AjaxCallDecorator;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bestbuy.bbym.ise.drp.DailyRhythmPortalSession;
import com.bestbuy.bbym.ise.drp.common.BasePanel;
import com.bestbuy.bbym.ise.drp.domain.DrpProperty;
import com.bestbuy.bbym.ise.drp.service.DrpPropertyService;
import com.bestbuy.bbym.ise.drp.utils.LoggerUtilServiceImpl;
import com.bestbuy.bbym.ise.exception.ServiceException;

public class AddEditPropertyPanel extends BasePanel {

    private static final long serialVersionUID = 1L;
    private static Logger logger = LoggerFactory.getLogger(AddEditPropertyPanel.class);

    private PropertyTablePanel propertyTablePanel;
    private TextField<String> propertyName;
    private TextField<String> propertyValue;
    private TextField<String> description;
    private CheckBox propertyWicketFlag;
    @SpringBean(name = "loggerUtilService")
    private LoggerUtilServiceImpl loggerUtilService;

    @SpringBean(name = "drpPropertyService")
    private DrpPropertyService drpPropertyService;

    public AddEditPropertyPanel(String id, final FeedbackPanel feedbackPanel) {
	super(id);

	Model<DrpProperty> addEditPropertyModel = new Model<DrpProperty>(getDailyRhythmPortalSession()
		.getAddEditDrpProperty());

	// form
	final Form<DrpProperty> form = new Form<DrpProperty>("addEditPropertyForm", addEditPropertyModel);
	form.setOutputMarkupPlaceholderTag(true);
	add(form);

	// section title
	form.add(new Label("sectionTitleLabel", new Model<String>() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public String getObject() {
		if (ManagePropertiesPage.isInAddMode(getDailyRhythmPortalSession())){
		    return getString("addPropertyForm.label");
		}
		return getString("editPropertyForm.label");
	    }
	}));

	// name field
	final PropertyModel<String> propertyNameModel = new PropertyModel<String>(getDailyRhythmPortalSession()
		.getAddEditDrpProperty(), "name");
	form.add(new Label("propertyNameLabel", new ResourceModel("addEditPropertyForm.propertyName")));
	propertyName = new TextField<String>("propertyName", propertyNameModel) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public boolean isEnabled() {
		return ManagePropertiesPage.isInAddMode(getDailyRhythmPortalSession());
	    }
	};
	propertyName.setOutputMarkupPlaceholderTag(true);
	propertyName.setRequired(true);
	form.add(propertyName);

	// value field
	final PropertyModel<String> propertyValueModel = new PropertyModel<String>(getDailyRhythmPortalSession()
		.getAddEditDrpProperty(), "value");
	form.add(new Label("propertyValueLabel", new ResourceModel("addEditPropertyForm.propertyValue")));
	propertyValue = new TextField<String>("propertyValue", propertyValueModel);
	propertyValue.setOutputMarkupPlaceholderTag(true);
	propertyValue.setRequired(true);
	form.add(propertyValue);

	// Wicket property flag
	final PropertyModel<Boolean> propertyWicketFlagModel = new PropertyModel<Boolean>(getDailyRhythmPortalSession()
		.getAddEditDrpProperty(), "wicketProperty");
	form.add(new Label("propertyWicketFlagLabel", new ResourceModel("addEditPropertyForm.propertyWicketFlag")));
	propertyWicketFlag = new CheckBox("propertyWicketFlag", propertyWicketFlagModel);
	propertyWicketFlag.setOutputMarkupId(true);
	propertyWicketFlag.setOutputMarkupPlaceholderTag(true);
	form.add(propertyWicketFlag);

	// Description Field
	final PropertyModel<String> descriptionModel = new PropertyModel<String>(getDailyRhythmPortalSession()
		.getAddEditDrpProperty(), "description");
	form.add(new Label("descriptionLabel", new ResourceModel("addEditPropertyForm.description")));
	description = new TextField<String>("description", descriptionModel);
	description.setOutputMarkupPlaceholderTag(true);
	form.add(description);

	// submit button
	AjaxButton submitButton = new AjaxButton("submitButton", new ResourceModel("addEditPropertyForm.submit.button")) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
		logger.info("in submit onSubmit...");
		DailyRhythmPortalSession session = getDailyRhythmPortalSession();

		try{
		    session.getAddEditDrpProperty().setModifiedBy(session.getDrpUser().getUserId());
		    DrpProperty drpProperty = session.getAddEditDrpProperty();
		    if (drpProperty.isWicketProperty()){
			String val = null;
			try{
			    val = getString(drpProperty.getName());
			}catch(MissingResourceException exc){
			    //Do nothing.
			}

			if (val == null){
			    error(drpProperty.getName()
				    + " doesn't exist in wicket property files. You can add only existing resource.");
			    target.add(feedbackPanel);
			    targetComponents(target);
			    return;
			}

			drpPropertyService.createOrUpdateProperty(drpProperty);
			this.getLocalizer().clearCache();
			session.setMenuBar(null);
		    }else{
			drpPropertyService.createOrUpdateProperty(drpProperty);
		    }
		    drpPropertyService.refreshPropertyMap();
		    if (drpProperty.getName().toUpperCase().startsWith("LOG4J")){
			logger.info("One of the Log4j property is changed. Creating Log4j from DB");
			loggerUtilService.createLog4jFromDB();
		    }
		}catch(ServiceException se){
		    error(se.getFullMessage());
		    return;
		}
		session.getAddEditDrpProperty().clear();
		setResponsePage(ManagePropertiesPage.class);
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
	AjaxButton cancelButton = new AjaxButton("cancelButton", new ResourceModel("addEditPropertyForm.cancel.button")) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
		logger.info("in cancel onSubmit...");
		DailyRhythmPortalSession session = getDailyRhythmPortalSession();
		session.getAddEditDrpProperty().setId(null);
		// Must re-attach models
		propertyName.setModel(propertyNameModel);
		propertyValue.setModel(propertyValueModel);
		description.setModel(descriptionModel);
		propertyWicketFlag.setModel(propertyWicketFlagModel);
		target.add(AddEditPropertyPanel.this);
		target.add(propertyTablePanel);
		targetComponents(target);
	    }

	    @Override
	    protected IAjaxCallDecorator getAjaxCallDecorator() {
		return new AjaxCallDecorator() {

		    private static final long serialVersionUID = 1L;

		    @Override
		    public CharSequence decorateScript(Component component, CharSequence script) {
			return "handleAddPropertyButtonEnabling(true);" + script;
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
	return ManagePropertiesPage.isInAddEditMode(getDailyRhythmPortalSession());
    }

    public void setPropertyTablePanel(final PropertyTablePanel propertyTablePanel) {
	this.propertyTablePanel = propertyTablePanel;
    }

    public void targetComponents(AjaxRequestTarget target) {
	logger.trace("targetComponents");
	target.add(propertyName);
	target.add(propertyValue);
	target.add(description);
	target.add(propertyWicketFlag);
    }
}
