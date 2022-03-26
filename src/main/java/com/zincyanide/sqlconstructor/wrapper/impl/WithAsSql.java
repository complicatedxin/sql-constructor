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

package com.zincyanide.sqlconstructor.wrapper.impl;

import com.zincyanide.sqlconstructor.SqlConstructor;
import com.zincyanide.sqlconstructor.internal.StringUtil;
import com.zincyanide.sqlconstructor.wrapper.QuerySqlWrapper;
import java.sql.SQLException;

public class WithAsSql extends QuerySqlWrapper
{
    private String withName;
    private SqlConstructor asSqlConstructor;

    public WithAsSql(SqlConstructor asSqlConstructor, String withName,
                     SqlConstructor querySqlConstructor)
    {
        super(querySqlConstructor);
        this.withName = withName;
        this.asSqlConstructor = asSqlConstructor;
    }

    @Override
    public String getSql()
    {
        try {
            validatePremise();

            return "WITH " + withName + " AS ( " + asSqlConstructor.getSql() + " ) "
                    + super.getSql();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return asSqlConstructor.getSql();
    }

    private void validatePremise() throws SQLException
    {
        if(StringUtil.isEmpty(withName)
                || asSqlConstructor == null)
            throw new SQLException("with-as-sql has a null value");
    }
}
