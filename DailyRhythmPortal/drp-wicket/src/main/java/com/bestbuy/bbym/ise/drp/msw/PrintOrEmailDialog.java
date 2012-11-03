/**
 * Best Buy (c)2011
 */
package com.bestbuy.bbym.ise.drp.msw;

import java.io.ByteArrayOutputStream;
import java.util.UUID;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;

import org.apache.commons.lang.StringUtils;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.PopupSettings;
import org.apache.wicket.markup.html.link.ResourceLink;
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
import com.bestbuy.bbym.ise.drp.domain.Recommendation;
import com.bestbuy.bbym.ise.drp.reporting.JasperReportsPDFResource;
import com.bestbuy.bbym.ise.drp.service.EmailService;
import com.bestbuy.bbym.ise.exception.IseExceptionCodeEnum;
import com.bestbuy.bbym.ise.util.Util;

public class PrintOrEmailDialog extends BaseWebPage {

    private static Logger logger = LoggerFactory.getLogger(PrintOrEmailDialog.class);
    private static final long serialVersionUID = 1L;

    @SpringBean
    private EmailService emailService;

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
		try{
		    Recommendation[] recs = new Recommendation[] {recommendation };
		    JasperPrint jasperPrint = JasperFillManager.fillReport(this.getClass().getClassLoader()
			    .getResourceAsStream("RecommendationSheet.jasper"), null, new JRBeanArrayDataSource(recs));
		    ByteArrayOutputStream pdfOutputStream = new ByteArrayOutputStream();
		    JasperExportManager.exportReportToPdfStream(jasperPrint, pdfOutputStream);
		    emailService.sendEmail(pdfOutputStream, emailAddress);
		}catch(Exception e){
		    String message = "An unexpected exception occured while attempting to email the rec sheet PDF";
		    logger.error(e.getMessage());
		    processException(message, IseExceptionCodeEnum.ReportError);
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

	String uuidString = UUID.randomUUID().toString().replaceAll("-", "");
	ResourceReference ref = new ResourceReference(uuidString) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public IResource getResource() {
		logger.info("Generating rec sheet for recommendation " + recommendation.getId());
		JasperReportsPDFResource jrpdf = null;
		try{
		    Recommendation[] recs = new Recommendation[] {recommendation };
		    JasperPrint jasperPrint = JasperFillManager.fillReport(this.getClass().getClassLoader()
			    .getResourceAsStream("RecommendationSheet.jasper"), null, new JRBeanArrayDataSource(recs));
		    logger.info("Generated report");
		    jrpdf = new JasperReportsPDFResource(jasperPrint);
		}catch(Exception e){
		    String message = "Unexpected Exception while generating rec sheet PDF";
		    logger.error(message, e);
		    processException(message, IseExceptionCodeEnum.ReportError);
		}
		return jrpdf;
	    }
	};

	if (ref.canBeRegistered()){
	    getApplication().getResourceReferenceRegistry().registerResourceReference(ref);
	}

	ResourceLink<JasperReportsPDFResource> printLink = new ResourceLink<JasperReportsPDFResource>("printLink", ref);
	PopupSettings popupSettings = new PopupSettings(PopupSettings.RESIZABLE | PopupSettings.SCROLLBARS);
	printLink.setPopupSettings(popupSettings);
	printEmailRecSheetForm.add(printLink);
    }

    public String getEmailAddress() {
	return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
	this.emailAddress = emailAddress;
    }

}
