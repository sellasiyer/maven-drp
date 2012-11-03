/**
 * Copyright 2011 BestBuy
 */
package com.bestbuy.bbym.ise.drp.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.bestbuy.bbym.ise.domain.User;
import com.bestbuy.bbym.ise.drp.domain.RecSheetReportList;
import com.bestbuy.bbym.ise.drp.domain.RecSheetReportingSearchResults;
import com.bestbuy.bbym.ise.drp.domain.Recommendation;
import com.bestbuy.bbym.ise.exception.ServiceException;

/**
 * This service provides an entry point to all business methods in the Mobile
 * Sales Workspace. The primary operations include CRUD functions of a digital
 * recommendation worksheet as well as various lookup functions used to populate
 * pick lists within it.
 * 
 * @author a868680
 */
public interface MobileSalesWorkspaceService {

    /**
     * Return all moblie digital recommendation worksheets for a customer matching the
     * supplied last name and originating in the given store. A <em>LIKE</em> as
     * opposed to an exact match search is employed
     * 
     * @param storeId
     *            the store Id. {@code null} to search all stores
     */
    public List<Recommendation> findRecommendations(String customerLastName, String storeId) throws ServiceException;

    /**
     * Return all mobile digital worksheets for a customer matching the supplied mobile
     * number and originating in the given store
     * 
     * @param storeId
     *            the store Id. {@code null} to search all stores
     */
    public List<Recommendation> findRecommendationsByMobile(String mobileNumber, String storeId)
	    throws ServiceException;

    /**
     * Return all computers and tablets digital recommendation worksheets for a customer matching the
     * supplied last name and originating in the given store. A <em>LIKE</em> as
     * opposed to an exact match search is employed
     * 
     * @param storeId
     *            the store Id. {@code null} to search all stores
     */
    public List<Recommendation> findRecommendationsForCAndT(String customerLastName, String storeId)
	    throws ServiceException;

    /**
     * Return all computers and tablets digital worksheets for a customer matching the supplied phone
     * number and originating in the given store
     * 
     * @param storeId
     *            the store Id. {@code null} to search all stores
     */
    public List<Recommendation> findRecommendationsForCAndTByPhone(String phoneNumber, String storeId)
	    throws ServiceException;

    /**
     * Saves a digital worksheet.
     * 
     * @param recommendation
     *            the worksheet to save.
     * @param user
     *            the user who will be recorded as modifying the worksheet.
     */
    public void saveRecommendation(Recommendation recommendation, User user) throws ServiceException;

    /**
     * Return all rec sheets created by and amended by an employee
     * 
     * @return list of rec sheets created and amended by an employee from start
     *         date to end date
     */
    public List<RecSheetReportingSearchResults> findRecommendationReportsByAId(String aId, Date startDate, Date endDate)
	    throws ServiceException;

    /**
     * Returns all rec sheets created by and amended by an employee adding the
     * filter of store id
     * 
     * @return list of rec sheets created and amended by an employee from start
     *         date to end date
     */
    public List<RecSheetReportingSearchResults> findRecommendationReportsByAId(String aId, Date startDate,
	    Date endDate, String storeId) throws ServiceException;

    /**
     * Returns all rec sheets created by and amended by an employee by employee
     * last name
     * 
     * @return list of rec sheets created and amended by an employee from start
     *         date to end date
     */
    public List<RecSheetReportingSearchResults> findRecommendationReportsByLastName(String empLastName, Date startDate,
	    Date endDate) throws ServiceException;

    /**
     * Returns all rec sheets created by and amended by an employee by employee
     * last name adding the filter of store id
     * 
     * @return list of rec sheets created and amended by an employee from start
     *         date to end date
     */
    public List<RecSheetReportingSearchResults> findRecommendationReportsByLastName(String empLastName, Date startDate,
	    Date endDate, String storeId) throws ServiceException;

    /**
     * Returns all rec sheets created from start date to end date
     * adding the filter of store id
     * 
     * @param storeId
     * 
     * @return list of rec sheets created from start date to end date
     *         
     */
    public List<RecSheetReportList> findRecommendationsFromToDate(Date fromDate, Date toDate, String storeId)
	    throws ServiceException;

    /**
     * Returns one rec sheets by a recommendation id
     * 
     * 
     * @param recommendationId
     *      
     * @return one of rec sheets by a recommendation id
     */
    public Recommendation getRecommendation(long recommendationId) throws ServiceException;

    /**
     * Returns GSP Plan Price and SKU
     * 
     *  @param deviceType - TABLET, DESKTOP, LAPTOP
     *  @param term - 1, 2 ,3 years
     *  @param productPrice
     *  @return exact price and sku
     */
    public String getRecommendationGSPSKU(String deviceType, int term, float productPrice) throws ServiceException;

    /**
     * Returns map of essential ids and prices
     */
    public Map<String, String> getRecommendationGSPPrice(float productPrice) throws ServiceException;

    /**
     * Returns RecSheet in PDF format
     */
    public byte[] getRecSheetPDF(Recommendation recommendation) throws ServiceException;

    /**
     * This method is called once after new RecSheet goes live. This method will
     * convert WOW and DeviceCapabilites bit maps to essential records.
     */
    public void convertLegacyRecSheets(Date fromDate, Date toDate) throws ServiceException;

    /**
     * This method will generate PDF for each Recommendation in the list and concatenates then together.
     */
    public byte[] getRecSheetReportAsPDF(List<Recommendation> recList) throws ServiceException;

}
