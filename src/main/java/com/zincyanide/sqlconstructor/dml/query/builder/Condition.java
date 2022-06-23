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
import com.zincyanide.sqlconstructor.internal.Conditional;
import com.zincyanide.sqlconstructor.internal.condition.PredicateNode;

public class Condition
{
    private PredicateNode root;

    protected final Conditional belongs;

    public Condition(Conditional belongs)
    {
        this.belongs = belongs;
        this.root = new PredicateNode();
    }

    public Condition and(String condition)
    {
        root = root.add(condition, false);
        return this;
    }

    public Condition and(PredicateNode predicate)
    {
        root = root.add(predicate, false);
        return this;
    }

    public Condition or(String condition)
    {
        root = root.add(condition, true);
        return this;
    }

    public Condition or(PredicateNode predicate)
    {
        root = root.add(predicate, true);
        return this;
    }

    protected String getConditions()
    {
        return root.ignite();
    }

    public BaseQuerySql build()
    {
        return belongs.incarnation().chief.build();
    }
}
