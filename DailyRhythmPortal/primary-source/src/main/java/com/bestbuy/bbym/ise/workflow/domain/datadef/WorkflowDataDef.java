package com.bestbuy.bbym.ise.workflow.domain.datadef;

import com.bestbuy.bbym.ise.domain.Customer;
import com.bestbuy.bbym.ise.exception.DataAccessException;
import com.bestbuy.bbym.ise.util.jdbc.DataDef;
import com.bestbuy.bbym.ise.workflow.domain.Workflow;

public class WorkflowDataDef extends DataDef {

    public final static int F_ID = 1;
    public final static int F_CUSTOMER_FIRST_NAME = 2;
    public final static int F_CUSTOMER_LAST_NAME = 3;
    public final static int F_COMMENT = 4;
    public final static int F_TYPE = 5;
    public final static int F_START_TIME = 6;
    public final static int F_END_TIME = 7;
    public final static int F_STATUS = 8;

    @Override
    public int getMaxFieldId() {
	return 8;
    }

    @Override
    public String getTable() {
	return "workflow";
    }

    @Override
    public String getSequenceName() {
	return "workflow_seq";
    }

    @Override
    public String getFieldName(int fieldId) throws DataAccessException {
	validateFieldId(fieldId);
	switch (fieldId) {
	    case F_ID:
		return "workflow_id";
	    case F_CUSTOMER_FIRST_NAME:
		return "cust_first_name";
	    case F_CUSTOMER_LAST_NAME:
		return "cust_last_name";
	    case F_COMMENT:
		return "wf_comment";
	    case F_TYPE:
		return "work_flow_typ";
	    case F_START_TIME:
		return "start_tm";
	    case F_END_TIME:
		return "end_tm";
	    case F_STATUS:
		return "status";
	}
	return null;
    }

    @Override
    public Object getFieldValue(int fieldId, Object dataObject) throws DataAccessException {
	validateFieldId(fieldId);
	if (dataObject == null){
	    return null;
	}
	Workflow wf = (Workflow) dataObject;
	switch (fieldId) {
	    case F_ID:
		return new Long(wf.getId());
	    case F_CUSTOMER_FIRST_NAME:
		if (wf.getCustomer() != null){
		    return wf.getCustomer().getFirstName();
		}
		break;
	    case F_CUSTOMER_LAST_NAME:
		if (wf.getCustomer() != null){
		    return wf.getCustomer().getLastName();
		}
		break;
	    case F_COMMENT:
		return wf.getComment();
	    case F_TYPE:
		return wf.getType();
	    case F_START_TIME:
		return wf.getStartTime();
	    case F_END_TIME:
		return wf.getEndTime();
	    case F_STATUS:
		return wf.getStatus();
	}
	return null;
    }

    @Override
    public void setFieldValue(int fieldId, Object dataObject, Object dataValue) throws DataAccessException {
	validateFieldId(fieldId);
	if (dataObject == null){
	    return;
	}
	Workflow wf = (Workflow) dataObject;
	switch (fieldId) {
	    case F_ID:
		wf.setId(getLong(dataValue));
		break;
	    case F_CUSTOMER_FIRST_NAME:
		if (dataValue == null){
		    return;
		}
		if (wf.getCustomer() == null){
		    wf.setCustomer(new Customer());
		}
		wf.getCustomer().setFirstName(getString(dataValue));
		break;
	    case F_CUSTOMER_LAST_NAME:
		if (dataValue == null){
		    return;
		}
		if (wf.getCustomer() == null){
		    wf.setCustomer(new Customer());
		}
		wf.getCustomer().setLastName(getString(dataValue));
		break;
	    case F_COMMENT:
		wf.setComment(getString(dataValue));
		break;
	    case F_TYPE:
		wf.setType(getString(dataValue));
		break;
	    case F_START_TIME:
		wf.setStartTime(getDate(dataValue));
		break;
	    case F_END_TIME:
		wf.setEndTime(getDate(dataValue));
		break;
	    case F_STATUS:
		wf.setStatus(getString(dataValue));
		break;
	}
    }

    @Override
    public DataType getFieldDataType(int fieldId) throws DataAccessException {
	validateFieldId(fieldId);
	switch (fieldId) {
	    case F_ID:
		return DataType.LONG;
	    case F_CUSTOMER_FIRST_NAME:
		return DataType.STRING;
	    case F_CUSTOMER_LAST_NAME:
		return DataType.STRING;
	    case F_COMMENT:
		return DataType.STRING;
	    case F_TYPE:
		return DataType.STRING;
	    case F_START_TIME:
		return DataType.DATE_TIMESTAMP;
	    case F_END_TIME:
		return DataType.DATE_TIMESTAMP;
	    case F_STATUS:
		return DataType.STRING;
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
	return Workflow.class;
    }
}
