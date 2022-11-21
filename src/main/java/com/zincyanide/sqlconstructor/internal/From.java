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

import com.zincyanide.sqlconstructor.internal.condition.ConditionNode;

public class From extends BuilderMinion implements Cacheable
{
    public static final String WHERE = "WHERE ";

    protected AliasField<String> tableField;


    public Where where(String condition)
    {
        return where(new ConditionNode(condition));
    }

    public Where where(ConditionNode node)
    {
        Where where = getFellow(Where.class);
        where.and(node);
        return where;
    }

    public From(SqlBuilder chief)
    {
        super(chief);
    }

    public void setTable(String table, String alias)
    {
        if(this.tableField == null)
        {
            this.tableField = new AliasField<>(table, alias);
        }
        this.tableField.setField(table);
        this.tableField.setAlias(alias);
    }

    public AliasField<String> getTableField()
    {
        return tableField;
    }

    @Override
    public void clear()
    {
        tableField = null;
    }
}
