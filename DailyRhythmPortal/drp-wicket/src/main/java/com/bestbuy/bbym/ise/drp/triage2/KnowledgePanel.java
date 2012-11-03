/**
 * Best Buy Inc (c) 2012
 */
package com.bestbuy.bbym.ise.drp.triage2;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.markup.html.link.PopupSettings;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bestbuy.bbym.ise.drp.DailyRhythmPortalSession;
import com.bestbuy.bbym.ise.drp.common.NewBaseNavPage;
import com.bestbuy.bbym.ise.drp.common.ServiceBasePanel;
import com.bestbuy.bbym.ise.drp.domain.Article;
import com.bestbuy.bbym.ise.drp.service.TriageService;
import com.bestbuy.bbym.ise.exception.IseBusinessException;
import com.bestbuy.bbym.ise.exception.ServiceException;

/**
 * @author Yernar Khuangan (a1003810)
 * @version 1.0
 * @since iteration 21
 */
public class KnowledgePanel extends ServiceBasePanel {

    private static final long serialVersionUID = 1L;
    private static Logger logger = LoggerFactory.getLogger(KnowledgePanel.class);

    @SpringBean(name = "triageService")
    private TriageService triageService;

    private String searchText;

    private List<Article> articles = new ArrayList<Article>();

    private AbstractDefaultAjaxBehavior wicketBehaviorKP;
    private WebMarkupContainer noSearchResultsContainer, searchResultsContainer;

    public KnowledgePanel(String id) {
	super(id);

	DailyRhythmPortalSession session = getDailyRhythmPortalSession();

	Form<Object> form = new Form<Object>("form");
	add(form);

	if (session.getSelectedLine() != null && session.getSelectedLine().getDevice() != null){
	    searchText = session.getSelectedLine().getDevice().getDescription();
	}
	final RequiredTextField<String> search = new RequiredTextField<String>("search", new PropertyModel<String>(
		this, "searchText"));
	search.setOutputMarkupPlaceholderTag(true);
	form.add(search);

	noSearchResultsContainer = new WebMarkupContainer("noSearchResultsContainer") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public boolean isVisible() {
		return articles == null || articles.isEmpty();
	    }

	};
	noSearchResultsContainer.setOutputMarkupPlaceholderTag(true);
	add(noSearchResultsContainer);

	searchResultsContainer = new WebMarkupContainer("searchResultsContainer") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public boolean isVisible() {
		return articles != null && !articles.isEmpty();
	    }
	};
	searchResultsContainer.setOutputMarkupPlaceholderTag(true);
	add(searchResultsContainer);

	final ListView<Article> searchResultsListView = new ListView<Article>("searchResultsListView", articles) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void populateItem(ListItem<Article> item) {
		DailyRhythmPortalSession session = getDailyRhythmPortalSession();
		Article article = (Article) item.getModelObject();
		ExternalLink link = new ExternalLink("link", buildURL(article, session.getDrpUser().getUserId()));
		link.setPopupSettings(new PopupSettings(PopupSettings.RESIZABLE | PopupSettings.SCROLLBARS));
		item.add(link);
		link.add(new Label("title", article.getTitle()));
		link.add(new Label("abstract", article.getArticleAbstract()));
	    }
	};
	searchResultsListView.setOutputMarkupPlaceholderTag(true);
	searchResultsContainer.add(searchResultsListView);

	final AjaxSubmitLink goLink = new AjaxSubmitLink("goLink", form) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
		logger.debug("goLink onClick");
		NewBaseNavPage page = (NewBaseNavPage) getPage();
		page.openLoadingModal(getString("triageKnowledge.loading.label"), target);
		target.appendJavaScript("doWicketBehavior('wicketBehaviorKP(\"search\")');");
	    }

	    @Override
	    protected void onError(AjaxRequestTarget target, Form<?> form) {
		target.add(getPage().get("feedback"));
		updatePanel(target);
	    }
	};
	goLink.setOutputMarkupPlaceholderTag(true);
	form.add(goLink);

	// means by which JS can callback into Java
	wicketBehaviorKP = new AbstractDefaultAjaxBehavior() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void respond(AjaxRequestTarget target) {
		NewBaseNavPage page = (NewBaseNavPage) getPage();
		String id = RequestCycle.get().getRequest().getRequestParameters().getParameterValue("id").toString();
		logger.debug("wicketBehaviorKP id=" + id);
		if ("search".equals(id)){
		    String error = doInitialServiceCalls();
		    page.closeLoadingModal(target);
		    updatePanel(target);
		    if (error != null){
			error(error);
			target.add(getPage().get("feedback"));
		    }
		}
	    }
	};
	add(wicketBehaviorKP);

	add(new AbstractDefaultAjaxBehavior() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void respond(AjaxRequestTarget target) {
	    }

	    @Override
	    public void renderHead(Component component, IHeaderResponse response) {
		super.renderHead(component, response);
		StringBuilder onDomReadyJS = new StringBuilder();
		onDomReadyJS.append("wicketBehaviorKP = function(id) { ");
		onDomReadyJS.append("wicketAjaxGet('");
		onDomReadyJS.append(wicketBehaviorKP.getCallbackUrl());
		onDomReadyJS.append("&id='+id); };");
		logger.debug("onDomReadyJS=" + onDomReadyJS.toString());
		response.renderOnDomReadyJavaScript(onDomReadyJS.toString());
	    }
	});
    }

    private String buildURL(Article article, String userId) {
	StringBuilder sb = new StringBuilder();
	sb.append(article.getLink());
	sb.append("&employeeId=");
	sb.append(userId);
	return sb.toString();
    }

    @Override
    public String doInitialServiceCalls() {
	articles.clear();
	logger.debug("searchText=" + searchText);
	String error = getString("triageKnowledge.getArticlesFailed.message.label");
	try{
	    List<Article> articleList = triageService.getArticles(searchText, null, null);
	    if (articleList != null){
		articles.addAll(articleList);
	    }
	}catch(ServiceException se){
	    logger.error("ServiceException getting knowledge-base articles", se);
	    return error + " " + se.getMessage();
	}catch(IseBusinessException e){
	    logger.error("BusinessException getting knowledge-base articles", e);
	    return error + " " + e.getMessage();
	}
	return null;
    }

    @Override
    public void updatePanel(AjaxRequestTarget target) {
	target.add(this, noSearchResultsContainer, searchResultsContainer);
    }

}
