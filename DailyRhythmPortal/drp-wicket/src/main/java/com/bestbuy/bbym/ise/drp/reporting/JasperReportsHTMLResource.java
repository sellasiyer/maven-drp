package com.bestbuy.bbym.ise.drp.reporting;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRRuntimeException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRHtmlExporter;

import org.apache.wicket.request.resource.AbstractResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author a186288
 */
public class JasperReportsHTMLResource extends AbstractResource {

    private static Logger logger = LoggerFactory.getLogger(JasperReportsHTMLResource.class);

    private static final long serialVersionUID = -5247243315308187493L;

    private JasperPrint print;

    public JasperReportsHTMLResource(JasperPrint print) {
	this.print = print;
    }

    @Override
    protected ResourceResponse newResourceResponse(Attributes attributes) {
	final ResourceResponse response = new ResourceResponse();

	response.setContentType("text/html");

	response.setWriteCallback(new WriteCallback() {

	    @Override
	    public void writeData(Attributes attributes) {

		JRHtmlExporter exporter = new JRHtmlExporter();
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
		exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, attributes.getResponse().getOutputStream());
		try{
		    exporter.exportReport();
		}catch(JRException e){
		    logger.error("Error exporting report", e);
		    throw new JRRuntimeException(e);
		}

	    }
	});

	return response;
    }
}
