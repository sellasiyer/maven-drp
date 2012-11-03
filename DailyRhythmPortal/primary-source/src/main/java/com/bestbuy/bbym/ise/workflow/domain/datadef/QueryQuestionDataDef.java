package com.bestbuy.bbym.ise.workflow.domain.datadef;

import com.bestbuy.bbym.ise.exception.DataAccessException;
import com.bestbuy.bbym.ise.util.jdbc.DataDef;
import com.bestbuy.bbym.ise.workflow.domain.QueryQuestion;

public class QueryQuestionDataDef extends DataDef {

    public final static int F_ID = 1;
    public final static int F_NAME = 2;
    public final static int F_INSTRUCTION = 3;
    public final static int F_QUESTION = 4;
    public final static int F_TYPE = 5;
    public final static int F_DETAIL = 6;
    public final static int F_IMAGE_URL = 7;
    public final static int F_DISPLAY_TYPE = 8;

    @Override
    public int getMaxFieldId() {
	return 8;
    }

    @Override
    public String getTable() {
	return "dialog_question";
    }

    @Override
    public String getSequenceName() {
	return "dialog_question_dialog_seq";
    }

    @Override
    public String getFieldName(int fieldId) throws DataAccessException {
	validateFieldId(fieldId);
	switch (fieldId) {
	    case F_ID:
		return "dialog_question_id";
	    case F_NAME:
		return "question_code";
	    case F_INSTRUCTION:
		return "instruction";
	    case F_QUESTION:
		return "question";
	    case F_TYPE:
		return "dialog_question_typ";
	    case F_DETAIL:
		return "instruction_detail";
	    case F_IMAGE_URL:
		return "image_url";
	    case F_DISPLAY_TYPE:
		return "display_typ";
	}
	return null;
    }

    @Override
    public Object getFieldValue(int fieldId, Object dataObject) throws DataAccessException {
	validateFieldId(fieldId);
	if (dataObject == null){
	    return null;
	}
	QueryQuestion queryQuestion = (QueryQuestion) dataObject;
	switch (fieldId) {
	    case F_ID:
		return new Long(queryQuestion.getId());
	    case F_NAME:
		return queryQuestion.getName();
	    case F_INSTRUCTION:
		return queryQuestion.getInstruction();
	    case F_QUESTION:
		return queryQuestion.getQuestion();
	    case F_TYPE:
		return queryQuestion.getType();
	    case F_DETAIL:
		return queryQuestion.getDetail();
	    case F_IMAGE_URL:
		return queryQuestion.getImageUrl();
	    case F_DISPLAY_TYPE:
		return queryQuestion.getDisplayType();
	}
	return null;
    }

    @Override
    public void setFieldValue(int fieldId, Object dataObject, Object dataValue) throws DataAccessException {
	validateFieldId(fieldId);
	if (dataObject == null){
	    return;
	}
	QueryQuestion queryQuestion = (QueryQuestion) dataObject;
	switch (fieldId) {
	    case F_ID:
		queryQuestion.setId(getLong(dataValue));
		break;
	    case F_NAME:
		queryQuestion.setName(getString(dataValue));
		break;
	    case F_INSTRUCTION:
		queryQuestion.setInstruction(getString(dataValue));
		break;
	    case F_QUESTION:
		queryQuestion.setQuestion(getString(dataValue));
		break;
	    case F_TYPE:
		queryQuestion.setType(getString(dataValue));
		break;
	    case F_DETAIL:
		queryQuestion.setDetail(getString(dataValue));
		break;
	    case F_IMAGE_URL:
		queryQuestion.setImageUrl(getString(dataValue));
		break;
	    case F_DISPLAY_TYPE:
		queryQuestion.setDisplayType(getString(dataValue));
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
	    case F_INSTRUCTION:
		return DataType.STRING;
	    case F_QUESTION:
		return DataType.STRING;
	    case F_TYPE:
		return DataType.STRING;
	    case F_DETAIL:
		return DataType.STRING;
	    case F_IMAGE_URL:
		return DataType.STRING;
	    case F_DISPLAY_TYPE:
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
	return QueryQuestion.class;
    }
}
