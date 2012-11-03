/**
 * 
 */
package com.bestbuy.bbym.ise.drp.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.bestbuy.bbym.ise.exception.DataAccessException;

/**
 * @author a175157
 *
 */
@Repository("recSheetGSPDataDao")
public class RecSheetGSPDataDaoImpl extends AbstractDao implements RecSheetGSPDataDao {

    private static final String selectQuery1 = "SELECT RTL_PRC, SKU FROM bst_ise_sch01.REC_GSP_PRC_SKU WHERE device_typ = ? and term = ? and max_prc >= ? and min_prc <= ?";

    private static final String selectQuery2 = "SELECT DEVICE_TYP, TERM, RTL_PRC FROM bst_ise_sch01.REC_GSP_PRC_SKU WHERE max_prc >= ? and min_prc <= ?";

    private static final String selectQuery3 = "SELECT SKU FROM bst_ise_sch01.REC_GSP_PRC_SKU WHERE device_typ = ? and term = ? and max_prc >= ? and min_prc <= ?";

    /* Returns GSP Plan Price and SKU
     * 
     */
    @Override
    public Map<String, String> getGSPData(String type, int term, float price) throws DataAccessException {
	return getJdbcTemplate().queryForObject(selectQuery1,
		new Object[] {type, new Integer(term), new Float(price), new Float(price) },
		new RowMapper<Map<String, String>>() {

		    @Override
		    public Map<String, String> mapRow(ResultSet rs, int rowNum) throws SQLException {
			Map<String, String> result = new HashMap<String, String>();
			result.put(rs.getString("RTL_PRC"), rs.getString("SKU"));
			return result;
		    }
		});
    }

    @Override
    public Map<String, String> getGSPPrice(float price) throws DataAccessException {
	final Map<String, String> result = new HashMap<String, String>();
	getJdbcTemplate().query(selectQuery2, new Object[] {new Float(price), new Float(price) },
		new RowMapper<Map<String, String>>() {

		    @Override
		    public Map<String, String> mapRow(ResultSet rs, int rowNum) throws SQLException {
			result.put(rs.getString("DEVICE_TYP") + rs.getString("TERM"), rs.getString("RTL_PRC"));
			return null;
		    }
		});
	return result;
    }

    @Override
    public String getGSPSKU(String type, int term, float price) throws DataAccessException {
	return getJdbcTemplate().queryForObject(selectQuery3,
		new Object[] {type, new Integer(term), new Float(price), new Float(price) }, String.class);
    }
}
