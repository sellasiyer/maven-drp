package com.bestbuy.bbym.ise.drp.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * JUnit test for {@link TruncateFormatter}.
 */
public class TruncateFormatterTest {

    @Test
    public void testFormat() {
	TruncateFormatter<String> tf = new TruncateFormatter<String>(6);
	assertEquals("A...GH", tf.format("ABCDEFGH"));
	tf = new TruncateFormatter<String>(8);
	assertEquals("AB...HIJ", tf.format("ABCDEFGHIJ"));
    }

}
