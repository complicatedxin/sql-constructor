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

package com.zincyanide.sqlconstructor.internal;

import com.zincyanide.sqlconstructor.SqlConstructor;
import com.zincyanide.sqlconstructor.internal.condition.ConditionalStatement;
import com.zincyanide.sqlconstructor.internal.condition.ConditionNode;

public class Where extends BuilderMinion implements Cacheable
{
    public static final String ANYWHERE = ConditionNode.Manner.ALL;
    public static final String NOWHERE = ConditionNode.Manner.NONE;

    ConditionalStatement conditionalStatement = new ConditionalStatement();

    public Where(SqlBuilder chief)
    {
        super(chief);
    }

    public Where and(String condition)
    {
        return and(new ConditionNode(condition));
    }

    public Where and(ConditionNode predicate)
    {
        this.conditionalStatement.and(predicate);
        return this;
    }

    public Where or(String condition)
    {
        return or(new ConditionNode(condition));
    }

    public Where or(ConditionNode predicate)
    {
        this.conditionalStatement.or(predicate);
        return this;
    }

    public SqlConstructor build()
    {
        return getChief().build();
    }

    @Override
    public void clear()
    {
        this.conditionalStatement = new ConditionalStatement();
    }

    public ConditionalStatement getConditionalStatement()
    {
        return conditionalStatement;
    }
}
