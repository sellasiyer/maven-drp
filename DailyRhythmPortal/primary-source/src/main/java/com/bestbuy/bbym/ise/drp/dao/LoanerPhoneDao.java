package com.bestbuy.bbym.ise.drp.dao;

import java.util.List;

import com.bestbuy.bbym.ise.drp.domain.Carrier;
import com.bestbuy.bbym.ise.drp.domain.CheckOutCheckInHistory;
import com.bestbuy.bbym.ise.drp.domain.OperatingSystem;
import com.bestbuy.bbym.ise.drp.domain.Phone;
import com.bestbuy.bbym.ise.drp.domain.PhoneModel;
import com.bestbuy.bbym.ise.drp.helpers.PhoneModelSearchCriteria;
import com.bestbuy.bbym.ise.drp.helpers.PhoneSearchCriteria;
import com.bestbuy.bbym.ise.exception.DataAccessException;

public interface LoanerPhoneDao {

    public List<Phone> getPhones(String storeId, PhoneSearchCriteria criteria) throws DataAccessException;

    public List<PhoneModel> getPhoneModels(String storeId, PhoneModelSearchCriteria criteria)
	    throws DataAccessException;

    public CheckOutCheckInHistory getCheckOutHistory(Phone phone) throws DataAccessException;

    public long createPhone(Phone phone) throws DataAccessException;

    public void updatePhone(Phone phone) throws DataAccessException;

    public void deletePhone(long loanr_ph_id) throws DataAccessException;

    public List<Carrier> getCarriers() throws DataAccessException;

    public List<OperatingSystem> getOperatingSystems() throws DataAccessException;

    public long checkOutPhone(CheckOutCheckInHistory history) throws DataAccessException;

    public void checkInPhone(CheckOutCheckInHistory history) throws DataAccessException;

    public String[] getDistinctMakes(String stor_id) throws DataAccessException;

    public String[] getDistinctModels(String stor_id) throws DataAccessException;

    public Phone getPhone(long id) throws DataAccessException;

    public Phone getPhoneWithStorIdAndSerialNumberForUpdate(String storeId, String serialNumber)
	    throws DataAccessException;

    public CheckOutCheckInHistory getCheckOutCheckInHistory(long id) throws DataAccessException;

    public void updateHistory(CheckOutCheckInHistory history) throws DataAccessException;
}
