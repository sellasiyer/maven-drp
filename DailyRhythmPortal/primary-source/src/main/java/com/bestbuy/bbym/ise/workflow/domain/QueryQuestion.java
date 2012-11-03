package com.bestbuy.bbym.ise.workflow.domain;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

public class QueryQuestion extends BaseDataObject {

    private static final long serialVersionUID = -8283022144796542491L;

    private long id;
    private String name;
    private String instruction;
    private String question;
    private String type;
    private String detail;
    private String imageUrl;
    private String displayType;

    public long getId() {
	return id;
    }

    public void setId(long id) {
	this.id = id;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public String getInstruction() {
	return instruction;
    }

    public void setInstruction(String instruction) {
	this.instruction = instruction;
    }

    public String getQuestion() {
	return question;
    }

    public void setQuestion(String question) {
	this.question = question;
    }

    public String getType() {
	return type;
    }

    public void setType(String type) {
	this.type = type;
    }

    public void setDetail(String detail) {
	this.detail = detail;
    }

    public String getDetail() {
	return detail;
    }

    public void setImageUrl(String imageUrl) {
	this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
	return imageUrl;
    }

    public void setDisplayType(String displayType) {
	this.displayType = displayType;
    }

    public String getDisplayType() {
	return displayType;
    }

    public String toString() {
	return ReflectionToStringBuilder.toString(this);
    }
}
