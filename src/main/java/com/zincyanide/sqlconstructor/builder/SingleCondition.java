package com.zincyanide.sqlconstructor.builder;

public class SingleCondition extends Condition
{
    public SingleCondition(StringBuilder sb)
    {
        super(sb);
    }

    @Override
    public Condition and(String condition)
    {
        return new Where(this.sb).and(condition);
    }

    @Override
    public Condition or(String condition)
    {
        return new Where(this.sb).or(condition);
    }

    @Override
    public String finish()
    {
        return null;
    }
}
