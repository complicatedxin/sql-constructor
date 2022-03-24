package com.zincyanide.sqlconstructor.builder;

import com.zincyanide.sqlconstructor.internal.Separate;
import com.zincyanide.sqlconstructor.internal.StringUtil;
import java.util.Objects;

public class Select
{
    BaseQuerySqlBuilder builder;

    private static final String FROM = "FROM ";

    public Select(BaseQuerySqlBuilder builder)
    {
        this.builder = builder;
    }

    public From from(String table)
    {
        return from(table, null);
    }

    public From from(String table, String alias)
    {
        StringUtil.requireNonWhite(table);

        builder.sqlSB.append(FROM).append(table).append(Separate.WHITESPACE);

        if(!StringUtil.isEmpty(alias)
                && !table.contains(Separate.WHITESPACE))
            builder.sqlSB.append(alias).append(Separate.WHITESPACE);

        return new From(this.builder);
    }

    public From from(String... tables)
    {
        Objects.requireNonNull(tables);

        builder.sqlSB.append(FROM);
        for(String table : tables)
        {
            StringUtil.requireNonEmpty(table, "table do not be null or empty");
            builder.sqlSB.append(table).append(Separate.COMMA);
        }
        builder.sqlSB.replace(builder.sqlSB.length()-1, builder.sqlSB.length(), Separate.WHITESPACE);

        return new From(this.builder);
    }
}
