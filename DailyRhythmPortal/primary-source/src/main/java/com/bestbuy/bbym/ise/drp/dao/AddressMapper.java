package com.bestbuy.bbym.ise.drp.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.bestbuy.bbym.ise.drp.domain.Address;

class AddressMapper implements RowMapper<Address> {

    public AddressMapper() {
	super();
    }

    @Override
    public Address mapRow(ResultSet rs, int rowNum) throws SQLException {
	//<ADDR ADDR_ID="2" addr_ln_1="addr_ln_1" addr_ln_2="addr_ln_2" city="city" zipcode="zipcode" state="ST"/>
	Address address = new Address();
	address.setAddressId(rs.getString("ADDR_ID"));
	address.setAddressline1(rs.getString("addr_ln_1"));
	address.setAddressline2(rs.getString("addr_ln_2"));
	address.setCity(rs.getString("city"));
	address.setZip(rs.getString("zipcode"));
	address.setState(rs.getString("state"));

	return address;
    }

}
