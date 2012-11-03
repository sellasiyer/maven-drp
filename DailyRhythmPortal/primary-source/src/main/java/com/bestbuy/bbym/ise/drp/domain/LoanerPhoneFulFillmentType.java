package com.bestbuy.bbym.ise.drp.domain;

public enum LoanerPhoneFulFillmentType {

    RAPID_EXCHANGE_GSP("Rapid Exchange GSP"), GSP_REPAIR("GSP Repair"), FACTORY_WARRANTY_REPAIR(
	    "Factory Warranty Repair"), LOANER_PHONE_ONLY("Loaner Phone Only");

    private final String label;

    private LoanerPhoneFulFillmentType(String label) {
	this.label = label;
    }

    @Override
    public String toString() {
	return label;
    }

}
