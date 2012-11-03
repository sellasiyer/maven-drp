package com.bestbuy.bbym.ise.drp.service;

import java.util.Date;
import java.util.List;
import java.util.ListIterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bestbuy.bbym.ise.drp.dao.LoanerPhoneDao;
import com.bestbuy.bbym.ise.drp.domain.Carrier;
import com.bestbuy.bbym.ise.drp.domain.CheckOutCheckInHistory;
import com.bestbuy.bbym.ise.drp.domain.LoanerPhoneCondition;
import com.bestbuy.bbym.ise.drp.domain.OperatingSystem;
import com.bestbuy.bbym.ise.drp.domain.Phone;
import com.bestbuy.bbym.ise.drp.domain.PhoneModel;
import com.bestbuy.bbym.ise.drp.helpers.PhoneModelSearchCriteria;
import com.bestbuy.bbym.ise.drp.helpers.PhoneSearchCriteria;
import com.bestbuy.bbym.ise.exception.DataAccessException;
import com.bestbuy.bbym.ise.exception.IseExceptionCodeEnum;
import com.bestbuy.bbym.ise.exception.ServiceException;

/**
 * 
 * @author a909782
 */
@Service("loanerPhoneService")
public class LoanerPhoneServiceImpl implements LoanerPhoneService {

    private static Logger logger = LoggerFactory.getLogger(LoanerPhoneServiceImpl.class);

    @Autowired
    private LoanerPhoneDao loanerPhoneDao;

    public LoanerPhoneServiceImpl() {
    }

    public LoanerPhoneServiceImpl(LoanerPhoneDao loanerPhoneDao) {
	this.loanerPhoneDao = loanerPhoneDao;
    }

    @Override
    public List<Phone> getPhones(String storeId, PhoneSearchCriteria criteria) throws ServiceException {
	try{
	    if (storeId == null){
		throw new DataAccessException("Invalid input storeId");
	    }
	    List<Phone> phoneList = loanerPhoneDao.getPhones(storeId, criteria);
	    Date today = new Date();
	    for(Phone p: phoneList){
		CheckOutCheckInHistory checkOut = p.getLatestCheckOutCheckInHistory();
		if (checkOut != null && checkOut.isCheckedOut()){
		    int noOfDaysOut = (int) calculateDays(checkOut.getCheckOutTime(), today);
		    if (noOfDaysOut == 0){
			noOfDaysOut = 1;
		    }
		    checkOut.setNoOfDaysOut(noOfDaysOut);
		    p.setLatestCheckOutCheckInHistory(checkOut);
		}
	    }
	    return phoneList;
	}catch(DataAccessException ex){
	    logger.error("Failed to get phones for storeId : " + storeId, ex);
	    throw new ServiceException(IseExceptionCodeEnum.DataAccess, ex.getMessage());
	}
    }

    @Override
    public List<PhoneModel> getPhoneModels(String storeId, PhoneModelSearchCriteria criteria) throws ServiceException {
	try{
	    if (storeId == null){
		throw new DataAccessException("Invalid input storeId");
	    }
	    List<PhoneModel> phoneModelList = loanerPhoneDao.getPhoneModels(storeId, criteria);
	    PhoneSearchCriteria phonecriteria = new PhoneSearchCriteria();
	    List<Phone> phoneList = loanerPhoneDao.getPhones(storeId, phonecriteria);
	    for(Phone p: phoneList){
		CheckOutCheckInHistory checkOut = loanerPhoneDao.getCheckOutHistory(p);
		for(PhoneModel pm: phoneModelList){
		    if ((pm.getCarrier() != null && pm.getCarrier().getCarrier() != null && p.getCarrier() != null && pm
			    .getCarrier().getCarrier().equals(p.getCarrier().getCarrier()))
			    && (pm.getMake() != null && pm.getMake().equals(p.getMake()))
			    && (pm.getModel() != null && pm.getModel().equals(p.getModelNumber()))
			    && (pm.getOs() != null && pm.getOs().getOs() != null && p.getOperatingSystem() != null && pm
				    .getOs().getOs().equals(p.getOperatingSystem().getOs()))){
			if (checkOut != null && pm.getNoOfPhones() > 0){
			    int noOfPhones = pm.getNoOfPhones() - 1;
			    pm.setNoOfPhones(noOfPhones);
			}
			if (pm.getNoOfPhones() > 0
				&& checkOut == null
				&& (p.getCondition() == LoanerPhoneCondition.DAMAGED || p.getCondition() == LoanerPhoneCondition.LOST)){
			    pm.setCondition(p.getCondition());
			    pm.setNoOfDamaged(pm.getNoOfDamaged() + 1);
			    pm.setNoOfPhones(pm.getNoOfPhones() - 1);
			}

		    }
		}
	    }
	    // remove the phoneModels from the list if noOfPhones is zero
	    ListIterator<PhoneModel> it = phoneModelList.listIterator();
	    while(it.hasNext()){
		PhoneModel pm = it.next();
		if (pm.getNoOfPhones() <= 0 && pm.getNoOfDamaged() <= 0){
		    it.remove();
		}
	    }
	    return phoneModelList;
	}catch(DataAccessException ex){
	    logger.error("Failed to get phone Models for storeId : " + storeId, ex);
	    throw new ServiceException(IseExceptionCodeEnum.DataAccess, ex.getMessage());
	}
    }

    @Override
    public List<Carrier> getCarriers() throws ServiceException {
	try{
	    return loanerPhoneDao.getCarriers();
	}catch(DataAccessException ex){
	    logger.error("Failed to get Carriers for the lookup", ex);
	    throw new ServiceException(IseExceptionCodeEnum.DataAccess, ex.getMessage());
	}

    }

    @Override
    public List<OperatingSystem> getOperatingSystems() throws ServiceException {
	try{
	    return loanerPhoneDao.getOperatingSystems();
	}catch(DataAccessException ex){
	    logger.error("Failed to get Operating Systems for the lookup", ex);
	    throw new ServiceException(IseExceptionCodeEnum.DataAccess, ex.getMessage());
	}
    }

    @Override
    public void createPhone(Phone phone) throws ServiceException {
	try{
	    loanerPhoneDao.createPhone(phone);
	}catch(DataAccessException ex){
	    logger.error("Failed to create phone", ex);
	    throw new ServiceException(ex.getErrorCode(), ex.getMessage());
	}
    }

    @Override
    public void updatePhone(Phone phone) throws ServiceException {
	try{
	    loanerPhoneDao.updatePhone(phone);
	}catch(DataAccessException ex){
	    logger.error("Failed to update phone", ex);
	    throw new ServiceException(ex.getErrorCode(), ex.getMessage());
	}
    }

    public void checkOutPhone(CheckOutCheckInHistory history) throws ServiceException {
	try{
	    loanerPhoneDao.checkOutPhone(history);
	}catch(DataAccessException ex){
	    logger.error("Failed to create check out history", ex);
	    throw new ServiceException(ex.getErrorCode(), ex.getMessage());
	}
    }

    public void checkInPhone(CheckOutCheckInHistory history) throws ServiceException {
	try{
	    loanerPhoneDao.checkInPhone(history);
	}catch(DataAccessException ex){
	    logger.error("Failed to create check-in history", ex);
	    throw new ServiceException(ex.getErrorCode(), ex.getMessage());
	}
    }

    public String[] getDistinctMakes(String stor_id) throws ServiceException {
	try{
	    return loanerPhoneDao.getDistinctMakes(stor_id);
	}catch(DataAccessException ex){
	    logger.error("Failed to get distinct makes", ex);
	    throw new ServiceException(ex.getErrorCode(), ex.getMessage());
	}
    }

    public String[] getDistinctModels(String stor_id) throws ServiceException {
	try{
	    return loanerPhoneDao.getDistinctModels(stor_id);
	}catch(DataAccessException ex){
	    logger.error("Failed to get distinct models", ex);
	    throw new ServiceException(ex.getErrorCode(), ex.getMessage());
	}
    }

    private long calculateDays(Date dateEarly, Date dateLater) {
	return (dateLater.getTime() - dateEarly.getTime()) / (24 * 60 * 60 * 1000);
    }

    @Override
    public void updateHistory(CheckOutCheckInHistory history) throws ServiceException {

	try{
	    loanerPhoneDao.updateHistory(history);
	}catch(DataAccessException e){
	    throw new ServiceException(e.getErrorCode(), e.getMessage());
	}

    }
}
