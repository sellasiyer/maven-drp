package com.bestbuy.bbym.ise.util.jdbc;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import com.bestbuy.bbym.ise.exception.DataAccessException;

public class TemplateUpdate<O> extends TemplateOperation {

    private static Logger logger = LoggerFactory.getLogger(TemplateUpdate.class);

    private int indexForBaseDataFields;
    private String editObjectTableAlias;
    private StringBuilder sql;

    public TemplateUpdate(JdbcTemplate jdbcTemplate, String schemaName, DataDefMap dataDefMap, Class<?> editObjectClass) {
	this.jdbcTemplate = jdbcTemplate;
	this.schemaName = schemaName;
	this.dataDefMap = dataDefMap;
	if (dataDefMap != null && editObjectClass != null){
	    editObjectTableAlias = dataDefMap.getTableAlias(editObjectClass);
	}
	if (editObjectTableAlias == null){
	    throw new RuntimeException("Failed to determine editObjectTableAlias");
	}
	addTableDataDef(editObjectTableAlias);
    }

    public int update(O dataObject) throws DataAccessException {
	try{
	    DataDef dd = tableDataDefMap.get(editObjectTableAlias);
	    baseDataObject = generateBaseDataObject(dd);
	    logger.debug(getSql());
	    dd.setDataObject(dataObject);
	    TemplateStatementCreator tsc = new TemplateStatementCreator(getSql(), varFieldInfoList, dd, changer);
	    tsc.setIndexForBaseDataFields(indexForBaseDataFields);
	    return jdbcTemplate.update(tsc);
	}catch(org.springframework.dao.DataAccessException sdae){
	    logger.error("Exception in update", sdae);
	    throw createDataAccessException(sdae);
	}
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

	DataDef dd = tableDataDefMap.get(editObjectTableAlias);
	sql.append("UPDATE ");
	sql.append(getTableSql(dd.getTable()));
	sql.append(" SET ");

	// Have to save where clause (and select update vars) data stored in
	// varFieldInfoList
	List<FieldInfo> initialFieldInfoList = new ArrayList<FieldInfo>();
	FieldInfo fi;
	if (!varFieldInfoList.isEmpty()){
	    for(FieldInfo fieldInfo: varFieldInfoList){
		fi = new FieldInfo();
		fi.setFieldId(fieldInfo.getFieldId());
		fi.setTableAlias(fieldInfo.getTableAlias());
		initialFieldInfoList.add(fi);
	    }
	    varFieldInfoList.clear();
	}
	if (!outFieldsMap.isEmpty()){
	    outFieldsMap.clear();
	}

	if (selectVarOutFieldCounter > 0){
	    // set the selected update vars
	    for(int i = 0; i < selectVarOutFieldCounter; i++){
		fi = initialFieldInfoList.get(i);
		addVarOutField(fi.getTableAlias(), fi.getFieldId());
	    }
	    indexForBaseDataFields = varFieldInfoList.size();

	    // set the vars for the where clauses
	    for(int i = selectVarOutFieldCounter; i < initialFieldInfoList.size(); i++){
		fi = initialFieldInfoList.get(i);
		fi.setIndex(varFieldInfoList.size() + 1);
		varFieldInfoList.add(fi);
	    }
	}else{
	    // Add all object vars as input (except for ID)
	    for(int i = 1; i <= dd.getMaxFieldId(); i++){
		if (i != dd.getPrimaryFieldId()){
		    addVarOutField(editObjectTableAlias, i);
		}
	    }
	    indexForBaseDataFields = varFieldInfoList.size();

	    // set the vars for the where clauses
	    for(FieldInfo fieldInfo: initialFieldInfoList){
		fieldInfo.setIndex(varFieldInfoList.size() + 1);
		varFieldInfoList.add(fieldInfo);
	    }
	}

	for(int i = 1; i <= getNumOutFields(); i++){
	    fi = getOutFieldInfo(i);
	    sql.append(dd.getFieldName(fi.getFieldId()));
	    sql.append("=?");
	    if (i < getNumOutFields()){
		sql.append(", ");
	    }
	}
	if (baseDataObject != null){
	    sql.append(", ");
	    sql.append(baseDataObject.getBaseUpdateFieldsSql());
	}

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

}
