package com.bestbuy.bbym.ise.drp.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.bestbuy.bbym.ise.drp.domain.BeastDevice;
import com.bestbuy.bbym.ise.exception.DataAccessException;

/**
 * 
 * @author a194869
 */
@Repository("beastDeviceDao")
public class TradeInDeviceDetailsDaoImpl extends AbstractDao implements TradeInDeviceDetailsDao {

    private final String DEVICE_DETAILS_INSERT_QUERY = "insert into BST_ISE_SCH01.trd_in_devc_list (id,dta_sharing_key,handset_id,ph_nbr,devc_desc,carr,handset_id_type,src,sel_flag,"
	    + "created_by,created_on) values (:id, :dataSharingKey, :handsetId, :phNbr, :devcDesc,:carrier,:handsetIdType,:src,:selectedFlag, :createdBy, sysdate)";

    private final String DEVICE_DETAILS_SELECT_QUERY = "SELECT id,dta_sharing_key,handset_id,ph_nbr,devc_desc,carr,handset_id_type,src,sel_flag,created_by,created_on, amended_by, "
	    + "amended_on from BST_ISE_SCH01.trd_in_devc_list WHERE dta_sharing_key = ?";

    private final String SELECTED_DEVICE_DETAILS_SELECT_QUERY = "SELECT id,dta_sharing_key,handset_id,ph_nbr,devc_desc,carr,handset_id_type,src,sel_flag,created_by,created_on, amended_by, "
	    + "amended_on from BST_ISE_SCH01.trd_in_devc_list WHERE dta_sharing_key = ? and sel_flag=1";

    private final String SELECTED_DEVICE_UPDATE_QUERY = "update BST_ISE_SCH01.trd_in_devc_list set sel_flag = :selectedFlag,"
	    + "amended_by = :modifiedBy, amended_on = sysdate where id = :id ";

    private final String RESET_SELECTED_DEVICE_QUERY = "update BST_ISE_SCH01.trd_in_devc_list set sel_flag = 0,"
	    + "amended_by = :modifiedBy, amended_on = sysdate where dta_sharing_key = :dataSharingKey ";

    @Override
    public void persistTradeInDeviceDetails(BeastDevice tradeInDeviceDetails) throws DataAccessException,
	    RuntimeException {
	Map<String, Object> params = new HashMap<String, Object>();
	params.put("id", tradeInDeviceDetails.getId());
	params.put("dataSharingKey", tradeInDeviceDetails.getDataSharingKey());
	params.put("handsetId", tradeInDeviceDetails.getHandsetIdentifier());
	params.put("phNbr", tradeInDeviceDetails.getPhoneNumber());
	params.put("devcDesc", tradeInDeviceDetails.getDeviceDesc());
	params.put("carrier", tradeInDeviceDetails.getCarrier());
	params.put("handsetIdType", tradeInDeviceDetails.getHandsetIdentifierType());
	params.put("src", tradeInDeviceDetails.getSource());
	params.put("selectedFlag", tradeInDeviceDetails.isSelectedFlag()?1:0);
	params.put("createdBy", tradeInDeviceDetails.getCreatedBy());
	getNamedJdbcTemplate().update(DEVICE_DETAILS_INSERT_QUERY, params);

    }

    @Override
    public void updateSelectedDevice(String dataSharingKey, String id, String modifiedBy, boolean selected)
	    throws DataAccessException, RuntimeException {
	resetSelectedDevice(dataSharingKey, modifiedBy);
	Map<String, Object> params = new HashMap<String, Object>();
	params.put("selectedFlag", selected?1:0);
	params.put("modifiedBy", modifiedBy);
	params.put("id", id);
	getNamedJdbcTemplate().update(SELECTED_DEVICE_UPDATE_QUERY, params);
    }

    @Override
    public void resetSelectedDevice(String dataSharingKey, String modifiedBy) {
	Map<String, Object> params = new HashMap<String, Object>();
	params.put("modifiedBy", modifiedBy);
	params.put("dataSharingKey", dataSharingKey);
	getNamedJdbcTemplate().update(RESET_SELECTED_DEVICE_QUERY, params);

    }

    @Override
    public List<BeastDevice> getBEASTDevices(String dataSharingKey) throws DataAccessException,
	    EmptyResultDataAccessException, RuntimeException {
	return getJdbcTemplate().query(DEVICE_DETAILS_SELECT_QUERY, new Object[] {dataSharingKey },
		new TradeInDeviceDetailsRowMapper());
    }

    @Override
    public BeastDevice getSelectedBEASTDevice(String dataSharingKey) throws DataAccessException,
	    EmptyResultDataAccessException, RuntimeException {
	return getJdbcTemplate().queryForObject(SELECTED_DEVICE_DETAILS_SELECT_QUERY, new Object[] {dataSharingKey },
		new TradeInDeviceDetailsRowMapper());
    }

    private class TradeInDeviceDetailsRowMapper implements RowMapper<BeastDevice> {

	@Override
	public BeastDevice mapRow(ResultSet rs, int rowNum) throws SQLException {
	    BeastDevice device = new BeastDevice();
	    device.setId(rs.getString("id"));
	    device.setDataSharingKey(rs.getString("dta_sharing_key"));
	    device.setHandsetIdentifier(rs.getString("handset_id"));
	    if (device.getHandsetIdentifier() != null && device.getHandsetIdentifier().length() > 4){
		device.setFourSymbolHandsetIdentifier(device.getHandsetIdentifier().substring(
			device.getHandsetIdentifier().length() - 4));
	    }
	    device.setPhoneNumber(rs.getString("ph_nbr"));
	    device.setDeviceDesc(rs.getString("devc_desc"));
	    device.setCarrier(rs.getString("carr"));
	    device.setHandsetIdentifierType(rs.getString("handset_id_type"));
	    device.setSource(rs.getString("src"));
	    device.setSelectedFlag(rs.getInt("sel_flag") == 1?true:false);
	    device.setCreatedBy(rs.getString("created_by"));
	    device.setCreatedOn(rs.getTimestamp("created_on"));
	    device.setModifiedBy(rs.getString("amended_by"));
	    device.setModifiedOn(rs.getTimestamp("amended_on"));
	    return device;
	}

    }

}
