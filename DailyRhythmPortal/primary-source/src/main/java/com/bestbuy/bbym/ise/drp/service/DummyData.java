package com.bestbuy.bbym.ise.drp.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import com.bestbuy.bbym.ise.exception.IseBusinessException;
import com.bestbuy.bbym.ise.exception.IseExceptionCodeEnum;
import com.bestbuy.bbym.ise.exception.ServiceException;
import com.bestbuy.bbym.ise.util.Util;

public class DummyData {

    private DummyData() {
    }

    private static final Random random = new Random();
    private static final boolean dontThrowExceptions = false;

    // Generic Data
    private static final String[] PHONE = new String[] {"2344355555", "1232343455", "4233452345", "9084563958",
	    "9233053099", "4101230909", "5551234545", "7871234543" };
    private static final String[] FIRST_NAME = new String[] {"Bob", "Jane", "Sally", "", "Joe", "Harold", "Ken", "Pete" };
    private static final String[] LAST_NAME = new String[] {"Smith", "Jones", "Morgan", "Wilson", "Simpson", "Thomas",
	    "Sellers", "Romney" };
    private static final String[] ADDRESS = new String[] {"123 Main St.", "255 Elm Lane", "342 Grove Ave.",
	    "600 Oak Blvd.", "780 Harvard Circle", "3402 Johnson St.", "5745 Baker Road", "308 Cedar Court" };
    private static final String[] ZIP_CODE = new String[] {"53355", "43455", "23545", "90845", "92339", "41019",
	    "34545" };
    private static final String[] CITY = new String[] {"Smallville", "Lizard Lick", "Bad Creek", "New York", "Raleigh",
	    "St. Paul", "Chicago" };
    private static final String[] STATE = new String[] {"NC", "IL", "MN", "MN", "NY", "WI", "CO" };
    private static final String[] EMAIL = new String[] {"bob@msn.com", "jj23@gmail.com", "sam@yahoo.com",
	    "pete23@gmail.com", "sal999@yahoo.com", "bigman@bestbuy.com", "stungun@msn.com" };

    // Carrier Data
    private static final boolean[] ELIGIBLE = new boolean[] {true, false, true, true, false, true, false };
    private static final String[] HARDWARE = new String[] {"iPhone", "Droid X", "Razor", null, "Blackberry", "Evo",
	    "Samsung", "iPhone", "HTC", "Samsung" };
    private static final String[] LINE_STATUS = new String[] {"ACTIVE", "SUSPENDED", "ACTIVE", "ACTIVE", "UNKNOWN",
	    "CANCELLED", "CLOSED", "ACTIVE", "ACTIVE" };
    private static final BigDecimal[] TRADE_IN = new BigDecimal[] {new BigDecimal(100.0), new BigDecimal(150.0),
	    new BigDecimal(25.00001), new BigDecimal(175.0), null, new BigDecimal(120.00), new BigDecimal(135.55) };
    private static final String[] OPT_IN = new String[] {"TEXT", "VOICE", "TEXT", "VOICE", "NONE", "NONE", null };
    private static final boolean[] OPT_IN_ALLOWED = new boolean[] {true, false, true, true, true, true, false };
    private static final String[] ELIGIBLE_TYPE = new String[] {"early", "standard", "standard", "early", "standard",
	    "early", "standard" };
    private static final String[] ACCT_ID = new String[] {"44355555", "12323455", "43452345", "90843958", "33053099",
	    "41230909", "55514545" };
    private static final String[] SKU = new String[] {"44355555", "12323455", "43452345", "90843958", "33053099",
	    "41230909", "55514545" };
    private static final BigDecimal[] BUY_BACK = new BigDecimal[] {new BigDecimal(199.99), new BigDecimal(150.0),
	    new BigDecimal(25.00001), null, null, new BigDecimal(0.00), new BigDecimal(5.00) };

    // BBY Customer Data
    private static final String[] CA_ACCT_ID = new String[] {"44355555", "12323455", "43452345", "90843958",
	    "33053099", "94855775", null };

    // Product Data
    private static final BigDecimal[] PRICE = new BigDecimal[] {new BigDecimal(499.99), new BigDecimal(13.992),
	    new BigDecimal(100.004), new BigDecimal(99.99), new BigDecimal(199.99), new BigDecimal(-49.99), null };
    private static final String[] NONPHONE_DEVICE = new String[] {"Car Charger", "Washer", "Dryer", null, "70\" TV",
	    "22\" TV", "DVD Player", "Power Scrubber" };
    private static final String[] FOUR_PART_KEY = new String[] {"1987^20120203^31^260", "1937^20110113^31^262",
	    "1217^20100923^31^232", null, "1217^20101101^31^233", "1327^20110713^31^252", "1234^20100223^31^235" };
    private static final String[] TRANSACTION_TYPE = new String[] {"Sale", "Return", "Sale Return", "Exchange", "Sale",
	    "Return", null };
    private static final String[] PLAN_NUMBER = new String[] {"443555552", "123234535", "434523345", "908643958",
	    "330538099", "412830909", "555174545" };
    private static final String[] PLAN_STATUS = new String[] {"ACTIVE", "INACTIVE", "ONHOLD", "CANCELLED", "UNKNOWN",
	    "ACTIVE", null, "ACTIVE", "ACTIVE" };
    private static final String[] PLAN_DESCRIPTION = new String[] {"1 Year", "2 Year", "3 Year", "1 Year", "1 Year",
	    "2 Year", "2 Year" };
    private static final String[] STORE_NUMBER = new String[] {"123", "232", "67", "786", "675", "34", null };
    private static final String[] REGISTER_NUMER = new String[] {"45", "43", "8", "67", "32", "88", null };
    private static final String[] MODEL_NUMBER = new String[] {"MA3245", "JA58543", "KL4658", "OP6967", "KA3332",
	    "QW3288", null };
    private static final String[] SERIAL_NUMBER = new String[] {"6224252154212", "7824252344213", "5022252154212",
	    "7024252152312", "3424252152312", "2424252152312", null };
    private static final String[] LINE_TYPE = new String[] {"Fam Pri.", "Fam Sec.", "ACCOUNT_LEVEL_D1", "Fam Pri.",
	    "Fam Sec.", "ACCOUNT_LEVEL_VD", "Share", null };
    private static final String[] GSP_TRANS_TYPE = new String[] {"PVS", "PVR", "R", "S", "PVS", "S", "S", null };

    // Shipping Data
    private static int MANIFEST_ID_COUNT = 0;
    private static int MANIFEST_LINE_ID_COUNT = 0;
    private static int UPS_TRACKING_NUMBER_COUNT = 0;
    private static final String[] UPS_TRACKING_NUMBER = new String[] {"1Z434435", "1Z431232", "1Z434345", "1Z439084",
	    "1Z433305", "1Z434123", "1Z435551" };
    private static final String[] MANIFEST_STATUS = new String[] {"In Transit", "In Transit", "Received", "In Transit",
	    "Received", "Open", "In Transit", "Received" };
    private static int ITEM_TAG_ID_COUNT = 0;
    private static final String[] ITEM_TAG_ID = new String[] {"99912341234", "12323453333", "32134441212",
	    "78656557788", "10956227799", "90844321212", "98755230909" };
    private static int MANIFEST_ITEM_IMEI_COUNT = 0;
    private static final String[] MANIFEST_ITEM_IMEI = new String[] {"01242425", "01244425", "01282225", "02242425",
	    "01278322", "03242429", null };
    private static final String[] MANIFEST_ITEM_DESCRIPTION = new String[] {"Sanyo Vero", "Apple iPhone 4S",
	    "LG Marquee", "Samsung Galaxy", "BlackBerry Torch", "Motorola Droid 4", null };
    private static final String[] MANIFEST_ITEM_STATUS = new String[] {"Not Ready For Shipping", "Ready For Shipping",
	    "UPS-Waiting Pick Up", "UPS-Intransit", "UPS-Delivered", "Ready For Shipping", "Received" };
    private static final String[] MANIFEST_ITEM_RETURN_TYPE = new String[] {"Tradein", "Tradein", "BuyBack", "BuyBack",
	    "BuyerRemorse", "BuyerRemorse", "RapidExchange" };
    private static final String[] PROMO_CODE = new String[] {"Freedom", "Freedom", "LifetimeFitness",
	    "LifetimeFitness", "SprintEvo4Gite", "SprintEvo4Gite", "Accessorize" };
    private static final String[] PROMO_DESC = new String[] {"Activate any phone and receive $25 gift card",
	    "Activate a sprint EVO 4 GITE and receive $50 off", "Activate any phone and receive lifetime fitness",
	    "Activate any phone and receive $30 off on any accessory", "Activate any phone and receive $25 gift card",
	    "Activate a sprint EVO 4 GITE and receive $50 off", "Activate a sprint EVO 4 GITE and receive $50 off" };
    private static final String[] PROMO_VALUE = new String[] {"25", null, "", "67", "70", "25", "45" };

    // Triage Data
    private static final String[] TRIAGE_TESTS = new String[] {"TOUCH SCREEN", "MULTI TOUCH SCREEN", "CAMERA FLASH",
	    "PROXIMITY SENSOR", "HEADSET", "HEADSET MIC", "SPEAKER", "MICROPHONE", "GPS", "FRONT CAMERA", "BLUETOOTH",
	    "WIFI", "ACCELEROMETER SENSOR", "MAGNETIC FIELD SENSOR", "BACK CAMERA", "VIBRATION", "TELEPHONY",
	    "GYROSCOPE SENSOR", "NEW TEST", "SOUND", "SILENT" };
    private static final int[] TRIAGE_STATUS = new int[] {0, 0, 0, 1, 1, 2, 2, 2, 3, 3, 1, 2 };
    private static final String[] TRIAGE_ISSUES = new String[] {"Hardware Issue", "Software Issue", "Battery Issue",
	    "Water Damage", "Cracked Screen" };

    // Reward Zone Data
    private static final String[] REWARD_ZONE_NUMBER = new String[] {"44355555", "12323455", "43452345", "90843958",
	    "33053099", "94855775", "40453099" };
    private static final String[] REWARD_ZONE_STATUS = new String[] {"Active", "Inactive", "Active", "Cancelled" };

    // Scoreboard Data

    private static final BigDecimal[] SCOREBOARD_TOTAL = new BigDecimal[] {new BigDecimal(500), new BigDecimal(800),
	    new BigDecimal(2333), new BigDecimal(1202), new BigDecimal(1002), new BigDecimal(50), new BigDecimal(788),
	    null };

    private static final String[] SCOREBOARD_MOBILE_ITEM = new String[] {"Post-Paid Units|iPhone",
	    "Post-Paid Units|New", "No-Contract Units|No Contract < $30", "No-Contract Units|No Contract > $ 100",
	    "Broadband|NC Broadband", "GSBTP|iPhone GSBTP", "MP3|Hardware" };

    private static final String[] SCOREBOARD_COMPUTING_ITEM = new String[] {"Laptops|Laptops < $500",
	    "Laptops|Laptops > $900", "Tablets|Tablets", "Connections|Tablet Activation",
	    "Accessories|All Computing & Tablet Accessories" };

    // Exception Methods

    public static final void throwIseBusinessException(int baseValue) throws IseBusinessException {
	if (dontThrowExceptions){
	    return;
	}
	switch (random.nextInt(baseValue)) {
	    case 0:
	    case 1:
		throw new IseBusinessException("Some business exception");
	}
    }

    public static final void throwServiceException(int baseValue) throws ServiceException {
	if (dontThrowExceptions){
	    return;
	}
	switch (random.nextInt(baseValue)) {
	    case 0:
		throw new ServiceException(IseExceptionCodeEnum.GeneralSystem, "Some service exception");
	    case 1:
		throw new ServiceException(IseExceptionCodeEnum.CapException, "Some service exception");
	}
    }

    public static final int getRandomIndex(int maxValue) {
	return random.nextInt(maxValue);
    }

    public static final Date getDate() {
	return getDate(250);
    }

    public static final Date getDate(int plusMinusNumDays) {
	int plusMinusCurrentDate = getRandomIndex(plusMinusNumDays * 2) - plusMinusNumDays;
	Calendar now = Calendar.getInstance();
	now.add(Calendar.DAY_OF_YEAR, plusMinusCurrentDate);
	return now.getTime();
    }

    public static final String getPhone() {
	return PHONE[getRandomIndex(PHONE.length)];
    }

    public static final boolean getStdEligible() {
	return ELIGIBLE[getRandomIndex(ELIGIBLE.length)];
    }

    public static final boolean getEarlyEligible() {
	return ELIGIBLE[getRandomIndex(ELIGIBLE.length)];
    }

    public static final String getHardware() {
	return HARDWARE[getRandomIndex(HARDWARE.length)];
    }

    public static final String getLineStatus() {
	return LINE_STATUS[getRandomIndex(LINE_STATUS.length)];
    }

    public static final BigDecimal getTradeInPrice() {
	return TRADE_IN[getRandomIndex(TRADE_IN.length)];
    }

    public static final String getOptIn() {
	return OPT_IN[getRandomIndex(OPT_IN.length)];
    }

    public static final boolean getOptInAllowed() {
	return OPT_IN_ALLOWED[getRandomIndex(OPT_IN_ALLOWED.length)];
    }

    public static final String getEligibleType() {
	return ELIGIBLE_TYPE[getRandomIndex(ELIGIBLE_TYPE.length)];
    }

    public static final String getFirstName() {
	return FIRST_NAME[getRandomIndex(FIRST_NAME.length)];
    }

    public static final String getLastName() {
	return LAST_NAME[getRandomIndex(LAST_NAME.length)];
    }

    public static final String getAcctId() {
	return ACCT_ID[getRandomIndex(ACCT_ID.length)];
    }

    public static final String getSku() {
	return SKU[getRandomIndex(SKU.length)];
    }

    public static final String getZipCode() {
	return ZIP_CODE[getRandomIndex(ZIP_CODE.length)];
    }

    public static final String getAddress() {
	return ADDRESS[getRandomIndex(ADDRESS.length)];
    }

    public static final String getCity() {
	return CITY[getRandomIndex(CITY.length)];
    }

    public static final String getState() {
	return STATE[getRandomIndex(STATE.length)];
    }

    public static final String getEmail() {
	return EMAIL[getRandomIndex(EMAIL.length)];
    }

    public static final String getCaAcctId() {
	return CA_ACCT_ID[getRandomIndex(CA_ACCT_ID.length)];
    }

    public static final BigDecimal getPrice() {
	return PRICE[getRandomIndex(PRICE.length)];
    }

    public static final String getNonPhoneDevice() {
	return NONPHONE_DEVICE[getRandomIndex(NONPHONE_DEVICE.length)];
    }

    public static final String getFourPartKey() {
	return FOUR_PART_KEY[getRandomIndex(FOUR_PART_KEY.length)];
    }

    public static final String getTransactionType() {
	return TRANSACTION_TYPE[getRandomIndex(TRANSACTION_TYPE.length)];
    }

    public static final String getPlanNumber() {
	return PLAN_NUMBER[getRandomIndex(PLAN_NUMBER.length)];
    }

    public static final String getPlanStatus() {
	return PLAN_STATUS[getRandomIndex(PLAN_STATUS.length)];
    }

    public static final String getPlanDescription() {
	return PLAN_DESCRIPTION[getRandomIndex(PLAN_DESCRIPTION.length)];
    }

    public static final String getStoreNumber() {
	return STORE_NUMBER[getRandomIndex(STORE_NUMBER.length)];
    }

    public static final String getRegisterNumber() {
	return REGISTER_NUMER[getRandomIndex(REGISTER_NUMER.length)];
    }

    public static final String getModelNumber() {
	return MODEL_NUMBER[getRandomIndex(MODEL_NUMBER.length)];
    }

    public static final String getSerialNumber() {
	return SERIAL_NUMBER[getRandomIndex(SERIAL_NUMBER.length)];
    }

    public static final String getLineType() {
	return LINE_TYPE[getRandomIndex(LINE_TYPE.length)];
    }

    public static final String getPhoneOrNonPhoneDescription() {
	int type = DummyData.getRandomIndex(5);
	if (type < 3){
	    return getHardware();
	}
	return getNonPhoneDevice();
    }

    public static final BigDecimal getBuyBackPrice() {
	return BUY_BACK[getRandomIndex(BUY_BACK.length)];
    }

    public static final String getGspTransType() {
	return GSP_TRANS_TYPE[getRandomIndex(GSP_TRANS_TYPE.length)];
    }

    public static final String getManifestId() {
	MANIFEST_ID_COUNT++;
	return "9999" + Util.pad(MANIFEST_ID_COUNT, 4, "0");
    }

    public static final BigInteger getManifestLineId() {
	MANIFEST_LINE_ID_COUNT++;
	return BigInteger.valueOf(60000 + MANIFEST_LINE_ID_COUNT);
    }

    public static final String getUpsTrackingNumber() {
	UPS_TRACKING_NUMBER_COUNT++;
	return UPS_TRACKING_NUMBER[getRandomIndex(UPS_TRACKING_NUMBER.length)]
		+ Util.pad(UPS_TRACKING_NUMBER_COUNT, 4, "0");
    }

    public static final String getManifestStatus() {
	return MANIFEST_STATUS[getRandomIndex(MANIFEST_STATUS.length)];
    }

    public static final String getNonOpenManifestStatus() {
	String nonOpenStatus;
	do{
	    nonOpenStatus = MANIFEST_STATUS[getRandomIndex(MANIFEST_STATUS.length)];
	}while(nonOpenStatus.equalsIgnoreCase("Open"));
	return nonOpenStatus;
    }

    public static final String getItemTagId() {
	ITEM_TAG_ID_COUNT++;
	return ITEM_TAG_ID[getRandomIndex(ITEM_TAG_ID.length)] + Util.pad(ITEM_TAG_ID_COUNT, 4, "0");
    }

    public static final String getManifestItemImei() {
	MANIFEST_ITEM_IMEI_COUNT++;
	String value = MANIFEST_ITEM_IMEI[getRandomIndex(MANIFEST_ITEM_IMEI.length)];
	if (value == null){
	    return null;
	}
	return value + Util.pad(MANIFEST_ITEM_IMEI_COUNT, 6, "0");
    }

    public static final String getManifestItemDescription() {
	return MANIFEST_ITEM_DESCRIPTION[getRandomIndex(MANIFEST_ITEM_DESCRIPTION.length)];
    }

    public static final String getManifestItemStatus() {
	return MANIFEST_ITEM_STATUS[getRandomIndex(MANIFEST_ITEM_STATUS.length)];
    }

    public static boolean getManifestItemShortStatus(String deviceStatus) {
	if ("Ready For Shipping".equals(deviceStatus)){
	    int type = DummyData.getRandomIndex(7);
	    if (type < 4){
		return true;
	    }
	}
	return false;
    }

    public static final String getManifestItemReturnType() {
	return MANIFEST_ITEM_RETURN_TYPE[getRandomIndex(MANIFEST_ITEM_RETURN_TYPE.length)];
    }

    public static final String getPromoCode() {
	return PROMO_CODE[getRandomIndex(PROMO_CODE.length)];
    }

    public static final String getPromoDesc() {
	return PROMO_DESC[getRandomIndex(PROMO_DESC.length)];
    }

    public static final String getPromoValue() {
	return PROMO_VALUE[getRandomIndex(PROMO_VALUE.length)];
    }

    public static final String[] getTriageTests() {
	return TRIAGE_TESTS;
    }

    public static final int getTriageStatus() {
	return TRIAGE_STATUS[getRandomIndex(TRIAGE_STATUS.length)];
    }

    public static final String[] getTriageIssues() {
	return TRIAGE_ISSUES;
    }

    public static final String getRewardZoneNumber() {
	return REWARD_ZONE_NUMBER[getRandomIndex(REWARD_ZONE_NUMBER.length)];
    }

    public static final String getRewardZoneStatus() {
	return REWARD_ZONE_STATUS[getRandomIndex(REWARD_ZONE_STATUS.length)];
    }

    public static final BigDecimal getScoreboardTotal() {
	return SCOREBOARD_TOTAL[getRandomIndex(SCOREBOARD_TOTAL.length)];
    }

    public static final String[] getMobileScoreboardItem() {
	String buf = SCOREBOARD_MOBILE_ITEM[getRandomIndex(SCOREBOARD_MOBILE_ITEM.length)];
	return buf.split("[|]");
    }

    public static final String[] getComputingScoreboardItem() {
	String buf = SCOREBOARD_COMPUTING_ITEM[getRandomIndex(SCOREBOARD_COMPUTING_ITEM.length)];
	return buf.split("[|]");
    }

    public static final void sleep(int seconds) {
	try{
	    Thread.sleep(seconds * 1000L);
	}catch(InterruptedException e){
	}
    }

}
