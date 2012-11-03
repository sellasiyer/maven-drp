package com.bestbuy.bbym.ise.drp.dao;

import static org.junit.Assert.*;

import java.util.Date;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.bestbuy.bbym.ise.drp.domain.Config;

public class ConfigDaoImplTest extends BaseDaoTest {

    ConfigDaoImpl dao = new ConfigDaoImpl();

    @Before
    public void setUp() throws Exception {
	dao.setUseNextSequenceOnCreate(true);
	dao.setNamedJdbcTemplate(new NamedParameterJdbcTemplate(db));
	dao.setJdbcTemplate(new JdbcTemplate(db));
    }

    @Test
    public void testGetConfigItemById() {
	Config config = buildConfig(1L);
	config = dao.persist(config);
	config = dao.getConfigItemById("1");
	assertNotNull(config.getParamName());
    }

    @Test
    public void testPersist() {
	Config config = buildConfig();
	config = dao.persist(config);

	assertNotNull(config.getConfigId());
    }

    private Config buildConfig() {
	Config config = new Config();
	config.setConfigType("configType");
	config.setCreatedBy("createdBy");
	java.sql.Date date = new java.sql.Date(new Date().getTime());
	config.setCreatedOn(date);
	config.setDescription("description");
	config.setModifiedBy("modifiedBy");
	config.setModifiedOn(date);
	config.setParamName("paramName");
	config.setParamValue("paramValue");
	return config;
    }

    private Config buildConfig(Long configId) {
	Config config = new Config();
	config.setConfigType("configType");
	config.setCreatedBy("createdBy");
	java.sql.Date date = new java.sql.Date(new Date().getTime());
	config.setCreatedOn(date);
	config.setDescription("description");
	config.setModifiedBy("modifiedBy");
	config.setModifiedOn(date);
	config.setParamName("paramName");
	config.setParamValue("paramValue");
	config.setConfigId(configId);
	return config;
    }

    @Test
    public void testUpdate() {
	Config config = buildConfig();
	config = dao.persist(config);

	config.setParamName("paramName1");
	config.setParamValue("paramValue1");
	config.setDescription("description1");
	config.setModifiedBy("modifiedBy1");
	java.sql.Date date = new java.sql.Date(new Date().getTime());
	config.setModifiedOn(date);
	config.setConfigType("configType1");

	config = dao.update(config);

	Config actual = dao.getConfigItemsByType("configType1").get(0);

	assertEquals(ReflectionToStringBuilder.toString(config, ToStringStyle.SHORT_PREFIX_STYLE),
		ReflectionToStringBuilder.toString(actual, ToStringStyle.SHORT_PREFIX_STYLE));
    }

    @Test
    public void testDelete() {
	Config config = buildConfig();
	config.setConfigType("configType2");
	config = dao.persist(config);

	dao.delete(config);

	int actual = dao.getConfigItemsByType("configType2").size();
	assertEquals(0, actual);
    }

    @Test
    public void testGetConfigItemsByTypeAndParamName() {
	Config config = buildConfig();
	config.setConfigType("configTypeByName");
	config.setParamName("paramName1");
	config = dao.persist(config);

	int actual = dao.getConfigItemsByTypeAndParamName("configTypeByName", "paramName1").size();
	assertEquals(1, actual);
    }

    @Test
    public void testGetConfigItemsByTypeAndParamValue() {
	Config config = buildConfig();
	config.setConfigType("configTypeByValue");
	config.setParamValue("paramValue2");
	config = dao.persist(config);

	int actual = dao.getConfigItemsByTypeAndParamValue("configTypeByValue", "paramValue2").size();
	assertEquals(1, actual);
    }

}
