package com.bestbuy.bbym.ise.util.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import com.bestbuy.bbym.ise.util.jdbc.DataDef.DataType;

@SuppressWarnings("rawtypes")
public class TemplateDataMapper implements ResultSetExtractor, RowMapper {

    private static Logger logger = LoggerFactory.getLogger(TemplateDataMapper.class);

    private DataDef dataDef;
    private TemplateOutFieldMap outFieldMap;

    public TemplateDataMapper(DataDef dataDef, TemplateOutFieldMap outFieldMap) {
	this.dataDef = dataDef;
	this.outFieldMap = outFieldMap;
    }

    @Override
    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
	try{
	    Object dataObject = dataDef.getDataObjectClass().newInstance();
	    Object dataValue;
	    FieldInfo fi;
	    for(int i = 1; i <= outFieldMap.getNumOutFields(); i++){
		fi = outFieldMap.getOutFieldInfo(i);
		dataValue = getFieldValue(rs, dataDef.getFieldDataType(fi.getFieldId()), i);
		dataDef.setFieldValue(fi.getFieldId(), dataObject, dataValue);
	    }
	    if (dataObject instanceof IBaseDataObject){
		IBaseDataObject bdo = (IBaseDataObject) dataObject;
		bdo.outputBaseFieldValues(outFieldMap.getNumOutFields() + 1, rs);
		return bdo;
	    }
	    return dataObject;
	}catch(InstantiationException ie){
	    throw new SQLException(new com.bestbuy.bbym.ise.exception.DataAccessException(
		    "Failed to create new instance of data object", ie));
	}catch(IllegalAccessException iae){
	    throw new SQLException(new com.bestbuy.bbym.ise.exception.DataAccessException(
		    "Failed to create new instance of data object", iae));
	}catch(com.bestbuy.bbym.ise.exception.DataAccessException dae){
	    throw new SQLException(dae);
	}
    }

    @Override
    public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
	if (!rs.next()){
	    return null;
	}
	return mapRow(rs, 1);
    }

    private Object getFieldValue(ResultSet rs, DataType fieldDataType, int idx) throws SQLException {
	try{
	    if (rs.getObject(idx) == null){
		return null;
	    }
	    switch (fieldDataType) {
		case INTEGER:
		    int intVal = rs.getInt(idx);
		    return new Integer(intVal);
		case LONG:
		    long longVal = rs.getLong(idx);
		    return new Long(longVal);
		case STRING:
		    return rs.getString(idx);
		case DATE_TIMESTAMP:
		    return DateUtil.sqlTimestampToUtilDate(rs.getTimestamp(idx));
		case DATE_DATE:
		    return DateUtil.sqlDateToUtilDate(rs.getDate(idx));
		default:
		    throw new com.bestbuy.bbym.ise.exception.DataAccessException("DataType not handled: "
			    + fieldDataType);
	    }
	}catch(com.bestbuy.bbym.ise.exception.DataAccessException dae){
	    throw new SQLException(dae);
	}
    }
}
