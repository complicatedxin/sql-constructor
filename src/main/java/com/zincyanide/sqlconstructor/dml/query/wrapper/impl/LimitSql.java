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

package com.zincyanide.sqlconstructor.dml.query.wrapper.impl;

import com.zincyanide.sqlconstructor.dml.query.QuerySql;
import com.zincyanide.sqlconstructor.dml.query.wrapper.QuerySqlWrapper;

public class LimitSql extends QuerySqlWrapper
{
    private int queryOffset = 0;
    private int queryNum = 0;

    public LimitSql(QuerySql querySql, int queryOffset, int queryNum)
    {
        super(querySql);
        this.queryOffset = queryOffset;
        this.queryNum = queryNum;
    }

    @Override
    public String getSql()
    {
        return super.getSql()
                + " LIMIT " + queryOffset + "," + queryNum;
    }
}
