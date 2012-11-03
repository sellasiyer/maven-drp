package com.bestbuy.bbym.ise.drp.domain;

import java.io.Serializable;

/**
 * @author a915724
 */
public class RecSheetCountByDay implements Serializable {

    private static final long serialVersionUID = 4138982543770676657L;

    private String storeId;
    private long recommendationId;
    private String firstName;
    private String lastName;
    private String createBy;
    private String changeDate;
    private String countByDay;
    private String aid;

    public String getAid() {
	return aid;
    }

    public void setAid(String aid) {
	this.aid = aid;
    }

    public String getStoreId() {
	return storeId;
    }

    public void setStoreId(String storeId) {
	this.storeId = storeId;
    }

    public long getRecommendationId() {
	return recommendationId;
    }

    public void setRecommendationId(long recommendationId) {
	this.recommendationId = recommendationId;
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

    public String getCreateBy() {
	return createBy;
    }

    public void setCreateBy(String createBy) {
	this.createBy = createBy;
    }

    public String getChangeDate() {
	return changeDate;
    }

    public void setChangeDate(String changeDate) {
	this.changeDate = changeDate;
    }

    public String getCountByDay() {
	return countByDay;
    }

    public void setCountByDay(String countByDay) {
	this.countByDay = countByDay;
    }

}
