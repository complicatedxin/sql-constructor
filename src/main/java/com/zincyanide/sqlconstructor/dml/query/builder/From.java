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

import com.zincyanide.sqlconstructor.internal.AliasField;
import com.zincyanide.sqlconstructor.internal.Cacheable;
import com.zincyanide.sqlconstructor.dml.query.BaseQuerySql;
import com.zincyanide.sqlconstructor.dml.query.QuerySql;
import com.zincyanide.sqlconstructor.internal.condition.ConditionNode;

public class From extends BuilderMinion implements Cacheable
{
    static final String WHERE = "WHERE ";
    private static final String INNER = "INNER JOIN ";
    private static final String LEFT = "LEFT JOIN ";
    private static final String RIGHT = "RIGHT JOIN ";

    AliasField<String> tableField;
    AliasField<QuerySql> subSqlField;

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
        return where(new ConditionNode(condition));
    }

    public Where where(ConditionNode node)
    {
        Where where = chief.getMinion(Where.class);
        where.and(node);
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
        Join join = chief.getMinion(Join.class);
        join.to(joinManner, table, alias);

        return join;
    }

    void setTable(String table, String alias)
    {
        if(this.tableField == null)
        {
            this.tableField = new AliasField<>(table, alias);
        }
        this.tableField.setField(table);
        this.tableField.setAlias(alias);
    }

    void setSubSql(QuerySql subSql, String alias)
    {
        if(this.subSqlField == null)
        {
            this.subSqlField = new AliasField<>(subSql, alias);
        }
        this.subSqlField.setField(subSql);
        this.subSqlField.setAlias(alias);
    }

    @Override
    public void clean()
    {
        this.tableField = null;
        this.subSqlField = null;
    }
}
