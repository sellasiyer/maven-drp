package com.bestbuy.bbym.ise.drp.domain;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Factory used to create dummy and mock {@link Recommendation} objects for
 * testing.
 */
public abstract class RecommendationFactory {

    private RecommendationFactory() {
	// This class is not meant to be extended or instantiated
    }

    /**
     * Builds mock data used for testing
     */
    public static Recommendation getRecommendation() {

	Recommendation recommendation = new Recommendation();
	recommendation.setFirstName("Sri");
	recommendation.setLastName("Sid");
	recommendation.setMobileNo("4102221212");

	return recommendation;
    }

    public static Recommendation buildDummyRecommendation() {
	Recommendation r = new Recommendation();

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
	r.setBbyCustomerId("someBbyCustomerId");
	r.setUpgradeReminderText(true);
	r.setUpgradeReminderCall(true);
	r.setUpgradeEligibilityDate(new Date());
	r.setSubscriptionInfo("current subscripiton information samsung spring 200 minutes test data");
	r.setTradeInValue(new BigDecimal(20.5f));
	r.setEmpCrtFrstNm("someEmployeeCreatedFirstName");
	r.setEmpCrtLastNm("someEmployeeCreatedLastName");
	r.setEmpAltFrstNm("someEmployeeAlteredFirstName");
	r.setEmpAltLastNm("someEmployeeAlteredLastName");

	r.setEssentials(EssentialsFactory.getEssentials());

	return r;
    }
}
