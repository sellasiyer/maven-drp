package com.bestbuy.bbym.ise.drp.domain.ui;

public abstract class UIBaseComponent implements UIComponent {

    private static final long serialVersionUID = 1L;

    private String name;
    private Long id;

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public abstract Type getType();

    public boolean isComponent() {
	return true;
    }

    public boolean isData() {
	return false;
    }

    @Override
    public String toString() {
	StringBuilder sb = new StringBuilder();
	sb.append("[name=");
	sb.append(name);
	sb.append(" id=");
	sb.append(id);
	sb.append(" type=");
	sb.append(getType());
	sb.append("]");
	return sb.toString();
    }
}
