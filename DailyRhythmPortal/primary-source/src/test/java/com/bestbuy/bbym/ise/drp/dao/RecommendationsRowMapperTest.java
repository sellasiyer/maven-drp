package com.bestbuy.bbym.ise.drp.dao;

import static org.junit.Assert.assertEquals;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import com.bestbuy.bbym.ise.drp.domain.Recommendation;

/**
 * JUnit test for {@link RecommendationsRowMapper}.
 * 
 * @author Udaya Duvvuri
 */
public class RecommendationsRowMapperTest {

    private ResultSet resultSet;

    @Before
    public void setUp() throws Exception {
	resultSet = EasyMock.createMock(ResultSet.class);
    }

    /**
     * Test {@link RecommendationsRowMapper#mapRow(ResultSet, int)}.
     */
    @Test
    public void testMapRow() throws Exception {

	final long id = 12345;
	final long deviceCapabilities = 98765;
	final long wowRequirements = 6939390;
	final String recommendedSubscription = "someRecommendedSubscription";
	final String recommendedDevice = "someRecommendedDevice";
	final String netUseInfo = "someNetUseInfo";
	final String notes = "someNotes";
	final String createdBy = "someCreatedBy";
	final Date createdDate = Calendar.getInstance().getTime();
	final String amendedBy = "someAmendedBy";
	final Date amendedOn = Calendar.getInstance().getTime();
	final String storeId = "someStoreId";
	final String specialistContactInfo = "someSpecialistContactInfo";
	final String firstName = "someFirstName";
	final String lastName = "someLastName";
	final String mobileNo = "someMobileNo";
	final String bestTimeToContact = "someBestTimeToContact";
	final String bbyCustomerId = "someBbyCustomerId";
	final boolean upgradeReminderText = true;
	final boolean upgradeReminderCall = false;
	final Date upgradeEligibilityDate = Calendar.getInstance().getTime();
	final String subscriptionInfo = "someSubscriptionInfo";
	final String empCreatedFirstName = "someEmpCreatedFirstName";
	final String empCreatedLastName = "someEmpCreatedFirstName";
	final String empAlteredFirstName = "someEmpCreatedFirstName";
	final String empAlteredLastName = "someEmpCreatedFirstName";
	final String addr = "someAddr";
	final String city = "someCity";
	final String state = "someState";
	final String zip = "someZip";
	final String businessCnsltFirstName = "someCnsltFirstName";
	final String businessCnsltLastName = "someCnsltLastName";
	final String businessCnsltPhoneNo = "someCnsltPhoneNo";
	final String businessCnsltPhoneExt = "someCnsltPhoneExt";
	final int recShtTyp = 1;
	final String language = "someLanguage";
	final String tradeInValue = "550$";

	RecommendationsRowMapper recommendationsRowMapper = new RecommendationsRowMapper();

	EasyMock.expect(resultSet.next()).andReturn(true);

	EasyMock.expect(resultSet.getLong("recommendation_id")).andReturn(id);
	EasyMock.expect(resultSet.getLong("device_capabilities")).andReturn(deviceCapabilities);
	EasyMock.expect(resultSet.getLong("wow_requirements")).andReturn(wowRequirements);
	EasyMock.expect(resultSet.getString("recommended_subscription")).andReturn(recommendedSubscription);
	EasyMock.expect(resultSet.getString("recommended_device")).andReturn(recommendedDevice);
	EasyMock.expect(resultSet.getString("net_use_info")).andReturn(netUseInfo);
	EasyMock.expect(resultSet.getString("notes")).andReturn(notes);
	EasyMock.expect(resultSet.getString("created_by")).andReturn(createdBy);
	EasyMock.expect(resultSet.getTimestamp("created_on")).andReturn(new Timestamp(createdDate.getTime()));
	EasyMock.expect(resultSet.getString("amended_by")).andReturn(amendedBy);
	EasyMock.expect(resultSet.getTimestamp("amended_on")).andReturn(new Timestamp(amendedOn.getTime()));
	EasyMock.expect(resultSet.getString("store_id")).andReturn(storeId);
	EasyMock.expect(resultSet.getString("emp_contact")).andReturn(specialistContactInfo);
	EasyMock.expect(resultSet.getString("first_name")).andReturn(firstName);
	EasyMock.expect(resultSet.getString("last_name")).andReturn(lastName);
	EasyMock.expect(resultSet.getString("mobile_no")).andReturn(mobileNo);
	EasyMock.expect(resultSet.getString("best_contact_tm_info")).andReturn(bestTimeToContact);
	EasyMock.expect(resultSet.getString("bby_customer_id")).andReturn(bbyCustomerId);
	EasyMock.expect(resultSet.getBoolean("upgrade_reminder_text")).andReturn(upgradeReminderText);
	EasyMock.expect(resultSet.getBoolean("upgrade_reminder_call")).andReturn(upgradeReminderCall);
	EasyMock.expect(resultSet.getDate("upgrade_eligibility_date")).andReturn(
		new java.sql.Date(upgradeEligibilityDate.getTime()));
	EasyMock.expect(resultSet.getString("subscription_info")).andReturn(subscriptionInfo);
	EasyMock.expect(resultSet.getString("emp_crt_frst_nm")).andReturn(empCreatedFirstName);
	EasyMock.expect(resultSet.getString("emp_crt_last_nm")).andReturn(empCreatedLastName);
	EasyMock.expect(resultSet.getString("emp_alt_frst_nm")).andReturn(empAlteredFirstName);
	EasyMock.expect(resultSet.getString("emp_alt_last_nm")).andReturn(empAlteredLastName);
	EasyMock.expect(resultSet.getString("trade_in_val")).andReturn(tradeInValue);
	EasyMock.expect(resultSet.getBigDecimal("trade_in_val")).andReturn(null);
	EasyMock.expect(resultSet.getInt("rec_sht_typ")).andReturn(recShtTyp);
	EasyMock.expect(resultSet.getString("addr")).andReturn(addr);
	EasyMock.expect(resultSet.getString("city")).andReturn(city);
	EasyMock.expect(resultSet.getString("state")).andReturn(state);
	EasyMock.expect(resultSet.getString("zipcode")).andReturn(zip);
	EasyMock.expect(resultSet.getString("bby_cns_frst_nm")).andReturn(businessCnsltFirstName);
	EasyMock.expect(resultSet.getString("bby_cns_last_nm")).andReturn(businessCnsltLastName);
	EasyMock.expect(resultSet.getString("bby_cns_ph_nbr")).andReturn(businessCnsltPhoneNo);
	EasyMock.expect(resultSet.getString("bby_cns_ph_ext")).andReturn(businessCnsltPhoneExt);
	EasyMock.expect(resultSet.getString("lang")).andReturn(language);
	EasyMock.expect(resultSet.next()).andReturn(false);
	EasyMock.replay(resultSet);

	Recommendation recommendation = recommendationsRowMapper.mapRow(resultSet, 0);

	assertEquals("Incorrect id", id, recommendation.getId());
	// assertEquals("Incorrect deviceCapabilities", deviceCapabilities,
	// recommendation.getDeviceCapabilities());
	// assertEquals("Incorrect wowRequirements", wowRequirements,
	// recommendation.getWowRequirements());
	assertEquals("Incorrect recommendedSubscription", recommendedSubscription, recommendation
		.getRecommendedSubscription());
	assertEquals("Incorrect recommendedDevice", recommendedDevice, recommendation.getRecommendedDevice());
	assertEquals("Incorrect netUseInfo", netUseInfo, recommendation.getNetUseInfo());
	assertEquals("Incorrect notes", notes, recommendation.getNotes());
	assertEquals("Incorrect createdBy", createdBy, recommendation.getCreatedBy());
	assertEquals("Incorrect createdOn", createdDate, recommendation.getCreatedOn());
	assertEquals("Incorrect amendedBy", amendedBy, recommendation.getAmendedBy());
	assertEquals("Incorrect amendedOn", amendedOn, recommendation.getAmendedOn());
	assertEquals("Incorrect storeId", storeId, recommendation.getStoreId());
	assertEquals("Incorrect specialistContactInfo", specialistContactInfo, recommendation
		.getSpecialistContactInfo());
	assertEquals("Incorrect firstName", firstName, recommendation.getFirstName());
	assertEquals("Incorrect lastName", lastName, recommendation.getLastName());
	assertEquals("Incorrect mobileNo", mobileNo, recommendation.getMobileNo());
	assertEquals("Incorrect bestTimeToContact", bestTimeToContact, recommendation.getBestTimeToContact());
	assertEquals("Incorrect bbyCustomerId", bbyCustomerId, recommendation.getBbyCustomerId());
	assertEquals("Incorrect upgradeReminderText", upgradeReminderText, recommendation.getUpgradeReminderText());
	assertEquals("Incorrect upgradeReminderCall", upgradeReminderCall, recommendation.getUpgradeReminderCall());
	assertEquals("Incorrect upgradeEligibilityDate", upgradeEligibilityDate, recommendation
		.getUpgradeEligibilityDate());
	assertEquals("Incorrect subscriptionInfo", subscriptionInfo, recommendation.getSubscriptionInfo());
	assertEquals("Incorrect empCreatedFirstName", empCreatedFirstName, recommendation.getEmpCrtFrstNm());
	assertEquals("Incorrect empCreatedLastName", empCreatedLastName, recommendation.getEmpCrtLastNm());
	assertEquals("Incorrect empAlteredFirstName", empAlteredFirstName, recommendation.getEmpAltFrstNm());
	assertEquals("Incorrect empAlteredLastName", empAlteredLastName, recommendation.getEmpAltLastNm());
	assertEquals("Incorrect language", language, recommendation.getLanguage());
	// assertEquals("Incorrect tradeInValue", tradeInValue,
	// recommendation.getTradeInValue());
    }
}
