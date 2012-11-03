package com.bestbuy.bbym.ise.drp.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.Locale;

import org.apache.wicket.util.convert.ConversionException;
import org.junit.Before;
import org.junit.Test;

import com.bestbuy.bbym.ise.drp.common.PhoneNumber;

/**
 * JUnit test for {@link PhoneNumberConverter}.
 */
public class PhoneNumberConverterTest {

    private PhoneNumberConverter pnc;

    @Before
    public void init() {
	pnc = new PhoneNumberConverter();
    }

    @Test
    public void convertToPhone() {
	PhoneNumber pn = new PhoneNumber("1234567890");

	// 10 digit valid US Phone numbers
	String[] phoneNums = {"1234567890", "(123)456-7890", "(123) 456-7890", "123 456 7890", "123 456-7890",
		"   123 456 - 7890   ", " ( 1 2 3 ) 4 5 6 - 7 8 9 0", "  1 2 3  4 5 6 7 8 9 0" };
	for(String num: phoneNums){
	    assertEquals(pn, pnc.convertToObject(num, Locale.getDefault()));
	}

	assertNull(pnc.convertToObject(null, Locale.getDefault()));
    }

    @Test(expected = ConversionException.class)
    public void convertEmptyString() {
	pnc.convertToObject("", null);
    }

    @Test
    public void convertFromPhone() {
	assertEquals("(123)456-7890", pnc.convertToString(1234567890L, Locale.getDefault()));

	PhoneNumber pn = new PhoneNumber("1234567890");
	assertEquals("(123)456-7890", pnc.convertToString(pn, Locale.getDefault()));
    }

}
