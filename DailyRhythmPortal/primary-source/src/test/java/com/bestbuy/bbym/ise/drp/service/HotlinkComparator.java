package com.bestbuy.bbym.ise.drp.service;

import java.util.Comparator;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bestbuy.bbym.ise.drp.domain.Hotlink;

public class HotlinkComparator implements Comparator<Hotlink> {

    private Logger logger = LoggerFactory.getLogger(HotlinkComparator.class);

    @Override
    public int compare(Hotlink h1, Hotlink h2) {
	if (h1 == null || h2 == null){
	    logger.warn("At least one Hotlink object is null");
	    return -1;
	}
	if (!ObjectUtils.equals(h1.getId(), h2.getId())){
	    logger.warn("Hotlink.id values are not equal");
	    return -1;
	}
	if (!StringUtils.equals(h1.getUrlAlias(), h2.getUrlAlias())){
	    logger.warn("Hotlink.urlAlias values are not equal");
	    return -1;
	}
	if (!StringUtils.equals(h1.getUrl(), h2.getUrl())){
	    logger.warn("Hotlink.url values are not equal");
	    return -1;
	}
	if (!StringUtils.equals(h1.getUserId(), h2.getUserId())){
	    logger.warn("Hotlink.userId values are not equal");
	    return -1;
	}
	if (h1.getDisplayOrder() != h2.getDisplayOrder()){
	    logger.warn("Hotlink.displayOrder values are not equal");
	    return -1;
	}
	if (!StringUtils.equals(h1.getCreatedBy(), h2.getCreatedBy())){
	    logger.warn("Hotlink.createdBy values are not equal");
	    return -1;
	}
	if (!StringUtils.equals(h1.getModifiedBy(), h2.getModifiedBy())){
	    logger.warn("Hotlink.modifiedBy values are not equal");
	    return -1;
	}
	return 0;
    }
}
