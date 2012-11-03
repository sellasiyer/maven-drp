package com.bestbuy.bbym.ise.workflow.domain.datadef;

import com.bestbuy.bbym.ise.exception.DataAccessException;
import com.bestbuy.bbym.ise.util.jdbc.DataDef;
import com.bestbuy.bbym.ise.workflow.domain.Dialog;
import com.bestbuy.bbym.ise.workflow.domain.WorkflowStep;

public class DialogDataDef extends DataDef {

    public final static int F_ID = 1;
    public final static int F_NAME = 2;
    public final static int F_STEP_ID = 3;

    @Override
    public int getMaxFieldId() {
	return 3;
    }

    @Override
    public String getTable() {
	return "dialog_history";
    }

    @Override
    public String getSequenceName() {
	return "dialog_history_seq";
    }

    @Override
    public String getFieldName(int fieldId) throws DataAccessException {
	validateFieldId(fieldId);
	switch (fieldId) {
	    case F_ID:
		return "dialog_history_id";
	    case F_NAME:
		return "name";
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
	Dialog dlg = (Dialog) dataObject;
	switch (fieldId) {
	    case F_ID:
		return new Long(dlg.getId());
	    case F_NAME:
		return dlg.getName();
	    case F_STEP_ID:
		if (dlg.getStep() != null){
		    return new Long(dlg.getStep().getId());
		}
		break;
	}
	return null;
    }

    @Override
    public void setFieldValue(int fieldId, Object dataObject, Object dataValue) throws DataAccessException {
	validateFieldId(fieldId);
	if (dataObject == null){
	    return;
	}
	Dialog dlg = (Dialog) dataObject;
	switch (fieldId) {
	    case F_ID:
		dlg.setId(getLong(dataValue));
		break;
	    case F_NAME:
		dlg.setName(getString(dataValue));
		break;
	    case F_STEP_ID:
		if (dataValue == null){
		    dlg.setStep(null);
		    return;
		}
		if (dlg.getStep() == null){
		    dlg.setStep(new WorkflowStep());
		}
		dlg.getStep().setId(getLong(dataValue));
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
	return Dialog.class;
    }
}
