package com.bestbuy.bbym.ise.drp.dao;

import java.util.Map;

import com.bestbuy.bbym.ise.domain.User;
import com.bestbuy.bbym.ise.exception.DataAccessException;

public interface EssentialsDao {

    /**
     * Retrieves the Essentials matching the recommendation id.
     */
    public Map<Long, String> getEssentials(long recommendationId) throws DataAccessException;

    /**
     * Adds the essentials and returns an Essentials object with the id's updated.
     * Will update the essentials argument with id's retrieved from db.
     */
    public void saveEssentials(Map<Long, String> preferences, Long recommendationId, User employee)
	    throws DataAccessException;

}
