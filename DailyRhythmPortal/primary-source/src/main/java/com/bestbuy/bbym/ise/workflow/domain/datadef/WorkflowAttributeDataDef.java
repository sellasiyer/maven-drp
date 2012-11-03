package com.bestbuy.bbym.ise.workflow.domain.datadef;

import com.bestbuy.bbym.ise.exception.DataAccessException;
import com.bestbuy.bbym.ise.util.jdbc.DataDef;
import com.bestbuy.bbym.ise.workflow.domain.WorkflowAttribute;

public class WorkflowAttributeDataDef extends DataDef {

    public final static int F_ID = 1;
    public final static int F_NAME = 2;
    public final static int F_VALUE = 3;
    public final static int F_STEP_ID = 4;

    @Override
    public int getMaxFieldId() {
	return 4;
    }

    @Override
    public String getTable() {
	return "workflow_attribute_hist";
    }

    @Override
    public String getSequenceName() {
	return "workflow_attribute_seq";
    }

    @Override
    public String getFieldName(int fieldId) throws DataAccessException {
	validateFieldId(fieldId);
	switch (fieldId) {
	    case F_ID:
		return "wf_attribute_hist_id";
	    case F_NAME:
		return "name";
	    case F_VALUE:
		return "val";
	    case F_STEP_ID:
		return "wf_step_hist_id";
	}
	return null;
    }

    @Override
    public Object getFieldValue(int fieldId, Object dataObject) throws DataAccessException {
	validateFieldId(fieldId);
	if (dataObject == null){
	    return null;
	}
	WorkflowAttribute wfAttribute = (WorkflowAttribute) dataObject;
	switch (fieldId) {
	    case F_ID:
		return new Long(wfAttribute.getId());
	    case F_NAME:
		return wfAttribute.getName();
	    case F_VALUE:
		return wfAttribute.getValue();
	    case F_STEP_ID:
		return new Long(wfAttribute.getStepId());
	}
	return null;
    }

    @Override
    public void setFieldValue(int fieldId, Object dataObject, Object dataValue) throws DataAccessException {
	validateFieldId(fieldId);
	if (dataObject == null){
	    return;
	}
	WorkflowAttribute wfAttribute = (WorkflowAttribute) dataObject;
	switch (fieldId) {
	    case F_ID:
		wfAttribute.setId(getLong(dataValue));
		break;
	    case F_NAME:
		wfAttribute.setName(getString(dataValue));
		break;
	    case F_VALUE:
		wfAttribute.setValue(getString(dataValue));
		break;
	    case F_STEP_ID:
		wfAttribute.setStepId(getLong(dataValue));
		break;
	}
    }

    @Override
    public DataType getFieldDataType(int fieldId) throws DataAccessException {
	validateFieldId(fieldId);
	switch (fieldId) {
	    case F_ID:
		return DataType.LONG;
	    case F_NAME:
		return DataType.STRING;
	    case F_VALUE:
		return DataType.STRING;
	    case F_STEP_ID:
		return DataType.LONG;
	}
	return null;
    }

    @Override
    public int getPrimaryFieldId() {
	return F_ID;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public Class getDataObjectClass() {
	return WorkflowAttribute.class;
    }
}
