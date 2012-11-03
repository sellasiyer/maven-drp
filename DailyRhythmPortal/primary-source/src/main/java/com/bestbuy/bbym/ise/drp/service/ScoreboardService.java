/**
 * Best Buy Inc (c) 2012
 */
package com.bestbuy.bbym.ise.drp.service;

import java.util.Date;
import java.util.List;

import com.bestbuy.bbym.ise.domain.Store;
import com.bestbuy.bbym.ise.drp.domain.Config;
import com.bestbuy.bbym.ise.drp.domain.DrpUser;
import com.bestbuy.bbym.ise.drp.domain.scoreboard.ScoreboardCategory;
import com.bestbuy.bbym.ise.drp.domain.scoreboard.ScoreboardDailyGoal;
import com.bestbuy.bbym.ise.drp.domain.scoreboard.ScoreboardData;
import com.bestbuy.bbym.ise.drp.domain.scoreboard.ScoreboardDataItem;
import com.bestbuy.bbym.ise.drp.domain.scoreboard.ScoreboardEmployeeDistribution;
import com.bestbuy.bbym.ise.drp.domain.scoreboard.ScoreboardEmployeeShift;
import com.bestbuy.bbym.ise.drp.domain.scoreboard.ScoreboardOperationType;
import com.bestbuy.bbym.ise.drp.domain.scoreboard.ScoreboardStoreType;
import com.bestbuy.bbym.ise.drp.domain.scoreboard.ScoreboardTransactionType;
import com.bestbuy.bbym.ise.drp.domain.scoreboard.ScoreboardWidget;
import com.bestbuy.bbym.ise.exception.IseBusinessException;
import com.bestbuy.bbym.ise.exception.ServiceException;

/**
 * Interface for daily score board service.
 * 
 * @author Yernar Khuangan (a1003810)
 * @version 1.0
 * @since iteration 25
 */
public interface ScoreboardService {

    /**
     * Returns score board data for given store.
     * 
     * @param storeId
     *            store id
     * @param operationType
     *            store operation type MOBILE or COMPUTING
     * @param storeType
     *            store type SAS or SWAS
     * @param localDate
     *            current date and time from client
     * @return returns score board data
     * @throws ServiceException
     */
    public ScoreboardData getScoreBoardData(String storeId, ScoreboardOperationType operationType,
	    ScoreboardStoreType storeType, Date localDate, String userId) throws ServiceException;

    /**
     * Returns score board sales and return items.
     * 
     * @param storeId
     *            store id
     * @param ntlMrgnDlyEmpId
     *            employee id, null if only store
     * @param storeType
     *            store type
     * @param operationType
     *            operation type
     * @param transactionType
     *            transaction type
     * @return returns list of item categories
     * @throws ServiceException
     */
    public List<ScoreboardCategory> getScoreboardItems(String storeId, Long ntlMrgnDlyEmpId,
	    ScoreboardStoreType storeType, ScoreboardOperationType operationType,
	    ScoreboardTransactionType transactionType) throws ServiceException;

    /**
     * Saves sales and return items.
     * 
     * @param storeId
     *            store id
     * @param emp
     *            employee shift info
     * @param transactionType
     *            transaction type
     * @param items
     *            list of items to be saved
     * @param user logged user into system
     * @param timeZoneOffset time zone offset between local and server time in milliseconds
     * @throws ServiceException
     */
    public void saveScoreboardItems(String storeId, ScoreboardEmployeeShift emp,
	    ScoreboardTransactionType transactionType, List<ScoreboardCategory> items, DrpUser user, int timeZoneOffset)
	    throws ServiceException;

    /**
     * Returns list of daily store goals.
     * 
     * @param storeId
     *            - store id
     * @return returns list of daily store goals.
     * @throws ServiceException
     */
    public List<ScoreboardDailyGoal> getStoreDailyGoals(String storeId, ScoreboardStoreType storeType,
	    ScoreboardOperationType operationType) throws ServiceException;

    /**
     * Saves daily store goals to repository.
     * 
     * @param storeId
     *            store id
     * @param storeType
     *            the store type
     * @param operationType
     *            the operation type
     * @param dailyGoalsList
     *            list of daily goals
     * @return returns saved list of daily store goals.
     * @throws ServiceException
     */
    public List<ScoreboardDailyGoal> saveStoreDailyGoals(String storeId, ScoreboardStoreType storeType,
	    ScoreboardOperationType operationType, List<ScoreboardDailyGoal> dailyGoalsList) throws ServiceException;

    /**
     * Retrieves user details from repository. If user does not exist then tries
     * get user details from LDAP and adds user details to repository.
     * 
     * @param empBbyId
     *            BBY user ID
     * @param storeId
     *            store ID
     * @param storeType
     *            the store type
     * @param operationType
     *            the operation type
     * @return returns employee data
     * @throws ServiceException
     */
    public ScoreboardEmployeeShift addEmployee(String empBbyId, String storeId, ScoreboardStoreType storeType,
	    ScoreboardOperationType operationType, DrpUser user) throws ServiceException;

    /**
     * Deletes user from employee shift list.
     * 
     * @param employee
     *            employee details
     * @throws ServiceException
     */
    public void removeEmployeeShift(ScoreboardEmployeeShift employee) throws ServiceException;

    /**
     * Retrieves store daily employee shift list.
     * 
     * @param storeId
     *             store id
     * @param storeType
     *            the store type
     * @param operationType
     *            the operation type
     * @return returns daily employee shift list
     * @throws ServiceException
     */
    public List<ScoreboardEmployeeShift> getStoreDailyEmployeeShift(String storeId, ScoreboardStoreType storeType,
	    ScoreboardOperationType operationType) throws ServiceException;

    /**
     * Saves daily employee shift to repository.
     * 
     * @param storeId
     *            store id
     * @param storeType
     *            the store type
     * @param operationType
     *            the operation type
     * @param dailyEmployeeShiftList
     *            list of store employee shift.
     * @return returns saved list of daily employee shift list
     * @throws ServiceException
     */
    public void saveStoreDailyEmployeeShift(String storeId, ScoreboardStoreType storeType,
	    ScoreboardOperationType operationType, List<ScoreboardEmployeeShift> dailyEmployeeShiftList)
	    throws ServiceException;

    /**
     * Returns store information based on store ID.
     * 
     * @param storeId
     *            store id
     * @return returns store information
     * @throws ServiceException
     * @throws IseBusinessException
     */
    public Store getStoreInfo(String storeId) throws ServiceException, IseBusinessException;

    /**
     * Returns list of widgets.
     * 
     * @param storeId
     *            store id
     * @param businessSourceId
     *            SWAS/SAS
     * @param localDate
     *            local date time
     */
    public List<ScoreboardWidget> getScoreboardWidgetList(String storeId, String businessSourceId, Date localDate);

    /**
     * Returns list of widgets.
     * 
     * @param storeId
     *            store id
     * @param storeType
     *            SWAS/SAS
     * @param localDate
     *            local date time
     */
    public ScoreboardEmployeeDistribution getScoreboardEmployeeDistribution(String storeId,
	    ScoreboardStoreType storeType, ScoreboardOperationType operationType, Date localDate);

    /**
     * Returns zone list.
     * 
     * @param storeType
     *            store type
     * @param operationType
     *            operation type
     * @return returns list of zones
     * @throws ServiceException
     */
    public List<Config> getZoneList(ScoreboardStoreType storeType, ScoreboardOperationType operationType)
	    throws ServiceException;

    /**
     * Update transaction status to deleted.
     * 
     * @param dataItem
     *            data to be updated
     * @param user
     *            current user
     * @throws ServiceException
     */
    void makeTransactionStatusDeleted(ScoreboardDataItem dataItem, DrpUser user) throws ServiceException;

    /**
     * Returns sales transaction list for given employee.
     * 
     * @param storeId
     *            store id
     * @param storeType
     *            store type
     * @param operationType
     *            operation type
     * @param emp
     *            employee info
     * @return returns list of data items
     * @throws ServiceException
     */
    List<ScoreboardDataItem> getEmployeeSalesTransactions(String storeId, ScoreboardStoreType storeType,
	    ScoreboardOperationType operationType, ScoreboardEmployeeShift emp) throws ServiceException;

    /**
     * Returns store return transaction list for given store.
     * 
     * @param storeId
     *            store id
     * @param storeType
     *            store type
     * @param operationType
     *            operation type
     * @return returns list of data items
     * @throws ServiceException
     */
    List<ScoreboardDataItem> getStoreReturnsTransactions(String storeId, ScoreboardStoreType storeType,
	    ScoreboardOperationType operationType) throws ServiceException;
}
