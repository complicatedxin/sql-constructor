package com.zincyanide.sqlconstructor.builder;

public class SingleCondition extends Condition
{
    public SingleCondition(BaseQuerySqlBuilder builder)
    {
        super(builder);
    }

    @Override
    public Condition and(String condition)
    {
        return ((Where) builder.getMinion(Where.class)).and(condition);
    }

    @Override
    public Condition or(String condition)
    {
        return  ((Where) builder.getMinion(Where.class)).or(condition);
    }

    @Override
    public String finish()
    {
        return null;
    }
}
