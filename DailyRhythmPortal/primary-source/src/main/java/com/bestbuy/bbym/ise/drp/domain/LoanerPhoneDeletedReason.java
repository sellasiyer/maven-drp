package com.bestbuy.bbym.ise.drp.domain;

public enum LoanerPhoneDeletedReason {

    LOST("Lost"), DAMAGED("Damaged"), OTHER("Other");

    private final String label;

    private LoanerPhoneDeletedReason(String label) {
	this.label = label;
    }

    @Override
    public String toString() {
	return label;
    }

}
