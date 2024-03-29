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

public class UpdateSqlBuilder
        extends SqlBuilder
        implements Bindable, Cacheable
{
    public Update update(String table)
    {
        return update(table, "");
    }

    public Update update(String table, String alias)
    {
        Update update = getMinion(Update.class);
        update.setTable(table, alias);
        return update;
    }

    @Override
    public UpdateSql build()
    {
        StringBuilder sb = new StringBuilder();

        buildupUpdate(sb);

        buildupSet(sb);

        buildupWhere(sb);

        return new UpdateSql(sb.toString());
    }

    private void buildupUpdate(StringBuilder sb)
    {
        Update update = getMinion(Update.class);
        sb.append(Update.UPDATE)
                .append(update.tableField)
                .append(CollectionUtil.asString(
                        update.sqlFieldList,
                        "", "",
                        "",
                        ", ", " ",
                        null, null))
                .append(Symbol.WHITESPACE);
    }

    private void buildupSet(StringBuilder sb)
    {
        ValueConverter converter = new ValueConverter();
        Set set = getMinion(Set.class);
        sb.append(Set.SET)
                .append(MapUtil.asString(set.changes,
                        "", "",
                        ", ",
                        "", "",
                        null, (e) -> e.getKey() instanceof Joint ?
                                e.getValue() : e.getKey() + " = " + converter.handleObjectVal(e.getValue())))
                .append(Symbol.WHITESPACE);
    }

    private void buildupWhere(StringBuilder sb)
    {
        Where where = getMinion(Where.class);
        String conditions = where.getConditionalStatement().get();
        if(!StringUtil.isEmpty(conditions))
            sb.append(From.WHERE).append(conditions);
    }

    @Override
    protected void summon()
    {
        minions.put(Update.class, new Update(this));
        minions.put(Set.class, new Set(this));
        minions.put(Where.class, new Where(this));
    }

    @Override
    public void bind()
    {
        Bindable.UPDATE_BUILDER.set(this);
    }

    @Override
    public void unbind()
    {
        Bindable.UPDATE_BUILDER.remove();
    }

    @Override
    public void clear()
    {
        clearUpdate();
        clearSet();
        clearWhere();
    }

    public UpdateSqlBuilder clearUpdate()
    {
        getMinion(Update.class).clear();
        return this;
    }

    public Update clearSet()
    {
        getMinion(Set.class).clear();
        return getMinion(Update.class);
    }

    private Set clearWhere()
    {
        getMinion(Where.class).clear();
        return getMinion(Set.class);
    }

}
