package com.bestbuy.bbym.ise.drp.sao;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import sun.misc.BASE64Decoder;

import com.bestbuy.bbym.ise.drp.domain.DrpUser;
import com.bestbuy.bbym.ise.drp.utils.DrpConstants;
import com.bestbuy.bbym.ise.exception.IseBusinessException;
import com.bestbuy.bbym.ise.exception.IseExceptionCodeEnum;
import com.bestbuy.bbym.ise.exception.ServiceException;
import com.bestbuy.bbym.ise.iseclientej.ElectronicJournalDataRequest;
import com.bestbuy.bbym.ise.iseclientej.ElectronicJournalFourPartKey;
import com.bestbuy.bbym.ise.iseclientej.ElectronicJournalFourPartKeys;
import com.bestbuy.bbym.ise.iseclientej.ElectronicJournalResponse;
import com.bestbuy.bbym.ise.iseclientej.ElectronicJournalResult;
import com.bestbuy.bbym.ise.util.Util;

@Service("ejSao")
public class EjSaoImpl extends AbstractSao implements EjSao {

    private static Logger logger = LoggerFactory.getLogger(EjSaoImpl.class);
    private static final String SERVICE = "EJ ";

    @Override
    public String getElectronicJournal(String storeId, String registerId, Date transactionDate,
	    String transactionNumber, DrpUser drpUser) throws ServiceException, IseBusinessException {
	logger.info("Getting electronic journal for storeId=" + storeId, ", registerId=" + registerId
		+ ", transactionDate=" + transactionDate + ", transactionNumber=" + transactionNumber);

	ElectronicJournalDataRequest eJDataReq = prepareFourPartKeyRequest(storeId, registerId, transactionDate,
		transactionNumber, drpUser);
	try{
	    ElectronicJournalResponse eJResponse = getEJServices().getElectronicJournal(eJDataReq);
	    String encodedReceipt = getReceiptData(eJResponse);
	    if (encodedReceipt != null){
		BASE64Decoder decoder = new BASE64Decoder();
		byte[] unEncodedReceipt = decoder.decodeBuffer(new String(encodedReceipt.getBytes()));
		return new String(unEncodedReceipt);
	    }
	}catch(ServiceException se){
	    logger.error(DrpConstants.EXTERNAL_SERVICE_CALL_ERROR + SERVICE + "Error getting four part key", se);
	    throw new ServiceException(IseExceptionCodeEnum.ExternalServiceException, se.getMessage());
	}catch(Throwable t){
	    handleException(SERVICE + " - get four part key", t);
	}
	return null;
    }

    private String getReceiptData(ElectronicJournalResponse eJResponse) {

	List<ElectronicJournalResult> eJournalResults = eJResponse.getElectronicJournalResult();
	logger.info("EJs found (should only zero or 1): " + eJournalResults.size());
	if (eJournalResults.size() > 0){
	    return eJournalResults.get(0).getElectronicJournalDocumentText().getReceiptData();
	}
	return null;
    }

    private ElectronicJournalDataRequest prepareFourPartKeyRequest(String storeId, String registerId,
	    Date transactionDate, String transactionNumber, DrpUser drpUser) {

	ElectronicJournalDataRequest eJDataRequest = new ElectronicJournalDataRequest();
	logger.info("Initializing Four Part Key Request");
	eJDataRequest.setUserId(drpUser.getUserId());
	ElectronicJournalFourPartKeys eJournalFourPartKeys = new ElectronicJournalFourPartKeys();
	ElectronicJournalFourPartKey eJournalFourPartKey = new ElectronicJournalFourPartKey();
	eJournalFourPartKey.setStoreId(Integer.parseInt(storeId));
	eJournalFourPartKey.setRegisterId(Integer.parseInt(registerId));
	eJournalFourPartKey.setTransactionDate(Util.toXmlGregorianCalendar(transactionDate));
	eJournalFourPartKey.setTransactionNumber(Integer.parseInt(transactionNumber));
	eJournalFourPartKey.setGetPrivateData(false);
	eJournalFourPartKeys.getElectronicJournalFourPartKey().add(eJournalFourPartKey);
	eJDataRequest.setElectronicJournalFourPartKeys(eJournalFourPartKeys);
	return eJDataRequest;
    }

}
