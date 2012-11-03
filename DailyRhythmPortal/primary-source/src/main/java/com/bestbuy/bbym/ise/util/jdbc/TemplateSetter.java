package com.bestbuy.bbym.ise.util.jdbc;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.PreparedStatementSetter;

import com.bestbuy.bbym.ise.exception.DataAccessException;

public class TemplateSetter implements PreparedStatementSetter {

    private static Logger logger = LoggerFactory.getLogger(TemplateSetter.class);

    private List<FieldInfo> inFieldInfoList;
    private Map<String, DataDef> tableDataDefMap;
    private InputDataMap inputDataMap;

    public TemplateSetter(List<FieldInfo> inFieldInfoList, InputDataMap inputDataMap,
	    Map<String, DataDef> tableDataDefMap) {
	this.inFieldInfoList = inFieldInfoList;
	this.tableDataDefMap = tableDataDefMap;
	this.inputDataMap = inputDataMap;
    }

    @Override
    public void setValues(PreparedStatement ps) throws SQLException {
	if (inFieldInfoList == null || inFieldInfoList.isEmpty()){
	    return;
	}
	for(FieldInfo fi: inFieldInfoList){
	    setFieldValue(ps, fi);
	}
    }

    private void setFieldValue(PreparedStatement ps, FieldInfo fi) throws SQLException {
	try{
	    Object dataValue = inputDataMap.get(fi.getIndex());
	    DataDef dd = tableDataDefMap.get(fi.getTableAlias());
	    DataDef.DataType dt = dd.getFieldDataType(fi.getFieldId());
	    TemplateOperation.setValue(ps, dt, fi.getIndex(), dataValue);
	}catch(DataAccessException dae){
	    throw new SQLException(dae);
	}
    }
}
