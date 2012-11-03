package com.bestbuy.bbym.ise.workflow.domain;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import com.bestbuy.bbym.ise.domain.User;
import com.bestbuy.bbym.ise.util.jdbc.DataDef;
import com.bestbuy.bbym.ise.util.jdbc.DateUtil;
import com.bestbuy.bbym.ise.util.jdbc.IBaseDataObject;
import com.bestbuy.bbym.ise.util.jdbc.TemplateOperation;

public abstract class BaseDataObject implements Serializable, IBaseDataObject {

    private static final long serialVersionUID = -7051813032673940459L;

    private String createdBy;
    private Date createdOn;
    private String amendedBy;
    private Date amendedOn;

    @Override
    public final String getBaseInsertFieldsSql() {
	return "created_by, created_on";
    }

    @Override
    public final String getBaseInsertParamsSql() {
	return "?, ?";
    }

    @Override
    public final int getNumBaseInsertFields() {
	return 2;
    }

    @Override
    public final String getBaseUpdateFieldsSql() {
	return "amended_by=?, amended_on=?";
    }

    @Override
    public final int getNumBaseUpdateFields() {
	return 2;
    }

    @Override
    public final String getBaseOutFieldsSql(String tableAlias) {
	StringBuilder sb = new StringBuilder();
	if (tableAlias != null){
	    sb.append(tableAlias);
	    sb.append(".");
	}
	sb.append("created_by, ");
	if (tableAlias != null){
	    sb.append(tableAlias);
	    sb.append(".");
	}
	sb.append("created_on, ");
	if (tableAlias != null){
	    sb.append(tableAlias);
	    sb.append(".");
	}
	sb.append("amended_by, ");
	if (tableAlias != null){
	    sb.append(tableAlias);
	    sb.append(".");
	}
	sb.append("amended_on");
	return sb.toString();
    }

    @Override
    public final int outputBaseFieldValues(int startIndex, ResultSet rs) throws SQLException {
	int idx = startIndex;
	setCreatedBy(rs.getString(idx++));
	setCreatedOn(DateUtil.sqlTimestampToUtilDate(rs.getTimestamp(idx++)));
	setAmendedBy(rs.getString(idx++));
	setAmendedOn(DateUtil.sqlTimestampToUtilDate(rs.getTimestamp(idx++)));
	return idx;
    }

    public final void inputBaseInsertFieldValues(int startIndex, PreparedStatement ps, User changer)
	    throws SQLException {
	int idx = startIndex;
	TemplateOperation.setValue(ps, DataDef.DataType.STRING, idx++, changer.getUserId());
	TemplateOperation.setValue(ps, DataDef.DataType.DATE_TIMESTAMP, idx++, new Date());
    }

    public final void inputBaseUpdateFieldValues(int startIndex, PreparedStatement ps, User changer)
	    throws SQLException {
	int idx = startIndex;
	TemplateOperation.setValue(ps, DataDef.DataType.STRING, idx++, changer.getUserId());
	TemplateOperation.setValue(ps, DataDef.DataType.DATE_TIMESTAMP, idx++, new Date());
    }

    public String getCreatedBy() {
	return createdBy;
    }

    public void setCreatedBy(String createdBy) {
	this.createdBy = createdBy;
    }

    public Date getCreatedOn() {
	return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
	this.createdOn = createdOn;
    }

    public String getAmendedBy() {
	return amendedBy;
    }

    public void setAmendedBy(String amendedBy) {
	this.amendedBy = amendedBy;
    }

    public Date getAmendedOn() {
	return amendedOn;
    }

    public void setAmendedOn(Date amendedOn) {
	this.amendedOn = amendedOn;
    }

    public String toString() {
	return ReflectionToStringBuilder.toString(this);
    }
}
