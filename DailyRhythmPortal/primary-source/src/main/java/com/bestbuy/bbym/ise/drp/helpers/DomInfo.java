package com.bestbuy.bbym.ise.drp.helpers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bestbuy.bbym.ise.drp.domain.ui.UINode;
import com.bestbuy.bbym.ise.util.Util;

public class DomInfo {

    public enum AccessType {
	UNKNOWN("UNK"), NODE("ND"), NODE_LIST("NL"), NODE_TEXT("NT"), ATTR_VAL("ATV");

	private String code;

	private AccessType(String code) {
	    this.code = code;
	}

	public String getCode() {
	    return code;
	}
    }

    public enum ConditionType {
	UNKNOWN("UNK"), TAG("TAG"), INDEX("IDX"), NODE_NAME("NN"), ATTR_NAME_VAL("ATNV");

	private String code;

	private ConditionType(String code) {
	    this.code = code;
	}

	public String getCode() {
	    return code;
	}
    }

    public enum CompareType {
	UNKNOWN("UNK"), EQUALS("EQ"), ENDS_WITH("EW");

	private String code;

	private CompareType(String code) {
	    this.code = code;
	}

	public String getCode() {
	    return code;
	}
    }

    private String name;
    private String dotName;
    private UINode.Type type = UINode.Type.UNKNOWN;
    private Map<String, DomInfo> domInfoMap;
    private List<DomAccess> domAccessList;

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public String getDotName() {
	return dotName;
    }

    public void setDotName(String dotName) {
	this.dotName = dotName;
    }

    public UINode.Type getType() {
	return type;
    }

    public void setType(UINode.Type type) {
	this.type = type;
    }

    public Map<String, DomInfo> getDomInfoMap() {
	return domInfoMap;
    }

    public void addMapDomInfo(String name, DomInfo domInfo) {
	if (domInfo == null){
	    return;
	}
	if (domInfoMap == null){
	    domInfoMap = new HashMap<String, DomInfo>();
	}
	domInfoMap.put(name, domInfo);
    }

    public void addDomAccess(DomAccess domAccess) {
	if (domAccess == null){
	    return;
	}
	if (domAccessList == null){
	    domAccessList = new ArrayList<DomAccess>();
	}
	domAccessList.add(domAccess);
    }

    public List<DomAccess> getDomAccessList() {
	return domAccessList;
    }

    @Override
    public String toString() {
	StringBuilder sb = new StringBuilder();
	sb.append("DomInfo[name=");
	sb.append(name);
	sb.append(" type=");
	sb.append(type.getCode());
	if (domInfoMap != null){
	    sb.append(" domInfoMap=[");
	    sb.append(Util.toStringMap(domInfoMap));
	    sb.append("]");
	}
	if (domAccessList != null){
	    sb.append(" domAccessList=[");
	    sb.append(Util.toStringList(domAccessList));
	    sb.append("]");
	}
	sb.append("]");
	return sb.toString();
    }

    public class DomAccess {

	private AccessType type = AccessType.UNKNOWN;
	private List<DomCondition> domCondList;
	private String attrName;

	public void setAccessType(String code) {
	    for(AccessType at: AccessType.values()){
		if (at.getCode().equals(code)){
		    type = at;
		    return;
		}
	    }
	    type = AccessType.UNKNOWN;
	}

	public AccessType getAccessType() {
	    return type;
	}

	public void addDomCond(DomCondition domCond) {
	    if (domCond == null){
		return;
	    }
	    if (domCondList == null){
		domCondList = new ArrayList<DomCondition>();
	    }
	    domCondList.add(domCond);
	}

	public List<DomCondition> getDomCondList() {
	    return domCondList;
	}

	public String getAttrName() {
	    return attrName;
	}

	public void setAttrName(String attrName) {
	    this.attrName = attrName;
	}

	@Override
	public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append(getAccessType().getCode());
	    if (attrName != null){
		sb.append(":");
		sb.append(attrName);
	    }
	    if (domCondList != null){
		sb.append(":[");
		sb.append(Util.toStringList(domCondList));
		sb.append("]");
	    }
	    return sb.toString();
	}

    }

    public class DomCondition {

	private ConditionType type = ConditionType.UNKNOWN;
	private CompareType compare = CompareType.UNKNOWN;
	private String pattern;

	public void setConditionType(String code) {
	    for(ConditionType ct: ConditionType.values()){
		if (ct.getCode().equals(code)){
		    type = ct;
		    return;
		}
	    }
	    type = ConditionType.UNKNOWN;
	}

	public ConditionType getConditionType() {
	    return type;
	}

	public void setCompareType(String code) {
	    for(CompareType ct: CompareType.values()){
		if (ct.getCode().equals(code)){
		    compare = ct;
		    return;
		}
	    }
	    compare = CompareType.UNKNOWN;
	}

	public CompareType getCompareType() {
	    return compare;
	}

	public String getPattern() {
	    return pattern;
	}

	public void setPattern(String pattern) {
	    this.pattern = pattern;
	}

	@Override
	public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append(type.getCode());
	    sb.append(":");
	    sb.append(compare.getCode());
	    sb.append(":");
	    sb.append(pattern);
	    return sb.toString();
	}

    }
}
