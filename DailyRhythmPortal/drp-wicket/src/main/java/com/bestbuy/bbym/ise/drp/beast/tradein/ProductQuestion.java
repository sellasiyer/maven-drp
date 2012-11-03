package com.bestbuy.bbym.ise.drp.beast.tradein;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.wicket.IClusterable;
import org.apache.wicket.validation.validator.AbstractValidator;

import com.bestbuy.bbym.ise.drp.domain.ui.UIDataItem;
import com.bestbuy.bbym.ise.drp.domain.ui.UIDataList;
import com.bestbuy.bbym.ise.drp.util.SelectItem;

public class ProductQuestion implements IClusterable {

    private String name;
    private String title;
    private String value;
    private String qName;
    private Date date;

    private List<SelectItem<String>> options = new ArrayList<SelectItem<String>>();
    private List<AbstractValidator> validators = new ArrayList<AbstractValidator>();
    private SelectItem<String> selectOption;

    public void markOptionsAsSelected(String key) {
	if (StringUtils.isEmpty(key)){
	    return;
	}

	for(SelectItem<String> current: options){
	    if (key.equalsIgnoreCase(current.getKey())){
		selectOption = current;
	    }
	}
    }

    public SelectItem<String> getSelectOption() {
	return selectOption;
    }

    public void setSelectOption(SelectItem<String> selectOption) {
	this.selectOption = selectOption;
    }

    public List<AbstractValidator> getValidators() {

	return validators;
    }

    private QuestionType type;
    private UIDataItem uiDataItem;

    public UIDataItem getUiDataItem() {
	return uiDataItem;
    }

    public void setUiDataItem(UIDataItem uiDataItem) {
	this.uiDataItem = uiDataItem;
    }

    public void addValidator(AbstractValidator validator) {
	validators.add(validator);
    }

    public void populateOptions(UIDataList list) {

	if (list == null || list.size() == 0){
	    return;
	}

	for(int i = 0; i < list.size(); i++){
	    SelectItem<String> selectOption = new SelectItem<String>(((UIDataItem) list.get(i)).getStringProp("Value",
		    null), ((UIDataItem) list.get(i)).getStringProp("Name", null));
	    options.add(selectOption);
	}
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public String getTitle() {
	return title;
    }

    public void setTitle(String title) {
	this.title = title;
    }

    public String getValue() {
	return value;
    }

    public void setValue(String value) {
	this.value = value;
    }

    public List<SelectItem<String>> getOptions() {
	return options;
    }

    public void setOptions(List<SelectItem<String>> options) {
	this.options = options;
    }

    public QuestionType getType() {
	return type;
    }

    public void setType(QuestionType type) {
	this.type = type;
    }

    public String getqName() {
	return qName;
    }

    public void setqName(String qName) {
	this.qName = qName;
    }

    public Date getDate() {
	return date;
    }

    public void setDate(Date date) {
	this.date = date;
    }

}
