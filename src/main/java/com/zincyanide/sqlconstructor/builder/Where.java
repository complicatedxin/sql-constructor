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

public class Where
{
    StringBuilder sb;

    public static final String ANYWHERE = "1 = 1";
    private static final String AND = "AND ";
    private static final String OR = "OR ";

    public Where(StringBuilder sb)
    {
        this.sb = sb;
    }

    public BaseQuerySql build()
    {
        return new BaseQuerySql(this.sb.toString());
    }

    public Condition and(String condition)
    {
        if (!StringUtil.isEmpty(condition))
            sb.append(AND)
                    .append(condition)
                    .append(Separate.WHITESPACE);

        return new SingleCondition(this.sb);
    }

    public Condition or(String condition)
    {
        if (!StringUtil.isEmpty(condition))
            sb.append(OR)
                    .append(condition)
                    .append(Separate.WHITESPACE);

        return new SingleCondition(this.sb);
    }
}
