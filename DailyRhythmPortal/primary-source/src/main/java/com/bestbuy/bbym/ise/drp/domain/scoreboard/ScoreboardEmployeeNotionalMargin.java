/**
 * Best Buy Inc (c) 2012
 */
package com.bestbuy.bbym.ise.drp.domain.scoreboard;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

/**
 * @author Yernar Khuangan (a1003810)
 * @version 1.0
 * @since iteration 25
 */
public class ScoreboardEmployeeNotionalMargin extends ScoreboardNotionalMargin {

    private static final long serialVersionUID = 3218987789186273482L;

    private ScoreboardEmployeeShift employeeShift;

    public ScoreboardEmployeeShift getEmployeeShift() {
	return employeeShift;
    }

    public void setEmployeeShift(ScoreboardEmployeeShift employeeShift) {
	this.employeeShift = employeeShift;
    }

    @Override
    public String toString() {
	return ReflectionToStringBuilder.toString(this);
    }
}
