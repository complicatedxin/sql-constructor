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

package com.zincyanide.sqlconstructor.dml.query.builder.factory;

import com.zincyanide.sqlconstructor.internal.Attachable;
import com.zincyanide.sqlconstructor.dml.query.builder.BaseQuerySqlBuilder;

public class MySQLBaseQuerySqlBuilderFactory implements ReusableBaseQuerySqlBuilderFactory
{
    private static volatile MySQLBaseQuerySqlBuilderFactory factory;

    private MySQLBaseQuerySqlBuilderFactory()
    {   }

    public static MySQLBaseQuerySqlBuilderFactory getInstance()
    {
        if(factory == null)
            synchronized (MySQLBaseQuerySqlBuilderFactory.class)
            {
                if(factory == null)
                    factory = new MySQLBaseQuerySqlBuilderFactory();
            }
        return factory;
    }

    @Override
    public BaseQuerySqlBuilder manu()
    {
        BaseQuerySqlBuilder sqlBuilder = (BaseQuerySqlBuilder) Attachable.THREAD_ATTACHMENT.get();

        if (sqlBuilder == null) //TODO sqlBuilder instanceof MySQLBuilder
        {
            sqlBuilder = new BaseQuerySqlBuilder();
            sqlBuilder.attach();
        }

        return sqlBuilder;
    }

    /**
     * @return new sqlBuilder or other else even NULL
     */
    @Override
    public BaseQuerySqlBuilder manu(boolean fresh)
    {
        BaseQuerySqlBuilder sqlBuilder;
        if (fresh)
        {
            sqlBuilder = new BaseQuerySqlBuilder();
            sqlBuilder.attach();
        }
        sqlBuilder = (BaseQuerySqlBuilder) Attachable.THREAD_ATTACHMENT.get();

        return sqlBuilder;
    }

    @Override
    public BaseQuerySqlBuilder obtain()
    {
        BaseQuerySqlBuilder sqlBuilder = (BaseQuerySqlBuilder) Attachable.THREAD_ATTACHMENT.get();

        if (sqlBuilder == null)
            throw new NullPointerException();

        return sqlBuilder;
    }
}
