package com.bestbuy.bbym.ise.drp.service;

import org.springframework.stereotype.Service;

import com.bestbuy.bbym.ise.drp.domain.Address;
import com.bestbuy.bbym.ise.drp.domain.BbyAccount;
import com.bestbuy.bbym.ise.drp.domain.Customer;
import com.bestbuy.bbym.ise.drp.domain.DrpUser;
import com.bestbuy.bbym.ise.drp.domain.Recommendation;
import com.bestbuy.bbym.ise.exception.IseBusinessException;
import com.bestbuy.bbym.ise.exception.ServiceException;

@Service("customerService2")
public class DummyCustomerService implements CustomerService {

    @Override
    public String addCustomer(Customer customer) throws ServiceException {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public void updateBbyCustomer(BbyAccount bbyCustomerAccount) throws ServiceException {
	// TODO Auto-generated method stub

    }

    @Override
    public String addBbyCustomer(BbyAccount bbyCustomerAccount) throws ServiceException {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Customer getCustomer(String storeId, String dataSharingKey) throws ServiceException, IseBusinessException {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public void updateCustomer(Customer customer) throws ServiceException {
	// TODO Auto-generated method stub

    }

    @Override
    public BbyAccount getBbyCustomer(String dataSharingKey) throws ServiceException {
	DummyData.sleep(1);
	DummyData.throwServiceException(40);

	BbyAccount account = new BbyAccount();
	account.setDataSharingKey(dataSharingKey);
	account.setFirstName(DummyData.getFirstName());
	account.setLastName(DummyData.getLastName());
	account.setPhoneNumber(DummyData.getPhone());
	account.setEmail(DummyData.getEmail());
	account.setAddress(new Address());
	account.getAddress().setAddressline1(DummyData.getAddress());
	account.getAddress().setCity(DummyData.getCity());
	account.getAddress().setState(DummyData.getState());
	account.getAddress().setZip(DummyData.getZipCode());
	if (DummyData.getRandomIndex(10) < 5){
	    account.setEccaId(DummyData.getCaAcctId());
	}
	return account;
    }

    @Override
    public void createCustomer(com.bestbuy.bbym.ise.domain.Customer customer, DrpUser user) throws ServiceException {
	// TODO Auto-generated method stub
    }

    @Override
    public void addRecSheetSummary(com.bestbuy.bbym.ise.domain.Customer customer, Recommendation recommendation,
	    DrpUser user) throws ServiceException {
	// TODO Auto-generated method stub
    }

}
