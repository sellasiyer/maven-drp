package com.bestbuy.bbym.ise.drp.dao;

import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.bestbuy.bbym.ise.drp.domain.Address;

/**
 * JUnit test for {@link AddressDaoImpl}.
 */
public class AddressDaoImplTest extends BaseDaoTest {

    private AddressDaoImpl addressDaoImpl = new AddressDaoImpl();

    @Before
    public void setUp() {
	addressDaoImpl.setNamedJdbcTemplate(new NamedParameterJdbcTemplate(db));
	addressDaoImpl.setJdbcTemplate(new JdbcTemplate(db));
    }

    @Test
    public void testDeleteAddress() {
	// TODO Implement me!!
    }

    /**
     * Test for persistAddress
     */
    @Test
    public void testPersistAddress() throws Exception {

	Address address = new Address("addressId", "48375", "addressline1", "addressline2", "city", "state",
		"createdBy", "modifiedBy");

	addressDaoImpl.persistAddress(address);
    }
}
