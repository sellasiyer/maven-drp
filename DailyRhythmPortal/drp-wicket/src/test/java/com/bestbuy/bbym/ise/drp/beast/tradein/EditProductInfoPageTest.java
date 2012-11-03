/*
 * Copyright 2012 a904002.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.bestbuy.bbym.ise.drp.beast.tradein;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.easymock.EasyMock;
import org.junit.Ignore;

import com.bestbuy.bbym.ise.drp.common.BaseWebPageTest;
import com.bestbuy.bbym.ise.drp.domain.Address;
import com.bestbuy.bbym.ise.drp.domain.CarrierAccount;
import com.bestbuy.bbym.ise.drp.domain.TitanDevice;
import com.bestbuy.bbym.ise.drp.domain.TitanDeviceFactory;
import com.bestbuy.bbym.ise.drp.domain.ui.UIDataItem;
import com.bestbuy.bbym.ise.drp.service.DrpPropertyService;
import com.bestbuy.bbym.ise.drp.service.TradeInService;
import com.bestbuy.bbym.ise.drp.service.UIService;
import com.bestbuy.bbym.ise.drp.utils.DrpConstants;
import com.bestbuy.bbym.ise.exception.ServiceException;

/**
 * JUnit test for {@link EditProductInfoPage}.
 * 
 * @author a904002
 */
@Ignore
public class EditProductInfoPageTest extends BaseWebPageTest {

    @Override
    public void setUp() {
	super.setUp();
	UIService uiService = EasyMock.createMock(UIService.class);
	mockApplicationContext.putBean("uiService", uiService);

	TradeInService tradeInService = EasyMock.createMock(TradeInService.class);
	mockApplicationContext.putBean("tradeInService", tradeInService);

	DrpPropertyService drpPropertyService = EasyMock.createMock(DrpPropertyService.class);
	mockApplicationContext.putBean("drpPropertyService", drpPropertyService);

	// TODO When services completed
	//setProductInformationBean(ProductInformationBeanFactory.getProductInformationBean());
	UIDataItem titanDataItem = new UIDataItem();
	setTitanDataItem(titanDataItem);

	TitanDevice titanDevice = TitanDeviceFactory.getTitanDevice();
	Address ad = new Address("1", "55068", "13252 Bronze Parkway", "", "Rosemount", "MN", "a904002", "a904002");

	CarrierAccount ca = new CarrierAccount("1", "55068", "John", "Doe", "6123257474", titanDevice
		.getDataSharingKey(), ad, "sella.iyer@bestbuy.com", "a904002", "a904002");

	titanDevice.setCarrierAccount(ca);
	setTitanDevice(titanDevice);
	try{
	    expect(tradeInService.getTradeInCarrierRecord("9aa26157-81cf-48c7-ab35-11a129693b43")).andReturn(
		    titanDevice);

	    replay(tradeInService);
	}catch(ServiceException ex){
	    Logger.getLogger(EditProductInfoPageTest.class.getName()).log(Level.SEVERE, null, ex);
	}

    }

    /**
     * Checks that only {@link DrpConstants#DRP_BEAST} role has access to the
     * page.
     */
    @Override
    public void testPageAccess() {
	assertAccessAllowed(EditProductInfoPage.class, DrpConstants.DRP_BEAST);
	assertAccessDenied(EditProductInfoPage.class, DrpConstants.DRP_ADMIN, DrpConstants.DRP_MANAGER,
		DrpConstants.DRP_LEAD, DrpConstants.DRP_USER, DrpConstants.SHP_USER, DrpConstants.DRP_TEAM,
		DrpConstants.SHP_MANAGER);
    }

    @Override
    public void testRenderMyPage() throws Exception {
	PageParameters pp = new PageParameters();
	pp.add("dsk", "9aa26157-81cf-48c7-ab35-11a129693b43");
	pp.add("userId", "a904002");
	pp.add("storeId", "0010");

	EditProductInfoPage editProductPage = new EditProductInfoPage(pp);
	tester.startPage(editProductPage);
	tester.assertRenderedPage(EditProductInfoPage.class);
	tester.assertNoErrorMessage();

    }

}
