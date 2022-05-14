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

package com.zincyanide.sqlconstructor.dml.query.wrapper.impl;

import com.zincyanide.sqlconstructor.SqlConstructor;
import com.zincyanide.sqlconstructor.dml.query.wrapper.QuerySqlWrapper;
import com.zincyanide.sqlconstructor.internal.StringUtil;
import com.zincyanide.sqlconstructor.internal.Symbol;

public class GroupSql extends QuerySqlWrapper
{
    private final String groups[];

    public GroupSql(SqlConstructor sqlConstructor, String... groups)
    {
        super(sqlConstructor);
        this.groups = groups;
    }

    @Override
    public String getSql()
    {
        int len;
        if(groups == null || (len=groups.length) <= 0)
            return super.getSql();
        StringBuilder sb = new StringBuilder(super.getSql()).append(" GROUP BY ");
        for(String col : groups)
        {
            if(StringUtil.isEmpty(col))
                continue;
            sb.append(col).append(Symbol.COMMA);
        }
        sb.replace(sb.length() - 1, sb.length(), Symbol.WHITESPACE);

        return sb.toString();
    }
}