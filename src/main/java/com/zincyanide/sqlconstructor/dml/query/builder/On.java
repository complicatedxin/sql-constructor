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

import com.zincyanide.sqlconstructor.internal.Conditional;
import com.zincyanide.sqlconstructor.internal.condition.Condition;
import com.zincyanide.sqlconstructor.internal.condition.PredicateNode;

public class On extends Condition implements DataIndex
{
    public On(Conditional belongs)
    {
        super(belongs);
    }

    private BaseQuerySqlBuilder getChief()
    {
        Join join = belongs.incarnation();
        return join.chief;
    }

    @Override
    public Join innerJoin(String table, String alias)
    {
        return getChief().getMinion(From.class).innerJoin(table, alias);
    }

    @Override
    public Join leftJoin(String table, String alias)
    {
        return getChief().getMinion(From.class).leftJoin(table, alias);
    }

    @Override
    public Join rightJoin(String table, String alias)
    {
        return getChief().getMinion(From.class).rightJoin(table, alias);
    }

    @Override
    public Where where(String condition)
    {
        return where(new PredicateNode(condition));
    }

    public Where where(PredicateNode predicate)
    {
        return getChief().getMinion(Where.class).and(predicate);
    }

}
