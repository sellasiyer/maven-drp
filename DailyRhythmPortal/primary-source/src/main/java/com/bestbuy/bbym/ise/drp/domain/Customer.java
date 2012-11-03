package com.bestbuy.bbym.ise.drp.domain;

import com.bestbuy.bbym.ise.domain.BaseObject;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author a175157
 */

public class Customer extends BaseObject implements Serializable {

    private static final long serialVersionUID = 1L;
    private String dataSharingKey;
    private String storeId;
    private boolean transferFlag;
    private String source;
    private RecSheetSummary recSheetSummary;
    private BbyAccount bbyAccount;
    private CarrierAccount carrierAccount;
    private String capTransactionId;

    public Customer() {
	super();
    }

    public Customer(String dataSharingKey, String storeId, boolean transferFlag, String source, String createdBy,
	    String lastModifiedBy, Date createdDate, RecSheetSummary recSheetSummary, BbyAccount bbyAccount,
	    CarrierAccount carrierAccount, String capTransactionId) {
	super(createdBy, lastModifiedBy);
	this.dataSharingKey = dataSharingKey;
	this.storeId = storeId;
	this.transferFlag = transferFlag;
	this.source = source;
	this.recSheetSummary = recSheetSummary;
	this.bbyAccount = bbyAccount;
	this.carrierAccount = carrierAccount;
	this.capTransactionId = capTransactionId;
    }

    public String getDataSharingKey() {
	return dataSharingKey;
    }

    public void setDataSharingKey(String dataSharingKey) {
	this.dataSharingKey = dataSharingKey;
    }

    public String getStoreId() {
	return storeId;
    }

    public void setStoreId(String storeId) {
	this.storeId = storeId;
    }

    public boolean getTransferFlag() {
	return transferFlag;
    }

    public void setTransferFlag(boolean transferFlag) {
	this.transferFlag = transferFlag;
    }

    public String getSource() {
	return source;
    }

    public void setSource(String source) {
	this.source = source;
    }

    @Override
    public String getCreatedBy() {
	return super.getCreatedBy();
    }

    @Override
    public Date getCreatedOn() {
	return super.getCreatedOn();
    }

    @Override
    public String getModifiedBy() {
	return super.getModifiedBy();
    }

    @Override
    public Date getModifiedOn() {
	return super.getModifiedOn();
    }

    public RecSheetSummary getRecSheetSummary() {
	return recSheetSummary;
    }

    public void setRecSheetSummary(RecSheetSummary recSheetSummary) {
	this.recSheetSummary = recSheetSummary;
    }

    public BbyAccount getBbyAccount() {
	return bbyAccount;
    }

    public void setBbyAccount(BbyAccount bbyAccount) {
	this.bbyAccount = bbyAccount;
    }

    public CarrierAccount getCarrierAccount() {
	return carrierAccount;
    }

    public void setCarrierAccount(CarrierAccount carrierAccount) {
	this.carrierAccount = carrierAccount;
    }

    public String getCapTransactionId() {
	return capTransactionId;
    }

    public void setCapTransactionId(String capTransactionId) {
	this.capTransactionId = capTransactionId;
    }

    @Override
    public String toString() {
	return "Customer [dataSharingKey=" + dataSharingKey + ", storeId=" + storeId + ", transferFlag=" + transferFlag
		+ ", source=" + source + ", createdBy=" + getCreatedBy() + ", createdDate=" + getCreatedOn()
		+ ", recSheetSummary=" + recSheetSummary + ", bbyAccount=" + bbyAccount + ", carrierAccount="
		+ carrierAccount + ", capTransactionId=" + capTransactionId + "]";
    }
}
