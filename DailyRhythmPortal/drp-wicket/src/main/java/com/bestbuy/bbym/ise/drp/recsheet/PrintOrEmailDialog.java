/**
 * Best Buy (c)2011
 */
package com.bestbuy.bbym.ise.drp.recsheet;

import java.io.ByteArrayOutputStream;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.resource.IResource;
import org.apache.wicket.request.resource.ResourceReference;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.validation.validator.EmailAddressValidator;
import org.apache.wicket.validation.validator.StringValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bestbuy.bbym.ise.drp.common.BaseWebPage;
import com.bestbuy.bbym.ise.drp.common.PdfByteArrayResource;
import com.bestbuy.bbym.ise.drp.domain.Recommendation;
import com.bestbuy.bbym.ise.drp.service.EmailService;
import com.bestbuy.bbym.ise.drp.service.MobileSalesWorkspaceService;
import com.bestbuy.bbym.ise.exception.IseExceptionCodeEnum;
import com.bestbuy.bbym.ise.util.Util;

public class PrintOrEmailDialog extends BaseWebPage {

    private static Logger logger = LoggerFactory.getLogger(PrintOrEmailDialog.class);
    private static final long serialVersionUID = 1L;

    @SpringBean
    private EmailService emailService;

    @SpringBean(name = "mobileSalesWorkspaceService")
    private MobileSalesWorkspaceService mswService;

    private String emailAddress;

    public PrintOrEmailDialog(final Recommendation recommendation) {
	super();

	if (getDailyRhythmPortalSession().getCustomer() != null
		&& StringUtils.isNotBlank(getDailyRhythmPortalSession().getCustomer().getEmail())){
	    emailAddress = getDailyRhythmPortalSession().getCustomer().getEmail();
	}

	final Form<PrintOrEmailDialog> printEmailRecSheetForm = new Form<PrintOrEmailDialog>("printEmailRecSheetForm");
	add(printEmailRecSheetForm);

	FeedbackPanel feedbackPanel = new FeedbackPanel("feedback");
	feedbackPanel.setOutputMarkupPlaceholderTag(true);
	printEmailRecSheetForm.add(feedbackPanel);

	final Label emailMsg = new Label("emailMsg");
	emailMsg.setOutputMarkupId(true);
	printEmailRecSheetForm.add(emailMsg);

	final AjaxButton emailButton = new AjaxButton("emailBtn", new ResourceModel(
		"printEmailRecSheetForm.email.button")) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onSubmit(AjaxRequestTarget target, Form<?> form) {

		ByteArrayOutputStream pdfOutputStream = new ByteArrayOutputStream();

		try{

		    byte[] pdfBytes = mswService.getRecSheetPDF(recommendation);
		    pdfOutputStream.write(pdfBytes);
		    emailService.sendEmail(pdfOutputStream, emailAddress);

		}catch(Exception e){
		    String message = "An unexpected exception occured while attempting to email the rec sheet PDF";
		    logger.error(e.getMessage());
		    processException(message, IseExceptionCodeEnum.ReportError);
		}finally{
		    try{
			pdfOutputStream.close();
		    }catch(Exception e){
			logger.debug("excep......" + e.getMessage());
		    }
		}
		emailMsg.setDefaultModel(new Model<String>("Email Sent"));
		target.add(emailMsg);
	    }

	    @Override
	    protected void onError(AjaxRequestTarget target, Form<?> form) {
		// TODO Implement me!
	    }
	};
	emailButton.setOutputMarkupPlaceholderTag(true);
	printEmailRecSheetForm.add(emailButton);

	TextField<String> emailInput = new TextField<String>("email", new PropertyModel<String>(this, "emailAddress")) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public boolean isRequired() {
		return printEmailRecSheetForm.findSubmittingButton() == emailButton;
	    }
	};
	emailInput.add(EmailAddressValidator.getInstance());
	emailInput.add(StringValidator.maximumLength(Util.getInt(getString("FLD_EMAIL_LENGTH"), 200)));
	printEmailRecSheetForm.add(emailInput);

    }

    public String getEmailAddress() {
	return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
	this.emailAddress = emailAddress;
    }

}
