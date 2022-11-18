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
import com.zincyanide.sqlconstructor.internal.*;
import java.io.Serializable;
import java.util.*;

public class BaseQuerySqlBuilder implements Serializable, Attachable, Reusable
{
    private final Map<Class<? extends BuilderMinion>, BuilderMinion> minions = new HashMap<>();

    public BaseQuerySqlBuilder()
    {
        summon();
    }

    private void summon()
    {
        minions.put(Select.class, new Select(this));
        minions.put(From.class, new From(this));
        minions.put(Join.class, new Join(this));
        minions.put(Where.class, new Where(this));
    }

    @Override
    public void attach()
    {
        Attachable.THREAD_ATTACHMENT.set(this);
    }

    @SuppressWarnings("unchecked")
    <T extends BuilderMinion> T getMinion(Class<T> clazz)
    {
        return (T) minions.get(clazz);
    }

    public BaseQuerySql build()
    {
        StringBuilder sb = new StringBuilder();

        buildupSelect(sb);

        buildupFrom(sb);

        buildupJoin(sb);

        buildupWhere(sb);

        return new BaseQuerySql(sb.toString());
    }

    private void buildupSelect(StringBuilder sb)
    {
        Select select = getMinion(Select.class);
        sb.append(SELECT)
            .append(select.mode)
            .append(CollectionUtil.asString(select.cols,
                    "", " ", ", ", "", "",
                    null, null));
    }

    private void buildupFrom(StringBuilder sb)
    {
        From from = getMinion(From.class);
        sb.append(Select.FROM);
        if (from.subSql != null)
            sb.append(from.subSql);
        else
            sb.append(from.tab);
        sb.append(Symbol.WHITESPACE)
            .append(from.alias).append(Symbol.WHITESPACE);
    }

    private void buildupJoin(StringBuilder sb)
    {
        Join join = getMinion(Join.class);
        List<Join.To> tos = join.tos;
        List<On> ons = join.ons;
        Join.To to;
        for (int i = 0; i < tos.size(); i++)
            sb.append((to=tos.get(i)).manner)
                .append(to.tab).append(Symbol.WHITESPACE)
                .append(to.alias).append(Symbol.WHITESPACE)
                .append(Join.ON).append(ons.get(i).condition.getConditions()).append(Symbol.WHITESPACE);
    }

    private void buildupWhere(StringBuilder sb)
    {
        Where where = getMinion(Where.class);
        String conditions = where.condition.getConditions();
        if(!StringUtil.isEmpty(conditions))
            sb.append(From.WHERE).append(conditions);
    }

    private static final String SELECT = "SELECT ";

    public Select select(String... columns)
    {
        Objects.requireNonNull(columns);

        return select(Select.Mode.DEFAULT, Arrays.asList(columns));
    }

    public Select selectDistinct(String... columns)
    {
        Objects.requireNonNull(columns);

        return select(Select.Mode.DISTINCT, Arrays.asList(columns));
    }

    public Select selectAllCols()
    {
        return select("*");
    }

    public Select selectCount()
    {
        return select("count(1)");
    }

    public Select selectCount(String in)
    {
        return select("count(" + in + ")");
    }

    private Select select(String mode, List<String> cols)
    {
        cols.forEach(StringUtil::requireNonWhite);

        Select select = getMinion(Select.class);
        select.mode = mode;
        select.cols = cols;

        return select;
    }




    //TODO:  sum(), case when, ...

    @Override
    public void clean()
    {
        cleanSelect();
        cleanFrom();
        cleanJoin();
        cleanWhere();
    }

    public BaseQuerySqlBuilder cleanSelect()
    {
        getMinion(Select.class).clean();
        return this;
    }

    public Select cleanFrom()
    {
        getMinion(From.class).clean();
        return getMinion(Select.class);
    }

    public From cleanJoin()
    {
        getMinion(Join.class).clean();
        return getMinion(From.class);
    }

    public From cleanWhere()
    {
        getMinion(Where.class).clean();
        return getMinion(From.class);
    }


}
