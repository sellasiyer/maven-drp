package com.bestbuy.bbym.ise.drp.dao;

import java.util.Date;
import java.util.List;

import com.bestbuy.bbym.ise.domain.User;
import com.bestbuy.bbym.ise.drp.domain.RecSheetCountByDay;
import com.bestbuy.bbym.ise.drp.domain.RecSheetReportingSearchResults;
import com.bestbuy.bbym.ise.drp.domain.Recommendation;
import com.bestbuy.bbym.ise.exception.DataAccessException;

public interface RecommendationsDao {

    public Recommendation getRecommendation(long recommendationId) throws DataAccessException;

    /**
     * Gets the recommendation list.
     * 
     * @param storeId
     *            the store Id. {@code null} to search all stores
     */
    public List<Recommendation> getRecommendationList(String storeId, String lastName) throws DataAccessException;

    /**
     * Gets the recommendation list.
     * 
     * @param storeId
     *            the store Id. {@code null} to search all stores
     */
    public List<Recommendation> getRecommendationListByMobile(String storeId, String mobileNumber)
	    throws DataAccessException;

    /**
     * Gets the recommendation list for computers and tablets.
     * 
     * @param storeId
     *            the store Id. {@code null} to search all stores
     */
    public List<Recommendation> getRecommendationListForCAndT(String storeId, String lastName)
	    throws DataAccessException;

    /**
     * Gets the recommendation list for computers and tablets.
     * 
     * @param storeId
     *            the store Id. {@code null} to search all stores
     */
    public List<Recommendation> getRecommendationListForCAndTByPhone(String storeId, String phoneNumber)
	    throws DataAccessException;

    public List<Recommendation> getRecommendationList(String storeId) throws DataAccessException;

    public long addRecommendation(Recommendation rs, User employee) throws DataAccessException;

    public void persistRecommendation(Recommendation rs, User employee) throws DataAccessException;

    public List<RecSheetReportingSearchResults> getRecommendationEmployeeNamesByAId(String aId)
	    throws DataAccessException;

    public List<Recommendation> getRecommendationReportsListByAId(String aId, Date startDate, Date endDate)
	    throws DataAccessException;

    public List<Recommendation> getRecommendationReportsListByAId(String aId, Date startDate, Date endDate,
	    String storeId) throws DataAccessException;

    public List<RecSheetReportingSearchResults> getRecommendationEmployeeNamesByLastName(String empLastName)
	    throws DataAccessException;

    public List<RecSheetCountByDay> getRecommendationListFromToDate(Date startDate, Date endDate, String storeId)
	    throws DataAccessException;

    public List<Recommendation> getRecommendationList(Date fromDate, Date toDate) throws DataAccessException;

}
