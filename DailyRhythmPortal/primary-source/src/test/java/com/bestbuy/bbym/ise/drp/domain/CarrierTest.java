package com.bestbuy.bbym.ise.drp.domain;

import org.junit.Assert;
import org.junit.Test;

/**
 * JUnit test for {@link Carrier}.
 */
public class CarrierTest {

    @Test
    public void testEqualsFalse() throws Exception {
	Carrier carrier1 = new Carrier();
	carrier1.setId(1L);
	carrier1.setCarrier("carrier");
	carrier1.setCarrierLoanerSku("carrierLoanerSku");
	Carrier carrier2 = new Carrier();
	carrier2.setId(2L);
	carrier2.setCarrier("carrier");
	carrier2.setCarrierLoanerSku("carrierLoanerSku");
	Assert.assertFalse(carrier1.equals(carrier2));
    }
}
