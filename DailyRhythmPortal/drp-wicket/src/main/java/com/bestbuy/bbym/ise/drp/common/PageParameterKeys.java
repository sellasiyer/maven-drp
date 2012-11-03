package com.bestbuy.bbym.ise.drp.common;

/**
 * @author a186288
 */
public enum PageParameterKeys {

    WORKFLOW_ID(null), WORKFLOW_NAME(null), EXCEPTION_CODE(null), EXCEPTION_DESCRIPTION(null), DATA_SHARING_KEY("dsk"),
    STORE_ID("storeId"), USER_ID("userId"), DATA("data"), GET_GSP_PLANS("getGspPlans"), NEW_CUSTOMER_SEARCH(
	    "newCustSearch"), EXIT_STATUS("exitStatus"), CLIENT_TIME("clientTime"),
    SCOREBOARD_OPERATION("scoreboardOp"), STORE_TYPE("storeType"),
    /**
     * Flag passed by ETK when navigating to BEAST Portal via the ETK widget.
     */
    ETK("etk"), CARRIER("carrier"), LAST_FOUR_SSN("lastFourSsn"), PIN("pin"), PHONE_NUMBER("phoneNumber"), ZIP_CODE(
	    "zipCode"), NO_SSN("noSsn"), SEARCH_DESCRIPTION("SEARCH_DESCRIPTION");

    private String urlParameterKey;

    private PageParameterKeys(String urlParameterKey) {
	this.urlParameterKey = urlParameterKey;
    }

    /**
     * Gets the parameter key as it appears in the URL.
     */
    public String getUrlParameterKey() {
	return urlParameterKey;
    }

}
