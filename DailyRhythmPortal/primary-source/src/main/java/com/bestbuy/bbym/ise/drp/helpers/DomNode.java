package com.bestbuy.bbym.ise.drp.helpers;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DomNode {

    enum Type {
	NODE, NODE_LIST, STRING_VAL
    }

    private Type type = Type.NODE;
    private Node node;
    private NodeList nodeList;
    private String stringVal;

    public Type getType() {
	return type;
    }

    public void setType(Type type) {
	this.type = type;
    }

    public Node getNode() {
	return node;
    }

    public void setNode(Node node) {
	this.node = node;
    }

    public NodeList getNodeList() {
	return nodeList;
    }

    public void setNodeList(NodeList nodeList) {
	this.nodeList = nodeList;
    }

    public String getStringVal() {
	return stringVal;
    }

    public void setStringVal(String stringVal) {
	this.stringVal = stringVal;
    }
}
