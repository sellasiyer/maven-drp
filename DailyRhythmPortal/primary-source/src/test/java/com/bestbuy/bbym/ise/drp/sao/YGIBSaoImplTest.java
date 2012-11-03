package com.bestbuy.bbym.ise.drp.sao;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import com.bestbuy.bbym.ise.drp.domain.YGIBDevice;
import com.bestbuy.bbym.ise.drp.domain.YGIBResponse;
import com.bestbuy.bbym.ise.drp.service.DrpPropertyService;
import com.bestbuy.bbym.ise.drp.service.YGIBServiceImpl;

/**
 * JUnit test for {@link YGIBServiceImpl}.
 */
public class YGIBSaoImplTest {

    private DrpPropertyService drpPropertyService;

    private YGIBSaoImpl ygibSao;

    @Before
    public void setUp() {
	drpPropertyService = EasyMock.createMock(DrpPropertyService.class);
    }

    /**
     * Test of getDomDocument method, of class TitanSaoImpl.
     * Temp. Ignored
     */
    @Ignore
    @Test
    public void testPostDeviceTest() throws Exception {
	ygibSao = new YGIBSaoImpl();
	YGIBDevice device = new YGIBDevice();
	device.setUid("A000002F299BCB");
	device.setPhoneNo("+19132376622");
	ReflectionTestUtils.setField(ygibSao, "drpPropertyService", drpPropertyService);

	expect(
		drpPropertyService.getProperty("YGIB_SERVICE_URL",
			"https://bbytest.yougetitback.com/bby/services/rest/download/getDevice/API-KEY/")).andReturn(
		"http://bbytest.yougetitback.com/bby/services/rest/download/getDevice/API-KEY/");
	expect(
		drpPropertyService.getProperty("YGIB_SIGNATURE",
			"/5lk6DzCEH1NPqhWclEewjmXJzSRRB0TqjyBiXTrsZNWQu3+PJEmRJwwmrD6112k1NM7358BW0nNFTakuOPY3w=="))
		.andReturn("/5lk6DzCEH1NPqhWclEewjmXJzSRRB0TqjyBiXTrsZNWQu3+PJEmRJwwmrD6112k1NM7358BW0nNFTakuOPY3w==");
	expect(drpPropertyService.getProperty("YGIB_API_KEY", "1bc1bb4be38b98ba09218e9360f9059aa")).andReturn(
		"1bc1bb4be38b98ba09218e9360f9059aa");
	replay(drpPropertyService);
	YGIBResponse result = ygibSao.postDeviceTest(device);
    }

}
