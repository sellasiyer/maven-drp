package com.bestbuy.bbym.ise.drp.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.bestbuy.bbym.ise.domain.User;
import com.bestbuy.bbym.ise.drp.domain.Hotlink;
import com.bestbuy.bbym.ise.exception.DataAccessException;
import com.bestbuy.bbym.ise.util.jdbc.DatabaseScript;

/**
 * JUnit test for {@link HotlinkDaoImpl}.
 */
public class HotlinkDaoImplTest extends BaseDaoTest {

    private HotlinkDaoImpl hotlinkDaoImpl = new HotlinkDaoImpl();
    private User testUser = new User();

    @Before
    public void setUp() {
	hotlinkDaoImpl.setUseNextSequenceOnCreate(true);
	hotlinkDaoImpl.setNamedJdbcTemplate(new NamedParameterJdbcTemplate(db));
	hotlinkDaoImpl.setJdbcTemplate(new JdbcTemplate(db));
	testUser.setUserId("a777");
    }

    @Test
    public void testCreateHotlink() throws DataAccessException {
	Hotlink hotlink = new Hotlink();
	hotlink.setUrl("www.gmail.com");
	hotlink.setUrlAlias("Gmail");
	hotlink.setDisplayOrder(4);
	hotlink.setUserId("a777");
	hotlink.setCreatedBy("a123");
	hotlink.setCreatedDate(new Date());
	hotlink.setModifiedBy("a123");
	hotlink.setModifiedDate(new Date());
	hotlinkDaoImpl.createHotlink(hotlink);
	//
	Hotlink hl = hotlinkDaoImpl.findHotlink("a777", "Gmail");
	assertNotNull(hl);
	assertNotNull(hl.getId());
	assertEquals("Gmail", hl.getUrlAlias());
	assertEquals("www.gmail.com", hl.getUrl());
	assertEquals(4, hl.getDisplayOrder());
	assertEquals("a777", hl.getUserId());
	assertEquals("a123", hl.getCreatedBy());
    }

    @Test
    public void testGetHotlinks() throws DataAccessException {
	DatabaseScript.execute("ise_db_scripts/add_hotlinks_data.sql", db);
	List<Hotlink> list = hotlinkDaoImpl.getHotlinks("a234");
	assertNotNull(list);
	assertEquals(2, list.size());
	Hotlink hotlink = list.get(1);
	assertNotNull(hotlink);
	assertEquals(new Long(3L), hotlink.getId());
	assertEquals("Ford", hotlink.getUrlAlias());
	assertEquals("www.ford.com", hotlink.getUrl());
	assertEquals("a234", hotlink.getUserId());
	assertEquals(2, hotlink.getDisplayOrder());
	assertEquals("a123", hotlink.getCreatedBy());
	assertNotNull(hotlink.getCreatedDate());
    }

    @Test
    public void testFindHotlink() throws DataAccessException {
	DatabaseScript.execute("ise_db_scripts/add_hotlinks_data.sql", db);
	Hotlink hl = hotlinkDaoImpl.findHotlink("a789", "Chevy");
	assertNotNull(hl);
	assertNotNull(hl.getId());
	assertEquals("Chevy", hl.getUrlAlias());
	assertEquals("www.chevy.com", hl.getUrl());
	assertEquals(3, hl.getDisplayOrder());
	assertEquals("a789", hl.getUserId());
	assertEquals("a123", hl.getCreatedBy());
	hl = hotlinkDaoImpl.findHotlink("a789", "NotThere");
	assertNull(hl);
    }

    @Test
    public void testUpdateHotlink() throws DataAccessException {
	DatabaseScript.execute("ise_db_scripts/add_hotlinks_data.sql", db);
	Hotlink h1 = hotlinkDaoImpl.findHotlink("a234", "Google");
	assertNotNull(h1);
	assertNotNull(h1.getId());
	Hotlink hotlink = new Hotlink();
	hotlink.setId(h1.getId());
	hotlink.setUrl("www.google.com/us");
	hotlink.setUrlAlias("Google");
	hotlink.setDisplayOrder(3);
	hotlink.setUserId("a234");
	hotlink.setModifiedBy("a777");
	hotlink.setModifiedDate(new Date());
	hotlinkDaoImpl.updateHotlink(hotlink);
	//
	Hotlink h2 = hotlinkDaoImpl.findHotlink("a234", "Google");
	assertNotNull(h2);
	assertNotNull(h2.getId());
	assertEquals("Google", h2.getUrlAlias());
	assertEquals("www.google.com/us", h2.getUrl());
	assertEquals(3, h2.getDisplayOrder());
	assertEquals("a234", h2.getUserId());
	assertEquals("a777", h2.getModifiedBy());
    }

    @Test
    public void testDeleteHotlink() throws DataAccessException {
	DatabaseScript.execute("ise_db_scripts/add_hotlinks_data.sql", db);
	Hotlink h1 = hotlinkDaoImpl.findHotlink("a234", "Google");
	assertNotNull(h1);
	assertNotNull(h1.getId());
	Hotlink hotlink = new Hotlink();
	hotlink.setId(h1.getId());
	hotlinkDaoImpl.deleteHotlink(hotlink);
	Hotlink h2 = hotlinkDaoImpl.findHotlink("a234", "Google");
	assertNull(h2);
    }

}
