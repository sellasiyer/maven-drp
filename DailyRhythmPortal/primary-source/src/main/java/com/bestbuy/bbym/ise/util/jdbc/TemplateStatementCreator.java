package com.bestbuy.bbym.ise.util.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.PreparedStatementCreator;

import com.bestbuy.bbym.ise.domain.User;
import com.bestbuy.bbym.ise.exception.DataAccessException;

public class TemplateStatementCreator implements PreparedStatementCreator {

    private static Logger logger = LoggerFactory.getLogger(TemplateStatementCreator.class);

    private String sql;
    private List<FieldInfo> varFieldInfoList;
    private DataDef addEditObjectDataDef;
    private String[] returnKeys = null;
    private User changer;
    private int indexForBaseDataFields = -1;

    public TemplateStatementCreator(String sql, List<FieldInfo> varFieldInfoList, DataDef addEditObjectDataDef,
	    User changer) {
	this.sql = sql;
	this.varFieldInfoList = varFieldInfoList;
	this.addEditObjectDataDef = addEditObjectDataDef;
	this.changer = changer;
    }

    public TemplateStatementCreator(String sql, List<FieldInfo> varFieldInfoList, DataDef addEditObjectDataDef,
	    String[] returnKeys, User changer) {
	this(sql, varFieldInfoList, addEditObjectDataDef, changer);
	this.returnKeys = returnKeys;
    }

    @Override
    public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
	PreparedStatement ps;
	if (returnKeys != null && returnKeys.length > 0){
	    ps = connection.prepareStatement(sql, returnKeys);
	}else if (returnKeys != null && returnKeys.length == 0){
	    ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
	}else{
	    ps = connection.prepareStatement(sql);
	}
	IBaseDataObject bdo;
	int idx = 0, addedFields = 0;
	for(FieldInfo fi: varFieldInfoList){
	    idx++;
	    setFieldValue(ps, fi, fi.getIndex() + addedFields);

	    if ((addEditObjectDataDef.getDataObject() instanceof IBaseDataObject) && idx == indexForBaseDataFields){
		bdo = (IBaseDataObject) addEditObjectDataDef.getDataObject();
		bdo.inputBaseUpdateFieldValues(idx + 1, ps, changer);
		addedFields = bdo.getNumBaseUpdateFields();
		idx += addedFields;
	    }
	}
	idx++;
	if ((addEditObjectDataDef.getDataObject() instanceof IBaseDataObject) && indexForBaseDataFields == -1){
	    bdo = (IBaseDataObject) addEditObjectDataDef.getDataObject();
	    bdo.inputBaseInsertFieldValues(idx, ps, changer);
	    idx += bdo.getNumBaseInsertFields();
	}
	return ps;
    }

    private void setFieldValue(PreparedStatement ps, FieldInfo fi, int idx) throws SQLException {
	try{
	    Object dataValue = addEditObjectDataDef
		    .getFieldValue(fi.getFieldId(), addEditObjectDataDef.getDataObject());
	    DataDef.DataType dt = addEditObjectDataDef.getFieldDataType(fi.getFieldId());
	    TemplateOperation.setValue(ps, dt, idx, dataValue);
	}catch(DataAccessException dae){
	    throw new SQLException(dae);
	}
    }

    public void setIndexForBaseDataFields(int indexForBaseDataFields) {
	this.indexForBaseDataFields = indexForBaseDataFields;
    }

    public int getIndexForBaseDataFields() {
	return indexForBaseDataFields;
    }
}
