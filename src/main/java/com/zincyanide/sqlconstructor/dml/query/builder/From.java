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

import com.zincyanide.sqlconstructor.Reusable;
import com.zincyanide.sqlconstructor.SqlConstructor;
import com.zincyanide.sqlconstructor.dml.query.BaseQuerySql;
import com.zincyanide.sqlconstructor.internal.StringUtil;
import com.zincyanide.sqlconstructor.internal.condition.PredicateNode;

public class From extends BuilderMinion implements DataIndex, Reusable
{
    static final String WHERE = "WHERE ";
    private static final String INNER = "INNER JOIN ";
    private static final String LEFT = "LEFT JOIN ";
    private static final String RIGHT = "RIGHT JOIN ";

    String tab;
    SqlConstructor subSql;
    String alias;

    public From(BaseQuerySqlBuilder builder)
    {
        super(builder);
    }

    public BaseQuerySql build()
    {
        return chief.build();
    }

    public Where where(String condition)
    {
        return where(new PredicateNode(condition));
    }

    public Where where(PredicateNode predicate)
    {
        Where where = chief.getMinion(Where.class);
        where.and(predicate);
        return where;
    }

    public Join innerJoin(String table, String alias)
    {
        return join(INNER, table, alias);
    }

    public Join leftJoin(String table, String alias)
    {
        return join(LEFT, table, alias);
    }

    public Join rightJoin(String table, String alias)
    {
        return join(RIGHT, table, alias);
    }

    protected Join join(String joinManner, String table, String alias)
    {
        StringUtil.requireNonWhite(table);

        Join join = chief.getMinion(Join.class);
        join.to(joinManner, table, alias);

        return join;
    }

    @Override
    public void clean()
    {
        this.tab = null;
        this.subSql = null;
        this.alias = null;
    }
}
