package com.bestbuy.bbym.ise.drp.domain.scoreboard;

import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import com.bestbuy.bbym.ise.domain.BaseObject;

public class ScoreboardEmployeeDistribution extends BaseObject {

    private static final long serialVersionUID = 213407793172843879L;
    private List<ScoreboardEmployeeNotionalMargin> scoreboardEmployeeScheduleList;
    private List<ScoreboardDailyGoal> scoreboardDailyGoalList;
    private Float totalHours;

    public Float getTotalHours() {
	return totalHours;
    }

    public void setTotalHours(Float totalHours) {
	this.totalHours = totalHours;
    }

    public List<ScoreboardEmployeeNotionalMargin> getScoreboardEmployeeScheduleList() {
	return scoreboardEmployeeScheduleList;
    }

    public void setScoreboardEmployeeScheduleList(List<ScoreboardEmployeeNotionalMargin> scoreboardEmployeeScheduleList) {
	this.scoreboardEmployeeScheduleList = scoreboardEmployeeScheduleList;
    }

    public List<ScoreboardDailyGoal> getScoreboardDailyGoal() {
	return scoreboardDailyGoalList;
    }

    public void setScoreboardDailyGoal(List<ScoreboardDailyGoal> scoreboardDailyGoal) {
	this.scoreboardDailyGoalList = scoreboardDailyGoal;
    }

    public String toString() {
	return ReflectionToStringBuilder.toString(this);
    }

}
