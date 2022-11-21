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

import com.zincyanide.sqlconstructor.dml.update.UpdateSql;
import com.zincyanide.sqlconstructor.internal.*;
import com.zincyanide.sqlconstructor.internal.condition.ConditionNode;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

public class Set extends BuilderMinion implements Cacheable
{
    static final String SET = "SET ";

    Map<Object, Object> changes = new LinkedHashMap<>();

    public Set(SqlBuilder chief)
    {
        super(chief);
    }

    public Set set(Joint joint)
    {
        changes.put(joint, joint);
        return this;
    }

    public Set set(String col, Object val)
    {
        changes.put(col, val);
        return this;
    }

    private boolean isString(Class<?> clazz)
    {
        return clazz == String.class;
    }

    private boolean isDate(Object obj)
    {
        return obj instanceof Date;
    }

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

    public UpdateSql build()
    {
        return ((UpdateSqlBuilder) getChief()).build();
    }

    @Override
    public void clear()
    {
        changes.clear();
    }
}
