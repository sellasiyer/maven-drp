package com.bestbuy.bbym.ise.drp.service;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bestbuy.bbym.ise.drp.domain.RssCategory;
import com.bestbuy.bbym.ise.drp.domain.RssItem;

/**
 * JUnit test for {@link RssFeedServiceImpl}.
 * 
 * @author a904002
 */
@Ignore
public class RssFeedServiceImplTest {

    private RssFeedServiceImpl rssFeedServiceImpl;
    private static Logger logger = LoggerFactory.getLogger(RssFeedServiceImplTest.class);

    /**
     * Test of getBeastNewsRssFeed method, of class RssFeedServiceImpl.
     */
    @Test
    public void testGetBeastNewsRssFeed() throws Exception {

	for(RssCategory entry: rssFeedServiceImpl.getBeastNewsRssFeed()){
	    logger.info("Category : " + entry.getTitle());
	    logger.info("Last Updated Date : " + entry.getLastUpdatedDate());

	    for(RssItem entry1: entry.getRssItemList()){

		logger.info("FullText : " + entry1.getFullText());

	    }

	}
	//   Rss rssFeed = (Rss)rssUnMarshaller.unmarshal(new StreamSource(new URL("http://www.bbydev.com/bbynews/services/get.php?tid=1&id=589").openStream()));
    }

    /**
     * Test of getBeastTickerRssFeed method, of class RssFeedServiceImpl.
     */
    @Test
    public void testGetBeastTickerRssFeed() throws Exception {
	List<RssCategory> result = rssFeedServiceImpl.getBeastTickerRssFeed();
	for(RssCategory entry: result){
	    logger.info("Short description : " + entry.getShortDescription());
	    logger.info("Last Updated Date : " + entry.getLastUpdatedDate());
	}
    }
}
