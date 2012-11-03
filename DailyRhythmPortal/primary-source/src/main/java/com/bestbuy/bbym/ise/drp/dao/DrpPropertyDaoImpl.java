package com.bestbuy.bbym.ise.drp.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import com.bestbuy.bbym.ise.drp.domain.DrpProperty;

/**
 * 
 * @author a904002
 */
@SuppressWarnings("unchecked")
@Repository("drpPropertiesDao")
public class DrpPropertyDaoImpl extends AbstractDao implements DrpPropertyDao {

    private static final String sqlSelect = "SELECT * from BST_ISE_SCH01.ise_properties";
    private static final String findPropertysql = " where prop_name=?";

    private static final String insertPropertySQL = "INSERT into BST_ISE_SCH01.ise_properties (prop_id, prop_name, prop_value, prop_desc,"
	    + " created_by, created_date, modified_by, modified_date, is_drp_prop_flg) VALUES("
	    + "BST_ISE_SCH01.ISE_PROPERTIES_SEQ.nextval, :prop_name, :prop_value, :prop_desc, :created_by, :created_date, :modified_by, :modified_date, :is_drp_prop_flg)";

    private static final String updatePropertySQL = "UPDATE BST_ISE_SCH01.ise_properties SET  prop_value = :prop_value, prop_desc = :prop_desc ,"
	    + " created_by = :created_by, created_date = :created_date, modified_by = :modified_by, modified_date = :modified_date, is_drp_prop_flg = :is_drp_prop_flg"
	    + " WHERE prop_name = :prop_name";

    private static final String deletePropertySQL = "DELETE FROM BST_ISE_SCH01.ise_properties WHERE prop_name = :prop_name";

    @Override
    public DrpProperty getProperty(String propertyName) {

	DrpProperty properties = null;

	try{
	    properties = (DrpProperty) getJdbcTemplate().queryForObject(sqlSelect + findPropertysql,
		    new Object[] {propertyName }, new DrpPropertyRowMapper());
	}catch(EmptyResultDataAccessException e){
	    // Do nothing;
	}

	return properties;
    }

    @Override
    public void updateProperty(DrpProperty updateProperty) {
	Map namedParamsMap = new HashMap();

	namedParamsMap.put("prop_name", updateProperty.getName());
	namedParamsMap.put("prop_value", updateProperty.getValue());
	namedParamsMap.put("prop_desc", updateProperty.getDescription());
	namedParamsMap.put("created_by", updateProperty.getCreatedBy());
	namedParamsMap.put("created_date", updateProperty.getCreatedDate());
	namedParamsMap.put("modified_by", updateProperty.getModifiedBy());
	namedParamsMap.put("modified_date", new Date());
	namedParamsMap.put("is_drp_prop_flg", updateProperty.isWicketProperty()?Integer.valueOf(1):Integer.valueOf(0));

	getNamedJdbcTemplate().update(updatePropertySQL, namedParamsMap);

    }

    @Override
    public List<DrpProperty> getAllProperties() {

	List<DrpProperty> propertiesList = (List<DrpProperty>) getJdbcTemplate().query(sqlSelect,
		new DrpPropertyRowMapper());

	return propertiesList;
    }

    @Override
    public void createProperty(DrpProperty newProperty) {
	Map namedParamsMap = new HashMap();

	namedParamsMap.put("prop_name", newProperty.getName());
	namedParamsMap.put("prop_value", newProperty.getValue());
	namedParamsMap.put("prop_desc", newProperty.getDescription());
	namedParamsMap.put("created_by", newProperty.getCreatedBy());
	namedParamsMap.put("created_date", new Date());
	namedParamsMap.put("modified_by", newProperty.getModifiedBy());
	namedParamsMap.put("modified_date", new Date());
	namedParamsMap.put("is_drp_prop_flg", newProperty.isWicketProperty()?Integer.valueOf(1):Integer.valueOf(0));
	getNamedJdbcTemplate().update(insertPropertySQL, namedParamsMap);
    }

    @Override
    public void deleteProperty(String propertyName) {

	Map<String, String> namedParamsMap = new HashMap<String, String>();
	namedParamsMap.put("prop_name", propertyName);
	getNamedJdbcTemplate().update(deletePropertySQL, namedParamsMap);

    }

}
