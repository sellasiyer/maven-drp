package com.bestbuy.bbym.ise.workflow.domain.datadef;

import com.bestbuy.bbym.ise.exception.DataAccessException;
import com.bestbuy.bbym.ise.util.jdbc.DataDef;
import com.bestbuy.bbym.ise.workflow.domain.WorkflowStep;

public class WorkflowStepDataDef extends DataDef {

    public final static int F_ID = 1;
    public final static int F_COMMENT = 2;
    public final static int F_START_TIME = 3;
    public final static int F_END_TIME = 4;
    public final static int F_SEQUENCE_NUM = 5;
    public final static int F_STEP_ID = 6;
    public final static int F_WORKFLOW_ID = 7;

    @Override
    public int getMaxFieldId() {
	return 7;
    }

    @Override
    public String getTable() {
	return "workflow_step_hist";
    }

    @Override
    public String getSequenceName() {
	return "workflow_step_hist_seq";
    }

    @Override
    public String getFieldName(int fieldId) throws DataAccessException {
	validateFieldId(fieldId);
	switch (fieldId) {
	    case F_ID:
		return "wf_step_hist_id";
	    case F_COMMENT:
		return "step_comment";
	    case F_START_TIME:
		return "start_tm";
	    case F_END_TIME:
		return "end_tm";
	    case F_SEQUENCE_NUM:
		return "sequence_number";
	    case F_STEP_ID:
		return "addstep_id";
	    case F_WORKFLOW_ID:
		return "workflow_id";
	}
	return null;
    }

    @Override
    public Object getFieldValue(int fieldId, Object dataObject) throws DataAccessException {
	validateFieldId(fieldId);
	if (dataObject == null){
	    return null;
	}
	WorkflowStep step = (WorkflowStep) dataObject;
	switch (fieldId) {
	    case F_ID:
		return new Long(step.getId());
	    case F_COMMENT:
		return step.getComment();
	    case F_START_TIME:
		return step.getStartTime();
	    case F_END_TIME:
		return step.getEndTime();
	    case F_SEQUENCE_NUM:
		return new Integer(step.getSequenceNum());
	    case F_STEP_ID:
		return new Integer(step.getStepId());
	    case F_WORKFLOW_ID:
		return new Long(step.getWorkflowId());
	}
	return null;
    }

    @Override
    public void setFieldValue(int fieldId, Object dataObject, Object dataValue) throws DataAccessException {
	validateFieldId(fieldId);
	if (dataObject == null){
	    return;
	}
	WorkflowStep step = (WorkflowStep) dataObject;
	switch (fieldId) {
	    case F_ID:
		step.setId(getLong(dataValue));
		break;
	    case F_COMMENT:
		step.setComment(getString(dataValue));
		break;
	    case F_START_TIME:
		step.setStartTime(getDate(dataValue));
		break;
	    case F_END_TIME:
		step.setEndTime(getDate(dataValue));
		break;
	    case F_SEQUENCE_NUM:
		step.setSequenceNum(getInteger(dataValue));
		break;
	    case F_STEP_ID:
		step.setStepId(getInteger(dataValue));
		break;
	    case F_WORKFLOW_ID:
		step.setWorkflowId(getLong(dataValue));
		break;
	}
    }

    @Override
    public DataType getFieldDataType(int fieldId) throws DataAccessException {
	validateFieldId(fieldId);
	switch (fieldId) {
	    case F_ID:
		return DataType.LONG;
	    case F_COMMENT:
		return DataType.STRING;
	    case F_START_TIME:
		return DataType.DATE_TIMESTAMP;
	    case F_END_TIME:
		return DataType.DATE_TIMESTAMP;
	    case F_SEQUENCE_NUM:
		return DataType.INTEGER;
	    case F_STEP_ID:
		return DataType.INTEGER;
	    case F_WORKFLOW_ID:
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
	return WorkflowStep.class;
    }
}
