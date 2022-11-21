package com.zincyanide.sqlconstructor.internal;

import com.zincyanide.sqlconstructor.SqlConstructor;

import java.util.HashMap;
import java.util.Map;

public abstract class SqlBuilder extends BuilderMinion
{
    protected final Map<Class<? extends BuilderMinion>, BuilderMinion> minions = new HashMap<>();

    public SqlBuilder()
    {
        this(null);
    }
    public SqlBuilder(SqlBuilder chief)
    {
        super(chief);
        summon();
    }

    public abstract SqlConstructor build();

    protected abstract void summon();

    protected  <T extends BuilderMinion> T getMinion(Class<T> clazz)
    {
        return (T) minions.get(clazz);
    }
}
