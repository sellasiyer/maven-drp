/**
 * Best Buy Inc (c) 2012
 */
package com.bestbuy.bbym.ise.drp.dao;

import java.util.List;

import com.bestbuy.bbym.ise.drp.domain.TriageAction;
import com.bestbuy.bbym.ise.drp.domain.TriageEvent;
import com.bestbuy.bbym.ise.drp.domain.TriageIssue;
import com.bestbuy.bbym.ise.drp.domain.TriageRecommendation;
import com.bestbuy.bbym.ise.drp.domain.TriageResolution;

/**
 * Interface for triage DAO operations.
 * 
 * @author Yernar Khuangan (a1003810)
 * @version 1.0
 * @since iteration 22
 */
public interface TriageDao {

    /**
     * This method inserts new triage action object to repository.
     * @param triageAction triage action object
     * @return returns inserted triage action object
     */
    TriageAction persist(TriageAction triageAction);

    /**
     * This method updates existing triage action object in repository.
     * @param triageAction triage action object
     * @return returns updated triage action object
     */
    TriageAction update(TriageAction triageAction);

    /**
     * This method deletes triage action object from repository.
     * @param triageAction triage action object
     */
    void delete(TriageAction triageAction);

    /**
     * This method return all active action objects for given recommendation
     * from repository.
     * @param rcmd given recommendation object
     * @return returns list of action objects
     */
    List<TriageAction> getTriageActionActiveListByRcmd(TriageRecommendation rcmd);

    /**
     * Inserts new event to repository.
     * @param triageEvent event object
     * @return returns inserted event object
     */
    TriageEvent persist(TriageEvent triageEvent);

    /**
     * Updates existing event object on repository.
     * @param triageEvent event object
     * @return returns updated event object
     */
    TriageEvent update(TriageEvent triageEvent);

    /**
     * Deletes given event object from repository.
     * @param triageEvent event object to be deleted
     */
    void delete(TriageEvent triageEvent);

    /**
     * This method returns triage history list
     * by given device serial number from repository.
     * @param serialNo given device serial number
     * @return returns list of events
     */
    List<TriageEvent> getTriageEventByDeviceSerialNo(String serialNo);

    /**
     * Inserts new issue object to repository.
     * @param triageIssue issue object to be insert
     * @return returns inserted issue object
     */
    TriageIssue persist(TriageIssue triageIssue);

    /**
     * Updates given issue object on repository.
     * @param triageIssue issue object to be updated
     * @return returns updated issue object
     */
    TriageIssue update(TriageIssue triageIssue);

    /**
     * Deletes given issue object from repository.
     * @param triageIssue issue object to be deleted
     */
    void delete(TriageIssue triageIssue);

    /**
     * This method returns all active issues from repository.
     * @return returns list of issues
     */
    List<TriageIssue> getTriageIssueActiveList();

    /**
     * This method returns recommendation for given issue and SKU.
     * If SKU is null then returns default recommendation for issue.
     * @param triageIssue issue object
     * @param sku SKU number
     * @return returns recommendation
     */
    TriageRecommendation getTriageRecommendationBySku(TriageIssue triageIssue, String sku);

    /**
     * Inserts new resolution to repository.
     * @param triageResolution resolution object to be inserted
     * @return returns inserted resolution object
     */
    TriageResolution persist(TriageResolution triageResolution);

    /**
     * Updates given resolution object on repository.
     * @param TriageResolution resolution object to be updated
     * @return returns updated resolution object
     */
    TriageResolution update(TriageResolution TriageResolution);

    /**
     * Deletes given resolution object from repository.
     * @param TriageResolution resolution object to be deleted
     */
    void delete(TriageResolution TriageResolution);

    /**
     * This method returns all active resolutions from repository. 
     * @return returns list of resolution
     */
    List<TriageResolution> getTriageResolutionActiveList();

}
