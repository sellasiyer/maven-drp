package com.bestbuy.bbym.ise.domain;

public enum States {

    ALABAMA("Alabama", "AL"), ALASKA("Alaska", "AK"), ARIZONA("Arizona", "AZ"), ARKANSAS("Arkansas", "AR"), CALIFORNIA(
	    "California", "CA"), COLORADO("Colorado", "CO"), DELAWARE("Delaware", "DE"), FLORIDA("Florida", "FL"),
    GEORGIA("Georgia", "GA"), HAWAII("Hawaii", "HI"), IDAHO("Idaho", "ID"), ILLINOIS("Illinois", "IL"), INDIANA(
	    "Indiana", "IN"), IOWA("Iowa", "IA"), KANSAS("Kansas", "KS"), KENTUCKY("Kentucky", "KY"), LOUISIANA(
	    "Louisiana", "LA"), MAINE("Maine", "ME"), MARYLAND("Maryland", "MD"), MASSACHUSETTS("Massachusetts", "MA"),
    MICHIGAN("Michigan", "MI"), MINNESOTA("Minnesota", "MN"), MISSISSIPPI("Mississippi", "MS"), MISSOURI("Missouri",
	    "MO"), MONTANA("Montana", "MT"), NEBRASKA("Nebraska", "NE"), NEVADA("Nevada", "NV"), NEW_HAMPSHIRE(
	    "New Hampshire", "NH"), NEW_JERSEY("New Jersey", "NJ"), NEW_MEXICO("New Mexico", "NM"), NEW_YORK(
	    "New York", "NY"), NORTH_CAROLINA("North Carolina", "NC"), NORTH_DAKOTA("North Dakota", "ND"), OHIO("Ohio",
	    "OH"), OKLAHOMA("Oklahoma", "OK"), OREGON("Oregon", "OR"), PENNSYLVANIA("Pennsylvania", "PA"),
    RHODE_ISLAND("Rhode Island", "RI"), SOUTH_CAROLINA("South Carolina", "SC"), SOUTH_DAKOTA("South Dakota", "SD"),
    TENNESSEE("Tennessee", "TN"), TEXAS("Texas", "TX"), UTAH("Utah", "UT"), VERMONT("Vermont", "VT"), VIRGINIA(
	    "Virginia", "VA"), WASHINGTON("Washington", "WA"), WEST_VIRGINIA("West Virginia", "WV"), WISCONSIN(
	    "Wisconsin", "WI"), WYOMING("Wyoming", "WY");

    private final String label;
    private final String shortLabel;

    private States(String label, String shortLabel) {
	this.label = label;
	this.shortLabel = shortLabel;

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

    public static States fromLabel(String label) {
	for(States state: values()){
	    if (state.label.equals(label)){
		return state;
	    }
	}
	return null;
    }

    public static States fromShortLabel(String shortLabel) {
	for(States state: values()){
	    if (state.shortLabel.equals(shortLabel)){
		return state;
	    }
	}
	return null;
    }

}
