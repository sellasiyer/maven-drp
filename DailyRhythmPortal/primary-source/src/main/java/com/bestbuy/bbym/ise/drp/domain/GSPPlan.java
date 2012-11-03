package com.bestbuy.bbym.ise.drp.domain;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

import com.bestbuy.bbym.ise.domain.BaseObject;

/**
 * 
 * @author a175157
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
public class GSPPlan extends BaseObject {

    public static final String BILLING_TYPE_MONTHLY = "MONTHLY";
    public static final String BILLING_TYPE_ONE_TIME = "ONE-TIME";

    private String id;
    private String dataSharingKey;
    private String billingType;
    private Date businessDate;
    private boolean cancel;
    private String contractSKU;
    private String contractSKUDescription;
    private Date expirationDate;
    private String monthlyPayment;
    private String planType;
    private String protectionPlanId;
    private String registerTransactionNumber;
    private String storeId;
    private String workstationId;

    @XmlTransient
    public String getId() {
	return id;
    }

    public void setId(String id) {
	this.id = id;
    }

    @XmlTransient
    public String getDataSharingKey() {
	return dataSharingKey;
    }

    public void setDataSharingKey(String dataSharingKey) {
	this.dataSharingKey = dataSharingKey;
    }

    public String getBillingType() {
	return billingType;
    }

    public void setBillingType(String billingType) {
	this.billingType = billingType;
    }

    public boolean isCancel() {
	return cancel;
    }

    public void setCancel(boolean cancel) {
	this.cancel = cancel;
    }

    @XmlElement(name = "contractSku")
    public String getContractSKU() {
	return contractSKU;
    }

    public void setContractSKU(String contractSKU) {
	this.contractSKU = contractSKU;
    }

    @XmlElement(name = "contractSkuDescription")
    public String getContractSKUDescription() {
	return contractSKUDescription;
    }

    public void setContractSKUDescription(String contractSKUDescription) {
	this.contractSKUDescription = contractSKUDescription;
    }

    @XmlTransient
    public Date getExpirationDate() {
	return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
	this.expirationDate = expirationDate;
    }

    @XmlTransient
    public String getMonthlyPayment() {
	return monthlyPayment;
    }

    public void setMonthlyPayment(String monthlyPayment) {
	this.monthlyPayment = monthlyPayment;
    }

    @XmlTransient
    public String getPlanType() {
	return planType;
    }

    public void setPlanType(String planType) {
	this.planType = planType;
    }

    public String getProtectionPlanId() {
	return protectionPlanId;
    }

    public void setProtectionPlanId(String protectionPlanId) {
	this.protectionPlanId = protectionPlanId;
    }

    public String getRegisterTransactionNumber() {
	return registerTransactionNumber;
    }

    public void setRegisterTransactionNumber(String registerTransactionNumber) {
	this.registerTransactionNumber = registerTransactionNumber;
    }

    public String getStoreId() {
	return storeId;
    }

    @XmlElement(name = "retailStoreId")
    public void setStoreId(String storeId) {
	this.storeId = storeId;
    }

    public String getWorkstationId() {
	return workstationId;
    }

    public void setWorkstationId(String workstationId) {
	this.workstationId = workstationId;
    }

    public Date getBusinessDate() {
	return businessDate;
    }

    public void setBusinessDate(Date businessDate) {
	this.businessDate = businessDate;
    }

    @Override
    @XmlTransient
    public Date getCreatedOn() {
	return super.getCreatedOn();
    }

    @Override
    @XmlTransient
    public Date getModifiedOn() {
	return super.getModifiedOn();
    }

    @Override
    @XmlTransient
    public String getCreatedBy() {
	return super.getCreatedBy();
    }

    @Override
    @XmlTransient
    public String getModifiedBy() {
	return super.getModifiedBy();
    }

    @Override
    public String toString() {
	return "GSPPlan [id=" + id + ", dataSharingKey=" + dataSharingKey + ", billingType=" + billingType
		+ ", businessDate=" + businessDate + ", cancel=" + cancel + ", contractSKU=" + contractSKU
		+ ", contractSKUDescription=" + contractSKUDescription + ", expirationDate=" + expirationDate
		+ ", monthlyPayment=" + monthlyPayment + ", planType=" + planType + ", protectionPlanId="
		+ protectionPlanId + ", registerTransactionNumber=" + registerTransactionNumber + ", storeId="
		+ storeId + ", workstationId=" + workstationId + "]";
    }

}
