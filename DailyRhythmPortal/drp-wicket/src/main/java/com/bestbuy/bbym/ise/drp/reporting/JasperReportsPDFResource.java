package com.bestbuy.bbym.ise.drp.reporting;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRRuntimeException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;

import org.apache.wicket.request.resource.AbstractResource;
import org.apache.wicket.request.resource.ContentDisposition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author a186288
 */
public class JasperReportsPDFResource extends AbstractResource {

    private static Logger logger = LoggerFactory.getLogger(JasperReportsPDFResource.class);
    private static final long serialVersionUID = 1L;

    private transient JasperPrint print;

    public JasperReportsPDFResource(JasperPrint print) {
	this.print = print;
    }

    @Override
    protected ResourceResponse newResourceResponse(Attributes attributes) {
	final ResourceResponse response = new ResourceResponse();

	response.setContentDisposition(ContentDisposition.INLINE);
	response.setContentType("application/pdf");
	response.getHeaders().addHeader("filename", "report.pdf");

	response.setWriteCallback(new WriteCallback() {

	    @Override
	    public void writeData(Attributes attributes) {

		JRPdfExporter exporter = new JRPdfExporter();
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
