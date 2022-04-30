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
import com.zincyanide.sqlconstructor.internal.Symbol;
import com.zincyanide.sqlconstructor.internal.StringUtil;

public class Select extends BuilderMinion
{
    private static final String FROM = "FROM ";

    public Select(BaseQuerySqlBuilder builder)
    {
        super(builder);
    }

    public From from(String table)
    {
        return from(table, null);
    }

    public From from(String table, String alias)
    {
        StringUtil.requireNonWhite(table);

        builder.sqlSB.append(FROM).append(table).append(Symbol.WHITESPACE);

        if(!StringUtil.isEmpty(alias))
            builder.sqlSB.append(alias)
                    .append(Symbol.WHITESPACE);

        return builder.getMinion(From.class);
    }

    public From from(SqlConstructor sqlConstructor, String alias)
    {
        StringUtil.requireNonWhite(alias, "Derived table should have an alias !");

        builder.sqlSB.append(FROM).append(sqlConstructor).append(Symbol.WHITESPACE)
                .append(alias).append(Symbol.WHITESPACE);

        return builder.getMinion(From.class);
    }
}
