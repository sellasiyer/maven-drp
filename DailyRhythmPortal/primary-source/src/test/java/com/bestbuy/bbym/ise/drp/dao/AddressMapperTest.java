package com.bestbuy.bbym.ise.drp.dao;

import static org.junit.Assert.assertEquals;

import java.sql.ResultSet;

import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.bestbuy.bbym.ise.drp.domain.Address;

public class AddressMapperTest {

    private ResultSet resultSet;

    @Before
    public void setUp() throws Exception {
	resultSet = EasyMock.createMock(ResultSet.class);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testAddress() throws Exception {
	AddressMapper addressMapper = new AddressMapper();

	EasyMock.expect(resultSet.next()).andReturn(true);
	EasyMock.expect(resultSet.getString("ADDR_ID")).andReturn("12345");
	EasyMock.expect(resultSet.getString("addr_ln_1")).andReturn("7601");
	EasyMock.expect(resultSet.getString("addr_ln_2")).andReturn("Penn Ave S");
	EasyMock.expect(resultSet.getString("city")).andReturn("Minneapolis");
	EasyMock.expect(resultSet.getString("zipcode")).andReturn("55423");
	EasyMock.expect(resultSet.getString("state")).andReturn("MN");

	EasyMock.expect(resultSet.next()).andReturn(false);

	EasyMock.replay(resultSet);

	Address address = addressMapper.mapRow(resultSet, 0);

	assertEquals(address.getAddressId(), "12345");
	assertEquals(address.getAddressline1(), "7601");
	assertEquals(address.getAddressline2(), "Penn Ave S");
	assertEquals(address.getCity(), "Minneapolis");
	assertEquals(address.getZip(), "55423");
	assertEquals(address.getState(), "MN");
    }
}
