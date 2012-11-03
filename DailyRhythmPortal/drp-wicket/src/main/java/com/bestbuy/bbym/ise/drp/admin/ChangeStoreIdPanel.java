package com.bestbuy.bbym.ise.drp.admin;

import org.apache.commons.lang.StringUtils;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.validation.validator.PatternValidator;
import org.apache.wicket.validation.validator.StringValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bestbuy.bbym.ise.drp.common.BasePanel;
import com.bestbuy.bbym.ise.drp.domain.DrpUser;

public class ChangeStoreIdPanel extends BasePanel {

    private static final long serialVersionUID = 1L;
    private static Logger logger = LoggerFactory.getLogger(ChangeStoreIdPanel.class);

    private TextField<String> changedStoreId;

    public ChangeStoreIdPanel(String id, final FeedbackPanel feedbackPanel) {
	super(id);

	// form
	final Form<Object> form = new Form<Object>("changeStoreIdForm");
	form.setOutputMarkupPlaceholderTag(true);
	add(form);

	// changeStoreId field
	form.add(new Label("changeStoreIdLabel", new StringResourceModel("changeStoreIdForm.storeIDLabel", this,
		new Model<DrpUser>(getDailyRhythmPortalSession().getDrpUser()))));
	final PropertyModel<String> propertyStoreIdModel = new PropertyModel<String>(getDailyRhythmPortalSession()
		.getDrpUser(), "storeId");
	changedStoreId = new TextField<String>("changedStoreId", propertyStoreIdModel);
	changedStoreId.setOutputMarkupPlaceholderTag(true);
	changedStoreId.add(new PatternValidator("[0-9]{1,4}"));
	changedStoreId.add(StringValidator.maximumLength(4));
	changedStoreId.setRequired(true);
	form.add(changedStoreId);

	// submit button
	AjaxButton submitButton = new AjaxButton("submitButton", new ResourceModel("changeStoreIdForm.submit")) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
		logger.info("Changing store ID ...");
		changedStoreId.setModel(propertyStoreIdModel);
		setStoreIdLabel();
		if (target != null){
		    target.add(ChangeStoreIdPanel.this);
		    targetComponents(target);
		}
		if (getDailyRhythmPortalSession().getDrpUser().getStore() != null){
		    getDailyRhythmPortalSession().getDrpUser().getStore().setStoreType(null);
		    getDailyRhythmPortalSession().getDrpUser().getStore().setStoreName(null);
		    getDailyRhythmPortalSession().getDrpUser().getStore().setStorePhoneNumber(null);
		}
	    }

	    private void setStoreIdLabel() {
		String changeStoreIdLabel = StringUtils.replace(getString("changeStoreIdForm.storeIDLabel"),
			"$(storeId)", getDailyRhythmPortalSession().getDrpUser().getStoreId());
		addOrReplace(new Label("changeStoreIdLabel", changeStoreIdLabel));
	    }

	    @Override
	    protected void onError(AjaxRequestTarget target, Form<?> form) {
		logger.info("in submit onError...");
		if (target != null){
		    target.add(feedbackPanel);
		    targetComponents(target);
		}
	    }
	};
	submitButton.setOutputMarkupPlaceholderTag(true);
	form.add(submitButton);

    }

    public void targetComponents(AjaxRequestTarget target) {
	logger.trace("targetComponents");
	target.add(changedStoreId);
    }
}
