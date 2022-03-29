/*
 * Copyright 2022 Zincyanide
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.zincyanide.sqlconstructor.builder;

import com.zincyanide.sqlconstructor.SqlConstructor;
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

    public From from(SqlConstructor sqlConstructor, String alias)
    {
        StringUtil.requireNonWhite(alias, "Derived table should have an alias !");

        builder.sqlSB
                .append(FROM).append(sqlConstructor).append(Separate.WHITESPACE)
                .append(alias).append(Separate.WHITESPACE);

        return new From(this.builder);
    }
}
