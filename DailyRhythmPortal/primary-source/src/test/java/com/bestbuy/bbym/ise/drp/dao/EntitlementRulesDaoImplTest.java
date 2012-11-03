package com.bestbuy.bbym.ise.drp.dao;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.bestbuy.bbym.ise.drp.domain.EntitlementRulesModel;
import com.bestbuy.bbym.ise.util.jdbc.DatabaseScript;

/**
 * JUnit test for {@link EntitlementRulesDaoImpl}.
 */
@Ignore
public class EntitlementRulesDaoImplTest extends BaseDaoTest {

    private Logger logger = LoggerFactory.getLogger(EntitlementRulesDaoImplTest.class);

    private EntitlementRulesDaoImpl entitlementRulesDaoImpl = new EntitlementRulesDaoImpl();

    @Before
    public void setUp() {
	entitlementRulesDaoImpl.setUseNextSequenceOnCreate(true);
	entitlementRulesDaoImpl.setNamedJdbcTemplate(new NamedParameterJdbcTemplate(db));
	entitlementRulesDaoImpl.setJdbcTemplate(new JdbcTemplate(db));
    }

    /**
     * Test for {@link EntitlementRulesDaoImpl#getAllEntitlementRules()}.
     */
    @Test
    public void testGetAllEntitlementRules() throws Exception {
	DatabaseScript.execute("ise_db_scripts/add_ret_exch_data.sql", db);
	List<EntitlementRulesModel> entitlementRulesModelList = entitlementRulesDaoImpl.getAllEntitlementRules();
	for(EntitlementRulesModel entitlementRulesModel: entitlementRulesModelList){
	    logger.info("EntitlementRules=" + entitlementRulesModel);
	}
	assertEquals(2, entitlementRulesModelList.size());
    }

}
