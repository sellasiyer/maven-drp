package com.bestbuy.bbym.ise.drp.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import com.bestbuy.bbym.ise.domain.BaseObject;

public class CheckOutCheckInHistory extends BaseObject {

    private static final long serialVersionUID = 1L;

    private Long id;
    private Phone phone;
    private String firstName;
    private String lastName;
    private String contactPhone;
    private String contactEmail;
    private String serviceOrderNo;
    private LoanerPhoneFulFillmentType fulfillmentType;
    private String gspNo;
    private Date checkOutTime;
    private Date checkInTime;
    private LoanerPhoneCondition checkOutCondition;
    private LoanerPhoneCondition checkInCondition;
    private String checkOutConditionComment;
    private String checkInConditionComment;
    private String checkOutEmployee;
    private String checkInEmployee;
    private BigDecimal checkOutDeposit;
    private BigDecimal checkInDeposit;
    private List<CheckOutCheckInExtra> phoneExtraList;
    private int noOfDaysOut;
    private boolean checkedOut;
    private Integer registerId;
    private Integer transactionNumber;
    private String posStoreId;
    private Date transactionDate;
    private String notes;

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public Phone getPhone() {
	return phone;
    }

    public void setPhone(Phone phone) {
	this.phone = phone;
    }

    public String getFirstName() {
	return firstName;
    }

    public void setFirstName(String firstName) {
	this.firstName = firstName;
    }

    public String getLastName() {
	return lastName;
    }

    public void setLastName(String lastName) {
	this.lastName = lastName;
    }

    public String getContactPhone() {
	return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
	this.contactPhone = contactPhone;
    }

    public String getContactEmail() {
	return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
	this.contactEmail = contactEmail;
    }

    public String getServiceOrderNo() {
	return serviceOrderNo;
    }

    public void setServiceOrderNo(String serviceOrderNo) {
	this.serviceOrderNo = serviceOrderNo;
    }

    public LoanerPhoneFulFillmentType getFulfillmentType() {
	return fulfillmentType;
    }

    public void setFulfillmentType(LoanerPhoneFulFillmentType fulfillmentType) {
	this.fulfillmentType = fulfillmentType;
    }

    public String getGspNo() {
	return gspNo;
    }

    public void setGspNo(String gspNo) {
	this.gspNo = gspNo;
    }

    public Date getCheckOutTime() {
	return checkOutTime;
    }

    public void setCheckOutTime(Date checkOutTime) {
	this.checkOutTime = checkOutTime;
    }

    public Date getCheckInTime() {
	return checkInTime;
    }

    public void setCheckInTime(Date checkInTime) {
	this.checkInTime = checkInTime;
    }

    public LoanerPhoneCondition getCheckOutCondition() {
	return checkOutCondition;
    }

    public void setCheckOutCondition(LoanerPhoneCondition checkOutCondition) {
	this.checkOutCondition = checkOutCondition;
    }

    public LoanerPhoneCondition getCheckInCondition() {
	return checkInCondition;
    }

    public void setCheckInCondition(LoanerPhoneCondition checkInCondition) {
	this.checkInCondition = checkInCondition;
    }

    public String getCheckOutEmployee() {
	return checkOutEmployee;
    }

    public String getCheckOutConditionComment() {
	return checkOutConditionComment;
    }

    public void setCheckOutConditionComment(String checkOutConditionComment) {
	this.checkOutConditionComment = checkOutConditionComment;
    }

    public String getCheckInConditionComment() {
	return checkInConditionComment;
    }

    public void setCheckInConditionComment(String checkInConditionComment) {
	this.checkInConditionComment = checkInConditionComment;
    }

    public void setCheckOutEmployee(String checkOutEmployee) {
	this.checkOutEmployee = checkOutEmployee;
    }

    public String getCheckInEmployee() {
	return checkInEmployee;
    }

    public void setCheckInEmployee(String checkInEmployee) {
	this.checkInEmployee = checkInEmployee;
    }

    public BigDecimal getCheckOutDeposit() {
	return checkOutDeposit;
    }

    public void setCheckOutDeposit(BigDecimal checkOutDeposit) {
	this.checkOutDeposit = checkOutDeposit;
    }

    public BigDecimal getCheckInDeposit() {
	return checkInDeposit;
    }

    public void setCheckInDeposit(BigDecimal checkInDeposit) {
	this.checkInDeposit = checkInDeposit;
    }

    public List<CheckOutCheckInExtra> getPhoneExtraList() {
	return phoneExtraList;
    }

    public void setPhoneExtraList(List<CheckOutCheckInExtra> phoneExtraList) {
	this.phoneExtraList = phoneExtraList;
    }

    public int getNoOfDaysOut() {
	return noOfDaysOut;
    }

    public void setNoOfDaysOut(int noOfDaysOut) {
	this.noOfDaysOut = noOfDaysOut;
    }

    public boolean isCheckedOut() {
	return checkedOut;
    }

    public void setCheckedOut(boolean checkedOut) {
	this.checkedOut = checkedOut;
    }

    public Integer getRegisterId() {
	return registerId;
    }

    public void setRegisterId(Integer registerId) {
	this.registerId = registerId;
    }

    public Integer getTransactionNumber() {
	return transactionNumber;
    }

    public void setTransactionNumber(Integer transaction_number) {
	this.transactionNumber = transaction_number;
    }

    public Date getTransactionDate() {
	return transactionDate;
    }

    public void setTransactionDate(Date transaction_date) {
	this.transactionDate = transaction_date;
    }

    public String getNotes() {
	return notes;
    }

    public void setNotes(String notes) {
	this.notes = notes;
    }

    @Override
    public String toString() {
	return ReflectionToStringBuilder.toString(this);
    }

    public void setPosStoreId(String posStoreId) {
	this.posStoreId = posStoreId;
    }

    public String getPosStoreId() {
	return posStoreId;
    }
}
