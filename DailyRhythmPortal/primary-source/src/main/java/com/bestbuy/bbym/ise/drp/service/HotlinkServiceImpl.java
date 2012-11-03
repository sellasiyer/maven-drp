package com.bestbuy.bbym.ise.drp.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bestbuy.bbym.ise.domain.User;
import com.bestbuy.bbym.ise.drp.dao.HotlinkDao;
import com.bestbuy.bbym.ise.drp.domain.Hotlink;
import com.bestbuy.bbym.ise.exception.DataAccessException;
import com.bestbuy.bbym.ise.exception.IseBusinessException;
import com.bestbuy.bbym.ise.exception.IseExceptionCodeEnum;
import com.bestbuy.bbym.ise.exception.ServiceException;

/**
 * 
 * @author a904002
 */
@Service("hotlinkService")
public class HotlinkServiceImpl implements HotlinkService {

    private static Logger logger = LoggerFactory.getLogger(HotlinkServiceImpl.class);

    @Autowired
    private HotlinkDao hotlinkDao;

    public HotlinkServiceImpl() {
    }

    public HotlinkServiceImpl(HotlinkDao hotlinkDao) {
	this.hotlinkDao = hotlinkDao;
    }

    public void setHotlinkDao(HotlinkDao hotlinkDao) {
	this.hotlinkDao = hotlinkDao;
    }

    @Override
    public void createUpdateLink(Hotlink hotlink, User employee) throws ServiceException, IseBusinessException {
	try{
	    if (hotlink == null){
		throw new DataAccessException("Invalid input hotlink");
	    }
	    if (employee == null){
		throw new DataAccessException("Invalid input employee");
	    }
	    // Create
	    if (hotlink.getId() == null || hotlink.getId().longValue() < 0L){
		Hotlink hl = hotlinkDao.findHotlink(hotlink.getUserId(), hotlink.getUrlAlias());
		if (hl != null){
		    throw new IseBusinessException(IseExceptionCodeEnum.DataItemAlreadyExists, "Hotlink with userId "
			    + hotlink.getUserId() + " and urlAlias " + hotlink.getUrlAlias() + " exists");
		}
		int displayOrder = 1;
		List<Hotlink> list = hotlinkDao.getHotlinks(hotlink.getUserId());
		if (list != null && !list.isEmpty()){
		    hl = list.get(list.size() - 1);
		    displayOrder = hl.getDisplayOrder() + 1;
		}
		hotlink.setDisplayOrder(displayOrder);
		hotlink.setCreatedBy(employee.getUserId());
		hotlink.setCreatedDate(new Date());
		// FIXME remove after DB changed
		hotlink.setModifiedBy(employee.getUserId());
		hotlink.setModifiedDate(new Date());
		hotlinkDao.createHotlink(hotlink);

		// Update
	    }else{
		hotlink.setModifiedBy(employee.getUserId());
		hotlink.setModifiedDate(new Date());
		hotlinkDao.updateHotlink(hotlink);
	    }
	}catch(DataAccessException ex){
	    logger.error("Failed to create/update hotlink for user : " + hotlink.getUserId(), ex);
	    throw new ServiceException(IseExceptionCodeEnum.DataAccess, ex.getMessage());
	}
    }

    @Override
    public List<Hotlink> getHotlinkListByUserId(String userId) throws ServiceException {
	try{
	    if (userId == null){
		throw new DataAccessException("Invalid input userId");
	    }
	    return hotlinkDao.getHotlinks(userId);
	}catch(DataAccessException ex){
	    logger.error("Failed to get hotlinks for user : " + userId, ex);
	    throw new ServiceException(IseExceptionCodeEnum.DataAccess, ex.getMessage());
	}
    }

    @Override
    public void deleteLink(Hotlink hotlink) throws ServiceException {
	try{
	    if (hotlink == null || hotlink.getId() == null){
		throw new DataAccessException("Invalid input hotlink");
	    }
	    hotlinkDao.deleteHotlink(hotlink);
	}catch(DataAccessException ex){
	    logger.error("Failed to delete hotlink", ex);
	    throw new ServiceException(IseExceptionCodeEnum.DataAccess, ex.getMessage());
	}
    }

    @Override
    public void moveLinkUp(Hotlink hotlink, User employee) throws ServiceException, IseBusinessException {
	try{
	    if (hotlink == null){
		throw new DataAccessException("Invalid input hotlink");
	    }
	    if (employee == null){
		throw new DataAccessException("Invalid input employee");
	    }
	    int idx = -1;
	    Hotlink hl;
	    List<Hotlink> list = hotlinkDao.getHotlinks(hotlink.getUserId());
	    for(int i = 0; i < list.size(); i++){
		hl = list.get(i);
		if (ObjectUtils.equals(hl.getId(), hotlink.getId())){
		    idx = i;
		    break;
		}
	    }
	    if (idx == -1){
		throw new IseBusinessException(IseExceptionCodeEnum.DataItemAlreadyExists,
			"Hotlink does not exist for userId " + hotlink.getUserId());
	    }
	    // Hotlink already at top
	    if (idx == 0){
		return;
	    }
	    Hotlink hlAbove = list.get(idx - 1);
	    int displayOrderAbove = hlAbove.getDisplayOrder();
	    int displayOrderBelow = hotlink.getDisplayOrder();

	    Date now = new Date();

	    hlAbove.setDisplayOrder(displayOrderBelow);
	    hlAbove.setModifiedBy(employee.getUserId());
	    hlAbove.setModifiedDate(now);
	    hotlinkDao.updateHotlink(hlAbove);

	    hotlink.setDisplayOrder(displayOrderAbove);
	    hotlink.setModifiedBy(employee.getUserId());
	    hotlink.setModifiedDate(now);
	    hotlinkDao.updateHotlink(hotlink);
	}catch(DataAccessException ex){
	    logger.error("Failed to move hotlink up in display order for user : " + hotlink.getUserId(), ex);
	    throw new ServiceException(IseExceptionCodeEnum.DataAccess, ex.getMessage());
	}
    }

    @Override
    public void moveLinkDown(Hotlink hotlink, User employee) throws ServiceException, IseBusinessException {
	try{
	    if (hotlink == null){
		throw new DataAccessException("Invalid input hotlink");
	    }
	    if (employee == null){
		throw new DataAccessException("Invalid input employee");
	    }
	    int idx = -1;
	    Hotlink hl;
	    List<Hotlink> list = hotlinkDao.getHotlinks(hotlink.getUserId());
	    for(int i = 0; i < list.size(); i++){
		hl = list.get(i);
		if (ObjectUtils.equals(hl.getId(), hotlink.getId())){
		    idx = i;
		    break;
		}
	    }
	    if (idx == -1){
		throw new IseBusinessException(IseExceptionCodeEnum.DataItemAlreadyExists,
			"Hotlink does not exist for userId " + hotlink.getUserId());
	    }
	    // Hotlink already at bottom
	    if (idx == (list.size() - 1)){
		return;
	    }
	    Hotlink hlBelow = list.get(idx + 1);
	    int displayOrderAbove = hotlink.getDisplayOrder();
	    int displayOrderBelow = hlBelow.getDisplayOrder();

	    Date now = new Date();

	    hlBelow.setDisplayOrder(displayOrderAbove);
	    hlBelow.setModifiedBy(employee.getUserId());
	    hlBelow.setModifiedDate(now);
	    hotlinkDao.updateHotlink(hlBelow);

	    hotlink.setDisplayOrder(displayOrderBelow);
	    hotlink.setModifiedBy(employee.getUserId());
	    hotlink.setModifiedDate(now);
	    hotlinkDao.updateHotlink(hotlink);
	}catch(DataAccessException ex){
	    logger.error("Failed to move hotlink down in display order for user : " + hotlink.getUserId(), ex);
	    throw new ServiceException(IseExceptionCodeEnum.DataAccess, ex.getMessage());
	}
    }
}
