package com.bestbuy.bbym.ise.drp.domain.ui;

public interface UIComponent extends UIElement {

    public Long getId();

    public enum Type {
	LIST_ROW, LIST, LINK, LABEL, IMAGE;
    }

    public Type getType();
}
