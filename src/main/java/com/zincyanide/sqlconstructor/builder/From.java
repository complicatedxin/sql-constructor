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
import com.zincyanide.sqlconstructor.internal.Separate;
import com.zincyanide.sqlconstructor.internal.StringUtil;

public class From
{
    StringBuilder sb;

    private static final String WHERE = "WHERE ";
    private static final String INNER = "INNER";
    private static final String LEFT = "LEFT";
    private static final String RIGHT = "RIGHT";
    private static final String JOIN = " JOIN ";

    public From(StringBuilder sb)
    {
        this.sb = sb;
    }

    public BaseQuerySql build()
    {
        return new BaseQuerySql(this.sb.toString());
    }

    public Where where(String condition)
    {
        if(StringUtil.isWhite(condition))
            condition = Where.ANYWHERE;

        sb.append(WHERE)
                .append(condition)
                .append(Separate.WHITESPACE);

        return new Where(this.sb);
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

    private Join join(String joinManner, String table, String alias)
    {
        StringUtil.requireNonWhite(table);

        sb.append(joinManner).append(JOIN)
                .append(table)
                .append(Separate.WHITESPACE);

        if(!StringUtil.isEmpty(alias)
                && !table.contains(Separate.WHITESPACE))
            sb.append(alias).append(Separate.WHITESPACE);

        return new Join(this.sb);
    }
}
