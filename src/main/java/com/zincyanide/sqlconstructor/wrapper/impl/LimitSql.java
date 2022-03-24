package com.zincyanide.sqlconstructor.wrapper.impl;

import com.zincyanide.sqlconstructor.SqlConstructor;
import com.zincyanide.sqlconstructor.wrapper.QuerySqlWrapper;

public class LimitSql extends QuerySqlWrapper
{
    private int queryOffset = 0;
    private int queryNum = 0;

    public LimitSql(SqlConstructor sqlConstructor, int queryOffset, int queryNum)
    {
        super(sqlConstructor);
        this.queryOffset = queryOffset;
        this.queryNum = queryNum;
    }

    @Override
    public String getSql()
    {
        return super.getSql()
                + " LIMIT " + queryOffset + "," + queryNum;
    }
}
