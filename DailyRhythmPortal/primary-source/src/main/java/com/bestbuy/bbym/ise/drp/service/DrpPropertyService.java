package com.bestbuy.bbym.ise.drp.service;

import java.util.List;

import com.bestbuy.bbym.ise.drp.domain.DrpProperty;
import com.bestbuy.bbym.ise.exception.ServiceException;

/**
 * Service for persist and retrieve the drp properties.
 * 
 * @author a904002
 */
public interface DrpPropertyService {

    /**
     * Returns the property value for the given property name.
     * 
     * @param propertyName 
     *            name of the property
     * @return the property value
     * @throws ServiceException
     *             thrown if the property has not been defined
     */
    public String getProperty(String propertyName) throws ServiceException;

    /**
     * Refresh the property map.
     */
    public void refreshPropertyMap();

    /**
     * Returns all the properties.
     */
    public List<DrpProperty> getAllProperties() throws ServiceException;

    /**
     * Create/Update the property in database.
     * 
     * @param drp
     *            DrpProperty object which holds the property name/value
     */
    public void createOrUpdateProperty(DrpProperty drp) throws ServiceException;

    /**
     * Returns the property value for the given property name. If not found
     * returns defaultValue.
     * 
     * @param propertyName
     *            name of the property
     * @param defaultValue
     *            default value if property not found
     */
    public String getProperty(String propertyName, String defaultValue);

    /**
     * Returns the property value for the given property name.
     * 
     * @param propertyName
     *                  name of the property
     * @return the property value for the given property name
     */
    public String getWicketProperty(String propertyName);

    /**
     * Deletes property from database
     */
    public void deleteProperty(String propertyName) throws ServiceException;

    /**
     * Synchronize the property maps on different instances of drp application
     */
    public void synchronizeServers() throws ServiceException;
}
