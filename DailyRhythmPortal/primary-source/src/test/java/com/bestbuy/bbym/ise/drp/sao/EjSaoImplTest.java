package com.bestbuy.bbym.ise.drp.sao;

import static org.easymock.EasyMock.capture;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.easymock.Capture;
import org.easymock.EasyMock;
import org.junit.Test;

import com.bestbuy.bbym.ise.drp.domain.DrpUser;
import com.bestbuy.bbym.ise.drp.domain.DrpUserFactory;
import com.bestbuy.bbym.ise.exception.ServiceException;
import com.bestbuy.bbym.ise.iseclientej.ElectronicJournalDataRequest;
import com.bestbuy.bbym.ise.iseclientej.ElectronicJournalFourPartKey;
import com.bestbuy.bbym.ise.iseclientej.ElectronicJournalResponse;
import com.bestbuy.bbym.ise.iseclientej.ElectronicJournalServices;
import com.bestbuy.bbym.ise.util.Util;

/**
 * JUnit test fro {@link EjSaoImpl}.
 */
public class EjSaoImplTest extends BaseSaoTest {

    private EjSaoImpl ejSaoImpl = new EjSaoImpl();
    private ElectronicJournalServices mockElectronicJournalServices;

    @Override
    public void setUp() {
	super.setUp();

	mockElectronicJournalServices = EasyMock.createMock(ElectronicJournalServices.class);
	ejSaoImpl = new EjSaoImpl() {

	    @Override
	    protected ElectronicJournalServices getEJServices() throws ServiceException {
		return mockElectronicJournalServices;
	    }
	};

	ejSaoImpl.setDrpPropertiesService(drpPropertyService);
    }

    @Override
    public void testTimeout() {
	// TODO Implement me!!
    }

    @Override
    public void assertIbhValuesPopulatedCorrectly() throws Exception {
	// TODO Why don't we have an IBH for EJ service?
    }

    /**
     * Test for
     * {@link EjSaoImpl#getElectronicJournal(List, com.bestbuy.bbym.ise.drp.domain.DrpUser)}
     * .
     */
    @Test
    public void testGetElectronicJournal() throws Exception {

	// Prepare to record request sent in service call
	Capture<ElectronicJournalDataRequest> capture = new Capture<ElectronicJournalDataRequest>();

	// Load a canned service response
	ElectronicJournalResponse ejResponse = loadResponse("EJ_ElectronicJournalResponse.xml",
		ElectronicJournalResponse.class);

	EasyMock.expect(mockElectronicJournalServices.getElectronicJournal(capture(capture))).andReturn(ejResponse);
	EasyMock.replay(mockElectronicJournalServices);

	final String storeId = "1";
	final String registerId = "2";
	final Date transactionDate = Calendar.getInstance().getTime();
	final String transactionNumber = "3";
	final DrpUser drpUser = DrpUserFactory.getDrpUser();

	String ej = ejSaoImpl.getElectronicJournal(storeId, registerId, transactionDate, transactionNumber, drpUser);

	// Check that correct values were sent in service call
	ElectronicJournalDataRequest sentEjRequest = capture.getValue();
	List<ElectronicJournalFourPartKey> sent4PartKeys = sentEjRequest.getElectronicJournalFourPartKeys()
		.getElectronicJournalFourPartKey();
	assertEquals("Incorrect number of 4 part keys passed in service call", 1, sent4PartKeys.size());
	assertEquals("Incorrect storeId passed in service call", Integer.parseInt(storeId), sent4PartKeys.get(0)
		.getStoreId());
	assertEquals("Incorrect registerId passed in service call", Integer.parseInt(registerId), sent4PartKeys.get(0)
		.getRegisterId());
	assertEquals("Incorrect transactionDate passed in service call", Util.toXmlGregorianCalendar(transactionDate),
		sent4PartKeys.get(0).getTransactionDate());
	assertEquals("Incorrect transactionNumber passed in service call", Integer.parseInt(transactionNumber),
		sent4PartKeys.get(0).getTransactionNumber());
	assertEquals("Incorrect userId passed in service call", drpUser.getUserId(), sentEjRequest.getUserId());
	//	assertFalse(sent4PartKeys.getPrivateData);

	// Check that correct values were returned from SAO
	assertNotNull("No ej returned from service call", ej);
	assertTrue("Incorrect ej returned from service call (start)", ej
		.startsWith("*********** START RECEIPT ***********"));
	assertTrue("Incorrect ej returned from service call (end)", ej.trim().endsWith(
		"Transaction Completed Successfully."));
    }
}
