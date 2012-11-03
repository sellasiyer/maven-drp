package com.bestbuy.bbym.ise.drp.common;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bestbuy.bbym.ise.drp.domain.RssCategory;
import com.bestbuy.bbym.ise.drp.domain.RssItem;
import com.bestbuy.bbym.ise.drp.service.RSSFeedService;
import com.bestbuy.bbym.ise.drp.utils.DrpConstants;
import com.bestbuy.bbym.ise.exception.ServiceException;

@AuthorizeInstantiation( {DrpConstants.DRP_ADMIN, DrpConstants.DRP_MANAGER, DrpConstants.DRP_LEAD,
	DrpConstants.DRP_USER, DrpConstants.DRP_TEAM, DrpConstants.SHP_USER, DrpConstants.SHP_MANAGER })
public class HomePage extends BaseNavPage {

    private static Logger logger = LoggerFactory.getLogger(HomePage.class);
    private static final long serialVersionUID = 1L;

    @SpringBean(name = "rssFeedService")
    private RSSFeedService rssFeedService;

    private List<RssCategory> newsCategories;

    public HomePage(final PageParameters parameters) {
	super(parameters);
	if (isTablet()){
	    add(new Label("tabletOnly", "This is a tablet. This message will not show up on a desktop"));
	    return;
	}

	NewsTickerPanel newsTickerPanel = new NewsTickerPanel("beastTicker");
	newsTickerPanel.setMarkupId("beastTicker");
	newsTickerPanel.setOutputMarkupId(true);
	add(newsTickerPanel);

	try{
	    List<RssCategory> potentialCategories = rssFeedService.getBeastNewsRssFeed();
	    newsCategories = new ArrayList<RssCategory>();
	    if (potentialCategories != null){
		for(RssCategory cat: potentialCategories){
		    if (cat.getTitle() != null && cat.getLastUpdatedDate() != null){
			newsCategories.add(cat);
		    }
		}
	    }
	}catch(ServiceException se){
	    String message = "An unexpected exception occured while attempting to get BEAST news RSS categories";
	    logger.error(message, se);
	}
	logCategories("BEAST News", newsCategories);

	add(new ListView<RssCategory>("newsCategoryListView", new PropertyModel<List<RssCategory>>(this,
		"newsCategories")) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void populateItem(final ListItem<RssCategory> listItem) {
		final RssCategory newsCategory = listItem.getModelObject();
		listItem.add(new Label("title", new Model<String>() {

		    private static final long serialVersionUID = 1L;

		    @Override
		    public String getObject() {
			return newsCategory.getTitle();
		    }
		}));
		listItem.add(new Label("date", new Model<String>() {

		    private static final long serialVersionUID = 1L;

		    @Override
		    public String getObject() {
			return getString("beastTicker.lastUpdated.label") + newsCategory.getLastUpdatedDate();
		    }
		}));
		Label newsHtml = new Label("news", new Model<String>() {

		    private static final long serialVersionUID = 1L;

		    @Override
		    public String getObject() {
			return createNewsText(newsCategory);
		    }
		});
		newsHtml.setEscapeModelStrings(false);
		listItem.add(newsHtml);
	    }
	});
    }

    public List<RssCategory> getNewsCategories() {
	return newsCategories;
    }

    private String createNewsText(RssCategory newsCategory) {
	StringBuilder news = new StringBuilder();
	if (newsCategory != null && newsCategory.getRssItemList() != null){
	    for(RssItem item: newsCategory.getRssItemList()){
		if (StringUtils.isNotBlank(item.getFullText())){
		    if (news.length() > 0){
			news.append(" ");
		    }
		    news.append(item.getFullText());
		}
	    }
	}
	return news.toString();
    }

    private void logCategories(String categoryDescription, List<RssCategory> categories) {
	if (logger.isDebugEnabled() && categories != null){
	    logger.debug(categoryDescription + " category count=" + categories.size());
	    for(RssCategory cat: categories){
		logger.debug(categoryDescription + '=' + ReflectionToStringBuilder.toString(cat));
	    }
	}
    }
}
