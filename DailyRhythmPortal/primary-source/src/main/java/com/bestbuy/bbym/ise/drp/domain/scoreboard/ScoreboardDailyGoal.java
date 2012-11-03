/**
 * Best Buy Inc (c) 2012
 */
package com.bestbuy.bbym.ise.drp.domain.scoreboard;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import com.bestbuy.bbym.ise.domain.BaseObject;

/**
 * Domain class for storing store daily goals.
 * 
 * @author Yernar Khuangan (a1003810)
 * @version 1.0
 * @since iteration 25
 */
public class ScoreboardDailyGoal extends BaseObject {

    private static final long serialVersionUID = -4275162930666038853L;

    private Long id;
    private Long goalTypeId;
    private ScoreboardWidget.Type goalType;
    private String name;
    private Long goalValue;
    private Long defaultValue;
    private Long minValue;
    private Long maxValue;

    public ScoreboardDailyGoal() {
    }

    public ScoreboardWidget.Type getGoalType() {
	return goalType;
    }

    public void setGoalType(ScoreboardWidget.Type goalType) {
	this.goalType = goalType;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public Long getGoalValue() {
	return goalValue;
    }

    public void setGoalValue(Long goalValue) {
	this.goalValue = goalValue;
    }

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public Long getGoalTypeId() {
	return goalTypeId;
    }

    public void setGoalTypeId(Long goalTypeId) {
	this.goalTypeId = goalTypeId;
    }

    public Long getDefaultValue() {
	return defaultValue;
    }

    public void setDefaultValue(Long defaultValue) {
	this.defaultValue = defaultValue;
    }

    public Long getMinValue() {
	return minValue;
    }

    public void setMinValue(Long minValue) {
	this.minValue = minValue;
    }

    public Long getMaxValue() {
	return maxValue;
    }

    public void setMaxValue(Long maxValue) {
	this.maxValue = maxValue;
    }

    @Override
    public String toString() {
	return ReflectionToStringBuilder.toString(this);
    }

}
