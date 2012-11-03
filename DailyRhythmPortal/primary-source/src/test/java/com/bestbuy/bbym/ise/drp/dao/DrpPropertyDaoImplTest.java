package com.bestbuy.bbym.ise.drp.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.bestbuy.bbym.ise.drp.domain.DrpProperty;
import com.bestbuy.bbym.ise.util.jdbc.DatabaseScript;

/**
 * JUnit test for {@link DrpPropertyDaoImpl}.
 */
public class DrpPropertyDaoImplTest extends BaseDaoTest {

    private DrpPropertyDaoImpl drpPropertyDaoImpl = new DrpPropertyDaoImpl();

    @Before
    public void setUp() {
	drpPropertyDaoImpl.setUseNextSequenceOnCreate(true);
	drpPropertyDaoImpl.setNamedJdbcTemplate(new NamedParameterJdbcTemplate(db));
	drpPropertyDaoImpl.setJdbcTemplate(new JdbcTemplate(db));
    }

    @Test
    public void testCreateProperty() throws Exception {
	DrpProperty drpProperty = DrpPropertyFactory.getDrpProperty();
	drpPropertyDaoImpl.createProperty(drpProperty);

	DrpProperty d1 = drpPropertyDaoImpl.getProperty("Gmail");
	assertNotNull(d1);
	assertNotNull(d1.getId());
	assertEquals("Incorrect name", "Gmail", d1.getName());
	assertEquals("Incorrect vale", "www.gmail.com", d1.getValue());
	assertEquals("Incorrect description", "TestDescription", d1.getDescription());
	assertEquals("Incorrect createdBy", "a123", d1.getCreatedBy());
    }

    @Test
    public void testGetDrpProperties() throws Exception {
	DatabaseScript.execute("ise_db_scripts/add_properties_data.sql", db);

	List<DrpProperty> list = drpPropertyDaoImpl.getAllProperties();
	assertNotNull("No properties found", list);
	assertEquals("Incorrect number of properties found", 2, list.size());

	// Check first property
	DrpProperty drpProperty = list.get(0);
	assertFalse("Incorrect wicketProperty", drpProperty.isWicketProperty());

	// Check second property
	drpProperty = list.get(1);
	assertNotNull("No property found", drpProperty);
	assertEquals("Incorrect id", new Long(3L), drpProperty.getId());
	assertEquals("Incorrect name", "Ford", drpProperty.getName());
	assertEquals("Incorrect value", "www.ford.com", drpProperty.getValue());
	assertEquals("Incorrect description", "TestDescription", drpProperty.getDescription());
	assertEquals("Incorrect createdBy", "a123", drpProperty.getCreatedBy());
	assertNotNull("No createdDate", drpProperty.getCreatedDate());
	assertTrue("Incorrect wicketProperty", drpProperty.isWicketProperty());
    }

    @Test
    public void testUpdateDrpProperty() throws Exception {
	final String newValue = "www.google.com/us";
	final String newDescription = "TestDescription123";
	final String newModifiedBy = "a777";

	DatabaseScript.execute("ise_db_scripts/add_properties_data.sql", db);
	DrpProperty updateProperty = new DrpProperty();
	updateProperty.setName("Google");
	updateProperty.setValue(newValue);
	updateProperty.setDescription(newDescription);
	updateProperty.setCreatedBy("a777");
	updateProperty.setCreatedDate(new Date());
	updateProperty.setModifiedBy(newModifiedBy);
	updateProperty.setModifiedDate(new Date());
	drpPropertyDaoImpl.updateProperty(updateProperty);

	DrpProperty d1 = drpPropertyDaoImpl.getProperty("Google");
	assertNotNull(d1);
	assertNotNull(d1.getId());
	assertEquals("Incorrect name", "Google", d1.getName());
	assertEquals("Incorrect value", newValue, d1.getValue());
	assertEquals("Incorrect description", newDescription, d1.getDescription());
	assertEquals("Incorrect modifiedBy", newModifiedBy, d1.getModifiedBy());
    }

    @Test
    public void testDeleteDrpProperty() throws Exception {
	DatabaseScript.execute("ise_db_scripts/add_properties_data.sql", db);
	drpPropertyDaoImpl.deleteProperty("Google");

	DrpProperty d1 = drpPropertyDaoImpl.getProperty("Google");
	assertNull(null, d1);
    }

}
