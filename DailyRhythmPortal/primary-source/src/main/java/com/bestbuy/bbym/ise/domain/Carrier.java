package com.bestbuy.bbym.ise.domain;

/**
 * @author a186288
 */
public enum Carrier {

    /**
     * AT&amp;T.
     */
    ATT("AT&T", "ATT", false),
    /**
     * Sprint.
     */
    SPRINT("Sprint", "SPR", false),
    /**
     * T-Mobile.
     */
    TMOBILE("T-Mobile", "TMO", false),
    /**
     * Verizon.
     */
    VERIZON("Verizon", "VEZ", false),
    /**
     * Virgin Mobile.
     */
    VIRGIN_MOBILE("Virgin Mobile", "VRM", true);

    private final String label;
    private final String shortLabel;
    private final boolean loanerPhoneOnly;

    private Carrier(String label, String shortLabel, boolean loanerPhoneOnly) {
	this.label = label;
	this.shortLabel = shortLabel;
	this.loanerPhoneOnly = loanerPhoneOnly;
    }

    public String getLabel() {
	return label;
    }

    @Override
    public String toString() {
	return label;
    }

    public String getShortLabel() {
	return shortLabel;
    }

    public boolean isLoanerPhoneOnly() {
	return loanerPhoneOnly;
    }

    public static Carrier fromLabel(String label) {
	for(Carrier carrier: values()){
	    if (carrier.label.equals(label)){
		return carrier;
	    }
	}
	return null;
    }

    public static Carrier fromShortLabel(String shortLabel) {
	for(Carrier carrier: values()){
	    if (carrier.shortLabel.equals(shortLabel)){
		return carrier;
	    }
	}
	return null;
    }

    /**
     * Gets the CSS style class.
     */
    public String getStyleClass() {
	if (this == VIRGIN_MOBILE){
	    return getShortLabel().toLowerCase();
	}
	return name().toLowerCase().replaceAll("[&-]", "");
    }
}
