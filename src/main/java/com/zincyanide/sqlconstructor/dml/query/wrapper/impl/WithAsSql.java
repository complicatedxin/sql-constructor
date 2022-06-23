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
import com.zincyanide.sqlconstructor.internal.StringUtil;
import com.zincyanide.sqlconstructor.dml.query.wrapper.QuerySqlWrapper;
import java.sql.SQLException;

public class WithAsSql extends QuerySqlWrapper
{
    private String withName;
    private QuerySql asSql;

    public WithAsSql(QuerySql asSql, String withName,
                     QuerySql querySql)
    {
        super(querySql);
        this.withName = withName;
        this.asSql = asSql;
    }

    @Override
    public String getSql()
    {
        try {
            validatePremise();

            return "WITH " + withName + " AS " + asSql + " "
                    + super.getSql();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return asSql.getSql();
    }

    private void validatePremise() throws SQLException
    {
        if(StringUtil.isBlank(withName)
                || asSql == null)
            throw new SQLException("with-as-sql has a null value");
    }
}
