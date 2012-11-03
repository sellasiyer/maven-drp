package com.bestbuy.bbym.ise.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringBufferInputStream;
import java.net.URL;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xhtmlrenderer.pdf.ITextRenderer;
import org.xml.sax.SAXException;

import com.bestbuy.bbym.ise.exception.IseExceptionCodeEnum;
import com.bestbuy.bbym.ise.exception.ServiceException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.AcroFields;
import com.lowagie.text.pdf.AcroFields.Item;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfCopy;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;

/**
 * @author a885527 (miked)
 */
public class PDFUtils {

    private static Logger logger = LoggerFactory.getLogger(PDFUtils.class);

    /**
     * Given a String of html, uses itext/flying saucer to convert into a PDF
     * document which is stuffed into the returned byteArrayOutputStream Flying
     * Saucer should provide CSS support.
     * 
     * @param htmlString
     *            The HTML to convert.
     * @return A ByteArrayOutputStream containing the rendered PDF.
     * @throws ServiceException
     */
    // private because i don't expect class to be extended or this f() to be
    // used
    // directly. Feel free to change.
    public static ByteArrayOutputStream parseHtmlToStream(final String htmlString) throws ServiceException {
	DocumentBuilder builder = null;

	try{
	    builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
	}catch(ParserConfigurationException e){
	    logger.error(e.getMessage());
	    throw new ServiceException(IseExceptionCodeEnum.PDFCreationError);
	}
	org.w3c.dom.Document doc = null;

	try{
	    // TODO: Replace this with un-deprecated IO object.
	    doc = builder.parse(new StringBufferInputStream(htmlString));
	}catch(SAXException e){
	    logger.error(e.getMessage());
	    throw new ServiceException(IseExceptionCodeEnum.PDFCreationError);
	}catch(IOException e){
	    logger.error(e.getMessage());
	    throw new ServiceException(IseExceptionCodeEnum.PDFCreationError);
	}

	ITextRenderer renderer = new ITextRenderer();
	renderer.setDocument(doc, null);

	ByteArrayOutputStream baos = new ByteArrayOutputStream();
	renderer.layout();

	try{
	    renderer.createPDF(baos);
	}catch(com.lowagie.text.DocumentException e){
	    logger.error(e.getMessage());
	    throw new ServiceException(IseExceptionCodeEnum.PDFCreationError);
	}
	renderer.finishPDF();
	try{
	    baos.close();
	}catch(IOException e){
	    logger.error(e.getMessage());
	    // not throwing ServiceException because baos should still be
	    // usable.
	}
	return baos;
    }

    /**
     * Populates the PDF template with the supplied values.
     * 
     * @param fileUrl
     *            the PDF template
     * @param fieldNameValueMap
     *            a {@code Map} whose keys represent the field names defined in
     *            the PDF template and values represent the text to be written
     *            to the PDF. Formatting of date fields should be done by the
     *            caller
     * @param imageInfos
     *            information for images that need to be merged into the PDF.
     *            The image will be scaled to fit
     * @return a byte array containing the PDF with all the fields filled in
     * @throws ServiceException
     *             if the file could not be read or if the the populated PDF
     *             could not be created
     */
    public static byte[] populatePdfTemplateWithValues(URL fileUrl, Map<String, String> fieldNameValueMap,
	    List<ImageInfo> imageInfos) throws ServiceException {

	logger.info("Merging " + fieldNameValueMap.keySet().size() + " fields into file - " + fileUrl);
	if (fileUrl == null)
	    throw new ServiceException(IseExceptionCodeEnum.PDFCreationError);
	ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	PdfStamper pdfStamper = null;
	try{
	    pdfStamper = new PdfStamper(new PdfReader(fileUrl), outputStream);
	}catch(Exception e){
	    logger.error("Failed to create PDF stamper", e);
	    throw new ServiceException(IseExceptionCodeEnum.PDFCreationError);
	}
	pdfStamper.setFormFlattening(true);

	AcroFields acroFields = pdfStamper.getAcroFields();

	int mergedFieldCount = 0;
	for(String fieldName: fieldNameValueMap.keySet()){
	    String fieldValue = fieldNameValueMap.get(fieldName);
	    logger.debug("Populating " + fieldName + " with value " + fieldValue);

	    Item item = acroFields.getFieldItem(fieldName);
	    if (item == null){
		logger.warn("Could not find field with name " + fieldName + " in PDF. Skipping it");
		continue;
	    }

	    logger.trace("There are " + item.size() + " fields named " + fieldName + " defined in the PDF");
	    for(int i = 0; i < item.size(); i++){
		try{
		    acroFields.setField(fieldName, fieldValue);
		    mergedFieldCount++;
		    logger.debug("Field populated successfully");
		}catch(Exception e){
		    logger.warn("Failed to populate field value", e);
		}
	    }
	}

	if (imageInfos != null && !imageInfos.isEmpty()){
	    for(ImageInfo imageInfo: imageInfos){
		if (logger.isDebugEnabled()){
		    logger.debug("Merging image pageNumber=" + imageInfo.getPageNumber() + ", xPosition="
			    + imageInfo.getXPosition() + ", yPosition=" + imageInfo.getYPosition() + ", imageHeight="
			    + imageInfo.getImageHeight() + ", imageWidth=" + imageInfo.getImageWidth());
		}
		Image image;
		try{
		    image = Image.getInstance(imageInfo.getImageContent());
		}catch(Exception e){
		    logger.error("Error occurred getting image content, skipping image merge", e);
		    break;
		}
		image.scaleToFit(imageInfo.getImageWidth(), imageInfo.getImageHeight());
		PdfContentByte pdfContentByte = pdfStamper.getOverContent(imageInfo.getPageNumber());
		image.setAbsolutePosition(imageInfo.getXPosition(), imageInfo.getYPosition());
		try{
		    pdfContentByte.addImage(image);
		}catch(DocumentException e){
		    logger.error("Error adding image to PDF, skipping image merge", e);
		    break;
		}
	    }
	}

	try{
	    pdfStamper.close();
	}catch(Exception e){
	    logger.error("Failed to close PDF stamper", e);
	    throw new ServiceException(IseExceptionCodeEnum.PDFCreationError);
	}

	logger.info("Done merging values into PDF. " + mergedFieldCount + " fields set");
	byte[] pdfBytes = outputStream.toByteArray();
	return pdfBytes;
    }

    /**
     * Image placement and content used to merge images into PDF documents.
     */
    public static class ImageInfo {

	private int pageNumber;
	private byte[] imageContent;
	private int xPosition;
	private int yPosition;
	private int imageWidth;
	private int imageHeight;

	/**
	 * Creates an {@code ImageInfo} with the information needed to merge the
	 * image into a PDF.
	 * 
	 * @param pageNumber
	 *            the page that the image should be placed on
	 * @param imageContent
	 *            the image content
	 * @param xPosition
	 *            the X position (in points) that the image should be placed at
	 * @param yPosition
	 *            the Y position (in points) that the image should be placed at
	 * @param imageWidth
	 *            the width (in points) that the image should consume in the PDF
	 * @param imageHeight
	 *            the height (in points) that the image should consume in the PDF
	 */
	public ImageInfo(int pageNumber, byte[] imageContent, int xPosition, int yPosition, int imageWidth,
		int imageHeight) {
	    this.pageNumber = pageNumber;
	    this.imageContent = imageContent;
	    this.xPosition = xPosition;
	    this.yPosition = yPosition;
	    this.imageWidth = imageWidth;
	    this.imageHeight = imageHeight;
	}

	public int getPageNumber() {
	    return pageNumber;
	}

	public byte[] getImageContent() {
	    return imageContent;
	}

	public int getXPosition() {
	    return xPosition;
	}

	public int getYPosition() {
	    return yPosition;
	}

	public int getImageWidth() {
	    return imageWidth;
	}

	public int getImageHeight() {
	    return imageHeight;
	}

    }

    /**
     * Glues a bunch of PDFs together into a single PDF.
     * 
     * @param pdfByteList
     *            a {@code List} containing the PDF byte arrays that need to be
     *            concatenated into a single PDF
     */
    public static byte[] concatenatePdfs(List<byte[]> pdfByteList) throws ServiceException {

	logger.info("Merging " + pdfByteList.size() + " PDFs into a single PDF");

	if (pdfByteList.size() == 1){
	    logger.debug("Only one PDF, so just returning it");
	    return pdfByteList.get(0);
	}

	ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	try{
	    byte[] pdfBytes = pdfByteList.get(0);

	    // Copy the first PDF
	    PdfReader reader = new PdfReader(pdfBytes);
	    Document document = new Document(reader.getPageSizeWithRotation(1));
	    PdfCopy copy = new PdfCopy(document, outputStream);
	    document.open();
	    for(int page = 1; page <= reader.getNumberOfPages(); page++){
		copy.addPage(copy.getImportedPage(reader, page));
	    }

	    // Concatenate the rest of the contracts
	    for(int i = 1; i < pdfByteList.size(); i++){
		logger.debug("Merging PDF " + (i + 1));
		pdfBytes = pdfByteList.get(i);
		reader = new PdfReader(pdfBytes);
		for(int page = 1; page <= reader.getNumberOfPages(); page++){
		    copy.addPage(copy.getImportedPage(reader, page));
		}
	    }
	    document.close();
	}catch(Throwable t){
	    logger.error("Failed to concatenate PDFs", t);
	    throw new ServiceException(IseExceptionCodeEnum.PDFCreationError);
	}
	return outputStream.toByteArray();
    }
}
