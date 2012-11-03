/**
 * Best Buy Inc (c) 2012
 */
package com.bestbuy.bbym.ise.drp.domain;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import com.bestbuy.bbym.ise.domain.BaseObject;

/**
 * Domain class for triage recommendation.
 * @author Yernar Khuangan (a1003810)
 * @version 1.0
 * @since iteration 21
 */
public class TriageRecommendation extends BaseObject {

    private static final long serialVersionUID = 6937185284509885519L;
    private Long id;
    private String recommendation;
    private Long triageIssueId;
    private String issueDisplayText;

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public String getRecommendation() {
	return recommendation;
    }

    public void setRecommendation(String recommendation) {
	this.recommendation = recommendation;
    }

    public Long getTriageIssueId() {
	return triageIssueId;
    }

    public void setTriageIssueId(Long triageIssueId) {
	this.triageIssueId = triageIssueId;
    }

    public String getIssueDisplayText() {
	return issueDisplayText;
    }

    public void setIssueDisplayText(String issueDisplayText) {
	this.issueDisplayText = issueDisplayText;
    }

    @Override
    public String toString() {
	return ReflectionToStringBuilder.toString(this);
    }

}
