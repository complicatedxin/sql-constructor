package com.zincyanide.sqlconstructor.builder;

import com.zincyanide.sqlconstructor.BaseQuerySql;

public abstract class Condition
{
    BaseQuerySqlBuilder builder;

    public Condition(BaseQuerySqlBuilder builder)
    {
        this.builder = builder;
    }

    public Condition and(String condition)
    {
        return new Where(this.builder).and(condition);
    }

    public Condition or(String condition)
    {
        return new Where(this.builder).or(condition);
    }

    public BaseQuerySql build()
    {
        return this.builder.build();
    }
}
