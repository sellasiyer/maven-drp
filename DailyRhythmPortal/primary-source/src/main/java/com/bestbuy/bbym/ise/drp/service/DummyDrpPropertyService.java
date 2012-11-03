package com.bestbuy.bbym.ise.drp.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.bestbuy.bbym.ise.drp.domain.DrpProperty;
import com.bestbuy.bbym.ise.exception.IseExceptionCodeEnum;
import com.bestbuy.bbym.ise.exception.ServiceException;

@Service("drpPropertyService2")
public class DummyDrpPropertyService implements DrpPropertyService {

    private static Logger logger = LoggerFactory.getLogger(DummyDrpPropertyService.class);

    private static long currentId = 0L;

    public static long getNextId() {
	return ++currentId;
    }

    private static List<DrpProperty> propertyList = new ArrayList<DrpProperty>();
    static{
	DrpProperty dp;
	dp = new DrpProperty("xyzUrl", "http://www.xyz.com");
	dp.setId(getNextId());
	propertyList.add(dp);
	dp = new DrpProperty("abcUrl", "http://www.abc.com");
	dp.setId(getNextId());
	propertyList.add(dp);
	dp = new DrpProperty("capUrl", "http://www.cap.com");
	dp.setId(getNextId());
	propertyList.add(dp);
	dp = new DrpProperty("main guy", "santa claus");
	dp.setId(getNextId());
	propertyList.add(dp);
    }

    @Override
    public String getProperty(String propertyName) throws ServiceException {
	Random random = new Random();
	if (random.nextInt(10) == 0){
	    throw new ServiceException(IseExceptionCodeEnum.DataAccess);
	}
	for(DrpProperty dp: propertyList){
	    if (propertyName != null && propertyName.equals(dp.getName())){
		return dp.getValue();
	    }
	}
	return null;
    }

    @Override
    public void refreshPropertyMap() {
    }

    @Override
    public List<DrpProperty> getAllProperties() throws ServiceException {
	Random random = new Random();
	if (random.nextInt(10) == 0){
	    throw new ServiceException(IseExceptionCodeEnum.DataAccess);
	}
	return propertyList;
    }

    @Override
    public void createOrUpdateProperty(DrpProperty drpProp) throws ServiceException {
	Random random = new Random();
	if (random.nextInt(10) == 0){
	    throw new ServiceException(IseExceptionCodeEnum.DataAccess);
	}
	if (random.nextInt(10) == 0){
	    throw new ServiceException(IseExceptionCodeEnum.GeneralSystem);
	}
	if (drpProp == null){
	    return;
	}
	// Update
	if (drpProp.getId() != null && drpProp.getId().longValue() >= 0L){
	    for(DrpProperty dp: propertyList){
		if (dp.getId() != null && dp.getId().longValue() == drpProp.getId().longValue()){
		    dp.setValue(drpProp.getValue());
		    return;
		}
	    }

	    // Add
	}else{
	    DrpProperty dp = new DrpProperty();
	    dp.copy(drpProp);
	    dp.setId(getNextId());
	    propertyList.add(dp);
	}
    }

    @Override
    public String getProperty(String propertyName, String defaultValue) {
	try{
	    return getProperty(propertyName);
	}catch(ServiceException e){
	    logger.warn(propertyName + " property not congigured in DB. Using default value:" + defaultValue);
	    return defaultValue;
	}
    }

    @Override
    public void deleteProperty(String propertyName) {
	// TODO Auto-generated method stub

    }

    @Override
    public void synchronizeServers() throws ServiceException {
	//Do Nothing
    }

    @Override
    public String getWicketProperty(String propertyName) {
	return null;
    }

}
