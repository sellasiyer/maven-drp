package com.bestbuy.bbym.ise.drp.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.bestbuy.bbym.ise.drp.domain.Carrier;
import com.bestbuy.bbym.ise.drp.domain.CheckOutCheckInHistory;
import com.bestbuy.bbym.ise.drp.domain.CheckOutCheckInHistoryFactory;
import com.bestbuy.bbym.ise.drp.domain.DrpUser;
import com.bestbuy.bbym.ise.drp.domain.DrpUserFactory;
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
import com.bestbuy.bbym.ise.exception.IseExceptionCodeEnum;
import com.bestbuy.bbym.ise.util.jdbc.DatabaseScript;

/**
 * JUnit test for {@link LoanerPhoneDaoImpl}.
 */
public class LoanerPhoneDaoImplTest extends BaseDaoTest {

    private Logger logger = LoggerFactory.getLogger(LoanerPhoneDaoImplTest.class);

    private LoanerPhoneDaoImpl loanerPhoneDao = new LoanerPhoneDaoImpl();

    @Before
    public void setUp() throws Exception {
	loanerPhoneDao.setNamedJdbcTemplate(new NamedParameterJdbcTemplate(db));
	loanerPhoneDao.setJdbcTemplate(new JdbcTemplate(db));
	DatabaseScript.execute("ise_db_scripts/add_phones_drp_ise_db.sql", db);
    }

    @After
    public void after() throws Exception {
	DatabaseScript.execute("ise_db_scripts/clean_ise_loanerPhone.sql", db);
    }

    @Test
    public void testGetAllPhones() throws Exception {
	List<Phone> list = loanerPhoneDao.getPhones("0699", null);
	logger.info("GETTING ALL PHONES");
	for(Phone phone: list){
	    logger.info("PHONE=" + phone);
	}
	assertEquals(3, list.size());
    }

    @Test
    public void testGetPhones() throws Exception {
	PhoneSearchCriteria criteria = new PhoneSearchCriteria();
	Carrier carrier = new Carrier();
	carrier.setCarrier("AT&T");
	// criteria.setLast4SerialNo("12345678");
	// criteria.setCarrier(carrier);
	OperatingSystem os = new OperatingSystem();
	os.setOs("iOS");
	// criteria.setOperatingSystem(os);
	List<Phone> list = loanerPhoneDao.getPhones("0699", criteria);
	assertNotNull(list);

	assertEquals(3, list.size()); // No criteria
	// assertEquals(1, list.size());
	Phone phone = list.get(0);
	assertNotNull(phone);
	assertEquals(new Long(1), phone.getId());
	assertEquals("Apple", phone.getMake());
	assertEquals("iphone 4S", phone.getModelNumber());
	assertEquals("a123", phone.getCreatedBy());
	assertNotNull(phone.getCreatedOn());
	assertEquals("AT&T", phone.getCarrier().getCarrier());
	assertEquals("iOS", phone.getOperatingSystem().getOs());
	assertEquals(true, phone.isActive());
    }

    @Test
    public void testGetCheckedOutPhones() throws Exception {
	PhoneSearchCriteria criteria = new PhoneSearchCriteria();
	Carrier carrier = new Carrier();
	carrier.setCarrier("AT&T");
	criteria.setLast4SerialNo("5678");
	criteria.setCarrier(carrier);
	OperatingSystem os = new OperatingSystem();
	os.setOs("iOS");
	criteria.setOperatingSystem(os);
	criteria.setLast4ServiceOrderNo("2345");
	criteria.setCheckedOut(true);

	logger.info("Getting the checked out phones.......");

	List<Phone> list = loanerPhoneDao.getPhones("0699", criteria);
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

    }

    @Test
    public void testGetCheckedInPhones() throws Exception {
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

	List<Phone> list = loanerPhoneDao.getPhones("0699", criteria);
	assertNotNull(list);
	assertEquals(2, list.size());

	Phone phone = list.get(1);
	assertNotNull(phone);
	assertEquals(new Long(2), phone.getId());
	assertEquals("Android", phone.getMake());
	assertEquals("Samsung", phone.getModelNumber());
	assertEquals("a234", phone.getCreatedBy());
	assertNotNull(phone.getCreatedOn());
	// assertNotNull(phone.getLatestCheckOutCheckInHistory());

	phone = list.get(0);
	assertNotNull(phone);
	assertEquals(new Long(3), phone.getId());
	assertEquals("Apple", phone.getMake());
	assertEquals("iphone 4S", phone.getModelNumber());
	assertEquals("a123", phone.getCreatedBy());
	assertNotNull(phone.getCreatedOn());
	assertNotNull(phone.getLatestCheckOutCheckInHistory());
    }

    @Test
    public void testGetPhoneModels() throws Exception {
	PhoneModelSearchCriteria criteria = new PhoneModelSearchCriteria();
	Carrier carrier = new Carrier();
	carrier.setCarrier("AT&T");
	// carrier.setCarrier("T-Mobile");
	criteria.setCarrier(carrier);
	OperatingSystem os = new OperatingSystem();
	os.setOs("iOS");
	// criteria.setOperatingSystem(os);
	List<PhoneModel> list = loanerPhoneDao.getPhoneModels("0699", criteria);
	assertNotNull(list);
	assertEquals(1, list.size());
	PhoneModel phoneModel = list.get(0);
	assertNotNull(phoneModel);
	assertEquals("Apple", phoneModel.getMake());
	assertEquals("iphone 4S", phoneModel.getModel());
	assertEquals(2, phoneModel.getNoOfPhones());
	assertEquals("AT&T", phoneModel.getCarrier().getCarrier());
	assertEquals("iOS", phoneModel.getOs().getOs());

	/*
	 * phoneModel = list.get(0); assertNotNull(phoneModel);
	 * assertEquals("Android", phoneModel.getMake());
	 * assertEquals("Samsung", phoneModel.getModel()); assertEquals(1,
	 * phoneModel.getNoOfPhones());
	 */

    }

    @Test
    public void testGetCarriers() throws Exception {
	List<Carrier> carrierList = loanerPhoneDao.getCarriers();
	assertNotNull(carrierList);
	assertEquals(4, carrierList.size());
	Carrier carrier = carrierList.get(0);
	assertNotNull(carrier);
	assertEquals("AT&T", carrier.getCarrier());
	// assertEquals("1111",carrier.getCarrierLoanerSku());
	carrier = carrierList.get(1);
	assertNotNull(carrier);
	assertEquals("Sprint", carrier.getCarrier());
	// assertEquals("2222",carrier.getCarrierLoanerSku());
	carrier = carrierList.get(2);
	assertNotNull(carrier);
	assertEquals("T-Mobile", carrier.getCarrier());
	// assertEquals("3333",carrier.getCarrierLoanerSku());
	carrier = carrierList.get(3);
	assertNotNull(carrier);
	assertEquals("Verizon", carrier.getCarrier());
	// assertEquals("4444",carrier.getCarrierLoanerSku());
    }

    @Test
    public void testGetOperatingSystems() throws Exception {
	List<OperatingSystem> osList = loanerPhoneDao.getOperatingSystems();
	assertNotNull(osList);
	assertEquals(5, osList.size());
	OperatingSystem os = osList.get(0);
	assertNotNull(os);
	assertEquals("iOS", os.getOs());
	assertNotNull(os.getInstruction());
	os = osList.get(1);
	assertNotNull(os);
	assertEquals("BlackBerry", os.getOs());
	assertNotNull(os.getInstruction());
	os = osList.get(2);
	assertNotNull(os);
	assertEquals("Android", os.getOs());
	assertNotNull(os.getInstruction());
	os = osList.get(3);
	assertNotNull(os);
	assertEquals("Windows", os.getOs());
	assertNotNull(os.getInstruction());
	os = osList.get(4);
	assertNotNull(os);
	assertEquals("Other", os.getOs());
	assertNotNull(os.getInstruction());
    }

    @Test
    public void testCreatePhone() throws Exception {
	Phone phone1 = PhoneFactory.createPhone();
	loanerPhoneDao.createPhone(phone1);

	Phone phone2 = loanerPhoneDao.getPhone(phone1.getId());
	BeanAssert.assertBeanEquals(phone1, phone2);
    }

    @Test
    public void testCreatePhoneWithNulls() throws Exception {
	Phone phone1 = new Phone();
	phone1.setCarrier(new Carrier());
	phone1.getCarrier().setId(1L);
	phone1.setOperatingSystem(new OperatingSystem());
	phone1.getOperatingSystem().setId(2L);
	phone1.setStoreId("3");
	phone1.setCreatedBy("createdBy");
	phone1.setCreatedOn(new java.sql.Date(new java.util.Date("1/1/2012").getTime()));
	phone1.setSerialNumber("12345678");
	phone1.setActive(true);
	loanerPhoneDao.createPhone(phone1);

	Phone phone2 = loanerPhoneDao.getPhone(phone1.getId());
	BeanAssert.assertBeanEquals(phone1, phone2);
    }

    @Test
    public void testCreate2PhonesWithDifferentStorIdAndSameSerialNumber() throws Exception {
	Phone phone1 = PhoneFactory.createPhone();
	loanerPhoneDao.createPhone(phone1);
	Phone phone2 = PhoneFactory.createPhone();
	phone2.setStoreId(Integer.valueOf(phone1.getStoreId() + 1).toString());
	loanerPhoneDao.createPhone(phone2);
    }

    @Test
    public void testCreate2PhonesWithSameStorIdAndDifferentSerialNumber() throws Exception {
	Phone phone1 = PhoneFactory.createPhone();
	phone1.setSerialNumber("serialNumber1");
	loanerPhoneDao.createPhone(phone1);
	Phone phone2 = PhoneFactory.createPhone();
	phone2.setSerialNumber("serialNumber2");
	loanerPhoneDao.createPhone(phone2);
    }

    @Test
    public void testCreate2PhonesWithSameStorIdAndSameSerialNumber() throws Exception {
	Phone phone1 = PhoneFactory.createPhone();
	loanerPhoneDao.createPhone(phone1);
	Phone phone2 = PhoneFactory.createPhone();
	try{
	    loanerPhoneDao.createPhone(phone2);
	    fail();
	}catch(DataAccessException ex){
	    assertEquals(IseExceptionCodeEnum.DuplicateLoanerPhone, ex.getErrorCode());
	}
    }

    @Test
    public void testUpdatePhone() throws Exception {
	Phone phone1 = PhoneFactory.createPhone();
	loanerPhoneDao.createPhone(phone1);
	phone1.setCondition(LoanerPhoneCondition.GOOD);
	phone1.setDeletedReason(LoanerPhoneDeletedReason.LOST);
	phone1 = loanerPhoneDao.getPhone(phone1.getId());
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
	phone1.setLastName("vankayala");
	phone1.setFirstName("deepthi");
	loanerPhoneDao.updatePhone(phone1);

	Phone phone2 = loanerPhoneDao.getPhone(phone1.getId());
	BeanAssert.assertBeanEquals(phone1, phone2);
    }

    @Test
    public void testCreatePhoneThenInactivatePhoneThenCreatePhoneWithSameSerialNumber() throws Exception {
	Phone phone1 = PhoneFactory.createPhone();
	phone1.setActive(true);
	phone1.setDeletedReason(null);
	loanerPhoneDao.createPhone(phone1);
	phone1.setActive(false);
	phone1.setDeletedReason(LoanerPhoneDeletedReason.DAMAGED);
	loanerPhoneDao.updatePhone(phone1);
	Phone phone2 = PhoneFactory.createPhone();
	phone2.setDeletedReason(null);
	loanerPhoneDao.createPhone(phone2);
	Phone phone3 = loanerPhoneDao.getPhone(phone2.getId());
	assertNotNull(phone3);
	BeanAssert.assertBeanEquals(phone2, phone3);
    }

    @Test
    public void testCreatePhoneThenUpdateSerialNumberThenCreatePhoneWithPreviousSerialNumber() throws Exception {
	Phone phone1 = PhoneFactory.createPhone();
	phone1.setActive(true);
	phone1.setSerialNumber("serialNumber1");
	loanerPhoneDao.createPhone(phone1);
	phone1.setSerialNumber("serialNumber2");
	loanerPhoneDao.updatePhone(phone1);
	Phone phone2 = PhoneFactory.createPhone();
	phone2.setSerialNumber("serialNumber1");
	loanerPhoneDao.createPhone(phone2);
	Phone phone3 = loanerPhoneDao.getPhone(phone1.getId());
	assertNotNull(phone3);
	assertEquals(true, phone3.isActive());
	assertEquals("serialNumber2", phone3.getSerialNumber());
	Phone phone4 = loanerPhoneDao.getPhone(phone2.getId());
	assertNotNull(phone4);
	assertEquals(true, phone4.isActive());
	assertEquals("serialNumber1", phone4.getSerialNumber());
    }

    @Test
    public void testUpdateDuplicatePhone() throws Exception {
	Phone phone1 = PhoneFactory.createPhone();
	phone1.setSerialNumber("serialNumber1");
	loanerPhoneDao.createPhone(phone1);
	Phone phone2 = PhoneFactory.createPhone();
	phone2.setSerialNumber("serialNumber2");
	loanerPhoneDao.createPhone(phone2);
	phone2.setSerialNumber(phone1.getSerialNumber());
	try{
	    loanerPhoneDao.updatePhone(phone2);
	    fail();
	}catch(DataAccessException ex){
	    assertEquals(IseExceptionCodeEnum.DuplicateLoanerPhone, ex.getErrorCode());
	}
    }

    @Test
    public void testUpdateDeactivateCheckedOutPhone() throws Exception {
	Phone phone = PhoneFactory.createPhone();
	CheckOutCheckInHistory history = CheckOutCheckInHistoryFactory.createCheckOutCheckInHistory();
	history.setPhone(phone);
	phone.setLatestCheckOutCheckInHistory(history);
	phone.setActive(false);
	try{
	    loanerPhoneDao.updatePhone(phone);
	    fail();
	}catch(DataAccessException ex){
	    assertEquals(IseExceptionCodeEnum.DeactivatedCheckedOutLoanerPhone, ex.getErrorCode());
	}
    }

    @Test
    public void testDeletePhone() throws Exception {
	Phone phone = PhoneFactory.createPhone();
	loanerPhoneDao.createPhone(phone);
	phone = loanerPhoneDao.getPhone(phone.getId());
	assertNotNull(phone);
	loanerPhoneDao.deletePhone(phone.getId());
	phone = loanerPhoneDao.getPhone(phone.getId());
	assertNull(phone);
    }

    @Test
    public void testCheckOutPhone() throws Exception {
	CheckOutCheckInHistory history1 = CheckOutCheckInHistoryFactory.createCheckOutCheckInHistory();
	DrpUser testUser = DrpUserFactory.getDrpUser();
	history1.setCreatedBy(testUser.getUserId());
	history1.setModifiedBy(testUser.getUserId());
	Phone phone = PhoneFactory.createPhone();
	phone.setId(new Long(2));
	history1.setPhone(phone);
	loanerPhoneDao.checkOutPhone(history1);
	CheckOutCheckInHistory history2 = loanerPhoneDao.getCheckOutCheckInHistory(history1.getId());
	assertEquals(history1.getCheckOutCondition(), history2.getCheckOutCondition());
	assertEquals(history1.getCheckOutTime(), history2.getCheckOutTime());
	assertEquals(history1.getCheckOutDeposit(), history2.getCheckOutDeposit());
	assertEquals(history1.getCheckOutEmployee(), history2.getCheckOutEmployee());
	// BeanAssert.assertBeanEquals(history1, history2);
    }

    @Test
    public void testCheckOutPhoneWithNulls() throws Exception {
	CheckOutCheckInHistory history1 = new CheckOutCheckInHistory();
	Phone phone = PhoneFactory.createPhone();
	phone.setId(new Long(2));
	history1.setPhone(phone);
	DrpUser testUser = DrpUserFactory.getDrpUser();
	history1.setModifiedBy(testUser.getUserId());
	history1.setCreatedBy(testUser.getUserId());
	history1.setCreatedOn(new Date());
	loanerPhoneDao.checkOutPhone(history1);
	CheckOutCheckInHistory history2 = loanerPhoneDao.getCheckOutCheckInHistory(history1.getId());
	// BeanAssert.assertBeanEquals(history1, history2);
    }

    @Test
    public void testCheckInPhone() throws Exception {
	CheckOutCheckInHistory history1 = new CheckOutCheckInHistory();
	Phone phone = PhoneFactory.createPhone();
	phone.setId(new Long(4));
	history1.setPhone(phone);
	DrpUser testUser = DrpUserFactory.getDrpUser();
	// history1.setModifiedBy(testUser.getUserId());
	history1.setCreatedBy(testUser.getUserId());
	history1.setCreatedOn(new Date());
	loanerPhoneDao.checkOutPhone(history1);
	loanerPhoneDao.checkInPhone(history1);
	CheckOutCheckInHistory history2 = loanerPhoneDao.getCheckOutCheckInHistory(history1.getId());
	assertEquals(history1.getCheckInCondition(), history2.getCheckInCondition());
	assertEquals(history1.getCheckInTime(), history2.getCheckInTime());
	assertEquals(history1.getCheckInDeposit(), history2.getCheckInDeposit());
	assertEquals(history1.getCheckInEmployee(), history2.getCheckInEmployee());
	// BeanAssert.assertBeanEquals(history1, history2);
    }

    @Test
    public void testCheckOutPhoneTwice() throws Exception {
	CheckOutCheckInHistory history1 = CheckOutCheckInHistoryFactory.createCheckOutCheckInHistory();

	DrpUser testUser = DrpUserFactory.getDrpUser();
	history1.setCreatedBy(testUser.getUserId());
	history1.setModifiedBy(testUser.getUserId());
	Phone phone = PhoneFactory.createPhone();
	phone.setId(new Long(1));
	history1.setPhone(phone);
	try{
	    loanerPhoneDao.checkOutPhone(history1); //phone id-1 is already checked out. should throw ex.
	}catch(DataAccessException ex){
	    IseExceptionCodeEnum code = ex.getErrorCode();
	    assertEquals(code, IseExceptionCodeEnum.LoanerPhoneAlreadyCheckedOut);
	}
    }

    @Test
    public void testGetDistinctMakes() throws Exception {
	String list[] = loanerPhoneDao.getDistinctMakes("699");
	assertEquals(2, list.length);
	assertEquals("Apple", list[0]);
	assertEquals("Android", list[1]);
    }

    @Test
    public void testGetDistinctModels() throws Exception {
	String list[] = loanerPhoneDao.getDistinctModels("699");
	assertEquals(2, list.length);
	assertEquals("iphone 4S", list[0]);
	assertEquals("Samsung", list[1]);
    }

}
