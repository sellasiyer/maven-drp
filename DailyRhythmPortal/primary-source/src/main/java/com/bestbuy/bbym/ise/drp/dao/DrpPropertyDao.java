package com.bestbuy.bbym.ise.drp.dao;

import java.util.List;

import com.bestbuy.bbym.ise.drp.domain.DrpProperty;

/**
 * 
 * @author a904002
 */
public interface DrpPropertyDao {

    public DrpProperty getProperty(String propertyName);

    public void createProperty(DrpProperty newProperty);

    public void updateProperty(DrpProperty updateProperty);

    public void deleteProperty(String propertyName);

    public List<DrpProperty> getAllProperties();

}
