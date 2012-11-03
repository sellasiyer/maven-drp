package com.bestbuy.bbym.ise.drp.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.bestbuy.bbym.ise.drp.domain.Address;
import com.bestbuy.bbym.ise.drp.domain.BbyAccount;

/**
 * JUnit test for {@link BbyAccountDaoImpl}.
 * 
 * @author Sridhar Savaram
 */
public class BbyAccountDaoImplTest extends BaseDaoTest {

    private BbyAccountDaoImpl bbymAccountDao = new BbyAccountDaoImpl();
    private AddressDaoImpl addressDao = new AddressDaoImpl();

    @Before
    public void setUp() {
	bbymAccountDao.setUseNextSequenceOnCreate(true);
	bbymAccountDao.setNamedJdbcTemplate(new NamedParameterJdbcTemplate(db));
	bbymAccountDao.setJdbcTemplate(new JdbcTemplate(db));
	addressDao.setUseNextSequenceOnCreate(true);
	addressDao.setNamedJdbcTemplate(new NamedParameterJdbcTemplate(db));
	addressDao.setJdbcTemplate(new JdbcTemplate(db));
	bbymAccountDao.setAddressDao(addressDao);
    }

    /**
     * Test {@link BbyAccountDaoImpl#persistBbyAccount(BbyAccount)}.
     */
    @Test
    public void testPersistBbyAccount() throws Exception {
	String dataSharingKey = UUID.randomUUID().toString();
	String bbymAccountAddressId = UUID.randomUUID().toString();
	Address address = new Address(bbymAccountAddressId, "55435", "7081 Penn Ave S", "Suite 110", "Richfield", "MN",
		"a175157", "a175157");
	BbyAccount bbymAccount1 = new BbyAccount(dataSharingKey, "Sri", "Sid", "4102221212", "aa@a.com", "1234567890",
		address, "a175157", "a175157");
	bbymAccountDao.persistBbyAccount(bbymAccount1);
	BbyAccount bbymAccount2 = bbymAccountDao.getBbyAccount(dataSharingKey);
	TestUtil.assertBeanEqual(bbymAccount1, bbymAccount2);
    }

    /**
     * Test {@link BbyAccountDaoImpl#updateBbyAccount(BbyAccount)}.
     */
    @Test
    public void testUpdateBbyAccount() throws Exception {
	String dataSharingKey = "36d70c7d-fc98-4c97-af32-314370eeafff";
	String bbymAccountAddressId = UUID.randomUUID().toString();

	Address address = new Address(bbymAccountAddressId, "55435", "7081 Penn Ave S", "Suite 110", "Richfield", "MN",
		"a175157", "a175157");
	BbyAccount bbymAccount1 = new BbyAccount(dataSharingKey, "Sri", "Sid", "4102221212", "aa@a.com", "1234567890",
		address, "a175157", "a175157");
	bbymAccountDao.persistBbyAccount(bbymAccount1);

	address = new Address(null, "55435", "7081 Penn Ave S", "BestBuy", "Richfield", "MN", "a175157", "a175157");
	bbymAccount1 = new BbyAccount(dataSharingKey, "Bob", "Sid", "6122913024", "aa@a.com", "1234567890", address,
		"a175157", "a175157");
	bbymAccountDao.updateBbyAccount(bbymAccount1);
	BbyAccount bbymAccount2 = bbymAccountDao.getBbyAccount(dataSharingKey);
	TestUtil.assertBeanEqual(bbymAccount1, bbymAccount2);
    }

    @Test
    public void testDeleteBbyAccount() throws Exception {
	String dataSharingKey = UUID.randomUUID().toString();
	String bbymAccountAddressId = UUID.randomUUID().toString();

	Address address = new Address(bbymAccountAddressId, "55435", "7081 Penn Ave S", "Suite 110", "Richfield", "MN",
		"a175157", "a175157");
	BbyAccount bbymAccount1 = new BbyAccount(dataSharingKey, "Sri", "Sid", "4102221212", "aa@a.com", "1234567890",
		address, "a175157", "a175157");
	bbymAccountDao.persistBbyAccount(bbymAccount1);
	bbymAccountDao.deleteBbyAccount(dataSharingKey);
	BbyAccount bbymAccount2 = null;
	try{
	    bbymAccount2 = bbymAccountDao.getBbyAccount(dataSharingKey);

	}catch(EmptyResultDataAccessException e){
	    // throw new ServiceException(IseExceptionCodeEnum.NOTFOUND,
	    // "No Data Available");
	}
	assertEquals(null, bbymAccount2);
    }

    @Test
    public void testDeleteBbyAccountException() throws Exception {
	String dataSharingKey = UUID.randomUUID().toString();
	try{
	    bbymAccountDao.deleteBbyAccount(dataSharingKey);
	}catch(EmptyResultDataAccessException e){
	    assertTrue("No Data is available", true);
	}
    }
}
