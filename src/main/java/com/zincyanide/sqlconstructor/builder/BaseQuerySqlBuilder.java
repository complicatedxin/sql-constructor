package com.zincyanide.sqlconstructor.builder;

import com.zincyanide.sqlconstructor.BaseQuerySql;
import com.zincyanide.sqlconstructor.internal.Separate;
import com.zincyanide.sqlconstructor.internal.StringUtil;
import java.util.Objects;

public class BaseQuerySqlBuilder
{
    StringBuilder sqlSB = new StringBuilder();

    private static final String SELECT = "SELECT ";

    public Select select(String... columns)
    {
        Objects.requireNonNull(columns);

        sqlSB.append(SELECT);
        for(String column : columns)
        {
            StringUtil.requireNonWhite(column);
            sqlSB.append(column).append(Separate.COMMA);
        }
        sqlSB.replace(sqlSB.length() - 1, sqlSB.length(), Separate.WHITESPACE);

        return new Select(this);
    }

    public BaseQuerySql build()
    {
        return new BaseQuerySql(sqlSB.toString());
    }
}
