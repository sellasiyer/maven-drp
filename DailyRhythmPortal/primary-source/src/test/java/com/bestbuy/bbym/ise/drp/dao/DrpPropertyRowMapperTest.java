package com.bestbuy.bbym.ise.drp.dao;

import static org.junit.Assert.assertEquals;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import com.bestbuy.bbym.ise.drp.domain.DrpProperty;

/**
 * JUnit test for {@link DrpPropertyRowMapper}.
 */
public class DrpPropertyRowMapperTest {

    private ResultSet resultSet;

    @Before
    public void setUp() throws Exception {
	resultSet = EasyMock.createMock(ResultSet.class);
    }

    /**
     * Test {@link DrpPropertyRowMapper#mapRow(ResultSet, int)}.
     */
    @Test
    public void testMapRow() throws Exception {
	DrpPropertyRowMapper drpPropertyRowMapper = new DrpPropertyRowMapper();

	final String name = "somePropertyName";
	final String value = "somePropertyValue";
	final String description = "somePropertyDescription";
	final String modifiedBy = "someModifiedBy";
	final Date modifiedDate = Calendar.getInstance().getTime();
	final String createdBy = "someCreatedBy";
	final Date createdDate = Calendar.getInstance().getTime();
	final long id = 12345;
	final boolean wicketProperty = true;

	EasyMock.expect(resultSet.next()).andReturn(true);
	EasyMock.expect(resultSet.getString("prop_name")).andReturn(name);
	EasyMock.expect(resultSet.getString("prop_value")).andReturn(value);
	EasyMock.expect(resultSet.getString("prop_desc")).andReturn(description);
	EasyMock.expect(resultSet.getString("modified_by")).andReturn(modifiedBy);
	EasyMock.expect(resultSet.getTimestamp("modified_date")).andReturn(new Timestamp(modifiedDate.getTime()));
	EasyMock.expect(resultSet.getString("created_by")).andReturn(createdBy);
	EasyMock.expect(resultSet.getTimestamp("created_date")).andReturn(new Timestamp(createdDate.getTime()));
	EasyMock.expect(resultSet.getLong("prop_id")).andReturn(id);
	EasyMock.expect(resultSet.getInt("is_drp_prop_flg")).andReturn(wicketProperty?1:0);

	EasyMock.expect(resultSet.next()).andReturn(false);
	EasyMock.replay(resultSet);

	DrpProperty drpProperty = drpPropertyRowMapper.mapRow(resultSet, 0);

	assertEquals("Incorrect name", name, drpProperty.getName());
	assertEquals("Incorrect value", value, drpProperty.getValue());
	assertEquals("Incorrect description", description, drpProperty.getDescription());
	assertEquals("Incorrect modifiedBy", modifiedBy, drpProperty.getModifiedBy());
	assertEquals("Incorrect modifiedDate", modifiedDate, drpProperty.getModifiedDate());
	assertEquals("Incorrect createdBy", createdBy, drpProperty.getCreatedBy());
	assertEquals("Incorrect createdDate", createdDate, drpProperty.getCreatedDate());
	assertEquals("Incorrect id", new Long(id), drpProperty.getId());
	assertEquals("Incorrect wicketProperty", wicketProperty, drpProperty.isWicketProperty());
    }
}
