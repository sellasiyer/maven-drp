package com.bestbuy.bbym.ise.drp.domain.scoreboard;

import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import com.bestbuy.bbym.ise.domain.BaseObject;
import com.bestbuy.bbym.ise.drp.domain.scoreboard.ScoreboardWidget;

/**
 * Domain class for ScoarBoard's Landing Page.
 * 
 * @author (a924357) get storeId from session
 * @version 1.0
 * @since iteration 25
 */

public class ScoreboardData extends BaseObject {

    private static final long serialVersionUID = -2857972111851758063L;

    private ScoreboardNotionalMargin scoreboardNotionalMargin;
    // calculate top employee from employeeNotionalMarginList
    private List<ScoreboardEmployeeNotionalMargin> employeeNotionalMarginList;
    private List<ScoreboardWidget> widgetList;

    public ScoreboardNotionalMargin getScoreboardNotionalMargin() {
	return scoreboardNotionalMargin;
    }

    public void setScoreboardNotionalMargin(ScoreboardNotionalMargin scoreboardNotionalMargin) {
	this.scoreboardNotionalMargin = scoreboardNotionalMargin;
    }

    public List<ScoreboardEmployeeNotionalMargin> getEmployeeNotionalMarginList() {
	return employeeNotionalMarginList;
    }

    public void setEmployeeNotionalMarginList(List<ScoreboardEmployeeNotionalMargin> employeeNotionalMarginList) {
	this.employeeNotionalMarginList = employeeNotionalMarginList;
    }

    public List<ScoreboardWidget> getWidgetList() {
	return widgetList;
    }

    public void setWidgetList(List<ScoreboardWidget> widgetList) {
	this.widgetList = widgetList;
    }

    @Override
    public String toString() {
	return ReflectionToStringBuilder.toString(this);
    }

}
