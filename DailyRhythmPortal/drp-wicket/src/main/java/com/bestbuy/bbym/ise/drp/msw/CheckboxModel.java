package com.bestbuy.bbym.ise.drp.msw;

import org.apache.wicket.model.IModel;

import com.bestbuy.bbym.ise.drp.domain.Recommendation;

public class CheckboxModel implements IModel {

    private static final long serialVersionUID = 1L;

    private Recommendation modelObject;
    private String property;
    private String key;

    //may rewrite this as an inline model on the digitalrecsheetpage.
    public CheckboxModel(Recommendation modelObject, String property, String key) {
	this.modelObject = modelObject;
	this.property = property;
	this.key = key.toString();
    }

    @Override
    public void detach() {

    }

    @Override
    public Object getObject() {

	if (this.property.equals("deviceCapabilities")){
	    return modelObject.getDeviceCapabilities(key);
	}else if (this.property.equals("wowRequirements")){
	    return modelObject.getWowRequirements(key);
	}

	return null;
    }

    @Override
    public void setObject(Object object) {
	Boolean b = (Boolean) object;
	if (this.property.equals("deviceCapabilities")){
	    modelObject.setDeviceCapabilities(key, b);
	}else if (this.property.equals("wowRequirements")){
	    modelObject.setWowRequirements(key, b);
	}

    }

}
