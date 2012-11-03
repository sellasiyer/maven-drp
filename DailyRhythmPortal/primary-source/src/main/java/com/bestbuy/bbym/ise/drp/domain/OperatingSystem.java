package com.bestbuy.bbym.ise.drp.domain;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import com.bestbuy.bbym.ise.domain.BaseObject;

public class OperatingSystem extends BaseObject {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String os;
    private String instruction;

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public String getOs() {
	return os;
    }

    public void setOs(String os) {
	this.os = os;
    }

    public String getInstruction() {
	return instruction;
    }

    public void setInstruction(String instruction) {
	this.instruction = instruction;
    }

    @Override
    public String toString() {
	return ReflectionToStringBuilder.toString(this);
    }

    //this can be removed after the lookup tables are setup
    public enum OSTypes {

	ANDROID("Android"), BLACKBERRY("BlackBerry"), IOS("iOS"), WINDOWS("Windows");

	private final String label;

	private OSTypes(String label) {
	    this.label = label;
	}

	@Override
	public String toString() {
	    return label;
	}

    }
}
