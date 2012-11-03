package com.bestbuy.bbym.ise.drp.common;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.wicket.ajax.AjaxSelfUpdatingTimerBehavior;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.time.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bestbuy.bbym.ise.drp.DailyRhythmPortalSession;
import com.bestbuy.bbym.ise.drp.domain.RssCategory;
import com.bestbuy.bbym.ise.drp.service.RSSFeedService;
import com.bestbuy.bbym.ise.exception.ServiceException;

public class NewsTickerPanel extends Panel {

    private static final long serialVersionUID = 1L;
    private static Logger logger = LoggerFactory.getLogger(NewsTickerPanel.class);

    @SpringBean(name = "rssFeedService")
    private RSSFeedService rssFeedService;

    private static String[] htmlToRemoveIn = new String[] {"<p>", "</p>", "andnbsp;" };
    private static String[] htmlToRemoveOut = new String[] {"", "", "" };

    public NewsTickerPanel(String id) {
	this(id, true);
    }

    public NewsTickerPanel(String id, final boolean getNewContent) {
	super(id);

	logger.debug("in NewsTickerPanel");

	final DailyRhythmPortalSession session = (DailyRhythmPortalSession) getSession();
	final String loadingTickerContent = getString("beastTicker.loadingNews.label");

	final AjaxSelfUpdatingTimerBehavior updateTimer = new AjaxSelfUpdatingTimerBehavior(Duration.seconds(2));

	final Label beastTickerContent = new Label("beastTickerContent", new AbstractReadOnlyModel<String>() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public String getObject() {
		if (session.isLoadTickerComplete()){
		    if (getNewContent){
			updateTimer.stop();
		    }
		    return session.getTickerContent();
		}else{
		    return loadingTickerContent;
		}
	    }
	});
	if (getNewContent){
	    beastTickerContent.add(updateTimer);
	}
	add(beastTickerContent);

	if (getNewContent){
	    session.setLoadTickerComplete(false);
	    final LoadingTickerThread loadingTickerThread = new LoadingTickerThread(session, rssFeedService,
		    getString("beastTicker.noNews.label"));
	    loadingTickerThread.start();
	}else{
	    session.setLoadTickerComplete(true);
	}

	logger.debug("out NewsTickerPanel");
    }

    @Override
    public void renderHead(IHeaderResponse response) {
	super.renderHead(response);

	logger.debug("renderOnDomReadyJavascript=ticker_start();");
	response.renderOnDomReadyJavaScript("ticker_start();");
    }

    private static class LoadingTickerThread extends Thread {

	private final DailyRhythmPortalSession session;
	private final RSSFeedService rssFeedService;
	private final String noTickerContent;

	public LoadingTickerThread(final DailyRhythmPortalSession session, final RSSFeedService rssFeedService,
		final String noTickerContent) {
	    this.session = session;
	    this.rssFeedService = rssFeedService;
	    this.noTickerContent = noTickerContent;
	}

	public void run() {
	    try{
		List<RssCategory> tickerCategories = rssFeedService.getBeastTickerRssFeed();
		if (tickerCategories != null){
		    logger.debug("tickerCategories.size=" + tickerCategories.size());
		    for(RssCategory cat: tickerCategories){
			logger.debug("BEAST Ticker=" + ReflectionToStringBuilder.toString(cat));
		    }
		}
		StringBuilder ticker = new StringBuilder();
		if (tickerCategories != null){
		    for(RssCategory cat: tickerCategories){
			if (StringUtils.isNotBlank(cat.getShortDescription())){
			    if (ticker.length() > 0){
				ticker.append(" ");
			    }
			    ticker.append(cat.getShortDescription());
			}
		    }
		}
		String tickerContent = "No News";
		if (ticker.length() > 0){
		    tickerContent = ticker.toString();
		    String newContent;
		    for(int i = 0; i < htmlToRemoveIn.length; i++){
			newContent = StringUtils.replace(tickerContent, htmlToRemoveIn[i], htmlToRemoveOut[i]);
			tickerContent = newContent;
		    }
		    session.setTickerContent(tickerContent);
		}else{
		    session.setTickerContent(noTickerContent);
		}
	    }catch(ServiceException se){
		String message = "An unexpected exception occured while attempting to get BEAST ticker RSS categories";
		logger.error(message, se);
	    }finally{
		session.setLoadTickerComplete(true);
	    }
	}
    }

}
