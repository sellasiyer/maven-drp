package com.bestbuy.bbym.ise.drp.service;

import java.util.Date;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bestbuy.bbym.ise.drp.dao.BbyAccountDao;
import com.bestbuy.bbym.ise.drp.dao.CarrierAccountDao;
import com.bestbuy.bbym.ise.drp.dao.CustomerDao;
import com.bestbuy.bbym.ise.drp.domain.Address;
import com.bestbuy.bbym.ise.drp.domain.BbyAccount;
import com.bestbuy.bbym.ise.drp.domain.CarrierAccount;
import com.bestbuy.bbym.ise.drp.domain.Customer;
import com.bestbuy.bbym.ise.drp.domain.DrpUser;
import com.bestbuy.bbym.ise.drp.domain.RecSheetSummary;
import com.bestbuy.bbym.ise.drp.domain.Recommendation;
import com.bestbuy.bbym.ise.drp.helpers.BeastObjectMapper;
import com.bestbuy.bbym.ise.exception.DataAccessException;
import com.bestbuy.bbym.ise.exception.IseBusinessException;
import com.bestbuy.bbym.ise.exception.IseExceptionCodeEnum;
import com.bestbuy.bbym.ise.exception.ServiceException;

/**
 * 
 * @author a904002
 */
@Transactional
@Service("customerService")
public class CustomerServiceImpl implements CustomerService {

    private static final Logger log = LoggerFactory.getLogger(CustomerServiceImpl.class);

    @Autowired
    private BbyAccountDao bbyAccountDao;
    @Autowired
    private CarrierAccountDao carrierAccountDao;
    @Autowired
    private CustomerDao customerDao;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public String addCustomer(Customer customer) throws ServiceException {

	String dataSharingKey = UUID.randomUUID().toString();
	if (customer.getDataSharingKey() != null){
	    dataSharingKey = customer.getDataSharingKey();
	}
	customer.setDataSharingKey(dataSharingKey);

	BbyAccount bbyAccount = customer.getBbyAccount();
	if (bbyAccount != null){
	    bbyAccount.setDataSharingKey(dataSharingKey);
	    bbyAccount.setCreatedBy(customer.getCreatedBy());
	    bbyAccount.setCreatedOn(new Date());
	    if (bbyAccount.getAddress() != null){
		bbyAccount.getAddress().setCreatedBy(customer.getCreatedBy());
		bbyAccount.getAddress().setCreatedOn(new Date());
		setAddressId(bbyAccount.getAddress());
	    }
	}

	CarrierAccount carrierAccount = customer.getCarrierAccount();
	if (carrierAccount != null){
	    carrierAccount.setDataSharingKey(dataSharingKey);
	    carrierAccount.setCreatedBy(customer.getCreatedBy());
	    carrierAccount.setCreatedOn(new Date());
	    if (carrierAccount.getAddress() != null){
		carrierAccount.getAddress().setCreatedBy(customer.getCreatedBy());
		carrierAccount.getAddress().setCreatedOn(new Date());
		setAddressId(carrierAccount.getAddress());
	    }
	}
	if (customer.getRecSheetSummary() != null){
	    customer.getRecSheetSummary().setCreatedBy(customer.getCreatedBy());
	    customer.getRecSheetSummary().setCreatedOn(customer.getCreatedOn());
	    customer.getRecSheetSummary().setDataSharingKey(dataSharingKey);
	}
	try{
	    customerDao.persistCustomer(customer);
	}catch(DataAccessException ex){
	    log.error("BEAST TRANSFER : Error while saving customer information, dataSharingKey:" + dataSharingKey, ex);
	    throw new ServiceException(IseExceptionCodeEnum.DataAccess, ex.getMessage());
	}catch(RuntimeException e){
	    log.error("BEAST TRANSFER : Unexpected Error, dataSharingKey:" + dataSharingKey, e);
	    throw new ServiceException(IseExceptionCodeEnum.DataAccess, e.getMessage());
	}

	return dataSharingKey;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public Customer getCustomer(String storeId, String dataSharingKey) throws ServiceException, IseBusinessException {

	Customer customer = null;
	try{

	    customer = customerDao.getCustomer(storeId, dataSharingKey);

	}catch(EmptyResultDataAccessException e){
	    log.error("BEAST TRANSFER : No record found, dataSharingKey:" + dataSharingKey);
	    throw new ServiceException(IseExceptionCodeEnum.NOTFOUND, e.getMessage());
	}catch(DataAccessException ex){
	    log.error("BEAST TRANSFER : Error while retrieving customer information for dataSharingKey : "
		    + dataSharingKey, ex);
	    throw new ServiceException(IseExceptionCodeEnum.DataAccess, ex.getMessage());
	}catch(RuntimeException e){
	    log.error("BEAST TRANSFER : Unexpected Error, dataSharingKey:" + dataSharingKey, e);
	    throw new ServiceException(IseExceptionCodeEnum.DataAccess, e.getMessage());
	}
	return customer;
    }

    private void setAddressId(Address address) {
	if (address != null){
	    address.setAddressId(UUID.randomUUID().toString());
	}
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public String addBbyCustomer(BbyAccount bbyCustomerAccount) throws ServiceException {
	String dataSharingKey = null;
	try{
	    bbyAccountDao.persistBbyAccount(bbyCustomerAccount);
	    dataSharingKey = bbyCustomerAccount.getDataSharingKey();
	}catch(DataAccessException ex){
	    log.error("BEAST WRITE CUSTOMER: Error while creating BBY Customer", ex);
	    throw new ServiceException(IseExceptionCodeEnum.CONFLICT, ex.getMessage());
	}catch(RuntimeException e){
	    log.error("BEAST WRITE CUSTOMER : Unexpected Error, dataSharingKey:" + dataSharingKey, e);
	    throw new ServiceException(IseExceptionCodeEnum.DataAccess, e.getMessage());
	}
	return dataSharingKey;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public void updateBbyCustomer(BbyAccount bbyCustomerAccount) throws ServiceException {
	String dataSharingKey = null;
	try{

	    dataSharingKey = bbyCustomerAccount.getDataSharingKey();
	    bbyAccountDao.updateBbyAccount(bbyCustomerAccount);
	}catch(DataAccessException ex){
	    log.error("UPDATE CUSTOMER: Error while updating BBY Customer", ex);
	    throw new ServiceException(IseExceptionCodeEnum.CONFLICT, "Error while updating BBY Customer");
	}catch(Exception e){
	    log.error("UPDATE CUSTOMER: Unexpected Error, dataSharingKey:" + dataSharingKey, e);
	    throw new ServiceException(IseExceptionCodeEnum.DataAccess, "Uxexpected Error Occurred");
	}

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public void updateCustomer(Customer customer) throws ServiceException {
	String dataSharingKey = null;
	if (StringUtils.isNotBlank(customer.getDataSharingKey())){
	    dataSharingKey = customer.getDataSharingKey();
	}

	BbyAccount bbyAccount = customer.getBbyAccount();
	if (bbyAccount != null){
	    bbyAccount.setDataSharingKey(dataSharingKey);
	    bbyAccount.setCreatedBy(customer.getModifiedBy());
	    bbyAccount.setModifiedBy(customer.getModifiedBy());
	    bbyAccount.setModifiedOn(new Date());
	    if (bbyAccount.getAddress() != null){
		bbyAccount.getAddress().setCreatedBy(customer.getModifiedBy());
		bbyAccount.getAddress().setModifiedBy(customer.getModifiedBy());
		bbyAccount.getAddress().setModifiedOn(new Date());
		setAddressId(bbyAccount.getAddress());
	    }
	}

	CarrierAccount carrierAccount = customer.getCarrierAccount();
	if (carrierAccount != null){
	    carrierAccount.setDataSharingKey(dataSharingKey);
	    carrierAccount.setCreatedBy(customer.getModifiedBy());
	    carrierAccount.setModifiedBy(customer.getModifiedBy());
	    carrierAccount.setModifiedOn(new Date());
	    if (carrierAccount.getAddress() != null){
		carrierAccount.getAddress().setCreatedBy(customer.getModifiedBy());
		carrierAccount.getAddress().setModifiedBy(customer.getModifiedBy());
		carrierAccount.getAddress().setModifiedOn(new Date());
		setAddressId(carrierAccount.getAddress());
	    }
	}
	if (customer.getRecSheetSummary() != null){
	    customer.getRecSheetSummary().setCreatedBy(customer.getModifiedBy());
	    customer.getRecSheetSummary().setModifiedBy(customer.getModifiedBy());
	    customer.getRecSheetSummary().setModifiedOn(new Date());
	    customer.getRecSheetSummary().setDataSharingKey(dataSharingKey);
	}
	try{

	    customerDao.updateCustomer(customer);
	    dataSharingKey = customer.getDataSharingKey();
	}catch(DataAccessException ex){
	    log.error("UPDATE CUSTOMER: Error while updating Customer", ex);
	    throw new ServiceException(IseExceptionCodeEnum.CONFLICT, ex.getMessage());
	}catch(Exception e){
	    log.error("UPDATE CUSTOMER : Unexpected Error, dataSharingKey:" + dataSharingKey, e);
	    throw new ServiceException(IseExceptionCodeEnum.DataAccess, e.getMessage());
	}

    }

    @Override
    @Transactional(readOnly = false)
    public BbyAccount getBbyCustomer(String dataSharingKey) throws ServiceException {
	BbyAccount bbyAccount;
	try{
	    bbyAccount = bbyAccountDao.getBbyAccount(dataSharingKey);

	}catch(EmptyResultDataAccessException e){
	    log.error("BEAST GET BBY Customer:No Data Available");
	    throw new ServiceException(IseExceptionCodeEnum.NOTFOUND, "No Data Available");
	}catch(DataAccessException ex){
	    log.error("BEAST GET BBY Customer: Error while retrieving BBY Customer", ex);
	    throw new ServiceException(IseExceptionCodeEnum.CONFLICT, ex.getMessage());
	}catch(Exception e){
	    log.error("BEAST GET BBY CUSTOMER : Unexpected Error, dataSharingKey:" + dataSharingKey, e);
	    throw new ServiceException(IseExceptionCodeEnum.DataAccess, e.getMessage());
	}
	return bbyAccount;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public void createCustomer(com.bestbuy.bbym.ise.domain.Customer customer, DrpUser user) throws ServiceException {
	try{

	    Customer dtaCustomer = BeastObjectMapper.convertToCustomer(customer);
	    dtaCustomer.setStoreId(user.getStoreId());
	    dtaCustomer.setCreatedBy(user.getUserId());
	    dtaCustomer.setCreatedOn(new Date());
	    dtaCustomer.setSource("BEAST-PORTAL");

	    if (customer.getDataSharingKey() != null){

		dtaCustomer.setDataSharingKey(customer.getDataSharingKey());
		dtaCustomer.setModifiedBy(user.getUserId());
		dtaCustomer.setModifiedOn(new Date());

		updateCustomer(dtaCustomer);
	    }else{

		String dataSharingKey = addCustomer(dtaCustomer);
		customer.setDataSharingKey(dataSharingKey);

	    }

	    customer.setTransferFlag(true);

	}catch(ServiceException ex){
	    log.error("Error while saving the customer information", ex);
	}

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public void addRecSheetSummary(com.bestbuy.bbym.ise.domain.Customer customer, Recommendation recommendation,
	    DrpUser user) throws ServiceException {
	if (StringUtils.isBlank(customer.getFirstName())
		|| !recommendation.getFirstName().equals(customer.getFirstName())){
	    customer.setFirstName(recommendation.getFirstName());
	}
	if (StringUtils.isBlank(customer.getLastName()) || !recommendation.getLastName().equals(customer.getLastName())){
	    customer.setLastName(recommendation.getLastName());
	}
	if (StringUtils.isBlank(customer.getContactPhone())
		|| !recommendation.getMobileNo().equals(customer.getContactPhone())){
	    customer.setContactPhone(recommendation.getMobileNo());
	}

	RecSheetSummary recSheetSummary = BeastObjectMapper.convertToRecSheetSummary(recommendation);

	if (StringUtils.isNotBlank(customer.getDataSharingKey())){

	    recSheetSummary.setCreatedBy(user.getUserId());
	    recSheetSummary.setCreatedOn(new Date());
	    Customer dtaCustomer = null;
	    try{
		dtaCustomer = customerDao.getCustomer(user.getStoreId(), customer.getDataSharingKey());

	    }catch(DataAccessException ex){
		log.error(">> DataAccessException : ", ex);
	    }catch(EmptyResultDataAccessException ex){
		log.error(">> DataAccessException : ", ex);
	    }catch(RuntimeException ex){
		log.error(">> DataAccessException : ", ex);
	    }
	    dtaCustomer.setStoreId(user.getStoreId());
	    dtaCustomer.setSource("BEAST-PORTAL");
	    dtaCustomer.setRecSheetSummary(recSheetSummary);
	    dtaCustomer.setDataSharingKey(customer.getDataSharingKey());
	    dtaCustomer.setModifiedBy(user.getUserId());
	    updateCustomer(dtaCustomer);
	    updateAccounts(customer.getDataSharingKey(), recommendation);
	    recommendation.setDataSharingKey(dtaCustomer.getDataSharingKey());
	}else{
	    Customer dtaCustomer = BeastObjectMapper.convertToCustomer(customer);
	    recSheetSummary.setModifiedBy(user.getUserId());
	    recSheetSummary.setModifiedOn(new Date());

	    dtaCustomer.setStoreId(user.getStoreId());
	    dtaCustomer.setSource("BEAST-PORTAL");
	    dtaCustomer.setCreatedBy(user.getUserId());
	    dtaCustomer.setCreatedOn(new Date());
	    dtaCustomer.setRecSheetSummary(recSheetSummary);
	    addCustomer(dtaCustomer);
	    recommendation.setDataSharingKey(dtaCustomer.getDataSharingKey());
	    customer.setDataSharingKey(dtaCustomer.getDataSharingKey());
	}

	recommendation.setTransferFlag(true);
	customer.setTransferFlag(true);
    }

    private void updateAccounts(String dataSharingKey, Recommendation recommendation) {
	try{
	    CarrierAccount carAccount = carrierAccountDao.getCarrierAccount(dataSharingKey);

	    if (!recommendation.getFirstName().equals(carAccount.getFirstName())
		    || (!recommendation.getLastName().equals(carAccount.getLastName()))
		    || (!recommendation.getMobileNo().equals(carAccount.getPhoneNumber()))){
		carAccount.setFirstName(recommendation.getFirstName());
		carAccount.setLastName(recommendation.getLastName());
		carAccount.setPhoneNumber(recommendation.getMobileNo());
		carAccount.setModifiedBy(recommendation.getAmendedBy());
		carrierAccountDao.updateCarrierAccount(carAccount);
	    }

	}catch(DataAccessException ex){
	    log.error(">>DataAccessException while updateing account information from RecSheet", ex);
	}catch(EmptyResultDataAccessException ex){
	    log.error(">>EmptyResultDataAccessException while updateing account information from RecSheet", ex);
	}catch(RuntimeException ex){
	    log.error(">>EmptyResultDataAccessException while updateing account information from RecSheet", ex);
	}

    }
}
