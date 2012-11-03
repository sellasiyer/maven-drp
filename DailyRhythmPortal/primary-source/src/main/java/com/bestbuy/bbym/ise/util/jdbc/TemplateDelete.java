package com.bestbuy.bbym.ise.util.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import com.bestbuy.bbym.ise.exception.DataAccessException;

public class TemplateDelete<O> extends TemplateOperation {

    private static Logger logger = LoggerFactory.getLogger(TemplateDelete.class);

    private int indexForBaseDataFields;
    private String deleteObjectTableAlias;
    private StringBuilder sql;

    public TemplateDelete(JdbcTemplate jdbcTemplate, String schemaName, DataDefMap dataDefMap,
	    Class<?> deleteObjectClass) {
	this.jdbcTemplate = jdbcTemplate;
	this.schemaName = schemaName;
	this.dataDefMap = dataDefMap;
	if (dataDefMap != null && deleteObjectClass != null){
	    deleteObjectTableAlias = dataDefMap.getTableAlias(deleteObjectClass);
	}
	if (deleteObjectTableAlias == null){
	    throw new RuntimeException("Failed to determine deleteObjectTableAlias");
	}
	addTableDataDef(deleteObjectTableAlias);
    }

    @Override
    public void resetSql() {
	sql = null;
    }

    @Override
    public String getSql() throws DataAccessException {
	if (sql != null){
	    return sql.toString();
	}
	sql = new StringBuilder();

	DataDef dd = dataDefMap.generateDataDef(deleteObjectTableAlias);
	sql.append("DELETE FROM ");
	sql.append(getTableSql(dd.getTable()));

	if (whereClauseList.isEmpty()){
	    return sql.toString();
	}
	sql.append(" WHERE");

	int i = 0;
	for(WhereClause wc: whereClauseList){
	    i++;
	    sql.append(" ");
	    sql.append(generateWhereClauseSql(wc));
	    if (i < whereClauseList.size()){
		sql.append(" AND");
	    }
	}

	return sql.toString();
    }

    @Override
    public boolean showAlias() {
	return false;
    }

    public int delete(O dataObject) throws DataAccessException {
	try{
	    DataDef dd = tableDataDefMap.get(deleteObjectTableAlias);
	    baseDataObject = generateBaseDataObject(dd);
	    logger.debug(getSql());
	    dd.setDataObject(dataObject);
	    TemplateStatementCreator tsc = new TemplateStatementCreator(getSql(), varFieldInfoList, dd, changer);
	    tsc.setIndexForBaseDataFields(indexForBaseDataFields);
	    return jdbcTemplate.update(tsc);
	}catch(org.springframework.dao.DataAccessException sdae){
	    throw createDataAccessException(sdae);
	}
    }
}
