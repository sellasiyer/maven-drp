package com.bestbuy.bbym.ise.drp.service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.bestbuy.bbym.ise.domain.Store;
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
import com.bestbuy.bbym.ise.exception.IseBusinessException;
import com.bestbuy.bbym.ise.exception.IseExceptionCodeEnum;
import com.bestbuy.bbym.ise.exception.ServiceException;
import com.bestbuy.bbym.ise.util.Util;

@Service("scoreboardService2")
public class DummyScoreboardService implements ScoreboardService {

    @Override
    public ScoreboardData getScoreBoardData(String storeId, ScoreboardOperationType operationType,
	    ScoreboardStoreType storeType, Date localDate, String userId) throws ServiceException {
	DummyData.sleep(2);
	DummyData.throwServiceException(100);
	if (storeId == null || operationType == null || storeType == null || localDate == null){
	    throw new ServiceException(IseExceptionCodeEnum.UnexpectedNull, "Unexpected null");
	}
	ScoreboardData sd = new ScoreboardData();
	ScoreboardNotionalMargin storeMargin = new ScoreboardNotionalMargin();

	int target = DummyData.getRandomIndex(5001) + 1000;
	int progress = target - DummyData.getRandomIndex(target + 1);
	if (DummyData.getRandomIndex(10) > 7){
	    progress = target + DummyData.getRandomIndex((int) (target * 0.5));
	}
	storeMargin.setCurrentValue(progress);
	storeMargin.setTargetValue(target);
	int productivity = DummyData.getRandomIndex(501) + 50;
	storeMargin.setProductivity(new Integer(productivity));

	String amPm = "PM";
	int hour = DummyData.getRandomIndex(12) + 1;
	if (hour >= 8 && hour <= 11){
	    amPm = "AM";
	}
	int minute = 0;
	DecimalFormat df = new DecimalFormat("00");
	storeMargin.setNextHour(df.format(hour) + ":" + df.format(minute) + amPm);
	storeMargin.setNextHourPercentage(DummyData.getRandomIndex(81) + 20);

	sd.setScoreboardNotionalMargin(storeMargin);

	List<ScoreboardWidget> sbwAttributes;
	ScoreboardWidget sbw, sbwa;
	List<ScoreboardWidget> widgetList = new ArrayList<ScoreboardWidget>();

	if (operationType == ScoreboardOperationType.MOBILE){
	    sbw = new ScoreboardWidget();
	    sbw.setWidgetName("Total<br/>Connections");
	    sbw.setWidgetType(ScoreboardWidget.Type.NUMERIC);
	    sbw.setTargetVal(30);
	    sbw.setCurrentVal(DummyData.getRandomIndex(41));
	    sbwAttributes = new ArrayList<ScoreboardWidget>();
	    sbwa = new ScoreboardWidget();
	    sbwa.setWidgetName("Post Paid (no iPhone)");
	    sbwa.setWidgetType(ScoreboardWidget.Type.NUMERIC);
	    sbwa.setTargetVal(10);
	    sbwa.setCurrentVal(DummyData.getRandomIndex(10));
	    sbwAttributes.add(sbwa);
	    sbwa = new ScoreboardWidget();
	    sbwa.setWidgetName("iPhone");
	    sbwa.setWidgetType(ScoreboardWidget.Type.NUMERIC);
	    sbwa.setTargetVal(10);
	    sbwa.setCurrentVal(DummyData.getRandomIndex(10));
	    sbwAttributes.add(sbwa);
	    sbwa = new ScoreboardWidget();
	    sbwa.setWidgetName("No contract");
	    sbwa.setWidgetType(ScoreboardWidget.Type.NUMERIC);
	    sbwa.setTargetVal(10);
	    sbwa.setCurrentVal(DummyData.getRandomIndex(10));
	    sbwAttributes.add(sbwa);
	    sbwa = new ScoreboardWidget();
	    sbwa.setWidgetName("Broadband");
	    sbwa.setWidgetType(ScoreboardWidget.Type.NUMERIC);
	    sbwa.setTargetVal(10);
	    sbwa.setCurrentVal(DummyData.getRandomIndex(10));
	    sbwAttributes.add(sbwa);
	    sbw.setWidgetAttributes(sbwAttributes);
	    widgetList.add(sbw);
	    //
	    sbw = new ScoreboardWidget();
	    sbw.setWidgetName("Post Paid<br/>GSP");
	    sbw.setWidgetType(ScoreboardWidget.Type.PERCENTAGE);
	    sbw.setTargetVal(20);
	    sbw.setCurrentVal(DummyData.getRandomIndex(31));
	    widgetList.add(sbw);
	    //
	    sbw = new ScoreboardWidget();
	    sbw.setWidgetName("Post Paid<br/>Accessories");
	    sbw.setWidgetType(ScoreboardWidget.Type.PERCENTAGE);
	    sbw.setTargetVal(50);
	    sbw.setCurrentVal(DummyData.getRandomIndex(61));
	    widgetList.add(sbw);
	    //
	    sbw = new ScoreboardWidget();
	    sbw.setWidgetName("No Contract<br/>Activations");
	    sbw.setWidgetType(ScoreboardWidget.Type.PERCENTAGE);
	    sbw.setTargetVal(50);
	    sbw.setCurrentVal(DummyData.getRandomIndex(61));
	    widgetList.add(sbw);
	    //
	    sbw = new ScoreboardWidget();
	    sbw.setWidgetName("MP3 Notional<br/>Margin");
	    sbw.setWidgetType(ScoreboardWidget.Type.DOLLARVALUE);
	    sbw.setTargetVal(1500);
	    sbw.setCurrentVal(DummyData.getRandomIndex(1701));
	    sbwAttributes = new ArrayList<ScoreboardWidget>();
	    sbwa = new ScoreboardWidget();
	    sbwa.setWidgetName("MP3 Hardware Units");
	    sbwa.setWidgetType(ScoreboardWidget.Type.NUMERIC);
	    sbwa.setTargetVal(10);
	    sbwa.setCurrentVal(DummyData.getRandomIndex(10));
	    sbwAttributes.add(sbwa);
	    sbwa = new ScoreboardWidget();
	    sbwa.setWidgetName("MP3 Accessory Margin");
	    sbwa.setWidgetType(ScoreboardWidget.Type.DOLLARVALUE);
	    sbwa.setTargetVal(400);
	    sbwa.setCurrentVal(DummyData.getRandomIndex(400));
	    sbwAttributes.add(sbwa);
	    sbw.setWidgetAttributes(sbwAttributes);
	    widgetList.add(sbw);
	    //
	    sbw = new ScoreboardWidget();
	    sbw.setWidgetName("MP3<br/>GSP");
	    sbw.setWidgetType(ScoreboardWidget.Type.PERCENTAGE);
	    sbw.setTargetVal(20);
	    sbw.setCurrentVal(DummyData.getRandomIndex(31));
	    widgetList.add(sbw);
	    //
	    sbw = new ScoreboardWidget();
	    sbw.setWidgetName("MP3<br/>Accessories");
	    sbw.setWidgetType(ScoreboardWidget.Type.PERCENTAGE);
	    sbw.setTargetVal(50);
	    sbw.setCurrentVal(DummyData.getRandomIndex(71));
	    widgetList.add(sbw);
	    //
	    sbw = new ScoreboardWidget();
	    sbw.setWidgetName("Upgrade<br/>Checks");
	    sbw.setWidgetType(ScoreboardWidget.Type.NUMERIC);
	    sbw.setTargetVal(20);
	    sbw.setCurrentVal(DummyData.getRandomIndex(41));
	    widgetList.add(sbw);

	}else if (operationType == ScoreboardOperationType.COMPUTING){
	    sbw = new ScoreboardWidget();
	    sbw.setWidgetName("Total<br/>Laptops");
	    sbw.setWidgetType(ScoreboardWidget.Type.NUMERIC);
	    sbw.setTargetVal(30);
	    sbw.setCurrentVal(DummyData.getRandomIndex(41));
	    sbwAttributes = new ArrayList<ScoreboardWidget>();
	    sbwa = new ScoreboardWidget();
	    sbwa.setWidgetName("Laptops < $500");
	    sbwa.setWidgetType(ScoreboardWidget.Type.NUMERIC);
	    sbwa.setTargetVal(10);
	    sbwa.setCurrentVal(DummyData.getRandomIndex(10));
	    sbwAttributes.add(sbwa);
	    sbwa = new ScoreboardWidget();
	    sbwa.setWidgetName("Laptops $500 - $900");
	    sbwa.setWidgetType(ScoreboardWidget.Type.NUMERIC);
	    sbwa.setTargetVal(10);
	    sbwa.setCurrentVal(DummyData.getRandomIndex(10));
	    sbwAttributes.add(sbwa);
	    sbwa = new ScoreboardWidget();
	    sbwa.setWidgetName("Laptops > $90");
	    sbwa.setWidgetType(ScoreboardWidget.Type.NUMERIC);
	    sbwa.setTargetVal(10);
	    sbwa.setCurrentVal(DummyData.getRandomIndex(10));
	    sbwAttributes.add(sbwa);
	    sbw.setWidgetAttributes(sbwAttributes);
	    widgetList.add(sbw);
	    //
	    sbw = new ScoreboardWidget();
	    sbw.setWidgetName("Desktops &amp;<br/>All-In-Ones");
	    sbw.setWidgetType(ScoreboardWidget.Type.NUMERIC);
	    sbw.setTargetVal(30);
	    sbw.setCurrentVal(DummyData.getRandomIndex(41));
	    sbwAttributes = new ArrayList<ScoreboardWidget>();
	    sbwa = new ScoreboardWidget();
	    sbwa.setWidgetName("Desktops");
	    sbwa.setWidgetType(ScoreboardWidget.Type.NUMERIC);
	    sbwa.setTargetVal(10);
	    sbwa.setCurrentVal(DummyData.getRandomIndex(10));
	    sbwAttributes.add(sbwa);
	    sbwa = new ScoreboardWidget();
	    sbwa.setWidgetName("All-In-Ones");
	    sbwa.setWidgetType(ScoreboardWidget.Type.NUMERIC);
	    sbwa.setTargetVal(10);
	    sbwa.setCurrentVal(DummyData.getRandomIndex(10));
	    sbwAttributes.add(sbwa);
	    sbw.setWidgetAttributes(sbwAttributes);
	    widgetList.add(sbw);
	    //
	    sbw = new ScoreboardWidget();
	    sbw.setWidgetName("Total<br/>Tablets");
	    sbw.setWidgetType(ScoreboardWidget.Type.NUMERIC);
	    sbw.setTargetVal(20);
	    sbw.setCurrentVal(DummyData.getRandomIndex(31));
	    widgetList.add(sbw);
	    //
	    sbw = new ScoreboardWidget();
	    sbw.setWidgetName("Total<br/>eReaders");
	    sbw.setWidgetType(ScoreboardWidget.Type.NUMERIC);
	    sbw.setTargetVal(10);
	    sbw.setCurrentVal(DummyData.getRandomIndex(21));
	    widgetList.add(sbw);
	    //
	    sbw = new ScoreboardWidget();
	    sbw.setWidgetName("<br/>Geek Squad");
	    sbw.setWidgetType(ScoreboardWidget.Type.DOLLARVALUE);
	    sbw.setTargetVal(2000);
	    sbw.setCurrentVal(DummyData.getRandomIndex(2501));
	    sbwAttributes = new ArrayList<ScoreboardWidget>();
	    sbwa = new ScoreboardWidget();
	    sbwa.setWidgetName("GSP");
	    sbwa.setWidgetType(ScoreboardWidget.Type.DOLLARVALUE);
	    sbwa.setTargetVal(300);
	    sbwa.setCurrentVal(DummyData.getRandomIndex(300));
	    sbwAttributes.add(sbwa);
	    sbwa = new ScoreboardWidget();
	    sbwa.setWidgetName("Geek Squad Services");
	    sbwa.setWidgetType(ScoreboardWidget.Type.DOLLARVALUE);
	    sbwa.setTargetVal(300);
	    sbwa.setCurrentVal(DummyData.getRandomIndex(300));
	    sbwAttributes.add(sbwa);
	    sbw.setWidgetAttributes(sbwAttributes);
	    widgetList.add(sbw);
	    //
	    sbw = new ScoreboardWidget();
	    sbw.setWidgetName("<br/>Accessories");
	    sbw.setWidgetType(ScoreboardWidget.Type.DOLLARVALUE);
	    sbw.setTargetVal(1000);
	    sbw.setCurrentVal(DummyData.getRandomIndex(1201));
	    widgetList.add(sbw);
	    //
	    sbw = new ScoreboardWidget();
	    sbw.setWidgetName("<br/>Content");
	    sbw.setWidgetType(ScoreboardWidget.Type.DOLLARVALUE);
	    sbw.setTargetVal(500);
	    sbw.setCurrentVal(DummyData.getRandomIndex(601));
	    widgetList.add(sbw);
	    //
	    sbw = new ScoreboardWidget();
	    sbw.setWidgetName("<br/>Connections");
	    sbw.setWidgetType(ScoreboardWidget.Type.NUMERIC);
	    sbw.setTargetVal(5);
	    sbw.setCurrentVal(DummyData.getRandomIndex(7));
	    widgetList.add(sbw);
	}
	sd.setWidgetList(widgetList);

	ScoreboardEmployeeShift emplShift;
	ScoreboardEmployeeNotionalMargin sbe;
	List<ScoreboardEmployeeNotionalMargin> employeeList = new ArrayList<ScoreboardEmployeeNotionalMargin>();

	int employeeCount = DummyData.getRandomIndex(8);
	for(int i = 0; i < employeeCount; i++){
	    sbe = new ScoreboardEmployeeNotionalMargin();
	    sbe.setTargetValue(500);
	    sbe.setCurrentValue(DummyData.getRandomIndex(601));
	    sbe.setProductivity(DummyData.getRandomIndex(501) + 50);
	    emplShift = new ScoreboardEmployeeShift();
	    emplShift.setEmpFullName(DummyData.getFirstName() + " " + DummyData.getLastName());
	    emplShift.getEmpFullName().trim();
	    emplShift.setEmpBBYId("a" + Util.pad(i + 100, 6, "0"));
	    sbe.setEmployeeShift(emplShift);
	    employeeList.add(sbe);
	}
	sd.setEmployeeNotionalMarginList(employeeList);

	return sd;
    }

    @Override
    public Store getStoreInfo(String storeId) throws ServiceException, IseBusinessException {
	DummyData.sleep(1);
	DummyData.throwServiceException(20);
	if (storeId == null){
	    throw new ServiceException(IseExceptionCodeEnum.UnexpectedNull, "Unexpected null");
	}
	Store store = new Store();
	if (DummyData.getRandomIndex(10) < 7){
	    store.setId("999");
	    store.setStoreType(ScoreboardStoreType.SWAS);
	    store.setStoreName("Fergus Falls");
	}else{
	    store.setId("666");
	    store.setStoreType(ScoreboardStoreType.SAS);
	    store.setStoreName("Mall America");
	}
	return store;
    }

    @Override
    public List<ScoreboardCategory> getScoreboardItems(String storeId, Long ntlMrgnDlyEmpId,
	    ScoreboardStoreType storeType, ScoreboardOperationType operationType,
	    ScoreboardTransactionType transactionType) throws ServiceException {
	DummyData.sleep(1);
	DummyData.throwServiceException(100);
	if (storeId == null){
	    throw new ServiceException(IseExceptionCodeEnum.UnexpectedNull, "Unexpected null storeId");
	}
	if (storeType == null){
	    throw new ServiceException(IseExceptionCodeEnum.UnexpectedNull, "Unexpected null storeType");
	}
	if (operationType == null){
	    throw new ServiceException(IseExceptionCodeEnum.UnexpectedNull, "Unexpected null operationType");
	}
	if (transactionType == null){
	    throw new ServiceException(IseExceptionCodeEnum.UnexpectedNull, "Unexpected null transactionType");
	}

	ScoreboardDataItem catItem;
	ScoreboardCategory cat;
	List<ScoreboardCategory> catList = new ArrayList<ScoreboardCategory>();

	if (operationType == ScoreboardOperationType.MOBILE){
	    cat = new ScoreboardCategory();
	    catList.add(cat);
	    cat.setName("Post-Paid Units");
	    cat.setDataItems(new ArrayList<ScoreboardDataItem>());
	    //
	    catItem = new ScoreboardDataItem();
	    catItem.setName("iPhone");
	    cat.getDataItems().add(catItem);
	    //
	    catItem = new ScoreboardDataItem();
	    catItem.setName("New");
	    cat.getDataItems().add(catItem);
	    //
	    catItem = new ScoreboardDataItem();
	    catItem.setName("Upgrade");
	    cat.getDataItems().add(catItem);
	    //
	    catItem = new ScoreboardDataItem();
	    catItem.setName("Handset");
	    cat.getDataItems().add(catItem);
	    //
	    cat = new ScoreboardCategory();
	    catList.add(cat);
	    cat.setName("No-Contract Units");
	    cat.setDataItems(new ArrayList<ScoreboardDataItem>());
	    //
	    catItem = new ScoreboardDataItem();
	    catItem.setName("No Contract < $30");
	    cat.getDataItems().add(catItem);
	    //
	    catItem = new ScoreboardDataItem();
	    catItem.setName("No Contract $30-$100");
	    cat.getDataItems().add(catItem);
	    //
	    catItem = new ScoreboardDataItem();
	    catItem.setName("No Contract > $100");
	    cat.getDataItems().add(catItem);
	    //
	    catItem = new ScoreboardDataItem();
	    catItem.setName("NC BEAST Activations");
	    cat.getDataItems().add(catItem);
	    //
	    cat = new ScoreboardCategory();
	    catList.add(cat);
	    cat.setName("Accessories");
	    cat.setDataItems(new ArrayList<ScoreboardDataItem>());
	    //
	    catItem = new ScoreboardDataItem();
	    catItem.setName("1st Attached (Post-Paid)");
	    cat.getDataItems().add(catItem);
	    //
	    catItem = new ScoreboardDataItem();
	    catItem.setName("Additional Accessories");
	    cat.getDataItems().add(catItem);
	    //
	    cat = new ScoreboardCategory();
	    catList.add(cat);
	    cat.setName("Broadband");
	    cat.setDataItems(new ArrayList<ScoreboardDataItem>());
	    //
	    catItem = new ScoreboardDataItem();
	    catItem.setName("PP Broadband Card");
	    cat.getDataItems().add(catItem);
	    //
	    catItem = new ScoreboardDataItem();
	    catItem.setName("NC Broadband");
	    cat.getDataItems().add(catItem);
	    //
	    cat = new ScoreboardCategory();
	    catList.add(cat);
	    cat.setName("GSBTP");
	    cat.setDataItems(new ArrayList<ScoreboardDataItem>());
	    //
	    catItem = new ScoreboardDataItem();
	    catItem.setName("GSBTP < $350");
	    cat.getDataItems().add(catItem);
	    //
	    catItem = new ScoreboardDataItem();
	    catItem.setName("GSBTP > $350");
	    cat.getDataItems().add(catItem);
	    //
	    catItem = new ScoreboardDataItem();
	    catItem.setName("iPhone GSBTP");
	    cat.getDataItems().add(catItem);
	    //
	    cat = new ScoreboardCategory();
	    catList.add(cat);
	    cat.setName("MP3");
	    cat.setDataItems(new ArrayList<ScoreboardDataItem>());
	    //
	    catItem = new ScoreboardDataItem();
	    catItem.setName("Hardware");
	    cat.getDataItems().add(catItem);
	    //
	    catItem = new ScoreboardDataItem();
	    catItem.setName("GSBTP");
	    cat.getDataItems().add(catItem);
	    //
	    catItem = new ScoreboardDataItem();
	    catItem.setName("All Accessories");
	    cat.getDataItems().add(catItem);

	}else if (operationType == ScoreboardOperationType.COMPUTING){
	    cat = new ScoreboardCategory();
	    catList.add(cat);
	    cat.setName("Laptops");
	    cat.setDataItems(new ArrayList<ScoreboardDataItem>());
	    //
	    catItem = new ScoreboardDataItem();
	    catItem.setName("Laptops < $500");
	    cat.getDataItems().add(catItem);
	    //
	    catItem = new ScoreboardDataItem();
	    catItem.setName("Laptops $500-$900");
	    cat.getDataItems().add(catItem);
	    //
	    catItem = new ScoreboardDataItem();
	    catItem.setName("Laptops > $900");
	    cat.getDataItems().add(catItem);
	    //
	    cat = new ScoreboardCategory();
	    catList.add(cat);
	    cat.setName("Desktops & All-In-Ones");
	    cat.setDataItems(new ArrayList<ScoreboardDataItem>());
	    //
	    catItem = new ScoreboardDataItem();
	    catItem.setName("Desktops");
	    cat.getDataItems().add(catItem);
	    //
	    catItem = new ScoreboardDataItem();
	    catItem.setName("All-In-Ones");
	    cat.getDataItems().add(catItem);
	    //
	    cat = new ScoreboardCategory();
	    catList.add(cat);
	    cat.setName("Tablets");
	    cat.setDataItems(new ArrayList<ScoreboardDataItem>());
	    //
	    catItem = new ScoreboardDataItem();
	    catItem.setName("Tablets");
	    cat.getDataItems().add(catItem);
	    //
	    cat = new ScoreboardCategory();
	    catList.add(cat);
	    cat.setName("Connections");
	    cat.setDataItems(new ArrayList<ScoreboardDataItem>());
	    //
	    catItem = new ScoreboardDataItem();
	    catItem.setName("CLEAR Month to Month");
	    cat.getDataItems().add(catItem);
	    //
	    catItem = new ScoreboardDataItem();
	    catItem.setName("Tablet Activation");
	    cat.getDataItems().add(catItem);
	    //
	    catItem = new ScoreboardDataItem();
	    catItem.setName("Broad Band");
	    cat.getDataItems().add(catItem);
	    //
	    catItem = new ScoreboardDataItem();
	    catItem.setName("Broad Band (No-Contract)");
	    cat.getDataItems().add(catItem);
	    //
	    cat = new ScoreboardCategory();
	    catList.add(cat);
	    cat.setName("Accessories");
	    cat.setDataItems(new ArrayList<ScoreboardDataItem>());
	    //
	    catItem = new ScoreboardDataItem();
	    catItem.setName("All Computing & Tablet Accessories");
	    cat.getDataItems().add(catItem);
	    //
	    cat = new ScoreboardCategory();
	    catList.add(cat);
	    cat.setName("Content");
	    cat.setDataItems(new ArrayList<ScoreboardDataItem>());
	    //
	    catItem = new ScoreboardDataItem();
	    catItem.setName("Productivity Software");
	    cat.getDataItems().add(catItem);
	    //
	    catItem = new ScoreboardDataItem();
	    catItem.setName("Subscription Software");
	    cat.getDataItems().add(catItem);
	    //
	    cat = new ScoreboardCategory();
	    catList.add(cat);
	    cat.setName("Geek Squad");
	    cat.setDataItems(new ArrayList<ScoreboardDataItem>());
	    //
	    catItem = new ScoreboardDataItem();
	    catItem.setName("Geek Squad Protection");
	    cat.getDataItems().add(catItem);
	    //
	    catItem = new ScoreboardDataItem();
	    catItem.setName("Geek Squad Services");
	    cat.getDataItems().add(catItem);
	    //
	    catItem = new ScoreboardDataItem();
	    catItem.setName("Geek Squad Tech Support");
	    cat.getDataItems().add(catItem);
	    //
	    cat = new ScoreboardCategory();
	    catList.add(cat);
	    cat.setName("eReaders");
	    cat.setDataItems(new ArrayList<ScoreboardDataItem>());
	    //
	    catItem = new ScoreboardDataItem();
	    catItem.setName("eReaders");
	    cat.getDataItems().add(catItem);
	}

	if (transactionType == ScoreboardTransactionType.EMP_SALES_DETAILS
		|| transactionType == ScoreboardTransactionType.STORE_DETAILS){
	    for(ScoreboardCategory sc: catList){
		for(ScoreboardDataItem sdi: sc.getDataItems()){
		    sdi.setReturnQuantity(new Integer(DummyData.getRandomIndex(12) + 1));
		    sdi.setReturnTotal(DummyData.getScoreboardTotal());
		    sdi.setSalesQuantity(new Integer(DummyData.getRandomIndex(12) + 1));
		    sdi.setSalesTotal(DummyData.getScoreboardTotal());
		}
	    }
	}

	return catList;
    }

    @Override
    public void saveScoreboardItems(String storeId, ScoreboardEmployeeShift emp,
	    ScoreboardTransactionType transactionType, List<ScoreboardCategory> items, DrpUser user, int timeZoneOffset)
	    throws ServiceException {
	DummyData.throwServiceException(20);
    }

    @Override
    public List<ScoreboardDailyGoal> getStoreDailyGoals(String storeId, ScoreboardStoreType storeType,
	    ScoreboardOperationType operationType) throws ServiceException {
	return null;
    }

    @Override
    public List<ScoreboardDailyGoal> saveStoreDailyGoals(String storeId, ScoreboardStoreType storeType,
	    ScoreboardOperationType operationType, List<ScoreboardDailyGoal> dailyGoalsList) throws ServiceException {
	return null;
    }

    @Override
    public ScoreboardEmployeeShift addEmployee(String empBbyId, String storeId, ScoreboardStoreType storeType,
	    ScoreboardOperationType operationType, DrpUser user) throws ServiceException {
	return null;
    }

    @Override
    public void removeEmployeeShift(ScoreboardEmployeeShift employee) throws ServiceException {
    }

    @Override
    public List<ScoreboardEmployeeShift> getStoreDailyEmployeeShift(String storeId, ScoreboardStoreType storeType,
	    ScoreboardOperationType operationType) throws ServiceException {
	DummyData.sleep(1);
	DummyData.throwServiceException(200);

	ScoreboardEmployeeShift emplShift;
	List<ScoreboardEmployeeShift> emplShiftList = new ArrayList<ScoreboardEmployeeShift>();

	int emplShiftCount = DummyData.getRandomIndex(8) + 1;
	for(int i = 0; i < emplShiftCount; i++){
	    emplShift = new ScoreboardEmployeeShift();
	    emplShift.setEmpFullName(DummyData.getFirstName() + " " + DummyData.getLastName());
	    emplShift.getEmpFullName().trim();
	    emplShift.setEmpBBYId("a" + Util.pad(i + 100, 6, "0"));
	    emplShiftList.add(emplShift);
	}
	return emplShiftList;
    }

    @Override
    public void saveStoreDailyEmployeeShift(String storeId, ScoreboardStoreType storeType,
	    ScoreboardOperationType operationType, List<ScoreboardEmployeeShift> dailyEmployeeShiftList)
	    throws ServiceException {
    }

    @Override
    public List<ScoreboardWidget> getScoreboardWidgetList(String storeId, String businessSourceId, Date localDate) {
	return null;
    }

    public ScoreboardEmployeeDistribution getScoreboardEmployeeDistribution(String storeId,
	    ScoreboardStoreType storeType, ScoreboardOperationType operationType, Date localDate) {
	return null;
    }

    @Override
    public List<Config> getZoneList(ScoreboardStoreType storeType, ScoreboardOperationType operationType)
	    throws ServiceException {
	return null;
    }

    @Override
    public void makeTransactionStatusDeleted(ScoreboardDataItem dataItem, DrpUser user) throws ServiceException {
	DummyData.sleep(1);
	DummyData.throwServiceException(20);
	if (dataItem == null || user == null){
	    throw new ServiceException(IseExceptionCodeEnum.UnexpectedNull, "Unexpected null");
	}
    }

    @Override
    public List<ScoreboardDataItem> getEmployeeSalesTransactions(String storeId, ScoreboardStoreType storeType,
	    ScoreboardOperationType operationType, ScoreboardEmployeeShift emp) throws ServiceException {
	DummyData.sleep(1);
	DummyData.throwServiceException(60);
	if (storeId == null || operationType == null || storeType == null || emp == null){
	    throw new ServiceException(IseExceptionCodeEnum.UnexpectedNull, "Unexpected null");
	}

	Calendar cal;
	String[] buf = null;
	ScoreboardDataItem dataItem;
	List<ScoreboardDataItem> itemList = new ArrayList<ScoreboardDataItem>();
	int dummycount = DummyData.getRandomIndex(20);
	for(int i = 0; i < dummycount; i++){
	    cal = Calendar.getInstance();
	    cal.add(Calendar.MINUTE, DummyData.getRandomIndex(100) - 50);
	    cal.add(Calendar.HOUR, DummyData.getRandomIndex(6) - 3);

	    if (operationType == ScoreboardOperationType.COMPUTING){
		buf = DummyData.getComputingScoreboardItem();
	    }else{
		buf = DummyData.getMobileScoreboardItem();
	    }
	    if (buf == null){
		continue;
	    }
	    dataItem = new ScoreboardDataItem();
	    dataItem.setTransactionDate(cal.getTime());
	    dataItem.setGroupName(buf[0]);
	    dataItem.setName(buf[1]);
	    dataItem.setEditQuantity(new Integer(DummyData.getRandomIndex(3) + 1));
	    if (DummyData.getRandomIndex(10) < 1){
		dataItem.setEditUnitPrice(DummyData.getScoreboardTotal());
	    }
	    if (DummyData.getRandomIndex(20) < 5){
		dataItem.setDeleted(true);
	    }
	    itemList.add(dataItem);
	}
	return itemList;
    }

    @Override
    public List<ScoreboardDataItem> getStoreReturnsTransactions(String storeId, ScoreboardStoreType storeType,
	    ScoreboardOperationType operationType) throws ServiceException {
	return null;
    }
}
