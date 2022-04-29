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

package com.zincyanide.sqlconstructor.builder;

import com.zincyanide.sqlconstructor.BaseQuerySql;
import com.zincyanide.sqlconstructor.internal.Symbol;
import com.zincyanide.sqlconstructor.internal.StringUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class BaseQuerySqlBuilder
{
    StringBuilder sqlSB = new StringBuilder();

    Map<Class<? extends BuilderMinion>, BuilderMinion> minionMap = new HashMap<>();

    @SuppressWarnings("unchecked")
    <T> T getMinion(Class<? extends BuilderMinion> clazz)
    {
        return (T) minionMap.get(clazz);
    }

    //TODO: develop into factory pattern
    {
        minionMap.put(Select.class, new Select(this));
        minionMap.put(From.class, new From(this));
        minionMap.put(Join.class, new Join(this));
        minionMap.put(Where.class, new Where(this));
        minionMap.put(JoinCondition.class, new JoinCondition(this));
        minionMap.put(SingleCondition.class, new SingleCondition(this));

        new InheritableThreadLocal<BaseQuerySqlBuilder>().set(this);
    }

    public BaseQuerySql build()
    {
        return new BaseQuerySql(sqlSB.toString());
    }

    private static final String SELECT = "SELECT ";

    public Select selectAllCols()
    {
        return select(Select.ALL_COLS);
    }

    public Select select(String... columns)
    {
        Objects.requireNonNull(columns);

        sqlSB.append(SELECT);
        for(String column : columns)
        {
            StringUtil.requireNonWhite(column);
            sqlSB.append(column).append(Symbol.COMMA);
        }
        sqlSB.replace(sqlSB.length() - 1, sqlSB.length(), Symbol.WHITESPACE);

        return getMinion(Select.class);
    }

}
