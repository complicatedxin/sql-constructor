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
    StringBuilder sb;

    private static final String FROM = "FROM ";

    public Select(StringBuilder sb)
    {
        this.sb = sb;
    }

    public From from(String table)
    {
        return from(table, null);
    }

    public From from(String table, String alias)
    {
        StringUtil.requireNonWhite(table);

        sb.append(FROM).append(table).append(Separate.WHITESPACE);

        if(!StringUtil.isEmpty(alias)
                && !table.contains(Separate.WHITESPACE))
            sb.append(alias).append(Separate.WHITESPACE);

        return new From(this.sb);
    }

    public From from(String... tables)
    {
        Objects.requireNonNull(tables);

        sb.append(FROM);
        for(String table : tables)
        {
            StringUtil.requireNonEmpty(table, "table do not be null or empty");
            sb.append(table).append(Separate.COMMA);
        }
        sb.replace(sb.length()-1, sb.length(), Separate.WHITESPACE);

        return new From(this.sb);
    }

    public From from(SqlConstructor sqlConstructor, String alias)
    {
        StringUtil.requireNonWhite(alias, "Derived table should have an alias !");

        sb.append(FROM).append(sqlConstructor).append(Separate.WHITESPACE)
                .append(alias).append(Separate.WHITESPACE);

        return new From(this.sb);
    }
}
