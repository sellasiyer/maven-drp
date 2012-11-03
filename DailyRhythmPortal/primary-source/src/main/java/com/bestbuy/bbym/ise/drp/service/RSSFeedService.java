package com.bestbuy.bbym.ise.drp.service;

import java.util.List;

import com.bestbuy.bbym.ise.drp.domain.RssCategory;
import com.bestbuy.bbym.ise.exception.ServiceException;

/**
 * 
 * @author a904002
 */
public interface RSSFeedService {

    public List<RssCategory> getBeastNewsRssFeed() throws ServiceException;

    public List<RssCategory> getBeastTickerRssFeed() throws ServiceException;
}
