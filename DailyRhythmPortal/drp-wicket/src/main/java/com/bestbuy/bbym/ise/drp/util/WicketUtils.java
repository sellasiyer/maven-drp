package com.bestbuy.bbym.ise.drp.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

import javax.imageio.ImageIO;

import net.sourceforge.barbecue.Barcode;
import net.sourceforge.barbecue.BarcodeFactory;
import net.sourceforge.barbecue.BarcodeImageHandler;

import org.apache.commons.lang.StringUtils;
import org.apache.wicket.Component;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.BufferedWebResponse;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bestbuy.bbym.ise.domain.Product;
import com.bestbuy.bbym.ise.drp.common.PdfByteArrayResource;
import com.bestbuy.bbym.ise.drp.domain.FourPartKey;
import com.bestbuy.bbym.ise.exception.ServiceException;
import com.bestbuy.bbym.ise.util.PDFUtils;
import com.bestbuy.bbym.ise.util.Util;

public class WicketUtils {

    private static Logger logger = LoggerFactory.getLogger(WicketUtils.class);

    private WicketUtils() {
	// This class is not meant to be instantiated
    }

    public static boolean getBooleanProperty(String propertyName, boolean defaultValue, Component component) {
	String value = component.getString(propertyName);
	if (value == null){
	    return defaultValue;
	}
	if (value.equalsIgnoreCase("true") || value.equalsIgnoreCase("yes")){
	    return true;
	}
	return false;
    }

    public static boolean determineBooleanParameter(final PageParameters parameters, String parameterName) {
	if (parameters == null){
	    return false;
	}
	Boolean value = parameters.get(parameterName).toOptionalBoolean();
	if (value == null){
	    return false;
	}
	return value.booleanValue();
    }

    public static String generateFourPartKeyHTML(final Product product, final String notAvailableLabel) {
	StringBuilder sb = new StringBuilder();
	if (product == null || StringUtils.isBlank(product.getTransactionId())){
	    sb.append(notAvailableLabel);
	}else{
	    // If dot com order display the transaction id without
	    // formatting
	    if (product.getTransactionId().startsWith("BBY")){
		sb.append(product.getTransactionId());
		sb.append("<br/>");
		if (product.getPurchaseDate() == null){
		    sb.append(notAvailableLabel);
		}else{
		    sb.append(Util.toString(product.getPurchaseDate(), "MM/dd/yy"));
		}

	    }else{
		String[] buf = product.getTransactionId().split("\\^");
		if (buf.length != 4){
		    sb.append(product.getTransactionId());
		}else{
		    sb.append(buf[0]);
		    sb.append(" ");
		    sb.append(buf[2]);
		    sb.append(" ");
		    sb.append(buf[3]);
		    sb.append("<br/>");
		    if (buf[1] != null){
			sb.append(buf[1].substring(4, 6));
			sb.append("/");
			sb.append(buf[1].substring(6));
			sb.append("/");
			sb.append(buf[1].substring(2, 4));
		    }
		}
	    }
	}
	return sb.toString();
    }

    public static FourPartKey generateFourPartKey(final Product product) {
	if (product == null || product.getTransactionId() == null){
	    return null;
	}
	String[] transArr = product.getTransactionId().split("\\^");
	if (transArr.length <= 3){
	    return null;
	}
	FourPartKey fpk = new FourPartKey();
	fpk.setStoreId(transArr[0]);
	fpk.setBusinessDate(product.getPurchaseDate());
	fpk.setWorkStationId(transArr[2]);
	fpk.setRegisterTransactionNum(transArr[3]);
	return fpk;
    }

    /**
     * Given a webpage, dummy-renders the page and returns the rendered html.
     * 
     * @param webPage
     *            An instantiated page
     * @return A buffer of HTML.
     */
    public static String renderHTMLFromPage(WebPage webPage) {
	BufferedWebResponse bufferedWebResponse = new BufferedWebResponse(null);
	webPage.getRequestCycle().setResponse(bufferedWebResponse);
	webPage.render();

	CharSequence cs = bufferedWebResponse.getText();
	return cs.toString();
    }

    /**
     * Given a string of HTML, calls FlyingSaucer / IText to return a
     * (flyingsaucer is css support for pdf conversion, if you were wondering)
     * PDFByteArrayResource This should have pretty good CSS support. CSS should
     * be
     * 
     * @param htmlString
     *            A string containing HTML that you want converted.
     * @return A PDFByteArrayResource containing the PDF.
     * @See ViewCheckedOutPage.java for info on displaying PDF.
     */
    public static PdfByteArrayResource renderPDFFromHtml(final String htmlString) {
	PdfByteArrayResource pdfResource = new PdfByteArrayResource() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public String fetchData() {
		setPdfByteArray(null);

		try{
		    ByteArrayOutputStream baos = PDFUtils.parseHtmlToStream(htmlString);
		    setPdfByteArray(baos.toByteArray());
		}catch(ServiceException se){

		}

		return null;
	    }

	};
	pdfResource.fetchData();
	return pdfResource;
    }

    // Using Bargeque API to generate Barcode image as bytes for any text String
    public static byte[] generateBarCodeBytes(String strText) {

	byte[] imageInByte = null;

	// Get 128B Barcode instance from the Factory
	try{
	    Barcode barcode = BarcodeFactory.createCode128B(strText);
	    BufferedImage bufferedImg = BarcodeImageHandler.getImage(barcode);
	    ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    ImageIO.write(bufferedImg, "jpg", baos);
	    baos.flush();
	    imageInByte = baos.toByteArray();
	    baos.close();

	}catch(Exception e){
	    logger.error("Error generating bar code for " + strText, e);
	    return null;
	}

	return imageInByte;

    }
}
