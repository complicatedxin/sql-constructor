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

package com.zincyanide.sqlconstructor.wrapper.impl;

import com.zincyanide.sqlconstructor.SqlConstructor;
import com.zincyanide.sqlconstructor.internal.Symbol;
import com.zincyanide.sqlconstructor.wrapper.QuerySqlWrapper;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class OrderSql extends QuerySqlWrapper
{
    private static final String ASC = "ASC";
    private static final String DESC = "DESC";

    private Map<String, Boolean> orderMap = new LinkedHashMap<>();

    public OrderSql(SqlConstructor sqlConstructor)
    {
        super(sqlConstructor);
    }
    public OrderSql(SqlConstructor sqlConstructor, String orderColumn, boolean isDesc)
    {
        super(sqlConstructor);
        orderMap.put(orderColumn, isDesc);
    }

    public OrderSql orderBy(String orderColumn, boolean isDesc)
    {
        orderMap.put(orderColumn, isDesc);
        return this;
    }

    @Override
    public String getSql()
    {
        if(orderMap.size() == 0)
            return super.getSql();

        StringBuilder sb = new StringBuilder(super.getSql() + " ORDER BY ");
        Iterator<Map.Entry<String, Boolean>> iterator =
                orderMap.entrySet().iterator();
        do {
            Map.Entry<String, Boolean> entry = iterator.next();
            sb.append(entry.getKey())
                    .append(Symbol.WHITESPACE)
                    .append(isDesc(entry.getValue()))
                    .append(Symbol.COMMA);
        } while (iterator.hasNext());
        sb.replace(sb.length()-1, sb.length(), Symbol.WHITESPACE);

        return sb.toString();
    }

    private String isDesc(boolean isDesc)
    {
        return isDesc ? DESC : ASC;
    }
}
