package com.bestbuy.bbym.ise.drp.service;

import java.util.List;

import com.bestbuy.bbym.ise.drp.domain.Carrier;
import com.bestbuy.bbym.ise.drp.domain.CheckOutCheckInHistory;
import com.bestbuy.bbym.ise.drp.domain.OperatingSystem;
import com.bestbuy.bbym.ise.drp.domain.Phone;
import com.bestbuy.bbym.ise.drp.domain.PhoneModel;
import com.bestbuy.bbym.ise.drp.helpers.PhoneModelSearchCriteria;
import com.bestbuy.bbym.ise.drp.helpers.PhoneSearchCriteria;
import com.bestbuy.bbym.ise.exception.ServiceException;

public interface LoanerPhoneService {

    public List<Phone> getPhones(String storeId, PhoneSearchCriteria criteria) throws ServiceException;

    public List<PhoneModel> getPhoneModels(String storeId, PhoneModelSearchCriteria criteria) throws ServiceException;

    public void createPhone(Phone phone) throws ServiceException;

    public void updatePhone(Phone phone) throws ServiceException;

    public List<Carrier> getCarriers() throws ServiceException;

    public List<OperatingSystem> getOperatingSystems() throws ServiceException;

    public void checkOutPhone(CheckOutCheckInHistory history) throws ServiceException;

    public void checkInPhone(CheckOutCheckInHistory history) throws ServiceException;

    /**
     * Provides a method to update a CheckoutCheckinHistory for changes unrelated to checkin or checkout.
     * @param history 
     * @throws ServiceException
     */
    public void updateHistory(CheckOutCheckInHistory history) throws ServiceException;

    public String[] getDistinctMakes(String stor_id) throws ServiceException;

    public String[] getDistinctModels(String stor_id) throws ServiceException;
}
