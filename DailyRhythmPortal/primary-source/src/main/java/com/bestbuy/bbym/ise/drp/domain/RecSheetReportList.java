package com.bestbuy.bbym.ise.drp.domain;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author a915724
 * 
 */
public class RecSheetReportList implements Serializable {

    private static final long serialVersionUID = 4138982543770676647L;

    private String storeId;
    private List<Long> recommendationIdList;
    private String firstName;
    private String lastName;
    private String createBy;
    private Map<String, String> dateMap;
    private String aid;

    public String getStoreId() {
	return storeId;
    }

    public void setStoreId(String storeId) {
	this.storeId = storeId;
    }

    public List<Long> getRecommendationIdList() {
	return recommendationIdList;
    }

    public void setRecommendationIdList(List<Long> recommendationIdList) {
	this.recommendationIdList = recommendationIdList;
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

    public Map<String, String> getDateMap() {
	return dateMap;
    }

    public void setDateMap(Map<String, String> dateMap) {
	this.dateMap = dateMap;
    }

    public String getAid() {
	return aid;
    }

    public void setAid(String aid) {
	this.aid = aid;
    }

}
