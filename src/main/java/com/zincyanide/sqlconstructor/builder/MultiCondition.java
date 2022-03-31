package com.zincyanide.sqlconstructor.builder;

import com.zincyanide.sqlconstructor.internal.Separate;
import com.zincyanide.sqlconstructor.internal.StringUtil;

public class MultiCondition extends Condition
{
    private static final String AND = "AND ";
    private static final String OR = "OR ";

    public MultiCondition(StringBuilder sb)
    {
        super(sb);
    }

    public static Condition first(String criterion)
    {
        StringBuilder sb = new StringBuilder(Separate.BRACKET_LEFT)
                .append(Separate.WHITESPACE)
                .append(criterion)
                .append(Separate.WHITESPACE);

        return new MultiCondition(sb);
    }

    @Override
    public Condition and(String condition)
    {
        if (!StringUtil.isEmpty(condition))
            sb.append(AND)
                    .append(condition)
                    .append(Separate.WHITESPACE);

        return this;
    }

    @Override
    public Condition or(String condition)
    {
        if (!StringUtil.isEmpty(condition))
            sb.append(OR)
                    .append(condition)
                    .append(Separate.WHITESPACE);

        return this;
    }

    @Override
    public String finish()
    {
        return sb.append(Separate.BRACKET_RIGHT).toString();
    }

}
