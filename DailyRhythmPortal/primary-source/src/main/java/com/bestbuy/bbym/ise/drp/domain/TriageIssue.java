/**
 * Best Buy Inc (c) 2012
 */
package com.bestbuy.bbym.ise.drp.domain;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import com.bestbuy.bbym.ise.domain.BaseObject;

/**
 * Domain class for triage issue.
 * @author Yernar Khuangan (a1003810)
 * @version 1.0
 * @since iteration 21
 */
public class TriageIssue extends BaseObject {

    private static final long serialVersionUID = 1L;
    private Long id;
    private String issueDesc;
    private String tooltip;
    private int displayOrder;
    private boolean activeFlag;
    private TriageRecommendation selectedRecommendation;
    //for temporary storing
    private String issueComment;
    private String techCheckerIssue;

    public Long getId() {
	return id;
    }

    public void setId(Long issueId) {
	this.id = issueId;
    }

    public String getIssueDesc() {
	return issueDesc;
    }

    public void setIssueDesc(String issueDesc) {
	this.issueDesc = issueDesc;
    }

    public String getTooltip() {
	return tooltip;
    }

    public void setTooltip(String tooltip) {
	this.tooltip = tooltip;
    }

    public int getDisplayOrder() {
	return displayOrder;
    }

    public void setDisplayOrder(int displayOrder) {
	this.displayOrder = displayOrder;
    }

    public boolean isActiveFlag() {
	return activeFlag;
    }

    public void setActiveFlag(boolean activeFlag) {
	this.activeFlag = activeFlag;
    }

    public TriageRecommendation getSelectedRecommendation() {
	return selectedRecommendation;
    }

    public void setSelectedRecommendation(TriageRecommendation selectedRecommendation) {
	this.selectedRecommendation = selectedRecommendation;
    }

    public String getIssueComment() {
	return issueComment;
    }

    public void setIssueComment(String issueComment) {
	this.issueComment = issueComment;
    }

    public String getTechCheckerIssue() {
	return techCheckerIssue;
    }

    public void setTechCheckerIssue(String techCheckerIssue) {
	this.techCheckerIssue = techCheckerIssue;
    }

    @Override
    public String toString() {
	return ReflectionToStringBuilder.toString(this);
    }

}
