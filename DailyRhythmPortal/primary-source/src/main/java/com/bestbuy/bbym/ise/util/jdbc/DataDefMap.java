package com.bestbuy.bbym.ise.util.jdbc;

public interface DataDefMap {

    public DataDef generateDataDef(String tableAlias);

    public String getTableAlias(Class<?> dataClass);

}
