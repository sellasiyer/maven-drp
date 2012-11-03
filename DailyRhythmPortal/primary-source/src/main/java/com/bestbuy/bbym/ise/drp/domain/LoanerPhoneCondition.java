package com.bestbuy.bbym.ise.drp.domain;

public enum LoanerPhoneCondition {

    GOOD("Good"), FAIR("Fair"), POOR("Poor"), LOST("Lost"), DAMAGED("Damaged");

    private final String label;

    private LoanerPhoneCondition(String label) {
	this.label = label;
    }

    @Override
    public String toString() {
	return label;
    }

}
