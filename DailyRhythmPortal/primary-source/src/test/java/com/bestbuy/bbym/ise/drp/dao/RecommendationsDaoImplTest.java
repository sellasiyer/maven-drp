package com.bestbuy.bbym.ise.drp.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.bestbuy.bbym.ise.drp.domain.DrpUser;
import com.bestbuy.bbym.ise.drp.domain.DrpUserFactory;
import com.bestbuy.bbym.ise.drp.domain.Essentials;
import com.bestbuy.bbym.ise.drp.domain.RecSheetCountByDay;
import com.bestbuy.bbym.ise.drp.domain.Recommendation;
import com.bestbuy.bbym.ise.drp.domain.RecommendationFactory;
import com.bestbuy.bbym.ise.util.jdbc.DatabaseScript;

/**
 * JUnit test for {@link RecommendationsDaoImpl}.
 */
public class RecommendationsDaoImplTest extends BaseDaoTest {

    private RecommendationsDaoImpl recommendationsDaoImpl = new RecommendationsDaoImpl();
    private EssentialsDao mockEssentialsDao;

    @Before
    public void setUp() {
	mockEssentialsDao = EasyMock.createMock(EssentialsDao.class);
	recommendationsDaoImpl.setEssentialsDao(mockEssentialsDao);
	recommendationsDaoImpl.setUseNextSequenceOnCreate(true);
	recommendationsDaoImpl.setNamedJdbcTemplate(new NamedParameterJdbcTemplate(db));
	recommendationsDaoImpl.setJdbcTemplate(new JdbcTemplate(db));
    }

    /**
     * Test {@link RecommendationsDaoImpl#getRecommendation(long)}.
     */
    @Test
    public void testGetRecommendation() throws Exception {
	DatabaseScript.execute("ise_db_scripts/add_recommendations_data.sql", db);

	final long id = 1;

	Recommendation recommendation = recommendationsDaoImpl.getRecommendation(id);
	assertNotNull("No recommendation found", recommendation);

	assertEquals("Incorrect id", id, recommendation.getId());
	assertEquals("Incorrect firstName", "someFirstName", recommendation.getFirstName());
	assertEquals("Incorrect lastName", "someLastName", recommendation.getLastName());
	assertEquals("Incorrect mobileNo", "5551111111", recommendation.getMobileNo());
	assertEquals("Incorrect bestTimeToContact", "someBestTimeToContact", recommendation.getBestTimeToContact());
	assertEquals("Incorrect bbyCustomerId", "someBbyCustomerId", recommendation.getBbyCustomerId());
	assertEquals("Incorrect upgradeReminderText", Boolean.FALSE, recommendation.getUpgradeReminderText());
	assertEquals("Incorrect upgradeReminderCall", Boolean.TRUE, recommendation.getUpgradeReminderCall());
	assertEquals("Incorrect upgradeEligibilityDate", null, recommendation.getUpgradeEligibilityDate());
	assertEquals("Incorrect subscriptionInfo", "someSubscriptionInfo", recommendation.getSubscriptionInfo());
	assertEquals("Incorrect tradeInValue", BigDecimal.valueOf(111.23), recommendation.getTradeInValue());
	assertEquals("Incorrect deviceCapabilities", 0, recommendation.getDeviceCapabilities());
	assertEquals("Incorrect netUseInfo", "someNetUseInfo", recommendation.getNetUseInfo());
	assertEquals("Incorrect recommendedSubscription", "someRecommendedSubscription", recommendation
		.getRecommendedSubscription());
	assertEquals("Incorrect recommendedDevice", "someRecommendedDevice", recommendation.getRecommendedDevice());
	assertEquals("Incorrect wowRequirements", 0, recommendation.getWowRequirements());
	assertEquals("Incorrect notes", "someNotes", recommendation.getNotes());
	assertEquals("Incorrect specialistContactInfo", "someSpecialistContactInfo", recommendation
		.getSpecialistContactInfo());
	assertEquals("Incorrect storeId", "0644", recommendation.getStoreId());
	// assertEquals("Incorrect createdOn", null, rec.getCreatedOn());
	assertEquals("Incorrect createdBy", "a123", recommendation.getCreatedBy());
	assertEquals("Incorrect amendedOn", null, recommendation.getAmendedOn());
	assertEquals("Incorrect amendedBy", null, recommendation.getAmendedBy());
	assertEquals("Incorrect empCrtFrstNm", "someEmpCreateFirstName", recommendation.getEmpCrtFrstNm());
	assertEquals("Incorrect empCrtLastNm", "someEmpCreateLastName", recommendation.getEmpCrtLastNm());
	assertEquals("Incorrect empAltFrstNm", null, recommendation.getEmpAltFrstNm());
	assertEquals("Incorrect empAltLastNm", null, recommendation.getEmpAltLastNm());
    }

    @Ignore
    @Test
    public void testPersistRecommendation() throws Exception {

	// get rec. Update with dummy recommendation data.
	Recommendation oldRec = recommendationsDaoImpl.getRecommendation(1L);
	assertNotNull(oldRec);
	Long recId = oldRec.getId();
	Recommendation rec = RecommendationFactory.buildDummyRecommendation();

	rec.setId(recId);

	DrpUser testUser = DrpUserFactory.getDrpUser();

	// persist new rec back to db.
	recommendationsDaoImpl.persistRecommendation(rec, testUser);
	Recommendation persistedRec = recommendationsDaoImpl.getRecommendation(oldRec.getId());
	Recommendation compareToRec = RecommendationFactory.buildDummyRecommendation();

	// lots of asserts to check data integrity.
	// tests on requirements values.
	assertEquals(persistedRec.getDeviceCapabilities(), oldRec.getDeviceCapabilities());
	assertEquals(persistedRec.getWowRequirements(), compareToRec.getWowRequirements());
	assertEquals(persistedRec.getBbyCustomerId(), compareToRec.getBbyCustomerId());
	assertEquals(persistedRec.getBestTimeToContact(), compareToRec.getBestTimeToContact());
	assertEquals(persistedRec.getFirstName(), compareToRec.getFirstName());
	assertEquals(persistedRec.getLastName(), compareToRec.getLastName());
	assertEquals(persistedRec.getMobileNo(), compareToRec.getMobileNo());

	assertEquals(persistedRec.getNetUseInfo(), compareToRec.getNetUseInfo());
	assertEquals(persistedRec.getNotes(), compareToRec.getNotes());
	assertEquals(persistedRec.getRecommendedDevice(), compareToRec.getRecommendedDevice());
	assertEquals(persistedRec.getRecommendedSubscription(), compareToRec.getRecommendedSubscription());
	assertEquals(persistedRec.getSpecialistContactInfo(), compareToRec.getSpecialistContactInfo());
	assertEquals(persistedRec.getStoreId(), testUser.getStoreId());
	assertEquals(persistedRec.getSubscriptionInfo(), compareToRec.getSubscriptionInfo());
	assertEquals(persistedRec.getTradeInValue(), compareToRec.getTradeInValue());
	assertEquals(persistedRec.getUpgradeEligibilityDate(), compareToRec.getUpgradeEligibilityDate());
	assertEquals(persistedRec.getUpgradeReminderCall(), compareToRec.getUpgradeReminderCall());
	assertEquals(persistedRec.getUpgradeReminderText(), compareToRec.getUpgradeReminderText());

	assertEquals(persistedRec.getCreatedBy(), testUser.getUserId());
	assertEquals(persistedRec.getAmendedBy(), testUser.getUserId());
	assertEquals("Incorrect empCrtFrstNm", compareToRec.getEmpCrtFrstNm(), persistedRec.getEmpCrtFrstNm());
	assertEquals("Incorrect empCrtLastNm", compareToRec.getEmpCrtLastNm(), persistedRec.getEmpCrtLastNm());
	assertEquals("Incorrect empAltFrstNm", compareToRec.getEmpAltFrstNm(), persistedRec.getEmpAltFrstNm());
	assertEquals("Incorrect empAltLastNm", compareToRec.getEmpAltLastNm(), persistedRec.getEmpAltLastNm());

	// test essentials got saved correctly.
	Essentials e = persistedRec.getEssentials();
	Essentials oldEssentials = compareToRec.getEssentials();
	assertEquals(e.getAccessories(), oldEssentials.getAccessories());
	assertEquals(e.getBuyback(), oldEssentials.getBuyback());
	assertEquals(e.getChargers(), oldEssentials.getChargers());
	assertEquals(e.getFinancing(), oldEssentials.getFinancing());
	assertEquals(e.getGsbtp(), oldEssentials.getGsbtp());
	assertEquals(e.getHandsfree(), oldEssentials.getHandsfree());
	assertEquals(e.getMemory(), oldEssentials.getMemory());
	assertEquals(e.getShields(), oldEssentials.getShields());

    }

    @Ignore
    @Test
    public void testEssentialsPersistence() throws Exception {
	Recommendation rec = RecommendationFactory.buildDummyRecommendation();

	DrpUser testUser = DrpUserFactory.getDrpUser();

	// save the rec to db.
	recommendationsDaoImpl.addRecommendation(rec, testUser);

	// create new essentials and populate with dummy info.
	Essentials dummyEssentials = new Essentials();
	dummyEssentials.setAccessories("stuff");
	dummyEssentials.setBuyback("buyback 100");
	dummyEssentials.setChargers("dc charger");

	// set rec object with dummy essentials
	rec.setEssentials(dummyEssentials);
	// trigger an update to db.
	recommendationsDaoImpl.persistRecommendation(rec, testUser);

	// retrieve recently updated recommendation and assign e to the recently
	// retrieve essentials.
	rec = recommendationsDaoImpl.getRecommendation(rec.getId());
	assertNotNull(rec);
	Essentials e = rec.getEssentials();
	assertNotNull(e);

	// check e against the dummy info we used, also check nulls.
	assertEquals("buyback 100", e.getBuyback());
	assertEquals("dc charger", e.getChargers());
	assertEquals("stuff", e.getAccessories());
	assertNull(e.getFinancing());
	assertNull(e.getGsbtp());
	assertNull(e.getMemory());
	assertNull(e.getShields());
	assertNull(e.getHandsfree());
    }

    /**
     * Test {@link RecommendationsDaoImpl#getRecommendationList(String)}.
     */
    @Test
    public void testGetRecommendationsListByStoreId() throws Exception {
	DatabaseScript.execute("ise_db_scripts/add_recommendations_data.sql", db);

	String storeId = "0644";
	EasyMock.expect(mockEssentialsDao.getEssentials(1)).andReturn(null);
	EasyMock.replay(mockEssentialsDao);

	List<Recommendation> list = recommendationsDaoImpl.getRecommendationList(storeId);

	assertNotNull("No recommendation found", list);
	assertEquals("Incorrect number of recommendations found", 1, list.size());
	assertEquals("Incorrect storeId", storeId, list.get(0).getStoreId());
    }

    /**
     * Test {@link RecommendationsDaoImpl#getRecommendationList(String, String)}
     * .
     */
    @Test
    public void testGetRecommendationsListByName() throws Exception {
	DatabaseScript.execute("ise_db_scripts/add_recommendations_data.sql", db);

	String storeId = "0644";
	String lastName = "someLastName";

	List<Recommendation> list = recommendationsDaoImpl.getRecommendationList(storeId, lastName);

	assertNotNull("No recommendation found", list);
	assertEquals("Incorrect number of recommendations found", 1, list.size());
	assertEquals("Incorrect storeId", storeId, list.get(0).getStoreId());
	assertEquals("Incorrect lastName", lastName, list.get(0).getLastName());
    }

    /**
     * Test
     * {@link RecommendationsDaoImpl#getRecommendationListByMobile(String, String)}
     * .
     */
    @Test
    public void testGetRecommendationsListByMobile() throws Exception {
	DatabaseScript.execute("ise_db_scripts/add_recommendations_data.sql", db);

	String storeId = "0644";
	String mobileNumber = "5551111111";

	// get list for original mobile no, make sure it's only size 2.
	// 4 records were added, one has wrong store id, one has wrong number.
	List<Recommendation> list = recommendationsDaoImpl.getRecommendationListByMobile(storeId, mobileNumber);
	assertNotNull("No recommendation found", list);
	assertEquals("Incorrect number of recommendations found", 1, list.size());
	assertEquals("Incorrect storeId", storeId, list.get(0).getStoreId());
	assertEquals("Incorrect mobileNo", mobileNumber, list.get(0).getMobileNo());
    }

    /**
     * Test {@link RecommendationsDaoImpl#getRecommendationList(String, String)}
     * .
     */
    @Test
    public void testGetRecommendationsListForCAndTByName() throws Exception {
	DatabaseScript.execute("ise_db_scripts/add_recommendations_data_computer_and_tablet.sql", db);

	String storeId = "0644";
	String lastName = "someLastName";

	List<Recommendation> list = recommendationsDaoImpl.getRecommendationListForCAndT(storeId, lastName);

	assertNotNull("No recommendation found", list);
	assertEquals("Incorrect number of recommendations found", 1, list.size());
	assertEquals("Incorrect storeId", storeId, list.get(0).getStoreId());
	assertEquals("Incorrect lastName", lastName, list.get(0).getLastName());
    }

    /**
     * Test
     * {@link RecommendationsDaoImpl#getRecommendationListByMobile(String, String)}
     * .
     */
    @Test
    public void testGetRecommendationsListForCAndTByPhone() throws Exception {
	DatabaseScript.execute("ise_db_scripts/add_recommendations_data_computer_and_tablet.sql", db);

	String storeId = "0644";
	String phoneNumber = "5551111111";

	// get list for original mobile no, make sure it's only size 2.
	// 4 records were added, one has wrong store id, one has wrong number.
	List<Recommendation> list = recommendationsDaoImpl.getRecommendationListForCAndTByPhone(storeId, phoneNumber);
	assertNotNull("No recommendation found", list);
	assertEquals("Incorrect number of recommendations found", 1, list.size());
	assertEquals("Incorrect storeId", storeId, list.get(0).getStoreId());
	assertEquals("Incorrect mobileNo", phoneNumber, list.get(0).getMobileNo());
    }

    /**
     * Test
     * {@link RecommendationsDaoImpl#getRecommendationListFromToDate(Date, Date, String)}
     * .
     */
    @Test
    public void testGetRecommendationListFromToDate() throws Exception {
	DatabaseScript.execute("ise_db_scripts/add_recommendations_data.sql", db);

	String storeId = "0644";
	Date startDate = new Date();

	List<RecSheetCountByDay> list = recommendationsDaoImpl.getRecommendationListFromToDate(startDate, new Date(),
		storeId);

	assertNotNull("No recommendation found", list);
	assertEquals("Incorrect number of recommendations found", 1, list.size());
	RecSheetCountByDay countByDay = list.get(0);
	assertEquals("Incorrect storeId", storeId, countByDay.getStoreId());
	assertEquals("Incorrect firstName", "someEmpCreateFirstName", countByDay.getFirstName());
	assertEquals("Incorrect lastName", "someEmpCreateLastName", countByDay.getLastName());
	String today = new SimpleDateFormat("yyyymmdd").format(new Date());
	assertEquals("Incorrect changeDate", today, countByDay.getChangeDate());
	assertEquals("Incorrect countByDay", String.valueOf(1), countByDay.getCountByDay());
	assertEquals("Incorrect aid", "a123", countByDay.getAid());
    }

    /**
     * Test
     * {@link RecommendationsDaoImpl#getRecommendationReportsListByAId(String, Date, Date)}
     */
    @Ignore
    @Test
    public void testGetRecommendationReportsListByAId() throws Exception {
	DatabaseScript.execute("ise_db_scripts/add_recommendations_data.sql", db);

	Calendar yesterday = Calendar.getInstance();
	yesterday.add(Calendar.DATE, -1);

	Calendar tomorrow = Calendar.getInstance();
	tomorrow.add(Calendar.DATE, 1);

	List<Recommendation> list = recommendationsDaoImpl.getRecommendationReportsListByAId("a123", yesterday
		.getTime(), tomorrow.getTime());

	assertNotNull("No recommendation found", list);
	assertEquals("Incorrect number of recommendations found", 1, list.size());
    }

    /**
     * Test
     * {@link RecommendationsDaoImpl#getRecommendationListFromToDate(Date, Date)}
     * .
     */
    @Test
    public void testGetRecommendationList() throws Exception {
	DatabaseScript.execute("ise_db_scripts/add_recommendations_data.sql", db);

	Date startDate = new Date();
	EasyMock.expect(mockEssentialsDao.getEssentials(1)).andReturn(null);
	EasyMock.expect(mockEssentialsDao.getEssentials(2)).andReturn(null);
	EasyMock.expect(mockEssentialsDao.getEssentials(5)).andReturn(null);
	EasyMock.expect(mockEssentialsDao.getEssentials(9)).andReturn(null);
	EasyMock.expect(mockEssentialsDao.getEssentials(22)).andReturn(null);
	EasyMock.replay(mockEssentialsDao);
	List<Recommendation> list = recommendationsDaoImpl.getRecommendationList(startDate, new Date());

	assertNotNull("No recommendation found", list);
	assertEquals("Incorrect number of recommendations found", 4, list.size());
    }

}
