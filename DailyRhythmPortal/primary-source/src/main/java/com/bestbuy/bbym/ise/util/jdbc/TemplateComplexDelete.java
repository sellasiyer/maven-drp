package com.bestbuy.bbym.ise.util.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;

import com.bestbuy.bbym.ise.exception.DataAccessException;

public class TemplateComplexDelete<O> extends TemplateSelect<O> {

    private static Logger logger = LoggerFactory.getLogger(TemplateComplexDelete.class);

    private StringBuilder sql;

    public TemplateComplexDelete(JdbcTemplate jdbcTemplate, String schemaName, DataDefMap dataDefMap,
	    Class<?> deleteObjectClass) {
	super(jdbcTemplate, schemaName, dataDefMap, deleteObjectClass);
    }

    @Override
    public void resetSql() {
	super.resetSql();
	sql = null;
    }

    @Override
    public String getSql() throws DataAccessException {
	if (sql != null){
	    return sql.toString();
	}
	sql = new StringBuilder();

	DataDef dd = dataDefMap.generateDataDef(returnObjectTableAlias);
	sql.append("DELETE FROM ");
	sql.append(getTableSql(dd.getTable()));
	sql.append(" ");
	sql.append(returnObjectTableAlias);
	sql.append(" WHERE EXISTS (");
	sql.append(super.getSql());
	sql.append(")");
	return sql.toString();
    }

    protected boolean skipTableAliasInFrom(String tableAlias) {
	return returnObjectTableAlias != null && returnObjectTableAlias.equals(tableAlias);
    }

    public int delete(InputDataMap inputDataMap) throws DataAccessException {
	try{
	    logger.debug(getSql());
	    PreparedStatementSetter pss = new TemplateSetter(varFieldInfoList, inputDataMap, tableDataDefMap);
	    return jdbcTemplate.update(getSql(), pss);
	}catch(org.springframework.dao.DataAccessException sdae){
	    throw createDataAccessException(sdae);
	}
    }
}
