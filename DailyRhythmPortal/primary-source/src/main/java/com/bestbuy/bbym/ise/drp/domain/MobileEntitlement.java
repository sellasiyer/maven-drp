/**
 * 
 */
package com.bestbuy.bbym.ise.drp.domain;

import java.util.ArrayList;
import java.util.List;

import com.bestbuy.bbym.ise.domain.BaseObject;

/**
 * @author a175157
 *
 */
public class MobileEntitlement extends BaseObject {

    private static final long serialVersionUID = 1L;

    private List<Entitlement> entitlementList = new ArrayList<Entitlement>();
    private VendorWarranty vendorWarranty;
    private String deviceETF;
    private String estimatedETF;
    private String etfDescription;
    private boolean tradeable;

    public List<Entitlement> getEntitlementList() {
	return entitlementList;
    }

    public void setEntitlementList(List<Entitlement> entitlementList) {
	this.entitlementList = entitlementList;
    }

    public VendorWarranty getVendorWarranty() {
	return vendorWarranty;
    }

    public void setVendorWarranty(VendorWarranty vendorWarranty) {
	this.vendorWarranty = vendorWarranty;
    }

    public String getDeviceETF() {
	return deviceETF;
    }

    public void setDeviceETF(String deviceETF) {
	this.deviceETF = deviceETF;
    }

    public String getEstimatedETF() {
	return estimatedETF;
    }

    public void setEstimatedETF(String estimatedETF) {
	this.estimatedETF = estimatedETF;
    }

    public String getEtfDescription() {
	return etfDescription;
    }

    public void setEtfDescription(String etfDescription) {
	this.etfDescription = etfDescription;
    }

    public boolean isTradeable() {
	return tradeable;
    }

    public void setTradeable(boolean tradeable) {
	this.tradeable = tradeable;
    }

    public static long getSerialversionuid() {
	return serialVersionUID;
    }
}
