package com.zincyanide.sqlconstructor.wrapper.impl;

import com.zincyanide.sqlconstructor.SqlConstructor;
import com.zincyanide.sqlconstructor.internal.StringUtil;
import com.zincyanide.sqlconstructor.wrapper.QuerySqlWrapper;
import java.sql.SQLException;

public class WithAsSql extends QuerySqlWrapper
{
    private String withName;
    private SqlConstructor asSqlConstructor;

    public WithAsSql(String withName, SqlConstructor asSqlConstructor,
                     SqlConstructor querySqlConstructor)
    {
        super(querySqlConstructor);
        this.withName = withName;
        this.asSqlConstructor = asSqlConstructor;
    }

    @Override
    public String getSql()
    {
        try {
            validatePremise();

            return "WITH " + withName + " AS ( " + asSqlConstructor.getSql() + " ) "
                    + super.getSql();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return asSqlConstructor.getSql();
    }

    private void validatePremise() throws SQLException
    {
        if(StringUtil.isEmpty(withName)
                || asSqlConstructor == null)
            throw new SQLException("with-as-sql has a null value");
    }
}
