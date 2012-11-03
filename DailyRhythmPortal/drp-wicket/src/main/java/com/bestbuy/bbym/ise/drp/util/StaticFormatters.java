package com.bestbuy.bbym.ise.drp.util;

import java.util.HashMap;
import java.util.Map;

import com.bestbuy.bbym.ise.drp.utils.DrpConstants;

public class StaticFormatters {

    private final static Map<String, String> transactionTypeMap = new HashMap<String, String>();
    static{
	for(int i = 0; i < DrpConstants.PSP_TRANSACTION_TYPE_VALUES.length; i = i + 2){
	    transactionTypeMap.put(DrpConstants.PSP_TRANSACTION_TYPE_VALUES[i],
		    DrpConstants.PSP_TRANSACTION_TYPE_VALUES[i + 1]);
	}
    }
    public static StringMapFormatter GSP_TRANSACTION_TYPE = new StringMapFormatter(transactionTypeMap);

    private StaticFormatters() {
	// This class is not meant to be instantiated
    }

}
