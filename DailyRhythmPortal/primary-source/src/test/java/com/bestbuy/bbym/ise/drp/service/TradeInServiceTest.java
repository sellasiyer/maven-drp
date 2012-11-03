package com.bestbuy.bbym.ise.drp.service;

import static org.easymock.EasyMock.createStrictMock;
import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import com.bestbuy.bbym.ise.drp.dao.DataTransferDao;
import com.bestbuy.bbym.ise.drp.dao.TradeInDao;
import com.bestbuy.bbym.ise.drp.dao.TradeInDeviceDetailsDao;
import com.bestbuy.bbym.ise.drp.domain.BeastDevice;
import com.bestbuy.bbym.ise.drp.domain.BeastDeviceFactory;
import com.bestbuy.bbym.ise.drp.domain.DataTransferSummary;
import com.bestbuy.bbym.ise.drp.domain.DrpUser;
import com.bestbuy.bbym.ise.drp.domain.DrpUserFactory;
import com.bestbuy.bbym.ise.drp.domain.TitanDevice;
import com.bestbuy.bbym.ise.drp.sao.TradabilityCheckSao;

/**
 * JUnit test for {@link TradeInServiceImpl}.
 * 
 * @author a904002
 */
public class TradeInServiceTest {

    private TradeInServiceImpl service;
    private TradeInDao mockTradeInDao;
    private DataTransferDao mockDataTransferDao;
    private TradeInDeviceDetailsDao mockTradeInDeviceDetailsDao;
    private TradabilityCheckSao mockTradabilityCheckSao;
    private String storeId = "0010";
    private String userId = "a194869";

    @Before
    public void setUp() {
	service = new TradeInServiceImpl();
	mockTradeInDao = createStrictMock(TradeInDao.class);
	mockDataTransferDao = createStrictMock(DataTransferDao.class);
	mockTradeInDeviceDetailsDao = createStrictMock(TradeInDeviceDetailsDao.class);
	mockTradabilityCheckSao = createStrictMock(TradabilityCheckSao.class);
	service.setTradeInDao(mockTradeInDao);
	service.setDataTransferDao(mockDataTransferDao);
	service.setTradeInDeviceDetailsDao(mockTradeInDeviceDetailsDao);
	service.setTradabilityCheckSao(mockTradabilityCheckSao);
    }

    /**
     * Test of isTradable method, of class TradeInService.
     */
    @Test
    public void testIsTradable() throws Exception {
	BeastDevice mockDevice = BeastDeviceFactory.getBeastDevice();
	DrpUser drpUser = DrpUserFactory.getDrpUser();
	DataTransferSummary mockDataTranferSummary = new DataTransferSummary();
	mockDataTranferSummary.setCapTransId("0281000067800006789");
	mockDataTranferSummary.setDataSharingKey(mockDevice.getDataSharingKey());
	mockDataTranferSummary.setStoreId(storeId);
	expect(mockDataTransferDao.getDataTransferRow(mockDevice.getDataSharingKey()))
		.andReturn(mockDataTranferSummary);
	expect(mockTradabilityCheckSao.checkForTradability(mockDevice, drpUser, storeId)).andReturn(true);
	EasyMock.replay(mockDataTransferDao);
	EasyMock.replay(mockTradabilityCheckSao);
	assertEquals(service.isTradable(mockDevice, drpUser), true);
    }

    @Test
    public void testWriteDeviceDetails() throws Exception {
	BeastDevice mockDevice = BeastDeviceFactory.getBeastDevice();
	List<BeastDevice> mockDeviceList = new ArrayList<BeastDevice>();
	mockDeviceList.add(mockDevice);
	service.writeDeviceDetails(mockDeviceList, storeId, userId, "BEAST");
	expect(service.getDeviceDetails(mockDevice.getDataSharingKey())).andReturn(mockDeviceList);

    }

    @Test
    public void testGetTradeableDevice() throws Exception {
	DrpUser drpUser = DrpUserFactory.getDrpUser();
	BeastDevice mockDevice = BeastDeviceFactory.getBeastDevice();
	List<BeastDevice> mockDeviceList = new ArrayList<BeastDevice>();
	mockDeviceList.add(0, mockDevice);
	expect(mockTradeInDeviceDetailsDao.getBEASTDevices(mockDevice.getDataSharingKey())).andReturn(mockDeviceList);
	mockTradeInDeviceDetailsDao.updateSelectedDevice(EasyMock.eq(mockDevice.getDataSharingKey()), EasyMock
		.eq(mockDevice.getId()), EasyMock.eq(drpUser.getUserId()), EasyMock.eq(true));
	EasyMock.replay(mockTradeInDeviceDetailsDao);
	mockDevice.setSelectedFlag(true);
	assertEquals(service.getTradeableDevice(mockDevice.getDataSharingKey(), "7890", drpUser), mockDevice);
	EasyMock.verify(mockTradeInDeviceDetailsDao);
    }

    @Test
    public void testAddTradeableDevice() throws Exception {
	// fail("Not yet implemented");
    }

    @Test
    public void testUpdateSelectedFlag() {
	// fail("Not yet implemented");
    }

    @Test
    public void testAddDeviceDetails() {
	// fail("Not yet implemented");
    }

    @Test
    public void testGetTradeInRecord() throws Exception {
	BeastDevice mockDevice = BeastDeviceFactory.getBeastDevice();
	TitanDevice mockTitanDevice = getTitanDevice();
	expect(mockTradeInDao.getTradeInRecord(mockDevice.getDataSharingKey())).andReturn(mockTitanDevice);
	EasyMock.replay(mockTradeInDao);
	assertEquals(service.getTradeInRecord(mockDevice.getDataSharingKey()), mockTitanDevice);
    }

    public static TitanDevice getTitanDevice() {
	TitanDevice titanDevice = new TitanDevice();
	return titanDevice;
    }

}
