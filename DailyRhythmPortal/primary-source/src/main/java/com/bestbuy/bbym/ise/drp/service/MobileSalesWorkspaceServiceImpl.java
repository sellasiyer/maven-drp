package com.bestbuy.bbym.ise.drp.service;

import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.bestbuy.bbym.ise.domain.User;
import com.bestbuy.bbym.ise.drp.dao.EssentialsDao;
import com.bestbuy.bbym.ise.drp.dao.RecSheetGSPDataDao;
import com.bestbuy.bbym.ise.drp.dao.RecommendationsDao;
import com.bestbuy.bbym.ise.drp.domain.DrpUser;
import com.bestbuy.bbym.ise.drp.domain.RecSheetCountByDay;
import com.bestbuy.bbym.ise.drp.domain.RecSheetReportList;
import com.bestbuy.bbym.ise.drp.domain.RecSheetReportingSearchResults;
import com.bestbuy.bbym.ise.drp.domain.Recommendation;
import com.bestbuy.bbym.ise.drp.domain.RecommendationTypeEnum;
import com.bestbuy.bbym.ise.exception.DataAccessException;
import com.bestbuy.bbym.ise.exception.IseExceptionCodeEnum;
import com.bestbuy.bbym.ise.exception.ServiceException;
import com.bestbuy.bbym.ise.util.PDFUtils;
import com.bestbuy.bbym.ise.util.Util;

@Service("mobileSalesWorkspaceService")
public class MobileSalesWorkspaceServiceImpl implements MobileSalesWorkspaceService {

    @Resource
    private RecommendationsDao recommendationsDao;

    @Resource
    private EssentialsDao essentailsDao;

    @Resource
    private RecSheetGSPDataDao recSheetGSPDataDao;

    private static Logger logger = LoggerFactory.getLogger(MobileSalesWorkspaceServiceImpl.class);

    @Override
    public List<Recommendation> findRecommendations(String customerLastName, String storeId) throws ServiceException {

	try{
	    return this.recommendationsDao.getRecommendationList(storeId, customerLastName);
	}catch(DataAccessException e){
	    logger.error("Failed to retrieve recommendations list for customer last name: " + customerLastName
		    + " & storeId: " + storeId, e);
	    throw new ServiceException(IseExceptionCodeEnum.DataAccess);
	}

    }

    @Override
    public List<Recommendation> findRecommendationsByMobile(String mobileNumber, String storeId)
	    throws ServiceException {
	try{
	    return this.recommendationsDao.getRecommendationListByMobile(storeId, mobileNumber);
	}catch(DataAccessException e){
	    logger.error("Failed to retrieve recommendations list for customer mobile #: " + mobileNumber
		    + " & storeId: " + storeId, e);
	    throw new ServiceException(IseExceptionCodeEnum.DataAccess);
	}
    }

    @Override
    public List<Recommendation> findRecommendationsForCAndT(String customerLastName, String storeId)
	    throws ServiceException {
	try{
	    return this.recommendationsDao.getRecommendationListForCAndT(storeId, customerLastName);
	}catch(DataAccessException e){
	    logger.error("Failed to retrieve recommendations list for customer last name: " + customerLastName
		    + " & storeId: " + storeId, e);
	    throw new ServiceException(IseExceptionCodeEnum.DataAccess);
	}

    }

    @Override
    public List<Recommendation> findRecommendationsForCAndTByPhone(String phoneNumber, String storeId)
	    throws ServiceException {
	try{
	    return this.recommendationsDao.getRecommendationListForCAndTByPhone(storeId, phoneNumber);
	}catch(DataAccessException e){
	    logger.error("Failed to retrieve recommendations list for customer mobile #: " + phoneNumber
		    + " & storeId: " + storeId, e);
	    throw new ServiceException(IseExceptionCodeEnum.DataAccess);
	}
    }

    @Override
    public List<RecSheetReportingSearchResults> findRecommendationReportsByAId(String aId, Date startDate, Date endDate)
	    throws ServiceException {
	try{
	    if (StringUtils.isEmpty(aId) || startDate == null || endDate == null){
		return Collections.emptyList();
	    }
	    List<RecSheetReportingSearchResults> verifiedResults = new ArrayList<RecSheetReportingSearchResults>();
	    RecSheetReportingSearchResults result = new RecSheetReportingSearchResults();
	    List<Recommendation> recs = recommendationsDao.getRecommendationReportsListByAId(aId, startDate, endDate);
	    if (recs != null && recs.size() > 0){
		result.setRecs(recs);
		result.setAid(aId);
		result.setFirstName(recs.get(0).getEmpCrtFrstNm());
		result.setLastName(recs.get(0).getEmpCrtLastNm());
		verifiedResults.add(result);
		logger.debug("\nfindRecommendationReportsByAId(without storeID): \nverifiedResults: \n"
			+ verifiedResults.toString());
	    }else{
		logger.debug("\nfindRecommendationReportsByAId(without storeID): NO RECOMMENDATION FOUND FOR ID " + aId
			+ "\n");
	    }
	    return verifiedResults;
	}catch(DataAccessException e){
	    logger.error("Failed to retrieve recommendations list for employee by A# :" + aId, e);
	    throw new ServiceException(IseExceptionCodeEnum.DataAccess);
	}
    }

    @Override
    public List<RecSheetReportingSearchResults> findRecommendationReportsByAId(String aId, Date startDate,
	    Date endDate, String storeId) throws ServiceException {
	try{
	    if (StringUtils.isEmpty(storeId) || StringUtils.isEmpty(aId) || startDate == null || endDate == null){
		return Collections.emptyList();
	    }
	    List<RecSheetReportingSearchResults> verifiedResults = new ArrayList<RecSheetReportingSearchResults>();
	    RecSheetReportingSearchResults result = new RecSheetReportingSearchResults();
	    List<Recommendation> recs = recommendationsDao.getRecommendationReportsListByAId(aId, startDate, endDate,
		    storeId);
	    if (recs != null && recs.size() > 0){
		result.setRecs(recs);
		result.setAid(aId);
		result.setFirstName(recs.get(0).getEmpCrtFrstNm());
		result.setLastName(recs.get(0).getEmpCrtLastNm());
		verifiedResults.add(result);
		logger.debug("\nfindRecommendationReportsByAId(with storeID): \nverifiedResults: \n"
			+ verifiedResults.toString());
	    }else{
		logger.debug("\nfindRecommendationReportsByAId(with storeID): NO RECOMMENDATION FOUND FOR ID " + aId
			+ "\n");
	    }
	    return verifiedResults;
	}catch(DataAccessException e){
	    logger.error("Failed to retrieve recommendations list for employee by A# :" + aId, e);
	    throw new ServiceException(IseExceptionCodeEnum.DataAccess);
	}
    }

    @Override
    public List<RecSheetReportingSearchResults> findRecommendationReportsByLastName(String empLastName, Date startDate,
	    Date endDate) throws ServiceException {
	try{
	    if (StringUtils.isEmpty(empLastName) || startDate == null || endDate == null){
		return Collections.emptyList();
	    }

	    List<RecSheetReportingSearchResults> results = this.recommendationsDao
		    .getRecommendationEmployeeNamesByLastName(empLastName);

	    List<RecSheetReportingSearchResults> verifiedResults = new ArrayList<RecSheetReportingSearchResults>();

	    for(RecSheetReportingSearchResults current: results){
		List<Recommendation> recs = this.recommendationsDao.getRecommendationReportsListByAId(current.getAid(),
			startDate, endDate);

		if (recs != null && recs.size() > 0){
		    current.setRecs(recs);
		    verifiedResults.add(current);
		}

	    }

	    return verifiedResults;

	}catch(DataAccessException e){
	    logger.error("Failed to retrieve recommendations list for employee by last name : " + empLastName, e);
	    throw new ServiceException(IseExceptionCodeEnum.DataAccess);
	}
    }

    @Override
    public List<RecSheetReportingSearchResults> findRecommendationReportsByLastName(String empLastName, Date startDate,
	    Date endDate, String storeId) throws ServiceException {
	try{
	    if (StringUtils.isEmpty(storeId) || StringUtils.isEmpty(empLastName) || startDate == null
		    || endDate == null){
		return Collections.emptyList();
	    }

	    List<RecSheetReportingSearchResults> results = this.recommendationsDao
		    .getRecommendationEmployeeNamesByLastName(empLastName);

	    List<RecSheetReportingSearchResults> verifiedResults = new ArrayList<RecSheetReportingSearchResults>();

	    for(RecSheetReportingSearchResults current: results){
		List<Recommendation> recs = this.recommendationsDao.getRecommendationReportsListByAId(current.getAid(),
			startDate, endDate, storeId);

		if (recs != null && recs.size() > 0){
		    current.setRecs(recs);
		    verifiedResults.add(current);
		}

	    }

	    return verifiedResults;

	}catch(DataAccessException e){
	    logger.error("Failed to retrieve recommendations list for employee by last name : " + empLastName, e);
	    throw new ServiceException(IseExceptionCodeEnum.DataAccess);
	}
    }

    public void setRecommendationsDao(RecommendationsDao recommendationsDao) {
	this.recommendationsDao = recommendationsDao;
    }

    @Override
    public void saveRecommendation(Recommendation recommendation, User user) throws ServiceException {
	try{
	    if (recommendation.getId() > 0){
		// if the id is greater than zero we're working with an existing
		// rec.
		this.recommendationsDao.persistRecommendation(recommendation, user);
	    }else{
		// Otherwise need to make a new recommendation.
		this.recommendationsDao.addRecommendation(recommendation, user);
	    }

	}catch(DataAccessException e){
	    logger.error("Failed to persist Recommendation: " + recommendation.toString() + " for user: "
		    + user.getUserId(), e);
	    throw new ServiceException(IseExceptionCodeEnum.DataAccess);
	}

    }

    @Override
    public List<RecSheetReportList> findRecommendationsFromToDate(Date fromDate, Date toDate, String storeId)
	    throws ServiceException {
	// TODO validate from & to date fields
	try{
	    logger.debug("Store:" + storeId);
	    if (null == fromDate || null == toDate || fromDate.after(toDate)){
		String errMsg = "Invalid Date Range.Please enter a valid TO and FROM date range";
		logger.error(errMsg);
		throw new ServiceException(IseExceptionCodeEnum.BusinessException, errMsg);
	    }

	    List<RecSheetCountByDay> list = this.recommendationsDao.getRecommendationListFromToDate(fromDate, toDate,
		    storeId);

	    List<Long> recommendationIdList = new ArrayList<Long>();

	    Map<String, String> dateMap = new HashMap<String, String>();
	    List<RecSheetReportList> listOfRecSheetReportList = new ArrayList<RecSheetReportList>();

	    String prevAid = null;

	    String prevChangeDate = null;
	    int countByDay = 0;

	    RecSheetReportList recSheetLine;
	    for(RecSheetCountByDay recByDay: list){
		if (!recByDay.getAid().equalsIgnoreCase(prevAid)){
		    recSheetLine = new RecSheetReportList();
		    dateMap = new HashMap<String, String>();

		    countByDay = 0;

		    recSheetLine.setDateMap(dateMap);

		    recommendationIdList = new ArrayList<Long>();
		    recSheetLine.setRecommendationIdList(recommendationIdList);

		    recSheetLine.setAid(recByDay.getAid());
		    recSheetLine.setFirstName(recByDay.getFirstName());
		    recSheetLine.setLastName(recByDay.getLastName());
		    recSheetLine.setStoreId(recByDay.getStoreId());
		    listOfRecSheetReportList.add(recSheetLine);
		}

		if (!recByDay.getChangeDate().equalsIgnoreCase(prevChangeDate)){
		    if (dateMap.containsKey(recByDay.getChangeDate())){
			countByDay = Integer.valueOf(dateMap.get(recByDay.getChangeDate()));
		    }else{
			countByDay = 0;
		    }
		}
		if (Integer.parseInt(recByDay.getCountByDay()) > 0){
		    countByDay += Integer.parseInt(recByDay.getCountByDay());
		    dateMap.put(recByDay.getChangeDate(), String.valueOf(countByDay));
		    recommendationIdList.add(recByDay.getRecommendationId());

		}
		prevAid = recByDay.getAid();
		prevChangeDate = recByDay.getChangeDate();
	    }
	    return listOfRecSheetReportList;

	}catch(DataAccessException e){
	    logger.error("Failed to retrieve recommendation summary list for parms from date: " + fromDate
		    + " & to date: " + toDate, e);
	    throw new ServiceException(IseExceptionCodeEnum.DataAccess);
	}catch(Exception e){
	    logger.error("Failed to retrieve recommendation summary list for parms from date: " + fromDate
		    + " & to date: " + toDate, e);
	    throw new ServiceException(IseExceptionCodeEnum.GeneralSystem);
	}
    }

    @Override
    public Recommendation getRecommendation(long recommendationId) throws ServiceException {
	try{
	    return this.recommendationsDao.getRecommendation(recommendationId);
	}catch(DataAccessException e){
	    throw new ServiceException(IseExceptionCodeEnum.DataAccess);
	}catch(Exception e){
	    throw new ServiceException(IseExceptionCodeEnum.GeneralSystem);
	}
    }

    @Override
    public String getRecommendationGSPSKU(String deviceType, int term, float productPrice) throws ServiceException {
	try{
	    return this.recSheetGSPDataDao.getGSPSKU(deviceType, term, productPrice);
	}catch(DataAccessException e){
	    logger.error("Failed to retrieve resheet GSP data for deviceType" + deviceType + " & term: " + term
		    + " & productPrice: " + productPrice, e);
	    throw new ServiceException(IseExceptionCodeEnum.DataAccess);
	}
    }

    @Override
    public Map<String, String> getRecommendationGSPPrice(float productPrice) throws ServiceException {
	try{
	    return this.recSheetGSPDataDao.getGSPPrice(productPrice);
	}catch(DataAccessException e){
	    logger.error("Failed to retrieve resheet GSP Prices for productPrice: " + productPrice, e);
	    throw new ServiceException(IseExceptionCodeEnum.DataAccess);
	}
    }

    private String blankIfNull(String val) {
	if (val == null)
	    return "";
	return val;
    }

    private String blankIfNull(BigDecimal val) {
	if (val == null)
	    return "";
	return val.toPlainString();
    }

    public byte[] getRecSheetReportAsPDF(List<Recommendation> recList) throws ServiceException {
	byte[] pdfs = null;
	try{
	    List<byte[]> pdfByteList = new ArrayList<byte[]>();
	    for(Recommendation recommendation: recList){
		byte pdf[] = getRecSheetPDF(recommendation);
		pdfByteList.add(pdf);
	    }
	    pdfs = PDFUtils.concatenatePdfs(pdfByteList);
	}catch(Exception e){
	    logger.error("Exception while generating Recommendation PDF Report", e);
	    throw new ServiceException(IseExceptionCodeEnum.PDFCreationError, "Unable Generate Recommendation Report");
	}
	return pdfs;
    }

    @Override
    public byte[] getRecSheetPDF(Recommendation recommendation) throws ServiceException {
	byte[] pdf = null;
	try{

	    Map<String, String> fieldNameValueMap = new HashMap<String, String>();
	    URL fileUrl = null;

	    if (RecommendationTypeEnum.MOBILE.getUniq() == recommendation.getRecShtTyp()){
		fileUrl = getClass().getClassLoader().getResource("Mobile_Rec_Sheet.pdf");
		fillMobileMap(recommendation, fieldNameValueMap);

	    }else if (RecommendationTypeEnum.PC.getUniq() == recommendation.getRecShtTyp()){
		fileUrl = getClass().getClassLoader().getResource("English PC.pdf");
		fillPCMap(recommendation, fieldNameValueMap);

	    }else if (RecommendationTypeEnum.MAC.getUniq() == recommendation.getRecShtTyp()){
		fileUrl = getClass().getClassLoader().getResource("English Mac.pdf");
		fillMacMap(recommendation, fieldNameValueMap);

	    }else if (RecommendationTypeEnum.TABLET.getUniq() == recommendation.getRecShtTyp()){
		fileUrl = getClass().getClassLoader().getResource("English Tablet.pdf");
		fillTabletMap(recommendation, fieldNameValueMap);

	    }
	    pdf = PDFUtils.populatePdfTemplateWithValues(fileUrl, fieldNameValueMap, null);
	}catch(Exception e){
	    logger.error("Exception while generating Recommendation PDF", e);
	    throw new ServiceException(IseExceptionCodeEnum.PDFCreationError, "Unable Generate Recommendation PDF");
	}

	return pdf;
    }

    private void fillTabletMap(Recommendation recommendation, Map<String, String> fieldNameValueMap) {
	populateCRSCommonPage(recommendation, fieldNameValueMap);

    }

    private void fillMacMap(Recommendation recommendation, Map<String, String> fieldNameValueMap) {
	populateCRSCommonPage(recommendation, fieldNameValueMap);
    }

    private void fillPCMap(Recommendation recommendation, Map<String, String> fieldNameValueMap) {
	populateCRSCommonPage(recommendation, fieldNameValueMap);
    }

    private void populateCRSCommonPage(Recommendation recommendation, Map<String, String> fieldNameValueMap) {
	// You

	fieldNameValueMap.put("you_name", recommendation.getFirstName() + " " + recommendation.getLastName());
	fieldNameValueMap.put("you_address", blankIfNull(recommendation.getAddr()));
	fieldNameValueMap.put("you_phone", Util.formatPhoneNumber(blankIfNull(recommendation.getMobileNo())));
	fieldNameValueMap.put("you_date", blankIfNull(Util.toString(
		(recommendation.getAmendedOn() != null?recommendation.getAmendedOn():recommendation.getCreatedOn()),
		"E MM/dd/yy")));
	fieldNameValueMap.put("you_email", blankIfNull(recommendation.getEmail()));
	Date upgradeElgDt = recommendation.getUpgradeEligibilityDate();
	if (upgradeElgDt != null){
	    Calendar cal = Calendar.getInstance();
	    cal.setTime(upgradeElgDt);
	    fieldNameValueMap.put("you_upgrade_date_month", String.valueOf(cal.get(Calendar.MONTH)));
	    fieldNameValueMap.put("you_upgrade_date_day", String.valueOf(cal.get(Calendar.DAY_OF_MONTH)));
	    fieldNameValueMap.put("you_upgrade_date_year", String.valueOf(cal.get(Calendar.YEAR)));
	}else{
	    fieldNameValueMap.put("you_upgrade_date_month", "");
	    fieldNameValueMap.put("you_upgrade_date_day", "");
	    fieldNameValueMap.put("you_upgrade_date_year", "");
	}
	fieldNameValueMap.put("you_text", String.valueOf(recommendation.getUpgradeReminderText()));
	fieldNameValueMap.put("you_call", String.valueOf(recommendation.getUpgradeReminderCall()));

	// Right harware
	fieldNameValueMap.put("perfect_device_do", blankIfNull(recommendation.getPreference(new Long(29))));
	fieldNameValueMap.put("seen_and_like", blankIfNull(recommendation.getPreference(new Long(30))));
	fieldNameValueMap.put("looking_for", blankIfNull(recommendation.getPreference(new Long(31))));
	StringBuffer impToYou = new StringBuffer();
	if ("true".equals(recommendation.getPreference(new Long(34))))
	    impToYou.append("Processing Speed, ");
	if ("true".equals(recommendation.getPreference(new Long(35))))
	    impToYou.append("Graphics, ");
	if ("true".equals(recommendation.getPreference(new Long(36))))
	    impToYou.append("Screen Size, ");
	if ("true".equals(recommendation.getPreference(new Long(37))))
	    impToYou.append("Battery Life, ");
	if ("true".equals(recommendation.getPreference(new Long(38))))
	    impToYou.append("Price, ");
	if ("true".equals(recommendation.getPreference(new Long(39))))
	    impToYou.append("Power, ");
	if ("true".equals(recommendation.getPreference(new Long(40))))
	    impToYou.append("Style, ");
	if ("true".equals(recommendation.getPreference(new Long(41))))
	    impToYou.append("Color, ");
	impToYou.append(blankIfNull(recommendation.getPreference(new Long(42))));
	if (impToYou.length() - impToYou.lastIndexOf(", ") == 2)
	    impToYou.delete(impToYou.lastIndexOf(", "), impToYou.length());
	fieldNameValueMap.put("important_to_you", impToYou.toString());
	fieldNameValueMap.put("where_primary_use", blankIfNull(recommendation.getPreference(new Long(32))));
	fieldNameValueMap.put("programs_use_most", blankIfNull(recommendation.getPreference(new Long(33))));

	StringBuffer howWhere = new StringBuffer();
	if ("true".equals(recommendation.getPreference(new Long(43))))
	    howWhere.append("High Speed Internet, ");
	if ("true".equals(recommendation.getPreference(new Long(44))))
	    howWhere.append("Wireless, ");
	if ("true".equals(recommendation.getPreference(new Long(45))))
	    howWhere.append("Mobile Broadband, ");
	howWhere.append(blankIfNull(recommendation.getPreference(new Long(46))));
	if (howWhere.length() - howWhere.lastIndexOf(", ") == 2)
	    howWhere.delete(howWhere.lastIndexOf(", "), howWhere.length());

	// Center column
	fieldNameValueMap.put("how_where_connect_to_internet", howWhere.toString());
	fieldNameValueMap.put("other_devices_to_connect", blankIfNull(recommendation.getPreference(new Long(47))));
	fieldNameValueMap.put("existing_hardware", blankIfNull(recommendation.getPreference(new Long(48))));
	fieldNameValueMap.put("trade_in_value", blankIfNull(recommendation.getTradeInValue()));
	fieldNameValueMap.put("recommendations", blankIfNull(recommendation.getPreference(new Long(49))));
	fieldNameValueMap.put("notes", blankIfNull(recommendation.getNotes()));
	Date date = recommendation.getAmendedOn();
	if (date == null)
	    date = recommendation.getCreatedOn();
	if (date == null)
	    date = new Date();

	fieldNameValueMap.put("date", blankIfNull(Util.toString(date, "E MM/dd/yy")));

	// Essentials
	fieldNameValueMap
		.put("essentials_heard_of_geek_squad", blankIfNull(recommendation.getPreference(new Long(50))));
	fieldNameValueMap.put("essentials_geek_squad_protection", blankIfNull(recommendation
		.getPreference(new Long(51))));
	fieldNameValueMap
		.put("essentials_geek_squad_services", blankIfNull(recommendation.getPreference(new Long(52))));
	fieldNameValueMap.put("essentials_internet_security", blankIfNull(recommendation.getPreference(new Long(53))));
	fieldNameValueMap.put("essentials_microsoft_office", blankIfNull(recommendation.getPreference(new Long(54))));
	fieldNameValueMap.put("essentials_printer_office_supplies", blankIfNull(recommendation.getPreference(new Long(
		55))));
	fieldNameValueMap.put("essentials_accessories", blankIfNull(recommendation.getPreference(new Long(56))));
	fieldNameValueMap.put("essentials_networking", blankIfNull(recommendation.getPreference(new Long(57))));
	fieldNameValueMap.put("essentials_external_storage", blankIfNull(recommendation.getPreference(new Long(58))));
	fieldNameValueMap.put("essentials_financing_and_rewards", blankIfNull(recommendation
		.getPreference(new Long(59))));
	fieldNameValueMap
		.put("essentials_headphones_speakers", blankIfNull(recommendation.getPreference(new Long(60))));

	// Consultant info
	fieldNameValueMap.put("consultant_info_name", blankIfNull(recommendation.getBbyCnsFrstNm()) + " "
		+ blankIfNull(recommendation.getBbyCnsLastNm()));
	//TODO
	//fieldNameValueMap.put("consultant_info_employee_number", "a12345678");
	fieldNameValueMap.put("consultant_info_store_phone_number", Util.formatPhoneNumber(blankIfNull(recommendation
		.getBbyCnsPhNbr()))
		+ (recommendation.getBbyCnsPhExt() != null?" Ext-":"") + blankIfNull(recommendation.getBbyCnsPhExt()));
    }

    private void fillMobileMap(Recommendation recommendation, Map<String, String> fieldNameValueMap) {
	fieldNameValueMap.put("you_name", recommendation.getFirstName() + " " + recommendation.getLastName());
	fieldNameValueMap.put("you_phone", Util.formatPhoneNumber(blankIfNull(recommendation.getMobileNo())));
	fieldNameValueMap.put("you_upgrade_date", blankIfNull(Util.toString(recommendation.getUpgradeEligibilityDate(),
		"E MM/dd/yy")));
	fieldNameValueMap.put("you_text", String.valueOf(recommendation.getUpgradeReminderText()));
	fieldNameValueMap.put("you_call", String.valueOf(recommendation.getUpgradeReminderCall()));
	fieldNameValueMap.put("you_current", blankIfNull(recommendation.getSubscriptionInfo()));
	fieldNameValueMap.put("trade_in_value", blankIfNull(recommendation.getTradeInValue()));

	fieldNameValueMap.put("perfect_device_internet", blankIfNull(recommendation.getPreference(new Long(9))));
	fieldNameValueMap.put("perfect_device_email", blankIfNull(recommendation.getPreference(new Long(10))));
	fieldNameValueMap.put("perfect_device_music", blankIfNull(recommendation.getPreference(new Long(11))));
	fieldNameValueMap.put("perfect_device_video", blankIfNull(recommendation.getPreference(new Long(12))));
	fieldNameValueMap.put("perfect_device_photo", blankIfNull(recommendation.getPreference(new Long(13))));
	fieldNameValueMap.put("perfect_device_tv", blankIfNull(recommendation.getPreference(new Long(14))));
	fieldNameValueMap.put("perfect_device_navigation", blankIfNull(recommendation.getPreference(new Long(15))));
	fieldNameValueMap.put("perfect_device_gaming", blankIfNull(recommendation.getPreference(new Long(16))));
	fieldNameValueMap.put("perfect_device_texting", blankIfNull(recommendation.getPreference(new Long(17))));
	fieldNameValueMap.put("perfect_device_unlocked", blankIfNull(recommendation.getPreference(new Long(18))));
	fieldNameValueMap.put("perfect_device_do", blankIfNull(recommendation.getPreference(new Long(19))));

	fieldNameValueMap.put("how_do_you_use_the_internet", blankIfNull(recommendation.getNetUseInfo()));
	Date date = recommendation.getAmendedOn();
	if (date == null)
	    date = recommendation.getCreatedOn();
	if (date == null)
	    date = new Date();
	fieldNameValueMap.put("date", blankIfNull(Util.toString(date, "E MM/dd/yy")));

	// Recommendations
	fieldNameValueMap.put("recommendations_plan_and_features", blankIfNull(recommendation
		.getRecommendedSubscription()));
	fieldNameValueMap.put("recommendations_phone", blankIfNull(recommendation.getRecommendedDevice()));

	// Walk out working
	fieldNameValueMap
		.put("walk_out_working_data_transfer", blankIfNull(recommendation.getPreference(new Long(20))));
	fieldNameValueMap.put("walk_out_working_social", blankIfNull(recommendation.getPreference(new Long(21))));
	fieldNameValueMap.put("walk_out_working_email", blankIfNull(recommendation.getPreference(new Long(22))));
	fieldNameValueMap.put("walk_out_working_power", blankIfNull(recommendation.getPreference(new Long(23))));
	fieldNameValueMap.put("walk_out_working_bluetooth", blankIfNull(recommendation.getPreference(new Long(24))));
	fieldNameValueMap.put("walk_out_working_voicemail", blankIfNull(recommendation.getPreference(new Long(25))));
	fieldNameValueMap.put("walk_out_working_applications", blankIfNull(recommendation.getPreference(new Long(26))));
	fieldNameValueMap.put("walk_out_working_other", blankIfNull(recommendation.getPreference(new Long(27))));
	fieldNameValueMap.put("walk_out_working_software_updates", blankIfNull(recommendation
		.getPreference(new Long(28))));

	// Essentials
	fieldNameValueMap.put("essentials_hands_free", blankIfNull(recommendation.getPreference(new Long(1))));
	fieldNameValueMap.put("essentials_memory", blankIfNull(recommendation.getPreference(new Long(2))));
	fieldNameValueMap.put("essentials_case_shield", blankIfNull(recommendation.getPreference(new Long(3))));
	fieldNameValueMap.put("essentials_appcessories", blankIfNull(recommendation.getPreference(new Long(4))));
	fieldNameValueMap.put("essentials_charger", blankIfNull(recommendation.getPreference(new Long(5))));
	fieldNameValueMap.put("essentials_geek_squad_protection",
		blankIfNull(recommendation.getPreference(new Long(6))));
	fieldNameValueMap.put("essentials_trade_in_plus", blankIfNull(recommendation.getPreference(new Long(7))));
	fieldNameValueMap.put("essentials_financing", blankIfNull(recommendation.getPreference(new Long(8))));

	fieldNameValueMap.put("notes", blankIfNull(recommendation.getNotes()));
	fieldNameValueMap.put("specialist_contact_info", blankIfNull(recommendation.getBbyCnsFrstNm()) + " "
		+ blankIfNull(recommendation.getBbyCnsLastNm()) + " "
		+ Util.formatPhoneNumber(blankIfNull(recommendation.getBbyCnsPhNbr()))
		+ (recommendation.getBbyCnsPhExt() != null?" Ext-":"") + blankIfNull(recommendation.getBbyCnsPhExt()));
    }

    /**
     * This method is called once after new RecSheet goes live. This method will
     * convert WOW and DeviceCapabilites bit maps to essential records.
     */

    @Override
    public void convertLegacyRecSheets(Date fromDate, Date toDate) throws ServiceException {
	try{
	    logger.info("Starting to convert Legacy Recsheets fromDate:" + fromDate + " - toDate:" + toDate);
	    List<Recommendation> recList = recommendationsDao.getRecommendationList(fromDate, toDate);
	    logger.info("No. of Legacy RecSheets:" + recList.size());
	    for(Recommendation rec: recList){
		logger.info("Legacy Recommendation Id:" + rec.getId());
		if (rec.isDataTransferWOW())
		    rec.setPreference(new Long(20), "true");
		if (rec.isSocialWOW())
		    rec.setPreference(new Long(21), "true");
		if (rec.isEmailWOW())
		    rec.setPreference(new Long(22), "true");
		if (rec.isPowerMgmtWOW())
		    rec.setPreference(new Long(23), "true");
		if (rec.isBluetoothWOW())
		    rec.setPreference(new Long(24), "true");
		if (rec.isVmailWOW())
		    rec.setPreference(new Long(25), "true");
		if (rec.isAppsWOW())
		    rec.setPreference(new Long(26), "true");
		if (rec.isOtherWOW())
		    rec.setPreference(new Long(27), "true");
		if (rec.isSoftwareUpdatesWOW())
		    rec.setPreference(new Long(28), "true");

		if (rec.isInternetCapability())
		    rec.setPreference(new Long(9), "true");
		if (rec.isEmailCapability())
		    rec.setPreference(new Long(10), "true");
		if (rec.isMusicCapability())
		    rec.setPreference(new Long(11), "true");
		if (rec.isVideoCapability())
		    rec.setPreference(new Long(12), "true");
		if (rec.isPhotoCapability())
		    rec.setPreference(new Long(13), "true");
		if (rec.isTvCapability())
		    rec.setPreference(new Long(14), "true");
		if (rec.isNavCapability())
		    rec.setPreference(new Long(15), "true");
		if (rec.isGamingCapability())
		    rec.setPreference(new Long(16), "true");
		if (rec.isTextingCapability())
		    rec.setPreference(new Long(17), "true");
		if (rec.isUnlockedCapability())
		    rec.setPreference(new Long(18), "true");
		DrpUser user = new DrpUser();
		user.setUserId(rec.getCreatedBy());
		logger.info("Legacy Recommendation Id:" + rec.getId() + " - Saving Essentials");
		logger.info("Preferrences:" + rec.getPreferences());
		essentailsDao.saveEssentials(rec.getPreferences(), rec.getId(), user);
	    }
	}catch(Exception e){
	    logger.error("Exception while inserting essential records", e);
	    throw new ServiceException(IseExceptionCodeEnum.DataAccess);
	}
	logger.info("End to convert Legacy Recsheets fromDate:" + fromDate + " - toDate:" + toDate);
    }

}
