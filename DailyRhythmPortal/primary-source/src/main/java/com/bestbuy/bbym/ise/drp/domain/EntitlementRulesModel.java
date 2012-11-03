package com.bestbuy.bbym.ise.drp.domain;

import java.util.Date;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

public class EntitlementRulesModel {

    private static final long serialVersionUID = 1L;

    private String coverageBenefit;
    private String coverageVehicle; //displayed on screen
    private String displayText;
    private String entitlementDetails;
    private String actions;
    private String created_by;
    private String amended_by;
    private Date created_on;
    private Date amended_on;
    private boolean displayFlag;

    public boolean isDisplayFlag() {
	return displayFlag;
    }

    public void setDisplayFlag(boolean displayFlag) {
	this.displayFlag = displayFlag;
    }

    public String getCoverageBenefit() {
	return coverageBenefit;
    }

    public void setCoverageBenefit(String coverageBenefit) {
	this.coverageBenefit = coverageBenefit;
    }

    public String getCoverageVehicle() {
	return coverageVehicle;
    }

    public void setCoverageVehicle(String coverageVehicle) {
	this.coverageVehicle = coverageVehicle;
    }

    public String getDisplayText() {
	return displayText;
    }

    public void setDisplayText(String displayText) {
	this.displayText = displayText;
    }

    public String getEntitlementDetails() {
	return entitlementDetails;
    }

    public void setEntitlementDetails(String entitlementDetails) {
	this.entitlementDetails = entitlementDetails;
    }

    public String getActions() {
	return actions;
    }

    public void setActions(String actions) {
	this.actions = actions;
    }

    public String getCreated_by() {
	return created_by;
    }

    public void setCreated_by(String created_by) {
	this.created_by = created_by;
    }

    public Date getCreated_on() {
	return created_on;
    }

    public void setCreated_on(Date created_on) {
	this.created_on = created_on;
    }

    public String getAmended_by() {
	return amended_by;
    }

    public void setAmended_by(String amended_by) {
	this.amended_by = amended_by;
    }

    public Date getAmended_on() {
	return amended_on;
    }

    public void setAmended_on(Date amended_on) {
	this.amended_on = amended_on;
    }

    @Override
    public String toString() {
	return ReflectionToStringBuilder.toString(this);
    }

    public Entitlement mapEntitlementsFromDaoToDomain() {
	Entitlement entitlement = new Entitlement();
	entitlement.setActions(this.getActions());
	entitlement.setCoverageBenefit(this.getCoverageBenefit());
	//entitlement.setCoverageBenefitPromptCode(this.get) nothing in dao model
	entitlement.setCoverageVehicle(this.getCoverageVehicle());
	entitlement.setDisplayText(this.getDisplayText());
	entitlement.setDetails(this.getEntitlementDetails());
	entitlement.setCreatedBy(this.getCreated_by());
	entitlement.setCreatedOn(this.getCreated_on());
	entitlement.setModifiedBy(this.getAmended_by());
	entitlement.setModifiedOn(this.getAmended_on());
	entitlement.setDisplayFlag(this.isDisplayFlag());
	return entitlement;
    }
}
