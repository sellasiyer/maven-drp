/**
 * Best Buy Inc (c) 2012
 */
package com.bestbuy.bbym.ise.drp.service;

import java.util.List;

import com.bestbuy.bbym.ise.domain.Carrier;
import com.bestbuy.bbym.ise.drp.domain.Article;
import com.bestbuy.bbym.ise.drp.domain.Config;
import com.bestbuy.bbym.ise.drp.domain.DrpUser;
import com.bestbuy.bbym.ise.drp.domain.TriageAction;
import com.bestbuy.bbym.ise.drp.domain.TriageEvent;
import com.bestbuy.bbym.ise.drp.domain.TriageIssue;
import com.bestbuy.bbym.ise.drp.domain.TriageRecommendation;
import com.bestbuy.bbym.ise.drp.domain.TriageResolution;
import com.bestbuy.bbym.ise.exception.IseBusinessException;
import com.bestbuy.bbym.ise.exception.ServiceException;

/**
 * Interface for triage service.
 * 
 * @author Yernar Khuangan (a1003810)
 * @version 1.0
 * @since iteration 20
 */
public interface TriageService {

    /**
     * Returns list of triage resolution.
     * 
     * @return returns triage resolution list
     */
    public List<TriageResolution> getResolutionList() throws ServiceException;

    /**
     * Returns list of triage issues.
     * 
     * @return returns triage issue list
     */
    public List<TriageIssue> getIssueList() throws ServiceException;

    /**
     * Returns recommendation for given issue and device SKU.
     * 
     * @param triageIssue
     *            triage issue object
     * @param sku
     *            device SKU
     * @return returns triage recommendation
     */
    public TriageRecommendation getRecommendation(TriageIssue triageIssue, String sku) throws ServiceException;

    /**
     * Returns default recommendation for given issue.
     * 
     * @param triageIssue
     * @return returns triage recommendation
     */
    public TriageRecommendation getRecommendation(TriageIssue triageIssue) throws ServiceException;

    /**
     * Returns action list for given recommendation.
     * 
     * @param triageRecommendation
     * @return returns action list
     */
    public List<TriageAction> getActions(TriageRecommendation triageRecommendation) throws ServiceException;

    /**
     * Adds new triage event to triage history repository.
     * 
     * @param triageEvent
     *            triage event object
     * @param user
     *            user object
     * @return returns added triage event object
     */
    public TriageEvent addTriageEvent(TriageEvent triageEvent, DrpUser user) throws ServiceException;

    /**
     * Deletes triage event issue comment and saves changes to repository.
     * 
     * @param triageEvent
     *            triage event object
     * @param user
     *            user object
     * @return returns added triage event object
     */
    public TriageEvent deleteIssueComment(TriageEvent triageEvent, DrpUser user) throws ServiceException;

    /**
     * Deletes triage event resolution comment and saves changes to repository.
     * 
     * @param triageEvent
     *            triage event object
     * @param user
     *            user object
     * @return returns added triage event object
     */
    public TriageEvent deleteResolutionComment(TriageEvent triageEvent, DrpUser user) throws ServiceException;

    /**
     * Returns triage event list by device serial number from repository.
     * 
     * @param serialNumber
     *            device serial number
     * @return returns list of triage events
     */
    public List<TriageEvent> getTriageHistoryBySerialNumber(String serialNumber) throws ServiceException;

    public List<Article> getArticles(String searchText, String model, Carrier carrier) throws ServiceException,
	    IseBusinessException;

    /**
     * Returns SKU list for screen repair.
     * Use parameter name as device name and parameter value as SKU number.
     * @return returns list of SKU
     * @throws ServiceException
     */
    public List<Config> getScreenRepairSKUList() throws ServiceException;

    public TriageEvent updateTriageHistoryCustomerBenefit(TriageEvent triageEvent) throws ServiceException;
}
