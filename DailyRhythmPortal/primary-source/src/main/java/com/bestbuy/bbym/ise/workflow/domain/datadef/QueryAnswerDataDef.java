package com.bestbuy.bbym.ise.workflow.domain.datadef;

import com.bestbuy.bbym.ise.exception.DataAccessException;
import com.bestbuy.bbym.ise.util.jdbc.DataDef;
import com.bestbuy.bbym.ise.workflow.domain.QueryAnswer;

public class QueryAnswerDataDef extends DataDef {

    public final static int F_ID = 1;
    public final static int F_NAME = 2;
    public final static int F_ANSWER = 3;

    @Override
    public int getMaxFieldId() {
	return 3;
    }

    @Override
    public String getTable() {
	return "dialog_question_answer";
    }

    @Override
    public String getSequenceName() {
	return "dialog_question_answer_seq";
    }

    @Override
    public String getFieldName(int fieldId) throws DataAccessException {
	validateFieldId(fieldId);
	switch (fieldId) {
	    case F_ID:
		return "dialog_question_answer_id";
	    case F_NAME:
		return "answer_code";
	    case F_ANSWER:
		return "answer";
	}
	return null;
    }

    @Override
    public Object getFieldValue(int fieldId, Object dataObject) throws DataAccessException {
	validateFieldId(fieldId);
	if (dataObject == null){
	    return null;
	}
	QueryAnswer qa = (QueryAnswer) dataObject;
	switch (fieldId) {
	    case F_ID:
		return new Long(qa.getId());
	    case F_NAME:
		return qa.getName();
	    case F_ANSWER:
		return qa.getAnswer();
	}
	return null;
    }

    @Override
    public void setFieldValue(int fieldId, Object dataObject, Object dataValue) throws DataAccessException {
	validateFieldId(fieldId);
	if (dataObject == null){
	    return;
	}
	QueryAnswer qa = (QueryAnswer) dataObject;
	switch (fieldId) {
	    case F_ID:
		qa.setId(getLong(dataValue));
		break;
	    case F_NAME:
		qa.setName(getString(dataValue));
		break;
	    case F_ANSWER:
		qa.setAnswer(getString(dataValue));
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
	return QueryAnswer.class;
    }
}
