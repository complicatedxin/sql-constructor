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

package com.zincyanide.sqlconstructor.dml.update.builder;

import com.zincyanide.sqlconstructor.dml.query.QuerySql;
import com.zincyanide.sqlconstructor.internal.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Update extends BuilderMinion implements Cacheable
{
    static final String UPDATE = "UPDATE ";

    AliasField<String> tableField;
    List<AliasField<QuerySql>> sqlFieldList;

    public Update(SqlBuilder chief)
    {
        super(chief);
    }

    public Update update(QuerySql correlated, String alias)
    {
        if(sqlFieldList == null)
            sqlFieldList = new LinkedList<>();
        sqlFieldList.add(new AliasField<>(correlated, alias));
        return this;
    }

    public Set set(Joint joint)
    {
        return getFellow(Set.class).set(joint);
    }

    public Set set(String col, Object val)
    {
        return getFellow(Set.class).set(col, val);
    }

    public Set set(Map<String, Object> changes)
    {
        Set set = getFellow(Set.class);
        set.changes.putAll(changes);
        return set;
    }


    @Override
    public void clear()
    {
        tableField = null;
        sqlFieldList = null;
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
}
