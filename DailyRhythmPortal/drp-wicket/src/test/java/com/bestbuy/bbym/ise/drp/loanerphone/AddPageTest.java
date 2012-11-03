package com.bestbuy.bbym.ise.drp.loanerphone;

import java.util.ArrayList;
import java.util.List;

import org.easymock.EasyMock;

import com.bestbuy.bbym.ise.drp.common.BaseNavPageTest;
import com.bestbuy.bbym.ise.drp.domain.Carrier;
import com.bestbuy.bbym.ise.drp.domain.OperatingSystem;
import com.bestbuy.bbym.ise.drp.service.LoanerPhoneService;
import com.bestbuy.bbym.ise.drp.utils.DrpConstants;

/**
 * JUnit test for {@link AddPage}.
 */
public class AddPageTest extends BaseNavPageTest {

    private LoanerPhoneService loanerPhoneService;

    @Override
    public void setUp() {
	super.setUp();
	loanerPhoneService = EasyMock.createMock(LoanerPhoneService.class);
	mockApplicationContext.putBean("loanerPhoneService", loanerPhoneService);
    }

    /**
     * Checks that only {@link DrpConstants#DRP_ADMIN},
     * {@link DrpConstants#DRP_MANAGER} and {@link DrpConstants#DRP_LEAD} roles
     * have access to the page.
     */
    @Override
    public void testPageAccess() {
	assertAccessAllowed(AddPage.class, DrpConstants.DRP_ADMIN, DrpConstants.DRP_MANAGER, DrpConstants.DRP_LEAD);
	assertAccessDenied(AddPage.class, DrpConstants.DRP_USER, DrpConstants.SHP_USER, DrpConstants.DRP_TEAM,
		DrpConstants.DRP_BEAST, DrpConstants.SHP_MANAGER);
    }

    /**
     * Tests rendering of page and presence of components.
     */
    @Override
    public void testRenderMyPage() throws Exception {

	List<OperatingSystem> list = new ArrayList<OperatingSystem>();
	OperatingSystem os = new OperatingSystem();
	os.setId(1L);
	os.setCreatedBy("A909241");
	os.setCreatedOn(null);
	list.add(os);

	List<Carrier> carrierList = new ArrayList<Carrier>();
	Carrier carrier = new Carrier();
	carrier.setCarrier("AT&T");
	carrier.setId(1L);
	carrier.setModifiedBy("A90241");
	carrierList.add(carrier);

	EasyMock.expect(loanerPhoneService.getOperatingSystems()).andReturn(list);
	EasyMock.expect(loanerPhoneService.getCarriers()).andReturn(carrierList);

	EasyMock.replay(loanerPhoneService);

	// start and render the test page
	AddPage page = new AddPage(null, null);
	tester.startPage(page);

	// Check that the right page was rendered (no unexpected redirect or
	// intercept)
	tester.assertRenderedPage(AddPage.class);
	tester.assertNoErrorMessage();
    }
}
