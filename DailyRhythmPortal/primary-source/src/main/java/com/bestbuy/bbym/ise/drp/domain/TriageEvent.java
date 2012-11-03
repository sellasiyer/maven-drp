/**
 * Best Buy Inc (c) 2012
 */
package com.bestbuy.bbym.ise.drp.domain;

import java.util.Date;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import com.bestbuy.bbym.ise.domain.BaseObject;

/**
 * Domain class for triage history.
 * 
 * @author Yernar Khuangan (a1003810)
 * @version 1.0
 * @since iteration 21
 */
public class TriageEvent extends BaseObject {

    private static final long serialVersionUID = -8471916220453956698L;

    private Long id;
    private TriageResolution triageResolution;
    private TriageRecommendation triageRecommendation;
    private String deviceSerialNo;
    private String issueComment;
    private String resolutionComment;
    private String protectionPlanId;
    private String techCheckerIssues;
    private String customerBenefits;

    public TriageEvent() {
	super();

    }

    public TriageEvent(Date createdOn, Date modifiedOn, String createdBy, String modifiedBy, Long id,
	    TriageResolution triageResolution, TriageRecommendation triageRecommendation, String deviceSerialNo,
	    String issueComment, String resolutionComment, String protectionPlanId, String techCheckerIssues,
	    String customerBenefits) {
	super(createdOn, modifiedOn, createdBy, modifiedBy);
	this.id = id;
	this.triageResolution = triageResolution;
	this.triageRecommendation = triageRecommendation;
	this.deviceSerialNo = deviceSerialNo;
	this.issueComment = issueComment;
	this.resolutionComment = resolutionComment;
	this.protectionPlanId = protectionPlanId;
	this.techCheckerIssues = techCheckerIssues;
	this.customerBenefits = customerBenefits;
    }

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public TriageResolution getTriageResolution() {
	return triageResolution;
    }

    public void setTriageResolution(TriageResolution triageResolution) {
	this.triageResolution = triageResolution;
    }

    public TriageRecommendation getTriageRecommendation() {
	return triageRecommendation;
    }

    public void setTriageRecommendation(TriageRecommendation triageRecommendation) {
	this.triageRecommendation = triageRecommendation;
    }

    public String getDeviceSerialNo() {
	return deviceSerialNo;
    }

    public void setDeviceSerialNo(String deviceSerialNo) {
	this.deviceSerialNo = deviceSerialNo;
    }

    public String getIssueComment() {
	return issueComment;
    }

    public void setIssueComment(String issueComment) {
	this.issueComment = issueComment;
    }

    public String getResolutionComment() {
	return resolutionComment;
    }

    public void setResolutionComment(String resolutionComment) {
	this.resolutionComment = resolutionComment;
    }

    public String getProtectionPlanId() {
	return protectionPlanId;
    }

    public void setProtectionPlanId(String protectionPlanId) {
	this.protectionPlanId = protectionPlanId;
    }

    public String getTechCheckerIssues() {
	return techCheckerIssues;
    }

    public void setTechCheckerIssues(String techCheckerIssues) {
	this.techCheckerIssues = techCheckerIssues;
    }

    public String getCustomerBenefits() {
	return customerBenefits;
    }

    public void setCustomerBenefits(String customerBenefits) {
	this.customerBenefits = customerBenefits;
    }

    @Override
    public String toString() {
	return ReflectionToStringBuilder.toString(this);
    }

}
