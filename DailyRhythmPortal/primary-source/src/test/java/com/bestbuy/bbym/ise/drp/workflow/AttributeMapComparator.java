package com.bestbuy.bbym.ise.drp.workflow;

import java.util.Comparator;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.junit.Ignore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bestbuy.bbym.ise.workflow.domain.WorkflowAttribute;

@Ignore
public class AttributeMapComparator implements Comparator<Map<String, WorkflowAttribute>> {

    private Logger logger = LoggerFactory.getLogger(AttributeMapComparator.class);

    @Override
    public int compare(Map<String, WorkflowAttribute> map1, Map<String, WorkflowAttribute> map2) {
	if (map1 == null || map2 == null){
	    logger.info("At least one AttributeMap object is null");
	    return -1;
	}
	for(String waName: map1.keySet()){
	    if (compareAttributes(map1.get(waName), map2.get(waName)) != 0){
		logger.info("AttributeMap values for key " + waName + " are not equal");
		return -1;
	    }
	}
	for(String waName: map2.keySet()){
	    if (compareAttributes(map1.get(waName), map2.get(waName)) != 0){
		logger.info("AttributeMap values for key " + waName + " are not equal");
		return -1;
	    }
	}
	return 0;
    }

    private int compareAttributes(WorkflowAttribute wa1, WorkflowAttribute wa2) {
	if (wa1 == null && wa2 == null){
	    return 0;
	}else if (wa1 != null && wa2 == null){
	    if (wa1.getValue() == null){
		return 0;
	    }
	    return -1;
	}else if (wa1 == null && wa2 != null){
	    if (wa2.getValue() == null){
		return 0;
	    }
	    return -1;
	}
	if (!StringUtils.equals(wa1.getValue(), wa2.getValue())){
	    return -1;
	}
	return 0;
    }
}
