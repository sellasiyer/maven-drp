package com.bestbuy.bbym.ise.drp.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;

import com.bestbuy.bbym.ise.drp.domain.Address;
import com.bestbuy.bbym.ise.drp.domain.BbyAccount;
import com.bestbuy.bbym.ise.drp.domain.CarrierAccount;
import com.bestbuy.bbym.ise.drp.domain.RecSheetSummary;

/**
 * Utility class for unit testing.
 * 
 * @author Sridhar Savaram
 */
public class TestUtil {

    private TestUtil() {
	// This class is not meant to be instantiated
    }

    public static void assertBeanEqual(BbyAccount bbymAccount1, BbyAccount bbymAccount2) {
	assertEquals(bbymAccount1.getFirstName(), bbymAccount2.getFirstName());
	assertEquals(bbymAccount1.getLastName(), bbymAccount2.getLastName());
	assertEquals(bbymAccount1.getPhoneNumber(), bbymAccount2.getPhoneNumber());
	assertEquals(bbymAccount1.getEmail(), bbymAccount2.getEmail());
	assertEquals(bbymAccount1.getEccaId(), bbymAccount2.getEccaId());
	assertEquals(bbymAccount1.getDataSharingKey(), bbymAccount2.getDataSharingKey());
	assertBeanEqual(bbymAccount1.getAddress(), bbymAccount2.getAddress());
    }

    public static void assertBeanEqual(CarrierAccount carrierAccount1, CarrierAccount carrierAccount2) {
	assertEquals(carrierAccount1.getFirstName(), carrierAccount2.getFirstName());
	assertEquals(carrierAccount1.getLastName(), carrierAccount2.getLastName());
	assertEquals(carrierAccount1.getPhoneNumber(), carrierAccount2.getPhoneNumber());
	assertEquals(carrierAccount1.getEmail(), carrierAccount2.getEmail());
	assertEquals(carrierAccount1.getCoverageZip(), carrierAccount2.getCoverageZip());
	assertEquals(carrierAccount1.getCarrierAccountNumber(), carrierAccount2.getCarrierAccountNumber());
	assertEquals(carrierAccount1.getDataSharingKey(), carrierAccount2.getDataSharingKey());
	assertBeanEqual(carrierAccount1.getAddress(), carrierAccount2.getAddress());
    }

    public static void assertBeanEqual(Address address1, Address address2) {
	assertEquals(address1.getAddressline1(), address2.getAddressline1());
	assertEquals(address1.getAddressline2(), address2.getAddressline2());
	assertEquals(address1.getCity(), address2.getCity());
	assertEquals(address1.getState(), address2.getState());
	assertEquals(address1.getZip(), address2.getZip());
    }

    public static void assertBeanEqual(RecSheetSummary recSheetSummary1, RecSheetSummary recSheetSummary2) {
	assertEquals(recSheetSummary1.getPlanInfo(), recSheetSummary2.getPlanInfo());
	assertEquals(recSheetSummary1.getDeviceInfo(), recSheetSummary2.getDeviceInfo());
	assertEquals(recSheetSummary1.getGspPlanInfo(), recSheetSummary2.getGspPlanInfo());
	assertEquals(recSheetSummary1.getBuyBackPlanInfo(), recSheetSummary2.getBuyBackPlanInfo());
	assertEquals(recSheetSummary1.getDataSharingKey(), recSheetSummary2.getDataSharingKey());
    }

    /**
     * Reads the contents of the file.
     * <p>
     * Fails if the file could not be read from the test resources folder.
     * </p>
     * 
     * @param testClass
     *            the test class
     * @param fileName
     *            the name of the file to be read
     * @return an input stream containing the file contents
     */
    public static InputStream readFileToInputStream(Class<?> testClass, String fileName) {
	String packageName = testClass.getPackage().getName();
	packageName = packageName.replace('.', '/');
	InputStream inputStream = TestUtil.class.getClassLoader().getResourceAsStream(packageName + '/' + fileName);
	if (inputStream == null){
	    fail("Failed to read contents of file " + fileName);
	}
	return inputStream;
    }

    /**
     * Reads the contents of the file.
     * <p>
     * Fails if the file could not be read from the test resources folder.
     * </p>
     * 
     * @param testClass
     *            the test class
     * @param fileName
     *            the name of the file to be read
     * @return the file contents
     */
    public static String readFileToString(Class<?> testClass, String fileName) {
	try{
	    return IOUtils.toString(readFileToInputStream(testClass, fileName));
	}catch(IOException e){
	    fail("Failed to read contents of file " + fileName);
	    // We'll never get here
	    return null;
	}
    }
}
