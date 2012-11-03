package com.bestbuy.bbym.ise.drp.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {

    private Validation() {
    }

    public final static int PHONE_NUMBER_SIZE = 10;
    public final static int FORMAT_PHONE_NUMBER_SIZE = 13;
    public final static int ZIP_CODE_SIZE = 5;
    public final static int SSN_SIZE = 4;
    public final static int CARRIER_PIN_SIZE = 5;
    public final static int FIRST_NAME_MAX_SIZE = 25;
    public final static int LAST_NAME_MAX_SIZE = 25;
    public final static int FIRST_NAME_MIN_SIZE = 2;
    public final static int LAST_NAME_MIN_SIZE = 2;

    public final static int STORE_NUM_MIN = 3;
    public final static int STORE_NUM_MAX = 4;

    public final static int REGISTER_MIN = 2;
    public final static int REGISTER_MAX = 3;

    public final static int TRANSACTION_NUM_MIN = 2;
    public final static int TRANSACTION_NUM_MAX = 4;

    public final static int ORDER_NUM = 18;

    public final static int PROD_SKU_MIN = 7;
    public final static int PROD_SKU_MAX = 12;

    public static boolean passesSizedNumberFieldValidation(int size, String value) {
	if (value == null){
	    return false;
	}
	Pattern p = Pattern.compile("\\d{" + size + "}");
	Matcher m = p.matcher(value);
	return m.matches();
    }

}
