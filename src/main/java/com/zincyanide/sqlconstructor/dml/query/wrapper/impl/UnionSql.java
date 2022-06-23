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

import java.util.LinkedList;
import java.util.List;

public class UnionSql extends QuerySqlWrapper
{
    private static final String UNION = " UNION ";
    private static final String UNION_ALL = " UNION ALL ";

    List<UnionSqlEntry> entryList;

    public UnionSql(QuerySql querySql)
    {
        super(querySql);
        this.entryList = new LinkedList<>();
    }

    public UnionSql union(QuerySql querySql)
    {
        entryList.add(new UnionSqlEntry(querySql, UNION));
        return this;
    }

    public UnionSql unionAll(QuerySql querySql)
    {
        entryList.add(new UnionSqlEntry(querySql, UNION_ALL));
        return this;
    }

    public String getSql()
    {
        StringBuilder sb = new StringBuilder(querySql.getSql());
        for(UnionSqlEntry e : entryList)
            sb.append(e.unionManner).append(e.unionSql.getSql());
        return sb.toString();
    }

    private class UnionSqlEntry
    {
        QuerySql unionSql;
        String unionManner;

        public UnionSqlEntry(QuerySql unionSql, String unionManner)
        {
            this.unionSql = unionSql;
            this.unionManner = unionManner;
        }
    }
}
