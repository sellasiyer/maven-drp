package com.bestbuy.bbym.ise.drp.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Date;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.bestbuy.bbym.ise.drp.domain.Address;
import com.bestbuy.bbym.ise.drp.domain.BbyAccount;
import com.bestbuy.bbym.ise.drp.domain.CarrierAccount;
import com.bestbuy.bbym.ise.drp.domain.Customer;
import com.bestbuy.bbym.ise.drp.domain.RecSheetSummary;
import com.bestbuy.bbym.ise.exception.DataAccessException;

/**
 * JUnit test for {@link CustomerDaoImpl}.
 * 
 * @author Sridhar Savaram
 */
public class CustomerDaoImplTest extends BaseDaoTest {

    private Logger logger = LoggerFactory.getLogger(CustomerDaoImplTest.class);

    private CustomerDaoImpl customerDao = new CustomerDaoImpl();
    private DataTransferDaoImpl dataTransferDao = new DataTransferDaoImpl();
    private BbyAccountDaoImpl bbymAccountDao = new BbyAccountDaoImpl();
    private CarrierAccountDaoImpl carrierAccountDao = new CarrierAccountDaoImpl();
    private AddressDaoImpl addressDao = new AddressDaoImpl();
    private RecSheetSummaryDaoImpl recSheetSummaryDao = new RecSheetSummaryDaoImpl();

    @Before
    public void setUp() {
	customerDao.setUseNextSequenceOnCreate(true);
	customerDao.setNamedJdbcTemplate(new NamedParameterJdbcTemplate(db));
	customerDao.setJdbcTemplate(new JdbcTemplate(db));
	addressDao.setUseNextSequenceOnCreate(true);
	addressDao.setNamedJdbcTemplate(new NamedParameterJdbcTemplate(db));
	addressDao.setJdbcTemplate(new JdbcTemplate(db));
	carrierAccountDao.setUseNextSequenceOnCreate(true);
	carrierAccountDao.setNamedJdbcTemplate(new NamedParameterJdbcTemplate(db));
	carrierAccountDao.setJdbcTemplate(new JdbcTemplate(db));
	carrierAccountDao.setAddressDao(addressDao);
	bbymAccountDao.setUseNextSequenceOnCreate(true);
	bbymAccountDao.setNamedJdbcTemplate(new NamedParameterJdbcTemplate(db));
	bbymAccountDao.setJdbcTemplate(new JdbcTemplate(db));
	bbymAccountDao.setAddressDao(addressDao);
	recSheetSummaryDao.setUseNextSequenceOnCreate(true);
	recSheetSummaryDao.setNamedJdbcTemplate(new NamedParameterJdbcTemplate(db));
	recSheetSummaryDao.setJdbcTemplate(new JdbcTemplate(db));
	dataTransferDao.setUseNextSequenceOnCreate(true);
	dataTransferDao.setNamedJdbcTemplate(new NamedParameterJdbcTemplate(db));
	dataTransferDao.setJdbcTemplate(new JdbcTemplate(db));
	customerDao.setDaos(dataTransferDao, bbymAccountDao, carrierAccountDao, recSheetSummaryDao);
    }

    @Test
    public void testPersistCustomerDao() throws DataAccessException {
	String dataSharingKey = UUID.randomUUID().toString();
	String bbymAccountAddressId = UUID.randomUUID().toString();
	Address address1 = new Address(bbymAccountAddressId, "55435", "7081 Penn Ave S", "Suite 110", "Richfield",
		"MN", "a175157", "a175157");
	BbyAccount bbymAccount1 = new BbyAccount(dataSharingKey, "Sri", "Sid", "4102221212", "aa@a.com", "1234567890",
		address1, "a175157", "a175157");

	String carrierAccountAddressId = UUID.randomUUID().toString();
	Address address2 = new Address(carrierAccountAddressId, "55435", "7081 Penn Ave S", "Suite 110", "Richfield",
		"MN", "a175157", "a175157");
	CarrierAccount carrierAccount1 = new CarrierAccount("1010101", "55435", "Sri", "Sid", "4102221212",
		dataSharingKey, address2, "aa@a.com", "a175157", "a175157");

	RecSheetSummary recSheetSummary1 = new RecSheetSummary("Plan Info", "Device Info", "GSP Plan Info",
		"BuyBack Info", dataSharingKey, "a175157", "a175157");

	Customer customer1 = new Customer(dataSharingKey, "3807", false, "BEAST", "a175157", "a175157", new Date(),
		recSheetSummary1, bbymAccount1, carrierAccount1, "390710101010010");
	customerDao.persistCustomer(customer1);
	Customer customer2 = customerDao.getCustomer("3807", dataSharingKey);
	assertEquals(customer1.getDataSharingKey(), customer2.getDataSharingKey());
	assertEquals(customer1.getStoreId(), customer2.getStoreId());
	assertEquals(customer1.getSource(), customer1.getSource());
	assertEquals(customer1.getTransferFlag(), customer1.getTransferFlag());
	TestUtil.assertBeanEqual(customer1.getBbyAccount(), customer2.getBbyAccount());
	TestUtil.assertBeanEqual(customer1.getCarrierAccount(), customer2.getCarrierAccount());
	TestUtil.assertBeanEqual(customer1.getRecSheetSummary(), customer2.getRecSheetSummary());
    }

    @Test
    public void testUpdateCustomer() throws DataAccessException {
	String dataSharingKey = UUID.randomUUID().toString();
	String bbymAccountAddressId = UUID.randomUUID().toString();
	Address address1 = new Address(bbymAccountAddressId, "55435", "7081 Penn Ave S", "Suite 110", "Richfield",
		"MN", "a175157", "a175157");
	BbyAccount bbymAccount1 = new BbyAccount(dataSharingKey, "Sri", "Sid", "4102221212", "aa@a.com", "1234567890",
		address1, "a175157", "a175157");

	String carrierAccountAddressId = UUID.randomUUID().toString();
	Address address2 = new Address(carrierAccountAddressId, "55435", "7081 Penn Ave S", "Suite 110", "Richfield",
		"MN", "a175157", "a175157");
	CarrierAccount carrierAccount1 = new CarrierAccount("1010101", "55435", "Sri", "Sid", "4102221212",
		dataSharingKey, address2, "aa@a.com", "a175157", "a175157");

	RecSheetSummary recSheetSummary1 = new RecSheetSummary("Plan Info", "Device Info", "GSP Plan Info",
		"BuyBack Info", dataSharingKey, "a175157", "a175157");
	Customer customer = new Customer(dataSharingKey, "3807", false, "BEAST", "a175157", "a175157", new Date(),
		recSheetSummary1, bbymAccount1, carrierAccount1, "390710101010010");
	try{
	    customerDao.updateCustomer(customer);
	}catch(EmptyResultDataAccessException ex){
	    fail("There is no account");
	}
	address2 = new Address(carrierAccountAddressId, "55435", "7081 Penn Ave S", "Suite 111", "Richfield", "MN",
		"a175157", "a175157");
	customer = new Customer(dataSharingKey, "3807", false, "BEAST", "a175157", "a175157", new Date(),
		recSheetSummary1, bbymAccount1, carrierAccount1, "390710101010010");
	try{
	    customerDao.updateCustomer(customer);
	}catch(DataAccessException exception){

	    Customer customer2 = customerDao.getCustomer("3807", dataSharingKey);

	    assertEquals(customer.getDataSharingKey(), customer2.getDataSharingKey());
	    assertEquals(customer.getStoreId(), customer2.getStoreId());
	    TestUtil.assertBeanEqual(customer.getBbyAccount(), customer2.getBbyAccount());
	    TestUtil.assertBeanEqual(customer.getCarrierAccount(), customer2.getCarrierAccount());
	    TestUtil.assertBeanEqual(customer.getRecSheetSummary(), customer2.getRecSheetSummary());
	}
    }

    @Test
    public void testUpdateCustomerException() throws DataAccessException {

	String dataSharingKey = UUID.randomUUID().toString();
	String bbymAccountAddressId = UUID.randomUUID().toString();

	Address address1 = new Address(bbymAccountAddressId, "55435", "7081 Penn Ave S", "Suite 110", "Richfield",
		"MN", "a175157", "a175157");
	BbyAccount bbymAccount1 = new BbyAccount(dataSharingKey, "Udaya", "Duvvuri", "4102221212", "aa@a.com",
		"0987654321", address1, "a909241", "a909241");
	String carrierAccountAddressId = UUID.randomUUID().toString();
	Address address2 = new Address(carrierAccountAddressId, "55435", "7601 Penn Ave S", "Suite 110", "Richfield",
		"MN", "a175157", "a175157");
	CarrierAccount carrierAccount1 = new CarrierAccount("1010101", "55435", "Sri", "Sid", "4102221212",
		dataSharingKey, address2, "aa@a.com", "a175157", "a175157");

	RecSheetSummary recSheetSummary1 = new RecSheetSummary("Plan Info", "Device Info", "GSP Plan Info",
		"BuyBack Info", dataSharingKey, "a175157", "a175157");
	Customer customer = new Customer(dataSharingKey, "3807", false, "BEAST", "a175157", "a175157", new Date(),
		recSheetSummary1, bbymAccount1, carrierAccount1, "390710101010010");
	customerDao.persistCustomer(customer);

	try{
	    customer = new Customer(dataSharingKey, "3807", false, "BEAST", "a175157", "a175157", new Date(),
		    recSheetSummary1, null, carrierAccount1, "390710101010010");
	    BbyAccount bbymAccount2 = null;
	    bbymAccount2 = customer.getBbyAccount();
	    assertTrue("Customer Account not found", true);
	    logger.info("Account is--->>" + bbymAccount2);
	}catch(EmptyResultDataAccessException ex){
	    BbyAccount bbymAccount3 = null;
	    Customer customer3 = null;
	    bbymAccount3 = customer3.getBbyAccount();
	    assertTrue("Customer Account not found", true);
	}
    }
}
