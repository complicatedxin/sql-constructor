package com.zincyanide.sqlconstructor.dml.query.builder;

import com.zincyanide.sqlconstructor.internal.Symbol;
import com.zincyanide.sqlconstructor.internal.StringUtil;

public class MultiCondition extends Condition
{
    private static final String AND = "AND ";
    private static final String OR = "OR ";

    private StringBuilder sB;

    public MultiCondition(BaseQuerySqlBuilder builder)
    {
        super(builder);
    }
    public MultiCondition(StringBuilder sb)
    {
        this((BaseQuerySqlBuilder) null);
        this.sB = sb;
    }

    public static Condition first(String criterion)
    {
        StringBuilder sb = new StringBuilder(Symbol.BRACKET_LEFT)
                .append(Symbol.WHITESPACE)
                .append(criterion)
                .append(Symbol.WHITESPACE);

        return new MultiCondition(sb);
    }

    @Override
    public Condition and(String condition)
    {
        if (!StringUtil.isEmpty(condition))
            sB.append(AND)
                    .append(condition)
                    .append(Symbol.WHITESPACE);

        return this;
    }

    @Override
    public Condition or(String condition)
    {
        if (!StringUtil.isEmpty(condition))
            sB.append(OR)
                    .append(condition)
                    .append(Symbol.WHITESPACE);

        return this;
    }

    @Override
    public String finish()
    {
        return sB.append(Symbol.BRACKET_RIGHT).toString();
    }

}
