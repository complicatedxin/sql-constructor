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
import com.zincyanide.sqlconstructor.internal.CollectionUtil;
import com.zincyanide.sqlconstructor.internal.StringUtil;
import com.zincyanide.sqlconstructor.internal.Symbol;
import com.zincyanide.sqlconstructor.internal.condition.ConditionNode;
import com.zincyanide.sqlconstructor.internal.condition.ConditionalStatement;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class GroupSql extends QuerySqlWrapper
{
    private final String alias;
    private final String groups[];
    List<String> targets;
    ConditionalStatement conditionalStatement = new ConditionalStatement();

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

    public GroupSql having(String condition)
    {
        conditionalStatement.and(condition);
        return this;
    }

    public GroupSql and(String condition)
    {
        return and(new ConditionNode(condition));
    }

    public GroupSql and(ConditionNode predicate)
    {
        this.conditionalStatement.and(predicate);
        return this;
    }

    public GroupSql or(String condition)
    {
        return or(new ConditionNode(condition));
    }

    public GroupSql or(ConditionNode predicate)
    {
        this.conditionalStatement.or(predicate);
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
            sb.append(CollectionUtil.asString(targets,
                    "", "", ", ", "", "",
                    null, null));
            sb.append("FROM ").append(querySql).append(Symbol.WHITESPACE)
                    .append(alias).append(Symbol.WHITESPACE);
        }
        else
            sb = new StringBuilder(super.getSql());

        sb.append("GROUP BY ")
                .append(CollectionUtil.asString(groups,
                    "", " ", ", ", "", "",
                    null, null));

        String conditions = conditionalStatement.get();
        if(!StringUtil.isEmpty(conditions))
            sb.append("HAVING ")
                    .append(conditions);

        return sb.toString();
    }

}
