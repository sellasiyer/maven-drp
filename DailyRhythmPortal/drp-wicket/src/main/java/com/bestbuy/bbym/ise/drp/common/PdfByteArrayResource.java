package com.bestbuy.bbym.ise.drp.common;

import java.io.IOException;

import org.apache.wicket.request.resource.AbstractResource;
import org.apache.wicket.request.resource.ContentDisposition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class PdfByteArrayResource extends AbstractResource {

    private static final long serialVersionUID = 1L;
    private static Logger logger = LoggerFactory.getLogger(PdfByteArrayResource.class);

    private byte[] pdfByteArray;

    public PdfByteArrayResource() {
    }

    @Override
    protected ResourceResponse newResourceResponse(Attributes attributes) {
	final ResourceResponse response = new ResourceResponse();
	response.setContentDisposition(ContentDisposition.INLINE);
	response.setContentType("application/pdf");
	response.setWriteCallback(new WriteCallback() {

	    @Override
	    public void writeData(Attributes attributes) {
		try{
		    if (pdfByteArray == null || pdfByteArray.length == 0){
			logger.warn("pdfByteArray is empty");
		    }
		    attributes.getResponse().getOutputStream().write(pdfByteArray);
		}catch(IOException e){
		    logger.error("IOException in writeData", e);
		}
	    }
	});
	return response;
    }

    // Return error string or null if no error
    public abstract String fetchData();

    public byte[] getPdfByteArray() {
	return pdfByteArray;
    }

    public void setPdfByteArray(byte[] pdfByteArray) {
	this.pdfByteArray = pdfByteArray;
    }

}
