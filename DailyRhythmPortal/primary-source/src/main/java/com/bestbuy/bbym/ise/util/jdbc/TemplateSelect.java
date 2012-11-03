package com.bestbuy.bbym.ise.util.jdbc;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import com.bestbuy.bbym.ise.exception.DataAccessException;

public class TemplateSelect<O> extends TemplateOperation {

    private static Logger logger = LoggerFactory.getLogger(TemplateSelect.class);

    protected List<OrderByInfo> orderByInfoList = new ArrayList<OrderByInfo>();
    protected String returnObjectTableAlias;
    private StringBuilder sql;
    private boolean selectAllPrimaryObjectFields = true;

    public TemplateSelect(JdbcTemplate jdbcTemplate, String schemaName, DataDefMap dataDefMap,
	    Class<?> returnObjectClass) {
	this.jdbcTemplate = jdbcTemplate;
	this.schemaName = schemaName;
	this.dataDefMap = dataDefMap;
	if (dataDefMap != null && returnObjectClass != null){
	    returnObjectTableAlias = dataDefMap.getTableAlias(returnObjectClass);
	}
	if (returnObjectTableAlias == null){
	    throw new RuntimeException("Failed to determine returnObjectTableAlias");
	}
	addTableDataDef(returnObjectTableAlias);
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

	sql.append("SELECT ");
	sql.append(createOutParamsSql());
	sql.append(" FROM ");

	DataDef dd;
	boolean firstOne = true;
	for(String tableAlias: tableDataDefMap.keySet()){
	    if (skipTableAliasInFrom(tableAlias)){
		continue;
	    }
	    if (!firstOne){
		sql.append(", ");
	    }
	    firstOne = false;
	    dd = dataDefMap.generateDataDef(tableAlias);
	    sql.append(getTableSql(dd.getTable()));
	    sql.append(" ");
	    sql.append(tableAlias);
	}

	if (!whereClauseList.isEmpty()){
	    sql.append(" WHERE ");
	    firstOne = true;
	    for(WhereClause wc: whereClauseList){
		if (!firstOne){
		    sql.append(" AND ");
		}
		firstOne = false;
		sql.append(generateWhereClauseSql(wc));
	    }
	}

	if (!orderByInfoList.isEmpty()){
	    sql.append(" ORDER BY ");
	    firstOne = true;
	    for(OrderByInfo obi: orderByInfoList){
		if (!firstOne){
		    sql.append(", ");
		}
		firstOne = false;
		sql.append(generateOrderBySql(obi));
	    }
	}

	return sql.toString();
    }

    @Override
    public boolean showAlias() {
	return true;
    }

    protected boolean skipTableAliasInFrom(String tableAlias) {
	return false;
    }

    private String createOutParamsSql() throws DataAccessException {
	FieldInfo fi;
	DataDef dd = dataDefMap.generateDataDef(returnObjectTableAlias);
	if (isSelectAllPrimaryObjectFields()){
	    for(int i = 1; i <= dd.getMaxFieldId(); i++){
		addOutField(returnObjectTableAlias, i);
	    }
	}
	boolean firstOne = true;
	StringBuilder sb = new StringBuilder();
	for(int i = 1; i <= getNumOutFields(); i++){
	    if (!firstOne){
		sb.append(", ");
	    }
	    firstOne = false;
	    fi = getOutFieldInfo(i);
	    sb.append(fi.getTableAlias() + "." + dd.getFieldName(fi.getFieldId()));
	}
	if (baseDataObject != null){
	    if (!firstOne){
		sb.append(", ");
	    }
	    firstOne = false;
	    sb.append(baseDataObject.getBaseOutFieldsSql(returnObjectTableAlias));
	}
	return sb.toString();
    }

    @SuppressWarnings( {"unchecked", "rawtypes" })
    public O selectObject(InputDataMap inputDataMap) throws DataAccessException {
	try{
	    DataDef dd = tableDataDefMap.get(returnObjectTableAlias);
	    baseDataObject = generateBaseDataObject(dd);
	    logger.debug(getSql());
	    PreparedStatementSetter pss = new TemplateSetter(varFieldInfoList, inputDataMap, tableDataDefMap);
	    ResultSetExtractor rse = new TemplateDataMapper(dd, this);
	    return (O) jdbcTemplate.query(getSql(), pss, rse);
	}catch(org.springframework.dao.DataAccessException sdae){
	    logger.error("Exception in selectObject", sdae);
	    throw createDataAccessException(sdae);
	}
    }

    @SuppressWarnings( {"unchecked", "rawtypes" })
    public List<O> selectList(InputDataMap inputDataMap) throws DataAccessException {
	try{
	    DataDef dd = tableDataDefMap.get(returnObjectTableAlias);
	    baseDataObject = generateBaseDataObject(dd);
	    logger.debug(getSql());
	    PreparedStatementSetter pss = new TemplateSetter(varFieldInfoList, inputDataMap, tableDataDefMap);
	    RowMapper rm = new TemplateDataMapper(dd, this);
	    return jdbcTemplate.query(getSql(), pss, rm);
	}catch(org.springframework.dao.DataAccessException sdae){
	    logger.error("Exception in selectList", sdae);
	    throw createDataAccessException(sdae);
	}
    }

    public void addOrderBy(Class<?> dataObjectClass, int dataFieldId, boolean ascending) {
	OrderByInfo orderByInfo = new OrderByInfo();
	orderByInfo.setTableAlias(dataDefMap.getTableAlias(dataObjectClass));
	orderByInfo.setFieldId(dataFieldId);
	orderByInfo.setAscending(ascending);
	orderByInfoList.add(orderByInfo);
    }

    private String generateOrderBySql(OrderByInfo orderByInfo) throws DataAccessException {
	String direction = " DESC";
	if (orderByInfo.isAscending()){
	    direction = " ASC";
	}
	DataDef dd = dataDefMap.generateDataDef(orderByInfo.getTableAlias());
	return orderByInfo.getTableAlias() + "." + dd.getFieldName(orderByInfo.getFieldId()) + direction;
    }

    public void setSelectAllPrimaryObjectFields(boolean selectAllPrimaryObjectFields) {
	this.selectAllPrimaryObjectFields = selectAllPrimaryObjectFields;
    }

    public boolean isSelectAllPrimaryObjectFields() {
	return selectAllPrimaryObjectFields;
    }
}
