/**
 * 
 */
package com.bestbuy.bbym.ise.drp.domain;

import com.bestbuy.bbym.ise.domain.BaseObject;

/**
 * @author a175157
 *
 */
public class Entitlement extends BaseObject {

    private static final long serialVersionUID = 1L;
    private String coverageBenefit;
    private String coverageBenefitPromptCode;
    private String coverageVehicle;
    private String entitlementType;
    private String details;
    private String actions;
    private String displayText;
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

    public String getCoverageBenefitPromptCode() {
	return coverageBenefitPromptCode;
    }

    public void setCoverageBenefitPromptCode(String coverageBenefitPromptCode) {
	this.coverageBenefitPromptCode = coverageBenefitPromptCode;
    }

    public String getCoverageVehicle() {
	return coverageVehicle;
    }

    public void setCoverageVehicle(String coverageVehicle) {
	this.coverageVehicle = coverageVehicle;
    }

    public String getEntitlementType() {
	return entitlementType;
    }

    public void setEntitlementType(String entitlementType) {
	this.entitlementType = entitlementType;
    }

    public String getDetails() {
	return details;
    }

    public void setDetails(String details) {
	this.details = details;
    }

    public String getActions() {
	return actions;
    }

    public void setActions(String actions) {
	this.actions = actions;
    }

    public static long getSerialversionuid() {
	return serialVersionUID;
    }

    public void setDisplayText(String displayText) {
	this.displayText = displayText;
    }

    public String getDisplayText() {
	return displayText;
    }
}
