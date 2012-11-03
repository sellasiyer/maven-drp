package com.bestbuy.bbym.ise.drp.rest.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import com.bestbuy.bbym.beast.portal.tradein.reqres.TradeIn;
import com.bestbuy.bbym.beast.portal.tradeindevicedetails.request.TradeInDeviceDetails;
import com.bestbuy.bbym.ise.drp.domain.TitanDevice;

/**
 * JUnit test for {@link TradeInJAXBDomainMapper}.
 * 
 * @author a904002
 */
@Ignore
public class TradeInJAXBDomainMapperTest {

    /**
     * Test of getTradeInDeviceDetails method, of class TradeInJAXBDomainMapper.
     */
    @Test
    public void testGetTradeInDeviceDetails() {
	System.out.println("getTradeInDeviceDetails");
	TradeInDeviceDetails tradeInDeviceDetails = null;
	List expResult = null;
	List result = TradeInJAXBDomainMapper.getTradeInDeviceDetails(tradeInDeviceDetails);
	assertEquals(expResult, result);
	// TODO review the generated test code and remove the default call to fail.
	fail("The test case is a prototype.");
    }

    /**
     * Test of createTitanDevice method, of class TradeInJAXBDomainMapper.
     */
    @Test
    public void testCreateTitanDevice() {
	System.out.println("createTitanDevice");
	TradeIn tradeIn = null;
	TitanDevice expResult = null;
	TitanDevice result = TradeInJAXBDomainMapper.createTitanDevice(tradeIn);
	assertEquals(expResult, result);
	// TODO review the generated test code and remove the default call to fail.
	fail("The test case is a prototype.");
    }

    /**
     * Test of getTradeIn method, of class TradeInJAXBDomainMapper.
     */
    @Test
    public void testGetTradeIn() throws Exception {
	System.out.println("getTradeIn");
	TitanDevice titanDevice = null;
	TradeIn expResult = null;
	TradeIn result = TradeInJAXBDomainMapper.getTradeIn(titanDevice);
	assertEquals(expResult, result);
	// TODO review the generated test code and remove the default call to fail.
	fail("The test case is a prototype.");
    }
}
