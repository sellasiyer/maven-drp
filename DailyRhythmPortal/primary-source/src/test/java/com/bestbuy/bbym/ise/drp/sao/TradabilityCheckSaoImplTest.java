package com.bestbuy.bbym.ise.drp.sao;

import static org.easymock.EasyMock.capture;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.easymock.Capture;
import org.easymock.EasyMock;
import org.junit.Ignore;
import org.junit.Test;

import com.bestbuy.bbym.ise.drp.domain.BeastDevice;
import com.bestbuy.bbym.ise.drp.domain.BeastDeviceFactory;
import com.bestbuy.bbym.ise.drp.domain.DrpUser;
import com.bestbuy.bbym.ise.drp.domain.DrpUserFactory;
import com.bestbuy.bbym.ise.drp.utils.DrpConstants;
import com.bestbuy.bbym.ise.exception.ServiceException;
import com.bestbuy.bbym.sales.transactioncheck.component.v1.HeaderType;
import com.bestbuy.bbym.sales.transactioncheck.component.v1.TransactionCheckRequestType;
import com.bestbuy.bbym.sales.transactioncheck.component.v1.TransactionCheckResponseType;
import com.bestbuy.tsh.businessservices.proxy.tradeincheck.TransactionCheckServicePortType;
import com.bestbuy.tsh.common.metadata.components.v1.RequestMetaDataType;

/**
 * JUnit test for {@link TradabilityCheckSaoImpl}.
 * 
 * @author a194869
 */
public class TradabilityCheckSaoImplTest extends BaseSaoTest {

    private TradabilityCheckSaoImpl tradabilityCheckSaoImpl;
    private TransactionCheckServicePortType mockTransactionCheckServicePortType;

    @Override
    public void setUp() {
	super.setUp();

	mockTransactionCheckServicePortType = EasyMock.createMock(TransactionCheckServicePortType.class);
	tradabilityCheckSaoImpl = new TradabilityCheckSaoImpl() {

	    @Override
	    protected TransactionCheckServicePortType getTradabilityCheckService() throws ServiceException {
		return mockTransactionCheckServicePortType;
	    }
	};

	expectIbhPropertiesCalls(false);

	tradabilityCheckSaoImpl.setDrpPropertiesService(drpPropertyService);
    }

    @Override
    public void testTimeout() {
	// TODO Implement me!
    }

    @Override
    public void assertIbhValuesPopulatedCorrectly() throws Exception {
	assertIbhValuesPopulatedCorrectly(com.bestbuy.tsh.common.metadata.components.v1.InternationalBusinessHierarchyType.class);
    }

    /**
     * Test successful call to {@link TradabilityCheckSaoImpl#checkForTradability(BeastDevice, DrpUser, String)}.
     */
    @Test
    public void testCheckForTradability() throws Exception {

	// Prepare to record request sent in service call
	Capture<TransactionCheckRequestType> capture = new Capture<TransactionCheckRequestType>();

	// Load a canned service response
	TransactionCheckResponseType transactionCheckResponseType = loadResponse(
		"TRADEIN_TransactionCheckResponse.xml", TransactionCheckResponseType.class);

	EasyMock.expect(mockTransactionCheckServicePortType.transactionCheck(capture(capture))).andReturn(
		transactionCheckResponseType);
	EasyMock.replay(mockTransactionCheckServicePortType);

	final String sourceSystem = "someSourceSystem";
	final String applicationName = "someApplicationName";

	EasyMock.expect(drpPropertyService.getProperty("TRADABILITY_FAKE", "false")).andReturn("false");
	EasyMock.expect(drpPropertyService.getProperty("SOURCE_SYSTEM")).andReturn(sourceSystem);
	EasyMock.expect(drpPropertyService.getProperty("APPLICATION_NAME")).andReturn(applicationName);
	EasyMock.replay(drpPropertyService);

	final BeastDevice tradeInDevice = BeastDeviceFactory.getBeastDevice();
	final DrpUser drpUser = DrpUserFactory.getDrpUser();
	final String storeId = "someStoreId";

	boolean tradeability = tradabilityCheckSaoImpl.checkForTradability(tradeInDevice, drpUser, storeId);

	// Check that correct values were sent in service call
	assertNotNull("No IBH passed in service call", capture.getValue().getInternationalBusinessHierarchy());
	RequestMetaDataType requestMetaData = capture.getValue().getRequestMetaData();
	assertEquals("Incorrect sourceId passed in service call", sourceSystem, requestMetaData.getSourceID());
	assertEquals("Incorrect userId passed in service call", drpUser.getUserId(), requestMetaData.getUserID());
	assertEquals("Incorrect programId passed in service call", applicationName, requestMetaData.getProgramID());
	HeaderType headerType = capture.getValue().getTransactionCheck().getTransactionDetails().getHeader();
	assertEquals("Incorrect idType passed in service call", DrpConstants.TRANSACTION_ID, headerType.getID().get(0)
		.getType());
	assertEquals("Incorrect idValue passed in service call", tradeInDevice.getBeastTransactionId(), headerType
		.getID().get(0).getValue());
	assertEquals("Incorrect carrier passed in service call", tradeInDevice.getCarrier(), headerType.getCarrier()
		.getCode().getName());
	assertEquals("Incorrect repId passed in service call", drpUser.getUserId(), headerType
		.getTransationSourceDetail().getRepID());
	assertEquals("Incorrect locationId passed in service call", storeId, headerType.getTransationSourceDetail()
		.getLocationID());
	assertEquals("Incorrect userId passed in service call", drpUser.getUserId(), capture.getValue()
		.getRequestMetaData().getUserID());

	// Check that correct values were returned from SAO
	assertTrue("Incorrect traeability returned from service call", tradeability);
    }

    /**
     * Test failed call to {@link TradabilityCheckSaoImpl#checkForTradability(BeastDevice, DrpUser, String)}.
     */
    @Ignore
    @Test
    public void testCheckForTradabilityFailure() throws Exception {

	// Load a canned service response
	TransactionCheckResponseType transactionCheckResponseType = loadResponse(
		"TRADEIN_TransactionCheckResponse1.xml", TransactionCheckResponseType.class);

	// TODO Implement me!
    }
}
