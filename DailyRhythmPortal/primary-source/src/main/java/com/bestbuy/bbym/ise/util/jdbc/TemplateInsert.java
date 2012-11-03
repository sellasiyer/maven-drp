package com.bestbuy.bbym.ise.util.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.bestbuy.bbym.ise.exception.DataAccessException;

public class TemplateInsert<O> extends TemplateOperation {

    private static Logger logger = LoggerFactory.getLogger(TemplateInsert.class);

    public enum InsertIndexMethod {
	DONT_INSERT_INDEX, INSERT_SEQUENCE_NEXTVAL
    }

    private InsertIndexMethod insertIndexMethod = InsertIndexMethod.INSERT_SEQUENCE_NEXTVAL;
    private String addObjectTableAlias;
    private StringBuilder sql;

    public TemplateInsert(JdbcTemplate jdbcTemplate, String schemaName, DataDefMap dataDefMap, Class<?> addObjectClass) {
	this.jdbcTemplate = jdbcTemplate;
	this.schemaName = schemaName;
	this.dataDefMap = dataDefMap;
	if (dataDefMap != null && addObjectClass != null){
	    addObjectTableAlias = dataDefMap.getTableAlias(addObjectClass);
	}
	if (addObjectTableAlias == null){
	    throw new RuntimeException("Failed to determine addObjectTableAlias");
	}
	addTableDataDef(addObjectTableAlias);
    }

    public void insert(O dataObject) throws DataAccessException {
	try{
	    KeyHolder keyHolder = new GeneratedKeyHolder();
	    DataDef dd = tableDataDefMap.get(addObjectTableAlias);
	    baseDataObject = generateBaseDataObject(dd);
	    logger.debug(getSql());
	    dd.setDataObject(dataObject);

	    String[] returnKeys;
	    switch (insertIndexMethod) {
		case INSERT_SEQUENCE_NEXTVAL:
		    returnKeys = new String[1];
		    returnKeys[0] = dd.getFieldName(dd.getPrimaryFieldId());
		    break;
		default:
		    returnKeys = new String[0];
		    break;
	    }

	    PreparedStatementCreator psc = new TemplateStatementCreator(getSql(), varFieldInfoList, dd, returnKeys,
		    changer);
	    jdbcTemplate.update(psc, keyHolder);
	    Long generatedId = new Long(keyHolder.getKey().longValue());
	    dd.setFieldValue(dd.getPrimaryFieldId(), dataObject, generatedId);
	}catch(org.springframework.dao.DataAccessException sdae){
	    logger.error("Exception in insert", sdae);
	    throw createDataAccessException(sdae);
	}
    }

    public void setInsertIndexMethod(InsertIndexMethod insertIndexMethod) {
	this.insertIndexMethod = insertIndexMethod;
    }

    public InsertIndexMethod getInsertIndexMethod() {
	return insertIndexMethod;
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

	DataDef dd = tableDataDefMap.get(addObjectTableAlias);
	sql.append("INSERT INTO ");
	sql.append(getTableSql(dd.getTable()));
	sql.append(" (");

	if (selectVarOutFieldCounter == 0){
	    for(int i = 1; i <= dd.getMaxFieldId(); i++){
		if (i != dd.getPrimaryFieldId()){
		    addVarOutField(addObjectTableAlias, i);
		}
	    }
	}
	FieldInfo fi;
	for(int i = 1; i <= getNumOutFields(); i++){
	    fi = getOutFieldInfo(i);
	    sql.append(dd.getFieldName(fi.getFieldId()));
	    if (i < getNumOutFields()){
		sql.append(", ");
	    }
	}
	if (baseDataObject != null){
	    sql.append(", ");
	    sql.append(baseDataObject.getBaseInsertFieldsSql());
	}

	switch (insertIndexMethod) {
	    case INSERT_SEQUENCE_NEXTVAL:
		sql.append(", ");
		sql.append(dd.getFieldName(dd.getPrimaryFieldId()));
		break;
	}

	sql.append(") VALUES (");

	for(int i = 1; i <= getNumOutFields(); i++){
	    sql.append("?");
	    if (i < getNumOutFields()){
		sql.append(", ");
	    }
	}
	if (baseDataObject != null){
	    sql.append(", ");
	    sql.append(baseDataObject.getBaseInsertParamsSql());
	}

	switch (insertIndexMethod) {
	    case INSERT_SEQUENCE_NEXTVAL:
		sql.append(", ");
		sql.append(getTableSql(dd.getSequenceName()));
		sql.append(".NEXTVAL");
		break;
	}

	sql.append(")");

	return sql.toString();
    }

    @Override
    public boolean showAlias() {
	return false;
    }

}
