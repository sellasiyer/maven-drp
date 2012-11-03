package com.bestbuy.bbym.ise.workflow.domain.datadef;

import com.bestbuy.bbym.ise.exception.DataAccessException;
import com.bestbuy.bbym.ise.util.jdbc.DataDef;
import com.bestbuy.bbym.ise.workflow.domain.DialogAnswer;

public class DialogAnswerDataDef extends DataDef {

    public final static int F_ID = 1;
    public final static int F_NAME = 2;
    public final static int F_ANSWER = 3;
    public final static int F_DIALOG_QUESTION_ID = 4;
    public final static int F_SEQUENCE_NUM = 5;

    @Override
    public int getMaxFieldId() {
	return 5;
    }

    @Override
    public String getTable() {
	return "dialog_question_answer_hist";
    }

    @Override
    public String getSequenceName() {
	return "dg_question_answer_hist_seq";
    }

    @Override
    public String getFieldName(int fieldId) throws DataAccessException {
	validateFieldId(fieldId);
	switch (fieldId) {
	    case F_ID:
		return "dg_question_answer_hist_id";
	    case F_NAME:
		return "answer_code";
	    case F_ANSWER:
		return "answer";
	    case F_DIALOG_QUESTION_ID:
		return "dg_question_hist_id";
	    case F_SEQUENCE_NUM:
		return "sequence_number";
	}
	return null;
    }

    @Override
    public Object getFieldValue(int fieldId, Object dataObject) throws DataAccessException {
	validateFieldId(fieldId);
	if (dataObject == null){
	    return null;
	}
	DialogAnswer da = (DialogAnswer) dataObject;
	switch (fieldId) {
	    case F_ID:
		return new Long(da.getId());
	    case F_NAME:
		return da.getName();
	    case F_ANSWER:
		return da.getAnswer();
	    case F_DIALOG_QUESTION_ID:
		return da.getDialogQuestionId();
	    case F_SEQUENCE_NUM:
		return new Integer(da.getSequenceNum());
	}
	return null;
    }

    @Override
    public void setFieldValue(int fieldId, Object dataObject, Object dataValue) throws DataAccessException {
	validateFieldId(fieldId);
	if (dataObject == null){
	    return;
	}
	DialogAnswer da = (DialogAnswer) dataObject;
	switch (fieldId) {
	    case F_ID:
		da.setId(getLong(dataValue));
		break;
	    case F_NAME:
		da.setName(getString(dataValue));
		break;
	    case F_ANSWER:
		da.setAnswer(getString(dataValue));
		break;
	    case F_DIALOG_QUESTION_ID:
		da.setDialogQuestionId(getLong(dataValue));
		break;
	    case F_SEQUENCE_NUM:
		da.setSequenceNum(getInteger(dataValue));
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
	    case F_ANSWER:
		return DataType.STRING;
	    case F_DIALOG_QUESTION_ID:
		return DataType.LONG;
	    case F_SEQUENCE_NUM:
		return DataType.INTEGER;
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
	return DialogAnswer.class;
    }
}
