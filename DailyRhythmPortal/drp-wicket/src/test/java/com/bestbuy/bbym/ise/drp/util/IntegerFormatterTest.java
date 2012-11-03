package com.bestbuy.bbym.ise.drp.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * JUnit test for {@link IntegerFormatter};
 */
public class IntegerFormatterTest {

    @Test
    public void testFormat() {
	IntegerFormatter<Integer> formatter = new IntegerFormatter<Integer>();
	assertEquals("Incorrect format", "123,456", formatter.format(Integer.valueOf(123456)));
    }
}
