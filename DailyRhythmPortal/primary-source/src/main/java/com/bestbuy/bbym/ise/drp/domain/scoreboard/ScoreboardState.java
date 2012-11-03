package com.bestbuy.bbym.ise.drp.domain.scoreboard;

import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import com.bestbuy.bbym.ise.domain.BaseObject;

/**
 * Domain class for SbTransactionType.
 * 
 * @author (a924357)
 * @version 1.0
 * @since iteration 25
 */

public class ScoreboardState extends BaseObject {

    private static final long serialVersionUID = 8286022266568359089L;

    private List<ScoreboardCategory> sbCategoryList;
    private ScoreboardEmployeeShift employee;

    public List<ScoreboardCategory> getSbCategoryList() {
	return sbCategoryList;
    }

    public void setSbCategoryList(List<ScoreboardCategory> sbCategoryList) {
	this.sbCategoryList = sbCategoryList;
    }

    public ScoreboardEmployeeShift getEmployee() {
	return employee;
    }

    public void setEmployee(ScoreboardEmployeeShift employee) {
	this.employee = employee;
    }

    @Override
    public String toString() {
	return ReflectionToStringBuilder.toString(this);
    }

}
