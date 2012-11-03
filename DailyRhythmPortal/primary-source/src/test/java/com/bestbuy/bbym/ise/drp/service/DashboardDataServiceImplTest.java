package com.bestbuy.bbym.ise.drp.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.bestbuy.bbym.ise.domain.Address;
import com.bestbuy.bbym.ise.domain.Carrier;
import com.bestbuy.bbym.ise.domain.CarrierOption;
import com.bestbuy.bbym.ise.domain.CarrierPlan;
import com.bestbuy.bbym.ise.domain.Customer;
import com.bestbuy.bbym.ise.domain.Line;
import com.bestbuy.bbym.ise.domain.Subscription;

/**
 * JUnit test for {@link DashboardDataServiceImpl}.
 */
public class DashboardDataServiceImplTest {

    private DashboardDataService dashboardDataService = new DashboardDataServiceImpl();

    @Test
    public void testGetPlansAndFeaturesTable() throws Exception {
	Customer customer = createCustomer();
	List<List<Object>> data = dashboardDataService.getPlansAndOptionsTable(customer);
	Assert.assertEquals(6, data.size());

	int i = 0;
	Assert.assertEquals(4, data.get(i).size());
	Assert.assertEquals(null, data.get(i).get(0));
	Assert.assertEquals("0", ((Line) data.get(i).get(1)).getMobileNumber());
	Assert.assertEquals("1", ((Line) data.get(i).get(2)).getMobileNumber());
	Assert.assertEquals(null, data.get(i).get(3));
	++i;
	Assert.assertEquals(4, data.get(i).size());
	Assert.assertEquals("planName0", data.get(i).get(0));
	Assert.assertEquals(new BigDecimal("543.21"), data.get(i).get(1));
	Assert.assertEquals(new BigDecimal("543.21"), data.get(i).get(2));
	Assert.assertEquals(new BigDecimal("1086.42"), data.get(i).get(3));
	++i;
	Assert.assertEquals(4, data.get(i).size());
	Assert.assertEquals("planName1", data.get(i).get(0));
	Assert.assertEquals(null, data.get(i).get(1));
	Assert.assertEquals(new BigDecimal("543.21"), data.get(i).get(2));
	Assert.assertEquals(new BigDecimal("543.21"), data.get(i).get(3));
	++i;
	Assert.assertEquals(4, data.get(i).size());
	Assert.assertEquals("optionName0", data.get(i).get(0));
	Assert.assertEquals(new BigDecimal("123.45"), data.get(i).get(1));
	Assert.assertEquals(new BigDecimal("123.45"), data.get(i).get(2));
	Assert.assertEquals(new BigDecimal("246.90"), data.get(i).get(3));
	++i;
	Assert.assertEquals(4, data.get(i).size());
	Assert.assertEquals("optionName1", data.get(i).get(0));
	Assert.assertEquals(null, data.get(i).get(1));
	Assert.assertEquals(new BigDecimal("123.45"), data.get(i).get(2));
	Assert.assertEquals(new BigDecimal("123.45"), data.get(i).get(3));
	++i;
	Assert.assertEquals(4, data.get(i).size());
	Assert.assertEquals(null, data.get(i).get(0));
	Assert.assertEquals(new BigDecimal("666.66"), data.get(i).get(1));
	Assert.assertEquals(new BigDecimal("1333.32"), data.get(i).get(2));
	Assert.assertEquals(new BigDecimal("1999.98"), data.get(i).get(3));
    }

    @Test
    public void testGetPlansAndFeaturesTableWithSharedPlans() throws Exception {
	Customer customer = createCustomer();
	for(int i = 0; i < customer.getSubscription().getLines().size(); i++){
	    customer.getSubscription().getLines().get(i).getCarrierPlans().get(0).setAccountMRC(
		    new BigDecimal("333.33"));
	}
	List<List<Object>> data = dashboardDataService.getPlansAndOptionsTable(customer);
	Assert.assertEquals(6, data.size());

	int i = 0;
	Assert.assertEquals(5, data.get(i).size());
	Assert.assertEquals("planName0", ((CarrierPlan) data.get(i).get(1)).getPlanName());
	++i;
	Assert.assertEquals(5, data.get(i).size());
	Assert.assertEquals(new BigDecimal("333.33"), data.get(i).get(1));
	++i;
	Assert.assertEquals(5, data.get(i).size());
	Assert.assertEquals(null, data.get(i).get(1));
	++i;
	Assert.assertEquals(5, data.get(i).size());
	Assert.assertEquals(null, data.get(i).get(1));
	++i;
	Assert.assertEquals(5, data.get(i).size());
	Assert.assertEquals(null, data.get(i).get(1));
	++i;
	Assert.assertEquals(5, data.get(i).size());
	Assert.assertEquals(new BigDecimal("333.33"), data.get(i).get(1));
    }

    public static Customer createCustomer() {
	Customer customer = new Customer();
	Subscription subscription = new Subscription();
	subscription.setLines(new java.util.ArrayList<Line>());
	subscription.setCarrier(Carrier.ATT);
	customer.setSubscription(subscription);

	Address address = new Address();
	address.setZipcode("55416");
	customer.setAddress(address);

	for(int i = 0; i < 2; i++){
	    Line line = new Line();
	    line.setMobileNumber(String.valueOf(i));
	    List<CarrierPlan> carrierPlans = new ArrayList<CarrierPlan>();
	    for(int j = 0; j <= i; ++j){
		CarrierPlan plan = new CarrierPlan();
		plan.setPlanCode("planCode" + j);
		plan.setPlanName("planName" + j);
		plan.setPlanMRC(new BigDecimal("543.21"));
		carrierPlans.add(plan);
		line.setCarrierPlans(carrierPlans);
	    }
	    List<CarrierOption> carrierOptions = new ArrayList<CarrierOption>();
	    for(int j = 0; j <= i; ++j){
		CarrierOption option = new CarrierOption();
		option.setOptionCode("optionCode" + j);
		option.setOptionName("optionName" + j);
		option.setOptionPrice(new BigDecimal("123.45"));
		carrierOptions.add(option);
		line.setCarrierOptions(carrierOptions);
	    }
	    customer.getSubscription().getLines().add(line);
	}
	return customer;
    }
}
