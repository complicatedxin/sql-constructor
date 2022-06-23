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

import com.zincyanide.sqlconstructor.dml.query.QuerySql;
import com.zincyanide.sqlconstructor.dml.query.wrapper.QuerySqlWrapper;
import com.zincyanide.sqlconstructor.internal.ArrayUtil;
import com.zincyanide.sqlconstructor.internal.StringUtil;
import com.zincyanide.sqlconstructor.internal.Symbol;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class GroupSql extends QuerySqlWrapper
{
    private final String alias;
    private final String groups[];
    private List<String> targets;

    public GroupSql(QuerySql querySql, String alias, String... groups)
    {
        super(querySql);
        this.alias = alias;
        this.groups = groups;
        this.targets = new LinkedList<>();
    }

    public GroupSql select(String... targets)
    {
        this.targets.addAll(Arrays.asList(targets));
        return this;
    }

    @Override
    public String getSql()
    {
        if(groups == null || groups.length <= 0)
            return super.getSql();

        StringBuilder sb;
        if(targets.size() > 0)
        {
            sb = new StringBuilder("SELECT ");
            sb.append(ArrayUtil.asString(targets, null, ", ", Symbol.WHITESPACE, StringUtil::isEmpty));
            sb.append("FROM ").append(querySql).append(Symbol.WHITESPACE)
                    .append(alias).append(Symbol.WHITESPACE);
        }
        else
            sb = new StringBuilder(super.getSql());

        sb.append(" GROUP BY ");
        sb.append(ArrayUtil.asString(groups, null, ", ", Symbol.WHITESPACE, StringUtil::isEmpty));

        return sb.toString();
    }
}
