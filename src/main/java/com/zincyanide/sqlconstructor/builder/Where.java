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

public class Where extends BuilderMinion
{
    public static final String ANYWHERE = "1 = 1";
    private static final String AND = "AND ";
    private static final String OR = "OR ";

    public Where(BaseQuerySqlBuilder builder)
    {
        super(builder);
    }

    public BaseQuerySql build()
    {
        return builder.build();
    }

    public Condition and(String condition)
    {
        if (!StringUtil.isEmpty(condition))
            builder.sqlSB.append(AND)
                    .append(condition)
                    .append(Symbol.WHITESPACE);

        return builder.getMinion(SingleCondition.class);
    }

    public Condition or(String condition)
    {
        if (!StringUtil.isEmpty(condition))
            builder.sqlSB.append(OR)
                    .append(condition)
                    .append(Symbol.WHITESPACE);

        return builder.getMinion(SingleCondition.class);
    }
}
