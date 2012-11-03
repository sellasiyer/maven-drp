package com.bestbuy.bbym.ise.drp.domain.ui;

public interface UIData extends UIElement {

    public enum Type {
	ITEM, LIST, MAP, REQUEST, REPLY
    }

    public Type getType();
}
