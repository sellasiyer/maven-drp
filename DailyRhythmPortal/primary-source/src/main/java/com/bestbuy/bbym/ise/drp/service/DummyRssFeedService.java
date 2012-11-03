package com.bestbuy.bbym.ise.drp.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.bestbuy.bbym.ise.drp.domain.RssCategory;
import com.bestbuy.bbym.ise.drp.domain.RssItem;
import com.bestbuy.bbym.ise.exception.ServiceException;

@Service("rssFeedService2")
public class DummyRssFeedService implements RSSFeedService {

    @Override
    public List<RssCategory> getBeastNewsRssFeed() throws ServiceException {
	DummyData.sleep(1);
	DummyData.throwServiceException(20);
	int listSize = DummyData.getRandomIndex(5), itemListSize;
	List<RssCategory> list = new ArrayList<RssCategory>();
	for(int i = 1; i <= listSize; i++){
	    RssCategory rssCat = new RssCategory();
	    rssCat.setTitle("Mobile News Category " + i);
	    rssCat.setLastUpdatedDate("0" + i + "/15/2012");
	    itemListSize = DummyData.getRandomIndex(8);
	    List<RssItem> itemList = new ArrayList<RssItem>();
	    for(int j = 1; j <= itemListSize; j++){
		RssItem rssItem = new RssItem("<p>Mobile News Item " + i + "." + j + "</p>");
		itemList.add(rssItem);
	    }
	    rssCat.setRssItemList(itemList);
	    list.add(rssCat);
	}
	return list;
    }

    @Override
    public List<RssCategory> getBeastTickerRssFeed() throws ServiceException {
	DummyData.sleep(10);
	DummyData.throwServiceException(70);
	int listSize = DummyData.getRandomIndex(5);
	List<RssCategory> list = new ArrayList<RssCategory>();

	for(int i = 1; i <= listSize; i++){
	    RssCategory rssCat = new RssCategory();
	    rssCat.setShortDescription("BEAST News Item " + i);
	    list.add(rssCat);
	}
	return list;
    }

}
