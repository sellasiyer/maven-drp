package com.bestbuy.bbym.ise.drp.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.math.BigDecimal;
import java.util.Locale;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * JUnit test for {@link MoneyConverter}.
 */
@Ignore
public class MoneyConverterTest {

    private MoneyConverter mc;

    @Before
    public void init() {
	mc = new MoneyConverter();
    }

    @Test
    public void convertToMoney() {
	String[] amounts = {"$12.34", " $12.34 ", "12.34", " 12.34 ", };
	for(String amount: amounts){
	    assertEquals(new BigDecimal("12.34"), mc.convertToObject(amount, Locale.getDefault()));
	}

	String[] negAmounts = {"($12.34)", "-12.34", " -12.34 " };
	for(String amount: negAmounts){
	    assertEquals(new BigDecimal("-12.34"), mc.convertToObject(amount, Locale.getDefault()));
	}

	String[] euroAmounts = {"12,34 €", " 12,34 € ", "12,34", " 12,34 " };
	for(String amount: euroAmounts){
	    assertEquals(new BigDecimal("12.34"), mc.convertToObject(amount, Locale.GERMANY));
	}

	String[] negEuroAmounts = {"-12,34 €", "-12,34", " -12,34 " };
	for(String amount: negEuroAmounts){
	    assertEquals(new BigDecimal("-12.34"), mc.convertToObject(amount, Locale.GERMANY));
	}

	assertEquals(new BigDecimal("1234567890"), mc.convertToObject("1234567890", Locale.getDefault()));
	assertNull(mc.convertToObject(null, null));
    }

    @Test
    public void convertFromMoney() {
	assertEquals("$12.34", mc.convertToString(new BigDecimal("12.34"), Locale.getDefault()));
	assertEquals("$1,234,567.89", mc.convertToString(new BigDecimal("1234567.89"), Locale.getDefault()));
	assertEquals("$1,234,567.89", mc.convertToString(new BigDecimal("1234567.8934"), Locale.getDefault()));
	assertEquals("$1,234,567.00", mc.convertToString(new BigDecimal("1234567"), Locale.getDefault()));
	assertEquals("($12.34)", mc.convertToString(new BigDecimal("-12.34"), Locale.getDefault()));
	assertEquals("($1,234,567.89)", mc.convertToString(new BigDecimal("-1234567.89"), Locale.getDefault()));

	assertEquals("12,34 €", mc.convertToString(new BigDecimal("12.34"), Locale.GERMANY));
	assertEquals("1.234.567,89 €", mc.convertToString(new BigDecimal("1234567.89"), Locale.GERMANY));
	assertEquals("1.234.567,89 €", mc.convertToString(new BigDecimal("1234567.8934"), Locale.GERMANY));
	assertEquals("1.234.567,00 €", mc.convertToString(new BigDecimal("1234567"), Locale.GERMANY));
	assertEquals("-12,34 €", mc.convertToString(new BigDecimal("-12.34"), Locale.GERMANY));
	assertEquals("-1.234.567,89 €", mc.convertToString(new BigDecimal("-1234567.89"), Locale.GERMANY));
    }

}
