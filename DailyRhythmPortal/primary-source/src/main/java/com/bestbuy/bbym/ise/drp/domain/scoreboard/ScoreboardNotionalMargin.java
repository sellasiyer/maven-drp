package com.bestbuy.bbym.ise.drp.domain.scoreboard;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import com.bestbuy.bbym.ise.domain.BaseObject;

/**
 * Domain class for score board employee data.
 * 
 * @author (a924357)
 * @version 1.0
 * @since iteration 25
 */
public class ScoreboardNotionalMargin extends BaseObject {

    private static final long serialVersionUID = -7961903654643001794L;

    private int currentValue;
    private int targetValue;
    private int productivity;
    private String nextHour;
    private int nextHourPercentage;

    public int getCurrentValue() {
	return currentValue;
    }

    public void setCurrentValue(int currentValue) {
	this.currentValue = currentValue;
    }

    public int getTargetValue() {
	return targetValue;
    }

    public void setTargetValue(int targetValue) {
	this.targetValue = targetValue;
    }

    public int getProductivity() {
	return productivity;
    }

    public void setProductivity(int productivity) {
	this.productivity = productivity;
    }

    public String getNextHour() {
	return nextHour;
    }

    public void setNextHour(String nextHour) {
	this.nextHour = nextHour;
    }

    public int getNextHourPercentage() {
	return nextHourPercentage;
    }

    public void setNextHourPercentage(int nextHourPercentage) {
	this.nextHourPercentage = nextHourPercentage;
    }

    @Override
    public String toString() {
	return ReflectionToStringBuilder.toString(this);
    }

}
