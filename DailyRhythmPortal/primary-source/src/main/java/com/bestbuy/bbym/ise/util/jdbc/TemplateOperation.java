package com.bestbuy.bbym.ise.util.jdbc;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import com.bestbuy.bbym.ise.domain.User;
import com.bestbuy.bbym.ise.exception.DataAccessException;
import com.bestbuy.bbym.ise.util.jdbc.WhereClause.OperatorType;

public abstract class TemplateOperation implements TemplateOutFieldMap {

    private static Logger logger = LoggerFactory.getLogger(TemplateOperation.class);

    protected Map<String, DataDef> tableDataDefMap = new HashMap<String, DataDef>();
    protected List<WhereClause> whereClauseList = new ArrayList<WhereClause>();
    protected List<FieldInfo> varFieldInfoList = new ArrayList<FieldInfo>();
    protected Map<Integer, FieldInfo> outFieldsMap = new HashMap<Integer, FieldInfo>();
    protected JdbcTemplate jdbcTemplate;
    protected User changer;
    protected String schemaName;
    protected DataDefMap dataDefMap;
    protected IBaseDataObject baseDataObject;
    protected int selectVarOutFieldCounter = 0;

    public abstract void resetSql();

    public abstract String getSql() throws DataAccessException;

    public abstract boolean showAlias();

    public void addTableDataDef(String tableAlias) {
	if (tableDataDefMap.containsKey(tableAlias)){
	    return;
	}
	tableDataDefMap.put(tableAlias, dataDefMap.generateDataDef(tableAlias));
    }

    public int addWhere(OperatorType operatorType, Class<?> dataObjectClass, int dataFieldId) {
	String tableAlias = dataDefMap.getTableAlias(dataObjectClass);
	addTableDataDef(tableAlias);
	WhereClause wc = new WhereClause();
	wc.setOperatorType(operatorType);
	FieldInfo fi1 = new FieldInfo(dataFieldId, tableAlias, WhereClause.ParamType.EXPRESSION);
	wc.setFieldInfo1(fi1);
	FieldInfo fi2 = new FieldInfo(dataFieldId, tableAlias, WhereClause.ParamType.OBJECT_VALUE);
	wc.setFieldInfo2(fi2);
	wc.setShowAlias(showAlias());
	whereClauseList.add(wc);
	fi2.setIndex(varFieldInfoList.size() + 1);
	varFieldInfoList.add(fi2);
	return varFieldInfoList.size();
    }

    public int addWhere(OperatorType operatorType, Class<?> dataObjectClass1, int dataFieldId1,
	    Class<?> dataObjectClass2, int dataFieldId2) {
	String tableAlias1 = dataDefMap.getTableAlias(dataObjectClass1);
	addTableDataDef(tableAlias1);
	String tableAlias2 = dataDefMap.getTableAlias(dataObjectClass2);
	addTableDataDef(tableAlias2);
	WhereClause wc = new WhereClause();
	wc.setOperatorType(operatorType);
	FieldInfo fi1 = new FieldInfo(dataFieldId1, tableAlias1, WhereClause.ParamType.EXPRESSION);
	wc.setFieldInfo1(fi1);
	FieldInfo fi2 = new FieldInfo(dataFieldId2, tableAlias2, WhereClause.ParamType.EXPRESSION);
	wc.setFieldInfo2(fi2);
	wc.setShowAlias(showAlias());
	whereClauseList.add(wc);
	return 0;
    }

    protected String generateWhereClauseSql(WhereClause whereClause) throws DataAccessException {
	StringBuilder sb = new StringBuilder();
	FieldInfo fi1 = whereClause.getFieldInfo1();
	switch (fi1.getParamType()) {
	    case EXPRESSION:
		sb.append(createInParamSql(fi1, whereClause.isShowAlias()));
		break;
	    case OBJECT_VALUE:
		sb.append("?");
		break;
	}
	sb.append(" ");
	sb.append(whereClause.getOperatorSql());
	sb.append(" ");
	FieldInfo fi2 = whereClause.getFieldInfo2();
	switch (fi2.getParamType()) {
	    case EXPRESSION:
		sb.append(createInParamSql(fi2, whereClause.isShowAlias()));
		break;
	    case OBJECT_VALUE:
		sb.append("?");
		break;
	}
	return sb.toString();
    }

    private String createInParamSql(FieldInfo fieldInfo, boolean showAlias) throws DataAccessException {
	DataDef dd = dataDefMap.generateDataDef(fieldInfo.getTableAlias());
	if (!showAlias){
	    return dd.getFieldName(fieldInfo.getFieldId());
	}
	return fieldInfo.getTableAlias() + "." + dd.getFieldName(fieldInfo.getFieldId());
    }

    public void addSelectOutField(Class<?> dataObjectClass, int dataFieldId) {
	addOutField(dataDefMap.getTableAlias(dataObjectClass), dataFieldId);
    }

    @Override
    public int getNumOutFields() {
	return outFieldsMap.size();
    }

    protected void addOutField(String tableAlias, int fieldId) {
	Integer outFieldId = new Integer(outFieldsMap.size() + 1);
	FieldInfo fi = new FieldInfo();
	fi.setFieldId(fieldId);
	fi.setTableAlias(tableAlias);
	outFieldsMap.put(outFieldId, fi);
    }

    public void addSelectVarOutField(Class<?> dataObjectClass, int dataFieldId) {
	selectVarOutFieldCounter++;
	addVarOutField(dataDefMap.getTableAlias(dataObjectClass), dataFieldId);
    }

    protected void addVarOutField(String tableAlias, int fieldId) {
	addOutField(tableAlias, fieldId);
	FieldInfo fi = new FieldInfo();
	fi.setFieldId(fieldId);
	fi.setTableAlias(tableAlias);
	fi.setIndex(varFieldInfoList.size() + 1);
	varFieldInfoList.add(fi);
    }

    @Override
    public FieldInfo getOutFieldInfo(int outFieldId) throws DataAccessException {
	FieldInfo fi = outFieldsMap.get(new Integer(outFieldId));
	if (fi == null){
	    throw new DataAccessException("Bad out field ID value: " + outFieldId);
	}
	return fi;
    }

    protected DataAccessException createDataAccessException(org.springframework.dao.DataAccessException sdae)
	    throws DataAccessException {
	if (sdae.getCause() != null && (sdae.getCause() instanceof SQLException)){
	    if (sdae.getCause().getCause() != null && (sdae.getCause().getCause() instanceof DataAccessException)){
		return new DataAccessException(sdae.getCause().getCause().getMessage(), sdae.getCause().getCause()
			.getCause());
	    }
	}
	return new DataAccessException(sdae);
    }

    public static void setValue(PreparedStatement ps, DataDef.DataType dataType, int index, Object dataValue)
	    throws SQLException {
	switch (dataType) {
	    case INTEGER:
		if (dataValue == null){
		    logger.debug("int param #" + index + "=null");
		    ps.setNull(index, java.sql.Types.INTEGER);
		    return;
		}
		if (!(dataValue instanceof Integer)){
		    throw new SQLException("Expected input param #" + index + " to be Integer");
		}
		Integer intVal = (Integer) dataValue;
		logger.debug("int param #" + index + "=" + intVal);
		ps.setInt(index, intVal.intValue());
		break;
	    case LONG:
		if (dataValue == null){
		    logger.debug("long param #" + index + "=null");
		    ps.setNull(index, java.sql.Types.BIGINT);
		    return;
		}
		if (!(dataValue instanceof Long)){
		    throw new SQLException("Expected input param #" + index + " to be Long");
		}
		Long longVal = (Long) dataValue;
		logger.debug("long param #" + index + "=" + longVal);
		ps.setLong(index, longVal.longValue());
		break;
	    case STRING:
		if (dataValue == null){
		    logger.debug("string param #" + index + "=null");
		    ps.setNull(index, java.sql.Types.VARCHAR);
		    return;
		}
		if (!(dataValue instanceof String)){
		    throw new SQLException("Expected input param #" + index + " to be String");
		}
		logger.debug("string param #" + index + "=" + (String) dataValue);
		ps.setString(index, (String) dataValue);
		break;
	    case DATE_TIMESTAMP:
		if (dataValue == null){
		    logger.debug("date param #" + index + "=null");
		    ps.setNull(index, java.sql.Types.TIMESTAMP);
		    return;
		}
		if (!(dataValue instanceof Date)){
		    throw new SQLException("Expected input param #" + index + " to be Date");
		}
		logger.debug("date param #" + index + "=" + (Date) dataValue);
		ps.setTimestamp(index, DateUtil.utilDateToSqlTimestamp((Date) dataValue));
		break;
	    case DATE_DATE:
		if (dataValue == null){
		    logger.debug("date param #" + index + "=null");
		    ps.setNull(index, java.sql.Types.DATE);
		    return;
		}
		if (!(dataValue instanceof Date)){
		    throw new SQLException("Expected input param #" + index + " to be Date");
		}
		logger.debug("date param #" + index + "=" + (Date) dataValue);
		ps.setDate(index, DateUtil.utilDateToSqlDate((Date) dataValue));
		break;
	    default:
		throw new SQLException("DataType not handled: " + dataType);
	}
    }

    public void setChanger(User changer) {
	this.changer = changer;
    }

    public IBaseDataObject generateBaseDataObject(DataDef dd) {
	try{
	    Object dataObject = dd.getDataObjectClass().newInstance();
	    if (dataObject instanceof IBaseDataObject){
		return (IBaseDataObject) dataObject;
	    }
	}catch(InstantiationException e){
	    logger.warn("Failed to create new instance of data object", e);
	}catch(IllegalAccessException e){
	    logger.warn("Failed to create new instance of data object", e);
	}
	return null;
    }

    public String getTableSql(String tableName) {
	if (schemaName == null){
	    return tableName;
	}
	return schemaName + "." + tableName;
    }
}
