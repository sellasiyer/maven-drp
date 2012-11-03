package com.bestbuy.bbym.ise.drp.common;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.easymock.EasyMock;

import com.bestbuy.bbym.ise.domain.Carrier;
import com.bestbuy.bbym.ise.drp.service.DailyRhythmPortalService;
import com.bestbuy.bbym.ise.drp.service.DrpPropertyService;
import com.bestbuy.bbym.ise.drp.service.HotlinkService;

/**
 * Base class for all web pages that display a header, navigation and footer.
 */
public abstract class BaseNavPageTest extends BaseWebPageTest {

    private DrpPropertyService propertyService;

    @Override
    public void setUp() {
	super.setUp();
	HotlinkService hotlinkService = EasyMock.createMock(HotlinkService.class);
	mockApplicationContext.putBean("hotlinkService", hotlinkService);
	propertyService = EasyMock.createMock(DrpPropertyService.class);
	mockApplicationContext.putBean("drpPropertyService", propertyService);
    }

    /**
     * Asserts that the header is present.
     */
    protected final void assertHeader() {
	tester.assertComponent("headerPanel", HeaderPanel.class);
    }

    /**
     * Asserts that the footer is present.
     */
    protected final void assertFooter() {
	assertNotNull("Missing footer version label", tester.getComponentFromLastRenderedPage("footer.version.label"));
    }

    /**
     * Asserts that the navigation components exist.
     */
    protected final void assertNavigation() {
	assertHeader();
	assertFooter();
    }

    protected final DrpPropertyService getMockDrpPropertyService() {
	return propertyService;
    }

    protected final void setMockSupportedCarriers(DailyRhythmPortalService dailyRhythmPortalService) {
	List<Carrier> carriers = new ArrayList<Carrier>();
	carriers.add(Carrier.SPRINT);
	EasyMock.expect(dailyRhythmPortalService.getSupportedCarriers()).andReturn(carriers);
	EasyMock.replay(dailyRhythmPortalService);
    }
}
