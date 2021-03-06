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
import com.zincyanide.sqlconstructor.internal.Reusable;
import com.zincyanide.sqlconstructor.dml.query.BaseQuerySql;
import com.zincyanide.sqlconstructor.internal.condition.Condition;
import com.zincyanide.sqlconstructor.internal.condition.PredicateNode;

public class Where extends BuilderMinion implements Conditional, Reusable
{
    public static final String ANYWHERE = PredicateNode.Manner.ALL;
    public static final String NOWHERE = PredicateNode.Manner.NONE;

    Condition condition = new Condition();

    public Where(BaseQuerySqlBuilder builder)
    {
        super(builder);
    }

    public Where and(String condition)
    {
        return and(new PredicateNode(condition));
    }

    public Where and(PredicateNode predicate)
    {
        this.condition.and(predicate);
        return this;
    }

    public Where or(String condition)
    {
        return or(new PredicateNode(condition));
    }

    public Where or(PredicateNode predicate)
    {
        this.condition.or(predicate);
        return this;
    }

    public BaseQuerySql build()
    {
        return chief.build();
    }

    @Override
    public <T> T incarnation()
    {
        return (T) this;
    }

    @Override
    public void clean()
    {
        this.condition = new Condition();
    }

}
