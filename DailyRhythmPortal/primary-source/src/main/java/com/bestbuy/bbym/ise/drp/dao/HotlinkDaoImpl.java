package com.bestbuy.bbym.ise.drp.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.bestbuy.bbym.ise.drp.domain.Hotlink;
import com.bestbuy.bbym.ise.exception.DataAccessException;

/**
 * Contains all methods that operate directly on the Hotlinks tables in the
 * database.
 */
@Repository("hotLinksDao")
public class HotlinkDaoImpl extends AbstractDao implements HotlinkDao {

    // TODO remove modified_by/modified_date on insert
    private static final String createHotlinkSQL = "insert into BST_ISE_SCH01.hotlinks (id, url_alias, url, user_id, display_order,"
	    + " created_by, created_date, modified_by, modified_date)"
	    + " values (NEW_INDEX, :url_alias, :url, :user_id, :display_order,"
	    + " :created_by, :created_date, :modified_by, :modified_date)";
    private static final String createHotlinkSequenceNextValue = "BST_ISE_SCH01.hotlinks_seq.nextval";

    private static final String updateHotlinkSQL = "update BST_ISE_SCH01.hotlinks set user_id=:user_id, url=:url, url_alias=:url_alias,"
	    + " display_order=:display_order, modified_by=:modified_by, modified_date=:modified_date where id=:id";

    private static final String deleteHotlinkByIdSQL = "delete from BST_ISE_SCH01.hotlinks where id=:id";

    private static final String getHotlinkByUserIdAndUrlAliasSQL = "select * from BST_ISE_SCH01.hotlinks where user_id=? and url_alias=?";
    private static final String getHotlinksByUserIdSQL = "select * from BST_ISE_SCH01.hotlinks where user_id=? order by display_order";
    private static final String getHotlinkByIdSQL = "select * from BST_ISE_SCH01.hotlinks where id=?";

    @Override
    public void createHotlink(Hotlink hotlink) throws DataAccessException {
	Map<String, Object> params = new HashMap<String, Object>();
	params.put("user_id", hotlink.getUserId());
	params.put("url_alias", hotlink.getUrlAlias());
	params.put("url", hotlink.getUrl());
	params.put("display_order", hotlink.getDisplayOrder());
	params.put("created_by", hotlink.getCreatedBy());
	params.put("created_date", hotlink.getCreatedDate());
	// TODO remove modified_by/modified_date on insert
	params.put("modified_by", hotlink.getModifiedBy());
	params.put("modified_date", hotlink.getModifiedDate());
	String sql;
	if (this.isUseNextSequenceOnCreate())
	    sql = StringUtils.replace(createHotlinkSQL, "NEW_INDEX", createHotlinkSequenceNextValue);
	else
	    sql = StringUtils.replace(createHotlinkSQL, "NEW_INDEX", "null");
	getNamedJdbcTemplate().update(sql, params);
    }

    @Override
    public void deleteHotlink(Hotlink hotlink) throws DataAccessException {
	Map<String, Object> params = new HashMap<String, Object>();
	params.put("id", hotlink.getId());
	getNamedJdbcTemplate().update(deleteHotlinkByIdSQL, params);
    }

    @Override
    public void updateHotlink(Hotlink hotlink) throws DataAccessException {
	Map<String, Object> params = new HashMap<String, Object>();
	params.put("user_id", hotlink.getUserId());
	params.put("url_alias", hotlink.getUrlAlias());
	params.put("url", hotlink.getUrl());
	params.put("modified_by", hotlink.getModifiedBy());
	params.put("modified_date", hotlink.getModifiedDate());
	params.put("display_order", hotlink.getDisplayOrder());
	params.put("id", hotlink.getId());
	getNamedJdbcTemplate().update(updateHotlinkSQL, params);
    }

    @Override
    public List<Hotlink> getHotlinks(String userId) throws DataAccessException {
	return getJdbcTemplate().query(getHotlinksByUserIdSQL, new Object[] {userId }, new HotlinkRowMapper());
    }

    @Override
    public Hotlink getHotlink(int hotlinkId) throws DataAccessException {
	try{
	    return getJdbcTemplate().queryForObject(getHotlinkByIdSQL, new Object[] {new Long(hotlinkId) },
		    new HotlinkRowMapper());
	}catch(EmptyResultDataAccessException ex){
	    return null;
	}
    }

    @Override
    public Hotlink findHotlink(String userId, String urlAlias) throws DataAccessException {
	try{
	    return getJdbcTemplate().queryForObject(getHotlinkByUserIdAndUrlAliasSQL, new Object[] {userId, urlAlias },
		    new HotlinkRowMapper());
	}catch(EmptyResultDataAccessException ex){
	    return null;
	}
    }

    class HotlinkRowMapper implements RowMapper<Hotlink> {

	@Override
	public Hotlink mapRow(ResultSet rs, int rowNum) throws SQLException {
	    Hotlink hotlink = new Hotlink();
	    hotlink.setUrlAlias(rs.getString("url_alias"));
	    hotlink.setUrl(rs.getString("url"));
	    hotlink.setId(rs.getLong("id"));
	    hotlink.setUserId(rs.getString("user_id"));
	    hotlink.setDisplayOrder(rs.getInt("display_order"));
	    hotlink.setCreatedBy(rs.getString("created_by"));
	    hotlink.setCreatedDate(rs.getDate("created_date"));
	    hotlink.setModifiedBy(rs.getString("modified_by"));
	    hotlink.setModifiedDate(rs.getDate("modified_date"));
	    return hotlink;
	}
    }
}
