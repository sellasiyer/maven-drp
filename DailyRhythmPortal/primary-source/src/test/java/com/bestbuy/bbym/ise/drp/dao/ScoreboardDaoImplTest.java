package com.bestbuy.bbym.ise.drp.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.StandardToStringStyle;
import org.apache.commons.lang.builder.ToStringStyle;
import org.codehaus.jackson.map.util.JSONPObject;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.util.ReflectionTestUtils;

import com.bestbuy.bbym.ise.drp.domain.Config;
import com.bestbuy.bbym.ise.drp.domain.scoreboard.ScoreboardDailyGoal;
import com.bestbuy.bbym.ise.drp.domain.scoreboard.ScoreboardDataItem;
import com.bestbuy.bbym.ise.drp.domain.scoreboard.ScoreboardEmployeeNotionalMargin;
import com.bestbuy.bbym.ise.drp.domain.scoreboard.ScoreboardEmployeeShift;
import com.bestbuy.bbym.ise.drp.domain.scoreboard.ScoreboardNotionalMargin;
import com.bestbuy.bbym.ise.drp.domain.scoreboard.ScoreboardOperationType;
import com.bestbuy.bbym.ise.drp.domain.scoreboard.ScoreboardStoreType;
import com.bestbuy.bbym.ise.drp.domain.scoreboard.ScoreboardWidget;
import com.bestbuy.bbym.ise.util.jdbc.DatabaseScript;

@Ignore
public class ScoreboardDaoImplTest extends BaseDaoTest {

    private static final long SALE = 100L;

    private static final String STORE_0105 = "0105";

    private static final String SAS_COMPUTING = "SAS_COMPUTING";

    private static final String SAS_MOBILE = "SAS_MOBILE";

    private static final String SWAS_COMPUTING = "SWAS_COMPUTING";

    private static final String SWAS_MOBILE = "SWAS_MOBILE";

    private static final Long BS_SOURCE_ID_SWAS_MOBILE = 104L;

    private static final String STORE_0698 = "0698";

    private static final String STORE_1945 = "1945";

    private static final int WIDGET_COUNT = 8;

    ScoreboardDaoImpl dao = new ScoreboardDaoImpl();

    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm");

    @Before
    public void setUp() throws Exception {
	dao.setUseNextSequenceOnCreate(true);
	dao.setNamedJdbcTemplate(new NamedParameterJdbcTemplate(db));
	dao.setJdbcTemplate(new JdbcTemplate(db));

	ReflectionTestUtils.setField(dao, "configDao", new ConfigDaoImpl());
	dao.getConfigDao().setJdbcTemplate(new JdbcTemplate(db));
	dao.getConfigDao().setNamedJdbcTemplate(new NamedParameterJdbcTemplate(db));

	DatabaseScript.execute("ise_db_scripts/add_scoreboard_data.sql", db);
    }

    @Test
    public void testToDate() throws ParseException {
	String actual = dao.getJdbcTemplate().queryForObject(
		"SELECT to_date('10/08/2012 11:30','mm/dd/yyyy HH24:MI') from dual", String.class);
	assertEquals("2012-10-08 11:30:00.0", actual);

	actual = dao.getJdbcTemplate().queryForObject(
		"SELECT to_date('10/08/2012 11:30','MM/DD/YYYY HH24:MI') from dual", String.class);
	assertEquals("2012-10-08 11:30:00.0", actual);
    }

    @Test
    public void testGetInitializedEmployeeCount() throws Exception {
	Long actual = dao.getInitializedEmployeeCount(STORE_0105, sdf.parse("10/08/2012 14:00"));
	assertEquals(19, actual.intValue());
    }

    @Test
    public void testGetEmployeeShiftNotionalMarginList() throws Exception {
	List<ScoreboardEmployeeNotionalMargin> list = dao.getEmployeeShiftNotionalMarginList(STORE_0105,
		ScoreboardStoreType.SWAS, ScoreboardOperationType.MOBILE, sdf.parse("10/08/2012 14:00"));
	assertNotNull(list);
	assertEquals(6, list.size());

	assertEquals(
		"ScoreboardEmployeeNotionalMargin[employeeShift=ScoreboardEmployeeShift[empDailyNtlMrgnId=178,shiftStartTime=Date[],shiftEndTime=Date[],businessSourceId=104,storeId=0105,empFullName=Martin Conlin,empBBYId=A311001,empNtlMrgnId=41,primaryZone=Config[configId=122,configType=SWAS_MOBILE_ZONE,paramName=Department Host,paramValue=1,description=<null>,createdOn=Date[],modifiedOn=<null>,createdBy=A311048,modifiedBy=<null>],secondaryZone=Config[configId=124,configType=SWAS_MOBILE_ZONE,paramName=MP3,paramValue=3,description=<null>,createdOn=Date[],modifiedOn=<null>,createdBy=A311048,modifiedBy=<null>],isChecked=false,createdOn=<null>,modifiedOn=<null>,createdBy=<null>,modifiedBy=<null>],currentValue=0,targetValue=0,productivity=0,nextHour=<null>,nextHourPercentage=0,createdOn=<null>,modifiedOn=<null>,createdBy=<null>,modifiedBy=<null>]",
		ReflectionToStringBuilder.toString(list.get(0), new RecursiveToStringStyle()));
    }

    @Test
    public void testGetStoreNotionalMargin() throws Exception {
	SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm");
	ScoreboardNotionalMargin nm = dao.getStoreNotionalMargin(STORE_0105, ScoreboardStoreType.SWAS,
		ScoreboardOperationType.MOBILE, sdf.parse("10/08/2012 14:00"));
	assertEquals(12693, nm.getCurrentValue());
	assertEquals(5142, nm.getTargetValue());
	assertNotNull(nm);

	assertEquals(
		"ScoreboardNotionalMargin[currentValue=12693,targetValue=5142,productivity=0,nextHour=<null>,nextHourPercentage=0,createdOn=<null>,modifiedOn=<null>,createdBy=<null>,modifiedBy=<null>]",
		ReflectionToStringBuilder.toString(nm, new RecursiveToStringStyle()));
    }

    @Test
    public void testGetEmployeeShift() {
	ScoreboardEmployeeShift emp = dao.getEmployeeShift("", STORE_0105, SWAS_MOBILE);
	assertNull(emp);

	emp = dao.getEmployeeShift("A311048", STORE_0105, SWAS_MOBILE);
	assertEquals(1, emp.getEmpDailyNtlMrgnId().intValue());
	assertEquals(1, emp.getEmpNtlMrgnId().intValue());
    }

    @Test
    public void testPersistEmployee() {

	ScoreboardEmployeeShift empShift = new ScoreboardEmployeeShift();
	empShift.setEmpBBYId("A1003810");
	empShift.setEmpFullName("Emp Full Name");
	empShift.setCreatedBy("createdBy");
	empShift = dao.persistEmployee(empShift);

	assertNotNull(empShift.getEmpNtlMrgnId());
    }

    @Test
    public void testPersistEmployeeShift() {
	ScoreboardEmployeeShift empShift = new ScoreboardEmployeeShift();
	empShift.setEmpBBYId("A1003810");
	empShift.setEmpFullName("Emp Full Name");
	empShift.setCreatedBy("createdBy");
	empShift = dao.persistEmployee(empShift);

	empShift.setBusinessSourceId(BS_SOURCE_ID_SWAS_MOBILE);
	empShift.setStoreId(STORE_0105);
	empShift.setShiftStartTime(new Date());
	empShift.setShiftEndTime(new Date());
	Config primaryZone = new Config();
	primaryZone.setConfigId(100L);
	empShift.setPrimaryZone(primaryZone);

	Config secondaryZone = new Config();
	secondaryZone.setConfigId(101L);
	empShift.setSecondaryZone(secondaryZone);

	empShift = dao.persistEmployeeShift(empShift);

	assertNotNull(empShift.getEmpDailyNtlMrgnId());
    }

    @Test
    public void testUpdateEmployeeShift() throws ParseException {
	ScoreboardEmployeeShift empShift = new ScoreboardEmployeeShift();
	empShift.setEmpBBYId("A1003810");
	empShift.setEmpFullName("Emp Full Name");
	empShift.setCreatedBy("createdBy");
	empShift = dao.persistEmployee(empShift);

	empShift.setBusinessSourceId(BS_SOURCE_ID_SWAS_MOBILE);
	empShift.setStoreId(STORE_0105);
	empShift.setShiftStartTime(new Date());
	empShift.setShiftEndTime(new Date());
	Config primaryZone = new Config();
	primaryZone.setConfigId(100L);
	empShift.setPrimaryZone(primaryZone);

	Config secondaryZone = new Config();
	secondaryZone.setConfigId(101L);
	empShift.setSecondaryZone(secondaryZone);

	empShift = dao.persistEmployeeShift(empShift);

	empShift.setShiftStartTime(sdf.parse("09/27/2012 14:00"));
	empShift.setShiftEndTime(sdf.parse("09/27/2012 15:00"));
	empShift.getPrimaryZone().setConfigId(104L);
	empShift.getSecondaryZone().setConfigId(108L);
	empShift = dao.updateEmployeeShift(empShift);

	ScoreboardEmployeeShift actual = dao.getEmployeeShift("A1003810", STORE_0105, SWAS_MOBILE);

	assertEquals(empShift.getEmpDailyNtlMrgnId().intValue(), actual.getEmpDailyNtlMrgnId().intValue());
	assertEquals(empShift.getEmpNtlMrgnId().intValue(), actual.getEmpNtlMrgnId().intValue());
    }

    @Test
    public void testDeleteEmployeeShift() {
	ScoreboardEmployeeShift empShift = new ScoreboardEmployeeShift();
	empShift.setEmpDailyNtlMrgnId(1L);

	dao.deleteEmployeeShift(empShift);

	ScoreboardEmployeeShift emp = dao.getEmployeeShift("A311048", STORE_0105, SWAS_MOBILE);
	assertEquals(0, emp.getEmpDailyNtlMrgnId().intValue());
    }

    @Test
    public void testGetDailyEmployeeList() {
	List<ScoreboardEmployeeShift> list = dao.getDailyEmployeeList(STORE_0105, SWAS_MOBILE);
	assertEquals(7, list.size());

	list = dao.getDailyEmployeeList(STORE_0105, SWAS_COMPUTING);
	assertEquals(10, list.size());

	list = dao.getDailyEmployeeList(STORE_0105, SAS_MOBILE);
	assertEquals(6, list.size());

	list = dao.getDailyEmployeeList(STORE_0105, SAS_COMPUTING);
	assertEquals(0, list.size());
    }

    @Test
    public void testGetDailyGoalList() {
	List<ScoreboardDailyGoal> list = dao.getDailyGoalList(STORE_0105, SWAS_MOBILE);
	assertEquals(7, list.size());
	Long actual = 0L;
	for(ScoreboardDailyGoal goal: list){
	    actual += goal.getGoalValue();
	}
	assertEquals(300, actual.intValue());

	list = dao.getDailyGoalList(STORE_0105, SWAS_COMPUTING);
	assertEquals(2, list.size());

	list = dao.getDailyGoalList(STORE_0105, SAS_MOBILE);
	assertEquals(5, list.size());
    }

    @Test
    public void testPersistGoal() {

	ScoreboardDailyGoal goal = new ScoreboardDailyGoal();
	goal.setGoalType(ScoreboardWidget.Type.PERCENTAGE);
	goal.setGoalValue(111L);
	goal.setGoalTypeId(108L);
	goal.setCreatedBy("createdBy");
	goal = dao.persistGoal(STORE_0698, BS_SOURCE_ID_SWAS_MOBILE, goal);

	assertNotNull(goal.getId());
    }

    @Test
    public void testUpdateGoal() {
	ScoreboardDailyGoal goal = new ScoreboardDailyGoal();
	goal.setGoalType(ScoreboardWidget.Type.PERCENTAGE);
	goal.setGoalValue(111L);
	goal.setGoalTypeId(109L);
	goal.setCreatedBy("createdBy");
	goal = dao.persistGoal(STORE_0698, BS_SOURCE_ID_SWAS_MOBILE, goal);

	assertNotNull(goal.getId());

	goal.setGoalValue(222L);
	dao.updateGoal(goal);

	List<ScoreboardDailyGoal> list = dao.getDailyGoalList(STORE_0698, SWAS_MOBILE);
	for(ScoreboardDailyGoal g: list){
	    if (g.getGoalTypeId().intValue() == goal.getGoalTypeId().intValue()){
		assertEquals(222, g.getGoalValue().intValue());
	    }
	}

    }

    @Test
    public void testInsertTransaction() {
	List<ScoreboardDataItem> items = new ArrayList<ScoreboardDataItem>();
	ScoreboardDataItem item = new ScoreboardDataItem();
	item.setEditQuantity(2);
	item.setEditUnitPrice(new BigDecimal("2.2"));
	item.setNtlMarginValueId(25l);
	item.setCreatedBy("createdBy");
	item.setTransactionDate(new Date());
	items.add(item);

	item = new ScoreboardDataItem();
	item.setEditQuantity(4);
	item.setEditUnitPrice(new BigDecimal("8.8"));
	item.setNtlMarginValueId(11l);
	item.setCreatedBy("createdBy");
	item.setTransactionDate(new Date());
	items.add(item);

	int[] id = dao.insertTransaction(STORE_0105, 188L, SALE, items);
	assertNotSame(0, id[0]);
	assertNotSame(0, id[1]);

	List<ScoreboardDataItem> actual = dao.getCategoryItems(STORE_0105, 188L, SWAS_MOBILE,
		"SCOREBOARD_ITEMS_SWAS_MOBILE");
    }

    @Test
    public void testGetStoreDetails() {
	List<ScoreboardDataItem> items = dao.getStoreDetails(STORE_0105, ScoreboardStoreType.SWAS,
		ScoreboardOperationType.MOBILE);
	assertEquals(24, items.size());
	int sales_qty = 0;
	double sales_amt = 0;
	int returns_qty = 0;
	double returns_amt = 0;
	for(ScoreboardDataItem item: items){
	    if (item.getNtlMarginValueId() != null && item.getNtlMarginValueId().intValue() != 0){
		sales_qty += item.getSalesQuantity();
		sales_amt += item.getSalesTotal().doubleValue();
		returns_qty += item.getReturnQuantity().intValue();
		returns_amt += item.getReturnTotal().doubleValue();
	    }
	}
	assertEquals(156, sales_qty);
	assertEquals(13198, sales_amt, 0.1);
	assertEquals(-43, returns_qty);
	assertEquals(-3719, returns_amt, 0.1);
    }

    @Test
    public void testGetEmployeeDetails() {
	List<ScoreboardDataItem> items = dao.getEmployeeDetails(STORE_0105, ScoreboardStoreType.SWAS,
		ScoreboardOperationType.MOBILE, 1L);
	assertEquals(24, items.size());
	int sales_qty = 0;
	double sales_amt = 0;
	for(ScoreboardDataItem item: items){
	    if (item.getNtlMarginValueId() != null && item.getNtlMarginValueId().intValue() != 0){
		sales_qty += item.getSalesQuantity();
		sales_amt += item.getSalesTotal().doubleValue();
	    }
	}
	assertEquals(113, sales_qty);
	assertEquals(9644, sales_amt, 0.1);

    }

    private static class RecursiveToStringStyle extends ToStringStyle {

	private static final int INFINITE_DEPTH = -1;

	/**
	 * Setting {@link #maxDepth} to 0 will have the same effect as using
	 * original {@link #ToStringStyle}: it will print all 1st level values
	 * without traversing into them. Setting to 1 will traverse up to 2nd
	 * level and so on.
	 */
	private int maxDepth;

	private int depth;

	public RecursiveToStringStyle() {
	    this(INFINITE_DEPTH);
	}

	public RecursiveToStringStyle(int maxDepth) {
	    setUseShortClassName(true);
	    setUseIdentityHashCode(false);

	    this.maxDepth = maxDepth;
	}

	@Override
	protected void appendDetail(StringBuffer buffer, String fieldName, Object value) {
	    if (value.getClass().getName().startsWith("java.lang.")
		    || (maxDepth != INFINITE_DEPTH && depth >= maxDepth)){
		buffer.append(value);
	    }else{
		depth++;
		buffer.append(ReflectionToStringBuilder.toString(value, this));
		depth--;
	    }
	}

	// another helpful method
	@Override
	protected void appendDetail(StringBuffer buffer, String fieldName, Collection coll) {
	    depth++;
	    buffer.append(ReflectionToStringBuilder.toString(coll.toArray(), this, true, true));
	    depth--;
	}

    }

    @Test
    @Ignore
    public void testGetScoreboardSwasMobileWidget() {

	for(int i = 0; i < WIDGET_COUNT - 1; i++){
	    ScoreboardWidget scoreboardWidget = new ScoreboardWidget();
	    scoreboardWidget = dao.getScoreboardWidget(STORE_0105, SWAS_MOBILE, new Date(112, 9, 8), i);
	    assertNotNull(scoreboardWidget.getCurrentVal());
	}
    }

    @Test
    public void testGetUpgradeChecksSwasMobileWidget() {
	ScoreboardWidget scoreboardWidget = new ScoreboardWidget();
	scoreboardWidget = dao.getScoreboardWidget(STORE_0105, SWAS_MOBILE, new Date(112, 9, 8), 7);
	assertNotNull(scoreboardWidget.getTargetVal());
    }

    @Test
    public void testGetScoreboardSasMobileWidget() {

	for(int i = 0; i < WIDGET_COUNT - 1; i++){
	    ScoreboardWidget scoreboardWidget = new ScoreboardWidget();
	    scoreboardWidget = dao.getScoreboardWidget(STORE_1945, SAS_MOBILE, new Date(112, 9, 8), i);
	    assertNotNull(scoreboardWidget.getCurrentVal());
	}
    }

    @Test
    public void testGetUpgradeChecksSasMobileWidget() {
	ScoreboardWidget scoreboardWidget = new ScoreboardWidget();
	scoreboardWidget = dao.getScoreboardWidget(STORE_1945, SAS_MOBILE, new Date(112, 9, 8), 7);
	assertNotNull(scoreboardWidget.getTargetVal());
    }

    @Test
    @Ignore
    public void testGetScoreboardComputingWidget() {

	for(int i = 0; i < WIDGET_COUNT - 1; i++){
	    ScoreboardWidget scoreboardWidget = new ScoreboardWidget();
	    scoreboardWidget = dao.getScoreboardWidget(STORE_0105, SWAS_COMPUTING, new Date(112, 9, 8), i);
	    assertNotNull(scoreboardWidget.getCurrentVal());
	}
    }

    @Test
    public void testGetUpgradeChecksComputingWidget() {
	ScoreboardWidget scoreboardWidget = new ScoreboardWidget();
	scoreboardWidget = dao.getScoreboardWidget(STORE_0105, SWAS_COMPUTING, new Date(112, 9, 8), 7);
	assertNotNull(scoreboardWidget.getTargetVal());
    }

    @Test
    public void testDeleteTransaction() {

    }

}
