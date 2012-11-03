package com.bestbuy.bbym.ise.drp.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.bestbuy.bbym.ise.drp.dao.BbyAccountDao;
import com.bestbuy.bbym.ise.drp.dao.DataTransferDao;
import com.bestbuy.bbym.ise.drp.domain.BbyAccount;
import com.bestbuy.bbym.ise.drp.domain.DrpUser;
import com.bestbuy.bbym.ise.drp.sao.OpenSSOSao;
import com.bestbuy.bbym.ise.drp.utils.DrpConstants;
import com.bestbuy.bbym.ise.exception.DataAccessException;
import com.bestbuy.bbym.ise.exception.IseBusinessException;
import com.bestbuy.bbym.ise.exception.IseExceptionCodeEnum;
import com.bestbuy.bbym.ise.exception.ServiceException;
import com.bestbuy.bbym.ise.util.Util;

@Service("authenticationService")
public class AuthenticationServiceImpl implements AuthenticationService {

    private static Logger logger = LoggerFactory.getLogger(AuthenticationServiceImpl.class);

    @Autowired
    private DrpPropertyService drpPropertyService;

    @Autowired
    private BbyAccountDao bbyAccountDao;

    @Autowired
    private DataTransferDao dataTransferDao;

    @Autowired
    private OpenSSOSao openSSOSao;

    public void setDrpPropertyService(DrpPropertyService drpPropertyService) {
	this.drpPropertyService = drpPropertyService;
    }

    public void setBbyAccountDao(BbyAccountDao bbyAccountDao) {
	this.bbyAccountDao = bbyAccountDao;
    }

    public void setDataTransferDao(DataTransferDao dataTransferDao) {
	this.dataTransferDao = dataTransferDao;
    }

    public void setOpenSSOSao(OpenSSOSao openSSOSao) {
	this.openSSOSao = openSSOSao;
    }

    @Override
    public DrpUser getAuthenticatedUser(Map<String, String> cookieNameValueMap) throws ServiceException,
	    IseBusinessException {
	String oblixCookie = cookieNameValueMap.get(OpenSSOSao.COOKIE_NAME_OBLIX);
	String openSsoCookie = cookieNameValueMap.get(OpenSSOSao.COOKIE_NAME_OPENSSO);
	if (oblixCookie == null && openSsoCookie == null){
	    logger.warn("No single signon cookie info supplied");
	    return null;
	}
	return openSSOSao.attributes(oblixCookie, openSsoCookie);
    }

    private int getDataSharingKeyIntervalInHours() {
	try{
	    String interval = drpPropertyService.getProperty("GSP_CANCEL_DATA_TRANSFER_INTERVAL");
	    return Util.getInt(interval, 1);
	}catch(ServiceException se){
	    logger.error(se.getFullMessage());
	    return 1;
	}
    }

    public DrpUser authenticateUser(String userName, String password) throws ServiceException, IseBusinessException {

	// trim inputs
	userName = StringUtils.trimToEmpty(userName.trim());
	password = StringUtils.trimToEmpty(password);

	DrpUser user = openSSOSao.authenticate(userName, password);

	List<String> roles = user.getApplicationRoles();
	if (roles.isEmpty() || (roles.size() == 1 && roles.contains(DrpConstants.DRP_UNAUTHORIZED))){
	    String msg = "User '" + userName
		    + "' has not been assigned any application roles. Please contact the support for access requests.";
	    throw new IseBusinessException(IseExceptionCodeEnum.LoginError, msg);
	}

	return user;
    }

    @Override
    public boolean authenticateSharingKey(String dataSharingKey, String userId, String storeId, boolean isGspCancel) {
	Date createdTime = null;
	BbyAccount account = null;
	try{
	    if (isGspCancel){
		account = bbyAccountDao.getBbyAccount(dataSharingKey, storeId, userId);
	    }else{
		createdTime = dataTransferDao.getDataTransferCreatedTime(dataSharingKey, storeId, userId);

		//Skip if the record exists - no time interval validation required other than GSP CANCEL Flow
		if (createdTime != null){
		    return true;
		}
	    }
	}catch(EmptyResultDataAccessException e){
	    logger.debug("no records found for DSK");
	}catch(DataAccessException e){
	    logger.info("exception while attempting to validate existence of record for GSP cancel");
	}catch(RuntimeException e){
	    logger.error("exception while attempting to validate existence of record for GSP cancel", e);
	}

	if (createdTime == null && account == null){
	    logger.info("Account not found for dataSharingKey=" + dataSharingKey);
	    return false;
	}

	Calendar createdOn = Calendar.getInstance();
	if (isGspCancel){
	    createdOn.setTime(account.getCreatedOn());
	}else{
	    createdOn.setTime(createdTime);
	}
	createdOn.add(Calendar.HOUR, getDataSharingKeyIntervalInHours());

	boolean valid = Calendar.getInstance().before(createdOn);
	if (!valid){
	    logger.info("Data sharing key has expired. dataSharingKey=" + dataSharingKey + " created more than "
		    + getDataSharingKeyIntervalInHours() + " hours ago");
	}
	return valid;
    }
}
