/**
 * Best Buy Inc (c) 2012
 */
package com.bestbuy.bbym.ise.drp.dao;

import java.util.Date;
import java.util.List;

import com.bestbuy.bbym.ise.drp.domain.scoreboard.ScoreboardDailyGoal;
import com.bestbuy.bbym.ise.drp.domain.scoreboard.ScoreboardDataItem;
import com.bestbuy.bbym.ise.drp.domain.scoreboard.ScoreboardEmployeeDistribution;
import com.bestbuy.bbym.ise.drp.domain.scoreboard.ScoreboardEmployeeNotionalMargin;
import com.bestbuy.bbym.ise.drp.domain.scoreboard.ScoreboardEmployeeShift;
import com.bestbuy.bbym.ise.drp.domain.scoreboard.ScoreboardNotionalMargin;
import com.bestbuy.bbym.ise.drp.domain.scoreboard.ScoreboardOperationType;
import com.bestbuy.bbym.ise.drp.domain.scoreboard.ScoreboardStoreType;
import com.bestbuy.bbym.ise.drp.domain.scoreboard.ScoreboardWidget;

/**
 * Interface for score board DAO operations.
 * 
 * @author Yernar Khuangan (a1003810)
 * @version 1.0
 * @since iteration 25
 */
public interface ScoreboardDao {

    /**
     * Returns score board employee list with notional margin data.
     * 
     * @param storeId
     *            store ID
     * @param storeType
     *            store type SWAS or SAS
     * @param operationType
     *            operation type MOBILE or COMPUTING
     * @param localDate
     *            client date
     * @return returns employee list
     */
    List<ScoreboardEmployeeNotionalMargin> getEmployeeShiftNotionalMarginList(String storeId,
	    ScoreboardStoreType storeType, ScoreboardOperationType operationType, Date localDate);

    /**
     * Returns number of initialized user for given store.
     * 
     * @param storeId
     *            store ID
     * @param localDate
     * @return returns number of employees
     */
    Long getInitializedEmployeeCount(String storeId, Date localDate);

    /**
     * Initializes store daily data.
     * 
     * @param userId
     *            user id
     * @param storeId
     *            store id
     */
    void initializeStoreData(String userId, String storeId);

    /**
     * Returns store current notional margin.
     * 
     * @param storeId
     *            store id
     * @param storeType
     *            store type SWAS or SAS
     * @param operationType
     *            operation type MOBILE or COMPUTING
     * @param localDate
     *            local date from client
     * @return returns store notional margin and next hour traffic
     */
    public ScoreboardNotionalMargin getStoreNotionalMargin(String storeId, ScoreboardStoreType storeType,
	    ScoreboardOperationType operationType, Date localDate);

    /**
     * Returns list of score board sales and return items.
     * 
     * @param storeId
     *            store ID
     * @param ntlMrgnDlyEmpId
     *            daily shift employee id
     * @param businessSource
     *            business source
     * @param categoryType
     *            configuration table type
     * @return returns list of score board categories
     */
    List<ScoreboardDataItem> getCategoryItems(String storeId, Long ntlMrgnDlyEmpId, String businessSource,
	    String categoryType);

    /**
     * Inserts transactions to repository.
     * 
     * @param storeId
     *            store id
     * @param ntlMrgnDlyEmpId
     *            employee shift id
     * @param tranType
     *            transaction type
     * @param items
     *            list of transaction items
     * @return returns array of inserted record numbers
     */
    int[] insertTransaction(String storeId, Long ntlMrgnDlyEmpId, Long tranType, List<ScoreboardDataItem> items);

    /**
     * Returns daily goals.
     * 
     * @param storeId
     *            store id
     * @param businessSource
     *            business source name
     * @return returns list of daily goals
     */
    List<ScoreboardDailyGoal> getDailyGoalList(String storeId, String businessSource);

    /**
     * Persists daily goal.
     * 
     * @param storeId
     *            store id
     * @param bsnSourceId
     *            business source id
     * @param goal
     *            goal object
     * @return returns saved goal object
     */
    ScoreboardDailyGoal persistGoal(String storeId, Long bsnSourceId, ScoreboardDailyGoal goal);

    /**
     * Updates goal object in repository.
     * 
     * @param goal
     *            goal object
     * @return returns updated goal object
     */
    ScoreboardDailyGoal updateGoal(ScoreboardDailyGoal goal);

    /**
     * Deletes employee shift from repository.
     * 
     * @param employee
     *            employee shift object
     */
    void deleteEmployeeShift(ScoreboardEmployeeShift employee);

    /**
     * Returns employee shift object.
     * 
     * @param empBbyId
     *            employee BBY id
     * @param storeId
     *            store id
     * @param businessSource
     *            business source name
     * @return returns employee shift object
     */
    ScoreboardEmployeeShift getEmployeeShift(String empBbyId, String storeId, String businessSource);

    /**
     * Persists employee.
     * 
     * @param empShift
     *            employee shift
     * @return returns persisted employee object
     */
    ScoreboardEmployeeShift persistEmployee(ScoreboardEmployeeShift empShift);

    /**
     * Persists employee shift.
     * 
     * @param empShift
     *            employee shift object
     * @return returns persisted employee shift object
     */
    ScoreboardEmployeeShift persistEmployeeShift(ScoreboardEmployeeShift empShift);

    /**
     * Returns daily employee shift list.
     * 
     * @param storeId
     *            store id
     * @param bsnSource
     *            business source name
     * @return returns list of employee shift
     */
    List<ScoreboardEmployeeShift> getDailyEmployeeList(String storeId, String bsnSource);

    /**
     * Updates employee shift object in repository.
     * 
     * @param empShift
     *            employee shift object
     * @return returns updated employee shift object
     */
    ScoreboardEmployeeShift updateEmployeeShift(ScoreboardEmployeeShift empShift);

    /**
     * Returns store sales and returns details.
     * 
     * @param storeId
     *            store id
     * @param storeType
     *            store type
     * @param operationType
     *            operation type
     * @return returns list of data items
     */
    List<ScoreboardDataItem> getStoreDetails(String storeId, ScoreboardStoreType storeType,
	    ScoreboardOperationType operationType);

    /**
     * Returns employee sales and returns details.
     * 
     * @param storeId
     *            store id
     * @param storeType
     *            store type
     * @param operationType
     *            operation type
     * @param ntlMrgnDlyEmpId
     *            employee shift id
     * @return returns list of data items
     */
    List<ScoreboardDataItem> getEmployeeDetails(String storeId, ScoreboardStoreType storeType,
	    ScoreboardOperationType operationType, Long ntlMrgnDlyEmpId);

    /**
     * Loads widget data.
     * 
     * @param storeId
     *            store id
     * @param businessSourceId
     *            business source id
     * @param localDate
     *            local date
     * @param widgetOrder
     *            widget order
     * @return returns widget object
     */
    public ScoreboardWidget getScoreboardWidget(String storeId, String businessSourceId, Date localDate, int widgetOrder);

    /**
     * Returns employee distribution.
     * 
     * @param storeId
     *            store id
     * @param storeType
     *            store type
     * @param operationType
     *            operation type
     * @param localDate
     *            local date
     * @return returns employee distribution object
     */
    public ScoreboardEmployeeDistribution getScoreboardEmployeeDistribution(String storeId,
	    ScoreboardStoreType storeType, ScoreboardOperationType operationType, Date localDate);

    /**
     * Makes transaction flag deleted.
     * 
     * @param dataItem
     *            transaction
     */
    void deleteTransaction(ScoreboardDataItem dataItem);

    /**
     * Returns list of employee sales transactions
     * 
     * @param storeId
     *            store id
     * @param storeType
     *            store type
     * @param operationType
     *            operation type
     * @param empId
     *            employee shift id
     * @return returns list of employee sales transactions
     */
    List<ScoreboardDataItem> getEmployeeSaleTransactions(String storeId, ScoreboardStoreType storeType,
	    ScoreboardOperationType operationType, Long empId);

    /**
     * Returns list of store return transactions.
     * 
     * @param storeId
     *            store Id
     * @param storeType
     *            store type
     * @param operationType
     *            operation type
     * @return returns list of transactions
     */
    List<ScoreboardDataItem> getStoreReturnTransactions(String storeId, ScoreboardStoreType storeType,
	    ScoreboardOperationType operationType);

    /**
     * Returns transaction number for employee shift.
     * 
     * @param employee
     *            employee shift
     * @return returns number of transaction
     */
    int getEmployeeTransactionCount(ScoreboardEmployeeShift employee);

}
