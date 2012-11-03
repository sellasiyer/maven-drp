package com.bestbuy.bbym.ise.drp.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.bestbuy.bbym.ise.domain.Carrier;
import com.bestbuy.bbym.ise.domain.CarrierOption;
import com.bestbuy.bbym.ise.domain.CarrierPlan;
import com.bestbuy.bbym.ise.domain.Customer;
import com.bestbuy.bbym.ise.domain.Device;
import com.bestbuy.bbym.ise.domain.Line;
import com.bestbuy.bbym.ise.domain.ProtectionPlan;
import com.bestbuy.bbym.ise.domain.Store;
import com.bestbuy.bbym.ise.domain.Subscription;
import com.bestbuy.bbym.ise.drp.domain.CustomersDashboardCarrierData;
import com.bestbuy.bbym.ise.drp.domain.DrpUser;
import com.bestbuy.bbym.ise.drp.domain.OptInResponse;
import com.bestbuy.bbym.ise.exception.IseBusinessException;
import com.bestbuy.bbym.ise.exception.IseExceptionCodeEnum;
import com.bestbuy.bbym.ise.exception.ServiceException;

@Service("carrierDataService2")
public class DummyCarrierDataService implements CarrierDataService {

    @Override
    public CustomersDashboardCarrierData getSubscribersCarrierInfo(Customer customer, DrpUser drpUser, boolean invokeUcs)
	    throws ServiceException, IseBusinessException {
	DummyData.sleep(2);
	DummyData.throwServiceException(50);
	DummyData.throwIseBusinessException(50);
	if (customer == null || drpUser == null){
	    throw new ServiceException(IseExceptionCodeEnum.UnexpectedNull, "Unexpected null");
	}

	CustomersDashboardCarrierData carrierData = new CustomersDashboardCarrierData();
	carrierData.setSubscriptionInfo(new Subscription());
	carrierData.getSubscriptionInfo().setAccountNumber(DummyData.getAcctId());
	carrierData.getSubscriptionInfo().setPrimAcctFirstName(DummyData.getFirstName());
	carrierData.getSubscriptionInfo().setPrimAcctLastName(DummyData.getLastName());
	carrierData.getSubscriptionInfo().setPrimAcctEmailId(DummyData.getEmail());
	carrierData.getSubscriptionInfo().setNumberOfLinesAvailable(new Integer(DummyData.getRandomIndex(4)));
	if (carrierData.getSubscriptionInfo().getNumberOfLinesAvailable().intValue() == 0){
	    carrierData.getSubscriptionInfo().setNumberOfLinesAvailable(null);
	}
	if (customer.getSubscription() != null && customer.getSubscription().getCarrier() != null){
	    carrierData.getSubscriptionInfo().setCarrier(customer.getSubscription().getCarrier());
	}

	carrierData.setStoreInfo(new Store());
	carrierData.getStoreInfo().setUpgradeCheckCount(DummyData.getRandomIndex(100) + 6);
	carrierData.getStoreInfo().setOptInCount(DummyData.getRandomIndex(11) + 3);

	carrierData.getSubscriptionInfo().setLines(new ArrayList<Line>());

	int dummycount = DummyData.getRandomIndex(10) + 3;
	for(int i = 0; i < dummycount; i++){
	    Line line = new Line();
	    line.setId(new Long(i));
	    line.setContractEndDate(DummyData.getDate());
	    line.setEarlyEligibilityDate(DummyData.getDate());
	    line.setStandardEligible(DummyData.getStdEligible());
	    line.setEarlyEligible(DummyData.getEarlyEligible());
	    Device device = new Device();
	    device.setDescription(DummyData.getHardware());
	    device.setTradeInValue(DummyData.getTradeInPrice());
	    device.setSerialNumber(DummyData.getSerialNumber());
	    if (DummyData.getRandomIndex(10) < 7){
		ProtectionPlan protectionPlan = new ProtectionPlan();
		protectionPlan.setPlanType("GSBTP");
		protectionPlan.setSku(DummyData.getSku());
		protectionPlan.setPlanStatus(DummyData.getPlanStatus());
		protectionPlan.setDescription(DummyData.getPlanDescription());
		protectionPlan.setPlanNumber(DummyData.getPlanNumber());
		protectionPlan.setPlanExpiryDate(DummyData.getDate());
		protectionPlan.setPlanEffectiveDate(DummyData.getDate());
		device.setProtectionPlan(protectionPlan);
	    }
	    line.setDevice(device);
	    line.setLineStatus(DummyData.getLineStatus());
	    List<CarrierPlan> carrierPlans = new ArrayList<CarrierPlan>();
	    CarrierPlan plan = new CarrierPlan();
	    plan.setPlanType(DummyData.getLineType());
	    plan.setPlanName("planName" + (i + 1));
	    plan.setPlanMRC(new BigDecimal("543.21"));
	    carrierPlans.add(plan);
	    line.setCarrierPlans(carrierPlans);
	    List<CarrierOption> carrierOptions = new ArrayList<CarrierOption>();
	    for(int j = 1; j <= i; ++j){
		CarrierOption option = new CarrierOption();
		option.setOptionCategory("optionCategory" + j);
		option.setOptionCode("optionCode" + j);
		option.setOptionName("optionName" + j);
		option.setOptionPrice(new BigDecimal("123.45"));
		carrierOptions.add(option);
	    }
	    line.setCarrierOptions(carrierOptions);
	    line.setMobileNumber(DummyData.getPhone());
	    line.setStdEligibilityDate(DummyData.getDate());
	    line.setEligibilityType(DummyData.getEligibleType());
	    line.setOptin(DummyData.getOptIn());
	    line.setOptinAllowed(DummyData.getOptInAllowed());
	    carrierData.getSubscriptionInfo().getLines().add(line);
	}

	// set the first lines to be the one searched for
	carrierData.getSubscriptionInfo().getLines().get(2).setMobileNumber(customer.getContactPhone());

	return carrierData;
    }

    @Override
    public OptInResponse setSubscribersOptIn(List<Line> optedSubsLineData, Customer customer, DrpUser drpUser)
	    throws ServiceException {
	DummyData.sleep(2);
	DummyData.throwServiceException(20);
	if (optedSubsLineData == null || customer == null || drpUser == null){
	    throw new ServiceException(IseExceptionCodeEnum.UnexpectedNull, "Unexpected null");
	}
	return new OptInResponse(DummyData.getRandomIndex(22) + 3);
    }

    @Override
    public Map<String, String> getTokenCodes(Carrier carrierType, DrpUser drpUser) throws ServiceException,
	    IseBusinessException {

	Map<String, String> tokenCodesMap = new LinkedHashMap<String, String>();
	tokenCodesMap.put("StoreId", "0699");
	tokenCodesMap.put("dealerCode", "ns4456");
	tokenCodesMap.put("salesAgentId", "45645222");
	tokenCodesMap.put("nssPassCode", "5616123131515");

	return tokenCodesMap;

    }

}
