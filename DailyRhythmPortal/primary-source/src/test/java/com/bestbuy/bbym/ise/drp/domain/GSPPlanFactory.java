package com.bestbuy.bbym.ise.drp.domain;

import java.util.Date;

/**
 * Factory used to create dummy and mock {@link GSPPlan} objects for testing.
 */
public abstract class GSPPlanFactory {

    private GSPPlanFactory() {
	// This class is not meant to be extended or instantiated
    }

    /**
     * Builds mock data used for testing
     */
    public static GSPPlan getGSPPlan() {
	GSPPlan mockPlan = new GSPPlan();

	mockPlan.setId("id");
	mockPlan.setDataSharingKey("dataSharingKey");
	mockPlan.setBillingType("billingType");
	mockPlan.setBusinessDate(new Date());
	mockPlan.setCancel(false);
	mockPlan.setContractSKU("contractSKU");
	mockPlan.setContractSKUDescription("contractSKUDescription");
	mockPlan.setExpirationDate(new Date());
	mockPlan.setMonthlyPayment("monthlyPayment");
	mockPlan.setPlanType("planType");
	mockPlan.setProtectionPlanId("protectionPlanId");
	mockPlan.setRegisterTransactionNumber("registerTransactionNumber");
	mockPlan.setStoreId("storeId");
	mockPlan.setWorkstationId("workstationId");

	return mockPlan;
    }

    public static GSPPlan getGspPlan(String id, String dataSharingKey, String billingType, Date businessDate,
	    boolean cancel, String contractSKU, String contractSKUDescription, Date expirationDate,
	    String monthlyPayment, String planType, String protectionPlanId, String registerTransactionNumber,
	    String storeId, String workstationId, String createdBy, String modifiedBy) {

	GSPPlan mockPlan = new GSPPlan();
	mockPlan.setCreatedBy(createdBy);
	mockPlan.setModifiedBy(modifiedBy);
	mockPlan.setId(id);
	mockPlan.setDataSharingKey(dataSharingKey);
	mockPlan.setBillingType(billingType);
	mockPlan.setBusinessDate(businessDate);
	mockPlan.setCancel(cancel);
	mockPlan.setContractSKU(contractSKU);
	mockPlan.setContractSKUDescription(contractSKUDescription);
	mockPlan.setExpirationDate(expirationDate);
	mockPlan.setMonthlyPayment(monthlyPayment);
	mockPlan.setPlanType(planType);
	mockPlan.setProtectionPlanId(protectionPlanId);
	mockPlan.setRegisterTransactionNumber(registerTransactionNumber);
	mockPlan.setStoreId(storeId);
	mockPlan.setWorkstationId(workstationId);

	return mockPlan;
    }
}
