package com.bestbuy.bbym.ise.drp.domain;

import java.util.List;
import java.util.Map;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * DRPDomObject
 *
 * @author a904002
 */
@XmlRootElement
@XmlType(propOrder = {"name", "attributeMap", "metadata", "value", "childList" })
public class DRPDomObject {

    private String name;
    private Map<String, DRPDomObject> attributeMap;
    private DRPDomObject metadata;
    private String value;

    private List<DRPDomObject> childList;

    public DRPDomObject() {
    }

    public List<DRPDomObject> getChildList() {
	return childList;
    }

    public void setChildList(List<DRPDomObject> childList) {
	this.childList = childList;
    }

    public Map<String, DRPDomObject> getAttributeMap() {
	return attributeMap;
    }

    public void setAttributeMap(Map<String, DRPDomObject> attributeMap) {
	this.attributeMap = attributeMap;
    }

    public String getValue() {
	return value;
    }

    public void setValue(String value) {
	this.value = value;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public DRPDomObject getMetadata() {
	return metadata;
    }

    public void setMetadata(DRPDomObject metadata) {
	this.metadata = metadata;
    }

    @Override
    public String toString() {
	return "DRPDomObject{" + "name=" + name + ", attributeMap=" + attributeMap + ", metadata=" + metadata
		+ ", value=" + value + ", childList=" + childList + '}';
    }

}
