package com.bestbuy.bbym.ise.drp.rest.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Ignore;
import org.junit.Test;

import com.bestbuy.bbym.beast.portal.customerinfo.response.Customer;
import com.bestbuy.bbym.beast.portal.datasummary.response.DataTransferSummaryList;
import com.bestbuy.bbym.beast.portal.gspcancel.write.response.DskResponse;
import com.bestbuy.bbym.beast.portal.retexch.getmobdev.request.GetMobileDeviceRequest;
import com.bestbuy.bbym.beast.portal.retexch.getmobdev.response.GetMobileDeviceResponse;
import com.bestbuy.bbym.beast.portal.tradein.reqres.TradeIn;
import com.bestbuy.bbym.beast.portal.tradeindevicedetails.request.TradeInDeviceDetails;

/**
 * JUnit test for {@link DSLServiceImpl}.
 */
@Ignore
public class DSLServiceImplTest {

    /**
     * Test of getDataTransferSummaryList method, of class DSLServiceImpl.
     */
    @Test
    public void testGetDataTransferSummaryList() throws Exception {
	System.out.println("getDataTransferSummaryList");
	String storeId = "";
	DSLServiceImpl instance = new DSLServiceImpl();
	DataTransferSummaryList expResult = null;
	DataTransferSummaryList result = instance.getDataTransferSummaryList(storeId);
	assertEquals(expResult, result);
	// TODO review the generated test code and remove the default call to fail.
	fail("The test case is a prototype.");
    }

    /**
     * Test of getCustomerDetail method, of class DSLServiceImpl.
     */
    @Test
    public void testGetCustomerDetail() throws Exception {
	System.out.println("getCustomerDetail");
	String dataSharingKey = "";
	String storeId = "";
	DSLServiceImpl instance = new DSLServiceImpl();
	Customer expResult = null;
	Customer result = instance.getCustomerDetail(dataSharingKey, storeId);
	assertEquals(expResult, result);
	// TODO review the generated test code and remove the default call to fail.
	fail("The test case is a prototype.");
    }

    /**
     * Test of writeDeviceDetails method, of class DSLServiceImpl.
     */
    @Test
    public void testWriteDeviceDetails() {
	System.out.println("writeDeviceDetails");
	TradeInDeviceDetails tradeInDeviceDetails = null;
	DSLServiceImpl instance = new DSLServiceImpl();
	DskResponse expResult = null;
	DskResponse result = instance.writeDeviceDetails(tradeInDeviceDetails);
	assertEquals(expResult, result);
	// TODO review the generated test code and remove the default call to fail.
	fail("The test case is a prototype.");
    }

    /**
     * Test of getTradeInRecord method, of class DSLServiceImpl.
     */
    @Test
    public void testGetTradeInRecord() {
	System.out.println("getTradeInRecord");
	String dataSharingKey = "";
	DSLServiceImpl instance = new DSLServiceImpl();
	TradeIn expResult = null;
	TradeIn result = instance.getTradeInRecord(dataSharingKey);
	assertEquals(expResult, result);
	// TODO review the generated test code and remove the default call to fail.
	fail("The test case is a prototype.");
    }

    /**
     * Test of writeTradeInRecord method, of class DSLServiceImpl.
     */
    @Test
    public void testWriteTradeInRecord() throws Exception {
	System.out.println("writeTradeInRecord");
	TradeIn tradeIn = null;
	DSLServiceImpl instance = new DSLServiceImpl();
	DskResponse expResult = null;
	DskResponse result = instance.writeTradeInRecord(tradeIn);
	assertEquals(expResult, result);
	// TODO review the generated test code and remove the default call to fail.
	fail("The test case is a prototype.");
    }

    /**
     * Test of getMobileDeviceData method, of class DSLServiceImpl.
     */
    @Test
    public void testGetMobileDeviceData() throws Exception {
	System.out.println("getMobileDeviceData");
	GetMobileDeviceRequest getMobileDeviceRequest = null;
	DSLServiceImpl instance = new DSLServiceImpl();
	GetMobileDeviceResponse expResult = null;
	GetMobileDeviceResponse result = instance.getMobileDeviceData(getMobileDeviceRequest);
	assertEquals(expResult, result);
	// TODO review the generated test code and remove the default call to fail.
	fail("The test case is a prototype.");
    }

}
