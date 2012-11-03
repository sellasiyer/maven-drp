package com.bestbuy.bbym.ise.drp.beast.accessories;

import java.util.HashMap;
import java.util.Map;

import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.easymock.EasyMock;

import com.bestbuy.bbym.ise.domain.Sku;
import com.bestbuy.bbym.ise.drp.common.BaseWebPageTest;
import com.bestbuy.bbym.ise.drp.common.PageParameterKeys;
import com.bestbuy.bbym.ise.drp.service.AccessoryService;
import com.bestbuy.bbym.ise.drp.service.DrpPropertyService;
import com.bestbuy.bbym.ise.drp.utils.DrpConstants;

/**
 * JUnit test for {@link DisplayAccessories}.
 */
public class DisplayAccessoriesPageTest extends BaseWebPageTest {

    private AccessoryService mockAccessoryService;

    @Override
    public void setUp() {
	super.setUp();

	mockAccessoryService = EasyMock.createMock(AccessoryService.class);
	mockApplicationContext.putBean("accessoryService", mockAccessoryService);
	DrpPropertyService drpPropertyService = EasyMock.createMock(DrpPropertyService.class);
	mockApplicationContext.putBean("drpPropertyService", drpPropertyService);
    }

    /**
     * Checks that only {@link DrpConstants#DRP_BEAST} role has access to the
     * page.
     */
    @Override
    public void testPageAccess() {
	assertAccessAllowed(DisplayAccessories.class, DrpConstants.DRP_BEAST);
	assertAccessDenied(DisplayAccessories.class, DrpConstants.DRP_ADMIN, DrpConstants.DRP_MANAGER,
		DrpConstants.DRP_LEAD, DrpConstants.DRP_USER, DrpConstants.SHP_USER, DrpConstants.DRP_TEAM,
		DrpConstants.SHP_MANAGER);
    }

    @Override
    public void testRenderMyPage() throws Exception {

	final String storeId = "3907";
	final String deviceSku = "8347096";
	final String phoneNumber = "7854185010";
	final Map<String, Sku> phoneNumberSkuMap = new HashMap<String, Sku>();
	Sku sku = new Sku();
	phoneNumberSkuMap.put(phoneNumber, sku);

	EasyMock.expect(mockAccessoryService.getAccessories(EasyMock.isA(String.class), EasyMock.isA(Map.class)))
		.andReturn(phoneNumberSkuMap);
	EasyMock.replay(mockAccessoryService);

	// start and render the test page
	PageParameters pageParameters = new PageParameters();
	pageParameters.add(PageParameterKeys.DATA.getUrlParameterKey(), "[" + deviceSku + '|' + phoneNumber + ']');
	pageParameters.add(PageParameterKeys.STORE_ID.getUrlParameterKey(), storeId);
	tester.startPage(DisplayAccessories.class, pageParameters);

	// Check that the right page was rendered (no unexpected redirect or
	// intercept)
	tester.assertRenderedPage(DisplayAccessories.class);
	tester.assertNoErrorMessage();
    }

}
