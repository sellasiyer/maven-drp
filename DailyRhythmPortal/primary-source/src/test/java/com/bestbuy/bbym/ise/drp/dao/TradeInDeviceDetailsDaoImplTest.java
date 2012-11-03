package com.bestbuy.bbym.ise.drp.dao;

import static org.junit.Assert.assertEquals;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.bestbuy.bbym.ise.drp.domain.BeastDevice;
import com.bestbuy.bbym.ise.exception.DataAccessException;

/**
 * JUnit test for {@link TradeInDeviceDetailsDaoImpl}.
 */
public class TradeInDeviceDetailsDaoImplTest extends BaseDaoTest {

    private TradeInDeviceDetailsDaoImpl tradeInDeviceDetailsDao = new TradeInDeviceDetailsDaoImpl();

    @Before
    public void setUp() {
	tradeInDeviceDetailsDao.setUseNextSequenceOnCreate(true);
	tradeInDeviceDetailsDao.setNamedJdbcTemplate(new NamedParameterJdbcTemplate(db));
	tradeInDeviceDetailsDao.setJdbcTemplate(new JdbcTemplate(db));
    }

    @Test
    public void testPersistTradeInDeviceDetails() throws DataAccessException, RuntimeException {
	BeastDevice tradeInDevice = new BeastDevice();
	tradeInDevice.setId(UUID.randomUUID().toString());
	tradeInDevice.setDataSharingKey("36d70c7d-fc98-4c97-af32-314370eeafff");
	tradeInDevice.setHandsetIdentifier("1234567890");
	tradeInDevice.setHandsetIdentifierType("IMEI");
	tradeInDevice.setCarrier("AT&T");
	tradeInDevice.setCreatedBy("a194869");
	tradeInDevice.setDeviceDesc("TEST");
	tradeInDevice.setPhoneNumber("6122270966");
	tradeInDeviceDetailsDao.persistTradeInDeviceDetails(tradeInDevice);
	BeastDevice newTradeInDevice = tradeInDeviceDetailsDao.getBEASTDevices("36d70c7d-fc98-4c97-af32-314370eeafff")
		.get(0);
	assertEquals(tradeInDevice.getDataSharingKey(), newTradeInDevice.getDataSharingKey());
	assertEquals(tradeInDevice.getId(), newTradeInDevice.getId());

	assertEquals(tradeInDevice.getHandsetIdentifier(), newTradeInDevice.getHandsetIdentifier());
	assertEquals(tradeInDevice.getHandsetIdentifierType(), newTradeInDevice.getHandsetIdentifierType());
	assertEquals(tradeInDevice.getCarrier(), newTradeInDevice.getCarrier());
	assertEquals(tradeInDevice.getCreatedBy(), newTradeInDevice.getCreatedBy());
	assertEquals(tradeInDevice.getPhoneNumber(), newTradeInDevice.getPhoneNumber());
	// fail("Not yet implemented");
    }

    @Test
    public void testGetTradeInDeviceDetails() throws DataAccessException, RuntimeException {
	// Same as testPersistTradeInDeviceDetails();
    }

    @Test
    public void testUpdateSelectedDevice() throws DataAccessException, RuntimeException {
	BeastDevice tradeInDevice = new BeastDevice();
	String id = UUID.randomUUID().toString();
	tradeInDevice.setId(id);
	tradeInDevice.setDataSharingKey("36d70c7d-fc98-4c97-af32-314370eeafff");
	tradeInDevice.setHandsetIdentifier("1234567890");
	tradeInDevice.setHandsetIdentifierType("IMEI");
	tradeInDevice.setCarrier("AT&T");
	tradeInDevice.setCreatedBy("a194869");
	tradeInDevice.setDeviceDesc("TEST");
	tradeInDevice.setPhoneNumber("6122270966");
	tradeInDeviceDetailsDao.persistTradeInDeviceDetails(tradeInDevice);
	tradeInDeviceDetailsDao.updateSelectedDevice("36d70c7d-fc98-4c97-af32-314370eeafff", id, "a194867", true);
	BeastDevice newTradeInDevice = tradeInDeviceDetailsDao
		.getSelectedBEASTDevice("36d70c7d-fc98-4c97-af32-314370eeafff");
	assertEquals(newTradeInDevice.isSelectedFlag(), true);
    }

}
