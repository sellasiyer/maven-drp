package com.bestbuy.bbym.ise.drp.service;

import java.util.List;

import com.bestbuy.bbym.ise.domain.User;
import com.bestbuy.bbym.ise.drp.domain.Hotlink;
import com.bestbuy.bbym.ise.exception.IseBusinessException;
import com.bestbuy.bbym.ise.exception.ServiceException;

/**
 * 
 * @author a904002
 */
public interface HotlinkService {

    public void createUpdateLink(Hotlink hotlink, User employee) throws ServiceException, IseBusinessException;

    public List<Hotlink> getHotlinkListByUserId(String userId) throws ServiceException;

    public void deleteLink(Hotlink hotlink) throws ServiceException;

    public void moveLinkUp(Hotlink hotlink, User employee) throws ServiceException, IseBusinessException;

    public void moveLinkDown(Hotlink hotlink, User employee) throws ServiceException, IseBusinessException;
}
