package com.zincyanide.sqlconstructor.wrapper;

import com.zincyanide.sqlconstructor.SqlConstructor;

public abstract class QuerySqlWrapper extends SqlConstructor
{
    protected SqlConstructor sqlConstructor;

    public QuerySqlWrapper(SqlConstructor sqlConstructor)
    {
        this.sqlConstructor = sqlConstructor;
    }

    public String getSql()
    {
        return this.sqlConstructor.getSql();
    }
}
