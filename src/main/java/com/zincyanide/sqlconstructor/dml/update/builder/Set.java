package com.zincyanide.sqlconstructor.dml.update.builder;

import com.zincyanide.sqlconstructor.dml.update.UpdateSql;
import com.zincyanide.sqlconstructor.internal.*;
import com.zincyanide.sqlconstructor.internal.condition.ConditionNode;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

public class Set extends BuilderMinion implements Cacheable
{
    static final String SET = "SET ";

    Map<Object, Object> changes = new LinkedHashMap<>();

    public Set(SqlBuilder chief)
    {
        super(chief);
    }

    public Set set(Joint joint)
    {
        changes.put(joint, joint);
        return this;
    }

    public Set set(String col, Object val)
    {
        changes.put(col, val);
        return this;
    }

    private boolean isString(Class<?> clazz)
    {
        return clazz == String.class;
    }

    private boolean isDate(Object obj)
    {
        return obj instanceof Date;
    }

    public Where where(String condition)
    {
        return where(new ConditionNode(condition));
    }

    public Where where(ConditionNode node)
    {
        Where where = getFellow(Where.class);
        where.and(node);
        return where;
    }

    public UpdateSql build()
    {
        return ((UpdateSqlBuilder) getChief()).build();
    }

    @Override
    public void clear()
    {
        changes.clear();
    }
}
