package com.zincyanide.sqlconstructor.wrapper.impl;

import com.zincyanide.sqlconstructor.SqlConstructor;
import com.zincyanide.sqlconstructor.wrapper.QuerySqlWrapper;

public class CountSql extends QuerySqlWrapper
{
    public CountSql(SqlConstructor sqlConstructor)
    {
        super(sqlConstructor);
    }

    @Override
    public String getSql()
    {
        return "SELECT count(1) FROM ( "
                    + super.getSql() +
                " )";
    }
}
