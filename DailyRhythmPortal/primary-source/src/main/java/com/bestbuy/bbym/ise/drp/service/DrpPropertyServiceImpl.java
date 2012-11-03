package com.bestbuy.bbym.ise.drp.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bestbuy.bbym.ise.drp.dao.DrpPropertyDao;
import com.bestbuy.bbym.ise.drp.domain.DrpProperty;
import com.bestbuy.bbym.ise.exception.IseExceptionCodeEnum;
import com.bestbuy.bbym.ise.exception.ServiceException;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;

/**
 * 
 * @author a904002
 */
@Service("drpPropertyService")
public class DrpPropertyServiceImpl implements DrpPropertyService {

    private static Logger logger = LoggerFactory.getLogger(DrpPropertyServiceImpl.class);

    private Map<String, DrpProperty> propertyMap = new HashMap<String, DrpProperty>();

    @Autowired
    private DrpPropertyDao drpPropertiesDao;

    // Property which holds the other instances ip/port information,
    // Separated by ","
    private static final String SERVER_ADDRESS = "SERVER_ADDRESS";

    public void setDrpPropertiesDao(DrpPropertyDao drpPropertiesDao) {
	this.drpPropertiesDao = drpPropertiesDao;
    }

    @Override
    public synchronized String getProperty(String propertyName) throws ServiceException {

	if (propertyMap.isEmpty()){
	    refreshPropertyMap();
	}

	if (!propertyMap.containsKey(propertyName)){
	    String msg = "Unable to find the property for " + propertyName
		    + " in property map. Please create a new one.";
	    logger.warn(msg);
	    throw new ServiceException(IseExceptionCodeEnum.GeneralSystem, msg);
	}

	DrpProperty drpProperty = propertyMap.get(propertyName);
	return drpProperty.isWicketProperty()?null:drpProperty.getValue();
    }

    @Override
    public synchronized void refreshPropertyMap() {
	List<DrpProperty> propertyList = drpPropertiesDao.getAllProperties();
	propertyMap = new HashMap<String, DrpProperty>();
	for(DrpProperty prop: propertyList){
	    if (propertyMap.containsKey(prop.getName())){

		propertyMap.remove(prop.getName());
	    }
	    propertyMap.put(prop.getName(), prop);
	}
    }

    @Override
    public synchronized List<DrpProperty> getAllProperties() throws ServiceException {

	if (propertyMap.isEmpty()){
	    refreshPropertyMap();
	}
	return new ArrayList<DrpProperty>(propertyMap.values());
    }

    @Override
    public void createOrUpdateProperty(DrpProperty drp) throws ServiceException {

	DrpProperty drpProperties = drpPropertiesDao.getProperty(drp.getName());
	if (null != drpProperties){
	    drpProperties.setValue(drp.getValue());
	    drpProperties.setModifiedDate(new Date());
	    drpProperties.setCreatedBy(drp.getCreatedBy());
	    drpProperties.setModifiedBy(drp.getModifiedBy());
	    drpProperties.setWicketProperty(drp.isWicketProperty());

	    drpPropertiesDao.updateProperty(drp);
	}else{
	    drp.setCreatedDate(new Date());
	    drp.setModifiedDate(new Date());

	    drpPropertiesDao.createProperty(drp);
	}
	refreshPropertyMap();
	synchronizeServers();

    }

    @Override
    public String getProperty(String propertyName, String defaultValue) {
	try{
	    return getProperty(propertyName);
	}catch(ServiceException e){
	    logger.warn(propertyName + " property not configured in DB. Using default value:" + defaultValue);
	    return defaultValue;
	}
    }

    @Override
    public void deleteProperty(String propertyName) throws ServiceException {

	if (propertyName != null){
	    drpPropertiesDao.deleteProperty(propertyName);
	    refreshPropertyMap();
	    synchronizeServers();
	}

    }

    // TODO Use a SAO instead of having the service make the call
    @Override
    public void synchronizeServers() throws ServiceException {

	String serverNames = null;
	try{
	    serverNames = getProperty(SERVER_ADDRESS);
	}catch(ServiceException ex){
	    logger.error("Unable to find the property for remote instances. Please configure the SERVER_ADDRESS.");
	}
	if (serverNames != null && StringUtils.isNotBlank(serverNames)){
	    Client client = Client.create();
	    client.addFilter(new HTTPBasicAuthFilter("beastportal", "m0bi13i53v3rythin8"));
	    if (StringUtils.isNotBlank(serverNames)){
		List<String> serverList = Arrays.asList(serverNames.split(","));
		for(String server: serverList){
		    try{
			ClientResponse response = client.resource(server + "/drp-war/drp/property/refresh").post(
				ClientResponse.class);

			if (response.getStatus() == 200){
			    logger.info("Server instance : " + server
				    + " properties successfully refreshed. Response status : " + response.getStatus());
			}else{
			    logger.error("Error while refreshing the properties. Server : " + server
				    + response.getStatus());
			}
		    }catch(Exception e){
			logger.error("ADMIN PROPERTIES UPDATE FAILED: " + server, e);
		    }
		}

	    }
	}else{
	    refreshPropertyMap();
	}
    }

    @Override
    public String getWicketProperty(String propertyName) {
	if (propertyName == null || propertyName.trim().length() == 0){
	    return null;
	}

	if (propertyMap.containsKey(propertyName)){
	    DrpProperty property = propertyMap.get(propertyName);
	    if (property.isWicketProperty()){
		return property.getValue();
	    }
	}
	return null;
    }
}
