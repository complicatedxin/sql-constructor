package com.zincyanide.sqlconstructor.builder;

import com.zincyanide.sqlconstructor.BaseQuerySql;
import com.zincyanide.sqlconstructor.internal.Separate;
import com.zincyanide.sqlconstructor.internal.StringUtil;

public class Where
{
    BaseQuerySqlBuilder builder;

    public static final String ANYWHERE = "1 = 1";
    private static final String AND = "AND ";
    private static final String OR = "OR ";

    public Where(BaseQuerySqlBuilder builder)
    {
        this.builder = builder;
    }

    public BaseQuerySql build()
    {
        return this.builder.build();
    }

    public Condition and(String condition)
    {
        if (StringUtil.isEmpty(condition))
            return new And(this.builder);

        builder.sqlSB
                .append(AND)
                .append(condition)
                .append(Separate.WHITESPACE);

        return new And(this.builder);
    }

    public Condition or(String condition)
    {
        if (StringUtil.isEmpty(condition))
            return new And(this.builder);

        builder.sqlSB
                .append(OR)
                .append(condition)
                .append(Separate.WHITESPACE);

        return new Or(this.builder);
    }
}
