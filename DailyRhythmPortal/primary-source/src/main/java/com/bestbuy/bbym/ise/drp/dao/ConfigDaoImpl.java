/**
 * Best Buy Inc (c) 2012
 */
package com.bestbuy.bbym.ise.drp.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.bestbuy.bbym.ise.drp.domain.Config;

/**
 * Implementation of configuration database operations.
 * 
 * @author Yernar Khuangan (a1003810)
 * @version 1.0
 * @since iteration 24
 */
@Repository("configDao")
public class ConfigDaoImpl extends AbstractDao implements ConfigDao {

    private static final String SQL_INSERT_CONFIG = "INSERT INTO BST_ISE_SCH01.CONFIG (config_id,config_typ,param_name,param_val,description,created_by,created_on,amended_by,amended_on) VALUES(BST_ISE_SCH01.CONFIG_ID.nextval,:config_typ,:param_name,:param_val,:description,:created_by,SYSDATE,NULL,NULL)";
    private static final String SQL_UPDATE_CONFIG = "UPDATE BST_ISE_SCH01.CONFIG SET config_typ=:config_typ, param_name=:param_name, param_val=:param_val, description=:description, amended_by=:amended_by, amended_on=SYSDATE WHERE config_id = :config_id";
    private static final String SQL_DELETE_CONFIG = "DELETE FROM BST_ISE_SCH01.CONFIG WHERE config_id = :config_id";
    private static final String SQL_SELECT_CONFIG_BY_TYPE = "SELECT config.* FROM BST_ISE_SCH01.CONFIG config WHERE config_typ=? ORDER BY config.param_name";
    private static final String SQL_SELECT_CONFIG_BY_TYPE_ORDER = "SELECT config.* FROM BST_ISE_SCH01.CONFIG config WHERE config_typ=? ORDER BY ";
    private static final String SQL_SELECT_CONFIG_BY_TYPE_PARAM_NAME = "SELECT config.* FROM BST_ISE_SCH01.CONFIG config WHERE config_typ=? and param_name=?";
    private static final String SQL_SELECT_CONFIG_BY_TYPE_PARAM_VALUE = "SELECT config.* FROM BST_ISE_SCH01.CONFIG config WHERE config_typ=? and param_val=?";
    private static final String SQL_SELECT_CONFIG_BY_ID = "SELECT config.* from BST_ISE_SCH01.CONFIG config WHERE config_id = ?";

    @Override
    public Config persist(Config config) {
	KeyHolder keyHolder = new GeneratedKeyHolder();
	getNamedJdbcTemplate().update(SQL_INSERT_CONFIG, buildParams(config), keyHolder, new String[] {"config_id" });
	config.setConfigId(keyHolder.getKey().longValue());
	return config;
    }

    private SqlParameterSource buildParams(Config config) {
	MapSqlParameterSource params = new MapSqlParameterSource();
	params.addValue("config_id", config.getConfigId());
	params.addValue("config_typ", config.getConfigType());
	params.addValue("param_name", config.getParamName());
	params.addValue("param_val", config.getParamValue());
	params.addValue("description", config.getDescription());
	params.addValue("created_by", config.getCreatedBy());
	params.addValue("created_on", config.getCreatedOn());
	params.addValue("amended_by", config.getModifiedBy());
	params.addValue("amended_on", config.getModifiedOn());
	return params;
    }

    @Override
    public Config update(Config config) {
	getNamedJdbcTemplate().update(SQL_UPDATE_CONFIG, buildParams(config));
	return config;
    }

    @Override
    public void delete(Config config) {
	getNamedJdbcTemplate().update(SQL_DELETE_CONFIG, buildParams(config));
    }

    @Override
    public List<Config> getConfigItemsByType(String configType) {
	return getJdbcTemplate().query(SQL_SELECT_CONFIG_BY_TYPE, new Object[] {configType }, new ConfigRowMapper());
    }

    @Override
    public List<Config> getConfigItemsByType(String configType, boolean orderByValue) {
	String orderBy = "config.param_name";
	if (orderByValue){
	    orderBy = "config.param_val";
	}
	return getJdbcTemplate().query(SQL_SELECT_CONFIG_BY_TYPE_ORDER + orderBy, new Object[] {configType },
		new ConfigRowMapper());
    }

    class ConfigRowMapper implements RowMapper<Config> {

	@Override
	public Config mapRow(ResultSet rs, int rowNum) throws SQLException {
	    Config config = new Config();
	    config.setConfigId(rs.getLong("config_id"));
	    config.setConfigType(rs.getString("config_typ"));
	    config.setParamName(rs.getString("param_name"));
	    config.setParamValue(rs.getString("param_val"));
	    config.setDescription(rs.getString("description"));
	    config.setCreatedBy(rs.getString("created_by"));
	    config.setCreatedOn(rs.getDate("created_on"));
	    config.setModifiedBy(rs.getString("amended_by"));
	    config.setModifiedOn(rs.getDate("amended_on"));
	    return config;
	}
    }

    @Override
    public List<Config> getConfigItemsByTypeAndParamName(String configType, String paramName) {
	return getJdbcTemplate().query(SQL_SELECT_CONFIG_BY_TYPE_PARAM_NAME, new Object[] {configType, paramName },
		new ConfigRowMapper());
    }

    @Override
    public List<Config> getConfigItemsByTypeAndParamValue(String configType, String paramValue) {
	return getJdbcTemplate().query(SQL_SELECT_CONFIG_BY_TYPE_PARAM_VALUE, new Object[] {configType, paramValue },
		new ConfigRowMapper());
    }

    @Override
    public Config getConfigItemById(String configId) {

	List<Config> list = getJdbcTemplate().query(SQL_SELECT_CONFIG_BY_ID, new Object[] {configId },
		new ConfigRowMapper());
	if (list.size() > 0){
	    return list.get(0);
	}
	return null;
    }
}
