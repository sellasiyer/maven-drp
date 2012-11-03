/**
 * Best Buy Inc (c) 2012
 */
package com.bestbuy.bbym.ise.drp.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bestbuy.bbym.ise.domain.Carrier;
import com.bestbuy.bbym.ise.drp.dao.ConfigDao;
import com.bestbuy.bbym.ise.drp.dao.TriageDao;
import com.bestbuy.bbym.ise.drp.domain.Article;
import com.bestbuy.bbym.ise.drp.domain.Config;
import com.bestbuy.bbym.ise.drp.domain.DrpUser;
import com.bestbuy.bbym.ise.drp.domain.TriageAction;
import com.bestbuy.bbym.ise.drp.domain.TriageEvent;
import com.bestbuy.bbym.ise.drp.domain.TriageIssue;
import com.bestbuy.bbym.ise.drp.domain.TriageRecommendation;
import com.bestbuy.bbym.ise.drp.domain.TriageResolution;
import com.bestbuy.bbym.ise.drp.sao.GeekSquadArmorySao;
import com.bestbuy.bbym.ise.exception.IseBusinessException;
import com.bestbuy.bbym.ise.exception.IseExceptionCodeEnum;
import com.bestbuy.bbym.ise.exception.ServiceException;

/**
 * @author Yernar Khuangan (a1003810)
 * @version 1.0
 * @since iteration 21
 */
@Transactional
@Service("triageService")
public class TriageServiceImpl implements TriageService {

    private static Logger logger = LoggerFactory.getLogger(TriageServiceImpl.class);

    @Autowired
    private TriageDao triageDao;
    @Autowired
    private GeekSquadArmorySao geekSquadArmorySao;
    @Autowired
    private ConfigDao configDao;

    @Override
    @Transactional(readOnly = true)
    public List<TriageIssue> getIssueList() throws ServiceException {
	List<TriageIssue> list = null;
	try{
	    list = triageDao.getTriageIssueActiveList();
	}catch(Exception e){
	    logger.error("TRIAGE: Unexpected Error:", e);
	    throw new ServiceException(IseExceptionCodeEnum.DataAccess, e.getMessage());
	}
	return list;
    }

    @Override
    @Transactional(readOnly = true)
    public TriageRecommendation getRecommendation(TriageIssue triageIssue, String sku) throws ServiceException {
	try{
	    return triageDao.getTriageRecommendationBySku(triageIssue, sku);
	}catch(Exception e){
	    logger.error("TRIAGE: Unexpected Error:", e);
	    throw new ServiceException(IseExceptionCodeEnum.DataAccess, e.getMessage());
	}
    }

    @Override
    public TriageRecommendation getRecommendation(TriageIssue triageIssue) throws ServiceException {
	return this.getRecommendation(triageIssue, null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TriageAction> getActions(TriageRecommendation triageRecommendation) throws ServiceException {
	List<TriageAction> list = null;
	try{
	    list = triageDao.getTriageActionActiveListByRcmd(triageRecommendation);
	}catch(Exception e){
	    logger.error("TRIAGE: Unexpected Error:", e);
	    throw new ServiceException(IseExceptionCodeEnum.DataAccess, e.getMessage());
	}
	return list;
    }

    @Override
    @Transactional(readOnly = false)
    public TriageEvent addTriageEvent(TriageEvent triageEvent, DrpUser user) throws ServiceException {
	try{
	    triageEvent.setCreatedBy(user.getUserId());
	    triageEvent.setModifiedBy(user.getUserId());
	    triageEvent = triageDao.persist(triageEvent);
	}catch(Exception e){
	    logger.error("TRIAGE: Unexpected Error:", e);
	    throw new ServiceException(IseExceptionCodeEnum.DataAccess, e.getMessage());
	}
	return triageEvent;
    }

    @Override
    @Transactional(readOnly = false)
    public TriageEvent deleteIssueComment(TriageEvent triageEvent, DrpUser user) throws ServiceException {
	try{
	    triageEvent.setModifiedBy(user.getUserId());
	    triageEvent.setIssueComment(null);
	    triageEvent = triageDao.update(triageEvent);
	}catch(Exception e){
	    logger.error("TRIAGE: Unexpected Error:", e);
	    throw new ServiceException(IseExceptionCodeEnum.DataAccess, e.getMessage());
	}
	return triageEvent;
    }

    @Override
    @Transactional(readOnly = false)
    public TriageEvent deleteResolutionComment(TriageEvent triageEvent, DrpUser user) throws ServiceException {
	try{
	    triageEvent.setModifiedBy(user.getUserId());
	    triageEvent.setResolutionComment(null);
	    triageEvent = triageDao.update(triageEvent);
	}catch(Exception e){
	    logger.error("TRIAGE: Unexpected Error:", e);
	    throw new ServiceException(IseExceptionCodeEnum.DataAccess, e.getMessage());
	}
	return triageEvent;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TriageResolution> getResolutionList() throws ServiceException {
	List<TriageResolution> list = null;
	try{
	    list = triageDao.getTriageResolutionActiveList();
	}catch(Exception e){
	    logger.error("TRIAGE: Unexpected Error:", e);
	    throw new ServiceException(IseExceptionCodeEnum.DataAccess, e.getMessage());
	}
	return list;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TriageEvent> getTriageHistoryBySerialNumber(String serialNumber) throws ServiceException {
	List<TriageEvent> list = null;
	try{
	    list = triageDao.getTriageEventByDeviceSerialNo(serialNumber);
	}catch(Exception e){
	    logger.error("TRIAGE: Unexpected Error:", e);
	    throw new ServiceException(IseExceptionCodeEnum.DataAccess, e.getMessage());
	}
	return list;
    }

    @Override
    public List<Article> getArticles(String searchText, String model, Carrier carrier) throws ServiceException,
	    IseBusinessException {
	return geekSquadArmorySao.getArticles(searchText, model, carrier);
    }

    @Override
    public List<Config> getScreenRepairSKUList() throws ServiceException {
	List<Config> list = null;
	try{
	    list = configDao.getConfigItemsByType(ConfigDao.TRIAGE_SCREEN_REPAIR_SKU);
	}catch(Exception e){
	    logger.error("TRIAGE: Unexpected Error:", e);
	    throw new ServiceException(IseExceptionCodeEnum.DataAccess, e.getMessage());
	}
	return list;
    }

    @Override
    public TriageEvent updateTriageHistoryCustomerBenefit(TriageEvent triageEvent) throws ServiceException {
	try{

	    triageDao.update(triageEvent);

	}catch(Exception e){
	    logger.error("TRIAGE: Unexpected Error:", e);
	    throw new ServiceException(IseExceptionCodeEnum.DataAccess, e.getMessage());
	}
	return triageEvent;
    }

}
