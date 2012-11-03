package com.bestbuy.bbym.ise.drp.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import com.bestbuy.bbym.ise.drp.domain.Customer;
import com.bestbuy.bbym.ise.exception.DataAccessException;
import com.bestbuy.bbym.ise.exception.IseExceptionCodeEnum;
import com.bestbuy.bbym.ise.util.WorkFlowTypeEnum;

/**
 * GSB Cancel - Customer details and data transfer summary are queried from
 * database.
 *
 * @author Sridhar Savaram
 */
@Repository("customerDao")
public class CustomerDaoImpl extends AbstractDao implements CustomerDao {

    private static Logger logger = LoggerFactory.getLogger(CustomerDaoImpl.class);
    private final String CUSTOMER_DETAILS_QUERY = "SELECT dt.dta_sharing_key, ba.cust_frst_nm ba_cust_frst_nm, ba.cust_last_nm ba_cust_last_nm, ba.cust_ph_nbr ba_cust_ph_nbr, "
	    + "ba.cust_email_id ba_cust_email_id, ba.bby_cust_id, ba.created_by ba_created_by, ba.created_on ba_created_on, ba.amended_by ba_amended_by, ba.amended_on ba_amended_on, "
	    + "ba.rwz_nbr rwz_nbr,baa.addr_ln_1 baa_address_line1, baa.addr_ln_2 baa_address_line2, baa.city baa_city, baa.state baa_state, baa.zipcode baa_zipcode, baa.created_by baa_created_by, "
	    + "baa.created_on baa_created_on, baa.amended_by baa_amended_by, baa.amended_on baa_amended_on, ca.acct_nbr, ca.covg_zip, ca.cust_frst_nm ca_cust_frst_nm, "
	    + "ca.cust_last_nm ca_cust_last_nm, ca.cust_ph_nbr ca_cust_ph_nbr, ca.cust_email_id ca_cust_email_id, ca.created_by ca_created_by, ca.created_on ca_created_on, "
	    + "ca.amended_by ca_amended_by, ca.amended_on ca_amended_on,ca.carr_id ca_carrier, caa.addr_ln_1 caa_address_line1, caa.addr_ln_2 caa_address_line2, caa.city caa_city, caa.state caa_state, "
	    + "caa.zipcode caa_zipcode, caa.created_by caa_created_by, caa.created_on caa_created_on, caa.amended_by caa_amended_by, caa.amended_on caa_amended_on, dt.stor_id, "
	    + "dt.xfer_flg, dt.src_sys, dt.cap_trans_id, dt.created_by dt_created_by, dt.created_on dt_created_on, dt.amended_by dt_amended_by, dt.amended_on dt_amended_on, rec.plan_info, "
	    + "rec.device_info, rec.gsp_plan_info, rec.buyback_plan_info, rec.created_by rec_created_by, rec.created_on rec_created_on, rec.amended_by rec_amended_by, "
	    + "rec.amended_on rec_amended_on FROM bst_ise_sch01.dta_xfer dt, bst_ise_sch01.bbym_acct ba, bst_ise_sch01.addr baa, bst_ise_sch01.carr_acct ca, bst_ise_sch01.addr caa, "
	    + "bst_ise_sch01.rec_sht_summ rec WHERE dt.dta_sharing_key = ba.dta_sharing_key(+)  AND ba.addr_id = baa.addr_id(+)  AND dt.dta_sharing_key = ca.dta_sharing_key(+)  "
	    + "AND ca.addr_id = caa.addr_id(+)  AND dt.dta_sharing_key = rec.dta_sharing_key(+)  AND dt.stor_id = ?  AND dt.dta_sharing_key = ?";
    @Autowired
    private DataTransferDao dataTransferDao;
    @Autowired
    private BbyAccountDao bbyAccountDao;
    @Autowired
    private CarrierAccountDao carrierAccountDao;
    @Autowired
    private RecSheetSummaryDao recSheetSummaryDao;

    @Override
    public Customer getCustomer(String storeId, String dataSharingKey) throws DataAccessException,
	    EmptyResultDataAccessException, RuntimeException {
	Customer customer = null;
	try{
	    dataTransferDao.updateTransferFlag(dataSharingKey);
	    customer = getJdbcTemplate().queryForObject(CUSTOMER_DETAILS_QUERY,
		    new Object[] {storeId, dataSharingKey }, new CustomerRowMapper());
	}catch(org.springframework.dao.DataAccessException ex){
	    logger.error(ex.getMessage());
	    DataAccessException exception = new DataAccessException("Unable to find the customer for given key : "
		    + dataSharingKey);
	    exception.setErrorCode(IseExceptionCodeEnum.NOTFOUND);
	    throw exception;
	}
	return customer;
    }

    @Override
    public void persistCustomer(Customer customer) throws DataAccessException, RuntimeException {
	dataTransferDao.persistDataTransfer(customer.getDataSharingKey(), customer.getStoreId(), customer
		.getCreatedBy(), customer.getTransferFlag(), customer.getSource(), customer.getCapTransactionId(),
		WorkFlowTypeEnum.BEAST_TRANSFER.name());
	if (customer.getBbyAccount() != null){

	    bbyAccountDao.persistBbyAccount(customer.getBbyAccount());
	}

	if (customer.getCarrierAccount() != null){

	    carrierAccountDao.persistCarrierAccount(customer.getCarrierAccount());
	}
	if (customer.getRecSheetSummary() != null){

	    recSheetSummaryDao.persistRecSheetSummary(customer.getRecSheetSummary());
	}
    }

    public void setDaos(DataTransferDao dataTransferDao, BbyAccountDao bbyAccountDao,
	    CarrierAccountDao carrierAccountDao, RecSheetSummaryDao recSheetSummaryDao) {
	this.dataTransferDao = dataTransferDao;
	this.bbyAccountDao = bbyAccountDao;
	this.carrierAccountDao = carrierAccountDao;
	this.recSheetSummaryDao = recSheetSummaryDao;
    }

    @Override
    public void updateCustomer(Customer customer) throws DataAccessException, RuntimeException {

	if (customer.getBbyAccount() != null){
	    logger.debug("Updating bbyAccount for datasharing key :  " + customer.getBbyAccount().getDataSharingKey());
	    try{
		if (bbyAccountDao.getBbyAccount(customer.getDataSharingKey()) != null){
		    bbyAccountDao.updateBbyAccount(customer.getBbyAccount());

		}else{
		    bbyAccountDao.persistBbyAccount(customer.getBbyAccount());
		}
	    }catch(EmptyResultDataAccessException ex){
		logger.info("BBY Account not found for " + customer.getDataSharingKey());
		logger.info(">> Creating new BBYAccount");
		bbyAccountDao.persistBbyAccount(customer.getBbyAccount());
	    }
	}
	if (customer.getCarrierAccount() != null){

	    try{
		if (carrierAccountDao.getCarrierAccount(customer.getDataSharingKey()) != null){
		    carrierAccountDao.updateCarrierAccount(customer.getCarrierAccount());
		}else{
		    carrierAccountDao.persistCarrierAccount(customer.getCarrierAccount());
		}
	    }catch(EmptyResultDataAccessException ex){
		logger.info("Carrier Account not found for " + customer.getDataSharingKey());
		logger.info(">> Creating new Carrier Account");
		carrierAccountDao.persistCarrierAccount(customer.getCarrierAccount());
	    }

	}
	if (customer.getRecSheetSummary() != null){

	    try{
		if (recSheetSummaryDao.getRecSheetSummary(customer.getDataSharingKey()) != null){

		    recSheetSummaryDao.updateRecSheetSummary(customer.getRecSheetSummary());
		}else{
		    recSheetSummaryDao.persistRecSheetSummary(customer.getRecSheetSummary());
		}

	    }catch(EmptyResultDataAccessException ex){
		logger.info("RecSheet Summary not found for " + customer.getDataSharingKey());
		logger.info(">> Creating new RecSheet Summary ");
		recSheetSummaryDao.persistRecSheetSummary(customer.getRecSheetSummary());
	    }
	}
	dataTransferDao.update(customer.getDataSharingKey(), customer.getCapTransactionId(), customer.getModifiedBy());
    }
}
