package com.bestbuy.bbym.ise.drp.domain.ui;

import java.io.Serializable;

public interface UIElement extends Serializable {

    public String getName();

    public void setName(String name);

    public boolean isComponent();

    public boolean isData();
}
