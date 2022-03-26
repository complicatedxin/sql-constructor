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

public class JoinCondition extends Condition
{
    public JoinCondition(BaseQuerySqlBuilder builder)
    {
        super(builder);
    }

    public Join innerJoin(String table, String alias)
    {
        return new From(this.builder).innerJoin(table, alias);
    }

    public Join leftJoin(String table, String alias)
    {
        return new From(this.builder).leftJoin(table, alias);
    }

    public Join rightJoin(String table, String alias)
    {
        return new From(this.builder).rightJoin(table, alias);
    }

    public Where where(String condition)
    {
        return new From(builder).where(condition);
    }

}
