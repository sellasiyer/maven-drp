package com.bestbuy.bbym.ise.util.jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.bestbuy.bbym.ise.domain.User;

public interface IBaseDataObject {

    public String getBaseOutFieldsSql(String tableAlias);

    public String getBaseInsertFieldsSql();

    public String getBaseInsertParamsSql();

    public int getNumBaseInsertFields();

    public String getBaseUpdateFieldsSql();

    public int getNumBaseUpdateFields();

    public int outputBaseFieldValues(int startIndex, ResultSet rs) throws SQLException;

    public void inputBaseInsertFieldValues(int startIndex, PreparedStatement ps, User changer) throws SQLException;

    public void inputBaseUpdateFieldValues(int startIndex, PreparedStatement ps, User changer) throws SQLException;
}
