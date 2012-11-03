package com.bestbuy.bbym.ise.drp.utils;

/**
 * @author a218635
 */
public interface DrpConstants {

    public static final String APPLICATION_ID = "ISE";

    public static final String DRP_ADMIN = "ADMIN";
    public static final String DRP_MANAGER = "DRP_MANAGER";
    public static final String DRP_LEAD = "DRP_LEAD";
    public static final String DRP_USER = "DRP_USER";
    public static final String SHP_USER = "SHIPPING_USER";
    public static final String SHP_MANAGER = "SHIPPING_MANAGER";
    public static final String DRP_TEAM = "TEAM";
    public static final String DRP_BEAST = "BEAST";
    /**
     * Unfortunately we need this because of our page hierarchy, otherwise
     * unauthorized users can't see the {@link AccessDeniedPage}.
     */
    public static final String DRP_UNAUTHORIZED = "UNAUTHORIZED";

    public static final String TABLET = "Tablet";

    public static final String INITIAL_TRIAGE_SCREEN_CODE = "ISSUE_TYPE";

    public static final String DATE_FORMAT = "MM/dd/yyyy";

    // PSP/GSP
    public static final String[] PSP_TRANSACTION_TYPE_VALUES = new String[] {"PVS", "Post Void Sale", "PVR",
	    "Post Void Return", "R", "Return", "S", "Sale" };

    // query parameters
    /**
     * @deprecated replaced by {@link PageParameterKeys}.
     */
    @Deprecated
    public static final String USER_ID = "userId";
    /**
     * @deprecated replaced by {@link PageParameterKeys}.
     */
    @Deprecated
    public static final String DATA = "data";

    /**
     * @deprecated replaced by {@link PageParameterKeys}.
     */
    @Deprecated
    public static final String STORE_ID = "storeId";
    public static final String LOG4J_FROM_DB = "LOG4J_FROM_DB";

    public static final String EXTERNAL_SERVICE_CALL_ERROR = "EXTERNAL SERVICE CALL ERROR - ";
    public static final String TIME_PROFILER = "TIME PROFILER - ";
    public static final String DAO_CLASS_ERRORS = "DAO CLASS ERRORS - ";
    public static final String SERVICE_CLASS_ERRORS = "SERIVCE CLASS ERRORS - ";
    public static final String DFLT_SESSION_TIMEOUT = "DFLT_SESSION_TIMEOUT";
    public static final String DFLT_SESSION_TIMEOUT_WARNING = "DFLT_SESSION_TIMEOUT_WARNING";
    public static final String GSP_CANCEL_BOOKMARK_VALUE = "gspCancel";
    public static final String SEND_CAP_TRANS_ID = "SEND_CAP_TRANS_ID";

    public static final String TSH_RESULTSET_NAME = "TradeIn";
    public static final String TRANSACTION_ID = "TransactionID";
    public static final String TRADABILITY_APPROVED = "SUCCESS";

    public static final String BBYOPEN_API_KEY = "BBYOPEN_API_KEY";
    public static final String BBYOPEN_URL = "BBYOPEN_URL";

    public static final String GEEKSQUAD_ARMORY_URL = "GEEKSQUAD_ARMORY_URL";
    public static final String GEEKSQUAD_ARMORY_SEARCH_SUFFIX = "GEEKSQUAD_ARMORY_SEARCH_SUFFIX";

    public static final String YGIB_PROXY_FLAG = "YGIB_PROXY_FLAG";

    public static final String GSP_BY_IMEI_FLAG = "GSP_BY_IMEI_FLAG";

    public static final String PROMO_REGISTRATION_ENABLED = "PROMO_REGISTRATION_ENABLED";

}
