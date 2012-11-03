package com.bestbuy.bbym.ise.drp.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.bestbuy.bbym.ise.drp.domain.Recommendation;

class RecommendationsRowMapper implements RowMapper<Recommendation> {

    @Override
    public Recommendation mapRow(ResultSet rs, int rowNum) throws SQLException {

	Recommendation r = new Recommendation();
	r.setId(rs.getLong("recommendation_id"));
	r.setDeviceCapabilities(rs.getLong("device_capabilities"));
	r.setWowRequirements(rs.getLong("wow_requirements"));
	r.setRecommendedSubscription(rs.getString("recommended_subscription"));
	r.setRecommendedDevice(rs.getString("recommended_device"));
	r.setNetUseInfo(rs.getString("net_use_info"));
	r.setNotes(rs.getString("notes"));

	r.setCreatedBy(rs.getString("created_by"));
	r.setCreatedOn(rs.getTimestamp("created_on"));
	r.setAmendedBy(rs.getString("amended_by"));
	r.setAmendedOn(rs.getTimestamp("amended_on"));

	r.setStoreId(rs.getString("store_id"));
	r.setSpecialistContactInfo(rs.getString("emp_contact"));
	r.setFirstName(rs.getString("first_name"));
	r.setLastName(rs.getString("last_name"));
	r.setMobileNo(rs.getString("mobile_no"));
	r.setBestTimeToContact(rs.getString("best_contact_tm_info"));
	r.setBbyCustomerId(rs.getString("bby_customer_id"));
	r.setUpgradeReminderText(rs.getBoolean("upgrade_reminder_text"));
	r.setUpgradeReminderCall(rs.getBoolean("upgrade_reminder_call"));
	r.setUpgradeEligibilityDate(rs.getDate("upgrade_eligibility_date"));
	r.setSubscriptionInfo(rs.getString("subscription_info"));

	r.setEmpCrtFrstNm(rs.getString("emp_crt_frst_nm"));
	r.setEmpCrtLastNm(rs.getString("emp_crt_last_nm"));
	r.setEmpAltFrstNm(rs.getString("emp_alt_frst_nm"));
	r.setEmpAltLastNm(rs.getString("emp_alt_last_nm"));

	r.setRecShtTyp(rs.getInt("rec_sht_typ"));
	r.setAddr(rs.getString("addr"));
	r.setCity(rs.getString("city"));
	r.setState(rs.getString("state"));
	r.setZipcode(rs.getString("zipcode"));
	r.setBbyCnsFrstNm(rs.getString("bby_cns_frst_nm"));
	r.setBbyCnsLastNm(rs.getString("bby_cns_last_nm"));
	r.setBbyCnsPhNbr(rs.getString("bby_cns_ph_nbr"));
	r.setBbyCnsPhExt(rs.getString("bby_cns_ph_ext"));

	// if trade in value is null, using getfloat will return 0 instead of
	// null, and we want null.
	// otherwise just leaving it as null which it should be set to by
	// default.
	String tradeIn = rs.getString("trade_in_val");
	if (tradeIn != null){
	    r.setTradeInValue(rs.getBigDecimal("trade_in_val"));
	}

	r.setLanguage(rs.getString("lang"));

	return r;
    }

}
