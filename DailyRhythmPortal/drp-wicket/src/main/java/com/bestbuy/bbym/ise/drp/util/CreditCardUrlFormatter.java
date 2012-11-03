package com.bestbuy.bbym.ise.drp.util;

public class CreditCardUrlFormatter {

    //put this in a property
    static final String baseURL = "https://monthlybilling.geeksquad.com/?";

    public static String formatURL(String firstName, String lastName) {
	String newUrl = baseURL;
	if (firstName != null){
	    newUrl += "first=" + firstName;
	}

	if (lastName != null){
	    newUrl += "&last=" + lastName;
	}

	return newUrl;
    }

    public static String formatURL(String firstName, String lastName, String pspNumber) {
	String newUrl = formatURL(firstName, lastName);

	newUrl += "&psp=" + pspNumber;

	return newUrl;
    }
}
