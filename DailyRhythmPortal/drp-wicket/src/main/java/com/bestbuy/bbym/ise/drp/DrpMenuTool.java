package com.bestbuy.bbym.ise.drp;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bestbuy.bbym.ise.drp.domain.Hotlink;
import com.bestbuy.bbym.ise.drp.navigation.MenuItem;
import com.bestbuy.bbym.ise.drp.navigation.MenuTool;
import com.bestbuy.bbym.ise.drp.service.HotlinkService;
import com.bestbuy.bbym.ise.exception.ServiceException;

public class DrpMenuTool extends MenuTool {

    private static final long serialVersionUID = 1L;
    private static Logger logger = LoggerFactory.getLogger(DrpMenuTool.class);

    private HotlinkService hotLinkService;

    @Override
    public List<MenuItem> getMenuItemList(String itemListFactory) {
	MenuItem mi;
	if ("GetUserHotlinks".equals(itemListFactory)){
	    if (hotLinkService == null){
		logger.warn("hotLinkService not set for itemListFactory named " + itemListFactory);
		return null;
	    }
	    if (getUser() == null){
		logger.warn("hotLinkService requires non-null User for itemListFactory named " + itemListFactory);
		return null;
	    }

	    try{
		List<Hotlink> list = hotLinkService.getHotlinkListByUserId(getUser().getUserId());
		if (list == null || list.isEmpty()){
		    return null;
		}
		List<MenuItem> itemList = new ArrayList<MenuItem>();
		for(Hotlink hl: list){
		    mi = new MenuItem();
		    mi.setName(hl.getUrlAlias());
		    mi.setUrl(hl.getUrl());
		    itemList.add(mi);
		}
		return itemList;
	    }catch(ServiceException se){
		logger.warn("Failed to get list of hotlinks for user " + getUser().getUserId(), se);
		return null;
	    }

	}else{
	    logger.warn("ItemListFactory not handled: " + itemListFactory);
	    return null;
	}
    }

    public void setHotlinkService(HotlinkService hotLinkService) {
	this.hotLinkService = hotLinkService;
    }

}
