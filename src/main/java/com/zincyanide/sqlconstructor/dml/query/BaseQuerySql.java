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

package com.zincyanide.sqlconstructor.dml.query;

import com.zincyanide.sqlconstructor.dml.query.builder.BaseQuerySqlBuilder;

/**
 *  A base query sql in this project
 *  is defined as a sql only including
 *  select, from, join and/or where statement.
 *
 *  To build a BaseQuerySql, a builder is recommended.
 *  @see BaseQuerySqlBuilder
 */
public class BaseQuerySql extends QuerySql
{
    private String sql;

    public BaseQuerySql(String sql)
    {
        this.sql = sql;
    }

    /**
     * is used to validate the format of sql
     * @return sqlStr
     */
    @Override
    public String getSql()
    {
        return sql;
    }

}
