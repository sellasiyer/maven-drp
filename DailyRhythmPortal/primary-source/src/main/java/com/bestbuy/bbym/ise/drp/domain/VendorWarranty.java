package com.bestbuy.bbym.ise.drp.domain;

import java.util.Date;

import com.bestbuy.bbym.ise.domain.BaseObject;

public class VendorWarranty extends BaseObject {

    private static final long serialVersionUID = 1L;
    private String warrantyStatus;
    private Date coverageStartDate;
    private Date coverageEndDate;
    private String productDescription;
    private String laborCovered;
    private String partsCovered;
    private boolean validSerialNumber;
    private String repeatServiceOrderNumber;
    private boolean repeatService;
    private String duplicateOrderNumber;
    private boolean duplicateOrder;
    private String manufacturer;
    private Date contractCoverageStartDate;
    private Date contractCoverageEndDate;
    private String errorCode;
    private String errorDescription;

    public String getWarrantyStatus() {
	return warrantyStatus;
    }

    public void setWarrantyStatus(String warrantyStatus) {
	this.warrantyStatus = warrantyStatus;
    }

    public Date getCoverageStartDate() {
	return coverageStartDate;
    }

    public void setCoverageStartDate(Date coverageEndDate) {
	this.coverageStartDate = coverageEndDate;
    }

    public Date getCoverageEndDate() {
	return coverageEndDate;
    }

    public void setCoverageEndDate(Date coverageEndDate) {
	this.coverageEndDate = coverageEndDate;
    }

    public String getProductDescription() {
	return productDescription;
    }

    public void setProductDescription(String productDescription) {
	this.productDescription = productDescription;
    }

    public String getLaborCovered() {
	return laborCovered;
    }

    public void setLaborCovered(String laborCovered) {
	this.laborCovered = laborCovered;
    }

    public String getPartsCovered() {
	return partsCovered;
    }

    public void setPartsCovered(String partsCovered) {
	this.partsCovered = partsCovered;
    }

    public boolean isValidSerialNumber() {
	return validSerialNumber;
    }

    public void setValidSerialNumber(boolean validSerialNumber) {
	this.validSerialNumber = validSerialNumber;
    }

    public String getRepeatServiceOrderNumber() {
	return repeatServiceOrderNumber;
    }

    public void setRepeatServiceOrderNumber(String repeatServiceOrderNumber) {
	this.repeatServiceOrderNumber = repeatServiceOrderNumber;
    }

    public boolean isRepeatService() {
	return repeatService;
    }

    public void setRepeatService(boolean repeatService) {
	this.repeatService = repeatService;
    }

    public String getDuplicateOrderNumber() {
	return duplicateOrderNumber;
    }

    public void setDuplicateOrderNumber(String duplicateOrderNumber) {
	this.duplicateOrderNumber = duplicateOrderNumber;
    }

    public boolean isDuplicateOrder() {
	return duplicateOrder;
    }

    public void setDuplicateOrder(boolean duplicateOrder) {
	this.duplicateOrder = duplicateOrder;
    }

    public String getManufacturer() {
	return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
	this.manufacturer = manufacturer;
    }

    public Date getContractCoverageStartDate() {
	return contractCoverageStartDate;
    }

    public void setContractCoverageStartDate(Date contractCoverageStartDate) {
	this.contractCoverageStartDate = contractCoverageStartDate;
    }

    public Date getContractCoverageEndDate() {
	return contractCoverageEndDate;
    }

    public void setContractCoverageEndDate(Date contractCoverageEndDate) {
	this.contractCoverageEndDate = contractCoverageEndDate;
    }

    public String getErrorCode() {
	return errorCode;
    }

    public void setErrorCode(String errorCode) {
	this.errorCode = errorCode;
    }

    public String getErrorDescription() {
	return errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
	this.errorDescription = errorDescription;
    }

    public static long getSerialversionuid() {
	return serialVersionUID;
    }
}
