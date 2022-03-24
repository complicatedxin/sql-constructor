package com.zincyanide.sqlconstructor.builder;

import com.zincyanide.sqlconstructor.BaseQuerySql;
import com.zincyanide.sqlconstructor.internal.Separate;
import com.zincyanide.sqlconstructor.internal.StringUtil;

public class From
{
    BaseQuerySqlBuilder builder;

    private static final String WHERE = "WHERE ";
    private static final String INNER = "INNER";
    private static final String LEFT = "LEFT";
    private static final String RIGHT = "RIGHT";
    private static final String JOIN = " JOIN ";

    public From(BaseQuerySqlBuilder builder)
    {
        this.builder = builder;
    }

    public BaseQuerySql build()
    {
        return this.builder.build();
    }

    public Where where(String condition)
    {
        if(StringUtil.isWhite(condition))
            condition = Where.ANYWHERE;

        builder.sqlSB
                .append(WHERE)
                .append(condition)
                .append(Separate.WHITESPACE);

        return new Where(this.builder);
    }

    public Join innerJoin(String table, String alias)
    {
        return join(INNER, table, alias);
    }

    public Join leftJoin(String table, String alias)
    {
        return join(LEFT, table, alias);
    }

    public Join rightJoin(String table, String alias)
    {
        return join(RIGHT, table, alias);
    }

    private Join join(String joinManner, String table, String alias)
    {
        StringUtil.requireNonWhite(table);

        builder.sqlSB
                .append(joinManner).append(JOIN).append(table).append(Separate.WHITESPACE);

        if(!StringUtil.isEmpty(alias)
                && !table.contains(Separate.WHITESPACE))
            builder.sqlSB.append(alias).append(Separate.WHITESPACE);

        return new Join(this.builder);
    }
}
