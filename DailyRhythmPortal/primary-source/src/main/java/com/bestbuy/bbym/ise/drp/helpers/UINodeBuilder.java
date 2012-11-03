package com.bestbuy.bbym.ise.drp.helpers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.bestbuy.bbym.ise.drp.domain.ui.UIConfig;
import com.bestbuy.bbym.ise.drp.domain.ui.UINode;
import com.bestbuy.bbym.ise.drp.helpers.DomInfo.DomAccess;
import com.bestbuy.bbym.ise.drp.helpers.DomInfo.DomCondition;
import com.bestbuy.bbym.ise.exception.IseBusinessException;
import com.bestbuy.bbym.ise.util.Util;

public class UINodeBuilder {

    private static Logger logger = LoggerFactory.getLogger(UINodeBuilder.class);

    private UINodeBuilder() {
    }

    public static UINode create(UIConfig uiConfig, Document doc) throws IseBusinessException {
	if (uiConfig == null){
	    throw new IseBusinessException("Invalid input uiConfig");
	}
	if (doc == null){
	    throw new IseBusinessException("Invalid input DOM doc");
	}
	// Set name of reply UINode from top element node name
	Element topElem = doc.getDocumentElement();
	DomInfo replyDomInfo = uiConfig.getReplyDomInfo(topElem.getNodeName());
	DomNode requestDomNode = new DomNode();
	requestDomNode.setType(DomNode.Type.NODE);
	UINode replyUINode = create("Reply", topElem.getNodeName(), replyDomInfo, requestDomNode, doc);
	return replyUINode;
    }

    private static UINode create(String name, String value, DomInfo domInfo, DomNode parentDomNode, Document doc)
	    throws IseBusinessException {
	Node node;
	DomNode domNode;
	UINode thisNode = new UINode(name, value, UINode.Type.UNKNOWN), mapItemNode, listItemNode;

	DomNode thisDomNode = getDomNode(parentDomNode, domInfo, doc);
	if (thisDomNode == null){
	    return thisNode;
	}

	if (domInfo.getType() == UINode.Type.LEAF){
	    if (thisDomNode.getType() != DomNode.Type.STRING_VAL){
		throw new IseBusinessException("Leaf Node must return String:" + (domInfo.getDotName() + ".DOM"));
	    }
	    thisNode.setType(UINode.Type.LEAF);
	    thisNode.setValue(thisDomNode.getStringVal());
	    return thisNode;

	}else if (domInfo.getType() == UINode.Type.LIST){
	    if (thisDomNode.getType() != DomNode.Type.NODE_LIST){
		throw new IseBusinessException("List Node must return NodeList:" + (domInfo.getDotName() + ".DOM"));
	    }
	    thisNode.setType(UINode.Type.LIST);
	    if (domInfo.getDomInfoMap() != null){
		if (thisDomNode.getNodeList() != null){
		    for(int i = 0; i < thisDomNode.getNodeList().getLength(); i++){
			node = thisDomNode.getNodeList().item(i);
			domNode = new DomNode();
			domNode.setNode(node);
			domNode.setType(DomNode.Type.NODE);
			listItemNode = new UINode(String.valueOf(i), UINode.Type.NODE);
			for(DomInfo di: domInfo.getDomInfoMap().values()){
			    mapItemNode = create(di.getName(), null, di, domNode, doc);
			    listItemNode.addMapNode(mapItemNode);
			}
			thisNode.addListNode(listItemNode);
		    }
		}
	    }

	}else if (domInfo.getType() == UINode.Type.NODE){
	    thisNode.setType(UINode.Type.NODE);
	    if (domInfo.getDomInfoMap() != null){
		for(DomInfo di: domInfo.getDomInfoMap().values()){
		    mapItemNode = create(di.getName(), null, di, thisDomNode, doc);
		    thisNode.addMapNode(mapItemNode);
		}
	    }

	}else{
	    throw new IseBusinessException("Cannot handle DomInfo type " + domInfo.getType());
	}
	return thisNode;
    }

    private static DomNode getDomNode(DomNode parentDomNode, DomInfo domInfo, Document doc) throws IseBusinessException {
	if (domInfo.getDomAccessList() == null || domInfo.getDomAccessList().isEmpty()){
	    return parentDomNode;
	}
	logger.trace("**********");
	logger.trace("Get DomNode for " + domInfo.getDotName());
	DomNode prevDomNode = parentDomNode, nextDomNode;
	for(DomInfo.DomAccess da: domInfo.getDomAccessList()){
	    nextDomNode = getNextDomNode(prevDomNode, da, doc);
	    if (nextDomNode == null){
		return null;
	    }
	    prevDomNode = nextDomNode;
	}
	return prevDomNode;
    }

    private static DomNode getNextDomNode(DomNode prevDomNode, DomAccess domAccess, Document doc)
	    throws IseBusinessException {
	DomNode nextDomNode = new DomNode();

	DomInfo.DomCondition firstDomCond = null;
	if (domAccess.getDomCondList() != null && !domAccess.getDomCondList().isEmpty()){
	    firstDomCond = domAccess.getDomCondList().get(0);
	}

	if (firstDomCond != null && firstDomCond.getConditionType() == DomInfo.ConditionType.TAG){
	    if (prevDomNode == null || prevDomNode.getType() != DomNode.Type.NODE || prevDomNode.getNode() != null){
		throw new IseBusinessException("Cannot handle DomAccess condition type " + DomInfo.ConditionType.TAG
			+ " unless previous DomNode is Node with null value");
	    }
	    if (domAccess.getDomCondList().size() > 1){
		throw new IseBusinessException("Cannot handle DomAccess condition type " + DomInfo.ConditionType.TAG
			+ " if there is more than one DomCondition per DomAccess");
	    }
	    if (domAccess.getAccessType() != DomInfo.AccessType.NODE_LIST){
		throw new IseBusinessException("Cannot handle DomAccess condition type " + DomInfo.ConditionType.TAG
			+ " unless DomAccess access type is " + DomInfo.AccessType.NODE_LIST);
	    }
	    nextDomNode.setType(DomNode.Type.NODE_LIST);
	    DomNodeList nl;
	    NodeList childNodes;
	    Node childNode;
	    if (firstDomCond.getCompareType() == DomInfo.CompareType.EQUALS){
		if (doc.getFirstChild() == null || doc.getFirstChild().getChildNodes() == null){
		    return null;
		}
		childNodes = doc.getFirstChild().getChildNodes();
		nl = new DomNodeList();
		for(int i = 0; i < childNodes.getLength(); i++){
		    childNode = childNodes.item(i);
		    if (childNode != null && childNode.getNodeName() != null
			    && childNode.getNodeName().equals(firstDomCond.getPattern())){
			nl.addNode(childNode);
		    }
		}
		nextDomNode.setNodeList(nl);

	    }else if (firstDomCond.getCompareType() == DomInfo.CompareType.ENDS_WITH){
		if (doc.getFirstChild() == null || doc.getFirstChild().getChildNodes() == null){
		    return null;
		}
		childNodes = doc.getFirstChild().getChildNodes();
		nl = new DomNodeList();
		for(int i = 0; i < childNodes.getLength(); i++){
		    childNode = childNodes.item(i);
		    if (childNode != null && childNode.getNodeName() != null
			    && childNode.getNodeName().endsWith(firstDomCond.getPattern())){
			nl.addNode(childNode);
		    }
		}
		nextDomNode.setNodeList(nl);

	    }else{
		throw new IseBusinessException("Cannot handle DomAccess condition type " + DomInfo.ConditionType.TAG
			+ " with DomCondition compare type " + firstDomCond.getCompareType());
	    }

	}else if (firstDomCond != null && firstDomCond.getConditionType() == DomInfo.ConditionType.INDEX){
	    if (prevDomNode == null || prevDomNode.getType() != DomNode.Type.NODE_LIST){
		throw new IseBusinessException("Cannot handle DomAccess condition type " + DomInfo.ConditionType.INDEX
			+ " unless previous DomNode is NodeList");
	    }
	    if (domAccess.getDomCondList().size() > 1){
		throw new IseBusinessException("Cannot handle DomAccess condition type " + DomInfo.ConditionType.INDEX
			+ " if there is more than one DomCondition per DomAccess");
	    }
	    if (domAccess.getAccessType() != DomInfo.AccessType.NODE){
		throw new IseBusinessException("Cannot handle DomAccess condition type " + DomInfo.ConditionType.INDEX
			+ " unless DomAccess access type is " + DomInfo.AccessType.NODE);
	    }
	    if (firstDomCond.getCompareType() != DomInfo.CompareType.EQUALS){
		throw new IseBusinessException("Cannot handle DomAccess condition type " + DomInfo.ConditionType.INDEX
			+ " unless DomCondition compare type is " + DomInfo.CompareType.EQUALS);
	    }
	    int index = Util.getInt(firstDomCond.getPattern(), -1);
	    if (index < 0){
		throw new IseBusinessException("Cannot handle DomAccess condition type " + DomInfo.ConditionType.INDEX
			+ " unless index value is >=0");
	    }
	    if (prevDomNode.getNodeList() == null){
		return null;
	    }
	    nextDomNode.setType(DomNode.Type.NODE);
	    if (index < prevDomNode.getNodeList().getLength()){
		nextDomNode.setNode(prevDomNode.getNodeList().item(index));
	    }

	}else if ((domAccess.getDomCondList() == null || domAccess.getDomCondList().isEmpty())
		&& domAccess.getAccessType() == DomInfo.AccessType.NODE_LIST){
	    if (prevDomNode == null || prevDomNode.getType() != DomNode.Type.NODE){
		throw new IseBusinessException("Cannot handle DomAccess access type " + DomInfo.AccessType.NODE_LIST
			+ " with empty DomAccess list unless previous DomNode is Node");
	    }
	    if (prevDomNode.getNode() == null){
		return null;
	    }
	    nextDomNode.setType(DomNode.Type.NODE_LIST);
	    nextDomNode.setNodeList(prevDomNode.getNode().getChildNodes());

	}else if (domAccess.getDomCondList() != null && !domAccess.getDomCondList().isEmpty()
		&& domAccess.getAccessType() == DomInfo.AccessType.NODE_LIST){
	    if (prevDomNode == null || prevDomNode.getType() != DomNode.Type.NODE){
		throw new IseBusinessException("Cannot handle DomAccess access type " + DomInfo.AccessType.NODE_LIST
			+ " with non-empty DomAccess list unless previous DomNode is Node");
	    }
	    if (prevDomNode.getNode() == null || prevDomNode.getNode().getChildNodes() == null){
		return null;
	    }
	    NodeList childNodes = prevDomNode.getNode().getChildNodes();
	    DomNodeList nl = new DomNodeList();
	    logger.trace("**********");
	    logger.trace("Looping thru children of node to build nodeList");
	    logger.trace(domAccess.toString());
	    nextDomNode.setType(DomNode.Type.NODE_LIST);
	    Node node;
	    boolean keepNode;
	    // loop thru child nodes and filter out nodes that don't satisfy
	    // conditions
	    for(int i = 0; i < childNodes.getLength(); i++){
		node = childNodes.item(i);
		logger.trace("node-" + (i + 1) + "/" + childNodes.getLength() + " name=" + node.getNodeName());
		keepNode = true;
		for(DomCondition dc: domAccess.getDomCondList()){
		    if (!nodeSatisfiesCondition(node, dc)){
			logger.trace("FAIL");
			keepNode = false;
			break;
		    }else{
			logger.trace("PASS");
		    }
		}
		if (keepNode){
		    nl.addNode(node);
		}
	    }
	    nextDomNode.setNodeList(nl);

	}else if (domAccess.getDomCondList() != null && !domAccess.getDomCondList().isEmpty()
		&& domAccess.getAccessType() == DomInfo.AccessType.NODE){
	    if (prevDomNode == null || prevDomNode.getType() != DomNode.Type.NODE_LIST){
		throw new IseBusinessException("Cannot handle DomAccess access type " + DomInfo.AccessType.NODE
			+ " with non-empty DomAccess list unless previous DomNode is NodeList");
	    }
	    if (prevDomNode.getNodeList() == null){
		return null;
	    }
	    logger.trace("**********");
	    logger.trace("Looping thru nodeList to find node");
	    logger.trace(domAccess.toString());
	    nextDomNode.setType(DomNode.Type.NODE);
	    Node node;
	    int goodNodeIdx = -1;
	    // loop thru nodes in list and see if all DOM conditions pass
	    for(int i = 0; i < prevDomNode.getNodeList().getLength(); i++){
		node = prevDomNode.getNodeList().item(i);
		logger.trace("node-" + (i + 1) + "/" + prevDomNode.getNodeList().getLength() + " name="
			+ node.getNodeName());
		goodNodeIdx = i;
		for(DomCondition dc: domAccess.getDomCondList()){
		    if (!nodeSatisfiesCondition(node, dc)){
			logger.trace("FAIL");
			goodNodeIdx = -1;
			break;
		    }else{
			logger.trace("PASS");
		    }
		}
		if (goodNodeIdx >= 0){
		    break;
		}
	    }
	    if (goodNodeIdx >= 0){
		nextDomNode.setNode(prevDomNode.getNodeList().item(goodNodeIdx));
	    }

	}else if (domAccess.getAccessType() == DomInfo.AccessType.NODE_TEXT){
	    if (prevDomNode == null || prevDomNode.getType() != DomNode.Type.NODE){
		throw new IseBusinessException("Cannot handle DomAccess access type " + DomInfo.AccessType.NODE_TEXT
			+ " unless previous DomNode is Node");
	    }
	    nextDomNode.setType(DomNode.Type.STRING_VAL);
	    if (prevDomNode.getNode() != null){
		nextDomNode.setStringVal(prevDomNode.getNode().getTextContent());
	    }

	}else if (domAccess.getAccessType() == DomInfo.AccessType.ATTR_VAL){
	    if (prevDomNode == null || prevDomNode.getType() != DomNode.Type.NODE){
		throw new IseBusinessException("Cannot handle DomAccess access type " + DomInfo.AccessType.ATTR_VAL
			+ " unless previous DomNode is Node");
	    }
	    nextDomNode.setType(DomNode.Type.STRING_VAL);
	    if (prevDomNode.getNode() != null){
		NamedNodeMap attrMap = prevDomNode.getNode().getAttributes();
		if (attrMap != null){
		    String attrVal = null;
		    Node attr;
		    for(int j = 0; j < attrMap.getLength(); j++){
			attr = attrMap.item(j);
			if (attr.getNodeName().equals(domAccess.getAttrName())){
			    attrVal = attr.getNodeValue();
			    break;
			}
		    }
		    nextDomNode.setStringVal(attrVal);
		}
	    }

	}else{
	    throw new IseBusinessException("DomAccess object not handled: " + domAccess.toString());
	}

	return nextDomNode;
    }

    private static boolean nodeSatisfiesCondition(Node node, DomCondition domCond) throws IseBusinessException {
	logger.trace(domCond.toString());
	boolean flag = false;
	if (domCond.getConditionType() == DomInfo.ConditionType.NODE_NAME
		&& domCond.getCompareType() == DomInfo.CompareType.EQUALS){
	    logger.trace("nodeName=" + node.getNodeName());
	    flag = node.getNodeName() != null && node.getNodeName().equals(domCond.getPattern());

	}else if (domCond.getConditionType() == DomInfo.ConditionType.NODE_NAME
		&& domCond.getCompareType() == DomInfo.CompareType.ENDS_WITH){
	    logger.trace("nodeName=" + node.getNodeName());
	    flag = node.getNodeName() != null && node.getNodeName().endsWith(domCond.getPattern());

	}else if (domCond.getConditionType() == DomInfo.ConditionType.ATTR_NAME_VAL
		&& domCond.getCompareType() == DomInfo.CompareType.EQUALS){
	    int breakIdx = domCond.getPattern().indexOf("|");
	    if (breakIdx < 0){
		return false;
	    }
	    String name = domCond.getPattern().substring(0, breakIdx);
	    String value = domCond.getPattern().substring(breakIdx + 1);
	    Node attr;
	    NamedNodeMap attrMap = node.getAttributes();
	    for(int j = 0; j < attrMap.getLength(); j++){
		attr = attrMap.item(j);
		logger.trace("attr-" + (j + 1) + "/" + attrMap.getLength() + " name=" + attr.getNodeName() + " val="
			+ attr.getNodeValue());
		if (attr.getNodeName().equals(name) && attr.getNodeValue().equals(value)){
		    logger.trace("TRUE");
		    return true;
		}
	    }
	    logger.trace("FALSE");
	    return false;

	}else{
	    throw new IseBusinessException("DomCondition object not handled: " + domCond.toString());
	}
	logger.trace(flag?"TRUE":"FALSE");
	return flag;
    }
}
