package com.bestbuy.bbym.ise.drp.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.bestbuy.bbym.ise.domain.User;
import com.bestbuy.bbym.ise.drp.domain.DrpUserFactory;
import com.bestbuy.bbym.ise.drp.domain.Essentials;
import com.bestbuy.bbym.ise.drp.domain.Essentials.Essential;
import com.bestbuy.bbym.ise.drp.domain.Essentials.EssentialType;
import com.bestbuy.bbym.ise.util.jdbc.DatabaseScript;

/**
 * JUnit test for {@link EssentialsDaoImpl}.
 */
public class EssentialsDaoImplTest extends BaseDaoTest {

    private EssentialsDaoImpl essentialsDaoImpl = new EssentialsDaoImpl();

    @Before
    public void setUp() {
	essentialsDaoImpl.setUseNextSequenceOnCreate(true);
	essentialsDaoImpl.setNamedJdbcTemplate(new NamedParameterJdbcTemplate(db));
	essentialsDaoImpl.setJdbcTemplate(new JdbcTemplate(db));
    }

    /**
     * Test {@link EssentialsDaoImpl#saveEssentials(Essentials, Long, User)}.
     */
    @Test
    public void testSaveEssentials() throws Exception {
	DatabaseScript.execute("ise_db_scripts/add_recommendations_data.sql", db);

	Map<Long, String> preferences = new HashMap<Long, String>();
	preferences.put(new Long(1L), "test");
	User user = DrpUserFactory.getDrpUser();

	essentialsDaoImpl.saveEssentials(preferences, new Long(1L), user);

	Map<Long, String> preferences1 = essentialsDaoImpl.getEssentials(new Long(1L));
	assertNotNull("No essentials found", preferences1);
	assertEquals("test", preferences1.get(new Long(1L)));
    }

    /**
     * Test {@link EssentialsDaoImpl#getEssentials(long)}.
     */
    @Test
    public void testGetEssentials() throws Exception {
	DatabaseScript.execute("ise_db_scripts/add_recommendations_data.sql", db);

	Map<Long, String> preferences = essentialsDaoImpl.getEssentials(new Long(1L));

	assertNotNull("No essentials found", preferences);
	assertEquals("Incorrect number of essentials", 1, preferences.size());
	Map.Entry<Long, String> preference = preferences.entrySet().iterator().next();
	assertEquals("Incorrect id", Long.valueOf(1), preference.getKey());
	assertEquals("Incorrect value", "someComment", preference.getValue());
    }

}
