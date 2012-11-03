package com.bestbuy.bbym.ise.util;

/**
 * 
 * @author a904002
 */
public enum WorkFlowTypeEnum {
    BEAST_TRANSFER, TRADE_IN;

    public static WorkFlowTypeEnum fromValue(String v) {
	return valueOf(v);
    }
}
