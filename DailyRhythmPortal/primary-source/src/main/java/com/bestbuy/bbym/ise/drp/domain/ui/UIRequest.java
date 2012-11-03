package com.bestbuy.bbym.ise.drp.domain.ui;

import java.util.HashMap;

import org.w3c.dom.Document;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;

import com.bestbuy.bbym.ise.drp.domain.TitanDevice;
import com.bestbuy.bbym.ise.util.Util;

public class UIRequest extends HashMap<String, UIElement> implements UIData {

    private static final long serialVersionUID = 1L;

    private String name;
    private Document domObject;
    private TitanDevice titanDevice;

    public Document getDomObject() {
	return domObject;
    }

    public void setDomObject(Document domObject) {
	this.domObject = domObject;
    }

    public TitanDevice getTitanDevice() {
	return titanDevice;
    }

    public void setTitanDevice(TitanDevice titanDevice) {
	this.titanDevice = titanDevice;
    }

    @Override
    public String getName() {
	return name;
    }

    @Override
    public void setName(String name) {
	this.name = name;
    }

    @Override
    public Type getType() {
	return UIData.Type.REQUEST;
    }

    @Override
    public boolean isComponent() {
	return false;
    }

    @Override
    public boolean isData() {
	return true;
    }

    @Override
    public String toString() {
	StringBuilder sb = new StringBuilder();
	sb.append("UIRequest[name=");
	sb.append(name);
	sb.append(" [");
	sb.append(Util.toStringMap(this));
	sb.append("]");
	if (domObject != null){
	    sb.append(" [Dom Object= ");
	    sb.append(getStringFromDoc(domObject));
	    sb.append("]");
	}
	if (titanDevice != null){
	    sb.append(" [Titan Device= ");
	    sb.append(titanDevice.toString());
	    sb.append("]");
	}
	sb.append("]");
	return sb.toString();
    }

    public String getStringFromDoc(Document doc) {
	DOMImplementationLS domImplementation = (DOMImplementationLS) doc.getImplementation();
	LSSerializer lsSerializer = domImplementation.createLSSerializer();
	return lsSerializer.writeToString(doc);
    }
}
