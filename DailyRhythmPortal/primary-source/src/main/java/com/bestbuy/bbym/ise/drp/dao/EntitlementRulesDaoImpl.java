package com.bestbuy.bbym.ise.drp.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.bestbuy.bbym.ise.drp.domain.EntitlementRulesModel;

@Repository("entitlementRulesDao")
public class EntitlementRulesDaoImpl extends AbstractDao implements EntitlementRulesDao {

    private static Logger logger = LoggerFactory.getLogger(EntitlementRulesDaoImpl.class);

    private static final String sqlgetAllEntitlementRules = "SELECT * FROM BST_ISE_SCH01.ENTITL_AND_ACTN";

    @Override
    public List<EntitlementRulesModel> getAllEntitlementRules() {

	try{
	    List<EntitlementRulesModel> entitlementRulesList = (List<EntitlementRulesModel>) getJdbcTemplate().query(
		    sqlgetAllEntitlementRules, new EntitlementAndActionRowMapper());
	    return entitlementRulesList;
	}catch(Exception e){
	    logger.error("Error occurred", e);
	}
	return null;
    }

}
