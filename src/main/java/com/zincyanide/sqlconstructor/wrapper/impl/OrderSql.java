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
import com.zincyanide.sqlconstructor.wrapper.QuerySqlWrapper;

public class OrderSql extends QuerySqlWrapper
{
    private static final String ASC = "ASC";
    private static final String DESC = "DESC";

    private String orderColumn;
    private boolean isDesc = false;

    public OrderSql(SqlConstructor sqlConstructor, String orderColumn, boolean isDesc)
    {
        super(sqlConstructor);
        this.orderColumn = orderColumn;
        this.isDesc = isDesc;
    }

    @Override
    public String getSql()
    {
        return super.getSql()
                + " ORDER BY " + orderColumn + " " + (isDesc ? DESC : ASC);
    }
}
