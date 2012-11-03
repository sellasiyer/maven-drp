package com.bestbuy.bbym.ise.drp.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.bestbuy.bbym.ise.domain.Carrier;
import com.bestbuy.bbym.ise.drp.domain.Article;
import com.bestbuy.bbym.ise.drp.domain.Config;
import com.bestbuy.bbym.ise.drp.domain.DrpUser;
import com.bestbuy.bbym.ise.drp.domain.TriageAction;
import com.bestbuy.bbym.ise.drp.domain.TriageEvent;
import com.bestbuy.bbym.ise.drp.domain.TriageIssue;
import com.bestbuy.bbym.ise.drp.domain.TriageRecommendation;
import com.bestbuy.bbym.ise.drp.domain.TriageResolution;
import com.bestbuy.bbym.ise.exception.IseExceptionCodeEnum;
import com.bestbuy.bbym.ise.exception.ServiceException;

@Service("triageService2")
public class DummyTriageService implements TriageService {

    private static List<TriageIssue> issueList = new ArrayList<TriageIssue>();
    static{
	long issueId = 0L;
	for(int i = 0; i < DummyData.getTriageIssues().length; i++){
	    TriageIssue ti = new TriageIssue();
	    ti.setId(issueId++);
	    ti.setIssueDesc(DummyData.getTriageIssues()[i]);
	    StringBuilder sb = new StringBuilder();
	    sb.append("What is covered?</br>");
	    sb.append(ti.getIssueDesc());
	    sb.append("</br>");
	    sb.append(ti.getIssueDesc());
	    sb.append("</br>");
	    ti.setTooltip(sb.toString());
	    issueList.add(ti);
	}
    }

    @Override
    public List<TriageIssue> getIssueList() throws ServiceException {
	DummyData.sleep(2);
	DummyData.throwServiceException(30);
	return issueList;
    }

    @Override
    public TriageRecommendation getRecommendation(TriageIssue triageIssue) throws ServiceException {
	DummyData.sleep(2);
	DummyData.throwServiceException(30);
	if (triageIssue == null || triageIssue.getIssueDesc() == null){
	    throw new ServiceException(IseExceptionCodeEnum.UnexpectedNull, "Unexpected null");
	}
	TriageRecommendation tr = new TriageRecommendation();
	tr.setRecommendation("Fix " + triageIssue.getIssueDesc());
	return tr;
    }

    @Override
    public List<TriageAction> getActions(TriageRecommendation triageRecommendation) throws ServiceException {
	DummyData.sleep(2);
	DummyData.throwServiceException(30);
	if (triageRecommendation == null){
	    throw new ServiceException(IseExceptionCodeEnum.UnexpectedNull, "Unexpected null");
	}
	List<TriageAction> actions = new ArrayList<TriageAction>();
	int numActions = DummyData.getRandomIndex(8) + 1;
	for(int i = 1; i <= numActions; i++){
	    TriageAction ta = new TriageAction();
	    ta.setAction("Action " + i);
	    actions.add(ta);
	}
	return actions;
    }

    @Override
    public List<TriageResolution> getResolutionList() throws ServiceException {
	// TODO Implement me!
	return null;
    }

    @Override
    public TriageRecommendation getRecommendation(TriageIssue triageIssue, String sku) throws ServiceException {
	// TODO Implement me!
	return null;
    }

    @Override
    public TriageEvent addTriageEvent(TriageEvent triageEvent, DrpUser user) throws ServiceException {
	// TODO Implement me!
	return null;
    }

    @Override
    public TriageEvent deleteIssueComment(TriageEvent triageEvent, DrpUser user) throws ServiceException {
	// TODO Implement me!
	return null;
    }

    @Override
    public TriageEvent deleteResolutionComment(TriageEvent triageEvent, DrpUser user) throws ServiceException {
	// TODO Implement me!
	return null;
    }

    @Override
    public List<TriageEvent> getTriageHistoryBySerialNumber(String serialNumber) throws ServiceException {
	// TODO Implement me!
	return null;
    }

    @Override
    public List<Article> getArticles(String searchText, String model, Carrier carrier) throws ServiceException {
	DummyData.sleep(2);
	DummyData.throwServiceException(30);
	List<Article> articles = new ArrayList<Article>();
	int numArticles = DummyData.getRandomIndex(8);
	for(int i = 1; i <= numArticles; i++){
	    Article a = new Article();
	    a.setTitle("Known Bug " + i + " with Phones");
	    a.setArticleAbstract("Here is how to fix know bug " + i + " with phones. First do this and then do that.");
	    a.setLink("http://sp.bestbuy.com/services/km/agent/Lists/Articles/DispForm.aspx?ID=1197");
	    articles.add(a);
	}
	return articles;
    }

    @Override
    public List<Config> getScreenRepairSKUList() throws ServiceException {
	DummyData.sleep(2);
	DummyData.throwServiceException(30);
	List<Config> configs = new ArrayList<Config>();
	int numConfigs = DummyData.getRandomIndex(8);
	for(int i = 1; i <= numConfigs; i++){
	    Config c = new Config();
	    c.setDescription(DummyData.getHardware());
	    c.setParamValue(DummyData.getSku());
	    configs.add(c);
	}
	return configs;
    }

    @Override
    public TriageEvent updateTriageHistoryCustomerBenefit(TriageEvent triageEvent) throws ServiceException {
	DummyData.sleep(2);
	DummyData.throwServiceException(30);
	return triageEvent;
    }
}
