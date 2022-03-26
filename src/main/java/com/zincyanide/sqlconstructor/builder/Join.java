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

import com.zincyanide.sqlconstructor.internal.Separate;
import com.zincyanide.sqlconstructor.internal.StringUtil;

public class Join
{
    BaseQuerySqlBuilder builder;

    private static final String ON = "ON ";

    public Join(BaseQuerySqlBuilder builder)
    {
        this.builder = builder;
    }

    public JoinCondition on(String joinCondition)
    {
        StringUtil.requireNonWhite(joinCondition);

        builder.sqlSB
                .append(ON)
                .append(joinCondition)
                .append(Separate.WHITESPACE);

        return new JoinCondition(this.builder);
    }

}
