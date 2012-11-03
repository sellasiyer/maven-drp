package com.bestbuy.bbym.ise.drp.domain.ui;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bestbuy.bbym.ise.util.Util;

public class UINode implements Serializable {

    public enum Type {
	UNKNOWN("UNK"), NODE("ND"), LEAF("LF"), LIST("LS");

	private String code;

	private Type(String code) {
	    this.code = code;
	}

	public String getCode() {
	    return code;
	}
    }

    public static Type determineType(String code) {
	for(Type nt: Type.values()){
	    if (nt.getCode().equals(code)){
		return nt;
	    }
	}
	return Type.UNKNOWN;
    }

    private static final long serialVersionUID = 1L;

    private String name;
    private Type type = Type.UNKNOWN;
    private String value;
    private Map<String, UINode> nodeMap;
    private List<UINode> nodeList;

    public UINode(String name, Type type) {
	setName(name);
	setType(type);
    }

    public UINode(String name, String value, Type type) {
	setName(name);
	setValue(value);
	setType(type);
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public Type getType() {
	return type;
    }

    public void setType(Type type) {
	this.type = type;
    }

    public String getValue() {
	return value;
    }

    public void setValue(String value) {
	this.value = value;
    }

    public void addMapNode(UINode node) {
	if (node == null){
	    return;
	}
	if (nodeMap == null){
	    nodeMap = new HashMap<String, UINode>();
	}
	nodeMap.put(node.getName(), node);
    }

    public void addListNode(UINode node) {
	if (node == null){
	    return;
	}
	if (nodeList == null){
	    nodeList = new ArrayList<UINode>();
	}
	nodeList.add(node);
    }

    public UINode getMapNode(String name) {
	if (nodeMap == null || name == null){
	    return null;
	}
	return nodeMap.get(name);
    }

    public String getMapNodeValue(String name) {
	if (nodeMap == null || name == null){
	    return null;
	}
	UINode uiNode = nodeMap.get(name);
	if (uiNode != null){
	    return uiNode.getValue();
	}
	return null;
    }

    public List<UINode> getNodeList() {
	return nodeList;
    }

    @Override
    public String toString() {
	StringBuilder sb = new StringBuilder();
	sb.append("UINode[name=");
	sb.append(name);
	sb.append(" type=");
	sb.append(type.getCode());
	switch (type) {
	    case LEAF:
		sb.append(" value=");
		sb.append(value);
		break;
	    case NODE:
		if (value != null){
		    sb.append(" value=");
		    sb.append(value);
		}
		sb.append(" [");
		sb.append(Util.toStringMap(nodeMap));
		sb.append("]");
		break;
	    case LIST:
		if (value != null){
		    sb.append(" value=");
		    sb.append(value);
		}
		sb.append(" [");
		sb.append(Util.toStringList(nodeList));
		sb.append("]");
		break;
	}
	sb.append("]");
	return sb.toString();
    }
}
