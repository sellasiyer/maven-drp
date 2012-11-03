package com.bestbuy.bbym.ise.drp.dao;

import java.util.List;

import com.bestbuy.bbym.ise.drp.domain.Hotlink;
import com.bestbuy.bbym.ise.exception.DataAccessException;

/**
 * 
 * @author a904002
 */
public interface HotlinkDao {

    public void createHotlink(Hotlink hotlink) throws DataAccessException;

    public void deleteHotlink(Hotlink hotlink) throws DataAccessException;

    public void updateHotlink(Hotlink hotlink) throws DataAccessException;

    public List<Hotlink> getHotlinks(String userId) throws DataAccessException;

    public Hotlink getHotlink(int hotlinkId) throws DataAccessException;

    public Hotlink findHotlink(String userId, String urlAlias) throws DataAccessException;
}
