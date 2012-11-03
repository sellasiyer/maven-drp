package com.bestbuy.bbym.ise.drp.domain;

import java.math.BigDecimal;

/**
 * Factory used to create dummy and mock {@link TitanDevice} objects for
 * testing.
 */
public class TitanDeviceFactory {

    private TitanDeviceFactory() {
	// This class is not meant to be extended or instantiated
    }

    /**
     * Gets a mock Titan device used for testing.
     */
    public static TitanDevice getTitanDevice() {
	TitanDevice titanDevice = new TitanDevice();
	titanDevice.setCartId(1040L);
	titanDevice.setCreatedBy("a904002");
	titanDevice.setDataSharingKey("9aa26157-81cf-48c7-ab35-11a129693b43");
	titanDevice.setDeviceId("9aa26157-81cf-48c7-ab35-11a129693b43");
	titanDevice.setDescription("testing description");
	titanDevice.setDeviceName("Apple iPhone 4s");
	titanDevice.setManufacturer("Apple");
	titanDevice.setModelNumber("A8758");
	titanDevice.setNextUrl("http://hydra-pt/test");
	titanDevice.setPromoCode("ABC123");
	titanDevice.setPromoValue("50");
	titanDevice.setReceiptNumber("11111111111111111");
	titanDevice.setSerialNumber("124568795665486");
	titanDevice.setSku("TESTSKU");
	titanDevice.setTechnologyType("IMEI");
	titanDevice.setTradeInValue(new BigDecimal("50"));
	return titanDevice;
    }
}
