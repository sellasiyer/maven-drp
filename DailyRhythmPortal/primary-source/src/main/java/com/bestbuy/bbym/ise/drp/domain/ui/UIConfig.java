package com.bestbuy.bbym.ise.drp.domain.ui;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bestbuy.bbym.ise.drp.helpers.DomInfo;
import com.bestbuy.bbym.ise.exception.IseBusinessException;
import com.bestbuy.bbym.ise.util.Util;

public class UIConfig {

    private static Logger logger = LoggerFactory.getLogger(UIConfig.class);

    private Map<String, DomInfo> replyConfigMap = new HashMap<String, DomInfo>();

    public void parse(Map<String, String> properties) throws IseBusinessException {
	if (properties == null){
	    throw new IseBusinessException("Invalid input properties");
	}
	String replyNames = properties.get("Replys");
	if (replyNames == null){
	    throw new IseBusinessException("Missing property: Replys");
	}

	String[] replyNameArray = replyNames.split(",");
	for(String replyName: replyNameArray){
	    replyConfigMap.put(replyName, createDomInfo(null, replyName, properties, UINode.Type.NODE));
	}
    }

    @Override
    public String toString() {
	StringBuilder sb = new StringBuilder();
	sb.append("UIConfig[");
	sb.append(Util.toStringMap(replyConfigMap));
	sb.append("]");
	return sb.toString();
    }

    public DomInfo getReplyDomInfo(String reply) throws IseBusinessException {
	DomInfo replyDomInfo = replyConfigMap.get(reply);
	if (replyDomInfo == null){
	    throw new IseBusinessException("No DomInfo for reply " + reply);
	}
	return replyDomInfo;
    }

    private DomInfo createDomInfo(DomInfo parentDomInfo, String name, Map<String, String> properties,
	    UINode.Type nodeType) throws IseBusinessException {
	DomInfo thisDomInfo = new DomInfo();
	thisDomInfo.setName(name);
	thisDomInfo.setType(nodeType);

	if (parentDomInfo != null){
	    thisDomInfo.setDotName(parentDomInfo.getDotName() + "." + name);
	}else{
	    thisDomInfo.setDotName(name);
	}

	String domPropertyName = thisDomInfo.getDotName() + ".DOM";
	String domAccessItems = properties.get(domPropertyName);
	if (domAccessItems != null){
	    String[] domAccessItemArray = domAccessItems.split(",");
	    for(String domAccessItem: domAccessItemArray){
		thisDomInfo.addDomAccess(createDomAccess(domPropertyName, domAccessItem, properties, thisDomInfo));
	    }
	}

	if (thisDomInfo.getType() == UINode.Type.LEAF){
	    return thisDomInfo;
	}

	String domInfoItems = properties.get(thisDomInfo.getDotName());
	if (domInfoItems == null){
	    throw new IseBusinessException("Missing property: " + thisDomInfo.getDotName());
	}

	UINode.Type nt;
	DomInfo childDomInfo;
	String[] domInfoItemArray = domInfoItems.split(","), domInfoPartArray;
	for(String domInfoItem: domInfoItemArray){
	    domInfoPartArray = domInfoItem.split(":");
	    if (domInfoPartArray == null || domInfoPartArray.length != 2){
		throw new IseBusinessException("Invalid property: " + thisDomInfo.getDotName());
	    }
	    nt = UINode.determineType(domInfoPartArray[0]);
	    if (nt == UINode.Type.UNKNOWN){
		logger.warn("domInfoItem=" + domInfoItem);
		throw new IseBusinessException("Invalid property: " + thisDomInfo.getDotName());
	    }
	    childDomInfo = createDomInfo(thisDomInfo, domInfoPartArray[1], properties, nt);
	    thisDomInfo.addMapDomInfo(domInfoPartArray[1], childDomInfo);
	}
	return thisDomInfo;
    }

    private DomInfo.DomAccess createDomAccess(String domPropertyName, String domAccessItem,
	    Map<String, String> properties, DomInfo domInfo) throws IseBusinessException {
	if (domAccessItem == null){
	    logger.warn("domAccessItem=" + domAccessItem);
	    throw new IseBusinessException("Invalid property: " + domPropertyName);
	}
	String[] domAccessArray = domAccessItem.split(":");
	if (domAccessArray == null || domAccessArray.length < 1 || domAccessArray.length > 2){
	    logger.warn("domAccessItem=" + domAccessItem);
	    throw new IseBusinessException("Invalid property: " + domPropertyName);
	}
	DomInfo.DomAccess domAccess = domInfo.new DomAccess();
	domAccess.setAccessType(domAccessArray[0]);
	if (domAccess.getAccessType() == DomInfo.AccessType.UNKNOWN){
	    logger.warn("domAccessItem=" + domAccessItem);
	    throw new IseBusinessException("Invalid property: " + domPropertyName);
	}

	// Only DomInfo.AccessType.NODE_LIST and
	// DomInfo.AccessType.NODE_TEXT can have just one part
	if (domAccessArray.length == 1){
	    if (domAccess.getAccessType() != DomInfo.AccessType.NODE_LIST
		    && domAccess.getAccessType() != DomInfo.AccessType.NODE_TEXT){
		logger.warn("domAccessItem=" + domAccessItem);
		throw new IseBusinessException("Invalid property: " + domPropertyName);
	    }
	    return domAccess;
	}

	if (domAccess.getAccessType() == DomInfo.AccessType.ATTR_VAL){
	    domAccess.setAttrName(domAccessArray[1]);
	    return domAccess;
	}

	// Only DomInfo.DomAccess.TYPE_NODE_LIST and
	// DomInfo.DomAccess.TYPE_NODE can have DOM conditions
	if (domAccess.getAccessType() != DomInfo.AccessType.NODE
		&& domAccess.getAccessType() != DomInfo.AccessType.NODE_LIST){
	    logger.warn("domAccessItem=" + domAccessItem);
	    throw new IseBusinessException("Invalid property: " + domPropertyName);
	}

	String[] domCondArray = domAccessArray[1].split("&");
	for(String domCond: domCondArray){
	    domAccess.addDomCond(createDomCondition(domPropertyName, domCond, properties, domInfo));
	}
	return domAccess;
    }

    private DomInfo.DomCondition createDomCondition(String domPropertyName, String domCondName,
	    Map<String, String> properties, DomInfo domInfo) throws IseBusinessException {
	if (domCondName == null){
	    throw new IseBusinessException("Invalid property: " + domPropertyName);
	}
	String domCondProperty = "NodeCondition." + domCondName;

	String domCondString = properties.get(domCondProperty);
	if (domCondString == null){
	    throw new IseBusinessException("Missing property: " + domCondProperty);
	}
	DomInfo.DomCondition domCond = domInfo.new DomCondition();

	int firstColon = domCondString.indexOf(":");
	if (firstColon < 0){
	    logger.warn("domCondString=" + domCondString);
	    throw new IseBusinessException("Invalid property: " + domCondProperty);
	}
	domCond.setConditionType(domCondString.substring(0, firstColon));
	if (domCond.getConditionType() == DomInfo.ConditionType.UNKNOWN){
	    logger.warn("domCondString=" + domCondString);
	    throw new IseBusinessException("Invalid property: " + domCondProperty);
	}

	int secondColon = domCondString.indexOf(":", firstColon + 1);
	if (secondColon < 0){
	    logger.warn("domCondString=" + domCondString);
	    throw new IseBusinessException("Invalid property: " + domCondProperty);
	}
	domCond.setCompareType(domCondString.substring(firstColon + 1, secondColon));
	if (domCond.getCompareType() == DomInfo.CompareType.UNKNOWN){
	    logger.warn("domCondString=" + domCondString);
	    throw new IseBusinessException("Invalid property: " + domCondProperty);
	}

	domCond.setPattern(domCondString.substring(secondColon + 1));

	if (domCond.getConditionType() == DomInfo.ConditionType.ATTR_NAME_VAL){
	    if (domCond.getPattern() == null || domCond.getPattern().indexOf("|") < 0){
		logger.warn("domCondString=" + domCondString);
		throw new IseBusinessException("Invalid property: " + domCondProperty);
	    }
	}

	return domCond;
    }
}
