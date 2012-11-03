package com.bestbuy.bbym.ise.drp.domain;

public enum EntitlementPurchaseType {

    STORE("Store Purchase"), ONLINE("Online Purchase");

    private final String label;

    private EntitlementPurchaseType(String label) {
	this.label = label;
    }

    @Override
    public String toString() {
	return label;
    }

}
