package com.bestbuy.bbym.ise.drp.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bestbuy.bbym.ise.domain.Store;
import com.bestbuy.bbym.ise.domain.User;
import com.bestbuy.bbym.ise.drp.dao.ConfigDao;
import com.bestbuy.bbym.ise.drp.dao.ScoreboardDao;
import com.bestbuy.bbym.ise.drp.domain.Config;
import com.bestbuy.bbym.ise.drp.domain.DrpUser;
import com.bestbuy.bbym.ise.drp.domain.scoreboard.ScoreboardCategory;
import com.bestbuy.bbym.ise.drp.domain.scoreboard.ScoreboardDailyGoal;
import com.bestbuy.bbym.ise.drp.domain.scoreboard.ScoreboardData;
import com.bestbuy.bbym.ise.drp.domain.scoreboard.ScoreboardDataItem;
import com.bestbuy.bbym.ise.drp.domain.scoreboard.ScoreboardEmployeeDistribution;
import com.bestbuy.bbym.ise.drp.domain.scoreboard.ScoreboardEmployeeNotionalMargin;
import com.bestbuy.bbym.ise.drp.domain.scoreboard.ScoreboardEmployeeShift;
import com.bestbuy.bbym.ise.drp.domain.scoreboard.ScoreboardNotionalMargin;
import com.bestbuy.bbym.ise.drp.domain.scoreboard.ScoreboardOperationType;
import com.bestbuy.bbym.ise.drp.domain.scoreboard.ScoreboardStoreType;
import com.bestbuy.bbym.ise.drp.domain.scoreboard.ScoreboardTransactionType;
import com.bestbuy.bbym.ise.drp.domain.scoreboard.ScoreboardWidget;
import com.bestbuy.bbym.ise.drp.sao.EmployeeLookupSao;
import com.bestbuy.bbym.ise.drp.sao.StoreInformationSao;
import com.bestbuy.bbym.ise.drp.sao.UpgradeCheckerSao;
import com.bestbuy.bbym.ise.exception.IseBusinessException;
import com.bestbuy.bbym.ise.exception.IseExceptionCodeEnum;
import com.bestbuy.bbym.ise.exception.ServiceException;

@Transactional
@Service("scoreboardService")
public class ScoreboardServiceImpl implements ScoreboardService {

    private static final int UPGRADE_CHECK_IDX = 7;

    private static Logger logger = LoggerFactory.getLogger(ScoreboardServiceImpl.class);

    private static final String SCOREBOARD_COMPUTING_WIDGET_LIST = "SCOREBOARD_COMPUTING_WIDGET_LIST";
    private static final String SCOREBOARD_SWAS_MOBILE_WIDGET_LIST = "SCOREBOARD_SWAS_MOBILE_WIDGET_LIST";
    private static final String SCOREBOARD_SAS_MOBILE_WIDGET_LIST = "SCOREBOARD_SAS_MOBILE_WIDGET_LIST";
    private static final int SCOREBOARD_WIDGET_LIST_SIZE = 8;
    private static final String NOTIONAL_MARGIN = "Notional Margin";

    @Autowired
    private ScoreboardDao scoreboardDao;

    @Autowired
    private ConfigDao configDao;

    @Autowired
    private StoreInformationSao storeInformationSao;

    @Autowired
    private EmployeeLookupSao employeeLookupSao;

    @Autowired
    private UpgradeCheckerSao upgradeCheckerSao;

    @Override
    @Transactional(readOnly = false, rollbackFor = ServiceException.class)
    public ScoreboardData getScoreBoardData(String storeId, ScoreboardOperationType operationType,
	    ScoreboardStoreType storeType, Date localDate, String userId) throws ServiceException {
	ScoreboardData sbData = new ScoreboardData();
	try{

	    initializeStore(userId, storeId, localDate);

	    ScoreboardNotionalMargin storeNtlMrgn = scoreboardDao.getStoreNotionalMargin(storeId, storeType,
		    operationType, localDate);
	    storeNtlMrgn.setNextHour(getNextHourDisplayText(localDate));
	    sbData.setScoreboardNotionalMargin(storeNtlMrgn);

	    sbData.setWidgetList(this.getScoreboardWidgetList(storeId, storeType.toString() + "_"
		    + operationType.toString(), localDate));

	    sbData.setEmployeeNotionalMarginList(loadEmployeeNotionalMarginList(storeId, storeType, operationType,
		    localDate, sbData.getScoreboardNotionalMargin().getTargetValue()));

	}catch(Exception e){
	    logger.error("SCOREBOARD: Unexpected Error:", e);
	    throw new ServiceException(IseExceptionCodeEnum.BusinessException, e.getMessage());
	}
	return sbData;
    }

    private String getNextHourDisplayText(Date localDate) {
	Calendar cal = Calendar.getInstance();
	cal.setTime(localDate);

	int hour = cal.get(Calendar.HOUR_OF_DAY);
	int minute = cal.get(Calendar.MINUTE);

	if (minute != 0){
	    hour++;
	}

	String nextHour;
	if (hour > 12){
	    nextHour = "" + (hour - 12) + ":00 PM";
	}else{
	    nextHour = "" + hour + ":00 AM";
	}
	return nextHour;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    private void initializeStore(String userId, String storeId, Date localDate) {
	long empCount = scoreboardDao.getInitializedEmployeeCount(storeId, localDate);
	logger.debug("Initialize store: " + storeId + ", employee count:" + empCount + ", localDate:" + localDate);
	if (empCount == 0){
	    scoreboardDao.initializeStoreData(userId, storeId);
	    logger.debug("Initialized store data:" + storeId);
	}
    }

    private List<ScoreboardEmployeeNotionalMargin> loadEmployeeNotionalMarginList(String storeId,
	    ScoreboardStoreType storeType, ScoreboardOperationType operationType, Date localDate, int goal) {
	logger.debug("Begin [loadEmployeeNotionalMarginList].");
	logger.debug("storeId:" + storeId);
	logger.debug("localDate:" + localDate);
	logger.debug("goal:" + goal);
	List<ScoreboardEmployeeNotionalMargin> list = scoreboardDao.getEmployeeShiftNotionalMarginList(storeId,
		storeType, operationType, localDate);

	float grabAndGo = 0;
	if (storeType == ScoreboardStoreType.SWAS && operationType == ScoreboardOperationType.COMPUTING){
	    String businessSource = storeType.toString() + "_" + operationType.toString();
	    List<ScoreboardDailyGoal> goalList = scoreboardDao.getDailyGoalList(storeId, businessSource);
	    for(ScoreboardDailyGoal goalItem: goalList){
		if ("GRAB_AND_GO_PERCENT".equals(goalItem.getName())){
		    grabAndGo = goalItem.getGoalValue().floatValue() / 100;
		    break;
		}
	    }
	}
	logger.debug("grabAndGo:" + grabAndGo);

	float totalStoreHours = 0;
	float perHourTarget = 0;
	if (list != null){
	    for(ScoreboardEmployeeNotionalMargin empMrgn: list){
		float diff = empMrgn.getEmployeeShift().getShiftEndTime().getTime()
			- empMrgn.getEmployeeShift().getShiftStartTime().getTime();
		diff = diff / (60 * 60 * 1000);
		totalStoreHours += diff;
		logger.debug("" + empMrgn.getProductivity());
	    }
	    perHourTarget = (goal - (grabAndGo * goal)) / totalStoreHours;

	    for(ScoreboardEmployeeNotionalMargin empMrgn: list){
		float diff = empMrgn.getEmployeeShift().getShiftEndTime().getTime()
			- empMrgn.getEmployeeShift().getShiftStartTime().getTime();
		diff = diff / (60 * 60 * 1000);
		empMrgn.setTargetValue((int) Math.round((double) diff * perHourTarget));
	    }
	}
	logger.debug("End [loadEmployeeNotionalMarginList].");
	return list;
    }

    @Override
    public List<ScoreboardCategory> getScoreboardItems(String storeId, Long ntlMrgnDlyEmpId,
	    ScoreboardStoreType storeType, ScoreboardOperationType operationType,
	    ScoreboardTransactionType transactionType) throws ServiceException {
	logger.debug("Begin [getScoreboardItems].");
	logger.debug("storeId:" + storeId);
	logger.debug("ntlMrgnDlyEmpId:" + ntlMrgnDlyEmpId);
	List<ScoreboardCategory> list = new ArrayList<ScoreboardCategory>();
	try{
	    String businessSource = storeType.toString() + "_" + operationType.toString();
	    logger.debug("businessSource:" + businessSource);
	    String categoryType = "SCOREBOARD_ITEMS_" + storeType.toString() + "_" + operationType.toString();

	    List<ScoreboardDataItem> items = null;

	    logger.debug("transactionType:" + transactionType);
	    if (transactionType == ScoreboardTransactionType.STORE_DETAILS){
		items = scoreboardDao.getStoreDetails(storeId, storeType, operationType);

	    }else if (transactionType == ScoreboardTransactionType.EMP_SALES_DETAILS){
		items = scoreboardDao.getEmployeeDetails(storeId, storeType, operationType, ntlMrgnDlyEmpId);

	    }else{
		items = scoreboardDao.getCategoryItems(storeId, ntlMrgnDlyEmpId, businessSource, categoryType);

	    }
	    ScoreboardCategory category = null;

	    for(ScoreboardDataItem item: items){
		if (item.getNtlMarginValueId() == null || item.getNtlMarginValueId().intValue() == 0){
		    category = new ScoreboardCategory();
		    category.setDataItems(new ArrayList<ScoreboardDataItem>());
		    category.setId(item.getId());
		    category.setName(item.getName());
		    list.add(category);
		}else{
		    if (transactionType == ScoreboardTransactionType.EMP_SALES_POST
			    || transactionType == ScoreboardTransactionType.STORE_RETURNS_POST){
			item.setSalesQuantity(0);
			item.setReturnQuantity(0);
		    }
		    if (category != null){
			category.getDataItems().add(item);
		    }
		}
	    }
	}catch(Exception e){
	    logger.error("SCOREBOARD: Unexpected Error:", e);
	    throw new ServiceException(IseExceptionCodeEnum.BusinessException, e.getMessage());
	}
	logger.debug("End [getScoreboardItems].");
	return list;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = ServiceException.class)
    public void saveScoreboardItems(String storeId, ScoreboardEmployeeShift emp,
	    ScoreboardTransactionType transactionType, List<ScoreboardCategory> items, DrpUser user, int timeZoneOffset)
	    throws ServiceException {
	logger.debug("Begin save scoreboard items.");
	try{
	    if (items != null){
		String tranTypeName = "RETURN";
		if (transactionType == ScoreboardTransactionType.EMP_SALES_POST
			|| transactionType == ScoreboardTransactionType.EMP_SALES_DETAILS){
		    tranTypeName = "SALE";

		}
		Config tranType = configDao.getConfigItemsByTypeAndParamName("TRAN_TYP", tranTypeName).get(0);

		List<ScoreboardDataItem> dataItems = new ArrayList<ScoreboardDataItem>();

		Calendar cal = Calendar.getInstance();
		Date date = cal.getTime();
		cal.add(Calendar.MILLISECOND, timeZoneOffset);
		Date localDate = cal.getTime();

		for(ScoreboardCategory cat: items){
		    if (cat.getDataItems() != null){
			for(ScoreboardDataItem item: cat.getDataItems()){

			    Integer qty = null;
			    if (transactionType == ScoreboardTransactionType.EMP_SALES_POST){
				qty = item.getSalesQuantity();
			    }else if (transactionType == ScoreboardTransactionType.STORE_RETURNS_POST){
				qty = item.getSalesQuantity();
				// qty = item.getReturnQuantity();
			    }

			    if (qty != null && qty.intValue() != 0){

				qty = Math.abs(qty);
				ScoreboardDataItem transItem = new ScoreboardDataItem();

				if (transactionType == ScoreboardTransactionType.STORE_RETURNS_POST){
				    qty = -qty;
				}

				transItem.setEditQuantity(new Integer(qty));
				if (item.getEditUnitPrice() != null){
				    transItem.setEditUnitPrice(new BigDecimal(item.getEditUnitPrice().abs()
					    .doubleValue()));
				}else{
				    transItem.setEditUnitPrice(null);
				}
				transItem.setNtlMarginValueId(item.getNtlMarginValueId());
				transItem.setTransactionDate(localDate);
				transItem.setCreatedBy(user.getUserId().toUpperCase());
				transItem.setCreatedOn(date);
				dataItems.add(transItem);
			    }
			}
		    }
		}
		Long empId = null;
		if (emp != null){
		    empId = emp.getEmpDailyNtlMrgnId();
		}
		scoreboardDao.insertTransaction(storeId, empId, tranType.getConfigId(), dataItems);
	    }
	}catch(Exception e){
	    logger.error("SCOREBOARD: Unexpected Error:", e);
	    throw new ServiceException(IseExceptionCodeEnum.BusinessException, e.getMessage());
	}
	logger.debug("End save scoreboard items.");
    }

    @Override
    public List<ScoreboardDailyGoal> getStoreDailyGoals(String storeId, ScoreboardStoreType storeType,
	    ScoreboardOperationType operationType) throws ServiceException {
	List<ScoreboardDailyGoal> list = null;

	try{
	    String bsnSource = storeType.toString() + "_" + operationType.toString();
	    list = scoreboardDao.getDailyGoalList(storeId, bsnSource);
	    for(Iterator<ScoreboardDailyGoal> iterator = list.iterator(); iterator.hasNext();){
		ScoreboardDailyGoal scoreboardDailyGoal = (ScoreboardDailyGoal) iterator.next();
		if (scoreboardDailyGoal.getId() == null || scoreboardDailyGoal.getId().intValue() == 0){
		    scoreboardDailyGoal.setGoalValue(scoreboardDailyGoal.getDefaultValue());
		}
	    }
	}catch(Exception e){
	    logger.error("SCOREBOARD: Unexpected Error:", e);
	    throw new ServiceException(IseExceptionCodeEnum.BusinessException, e.getMessage());
	}

	return list;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = ServiceException.class)
    public List<ScoreboardDailyGoal> saveStoreDailyGoals(String storeId, ScoreboardStoreType storeType,
	    ScoreboardOperationType operationType, List<ScoreboardDailyGoal> dailyGoalsList) throws ServiceException {
	try{
	    Config bsnSource = null;

	    for(ScoreboardDailyGoal goal: dailyGoalsList){
		if (goal.getId() == null || goal.getId().intValue() == 0){
		    // insert
		    if (bsnSource == null){
			String bsnSourceName = storeType.toString() + "_" + operationType.toString();
			bsnSource = configDao.getConfigItemsByTypeAndParamName("BSNS_SRC_NM", bsnSourceName).get(0);
		    }
		    goal = scoreboardDao.persistGoal(storeId, bsnSource.getConfigId(), goal);
		}else{
		    // update
		    goal = scoreboardDao.updateGoal(goal);
		}

	    }

	}catch(Exception e){
	    logger.error("SCOREBOARD: Unexpected Error:", e);
	    throw new ServiceException(IseExceptionCodeEnum.BusinessException, e.getMessage());
	}
	return dailyGoalsList;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = ServiceException.class)
    public ScoreboardEmployeeShift addEmployee(String empBbyId, String storeId, ScoreboardStoreType storeType,
	    ScoreboardOperationType operationType, DrpUser user) throws ServiceException {
	ScoreboardEmployeeShift empShift = null;
	try{
	    empShift = scoreboardDao.getEmployeeShift(empBbyId, storeId, storeType.toString() + "_"
		    + operationType.toString());
	    if (empShift == null){
		User userInfo;
		try{// call service to get first and last name
		    userInfo = employeeLookupSao.getEmployeeDetails(empBbyId);
		}catch(Exception ex){
		    logger.error("SCOREBOARD: Unexpected Error:", ex);
		    throw new ServiceException(IseExceptionCodeEnum.INTERNALSERVERERROR);
		}
		if (userInfo == null){
		    throw new ServiceException(IseExceptionCodeEnum.NOTFOUND);
		}
		empShift = new ScoreboardEmployeeShift();
		empShift.setEmpBBYId(empBbyId);
		empShift.setEmpFullName(userInfo.getFirstName() + " " + userInfo.getLastName());
		empShift.setCreatedBy(user.getUserId().toUpperCase());
		empShift = scoreboardDao.persistEmployee(empShift);
	    }
	    if (empShift.getEmpDailyNtlMrgnId() == null || empShift.getEmpDailyNtlMrgnId().intValue() == 0){
		empShift.setStoreId(storeId);
		String bsnSourceName = storeType.toString() + "_" + operationType.toString();
		Config bsnSource = configDao.getConfigItemsByTypeAndParamName("BSNS_SRC_NM", bsnSourceName).get(0);
		empShift.setBusinessSourceId(bsnSource.getConfigId());
		empShift.setShiftStartTime(null);
		empShift.setShiftEndTime(null);
		empShift.setPrimaryZone(null);
		empShift.setSecondaryZone(null);
		empShift.setCreatedBy(user.getUserId());
		empShift = scoreboardDao.persistEmployeeShift(empShift);
	    }else{
		throw new ServiceException(IseExceptionCodeEnum.DataItemAlreadyExists,
			"The BBY Network ID you entered already exists.");
	    }

	}catch(ServiceException se){
	    logger.error("SCOREBOARD: Unexpected Error:", se);
	    throw se;
	}catch(Exception e){
	    logger.error("SCOREBOARD: Unexpected Error:", e);
	    throw new ServiceException(IseExceptionCodeEnum.BusinessException, e.getMessage());
	}
	return empShift;
    }

    @Override
    public List<ScoreboardEmployeeShift> getStoreDailyEmployeeShift(String storeId, ScoreboardStoreType storeType,
	    ScoreboardOperationType operationType) throws ServiceException {
	List<ScoreboardEmployeeShift> list = null;

	try{
	    String bsnSource = storeType.toString() + "_" + operationType.toString();
	    list = scoreboardDao.getDailyEmployeeList(storeId, bsnSource);
	    for(ScoreboardEmployeeShift emp: list){
		if (emp.getShiftStartTime() != null && emp.getShiftStartTime().getTime() != 0){
		    emp.setSelected(true);
		}
	    }

	}catch(Exception e){
	    logger.error("SCOREBOARD: Unexpected Error:", e);
	    throw new ServiceException(IseExceptionCodeEnum.BusinessException, e.getMessage());
	}

	return list;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = ServiceException.class)
    public void saveStoreDailyEmployeeShift(String storeId, ScoreboardStoreType storeType,
	    ScoreboardOperationType operationType, List<ScoreboardEmployeeShift> dailyEmployeeShiftList)
	    throws ServiceException {
	try{

	    for(ScoreboardEmployeeShift empShift: dailyEmployeeShiftList){
		empShift = scoreboardDao.updateEmployeeShift(empShift);
	    }
	}catch(Exception e){
	    logger.error("SCOREBOARD: Unexpected Error:", e);
	    throw new ServiceException(IseExceptionCodeEnum.BusinessException, e.getMessage());
	}
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = ServiceException.class)
    public void removeEmployeeShift(ScoreboardEmployeeShift employee) throws ServiceException {
	try{
	    int count = scoreboardDao.getEmployeeTransactionCount(employee);
	    if(count > 0){
		throw new ServiceException(IseExceptionCodeEnum.DataItemAlreadyExists,"Employee shift can not be deleted. The employee has transactions.");
	    }
	    scoreboardDao.deleteEmployeeShift(employee);
	}catch(ServiceException se){
	    throw se;
	}catch(Exception e){
	    logger.error("SCOREBOARD: Unexpected Error:", e);
	    throw new ServiceException(IseExceptionCodeEnum.BusinessException, e.getMessage());
	}
    }

    @Override
    public Store getStoreInfo(String storeId) throws ServiceException, IseBusinessException {
	return storeInformationSao.getStoreDetails(storeId);
    }

    @Override
    public List<ScoreboardWidget> getScoreboardWidgetList(String storeId, String businessSourceId, Date localDate) {

	List<Config> configList = new ArrayList<Config>();
	List<ScoreboardWidget> scoreBoardWidgetList = new ArrayList<ScoreboardWidget>();
	String operationType = businessSourceId.split("_")[1];
	String storeType = businessSourceId.split("_")[0];

	if (operationType.equalsIgnoreCase("MOBILE")){
	    if (storeType.equalsIgnoreCase(ScoreboardStoreType.SAS.toString()))
		configList = configDao.getConfigItemsByType(SCOREBOARD_SAS_MOBILE_WIDGET_LIST);
	    else if (storeType.equalsIgnoreCase(ScoreboardStoreType.SWAS.toString()))
		configList = configDao.getConfigItemsByType(SCOREBOARD_SWAS_MOBILE_WIDGET_LIST);
	}else if (operationType.equalsIgnoreCase("COMPUTING")){
	    configList = configDao.getConfigItemsByType(SCOREBOARD_COMPUTING_WIDGET_LIST);
	}

	if (configList.size() != SCOREBOARD_WIDGET_LIST_SIZE){
	    logger.error("widget list is not complete in config table");
	    // throw some exception
	}else{
	}

	for(Config config: configList){
	    ScoreboardWidget scoreboardWidget = scoreboardDao.getScoreboardWidget(storeId, businessSourceId, localDate,
		    configList.indexOf(config));
	    int idx = configList.indexOf(config);
	    scoreboardWidget.setId((long) idx);
	    scoreboardWidget.setWidgetName(config.getDescription());
	    scoreboardWidget.setWidgetType(ScoreboardWidget.Type.valueOf(config.getParamValue().trim().toUpperCase()));

	    if (idx == UPGRADE_CHECK_IDX && operationType.equalsIgnoreCase("MOBILE")){
		//loading upgrade checks value
		try{
		    DrpUser drpUser = new DrpUser();
		    drpUser.setStoreId(storeId);
		    Store store;
		    store = upgradeCheckerSao.getUCSStoreCount(drpUser);
		    scoreboardWidget.setCurrentVal(store.getUpgradeCheckCount());
		}catch(Exception e){
		    logger.warn("Failed get upgrade checks count.", e);
		    scoreboardWidget.setCurrentVal(0);
		}
	    }

	    scoreBoardWidgetList.add(scoreboardWidget);
	}
	return scoreBoardWidgetList;
    }

    @Override
    public ScoreboardEmployeeDistribution getScoreboardEmployeeDistribution(String storeId,
	    ScoreboardStoreType storeType, ScoreboardOperationType operationType, Date localDate) {

	ScoreboardEmployeeDistribution scoreboardEmployeeDistribution = scoreboardDao
		.getScoreboardEmployeeDistribution(storeId, storeType, operationType, localDate);

	Long storeGoalValue = 0L;
	for(ScoreboardDailyGoal scoreboardDailyGoal: scoreboardEmployeeDistribution.getScoreboardDailyGoal()){
	    if (scoreboardDailyGoal.getName().equalsIgnoreCase(NOTIONAL_MARGIN))
		storeGoalValue = scoreboardDailyGoal.getGoalValue();
	}
	float totalStoreHours = 0;
	float perHourTarget = 0;
	if (scoreboardEmployeeDistribution.getScoreboardEmployeeScheduleList() != null){
	    for(ScoreboardEmployeeNotionalMargin empMrgn: scoreboardEmployeeDistribution
		    .getScoreboardEmployeeScheduleList()){
		float diff = empMrgn.getEmployeeShift().getShiftEndTime().getTime()
			- empMrgn.getEmployeeShift().getShiftStartTime().getTime();
		diff = diff / (60 * 60 * 1000);
		totalStoreHours += diff;
	    }

	    scoreboardEmployeeDistribution.setTotalHours(totalStoreHours);

	    perHourTarget = storeGoalValue / totalStoreHours;
	    for(ScoreboardEmployeeNotionalMargin empMrgn: scoreboardEmployeeDistribution
		    .getScoreboardEmployeeScheduleList()){
		float diff = empMrgn.getEmployeeShift().getShiftEndTime().getTime()
			- empMrgn.getEmployeeShift().getShiftStartTime().getTime();
		diff = diff / (60 * 60 * 1000);
		empMrgn.setTargetValue((int) Math.round((double) diff * perHourTarget));
	    }
	}
	return scoreboardEmployeeDistribution;

    }

    @Override
    public List<Config> getZoneList(ScoreboardStoreType storeType, ScoreboardOperationType operationType)
	    throws ServiceException {

	try{
	    return configDao
		    .getConfigItemsByType(storeType.toString() + "_" + operationType.toString() + "_ZONE", true);
	}catch(Exception e){
	    logger.error("SCOREBOARD: Unexpected Error:", e);
	    throw new ServiceException(IseExceptionCodeEnum.BusinessException, e.getMessage());
	}
    }

    @Override
    public void makeTransactionStatusDeleted(ScoreboardDataItem dataItem, DrpUser user) throws ServiceException {
	try{
	    dataItem.setModifiedBy(user.getUserId());
	    scoreboardDao.deleteTransaction(dataItem);
	}catch(Exception e){
	    logger.error("SCOREBOARD: Unexpected Error:", e);
	    throw new ServiceException(IseExceptionCodeEnum.BusinessException, e.getMessage());
	}

    }

    @Override
    public List<ScoreboardDataItem> getEmployeeSalesTransactions(String storeId, ScoreboardStoreType storeType,
	    ScoreboardOperationType operationType, ScoreboardEmployeeShift emp) throws ServiceException {
	if (logger.isDebugEnabled()){
	    logger.debug("Begin [getEditEmployeeSales].");
	    logger.debug("storeId:" + storeId);
	    logger.debug("emp dly Id:" + emp.getEmpDailyNtlMrgnId());
	    logger.debug("storeType:" + storeType);
	    logger.debug("operationType:" + operationType);
	}
	try{
	    return scoreboardDao.getEmployeeSaleTransactions(storeId, storeType, operationType, emp.getEmpDailyNtlMrgnId());
	}catch(Exception e){
	    logger.error("SCOREBOARD: Unexpected Error:", e);
	    throw new ServiceException(IseExceptionCodeEnum.BusinessException, e.getMessage());
	}
    }

    @Override
    public List<ScoreboardDataItem> getStoreReturnsTransactions(String storeId, ScoreboardStoreType storeType,
	    ScoreboardOperationType operationType) throws ServiceException {
	try{
	    return scoreboardDao.getStoreReturnTransactions(storeId, storeType, operationType);
	}catch(Exception e){
	    logger.error("SCOREBOARD: Unexpected Error:", e);
	    throw new ServiceException(IseExceptionCodeEnum.BusinessException, e.getMessage());
	}
    }

}
