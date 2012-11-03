package com.bestbuy.bbym.ise.drp.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.bestbuy.bbym.ise.drp.domain.Recommendation;

public class RecSheetReportingSearchResults implements Serializable {

    private static final long serialVersionUID = 1L;

    private String lastName;
    private String firstName;
    private String aid;

    private List<Recommendation> recs = new ArrayList<Recommendation>();

    public String getLastName() {
	return lastName;
    }

    public void setLastName(String lastName) {
	this.lastName = lastName;
    }

    public String getFirstName() {
	return firstName;
    }

    public void setFirstName(String firstName) {
	this.firstName = firstName;
    }

    public String getAid() {
	return aid;
    }

    public void setAid(String aid) {
	this.aid = aid;
    }

    public List<Recommendation> getRecs() {
	return recs;
    }

    public void setRecs(List<Recommendation> recs) {
	this.recs = recs;
    }

    public void addRecommendation(Recommendation rec) {
	this.recs.add(rec);

    }

    public String getSheetsNum() {
	return Integer.toString(recs.size());
    }

}
