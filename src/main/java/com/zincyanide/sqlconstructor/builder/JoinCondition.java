package com.zincyanide.sqlconstructor.builder;

public class JoinCondition extends Condition
{
    public JoinCondition(BaseQuerySqlBuilder builder)
    {
        super(builder);
    }

    public Join innerJoin(String table, String alias)
    {
        return new From(this.builder).innerJoin(table, alias);
    }

    public Join leftJoin(String table, String alias)
    {
        return new From(this.builder).leftJoin(table, alias);
    }

    public Join rightJoin(String table, String alias)
    {
        return new From(this.builder).rightJoin(table, alias);
    }

    public Where where(String condition)
    {
        return new From(builder).where(condition);
    }

}
