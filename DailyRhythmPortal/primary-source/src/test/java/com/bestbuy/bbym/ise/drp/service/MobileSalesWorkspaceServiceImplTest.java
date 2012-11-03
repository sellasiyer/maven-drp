package com.bestbuy.bbym.ise.drp.service;

import static org.easymock.EasyMock.createStrictMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.bestbuy.bbym.ise.domain.User;
import com.bestbuy.bbym.ise.drp.dao.RecommendationsDaoImpl;
import com.bestbuy.bbym.ise.drp.domain.DrpUserFactory;
import com.bestbuy.bbym.ise.drp.domain.EssentialsFactory;
import com.bestbuy.bbym.ise.drp.domain.RecSheetCountByDay;
import com.bestbuy.bbym.ise.drp.domain.RecSheetCountByDayFactory;
import com.bestbuy.bbym.ise.drp.domain.RecSheetReportList;
import com.bestbuy.bbym.ise.drp.domain.RecSheetReportingSearchResults;
import com.bestbuy.bbym.ise.drp.domain.Recommendation;
import com.bestbuy.bbym.ise.exception.DataAccessException;
import com.bestbuy.bbym.ise.exception.ServiceException;

/**
 * JUnit test for {@link MobileSalesWorkspaceServiceImpl}.
 */
public class MobileSalesWorkspaceServiceImplTest {

    private MobileSalesWorkspaceServiceImpl service = new MobileSalesWorkspaceServiceImpl();
    private RecommendationsDaoImpl mockDao;

    @Before
    public void setUp() {
	mockDao = createStrictMock(RecommendationsDaoImpl.class);
	service.setRecommendationsDao(mockDao);
    }

    /**
     * Testing for the happy path for findRecommendations
     */
    @Test
    public void testFindRecommendations() throws Exception {
	// Setting up the mock rec sheets
	Recommendation rec1 = this.buildMockRecommendation();
	Recommendation rec2 = this.buildMockRecommendation();
	String storeId = rec1.getStoreId();
	String customerLastName = rec1.getLastName();
	List<Recommendation> results = new ArrayList<Recommendation>();
	results.add(rec1);
	results.add(rec2);

	// Testing the happy path
	expect(mockDao.getRecommendationList(storeId, customerLastName)).andReturn(results);

	replay(mockDao);
	assertEquals(service.findRecommendations(customerLastName, storeId), results);
	verify(mockDao);
    }

    /**
     * Testing for Service Exception in the method findRecommendations
     */
    @Test(expected = ServiceException.class)
    public void testFindRecommendationsDataAccessException() throws Exception {
	// Setting up mock data used to call service test
	String customerLastName = "Smith";
	String storeId = "0699";

	// Setting up throw the exception
	expect(mockDao.getRecommendationList(storeId, customerLastName)).andThrow(new DataAccessException());
	replay(mockDao);

	service.findRecommendations(customerLastName, storeId);
    }

    /**
     * Testing the happy path for findRecommendationsByMobile
     */
    @Test
    public void testFindRecommendationsByMobile() throws Exception {
	// Setting up the mock rec sheets
	Recommendation rec1 = this.buildMockRecommendation();
	Recommendation rec2 = this.buildMockRecommendation();
	String storeId = rec1.getStoreId();
	String mobileNumber = rec1.getMobileNo();
	List<Recommendation> results = new ArrayList<Recommendation>();
	results.add(rec1);
	results.add(rec2);

	// Testing the happy path
	expect(mockDao.getRecommendationListByMobile(storeId, mobileNumber)).andReturn(results);

	replay(mockDao);
	assertEquals(service.findRecommendationsByMobile(mobileNumber, storeId), results);
	verify(mockDao);
    }

    /**
     * Testing the Service Exception of the method findRecommendationByMobile
     */
    @Test(expected = ServiceException.class)
    public void testFindRecommendationsByMoblieServiceException() throws Exception {
	// Setting up mock data used to call service
	String storeId = "0699";
	String mobileNumber = "3134221704";

	// Setting up the throw exceptions
	expect(mockDao.getRecommendationListByMobile(storeId, mobileNumber)).andThrow(new DataAccessException());
	replay(mockDao);

	service.findRecommendationsByMobile(mobileNumber, storeId);
    }

    /**
     * Testing for the happy path for findRecommendationsForCAndT
     */
    @Test
    public void testFindRecommendationsForCAndT() throws Exception {
	// Setting up the mock rec sheets
	Recommendation rec1 = this.buildMockRecommendation();
	Recommendation rec2 = this.buildMockRecommendation();
	String storeId = rec1.getStoreId();
	String customerLastName = rec1.getLastName();
	List<Recommendation> results = new ArrayList<Recommendation>();
	results.add(rec1);
	results.add(rec2);

	// Testing the happy path
	expect(mockDao.getRecommendationListForCAndT(storeId, customerLastName)).andReturn(results);

	replay(mockDao);
	assertEquals(service.findRecommendationsForCAndT(customerLastName, storeId), results);
	verify(mockDao);
    }

    /**
     * Testing for Service Exception in the method findRecommendationsForCAndT
     */
    @Test(expected = ServiceException.class)
    public void testFindRecommendationsForCAndTDataAccessException() throws Exception {
	// Setting up mock data used to call service test
	String customerLastName = "Smith";
	String storeId = "0699";

	// Setting up throw the exception
	expect(mockDao.getRecommendationListForCAndT(storeId, customerLastName)).andThrow(new DataAccessException());
	replay(mockDao);

	service.findRecommendationsForCAndT(customerLastName, storeId);
    }

    /**
     * Testing the happy path for findRecommendationsForCAndTByMobile
     */
    @Test
    public void testFindRecommendationsForCAndTByPhone() throws Exception {
	// Setting up the mock rec sheets
	Recommendation rec1 = this.buildMockRecommendation();
	Recommendation rec2 = this.buildMockRecommendation();
	String storeId = rec1.getStoreId();
	String mobileNumber = rec1.getMobileNo();
	List<Recommendation> results = new ArrayList<Recommendation>();
	results.add(rec1);
	results.add(rec2);

	// Testing the happy path
	expect(mockDao.getRecommendationListForCAndTByPhone(storeId, mobileNumber)).andReturn(results);

	replay(mockDao);
	assertEquals(service.findRecommendationsForCAndTByPhone(mobileNumber, storeId), results);
	verify(mockDao);
    }

    /**
     * Testing the Service Exception of the method findRecommendationForCAndTByMobile
     */
    @Test(expected = ServiceException.class)
    public void testFindRecommendationsForCAndTByPhoneServiceException() throws Exception {
	// Setting up mock data used to call service
	String storeId = "0699";
	String mobileNumber = "3134221704";

	// Setting up the throw exceptions
	expect(mockDao.getRecommendationListForCAndTByPhone(storeId, mobileNumber)).andThrow(new DataAccessException());
	replay(mockDao);

	service.findRecommendationsForCAndTByPhone(mobileNumber, storeId);
    }

    /**
     * Testing the happy path of findRecommendationReportsByAId
     */
    @Ignore
    @Test
    public void testFindRecommendationReportsByAId() throws Exception {
	// Setting up start date
	Date startDate = new Date();
	Date endDate = new Date();

	// Setting up some dummy rec sheets
	Recommendation rec = this.buildMockRecommendation();
	String aId = rec.getCreatedBy();
	String firstName = rec.getEmpCrtFrstNm();
	String lastName = rec.getEmpCrtLastNm();
	rec.setCreatedOn(new Date());

	// Setting up the expected results
	RecSheetReportingSearchResults result = new RecSheetReportingSearchResults();
	result.setAid(aId);
	result.setFirstName(firstName);
	result.setLastName(lastName);
	result.addRecommendation(rec);
	result.addRecommendation(rec);

	List<RecSheetReportingSearchResults> resultList = new ArrayList<RecSheetReportingSearchResults>();
	resultList.add(result);

	// Testing findRecommendationReportsByAId
	expect(mockDao.getRecommendationEmployeeNamesByAId(aId)).andReturn(resultList);
	expect(mockDao.getRecommendationReportsListByAId(aId, startDate, endDate)).andReturn(result.getRecs());

	replay(mockDao);
	assertEquals(service.findRecommendationReportsByAId(aId, startDate, endDate), resultList);
	verify(mockDao);
    }

    /**
     * Testing the null path of the method findRecommendationReportsByAId
     */
    @Test
    public void testFindRecommendationReportsByAIdNullPath() throws Exception {
	// Call the service using nulls
	List<RecSheetReportingSearchResults> results = service.findRecommendationReportsByAId(null, null, null);
	assertEquals(Collections.EMPTY_LIST, results);
    }

    /**
     * Testing the ServiceException for findRecommendationReportsByAId
     */
    @Ignore
    @Test(expected = ServiceException.class)
    public void testFindRecommendationReportsByAIdServiceException() throws Exception {
	// Setting up mock data used to call service
	String aId = "a911002";
	Date startDate = new Date();
	Date endDate = new Date();

	// Setting up mockDao calls
	expect(mockDao.getRecommendationEmployeeNamesByAId(aId)).andThrow(new DataAccessException());
	replay(mockDao);

	service.findRecommendationReportsByAId(aId, startDate, endDate);
    }

    /**
     * Testing the happy path of findRecommendationReportsByAId adding store id
     */
    @Ignore
    @Test
    public void testFindRecommendationReportsByAIdStoreId() throws Exception {
	// Setting up start date
	Date startDate = new Date();
	Date endDate = new Date();

	// Setting up some dummy rec sheets
	Recommendation rec = this.buildMockRecommendation();
	String aId = rec.getCreatedBy();
	String firstName = rec.getEmpCrtFrstNm();
	String lastName = rec.getEmpCrtLastNm();
	String storeId = rec.getStoreId();
	rec.setCreatedOn(new Date());

	// Setting up the expected results
	RecSheetReportingSearchResults result = new RecSheetReportingSearchResults();
	result.setAid(aId);
	result.setFirstName(firstName);
	result.setLastName(lastName);
	result.addRecommendation(rec);
	result.addRecommendation(rec);

	List<RecSheetReportingSearchResults> resultList = new ArrayList<RecSheetReportingSearchResults>();
	resultList.add(result);

	// Testing findRecommendationReportsByAId
	expect(mockDao.getRecommendationEmployeeNamesByAId(aId)).andReturn(resultList);
	expect(mockDao.getRecommendationReportsListByAId(aId, startDate, endDate, storeId)).andReturn(result.getRecs());

	replay(mockDao);
	assertEquals(service.findRecommendationReportsByAId(aId, startDate, endDate, storeId), resultList);
	verify(mockDao);
    }

    /**
     * Testing the null path of the method findRecommendationReportsByAId adding
     * store id
     */
    @Test
    public void testFindRecommendationReportsByAIdNullPathStoreId() throws Exception {
	// Call the service using nulls
	List<RecSheetReportingSearchResults> results = service.findRecommendationReportsByAId(null, null, null, null);
	assertEquals(Collections.EMPTY_LIST, results);
    }

    /**
     * Testing the ServiceException for findRecommendationReportsByAId adding
     * store id
     */
    @Ignore
    @Test(expected = ServiceException.class)
    public void testFindRecommendationReportsByAIdServiceExceptionStoreId() throws Exception {
	// Setting up mock data used to call service
	String aId = "a911002";
	Date startDate = new Date();
	Date endDate = new Date();
	String storeId = "0699";

	// Setting up mockDao calls
	expect(mockDao.getRecommendationEmployeeNamesByAId(aId)).andThrow(new DataAccessException());
	replay(mockDao);

	service.findRecommendationReportsByAId(aId, startDate, endDate, storeId);
    }

    /**
     * Testing the happy path for the method findRecSheetReportsByLastName
     */
    @Test
    public void testFindRecSheetReportsByLastName() throws Exception {
	// setting up start date
	Date startDate = new Date();
	Date endDate = new Date();

	// setting up some dummy rec sheets
	Recommendation rec = this.buildMockRecommendation();
	String aId = rec.getCreatedBy();
	String firstName = rec.getEmpCrtFrstNm();
	String lastName = rec.getEmpCrtLastNm();
	rec.setCreatedOn(new Date());

	// setting up the expected results
	RecSheetReportingSearchResults result = new RecSheetReportingSearchResults();
	result.setAid(aId);
	result.setFirstName(firstName);
	result.setLastName(lastName);
	result.addRecommendation(rec);
	result.addRecommendation(rec);

	List<RecSheetReportingSearchResults> resultList = new ArrayList<RecSheetReportingSearchResults>();
	resultList.add(result);

	// testing findRecommendationReportsByAId
	expect(mockDao.getRecommendationEmployeeNamesByLastName(lastName)).andReturn(resultList);
	expect(mockDao.getRecommendationReportsListByAId(aId, startDate, endDate)).andReturn(result.getRecs());

	replay(mockDao);

	List<RecSheetReportingSearchResults> finalResults = service.findRecommendationReportsByLastName(lastName,
		startDate, endDate);
	assertNotNull(finalResults);
	verify(mockDao);

    }

    /**
     * Testing the null path of the method findRecommendationReportsByLastName
     */
    @Test
    public void testFindRecommendationReportsByLastNameNullPath() throws Exception {
	// Call the service using nulls
	List<RecSheetReportingSearchResults> results = service.findRecommendationReportsByLastName(null, null, null);
	assertEquals(Collections.EMPTY_LIST, results);
    }

    /**
     * Testing the ServiceException for findRecommendationReportsByLastName
     */
    @Test(expected = ServiceException.class)
    public void testFindRecommendationReportsByLastNameServiceException() throws Exception {
	// Setting up mock data used to call service
	String empLastName = "Tester";
	Date startDate = new Date();
	Date endDate = new Date();

	// Setting up mockDao calls
	expect(mockDao.getRecommendationEmployeeNamesByLastName(empLastName)).andThrow(new DataAccessException());
	replay(mockDao);

	service.findRecommendationReportsByLastName(empLastName, startDate, endDate);
    }

    /**
     * Testing the happy path for the method findRecSheetReportsByLastName
     * adding store id
     */
    @Test
    public void testFindRecSheetReportsByLastNameStoreId() throws Exception {
	// setting up start date
	Date startDate = new Date();
	Date endDate = new Date();

	// setting up some dummy rec sheets
	Recommendation rec = this.buildMockRecommendation();
	String aId = rec.getCreatedBy();
	String firstName = rec.getEmpCrtFrstNm();
	String lastName = rec.getEmpCrtLastNm();
	String storeId = rec.getStoreId();
	rec.setCreatedOn(new Date());

	// setting up the expected results
	RecSheetReportingSearchResults result = new RecSheetReportingSearchResults();
	result.setAid(aId);
	result.setFirstName(firstName);
	result.setLastName(lastName);
	result.addRecommendation(rec);
	result.addRecommendation(rec);

	List<RecSheetReportingSearchResults> resultList = new ArrayList<RecSheetReportingSearchResults>();
	resultList.add(result);

	// testing findRecommendationReportsByAId
	expect(mockDao.getRecommendationEmployeeNamesByLastName(lastName)).andReturn(resultList);
	expect(mockDao.getRecommendationReportsListByAId(aId, startDate, endDate, storeId)).andReturn(result.getRecs());

	replay(mockDao);

	List<RecSheetReportingSearchResults> finalResults = service.findRecommendationReportsByLastName(lastName,
		startDate, endDate, storeId);
	assertNotNull(finalResults);
	verify(mockDao);

    }

    /**
     * Testing the null path of the method findRecommendationReportsByLastName
     * adding the store id
     */
    @Test
    public void testFindRecommendationReportsByLastNameNullPathStoreId() throws Exception {
	// Call the service using nulls
	List<RecSheetReportingSearchResults> results = service.findRecommendationReportsByLastName(null, null, null,
		null);
	assertEquals(Collections.EMPTY_LIST, results);
    }

    /**
     * Testing the ServiceException for findRecommendationReportsByLastName
     * adding the store id
     */
    @Test(expected = ServiceException.class)
    public void testFindRecommendationReportsByLastNameServiceExceptionStoreId() throws Exception {
	// Setting up mock data used to call service
	String empLastName = "Tester";
	Date startDate = new Date();
	Date endDate = new Date();
	String storeId = "0699";

	// Setting up mockDao calls
	expect(mockDao.getRecommendationEmployeeNamesByLastName(empLastName)).andThrow(new DataAccessException());
	replay(mockDao);

	service.findRecommendationReportsByLastName(empLastName, startDate, endDate, storeId);
    }

    /**
     * Testing the happy path with an existing rec sheet for saveRecommendation
     */
    @Test
    public void testSaveRecommendationExisting() throws Exception {
	// Setting up mock data
	Recommendation rec = this.buildMockRecommendation();
	User mockUser = DrpUserFactory.getDrpUser();
	// Setting rec sheet id in order to test for existing sheet
	rec.setId(2500);

	mockDao.persistRecommendation(rec, mockUser);
	EasyMock.expectLastCall();

	EasyMock.replay(mockDao);

	// Testing the Happy Path
	service.saveRecommendation(rec, mockUser);
	EasyMock.verify(mockDao);
    }

    /**
     * Testing the happy path with a new rec sheet for saveRecommendation
     */
    @Test
    public void testSaveRecommendationNew() throws Exception {
	// Setting up mock data to test
	Recommendation rec = this.buildMockRecommendation();
	User mockUser = DrpUserFactory.getDrpUser();

	expect(mockDao.addRecommendation(rec, mockUser)).andReturn((long) 1000);
	EasyMock.expectLastCall();

	EasyMock.replay(mockDao);

	// Testing the Happy Path
	service.saveRecommendation(rec, mockUser);
	EasyMock.verify(mockDao);
    }

    /**
     * Testing for ServiceException in the method saveRecommendation
     */
    @Test(expected = ServiceException.class)
    public void testSaveRecommendationServiceException() throws Exception {
	Recommendation rec = this.buildMockRecommendation();
	User mockUser = DrpUserFactory.getDrpUser();
	rec.setId(2500);

	mockDao.persistRecommendation(rec, mockUser);
	EasyMock.expectLastCall().andThrow(new DataAccessException());

	EasyMock.replay(mockDao);

	service.saveRecommendation(rec, mockUser);
	EasyMock.verify(mockDao);
    }

    @Test
    public void testFindRecommendationFromToDate() throws Exception {
	// Setting up the mock data used to test
	Date startDate = new Date();
	RecSheetCountByDay count1 = RecSheetCountByDayFactory.getRecSheetCountByDay("a022232", "George", "Washington");
	RecSheetCountByDay count2 = RecSheetCountByDayFactory.getRecSheetCountByDay("a021209", "Abraham", "Lincoln");
	List<RecSheetCountByDay> list = new ArrayList<RecSheetCountByDay>();
	list.add(count1);
	list.add(count2);
	Date endDate = new Date();

	expect(mockDao.getRecommendationListFromToDate(startDate, endDate, count1.getStoreId())).andReturn(list);
	EasyMock.replay(mockDao);

	List<RecSheetReportList> results = service.findRecommendationsFromToDate(startDate, endDate, count1
		.getStoreId());
	assertEquals(results.size(), 2);
	EasyMock.verify(mockDao);
    }

    /**
     * Testing ServiceException for the method findRecommendationFromToDate
     */
    @Test(expected = ServiceException.class)
    public void testFindRecommendationsFromToDateNullPath() throws Exception {
	Date fromDate = new Date();
	String storeId = "0699";
	Date toDate = new Date();

	List<RecSheetCountByDay> results = null;

	expect(mockDao.getRecommendationListFromToDate(fromDate, toDate, storeId)).andReturn(results);

	EasyMock.replay(mockDao);

	service.findRecommendationsFromToDate(fromDate, toDate, storeId);
	EasyMock.verify(mockDao);
    }

    /**
     * Testing the ServiceException for the method findRecommendationFromToDate
     */
    @Test(expected = ServiceException.class)
    public void testFindRecommendationsFromToDateServiceException() throws Exception {
	Date fromDate = new Date();
	String storeId = "0699";
	Date toDate = new Date();

	expect(mockDao.getRecommendationListFromToDate(fromDate, toDate, storeId)).andThrow(new DataAccessException());
	EasyMock.replay(mockDao);

	service.findRecommendationsFromToDate(fromDate, toDate, storeId);
	EasyMock.verify(mockDao);
    }

    @Test(expected = ServiceException.class)
    public void testFindRecommendationFromToDateSwithcedDates() throws ServiceException {
	String storeId = "0699";

	service.findRecommendationsFromToDate(null, null, storeId);
    }

    /**
     * Setting up the mock data used for testing
     */
    private Recommendation buildMockRecommendation() {
	com.bestbuy.bbym.ise.drp.domain.Recommendation r = new com.bestbuy.bbym.ise.drp.domain.Recommendation();

	// in order they appear in db table.
	r.setWowRequirements(Recommendation.WowRequirements.APPLICATIONS.name(), true);
	r.setWowRequirements(Recommendation.WowRequirements.BLUETOOTHPAIRING.name(), true);
	r.setWowRequirements(Recommendation.WowRequirements.POWERMANAGEMENT.name(), true);
	r.setDeviceCapabilities(Recommendation.DeviceCapabilities.EMAIL.name(), true);
	r.setDeviceCapabilities(Recommendation.DeviceCapabilities.NAVIGATION.name(), true);
	r.setDeviceCapabilities(Recommendation.DeviceCapabilities.UNLOCKED.name(), true);

	r.setRecommendedSubscription("test data unlimited subscription");
	r.setRecommendedDevice("test data samsung 5200");
	r.setNetUseInfo("test_netuseInfo");
	r.setNotes("test_notes");
	r.setCreatedBy("a885527");
	r.setAmendedBy("a885527");
	r.setStoreId("store12345-test");
	r.setSpecialistContactInfo("test_contact info");
	r.setFirstName("fuzzy -testname");
	r.setLastName("wuzzy -testname");
	r.setMobileNo("9894003932");
	r.setBestTimeToContact("after 4 pm -testdata-");
	r.setBbyCustomerId("bbycust_string -testdata-");
	r.setUpgradeReminderText(true);
	r.setUpgradeReminderCall(true);
	r.setUpgradeEligibilityDate(new Date());
	r.setSubscriptionInfo("current subscripiton information samsung spring 200 minutes test data");
	r.setTradeInValue(new BigDecimal(20.5f));
	r.setEmpCrtLastNm("Tester");
	r.setEmpCrtFrstNm("Johnny");

	r.setEssentials(EssentialsFactory.getEssentials());

	return r;
    }

}
