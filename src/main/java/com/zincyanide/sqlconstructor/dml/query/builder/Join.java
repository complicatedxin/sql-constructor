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

import com.zincyanide.sqlconstructor.internal.BuilderMinion;
import com.zincyanide.sqlconstructor.internal.Cacheable;
import com.zincyanide.sqlconstructor.internal.From;
import com.zincyanide.sqlconstructor.internal.condition.ConditionNode;
import java.util.LinkedList;
import java.util.List;

public class Join extends BuilderMinion implements Cacheable
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
        return on(new ConditionNode(condition));
    }

    public On on(ConditionNode node)
    {
        On on = new On(getFellow(QueryFrom.class));
        on.and(node);
        ons.add(on);
        return on;
    }

    @Override
    public void clear()
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
