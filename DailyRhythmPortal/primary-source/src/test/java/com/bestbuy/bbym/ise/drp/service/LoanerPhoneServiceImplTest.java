package com.bestbuy.bbym.ise.drp.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import com.bestbuy.bbym.ise.domain.User;
import com.bestbuy.bbym.ise.drp.dao.LoanerPhoneDaoImpl;
import com.bestbuy.bbym.ise.drp.domain.Carrier;
import com.bestbuy.bbym.ise.drp.domain.CheckOutCheckInHistory;
import com.bestbuy.bbym.ise.drp.domain.CheckOutCheckInHistoryFactory;
import com.bestbuy.bbym.ise.drp.domain.LoanerPhoneCondition;
import com.bestbuy.bbym.ise.drp.domain.LoanerPhoneDeletedReason;
import com.bestbuy.bbym.ise.drp.domain.OperatingSystem;
import com.bestbuy.bbym.ise.drp.domain.Phone;
import com.bestbuy.bbym.ise.drp.domain.PhoneFactory;
import com.bestbuy.bbym.ise.drp.domain.PhoneModel;
import com.bestbuy.bbym.ise.drp.helpers.PhoneModelSearchCriteria;
import com.bestbuy.bbym.ise.drp.helpers.PhoneSearchCriteria;
import com.bestbuy.bbym.ise.drp.utils.BeanAssert;
import com.bestbuy.bbym.ise.exception.DataAccessException;
import com.bestbuy.bbym.ise.exception.IseBusinessException;
import com.bestbuy.bbym.ise.exception.IseExceptionCodeEnum;
import com.bestbuy.bbym.ise.exception.ServiceException;
import com.bestbuy.bbym.ise.util.jdbc.DatabaseScript;

/**
 * JUnit test for {@link LoanerPhoneServiceImpl}.
 */
public class LoanerPhoneServiceImplTest {

    private Logger logger = LoggerFactory.getLogger(LoanerPhoneServiceImplTest.class);

    private LoanerPhoneDaoImpl loanerPhoneDaoImpl = new LoanerPhoneDaoImpl();
    private LoanerPhoneServiceImpl loanerPhoneServiceImpl = new LoanerPhoneServiceImpl(loanerPhoneDaoImpl);
    private User testUser = new User();
    private static EmbeddedDatabase db;
    private static EmbeddedDatabaseBuilder builder;
    private static DataSourceTransactionManager transactionManager;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
	builder = new EmbeddedDatabaseBuilder();
	db = builder.setType(EmbeddedDatabaseType.H2).addScript("ise_db_scripts/create_ise_loanerPhone.sql").build();
	transactionManager = new DataSourceTransactionManager(db);
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
	DatabaseScript.execute("ise_db_scripts/drop_ise_loanerPhone.sql", db);
	db.shutdown();
    }

    @Before
    public void setUp() {
	loanerPhoneDaoImpl.setUseNextSequenceOnCreate(true);
	loanerPhoneDaoImpl.setNamedJdbcTemplate(new NamedParameterJdbcTemplate(db));
	loanerPhoneDaoImpl.setJdbcTemplate(new JdbcTemplate(db));
	loanerPhoneDaoImpl.setTransactionManager(transactionManager);
	testUser.setUserId("a777");
    }

    @After
    public void tearDown() throws Exception {
	DatabaseScript.execute("ise_db_scripts/clean_ise_loanerPhone.sql", db);
    }

    // @Test
    public void testGetPhones() throws ServiceException, IseBusinessException, DataAccessException {
	DatabaseScript.execute("ise_db_scripts/add_phones_drp_ise_db.sql", db);
	PhoneSearchCriteria criteria = new PhoneSearchCriteria();
	Carrier carrier = new Carrier();
	carrier.setCarrier("AT&T");
	// criteria.setLast4SerialNo("5678");
	// criteria.setCarrier(carrier);
	OperatingSystem os = new OperatingSystem();
	os.setOs("iOS");
	// criteria.setOperatingSystem(os);

	// criteria.setCheckedOut(false);

	List<Phone> list = loanerPhoneServiceImpl.getPhones("0699", criteria);
	assertNotNull(list);
	assertEquals(3, list.size());
	Phone phone = list.get(0);
	assertNotNull(phone);
	assertEquals(new Long(1), phone.getId());
	assertEquals("Apple", phone.getMake());
	assertEquals("iphone 4S", phone.getModelNumber());
	assertEquals("a123", phone.getCreatedBy());
	assertNotNull(phone.getCreatedOn());
	assertNotNull(phone.getLatestCheckOutCheckInHistory());
	assertEquals("Deepthi", phone.getLatestCheckOutCheckInHistory().getFirstName());
	assertEquals(3, phone.getLatestCheckOutCheckInHistory().getNoOfDaysOut());
	assertEquals("AT&T", phone.getCarrier().getCarrier());
	assertEquals("iOS", phone.getOperatingSystem().getOs());
	assertEquals("12345678", phone.getSerialNumber());

	/*
	 * phone = list.get(1); assertNotNull(phone); assertEquals(new Long(3),
	 * phone.getId()); assertEquals("Apple", phone.getMake());
	 * assertEquals("iphone 4S", phone.getModelNumber());
	 * assertEquals("a123", phone.getCreatedBy());
	 * assertNotNull(phone.getCreatedOn());
	 * assertNull(phone.getLatestCheckOutCheckInHistory());
	 */

    }

    // @Test
    public void testGetCheckedOutPhones() throws ServiceException, IseBusinessException, DataAccessException {
	DatabaseScript.execute("ise_db_scripts/add_phones_drp_ise_db.sql", db);
	PhoneSearchCriteria criteria = new PhoneSearchCriteria();
	Carrier carrier = new Carrier();
	carrier.setCarrier("AT&T");
	// criteria.setLast4SerialNo("5678");
	// criteria.setCarrier(carrier);
	OperatingSystem os = new OperatingSystem();
	os.setOs("iOS");
	// criteria.setOperatingSystem(os);
	criteria.setLast4ServiceOrderNo("2345");
	criteria.setCheckedOut(true);

	logger.info("Getting the checked out phones.......");

	List<Phone> list = loanerPhoneServiceImpl.getPhones("0699", criteria);
	assertNotNull(list);
	assertEquals(1, list.size());
	Phone phone = list.get(0);
	assertNotNull(phone);
	assertEquals(new Long(1), phone.getId());
	assertEquals("Apple", phone.getMake());
	assertEquals("iphone 4S", phone.getModelNumber());
	assertEquals("a123", phone.getCreatedBy());
	assertNotNull(phone.getCreatedOn());

	assertNotNull(phone.getLatestCheckOutCheckInHistory());
	assertEquals("Deepthi", phone.getLatestCheckOutCheckInHistory().getFirstName());
	assertEquals(3, phone.getLatestCheckOutCheckInHistory().getNoOfDaysOut());

    }

    // @Test
    public void testGetCheckedInPhones() throws ServiceException, IseBusinessException, DataAccessException {
	DatabaseScript.execute("ise_db_scripts/add_phones_drp_ise_db.sql", db);
	PhoneSearchCriteria criteria = new PhoneSearchCriteria();
	Carrier carrier = new Carrier();
	carrier.setCarrier("AT&T");
	// criteria.setLast4SerialNo("5678");
	// criteria.setCarrier(carrier);
	OperatingSystem os = new OperatingSystem();
	os.setOs("iOS");
	// criteria.setOperatingSystem(os);

	criteria.setCheckedOut(false);

	logger.info("Getting the checked in phones.......");

	List<Phone> list = loanerPhoneServiceImpl.getPhones("0699", criteria);
	assertNotNull(list);
	assertEquals(2, list.size());

	Phone phone1 = list.get(0);
	assertNotNull(phone1);
	assertEquals(new Long(2), phone1.getId());
	assertEquals("Android", phone1.getMake());
	assertEquals("Samsung", phone1.getModelNumber());
	assertEquals("a234", phone1.getCreatedBy());
	assertNotNull(phone1.getCreatedOn());
	assertNotNull(phone1.getLatestCheckOutCheckInHistory());
	assertEquals(0, phone1.getLatestCheckOutCheckInHistory().getNoOfDaysOut());
    }

    // @Test
    public void testGetPhoneModels() throws ServiceException, IseBusinessException, DataAccessException {
	DatabaseScript.execute("ise_db_scripts/add_phones_drp_ise_db.sql", db);
	PhoneModelSearchCriteria criteria = new PhoneModelSearchCriteria();
	Carrier carrier = new Carrier();
	// carrier.setCarrier("AT&T");
	carrier.setCarrier("Sprint");
	criteria.setCarrier(carrier);
	OperatingSystem os = new OperatingSystem();
	// os.setOs("iOS");
	os.setOs("BlackBerry");
	criteria.setOperatingSystem(os);
	List<PhoneModel> list = loanerPhoneServiceImpl.getPhoneModels("0699", criteria);
	assertNotNull(list);
	assertEquals(1, list.size());
	PhoneModel phoneModel = list.get(0);
	/*
	 * assertNotNull(phoneModel); assertEquals("Apple",
	 * phoneModel.getMake()); assertEquals("iphone 4S",
	 * phoneModel.getModel()); assertEquals(1, phoneModel.getNoOfPhones());
	 * assertEquals("Damaged", phoneModel.getCondition());
	 */

	phoneModel = list.get(0);
	assertNotNull(phoneModel);
	assertEquals("Android", phoneModel.getMake());
	assertEquals("Samsung", phoneModel.getModel());
	assertEquals(1, phoneModel.getNoOfPhones());
	assertEquals(null, phoneModel.getCondition());
	assertEquals("Sprint", phoneModel.getCarrier().getCarrier());
	assertEquals("BlackBerry", phoneModel.getOs().getOs());

    }

    @Test
    public void testCreatePhone() throws Exception {
	DatabaseScript.execute("ise_db_scripts/add_phones_drp_ise_db.sql", db);
	Phone phone1 = PhoneFactory.createPhone();
	loanerPhoneServiceImpl.createPhone(phone1);
	Phone phone2 = loanerPhoneDaoImpl.getPhone(phone1.getId());
	BeanAssert.assertBeanEquals(phone1, phone2);
    }

    @Test
    public void testCreateDuplicatePhone() throws Exception {
	DatabaseScript.execute("ise_db_scripts/add_phones_drp_ise_db.sql", db);
	Phone phone1 = PhoneFactory.createPhone();
	phone1.setSerialNumber("serialNumber");
	loanerPhoneServiceImpl.createPhone(phone1);
	Phone phone2 = PhoneFactory.createPhone();
	phone2.setSerialNumber("serialNumber");
	try{
	    loanerPhoneServiceImpl.createPhone(phone2);
	    fail();
	}catch(ServiceException ex){
	    assertEquals(IseExceptionCodeEnum.DuplicateLoanerPhone, ex.getErrorCode());
	}
    }

    @Test
    public void testUpdatePhone() throws Exception {
	DatabaseScript.execute("ise_db_scripts/add_phones_drp_ise_db.sql", db);
	Phone phone1 = PhoneFactory.createPhone();
	loanerPhoneDaoImpl.createPhone(phone1);
	phone1 = loanerPhoneDaoImpl.getPhone(phone1.getId());
	phone1.setCarrier(new Carrier());
	phone1.getCarrier().setId(2L);
	phone1.setOperatingSystem(new OperatingSystem());
	phone1.getOperatingSystem().setId(3L);
	phone1.setStoreId("4");
	phone1.setCreatedBy("createdByX");
	phone1.setCreatedOn(new java.sql.Date(new java.util.Date("1/1/2013").getTime()));
	phone1.setSerialNumber("12345678X");
	phone1.setActive(false);
	phone1.setCondition(LoanerPhoneCondition.POOR);
	phone1.setConditionComment("conditionCommentX");
	phone1.setDeletedReason(LoanerPhoneDeletedReason.OTHER);
	phone1.setLastActionUserId("lastActionUserIdX");
	phone1.setMake("makeX");
	phone1.setModelNumber("modelNumberX");
	phone1.setModifiedBy("modifiedByX");
	phone1.setModifiedOn(new java.sql.Date(new java.util.Date("1/1/2013").getTime()));
	phone1.setSku("skuX");
	loanerPhoneServiceImpl.updatePhone(phone1);

	Phone phone2 = loanerPhoneDaoImpl.getPhone(phone1.getId());
	BeanAssert.assertBeanEquals(phone1, phone2);
    }

    @Test
    public void testUpdateDuplicatePhone() throws Exception {
	DatabaseScript.execute("ise_db_scripts/add_phones_drp_ise_db.sql", db);
	Phone phone1 = PhoneFactory.createPhone();
	phone1.setSerialNumber("serialNumber1");
	loanerPhoneServiceImpl.createPhone(phone1);
	Phone phone2 = PhoneFactory.createPhone();
	phone2.setSerialNumber("serialNumber2");
	loanerPhoneServiceImpl.createPhone(phone2);
	phone2.setSerialNumber(phone1.getSerialNumber());
	try{
	    loanerPhoneServiceImpl.updatePhone(phone2);
	    fail();
	}catch(ServiceException ex){
	    assertEquals(IseExceptionCodeEnum.DuplicateLoanerPhone, ex.getErrorCode());
	}
    }

    @Test
    public void testCreateCheckOutCheckInHistory() throws Exception {
	DatabaseScript.execute("ise_db_scripts/add_phones_drp_ise_db.sql", db);
	CheckOutCheckInHistory history1 = CheckOutCheckInHistoryFactory.createCheckOutCheckInHistory();
	Phone phone = PhoneFactory.createPhone();
	phone.setId(new Long(2));
	history1.setPhone(phone);
	loanerPhoneServiceImpl.checkOutPhone(history1);
	CheckOutCheckInHistory history2 = loanerPhoneDaoImpl.getCheckOutCheckInHistory(history1.getId());
	assertEquals(history1.getCheckOutCondition(), history2.getCheckOutCondition());
	assertEquals(history1.getCheckOutTime(), history2.getCheckOutTime());
	assertEquals(history1.getCheckOutDeposit(), history2.getCheckOutDeposit());
	assertEquals(history1.getCheckOutEmployee(), history2.getCheckOutEmployee());
    }

    @Test
    public void testCheckInPhone() throws Exception {
	DatabaseScript.execute("ise_db_scripts/add_phones_drp_ise_db.sql", db);
	CheckOutCheckInHistory history1 = CheckOutCheckInHistoryFactory.createCheckOutCheckInHistory();
	Phone phone = PhoneFactory.createPhone();
	phone.setId(new Long(4));
	history1.setPhone(phone);
	loanerPhoneServiceImpl.checkOutPhone(history1);
	CheckOutCheckInHistory history2 = loanerPhoneDaoImpl.getCheckOutCheckInHistory(history1.getId());
	// BeanAssert.assertBeanEquals(history1, history2);
	assertEquals(history1.getCheckInCondition(), history2.getCheckInCondition());
	assertEquals(history1.getCheckInTime(), history2.getCheckInTime());
	assertEquals(history1.getCheckInDeposit(), history2.getCheckInDeposit());
	assertEquals(history1.getCheckInEmployee(), history2.getCheckInEmployee());
    }
}
