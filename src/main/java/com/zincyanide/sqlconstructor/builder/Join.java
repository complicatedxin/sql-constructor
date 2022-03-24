package com.zincyanide.sqlconstructor.builder;

import com.zincyanide.sqlconstructor.internal.Separate;
import com.zincyanide.sqlconstructor.internal.StringUtil;

public class Join
{
    BaseQuerySqlBuilder builder;

    private static final String ON = "ON ";

    public Join(BaseQuerySqlBuilder builder)
    {
        this.builder = builder;
    }

    public JoinCondition on(String joinCondition)
    {
        StringUtil.requireNonWhite(joinCondition);

        builder.sqlSB
                .append(ON)
                .append(joinCondition)
                .append(Separate.WHITESPACE);

        return new JoinCondition(this.builder);
    }

}
