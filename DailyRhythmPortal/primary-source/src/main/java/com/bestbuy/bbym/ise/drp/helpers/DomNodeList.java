package com.bestbuy.bbym.ise.drp.helpers;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DomNodeList implements NodeList {

    private List<Node> nodeList = new ArrayList<Node>();

    @Override
    public Node item(int index) {
	return nodeList.get(index);
    }

    @Override
    public int getLength() {
	return nodeList.size();
    }

    public void addNode(Node node) {
	if (node == null){
	    return;
	}
	nodeList.add(node);
    }
}
