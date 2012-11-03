package com.bestbuy.bbym.ise.drp.util;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

import org.junit.Ignore;
import org.junit.Test;

import com.bestbuy.bbym.ise.drp.domain.Carrier;

/**
 * JUnit test for {@link StringModel}.
 * @author a885527
 */
public class StringModelTest {

    private StringModel stringModel;

    private String testString;

    @Test
    public void testPropertyConstructor() {
	this.testString = "gotigers";

	stringModel = new StringModel(this, "testString", "asdf");
	String str = stringModel.getObject();

	assertEquals(str, testString);
    }

    @Test
    public void testStringConstructor() {

	final String sourceString = "golions";
	stringModel = new StringModel(sourceString, "");
	assertEquals(sourceString, stringModel.getObject());
    }

    @Test
    public void testGetObject() {
	stringModel = new StringModel(null, "notnull");

	//test that it doesn't ever return null.
	assertNotNull(stringModel.getObject());
	//test that it returns the expected value.
	assertEquals(stringModel.getObject(), "notnull");

	//test that it can return null when we specify null return value.
	stringModel = new StringModel(null, null);
	assertNull(stringModel.getObject());
    }

}
