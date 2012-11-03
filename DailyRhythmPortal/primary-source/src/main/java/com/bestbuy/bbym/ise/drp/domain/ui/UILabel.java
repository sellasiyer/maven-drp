package com.bestbuy.bbym.ise.drp.domain.ui;

public class UILabel extends UIBaseComponent {

    private static final long serialVersionUID = 1L;

    private String value;

    public String getValue() {
	return value;
    }

    public void setValue(String value) {
	this.value = value;
    }

    @Override
    public Type getType() {
	return UIComponent.Type.LABEL;
    }

    @Override
    public String toString() {
	StringBuilder sb = new StringBuilder();
	sb.append("UILabel[value=");
	sb.append(value);
	sb.append(" ");
	sb.append(super.toString());
	sb.append("]");
	return sb.toString();
    }
}
