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

import com.zincyanide.sqlconstructor.internal.Reusable;
import com.zincyanide.sqlconstructor.dml.query.QuerySql;
import com.zincyanide.sqlconstructor.internal.StringUtil;

import java.util.LinkedList;
import java.util.List;

public class Select extends BuilderMinion implements Reusable
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

    public From from(QuerySql querySql, String alias)
    {
        StringUtil.requireNonWhite(alias, "Derived table should have an alias !");

        From from = chief.getMinion(From.class);
        from.subSql = querySql;
        from.alias = alias;

        return from;
    }

    @Override
    public void clean()
    {
        this.cols = new LinkedList<>();
    }

    interface Mode
    {
        String DEFAULT     =   "";
        String DISTINCT    =   "DISTINCT ";
    }
}
