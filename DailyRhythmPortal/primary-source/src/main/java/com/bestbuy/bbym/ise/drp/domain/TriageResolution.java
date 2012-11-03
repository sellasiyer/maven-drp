/**
 * Best Buy Inc (c) 2012
 */
package com.bestbuy.bbym.ise.drp.domain;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import com.bestbuy.bbym.ise.domain.BaseObject;

/**
 * Domain class for triage resolution code.
 * 
 * @author Yernar Khuangan (a1003810)
 * @version 1.0
 * @since iteration 21
 */
public class TriageResolution extends BaseObject {

    private static final long serialVersionUID = 8156868147184702797L;

    private Long id;
    private String resolutionDesc;
    private int displayOrder;
    private boolean activeFlag;

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public String getResolutionDesc() {
	return resolutionDesc;
    }

    public void setResolutionDesc(String resolutionDesc) {
	this.resolutionDesc = resolutionDesc;
    }

    public int getDisplayOrder() {
	return displayOrder;
    }

    public void setDisplayOrder(int displayOrder) {
	this.displayOrder = displayOrder;
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
