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

import com.zincyanide.sqlconstructor.internal.condition.PredicateNode;

public class JoinCondition extends Condition
{
    public JoinCondition(Conditional belongs)
    {
        super(belongs);
    }

    public JoinCondition and(String condition)
    {
        return and(new PredicateNode(condition));
    }

    public JoinCondition and(PredicateNode predicate)
    {
        super.and(predicate);
        return this;
    }

    public JoinCondition or(String condition)
    {
        return or(new PredicateNode(condition));
    }

    public JoinCondition or(PredicateNode predicate)
    {
        super.or(predicate);
        return this;
    }

    public Where where(String condition)
    {
        return where(new PredicateNode(condition));
    }

    public Where where(PredicateNode predicate)
    {
        Where where = belongs.incarnation().chief.getMinion(Where.class);
        where.and(predicate);
        return where;
    }
}
