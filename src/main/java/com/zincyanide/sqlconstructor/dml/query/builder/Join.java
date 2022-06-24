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
import com.zincyanide.sqlconstructor.internal.Conditional;
import com.zincyanide.sqlconstructor.internal.condition.PredicateNode;
import java.util.LinkedList;
import java.util.List;

public class Join extends BuilderMinion implements Conditional, Reusable
{
    static final String ON = "ON ";

    List<To> tos;
    List<On> ons;

    public Join(BaseQuerySqlBuilder builder)
    {
        super(builder);
        this.tos = new LinkedList<>();
        this.ons = new LinkedList<>();
    }

    void to(String manner, String tab, String alias)
    {
        tos.add(new To(manner, tab, alias));
    }

    public On on(String condition)
    {
        return on(new PredicateNode(condition));
    }

    public On on(PredicateNode predicate)
    {
        On on = new On(this);
        on.and(predicate);
        ons.add(on);
        return on;
    }

    @Override
    public <T> T incarnation()
    {
        return (T) this;
    }

    @Override
    public void clean()
    {
        this.tos = new LinkedList<>();
        this.ons = new LinkedList<>();
    }

    class To
    {
        String manner;
        String tab;
        String alias = "";

        public To(String manner, String tab, String alias)
        {
            this.manner = manner;
            this.tab = tab;
            this.alias = alias;
        }
    }
}
