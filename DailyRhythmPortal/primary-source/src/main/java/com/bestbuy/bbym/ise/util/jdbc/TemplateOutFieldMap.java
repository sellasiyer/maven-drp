package com.bestbuy.bbym.ise.util.jdbc;

import com.bestbuy.bbym.ise.exception.DataAccessException;

public interface TemplateOutFieldMap {

    public int getNumOutFields();

    public FieldInfo getOutFieldInfo(int outFieldId) throws DataAccessException;

}
