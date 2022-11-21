package com.zincyanide.sqlconstructor.dml.delete;

import com.zincyanide.sqlconstructor.SqlConstructor;

public class DeleteSql extends SqlConstructor
{
    private String sql;

    public DeleteSql(String sql)
    {
        this.sql = sql;
    }

    @Override
    public String getSql()
    {
        return sql;
    }

}
