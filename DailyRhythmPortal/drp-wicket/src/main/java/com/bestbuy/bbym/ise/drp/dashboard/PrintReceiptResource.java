package com.bestbuy.bbym.ise.drp.dashboard;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.wicket.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bestbuy.bbym.ise.domain.Product;
import com.bestbuy.bbym.ise.domain.ServicePlanTransaction;
import com.bestbuy.bbym.ise.drp.common.PdfByteArrayResource;
import com.bestbuy.bbym.ise.drp.domain.DrpUser;
import com.bestbuy.bbym.ise.drp.domain.FourPartKey;
import com.bestbuy.bbym.ise.drp.service.CustomerDataService;
import com.bestbuy.bbym.ise.drp.util.WicketUtils;
import com.bestbuy.bbym.ise.exception.IseBusinessException;
import com.bestbuy.bbym.ise.exception.ServiceException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;

public class PrintReceiptResource extends PdfByteArrayResource {

    private static final long serialVersionUID = 1L;
    private static Logger logger = LoggerFactory.getLogger(PrintReceiptResource.class);

    public enum BaseObjectType {
	SERVICE_PLAN_TRANSACTION, PRODUCT
    }

    private Component component;
    private CustomerDataService customerDataService;
    private DrpUser drpUser;
    private ServicePlanTransaction transaction;
    private Product product;
    private BaseObjectType baseObjectType = BaseObjectType.SERVICE_PLAN_TRANSACTION;

    public PrintReceiptResource(Component component, CustomerDataService customerDataService,
	    BaseObjectType baseObjectType) {
	super();
	this.component = component;
	this.customerDataService = customerDataService;
	if (baseObjectType != null){
	    this.baseObjectType = baseObjectType;
	}
    }

    @Override
    public String fetchData() {
	setPdfByteArray(null);
	String bFile = null;
	FourPartKey fourPartKey = null;
	if (baseObjectType == BaseObjectType.SERVICE_PLAN_TRANSACTION
		&& (transaction == null || transaction.getStoreNum() == null || transaction.getRegisterNum() == null
			|| transaction.getPurchaseDate() == null || transaction.getTransactionNum() == null)){
	    return component.getString("printTransactionReceipt.badTransaction.message.label");
	}else if (baseObjectType == BaseObjectType.PRODUCT && product == null){
	    return component.getString("printTransactionReceipt.badTransaction.message.label");
	}else if (baseObjectType == BaseObjectType.PRODUCT && product != null){
	    fourPartKey = WicketUtils.generateFourPartKey(product);
	}

	if (baseObjectType == BaseObjectType.PRODUCT
		&& (fourPartKey == null || fourPartKey.getStoreId() == null || fourPartKey.getWorkStationId() == null
			|| fourPartKey.getBusinessDate() == null || fourPartKey.getRegisterTransactionNum() == null)){
	    return component.getString("printTransactionReceipt.badTransaction.message.label");
	}

	try{
	    if (baseObjectType == BaseObjectType.SERVICE_PLAN_TRANSACTION){
		bFile = customerDataService.getElectronicJournal(transaction.getStoreNum(), transaction
			.getRegisterNum(), transaction.getPurchaseDate(), transaction.getTransactionNum(), drpUser);
	    }else if (baseObjectType == BaseObjectType.PRODUCT){
		bFile = customerDataService.getElectronicJournal(fourPartKey.getStoreId(), fourPartKey
			.getWorkStationId(), fourPartKey.getBusinessDate(), fourPartKey.getRegisterTransactionNum(),
			drpUser);
	    }
	}catch(IseBusinessException be){
	    logger.error("IseBusinessException while getting print receipt");
	    return component.getString("printTransactionReceipt.getReceiptDataFailed.message.label") + " "
		    + be.getMessage();
	}catch(ServiceException se){
	    logger.error("ServiceException while getting print receipt", se);
	    return component.getString("printTransactionReceipt.getReceiptDataFailed.message.label") + " "
		    + se.getMessage();
	}
	if (bFile == null || bFile.length() == 0){
	    if (bFile == null){
		logger.warn("Print receipt data returned is null");
	    }else{
		logger.warn("Print receipt data returned is 0 bytes");
	    }
	    return component.getString("printTransactionReceipt.badReceiptData.message.label");
	}

	Document document = new Document();
	ByteArrayOutputStream baos = new ByteArrayOutputStream();
	try{
	    PdfWriter.getInstance(document, baos);
	    document.open();
	    document.add(new Paragraph(bFile.replace("Keep your receipt!",
		    "Keep your receipt!\n*********** Duplicate Receipt ***********")));
	}catch(DocumentException de){
	    logger.error("DocumentException while converting print receipt into PDF", de);
	    return component.getString("printTransactionReceipt.convertReceiptDataFailed.message.label") + " "
		    + de.getMessage();
	}finally{
	    document.close();
	    try{
		baos.close();
	    }catch(IOException ioe){
	    }
	}
	setPdfByteArray(baos.toByteArray());
	return null;
    }

    public DrpUser getDrpUser() {
	return drpUser;
    }

    public void setDrpUser(DrpUser drpUser) {
	this.drpUser = drpUser;
    }

    public ServicePlanTransaction getTransaction() {
	return transaction;
    }

    public void setTransaction(ServicePlanTransaction transaction) {
	this.transaction = transaction;
    }

    public void setProduct(Product product) {
	this.product = product;
    }
}
