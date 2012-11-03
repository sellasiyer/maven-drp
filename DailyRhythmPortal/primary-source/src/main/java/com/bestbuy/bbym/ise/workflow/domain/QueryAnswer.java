package com.bestbuy.bbym.ise.workflow.domain;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

public class QueryAnswer extends BaseDataObject {

    private static final long serialVersionUID = 7275681933815022771L;

    private long id;
    private String name;
    private String answer;

    public long getId() {
	return id;
    }

    public void setId(long id) {
	this.id = id;
    }

    public void setName(String name) {
	this.name = name;
    }

    public String getName() {
	return name;
    }

    public String getAnswer() {
	return answer;
    }

    public void setAnswer(String answer) {
	this.answer = answer;
    }

    public String toString() {
	return ReflectionToStringBuilder.toString(this);
    }
}
