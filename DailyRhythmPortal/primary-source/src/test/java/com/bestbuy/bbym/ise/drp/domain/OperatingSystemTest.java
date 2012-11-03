package com.bestbuy.bbym.ise.drp.domain;

import org.junit.Assert;
import org.junit.Test;

/**
 * JUnit test for {@link OperatingSystem}.
 */
public class OperatingSystemTest {

    @Test
    public void testEqualsFalse() throws Exception {
	OperatingSystem os1 = new OperatingSystem();
	os1.setId(1L);
	os1.setOs("os");
	OperatingSystem os2 = new OperatingSystem();
	os2.setId(2L);
	os2.setOs("os");
	Assert.assertFalse(os1.equals(os2));
    }
}
