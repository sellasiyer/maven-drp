package com.bestbuy.bbym.ise.drp.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.bestbuy.bbym.ise.domain.Carrier;
import com.bestbuy.bbym.ise.drp.domain.Address;
import com.bestbuy.bbym.ise.drp.domain.BbyAccount;
import com.bestbuy.bbym.ise.drp.domain.CarrierAccount;
import com.bestbuy.bbym.ise.drp.domain.Customer;
import com.bestbuy.bbym.ise.drp.domain.RecSheetSummary;

public class CustomerRowMapper implements RowMapper<Customer> {

    @Override
    public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
	Customer customer = new Customer();
	customer.setDataSharingKey(rs.getString("dta_sharing_key"));
	BbyAccount bbyAccount = new BbyAccount();
	bbyAccount.setDataSharingKey(rs.getString("dta_sharing_key"));
	bbyAccount.setFirstName(rs.getString("ba_cust_frst_nm"));
	bbyAccount.setLastName(rs.getString("ba_cust_last_nm"));
	bbyAccount.setPhoneNumber(rs.getString("ba_cust_ph_nbr"));
	bbyAccount.setEmail(rs.getString("ba_cust_email_id"));
	bbyAccount.setEccaId(rs.getString("bby_cust_id"));
	bbyAccount.setCreatedBy(rs.getString("ba_created_by"));
	bbyAccount.setCreatedOn(rs.getDate("ba_created_on"));
	bbyAccount.setModifiedBy(rs.getString("ba_amended_by"));
	bbyAccount.setModifiedOn(rs.getDate("ba_amended_on"));
	bbyAccount.setRewardZoneNo(rs.getString("rwz_nbr"));
	Address bbyAccountAddress = new Address();
	bbyAccountAddress.setAddressline1(rs.getString("baa_address_line1"));
	bbyAccountAddress.setAddressline2(rs.getString("baa_address_line2"));
	bbyAccountAddress.setCity(rs.getString("baa_city"));
	bbyAccountAddress.setState(rs.getString("baa_state"));
	bbyAccountAddress.setZip(rs.getString("baa_zipcode"));
	bbyAccountAddress.setCreatedBy(rs.getString("baa_created_by"));
	bbyAccountAddress.setCreatedOn(rs.getDate("baa_created_on"));
	bbyAccountAddress.setModifiedBy(rs.getString("baa_amended_by"));
	bbyAccountAddress.setModifiedOn(rs.getDate("baa_amended_on"));
	bbyAccount.setAddress(bbyAccountAddress);
	customer.setBbyAccount(bbyAccount);
	CarrierAccount carrierAccount = new CarrierAccount();
	carrierAccount.setDataSharingKey(rs.getString("dta_sharing_key"));
	carrierAccount.setCarrierAccountNumber(rs.getString("acct_nbr"));
	carrierAccount.setCoverageZip(rs.getString("covg_zip"));
	carrierAccount.setFirstName(rs.getString("ca_cust_frst_nm"));
	carrierAccount.setLastName(rs.getString("ca_cust_last_nm"));
	carrierAccount.setPhoneNumber(rs.getString("ca_cust_ph_nbr"));
	carrierAccount.setEmail(rs.getString("ca_cust_email_id"));
	carrierAccount.setCreatedBy(rs.getString("ca_created_by"));
	carrierAccount.setCreatedOn(rs.getDate("ca_created_on"));
	carrierAccount.setModifiedBy(rs.getString("ca_amended_by"));
	carrierAccount.setModifiedOn(rs.getDate("ca_amended_on"));
	carrierAccount.setCarrier(rs.getString("ca_carrier") != null?Carrier.fromShortLabel(rs.getString("ca_carrier"))
		:null);
	Address caAccountAddress = new Address();
	caAccountAddress.setAddressline1(rs.getString("caa_address_line1"));
	caAccountAddress.setAddressline2(rs.getString("caa_address_line2"));
	caAccountAddress.setCity(rs.getString("caa_city"));
	caAccountAddress.setState(rs.getString("caa_state"));
	caAccountAddress.setZip(rs.getString("caa_zipcode"));
	caAccountAddress.setCreatedBy(rs.getString("caa_created_by"));
	caAccountAddress.setCreatedOn(rs.getDate("caa_created_on"));
	caAccountAddress.setModifiedBy(rs.getString("caa_amended_by"));
	caAccountAddress.setModifiedOn(rs.getDate("caa_amended_on"));
	carrierAccount.setAddress(caAccountAddress);
	RecSheetSummary recSheetSummary = new RecSheetSummary();
	recSheetSummary.setDataSharingKey(rs.getString("dta_sharing_key"));
	recSheetSummary.setPlanInfo(rs.getString("plan_info"));
	recSheetSummary.setDeviceInfo(rs.getString("device_info"));
	recSheetSummary.setGspPlanInfo(rs.getString("gsp_plan_info"));
	recSheetSummary.setBuyBackPlanInfo(rs.getString("buyback_plan_info"));
	recSheetSummary.setCreatedBy(rs.getString("rec_created_by"));
	recSheetSummary.setCreatedOn(rs.getDate("rec_created_on"));
	recSheetSummary.setModifiedBy(rs.getString("rec_amended_by"));
	recSheetSummary.setModifiedOn(rs.getDate("rec_amended_on"));
	customer.setRecSheetSummary(recSheetSummary);
	customer.setCarrierAccount(carrierAccount);
	customer.setStoreId(rs.getString("stor_id"));
	customer.setTransferFlag(rs.getInt("xfer_flg") == 1?true:false);
	customer.setSource(rs.getString("src_sys"));
	customer.setCapTransactionId(rs.getString("cap_trans_id"));
	customer.setCreatedBy(rs.getString("dt_created_by"));
	customer.setCreatedOn(rs.getDate("dt_created_on"));
	customer.setModifiedBy(rs.getString("dt_amended_by"));
	customer.setModifiedOn(rs.getDate("dt_amended_on"));
	return customer;
    }

}
