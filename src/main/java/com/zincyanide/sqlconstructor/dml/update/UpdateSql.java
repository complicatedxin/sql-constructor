package com.zincyanide.sqlconstructor.dml.update;

import com.zincyanide.sqlconstructor.SqlConstructor;

public class UpdateSql extends SqlConstructor
{
    private String sql;

    public UpdateSql(String sql)
    {
        this.sql = sql;
    }

    @Override
    public String getSql()
    {
        return sql;
    }
}
