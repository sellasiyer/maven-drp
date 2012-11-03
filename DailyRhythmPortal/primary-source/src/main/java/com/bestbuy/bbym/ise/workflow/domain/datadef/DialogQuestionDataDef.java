package com.bestbuy.bbym.ise.workflow.domain.datadef;

import com.bestbuy.bbym.ise.exception.DataAccessException;
import com.bestbuy.bbym.ise.util.jdbc.DataDef;
import com.bestbuy.bbym.ise.workflow.domain.DialogQuestion;
import com.bestbuy.bbym.ise.workflow.domain.QueryQuestion;

public class DialogQuestionDataDef extends DataDef {

    public final static int F_ID = 1;
    public final static int F_ANSWER = 2;
    public final static int F_DIALOG_ID = 3;
    public final static int F_SEQUENCE_NUM = 4;
    public final static int F_QUESTION_ID = 5;

    @Override
    public int getMaxFieldId() {
	return 5;
    }

    @Override
    public String getTable() {
	return "dialog_question_hist";
    }

    @Override
    public String getSequenceName() {
	return "dialog_question_hist_seq";
    }

    @Override
    public String getFieldName(int fieldId) throws DataAccessException {
	validateFieldId(fieldId);
	switch (fieldId) {
	    case F_ID:
		return "dg_question_hist_id";
	    case F_ANSWER:
		return "answer_text";
	    case F_DIALOG_ID:
		return "dialog_history_id";
	    case F_SEQUENCE_NUM:
		return "sequence_number";
	    case F_QUESTION_ID:
		return "dialog_question_id";
	}
	return null;
    }

    @Override
    public Object getFieldValue(int fieldId, Object dataObject) throws DataAccessException {
	validateFieldId(fieldId);
	if (dataObject == null){
	    return null;
	}
	DialogQuestion dq = (DialogQuestion) dataObject;
	switch (fieldId) {
	    case F_ID:
		return new Long(dq.getId());
	    case F_ANSWER:
		return dq.getAnswerText();
	    case F_DIALOG_ID:
		return new Long(dq.getDialogId());
	    case F_SEQUENCE_NUM:
		return new Integer(dq.getSequenceNum());
	    case F_QUESTION_ID:
		if (dq.getQuestion() != null){
		    return new Long(dq.getQuestion().getId());
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
	DialogQuestion dq = (DialogQuestion) dataObject;
	switch (fieldId) {
	    case F_ID:
		dq.setId(getLong(dataValue));
		break;
	    case F_ANSWER:
		dq.setAnswerText(getString(dataValue));
		break;
	    case F_DIALOG_ID:
		dq.setDialogId(getLong(dataValue));
		break;
	    case F_SEQUENCE_NUM:
		dq.setSequenceNum(getInteger(dataValue));
		break;
	    case F_QUESTION_ID:
		if (dataValue == null){
		    dq.setQuestion(null);
		    return;
		}
		if (dq.getQuestion() == null){
		    dq.setQuestion(new QueryQuestion());
		}
		dq.getQuestion().setId(getLong(dataValue));
		break;
	}
    }

    @Override
    public DataType getFieldDataType(int fieldId) throws DataAccessException {
	validateFieldId(fieldId);
	switch (fieldId) {
	    case F_ID:
		return DataType.LONG;
	    case F_ANSWER:
		return DataType.STRING;
	    case F_DIALOG_ID:
		return DataType.LONG;
	    case F_SEQUENCE_NUM:
		return DataType.INTEGER;
	    case F_QUESTION_ID:
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
	return DialogQuestion.class;
    }
}
