package com.bestbuy.bbym.ise.drp.admin;

import org.apache.wicket.PageReference;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bestbuy.bbym.ise.drp.DailyRhythmPortalSession;
import com.bestbuy.bbym.ise.drp.common.BasePanel;
import com.bestbuy.bbym.ise.drp.domain.DrpProperty;
import com.bestbuy.bbym.ise.drp.loanerphone.CustomModalWindow;
import com.bestbuy.bbym.ise.drp.service.DrpPropertyService;
import com.bestbuy.bbym.ise.exception.ServiceException;

public class DeleteModalWindowPanel extends BasePanel {

    private static final long serialVersionUID = 1L;

    private static Logger logger = LoggerFactory.getLogger(DeleteModalWindowPanel.class);

    public DeleteModalWindowPanel(String id, PageParameters parameters, final PageReference modalWindowPage,
	    final CustomModalWindow window, String message, final DrpPropertyService drpPropertyService,
	    final DrpProperty selection) {
	super(id);

	logger.info("in the delete window");
	Form<Object> deleteModalWindowForm = new Form<Object>("deleteModalWindowForm");
	deleteModalWindowForm.setOutputMarkupPlaceholderTag(true);
	add(deleteModalWindowForm);

	final Label confirmationLabel = new Label("confirmationLabel", new Model<String>(message));
	confirmationLabel.setOutputMarkupPlaceholderTag(true);
	confirmationLabel.setEscapeModelStrings(false);
	deleteModalWindowForm.add(confirmationLabel);

	AjaxButton yesButton = new AjaxButton("yesButton", deleteModalWindowForm) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void onSubmit(AjaxRequestTarget target, Form<?> form) {

		logger.info("on submit....." + selection);
		try{
		    drpPropertyService.deleteProperty(selection.getName());
		    if (selection.isWicketProperty()){
			DailyRhythmPortalSession session = getDailyRhythmPortalSession();
			drpPropertyService.refreshPropertyMap();
			this.getLocalizer().clearCache();
			session.setMenuBar(null);
		    }
		}catch(ServiceException se){
		    error(se.getFullMessage());
		    // TODO Display error message on UI
		}
		logger.info("deleted successfully...");

		window.close(target);
		drpPropertyService.refreshPropertyMap();
		setResponsePage(ManagePropertiesPage.class);

	    }

	    @Override
	    protected void onError(AjaxRequestTarget target, Form<?> form) {
		// TODO Implement me!
	    }
	};

	yesButton.setOutputMarkupPlaceholderTag(true);
	add(yesButton);

	AjaxButton noButton = new AjaxButton("noButton", deleteModalWindowForm) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
		if (target != null){
		    window.close(target);
		}
	    }

	    @Override
	    protected void onError(AjaxRequestTarget target, Form<?> form) {
		// TODO Implement me!
	    }
	};
	noButton.setOutputMarkupPlaceholderTag(true);
	add(noButton);

    }

}
