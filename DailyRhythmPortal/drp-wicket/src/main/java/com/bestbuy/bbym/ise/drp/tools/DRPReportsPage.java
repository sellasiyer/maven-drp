package com.bestbuy.bbym.ise.drp.tools;

import java.sql.Connection;
import java.util.UUID;

import javax.sql.DataSource;

import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.IResource;
import org.apache.wicket.request.resource.ResourceReference;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bestbuy.bbym.ise.drp.common.BaseNavPage;
import com.bestbuy.bbym.ise.drp.common.ExceptionPageHandler;
import com.bestbuy.bbym.ise.drp.reporting.JasperReportsPDFResource;
import com.bestbuy.bbym.ise.exception.IseExceptionCodeEnum;

public class DRPReportsPage extends BaseNavPage {

    private static Logger logger = LoggerFactory.getLogger(DRPReportsPage.class);
    private static final long serialVersionUID = 1L;

    @SpringBean
    private DataSource datasource;

    public DRPReportsPage(PageParameters parameters) {

	super(parameters);

	final FeedbackPanel feedbackPanel = new FeedbackPanel("feedback");
	feedbackPanel.setOutputMarkupPlaceholderTag(true);
	add(feedbackPanel);

	final Form<Object> reportsForm = new Form<Object>("reportsForm");
	add(reportsForm);

	final AjaxButton triageReportButton = new AjaxButton("triageReportButton", new ResourceModel(
		"triageReport.button")) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void onSubmit(AjaxRequestTarget target, Form<?> form) {

		String uuidString = UUID.randomUUID().toString().replaceAll("-", "");
		ResourceReference ref = new ResourceReference(uuidString) {

		    private static final long serialVersionUID = 1L;

		    @Override
		    public IResource getResource() {
			JasperReportsPDFResource jrpdf = null;
			try{

			    Connection connection = datasource.getConnection();
			    JasperPrint jasperPrint = JasperFillManager.fillReport(this.getClass().getClassLoader()
				    .getResourceAsStream("triageCompletionReport.jasper"), null, connection);
			    jrpdf = new JasperReportsPDFResource(jasperPrint);

			}catch(Exception e){
			    String message = "An unexpected exception occured while attempting to render a Triage report";
			    logger.error(e.getMessage());
			    ExceptionPageHandler.processException(message, IseExceptionCodeEnum.ReportError, getPage()
				    .getPageParameters(), getDailyRhythmPortalSession());
			}

			return jrpdf;
		    }
		};

		if (ref.canBeRegistered()){
		    getApplication().getResourceReferenceRegistry().registerResourceReference(ref);
		    target.appendJavaScript("window.open('" + urlFor(ref, null) + "')");
		}
	    }

	    @Override
	    protected void onError(AjaxRequestTarget target, Form<?> form) {
		// TODO Implement me!
	    }
	};
	reportsForm.add(triageReportButton);

    }
}
