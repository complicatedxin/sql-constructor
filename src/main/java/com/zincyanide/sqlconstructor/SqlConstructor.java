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

package com.zincyanide.sqlconstructor;

import com.zincyanide.sqlconstructor.dml.query.wrapper.QuerySqlWrapper;
import com.zincyanide.sqlconstructor.internal.Symbol;

/**
 * a constructor of sql
 *
 * currently supporting
 *  + query sql:
 * @see com.zincyanide.sqlconstructor.dml.query.BaseQuerySql
 * @see QuerySqlWrapper
 */
public abstract class SqlConstructor
{
    public abstract String getSql();

    @Override
    public String toString()
    {
        return Symbol.BRACKET_LEFT + Symbol.WHITESPACE +
                getSql() +
                Symbol.WHITESPACE + Symbol.BRACKET_RIGHT;
    }
}
