/*
 * Copyright 2012 a904002.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.bestbuy.bbym.ise.drp.rest.service;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bestbuy.bbym.ise.domain.User;
import com.bestbuy.bbym.ise.drp.dao.RecommendationsDao;
import com.bestbuy.bbym.ise.drp.domain.Recommendation;
import com.bestbuy.bbym.ise.exception.DataAccessException;

/**
 * REST Web Service
 *
 * @author a904002
 */
@Path("/recommendation")
@Component
@Scope("session")
public class RecommendationRestService {

    private static Logger logger = LoggerFactory.getLogger(RecommendationRestService.class);

    @Autowired
    private RecommendationsDao recommendationsDao;

    /**
     * PUT method for updating or creating an instance of
     * RecommendationRestService
     */
    @Path("/{userid}")
    @PUT
    @Produces("application/xml,application/json")
    @Consumes("application/xml, application/json")
    public long saveRecommendation(Recommendation recommendation, @PathParam("userid") String userId) {

	long rsId = 0;

	User user = new User();
	user.setUserId(userId);
	try{
	    rsId = recommendationsDao.addRecommendation(recommendation, user);
	}catch(DataAccessException ex){

	}

	return rsId;
    }

    @Path("/{storeId}/{lastName}")
    @GET
    @Produces("application/xml,application/json")
    @Consumes("application/xml, application/json")
    public List<Recommendation> getRecommendationList(@PathParam("storeId") String storeId,
	    @PathParam("lastName") String lastName, @PathParam("recShtTyp") int recShtTyp) throws DataAccessException {
	return recommendationsDao.getRecommendationList(storeId, lastName);
    }

    @Path("/{storeId}")
    @GET
    @Produces("application/xml,application/json")
    @Consumes("application/xml, application/json")
    public List<Recommendation> getRecommendationList(@PathParam("storeId") String storeId) throws DataAccessException {
	logger.info("In getRecommendationList");
	return recommendationsDao.getRecommendationList(storeId);
    }

    @Path("/{userid}")
    @POST
    @Produces("application/xml,application/json")
    @Consumes("application/xml, application/json")
    public long addRecommendation(Recommendation rs, @PathParam("userid") String userId) throws DataAccessException {

	long rsId = 0;

	User user = new User();
	user.setUserId(userId);
	Recommendation updateRec = recommendationsDao.getRecommendation(rs.getId());
	if (updateRec != null){
	    recommendationsDao.persistRecommendation(rs, user);
	    rsId = rs.getId();
	}else{
	    rsId = recommendationsDao.addRecommendation(rs, user);

	}

	return rsId;
    }
}
