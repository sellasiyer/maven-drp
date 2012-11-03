package com.bestbuy.bbym.ise.drp.common;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.easymock.EasyMock;
import org.junit.Test;

import com.bestbuy.bbym.ise.drp.domain.RssCategory;
import com.bestbuy.bbym.ise.drp.domain.RssCategoryFactory;
import com.bestbuy.bbym.ise.drp.domain.RssItem;
import com.bestbuy.bbym.ise.drp.domain.RssItemFactory;
import com.bestbuy.bbym.ise.drp.service.RSSFeedService;
import com.bestbuy.bbym.ise.drp.utils.DrpConstants;
import com.bestbuy.bbym.ise.exception.IseExceptionCodeEnum;
import com.bestbuy.bbym.ise.exception.ServiceException;

/**
 * JUnit test for {@link HomePage}.
 */
public class HomePageTest extends BaseNavPageTest {

    private RSSFeedService rssFeedService;

    @Override
    public void setUp() {
	super.setUp();
	rssFeedService = EasyMock.createMock(RSSFeedService.class);
	mockApplicationContext.putBean("rssFeedService", rssFeedService);
    }

    /**
     * Checks that all roles except {@link DrpConstants#DRP_BEAST} have access
     * to the page.
     */
    @Override
    public void testPageAccess() {
	assertAccessAllowed(HomePage.class, DrpConstants.DRP_ADMIN, DrpConstants.DRP_MANAGER, DrpConstants.DRP_LEAD,
		DrpConstants.DRP_USER, DrpConstants.SHP_USER, DrpConstants.SHP_MANAGER, DrpConstants.DRP_TEAM);
	assertAccessDenied(HomePage.class, DrpConstants.DRP_BEAST);
    }

    /**
     * Tests rendering of page and presence of components.
     */
    @Override
    public void testRenderMyPage() {
	List<RssCategory> rss = new ArrayList<RssCategory>();
	rss.add(RssCategoryFactory.create());
	try{
	    expect(rssFeedService.getBeastNewsRssFeed()).andReturn(rss);
	    expect(rssFeedService.getBeastTickerRssFeed()).andReturn(rss);
	    replay(rssFeedService);
	}catch(Exception e){
	    //Do Nothing
	}
	// start and render the test page
	tester.startPage(HomePage.class, new PageParameters());

	// Check that the right page was rendered (no unexpected redirect or
	// intercept)

	tester.assertRenderedPage(HomePage.class);
	tester.assertNoErrorMessage();

	assertNavigation();

	tester.assertComponent("beastTicker", NewsTickerPanel.class);
	tester.assertComponent("newsCategoryListView", ListView.class);
    }

    /**
     * Tests contents of ticker.
     */
    @Test
    public void testBeastTicker() throws Exception {
	List<RssCategory> rssCategories = new ArrayList<RssCategory>();
	RssCategory displayedRssCategory = RssCategoryFactory.create();
	List<RssItem> rssItems = new ArrayList<RssItem>();
	RssItem displayedRssItem1 = RssItemFactory.create("Some RSS Item Text");
	rssItems.add(displayedRssItem1);
	RssItem displayedRssItem2 = RssItemFactory.create("Some more RSS Item Text");
	rssItems.add(displayedRssItem2);
	displayedRssCategory.setRssItemList(rssItems);
	rssCategories.add(displayedRssCategory);
	rssCategories.add(RssCategoryFactory.createWithNullTitle());
	EasyMock.expect(rssFeedService.getBeastNewsRssFeed()).andReturn(rssCategories);
	EasyMock.replay(rssFeedService);

	HomePage page = (HomePage) tester.startPage(HomePage.class, new PageParameters());

	assertNotNull(page.getNewsCategories());
	assertEquals("Only one category should be displayed", 1, page.getNewsCategories().size());
	assertSame("Incorrect category displayed", displayedRssCategory, page.getNewsCategories().get(0));

	tester.assertListView("newsCategoryListView", page.getNewsCategories());
	tester.assertComponent("newsCategoryListView:0:title", Label.class);
	tester.assertLabel("newsCategoryListView:0:title", displayedRssCategory.getTitle());
	tester.assertComponent("newsCategoryListView:0:date", Label.class);
	tester.assertLabel("newsCategoryListView:0:date", "Last updated on  "
		+ displayedRssCategory.getLastUpdatedDate());
	tester.assertComponent("newsCategoryListView:0:news", Label.class);
	tester.assertLabel("newsCategoryListView:0:news", displayedRssItem1.getFullText() + " "
		+ displayedRssItem2.getFullText());
    }

    /**
     * Test page display when {@link RSSFeedService} throws a
     * {@link ServiceException}.
     */
    @Test
    public void testWithRSSFeedServiceServiceException() throws Exception {
	ServiceException serviceException = new ServiceException(IseExceptionCodeEnum.BusinessException);
	EasyMock.expect(rssFeedService.getBeastNewsRssFeed()).andThrow(serviceException);
	EasyMock.replay(rssFeedService);

	HomePage page = (HomePage) tester.startPage(HomePage.class, new PageParameters());

	assertNull("No categories should be displayed", page.getNewsCategories());
    }

}
