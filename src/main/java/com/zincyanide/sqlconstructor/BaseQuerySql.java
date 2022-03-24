package com.zincyanide.sqlconstructor;

/**
 *  is used to validate the format of sql
 */
public class BaseQuerySql extends SqlConstructor
{
    private String sql;

    public BaseQuerySql(String sql)
    {
        this.sql = sql;
    }

    @Override
    public String getSql()
    {
        //TODO validate the format of sql

        return sql;
    }

}
