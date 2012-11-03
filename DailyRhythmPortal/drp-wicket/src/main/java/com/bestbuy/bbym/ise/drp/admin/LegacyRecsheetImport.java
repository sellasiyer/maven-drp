package com.bestbuy.bbym.ise.drp.admin;

import java.util.Date;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.IAjaxCallDecorator;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.extensions.markup.html.form.DateTextField;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bestbuy.bbym.ise.drp.common.BaseNavPage;
import com.bestbuy.bbym.ise.drp.service.MobileSalesWorkspaceService;
import com.bestbuy.bbym.ise.drp.utils.DrpConstants;

@AuthorizeInstantiation( {DrpConstants.DRP_ADMIN })
public class LegacyRecsheetImport extends BaseNavPage {

    private static Logger logger = LoggerFactory.getLogger(LegacyRecsheetImport.class);
    private static final long serialVersionUID = 1L;

    @SpringBean(name = "mobileSalesWorkspaceService")
    private MobileSalesWorkspaceService mswService;

    DateTextField fromDateField, toDateField;
    private Date fromDate, toDate;

    public LegacyRecsheetImport(final PageParameters parameters) {
	super(null);

	Form<Object> form = new Form<Object>("legacyRecSheetForm");
	add(form);

	final FeedbackPanel feedbackPanel = new FeedbackPanel("feedback");
	feedbackPanel.setOutputMarkupPlaceholderTag(true);
	add(feedbackPanel);

	fromDateField = new DateTextField("fromDate", new PropertyModel<Date>(this, "fromDate"), "MM/dd/yy");
	fromDateField.setOutputMarkupPlaceholderTag(true);

	toDateField = new DateTextField("toDate", new PropertyModel<Date>(this, "toDate"), "MM/dd/yy");
	toDateField.setOutputMarkupPlaceholderTag(true);

	form.add(fromDateField);
	form.add(toDateField);

	AjaxButton button = new AjaxButton("submitButton") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
		try{
		    mswService.convertLegacyRecSheets(fromDate, toDate);
		    error("Service call completed Successfully !! ");
		}catch(Exception se){
		    error("Exception in service call :: " + se.getMessage());
		    logger.debug("Service Exception ::" + se.getMessage());
		}
		target.add(feedbackPanel);
	    }

	    @Override
	    protected IAjaxCallDecorator getAjaxCallDecorator() {
		return new IAjaxCallDecorator() {

		    private static final long serialVersionUID = 1L;

		    public CharSequence decorateScript(Component comp, CharSequence script) {
			return "showButtonLoading(true, '#submitButton');" + script;
		    }

		    public CharSequence decorateOnFailureScript(Component comp, CharSequence script) {
			return "showButtonLoading(false, '#submitButton');" + script;
		    }

		    public CharSequence decorateOnSuccessScript(Component comp, CharSequence script) {
			return "showButtonLoading(false, '#submitButton');" + script;
		    }
		};
	    }

	};
	button.setMarkupId("submitButton");
	button.setOutputMarkupId(true);
	button.setOutputMarkupPlaceholderTag(true);
	form.add(button);

    }

}
