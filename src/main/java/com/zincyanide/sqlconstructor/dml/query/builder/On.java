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

import com.zincyanide.sqlconstructor.dml.query.BaseQuerySql;
import com.zincyanide.sqlconstructor.internal.Where;
import com.zincyanide.sqlconstructor.internal.condition.ConditionalStatement;
import com.zincyanide.sqlconstructor.internal.condition.ConditionNode;

public class On
{
    ConditionalStatement conditionalStatement = new ConditionalStatement();

    private QueryFrom recall;

    public On(QueryFrom recall)
    {
        this.recall = recall;
    }

    public On and(String condition)
    {
        return and(new ConditionNode(condition));
    }

    public On and(ConditionNode predicate)
    {
        this.conditionalStatement.and(predicate);
        return this;
    }

    public On or(String condition)
    {
        return or(new ConditionNode(condition));
    }

    public On or(ConditionNode predicate)
    {
        this.conditionalStatement.or(predicate);
        return this;
    }

    public Join innerJoin(String table, String alias)
    {
        return recall.innerJoin(table, alias);
    }

    public Join leftJoin(String table, String alias)
    {
        return recall.leftJoin(table, alias);
    }

    public Join rightJoin(String table, String alias)
    {
        return recall.rightJoin(table, alias);
    }

    public Where where(String condition)
    {
        return recall.where(condition);
    }

    public Where where(ConditionNode node)
    {
        return recall.where(node);
    }

    public BaseQuerySql build()
    {
        return recall.build();
    }

}
