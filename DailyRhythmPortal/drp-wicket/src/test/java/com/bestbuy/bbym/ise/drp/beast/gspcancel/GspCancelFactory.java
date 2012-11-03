package com.bestbuy.bbym.ise.drp.beast.gspcancel;

import java.util.ArrayList;
import java.util.List;

import com.bestbuy.bbym.ise.domain.ProtectionPlan;
import com.bestbuy.bbym.ise.domain.ServicePlanTransaction;

public abstract class GspCancelFactory {

    private GspCancelFactory() {
	// This class is not meant to be extended or instantiated
    }

    public static ProtectionPlan getProtectionPlanDetails() {

	ProtectionPlan protectionPlanDetails = new ProtectionPlan();
	protectionPlanDetails.setPlanType("abcd");
	protectionPlanDetails.setServicePlanTransactions(getServicePlanTransactions());

	return protectionPlanDetails;

    }

    public static List<ServicePlanTransaction> getServicePlanTransactions() {

	List<ServicePlanTransaction> list = new ArrayList<ServicePlanTransaction>();
	ServicePlanTransaction servicePlanTransactions = new ServicePlanTransaction();
	servicePlanTransactions.setStoreNum("0699");
	servicePlanTransactions.setTransactionNum("007");

	list.add(servicePlanTransactions);

	return list;
    }

}
