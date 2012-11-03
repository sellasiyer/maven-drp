package com.bestbuy.bbym.ise.drp.domain;

import java.io.Serializable;
import java.util.Date;

import com.bestbuy.bbym.ise.domain.BaseObject;

public class FourPartKey extends BaseObject implements Serializable {

    private static final long serialVersionUID = -4542898548709379069L;

    protected String storeId;
    protected String workStationId;
    protected String registerTransactionNum;
    protected Date businessDate;

    public FourPartKey(String storeId, String workStationId, String registerTransactionNum, Date businessDate) {
	super();
	this.storeId = storeId;
	this.workStationId = workStationId;
	this.registerTransactionNum = registerTransactionNum;
	this.businessDate = businessDate;
    }

    public FourPartKey() {
	super();
    }

    public String getStoreId() {
	return storeId;
    }

    public void setStoreId(String storeId) {
	this.storeId = storeId;
    }

    public String getWorkStationId() {
	return workStationId;
    }

    public void setWorkStationId(String workStationId) {
	this.workStationId = workStationId;
    }

    public String getRegisterTransactionNum() {
	return registerTransactionNum;
    }

    public void setRegisterTransactionNum(String registerTransactionNum) {
	this.registerTransactionNum = registerTransactionNum;
    }

    public Date getBusinessDate() {
	return businessDate;
    }

    public void setBusinessDate(Date businessDate) {
	this.businessDate = businessDate;
    }
}
