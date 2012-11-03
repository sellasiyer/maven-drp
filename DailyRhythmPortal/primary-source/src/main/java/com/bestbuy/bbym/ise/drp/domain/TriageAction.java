/**
 * Best Buy Inc (c) 2012
 */
package com.bestbuy.bbym.ise.drp.domain;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import com.bestbuy.bbym.ise.domain.BaseObject;

/**
 * Domain class for triage action.
 * @author Yernar Khuangan (a1003810)
 * @version 1.0
 * @since iteration 21
 */
public class TriageAction extends BaseObject {

    private static final long serialVersionUID = -2815719215759206570L;
    private Long id;
    private String action;
    private boolean activeFlag;

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public String getAction() {
	return action;
    }

    public void setAction(String action) {
	this.action = action;
    }

    public boolean isActiveFlag() {
	return activeFlag;
    }

    public void setActiveFlag(boolean activeFlag) {
	this.activeFlag = activeFlag;
    }

    @Override
    public String toString() {
	return ReflectionToStringBuilder.toString(this);
    }

}
