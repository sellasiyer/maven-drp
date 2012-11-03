package com.bestbuy.bbym.ise.drp.domain.scoreboard;

import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import com.bestbuy.bbym.ise.domain.BaseObject;

/**
 * Domain class for score board widgets(Tachometers)
 * 
 * @author (a924357)
 * @version 1.0
 * @since iteration 25
 */

public class ScoreboardWidget extends BaseObject {

    public enum Type {
	NUMERIC, PERCENTAGE, DOLLARVALUE;
    }

    private static final long serialVersionUID = -262558452371185304L;

    private Long id;
    private String widgetName;
    private int currentVal;
    private int targetVal;
    private Type widgetType;
    private List<ScoreboardWidget> widgetAttributes;

    public String getWidgetName() {
	return widgetName;
    }

    public void setWidgetName(String widgetName) {
	this.widgetName = widgetName;
    }

    public int getCurrentVal() {
	return currentVal;
    }

    public void setCurrentVal(int currentVal) {
	this.currentVal = currentVal;
    }

    public int getTargetVal() {
	return targetVal;
    }

    public void setTargetVal(int targetVal) {
	this.targetVal = targetVal;
    }

    public Type getWidgetType() {
	return widgetType;
    }

    public void setWidgetType(Type widgetType) {
	this.widgetType = widgetType;
    }

    public List<ScoreboardWidget> getWidgetAttributes() {
	return widgetAttributes;
    }

    public void setWidgetAttributes(List<ScoreboardWidget> widgetAttributes) {
	this.widgetAttributes = widgetAttributes;
    }

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    @Override
    public String toString() {
	return ReflectionToStringBuilder.toString(this);
    }

}
