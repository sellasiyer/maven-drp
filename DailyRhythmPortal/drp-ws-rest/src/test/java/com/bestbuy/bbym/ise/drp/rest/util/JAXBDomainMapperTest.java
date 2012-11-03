package com.bestbuy.bbym.ise.drp.rest.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Ignore;
import org.junit.Test;

import com.bestbuy.bbym.beast.portal.cancelgsp.response.GspPlanType;
import com.bestbuy.bbym.beast.portal.customerinfo.response.BbyAccountType;
import com.bestbuy.bbym.beast.portal.customerinfo.response.CarrierAccountType;
import com.bestbuy.bbym.beast.portal.datasummary.response.DataTransferSummaryType;
import com.bestbuy.bbym.beast.portal.gspcancel.write.request.CustomerRequest;
import com.bestbuy.bbym.ise.common.AddressType;
import com.bestbuy.bbym.ise.common.RecommendationType;
import com.bestbuy.bbym.ise.drp.domain.Address;
import com.bestbuy.bbym.ise.drp.domain.BbyAccount;
import com.bestbuy.bbym.ise.drp.domain.CarrierAccount;
import com.bestbuy.bbym.ise.drp.domain.Customer;
import com.bestbuy.bbym.ise.drp.domain.DataTransferSummary;
import com.bestbuy.bbym.ise.drp.domain.GSPPlan;
import com.bestbuy.bbym.ise.drp.domain.RecSheetSummary;

/**
 *
 * @author a904002
 */
@Ignore
public class JAXBDomainMapperTest {

    /**
     * Test of getDataTransferType method, of class JAXBDomainMapper.
     */
    @Test
    public void testGetDataTransferType() {
	System.out.println("getDataTransferType");
	DataTransferSummary dataTransferSummary = null;
	DataTransferSummaryType expResult = null;
	DataTransferSummaryType result = JAXBDomainMapper.getDataTransferType(dataTransferSummary);
	assertEquals(expResult, result);
	// TODO review the generated test code and remove the default call to fail.
	fail("The test case is a prototype.");
    }

    /**
     * Test of getCustomer method, of class JAXBDomainMapper.
     */
    @Test
    public void testGetCustomer() throws Exception {
	System.out.println("getCustomer");
	Customer dCustomer = null;
	com.bestbuy.bbym.beast.portal.customerinfo.response.Customer expResult = null;
	com.bestbuy.bbym.beast.portal.customerinfo.response.Customer result = JAXBDomainMapper.getCustomer(dCustomer);
	assertEquals(expResult, result);
	// TODO review the generated test code and remove the default call to fail.
	fail("The test case is a prototype.");
    }

    /**
     * Test of getRestBbyAccount method, of class JAXBDomainMapper.
     */
    @Test
    public void testGetRestBbyAccount() {
	System.out.println("getRestBbyAccount");
	BbyAccount bbyAccout = null;
	BbyAccountType expResult = null;
	BbyAccountType result = JAXBDomainMapper.getRestBbyAccount(bbyAccout);
	assertEquals(expResult, result);
	// TODO review the generated test code and remove the default call to fail.
	fail("The test case is a prototype.");
    }

    /**
     * Test of getRestCarrierAccount method, of class JAXBDomainMapper.
     */
    @Test
    public void testGetRestCarrierAccount() {
	System.out.println("getRestCarrierAccount");
	CarrierAccount carrierAccount = null;
	CarrierAccountType expResult = null;
	CarrierAccountType result = JAXBDomainMapper.getRestCarrierAccount(carrierAccount);
	assertEquals(expResult, result);
	// TODO review the generated test code and remove the default call to fail.
	fail("The test case is a prototype.");
    }

    /**
     * Test of getRestAddressType method, of class JAXBDomainMapper.
     */
    @Test
    public void testGetRestAddressType() {
	System.out.println("getRestAddressType");
	Address address = null;
	AddressType expResult = null;
	AddressType result = JAXBDomainMapper.getRestAddressType(address);
	assertEquals(expResult, result);
	// TODO review the generated test code and remove the default call to fail.
	fail("The test case is a prototype.");
    }

    /**
     * Test of getRestRecSheetSummary method, of class JAXBDomainMapper.
     */
    @Test
    public void testGetRestRecSheetSummary() {
	System.out.println("getRestRecSheetSummary");
	RecSheetSummary recSheetSummary = null;
	RecommendationType expResult = null;
	RecommendationType result = JAXBDomainMapper.getRestRecSheetSummary(recSheetSummary);
	assertEquals(expResult, result);
	// TODO review the generated test code and remove the default call to fail.
	fail("The test case is a prototype.");
    }

    /**
     * Test of getGspPlanType method, of class JAXBDomainMapper.
     */
    @Test
    public void testGetGspPlanType() throws Exception {
	System.out.println("getGspPlanType");
	GSPPlan gspPlan = null;
	GspPlanType expResult = null;
	GspPlanType result = JAXBDomainMapper.getGspPlanType(gspPlan);
	assertEquals(expResult, result);
	// TODO review the generated test code and remove the default call to fail.
	fail("The test case is a prototype.");
    }

    /**
     * Test of getAddressFromRestAddressType method, of class JAXBDomainMapper.
     */
    @Test
    public void testGetAddressFromRestAddressType() {
	System.out.println("getAddressFromRestAddressType");
	AddressType adType = null;
	String userId = "";
	Address expResult = null;
	Address result = JAXBDomainMapper.getAddressFromRestAddressType(adType, userId);
	assertEquals(expResult, result);
	// TODO review the generated test code and remove the default call to fail.
	fail("The test case is a prototype.");
    }

    /**
     * Test of getBbyAccount method, of class JAXBDomainMapper.
     */
    @Test
    public void testGetBbyAccount() {
	System.out.println("getBbyAccount");
	CustomerRequest account = null;
	String storeId = "";
	String userId = "";
	BbyAccount expResult = null;
	BbyAccount result = JAXBDomainMapper.getBbyAccount(account, storeId, userId);
	assertEquals(expResult, result);
	// TODO review the generated test code and remove the default call to fail.
	fail("The test case is a prototype.");
    }

}
