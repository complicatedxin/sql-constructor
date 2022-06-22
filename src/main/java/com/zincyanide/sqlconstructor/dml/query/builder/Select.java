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

package com.zincyanide.sqlconstructor.dml.query.builder;

import com.zincyanide.sqlconstructor.SqlConstructor;
import com.zincyanide.sqlconstructor.internal.StringUtil;
import java.util.List;

public class Select extends BuilderMinion
{
    static final String FROM = "FROM ";

    String mode = Mode.DEFAULT;

    List<String> cols;

    public Select(BaseQuerySqlBuilder builder)
    {
        super(builder);
    }

    public From from(String table)
    {
        return from(table, "");
    }

    public From from(String table, String alias)
    {
        StringUtil.requireNonWhite(table);

        From from = chief.getMinion(From.class);
        from.tab = table;
        from.alias = alias;

        return from;
    }

    public From from(SqlConstructor sqlConstructor, String alias)
    {
        StringUtil.requireNonWhite(alias, "Derived table should have an alias !");

        From from = chief.getMinion(From.class);
        from.subSql = sqlConstructor;
        from.alias = alias;

        return from;
    }

    interface Mode
    {
        String DEFAULT     =   "";
        String DISTINCT    =   "DISTINCT ";
    }
}
