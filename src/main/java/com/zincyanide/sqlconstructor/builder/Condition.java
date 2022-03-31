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

public abstract class Condition
{
    StringBuilder sb;

    public Condition(StringBuilder sb)
    {
        this.sb = sb;
    }

    public abstract Condition and(String condition);

    public abstract Condition or(String condition);

    /**
     * 组合条件的结束符，用于生成组合条件字符串。
     * @see MultiCondition#finish()
     * 单条件不适用此方法
     *
     * @return 组合条件字符串
     */
    public abstract String finish();

    public BaseQuerySql build()
    {
        return new BaseQuerySql(this.sb.toString());
    }

}
