package com.bestbuy.bbym.ise.drp.dao;

import java.util.UUID;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.bestbuy.bbym.ise.drp.domain.Address;
import com.bestbuy.bbym.ise.drp.domain.CarrierAccount;
import com.bestbuy.bbym.ise.exception.DataAccessException;

/**
 * JUnit test for {@link CarrierAccountDaoImpl}.
 * 
 * @author Sridhar Savaram
 */
public class CarrierAccountDaoImplTest extends BaseDaoTest {

    private CarrierAccountDaoImpl carrierAccountDao = new CarrierAccountDaoImpl();
    private AddressDaoImpl addressDao = new AddressDaoImpl();

    @Before
    public void setUp() {
	carrierAccountDao.setUseNextSequenceOnCreate(true);
	carrierAccountDao.setNamedJdbcTemplate(new NamedParameterJdbcTemplate(db));
	carrierAccountDao.setJdbcTemplate(new JdbcTemplate(db));
	addressDao.setUseNextSequenceOnCreate(true);
	addressDao.setNamedJdbcTemplate(new NamedParameterJdbcTemplate(db));
	addressDao.setJdbcTemplate(new JdbcTemplate(db));
	carrierAccountDao.setAddressDao(addressDao);
    }

    @Test
    public void testPersistCarrierAccountDao() throws DataAccessException {
	String dataSharingKey = UUID.randomUUID().toString();
	String carrierAccountAddressId = UUID.randomUUID().toString();
	Address address = new Address(carrierAccountAddressId, "55435", "7081 Penn Ave S", "Suite 110", "Richfield",
		"MN", "a175157", "a175157");
	CarrierAccount carrierAccount1 = new CarrierAccount("1010101", "55435", "Sri", "Sid", "4102221212",
		dataSharingKey, address, "aa@a.com", "a175157", "a175157");
	carrierAccountDao.persistCarrierAccount(carrierAccount1);
	CarrierAccount carrierAccount2 = carrierAccountDao.getCarrierAccount(dataSharingKey);
	TestUtil.assertBeanEqual(carrierAccount1, carrierAccount2);
    }

    @Test
    @Ignore
    public void testUpdateCarrierAccount() throws DataAccessException {
	String dataSharingKey = "df86bcf8-75d5-4d82-a4ef-82052893469a";
	Address address = new Address(null, "55435", "7081 Penn Ave S", "Suite 110", "Richfield", "MN", "a175157",
		"a175157");
	CarrierAccount carrierAccount1 = new CarrierAccount("999999", "55555", "SSS", "AAA", "666666666",
		dataSharingKey, null, "aa@a.aaa", "a175157", "a175157");
	carrierAccountDao.updateCarrierAccount(carrierAccount1);
	CarrierAccount carrierAccount2 = carrierAccountDao.getCarrierAccount(dataSharingKey);
	TestUtil.assertBeanEqual(carrierAccount1, carrierAccount2);
    }
}
