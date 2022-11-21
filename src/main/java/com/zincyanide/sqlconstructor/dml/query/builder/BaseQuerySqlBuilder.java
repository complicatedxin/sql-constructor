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
import java.util.*;

public class BaseQuerySqlBuilder
        extends com.zincyanide.sqlconstructor.internal.SqlBuilder
        implements Bindable, Cacheable
{
    public BaseQuerySql build()
    {
        StringBuilder sb = new StringBuilder();

        buildupSelect(sb);
        if(buildupFrom(sb))
        {
            buildupJoin(sb);
            buildupWhere(sb);
        }

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

    private boolean buildupFrom(StringBuilder sb)
    {
        QueryFrom queryFrom = getMinion(QueryFrom.class);
        AliasField<?> af = queryFrom.subSqlField;
        if (af == null)
            af = queryFrom.getTableField();
        if (af == null)
            return false;
        sb.append(Select.FROM)
                .append(af.getField())
                .append(Symbol.WHITESPACE)
                .append(af.getAlias())
                .append(Symbol.WHITESPACE);
        return true;
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
                    .append(Join.ON)
                    .append(ons.get(i).conditionalStatement.get()).append(Symbol.WHITESPACE);
    }

    private void buildupWhere(StringBuilder sb)
    {
        Where where = getMinion(Where.class);
        String conditions = where.getConditionalStatement().get();
        if(!StringUtil.isEmpty(conditions))
            sb.append(From.WHERE).append(conditions);
    }

    private static final String SELECT = "SELECT ";

    public Select.Chain select(Select.StringValue col)
    {
        return select(col.toString());
    }

    public Select.Chain select(String column)
    {
        Select select = getMinion(Select.class);
        select.setMode(Select.Mode.DEFAULT);
        select.addCol(column);
        return (Select.Chain) select;
    }

    public Select select(String... columns)
    {
        return select(Select.Mode.DEFAULT, Arrays.asList(columns));
    }

    public Select.Chain selectDistinct(String column)
    {
        Select select = getMinion(Select.class);
        select.setMode(Select.Mode.DEFAULT);
        select.addCol(column);
        return (Select.Chain) select;
    }

    public Select selectDistinct(String... columns)
    {
        return select(Select.Mode.DISTINCT, Arrays.asList(columns));
    }

    public Select selectAll()
    {
        return select("*");
    }

    public Select selectCount()
    {
        return selectCount("1");
    }

    public Select selectCount(String in)
    {
        return select("count(" + in + ")");
    }

    private Select select(String mode, List<String> cols)
    {
        Select select = getMinion(Select.class);
        select.setMode(mode);
        cols.forEach(select::addCol);
        return select;
    }

    @Override
    protected void summon()
    {
        minions.put(Select.class, new Select.Chain(this));
        minions.put(QueryFrom.class, new QueryFrom(this));
        minions.put(Join.class, new Join(this));
        minions.put(Where.class, new Where(this));
    }

    @Override
    public void bind()
    {
        Bindable.QUERY_BUILDER.set(this);
    }

    @Override
    public void unbind()
    {
        Bindable.QUERY_BUILDER.remove();
    }

    @Override
    public void clear()
    {
        clearSelect();
        clearFrom();
        clearJoin();
        clearWhere();
    }

    public BaseQuerySqlBuilder clearSelect()
    {
        getMinion(Select.class).clear();
        return this;
    }

    public Select clearFrom()
    {
        getMinion(QueryFrom.class).clear();
        return getMinion(Select.class);
    }

    public QueryFrom clearJoin()
    {
        getMinion(Join.class).clear();
        return getMinion(QueryFrom.class);
    }

    public QueryFrom clearWhere()
    {
        getMinion(Where.class).clear();
        return getMinion(QueryFrom.class);
    }


}
