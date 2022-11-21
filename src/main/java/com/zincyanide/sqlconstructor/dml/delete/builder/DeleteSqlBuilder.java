package com.zincyanide.sqlconstructor.dml.delete.builder;

import com.zincyanide.sqlconstructor.SqlConstructor;
import com.zincyanide.sqlconstructor.dml.delete.DeleteSql;
import com.zincyanide.sqlconstructor.internal.*;

import java.util.Objects;

public class DeleteSqlBuilder
        extends SqlBuilder
        implements Bindable, Cacheable
{
    private static final String DELETE = "DELETE FROM ";

    public From from(String table)
    {
        From from = getMinion(From.class);
        from.setTable(table, "");
        return from;
    }

    @Override
    public SqlConstructor build()
    {
        StringBuilder sb = new StringBuilder();

        buildupFrom(sb);

        buildupWhere(sb);

        return new DeleteSql(sb.toString());
    }

    private void buildupFrom(StringBuilder sb)
    {
        From from = getMinion(From.class);
        AliasField<?> af = from.getTableField();
        Objects.requireNonNull(af, "no table declared");
        sb.append(DELETE)
                .append(af.getField())
                .append(Symbol.WHITESPACE)
                .append(af.getAlias())
                .append(Symbol.WHITESPACE);
    }

    private void buildupWhere(StringBuilder sb)
    {
        Where where = getMinion(Where.class);
        String conditions = where.getConditionalStatement().get();
        if(!StringUtil.isEmpty(conditions))
            sb.append(From.WHERE).append(conditions);
    }

    @Override
    protected void summon()
    {
        minions.put(From.class, new From(this));
        minions.put(Where.class, new Where(this));
    }


    @Override
    public void bind()
    {
        Bindable.THREAD_BIND.set(this);
    }

    @Override
    public void clear()
    {
        clearFrom();
        clearWhere();
    }

    public DeleteSqlBuilder clearFrom()
    {
        getMinion(From.class).clear();
        return this;
    }

    public From clearWhere()
    {
        getMinion(Where.class).clear();
        return getMinion(From.class);
    }


}
