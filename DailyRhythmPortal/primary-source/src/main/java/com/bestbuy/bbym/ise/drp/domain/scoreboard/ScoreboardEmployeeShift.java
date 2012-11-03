package com.bestbuy.bbym.ise.drp.domain.scoreboard;

import java.util.Date;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import com.bestbuy.bbym.ise.domain.BaseObject;
import com.bestbuy.bbym.ise.drp.domain.Config;

public class ScoreboardEmployeeShift extends BaseObject {

    private static final long serialVersionUID = -2805621254587511288L;

    private Long empDailyNtlMrgnId;
    private Date shiftStartTime;
    private Date shiftEndTime;
    private Long businessSourceId;
    private String storeId;
    private String empFullName;
    private String empBBYId;
    private Long empNtlMrgnId;
    private Config primaryZone;
    private Config secondaryZone;
    private Boolean selected = Boolean.FALSE;

    public Long getEmpDailyNtlMrgnId() {
	return empDailyNtlMrgnId;
    }

    public void setEmpDailyNtlMrgnId(Long empDailyNtlMrgnId) {
	this.empDailyNtlMrgnId = empDailyNtlMrgnId;
    }

    public Date getShiftStartTime() {
	return shiftStartTime;
    }

    public void setShiftStartTime(Date shiftStartTime) {
	this.shiftStartTime = shiftStartTime;
    }

    public Date getShiftEndTime() {
	return shiftEndTime;
    }

    public void setShiftEndTime(Date shiftEndTime) {
	this.shiftEndTime = shiftEndTime;
    }

    public String getStoreId() {
	return storeId;
    }

    public void setStoreId(String storeId) {
	this.storeId = storeId;
    }

    public String getEmpFullName() {
	return empFullName;
    }

    public void setEmpFullName(String empFullName) {
	this.empFullName = empFullName;
    }

    public String getEmpBBYId() {
	return empBBYId;
    }

    public void setEmpBBYId(String empBBYId) {
	this.empBBYId = empBBYId;
    }

    public Long getEmpNtlMrgnId() {
	return empNtlMrgnId;
    }

    public void setEmpNtlMrgnId(Long empNtlMrgnId) {
	this.empNtlMrgnId = empNtlMrgnId;
    }

    public Long getBusinessSourceId() {
	return businessSourceId;
    }

    public void setBusinessSourceId(Long businessSourceId) {
	this.businessSourceId = businessSourceId;
    }

    public Config getPrimaryZone() {
	return primaryZone;
    }

    public void setPrimaryZone(Config primaryZone) {
	this.primaryZone = primaryZone;
    }

    public Config getSecondaryZone() {
	return secondaryZone;
    }

    public void setSecondaryZone(Config secondaryZone) {
	this.secondaryZone = secondaryZone;
    }

    public Boolean getSelected() {
	return selected;
    }

    public void setSelected(Boolean selected) {
	this.selected = selected;
    }

    @Override
    public String toString() {
	return ReflectionToStringBuilder.toString(this);
    }

}
