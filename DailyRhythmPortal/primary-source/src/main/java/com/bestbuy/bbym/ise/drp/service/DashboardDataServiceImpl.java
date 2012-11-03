package com.bestbuy.bbym.ise.drp.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.bestbuy.bbym.ise.domain.CarrierOption;
import com.bestbuy.bbym.ise.domain.CarrierPlan;
import com.bestbuy.bbym.ise.domain.Customer;
import com.bestbuy.bbym.ise.domain.CustomerSearchCriteria;
import com.bestbuy.bbym.ise.domain.Line;
import com.bestbuy.bbym.ise.drp.domain.DashboardData;
import com.bestbuy.bbym.ise.drp.domain.DrpUser;
import com.bestbuy.bbym.ise.exception.IseBusinessException;
import com.bestbuy.bbym.ise.exception.ServiceException;

/**
 * @author a186288
 */
@Service("dashboardDataService")
public class DashboardDataServiceImpl implements DashboardDataService {

    private static Logger logger = LoggerFactory.getLogger(DashboardDataServiceImpl.class);

    @Resource
    private CarrierDataService carrierDataService;

    @Resource
    private CustomerDataService customerDataService;

    @Override
    public DashboardData getDashboardData(Customer customer, DrpUser drpUser, boolean invokeUcs)
	    throws ServiceException, IseBusinessException {
	long t0 = System.currentTimeMillis();
	try{
	    DashboardData dashboardData = new DashboardData();
	    dashboardData.setCarrierData(carrierDataService.getSubscribersCarrierInfo(customer, drpUser, invokeUcs));

	    try{
		logger
			.info("About to orchestrate the Carrier Data Search with BBY's Customer Profile Search based on Phone Number ");
		dashboardData.setCustomerSearchCriteria(CustomerSearchCriteria.PHONE_NUMBER);
		List<Customer> custProfileInfoList = customerDataService.getBBYCustomerProfile(customer, dashboardData
			.getCustomerSearchCriteria(), drpUser);
		if (custProfileInfoList != null && !custProfileInfoList.isEmpty()){
		    dashboardData.setSearchCustomers(custProfileInfoList);
		}else{
		    try{
			if (dashboardData.getCarrierData().getSubscriptionInfo() != null
				&& StringUtils.isNotBlank(dashboardData.getCarrierData().getSubscriptionInfo()
					.getPrimAcctEmailId())){
			    dashboardData.setCustomerSearchCriteria(CustomerSearchCriteria.EMAIL);
			    customer
				    .setEmail(dashboardData.getCarrierData().getSubscriptionInfo().getPrimAcctEmailId());
			    dashboardData.setSearchCustomers(customerDataService.getBBYCustomerProfile(customer,
				    dashboardData.getCustomerSearchCriteria(), drpUser));
			    dashboardData.setCustomerSearchCriteria(CustomerSearchCriteria.PHONE_EMAIL);
			}
		    }catch(Exception e){
			logger.error("Exception while calling getBBYCustomerProfile with Search Criteria: "
				+ CustomerSearchCriteria.EMAIL, e);
			return dashboardData;
		    }
		}
	    }catch(Exception e){
		logger.error("caught exception while calling getBBYCustomerProfile with Search Criteria: "
			+ CustomerSearchCriteria.PHONE_NUMBER, e);
		try{
		    if (dashboardData.getCarrierData().getSubscriptionInfo() != null
			    && StringUtils.isNotBlank(dashboardData.getCarrierData().getSubscriptionInfo()
				    .getPrimAcctEmailId())){
			dashboardData.setCustomerSearchCriteria(CustomerSearchCriteria.EMAIL);
			customer.setEmail(dashboardData.getCarrierData().getSubscriptionInfo().getPrimAcctEmailId());
			dashboardData.setSearchCustomers(customerDataService.getBBYCustomerProfile(customer,
				dashboardData.getCustomerSearchCriteria(), drpUser));
			dashboardData.setCustomerSearchCriteria(CustomerSearchCriteria.PHONE_EMAIL);
		    }
		}catch(Exception ex){
		    logger.error("caught exception while calling getBBYCustomerProfile with Search Criteria: "
			    + CustomerSearchCriteria.EMAIL, ex);
		}
	    }

	    return dashboardData;
	}finally{
	    long t1 = System.currentTimeMillis();
	    logger.info("getDashboardData: " + (t1 - t0) + "ms");
	}
    }

    @Override
    public List<List<Object>> getPlansAndOptionsTable(Customer customer) {
	List<List<Object>> data = new ArrayList<List<Object>>();
	List<Object> headerRow = new ArrayList<Object>();
	headerRow.add(null);
	data.add(headerRow);
	ArrayList<CarrierPlan> uniqueCarrierPlanList = new ArrayList<CarrierPlan>();
	ArrayList<CarrierOption> uniqueCarrierOptionList = new ArrayList<CarrierOption>();
	List<Line> lines = customer.getSubscription().getLines();
	if (lines != null){

	    // Add plans
	    for(int i = 0; i < lines.size(); ++i){
		Line line = lines.get(i);

		for(List<Object> row: data){
		    if (lines.size() >= row.size()){
			row.add(null);
		    }
		}

		headerRow.set(i + 1, line);

		if (line != null){
		    List<CarrierPlan> carrierPlans = line.getCarrierPlans();
		    if (carrierPlans != null){
			for(CarrierPlan carrierPlan: carrierPlans){
			    if (carrierPlan != null){
				boolean found = false;
				for(int j = 0; j < uniqueCarrierPlanList.size(); ++j){
				    CarrierPlan uniqueCarrierPlan = uniqueCarrierPlanList.get(j);
				    String uniquePlanName = uniqueCarrierPlan.getPlanName();
				    if (uniquePlanName != null && uniquePlanName.equals(carrierPlan.getPlanName())){
					found = true;
					data.get(j + 1).set(i + 1, carrierPlan.getPlanMRC());
					break;
				    }
				}
				if (!found){
				    uniqueCarrierPlanList.add(carrierPlan);
				    List<Object> newRow = new ArrayList<Object>();
				    newRow.add(carrierPlan.getPlanName());
				    for(int j = 0; j < i; ++j){
					newRow.add(null);
				    }
				    newRow.add(carrierPlan.getPlanMRC());
				    data.add(newRow);
				}
			    }
			}
		    }
		}
	    }

	    // Add options
	    for(int i = 0; i < lines.size(); ++i){
		Line line = lines.get(i);
		if (line != null){

		    for(List<Object> row: data){
			if (lines.size() >= row.size()){
			    row.add(null);
			}
		    }

		    List<CarrierOption> carrierOptions = line.getCarrierOptions();
		    if (carrierOptions != null){
			for(CarrierOption carrierOption: carrierOptions){
			    if (carrierOption != null){
				boolean found = false;
				for(int j = 0; j < uniqueCarrierOptionList.size(); ++j){
				    CarrierOption uniqueCarrierOption = uniqueCarrierOptionList.get(j);
				    String uniqueOptionName = uniqueCarrierOption.getOptionName();
				    if (uniqueOptionName != null
					    && uniqueOptionName.equals(carrierOption.getOptionName())){
					found = true;
					data.get(j + 1 + uniqueCarrierPlanList.size()).set(i + 1,
						carrierOption.getOptionPrice());
					break;
				    }
				}
				if (!found){
				    uniqueCarrierOptionList.add(carrierOption);
				    List<Object> newRow = new ArrayList<Object>();
				    newRow.add(carrierOption.getOptionName());
				    for(int j = 0; j < i; ++j){
					newRow.add(null);
				    }
				    newRow.add(carrierOption.getOptionPrice());
				    data.add(newRow);
				}
			    }
			}
		    }
		}
	    }
	}

	// Add shared plan columns
	// A shared plan is a plan with a non-null value of accountMRC.
	for(int i = 0; i < uniqueCarrierPlanList.size(); ++i){
	    CarrierPlan uniqueCarrierPlan = uniqueCarrierPlanList.get(i);
	    if (uniqueCarrierPlan != null && uniqueCarrierPlan.getAccountMRC() != null){
		headerRow.add(1, uniqueCarrierPlan);
		for(int j = 1; j < data.size(); ++j){
		    data.get(j).add(1, null);
		}
		data.get(i + 1).set(1, uniqueCarrierPlan.getAccountMRC());
	    }
	}

	// Add totals.
	BigDecimal grandTotal = new BigDecimal(0);
	List<Object> totalRow = new ArrayList<Object>();
	totalRow.add(null);
	headerRow.add(null);
	for(int j = 1; j < data.size(); ++j){
	    List<Object> row = data.get(j);
	    BigDecimal total = new BigDecimal(0);
	    for(int i = 1; i < row.size(); ++i){
		BigDecimal price = (BigDecimal) row.get(i);
		if (price != null){
		    total = total.add(price);
		}
		BigDecimal columnTotal = null;
		if (i < totalRow.size()){
		    if (price != null){
			columnTotal = (BigDecimal) totalRow.get(i);
			if (columnTotal != null){
			    columnTotal = columnTotal.add(price);
			}else{
			    columnTotal = price;
			}
			totalRow.set(i, columnTotal);
		    }
		}else{
		    totalRow.add(price);
		}
	    }
	    row.add(total);
	    if (total != null){
		grandTotal = grandTotal.add(total);
	    }
	}
	totalRow.add(grandTotal);
	data.add(totalRow);

	return data;
    }
}
