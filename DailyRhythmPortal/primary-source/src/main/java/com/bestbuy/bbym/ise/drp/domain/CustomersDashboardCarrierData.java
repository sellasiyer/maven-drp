package com.bestbuy.bbym.ise.drp.domain;

import java.io.Serializable;

import com.bestbuy.bbym.ise.domain.Store;
import com.bestbuy.bbym.ise.domain.Subscription;

/**
 * @author a218635
 */
public class CustomersDashboardCarrierData implements Serializable {

    private static final long serialVersionUID = 1L;

    private Subscription subscriptionInfo;
    private Store storeInfo;

    public Subscription getSubscriptionInfo() {
	return subscriptionInfo;
    }

    public void setSubscriptionInfo(Subscription subscriptionInfo) {
	this.subscriptionInfo = subscriptionInfo;
    }

    public Store getStoreInfo() {
	return storeInfo;
    }

    public void setStoreInfo(Store storeInfo) {
	this.storeInfo = storeInfo;
    }

}
