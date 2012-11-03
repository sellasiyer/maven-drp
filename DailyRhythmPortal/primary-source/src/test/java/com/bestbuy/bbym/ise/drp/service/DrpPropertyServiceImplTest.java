package com.bestbuy.bbym.ise.drp.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import com.bestbuy.bbym.ise.drp.dao.DrpPropertyDao;
import com.bestbuy.bbym.ise.drp.domain.DrpProperty;
import com.bestbuy.bbym.ise.drp.domain.DrpPropertyFactory;

/**
 * JUnit test for {@link DrpPropertyServiceImpl}.
 * 
 * @author a904002
 * 
 * Some of EasyMock methods added by a911002
 */
public class DrpPropertyServiceImplTest {

    private DrpPropertyServiceImpl drpPropertyServiceImpl = new DrpPropertyServiceImpl();

    private DrpPropertyDao drpPropertiesDao;

    @Before
    public void sepUp() {
	drpPropertiesDao = EasyMock.createMock(DrpPropertyDao.class);
	drpPropertyServiceImpl.setDrpPropertiesDao(drpPropertiesDao);
    }

    /**
     * Test {@link DrpPropertyServiceImpl#getProperty(String)}.
     */
    @Test
    public void testGetProperty() throws Exception {
	List<DrpProperty> mockDrpProperties = new ArrayList<DrpProperty>();
	DrpProperty ldapProperty = DrpPropertyFactory.getDrpProperty("LDAP", "http://test.ldap.com");
	mockDrpProperties.add(ldapProperty);
	mockDrpProperties.add(DrpPropertyFactory.getDrpProperty("UCS", "http://test.ucs.com"));
	mockDrpProperties.add(DrpPropertyFactory.getDrpProperty("CAP", "http://test.cap.com"));

	EasyMock.expect(drpPropertiesDao.getAllProperties()).andReturn(mockDrpProperties);
	EasyMock.replay(drpPropertiesDao);

	assertSame(ldapProperty.getValue(), drpPropertyServiceImpl.getProperty(ldapProperty.getName()));
    }

    @Test
    public void testGetPropertyWithParametersPropertyNameAndProperyValue() throws Exception {
	List<DrpProperty> mockDrpProperties = new ArrayList<DrpProperty>();
	DrpProperty ldapProperty = DrpPropertyFactory.getDrpProperty("LDAP", "http://test.ldap.com");
	mockDrpProperties.add(ldapProperty);
	mockDrpProperties.add(DrpPropertyFactory.getDrpProperty("UCS", "http://test.ucs.com"));
	mockDrpProperties.add(DrpPropertyFactory.getDrpProperty("CAP", "http://test.cap.com"));

	EasyMock.expect(drpPropertiesDao.getAllProperties()).andReturn(mockDrpProperties);
	EasyMock.replay(drpPropertiesDao);

	assertSame(ldapProperty.getValue(), drpPropertyServiceImpl.getProperty(ldapProperty.getName(), ldapProperty
		.getValue()));
    }

    /**
     * Test {@link DrpPropertyServiceImpl#createOrUpdateProperty(DrpProperty)}.
     * 
     * This test is for the update part of the call.
     */
    @Test
    public void testCreateOrUpdatePropertyTestingUpdate() throws Exception {
	// Creating a property
	DrpProperty mockDrpProperty = DrpPropertyFactory.getDrpProperty("UCS", "http://test.ucs.com");
	mockDrpProperty.setCreatedBy("Bob Barker");
	mockDrpProperty.setId((long) 1234);
	mockDrpProperty.setCreatedDate(new Date());
	mockDrpProperty.setDescription("This is a mock Property");

	String param = mockDrpProperty.getName();

	List<DrpProperty> mockDrpProperties = new ArrayList<DrpProperty>();
	DrpProperty ldapProperty = DrpPropertyFactory.getDrpProperty("LDAP", "http://test.ldap.com");
	mockDrpProperties.add(ldapProperty);
	mockDrpProperties.add(DrpPropertyFactory.getDrpProperty("UCS", "http://test.ucs.com"));
	mockDrpProperties.add(DrpPropertyFactory.getDrpProperty("CAP", "http://test.cap.com"));

	EasyMock.expect(drpPropertiesDao.getProperty(param)).andReturn(mockDrpProperty);
	drpPropertiesDao.updateProperty(mockDrpProperty);
	EasyMock.expectLastCall();
	EasyMock.expect(drpPropertiesDao.getAllProperties()).andReturn(mockDrpProperties);
	EasyMock.expect(drpPropertiesDao.getAllProperties()).andReturn(mockDrpProperties);
	EasyMock.replay(drpPropertiesDao);

	drpPropertyServiceImpl.createOrUpdateProperty(mockDrpProperty);
	EasyMock.verify(drpPropertiesDao);
    }

    @Test
    public void testCreateOrUpdatePropertyTestingCreate() throws Exception {
	// Creating a property
	DrpProperty mockDrpProperty = DrpPropertyFactory.getDrpProperty("UCS", "http://test.ucs.com");
	mockDrpProperty.setCreatedBy("Bob Barker");
	mockDrpProperty.setId((long) 1234);
	mockDrpProperty.setCreatedDate(new Date());
	mockDrpProperty.setDescription("This is a mock Property");

	String param = mockDrpProperty.getName();

	List<DrpProperty> mockDrpProperties = new ArrayList<DrpProperty>();
	DrpProperty ldapProperty = DrpPropertyFactory.getDrpProperty("LDAP", "http://test.ldap.com");
	mockDrpProperties.add(ldapProperty);
	mockDrpProperties.add(DrpPropertyFactory.getDrpProperty("UCS", "http://test.ucs.com"));
	mockDrpProperties.add(DrpPropertyFactory.getDrpProperty("CAP", "http://test.cap.com"));

	EasyMock.expect(drpPropertiesDao.getProperty(param)).andReturn(null);
	drpPropertiesDao.createProperty(mockDrpProperty);
	EasyMock.expectLastCall();
	EasyMock.expect(drpPropertiesDao.getAllProperties()).andReturn(mockDrpProperties);
	EasyMock.expect(drpPropertiesDao.getAllProperties()).andReturn(mockDrpProperties);
	EasyMock.replay(drpPropertiesDao);

	drpPropertyServiceImpl.createOrUpdateProperty(mockDrpProperty);
	EasyMock.verify(drpPropertiesDao);
    }

    /**
     * Test deleteProperty method
     */
    @Test
    public void testDeleteProperty() throws Exception {
	String propertyName = "LDAP";
	List<DrpProperty> mockDrpProperties = new ArrayList<DrpProperty>();
	DrpProperty ldapProperty = DrpPropertyFactory.getDrpProperty("LDAP", "http://test.ldap.com");
	mockDrpProperties.add(ldapProperty);
	mockDrpProperties.add(DrpPropertyFactory.getDrpProperty("UCS", "http://test.ucs.com"));
	mockDrpProperties.add(DrpPropertyFactory.getDrpProperty("CAP", "http://test.cap.com"));

	drpPropertiesDao.deleteProperty(propertyName);
	EasyMock.expect(drpPropertiesDao.getAllProperties()).andReturn(mockDrpProperties);
	EasyMock.expect(drpPropertiesDao.getAllProperties()).andReturn(mockDrpProperties);
	EasyMock.expectLastCall();
	EasyMock.replay(drpPropertiesDao);

	drpPropertyServiceImpl.deleteProperty(propertyName);
	EasyMock.verify(drpPropertiesDao);
    }

    /**
     * Test the deleteProperty method with Property Not Found
     */
    @Test
    public void testDeletePropertyProperyNotFound() throws Exception {
	String propertyName = "Mock Property Name";
	List<DrpProperty> mockDrpProperties = new ArrayList<DrpProperty>();
	DrpProperty ldapProperty = DrpPropertyFactory.getDrpProperty("LDAP", "http://test.ldap.com");
	mockDrpProperties.add(ldapProperty);
	mockDrpProperties.add(DrpPropertyFactory.getDrpProperty("UCS", "http://test.ucs.com"));
	mockDrpProperties.add(DrpPropertyFactory.getDrpProperty("CAP", "http://test.cap.com"));

	drpPropertiesDao.deleteProperty(propertyName);
	EasyMock.expect(drpPropertiesDao.getAllProperties()).andReturn(mockDrpProperties);
	EasyMock.expect(drpPropertiesDao.getAllProperties()).andReturn(mockDrpProperties);
	EasyMock.expectLastCall();
	EasyMock.replay(drpPropertiesDao);

	drpPropertyServiceImpl.deleteProperty(propertyName);
	EasyMock.verify(drpPropertiesDao);
    }

    /**
     * Test synchronizeServers method
     */
    @Test
    public void testSynchronizeServers() throws Exception {
	List<DrpProperty> mockDrpProperties = new ArrayList<DrpProperty>();
	DrpProperty ldapProperty = DrpPropertyFactory.getDrpProperty("LDAP", "http://test.ldap.com");
	mockDrpProperties.add(ldapProperty);
	mockDrpProperties.add(DrpPropertyFactory.getDrpProperty("UCS", "http://test.ucs.com"));
	mockDrpProperties.add(DrpPropertyFactory.getDrpProperty("CAP", "http://test.cap.com"));

	EasyMock.expect(drpPropertiesDao.getAllProperties()).andReturn(mockDrpProperties);
	EasyMock.expect(drpPropertiesDao.getAllProperties()).andReturn(mockDrpProperties);
	EasyMock.replay(drpPropertiesDao);

	drpPropertyServiceImpl.synchronizeServers();
	EasyMock.verify(drpPropertiesDao);
    }

    /**
     * Test {@link DrpPropertyServiceImpl#refreshPropertyMap()}.
     */
    @Test
    public void testRefreshPropertyMap() throws Exception {
	List<DrpProperty> mockDrpProperties = new ArrayList<DrpProperty>();
	DrpProperty ldapProperty = DrpPropertyFactory.getDrpProperty("LDAP", "http://test.ldap.com");
	mockDrpProperties.add(ldapProperty);
	mockDrpProperties.add(DrpPropertyFactory.getDrpProperty("UCS", "http://test.ucs.com"));
	mockDrpProperties.add(DrpPropertyFactory.getDrpProperty("CAP", "http://test.cap.com"));

	EasyMock.expect(drpPropertiesDao.getAllProperties()).andReturn(mockDrpProperties);
	EasyMock.replay(drpPropertiesDao);

	drpPropertyServiceImpl.refreshPropertyMap();
	EasyMock.verify(drpPropertiesDao);
    }

    @Test
    public void testGetAllProperties() throws Exception {
	List<DrpProperty> mockDrpProperties = new ArrayList<DrpProperty>();
	DrpProperty ldapProperty = DrpPropertyFactory.getDrpProperty("LDAP", "http://test.ldap.com");
	mockDrpProperties.add(ldapProperty);
	mockDrpProperties.add(DrpPropertyFactory.getDrpProperty("UCS", "http://test.ucs.com"));
	mockDrpProperties.add(DrpPropertyFactory.getDrpProperty("CAP", "http://test.cap.com"));

	EasyMock.expect(drpPropertiesDao.getAllProperties()).andReturn(mockDrpProperties);
	EasyMock.replay(drpPropertiesDao);

	assertFalse(drpPropertyServiceImpl.getAllProperties().isEmpty());
    }

}
