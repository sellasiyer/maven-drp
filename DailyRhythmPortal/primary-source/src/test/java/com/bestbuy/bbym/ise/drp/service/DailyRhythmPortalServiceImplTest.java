package com.bestbuy.bbym.ise.drp.service;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import com.bestbuy.bbym.ise.domain.Carrier;

/**
 * JUuit test for {@link DailyRhythmPortalServiceImpl}.
 */
public class DailyRhythmPortalServiceImplTest {

    private DailyRhythmPortalServiceImpl dailyRhythmPortalServiceImpl = new DailyRhythmPortalServiceImpl();

    /**
     * Test for {@link DailyRhythmPortalServiceImpl#getSupportedCarriers()}.
     */
    @Test
    public void testGetSupportedCarriers() {

	List<Carrier> carriers = dailyRhythmPortalServiceImpl.getSupportedCarriers();

	for(Carrier carrier: Carrier.values()){
	    if (carrier.isLoanerPhoneOnly()){
		assertTrue("Loaner phone only carrier should not be supported", !carriers.contains(carrier));
	    }else{
		assertTrue("Non-Loaner phone only carrier should be supported", carriers.contains(carrier));
	    }
	}
    }
}
